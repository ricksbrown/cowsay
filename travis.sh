#!/bin/bash

mvn -P coverage clean org.jacoco:jacoco-maven-plugin:prepare-agent verify sonar:sonar -Dsonar.projectKey=ricksbrown_cowsay -Dsonar.sources=. -Dsonar.inclusions=src/main/**
