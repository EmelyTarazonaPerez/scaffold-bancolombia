output "alb_security_group_id" {
  description = "ID the security group del ALB"
  value       = aws_security_group.alb.id
}

output "ecs_security_group_id" {
  description = "ID the security group de ECS"
  value       = aws_security_group.ecs.id
}

