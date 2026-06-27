# Defensa de la Interfaz (UX / HCI)

## Metáfora de la vida cotidiana
La interfaz se apoya en la metáfora del **carrito de compras + ticket de caja**: se "cargan productos
al carrito", se "agrupan en kits/combos" y al final sale el "ticket" con el subtotal, el descuento y el
total. Son objetos cotidianos que el usuario ya conoce, lo que reduce la curva de aprendizaje.

## Heurísticas de Nielsen aplicadas
- **Visibilidad del estado del sistema:** el subtotal y el total final están siempre visibles mientras
  se arma la orden.
- **Correspondencia con el mundo real:** vocabulario del dominio (producto, kit, descuento, ticket,
  total).
- **Prevención de errores:** el descuento se elige de una lista (no se tipea), evitando valores
  inválidos; no se puede cerrar una venta sin ítems.
- **Reconocer en lugar de recordar:** la orden se muestra como un árbol (productos y kits) y las
  acciones están visibles en pantalla.
- **Estética y diseño minimalista:** pantallas con pocos elementos y un botón primario destacado.

## Diseño inclusivo
- Los montos se muestran con formato claro y etiquetas textuales (no se depende solo del color).
- Tipografía legible y objetivos de toque grandes (uso rápido en caja).

## Nuevos paradigmas
- **Actualización reactiva** del formato de moneda y fecha al cambiar las preferencias (patrón
  Observer): todas las vistas que muestran montos se refrescan solas, sin recargar.

> **Pendiente:** confirmar con el material de cátedra de HCI las heurísticas y conceptos puntuales que
> el docente quiere ver nombrados, para usar el mismo vocabulario en la defensa.
