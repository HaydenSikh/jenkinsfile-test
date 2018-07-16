pipeline {
  agent {
    docker {
      image 'hseeberger/scala-sbt:8u171_2.12.6_1.1.6'
      args '-v $HOME/.ivy2:/root/.ivy2'
    }
  }

  options {
    // Keep the 10 most recent builds
    buildDiscarder(logRotator(numToKeepStr:'10'))
  }

  stages {
    stage ('Build') {
      steps {
        sh './sbt clean test package'

      }
      post {
        always {
          archiveArtifacts 'target/scala-*/*.jar'
          junit 'target/test-reports/**/*.xml'
        }
      }
    }

    stage ('Post-build') {
      parallel {
        stage ('Check style') {
          steps {
            sh './sbt scalastyle'
          }

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
