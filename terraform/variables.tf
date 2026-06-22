variable "resource_group_name" {
  default = "rg-access-request-platform"
}

variable "location" {
  default = "eastus2"
}

variable "postgres_location" {
  default = "centralus"
}

variable "app_name" {
  default = "access-request-platform"
}

variable "postgres_admin_username" {
  default = "pgadminuser"
}

variable "postgres_admin_password" {
  sensitive = true
}