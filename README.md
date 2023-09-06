# Prueba tecnica Nexus !

## Ejecutar la aplicación
### App desplegada
Para acceder a la aplicación solo debes ingresar al siguiente enlace, ya que la aplicación se encuentra desplegada en usando usando Railway para el backend y Render para la Bd Postgres : 

https://nexuschallengebackend-production.up.railway.app/


### Ejecutar en local
Esta app esta usando como servidor ficticio DummyJSON el cual se encarga de suministrar los endpoints necesarios para su funcionamiento, por lo cual no requieres instalar ni ejecutar nada para hacer correr el "backend" ya que los endpoints se encuentran desplegados.

**1. Requisitos previos:**

- Descargar / clonar este repositorio en su maquina local.
-   Asegúrese de tener una IDE (Entorno de Desarrollo Integrado) instalada en su máquina. Se recomienda el uso de IntelliJ IDEA, que es compatible con proyectos Java.
- Asegúrese de que su máquina tenga Java 17 instalado. Puede verificar la versión de Java en su sistema ejecutando el siguiente comando en la terminal:
> java -version
 - Si Java 17 no está instalado, descárguelo e instálelo desde el sitio web oficial de Oracle.

**2. **Descargar el repositorio**:**

- Clone o descargue este repositorio desde GitHub y guárdelo en una ubicación local de su elección. Puede usar Git para clonar el repositorio o simplemente descargar el archivo ZIP desde la página del repositorio.

Para clonar el repositorio, ejecute el siguiente comando en la terminal:
> git clone https://github.com/JuanPabloSR/nexusChallenge_Backend.git

**2.3 Importar el proyecto en su IDE:**

-   Importe el proyecto en su IDE seleccionando la opción "Abrir" o "Importar proyecto". Navegue hasta la ubicación donde guardó el repositorio clonado y selecciónelo.
    
-   Siga las instrucciones para configurar el proyecto en su IDE. Esto puede incluir la configuración del SDK de Java, la importación de dependencias y la configuración de archivos de propiedades específicos del proyecto.

**2.4 Ejecutar la aplicación:**

-   Una vez que haya importado el proyecto con éxito, busque la clase principal de la aplicación. En la mayoría de los proyectos Java, esta clase tendrá un método `main` que sirve como punto de entrada.
    
-   Haga clic con el botón derecho en la clase principal y elija la opción "Run" (Ejecutar) o "Debug" (Depurar) según sus necesidades.
    
-   La aplicación se compilará y se ejecutará en su máquina local. Puede acceder a la aplicación a través de un navegador web o cualquier otra interfaz según la naturaleza del proyecto.

## Anotacion importante Base de datos Postgres
**Archivo de Configuración (application.properties):**

    spring.application.name=inventory-service
    spring.datasource.url=jdbc:postgresql://dpg-cjsf57m3m8ac73cj4cn0-a.oregon-postgres.render.com:5432/nexus_database?sslmode=require  
    spring.datasource.username=nexus_database_user  
    spring.datasource.password=JBkSFC060yT3h9Lf3ruWj3VctJNRmfML  
    spring.datasource.driver-class-name=org.postgresql.Driver  
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect  
      
    spring.jpa.hibernate.ddl-auto=update  
      
    spring.jpa.show-sql=true  

  
#======================================================  

    #Local Conection  
      
    #spring.application.name=inventory-service  
    #spring.datasource.url=jdbc:postgresql://localhost:5432/nexus?currentSchema=inventory&ssl=false  
    #spring.datasource.username=postgres  
    #spring.datasource.password=admin  
    #spring.datasource.driver-class-name=org.postgresql.Driver  
    #spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect  
    #spring.jpa.hibernate.ddl-auto=update  
    #spring.jpa.show-sql=true

-   Para la base de datos de Render, se utiliza la URL de conexión, nombre de usuario y contraseña proporcionados por Render. También se especifica el controlador de PostgreSQL y algunas configuraciones de Hibernate para controlar el comportamiento de la base de datos.
    
