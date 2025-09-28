# Guía de Integración de Pruebas en el Flujo Ágil - Bookstore API

## Resumen del Proyecto

**Bookstore API** es una aplicación REST desarrollada con Spring Boot que gestiona una librería online con las siguientes funcionalidades:

### Funcionalidades Implementadas:
- **Gestión de Libros**: CRUD completo (Create, Read, Update, Delete)
- **Gestión de Clientes**: Registro y administración de clientes con emails únicos
- **Sistema de Ventas**: Control de inventario y validación de stock en tiempo real
- **Validaciones de Negocio**:
  - Precio de libros debe ser positivo
  - Control de stock antes de ventas
  - Email único por cliente
  - Transacciones para mantener integridad de datos

## Integración de Pruebas en el Flujo Ágil

### 1. Estrategia de Pruebas Implementada

#### Tipos de Pruebas:
- **Pruebas Unitarias**: Para servicios y lógica de negocio (Mockito)
- **Pruebas de Integración**: Para controladores REST (MockMvc)
- **Pruebas de Base de Datos**: Con @DataJpaTest y H2 en memoria

#### Cobertura de Código:
- JaCoCo configurado con umbral mínimo del 70%
- Reportes automáticos en `target/site/jacoco/index.html`
- Exclusión de clases de modelo, repositorios y excepciones

### 2. Pipeline de Integración Continua (CI/CD)

#### GitHub Actions Workflow:
```yaml
Activadores:
- Push a ramas: main, develop, feat/**, Feat_**
- Pull Requests a: main, develop

Jobs:
1. Test Job:
   - Configuración de JDK 17
   - Base de datos MySQL en contenedor
   - Ejecución de pruebas unitarias
   - Ejecución de pruebas de integración
   - Generación de reportes de cobertura
   - Subida a Codecov

2. Build Job:
   - Construcción del artefacto JAR
   - Archivo del JAR como artefacto
```

### 3. Flujo de Trabajo Ágil

#### Proceso de Desarrollo TDD:
1. **Escribir la prueba primero** (Test-First)
2. **Ver la prueba fallar** (Red)
3. **Escribir código mínimo para pasar** (Green)
4. **Refactorizar** (Refactor)
5. **Ejecutar todas las pruebas**
6. **Commit y Push** (activa CI automáticamente)

#### Comandos Disponibles (Makefile):
```bash
make test              # Ejecuta todas las pruebas
make test-unit        # Solo pruebas unitarias
make test-integration # Solo pruebas de integración
make coverage         # Genera reporte de cobertura
make build           # Construye el proyecto
make run             # Ejecuta la aplicación
```

### 4. Garantías de Calidad

#### En Cada Commit:
- ✅ Pruebas unitarias ejecutadas automáticamente
- ✅ Pruebas de integración validadas
- ✅ Cobertura de código verificada (mínimo 70%)
- ✅ Build del proyecto validado
- ✅ Artefactos generados y almacenados

#### En Cada Pull Request:
- ✅ Todas las pruebas deben pasar
- ✅ Cobertura no debe disminuir
- ✅ Build debe ser exitoso
- ✅ Revisión de código requerida

### 5. Métricas y Reportes

#### Métricas Disponibles:
- **Cobertura de líneas de código**: >70%
- **Tiempo de ejecución de pruebas**: <2 minutos
- **Pruebas totales**: 30+ casos de prueba
- **Tasa de éxito**: 100% requerido para merge

#### Reportes Generados:
- Surefire Reports (Maven)
- JaCoCo Coverage Reports
- Codecov Dashboard (online)
- GitHub Actions Logs

### 6. Beneficios del Flujo Implementado

1. **Detección Temprana de Errores**: Las pruebas se ejecutan en cada push
2. **Calidad Garantizada**: No se permite código sin pruebas
3. **Documentación Viva**: Las pruebas documentan el comportamiento esperado
4. **Refactoring Seguro**: Las pruebas protegen contra regresiones
5. **Integración Continua Real**: Cada cambio es validado automáticamente
6. **Feedback Rápido**: Los desarrolladores saben inmediatamente si algo falla

### 7. Mejoras Continuas

#### Próximos Pasos Sugeridos:
- [ ] Implementar pruebas de contrato (Contract Testing)
- [ ] Agregar pruebas de rendimiento (JMeter/Gatling)
- [ ] Implementar mutation testing (PIT)
- [ ] Configurar análisis estático (SonarQube)
- [ ] Agregar pruebas E2E con Selenium/Cypress

## Conclusión

Este proyecto demuestra una **integración efectiva de pruebas en el flujo ágil**, cumpliendo con los requisitos de:
- ✅ **Flujo iterativo**: Desarrollo incremental con TDD
- ✅ **Integración continua**: Pipeline automatizado en GitHub Actions
- ✅ **Pruebas en el desarrollo**: Parte integral del proceso, no una fase separada
- ✅ **Garantía de calidad**: Métricas y umbrales definidos

El sistema garantiza que cada cambio en el código es validado automáticamente, manteniendo la calidad del software y permitiendo entregas frecuentes y confiables.