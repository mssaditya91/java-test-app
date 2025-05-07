output "vpc_id" {
  value = aws_vpc.main.id
}

output "public_subnet_ids" {
  value = aws_subnet.public[*].id
}

output "eks_cluster_name" {
  value = aws_eks_cluster.main.name
}

output "eks_cluster_endpoint" {
  value = aws_eks_cluster.main.endpoint
}

output "eks_cluster_ca_certificate" {
  value = aws_eks_cluster.main.certificate_authority[0].data
}

output "eks_node_group_role_arn" {
  value = aws_iam_role.eks_node_role.arn
}
