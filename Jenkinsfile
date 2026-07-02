pipeline {
    agent any

    environment {
        IMAGE_NAME = "notification-service"
        IMAGE_TAG = "v1"
        CONTAINER_NAME = "notification-service"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% .'
            }
        }

        stage('Stop Old Container') {
            steps {
                bat 'docker stop %CONTAINER_NAME% || exit 0'
                bat 'docker rm %CONTAINER_NAME% || exit 0'
            }
        }

        stage('Run Container') {
            steps {
                bat 'docker run -d --name %CONTAINER_NAME% -p 8100:8100 %IMAGE_NAME%:%IMAGE_TAG%'
            }
        }
    }

    post {

        success {
            echo 'Pipeline Completed Successfully'
        }

        failure {
            echo 'Pipeline Failed'
        }
    }
}