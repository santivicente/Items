# EcoCarga — Diseño del Trabajo Final Integrador (ISW III)

**Materia:** Ingeniería de Software III — Trabajo Final Integrador 2026
**Tema:** Solución a problemas de diseño usando UML y Patrones de Diseño
**Fecha:** 2026-06-26
**Integrantes:** Santiago Vicente, Josué Ferreyra, Matias Porcari, Delfina Ibañez, Candela Aguilar

---

## 1. Resumen

**EcoCarga** es un módulo full-stack para la gestión de una **estación de carga de autos eléctricos**.
Permite al operador ver el estado de los cargadores, iniciar y finalizar sesiones de carga,
calcular el costo según distintas tarifas, dar de alta cargadores y configurar las preferencias
de formato de fecha y moneda.

El módulo se construye sobre **al menos 4 patrones de diseño GOF** en el backend (Java + Spring Boot)
y una interfaz cuidada según DCU/UX/HCI en el frontend (React).

### Cumplimiento de la consigna

| Requisito de la consigna | Cómo lo cumple EcoCarga |
|--------------------------|--------------------------|
| Módulo funcional a elección | Estación de carga de autos eléctricos |
| Backend con ≥ 4 patrones GOF | Singleton, Factory Method, Strategy, Observer |
| Backend en Java + Spring Boot | API REST en Spring Boot |
| Frontend con UX/DCU/HCI | React, con análisis de usuario, wireframes y defensa UX |
| 6 puntos por patrón (nombre, propósito, motivación, estructura, participantes, colaboración) | Sección 4 + diagramas UML |
| Análisis de usuarios y arquitectura de información (DCU) | Sección 5 |
| Wireframes/Mockups | Sección 6 |
| Defensa de la interfaz (UX/HCI) | Sección 7 |
| Código en GitHub público | Repo a crear |

---

## 2. Usuario objetivo y alcance (DCU)

**Usuario primario:** el **operador / encargado** de la estación de carga.
Necesidad central: saber de un vistazo qué cargador está libre, iniciar una carga y cobrar rápido,
con la menor cantidad de pasos posible.

**Metáfora central de la vida cotidiana:** el **surtidor de nafta**. El operador "elige el surtidor,
enchufa, llena el tanque y cobra el ticket", trasladado al mundo eléctrico (kWh en vez de litros).

### Alcance del MVP (5 pantallas)

1. **Tablero de cargadores** — estado en tiempo real: 🟢 Libre / 🔴 Ocupado / ⚫ Fuera de servicio.
2. **Iniciar carga** — seleccionar cargador + tipo de tarifa + patente del vehículo.
3. **Ticket de carga** — al finalizar, muestra energía (kWh), tiempo y costo formateado.
4. **Alta de cargador** — agregar un cargador nuevo eligiendo su tipo (Factory).
5. **Preferencias (I18N)** — configurar formato de fecha y de moneda (Observer).

### Fuera de alcance (YAGNI)

- Autenticación/roles de usuario.
- Pagos reales / pasarela de pago.
- Persistencia en base de datos productiva (se usa repositorio en memoria o H2).
- App móvil para el conductor (solo panel del operador).
- Reportes/estadísticas avanzadas.

---

## 3. Modelo de dominio

```
EstacionDeCarga (Singleton)
 ├─ nombre: String
 ├─ cargadores: List<Cargador>
 └─ sesiones: List<SesionDeCarga>

Cargador (abstracto, creado por Factory Method)
 ├─ id: int
 ├─ ubicacion: String        // "Surtidor 1"
 ├─ potenciaKw: double
 ├─ estado: EstadoCargador   // LIBRE | OCUPADO | FUERA_DE_SERVICIO
 └─ subtipos: CargadorRapidoDC | CargadorSemiRapidoAC | CargadorLento

SesionDeCarga
 ├─ cargador: Cargador
 ├─ patente: String
 ├─ fechaInicio: LocalDateTime
 ├─ fechaFin: LocalDateTime
 ├─ energiaKwh: double
 ├─ tarifa: EstrategiaDeTarifa   // Strategy
 └─ costo: double

EstrategiaDeTarifa (Strategy, interface)
 └─ TarifaPorEnergia | TarifaPorTiempo | TarifaNocturna | TarifaSocio

ConfiguracionI18N (Observer / Subject)
 ├─ formatoFecha: String
 ├─ formatoMoneda: String
 └─ observers: List<IObserver>  → FormatoFecha, FormatoMoneda
```

