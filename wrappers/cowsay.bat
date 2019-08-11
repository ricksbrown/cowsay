@ECHO OFF
REM This can be used to run java cowsay as a command line utility on Windows systems.
REM Downloaded the cowsay jar.
REM Put this wrapper script in the same directory.
REM Make this wrapper script executable: chmod +x cowsay
REM Ensure the directory that contains this wrapper script is in your Path.

java -jar %~dp0\cowsay-1.0.4.jar %*
