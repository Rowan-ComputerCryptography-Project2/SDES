package project2.sdes;

import java.util.Scanner;

/** Test Driver for Simple DES program.
 * @author James A. Donnell Jr.
 * @author Danielle Lopez
 * @author Spencer Escalante */
public class TestDriver {

	public static void main(String[] args) {
		String plain = "Hello world!";
		
		// Key as string
		System.out.println("Example 1:");
		String tenBitKey = "1111100001";
		SDES sdes = new SDES(tenBitKey);
		display(sdes, plain);
		
		// Key as input
		System.out.println("Example 2:");
		sdes = new SDES(new Scanner(System.in));
		display(sdes, plain);
	}
	
	private static void display(SDES sdes, String plaintext) {
		byte[] encrypted = sdes.encrypt(plaintext);
		byte[] decrypted = sdes.decrypt(encrypted);
		
		sdes.show(encrypted);
		sdes.show(decrypted);
		System.out.println("Plain Text:\t" + plaintext);
		System.out.println("Encrypted Text:\t" + sdes.byteArrayToString(encrypted));
		System.out.println("Decrypted Text:\t" + sdes.byteArrayToString(decrypted) + "\n");
	}
}