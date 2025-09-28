.PHONY: help test test-unit test-integration coverage build clean install run

help: ## Muestra esta ayuda
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

install: ## Instala las dependencias del proyecto
	mvn clean install -DskipTests

test: ## Ejecuta todas las pruebas
	mvn clean test verify

test-unit: ## Ejecuta solo las pruebas unitarias
	mvn clean test -Dtest="*ServiceTest"

test-integration: ## Ejecuta solo las pruebas de integración
	mvn clean test -Dtest="*IntegrationTest"

coverage: ## Genera reporte de cobertura de código
	mvn clean verify
	@echo "Reporte de cobertura generado en: target/site/jacoco/index.html"

build: ## Construye el proyecto
	mvn clean package

clean: ## Limpia archivos generados
	mvn clean

run: ## Ejecuta la aplicación
	mvn spring-boot:run

docker-build: ## Construye imagen Docker
	docker build -t bookstore-api:latest .

docker-run: ## Ejecuta contenedor Docker
	docker run -d -p 8080:8080 --name bookstore-api bookstore-api:latest

docker-stop: ## Detiene y elimina contenedor Docker
	docker stop bookstore-api && docker rm bookstore-api