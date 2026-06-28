# Wireframes de baja fidelidad — VentaSimple

> Recrear en **Excalidraw o draw.io** a partir de estas guías y exportar como imagen para el Word.
> Diseño inclusivo: los montos y estados se comunican con texto + formato claro (no solo color).

## 1. Armar orden (home: catálogo + kit + orden en árbol)
```
┌─────────────────────────────────────────────────┐
│ 🛒 VentaSimple   Armar orden | Total | Ventas | ⚙ │
├─────────────────────────────────────────────────┤
│ Catálogo de productos                             │
│  Notebook — $1.500 .................. [ Agregar ] │
│  Monitor  — $  900 .................. [ Agregar ] │
│  Mouse    — $   80 .................. [ Agregar ] │
│  ... (más productos del catálogo)                 │
├─────────────────────────────────────────────────┤
│ Armar un kit                                      │
│  Nombre: [ Kit Oficina        ]                   │
│  [✓] Mouse  [✓] Teclado  [ ] Webcam  [ ] ...      │
│                          [ Agregar kit a la orden]│
├─────────────────────────────────────────────────┤
│ Orden #OV-001                                     │
│  • Notebook ............................ $1.500   │
│  • Kit Oficina ......................... $  200   │
│      ↳ Mouse ........................... $   80   │
│      ↳ Teclado ......................... $  120   │
│ ───────────────────────────────────────────────  │
│            SUBTOTAL: $1.700   [ Ir al total ]     │
└─────────────────────────────────────────────────┘
```

## 2. Total y descuento (Strategy) — ticket
```
┌───────────────────────────────┐
│        🧾 Ticket de venta       │
├───────────────────────────────┤
│ Orden #OV-001                  │
│ Fecha:            27/06/2026   │
│ Subtotal:         $ 1.700,00   │
│ Descuento: [ Porcentaje 10% ▾] │
│            (Ninguno / % / VIP) │
│ ─────────────────────────────  │
│ TOTAL FINAL:      $ 1.530,00   │
│    [ ✓ Cerrar venta ] [Volver] │
└───────────────────────────────┘
```

## 3. Ventas emitidas (historial)
```
┌─────────────────────────────────────────────┐
│ 📒 Ventas emitidas                            │
├─────────────────────────────────────────────┤
│ N°     FECHA       HORA  ÍTEMS DESC.   TOTAL  │
│ OV-001 27/06/2026  21:28  2   10%   $1.530,00 │
│ OV-002 27/06/2026  21:29  1   VIP   US$ 20,00 │
│ ───────────────────────────────────────────  │
│ Ventas registradas: 2     [ Armar nueva orden]│
└─────────────────────────────────────────────┘
```

## 4. Preferencias (Observer)
```
┌───────────────────────────────────┐
│ ← Preferencias                     │
├───────────────────────────────────┤
│ Formato de moneda: [ ARS ($)    ▾] │
│ Formato de fecha:  [ dd/MM/yyyy ▾] │
│                                    │
│ Vista previa:                      │
│   Monto:  $ 2.340,00               │
│   Fecha:  27/06/2026               │
│            [  Guardar  ]           │
└───────────────────────────────────┘
```
