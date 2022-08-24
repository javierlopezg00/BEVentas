pipeline{

    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    stages{

        stage('Scan'){
            steps{
                withSonarQubeEnv(installationName: 'SonarQube'){
                    sh 'chmod +x mvnw && ./mvnw clean verify sonar:sonar -Dsonar.projectKey=javaService -Dsonar.login=sqp_e352961a19b65f147bc619fd244acd0b8926c488'
                }
            }
        }
    }
}
