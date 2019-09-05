# Cowjar

This is the default set of cowfiles that ships with cowsay.

This cowjar packages cowfiles from [https://github.com/schacon/cowsay](https://github.com/schacon/cowsay).

A few potentially offensive ones are excluded but can be added using either [COWPATH](https://linux.die.net/man/1/cowsay) environment variable or [cowjar-off](../cowjar-off).

## What is a Cowjar

Cowjars are simply a jar file containing a `cows` directory with cowfiles that can be placed on the classpath, for example using Maven:

```xml
<dependency>
	<groupId>com.github.ricksbrown</groupId>
	<artifactId>cowjar-extra</artifactId>
	<version>1.1.0</version>
</dependency>
```

Java cowsay will find them there and use them in commands like `cowsay -list` and `cowsay -f cheese`.

A few are provided but you can build your own.

## Cowjar Sources

The actual cowfiles are fetched at build time which is why you do not find most/any cowfiles in the source.
