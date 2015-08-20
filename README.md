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
# Internationalization
This version of cowsay fully supports i18n.

New and/or improved translations welcome - the default (English) messages can be found in the file `src/main/resources/MessagesBundle.properties`

# Usage
All the ways of using it support configuration consistent with the commandline flags of the original application.
Documentation can be found in various man pages on the web.

## Additional flags not found in the original cowsay
* `--html` HTML output mode, produces accessible HTML5
* `--alt <arg>` Choose your own alt text for HTML output mode
* `--lang <arg>` Switch language

## As a Java library
Use the main methods `Cowsay.say` and `Cowsay.think`.

Example:

```java
String[] args = new String[]{"-f", "tux", "Moo!"};
String result = Cowsay.say(args);
```

## As an executable jar

Example:

```
java -jar cowsay.jar "Moo!"
java -jar cowsay.jar -f tux "Moo!"
java -jar cowsay.jar --cowthink "Moo!"
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
<cowsay message="Moo!" eyes="Oo" tongue=" U"/>
```

## As a Maven plugin

Example:

```xml
<plugin>
	<groupId>com.github.ricksbrown</groupId>
	<artifactId>cowsay</artifactId>
	<version>1.0.1</version>
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


# Building
1. `git clone --recursive https://github.com/ricksbrown/cowsay.git`
2. `cd cowsay`
3. `mvn install`

# Contributing
Pull requests welcome (as long as they incorporate a "moo" somewhere)!
