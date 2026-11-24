# CompoRack - Control de Infraestructura

Aplicación móvil desarrollada en **Android con Jetpack Compose** para la gestión, inspección y control de inventario de componentes en racks de centros de datos.

---

## 🚀 Funcionalidades Principales

* **Autenticación con Validación de Datos:** Formulario de inicio de sesión institucional con control de visibilidad de contraseña y manejo reactivo de errores.
* **Persistencia de Sesión Local:** Almacenamiento seguro del estado de login y nombre del usuario en el dispositivo. Si la sesión está activa, la app omite el login en futuros arranques.
* **Navegación Controlada (Back Stack):** Flujo de pantallas que evita ciclos innecesarios en el historial; al iniciar sesión o retroceder, las pantallas se gestionan de forma limpia.
* **Resumen de Inspección:** Vista detallada con información del rack inspeccionado y listado optimizado de componentes identificados mediante estados visuales.

---

## 🛠️ Tecnologías y Conceptos Implementados

### 1. Interfaz de Usuario (UI)
* **Jetpack Compose & Material 3:** Declaración de interfaces modernas utilizando componentes estándar como `Card`, `Column`, `Row`, `Button`, `OutlinedTextField` y `HorizontalDivider`.
* **Tema Oscuro Personalizado:** Paleta de colores técnica y uniforme (`#0A1128`, `#1E293B`, `#2563EB`) diseñada para entornos de infraestructura.

### 2. Arquitectura y Lógica de Negocio
* **Patrón MVVM (Model-View-ViewModel):** Separación de la lógica de negocio y validación de formularios (`LoginViewModel`, `ResumenViewModel`) de la capa visual.
* **Manejo de Estado Reactivo:** Uso de estados observables de Compose para reaccionar a cambios en campos de texto, alternancia de contraseñas y validaciones.

### 3. Persistencia y Sistema
* **`SessionManager` (`SharedPreferences`):** Implementación de persistencia local mediante el uso de `Context` (`LocalContext.current`), permitiendo guardar, consultar y borrar credenciales de sesión en archivos XML privados.
* **Uso de `remember`:** Optimización del ciclo de vida de objetos auxiliares para evitar su recreación innecesaria durante las recomposiciones de la UI.

### 4. Navegación
* **Navigation Compose (`NavHost`, `NavController`):**
    * `startDestination` dinámico determinado según el estado de la sesión (`estaIniciada()`).
    * `popUpTo("login") { inclusive = true }` para eliminar la pantalla de acceso del historial al autenticarse exitosamente.
    * `popBackStack()` para retrocesos limpios que liberan memoria sin duplicar pantallas.

---

## 📱 Pantallas de la Aplicación

1. **`LoginScreen`:** Validación de credenciales de usuario y punto de entrada al sistema.
2. **`RacksScreen`:** Pantalla principal de gestión de racks disponibles para inspección.
3. **`DetalleRacks`:** Pantalla donde se muestra informacion sobre el rack y boton para un nuevo registro o revisar el resumen.
4. **`NuevoRegistro`:** Pantalla donde se escanenan los codigos de racks y componenetes para añadirlos al registro.
5. **`ResumenScreen`:** Visualización de la ultima inspeccion del rack.
6. **`Perfil`:** Vista donde se muestra la informacion principal del usuario.

---

## Consumo de API REST (Xano + Retrofit)

Se reemplazaron los datos simulados por consultas reales a **Xano** mediante **Retrofit** y **Coroutines** bajo arquitectura **MVVM + Repository**:

* `POST auth/login` — Autenticación de usuario y guardado de sesión (`SessionManager`).
* `GET usuario/{usuario_id}` — Consulta de datos del perfil del técnico por ID.
* `GET racks` — Listado general de racks.
* `GET racks/{racks_id}` — Detalle y resumen de un rack específico por ID.
* `PATCH racks/{racks_id}` — Actualización del rack luego de realizar un registro.
* `GET componentes_racks` — Listado de todos los componentes registrados.
* `GET componentes_racks/validar/{num_serie}` — Validación manual del código/número de serie de un componente.

---

## Pruebas Unitarias (JUnit 4)

Se incorporaron pruebas unitarias automatizadas con **JUnit 4** (`src/test/java/com/duoc/comporack/viewmodel/`) para validar la lógica de negocio y el manejo de estados reactivos en los `ViewModel` de forma aislada antes del envío de datos:

* **`PerfilViewModelTest`:**
  * Verifica que una petición de cambio de datos vacía active el estado de error (`"La petición no puede estar vacía"`), impida el envío y mantenga abierto el modal.
  * Verifica la validación de longitud mínima (`"Describe el cambio con al menos 10 caracteres"`) ante entradas demasiado cortas.
  * Verifica que una petición válida marque `peticionEnviada = true`, cierre el modal y limpie errores previos.

---

## APK Firmada (Release)

Se generó y firmó el paquete de instalación `app-release.apk` mediante certificado digital propio (`Keystore` JKS) en Android Studio, ubicado en `app/release/app-release.apk` para instalación directa en dispositivos Android.