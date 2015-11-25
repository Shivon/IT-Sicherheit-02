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

	// Constructor with following parameter; FIlename einer zu
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
		int len = (int) schluesselDatei.length();
		byte[] des1 = new byte[8];
		byte[] des2 = new byte[8];
		byte[] des3 = new byte[8];
		in.read(des1);
		desFirst = new DES(des1);
		for (int i = 0; i < des1.length; i++)
			System.out.print(des1[i]);
		in.read(des2);
		desSecond = new DES(des2);
		for (int i = 0; i < des2.length; i++)
			System.out.print(des2[i]);
		in.read(des3);
		desThird = new DES(des3);
		System.out.println("          des3");
		for (int i = 0; i < des3.length; i++)
			System.out.print(des3[i]);
		in.read(initVector);
		System.out.println("           iv:");
		for (int i = 0; i < initVector.length; i++)
			System.out.print(initVector[i]);
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
		//System.out.println("In decrypt");
		InputStream inputstream = new FileInputStream(this.verschluesselteDatei);
		OutputStream outputstream = new FileOutputStream(entschluesselteDatei);
		byte[] buffer = new byte[8];
		byte[] encryptBuffer = new byte[8];
		byte[] decryptBuffer = new byte[8];
		byte[] plainText = new byte[8];
		//System.out.println("iv "+initVector);
		desFirst.encrypt(initVector, 0, encryptBuffer, 0);
		desSecond.decrypt(encryptBuffer, 0, encryptBuffer, 0);
		desThird.encrypt(encryptBuffer, 0, encryptBuffer, 0);
		//System.out.println("eB "+encryptBuffer);
		while (inputstream.read(plainText) > 0) {
			plainText = xor(encryptBuffer, plainText);
			desFirst.encrypt(plainText, 0, encryptBuffer, 0);
			desSecond.decrypt(encryptBuffer, 0, encryptBuffer, 0);
			desThird.encrypt(encryptBuffer, 0, encryptBuffer, 0);
			outputstream.write(plainText);
		}
		inputstream.close();
		outputstream.close();
	}

	public static void main(String[] args) throws IOException {
		// Parameter einlesen, Objekt erstellen
		File input = new File(args[1]);
		File inputKey = new File(args[2]);
		File output = new File(args[3]);
		output.setWritable(true);
		TripleDES tripleDes = new TripleDES(input, output, inputKey);
		String status = args[0];
		// gewünschte Aktion abfragen
		if (status.equals("encrypt")) {
			System.out.println("kkkkkkk");
			tripleDes.encrypt();
		} else if (status.equals("decrypt")) {
			//System.out.println("kkk");
			tripleDes.decrypt();
		}

		else {
			System.out.println("mlo");

		}
	}

}
