#!/bin/bash

# Script para construir y desplegar la aplicación
# Uso: ./build-docker.sh

set -e

echo "🏗️  Compilando proyecto..."
./gradlew clean build -x test

echo "📦 Copiando JAR al directorio de deployment..."
cp applications/app-service/build/libs/*.jar deployment/

echo "🐳 Construyendo imagen Docker..."
docker build -f deployment/Dockerfile -t cleanarchitecture:latest deployment/

echo "✅ Imagen construida exitosamente!"
echo ""
echo "Para ejecutar la aplicación:"
echo "  Opción 1: docker run -p 8080:8080 --env-file .env cleanarchitecture:latest"
echo "  Opción 2: docker-compose up -d"
echo ""
echo "Para verificar: curl http://localhost:8080/actuator/health"