-   La configuración de la base de datos local está comentada en este ejemplo. Cuando desees conectarte a una base de datos local, simplemente descomenta estas líneas y ajusta la URL, nombre de usuario y contraseña según tu configuración local.

## Importar Tablas a la Base de Datos

-   El archivo `dump-${database-nexus}-202309061622` es un archivo para importar una base de datos en formato SQL en Postgres.
- Lo dejo acontinuacion por si necesitas descargarlo, ya contiene datos de prueba de los users y merchandise.

- https://drive.google.com/file/d/1ZdGXiXVK7llchNjSKa_qv2c-B4C7QqbF/view?usp=sharing

## Colección de solicitudes HTTP

- A continuación, se proporcionará un archivo que permitirá la importación de los endpoints de prueba en Postman. Este archivo contiene una colección de solicitudes HTTP predefinidas que facilitarán las pruebas y la interacción con el servicio web desarrollado por el usuario.

- https://drive.google.com/file/d/11GDEn32JLAV8JlfXAF8zsDfcz6_6IMPJ/view?usp=sharing

### Position

1.  **Agregar Posición (Add Position)**
    
    -   Método: POST
        
    -   Ruta: `/api/position`
        
    -   Descripción: Este endpoint permite agregar una nueva posición al sistema. Se debe enviar un objeto JSON en el cuerpo de la solicitud que incluya el campo "jobTitle" con el nombre del cargo que se desea agregar. Por ejemplo:
        
        jsonCopy code
        
        `{
          "jobTitle": "Practicante"
        }` 
        
2.  **Obtener Todas las Posiciones (Get All Positions)**
    
    -   Método: GET
    -   Ruta: `/api/position`
    -   Descripción: Este endpoint recupera todas las posiciones disponibles en el sistema y devuelve una lista de ellas en formato JSON. No requiere parámetros adicionales.
3.  **Eliminar Posición por ID (Delete Position by ID)**
    
    -   Método: DELETE
    -   Ruta: `/api/position/{id}`
    -   Descripción: Esta solicitud permite eliminar una posición específica del sistema utilizando su ID. Se debe proporcionar el ID de la posición como parte de la URL. Por ejemplo, `/posiciones/123` eliminará la posición con ID 123.

### User
1.  **Agregar Usuario (Add User)**
    
    -   Método: POST
        
    -   Ruta: `/api/user`
        
    -   Descripción: Este endpoint permite agregar un nuevo usuario al sistema. Para ello, se debe enviar un objeto JSON en el cuerpo de la solicitud con la siguiente estructura:
        
        jsonCopy code
        
        `{
          "name": "Alfonzo Lopez",
          "age": 23,
          "positionId": 1,
          "joinDate": "2023-08-09"
        }` 
        
        Donde:
        
        -   "name" representa el nombre del usuario.
        -   "age" representa la edad del usuario.
        -   "positionId" representa el ID de la posición que ocupa el usuario.
        -   "joinDate" representa la fecha de incorporación del usuario en formato "yyyy-MM-dd".

- No puede exisitir 2 usuarios con el mismo nombre ya que saldra una excepcion.

2.  **Editar Usuario (Edit User)**
    
    -   Método: PUT
        
    -   Ruta: `/api/user{id}`
        
    -   Descripción: Este endpoint permite editar la información de un usuario existente en el sistema. Se debe proporcionar el ID del usuario como parte de la URL y enviar un objeto JSON en el cuerpo de la solicitud con la siguiente estructura:
        
        jsonCopy code
        
        `{
          "name": "Camilo Cardenas",
          "age": 32,
          "positionId": 3,
          "joinDate": "2023-09-03"
        }` 
        
        Donde los campos tienen el mismo significado que en la solicitud "Agregar Usuario".
        
