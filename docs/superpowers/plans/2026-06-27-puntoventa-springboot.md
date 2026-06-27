# VentaSimple — Plan 2: Aplicación Spring Boot + Thymeleaf

**Goal:** Implementar el módulo completo en un único proyecto Spring Boot (Java 17 + Gradle): los 4
patrones GOF en el dominio + UI con Thymeleaf (4 pantallas), con tests de los patrones.

**Architecture:** App Spring Boot MVC. El dominio contiene los patrones (Composite, Singleton,
Strategy, Observer). Los controladores MVC renderizan plantillas Thymeleaf. Estado en memoria
(el Singleton `PuntoDeVenta` guarda la orden actual y la configuración).

**Tech Stack:** Java 17, Spring Boot 3.x (Gradle 8.3), Thymeleaf, JUnit 5.

## Global Constraints
- Idioma del código/UI: español. Sin React (UI = Thymeleaf).
- Patrones GOF (exactos): Composite, Singleton, Observer, Strategy. Nombres de clase según el diagrama
  `docs/uml/01-clases.puml`.
- Observer: métodos `notificar()` / `actualizar()` (no `notify()`).
- El Composite no mezcla el descuento: `OrdenVenta.calcularTotal()` = suma del árbol;
  `calcularTotalFinal()` = aplica la `EstrategiaDescuento`.
- Paquete base: `com.ventasimple`.

## Estructura de archivos
```
build.gradle, settings.gradle
src/main/java/com/ventasimple/
  VentaSimpleApplication.java
  domain/
    PuntoDeVenta.java                         (Singleton)
    composite/ ItemVenta, OrdenVenta, ItemCompuesto, ItemSimple
    descuento/ EstrategiaDescuento, SinDescuento, DescuentoPorcentaje, DescuentoVIP
    i18n/      IObservable, IObserver, ConfiguracionI18N, FormatoMoneda, FormatoFecha
  web/
    OrdenController.java
    PreferenciasController.java
src/main/resources/
  templates/ armar-orden.html, resumen.html, ticket.html, preferencias.html, fragments.html
  static/css/styles.css
  application.properties
src/test/java/com/ventasimple/domain/
  CompositeTest, StrategyTest, SingletonTest, ObserverTest
```

## Tareas

### Task 1: Scaffold del proyecto Gradle + Spring Boot
- `build.gradle` con plugin Spring Boot 3.x, dependencias `spring-boot-starter-web`,
  `spring-boot-starter-thymeleaf`, `spring-boot-starter-test`.
- `settings.gradle` (`rootProject.name = 'ventasimple'`).
- `VentaSimpleApplication.java` con `@SpringBootApplication`.
- `application.properties` (puerto 8080).
- Verificación: `gradle build` compila (app vacía arranca).

### Task 2: Composite (dominio) + test
- `ItemVenta` (interface): `calcularTotal()`, `getDescripcion()`.
- `ItemSimple` (Leaf): `nombre`, `precio`.
- `ItemCompuesto` (Composite): `partes`, `add/remove`, suma recursiva.
- `OrdenVenta` (Composite raíz): `items`, `add`, `calcularTotal()`.
- Test: una orden con ítems simples + un kit anidado suma correcto.

### Task 3: Strategy (descuento) + integración
- `EstrategiaDescuento` (interface): `aplicar(subtotal)`.
- `SinDescuento`, `DescuentoPorcentaje(pct)`, `DescuentoVIP`.
- `OrdenVenta` recibe una `EstrategiaDescuento` y expone `calcularTotalFinal()`.
- Test: subtotal con cada estrategia da el total esperado.

### Task 4: Singleton (PuntoDeVenta) + test
- `PuntoDeVenta`: instancia única (double-checked locking), guarda la orden actual y la
  `ConfiguracionI18N`.
- Test: `getInstancia()` devuelve siempre el mismo objeto.

### Task 5: Observer (i18n) + test
- `IObservable`, `IObserver`, `ConfiguracionI18N` (Subject), `FormatoMoneda`, `FormatoFecha`.
- Test: al `setConfiguracion()`, los observadores reciben el nuevo formato.

### Task 6: Controladores web (Thymeleaf MVC)
- `OrdenController`: armar orden, agregar ítem/kit, resumen, total + descuento (ticket).
- `PreferenciasController`: ver/guardar formato moneda y fecha.

### Task 7: Plantillas Thymeleaf + CSS
- 4 pantallas según los wireframes (`docs/wireframes/wireframes.md`), con diseño inclusivo.

### Task 8: Build, test y smoke run
- `gradle test` (todos verdes) y `gradle bootRun` (la app levanta y las pantallas responden).

## Verificación final
- [ ] `gradle test` verde (4 tests de patrones).
- [ ] `gradle bootRun` levanta en :8080 y se puede armar una orden, aplicar descuento y cambiar formato.
- [ ] Los 4 patrones quedan demostrables en el código.
