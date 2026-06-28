# VentaSimple — Diseño del Trabajo Final Integrador (ISW III)

**Materia:** Ingeniería de Software III — Trabajo Final Integrador 2026
**Tema:** Solución a problemas de diseño usando UML y Patrones de Diseño
**Fecha:** 2026-06-27
**Integrantes:** Santiago Vicente, Josué Ferreyra, Matias Porcari, Delfina Ibañez, Candela Aguilar

---

## 1. Resumen

**VentaSimple** es un módulo full-stack para un **punto de venta**: el vendedor arma una orden de
venta agregando productos sueltos y kits (agrupaciones de productos), calcula el total, le aplica un
descuento y lo muestra con el formato de moneda configurado.

El backend (Java + Spring Boot) aplica **4 patrones de diseño GOF**; el frontend (React) presenta una
interfaz según DCU/UX/HCI.

> **Origen y estrategia:** se reutilizan tres TP ya resueltos por el equipo — Composite (TP10),
> Singleton (TP6) y Observer (TP11) — y se agrega **un patrón nuevo, Strategy**, para llegar a los 4
> patrones que exige la consigna. El objetivo es minimizar el código nuevo: solo Strategy se codea
> desde cero; el resto se adapta de los TP existentes. El diagrama de Composite ya está validado por
> el equipo.

### Cumplimiento de la consigna

| Requisito | Cómo lo cumple |
|-----------|----------------|
| Módulo funcional a elección | Punto de venta / orden de venta |
| Backend con ≥ 4 patrones GOF | Composite, Singleton, Observer, Strategy |
| Backend Java + Spring Boot | API REST |
| Frontend UX/DCU/HCI | React, con análisis de usuario, wireframes y defensa UX |
| 6 puntos por patrón | Sección 4 + diagramas UML |
| Análisis DCU + arquitectura de info | Sección 5 |
| Wireframes/Mockups | Sección 6 |
| Defensa UX/HCI | Sección 7 |
| Código en GitHub público | Repo `EcoCarga` (santivicente) |

---

## 2. Usuario objetivo y alcance (DCU)

**Usuario primario:** el **vendedor / cajero** que arma la orden y cobra.
Necesidad central: agregar productos y kits rápido, ver el total claro y aplicar descuentos sin errores.

**Metáfora central:** el **carrito de compras + ticket de caja**. Se "cargan productos al carrito",
se "agrupan en combos/kits", y al final sale el "ticket" con el total y el descuento.

### Alcance del MVP (5 pantallas)

1. **Armar orden** — buscar/agregar productos sueltos y crear kits (ítems compuestos).
2. **Resumen de la orden** — lista de ítems con subtotal (vista del árbol Composite).
3. **Total y descuento** — elegir estrategia de descuento, ver el total final y **cerrar la venta**.
4. **Ventas emitidas** — historial de ventas cerradas (registro guardado en el Singleton).
5. **Preferencias** — formato de moneda y fecha (Observer).

### Fuera de alcance (YAGNI)

- Autenticación/roles, pagos reales, stock/inventario, base de datos productiva (repositorio en
  memoria o H2), reportes.

---

## 3. Modelo de dominio

```
PuntoDeVenta (Singleton)
 ├─ nombre
 ├─ ordenes : List<OrdenVenta>
 └─ config : ConfiguracionI18N

ItemVenta (interface, Composite Component)
 ├─ calcularTotal() : double
 └─ getDescripcion() : String
   ├─ OrdenVenta        (Composite raíz)  → items : List<ItemVenta> + EstrategiaDescuento
   ├─ ItemCompuesto     (Composite)       → partes : List<ItemVenta>
   └─ ItemSimple        (Leaf)            → nombre, precio

EstrategiaDescuento (interface, Strategy)
 └─ SinDescuento | DescuentoPorcentaje | DescuentoVIP

ConfiguracionI18N (Observer / Subject)
 └─ notifica a FormatoMoneda y FormatoFecha
```

---

## 4. Los 4 patrones GOF

Cada patrón se documenta con los 6 puntos: nombre, propósito, motivación, estructura, participantes y
colaboración.

### 4.1 Composite — `OrdenVenta` / `ItemVenta`
- **Propósito:** componer objetos en estructuras de árbol parte-todo y tratar de forma uniforme ítems
  individuales y agrupaciones.
- **Motivación:** una orden puede contener productos sueltos y kits (que a su vez contienen productos).
  El vendedor calcula el total sin distinguir si un ítem es simple o compuesto.
- **Participantes:** `ItemVenta` (Component), `OrdenVenta` (Composite raíz), `ItemCompuesto`
  (Composite), `ItemSimple` (Leaf, con `nombre` y `precio`).
- **Colaboración:** `calcularTotal()` se delega recursivamente; cada hoja devuelve su precio y cada
  compuesto suma el de sus partes.
- **Origen:** reutiliza el TP10 (diagrama validado por el equipo).

### 4.2 Singleton — `PuntoDeVenta`
- **Propósito:** una única instancia del punto de venta con acceso global.
- **Motivación:** hay una sola caja/sistema; debe haber un único registro consistente de órdenes y una
  única configuración.
- **Participantes:** `PuntoDeVenta` (constructor privado, `instancia` estática, `getInstancia()`).
- **Colaboración:** los servicios acceden con `PuntoDeVenta.getInstancia()`; double-checked locking.
- **Origen:** reutiliza el TP6 (InstitutoEducativo → PuntoDeVenta).

