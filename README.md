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

## Cómo correr la app (Spring Boot + Thymeleaf)
Requiere Java 17. Desde la raíz del proyecto:
```bash
# Windows
gradlew.bat bootRun
# Linux/Mac
./gradlew bootRun
```
Luego abrir http://localhost:8080

Correr los tests de los patrones:
```bash
gradlew.bat test
```

## Estructura del código
- `src/main/java/com/ventasimple/domain/` — los 4 patrones (composite, descuento, i18n, PuntoDeVenta)
- `src/main/java/com/ventasimple/web/` — controladores Spring MVC
- `src/main/resources/templates/` — vistas Thymeleaf (4 pantallas)
- `src/test/java/` — tests de los 4 patrones

## Integrantes
Santiago Vicente, Josué Ferreyra, Matias Porcari, Delfina Ibañez, Candela Aguilar