3.  **Obtener Todos los Usuarios (Get All Users)**
    
    -   Método: GET
    -   Ruta: `/api/user`
    -   Descripción: Este endpoint recupera todos los usuarios registrados en el sistema y devuelve una lista de ellos en formato JSON. No requiere parámetros adicionales.
4.  **Obtener Usuario por ID (Get User by ID)**
    
    -   Método: GET
    -   Ruta: `/api/user{id}`
    -   Descripción: Esta solicitud permite obtener los detalles de un usuario específico en el sistema. Se debe proporcionar el ID del usuario como parte de la URL.
5.  **Eliminar Usuario por ID (Delete User by ID)**
    
    -   Método: DELETE
    -   Ruta: `/api/user{id}`
    -   Descripción: Este endpoint permite eliminar un usuario del sistema utilizando su ID. Se debe proporcionar el ID del usuario como parte de la URL.


### Merchandise

1.  **Agregar Mercancía (Add Merchandise)**
    
    -   Método: POST
        
    -   Ruta: `api/merchandise`
        
    -   Descripción: Este endpoint permite agregar un nuevo producto o mercancía al sistema. Para ello, se debe enviar un objeto JSON en el cuerpo de la solicitud con la siguiente estructura:
        
        jsonCopy code
        
        `{
          "productName": "Aceite de transmisión Mobil4",
          "quantity": 55,
          "entryDate": "2023-08-30",
          "registeredById": 11
        }` 
        
        Restricciones:
        
        -   "productName" representa el nombre del producto y no puede haber nombres repetidos.
        -   "quantity" indica la cantidad de producto disponible.
        -   "entryDate" es la fecha de entrada del producto y debe ser igual o menor a la fecha actual.
        -   "registeredById" es el ID del usuario que registró la mercancía y debe existir en el sistema.
        
2.  **Editar Mercancía (Edit Merchandise)**
    
    -   Método: PUT
        
    -   Ruta: `api/merchandise/{id}`
        
    -   Descripción: Este endpoint permite editar la información de un producto o mercancía existente en el sistema. Se debe proporcionar el ID del producto como parte de la URL y enviar un objeto JSON en el cuerpo de la solicitud con la siguiente estructura:
        
        jsonCopy code
        
        `{
          "productName": "Aceite de transmisión Mobil 4",
          "quantity": 150,
          "entryDate": "2023-09-01",
          "editedById": 10
        }` 
        
        Restricciones:
        
        -   Los campos y restricciones son similares a los de la solicitud "Agregar Mercancía". Además, se debe proporcionar el "editedById" del usuario que realizó la edición.

3.  **Obtener Todas las Mercancías (Get All)**
    
    -   Método: GET
    -   Ruta: `/api/merchandise`
    -   Descripción: Este endpoint recupera todas las mercancías registradas en el sistema y devuelve una lista de ellas en formato JSON. Puede utilizar varios parámetros para filtrar los resultados:

        -   `entryDate`: Filtra por fecha de entrada de la mercancía.
        -   `searchTerm`: Filtra por nombre del producto o nombre del usuario que registró la mercancía.
        -   `size`: Define el número de resultados a mostrar por página (número).
        -   `page`: Maneja la paginación, donde la primera página es 0.
        -   `sort`: Permite ordenar los resultados por diferentes campos, por ejemplo, "quantity".
    
    Ejemplo de solicitud con filtros:
        
    `/api/merchandise?entryDate=2023-09-01&searchTerm=mecanico&size=10&page=2&sort=quantity` 
    
4.  **Obtener Mercancía por ID (Get Merchandise by ID)**
    
    -   Método: GET
    -   Ruta: `/api/merchandise/{id}`
    -   Descripción: Esta solicitud permite obtener los detalles de una mercancía específica en el sistema. Se debe proporcionar el ID de la mercancía como parte de la URL.
