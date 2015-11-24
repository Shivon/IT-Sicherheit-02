import java.io.*;
import java.math.BigInteger;

public class HC1 {

//    Schreiben Sie unter Verwendung der LCG – Klasse aus Teil 1 ein JAVA-Programm HC1 („HAW-Chiffre 1“),
//    welches als Eingabeparameter von der Standardeingabe einen numerischen Schlüssel
//    (Startwert) sowie den Pfad für eine zu verschlüsselnde / entschlüsselnde Datei erhält.

//    Ihr Programm soll jedes Byte der Datei mit einem – ausgehend vom übergebenen Startwert – „zufäl-
//    lig“ erzeugten Schlüsselbyte mittels XOR verknüpfen und das Ergebnis in eine neue Chiffredatei aus-
//    geben.
    private final LCG key;
    private final String inputPath;

    public HC1(long startValueKey, String inputPath) {
        this.key = new LCG(BigInteger.valueOf(startValueKey));
        this.inputPath = inputPath;
    }

    public void crypt(String outputPath) throws IOException {
        FileInputStream inputStream = new FileInputStream(inputPath);
        FileOutputStream outputStream = new FileOutputStream(outputPath);

        // TODO: Re-check
        byte[] buffer = new byte[8];
        int readInput;
        while ((readInput = inputStream.read(buffer)) > 0) {
            BigInteger nextRandomKey = this.key.nextValue();

            for (int i = 0; i < readInput; i++) {
                buffer[i] = (byte) (buffer[i] ^ (byte)(nextRandomKey.longValue() >> 8 * i));
            }

            outputStream.write(buffer, 0, readInput);
        }
        inputStream.close();
        outputStream.close();
    }

    public static void main(String[] args) throws IOException {
        long startValue;
        String inputPath;
        String outputPath;

        if (args.length == 3) {
            startValue = HC1.getLong(args[0]);
            if (startValue == (long) -1) {
                return;
            }
            inputPath = args[1];
            outputPath = args[2];

            System.out.println("number: " + startValue);
            System.out.println("inputPath: " + inputPath);
            System.out.println("outputPath: " + outputPath);

            HC1 hc1 = new HC1(startValue, inputPath);
            hc1.crypt(outputPath);
        } else {
            System.out.println("Please enter number, path of input file and path of output file.");
        }
    }

    /*
     * HELPER METHOD
     */

    private static long getLong(String longString) {
        try {
            return Long.parseLong(longString);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number as first parameter.");
            return -1;
        }
    }
}
