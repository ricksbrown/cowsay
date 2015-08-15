# Cowsay
Java port of the original cowsay by Tony Monroe.
Moo!

```
 ______
< Moo! >
 ------
        \   ^__^
         \  (oo)\_______
            (__)\       )\/\
                ||----w |
                ||     ||
```

# Usage
All the ways of using it support configuration consistent with the commandline flags of the original application.
Documentation can be found in various man pages on the web.

## As a Java library
Use the main methods `Cowsay.say` and `Cowsay.think`.

Example:

```java
String[] args = new String[]{"Moo!", "-f", "tux"};
String result = Cowsay.say(args);
```

## As an executable jar

Example:

```
java -jar cowsay.jar "Moo!"
java -jar cowsay.jar -f tux "Moo!"
java -jar cowsay.jar "Moo!" --cowthink
```

## As an Ant task

Example:

```xml
<taskdef name="cowsay"
			classname="com.github.ricksbrown.cowsay.ant.CowsayTask"
			classpath="cowsay.jar"/>

<cowsay message="Moo!"/>
<cowsay message="Moo!" think="true"/><!-- cowthink -->
<cowsay message="Moo!" mode="b"/><!-- Borg mode -->
<cowsay message="Long live linux!" cowfile="tux"/>
```

## As a Maven plugin
TODO: get this into maven central - the world needs it!

Example:

```xml
<plugin>
	<groupId>com.github.ricksbrown</groupId>
	<artifactId>Cowsay</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<executions>
		<execution>
			<id>say-moo</id>
			<phase>compile</phase>
			<goals>
				<goal>moo</goal>
			</goals>
			<configuration>
				<message>Moo!</message>
				<think>true</think><!-- cowthink -->
				<mode>b</mode><!-- Borg mode -->
			</configuration>
		</execution>
	</executions>
</plugin>
```

# Building
1. `git clone --recursive https://github.com/RickSBrown/cowsay.git`
2. `cd cowsay`
3. `mvn install`

# Contributing
Pull requests welcome (as long as they incorporate a "moo" somewhere)!

This project will no doubt be incorporated into many important applications and critical (e.g. life support) systems.

For this reason all contributions should include unit tests.
