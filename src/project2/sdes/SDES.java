package project2.sdes;

import java.util.Arrays;
import java.util.Scanner;

/** Simple DES.
 * @author James A. Donnell Jr.
 * @author Danielle Lopez
 * @author Spencer Escalante */
public class SDES {

	/** 4-bit permutation. */
	private static int[] p4 = {1, 3, 2, 0};
	/** 4-bit Expansion permutation. */
	private static int[] expansionPermutation = {3, 0, 1, 2, 1, 2, 3, 0};
	/** 1st key permutation. */
	private static int[] k1 = {0, 6, 8, 3, 7, 2, 9, 5};
	/** 1st key permutation. */
	private static int[] k2 = {7, 2, 5, 4, 9, 1, 8, 0};
	/** Initial Permutation */
	private static int[] ip = {1, 5, 2, 0, 3, 7, 4, 6};
	/** Initial Permutation Inverse */
	private static int[] ipInv = {3, 0, 2, 4, 6, 1, 7, 5};
	
	/** Ten-bit key in a bit array. */
	private boolean[] key = new boolean[10];
	
	/** Constructor with 10-bit key given as a String.
	 * @param tenBitKey 10-bit key. */
	public SDES(String tenBitKey) {
		if (tenBitKey.length() != 10)
			throw new IllegalArgumentException("Input must be 10 digits.");
		
		String[] keyArray = tenBitKey.split("");
		for(int i = 0; i < keyArray.length; i++) {
			if (!(keyArray[i].equals("0") || keyArray[i].equals("1")))
				throw new IllegalArgumentException("Key must include only 0s and 1s");
			key[i] = stringToBoolean(keyArray[i]);
		}
	}
	
	/** Constructor with 10-bit key given as input.
	 * @param scanner Scanner for 10-bit key input. */
	public SDES(Scanner scanner) {
		this(getKey10(scanner));
	}

	/** Convert the given bit array to a single byte.
	 * @param inp A bit array, max length is 8 bits.
	 * @return byte */
	public byte bitArrayToByte(boolean[] inp) {
		String num = "";
		for(int i = 0; i < inp.length; i++)
			num += booleanToInt(inp[i]);
		return (byte)(int)Integer.valueOf(num, 2);
	}

	/** Convert the given byte array to a String.
	 * @param inp An array of bytes, hopefully storing the codes of printable characters.
	 * @return String */
	public String byteArrayToString(byte[] inp) {
		return new String(inp);
	}

	/** Convert the given byte to a bit array, of the given size.
	 * @param b Given byte.
	 * @param size The size of the resulting bit array.
	 * @return boolean[] */
	public boolean[] byteToBitArray(byte b, int size) {
		boolean[] result = new boolean[size]; // boolean type defaults to false(0)
		String[] binary = Integer.toBinaryString((b+256)%256).split("");
		for(int i = 0; i < binary.length; i++)
			result[size-binary.length+i] = stringToBoolean(binary[i]);
		return result;
	}

	/** Concatenate the two bit arrays, x || y.
	 * @param x Left half.
	 * @param y Right half.
	 * @return boolean[] */
	public boolean[] concat(boolean[] x,  boolean[] y) {
		boolean[] result = new boolean[x.length+y.length];
		for(int i = 0; i < x.length; i++)
			result[i] = x[i];
		for(int j = 0; j < y.length; j++)
			result[x.length + j] = y[j];
		return result;
	}

	/** Decrypt the given byte array.
	 * @param cipher An array of bytes representing the cipher text.
	 * @return byte[] */
	public byte[] decrypt(byte[] cipher) {
		byte[] plain = new byte[cipher.length];
		for (int i =0; i < cipher.length; i++)
			plain[i] = decryptByte(cipher[i]);
		return plain;
	}

	/** Decrypt a single byte using SDES.
	 * @param b Byte to decrypt.
	 * @return byte */
	public byte decryptByte(byte b) {
		boolean[] cipher = byteToBitArray(b, 8);
		boolean[] step1 = expPerm(cipher, ip);
		boolean[] step2 = f(step1, expPerm(key, k2));
		boolean[] step3 = concat(rh(step2), lh(step2));
		boolean[] step4 = f(step3, expPerm(key, k1));
		boolean[] step5 = expPerm(step4, ipInv);
		return bitArrayToByte(step5);
	}

	/** Encrypt the given string using SDES Each character produces a byte of cipher.
	 * @param msg Message to encrypt.
	 * @return byte[] */
	public byte[] encrypt(String msg) {
		byte[] plain = msg.getBytes();
		byte[] cipher = new byte[plain.length];
		for (int i =0; i < cipher.length; i++)
			cipher[i] = encryptByte(plain[i]);
		return cipher;
	}

	/** Encrypt a single byte using SDES
	 * @param b Byte to encrypt.
	 * @return byte. */
	public byte encryptByte(byte b) {
		boolean[] plain = byteToBitArray(b, 8);
		boolean[] step1 = expPerm(plain, ip);
		boolean[] step2 = f(step1, expPerm(key, k1));
		boolean[] step3 = concat(rh(step2), lh(step2));
		boolean[] step4 = f(step3, expPerm(key, k2));
		boolean[] step5 = expPerm(step4, ipInv);
		return bitArrayToByte(step5);
	}

