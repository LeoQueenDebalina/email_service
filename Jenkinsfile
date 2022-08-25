pipeline{
    agent any
    tools{
        maven 'maven_3.8.6'
    }
    stages{
        stage('Build maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/LeoQueenDebalina/email_service.git']]])
                sh 'mvn -e clean package shade:shade'
            }
        }

    }
}