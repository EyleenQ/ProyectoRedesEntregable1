# ProyectoRedesEntregable1

Este proyecto cuenta con dos modulos separados que son un servidor y un cliente, los cuales permiten establecer una conexion FTP en ellos, esto con el fin de transpasar y almacenar archivos.

Software Requerido
Para el cliente
JAVA 1.8.0 -Versión IDE de Java
NetBeans 8.2 - Versión del IDE

Para el server
JAVA 1.8.0 -Versión IDE de Java
NetBeans 8.2 - Versión del IDE
MySQL Workbench 8.0 - Versión de la base de datos

Clonar Proyecto
Utilizando la información previa, se instalan las herramientas. Una vez están listas se puede clonar el repositorio.

Abrir NetBeans ir a Team->Git->Clone
En el campo que dice Url del repositorio se ingresa https://github.com/EyleenQ/ProyectoRedesEntregable1.git	
En los campo para usuario y clave se ingresar las credenciales correctas.
Después dar clic al botón de siguiente y se debe seleccionar la casilla que dice master.


Instalar Proyecto

Una vez se tenga el proyecto clonado en NetBeans se debe hacer lo siguiente:
Abrir el proyecto que desea ejecutar(FTPServer o FTPCliente)
Buscar la raíz del proyecto y posicionarse sobre esta, específicamente sobre el nombre
Revisar que tenga los requerimientos asociados mencionados.
Colocarse en la raíz del proyecto.
Seleccionar clic derecho sobre el proyecto>Run.


Setup Base de datos

Abrir MySQL Workbench 8.0
Selecciona la opción de agregar nueva conexión.
Ingresar las credenciales que se encuentran guardadas en el proyecto, para obtenerlas:
	Abrir el proyecto>Source Packages>ConexionBaseDatos>ConexionSQL
Una vez conectado busca la base de datos nombrada en el proyecto, ubicados en la misma sección mencionada.


Ejecutar Aplicación

Cuando todos los requerimientos estén instalados y listos, se podrá ejecutar la aplicación siguiendo los siguientes pasos: 
1. Abrir NetBeans 8.2
2.Clic derecho sobre el proyecto que fue anteriormente descargado
3. Seleccionar el botón de Run

Resultados

Cuando el aplicación esta corriendo

El Servidor:

Podremos ingresar los datos de un nuevo usuario y registrarlos en la base de datos y crear su carpeta asociada.
También se podrá permitir la conexión con los clientes, así como enviar y recibir archivos del cliente conectado.

El Cliente:

Tendremos la opción de ingresar los datos para establecer conexión con el servidor.
Una vez establecidas la conexión se tendrá la opción de enviar archivos al servidor para guárdalos en su carpeta asociada.
También se podrá recibir archivos que se encuentren guardados en la carpeta asociada en el servidor.

Clases Utilizadas
-Usuario (String nombreUsuario, String contrasenna)
