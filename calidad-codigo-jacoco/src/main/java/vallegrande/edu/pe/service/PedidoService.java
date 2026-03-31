package vallegrande.edu.pe.service;

public class PedidoService {

    public double calcularTotal(double precio, int cantidad, String tipoCliente) {
        double total = precio * cantidad;

        if (tipoCliente.equals("VIP")) {
            total = total - (total * 0.2);
        } else if (tipoCliente.equals("REGULAR")) {
            total = total - (total * 0.1);
        }

        if (total > 500) {
            total = total - 20;
        }

        return total;
    }
}

/**
cd calidad-codigo-jacoco
mvn clean test
Start-Process "C:\Escritorio\JaCoCo\calidad-codigo-jacoco\target\site\jacoco\index.html"
Pruebas:
Test-Path "calidad-codigo-jacoco/target/site/jacoco/index.html"
Test-Path "calidad-codigo-jacoco/target/site/jacoco/index.html" -PathType Leaf
 */