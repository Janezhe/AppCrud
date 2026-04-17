## AppCrud
AppCrud es una aplicación Android nativa desarrollada con Jetpack Compose que permite gestionar gastos y pagos personales de forma sencilla e intuitiva, con una interfaz estilo bancario y persistencia de datos local.
## Instalación
1. Clonar repositorio
```bash
git clone https://github.com/tu-usuario/AppCrud.git
```
2. Abrir en Android Studio
3. Sincronizar dependencias de Gradle
4. Correr en emulador o dispositivo físico
## Estructura del proyecto
```
app/
├── src/
│   └── main/
│       ├── java/com/example/appcrud/
│       │   ├── MainActivity.kt          # Entry point - pantalla Gastos
│       │   ├── PagosActivity.kt         # Entry point - pantalla Pagos
│       │   ├── GastosScreen.kt          # UI, lista, tarjetas y modal de Gastos
│       │   ├── PagosScreen.kt           # UI, lista, tarjetas y modal de Pagos
│       │   ├── BottomNavBar.kt          # Navegación inferior (Gastos / Pagos)
│       │   ├── Gasto.kt                 # Modelo de datos - Gasto
│       │   ├── Pago.kt                  # Modelo de datos - Pago
│       │   ├── GastoAdapter.kt          # Adapter RecyclerView Gastos
│       │   ├── PagoAdapter.kt           # Adapter RecyclerView Pagos
│       │   ├── JsonHelper.kt            # Persistencia JSON - lee y guarda Gastos
│       │   └── JsonHelperPagos.kt       # Persistencia JSON - lee y guarda Pagos
│       ├── res/                         # Recursos (Imágenes, Textos, Estilos)
│       └── AndroidManifest.xml          # Configuración principal de Android
├── build.gradle.kts                     # Configuración de dependencias
├── settings.gradle.kts                  # Configuración de módulos
└── gradle.properties                    # Propiedades globales de compilación
```
## Pantallazos

### Gastos

| Listado | Crear | Editar | Eliminar |
|---------|-------|--------|----------|
| ![Listado](url_imagen) | ![Crear](url_imagen) | ![Editar](url_imagen) | ![Eliminar](url_imagen) |

### Pagos

| Listado | Crear | Editar | Eliminar |
|---------|-------|--------|----------|
| ![Listado](url_imagen) | ![Crear](url_imagen) | ![Editar](url_imagen) | ![Eliminar](url_imagen) |

## Tecnologias
1. Kotlin1.9+
2. Jetpack Compose BOM 2024+
3. Material 31.2+
4. Gson2.10+
5. Android SDKAPI 30+

