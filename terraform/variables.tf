variable "aws_region" {
  description = "AWS region where the infrastructure will be deployed"
  type        = string
  default     = "us-east-2"
}

variable "project_name" {
  description = "Project name (used for resource naming)"
  type        = string
  default     = "franchise-api"
}

variable "vpc_cidr_block" {
  description = "Main CIDR block for the VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "public_subnet_1_cidr" {
  description = "CIDR block for public subnet 1"
  type        = string
  default     = "10.0.1.0/24"
}

variable "public_subnet_2_cidr" {
  description = "CIDR block for public subnet 2"
  type        = string
  default     = "10.0.2.0/24"
}

variable "availability_zone_1" {
  description = "First availability zone"
  type        = string
  default     = "us-east-2a"
}

variable "availability_zone_2" {
  description = "Second availability zone"
  type        = string
  default     = "us-east-2b"
}

variable "container_port" {
  description = "Port on which the application listens inside the container"
  type        = number
  default     = 8080
}

variable "image_tag" {
  description = "Docker image tag in ECR"
  type        = string
  default     = "v3"
}

variable "health_check_path" {
  description = "Path for the ALB health check"
  type        = string
  default     = "/actuator/health"
}

variable "task_cpu" {
  description = "CPU allocated to each ECS task (256, 512, 1024, etc.)"
  type        = string
  default     = "256"
}

variable "task_memory" {
  description = "Memory allocated to each ECS task (512, 1024, 2048, etc. in MB)"
  type        = string
  default     = "512"
}

variable "desired_count" {
  description = "Desired number of ECS service instances/replicas"
  type        = number
  default     = 1
}

variable "log_retention_days" {
  description = "Log retention days in CloudWatch"
  type        = number
  default     = 7
}

variable "mongodb_uri" {
  description = "MongoDB connection URI (with credentials)"
  type        = string
  sensitive   = true
  default     = "mongodb+srv://emelydayannatp_db_user:3hqdbOaqN5aX8wIR@franchise.ffdctcj.mongodb.net/franchise?retryWrites=true&w=majority"
}

variable "github_repository" {
  description = "GitHub repository in owner/repo format"
  type        = string
  default     = "EmelyTarazonaPerez/scaffold-bancolombia"
}

variable "github_branch" {
  description = "GitHub branch allowed for CI/CD"
  type        = string
  default     = "main"
}
