output "repository_url" {
  description = "URL of repositorio ECR"
  value       = aws_ecr_repository.app.repository_url
}

output "repository_arn" {
  description = "ARN of repositorio ECR"
  value       = aws_ecr_repository.app.arn
}

output "repository_name" {
  description = "Nombre of repositorio ECR"
  value       = aws_ecr_repository.app.name
}

