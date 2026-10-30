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