### 4.3 Observer — `ConfiguracionI18N`
- **Propósito:** al cambiar el formato de moneda/fecha, todas las vistas que muestran montos y fechas
  se actualizan automáticamente.
- **Motivación:** el total de la orden se muestra en muchos lugares; al cambiar el formato no debe
  haber polling ni inconsistencias.
- **Participantes:** `IObservable`/`IObserver`, `ConfiguracionI18N` (ConcreteSubject), `FormatoMoneda`
  y `FormatoFecha` (ConcreteObserver).
- **Colaboración:** `setConfiguracion()` cambia el estado y llama `notificar()`, que invoca
  `actualizar()` en cada observador (modelo pull).
- **Origen:** reutiliza el TP11. (Se usa `notificar()`/`actualizar()` en vez de `notify()`, que es
  `final` en `Object`.)

### 4.4 Strategy — `EstrategiaDescuento` (NUEVO)
- **Propósito:** encapsular distintas políticas de descuento e intercambiarlas en runtime.
- **Motivación:** el total de una orden puede tener distintos descuentos (ninguno, porcentaje fijo,
  cliente VIP); no conviene un `if/switch` dentro de `OrdenVenta`.
- **Participantes:** `EstrategiaDescuento` (Strategy) con `aplicar(subtotal)`; `SinDescuento`,
  `DescuentoPorcentaje`, `DescuentoVIP` (ConcreteStrategy); `OrdenVenta` (Context).
- **Colaboración:** `OrdenVenta.calcularTotalFinal()` calcula el subtotal con el Composite
  (`calcularTotal()`) y delega en `estrategiaDescuento.aplicar(subtotal)`. **No modifica el Composite
  del TP10**, lo extiende.

---

## 5. Análisis de usuarios y arquitectura de la información (DCU)

- **Persona:** "Caja rápida": vendedor que necesita armar la orden y cobrar en pocos pasos.
- **Tareas:** agregar productos → armar kits → ver subtotal → aplicar descuento → ver total final.
- **Navegación:**
  ```
  Armar orden (home)
   ├─ Resumen de la orden
   ├─ Total y descuento ──► Ticket
   └─ Preferencias (moneda/fecha)
  ```
- **Herramienta sugerida:** Miro o FigJam para el mapa de navegación.

---

## 6. Prototipado rápido (Wireframes / Mockups)

Wireframes de baja fidelidad de las 4 pantallas, en **Excalidraw o draw.io**. Diseño inclusivo: los
estados y montos se comunican con texto + formato claro (no solo color). El total final se destaca como
en un ticket de caja.

---

## 7. Defensa de la interfaz (UX / HCI)

- **Metáfora:** carrito de compras + ticket de caja (objetos cotidianos).
- **Heurísticas de Nielsen:** visibilidad del estado (subtotal/total siempre visibles),
  correspondencia con el mundo real (carrito, kit, descuento, ticket), prevención de errores (no
  permitir descuentos inválidos), reconocer en lugar de recordar.
- **Diseño inclusivo:** montos con formato claro y etiquetas textuales; tipografía legible; botones
  grandes.
- **Nuevos paradigmas:** actualización reactiva del formato de moneda al cambiar preferencias (Observer).

> Confirmar vocabulario de heurísticas con el material de cátedra de HCI.

---

## 8. Arquitectura técnica

### Backend (Java + Spring Boot)
```
controller/   → OrdenController, ConfiguracionController
service/      → OrdenService (orquesta Composite + Strategy + Singleton)
domain/
  composite/  → ItemVenta, OrdenVenta, ItemCompuesto, ItemSimple
  descuento/  → EstrategiaDescuento + implementaciones (Strategy)
  i18n/       → ConfiguracionI18N, IObservable, IObserver, FormatoMoneda, FormatoFecha (Observer)
  PuntoDeVenta (Singleton)
repository/   → en memoria / H2
```

### Frontend (Thymeleaf — todo en el mismo proyecto Spring Boot)
> Decisión 2026-06-27: el frontend se hace con **Thymeleaf** (HTML renderizado por Spring Boot),
> sin React. Todo es un único proyecto Gradle.
```
src/main/resources/templates/   → armar-orden.html, resumen.html, ticket.html, preferencias.html
src/main/resources/static/css/  → styles.css
```

### Rutas (controladores MVC con Thymeleaf)
- `GET  /` — armar orden (home).
- `POST /ordenes/items` — agregar ítem simple o kit.
- `GET  /ordenes/resumen` — resumen (árbol Composite).
- `GET/POST /ordenes/total` — total con descuento (Strategy).
- `GET/POST /preferencias` — formato moneda/fecha (Observer).

---

## 9. Diagramas UML a entregar

1. **Diagrama de clases** integrando los 4 patrones.
2. **Diagramas de secuencia:**
   - Crear orden y calcular total (Composite + Singleton).
   - Aplicar descuento al total (Strategy).
   - Cambiar formato de moneda (Observer).

---

## 10. Orden de trabajo

1. Análisis + UML (diagrama de clases + 3 secuencias). *(prioridad)*
2. Documento Word: 6 puntos por patrón + DCU + wireframes + defensa UX.
3. Backend Spring Boot (solo Strategy es código nuevo; el resto se adapta).
4. Frontend React (4 pantallas).
5. Integración + repo GitHub.
