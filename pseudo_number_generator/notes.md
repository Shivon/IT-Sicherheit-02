### Specification pseudo number generator

* implement with Java
* define class `LCG` with method `nextValue()`
  * `nextValue()` shall deliver a "random number" by using linear congruential generator
  * start value of the pseudo number generator (X⁰) shall be passed as param to constructor of `LCG`-class

### Hints and tasks

* use combination if params for a, b and N from file "LinearerKongruenzgenerator-Infos.pdf"
* pay attention to using adequate data types (e.g. "long") for preventing possible overflow

### Further reading

* [Simple reading @Attraktor - German](http://attraktor.info/komplizierter-name-einfache-funktion-lineare-kongruenzgeneratoren/)

### Explanatory note

* We used SUN-UNIX drand48 for reference
  * The parameters within this reference are so big that even long is not enough and creates an overflow    
    -> we have to use BigInter

### Additional notes

* BigInteger is not as high-performing as long, take other standard and use long if performance is an issue (e.g. games)
