output "rds_endpoint" {
  description = "RDS PostgreSQL endpoint"
  value       = aws_db_instance.postgres.endpoint
}

output "db_name" {
  description = "RDS PostgreSQL database name"
  value       = aws_db_instance.postgres.db_name
}

output "db_username" {
  description = "RDS PostgreSQL username"
  value       = aws_db_instance.postgres.username
}

output "db_password" {
  description = "RDS PostgreSQL password"
  value       = aws_db_instance.postgres.password
  sensitive   = true
}
