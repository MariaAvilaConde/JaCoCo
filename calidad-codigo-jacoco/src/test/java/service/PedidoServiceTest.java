package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import vallegrande.edu.pe.service.PedidoService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite completa de pruebas para PedidoService.
 *
 * Cubre:
 * - Todos los tipos de cliente (VIP, REGULAR, OTRO)
 * - Descuento adicional por total > 500
 * - Casos límite (precio 0, cantidad 0, total exactamente 500)
 * - Validaciones de entrada inválida
 */
public class PedidoServiceTest {

    private PedidoService service;

    @BeforeEach
    void setUp() {
        // Se crea una instancia fresca antes de cada test
        service = new PedidoService();
    }

    // =========================================================
    // GRUPO 1: Descuentos por tipo de cliente
    // =========================================================

    @Nested
    @DisplayName("Descuentos por tipo de cliente")
    class DescuentosPorTipoCliente {

        @Test
        @DisplayName("Cliente VIP recibe 20% de descuento")
        void testClienteVIP() {
            // precio=100, cantidad=2 → total bruto=200
            // descuento VIP 20% → 200 - 40 = 160
            double total = service.calcularTotal(100, 2, "VIP");
            assertEquals(160.0, total, 0.001);
        }

        @Test
        @DisplayName("Cliente REGULAR recibe 10% de descuento")
        void testClienteRegular() {
            // precio=100, cantidad=2 → total bruto=200
            // descuento REGULAR 10% → 200 - 20 = 180
            double total = service.calcularTotal(100, 2, "REGULAR");
            assertEquals(180.0, total, 0.001);
        }

        @Test
        @DisplayName("Cliente sin tipo especial no recibe descuento")
        void testClienteSinDescuento() {
            // precio=100, cantidad=2 → total bruto=200
            // sin descuento → 200
            double total = service.calcularTotal(100, 2, "OTRO");
            assertEquals(200.0, total, 0.001);
        }

        @Test
        @DisplayName("Tipo de cliente en minúsculas no aplica descuento (case-sensitive)")
        void testClienteVIPMinusculas() {
            // "vip" != "VIP" → no aplica descuento
            double total = service.calcularTotal(100, 2, "vip");
            assertEquals(200.0, total, 0.001);
        }

        @Test
        @DisplayName("Tipo de cliente vacío no aplica descuento")
        void testClienteVacio() {
            double total = service.calcularTotal(100, 2, "");
            assertEquals(200.0, total, 0.001);
        }
    }

    // =========================================================
    // GRUPO 2: Descuento adicional por total > 500
    // =========================================================

    @Nested
    @DisplayName("Descuento adicional por monto")
    class DescuentoAdicionalPorMonto {

        @Test
        @DisplayName("Total > 500 recibe descuento adicional de 20")
        void testDescuentoAdicionalConClienteRegular() {
            // precio=100, cantidad=6 → total bruto=600
            // descuento REGULAR 10% → 600 - 60 = 540
            // descuento adicional → 540 - 20 = 520
            double total = service.calcularTotal(100, 6, "REGULAR");
            assertEquals(520.0, total, 0.001);
        }

        @Test
        @DisplayName("Total > 500 con cliente VIP también recibe descuento adicional")
        void testDescuentoAdicionalConClienteVIP() {
            // precio=100, cantidad=8 → total bruto=800
            // descuento VIP 20% → 800 - 160 = 640
            // descuento adicional → 640 - 20 = 620
            double total = service.calcularTotal(100, 8, "VIP");
            assertEquals(620.0, total, 0.001);
        }

        @Test
        @DisplayName("Total > 500 sin tipo especial recibe descuento adicional")
        void testDescuentoAdicionalSinTipoCliente() {
            // precio=100, cantidad=6 → total bruto=600
            // sin descuento de cliente → 600
            // descuento adicional → 600 - 20 = 580
            double total = service.calcularTotal(100, 6, "OTRO");
            assertEquals(580.0, total, 0.001);
        }

        @Test
        @DisplayName("Total exactamente 500 NO recibe descuento adicional (límite exacto)")
        void testTotalExactamente500() {
            // precio=100, cantidad=5 → total bruto=500
            // sin descuento de cliente → 500
            // 500 NO es > 500, así que no aplica descuento adicional
            double total = service.calcularTotal(100, 5, "OTRO");
            assertEquals(500.0, total, 0.001);
        }

        @Test
        @DisplayName("Total de 501 SÍ recibe descuento adicional (justo sobre el límite)")
        void testTotalJustoPorEncimaDelLimite() {
            // precio=501, cantidad=1 → total bruto=501
            // sin descuento de cliente → 501
            // 501 > 500 → descuento adicional → 501 - 20 = 481
            double total = service.calcularTotal(501, 1, "OTRO");
            assertEquals(481.0, total, 0.001);
        }
    }

    // =========================================================
    // GRUPO 3: Casos límite (edge cases)
    // =========================================================

    @Nested
    @DisplayName("Casos límite")
    class CasosLimite {

        @Test
        @DisplayName("Precio cero resulta en total cero")
        void testPrecioCero() {
            double total = service.calcularTotal(0, 5, "VIP");
            assertEquals(0.0, total, 0.001);
        }

        @Test
        @DisplayName("Cantidad cero resulta en total cero")
        void testCantidadCero() {
            double total = service.calcularTotal(100, 0, "VIP");
            assertEquals(0.0, total, 0.001);
        }

        @Test
        @DisplayName("Cantidad uno con cliente VIP aplica descuento correctamente")
        void testCantidadUno() {
            // precio=100, cantidad=1 → total bruto=100
            // descuento VIP 20% → 100 - 20 = 80
            double total = service.calcularTotal(100, 1, "VIP");
            assertEquals(80.0, total, 0.001);
        }

        @Test
        @DisplayName("Precio con decimales calcula correctamente")
        void testPrecioDecimal() {
            // precio=99.99, cantidad=2 → total bruto=199.98
            // descuento REGULAR 10% → 199.98 - 19.998 = 179.982
            double total = service.calcularTotal(99.99, 2, "REGULAR");
            assertEquals(179.982, total, 0.001);
        }
    }

    // =========================================================
    // GRUPO 4: Validaciones de entrada inválida
    // =========================================================

    @Nested
    @DisplayName("Validaciones de entrada inválida")
    class ValidacionesEntradaInvalida {

        @Test
        @DisplayName("tipoCliente null lanza NullPointerException")
        void testClienteNullLanzaExcepcion() {
            // El método actual usa .equals() sin null-check → NullPointerException
            // Este test documenta el comportamiento actual (bug conocido)
            assertThrows(NullPointerException.class, () ->
                service.calcularTotal(100, 2, null)
            );
        }
    }
}
