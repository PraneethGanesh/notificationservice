pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                        url: 'https://github.com/PraneethGanesh/notificationservice.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t notificationservice:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                sh '''
                    docker rm -f notificationservice || true
                    docker run -d --name notificationservice -p 8100:8100 notificationservice:latest
                '''
            }
        }
    }

    post {
        success {
            echo 'Build, test, Docker build, and run completed successfully.'
        }

        failure {
            echo 'Pipeline failed.'
        }
    }
}