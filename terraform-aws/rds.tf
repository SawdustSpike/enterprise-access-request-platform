resource "aws_db_instance" "postgres" {
  identifier = "access-request-db"

  engine         = "postgres"
  engine_version = "16"

  instance_class = "db.t3.micro"

  allocated_storage = 20
  storage_type      = "gp3"

  db_name  = var.db_name
  username = var.db_username
  password = var.db_password

  publicly_accessible = true

  skip_final_snapshot     = true
  backup_retention_period = 7
  deletion_protection     = false
}