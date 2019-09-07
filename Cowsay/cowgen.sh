#!/bin/bash

# This script can be used to generate output for each cowfile in a given directory.
# This is useful for creating test resources.
# Call it like so: ./cowgen.sh /home/rick/projects/cowsay/cowsay/cows /home/rick/projects/cowsay/src/test/resources/cowgen/cowjar

COWSAY="/usr/games/cowsay"
INPUT=$1
OUTPUT=$2
if [ -f $COWSAY ]
then
	echo "Generating new cow output"
	mkdir -p $OUTPUT
	find $INPUT -maxdepth 1 -type f -name "*.cow"|while read fname; do
		$COWSAY -f $fname Moo > $OUTPUT/$(basename $fname).txt
	done
fi
