[![CircleCI](https://circleci.com/gh/markoniemi/selenium-test-rule.svg?style=svg)](https://circleci.com/gh/markoniemi/selenium-test-rule)
[![Build Status](https://travis-ci.org/markoniemi/selenium-test-rule.svg?branch=master)](https://travis-ci.org/markoniemi/selenium-test-rule)
[![SonarCloud](https://sonarcloud.io/api/badges/gate?key=org.markoniemi:selenium-test-rule)](https://sonarcloud.io/dashboard?id=org.markoniemi:selenium-test-rule)
[![SonarCloud](https://sonarcloud.io/api/badges/measure?key=org.markoniemi:selenium-test-rule&metric=coverage)](https://sonarcloud.io/dashboard?id=org.markoniemi:selenium-test-rule)

When using SeleniumTestRule, do not close or quit webDriver in order to get screenshots of failed cases. 
When using WebDriverAnnotations.initializeWebDriver, you must quit webDriver to prevent hanging browser processes.

Version update
-
mvn --batch-mode release:prepare release:perform
All Travis builds will deploy to PackageCloud.


Deploy to bintray
-
mvn deploy -DskipTests=true -DBINTRAY_API_KEY=api_key --settings settings.xml
