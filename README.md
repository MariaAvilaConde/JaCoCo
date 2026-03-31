# JaCoCo
De 4 tests básicos pasamos a 14 tests organizados en grupos con @Nested y @DisplayName.

Las mejoras clave:

1. @BeforeEach — elimina la repetición de new PedidoService() en cada test
2. @Nested — agrupa los tests por responsabilidad, más fácil de leer y mantener
3. @DisplayName — nombres descriptivos en lugar de testClienteVIP1, testClienteVIP2, etc.
4. assertEquals(valor, total, 0.001) — tolerancia de precisión para evitar falsos negativos con double

Escenarios nuevos que no existían antes:

1. "vip" en minúsculas → confirma que el método es case-sensitive
2. tipoCliente = "" → string vacío no rompe nada
3. tipoCliente = null → documenta el NullPointerException como bug conocido
4. total == 500 exacto → el límite del descuento adicional NO se activa
5. total == 501 → justo por encima, SÍ se activa
6. precio = 0 y cantidad = 0 → casos de valor cero
7. precio con decimales → valida precisión numérica

El resultado esperado en JaCoCo es pasar de ~60% a ~95%+ de cobertura de branches en PedidoService.

<img width="702" height="360" alt="image" src="https://github.com/user-attachments/assets/eae03d97-ea9e-4a9a-8dfb-bf9b36cf1aaf" />

<img width="1366" height="720" alt="image" src="https://github.com/user-attachments/assets/ca0b2645-efa0-4685-b710-2039edfb1af9" />
