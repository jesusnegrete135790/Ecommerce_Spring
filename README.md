# 🛒 E-Commerce API RESTful | Advanced Backend Architecture

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker)
![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions)

Una API RESTful robusta y escalable para la gestión completa de un sistema de comercio electrónico. Construida bajo los principios de **Clean Architecture**, este proyecto demuestra la implementación de patrones de diseño empresariales, procesamiento asíncrono y optimización de rendimiento.

## 🚀 Arquitectura y Decisiones Técnicas

* **Seguridad (Autenticación y Autorización):** Implementación de **Spring Security con Tokens JWT** sin estado. Rutas protegidas por roles y filtros de validación por cada petición al API Gateway interno.
* **Optimización de Lectura (Caché):** Integración de **Redis** para almacenar en memoria caché datos de alto tráfico (como el catálogo de categorías y productos), reduciendo los tiempos de respuesta y liberando carga de la base de datos principal.
* **Procesamiento Asíncrono:** Uso de **RabbitMQ** como Message Broker. Procesos pesados (como el envío de correos electrónicos de bienvenida o confirmación de pedidos) se delegan a colas de mensajería para no bloquear el hilo de respuesta del usuario.
* **Mapeo de Datos:** Aislamiento estricto de la capa de persistencia utilizando **MapStruct** para la conversión eficiente entre Entidades y DTOs, evitando fugas de información sensible.
* **Manejo de Excepciones:** Uso de un `@ControllerAdvice` global para capturar errores de negocio (ej. `StockMenorACero`, `PagoRechazadoException`) y estandarizar las respuestas JSON de error.

## ⚙️ Stack Tecnológico

* **Core:** Java 21, Spring Boot 3.5.x
* **Base de Datos Relacional:** PostgreSQL
* **Caché en Memoria:** Redis
* **Mensajería / Broker:** RabbitMQ
* **Mapeo de Objetos:** MapStruct
* **Testing:** JUnit 5, Mockito (100% aislamientos en capa de servicios)
* **Documentación:** Swagger / OpenAPI 3
* **Infraestructura:** Docker & Docker Compose
* **DevOps:** GitHub Actions (Integración Continua automatizada)

## 📦 Características Principales (Endpoints)

El sistema cuenta con controladores dedicados para gestionar todo el ciclo de vida del E-commerce:

1. **Auth (`/auth`):** Registro de usuarios y generación de JWT (Login).
2. **Catálogo (`/productos`, `/categorias`):** Gestión de inventario con validación de stock en tiempo real.
3. **Carrito de Compras (`/carrito`, `/items-carrito`):** Lógica transaccional para agrupar productos antes de la compra.
4. **Checkout y Envíos (`/pedidos`, `/pagos`):** Creación de órdenes y procesamiento simulado de pagos.

## 🛠️ Instalación y Despliegue Local

El proyecto está dockerizado para garantizar que funcione en cualquier entorno sin configuraciones complejas.

### Requisitos previos
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado y en ejecución.
* JDK 21 (Opcional, si deseas correrlo fuera del contenedor).

### Pasos para ejecutar

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/tu-repositorio.git](https://github.com/tu-usuario/tu-repositorio.git)
   cd tu-repositorio
Levantar la Infraestructura (Bases de datos y Broker):
Asegúrate de estar en la carpeta donde está el archivo docker-compose.yml.

```bash

docker compose up -d
```
Esto descargará e iniciará automáticamente PostgreSQL, Redis y RabbitMQ en segundo plano.

Ejecutar la aplicación Spring Boot:

```Bash

./mvnw spring-boot:run
```
📖 Documentación de la API (Swagger)
Una vez que la aplicación esté corriendo, La documentación interactiva se genera automáticamente.

URL de Swagger UI: http://localhost:8080/swagger-ui/index.html

Para probar: Ve al endpoint /auth/login o /auth/registro, obtén tu token JWT, haz clic en el botón verde "Authorize" en la parte superior derecha de Swagger, pega tu token con el prefijo Bearer  y podrás acceder a todos los endpoints protegidos.

🧪 Pruebas y Calidad de Código (CI/CD)
El proyecto cuenta con una extensa suite de pruebas unitarias enfocadas en la lógica de negocio (Capa de Servicios) utilizando JUnit y aislando la base de datos con Mockito.

Para ejecutar las pruebas localmente:

```Bash

  ./mvnw clean test
```
Integración Continua (GitHub Actions)
Cada push a la rama principal dispara un flujo de trabajo que levanta una máquina virtual en Ubuntu, instala Java 21 y ejecuta automáticamente todas las pruebas unitarias para garantizar la estabilidad del código antes de cualquier despliegue.

Desarrollado por Jesús Negrete Calixtro
Ingeniero de Software enfocado en el desarrollo Backend y Arquitecturas Escalables.