	/** Expand and/or permute and/or select from the bit array, 
	 * inp, producing an expanded/permuted/selected bit array. 
	 * Use the expansion/permutation vector epv.
	 * @param inp A bit array represented as booleans, true=1, false=0.
	 * @param epv An expansion and/or permutation and/or selection vector; all numbers in epv must be in the range 0..inp.length, i.e. they must be valid subscripts for inp.
	 * @return boolean[] */
	public boolean[] expPerm(boolean[] inp, int[] epv) {
		boolean[] result = new boolean[epv.length];
		for(int i = 0; i < epv.length; i++)
			result[i] = inp[epv[i]];
		return result;
	}

	/** This is the 'round' function. It is its own inverse.
	 * f(x,k) = (L(x) xor F(R(x), k)) || R(x)
	 * @param x x input.
	 * @param k k input.
	 * @return boolean[] */
	public boolean[] f(boolean[] x, boolean[] k) {
		boolean[] rhx = rh(x);
		boolean[] lh = xor(lh(x), feistel(k, rhx));
		return concat(lh, rhx);
	}

	/** F(k,x) is a Feistel function.
	 * F(k,x) = P4 (s0 (L (k xor EP(x))) || s1 (R (k xor EP(x)))
	 * @param k k input.
	 * @param x x input.
	 * @return boolean[] */
	public boolean[] feistel(boolean[] k, boolean[] x) {
		boolean[] epx = expPerm(x, expansionPermutation);
		boolean[] k_xor_ep = xor(k, epx);
		
		boolean[] s0 = sBox0(lh(k_xor_ep));
		boolean[] s1 = sBox1(rh(k_xor_ep));
		
		return expPerm(concat(s0, s1), p4);
	}

	/** S-Box 0 as defined on page 330.
	 * Derived by boolean expression simplification utilizing a Karnaugh Map.
	 * y1 = x1'x2'x4 + x1'x2x4' + x1x3'x4 + x1x3x4' + x1x2x4
	 * y2 = x1'x3' + x2x3'x4 + x1x2x4' + x1x2'x4
	 * @param x 4-bit array.
	 * @return boolean[] */
	public static boolean[] sBox0(boolean[] x) {
		boolean[] result = new boolean[2];
		result[0] = (!x[0]&!x[1]&x[3]) | (!x[0]&x[1]&!x[3]) | (x[0]&!x[2]&x[3]) | (x[0]&x[2]&!x[3]) | (x[0]&x[1]&x[3]);
		result[1] = (!x[0]&!x[2]) | (x[1]&!x[2]&x[3]) | (x[0]&x[1]&!x[3]) | (x[0]&!x[1]&x[3]);
		return result;
	}

	/** S-Box 1 as defined on page 330.
	 * Derived by boolean expression simplification utilizing a Karnaugh Map.
	 * y1 = x2'x3'x4 + x1'x2x4' + x2x3x4 + x1x2'x3'
	 * y2 = x1'x3x4' + x1'x2x4 + x1x3'x4' + x1x3x4
	 * @param x 4-bit array.
	 * @return boolean[] */
	public static boolean[] sBox1(boolean[] x) {
		boolean[] result = new boolean[2];
		result[0] = (!x[1]&!x[2]&x[3]) | (!x[0]&x[1]&!x[3]) | (x[1]&x[2]&x[3]) | (x[0]&!x[1]&!x[2]);
		result[1] = (!x[0]&x[2]&!x[3]) | (!x[0]&x[1]&x[3]) | (x[0]&!x[2]&!x[3]) | (x[0]&x[2]&x[3]);
		return result;
	}
	
	/** Get a 10 bit key from the keyboard, such as 1010101010.
	 * Store it as an array of booleans in a field.
	 * @param scanner User input. 
	 * @return String */
	private static String getKey10(Scanner scanner) {
		System.out.println("Please enter your 10 Bit Key without any spaces: ");
		return scanner.nextLine();
	}

	/** Return Left half of x, L(x).
	 * @param inp Given array.
	 * @return boolean[] */
	public boolean[] lh(boolean[] inp) {
		return Arrays.copyOfRange(inp, 0, inp.length/2);
	}

	/** Return Right half of x, L(x).
	 * @param inp Given array.
	 * @return boolean[] */
	public boolean[] rh(boolean[] inp) {
		return Arrays.copyOfRange(inp, inp.length/2, inp.length);
	}

	/** Send the bitArray to stdout as 1's and 0's.
	 * @param inp Given array. */
	public void show(boolean[] inp) {
		String result = "";
		for(int i =0; i < inp.length; i++)
			result += booleanToInt(inp[i]);
		System.out.print(result);
	}

	/** Send the byteArray to stdout.
	 * @param byteArray Given array. */
	public void show(byte[] byteArray) {
		for(byte byteTemp : byteArray) {
			show(byteToBitArray(byteTemp, 8));
			System.out.print(" ");
		}
		System.out.println();
	}
	
	/** Returns 0 or 1 representing given boolean.
	 * @param bool boolean to convert.
	 * @return int */
	private int booleanToInt(boolean bool) {
		return (bool) ? 1 : 0;
	}
	
	/** Converts a string to a boolean based on it's value.
	 * Assumes string is either a 0 or a 1. No error-checking.
	 * @param string 0 or 1 to convert.
	 * @return boolean */
	private boolean stringToBoolean(String string) {
		return string.equals("1");
	}

	/** Exclusive OR. x and y must have the same length.
	 * x xor y is the same as x != y.
	 * @param x x input.
	 * @param y y input.
	 * @return boolean[] */
	public boolean[] xor(boolean[] x, boolean[] y) {
		boolean[] result = new boolean[x.length];
		for (int i = 0; i < x.length; i++)
			result[i] = x[i]^y[i];
		return result;
	}
}
