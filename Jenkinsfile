pipeline{

agent any

stages{

    stage('Scan'){
        steps{
            withSonarQubeEnv(installationName: 'sq1'){
                sh './mvn clean  sonar:sonar'
            }
        }
    }
  }  
}
}
