pipeline {
    agent any

    stages {
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
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            echo 'Pipeline completed successfully.'
        }

        failure {
            echo 'Pipeline failed.'
        }
    }
}