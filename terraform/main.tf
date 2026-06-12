data "aws_caller_identity" "current" {}

module "networking" {
  source = "./modules/networking"

  project_name          = var.project_name
  vpc_cidr_block        = var.vpc_cidr_block
  public_subnet_1_cidr  = var.public_subnet_1_cidr
  public_subnet_2_cidr  = var.public_subnet_2_cidr
  availability_zone_1   = var.availability_zone_1
  availability_zone_2   = var.availability_zone_2
}

module "security" {
  source = "./modules/security"

  project_name    = var.project_name
  vpc_id          = module.networking.vpc_id
  container_port  = var.container_port
}


module "container_registry" {
  source = "./modules/container_registry"

  project_name = var.project_name
}

module "load_balancer" {
  source = "./modules/load_balancer"

  project_name             = var.project_name
  vpc_id                   = module.networking.vpc_id
  alb_security_group_id    = module.security.alb_security_group_id
  public_subnet_ids        = module.networking.public_subnet_ids
  container_port           = var.container_port
  health_check_path        = var.health_check_path
}


module "compute" {
  source = "./modules/compute"

  project_name                = var.project_name
  aws_region                  = var.aws_region
  container_image             = "${module.container_registry.repository_url}:${var.image_tag}"
  container_port              = var.container_port
  task_cpu                    = var.task_cpu
  task_memory                 = var.task_memory
  mongodb_uri                 = var.mongodb_uri
  log_retention_days          = var.log_retention_days
  desired_count               = var.desired_count
  public_subnet_ids           = module.networking.public_subnet_ids
  ecs_security_group_id       = module.security.ecs_security_group_id
  target_group_arn            = module.load_balancer.target_group_arn
  load_balancer_listener_arn  = module.load_balancer.listener_arn
}


module "cicd" {
  source = "./modules/cicd"

  github_repository    = var.github_repository
  github_branch        = var.github_branch
  aws_account_id       = data.aws_caller_identity.current.account_id
  project_name         = var.project_name
  ecr_repository_name  = module.container_registry.repository_name
  ecs_cluster_name     = module.compute.ecs_cluster_name
  ecs_service_name     = module.compute.ecs_service_name
}
