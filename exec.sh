#!/bin/bash
clear
javadoc -d doc/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls src/*java
javac -d bin/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls src/*java
java -cp bin/:img/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls Pendu
