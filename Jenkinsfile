pipeline {
    agent none
    stages {
        stage('Clone Code') {
            agent {
                label 'master'
            }
            steps {
                echo "1.Git Clone Code"

                git url: "https://kgithub.com/mikumifa/rate-limiter-hello.git"
            }
        }
        stage('Maven Build') {
            agent {
                docker {
                    image 'maven:latest'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                echo "2.Maven Build Stage"
                sh 'mvn -B clean package -Dmaven.test.skip=true'
            }
        }
        stage('Image Build') {
            agent {
                label 'master'
            }
            steps {
            echo "3.Image Build Stage"
            sh 'docker build -f Dockerfile -t hello:${BUILD_ID} . '
            sh 'docker tag  hello:${BUILD_ID}  harbor.edu.cn/nju23/hello:${BUILD_ID}'
            }
        }
        stage('Push') {
            agent {
                label 'main'
            }
            steps {
            echo "4.Push Docker Image Stage"
            sh "docker login --username=nju23 harbor.edu.cn -p nju232023"
            sh "docker push harbor.edu.cn/nju23/hello:${BUILD_ID}"
            }
        }
    }
}


node('slave') {
    container('jnlp-kubectl') {
        
        stage('Clone YAML') {
        echo "5. Git Clone YAML To Slave"
        git url: "https://kgithub.com/mikumifa/rate-limiter-hello.git"

        }
        
        stage('YAML') {
        echo "6. Change YAML File Stage"
        sh 'sed -i "s#{VERSION}#${BUILD_ID}#g" ./jenkins/scripts/deployment.yaml'
        }
    
        stage('Deploy') {
        echo "7. Deploy To K8s Stage"
        sh 'kubectl apply -f ./jenkins/scripts/deployment.yaml'
        }
    }
}
