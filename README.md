# **TempoService** - Sistema de Gestión de Tenpistas y Transacciones Bancarias

## 📋 Descripción del Proyecto

**TempoService** es una aplicación backend desarrollada en **Spring Boot 4.0.1** que proporciona un sistema integral de gestión de **Tenpistas** (clientes) y **Transacciones Bancarias** asociadas a ellos. La aplicación está diseñada para ser escalable, segura y fácil de mantener, siguiendo las mejores prácticas de arquitectura de software y metodologías ágiles.

### 🎯 Objetivo de Negocio

TempoService soluciona la necesidad de gestionar de manera eficiente:
- **Registro y administración de Tenpistas** (clientes bancarios)
- **Registro y consulta de transacciones** asociadas a cada Tenpista
- **Trazabilidad completa** de operaciones con logging estructurado en JSON
- **Correlación de eventos** mediante IDs únicos para auditoría y análisis

---

## 🏗️ Arquitectura Técnica

### Stack Tecnológico

| Componente | Versión | Descripción |
|-----------|---------|-------------|
| **Java** | 17 LTS | Lenguaje de programación principal |
| **Spring Boot** | 4.0.1 | Framework web y de aplicación |
| **Spring Data JPA** | Latest | ORM para persistencia de datos |
| **PostgreSQL** | Latest | Base de datos relacional |
| **Maven** | 3.x+ | Gestor de dependencias y build |
| **JUnit 5** | 6.0.1 | Framework de testing |
| **Mockito** | 5.20.0 | Framework de mocking |
| **Swagger/OpenAPI** | 2.7.0 | Documentación de API |
| **Logback** | Latest | Framework de logging |
| **Logstash Encoder** | 8.0 | Codificador JSON para logs |

### Patrones de Diseño Implementados

- **MVC (Model-View-Controller)** - Separación de responsabilidades
- **Service Layer** - Lógica de negocio centralizada
- **Repository Pattern** - Abstracción de acceso a datos
- **Dependency Injection** - Spring IoC Container
- **REST API** - Arquitectura basada en recursos
- **Exception Handling** - Manejo centralizado de errores
- **Logging Estructurado** - Logs en formato JSON con correlación

---

## 📁 Estructura del Proyecto

