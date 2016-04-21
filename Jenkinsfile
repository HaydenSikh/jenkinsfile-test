stage name: 'Build'

node {
  checkout([
    $class: 'GitSCM',
    branches: [[name: '*/master']],
    doGenerateSubmoduleConfigurations: false,
    extensions: [
      [
        $class: 'UserExclusion',
        excludedUsers: 'jenkins'
      ],
      [
        $class: 'PathRestriction',
        excludedRegions: 'version.sbt',
        includedRegions: ''
      ],
      [$class: 'PruneStaleBranch']
    ],
    submoduleCfg: [],
    userRemoteConfigs: [[url: 'git@github.com:HaydenSikh/jenkinsfile-test']]
  ])

  sh './sbt clean package'
}

stage name: 'Analyze and Deploy'

parallel (
  staticAnalysis : node {
    // build job: 'jenkinsfile-test_analysis', wait: false
    sh './sbt scalastyle clean coverage test coverageReport'

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
