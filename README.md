[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c2ae0ae0cbc24295975e1f75bd111662)](https://www.codacy.com/app/ricksbrown/cowsay?utm_source=github.com&utm_medium=referral&utm_content=ricksbrown/cowsay&utm_campaign=badger)
[![Build Status](https://travis-ci.org/ricksbrown/cowsay.svg?branch=master)](https://travis-ci.org/ricksbrown/cowsay)
[![Javadocs](https://javadoc.io/badge/com.github.ricksbrown/cowsay.svg)](https://javadoc.io/doc/com.github.ricksbrown/cowsay)

# Cowsay
Java port of the original cowsay by Tony Monroe.
Moo!

```
 ______________________________
< Available on Maven Central! >
 ------------------------------
        \   ^__^
         \  (oo)\_______
            (__)\       )\/\
                ||----w |
                ||     ||
```

New in 1.0.4: you can now pipe input into cowsay, e.g. `ls | cowsay`

# Download

The JAR available on Maven Central is _The One JAR_ and can be used in any of the advertised ways:
 * Maven Plugin
 * Java library on the classpath
 * Ant Task
 * Self Executing JAR

The last two scenarios in particular may require you to manually download _The One JAR_ from Maven.
It will be named following the convention `cowsay-{version}.jar`. 

For example if you want version `1.0.4` you would go [here](http://repo1.maven.org/maven2/com/github/ricksbrown/cowsay/1.0.4/) and download [cowsay-1.0.4.jar](http://repo1.maven.org/maven2/com/github/ricksbrown/cowsay/1.0.4/cowsay-1.0.4.jar).

>One JAR to rule the cows, 
One JAR to find them,
One JAR to bring them all, 
and in the pasture bind them

# Usage
All the ways of using it support configuration consistent with the commandline flags of the original application, including full support for `COWPATH` environment variable.

Documentation can be found in various man pages on the web.

## As a Command Line Utility
To make it work like any other cowsay 
you need to add some wrapper scripts to your [PATH](https://en.wikipedia.org/wiki/PATH_(variable)).

Basic Windows and *nix wrappers are provided for convenience here in the [wrappers](wrappers) directory.

Once installed you can execute cowsay and cowthink exactly like other versions:

```
cowsay Moo!
cowsay -f tux Moo!
cowthink Moo!

# Since 1.0.4 handles piped input
echo Moo! | cowsay
```

## As an executable jar

```
java -jar cowsay.jar "Moo!"
java -jar cowsay.jar -f tux "Moo!"
java -jar cowsay.jar --cowthink "Moo!"

# Since 1.0.4 handles piped input
echo Moo! | java -jar cowsay.jar
```

## As a Java library
Include it as a dependency in your project, for example in your Maven pom.xml:

```xml
<dependency>
	<groupId>com.github.ricksbrown</groupId>
	<artifactId>cowsay</artifactId>
	<version>1.0.4</version>
</dependency>
```

Then use the main methods [Cowsay.say](https://static.javadoc.io/com.github.ricksbrown/cowsay/1.0.4/index.html?com/github/ricksbrown/cowsay/Cowsay.html) and [Cowsay.think](https://static.javadoc.io/com.github.ricksbrown/cowsay/1.0.4/index.html?com/github/ricksbrown/cowsay/Cowsay.html).


```java
String[] args = new String[]{"-f", "tux", "Moo!"};
String result = Cowsay.say(args);
```

You may also use [CowExecutor](https://static.javadoc.io/com.github.ricksbrown/cowsay/1.0.4/index.html?com/github/ricksbrown/cowsay/plugin/CowExecutor.html) for more advanced scenarios.

Try running the example java project: [cowsay-example](cowsay-example)

## As an Ant task

```xml
<taskdef name="cowsay"
			classname="com.github.ricksbrown.cowsay.ant.CowsayTask"
			classpath="cowsay.jar"/>

<cowsay message="Moo!"/>
<cowsay message="Moo!" think="true"/><!-- cowthink -->
<cowsay message="Moo!" mode="b"/><!-- Borg mode -->
<cowsay message="Long live linux!" cowfile="tux"/>
<cowsay message="Moo!" eyes="Oo" tongue=" U"/>
```

Try running Ant on the example java project: [cowsay-example](cowsay-example)

## As a Maven plugin

```xml
<plugin>
	<groupId>com.github.ricksbrown</groupId>
	<artifactId>cowsay</artifactId>
	<version>1.0.4</version>
	<executions>
		<execution>
			<id>say-moo</id>
			<phase>compile</phase>
			<goals>
				<goal>moo</goal>
			</goals>
			<configuration>
				<message>Compiling awesome stuff...</message>
				<!-- Other options:
				<eyes>Oo</eyes>
				<cowfile>sheep</cowfile>
				<mode>b</mode>
				<think>true</think>
				<tongue> V</tongue>
				<wrap>60</wrap>
				-->
			</configuration>
		</execution>
	</executions>
</plugin>
```
```
 ____________________________
< Compiling awesome stuff... >
 ----------------------------
        \   ^__^
         \  (oo)\_______
            (__)\       )\/\
                ||----w |
                ||     ||
```

Try running maven on the example java project: [cowsay-example](cowsay-example)

## Additional flags not found in the original cowsay
* `--html` HTML output mode, produces accessible HTML5
* `--alt <arg>` Choose your own alt text for HTML output mode
* `--lang <arg>` Switch language

# Internationalization
In the interests of absurd over-engineering this version of cowsay fully supports i18n.

New and/or improved translations welcome - the default (English) messages can be found in the file `src/main/resources/MessagesBundle.properties`

# Building
1. `git clone --recursive https://github.com/ricksbrown/cowsay.git`
2. `cd cowsay`
3. `mvn install`
