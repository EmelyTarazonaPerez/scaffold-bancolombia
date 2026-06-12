variable "project_name" {
  type = string
}

variable "aws_region" {
  type = string
}

variable "container_image" {
  type = string
}

variable "container_port" {
  type = number
}

variable "task_cpu" {
  type = string
}

variable "task_memory" {
  type = string
}

variable "mongodb_uri" {
  type      = string
  sensitive = true
}

variable "log_retention_days" {
  type = number
}

variable "desired_count" {
  type = number
}

variable "public_subnet_ids" {
  type = list(string)
}

variable "ecs_security_group_id" {
  type = string
}

variable "target_group_arn" {
  type = string
}

variable "load_balancer_listener_arn" {
  type = string
}

