### Specification stream cipher

* write a Java program HC1 („HAW-Chiffre 1“) which decrypts/ encrypts a file
* use your LCG class from part 1
* use input/ output streams instead of "buffered reader" or "buffered writer" classes

#### Params
1. Numeric key (start value)
2. Path of input file
3. Path of output file

#### Procedure
* reads each byte of an input file
* concatenates it via XOR with a key byte
* first key is generated with start value
* following keys generated from predecessor key
* resulting bytes are written into cipher file

### Tasks

* test stream cipher by
  * encrypting a plaintext file
  * decrypting the encrypted file by calling HC1 again
  * check with `diff` of the plaintext and decrypted file if both really have the same content
