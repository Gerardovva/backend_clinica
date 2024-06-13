# Clínica API Rest

Este proyecto es una API Rest diseñada para gestionar el flujo de información en una clínica médica. Proporciona una interfaz para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre pacientes y médicos, así como para agendar consultas entre ellos.

## Funcionalidades

La API ofrece las siguientes funcionalidades clave:

- **Gestión de Pacientes y Médicos**: Permite registrar, actualizar, eliminar y consultar información detallada de pacientes y médicos, incluyendo sus datos personales y médicos.
- **Agendamiento de Consultas**: Facilita la programación de citas entre pacientes y médicos, con soporte para la gestión de horarios y disponibilidad.
- **Autenticación y Seguridad**: Utiliza JSON Web Tokens (JWT) para autenticar usuarios y validar solicitudes, garantizando un acceso seguro y autorizado a los recursos de la API.
- **Manejo de Errores**: Implementa un sistema robusto de manejo de errores para una experiencia de usuario fluida y una mejor depuración.

## Detalles del Repositorio

El repositorio contiene una serie de actualizaciones y características implementadas por Gerardo Vásquez. Aquí hay un resumen de las principales funcionalidades y tecnologías utilizadas:

- **Funcionalidades**: Gestión completa de pacientes y médicos, agendamiento de consultas, autenticación segura y manejo de errores.
- **Tecnologías Utilizadas**:
  - **Spring Boot 3**: Framework utilizado para el desarrollo de la API, proporcionando un entorno de ejecución para aplicaciones Java.
  - **Java Development Kit (JDK) 17**: Versión del kit de desarrollo de Java utilizada para la implementación de la API.
  - **JPA (Java Persistence API)**: Utilizada para el mapeo objeto-relacional, facilitando el acceso y la manipulación de datos en la base de datos.
  - **MySQL**: Se emplea como sistema de gestión de bases de datos relacional para almacenar la información de la clínica.
  - **JSON Web Token (JWT)**: Ofrece un mecanismo seguro para la autenticación y autorización de usuarios, protegiendo la API contra accesos no autorizados.
  - **Filtros**: Implementados para mejorar la seguridad y el rendimiento de la API, filtrando y validando las solicitudes entrantes.

## Uso

Para comenzar a utilizar la API, sigue estos pasos:

1. **Clonación del Repositorio**: Clona el repositorio en tu máquina local utilizando el comando `git clone`.
2. **Creación de la Base de Datos**: Crea una base de datos MySQL llamada `vollmed_api`.
3. **Configuración del Entorno de Desarrollo**: Asegúrate de tener configurado un entorno de desarrollo adecuado con todas las dependencias necesarias.
4. **Ejecución de la API**: Inicia la API en tu servidor local.
5. **Interacción con la API**: Realiza solicitudes HTTP a los endpoints proporcionados para interactuar con los recursos de la API.

Para ejecutar el frontend, navega hasta el directorio `frontend_clinica` en el repositorio clonado y sigue las instrucciones específicas del proyecto Angular para ejecutar la aplicación.

## Frontend

Además de la API, este proyecto también cuenta con un frontend desarrollado con Angular para proporcionar una interfaz de usuario intuitiva y amigable para los usuarios finales.

