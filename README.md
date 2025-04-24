# Sistema Educativo Microservicios 

##  Descripción
Sistema educativo basado en microservicios para la gestión de estudiantes, profesores, asignaturas y matrículas. Implementado con Spring Boot y arquitectura de microservicios.

##  Características Principales
-  Gestión completa de usuarios (estudiantes, profesores, administradores)
-  Control de asignaturas y matrículas
-  Autenticación JWT
-  Arquitectura de microservicios
-  Configuración centralizada
-  Service Discovery con Eureka

## Tecnologías Utilizadas
- Java 17
- Spring Boot 3.x
- Spring Cloud
- MySQL 8
- Docker & Docker Compose
- JWT
- Maven

##  Arquitectura
El sistema está compuesto por los siguientes microservicios:

- Servicio de Usuarios (Puerto: 9083)
- Servicio de Asignaturas (Puerto: 9081)
- Servicio de Matrículas (Puerto: 9082)
- Eureka Server (Puerto: 9080)
- Config Server (Puerto: 9888)

##  Requisitos Previos
- Java 17 o superior
- Docker y Docker Compose
- Maven
- Git

##  Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/sistema-educativo-microservicios.git
cd sistema-educativo-microservicios
```

### 2. Configurar variables de entorno
```properties
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/db_name
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=Arauca2024**
```

### 3. Construir los servicios
```bash
mvn clean package
```

### 4. Iniciar los servicios con Docker Compose
```bash
docker-compose up -d
```

##  Puertos y Servicios

| Servicio | Puerto |
|----------|--------|
| Eureka Server | 9080 |
| Config Server | 9888 |
| Usuarios | 9083 |
| Asignaturas | 9081 |
| Matrículas | 9082 |
| MySQL Asignaturas | 3307 |
| MySQL Matrículas | 3308 |
| MySQL Usuarios | 3309 |

##  Seguridad
El sistema utiliza JWT (JSON Web Tokens) para la autenticación. Para acceder a los endpoints protegidos:

### Obtener token mediante login:
```http
POST /api/auth/login
{
  "username": "usuario",
  "password": "contraseña"
}
```

### Usar el token en las solicitudes:
```http
Authorization: Bearer <token>
```

##  Documentación API

### Endpoints principales:

#### Usuarios
```http
POST /api/auth/login - Iniciar sesión
POST /api/auth/register - Registrar usuario
```

#### Asignaturas
```http
GET /api/asignaturas - Listar asignaturas
POST /api/asignaturas - Crear asignatura
GET /api/asignaturas/{id} - Obtener asignatura
```

#### Matrículas
```http
GET /api/matriculas - Listar matrículas
POST /api/matriculas - Crear matrícula
GET /api/matriculas/{id} - Obtener matrícula
```
