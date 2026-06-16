output "load_balancer_arn" {
  description = "ARN of Load Balancer"
  value       = aws_lb.app.arn
}

output "load_balancer_dns_name" {
  description = "DNS name the Load Balancer"
  value       = aws_lb.app.dns_name
}

output "target_group_arn" {
  description = "ARN of Target Group"
  value       = aws_lb_target_group.app.arn
}

output "listener_arn" {
  description = "ARN of Listener HTTP"
  value       = aws_lb_listener.http.arn
}

