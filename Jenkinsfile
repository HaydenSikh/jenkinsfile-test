stage name: 'Build'

node {
  wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm', 'defaultFg': 1, 'defaultBg': 2]) {
    wrap([$class: 'TimestamperBuildWrapper']) {
      git 'git@github.com:HaydenSikh/jenkinsfile-test'
      sh './sbt clean package'
    }
  }
}

stage name: 'Analyze and Deploy'

parallel (
  staticAnalysis: { node {
    wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm', 'defaultFg': 1, 'defaultBg': 2]) {
      wrap([$class: 'TimestamperBuildWrapper']) {
        stage name: 'Static analysis'

        git 'git@github.com:HaydenSikh/jenkinsfile-test'

        // build job: 'jenkinsfile-test_analysis', wait: false
        sh './sbt scalastyle clean coverage test coverageReport'

        step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/target/scalastyle-result.xml', unHealthy: ''])
        step([$class: 'WarningsPublisher', canComputeNew: false, canResolveRelativePaths: false, consoleParsers: [[parserName: 'Scala Compiler (scalac)']], defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', messagesPattern: '', unHealthy: ''])
        step([$class: 'TasksPublisher', canComputeNew: false, defaultEncoding: '', excludePattern: 'target/', healthy: '', high: 'FIXME', low: '', normal: 'TODO', pattern: '**/*.scala', unHealthy: ''])
      }
    }
 }},
 deploy: {
   stage concurrency: 1, name: 'Deploy to staging'

    node {
      wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm', 'defaultFg': 1, 'defaultBg': 2]) {
        wrap([$class: 'TimestamperBuildWrapper']) {
          sh "echo bundle clean --force"
          sh "echo bundle install"
          sh "echo bundle exec cap staging deploy"

          sleep 5
        }
      }
    }

    stage concurrency: 1, name: 'Deploy to production'

    node {
      wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm', 'defaultFg': 1, 'defaultBg': 2]) {
        wrap([$class: 'TimestamperBuildWrapper']) {
          sh "echo bundle clean --force"
          sh "echo bundle install"
          sh "echo bundle exec cap production deploy"

          sleep 20
        }
      }
    }
  }
)
