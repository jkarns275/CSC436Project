#!/bin/bash
javac -d bin -cp GoPiGoLib.jar src/edu/oswego/csc436/data/*.java src/edu/oswego/csc436/states/*.java src/edu/oswego/csc436/*.java
cd bin
jar cmvf META-INF/MANIFEST.MF ../out.jar edu/oswego/csc436/*.class edu/oswego/csc436/data/*.class edu/oswego/csc436/states/*.class
