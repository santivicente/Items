# Wireframes de baja fidelidad — EcoCarga

> Recrear en **Excalidraw o draw.io** a partir de estas guías y exportar como imagen para el Word.
> Regla de diseño inclusivo: todo estado se comunica por **color + texto/ícono** (no solo color).

## 1. Tablero de cargadores (home)
```
┌───────────────────────────────────────────────┐
│ EcoCarga              [ Preferencias ⚙ ]        │
├───────────────────────────────────────────────┤
│  Cargadores                  [ + Alta cargador ]│
│                                                 │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐       │
│  │Surtidor 1│  │Surtidor 2│  │Surtidor 3│       │
│  │ 🟢 LIBRE │  │ 🔴 OCUP. │  │ ⚫ F.SERV.│       │
│  │ 50 kW DC │  │ 22 kW AC │  │  7 kW    │       │
│  │[ Iniciar]│  │[Finaliz.]│  │          │       │
│  └──────────┘  └──────────┘  └──────────┘       │
└───────────────────────────────────────────────┘
```

## 2. Iniciar carga
```
┌───────────────────────────────────┐
│ ← Iniciar carga — Surtidor 1       │
├───────────────────────────────────┤
│ Patente:   [ ABC123        ]       │
│ Tarifa:    [ Por energía  ▾]       │
│                                    │
│           [  Iniciar carga  ]      │
└───────────────────────────────────┘
```

## 3. Ticket de carga (al finalizar)
```
┌───────────────────────────────┐
│        ⚡ Ticket de carga       │
├───────────────────────────────┤
│ Surtidor 1 — ABC123            │
│ Inicio:  26/06/2026 14:05      │
│ Fin:     26/06/2026 14:50      │
│ Energía: 28,5 kWh              │
│ ─────────────────────────────  │
│ TOTAL:   $ 12.450,00           │
│           [  Cerrar  ]         │
└───────────────────────────────┘
```

## 4. Alta de cargador
```
┌───────────────────────────────────┐
│ ← Alta de cargador                 │
├───────────────────────────────────┤
│ Ubicación: [ Surtidor 4     ]      │
│ Tipo:      [ Rápido DC      ▾]     │
│            (Rápido DC / Semi AC /  │
│             Lento)                 │
│            [  Crear cargador  ]    │
└───────────────────────────────────┘
```

## 5. Preferencias (I18N)
```
┌───────────────────────────────────┐
│ ← Preferencias                     │
├───────────────────────────────────┤
│ Formato de fecha:  [ dd/MM/yyyy ▾] │
│ Formato de moneda: [ ARS ($)    ▾] │
│                                    │
│ Vista previa:                      │
│   Fecha:  26/06/2026               │
│   Monto:  $ 12.450,00              │
│            [  Guardar  ]           │
└───────────────────────────────────┘
```
