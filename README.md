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


### tripleDES

* __Usage__ via terminal
* Go into `/tripleDES/src` and compile all java files (`javac *.java`)
* Execute tripleDES by typing in terminal `java TripleDES <decrypt/encrypt> <input path> <key> <output path>`, e.g. `java TripleDES decrypt ../3DESTest.enc ../3DESTest.key ../3des.pdf`
  * this will call the tripleDES with the modus "decrypt", the reading file 3DESTest.enc, the key 3DESTest.key and the writing file 3des.pdf
