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
                sh "echo 'configurar para ejecutar los tests'"
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
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
}