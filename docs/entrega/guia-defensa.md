# Guía de defensa — VentaSimple (Trabajo Final ISW III)

Resumen para estudiar y anticipar preguntas. Equipo: Santiago Vicente, Josué Ferreyra,
Matias Porcari, Delfina Ibañez, Candela Aguilar.

---

## 1. Pitch de 30 segundos

> "VentaSimple es un módulo de **punto de venta**: el vendedor arma una orden con productos sueltos y
> kits, le aplica un descuento, ve el total y cierra la venta, que queda guardada en un historial.
> Está hecho en **Java con Spring Boot** (UI con Thymeleaf) y aplica **4 patrones de diseño GOF**:
> **Composite, Singleton, Observer y Strategy**."

**Datos duros que conviene tener a mano:**
- Backend: Java 17 + Spring Boot 3.2 (Gradle). UI: Thymeleaf. Estado en memoria.
- Tests: uno por patrón (Composite, Strategy, Singleton, Observer), todos verdes.
- Ejemplo de cálculo: Notebook $1.500 + Monitor $900 + Kit(Mouse $80 + Teclado $120) = **$2.600**;
  con 10% de descuento = **$2.340**.

---

## 2. Los 4 patrones (lo más preguntado)

### 🌳 Composite — `OrdenVenta` / `ItemVenta`
- **Qué es:** patrón **estructural**. Arma estructuras de árbol "parte-todo" y trata igual a un ítem
  simple y a una agrupación.
- **Dónde:** `ItemVenta` (interfaz) la implementan `OrdenVenta` (raíz), `ItemCompuesto` (kit) e
  `ItemSimple` (hoja). Todos tienen `calcularTotal()`.
- **Por qué:** el vendedor calcula el total sin preguntarse si un ítem es simple o un kit con cosas
  adentro. La suma es **recursiva**.
- **Pregunta trampa:** *"¿Qué pasa si un kit tiene otro kit adentro?"* → Funciona igual, porque un
  `ItemCompuesto` también es un `ItemVenta`; la recursión lo resuelve solo.

### 🔒 Singleton — `PuntoDeVenta`
- **Qué es:** patrón **creacional**. Garantiza **una única instancia** y un punto de acceso global.
- **Dónde:** `PuntoDeVenta` (constructor privado + atributo estático `instancia` + `getInstancia()`).
  Guarda la orden actual, el **historial de ventas** y la configuración i18n.
- **Por qué:** hay una sola "caja". Si hubiera dos, tendrían historiales y configuraciones distintas
  (estado inconsistente).
- **Pregunta trampa 1:** *"¿No es lo mismo que un bean singleton de Spring?"* → No. El bean de Spring
  es un singleton **gestionado por el contenedor** (uno por contexto). El nuestro es el **Singleton
  GOF clásico**, implementado a mano (constructor privado + `getInstancia()`), independiente de Spring.
- **Pregunta trampa 2:** *"¿Y si hay varios hilos?"* → Usamos **double-checked locking** con
  `synchronized` para que no se creen dos instancias a la vez.

### 📡 Observer — `ConfiguracionI18N`
- **Qué es:** patrón **de comportamiento**. Dependencia uno-a-muchos: cuando el sujeto cambia, avisa
  solo a todos los interesados.
- **Dónde:** `ConfiguracionI18N` (Subject) guarda el formato de moneda y fecha; `FormatoMoneda` y
  `FormatoFecha` (Observers) se actualizan cuando cambia. Interfaces `IObservable` / `IObserver`.
- **Por qué:** al cambiar el formato en Preferencias, todo lo que muestra montos/fechas se actualiza
  **sin polling** (sin andar preguntando) y sin acoplarse.
- **Pregunta trampa:** *"¿Por qué `notificar()` y `actualizar()` y no `notify()`?"* → Porque
  `Object.notify()` es **`final`** en Java (es de threads) y no se puede sobrescribir. Es una mejora
  respecto del ejemplo del TP11.

### 🎯 Strategy — `EstrategiaDescuento` (el patrón nuevo)
- **Qué es:** patrón **de comportamiento**. Familia de algoritmos intercambiables.
- **Dónde:** `EstrategiaDescuento` (interfaz) con `aplicar(subtotal)`; implementaciones `SinDescuento`,
  `DescuentoPorcentaje`, `DescuentoVIP`. La `OrdenVenta` es el **contexto**.
- **Por qué:** el descuento puede variar (ninguno / 10% / VIP 15%) sin llenar la orden de `if/switch`.
- **Pregunta trampa:** *"¿Cómo agregás un descuento nuevo (ej. 2x1)?"* → Creo una clase nueva que
  implemente `EstrategiaDescuento`, **sin tocar** `OrdenVenta`. Eso es el **principio abierto/cerrado**.

---

## 3. Cómo se combinan Composite + Strategy (importante)
El Composite y el Strategy **no se mezclan**:
- `OrdenVenta.calcularTotal()` → suma del árbol (Composite), **sin descuento**.
- `OrdenVenta.calcularTotalFinal()` → toma ese subtotal y le aplica `estrategiaDescuento.aplicar(...)`.

Así el Composite queda intacto (como el TP10 original) y el descuento es un agregado limpio.

---

## 4. Preguntas frecuentes (Q&A)

- **¿Dónde se guarda el ticket emitido?** → En el historial "Ventas emitidas", dentro del `PuntoDeVenta`
  (Singleton). Cada venta se guarda como una **foto** (`Venta`).
