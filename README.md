API RestFull Java Registro de Usuarios:

El nombre del proyecto se llama Record.

Este proyecto proporciona una Api Rest en Post, para registrar los usuarios, solo se aceptan valores en formato Json.

En este proyecto se trabajó con ciertas tecnologías y herramientas:

•	Java 8
•	Sprint Boot
•	UUID
•	JWT
•	Swagger con Spring Fox
•	Pruebas Unitarias en JUnit
•	JPA
•	Base de datos HSLQDB
•	Log4j
•	GitHub
•	Maven
•	Intelijj Idea
•	Postman
•	JSON

Base de datos HSQLDB

Pluggin: hsqldb-2.5.0.jar que sirve para trabajar en Java 8

Iniciar el Servidor

![servidor](https://user-images.githubusercontent.com/26574368/183315256-9e1ab540-510d-433c-b0c1-06662eda8d7a.png)

Abrir otra consola aparte

![image](https://user-images.githubusercontent.com/26574368/183315293-6f8b7795-c2bd-47c3-8fdb-8c95932e539d.png)


Crear la base de datos

![image](https://user-images.githubusercontent.com/26574368/183315302-a41a7f1d-3a0a-4921-a823-9cb691e34875.png)


Scripts

Los scripts para la creación de las tablas y algunos inserts se encuentran en una carpeta dentro del mismo proyecto, la carpeta scripts

Logs	

En las especificaciones del proyecto no se comenta sobre la necesidad de utilizar logs pero se considera conveniente que todo proyecto deba poseer un manejo de logs.

logs

Maven

Como manejador de dependencias se utilizó Maven.


Token en JWT

Para la creación del Token se utilizó la librería JWT.
La configuración actual esta para durar 5 minutos, este valor de duración es parametrizable por minutos y se puede configurar en el archivo config.properties en la línea.

token.duration=5


Expresión Regular para la contraseña

La contraseña debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una minúscula y al menos una mayúscula.
NO puede tener otros símbolos. 
Esta expresión regular se encuentra en el archivo config.properties, se puede cambiar esta expresión regular cambiando esta línea.

passwRegExp ^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,16}$ 


Llave de encriptación

Esta llave será usada para encriptar y desencriptar las contraseñas ya que no deben guardarse “en claro” en la base de datos.
Esta llave se encuentra en el archivo config.properties, se puede cambiar esta llave cambiando esta línea.	

secretKey:nisumsoftkey

Los métodos de encriptado y desencriptado se encuentran en la clase Utility.


Pruebas Unitarias

Para la realización de pruebas unitarias se realizó la prueba unitaria sobre EmailRepository, se pueden realizar sobre cada una de las clases, pero acá se tomará esta como ejemplo.
Se utilizó JUnit y como es una clase de persistencia se utilizó la notación @DataJpaTest que crea una base de datos en memoria para test y no manipular la data de nuestra base de datos.


![image](https://user-images.githubusercontent.com/26574368/183315413-cfc551fd-93c5-49d1-81b0-9d12cb7f1e96.png)


Swagger

Swagger es un framework que nos permite documentar nuestras API Rest en la web, se utilizó la librería Springfox que se le agrega como dependencia al archivo pom.xml de Maven, más una línea en el archivo application.properties, al ejecutar la aplicación esta puede ser accedida mediante Swagger y ver toda la documentación de la Api.

Línea agregada en el application.properties:

spring.mvc.pathmatch.matching-strategy=ant_path_matcher

Dirección Url para ver la documentación en Swagger: 

http://localhost:8080/swagger-ui/index.html

![image](https://user-images.githubusercontent.com/26574368/183315453-12447fe4-aac0-494e-8de5-e6c2cd74c960.png)


Diagrama de la solución

![image](https://user-images.githubusercontent.com/26574368/183315463-68f38753-f9c3-4f42-bdcf-67a1fb80d37d.png)


Pruebas e implementación

Datos de Entrada:

El servicio es API Rest, es del tipo POST y solo acepta formato JSON

URL: 

http://localhost:8080/recordUser

JSON:

Un ejemplo de datos de entrada seria el siguiente

{
    "name": "Javier Vidal",
    "email": "javier@vidal.org",
    "password": "Espain12",
    "phones": [
        {
            "number": "1234569",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}

Se valida que algunos datos sean obligatorios, se tomara como obligatorios los datos:

name
email
password

Estos no pueden ser nulos ni vacíos, si no, se devolverá un error con un mensaje de “Parametros incompletos”.

Los campos telefónicos no son obligatorios, pero si se envían deben de ser de caracteres numéricos, si no se devolverá un mensaje de error: “Los datos telefonicos deben ser numericos”, además los códigos del país y de la ciudad deben existir en la base de datos, si no, se devolverá un mensaje de error: “Los codigos de pais y de ciudad son invalidos, no se encuentran registrados”.

Datos de Salida:

Para la data de salida se devolverá un Json con todos los campos en String.

Ya que se solicita devolver un estatus, se enviara el campo estatus, con un campo statusMessage y en otro campo llamado data, allí estará la data solicitada, bien sea la data si es el proceso correcto o un campo message si existió algún error en el proceso.

Ejemplos:

Salida exitosa

{
    "status": "200",
    "statusMessage": "Status Ok",
    "data": {
        "id": "aebb611d-37d1-4117-bf54-dc86bd396e31",
        "created": "Sun Aug 07 16:32:58 VET 2022",
        "modified": "Sun Aug 07 16:32:58 VET 2022",
        "last_login": "Sun Aug 07 16:32:58 VET 2022",
        "token": "eyJhbGciOiJub25lIn0.eyJuYW1lIjoiQW50b25pbyBMb3BleiIsImVtYWlsIjoiYW50b25pb0Bsb3Blei5vcmciLCJzdWIiOiJhZWJiNjExZC0zN2QxLTQxMTctYmY1NC1kYzg2YmQzOTZlMzEiLCJqdGkiOiIzNDM2MmU2NC1lYWU3LTQ3ZTYtOWUzYi01MTk3YjY0MzA5NTgiLCJpYXQiOjE2NTk5MDQzNzgsImV4cCI6MTY1OTkwNDY3OH0.",
        "isactive": "1"
    }
}

Salida con un error en los datos

{
    "status": "201",
    "statusMessage": "Error en el proceso",
    "data": {
        "message": "El correo ya esta registrado"
    }
}

Salida si ocurre una excepcion en el sistema

{
    "status": "202",
    "statusMessage": " Error en el sistema",
    "data": null     
}


Se manejan esos 3 tipos de status

200 = status Ok
201= Error en el proceso, este se da porque el proceso de consulta fue realizado con éxito, pero el proceso devuelve un error por algún dato invalido
202=Este es un error para el manejo de excepciones, cuando hay una falla en el sistema, el campo data vendrá en null

Todos los estatus y los mensajes de los estatus, así como también los mensajes de error son configurables en el archivo config.properties

Postman

Para probar la API se utilizó la herramienta de Postman

![image](https://user-images.githubusercontent.com/26574368/183315621-23b4c231-2d6f-443e-9a8a-f57eec5e4580.png)


