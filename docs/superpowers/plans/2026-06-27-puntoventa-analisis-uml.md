# VentaSimple — Plan 1: Análisis y Modelado UML

> **Estado:** EJECUTADO el 2026-06-27. Este plan documenta la parte de análisis y modelado, ya
> producida en `docs/`. Los Planes 2 (Backend) y 3 (Frontend) quedan pendientes.

**Goal:** Producir el análisis (DCU), el diagrama de clases con los 4 patrones GOF y los diagramas de
secuencia, más los wireframes y el documento maestro, para el módulo VentaSimple (punto de venta).

**Tech Stack:** PlantUML (diagramas, renderizados a PNG con Smetana), Markdown (documentación).

## Global Constraints
- Idioma: español.
- Patrones GOF (exactos): **Composite, Singleton, Observer, Strategy**.
- Dominio: **VentaSimple** — punto de venta / orden de venta.
- Solo **Strategy** es código nuevo; Composite (TP10), Singleton (TP6) y Observer (TP11) se reutilizan.
- Observer: usar `notificar()`/`actualizar()` (no `notify()`).
- El Composite del TP10 no se modifica: Strategy se agrega como `OrdenVenta.calcularTotalFinal()`.

---

## Entregables producidos

| Entregable | Archivo |
|------------|---------|
| Diagrama de clases (4 patrones) | `docs/uml/01-clases.puml` → `VentaSimple-Clases.png` |
| Secuencia: calcular total (Composite + Singleton) | `docs/uml/02-seq-calcular-total.puml` → `.png` |
| Secuencia: aplicar descuento (Strategy) | `docs/uml/03-seq-descuento.puml` → `.png` |
| Secuencia: cambiar formato moneda (Observer) | `docs/uml/04-seq-configuracion.puml` → `.png` |
| Patrones (6 puntos c/u) | `docs/entrega/02-patrones.md` |
| Análisis DCU + navegación | `docs/entrega/01-analisis-dcu.md` |
| Wireframes 4 pantallas | `docs/wireframes/wireframes.md` |
| Defensa UX/HCI | `docs/entrega/03-defensa-ux.md` |
| Documento maestro | `docs/entrega/VentaSimple-Entrega.md` |

## Verificación (cobertura de la consigna)
- [x] Puntos 1-6 por patrón → diagramas + `02-patrones.md`.
- [x] Punto 7 (DCU) → `01-analisis-dcu.md`.
- [x] Punto 8 (wireframes) → `wireframes.md`.
- [x] Punto 9 (defensa UX) → `03-defensa-ux.md`.
- [ ] Punto 10 (código) → Planes 2 y 3.

## Pendientes manuales (herramientas visuales)
- Recrear el mapa de navegación en Miro/FigJam.
- Recrear los wireframes en Excalidraw/draw.io a partir de las guías.
- Confirmar heurísticas con el material de cátedra de HCI.

## Próximos planes
- **Plan 2 — Backend Spring Boot:** implementar Strategy (nuevo) + adaptar Composite/Singleton/Observer
  + API REST, con TDD.
- **Plan 3 — Frontend React:** las 4 pantallas consumiendo la API.
