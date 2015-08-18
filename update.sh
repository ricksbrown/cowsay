#!/bin/bash
# This script is necessary for the maven-release-plugin since it does not handle git submodules
echo "Executing update.sh to fetch submodules"
cwd=$(pwd)
echo "In directory: $cwd"

git submodule update --init
git submodule foreach git submodule update --init