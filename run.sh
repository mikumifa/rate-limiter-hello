
#!/bin/bash

# 应用配置文件的路径
DEPLOYMENT_FILE="./deployment.yaml"


gradle dockerBuildImage

# 创建 Deployment
echo "Creating Deployment..."
kubectl apply -f "$DEPLOYMENT_FILE"



# 输出 Service 信息
echo "Service information:"
kubectl get svc hello-service

echo "Deployment and Service have been created successfully."
