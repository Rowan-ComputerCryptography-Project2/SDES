package project2.sdes;

import java.util.Scanner;

public class SDES {

	/** 4-bit permutation. */
	private static int[] p4 = {1, 3, 2, 0};
	/** 4-bit Expansion permutation. */
	private static int[] expansionPermutation = {3, 0, 1, 2, 1, 2, 3, 0};
	
	/** Convert the given bit array to a single byte.
	 * @param inp A bit array, max length is 8 bits.
	 * @return byte */
	public byte bitArrayToByte(boolean[] inp) {
		// TODO: Part 2
		return 0;
	}

	/** Convert the given byte array to a String.
	 * @param inp An array of bytes, hopefully storing the codes of printable characters.
	 * @return String */
	public String byteArrayToString(byte[] inp) {
		// TODO: Part 2
		return null;
	}

	/** Convert the given byte to a bit array, of the given size.
	 * @param b Given byte.
	 * @param size The size of the resulting bit array. The operator >>> can be used for an unsigned right shift.
	 * @return boolean[] */
	public boolean[] byteToBitArray(byte b, int size) {
		// TODO: Part 2
		return null;
	}

	/** Concatenate the two bit arrays, x || y.
	 * @param x Left half.
	 * @param y Right half.
	 * @return boolean[] */
	public boolean[] concat(boolean[] x,  boolean[] y) {
		// TODO: Part 2
		return null;
	}

	/** Decrypt the given byte array.
	 * @param cipher An array of bytes representing the cipher text.
	 * @return byte[] */
	public byte[] decrypt(byte[] cipher) {
		// TODO: Part 1
		return null;
	}

	/** Decrypt a single byte using SDES.
	 * @param b Byte to decrypt.
	 * @return byte */
	public byte decryptByte(byte b) {
		// TODO: Part 1
		return 0;
	}

	/** Encrypt the given string using SDES Each character produces a byte of cipher.
	 * @param msg Message to encrypt.
	 * @return byte[] */
	public byte[] encrypt(String msg) {
		// TODO: Part 1
		return null;
	}

	/** Encrypt a single byte using SDES
	 * @param b Byte to encrypt.
	 * @return byte. */
	public byte encryptByte(byte b) {
		// TODO: Part 1
		return 0;
	}

	/** Expand and/or permute and/or select from the bit array, 
	 * inp, producing an expanded/permuted/selected bit array. 
	 * Use the expansion/permutation vector epv.
	 * @param inp A bit array represented as booleans, true=1, false=0.
	 * @param epv An expansion and/or permutation and/or selection vector; all numbers in epv must be in the range 0..inp.length, i.e. they must be valid subscripts for inp.
	 * @return boolean[] */
	public boolean[] expPerm(boolean[] inp, int[] epv) {
		// TODO: Part 2
		return null;
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
		boolean[] s1 = sBox1(lh(k_xor_ep));
		
		return expPerm(concat(s0, s1), p4);
	}

	private boolean[] sBox0(boolean[] inp) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean[] sBox1(boolean[] inp) {
		// TODO Auto-generated method stub
		return null;
	}

	/** Get a 10 bit key from the keyboard, such as 1010101010.
	 * Store it as an array of booleans in a field.
	 * @param scanner User input. */
	public void getKey10(Scanner scanner) {
		// TODO: Part 2
	}

	/** Return Left half of x, L(x).
	 * @param inp Given array.
	 * @return boolean[] */
	public boolean[] lh(boolean[] inp) {
		// TODO: Part 2
		return null;
	}

	/** Return Right half of x, L(x).
	 * @param inp Given array.
	 * @return boolean[] */
	public boolean[] rh(boolean[] inp) {
		// TODO: Part 2
		return null;
	}

	/** Send the bitArray to stdout as 1's and 0's.
	 * @param inp Given array. */
	public void show(boolean[] inp) {
		// TODO: Part 1
	}

	/** Send the byteArray to stdout.
	 * @param byteArray Given array. */
	public void show(byte[] byteArray) {
		// TODO: Part 1
	}

	/** Exclusive OR. x and y must have the same length.
	 * x xor y is the same as x != y.
	 * @param x x input.
	 * @param y y input.
	 * @return boolean[] */
	public boolean[] xor(boolean[] x, boolean[] y) {
		// TODO: Part 2
		return null;
	}
}