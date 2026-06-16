data "tls_certificate" "github" {
  url = "https://token.actions.githubusercontent.com"
}

resource "aws_iam_openid_connect_provider" "github" {
  client_id_list  = ["sts.amazonaws.com"]
  thumbprint_list = [data.tls_certificate.github.certificates[0].sha1_fingerprint]
  url             = "https://token.actions.githubusercontent.com"
}

data "aws_iam_policy_document" "github_assume_role" {
  statement {
    effect = "Allow"

    principals {
      type        = "Federated"
      identifiers = [aws_iam_openid_connect_provider.github.arn]
    }

    actions = ["sts:AssumeRoleWithWebIdentity"]

    condition {
      test     = "StringEquals"
      variable = "token.actions.githubusercontent.com:aud"
      values   = ["sts.amazonaws.com"]
    }

    condition {
      test     = "StringLike"
      variable = "token.actions.githubusercontent.com:sub"
      values   = ["repo:${var.github_repository}:ref:refs/heads/${var.github_branch}"]
    }
  }
}

resource "aws_iam_role" "github_actions" {
  name               = "${var.project_name}-github-actions-role"
  assume_role_policy = data.aws_iam_policy_document.github_assume_role.json
}

data "aws_iam_policy_document" "github_ecr_policy" {
  statement {
    effect = "Allow"
    actions = [
      "ecr:GetAuthorizationToken",
      "ecr:BatchCheckLayerAvailability"
    ]
    resources = ["*"]
  }

  statement {
    effect = "Allow"
    actions = [
      "ecr:BatchGetImage",
      "ecr:GetDownloadUrlForLayer",
      "ecr:PutImage",
      "ecr:InitiateLayerUpload",
      "ecr:UploadLayerPart",
      "ecr:CompleteLayerUpload",
      "ecr:DescribeRepositories",
      "ecr:DescribeImages"
    ]
    resources = ["arn:aws:ecr:*:${var.aws_account_id}:repository/${var.ecr_repository_name}"]
  }
}

resource "aws_iam_role_policy" "github_ecr" {
  name   = "${var.project_name}-github-ecr-policy"
  role   = aws_iam_role.github_actions.id
  policy = data.aws_iam_policy_document.github_ecr_policy.json
}

data "aws_iam_policy_document" "github_ecs_policy" {
  statement {
    effect = "Allow"
    actions = [
      "ecs:UpdateService",
      "ecs:DescribeServices",
      "ecs:DescribeTaskDefinition",
      "ecs:DescribeCluster"
    ]
    resources = [
      "arn:aws:ecs:*:${var.aws_account_id}:service/${var.ecs_cluster_name}/${var.ecs_service_name}",
      "arn:aws:ecs:*:${var.aws_account_id}:task-definition/*"
    ]
  }

  statement {
    effect = "Allow"
    actions = [
      "iam:PassRole"
    ]
    resources = ["*"]
    condition {
      test     = "StringEquals"
      variable = "iam:PassedToService"
      values   = ["ecs-tasks.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy" "github_ecs" {
  name   = "${var.project_name}-github-ecs-policy"
  role   = aws_iam_role.github_actions.id
  policy = data.aws_iam_policy_document.github_ecs_policy.json
}

data "aws_iam_policy_document" "github_cloudwatch_policy" {
  statement {
    effect = "Allow"
    actions = [
      "logs:CreateLogGroup",
      "logs:CreateLogStream",
      "logs:PutLogEvents",
      "logs:DescribeLogStreams",
      "logs:DescribeLogGroups"
    ]
    resources = ["arn:aws:logs:*:${var.aws_account_id}:*"]
  }
}

resource "aws_iam_role_policy" "github_cloudwatch" {
  name   = "${var.project_name}-github-cloudwatch-policy"
  role   = aws_iam_role.github_actions.id
  policy = data.aws_iam_policy_document.github_cloudwatch_policy.json
}

data "aws_iam_policy_document" "github_terraform_policy" {
  statement {
    effect = "Allow"
    actions = [
      "s3:GetObject",
      "s3:PutObject",
      "s3:ListBucket"
    ]
    resources = [
      "arn:aws:s3:::*terraform*state*",
      "arn:aws:s3:::*terraform*state*/*"
    ]
  }

  statement {
    effect = "Allow"
    actions = [
      "dynamodb:PutItem",
      "dynamodb:GetItem",
      "dynamodb:DeleteItem",
      "dynamodb:DescribeTable"
    ]
    resources = ["arn:aws:dynamodb:*:${var.aws_account_id}:table/*terraform*lock*"]
  }
}

resource "aws_iam_role_policy" "github_terraform" {
  name   = "${var.project_name}-github-terraform-policy"
  role   = aws_iam_role.github_actions.id
  policy = data.aws_iam_policy_document.github_terraform_policy.json
}

