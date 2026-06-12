output "ecs_cluster_name" {
  description = "Nombre del cluster ECS"
  value       = aws_ecs_cluster.main.name
}

output "ecs_cluster_arn" {
  description = "ARN del cluster ECS"
  value       = aws_ecs_cluster.main.arn
}

output "ecs_service_name" {
  description = "Nombre del servicio ECS"
  value       = aws_ecs_service.app.name
}

output "ecs_service_arn" {
  description = "ARN del servicio ECS"
  value       = aws_ecs_service.app.arn
}

output "task_definition_arn" {
  description = "ARN de la task definition"
  value       = aws_ecs_task_definition.app.arn
}

output "task_execution_role_arn" {
  description = "ARN del rol de ejecución de tareas"
  value       = aws_iam_role.ecs_task_execution_role.arn
}

output "log_group_name" {
  description = "Nombre del log group de CloudWatch"
  value       = aws_cloudwatch_log_group.app.name
}

