
# 📱 API RESTful con Flask y Retrofit

Este proyecto forma parte del Taller de Desarrollo de Aplicaciones Móviles. Consiste en la creación e integración de una API RESTful utilizando Flask (Python) y Retrofit (Android) para el consumo de datos desde una base de datos MySQL.

---

## 🛠️ Tecnologías Utilizadas

- Python 3.10
- Flask 2.3.3
- Flask-JWT
- PyMySQL
- MySQL
- Retrofit 2 (Android)
- Gson (Android)

---

## 🚀 Pasos para Implementar la API en PythonAnywhere

### 1. Crear una Cuenta

Registrarse en [PythonAnywhere](https://www.pythonanywhere.com/)

### 2. Crear un Nuevo Sitio Web

Seleccionar:

- Framework: Flask  
- Versión de Python: 3.10

### 3. Configurar el Entorno Virtual

#### 3.1 Crear entorno virtual

```bash
mkvirtualenv nombre_entorno --python=/usr/bin/python3.10
```

#### 3.2 Comandos útiles

```bash
workon nombre_entorno     # Activar entorno
deactivate                # Salir del entorno
echo $VIRTUAL_ENV         # Ver ruta del entorno virtual
```

#### 3.3 Establecer entorno en la configuración del sitio web

Desde la pestaña "Web", configura el entorno virtual en el campo correspondiente (Web > Virtualenv path).

### 4. Instalar Dependencias

Dentro del entorno virtual:

```bash
pip install Flask==2.3.3
pip install pymysql
pip install Flask-JWT
pip list  # Verificar paquetes instalados
```

---

## 🗃️ Base de Datos MySQL

### Crear la tabla

```sql
CREATE TABLE libros (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  isbn VARCHAR(255) NOT NULL
);
```

### Insertar registros de ejemplo

```sql
INSERT INTO libros (nombre, isbn) VALUES
('Cien años de soledad', '9780307474728'),
('El Quijote', '9780060934347'),
('1984', '9780451524935'),
('La sombra del viento', '9788408171052'),
('Orgullo y prejuicio', '9780486284736');
```

---

## 🔌 Conexión entre Flask y la Base de Datos

1. Abre el archivo principal de la aplicación Flask (por ejemplo, flask_app.py).

2. Establecer conexión con la base de datos usando PyMySQL.

Ejemplo:

```python
from flask import Flask, jsonify
import pymysql

def obtenerConexion():
    try:
        connection = pymysql.connect(
            host='djyerri.mysql.pythonanywhere-services.com',
            user='djyerri',
            password='abcXYZ$123',
            database='djyerri$testBD',
            cursorclass=pymysql.cursors.DictCursor
        )
        return connection
    except Exception as e:
        return str(e)

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello from Flask!'

@app.route('/connect')
def conexion():
    try:
        connection = obtenerConexion()
        connection.close()
        return '✅ Conexión exitosa'
    except Exception as e:
        return str(e)
```

---

## 🤖 Integración con Android Studio usando Retrofit

### 1. Configurar build.gradle (Module: app)

Agrega las siguientes dependencias:

```kotlin
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")
```

### 2. Crear Estructura de Paquetes

Organiza tu proyecto Android en los siguientes paquetes:

- Model  
  - Producto.java  
  - RptaObtenerProductos.java  

- Interface  
  - ApiService.java  

### 3. Definir Interface para Retrofit

ApiService.java:

```java
interface ApiService {
    @GET("/uri")
    Call<RptaObtenerProductos> obtenerProductos();
}
```

### 4. Consumir la API desde un Fragment o Activity

Ejemplo:

```java
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://TU_USUARIO.pythonanywhere.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build();

ApiService api = retrofit.create(ApiService.class);

api.obtenerProductos().enqueue(new Callback<RptaObtenerProductos>() {
    @Override
    public void onResponse(Call<RptaObtenerProductos> call, Response<RptaObtenerProductos> response) {
        if (response.isSuccessful()) {
            List<Producto> productos = response.body().getData();
            // Usar productos
        }
    }

    @Override
    public void onFailure(Call<RptaObtenerProductos> call, Throwable t) {
        Log.e("API", "Error al conectar: " + t.getMessage());
    }
});
```

---

## 📚 Sobre Retrofit y Gson

- Retrofit es una librería cliente HTTP para Android/Java que facilita el consumo de APIs REST.
- Gson es usada por Retrofit para convertir automáticamente entre JSON y objetos Java/Kotlin.

---

## 📌 Recomendaciones

- Verifica que tu app tenga permisos de Internet en el archivo AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

- Asegúrate de que el endpoint de la API esté expuesto correctamente y no tenga restricciones de CORS.
- Prueba los endpoints usando herramientas como Postman antes de integrarlos en la app.
