pipeline {
    agent any

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    parameters {
        choice(
            name: 'ENV',
            choices: ['qa', 'staging', 'mock-server'],
            description: 'Target API environment'
        )
        choice(
            name: 'SUITE',
            choices: ['regression', 'smoke', 'store'],
            description: 'TestNG suite to execute'
        )
    }

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    environment {
        SUITE_FILE = "testng-${params.SUITE}.xml"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    echo "Running suite [${env.SUITE_FILE}] on environment [${params.ENV}]"
                }
                sh "mvn clean test -Denv=${params.ENV} -DsuiteXmlFile=${env.SUITE_FILE}"
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'Reports/*.html, target/surefire-reports/**', allowEmptyArchive: true
        }
        success {
            echo 'All tests passed — build successful.'
        }
        failure {
            echo 'Tests failed — build marked UNSTABLE/FAILED.'
            error('Pipeline failed due to test failures.')
        }
    }
}