---

## 4. Los 4 patrones GOF

Para cada patrón se documentarán los 6 puntos que pide la consigna (igual que los TP de referencia):
nombre, propósito, motivación, estructura (diagrama de clases), participantes, colaboración
(diagrama de secuencia).

### 4.1 Singleton — `EstacionDeCarga`
- **Propósito:** garantizar una única instancia de la estación y un punto de acceso global.
- **Motivación:** en el mundo real existe una sola estación; dos instancias tendrían listas de
  cargadores y sesiones inconsistentes.
- **Participantes:** `EstacionDeCarga` (constructor privado, atributo estático `instancia`,
  método estático `getInstancia()`).
- **Colaboración:** cualquier servicio accede siempre vía `EstacionDeCarga.getInstancia()`.
  Implementación thread-safe con double-checked locking.
- **Origen:** reutiliza el patrón del TP6 (InstitutoEducativo → EstacionDeCarga).

### 4.2 Factory Method — creación de `Cargador`
- **Propósito:** definir una interfaz para crear cargadores, delegando en subtipos el tipo concreto.
- **Motivación:** existen distintos tipos de cargador (Rápido DC, Semirrápido AC, Lento) con
  potencias y conectores distintos; el cliente no debería conocer las clases concretas.
- **Participantes:** producto `Cargador` (abstracto) y sus subtipos; creador `FabricaDeCargadores`
  con el método fábrica `crearCargador(TipoCargador)`.
- **Colaboración:** al dar de alta un cargador, el servicio pide a la fábrica el subtipo según el
  tipo elegido; agregar un tipo nuevo no modifica el código cliente (principio abierto/cerrado).

### 4.3 Strategy — cálculo de tarifa de la sesión
- **Propósito:** encapsular una familia de algoritmos de tarifa e intercambiarlos en runtime.
- **Motivación:** el costo de una carga puede calcularse por energía (kWh), por tiempo, con tarifa
  nocturna o con tarifa de socio; no conviene un `if/switch` gigante dentro de `SesionDeCarga`.
- **Participantes:** `EstrategiaDeTarifa` (interface) con `calcularCosto(SesionDeCarga)`;
  implementaciones `TarifaPorEnergia`, `TarifaPorTiempo`, `TarifaNocturna`, `TarifaSocio`;
  contexto `SesionDeCarga` que delega en la estrategia.
- **Colaboración:** al finalizar la sesión, `SesionDeCarga` llama a `tarifa.calcularCosto(this)`.

### 4.4 Observer — `ConfiguracionI18N`
- **Propósito:** dependencia uno-a-muchos: al cambiar la configuración, los interesados se
  notifican y actualizan automáticamente.
- **Motivación:** el formato de fecha y moneda se usa en muchas vistas (tickets, tablero); al
  cambiarlo, todo debe reflejarlo sin polling ni acoplamiento directo.
- **Participantes:** `IObservable`/`IObserver`, `ConfiguracionI18N` (ConcreteObservable),
  `FormatoFecha` y `FormatoMoneda` (ConcreteObserver).
- **Colaboración:** `setConfiguracion()` cambia el estado y llama a `notifyAll()`, que invoca
  `notify()` en cada observador (modelo pull).
- **Origen:** reutiliza el patrón del TP11.

---

## 5. Análisis de usuarios y arquitectura de la información (DCU)

- **Persona:** "Operador de estación", busca rapidez y claridad; trabaja de pie, a veces con
  poca luz o apurado. Prioriza información de estado a simple vista.
- **Tareas principales:** ver disponibilidad → iniciar carga → finalizar y cobrar → configurar.
- **Arquitectura de información / navegación:**
  ```
  Tablero (home)
   ├─ Iniciar carga → Ticket
   ├─ Finalizar carga (desde un cargador ocupado) → Ticket
   ├─ Alta de cargador
   └─ Preferencias (I18N)
  ```
  Navegación plana (1 nivel) para minimizar carga cognitiva.
- **Herramienta sugerida para el diagrama de navegación:** Miro o FigJam.

---

## 6. Prototipado rápido (Wireframes / Mockups)

