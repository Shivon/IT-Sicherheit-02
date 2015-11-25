### Specification stream cipher

* Write a Java program HC ()
* Use your LCG class from part 1 and write
Schreiben Sie unter Verwendung der LCG – Klasse aus Teil 1 ein JAVA-Programm HC1 („HAW-
Chiffre 1“), welches als Eingabeparameter von der Standardeingabe einen numerischen Schlüssel
(Startwert) sowie den Pfad für eine zu verschlüsselnde / entschlüsselnde Datei erhält.
Ihr Programm soll jedes Byte der Datei mit einem – ausgehend vom übergebenen Startwert – „zufäl-
lig“ erzeugten Schlüsselbyte mittels XOR verknüpfen und das Ergebnis in eine neue Chiffredatei aus-
geben.

### Tasks

* Testen Sie Ihre Stromchiffre HC1, indem Sie eine Klartextdatei verschlüsseln und die erzeugte
Chiffredatei anschließend durch einen nochmaligen Aufruf von HC1 wieder entschlüsseln.
Verifizieren Sie (z.B. mittels „diff“), dass beide Dateien identische Inhalte besitzen.
Arbeiten Sie mit Input/Outputstreams und vermeiden Sie die Verwendung von „Buffered Reader“
oder „Buffered Writer“ – Klassen!