```
tempoService/
├── src/
│   ├── main/
│   │   ├── java/org/bank/temposervice/
│   │   │   ├── tempoServiceApplication.java          # Clase principal
│   │   │   ├── config/                               # Configuración
│   │   │   │   └── OpenApiConfig.java                # Configuración Swagger/OpenAPI
│   │   │   ├── controller/                           # REST Controllers
│   │   │   │   ├── TenpistaController.java
│   │   │   │   └── TransactionController.java
│   │   │   ├── service/                              # Interfaces de servicios
│   │   │   │   ├── TenpistaService.java
│   │   │   │   ├── TransactionService.java
│   │   │   │   └── impl/                             # Implementaciones
│   │   │   │       ├── TenpistaServiceImpl.java
│   │   │   │       └── TransactionServiceImpl.java
│   │   │   ├── repository/                           # JPA Repositories
│   │   │   │   ├── TenpistaRepository.java
│   │   │   │   └── TransactionRepository.java
│   │   │   ├── entity/                               # Entidades JPA
│   │   │   │   └── Tenpista.java
│   │   │   ├── model/                                # Modelos de datos
│   │   │   │   └── Transaction.java
│   │   │   ├── dto/                                  # Data Transfer Objects
│   │   │   │   ├── request/
│   │   │   │   │   ├── TenpistaRequest.java
│   │   │   │   │   └── TransactionRequest.java
│   │   │   │   └── response/
│   │   │   │       ├── TenpistaResponse.java
│   │   │   │       ├── TransactionResponse.java
│   │   │   │       └── ErrorResponse.java
│   │   │   ├── exception/                            # Excepciones personalizadas
│   │   │   │   ├── BusinessException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── logging/                              # Configuración de logging
│   │   │   │   ├── CorrelationIdFilter.java
│   │   │   │   └── LoggingConstants.java
│   │   │   └── enums/
│   │   │       └── TransactionStatus.java
│   │   └── resources/
│   │       ├── application.properties                # Configuración principal
│   │       └── logback-spring.xml                    # Configuración de logging
│   └── test/
│       └── java/org/bank/temposervice/
│           ├── service/impl/
│           │   ├── TenpistaServiceImplTest.java      # Tests del servicio
│           │   └── TransactionServiceImplTest.java
│           └── controller/
│               ├── TenpistaControllerTest.java       # Tests del controller
│               └── TransactionControllerTest.java
├── pom.xml                                            # Configuración Maven
├── Dockerfile                                         # Configuración Docker
├── docker-compose.yml                                # Composición de servicios
└── README.md                                          # Este archivo
```

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:8080
```

### Documentación Interactiva
```
http://localhost:8080/swagger-ui.html
```

### 1️⃣ **Tenpistas** - Endpoints de Gestión de Clientes

#### 📖 Obtener todos los Tenpistas
```http
GET /tenpistas
```
- **Descripción**: Obtiene el listado completo de todos los Tenpistas
- **Respuesta (200)**: Lista de Tenpistas
```json
[
  {
    "id": 1,
    "name": "Juan Pérez"
  },
  {
    "id": 2,
    "name": "María García"
  }
]
```

#### 🔍 Obtener Tenpista por Nombre
```http
GET /tenpistas/{name}
```
- **Parámetro**: `name` (Path) - Nombre del Tenpista
- **Ejemplo**: `GET /tenpistas/Juan Pérez`
- **Respuesta (200)**:
```json
{
  "id": 1,
  "name": "Juan Pérez"
}
```
- **Respuesta (404)**: Tenpista no encontrado

#### ✨ Crear nuevo Tenpista
```http
POST /tenpistas
Content-Type: application/json

{
  "name": "Carlos López"
}
```
- **Request Body**:
```json
{
  "name": "Carlos López"
}
```
- **Respuesta (201)**:
```json
{
  "id": 3,
  "name": "Carlos López"
}
```
- **Validaciones**:
  - `name` es requerido
  - `name` no puede estar en blanco
  - `name` debe ser único

---

### 2️⃣ **Transacciones** - Endpoints de Gestión de Transacciones

#### 📖 Obtener todas las Transacciones
```http
GET /transaction
```
- **Descripción**: Obtiene el listado de todas las transacciones
- **Respuesta (200)**:
```json
[
  {
    "transactionId": 100,
    "amount": 500,
    "merchant": "Amazon",
    "tenpistaName": "Juan Pérez",
    "transactionDate": "2026-03-30T10:00:00",
    "createdAt": "2026-03-30T10:00:05"
  },
  {
    "transactionId": 101,
    "amount": 1000,
    "merchant": "eBay",
    "tenpistaName": "María García",
    "transactionDate": "2026-03-30T11:15:00",
    "createdAt": "2026-03-30T11:15:05"
  }
]
```

#### 🔍 Obtener Transacción por ID
```http
GET /transaction/{transactionId}
```
- **Parámetro**: `transactionId` (Path) - ID único de la transacción
- **Ejemplo**: `GET /transaction/100`
- **Respuesta (200)**:
```json
{
  "transactionId": 100,
  "amount": 500,
  "merchant": "Amazon",
  "tenpistaName": "Juan Pérez",
  "transactionDate": "2026-03-30T10:00:00",
  "createdAt": "2026-03-30T10:00:05"
}
```
- **Respuesta (404)**: Transacción no encontrada

#### ✨ Crear nueva Transacción
```http
POST /transaction
Content-Type: application/json

