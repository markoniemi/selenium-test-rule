node {
  sh 'env > env.txt'
  def mvnHome = tool 'Maven 3.3'
  stage ('Checkout') {
    git credentialsId: '73534043-e92f-42d2-b0a3-c954b09ebd49', url: 'https://github.com/markoniemi/selenium-test-rule.git'
  }
  stage ('Build') {
    env.JAVA_HOME="${tool 'JDK 1.8'}"
    //env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
    sh 'java -version'   
    sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore clean package -DskipTests=true"
  }
  stage ('Test') {
    sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore package"
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
  }
  stage ('Integration test') {
    sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore install"
    step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
  }
  stage ('Site') {
    sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore -DskipTests=true site"
  }
  stage ('Sonar') {
    if (isTimeBetween(13,19)){
      sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore sonar:sonar -DskipTests=true -Dsonar.host.url=${env.SONAR_URL}"
    } else {
        println "Skipping Sonar"
    }
  }
}

boolean isTimeBetween(startHour, endHour){
    def currentTime = new java.util.Date();
    return currentTime.getHours() >= 13 && currentTime.getHours() <= 19
}