#!/bin/bash

javac -d target src/edu/school21/printer/*/*.java
cp -R src/resources target/.
jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target/ .
chmod u+x target/images-to-chars-printer.jar
java -jar target/images-to-chars-printer.jar