- **¿Y si reinicio la app?** → El historial es **en memoria**, así que se borra. Es suficiente para el
  TP; el siguiente paso sería persistir en una base **H2**.
- **¿Por qué el historial NO cambia de moneda si cambio la configuración?** → Porque cada `Venta`
  **congela** su total ya formateado al cerrarse (snapshot). Es la lógica correcta de un comprobante:
  si se emitió en dólares, queda en dólares. (Esto conecta con el tema de **i18n/localización**.)
- **¿Por qué 4 patrones?** → La consigna pide al menos 4 patrones GOF.
- **¿Por qué Thymeleaf y no React?** → Decisión del equipo de hacer todo en Java/Spring Boot. *(Aclarar
  con el profe: la consigna dice "puede usar JS con framework"; nosotros usamos Thymeleaf, que es
  renderizado del lado del servidor.)*

---

## 5. UX / HCI (con el vocabulario del profe)

- **HCI/IPO:** Interacción Persona-Ordenador. Objetivo: **usabilidad** = efectividad, eficiencia y
  satisfacción.
- **5 atributos de usabilidad (Nielsen):** aprendizaje, eficiencia, recordación, errores, satisfacción.
  En la app: flujo corto, nav siempre visible con pestaña activa, prevención de errores (descuento por
  lista, precios no negativos).
- **Norman:** la interfaz deja al usuario **centrarse en su tarea**, no en la app.
- **Metáfora:** **carrito de compras + ticket de caja** (dominio familiar → interfaz transparente).
- **Color (reglas de Murch / daltonismo):** pocos colores (5±2), significado consistente (azul=acción,
  verde=total), y **nunca solo color**: los estados llevan texto. Foco de teclado visible (accesible).
- **DCU + prototipos:** definimos usuario y navegación e hicimos **wireframes antes** de codear.
- **Internacionalización (i18n) ⭐:** la pantalla Preferencias localiza **moneda y fecha**, y además
  **convierte** el valor (US$ 1 = $ 1.500). Es el tema de i18n que vio el profe, resuelto técnicamente
  con el patrón **Observer**. (Buen punto para lucirse: une la teoría de UX con un patrón.)

### Mapeo fino con los slides del profe (para citar de memoria)

- **Diseño funcional, no decorativo (Steve Jobs):** empezamos por la arquitectura (4 patrones) y los
  wireframes ANTES de la UI. El diseño es "cómo funciona", no una capa estética al final.
- **UX ≠ Usabilidad ≠ UI ≠ IxD:** en nuestra app → **UI** = nav, tarjetas, botones; **IxD** (diseño de
  interacción) = los formularios, el desplegable "Ver ítems", los checkboxes del kit; **Usabilidad** =
  qué tan fácil es cobrar; **UX** = la experiencia completa del flujo.
- **El Tao de la página (5 principios):**
  1. *Simplicidad y elegancia* → UI limpia, pocos colores, formularios cortos.
  2. *Proximidad y relevancia* (Gestalt) → cada cosa en su tarjeta; botón "Agregar" pegado a cada
     producto; precio junto al nombre.
  3. *Foco y retroalimentación* → el cartel "✓ Se agregó X", el resaltado del checkbox tildado, el
     total que se actualiza, y los **mensajes con código de color** (verde = ok, amarillo = atención).
  4. *Jerarquía de importancia y de tareas* → el TOTAL FINAL grande y verde; botón primario destacado;
     la navegación en orden de tarea.
  5. *La herramienta correcta* → desplegable para el descuento, checkboxes para el kit, `<details>`
     para el detalle. Cada control para su propósito.
- **Sistemas de navegación:** la barra superior es **navegación global** (en todas las pantallas) y la
  **pestaña activa resaltada** responde "¿dónde estoy?".
- **Uso del color (Murch / Simplicidad / Consistencia / Claridad):** ~4 colores; azul = acción,
  verde = total, siempre con el mismo significado; **códigos de color en los mensajes**; estados con
  texto + color (no solo color → daltonismo).
- **Estándares y guías:** aplicamos una "guía de estilo" propia y consistente (un único encabezado en
  fragmento Thymeleaf + una hoja de estilos común para todas las pantallas).

---

## 6. Arquitectura / técnico

- **Capas:** `domain/` (los 4 patrones) → `web/` (controladores Spring MVC) → `templates/` (Thymeleaf).
- **Cómo correr:** `gradlew.bat bootRun` y abrir `http://localhost:8080`. Tests: `gradlew.bat test`.
- **Pantallas (4):** Armar orden (catálogo + kits + orden en árbol), Total y descuento (ticket +
  cerrar venta), Ventas emitidas (historial), Preferencias (i18n).

---

## 7. Mapa rápido de clases

| Clase | Patrón / Rol |
|-------|--------------|
| `ItemVenta` | Composite — Component (interfaz) |
| `OrdenVenta` | Composite raíz + contexto de Strategy |
| `ItemCompuesto` | Composite — kit |
| `ItemSimple` | Composite — hoja (nombre, precio) |
| `EstrategiaDescuento` | Strategy (interfaz) |
| `SinDescuento` / `DescuentoPorcentaje` / `DescuentoVIP` | Strategy concretas |
| `PuntoDeVenta` | Singleton (caja única + historial) |
| `Venta` | Foto de una venta cerrada |
| `ConfiguracionI18N` | Observer — Subject |
| `IObservable` / `IObserver` | Observer — interfaces |
| `FormatoMoneda` / `FormatoFecha` | Observer — observadores |
