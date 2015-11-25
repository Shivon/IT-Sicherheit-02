package tripleDES.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TripleDES {

	private File verschluesselteDatei;
	private File entschluesselteDatei;
	private File schluesselDatei;
	private DES desFirst;
	private DES desSecond;
	private DES desThird;
	private byte[] initVector = new byte[8];

	// Konstruktor mit folgenden Parametern; Dateiname einer zu
	// verschlüsselnden/entschlüsselnden Datei,
	// Dateiname einer Schlüssel-Datei
	// Belegung der Schlüssel + Initialiesierungs Vector
	public TripleDES(File verschluesselteDatei, File entschluesselteDatei, File schluesselDatei) {
		this.verschluesselteDatei = verschluesselteDatei;
		this.entschluesselteDatei = entschluesselteDatei;
		this.schluesselDatei = schluesselDatei;
		try {
			initKeysVector();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initKeysVector() throws IOException {
		FileInputStream in = new FileInputStream(schluesselDatei);
		byte[] des1 = new byte[8];
		byte[] des2 = new byte[8];
		byte[] des3 = new byte[8];
		in.read(des1);
		desFirst = new DES(des1);
		System.out.println();
		in.read(des2);
		desSecond = new DES(des2);
		in.read(des3);;
		desThird = new DES(des3);
		in.read(initVector);
		in.close();
	}

	// verschlüsseln
	public void encrypt() throws IOException {
		InputStream inputstream = new FileInputStream(verschluesselteDatei);
		OutputStream outputstream = new FileOutputStream(entschluesselteDatei);
		byte[] plaintext = new byte[8];
		byte[] encryptBuffer = new byte[8];
		byte[] decryptBuffer;
		// cfb 1.schritt
		desFirst.encrypt(initVector, 0, encryptBuffer, 0);
		desSecond.decrypt(encryptBuffer, 0, encryptBuffer, 0);
		desThird.encrypt(encryptBuffer, 0, encryptBuffer, 0);
		while (inputstream.read(plaintext) > 0) {
			// encrypted text xor mit nächsten 8 bytes
			decryptBuffer = xor(encryptBuffer, plaintext);
			outputstream.write(decryptBuffer);
			desFirst.encrypt(plaintext, 0, encryptBuffer, 0);
			desSecond.decrypt(encryptBuffer, 0, encryptBuffer, 0);
			desThird.encrypt(encryptBuffer, 0, encryptBuffer, 0);
			outputstream.write(plaintext);

		}
		inputstream.close();
		outputstream.close();

	}

	public byte[] xor(byte[] encryptedText, byte[] plainText) {
		byte[] chiffre = new byte[8];
		for (int i = 0; i < 8; i++) {
			chiffre[i] = (byte) (encryptedText[i] ^ plainText[i]);
		}
		return chiffre;
	}

	public void decrypt() throws IOException {
		InputStream inputstream = new FileInputStream(this.verschluesselteDatei);
		OutputStream outputstream = new FileOutputStream(entschluesselteDatei);
		byte[] encryptBuffer = new byte[8];
		byte[] decryptBuffer = new byte[8];
		byte[] plainText = new byte[8];
		desFirst.encrypt(initVector, 0, encryptBuffer, 0);
		desSecond.decrypt(encryptBuffer, 0, encryptBuffer, 0);
		desThird.encrypt(encryptBuffer, 0, encryptBuffer, 0);
		while (inputstream.read(plainText) > 0) {
			decryptBuffer = xor(encryptBuffer, plainText);
			desFirst.encrypt(plainText, 0, encryptBuffer, 0);
			desSecond.decrypt(encryptBuffer, 0, encryptBuffer, 0);
			desThird.encrypt(encryptBuffer, 0, encryptBuffer, 0);
			outputstream.write(decryptBuffer);
		}
		inputstream.close();
		outputstream.close();
	}

	public static void main(String[] args) throws IOException {
		// Parameter einlesen, Objekt erstellen
		String status = args[0];
		File input = new File(args[1]);
		File inputKey = new File(args[2]);
		File output = new File(args[3]);
		TripleDES tripleDes = new TripleDES(input, output, inputKey);
		// gewünschte Aktion abfragen
		if (status.equals("encrypt")) {
			tripleDes.encrypt();
		} else if (status.equals("decrypt")) {
			tripleDes.decrypt();
		}

		else {
			System.out.println("Bitte eine gewünschte Aktion aussuchen(decrypt/encrypt)");

		}
	}

}
