
node {
  wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm', 'defaultFg': 1, 'defaultBg': 2]) {
    wrap([$class: 'TimestamperBuildWrapper']) {
      stage name: 'Build'

      git 'git@github.com:HaydenSikh/jenkinsfile-test'
      sh './sbt clean package'

      stage name: 'Static Analysis'
      sh './sbt scalastyle clean coverage test coverageReport'

      step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/target/scalastyle-result.xml', unHealthy: ''])
      step([$class: 'WarningsPublisher', canComputeNew: false, canResolveRelativePaths: false, consoleParsers: [[parserName: 'Scala Compiler (scalac)']], defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', messagesPattern: '', unHealthy: ''])
      step([$class: 'TasksPublisher', canComputeNew: false, defaultEncoding: '', excludePattern: 'target/', healthy: '', high: 'FIXME', low: '', normal: 'TODO', pattern: '**/*.scala', unHealthy: ''])

      stage concurrency: 1, name: 'Deploy to staging'

      sh "echo bundle clean --force"
      sh "echo bundle install"
      sh "echo bundle exec cap staging deploy"

      sleep 5

      stage concurrency: 1, name: 'Deploy to production'

      sh "echo bundle clean --force"
      sh "echo bundle install"
      sh "echo bundle exec cap production deploy"

      sleep 20
    }
  }
}

