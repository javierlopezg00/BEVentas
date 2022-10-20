pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                withSonarQubeEnv(installationName: 'SonarQube') {
                    sh 'chmod +x mvnw && ./mvnw clean verify sonar:sonar -Dsonar.qualitygate.wait=true -Dsonar.projectKey=javaService -Dsonar.login=sqp_beef938e6cabd802841d673ffb1266bb5dc32a52'
                }
            }
        }
    }
    post {
        success {
            mail to: 'javierlopezguzman00@gmail.com', subject:"La build fue exitosa en el pipeline ${env.JOB_NAME} ${env.GIT_LOCAL_BRANCH}", body: "Jenkins Pipeline ${env.JOB_NAME}, Numero de build ${env.BUILD_NUMBER}, Log disponible en el URL ${env.BUILD_URL}    "
        }
        failure {
            mail to: 'javierlopezguzman00@gmail.com', subject:"Ocurrio un fallo en el pipeline ${env.JOB_NAME}", body: "Jenkins Pipeline ${env.JOB_NAME}, Numero de build ${env.BUILD_NUMBER}, Log disponible en el URL ${env.BUILD_URL}"
        }
    }
}

