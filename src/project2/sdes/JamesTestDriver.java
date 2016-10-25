package project2.sdes;

import project2.sdes.SDES;

/** Test Driver for Simple DES program.
 * @author James A. Donnell Jr. */
public class JamesTestDriver {

	public static void main(String[] args) {
		sBoxTest();
	}

	private static String boolArrayToBitString(boolean[] x) {
		String result = "[";
		for (boolean i : x){
			result += i ? 1 : 0;  
		}
		result += "]";
		return result;
	}

	private static void sBoxTest() {
		System.out.println("[0123]:[s0]:[s1]\n----------------");
		boolean[] x0000 = {false, false, false, false};
		System.out.println(boolArrayToBitString(x0000) + ":" + boolArrayToBitString(SDES.sBox0(x0000)) + ":" + boolArrayToBitString(SDES.sBox1(x0000)));
		boolean[] x0001 = {false, false, false, true};
		System.out.println(boolArrayToBitString(x0001) + ":" + boolArrayToBitString(SDES.sBox0(x0001)) + ":" + boolArrayToBitString(SDES.sBox1(x0001)));
		boolean[] x0010 = {false, false, true, false};
		System.out.println(boolArrayToBitString(x0010) + ":" + boolArrayToBitString(SDES.sBox0(x0010)) + ":" + boolArrayToBitString(SDES.sBox1(x0010)));
		boolean[] x0011 = {false, false, true, true};
		System.out.println(boolArrayToBitString(x0011) + ":" + boolArrayToBitString(SDES.sBox0(x0011)) + ":" + boolArrayToBitString(SDES.sBox1(x0011)));
		boolean[] x0100 = {false, true, false, false};
		System.out.println(boolArrayToBitString(x0100) + ":" + boolArrayToBitString(SDES.sBox0(x0100)) + ":" + boolArrayToBitString(SDES.sBox1(x0100)));
		boolean[] x0101 = {false, true, false, true};
		System.out.println(boolArrayToBitString(x0101) + ":" + boolArrayToBitString(SDES.sBox0(x0101)) + ":" + boolArrayToBitString(SDES.sBox1(x0101)));
		boolean[] x0110 = {false, true, true, false};
		System.out.println(boolArrayToBitString(x0110) + ":" + boolArrayToBitString(SDES.sBox0(x0110)) + ":" + boolArrayToBitString(SDES.sBox1(x0110)));
		boolean[] x0111 = {false, true, true, true};
		System.out.println(boolArrayToBitString(x0111) + ":" + boolArrayToBitString(SDES.sBox0(x0111)) + ":" + boolArrayToBitString(SDES.sBox1(x0111)));
		boolean[] x1000 = {true, false, false, false};
		System.out.println(boolArrayToBitString(x1000) + ":" + boolArrayToBitString(SDES.sBox0(x1000)) + ":" + boolArrayToBitString(SDES.sBox1(x1000)));
		boolean[] x1001 = {true, false, false, true};
		System.out.println(boolArrayToBitString(x1001) + ":" + boolArrayToBitString(SDES.sBox0(x1001)) + ":" + boolArrayToBitString(SDES.sBox1(x1001)));
		boolean[] x1010 = {true, false, true, false};
		System.out.println(boolArrayToBitString(x1010) + ":" + boolArrayToBitString(SDES.sBox0(x1010)) + ":" + boolArrayToBitString(SDES.sBox1(x1010)));
		boolean[] x1011 = {true, false, true, true};
		System.out.println(boolArrayToBitString(x1011) + ":" + boolArrayToBitString(SDES.sBox0(x1011)) + ":" + boolArrayToBitString(SDES.sBox1(x1011)));
		boolean[] x1100 = {true, true, false, false};
		System.out.println(boolArrayToBitString(x1100) + ":" + boolArrayToBitString(SDES.sBox0(x1100)) + ":" + boolArrayToBitString(SDES.sBox1(x1100)));
		boolean[] x1101 = {true, true, false, true};
		System.out.println(boolArrayToBitString(x1101) + ":" + boolArrayToBitString(SDES.sBox0(x1101)) + ":" + boolArrayToBitString(SDES.sBox1(x1101)));
		boolean[] x1110 = {true, true, true, false};
		System.out.println(boolArrayToBitString(x1110) + ":" + boolArrayToBitString(SDES.sBox0(x1110)) + ":" + boolArrayToBitString(SDES.sBox1(x1110)));
		boolean[] x1111 = {true, true, true, true};
		System.out.println(boolArrayToBitString(x1111) + ":" + boolArrayToBitString(SDES.sBox0(x1111)) + ":" + boolArrayToBitString(SDES.sBox1(x1111)));
	}
}