pipeline{
 
	agent {
		label 'Slave_Induccion'
	}
		
	options {
		buildDiscarder(logRotator(numToKeepStr:'3'))
		disableConcurrentBuilds()
	}
	tools{
		jdk 'JDK8_Centos'
		gradle 'Gradle4.5_Centos' 
	}
	   
	

	stages {
		
		
		stage('Checkout'){
			steps{
				echo "------------>Checkout<------------"
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], gitTool: 'Git_Centos', submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GitHub_andersonsantamaria', url: 'https://github.com/andersonsantamaria/Ceiba-Estacionamiento.git']]])
			}
		}
	  
		stage('Unit Tests') {
			steps {
				echo "------------>Unit Tests<------------"
				sh 'gradle --b ./build.gradle test'
			}
		}
		
		stage('Static Code Analysis') {
			steps{
				echo '------------>Análisis de código estático<------------'
				withSonarQubeEnv('Sonar') {
					sh "${tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"
				}
			}
		}
		
		stage('Build') { 
			steps {
				echo "------------>Build<------------"
				sh 'gradle --b ./build.gradle build -x test'				
			}
		}
		
	}

	post {
		echo 'This will run only if failed'
		failure { 
			mail( to: 'anderson.santamaria@ceiba.com.co' ,
				body: "Build failed in Jenkins: Project: ${env.JOB_NAME} Build /n Number: ${env.BUILD_NUMBER} URL de build: ${env.BUILD_NUMBER}/n/n Please go to ${env.BUILD_URL} and verify the build", 
				subject: "ERROR CI: Project name → ${env.JOB_NAME}")
		}
		
		success {
			echo 'This will run only if successful'
			junit '**/build/test-results/test/*.xml'
		}
	}
}  