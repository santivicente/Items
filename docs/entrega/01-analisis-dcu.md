# Análisis de Usuarios y Arquitectura de la Información (DCU)

## Persona: "Caja rápida"
- **Contexto:** vendedor/cajero que atiende clientes y arma órdenes de venta durante el día.
- **Objetivos:** agregar productos y kits rápido, ver el total claro y aplicar descuentos sin equivocarse.
- **Frustraciones:** pantallas recargadas, no saber el subtotal mientras carga, errores al descontar.
- **Necesidades de diseño:** subtotal/total siempre visibles, botones grandes, flujo corto.

## Tareas principales (análisis Top-Down)
1. Agregar productos a la orden desde el catálogo predefinido.
2. Armar kits (ítems compuestos) agrupando productos del catálogo (cualquier cantidad).
3. Ver la orden como árbol con su subtotal (vista del Composite, en la misma pantalla).
4. Elegir una estrategia de descuento, ver el total final y cerrar la venta.
5. Consultar el historial de ventas emitidas.
6. Ajustar preferencias de formato (moneda / fecha).

## Arquitectura de la información / Mapa de navegación
```
Armar orden (home: catálogo + kits + orden en árbol)
 ├─ Total y descuento ──────► Ticket ──► Cerrar venta
 ├─ Ventas emitidas (historial)
 └─ Preferencias (moneda/fecha)
```
Navegación plana (1 nivel) desde "Armar orden", para minimizar la carga cognitiva.

> Recrear este mapa en **Miro o FigJam** para la entrega (captura del tablero de navegación).
