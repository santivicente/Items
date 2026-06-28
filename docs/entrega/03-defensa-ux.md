# Defensa de la Interfaz (UX / HCI)

> Esta defensa usa el marco conceptual visto en clase (Unidad 3 — HCI/IPO, Lic. M. Palma).

## 1. HCI/IPO y el objetivo de usabilidad
La interfaz de **VentaSimple** se diseñó como un sistema interactivo centrado en el usuario
(Interacción Persona-Ordenador). Se buscaron los objetivos de HCI: **efectividad, eficiencia y
satisfacción** (definición de usabilidad ISO). Concretamente, se atendieron los cinco atributos de
usabilidad:
- **Facilidad de aprendizaje:** flujo lineal (armar → resumen → total → preferencias) con vocabulario
  cotidiano.
- **Eficiencia:** pocos pasos para cargar un producto y cobrar.
- **Recordación (memorabilidad):** la navegación es siempre la misma barra y marca la pantalla activa.
- **Errores:** se previenen (descuento por lista, precios no negativos, campos requeridos).
- **Satisfacción:** interfaz limpia, total destacado, respuesta inmediata.

## 2. Enfoque en la tarea, no en la aplicación (Norman)
Siguiendo a Norman ("una app usable deja al usuario centrarse en su tarea, no en la aplicación"), la
pantalla principal pone el foco en la tarea real del cajero —armar la orden y ver el total— y deja la
tecnología (los patrones, el cálculo) invisible para el usuario.

## 3. Metáfora
Se aplicó la metáfora del **carrito de compras + ticket de caja**, trasladando un dominio familiar a la
app (objetivo de las metáforas: hacer la interfaz transparente). Evaluación de la metáfora según los
criterios vistos: es **representativa** (todos conocen un ticket), **aplicable** y **adecuada a la
audiencia** (un vendedor).

## 4. Estilo de interacción y factor humano
- **Estilo:** menús y navegación + formularios directos (manipulación directa al agregar ítems y ver el
  resultado al instante).
- **Factor humano / memoria de trabajo:** se respeta el límite **5±2** — la barra de navegación tiene
  4 ítems y los formularios son cortos, para no sobrecargar la memoria de trabajo del usuario.

## 5. Diseño gráfico y uso del color
Siguiendo las pautas de color vistas (Simplicidad, Consistencia, Claridad y reglas de Murch):
- **Simplicidad:** paleta reducida (≈4 colores: azul de acción, verde para el total, gris de fondo,
  ámbar de foco), dentro del rango 5±2.
- **Consistencia:** mismo color = mismo concepto en todas las pantallas (azul = acciones/navegación,
  verde = total final).
- **Murch / daltonismo:** **no se distingue solo por color** — los estados y montos llevan texto; el
  texto principal es oscuro (no azul puro); el foco de teclado usa un contorno ámbar visible.

## 6. Internacionalización (i18n) ⭐
Tema central de la cátedra que la app implementa de verdad: la pantalla **Preferencias** localiza los
**formatos de moneda** (ARS $ / USD US$) y de **fecha** (dd/MM/yyyy, yyyy-MM-dd, MM/dd/yyyy). Además
de cambiar el formato, **convierte el valor** según la cotización (US$ 1 = $ 1.500), mostrando el monto
real en cada moneda. Estos formatos se aplican en el ticket (monto y fecha de la venta). Técnicamente se
resuelve con el patrón **Observer** (`ConfiguracionI18N`): al cambiar la configuración, los formateadores
se actualizan solos — el "modelo de localización" (recursos + código) visto en clase.

## 7. DCU y prototipos
Se aplicó **Diseño Centrado en el Usuario**: se definió primero el usuario y la navegación
(`01-analisis-dcu.md`) y se hicieron **prototipos** de las pantallas (`../wireframes/wireframes.md`,
tipo papel/texto) **antes** de codear, no al final.

## 8. Nuevos paradigmas
La actualización **reactiva** de la interfaz ante cambios de configuración (Observer) refleja la
tendencia a interfaces que responden solas, sin que el usuario tenga que refrescar ni consultar.
