# Defensa de la Interfaz (UX / HCI)

## Metáfora de la vida cotidiana
La interfaz se apoya en la metáfora del **surtidor de combustible**: el operador "elige el surtidor,
enchufa, llena el tanque y cobra el ticket", trasladado al mundo eléctrico (kWh en vez de litros). Esta
correspondencia con un objeto conocido reduce la curva de aprendizaje.

## Heurísticas de Nielsen aplicadas
- **Visibilidad del estado del sistema:** el tablero muestra en vivo el estado de cada cargador
  (🟢 Libre / 🔴 Ocupado / ⚫ Fuera de servicio).
- **Correspondencia con el mundo real:** vocabulario del dominio (cargar, surtidor, ticket, patente).
- **Prevención de errores:** no se permite iniciar una carga en un cargador ocupado o fuera de
  servicio (el botón "Iniciar" solo aparece en cargadores libres).
- **Reconocer en lugar de recordar:** estados comunicados con color + texto; acciones visibles en cada
  tarjeta de cargador.
- **Estética y diseño minimalista:** pantallas con pocos campos y un botón primario destacado.

## Diseño inclusivo
- Los estados se comunican por **color y texto/ícono** a la vez (no solo color), para usuarios con
  daltonismo.
- Tipografía legible y objetivos de toque grandes (uso de pie, con apuro).

## Nuevos paradigmas
- **Actualización reactiva** de la interfaz ante cambios de configuración (patrón Observer): al cambiar
  el formato de fecha/moneda, las vistas se refrescan solas, sin recargar ni consultar manualmente.

> **Pendiente:** confirmar con el material de cátedra `ISWIII-U3-HCI.pdf` las heurísticas y conceptos
> puntuales que el docente quiere ver nombrados, para usar el mismo vocabulario en la defensa.
