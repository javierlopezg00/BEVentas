pipeline{

    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    stages{

        stage('Scan'){
            steps{
                withSonarQubeEnv(installationName: 'SonarQube'){
                    sh 'chmod +x mvnw && ./mvnw clean verify sonar:sonar -Dsonar.projectKey=javaService -Dsonar.login=squ_6d18fcabc092776cc791d7e7ef06ef4101657be3'
                }
            }
        }
    }
    
      post {
            failure {
                mail to: 'jflores@unis.edu.gt', subject: 'The Pipeline failed :(' , body: "failure en pipeline  ${env.JOB_NAME}, build ${env.BUILD_DISPLAY_NAME}"
        }
    }   
}
