pipeline {
  agent any

  options {
    // Keep the 10 most recent builds
    buildDiscarder(logRotator(numToKeepStr:'10'))
  }

  stages {
    stage ('Build') {
      steps {
        sh './sbt clean package'

        archive includes: 'target/scala-*/*.jar'
      }
    }

    stage ('Post-build') {
      parallel {
        stage ('Check style') {
          sh './sbt scalastyle'

          post {
            success {
              checkstyle pattern: '**/target/scalastyle-result.xml'
            }
          }
        }

        stage ('Test Coverage') {
          steps {
            sh './sbt coverage test coverageReport'
          }

          post {
            success {
              publishHTML target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: 'target/scala-2.11/scoverage-report',
                reportFiles: 'index.html',
                reportName: 'Coverage'
              ]
            }
          }
        }

        stage ('Deploy to Prod') {
          steps {
            echo 'Deploying to Prod!'
          }
        }
      }
    }
  }
}
