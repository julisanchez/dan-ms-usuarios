#!/usr/bin/env groovy

pipeline {
    agent any
    stages {
        stage('clean') {
            steps {
                sh "java -version"
                sh "./mvnw clean"
            }
        }
        stage('backend tests') {
            steps {
                //bat "./mvnw test"
                sh "./mvnw verify"
            }
        }
        stage('Analisis estatico') {
            steps {
                sh "./mvnw site"
                sh "./mvnw checkstyle:checkstyle pmd:pmd pmd:cpd findbugs:findbugs spotbugs:spotbugs"
            }
        }
        stage('Install - Master') {
            steps {
                sh "./mvnw clean install site -DskipTests"
                sh "./mvnw pmd:pmd"
                sh "./mvnw pmd:cpd"
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                archiveArtifacts artifacts: '**/target/site/**'
            }
        }
        stage('reportes') {
            steps {
                publishHTML([allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site',
                    reportFiles: 'index.html',
                    reportName: 'Site'
                ])
            }
        }
    }
    post {
        always{
            archiveArtifacts artifacts: '**/target/site/**', fingerprint: true
            publishHTML([allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'target/site',
            reportFiles: 'index.html',
            reportName: 'Site'
            ])
            junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
            jacoco ( execPattern: 'target/jacoco.exec')
            recordIssues enabledForFailure: true, tools: [mavenConsole(), java(), javaDoc()]
            recordIssues enabledForFailure: true, tools: [checkStyle()]
            recordIssues enabledForFailure: true, tools: [spotBugs()]
            recordIssues enabledForFailure: true, tools: [cpd(pattern: '**/target/cpd.xml')]
            recordIssues enabledForFailure: true, tools: [pmdParser(pattern: '**/target/pmd.xml')]
        }
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
}
