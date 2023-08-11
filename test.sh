#!/bin/bash

# 构建Docker镜像
docker build -t hello -f Dockerfile .

# 应用Kubernetes配置
kubectl apply -f deployment.yaml
