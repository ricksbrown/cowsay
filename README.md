
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c2ae0ae0cbc24295975e1f75bd111662)](https://www.codacy.com/app/ricksbrown/cowsay?utm_source=github.com&utm_medium=referral&utm_content=ricksbrown/cowsay&utm_campaign=badger)
[![Build Status](https://travis-ci.org/ricksbrown/cowsay.svg?branch=master)](https://travis-ci.org/ricksbrown/cowsay)
[![Javadocs](https://javadoc.io/badge/com.github.ricksbrown/cowsay.svg)](https://javadoc.io/doc/com.github.ricksbrown/cowsay)

# Cowsay

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

## New Features and Changes

Detailed information can be found in the [CHANGELOG](CHANGELOG.md)

## Download

The Jar available on Maven Central is _The One Jar_ and can be used in any of the advertised ways:

* [Command Line Tool](#as-a-command-line-utility)
* [Executable Jar](#as-an-executable-jar)
* [Java library](#as-a-java-library)
* [Maven Plugin](#as-a-maven-plugin)
* [Ant Task](#as-an-ant-task)

The first two scenarios require you to manually download _The One Jar_ from Maven. It will be named following the convention `cowsay-{version}.jar`.

E.g. for version `1.1.0` you would go [here](http://repo1.maven.org/maven2/com/github/ricksbrown/cowsay/1.1.0/) and download [cowsay-1.1.0.jar](http://repo1.maven.org/maven2/com/github/ricksbrown/cowsay/1.1.0/cowsay-1.1.0.jar).

## Usage

All the ways of using it support configuration consistent with the command line flags of the original application, including full support for `COWPATH` environment variable.

Documentation can be found in various man pages on the web.

It also introduces [cowjars](cowjar) which is a way of adding extra cows to the Java classpath.

### As a Command Line Utility

#### CLI Installation

1. Download the executable jar as [described above](#download).
2. Download the [wrapper scripts](wrappers).
3. Update your PATH

#### CLI Usage

Once installed you can execute cowsay and cowthink from the command line like so:

```bash
# Simple cowsay CLI
cowsay -f stegosaurus Moo!

# Pipe to cowsay
echo "Piping to cowsay" | cowsay -f dragon
```

```
 __________________
< Piping to cowsay >
 ------------------
      \                    / \  //\
       \    |\___/|      /   \//  \\
            /0  0  \__  /    //  | \ \
           /     /  \/_/    //   |  \  \
           @_^_@'/   \/_   //    |   \   \
           //_^_/     \/_ //     |    \    \
        ( //) |        \///      |     \     \
      ( / /) _|_ /   )  //       |      \     _\
    ( // /) '/,_ _ _/  ( ; -.    |    _ _\.-~        .-~~~^-.
  (( / / )) ,-{        _      `-.|.-~-.           .~         `.
 (( // / ))  '/\      /                 ~-. _ .-~      .-~^-.  \
 (( /// ))      `.   {            }                   /      \  \
  (( / ))     .----~-.\        \-'                 .~         \  `. \^-.
             ///.----..>        \             _ -~             `.  ^-`  ^-_
               ///-._ _ _ _ _ _ _}^ - - - - ~                     ~-- ,.-~
                                                                  /.-~
```

### As an executable jar

Download the executable jar as [described above](#download) and run it like so:

```bash
# Same args as cowsay CLI
java -jar cowsay.jar -f tux "Moo!"

# Can invoke cowthink like so
java -jar cowsay.jar --cowthink "Moo!"

# Can pipe to it
echo Moo! | java -jar cowsay.jar
```

### As a Java library

Include it as a dependency in your project, for example in your Maven pom.xml:

```xml
<dependency>
	<groupId>com.github.ricksbrown</groupId>
	<artifactId>cowsay</artifactId>
	<version>1.1.0</version>
	<!-- The "lib" classifier is optional, but it gives you a MUCH smaller jar which is all you need as a Java library -->
	<classifier>lib</classifier>
</dependency>
```

Then use the main methods [Cowsay.say](https://static.javadoc.io/com.github.ricksbrown/cowsay/1.1.0/index.html?com/github/ricksbrown/cowsay/Cowsay.html) and [Cowsay.think](https://static.javadoc.io/com.github.ricksbrown/cowsay/1.1.0/index.html?com/github/ricksbrown/cowsay/Cowsay.html).

```java

String[] args = new String[]{"-f", "Stegosaurus", "Hello from Java!"};
String result = Cowsay.say(args);
System.out.println(result);
```

```
 __________________
< Hello from Java! >
 ------------------
\                             .       .
 \                           / `.   .' "
  \                  .---.  <    > <    >  .---.
   \                 |    \  \ - ~ ~ - /  /    |
         _____          ..-~             ~-..-~
        |     |   \~~~\.'                    `./~~~/
       ---------   \__/                        \__/
      .'  O    \     /               /       \  "
     (_____,    `._.'               |         }  \/~~~/
      `----.          /       }     |        /    \__/
            `-.      |       /      |       /      `. ,~~|
                ~-.__|      /_ - ~ ^|      /- _      `..-'
                     |     /        |     /     ~-.     `-. _  _  _
                     |_____|        |_____|         ~ - . _ _ _ _ _>
```

Or with [CowExecutor](https://static.javadoc.io/com.github.ricksbrown/cowsay/1.1.0/index.html?com/github/ricksbrown/cowsay/plugin/CowExecutor.html) like so:

```java
CowExecutor cowExecutor = new CowExecutor();
cowExecutor.setCowfile("Stegosaurus");
cowExecutor.setMessage("Hello from Java!");
String result = cowExecutor.execute();
System.out.println(result);
```

To add additional cows with [cowjars](cowjar):

```xml
<dependency>
	<groupId>com.github.ricksbrown</groupId>
	<artifactId>cowjar-extra</artifactId>
	<version>1.1.0</version>
</dependency>
```

For a working java example see [cowsay-example](cowsay-example)

### As a Maven plugin

```xml
<plugin>
	<groupId>com.github.ricksbrown</groupId>
	<artifactId>cowsay</artifactId>
	<version>1.1.0</version>
	<executions>
		<execution>
			<id>say-moo</id>
			<phase>compile</phase>
			<goals>
				<goal>moo</goal>
			</goals>
			<configuration>
				<message>Compiling with Maven...</message>
				<cowfile>daemon</cowfile>
				<!-- Other options:
				<eyes>Oo</eyes>
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
 _________________________
< Compiling with Maven... >
 -------------------------
   \         ,        ,
    \       /(        )`
     \      \ \___   / |
            /- _  `-/  '
           (/\/ \ \   /\
           / /   | `    \
           O O   ) /    |
           `-^--'`<     '
          (_.)  _  )   /
           `.___/`    /
             `-----' /
<----.     __ / __   \
<----|====O)))==) \) /====
<----'    `--' `.__,' \
             |        |
              \       /
        ______( (_  / \______
      ,'  ,-----'   |        \
      `--{__________)        \/
```

Try running maven on the example java project: [cowsay-example](cowsay-example)

### As an Ant task

```xml
<taskdef name="cowsay"
	classname="com.github.ricksbrown.cowsay.ant.CowsayTask"
	classpath="cowsay.jar"/>

<!--
<cowsay message="Moo!"/>
<cowsay message="Moo!" think="true"/>
<cowsay message="Moo!" mode="b"/>
<cowsay message="Moo!" eyes="Oo" tongue=" U"/>
-->

<cowsay message="The cool kids don't love Ant anymore" cowfile="tux"/>
```

```
 ______________________________________
< The cool kids don't love Ant anymore >
 --------------------------------------
   \
    \
        .--.
       |o_o |
       |:_/ |
      //   \ \
     (|     | )
    /'\_   _/`\
    \___)=(___/
```

Try running Ant on the example java project: [cowsay-example](cowsay-example)

## Adding more cows

As with original cowsay you can add cowfiles using the environment variable `COWPATH` and using the `-f` command line flag.

To facilitate adding cowfiles when used as a Maven plugin, Ant task or Java library you can use the provided [cowjars](cowjar) (or create your own).

The [cowsay-example](cowsay-example) project demonstrates how to use cowjars.

## Additional flags not found in the original cowsay

* `--html` HTML output mode, produces accessible HTML5
* `--alt <arg>` Choose your own alt text for HTML output mode
* `--lang <arg>` Switch language

## Internationalization

In the interests of absurd over-engineering this version of cowsay fully supports i18n.

New and/or improved translations welcome - the default (English) messages can be found in the file `src/main/resources/MessagesBundle.properties`

## Building

1. `git clone https://github.com/ricksbrown/cowsay.git`
2. `cd cowsay`
3. `mvn install`
