#!/bin/bash

# GITHUB ACTIONS SETUP GUIDE
# Este script ayuda a configurar GitHub Actions con OIDC

echo "======================================"
echo "CI/CD con GitHub Actions + AWS OIDC"
echo "======================================"
echo ""

# 1. Aplicar cambios de Terraform
echo "1. Aplicar cambios de Terraform"
echo "   cd terraform"
echo "   terraform init"
echo "   terraform plan"
echo "   terraform apply"
echo ""

# 2. Obtener outputs
echo "2. Obtener outputs necesarios para GitHub"
echo "   terraform output github_actions_role_arn"
echo "   terraform output"
echo ""

# 3. Configurar secretos en GitHub
echo "3. Ir a GitHub"
echo "   Settings > Secrets and variables > Actions"
echo ""

echo "4. Crear estos Repository Secrets:"
echo "   - Name: AWS_ROLE_ARN"
echo "     Value: (output del paso anterior: github_actions_role_arn)"
echo ""
echo "   - Name: AWS_ACCOUNT_ID"
echo "     Value: (tu ID de cuenta AWS)"
echo ""

# 4. Variables de entorno
echo "5. Variables de entorno en el workflow (ya configuradas):"
echo "   - AWS_REGION: us-east-2"
echo "   - ECR_REPOSITORY: franchise-api"
echo ""

echo "======================================"
echo "Proceso completado!"
echo "======================================"
echo ""
echo "El workflow se ejecutará automáticamente"
echo "cuando hagas push a la rama 'main'"
echo ""
echo "Para ver logs:"
echo "Ir a GitHub > Actions tab"