{
  "transactionId": 102,
  "amount": 750,
  "merchant": "Walmart",
  "tenpistaId": 1,
  "transactionDate": "2026-03-30T14:30:00"
}
```
- **Request Body**:
```json
{
  "transactionId": 102,
  "amount": 750,
  "merchant": "Walmart",
  "tenpistaId": 1,
  "transactionDate": "2026-03-30T14:30:00"
}
```
- **Respuesta (201)**:
```json
{
  "transactionId": 102,
  "amount": 750,
  "merchant": "Walmart",
  "tenpistaName": "Juan Pérez",
  "transactionDate": "2026-03-30T14:30:00",
  "createdAt": "2026-03-30T14:30:05"
}
```
- **Validaciones**:
  - `transactionId` es requerido y debe ser positivo
  - `amount` es requerido y debe ser mayor a 0
  - `merchant` es requerido y no puede estar en blanco
  - `tenpistaId` es requerido y debe ser positivo
  - `tenpistaId` debe existir en la base de datos
  - `transactionDate` es requerido
  - `transactionId` debe ser único

---

## 🗄️ Base de Datos

### Configuración
- **Motor**: PostgreSQL
- **Host**: `localhost`
- **Puerto**: `5432`
- **Base de datos**: `tempoDb`
- **Usuario**: `postgres`
- **Contraseña**: `postgres`
- **DDL**: Auto-generado por Hibernate (`create-drop`)

### Esquema de Datos

#### Tabla: `tenpistas`
```sql
CREATE TABLE tenpistas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT uk_tenpista_name UNIQUE (name)
);
```

**Columnas**:
- `id` (BIGINT) - Identificador único
- `name` (VARCHAR) - Nombre del Tenpista (UNIQUE)

---

#### Tabla: `transactions`
```sql
CREATE TABLE transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    transaction_id INT NOT NULL UNIQUE,
    amount INT NOT NULL,
    merchant VARCHAR(255) NOT NULL,
    tenpista_id BIGINT,
    transaction_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (tenpista_id) REFERENCES tenpistas(id)
);
```

**Columnas**:
- `id` (BIGINT) - Identificador único interno
- `transaction_id` (INT) - ID de negocio de la transacción (UNIQUE)
- `amount` (INT) - Monto de la transacción
- `merchant` (VARCHAR) - Nombre del comerciante
- `tenpista_id` (BIGINT) - Referencia al Tenpista
- `transaction_date` (TIMESTAMP) - Fecha de la transacción
- `created_at` (TIMESTAMP) - Fecha de creación (auto-generada)
- `updated_at` (TIMESTAMP) - Fecha de última actualización

---

## 📋 Requisitos

### Mínimos del Sistema
- **Java**: 17 o superior (LTS)
- **Maven**: 3.8.0 o superior
- **PostgreSQL**: 12 o superior
- **RAM**: 512 MB mínimo
- **Espacio en disco**: 500 MB

### Para Desarrollo
- **Java IDE**: IntelliJ IDEA, Eclipse o VS Code
- **Git**: Para control de versiones
- **Docker** (opcional): Para ejecución en contenedores
- **Postman** (opcional): Para pruebas de API

---

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd tempoService
```

### 2. Verificar Java
```bash
java -version
# Salida esperada: OpenJDK 17.x.x
```

### 3. Instalar y Ejecutar PostgreSQL

#### Opción A: PostgreSQL Local
```bash
# macOS con Homebrew
brew install postgresql@15
brew services start postgresql@15

# Linux (Ubuntu/Debian)
sudo apt-get install postgresql-15
sudo systemctl start postgresql

# Windows: Descargar desde https://www.postgresql.org/download/windows/
```

#### Opción B: PostgreSQL con Docker
```bash
docker run --name tempo-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=tempoDb \
  -p 5432:5432 \
  -d postgres:15
```

### 4. Configurar la Base de Datos
```bash
# Conectarse a PostgreSQL
psql -U postgres

# En la consola de psql
CREATE DATABASE tempoDb;
\q
```

