# IT-Sicherheit-02
Pseudo number generator, stream cipher and tripleDES


### Pseudo number generator

* __Usage__ via terminal
* Go into `/pseudo_number_generator/src` and compile LCG.java
* You have __2 ways of executing__ the pseudo number generator:
  * type in terminal `java LCG <number>`, e.g. `java LCG 8`
    * this will call the generator with the start value "8"
  * type in terminal `java LCG`
    * this will call the generator with a randomly generated BigInteger as start value
* For __further notes__ see notes.md in the folder pseudo_number_generator


### Stream cipher

* __Usage__ via terminal
* Go into `/stream_cipher/src` and compile all java files (`javac *.java`)
* Execute stream cipher by typing in terminal `java HC1 <number> <input path> <output path>`, e.g. `java HC1 9630 ../inputFile1.txt ../outputFile1.txt`
  * this will call the stream cipher with the keyStartValue "9630", the reading file inputFile1.txt and the writing file outputFile1.txt
* For __further notes__ see notes.md in the folder stream_cipher
