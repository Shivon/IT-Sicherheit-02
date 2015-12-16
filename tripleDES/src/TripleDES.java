//package tripleDES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class TripleDES {
    private File encryptedFile;
    private File decryptedFile;
    private File keyFile;
    private DES desFirst;
    private DES desSecond;
    private DES desThird;
    private byte[] initVector = new byte[8];
    private boolean encrypt;


    // params: encrypted file, file to save decrypted to, file with the key data
    public TripleDES(File encryptedFile, File decryptedFile, File keyFile) throws IOException {
        this.encryptedFile = encryptedFile;
        this.decryptedFile = decryptedFile;
        this.keyFile = keyFile;
        initKeysVector();
    }


    // splitting the key data file in 3 DES and initial vector
    public void initKeysVector() throws IOException {
        FileInputStream inputStream = new FileInputStream(keyFile);
        byte[] des1 = new byte[8];
        byte[] des2 = new byte[8];
        byte[] des3 = new byte[8];

        inputStream.read(des1);
        desFirst = new DES(des1);

        inputStream.read(des2);
        desSecond = new DES(des2);

        inputStream.read(des3);
        desThird = new DES(des3);
        
        inputStream.read(initVector);
        inputStream.close();
    }

    
    public void encryptDecrypt() throws IOException {
        InputStream inputstream = new FileInputStream(encryptedFile);
        OutputStream outputstream = new FileOutputStream(decryptedFile);

        byte[] firstDESBuffer = new byte[8];
        byte[] secondDESBuffer = new byte[8];
        byte[] thirdDESBuffer = new byte[8];

        if (encrypt) {
            secondDESBuffer = getModifiedOutputBuffer(initVector, secondDESBuffer);

            int length;
            while ((length = inputstream.read(firstDESBuffer)) > 0) {
                thirdDESBuffer = xor(secondDESBuffer, firstDESBuffer);
                outputstream.write(thirdDESBuffer, 0, length);
                secondDESBuffer = getModifiedOutputBuffer(thirdDESBuffer, secondDESBuffer);
            }
        } else {
            firstDESBuffer = getModifiedOutputBuffer(initVector, firstDESBuffer);

            while (inputstream.read(thirdDESBuffer) > 0) {
                secondDESBuffer = xor(firstDESBuffer, thirdDESBuffer);
                firstDESBuffer = getModifiedOutputBuffer(thirdDESBuffer, firstDESBuffer);
                outputstream.write(secondDESBuffer);
            }
        }

        inputstream.close();
        outputstream.close();
    }

    // returns modified output buffer
    private byte[] getModifiedOutputBuffer(byte[] inputBuffer, byte[] outputBuffer) {
        desFirst.encrypt(inputBuffer, 0, outputBuffer, 0);
        desSecond.decrypt(outputBuffer, 0, outputBuffer, 0);
        desThird.encrypt(outputBuffer, 0, outputBuffer, 0);

        return outputBuffer;
    }

    // xor encrypted text with the next 8 bytes
    public byte[] xor(byte[] encryptedText, byte[] plainText) {
        byte[] chiffre = new byte[8];
        for (int i = 0; i < 8; i++) {
            chiffre[i] = (byte) (encryptedText[i] ^ plainText[i]);
        }
        return chiffre;
    }


    /*
     * params to start the application:
     * 1. input file to decrypt/ encrypt
     * 2. key file
     * 3. output file for decrypted/ encrypted data
     * 4. status (decrypt/ encrypt)
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.out.println("Usage: java TripleDES inputFile keyFile outputFile status(choose: decrypt/ encrypt)");
            return;
        }

        File inputFile = new File(args[0]);
        File keyFile = new File(args[1]);
        File outputFile = new File(args[2]);
        String status = args[3];

        TripleDES tripleDes = new TripleDES(inputFile, outputFile, keyFile);

        // determine if encrypt or decrypt
        if (status.equals("encrypt")) {
            tripleDes.encrypt = true;
        } else if (status.equals("decrypt")) {
            tripleDes.encrypt = false;
        } else {
            System.out.println("Please choose status: decrypt or encrypt");
            return;
        }

        tripleDes.encryptDecrypt();
    }
}