### 5. Revisar Configuración de Aplicación

**Archivo**: `src/main/resources/application.properties`

```properties
spring.application.name=tempoServiceApplication

# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/tempoDb
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=create-drop

# Swagger Configuration
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

### 6. Compilar el Proyecto
```bash
./mvnw clean install -DskipTests
```

### 7. Ejecutar la Aplicación

#### Opción A: Maven
```bash
./mvnw spring-boot:run
```

#### Opción B: JAR ejecutable
```bash
java -jar target/tempoService-0.0.1-SNAPSHOT.jar
```

#### Opción C: Docker Compose
```bash
docker-compose up
```

### 8. Verificar que la aplicación está ejecutándose
```bash
curl -s http://localhost:8080/tenpistas | jq .
```

---

## 🧪 Testing

### Ejecutar todos los Tests
```bash
./mvnw test
```

### Ejecutar tests específicos
```bash
# Tests del servicio
./mvnw test -Dtest=TenpistaServiceImplTest

# Tests del controller
./mvnw test -Dtest=TenpistaControllerTest
```

### Cobertura de Tests
```bash
./mvnw test jacoco:report
```

### Cobertura Actual
- **Total de Tests**: 23
- **Casos Exitosos**: 23 / 23 (100%)
- **Cobertura**: Servicios y Controllers

#### Tests por Componente

| Componente | Tests | Estado |
|-----------|-------|--------|
| TenpistaServiceImpl | 6 | ✅ Pasados |
| TransactionServiceImpl | 7 | ✅ Pasados |
| TenpistaController | 5 | ✅ Pasados |
| TransactionController | 5 | ✅ Pasados |

---

## 📊 Logging

### Configuración de Logging

**Archivo**: `src/main/resources/logback-spring.xml`

### Niveles de Log
```properties
logging.level.org.hibernate=WARN
logging.level.org.hibernate.engine.transaction.internal.TransactionImpl=ERROR
logging.level.org.bank.temposervice=DEBUG
```

### Formato de Logs
```json
{
  "timestamp": "2026-03-30T16:00:00.000Z",
  "level": "INFO",
  "thread": "main",
  "logger": "org.bank.temposervice.service.impl.TenpistaServiceImpl",
  "message": "Getting all tenpistas",
  "correlationId": "abc123def456"
}
```

### Características
- ✅ Logging estructurado en JSON
- ✅ Correlación de eventos
- ✅ Trazabilidad de operaciones
- ✅ Integración con Logstash/ELK

---

## 🏃‍♂️ Ejecutar la Aplicación

### Iniciar Servicios Dependientes (Docker)
```bash
docker-compose up -d
```

### Iniciar la Aplicación
```bash
./mvnw spring-boot:run
```

### Salida Esperada
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_|\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v4.0.1)

Started tempoServiceApplication in 2.345 seconds
INFO  TomcatEmbeddedServletContainer (DispatcherServlet.java:534) - Initializing Spring DispatcherServlet 'dispatcherServlet'
```

### Acceder a la Aplicación
- **API REST**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

---

## 📚 Documentación de la API

### Swagger/OpenAPI UI
Accede a la documentación interactiva de la API en:
```
http://localhost:8080/swagger-ui.html
```

Aquí puedes:
- ✅ Ver todos los endpoints
- ✅ Probar las APIs directamente
- ✅ Ver esquemas de request/response
- ✅ Descargar especificación OpenAPI

### OpenAPI JSON
```
http://localhost:8080/v3/api-docs
```

---

## 🔧 Configuración Avanzada

### Variables de Entorno
```bash
# Base de datos
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=tempoDb
export DB_USER=postgres
export DB_PASSWORD=postgres

# Spring
export SERVER_PORT=8080
export LOG_LEVEL=INFO
```

### Cambiar Puerto
```properties
server.port=9090
```

