# Taller de Desarrollo de Aplicaciones Moviles
# Configuración *pythonanywhere* 
### 1. Crear un Nuevo Sitio Web 
Seleccionar:
- Framework: Flask
- Versión de Python: 3.10
---
### 2. Configurar el Entorno Virtual

```shell
mkvirtualenv nombre_entorno --python=/usr/bin/python3.10
```
---
### 3. Instalar Dependencias
Dentro del entorno virtual:

```shell
pip install Flask==2.3.3
pip install pymysql
pip install Flask-JWT
pip list  # Verificar paquetes instalados
```
---
### 4 . Configurar base de datos
Creación de la tabla
```sql
CREATE TABLE activos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(100),
    puntuacion DECIMAL(4,2)
);
```

Registros en la tabla
```sql
INSERT INTO activos (descripcion, puntuacion) VALUES
('Firewall XYZ', 5.76),
('Servidor ABC', 6.88),
('Switch MNP', 5.22);
```

## Conexión de la Base de Datos
```py
from flask import Flask, jsonify, request
import pymysql.cursors

def obtenerConexion():
    try:
        connection = pymysql.connect(
                        host='de-tu-cuenta',
                        user='usuario-de-tu-cuenta',
                        password='de-tu-cuenta',
                        database='de-tu-cuenta',
                        cursorclass=pymysql.cursors.DictCursor)
        return connection
    except Exception as e:
        return f"error base datos: {str(e)}"


app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello from Flask!'

@app.route('/conexion')
def conexion():
    connection = obtenerConexion()
    if connection:
        return 'conexion exitosa congresoBD'
    else:
        return 'error en la conexion'


@app.route('/api_obteneractivos_ac')
def obtenerActivos():
    try:
        connection = obtenerConexion()
        with connection:
            with connection.cursor() as cursor:
                sql = "SELECT `id`, `descripcion`, `puntuacion` FROM `activos`"
                cursor.execute(sql)
                result = cursor.fetchall()
        return jsonify({
                "status": 1,
                "data": result,
                "message": "se obtuvo todos los activos"
            })
    except Exception as e:
        return jsonify({
                "satatus": 0,
                "message": str(e)
            })

@app.route('/api_agregarActivos_ac', methods=["POST"])
def agregarActivos():
    try:
        ag_desc = request.json["descripcion"]
        ag_punt = request.json["puntuacion"]
        connection = obtenerConexion()
        with connection:
            with connection.cursor() as cursor:
                sql = "INSERT INTO `activos` (`descripcion`, `puntuacion`) VALUES (%s, %s)"
                cursor.execute(sql, (ag_desc, ag_punt))
            connection.commit()
        return jsonify({
                "status": 1,
                "data": { "descripcion": ag_desc, "puntuacion": ag_punt},
                "message": "agregado correctamente"
            })
    except Exception as e:
        return jsonify({
                "status": 0,
                "message": str(e)
                })
```

## Integración con Android Studio
### 1. Configurar build.gradle (Module: app)
Agrega las siguientes dependencias:

```java
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")
```

### 2. Crear Archivos

Organiza tu proyecto Android en los siguientes paquetes:

- Models
    - Producto.java
    ```java
    public class Productos {  
        private int id;  
        private String descripcion;  
        private float puntuacion;  
      
        public int getId() {  
            return id;  
        }  
      
        public float getPuntuacion() {  
            return puntuacion;  
        }  
      
        public String getDescripcion() {  
            return descripcion;  
        }  
    }
```
    - RptaObtenerProductos.java
    ```java
    public class RptaObtenerProductos {  
          private int status;  
          private List<Productos> data;  
          private String message;  
        
          public int getStatus() {  
              return status;  
          }  
        
          public String getMessage() {  
              return message;  
          }  
        
          public List<Productos> getData() {  
              return data;  
          }  
    }
```
- Interface
    - APIYerri.java
    ```java
    public interface APIYerri {  
  
        @GET("api_obtenerProductos_ac")  
        Call<RptaObtenerProductos> obtenerProductos();  
    }
```



En la carpeta *manifests/AndroidManifest.xml*
```java
<uses-permission android:name="android.permission.INTERNET" />
```

