# VentaSimple — Trabajo Final Integrador (ISW III)

Módulo de **punto de venta / orden de venta**, aplicando 4 patrones de diseño GOF
(**Composite, Singleton, Observer, Strategy**) con backend Java + Spring Boot y frontend React.

## Estructura
- `docs/uml/` — diagramas PlantUML (clases y secuencia) + PNG renderizados
- `docs/wireframes/` — wireframes de las pantallas
- `docs/entrega/` — patrones, análisis DCU, defensa UX y documento maestro
- `docs/superpowers/` — spec y planes de trabajo

## Patrones
- **Composite** → `OrdenVenta` / `ItemVenta` (productos sueltos y kits)
- **Singleton** → `PuntoDeVenta` (única caja del sistema)
- **Observer** → `ConfiguracionI18N` (formato de moneda/fecha)
- **Strategy** → `EstrategiaDescuento` (descuentos sobre el total)

## Integrantes
Santiago Vicente, Josué Ferreyra, Matias Porcari, Delfina Ibañez, Candela Aguilar
