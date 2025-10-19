pipeline {
  agent any

  environment {
    commit_hash = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
  }

  stages {
    stage('build') {
      steps {
        sh './gradle front-ui:clean front-ui:build'
        sh './gradle accounts:clean accounts:build'
        sh './gradle blocker:clean blocker:build'
        sh './gradle cash:clean cash:build'
        sh './gradle exchange:clean exchange:build'
        sh './gradle exchange-generator:clean exchange-generator:build'
        sh './gradle notifications:clean notifications:build'
        sh './gradle transfer:clean transfer:build'
      }
    }

    stage('build') {
      steps {
        sh '''
          eval $(minikube docker-env)

          docker build --no-cache -t accounts:latest -f accounts/Dockerfile .
          docker build --no-cache -t blocker:latest -f blocker/Dockerfile .
          docker build --no-cache -t cash:latest -f cash/Dockerfile .
          docker build --no-cache -t exchange:latest -f /exchange/Dockerfile .
          docker build --no-cache -t exchange-generator:latest -f exchange-generator/Dockerfile .
          docker build --no-cache -t front-ui:latest -f front-ui/Dockerfile .
          docker build --no-cache -t notifications:latest -f notifications/Dockerfile .
          docker build --no-cache -t transfer:latest -f transfer/Dockerfile .
        '''
      }
    }

    stage('deploy') {
      steps {
        sh 'kubectl delete deployment bank-practicum --ignore-not-found'
        sh 'kubectl create deployment bank-practicum'
      }
    }
  }
}