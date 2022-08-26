pipeline {
    agent any
    tools{
        maven 'maven_3.8.6'
    }
    stages{
        stage('Checkout from github'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/LeoQueenDebalina/email_service.git']]])
            }
        }
        stage('Build mvn project'){
            steps{
                script{
                    sh 'mvn clean package shade:shade'
                }
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker image build -t email-service:latest .'
                }
            }
        }
    }
}