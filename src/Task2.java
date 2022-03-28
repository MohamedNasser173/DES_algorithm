
/*
 * Mohamed Nasser Ismail 
 * 
 * ID : 20180241
 * 
 *  
 * */
public class Task2 {

	private static class DES {
		int[] shiftBits = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

		int[] initialPermutation = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30,
				22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3,
				61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
		int[] finalPermutation = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22,
				62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34,
				2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25 };
		int[] expansionPermutation = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16,
				17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };
		int[] pTable = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19,
				13, 30, 6, 22, 11, 4, 25 };

		int[] PC1 = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60,
				52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5,
				28, 20, 12, 4 };

		int[] PC2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52,
				31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };
		int[][] sBox = { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
				{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
				{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
				{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } };

		String hexaToBinary(String input) // convert from hexadecimal to Binary
		{
			int n = input.length() * 4;
			input = Long.toBinaryString(Long.parseUnsignedLong(input, 16));
			while (input.length() < n)
				input = "0" + input;
			return input;
		}

		String binaryToHexa(String input) // convert from Binary to hexadecimal
		{
			int n = (int) input.length() / 4;
			input = Long.toHexString(Long.parseUnsignedLong(input, 2));
			while (input.length() < n)
				input = "0" + input;
			return input;
		}

		String permutation(int[] permute, String input) {
			/*
			 * ex replace input[0]by input[permute[0]] if frist index in permute =15 , we
			 * will get Content of index number 15 of input and put it in input index number
			 * 0 and so on
			 */
			String output = "";
			input = hexaToBinary(input);
			for (int i = 0; i < permute.length; i++)
				output += input.charAt(permute[i] - 1); // -1 Because we use zero base
			output = binaryToHexa(output);
			return output;
		}

		String leftShift(String input, int numberOfShiftBits) {
			int n = input.length() * 4;
			int p[] = new int[n];
			for (int i = 0; i < n - 1; i++)
				p[i] = i + 2;
			p[n - 1] = 1;
			while (numberOfShiftBits > 0) {
				input = permutation(p, input);
				numberOfShiftBits--;

			}
			return input;
		}

		String[] keyGeneration(String key) {
			// steps
			// 1- permutation for key by PC1
			// 2- shift lift key and get keys
			// 3- permutation for key by PC2
			// 4- return 16 key

			String keys[] = new String[16];

			key = permutation(PC1, key);
			for (int i = 0; i < 16; i++) {

				String C, D;
				C = leftShift(key.substring(0, 7), shiftBits[i]);
				D = leftShift(key.substring(7, 14), shiftBits[i]);

				key = C + D;

				keys[i] = permutation(PC2, key);
			}
			return keys;
		}

		String xor(String s1, String s2) {
			long decima1 = Long.parseUnsignedLong(s1, 16); // Hexa to decimal
			long decimal2 = Long.parseUnsignedLong(s2, 16);// Hexa to decimal
			decima1 = decima1 ^ decimal2; // XOR
			String output = Long.toHexString(decima1); // Decimal to Hexa
			while (output.length() < s2.length()) // add by Mohamed Nasser
				output = "0" + output;

			return output;
		}

		String sBox(String input) {
			/*
			 * Devide input into blocks each block contain 6 bits then we will sum bit
			 * number 1 and bit number 6 to get row index and sum bit from 2 to 6 to get
			 * column index
			 */

			String output = "";
			input = hexaToBinary(input);
			for (int i = 0; i < 48; i += 6) {
				String temp = input.substring(i, i + 6);
				String rowIndexBin = temp.charAt(0) + "" + temp.charAt(5);
				String ColIndexBin = temp.substring(1, 5);
				int row = Integer.parseInt(rowIndexBin, 2);
				int col = Integer.parseInt(ColIndexBin, 2);

				String int2Hexa = Integer.toHexString(sBox[row][col]);
				output += int2Hexa;
			}

			return output;
		}

		String round(String input, String key, int numOfRound) {
			// steps
			// 1-Expansion Permutation for rightTemp
			// 2-Do XOR between rightTemp and key of round
			// 3-search in s-box table
			// 4- D box
			// 5- DO XOR between left and rightTemo and put the result in left
			// swap left and right

			String right, rightTemp, left;
			left = input.substring(0, 8);
			rightTemp = input.substring(8, 16);
			right = rightTemp;
			rightTemp = permutation(expansionPermutation, rightTemp);
			rightTemp = xor(rightTemp, key);
			sBox(rightTemp);
			rightTemp = permutation(pTable, rightTemp);
			xor(left, rightTemp);

			String R = right.toUpperCase();
			String L = left.toUpperCase();
			String K = key.toUpperCase();
			System.out.println(
					"Round " + (numOfRound + 1) + "=  " + R + " " + L + "  key " + (numOfRound + 1) + "= " + K);

			String output = right + left;
			return output;

		}

		String encryption(String plainText, String key) {
			// Steps
			// 1-key Generation
			// 2- first permutation
			// 3- 16 round from 0 to 15
			// 4-final permutation
			String keys[] = keyGeneration(key);
			plainText = permutation(initialPermutation, plainText);
			System.out.println("first permutation: " + plainText.toUpperCase());
			String l, r;
			l = plainText.substring(0, 8).toUpperCase();
			r = plainText.substring(8, 16).toUpperCase();
			System.out.println("split: L=" + l + " R=" + r);

			for (int i = 0; i < 16; i++) {
				plainText = round(plainText, keys[i], i);
			}

			String Right = plainText.substring(8, 16);
			String Left = plainText.substring(0, 8);
			plainText = Right + Left;
			String cipherText = permutation(finalPermutation, plainText);

			return cipherText;

		}

		String decryption(String plainText, String key) {
			String keys[] = keyGeneration(key);

			plainText = permutation(initialPermutation, plainText);
			System.out.println("first permutation: " + plainText.toUpperCase());
			String l = plainText.substring(0, 8).toUpperCase();
			String r = plainText.substring(8, 16).toUpperCase();
			System.out.println("split: L=" + l + " R=" + r);

			for (int i = 15; i > -1; i--) {
				plainText = round(plainText, keys[i], 15 - i);
			}

			String left = plainText.substring(0, 8);
			String right = plainText.substring(8, 16);
			plainText = right + left; // swap
			plainText = permutation(finalPermutation, plainText);
			return plainText;
		}

	}

	public static void main(String[] args) {

		String PlainText = "123456789ABCDABC";
		String key = "ABCABC123456ABCD";

		DES obj = new DES();
		System.out.println("                 Encryption\n");
		String CipherText = obj.encryption(PlainText, key);
		System.out.println("\nCipher Text: " + CipherText.toUpperCase());
		System.out.println(
				"********************************************************************************************");

		/*
		 * there is an logical error in Decryption
		 * System.out.println("                 Decryption\n"); String originalText =
		 * obj.decryption(CipherText, key);
		 * System.out.println("Plain Text after decryption: " +
		 * originalText.toUpperCase());
		 */
	}

}
