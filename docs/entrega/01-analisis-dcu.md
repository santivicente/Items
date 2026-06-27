# Análisis de Usuarios y Arquitectura de la Información (DCU)

## Persona: "Caja rápida"
- **Contexto:** vendedor/cajero que atiende clientes y arma órdenes de venta durante el día.
- **Objetivos:** agregar productos y kits rápido, ver el total claro y aplicar descuentos sin equivocarse.
- **Frustraciones:** pantallas recargadas, no saber el subtotal mientras carga, errores al descontar.
- **Necesidades de diseño:** subtotal/total siempre visibles, botones grandes, flujo corto.

## Tareas principales (análisis Top-Down)
1. Agregar productos sueltos a la orden.
2. Armar kits (ítems compuestos) agrupando productos.
3. Ver el subtotal de la orden (vista del árbol Composite).
4. Elegir una estrategia de descuento y ver el total final.
5. Ajustar preferencias de formato (moneda / fecha).

## Arquitectura de la información / Mapa de navegación
```
Armar orden (home)
 ├─ Resumen de la orden
 ├─ Total y descuento ──────► Ticket
 └─ Preferencias (moneda/fecha)
```
Navegación plana (1 nivel) desde "Armar orden", para minimizar la carga cognitiva.

> Recrear este mapa en **Miro o FigJam** para la entrega (captura del tablero de navegación).
