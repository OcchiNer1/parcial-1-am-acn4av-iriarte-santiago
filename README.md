# Chequeo

**Parcial 2 - Aplicaciones Móviles**  
**Santiago Alejo Iriarte Perez**  
**Comisión:** ACN4AV  
**Profesor:** Sergio Medina  

---

## Descripción

App de finanzas personales para registrar y visualizar gastos diarios. Diseño oscuro y moderno inspirado en apps fintech como Brubank y Lemon Cash, con video animado de fondo en todas las pantallas.

---

## Flujo de uso

```
Splash (animación intro)
    ↓
    ¿Hay sesión activa?
   /              \
 SÍ               NO
  ↓                ↓
Main           Login ──→ Registro
  ↓
+ Agregar
  ↓
Formulario
  ↓
Resumen (confirmación)
  ↓
Main ──→ Estadísticas
```

---

## Pantallas

### 1. SplashActivity
Pantalla de inicio con animación de intro (video de 4 segundos fabricado en Canva). Al terminar, chequea si hay una sesión de Firebase activa:
- Si hay sesión → va directo al Main
- Si no hay sesión → va al Login

**Elementos:** `FullScreenVideoView` custom que ocupa toda la pantalla sin dejar bordes.

---

### 2. LoginActivity
Pantalla de autenticación con Firebase Auth.

**Funcionalidades:**
- Ingreso con email y contraseña
- Validación de campos vacíos
- Mensaje de error si las credenciales son incorrectas
- Botón para ir a Registro si el usuario no tiene cuenta

**Elementos:** `ConstraintLayout`, `LinearLayout`, `EditText` (email, password), `Button` (ingresar, ir a registro), `TextView` (error).

**Comportamiento dinámico:** el botón se deshabilita mientras se procesa el login para evitar doble tap. El mensaje de error aparece y desaparece según el resultado.

---

### 3. RegistroActivity
Pantalla para crear una cuenta nueva en Firebase Auth.

**Funcionalidades:**
- Registro con email y contraseña
- Validación: campos vacíos y contraseña mínima de 6 caracteres
- Al registrarse exitosamente va directo al Main
- Botón para volver al Login si ya tiene cuenta

**Elementos:** `ConstraintLayout`, `LinearLayout`, `EditText` (email, password), `Button` (crear cuenta, volver), `TextView` (error).

---

### 4. MainActivity
Panel principal de la app. Muestra el total gastado en la semana y la lista de transacciones cargadas.

**Funcionalidades:**
- Visualización del gasto total semanal actualizado en tiempo real
- Lista de transacciones recientes como mini-cards con color por categoría
- Navegación al formulario para agregar un gasto
- Navegación a estadísticas

**Elementos:** `ConstraintLayout`, `LinearLayout` (cards), `ScrollView` + `LinearLayout` (lista dinámica), `Button` (agregar, estadísticas), `TextView` (monto, título).

**Comportamiento dinámico:** cada vez que se vuelve al Main desde el Resumen, la lista y el monto se actualizan automáticamente en `onResume()`. Las mini-cards se generan por código Java con un punto de color distinto por categoría.

---

### 5. FormularioGastoActivity
Pantalla para registrar un nuevo gasto.

**Funcionalidades:**
- Ingreso de monto 
- Ingreso de categoría
- Validación de campos vacíos y monto válido
- Al confirmar, actualiza `DatosGlobales` y `SharedPreferences`, y navega al Resumen pasando los datos por extras

**Elementos:** `ConstraintLayout`, `LinearLayout`, `EditText` (monto, categoría), `Button` (confirmar), `TextView` (labels, error).

**Pasaje de datos:** usa `Intent.putExtra()` para enviar monto y categoría a `ResumenActivity`.

---

### 6. ResumenActivity
Pantalla de confirmación del gasto registrado.

**Funcionalidades:**
- Muestra el monto gastado recibido por extras
- Muestra la categoría formateada (primera letra mayúscula)
- Botón para volver al inicio

**Elementos:** `ConstraintLayout`, `LinearLayout` (card central), `TextView` (monto, categoría), `Button` (volver).

**Recepción de datos:** usa `getIntent().getExtras()` para leer monto y categoría enviados desde el Formulario.

---

### 7. EstadisticasActivity
Vista analítica de los gastos agrupados por categoría.

**Funcionalidades:**
- Header con total general, cantidad de categorías y categoría con mayor gasto
- Lista de categorías ordenadas de mayor a menor gasto
- Barra de progreso proporcional al porcentaje de cada categoría
- Imagen por categoría descargada desde URL usando Glide (Unsplash)
- Si no hay imagen disponible, muestra círculo con la inicial de la categoría
- Colores únicos por categoría (paleta de 8 colores en rotación)

**Elementos:** `ConstraintLayout`, `ScrollView` + `LinearLayout` (lista dinámica), `ImageView` (icono Glide), `TextView` (categoría, monto, porcentaje, transacciones), `FrameLayout` + `View` (barra de progreso).

**Comportamiento dinámico:** las cards, barras e imágenes se generan completamente por código Java en base a los datos actuales.

---

## Tecnologías y decisiones técnicas

| Tecnología | Uso |
|---|---|
| Firebase Auth | Login y registro de usuarios |
| Glide 4.16 | Descarga de imágenes por categoría desde Unsplash |
| SharedPreferences | Persistencia local de gastos entre sesiones |
| ViewBinding | Acceso a vistas sin `findViewById` |
| FullScreenVideoView | Video de fondo full screen en todas las pantallas |
| Intent + Extras | Pasaje de datos entre activities |
| DatosGlobales | Clase estática para compartir estado en memoria |

---

## Organización de recursos

- `res/values/strings.xml` — todos los textos de la app
- `res/values/colors.xml` — paleta completa con nombres semánticos (`accent_purple`, `bg_card_principal`, `texto_muted`, etc.)
- `res/values/dimens.xml` — dimensiones, márgenes, tamaños de texto y alturas de botones

---

## Estructura del proyecto

```
app/src/main/java/com/example/chequeo/
├── SplashActivity.java
├── LoginActivity.java
├── RegistroActivity.java
├── MainActivity.java
├── FormularioGastoActivity.java
├── ResumenActivity.java
├── EstadisticasActivity.java
├── DatosGlobales.java
└── FullScreenVideoView.java

res/
├── layout/
│   ├── activity_splash.xml
│   ├── activity_login.xml
│   ├── activity_registro.xml
│   ├── activity_main.xml
│   ├── activity_formulario_gasto.xml
│   ├── activity_resumen.xml
│   ├── activity_estadisticas.xml
│   └── item_transaccion.xml
├── values/
│   ├── strings.xml
│   ├── colors.xml
│   └── dimens.xml
└── raw/
    ├── intro.mp4
    └── video.mp4
```

---

## Mockup

El mockup fue realizado en Canva. Las animaciones de los videos de fondo también fueron creadas ahí.

<img width="841" height="883" alt="Screenshot 2026-05-07 051825" src="https://github.com/user-attachments/assets/95abc319-4668-45d6-97b9-335c1c971c75" />
<img width="1232" height="813" alt="Screenshot 2026-05-07 051913" src="https://github.com/user-attachments/assets/b116f8cd-809b-4ac7-89f9-6f558d334070" />
<img width="1727" height="774" alt="Screenshot 2026-05-07 051857" src="https://github.com/user-attachments/assets/d00fa999-6ab7-434a-bf97-77dea9f6449e" />