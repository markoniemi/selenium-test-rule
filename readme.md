[![CircleCI](https://circleci.com/gh/markoniemi/selenium-test-rule.svg?style=svg)](https://circleci.com/gh/markoniemi/selenium-test-rule)
[![Build Status](https://travis-ci.org/markoniemi/selenium-test-rule.svg?branch=master)](https://travis-ci.org/markoniemi/selenium-test-rule)
[![SonarCloud](https://sonarcloud.io/api/badges/gate?key=org.selenium:selenium-test-rule)](https://sonarcloud.io/dashboard?id=org.selenium:selenium-test-rule)

Upload file to bintray
-
Add to settings.xml:

    <?xml version="1.0" encoding="UTF-8" ?>
    <settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd'
              xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
        <servers>
            <server>
                <id>bintray-markoniemi-maven-repo</id>
                <username>markoniemi</username>
                <password>{bintray-api-key}</password>
            </server>
        </servers>
        <profiles>
            <profile>
                <repositories>
                    <repository>
                        <snapshots>
                            <enabled>false</enabled>
                        </snapshots>
                        <id>bintray-markoniemi-maven-repo</id>
                        <name>bintray</name>
                        <url>https://dl.bintray.com/markoniemi/maven-repo</url>
                    </repository>
                </repositories>
                <pluginRepositories>
                    <pluginRepository>
                        <snapshots>
                            <enabled>false</enabled>
                        </snapshots>
                        <id>bintray-markoniemi-maven-repo</id>
                        <name>bintray-plugins</name>
                        <url>https://dl.bintray.com/markoniemi/maven-repo</url>
                    </pluginRepository>
                </pluginRepositories>
                <id>bintray</id>
            </profile>
        </profiles>
        <activeProfiles>
            <activeProfile>bintray</activeProfile>
        </activeProfiles>
    </settings>
