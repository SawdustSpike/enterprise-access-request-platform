resource "azurerm_resource_group" "main" {
  name     = var.resource_group_name
  location = var.location
}
resource "azurerm_container_registry" "main" {
  name                = "acraccessrequestplatform"
  resource_group_name = azurerm_resource_group.main.name
  location            = azurerm_resource_group.main.location
  sku                 = "Basic"
  admin_enabled       = true
}
resource "azurerm_container_app_environment" "main" {
  name                = "cae-access-request-platform"
  location            = azurerm_resource_group.main.location
  resource_group_name = azurerm_resource_group.main.name
}
resource "azurerm_container_app" "main" {
  name                         = "access-request-platform"
  container_app_environment_id = azurerm_container_app_environment.main.id
  resource_group_name          = azurerm_resource_group.main.name
  revision_mode                = "Single"

  template {
    container {
      name   = "access-request-platform"
      image  = "${azurerm_container_registry.main.login_server}/access-request-platform:latest"
      cpu    = 0.5
      memory = "1Gi"
    }
  }

  ingress {
    external_enabled = true
    target_port      = 8080

    traffic_weight {
      latest_revision = true
      percentage      = 100
    }
  }

  registry {
    server               = azurerm_container_registry.main.login_server
    username             = azurerm_container_registry.main.admin_username
    password_secret_name = "acr-password"
  }

  secret {
    name  = "acr-password"
    value = azurerm_container_registry.main.admin_password
  }
}