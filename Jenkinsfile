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

  stage("build"){
          steps{
              echo "Building"
              
          }
  } 
  stage("test"){
          steps{
              echo "testing"
          }
  }  
  stage("deploy"){
          steps{
              echo "deploying"
    }
  }  
}
}
