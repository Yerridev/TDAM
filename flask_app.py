
# A very simple Flask Hello World app for you to get started with...

from flask import Flask, jsonify, request
import pymysql.cursors
from flask_jwt import JWT, jwt_required, current_identity

# INICIO DE SEGURIDAD
def obtenerConexion():
        try:
            # conenct to the database
            connection = pymysql.connect(host='djyerri.mysql.pythonanywhere-services.com',
                                         user='djyerri',
                                         password='abcXYZ$123',
                                         database='djyerri$testBD',
                                             cursorclass=pymysql.cursors.DictCursor)

            return connection
        except Exception as e:
            str(e)

class User(object):
    def __init__(self, id, username, password):
        self.id = id
        self.username = username
        self.password = password

    def __str__(self):
        return "User(id='%s')" % self.id

def obtenerUsername(buscar_username):
    try:
        connection = obtenerConexion()
        with connection:
            with connection.cursor() as cursor:
                sql = "SELECT `id`, `password` FROM `users` WHERE `email` = %s "
                cursor.execute(sql, (buscar_username))
                result = cursor.fetchone()
        if result:
            return User(result['id'], buscar_username, result['password'])
        else:
            return None
    except Exception as e:
        jsonify({"state": 0,
                 "message": str(e)
                })

def obtenerUsuarioXid(buscar_id):
    try:
        connection = obtenerConexion()
        with connection:
            with connection.cursor() as cursor:
                sql = "SELECT `email`, `password` FROM `users` WHERE `id` = %s "
                cursor.execute(sql, (buscar_id))
                result = cursor.fetchone()
        if result:
            return User(buscar_id, result['id'], result['password'])
        else:
            return None
    except Exception as e:
        jsonify({"state": 0,
                 "message": str(e)
                })

def authenticate(username, password):
    #user = username_table.get(username, None)
    user = obtenerUsername(username)
    if user and user.password.encode('utf-8') == password.encode('utf-8'):
        return user

def identity(payload):
    user_id = payload['identity']
    # return userid_table.get(user_id, None)
    return obtenerUsuarioXid(user_id)

# FIN DE SEGURIDAD


app = Flask(__name__)
app.debug = True
app.config['SECRET_KEY'] = 'super-secret'

jwt = JWT(app, authenticate, identity)

@app.route('/')
def hello_world():
    return 'Hello from Flask!'

@app.route('/nueva-ruta')
def firts_route():
    return 'Hello from new route!'

@app.route('/connect')
def conexion():
    try:
        connection = obtenerConexion()
        connection.close()
        return '✅conexion exitosa'
    except Exception as e:
        return str(e)


@app.route('/api_insertarUsuairo', methods=['POST'])
def insertarUsuario():
    try:
        c_user = request.json["email"]
        c_password = request.json["password"]
        connection = obtenerConexion()

        with connection:
            with connection.cursor() as cursor:
                sql = "INSERT INTO `users` (`email`, `password`) VALUES (%s, %s)"
                cursor.execute(sql, (c_user, c_password))
            connection.commit()
        return jsonify({
                        "status": 1,
                        "data": {"user": c_user, "password": c_password},
                        "message": "registro correctamente"
                        })
    except Exception as e:
        return jsonify({"Status": 0,
                        "message": str(e)
                        })

@app.route('/api_obtenerUsuarios')
def obtenerUsuarios():
    try:
        connection = obtenerConexion()
        with connection:
            with connection.cursor() as cursor:
                sql = "SELECT * FROM `users`"
                cursor.execute(sql, )
                result = cursor.fetchall()
        return jsonify({
                    "status": 1,
                    "data": result,
                    "message": "Usuarios obtenidos correctamente"
            })
    except Exception as e:
        return jsonify({"status": 0,
                        "message": str(e)
                        })













