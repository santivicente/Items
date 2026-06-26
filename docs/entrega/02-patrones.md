# Solución con Patrones de Diseño GOF — EcoCarga

Módulo: **gestión de una estación de carga de autos eléctricos**. Se aplican cuatro patrones de
diseño GOF en el backend. Para cada uno se documentan los seis puntos pedidos: nombre, propósito,
motivación, estructura, participantes y colaboración.

> Diagrama de clases general: `docs/uml/01-clases.puml` → `EcoCarga-Clases.png`.

---

## Patrón 1 — Singleton

**1. Nombre del Patrón:** Singleton (patrón creacional).

**2. Propósito:** garantizar que la clase `EstacionDeCarga` tenga una única instancia y proveer un
punto de acceso global a ella.

**3. Motivación:** en el mundo real existe una sola estación de carga. Si el sistema permitiera crear
varias instancias de `EstacionDeCarga`, cada una tendría su propia lista de cargadores y de sesiones,
generando estados inconsistentes (un cargador "ocupado" en una instancia y "libre" en otra). Aplicando
Singleton, cualquier módulo que necesite la estación obtiene siempre el mismo objeto y el estado es
consistente en todo el sistema.

**4. Estructura:** ver `docs/uml/01-clases.puml`. `EstacionDeCarga` con atributo estático `instancia`,
constructor privado y método estático `getInstancia()`.

**5. Participantes:**
- `EstacionDeCarga` (Singleton): atributo estático privado `instancia`; constructor privado que impide
  la instanciación externa; método estático `getInstancia()` que crea la instancia la primera vez y la
  retorna en las siguientes llamadas. Mantiene el nombre, la lista de cargadores y la lista de sesiones.

**6. Colaboración:** los servicios (`CargadorService`, `SesionService`) nunca instancian la estación
directamente, sino que acceden mediante `EstacionDeCarga.getInstancia()`. La primera llamada ejecuta el
constructor privado; las siguientes retornan la instancia existente. La implementación usa
*double-checked locking* con `synchronized` para ser segura en entornos multihilo.

---

## Patrón 2 — Factory Method

**1. Nombre del Patrón:** Factory Method (patrón creacional).

**2. Propósito:** definir una interfaz para crear un objeto `Cargador`, delegando en las subclases la
decisión de qué tipo concreto instanciar.

**3. Motivación:** la estación maneja distintos tipos de cargador (`CargadorRapidoDC`,
`CargadorSemiRapidoAC`, `CargadorLento`), cada uno con su potencia y tipo de conector. El código que da
de alta un cargador no debería conocer las clases concretas ni llenarse de `if/switch`. Con Factory
Method, agregar un tipo nuevo de cargador solo requiere una nueva fábrica concreta, sin tocar el código
cliente (principio abierto/cerrado).

**4. Estructura:** ver `docs/uml/01-clases.puml` (jerarquías `Cargador` y `FabricaDeCargadores`) y la
secuencia `docs/uml/02-seq-alta-cargador.puml` → `EcoCarga-Seq-AltaCargador.png`.

**5. Participantes:**
- `Cargador` (Product, abstracto) y sus subtipos `CargadorRapidoDC`, `CargadorSemiRapidoAC`,
  `CargadorLento` (ConcreteProduct).
- `FabricaDeCargadores` (Creator, abstracto): define `crearCargador(ubicacion)` y el método fábrica
  abstracto `factoryMethod()`.
- `FabricaRapidoDC`, `FabricaSemiRapidoAC`, `FabricaLento` (ConcreteCreator): implementan
  `factoryMethod()` devolviendo el subtipo correspondiente.

**6. Colaboración:** al dar de alta un cargador, `CargadorService` invoca `crearCargador(ubicacion)`
sobre la fábrica concreta elegida; ésta llama internamente a `factoryMethod()` para construir el
`Cargador` del tipo adecuado y lo devuelve. Luego el cargador se agrega a la estación (Singleton).

---

## Patrón 3 — Strategy

**1. Nombre del Patrón:** Strategy (patrón de comportamiento).

**2. Propósito:** definir una familia de algoritmos de tarifa, encapsular cada uno y hacerlos
intercambiables, permitiendo que el cálculo del costo varíe independientemente de la sesión.

