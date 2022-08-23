pipeline{

    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    stages{

        stage('Scan'){
            steps{
                withSonarQubeEnv(installationName: 'SonarQube'){
                    sh 'chmod +x mvnw && ./mvnw clean  sonar:sonar'
                }
            }
        }
    }
}
