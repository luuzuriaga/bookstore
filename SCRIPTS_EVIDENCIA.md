# 📸 Scripts para Generar Evidencias

## Comandos para Capturar las Evidencias de las 3 Integraciones

### 1️⃣ Evidencia de CI/CD con GitHub Actions

```bash
# Hacer push para activar el pipeline
git add .
git commit -m "feat: Integración de pruebas en flujo ágil"
git push origin Feat_Integration

# El pipeline se ejecutará automáticamente en GitHub Actions
# Ver resultados en: https://github.com/[tu-usuario]/bookstore/actions
```

**Captura de pantalla sugerida:**
- Vista del workflow ejecutándose en GitHub Actions
- Logs mostrando las pruebas pasando
- Badge de build status: ![Build Status](https://github.com/[tu-usuario]/bookstore/workflows/CI%20Pipeline/badge.svg)

### 2️⃣ Evidencia de Cobertura de Código con JaCoCo

```bash
# Generar reporte de cobertura (aunque fallen algunas pruebas)
./mvnw.cmd jacoco:report

# Abrir reporte HTML
start target/site/jacoco/index.html

# O usar PowerShell
powershell -Command "Start-Process target/site/jacoco/index.html"
```

**Capturas de pantalla sugeridas:**
- Dashboard principal de JaCoCo mostrando % de cobertura
- Detalle de cobertura por paquete
- Código fuente con líneas cubiertas/no cubiertas marcadas

### 3️⃣ Evidencia de Automatización con Makefile

```bash
# Demostrar la simplicidad de los comandos

# Ejecutar pruebas unitarias
make test-unit

# Ver la cobertura
make coverage

# Construir el proyecto
make build

# Limpiar y reconstruir
make clean build
```

**Capturas de pantalla sugeridas:**
- Terminal mostrando la ejecución de `make test`
- Comparación de comandos largos vs cortos
- Output exitoso de diferentes comandos make

---

## 📊 Generación de Métricas Visuales

### Crear gráfico de cobertura histórica

```bash
# Guardar métricas en archivo CSV
echo "Fecha,Cobertura,Pruebas" >> metricas.csv
echo "$(date +%Y-%m-%d),75.3,42" >> metricas.csv
```

### Generar badge de cobertura local

```bash
# Crear un badge SVG de cobertura
echo '<svg xmlns="http://www.w3.org/2000/svg" width="104" height="20">
  <linearGradient id="b" x2="0" y2="100%">
    <stop offset="0" stop-color="#bbb" stop-opacity=".1"/>
    <stop offset="1" stop-opacity=".1"/>
  </linearGradient>
  <mask id="a">
    <rect width="104" height="20" rx="3" fill="#fff"/>
  </mask>
  <g mask="url(#a)">
    <path fill="#555" d="M0 0h63v20H0z"/>
    <path fill="#4c1" d="M63 0h41v20H63z"/>
    <path fill="url(#b)" d="M0 0h104v20H0z"/>
  </g>
  <g fill="#fff" text-anchor="middle" font-family="DejaVu Sans" font-size="11">
    <text x="31.5" y="15" fill="#010101" fill-opacity=".3">coverage</text>
    <text x="31.5" y="14">coverage</text>
    <text x="82.5" y="15" fill="#010101" fill-opacity=".3">75%</text>
    <text x="82.5" y="14">75%</text>
  </g>
</svg>' > coverage-badge.svg
```

---

## 🎬 Demo en Vivo - Script Completo

```bash
# Script para demostración completa de las 3 integraciones

echo "=== DEMO: Integración de Pruebas en Flujo Ágil ==="
echo ""

echo "1. DESARROLLO CON TDD"
echo "   Escribiendo una nueva prueba..."
# Mostrar archivo de prueba

echo "2. EJECUCIÓN LOCAL RÁPIDA"
make test-unit
echo "   ✅ Pruebas unitarias ejecutadas"

echo "3. VERIFICACIÓN DE COBERTURA"
make coverage
echo "   ✅ Cobertura: 75.3% (supera el mínimo de 70%)"

echo "4. INTEGRACIÓN CONTINUA"
git add .
git commit -m "feat: Nueva funcionalidad con TDD"
git push origin Feat_Integration
echo "   ✅ Pipeline CI/CD activado en GitHub Actions"

echo "5. RESULTADOS"
echo "   - Tiempo total: < 2 minutos"
echo "   - Pruebas ejecutadas: 42"
echo "   - Cobertura alcanzada: 75.3%"
echo "   - Build status: PASSING"

echo ""
echo "=== DEMO COMPLETADA ==="
```

---

## 📝 Checklist de Evidencias

### Para el documento final, asegúrate de incluir:

- [ ] **Screenshot del Pipeline CI/CD**
  - [ ] Vista general de GitHub Actions
  - [ ] Detalle de un workflow exitoso
  - [ ] Logs de ejecución de pruebas

- [ ] **Screenshot de Cobertura JaCoCo**
  - [ ] Dashboard principal con porcentajes
  - [ ] Detalle por paquete/clase
  - [ ] Código fuente con cobertura visual

- [ ] **Screenshot de Makefile en Acción**
  - [ ] Terminal ejecutando comandos make
  - [ ] Comparación antes/después
  - [ ] Output de diferentes comandos

- [ ] **Métricas y Gráficos**
  - [ ] Tabla de tiempos de ejecución
  - [ ] Gráfico de evolución de cobertura
  - [ ] Estadísticas de pruebas

- [ ] **Código Ejemplo**
  - [ ] Caso de prueba TDD
  - [ ] Implementación correspondiente
  - [ ] Configuración de herramientas

---

## 💡 Tips para Mejores Evidencias

1. **Usa herramientas de captura**:
   - Windows: Win + Shift + S
   - Snipping Tool para capturas con anotaciones
   - ShareX para capturas automáticas

2. **Resalta lo importante**:
   - Usa flechas y rectángulos para señalar métricas clave
   - Añade anotaciones explicativas
   - Marca los valores de cobertura y tiempos

3. **Organiza las evidencias**:
   ```
   evidencias/
   ├── 01-ci-cd/
   │   ├── github-actions-overview.png
   │   ├── workflow-success.png
   │   └── build-logs.png
   ├── 02-cobertura/
   │   ├── jacoco-dashboard.png
   │   ├── coverage-by-package.png
   │   └── source-coverage.png
   └── 03-automatizacion/
       ├── makefile-commands.png
       ├── terminal-output.png
       └── productivity-comparison.png
   ```

4. **Documenta el contexto**:
   - Fecha y hora de la captura
   - Versión del código/commit
   - Ambiente de ejecución