### Cambiar Base de Datos
```properties
spring.datasource.url=jdbc:postgresql://tu-host:5432/tu-db
spring.datasource.username=tu-usuario
spring.datasource.password=tu-contraseña
```

---

## 🐳 Docker

### Construir Imagen Docker
```bash
docker build -t tenpista/tempo-service:latest .
```

### Ejecutar Contenedor
```bash
docker run -d \
  --name tempo-service \
  -p 8080:8080 \
  --env SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/tempoDb \
  --env SPRING_DATASOURCE_USERNAME=postgres \
  --env SPRING_DATASOURCE_PASSWORD=postgres \
  --link postgres:postgres \
  tenpista/tempo-service:latest
```

### Docker Compose (Simplificado)
```bash
docker-compose up
```

---

## 🐛 Solución de Problemas

### Error: Connection Refused
```
Error: Cannot connect to PostgreSQL
```
**Solución**: Verificar que PostgreSQL esté ejecutándose
```bash
pg_isready -h localhost -p 5432
```

### Error: Bad Credentials
```
User: postgres password authentication failed
```
**Solución**: Verificar credenciales en `application.properties`

### Error: Port Already in Use
```
Address already in use: bind
```
**Solución**: Cambiar puerto o matar proceso
```bash
lsof -i :8080
kill -9 <PID>
```

### Error: Dependency Issues
```
Could not find artifact...
```
**Solución**: Limpiar y descargar dependencias
```bash
./mvnw clean dependency:resolve
```

---

## 📈 Endpoints Ejemplo con cURL

### Crear Tenpista
```bash
curl -X POST http://localhost:8080/tenpistas \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan Pérez"}'
```

### Obtener todos los Tenpistas
```bash
curl http://localhost:8080/tenpistas
```

### Crear Transacción
```bash
curl -X POST http://localhost:8080/transaction \
  -H "Content-Type: application/json" \
  -d '{
    "transactionId": 100,
    "amount": 500,
    "merchant": "Amazon",
    "tenpistaId": 1,
    "transactionDate": "2026-03-30T10:00:00"
  }'
```

### Obtener Transacciones
```bash
curl http://localhost:8080/transaction
```

---

## 🔐 Seguridad

### Validación de Entrada
- ✅ Validación de anotaciones (`@NotNull`, `@NotBlank`, `@Positive`)
- ✅ Validación de tipos de datos
- ✅ Sanitización de strings

### Manejo de Errores
- ✅ Excepciones personalizadas
- ✅ Códigos de error HTTP estándar
- ✅ Mensajes de error descriptivos

### Base de Datos
- ✅ Constraints de unicidad
- ✅ Foreign keys
- ✅ Prepared statements (JPA)

### Logging
- ✅ No se registran datos sensibles
- ✅ Correlación de eventos para auditoría
- ✅ Trazabilidad de operaciones

---

## 📦 Dependencias Principales

```xml
<!-- Spring Boot Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Swagger/OpenAPI -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.7.0</version>
</dependency>

<!-- Logging JSON -->
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>8.0</version>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 🎯 Roadmap Futuro

- [ ] Autenticación y Autorización (JWT)
- [ ] Rate Limiting
- [ ] Caché (Redis)
- [ ] Búsqueda avanzada
- [ ] Paginación
- [ ] Validación adicional
- [ ] Métodos de pago
- [ ] Reportes
- [ ] Métricas de negocio

---

## 👥 Información del Equipo

- **Proyecto**: TempoService
- **Versión**: 0.0.1-SNAPSHOT
- **Estado**: En Desarrollo
- **Última Actualización**: 30 de Marzo de 2026

---

## 📞 Soporte

Para reportar problemas o sugerir mejoras, por favor:
1. Abre un Issue en el repositorio
2. Describe el problema detalladamente
3. Incluye logs relevantes

---

## 📄 Licencia

[Especificar licencia]

---

**Documentación actualizada**: 30 de Marzo de 2026

Para más información sobre Spring Boot, visita: [spring.io](https://spring.io)
