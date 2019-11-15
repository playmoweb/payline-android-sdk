pipeline {
    agent any
    
    options {
      buildDiscarder(logRotator(numToKeepStr:'10'))
    }
    
    stages {
      stage('Build') {
          steps {
            sh './gradlew clean assemble'
          }
      }
      stage('SonarQube analysis') {
        steps {
            withSonarQubeEnv('SonarMonext') {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
            }
        }
      }
      stage('Deploy') {
          steps {
              withCredentials([string(credentialsId: 'KEY_GPG_PASSPHRASE', variable: 'KEY_GPG_PASSPHRASE'),
                               usernamePassword(credentialsId: 'OSSRH', usernameVariable: 'OSSRH_USER', passwordVariable: 'OSSRH_PWD')]) {
                 sh './gradlew publish'
              }
          }
      }
    }
}
