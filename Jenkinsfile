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
            mail to: 'javierlopezguzman00@gmail.com, jflores@unis.edu.gt', subject:"Build exitosa en el pipeline ${env.JOB_NAME} ${env.GIT_LOCAL_BRANCH}", body: "Pipeline ${env.JOB_NAME}, Build Numero ${env.BUILD_NUMBER}, Log: ${env.BUILD_URL}    "
        }
        failure {
            mail to: 'javierlopezguzman00@gmail.com, jflores@unis.edu.gt', subject:"Ocurrio un fallo en el pipeline ${env.JOB_NAME}", body: "Pipeline ${env.JOB_NAME}, Build ${env.BUILD_NUMBER}, Log:L ${env.BUILD_URL}"
        }
    }
}
