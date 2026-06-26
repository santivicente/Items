# Análisis de Usuarios y Arquitectura de la Información (DCU)

## Persona: "Lucía, operadora de estación"
- **Contexto:** atiende la estación de carga; trabaja de pie, a veces de noche o con apuro.
- **Objetivos:** ver al instante qué cargador está libre, iniciar una carga y cobrar rápido.
- **Frustraciones:** pantallas recargadas, pasos de más, no distinguir estados de un vistazo.
- **Necesidades de diseño:** estados muy visibles (color + texto), botones grandes, flujos cortos.

## Tareas principales (análisis Top-Down)
1. Ver disponibilidad de cargadores.
2. Iniciar una carga (cargador + tarifa + patente).
3. Finalizar una carga y emitir el ticket con el costo.
4. Dar de alta un cargador.
5. Ajustar preferencias de formato (fecha/moneda).

## Arquitectura de la información / Mapa de navegación
```
Tablero (home)
 ├─ Iniciar carga ──────────► Ticket
 ├─ Finalizar carga (desde un cargador ocupado) ──► Ticket
 ├─ Alta de cargador
 └─ Preferencias (I18N)
```
Navegación plana (1 nivel) desde el Tablero, para minimizar la carga cognitiva.

> Recrear este mapa en **Miro o FigJam** para la entrega (captura del tablero de navegación).
