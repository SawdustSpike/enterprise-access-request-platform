variable "aws_region" {
  default = "us-east-2"
}

variable "project_name" {
  default = "access-request-platform"
}
variable "db_name" {
  default = "accessplatform"
}

variable "db_username" {
  default = "pgadminuser"
}

variable "db_password" {
  description = "PostgreSQL admin password"
  sensitive   = true
}