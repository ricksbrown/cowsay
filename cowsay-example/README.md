# Cowsay Example App

This demonstrates how software developers can use java cowsay.

## Maven

Run `mvn compile` to see the plugin output.

Take a look at the `plugins` section of the [pom.xml](pom.xml) to see how it's done.

## Java

Read the [source code](src/main/java/Moo.java) for examples.

Run the java main method once you have compiled it to see the java library output.

Take a look at the `dependencies` section of the [pom.xml](pom.xml) to see how it's done.

## Ant

Run `ant -Dcowjarpath="/path/to/cowsay.jar" build`
or if you have built the cowsay project just pass the version you have built: `ant -Dcowsay.version="1.1.0-SNAPSHOT" build`

If you want to use the excluded/additional cows:

Run `ant -Dcowjarpath="/path/to/cowjar-off.jar:/path/to/cowsay.jar" build`

Take a look at [build.xml](build.xml) to see how it's done.
