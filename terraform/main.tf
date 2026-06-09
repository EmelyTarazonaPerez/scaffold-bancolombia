provider "aws" {
  region = "us-east-2"
}

resource "aws_ecr_repository" "franchise_api" {
  name = "franchise-api-terraform"
}

resource "aws_ecs_cluster" "franchise_cluster" {
  name = "franchise-cluster-terraform"
}

resource "aws_cloudwatch_log_group" "franchise_logs" {
  name              = "/ecs/franchise-api-terraform"
  retention_in_days = 1
}