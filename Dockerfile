# 使用一个适合的基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

RUN cp /etc/apt/sources.list /etc/apt/sources.list.bak
COPY . .
ADD sources.list /etc/apt/
RUN apt-get update

# 将编译后的jar文件复制到容器中
COPY target/hello-rate-limiter-1.0.jar /app/hello-rate-limiter.jar

# 暴露应用程序端口
EXPOSE 8080

# 启动应用程序
CMD ["java", "-jar", "hello-rate-limiter.jar"]