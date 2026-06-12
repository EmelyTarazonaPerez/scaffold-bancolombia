output "vpc_id" {
  description = "ID de la VPC"
  value       = aws_vpc.main.id
}

output "public_subnet_1_id" {
  description = "ID de la subnet pública 1"
  value       = aws_subnet.public_1.id
}

output "public_subnet_2_id" {
  description = "ID de la subnet pública 2"
  value       = aws_subnet.public_2.id
}

output "public_subnet_ids" {
  description = "IDs de las subnets públicas"
  value       = [aws_subnet.public_1.id, aws_subnet.public_2.id]
}

output "internet_gateway_id" {
  description = "ID del Internet Gateway"
  value       = aws_internet_gateway.main.id
}

