
output "vpc_id" {
  description = "ID de la VPC principal"
  value       = module.networking.vpc_id
}

output "public_subnet_ids" {
  description = "IDs de las subnets públicas"
  value       = module.networking.public_subnet_ids
}

output "ecr_repository_url" {
  description = "URL del repositorio ECR"
  value       = module.container_registry.repository_url
}

output "public_url" {
  description = "URL pública del Load Balancer para acceder a la aplicación"
  value       = "http://${module.load_balancer.load_balancer_dns_name}"
}

output "alb_dns_name" {
  description = "DNS name del Application Load Balancer"
  value       = module.load_balancer.load_balancer_dns_name
}

output "ecs_cluster_name" {
  description = "Nombre del cluster ECS"
  value       = module.compute.ecs_cluster_name
}

output "ecs_service_name" {
  description = "Nombre del servicio ECS"
  value       = module.compute.ecs_service_name
}

output "log_group_name" {
  description = "Nombre del grupo de logs de CloudWatch"
  value       = module.compute.log_group_name
}

output "github_actions_role_arn" {
  description = "ARN del rol de GitHub Actions para asumir con OIDC"
  value       = module.cicd.github_actions_role_arn
}

output "github_oidc_provider_arn" {
  description = "ARN del proveedor OIDC de GitHub"
  value       = module.cicd.github_oidc_provider_arn
}

output "github_actions_role_name" {
  description = "Nombre del rol de GitHub Actions"
  value       = module.cicd.github_actions_role_name
}
