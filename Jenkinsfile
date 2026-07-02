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
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }
}