**3. Motivación:** el costo de una carga puede calcularse de varias formas: por energía consumida
(kWh), por tiempo, con tarifa nocturna o con tarifa preferencial de socio. Codificar todas estas
variantes con condicionales dentro de `SesionDeCarga` la volvería rígida y difícil de mantener. Con
Strategy, cada política de tarifa es una clase propia y se puede cambiar en tiempo de ejecución.

**4. Estructura:** ver `docs/uml/01-clases.puml` (interfaz `EstrategiaDeTarifa` y sus implementaciones)
y la secuencia `docs/uml/03-seq-finalizar-carga.puml` → `EcoCarga-Seq-FinalizarCarga.png`.

**5. Participantes:**
- `EstrategiaDeTarifa` (Strategy, interfaz): declara `calcularCosto(SesionDeCarga)`.
- `TarifaPorEnergia`, `TarifaPorTiempo`, `TarifaNocturna`, `TarifaSocio` (ConcreteStrategy):
  implementan cada algoritmo de cálculo.
- `SesionDeCarga` (Context): mantiene una referencia a una `EstrategiaDeTarifa` y delega en ella el
  cálculo del costo.

**6. Colaboración:** al iniciarse, la sesión recibe la estrategia de tarifa elegida. Al finalizar la
carga, `SesionDeCarga.calcularCosto()` delega en `tarifa.calcularCosto(this)`, que aplica el algoritmo
concreto y devuelve el costo. La sesión no conoce los detalles del cálculo.

---

## Patrón 4 — Observer

**1. Nombre del Patrón:** Observer (patrón de comportamiento). También conocido como
Publish-Subscribe / Dependents.

**2. Propósito:** definir una dependencia uno-a-muchos entre objetos, de modo que cuando
`ConfiguracionI18N` cambia de estado, todos sus dependientes son notificados y actualizados
automáticamente.

**3. Motivación:** el formato de fecha y de moneda se usa en muchas vistas (tickets, tablero). Cuando
el operador cambia estas preferencias, todas las partes que muestran fechas o montos deben reflejar el
nuevo formato. Sin un mecanismo de notificación, cada vista tendría que consultar periódicamente la
configuración (polling), generando acoplamiento y posibles inconsistencias. Observer invierte la
responsabilidad: la configuración notifica a los interesados en el momento del cambio.

**4. Estructura:** ver `docs/uml/01-clases.puml` (interfaces `IObservable`/`IObserver` y clases
`ConfiguracionI18N`, `FormatoFecha`, `FormatoMoneda`) y la secuencia
`docs/uml/04-seq-configuracion.puml` → `EcoCarga-Seq-Configuracion.png`.

**5. Participantes:**
- `IObservable` (Subject, interfaz): `addObserver()`, `removeObserver()`, `notificar()`.
- `IObserver` (Observer, interfaz): `actualizar()`.
- `ConfiguracionI18N` (ConcreteSubject): almacena `formatoFecha`, `formatoMoneda` y la lista de
  observadores; al cambiar su estado invoca `notificar()`. Expone `getFormatoFecha()` y
  `getFormatoMoneda()` (modelo pull).
- `FormatoFecha`, `FormatoMoneda` (ConcreteObserver): implementan `actualizar()` consultando el nuevo
  valor al Subject.

**6. Colaboración:** `setConfiguracion(fecha, moneda)` actualiza el estado y llama a `notificar()`,
que recorre la lista de observadores invocando `actualizar()` en cada uno. Cada observador, en su
`actualizar()`, consulta el valor actual al Subject (modelo pull) y refresca su representación.

> **Nota de implementación:** se usa `notificar()` (Subject) y `actualizar()` (Observer) en lugar de
> `notify()`, porque `Object.notify()` es `final` en Java y no puede sobrescribirse. Es una mejora
> respecto del ejemplo original del TP11.

---

## Patrones relacionados
- **Singleton + Observer:** `ConfiguracionI18N` podría a su vez ser Singleton para garantizar una única
  configuración global.
- **Strategy + Factory Method:** ambos desacoplan al cliente de las clases concretas; Factory Method
  crea objetos, Strategy intercambia algoritmos.
