stage name: 'Build'

node {
  sh './sbt clean package'
}

stage name: 'Analyze and Deploy'

parallel (
  staticAnalysis : node {
    // build job: 'jenkinsfile-test_analysis', wait: false
    sh './sbt scalastyle clean coverage test coverageReport'

    step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/target/scalastyle-result.xml', unHealthy: ''])
    step([$class: 'WarningsPublisher', canComputeNew: false, canResolveRelativePaths: false, consoleParsers: [[parserName: 'Scala Compiler (scalac)']], defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', messagesPattern: '', unHealthy: ''])
    step([$class: 'TasksPublisher', canComputeNew: false, defaultEncoding: '', excludePattern: 'target/', healthy: '', high: 'FIXME', low: '', normal: 'TODO', pattern: '**/*.scala', unHealthy: ''])
  },
  deploy: {
    stage concurrency: 1, name: 'Deploy_to_staging'

    node {
      sh """echo bundle clean --force
      echo bundle install
      echo bundle exec cap staging deploy
      """.stripIndent()

      sleep 5
    }

    stage concurrency: 1, name: 'Deploy_to_production'

    node {
      sh """echo bundle clean --force
      echo bundle install
      echo bundle exec cap production deploy
      """.stripIndent()

      sleep 20
    }
  }
)

