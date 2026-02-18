# 🛒 Ecommerce Backend API

Bienvenido al backend del proyecto **Ecommerce**. Esta es una API RESTful robusta construida con **Java** y **Spring Boot**, diseñada para gestionar las operaciones de una plataforma de comercio electrónico, incluyendo usuarios, productos, carritos de compra, pedidos y pagos.

## 🚀 Tecnologías Utilizadas

El proyecto utiliza un stack tecnológico moderno y estándar en la industria:

* **Lenguaje:** Java 25
* **Framework Principal:** Spring Boot 3.5.6
* **Base de Datos:** PostgreSQL
* **Persistencia:** Spring Data JPA (Hibernate)
* **Seguridad:** Spring Security (Configurado para APIs REST)
* **Mapeo de Objetos:** MapStruct 1.6.3
* **Validación:** Jakarta Bean Validation (Hibernate Validator)
* **Herramientas:** Lombok, Maven

## 📋 Características Principales

La API está dividida en varios módulos funcionales:

### 👤 Usuarios (`/usuario`)
* Registro y autenticación de usuarios.
* Gestión de perfiles (ver, editar, eliminar).
* Hashing de contraseñas con `BCrypt`.

### 📦 Productos y Categorías (`/producto`, `/categoria`)
* **Catálogo:** Listar productos, buscar por nombre o descripción.
* **Filtrado:** Ordenar productos por categoría.
* **Inventario:** Gestión de stock en tiempo real (validación de stock negativo).
* **Categorías:** Estructura jerárquica (categorías padre/hija).

### 🛒 Carrito de Compras (`/itemcarrito`)
* Agregar productos al carrito.
* Modificar cantidades (con validación de stock disponible).
* Vaciar carrito o eliminar ítems específicos.

### 🧾 Pedidos (`/pedidos`)
* **Checkout:** Transformación de carrito a pedido confirmado.
* **Historial:** Ver pedidos por usuario.
* **Estados:** Gestión de estados del pedido (Iniciado, Cancelado, etc.).
* **Cálculo:** Cálculo automático de totales.

### 💳 Pagos (`/pagos`)
* Simulación de pasarelas de pago (PayPal, Visa, etc.).
* Registro de transacciones asociadas a pedidos.

## ⚙️ Configuración del Proyecto

### Prerrequisitos
1.  Tener instalado **Java JDK 25**.
2.  Tener instalado **PostgreSQL**.
3.  Tener **Maven** instalado (o usar el wrapper `mvnw` incluido).

### Base de Datos
Debes crear una base de datos en PostgreSQL llamada `ecomerceb`.

La configuración se encuentra en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecomerceb
spring.datasource.username=postgres
spring.datasource.password=135790
spring.jpa.hibernate.ddl-auto=update
Asegúrate de cambiar username y password por tus credenciales locales.
```
🛠️ Instalación y Ejecución
Clonar el repositorio:

```
Bash

git clone [https://github.com/tu-usuario/ecommerce.git](https://github.com/tu-usuario/ecommerce.git)
cd ecommerce
```
Compilar el proyecto:
```
Bash

./mvnw clean install
```
Ejecutar la aplicación:

```
Bash

./mvnw spring-boot:run
```
La API estará disponible en: http://localhost:8080


📂 Estructura del Proyecto
El código sigue una Arquitectura por Capas limpia:
```
Plaintext

src/main/java/com/Jesus/Ecommerce
│
├── 🎮 Controllers      # Puntos de entrada de la API (REST)
├── 🧠 Services         # Lógica de negocio y validaciones
├── 💾 Repositories     # Acceso a datos (Interfaces JPA)
├── 📦 Models (Entity)  # Entidades de Base de Datos
├── 📨 DTOs             # Objetos de transferencia de datos (Requests/Responses)
├── 🗺️ Mappers          # Conversión Entidad <-> DTO (MapStruct)
├── 🛡️ Configuracion    # Configuración de Security y Beans
└── ⚠️ Exepciones       # Manejo global de errores (GlobalExceptionHandler)
```
🧪 Endpoints de Ejemplo
Aquí tienes algunos ejemplos de cómo probar la API (usando Postman o cURL):
```
Crear un Producto (POST): http://localhost:8080/producto

JSON

{
  "nombre": "Laptop Gamer",
  "descripcion": "Alta gama",
  "precio": 1500.00,
  "cantidadStock": 10,
  "categoriaID": 1
}
Registrar Usuario (POST): http://localhost:8080/usuario

JSON

{
  "nombreUsuario": "jesusdev",
  "contrasena": "Password123!",
  "correoElectronico": "jesus@mail.com",
  "nombreCompleto": "Jesus Developer",
  "telefono": "5512345678",
  "rol": "CLIENTE"
}
```
 Contribución
¡Las contribuciones son bienvenidas! Por favor, abre un issue o envía un pull request para mejoras.

Desarrollado por Jesus Negrete Calixtro
