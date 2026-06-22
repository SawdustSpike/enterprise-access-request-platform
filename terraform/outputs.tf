output "resource_group_name" {
  value = azurerm_resource_group.main.name
}
output "container_registry_login_server" {
  value = azurerm_container_registry.main.login_server
}
output "container_app_url" {
  value = azurerm_container_app.main.latest_revision_fqdn
}