Se entregarán wireframes de baja fidelidad de las 5 pantallas clave, hechos en **Excalidraw o draw.io**,
demostrando DCU antes de codificar. Decisiones de diseño a reflejar:

- **Tablero:** grilla de tarjetas tipo "surtidor", con color de estado (verde/rojo/gris) y rótulo
  textual (no solo color → diseño inclusivo / daltonismo).
- **Iniciar carga:** formulario corto, 3 campos, botón primario grande.
- **Ticket:** comprobante claro tipo recibo, con costo destacado.
- **Preferencias:** selección de formato con vista previa en vivo (refleja el efecto Observer).

---

## 7. Defensa de la interfaz (UX / HCI)

- **Metáfora de la vida cotidiana:** el surtidor de combustible (elegir surtidor, enchufar, llenar,
  cobrar) → reduce la curva de aprendizaje.
- **Heurísticas de Nielsen aplicadas:** visibilidad del estado del sistema (estado en vivo de
  cargadores), correspondencia con el mundo real (vocabulario: cargar, surtidor, ticket),
  prevención de errores (no permitir iniciar carga en cargador ocupado/fuera de servicio),
  reconocer en lugar de recordar (estados con color + texto).
- **Diseño inclusivo:** estados comunicados por color **y** texto/íconos; tipografía legible;
  objetivos de toque grandes.
- **Nuevos paradigmas:** actualización reactiva de la UI ante cambios de configuración (Observer)
  y de estado de cargadores.

> Nota: confirmar con el material de cátedra (ISWIII-U3-HCI) las heurísticas/conceptos puntuales
> que el docente quiere ver nombrados, para usar el mismo vocabulario.

---

## 8. Arquitectura técnica

### Backend (Java + Spring Boot)
```
controller/   → REST endpoints (CargadorController, SesionController, ConfiguracionController)
service/      → lógica de aplicación (orquesta los patrones)
domain/       → EstacionDeCarga (Singleton), Cargador + subtipos, SesionDeCarga
  factory/    → FabricaDeCargadores (Factory Method)
  tarifa/     → EstrategiaDeTarifa + implementaciones (Strategy)
  i18n/       → ConfiguracionI18N, IObserver, FormatoFecha, FormatoMoneda (Observer)
repository/   → almacenamiento en memoria / H2
```
- Nota de diseño: el Singleton GOF (`EstacionDeCarga`) se implementa explícitamente en el dominio
  (constructor privado + `getInstancia()`), distinto del scope singleton de los beans de Spring;
  se documentará esa diferencia en la defensa.

### Frontend (React + Vite)
```
src/
  pages/      → Tablero, IniciarCarga, Ticket, AltaCargador, Preferencias
  components/ → CargadorCard, FormatoPreview, ...
  api/        → cliente HTTP hacia el backend
```

### Endpoints REST (borrador)
- `GET  /api/cargadores` — lista y estado.
- `POST /api/cargadores` — alta (Factory).
- `POST /api/sesiones/iniciar` — iniciar carga.
- `POST /api/sesiones/{id}/finalizar` — finalizar y calcular costo (Strategy).
- `GET  /api/configuracion` / `PUT /api/configuracion` — preferencias I18N (Observer).

---

## 9. Diagramas UML a entregar

1. **Diagrama de clases** integrando los 4 patrones (entregable en PlantUML/Mermaid → exportar a
   imagen para el Word; modelable también en StarUML).
2. **Diagramas de secuencia (colaboración):**
   - Iniciar carga (usa Singleton + Factory para el cargador).
   - Finalizar carga (usa Strategy para el costo).
   - Cambiar configuración I18N (usa Observer).

---

## 10. Orden de trabajo

1. **Análisis + UML:** análisis de usuario/AI, diagrama de clases con los 4 patrones, diagramas de
   secuencia. *(prioridad del equipo)*
2. **Documento Word:** 6 puntos por patrón + DCU + wireframes + defensa UX.
3. **Backend:** Spring Boot con los 4 patrones.
4. **Frontend:** React, las 5 pantallas.
5. **Integración + repo GitHub** público.

---

## 11. Entregables finales (según consigna)

- Documento Word de resolución de consignas (completo).
- Código fuente en GitHub público (backend Spring Boot + frontend React).
- Diagramas UML (clases + secuencia) exportados.
- Wireframes (Excalidraw/draw.io) y diagrama de navegación (Miro/FigJam).
