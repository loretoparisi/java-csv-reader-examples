#!/bin/bash

LIBS=lib/opencsv-3.9.jar:lib/commons-lang3-3.5.jar:lib/commons-csv-1.4.jar:

javac -cp $LIBS CSVReaderExample.java -Xlint:unchecked
java -cp $LIBS:./ CSVReaderExample $@
