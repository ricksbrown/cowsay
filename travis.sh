#!/bin/bash

if [[ -n ${TRAVIS_SECURE_ENV_VARS+x} && ${TRAVIS_SECURE_ENV_VARS} == true && ! -z ${SONAR_TOKEN} ]]; then
	echo "Secure variables available"
	# the following command line builds the project, runs the tests with coverage and then execute the SonarCloud analysis
	mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.projectKey=ricksbrown_cowsay
else
	echo "Secure variables NOT available"
	mvn clean install
fi
