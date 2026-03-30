# Sistema de Logging - Tempo

## 📁 Estructura del Proyecto

```
issuingBank/
├── src/main/
│   ├── java/org/bank/issuingbank/
│   │   ├── logging/
│   │   │   ├── CorrelationIdFilter.java      # Filtro HTTP para correlationId
│   │   │   └── LoggingConstants.java          # Constantes de logging
│   │   ├── exception/
│   │   │   └── GlobalExceptionHandler.java    # Handler con logging integrado
│   │   └── service/impl/
│   │       └── PaymentServiceImpl.java        # Ejemplo de uso de logging
│   └── resources/
│       └── logback-spring.xml                  # Configuración de Logback
└── logs/                                       # Archivos de log (generados)
    ├── application.log                         # Todos los logs
    ├── application-info.log                    # Solo INFO
    └── application-error.log                   # Solo ERROR
```

## 🎯 Características Implementadas

### 1. **Logging Estructurado JSON**
- ✅ Formato JSON para fácil parsing (ELK, Grafana Loki, Datadog)
- ✅ Incluye timestamp, nivel, mensaje, logger, thread
- ✅ Metadata personalizada (service name, correlationId)

### 2. **CorrelationId (TraceId)**
- ✅ Generación automática con UUID
- ✅ Propagación via MDC (Mapped Diagnostic Context)
- ✅ Incluido en response header `X-Correlation-Id`
- ✅ Presente en todos los logs

### 3. **Niveles de Log**
- ✅ INFO: Operaciones normales
- ✅ DEBUG: Información detallada (solo desarrollo)
- ✅ WARN: Errores controlados (validación, negocio)
- ✅ ERROR: Errores críticos con stack trace

### 4. **Archivos Rotativos**
- ✅ Rotación por tamaño (10MB) y fecha
- ✅ Retención: 30 días (general), 60 días (errores)
- ✅ Límite total: 1GB (general), 500MB (info/error)
- ✅ Separación por nivel

### 5. **Perfiles de Ejecución**
- **dev/default**: Consola con formato legible, DEBUG activo
- **prod**: JSON a archivos, async appenders, INFO mínimo
- **test**: Logs mínimos, solo WARN+

### 6. **Rendimiento**
- ✅ Async Appenders (no bloquea threads)
- ✅ Queue size: 512 (general), 256 (info/error)
- ✅ discardingThreshold: 0 (no descarta logs)

## 🚀 Uso

### Ejemplo 1: Logging básico en un servicio

```java
@Service
public class MyService {
    private static final Logger log = LoggerFactory.getLogger(MyService.class);
    
    public void processOrder(String orderId) {
        log.info("Processing order - orderId: {}", orderId);
        
        try {
            // Lógica de negocio
            log.debug("Order details loaded - orderId: {}", orderId);
            
            // ...
            
            log.info("Order processed successfully - orderId: {}", orderId);
        } catch (Exception e) {
            log.error("Failed to process order - orderId: {}", orderId, e);
            throw e;
        }
    }
}
```

### Ejemplo 2: Logging con contexto adicional

```java
log.info("Payment approved - merchantId: {}, amount: {}, currency: {}, transactionId: {}",
    merchantId, amount, currency, transactionId);
```

### Ejemplo 3: Logging de errores

```java
// Error controlado (WARN)
log.warn("Invalid payment amount - amount: {}, minimum: {}", amount, MIN_AMOUNT);

// Error crítico (ERROR con stack trace)
log.error("Database connection failed - retries: {}", retryCount, exception);
```

## 📊 Formato de Logs

### Desarrollo (Consola)
```
2025-12-28 10:30:45.123 [http-nio-8080-exec-1] INFO  o.b.i.service.PaymentServiceImpl [fa2f2617-7a3f-44a7-af3f-50d5d427c139] - Processing payment - merchantId: MERCHANT_001, amount: 15000, currency: CLP
```

### Producción (JSON)
```json
{
  "timestamp": "2025-12-28T10:30:45.123-03:00",
  "level": "INFO",
  "thread": "http-nio-8080-exec-1",
  "logger": "org.bank.temposervice.service.PaymentServiceImpl",
  "message": "Processing payment - merchantId: MERCHANT_001, amount: 15000, currency: CLP",
  "correlationId": "fa2f2617-7a3f-44a7-af3f-50d5d427c139",
  "service": "tempo-service"
}
```

## 🔧 Configuración

### Cambiar nivel de log en runtime (application.properties)
```properties
# Nivel general
logging.level.root=INFO

# Nivel por paquete
logging.level.org.bank.temposervice=DEBUG
logging.level.org.springframework=WARN
```

### Activar perfil de producción
```bash
java -jar issuingBank.jar --spring.profiles.active=prod
```

## 🎯 Integración con Herramientas

### ELK Stack (Elasticsearch, Logstash, Kibana)
1. Los logs JSON se escriben en archivos
2. Filebeat o Logstash lee los archivos
3. Envía a Elasticsearch
4. Visualiza en Kibana con queries por `correlationId`

### Grafana Loki
1. Promtail lee los logs JSON
2. Envía a Loki
3. Query por labels: `{service="tempo-service", level="ERROR"}`

### Datadog
1. Datadog Agent lee los logs JSON
2. Parsing automático de campos
3. Dashboard con `correlationId`, `service`, `level`

## 📈 Best Practices Implementadas

1. ✅ **No usar concatenación de strings**: Usa placeholders `{}`
2. ✅ **Log en el nivel correcto**: INFO (operaciones), WARN (errores controlados), ERROR (críticos)
3. ✅ **Incluir contexto**: Siempre agregar IDs relevantes (transactionId, merchantId, etc.)
4. ✅ **No loggear información sensible**: PAN, CVV, contraseñas (usar máscaras)
5. ✅ **Limpiar MDC**: El filtro limpia MDC.clear() en finally
6. ✅ **Stack traces solo en ERROR**: Para debugging profundo
7. ✅ **Async para rendimiento**: Los logs no bloquean requests

## 🔒 Seguridad

- ❌ NO loggear: PAN completo, CVV, contraseñas, tokens completos
- ✅ SÍ loggear: PAN enmascarado (`****1234`), hash de tokens, IDs

## 📝 Ejemplo de Flujo Completo

```
1. Request llega → CorrelationIdFilter genera/extrae correlationId
2. CorrelationId se inyecta en MDC
3. Controller → Service → Repository (todos usan mismo correlationId)
4. Cualquier log incluye automáticamente el correlationId
5. Si hay error → GlobalExceptionHandler logea con correlationId
6. Response incluye header X-Correlation-Id
7. MDC se limpia en finally
```

## 🎓 Mantenimiento Futuro

- **Agregar más campos a MDC**: userId, sessionId, etc.
- **Métricas**: Integrar Micrometer para métricas de rendimiento
- **Alertas**: Configurar alertas en Grafana/Datadog por logs ERROR
- **Sampling**: Si volumen es alto, implementar sampling para DEBUG

---

**Logs generados**: `issuingBank/logs/`  
**Swagger**: http://localhost:8080/swagger-ui.html  
**Documentación completa**: Ver archivos fuente con JavaDoc
