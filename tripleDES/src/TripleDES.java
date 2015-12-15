package tripleDES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TripleDES {
    // TODO: refactor and debug
    private File verschluesselteDatei;
    private File entschluesselteDatei;
    private File schluesselDatei;
    private DES desFirst;
    private DES desSecond;
    private DES desThird;
    private byte[] initVector = new byte[8];

    // parameters: encrypted file, file to save the decrypted file, file with
    // the keydata
    public TripleDES(File verschluesselteDatei, File entschluesselteDatei,
            File schluesselDatei) {
        this.verschluesselteDatei = verschluesselteDatei;
        this.entschluesselteDatei = entschluesselteDatei;
        this.schluesselDatei = schluesselDatei;
        try {
            initKeysVector();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // splitting the keydata file in 3 DES and initial vector
    public void initKeysVector() throws IOException {
        FileInputStream in = new FileInputStream(schluesselDatei);
        byte[] des1 = new byte[8];
        byte[] des2 = new byte[8];
        byte[] des3 = new byte[8];
        in.read(des1);
        desFirst = new DES(des1);
        in.read(des2);
        desSecond = new DES(des2);
        in.read(des3);
        desThird = new DES(des3);
        in.read(initVector);
        in.close();
    }

    
    
    public void encryptDecrypt(Boolean isEncrypt) throws IOException{
        InputStream inputstream = new FileInputStream(this.verschluesselteDatei);
        OutputStream outputstream = new FileOutputStream(entschluesselteDatei);
        byte[] encryptBuffer = new byte[8];
        byte[] decryptBuffer = new byte[8];
        byte[] plainText = new byte[8];
        desFirst.encrypt(initVector, 0, encryptBuffer, 0);
        desSecond.decrypt(encryptBuffer, 0, encryptBuffer, 0);
        desThird.encrypt(encryptBuffer, 0, encryptBuffer, 0);
        int length;
        while ((length = inputstream.read(plainText)) > 0){
    
            decryptBuffer = xor(encryptBuffer, plainText);
        
            if (!isEncrypt)
                outputstream.write(decryptBuffer, 0, length);
        
            desFirst.encrypt(plainText, 0, encryptBuffer, 0);
            desSecond.decrypt(encryptBuffer, 0, encryptBuffer, 0);
            desThird.encrypt(encryptBuffer, 0, encryptBuffer, 0);
            if (!isEncrypt)
            outputstream.write(decryptBuffer);
        }
        inputstream.close();
        outputstream.close();
    }
        
        

    // xor encrypted text with the next 8 bytes
    public byte[] xor(byte[] encryptedText, byte[] plainText) {
        byte[] chiffre = new byte[8];
        for (int i = 0; i < 8; i++) {
            chiffre[i] = (byte) (encryptedText[i] ^ plainText[i]);
        }
        return chiffre;
    }


    public static void main(String[] args) throws IOException {
        // parameters to start the application:
        // decrypt/encrypt, , ,
        String status = args[0];
        File input = new File(args[1]);
        File inputKey = new File(args[2]);
        File output = new File(args[3]);
        TripleDES tripleDes = new TripleDES(input, output, inputKey);
        // gewünschte Aktion abfragen
        if (status.equals("encrypt")) {
            tripleDes.encryptDecrypt(true);
            } else if (status.equals("decrypt")) {
            tripleDes.encryptDecrypt(false);
            } else {
            System.out
                    .println("Bitte eine gewünschte Aktion aussuchen(decrypt/encrypt)");
        }
    }
}
© 2015 Microsoft Nutzungsbedingungen Datenschutz und Cookies Impressum Entwickler Deutsch
