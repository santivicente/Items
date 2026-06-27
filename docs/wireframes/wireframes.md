# Wireframes de baja fidelidad — VentaSimple

> Recrear en **Excalidraw o draw.io** a partir de estas guías y exportar como imagen para el Word.
> Diseño inclusivo: los montos y estados se comunican con texto + formato claro (no solo color).

## 1. Armar orden (home)
```
┌─────────────────────────────────────────────────┐
│ VentaSimple — Orden #OV-001     [ Preferencias ⚙ ]│
├─────────────────────────────────────────────────┤
│ Buscar producto: [ Notebook        ] [ Agregar ] │
│                                                   │
│ [ + Crear kit ]                                   │
│                                                   │
│ Ítems en la orden:                  Subtotal      │
│  • Notebook ............................ $1.500   │
│  • Monitor ............................. $  900   │
│  • Kit Accesorios (2) .................. $  200   │
│ ───────────────────────────────────────────────  │
│                          SUBTOTAL:      $2.600    │
│                          [ Ver total y descuento ]│
└─────────────────────────────────────────────────┘
```

## 2. Resumen de la orden (árbol Composite)
```
┌───────────────────────────────────┐
│ ← Resumen — Orden #OV-001          │
├───────────────────────────────────┤
│ Orden                              │
│  ├─ Notebook ............ $1.500   │
│  ├─ Monitor ............. $  900   │
│  └─ Kit Accesorios                 │
│      ├─ Mouse ........... $   80   │
│      └─ Teclado ......... $  120   │
│ ─────────────────────────────────  │
│ SUBTOTAL: $2.600                   │
└───────────────────────────────────┘
```

## 3. Total y descuento (Strategy) — ticket
```
┌───────────────────────────────┐
│        🧾 Ticket de venta       │
├───────────────────────────────┤
│ Orden #OV-001                  │
│ Subtotal:         $ 2.600,00   │
│ Descuento: [ Porcentaje 10% ▾] │
│            (Ninguno / % / VIP) │
│ ─────────────────────────────  │
│ TOTAL FINAL:      $ 2.340,00   │
│            [  Cerrar venta  ]  │
└───────────────────────────────┘
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