5.  **Eliminar Mercancía por ID (Delete Merchandise by ID)**
    
    -   Método: DELETE
    -   Ruta: `/api/merchandise/{id}`
    -   Descripción: Este endpoint permite eliminar una mercancía del sistema utilizando su ID. Se debe proporcionar el ID de la mercancía como parte de la URL.

## Tecnologías utilizadas
Las tecnologías utilizadas en este proyecto son:

-   Java 17: La última versión del lenguaje de programación Java, que proporciona mejoras y nuevas características.
    
    -   [Documentación de Java 17](https://docs.oracle.com/en/java/javase/17/)
-   Spring Boot: Un framework de desarrollo de aplicaciones Java que simplifica la creación de aplicaciones basadas en Spring.
    
    -   [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
-   PostgreSQL: Un sistema de gestión de bases de datos relacional de código abierto y muy potente.
    
    -   [Documentación de PostgreSQL](https://www.postgresql.org/docs/)
-   JUnit 5: Un framework de pruebas unitarias para Java que proporciona mejoras sobre versiones anteriores.
    
    -   [Documentación de JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
-   Mockito: Una biblioteca de Java para crear objetos simulados (mocks) en pruebas unitarias.
    
    -   [Documentación de Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/index.html)
-   Maven: Una herramienta de gestión de proyectos de código abierto que se utiliza para construir y gestionar proyectos Java.
    
    -   [Documentación de Apache Maven](https://maven.apache.org/guides/index.html)
-   JPA (Java Persistence API): Una API de Java que proporciona un marco para administrar relaciones entre objetos y bases de datos relacionales.
    
    -   [Documentación de JPA](https://docs.oracle.com/en/javaee/7/api/javax/persistence/package-summary.html)
-   REST (Representational State Transfer): Un estilo de arquitectura de software que utiliza un conjunto de restricciones para crear servicios web.
    
    -   [Documentación de REST](https://restfulapi.net/)
-   Modelo MVC (Modelo-Vista-Controlador): Un patrón de diseño de software que separa una aplicación en tres componentes principales: Modelo, Vista y Controlador.
    
    -   [Documentación de MVC](https://docs.oracle.com/javaee/6/tutorial/doc/bnacj.html)
-   Deploy en Render para la base de datos PostgreSQL: Render es una plataforma de alojamiento y despliegue que permite ejecutar aplicaciones y bases de datos en la nube.
    
    -   [Render](https://render.com/docs)
-   Railway para el backend Spring Boot: Railway es una plataforma de alojamiento y despliegue que facilita el despliegue y la gestión de aplicaciones en la nube.
    
    -   [Railway](https://docs.railway.app/)
 
    -   JaCoCo: Una herramienta de análisis de cobertura de código para Java que permite medir cuánto del código fuente se ha ejecutado en las pruebas.

    - Documentación de JaCoCo


## Breve descripción de la app

La empresa Nexos Software requiere desarrollar un sistema de inventario para el sector automotriz donde se controle la mercancía que ingresa y la que sale. 

El sistema debe permitir registrar nueva mercancía, editar y eliminar. 

Para registrar mercancía nueva se requiere tener en cuenta los siguientes datos: Nombre producto, cantidad, fecha de ingreso, usuario que realiza registro. Restricciones: no puede haber mas de una mercancía con el mismo nombre, la cantidad debe ser un número entero, la fecha de ingreso debe ser menor o igual a la fecha actual. 

Para editar mercancía se deben tener las mismas condiciones de cuando se registra una nueva, aparte hay que registrar el usuario que hace la modificación y la fecha. Para eliminar mercancía, solo lo puede hacer el usuario que la registró. 

El sistema también debe permitir mostrar por pantalla la mercancía registrada, los filtros de búsqueda pueden ser por fecha, usuario y/o nombre (se debe buscar mínimo por un filtro). Los usuarios que pueden ejecutar las acciones deben estar registrados con su nombre, edad, cargo y fecha de ingreso a la compañía
