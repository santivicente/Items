# Solución con Patrones de Diseño GOF — VentaSimple

Módulo: **punto de venta / orden de venta**. Se aplican cuatro patrones de diseño GOF en el backend.
Para cada uno se documentan los seis puntos: nombre, propósito, motivación, estructura, participantes y
colaboración.

> Diagrama de clases general: `docs/uml/01-clases.puml` → `VentaSimple-Clases.png`.

---

## Patrón 1 — Composite

**1. Nombre del Patrón:** Composite (Compuesto) — patrón estructural GOF.

**2. Propósito:** componer objetos en estructuras de árbol para representar jerarquías parte-todo.
Permite que el cliente trate de manera uniforme tanto objetos individuales como composiciones, usando
la misma interfaz para ambos.

**3. Motivación:** un vendedor necesita calcular el total de una orden que puede contener ítems simples
(un producto con su precio) y también ítems compuestos (kits que agrupan productos y que a su vez pueden
contener sub-ítems). El código del vendedor calcula el total sin saber si trata con un ítem simple o con
un conjunto anidado. El Composite define la interfaz común `ItemVenta` con `calcularTotal()`; cada nodo
delega recursivamente en sus hijos.

**4. Estructura:** ver `docs/uml/01-clases.puml` y la secuencia `docs/uml/02-seq-calcular-total.puml`
→ `VentaSimple-Seq-CalcularTotal.png`.

**5. Participantes:**

| Rol Composite | Clase | Responsabilidad |
|---------------|-------|-----------------|
| Component | `ItemVenta` (interfaz) | Declara `calcularTotal()` y `getDescripcion()` |
| Composite (raíz) | `OrdenVenta` | Contiene lista de `ItemVenta`; `calcularTotal()` itera y suma |
| Composite | `ItemCompuesto` | Agrupación interna (kit); delega `calcularTotal()` a sus partes |
| Leaf | `ItemSimple` | Producto individual; tiene `nombre` y `precio`, y devuelve su precio |
| Client | `Vendedor` / `OrdenService` | Crea la orden, agrega ítems y llama `calcularTotal()` |

**6. Colaboración:** el vendedor agrega ítems simples y compuestos a la orden y llama `calcularTotal()`.
`OrdenVenta` itera su lista; cada `ItemSimple` retorna su precio y cada `ItemCompuesto` suma
recursivamente sus partes. El total acumulado vuelve al vendedor.

**Origen:** reutiliza el TP10 (diagrama de clases ya validado por el equipo).

---

## Patrón 2 — Singleton

**1. Nombre del Patrón:** Singleton — patrón creacional GOF.

**2. Propósito:** garantizar que `PuntoDeVenta` tenga una única instancia y proveer un punto de acceso
global.

**3. Motivación:** existe una sola caja / sistema de venta. Si hubiera varias instancias, cada una
tendría su propia lista de órdenes y su propia configuración, generando estados inconsistentes. Con
Singleton, cualquier módulo obtiene siempre el mismo `PuntoDeVenta`.

**4. Estructura:** ver `docs/uml/01-clases.puml` (clase `PuntoDeVenta`).

**5. Participantes:**
- `PuntoDeVenta` (Singleton): atributo estático privado `instancia`; constructor privado;
  método estático `getInstancia()`. Mantiene el nombre, la lista de órdenes y la `ConfiguracionI18N`.

**6. Colaboración:** los servicios acceden con `PuntoDeVenta.getInstancia()`. La primera llamada crea
la instancia; las siguientes la retornan. Implementación thread-safe con double-checked locking.

**Origen:** reutiliza el TP6 (InstitutoEducativo → PuntoDeVenta).

---

## Patrón 3 — Observer

**1. Nombre del Patrón:** Observer — patrón de comportamiento GOF (Publish-Subscribe).

**2. Propósito:** definir una dependencia uno-a-muchos: cuando `ConfiguracionI18N` cambia, todos sus
dependientes son notificados y actualizados automáticamente.

**3. Motivación:** el total de la orden se muestra en distintas vistas con un formato de moneda (y de
fecha). Cuando el usuario cambia el formato, todas las vistas deben reflejarlo sin polling ni
acoplamiento directo.

**4. Estructura:** ver `docs/uml/01-clases.puml` y la secuencia `docs/uml/04-seq-configuracion.puml`
→ `VentaSimple-Seq-Configuracion.png`.

**5. Participantes:**
- `IObservable` (Subject): `addObserver()`, `removeObserver()`, `notificar()`.
- `IObserver` (Observer): `actualizar()`.
- `ConfiguracionI18N` (ConcreteSubject): almacena `formatoMoneda`, `formatoFecha` y la lista de
  observadores; al cambiar invoca `notificar()`. Expone `getFormatoMoneda()` y `getFormatoFecha()`.
- `FormatoMoneda`, `FormatoFecha` (ConcreteObserver): en `actualizar()` consultan el nuevo valor.

**6. Colaboración:** `setConfiguracion(moneda, fecha)` actualiza el estado y llama `notificar()`, que
recorre los observadores invocando `actualizar()` en cada uno (modelo pull).

**Origen:** reutiliza el TP11. *Nota:* se usa `notificar()`/`actualizar()` en lugar de `notify()`,
porque `Object.notify()` es `final` en Java y no puede sobrescribirse.

---

## Patrón 4 — Strategy *(patrón nuevo)*

**1. Nombre del Patrón:** Strategy — patrón de comportamiento GOF.

**2. Propósito:** definir una familia de algoritmos de descuento, encapsular cada uno y hacerlos
intercambiables, de modo que el descuento varíe independientemente de la orden.

**3. Motivación:** el total de una orden puede tener distintos descuentos: ninguno, un porcentaje fijo,
o un descuento de cliente VIP. Codificar estas variantes con condicionales dentro de `OrdenVenta` la
volvería rígida. Con Strategy, cada política es una clase propia e intercambiable en tiempo de ejecución.

**4. Estructura:** ver `docs/uml/01-clases.puml` (interfaz `EstrategiaDescuento` y sus implementaciones)
y la secuencia `docs/uml/03-seq-descuento.puml` → `VentaSimple-Seq-Descuento.png`.

**5. Participantes:**
- `EstrategiaDescuento` (Strategy, interfaz): declara `aplicar(subtotal) : double`.
- `SinDescuento`, `DescuentoPorcentaje`, `DescuentoVIP` (ConcreteStrategy).
- `OrdenVenta` (Context): mantiene una `EstrategiaDescuento` y delega en ella el descuento.

**6. Colaboración:** `OrdenVenta.calcularTotalFinal()` obtiene el subtotal con el Composite
(`calcularTotal()`) y delega en `estrategiaDescuento.aplicar(subtotal)`, que aplica el algoritmo
concreto. **Esto extiende el Composite del TP10 sin modificarlo.**

---

## Patrones relacionados
- **Composite + Strategy:** el Composite calcula el subtotal del árbol y Strategy decide el descuento
  sobre ese subtotal; se combinan sin acoplarse.
- **Singleton + Observer:** la configuración vive dentro del único `PuntoDeVenta`, garantizando una sola
  fuente de preferencias global.
