import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Cipher {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '" + "\"" + "![]/%-_;?=:" + '\n' + '\r';
	private static final String SIMPLE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String SUPER_SIMPLE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final Dictionary d = Dictionary.buildDictionary("/Users/naman/Documents/APCS-workspace/Cipher/words.txt");

	// Set this variable to the default alphabet you wish to use
	private static final String DEFAULT_ALPHABET = ALPHABET;
	
	/**
	 * Returns plaintext encrypted by the rotation cipher with a shift of movement.
	 * @param alphabet the alphabet to be used for the encryption
	 * @param plainText the plain text to be encrypted.
	 * @param shiftAmount the number of characters in ALPHABET to shift by.
	 * @return returns the encrypted plainText.
	 */
	public static String rotationCipherEncrypt(String plainText, int shiftAmount, String alphabet) {
		StringBuilder output = new StringBuilder();
		int messageLength = plainText.length(), alphabetLength = alphabet.length();
		
		for (int i = 0; i < messageLength; i++) {
			int index = alphabet.indexOf(plainText.substring(i, i+1));
			index = ShiftIndex(index, shiftAmount, alphabetLength);
			output.append(alphabet.substring(index, index+1));
		}
		
		return output.toString();
	}
	
	/**
	 * Shifts the index value of a character by shift indexes
	 * @param index index
	 * @param shift number of characters to shift by
	 * @param alphabetLength alphabet length
	 * @return the shifted index
	 */
	public static int ShiftIndex(int index, int shift, int alphabetLength) {
		while (shift < 0) 
			shift += alphabetLength;
		index += shift;
		index %= alphabetLength;
		
		return index;
	}
	
	public static String rotationCipherEncrypt(String plainText, int shiftAmount) {
		return rotationCipherEncrypt(plainText, shiftAmount, DEFAULT_ALPHABET);
	}

	/**
	 * Returns a the result of decrypting cipherText by shiftAmount using the rotation cipher.
	 * @param alphabet the alphabet to be used for the encryption
	 * @param cipherText the encrypted text.
	 * @param shiftAmount the key to decrypt the cipher.
	 * @return returns the decrypted cipherText.
	 */
	public static String rotationCipherDecrypt(String cipherText, int shiftAmount, String alphabet) {
		StringBuilder output = new StringBuilder();
		int messageLength = cipherText.length(), alphabetLength = alphabet.length();
		
		for (int i = 0; i < messageLength; i++) {
			int index = alphabet.indexOf(cipherText.substring(i, i+1));
			index = ShiftIndex(index, -shiftAmount, alphabetLength);
			output.append(alphabet.substring(index, index+1));
		}
		
		return output.toString();
	}
	
	public static String rotationCipherDecrypt(String cipherText, int shiftAmount) {
		return rotationCipherDecrypt(cipherText, shiftAmount, DEFAULT_ALPHABET);
	}

	/**
	 * Returns plaintext encrypted by the vigenere cipher encoded with the String code
	 * @param alphabet the alphabet to be used for the encryption
	 * @param plainText the plain text to be encrypted.
	 * @param code the code to use as the encryption key.
	 * @return returns the encrypted plainText.
	 */
	public static String vigenereCipherEncrypt(String plainText, String code, String alphabet) {
		StringBuilder output = new StringBuilder();
		int codeLength = code.length(), messageLength = plainText.length();
		int[] shifts = codeToShifts(code, alphabet);
		
		for (int i = 0; i < messageLength; i += codeLength) {
			while (i + codeLength > messageLength) codeLength--;
			output.append(substitutionCipherEncrypt(plainText.substring(i, i + codeLength), shifts, alphabet));
		}
		
		return output.toString();
	}
	
	/**
	 * Translates a string code to an int array of the indexes
	 * @param code the code to be translated
	 * @return the int array with the indexes of the code
	 */
	public static int[] codeToShifts (String code, String alphabet) {
		int length = code.length();
		int[] arr = new int[length];
		
		for (int i = 0; i < length; i++) 
			arr[i] = alphabet.indexOf(code.substring(i, i+1));
		
		return arr;
	}
	
	public static int[] codeToShifts (String code) {
		return codeToShifts(code, DEFAULT_ALPHABET);
	}
	
	public static String vigenereCipherEncrypt(String plainText, String code) {
		return vigenereCipherEncrypt(plainText, code, DEFAULT_ALPHABET);
	}

	/**
	 * Returns the result of decrypting cipherText with the key code.
	 * @param alphabet the alphabet to be used for the encryption
	 * @param cipherText the encrypted text.
	 * @param code the decryption key
	 * @return returns the decrypted cipherText.
	 */
	public static String vigenereCipherDecrypt(String cipherText, String code, String alphabet) {
		StringBuilder output = new StringBuilder();
		int codeLength = code.length(), messageLength = cipherText.length();
		int[] shifts = codeToShifts(code, alphabet);
		
		for (int i = 0; i < messageLength; i += codeLength) {
			while (i + codeLength > messageLength) codeLength--;
			output.append(substitutionCipherDecrypt(cipherText.substring(i, i + codeLength), shifts, alphabet));
		}
		
		return output.toString();
	}
	
	
	public static String vigenereCipherDecrypt(String cipherText, String code) {
		return vigenereCipherDecrypt(cipherText, code, DEFAULT_ALPHABET);
	}
	
	/**
	 * Returns the result of substituting values in a String by certain permutations
	 * @param plaintext the text to substitute
	 * @param permutation the array that describes how the characters are to be replaced.
	 * @param alphabet the alphabet
	 * @return the substituted String
	 */
	public static String substitutionCipherEncrypt(String plaintext, int[] permutation, String alphabet) {
		StringBuilder output = new StringBuilder();
		int length = plaintext.length();
		
		if (!isValidPermutation(permutation)) return "failure";
		if (permutation.length < length) return "The permutation does not contain enough information";
		
		for (int i = 0; i < length; i++) 
			output.append(rotationCipherEncrypt(plaintext.substring(i, i+1), permutation[i], alphabet));
		
		return output.toString();
	}
	
	public static String substitutionCipherEncrypt (String plaintext, int[] permutation) {
		return substitutionCipherEncrypt(plaintext, permutation, DEFAULT_ALPHABET);
	}
	
	/**
	 * Returns the result of substituting values in a String by certain permutations
	 * @param plaintext the text to substitute
	 * @param permutation the array that describes how the characters are to be replaced.
	 * @param alphabet the alphabet
	 * @return the substituted String
	 */
	public static String substitutionCipherDecrypt(String plaintext, int[] permutation, String alphabet) {
		StringBuilder output = new StringBuilder();
		int length = plaintext.length(), alphabetLength = alphabet.length();
		
		if (permutation.length < length) return "The permutation does not contain enough information";
		
		for (int i = 0; i < length; i++) {
			int index = ShiftIndex(alphabet.indexOf(plaintext.substring(i, i+1)), -permutation[i], alphabetLength);	//permutation index is the shift amount
			String newLetter = alphabet.substring(index, index+1);
			output.append(newLetter);
		}
		
		return output.toString();
	}
	
	public static String substitutionCipherDecrypt(String plaintext, int[] permutation) {
		return substitutionCipherDecrypt(plaintext, permutation, DEFAULT_ALPHABET);
	}
	
	/**
	 * Returns if the permutation array is valid
	 * @param permutation the array containing the permutation values
	 * @return if the array is valid
	 */
	public static boolean isValidPermutation(int[] permutation) {
		for (int i = 0; i < permutation.length; i++)
			for (int j = 0; j < permutation.length; j++)
				if (i != j && permutation[i] == permutation[j]) return false;
		
		return true;
	}
	
	/**
	 * Returns an array containing random permutations
	 * @param length the length of the array
	 * @return the array of random permutations
	 */
	public static int[] randomPermutation(int length) {
		int[] output = new int[length];
		
		for (int i = 0; i < length; i++)
			output[i] = (int) Math.random() * length;
		
		return output;
	}
	
	/**
	 * Returns a String array containing the different words from a string
	 * @param str the original string
	 * @return a String array with the individual words
	 */
	public static String[] getWords(String str) {
		str.trim();
		int currentIndex = 0, strLength = str.length();
		String[] output = new String[countWordsIn(str)];
		
		for (int i = 0; i < strLength; i++) {	//loop through all the words in the string
			if (currentIndex < output.length) {
				output[currentIndex] = findNextWord(str, i);//save word in array
				i += output[currentIndex++].length();		//change the index to search for words to the last found word index
			} else break;
		}
		return output;
	}
	
	/**
	 * Returns the next word in a string
	 * @param str the string with words
	 * @param index the current index to begin searching
	 * @return the next word after the index
	 */
	public static String findNextWord(String str, int index) {
		int strLength = str.length();
		StringBuilder word = new StringBuilder();
		
		while (index < strLength && !str.substring(index, index+1).equals(" ")) {
			word.append(str.substring(index, index+1));
			index++;
		}
		
		return stripInvalidChars(word.toString(), SUPER_SIMPLE_ALPHABET);
	}
	
	/**
	 * Returns the number of words in a string
	 * @param str the string
	 * @return the number of words in a string
	 */
	public static int countWordsIn(String str) {
		int size = 1, strLength = str.length();
		
		for (int i = 0; i < strLength; i++)
			if (i > 1 && str.substring(i, i+1).equals(" ") && !str.substring(i-1, i).equals(" ")) size++;
		
		if (str.substring(strLength-1, strLength).equals(" ")) size--;
		
		return size;
	}
	
	/**
	 * returns a copy of the input plaintext String with invalid characters
	 * stripped out.
	 * 
	 * @param plaintext
	 *          The plaintext string you wish to remove illegal characters from
	 * @param alphabet
	 *          A string of all legal characters.
	 * @return String A copy of plain with all characters not in alphabet removed.
	 */
	private static String stripInvalidChars(String plaintext, String alphabet) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < plaintext.length(); i++) { 			// loop through plaintext
			if (alphabet.indexOf(plaintext.charAt(i)) >= 0) 	// get index of char
				b.append(plaintext.charAt(i)); 					// if it exists, keep it
			//else
																								// otherwise skip it &
				//System.out.println("Stripping letter: \"" + plaintext.charAt(i) 				// display
																								// a
																								// message
					//	+ "\"");
		}
		return b.toString();
	}

	/**
	 * returns true if plaintext is valid English.
	 * @param plaintext
	 *          the text you wish to test for whether it's valid English
	 * @return boolean returns true if plaintext is valid English.
	 */
	public static boolean isEnglish(String plaintext) {
		String[] words = getWords(plaintext);
		int englishNumberOfWords = 0;
		
		for (String n : words) 
			if (d.isWord(n.toLowerCase())) englishNumberOfWords++;
		
		if (words.length == 0) return false;
		else if (((double) (englishNumberOfWords / (words.length))) > 0.8) {
			return true;
		}
			
		
		return false;
	}
	
	public static String rotationCipherCrack(String cipherText) {
		return rotationCipherCrack(cipherText, DEFAULT_ALPHABET);
	}
	
	/**
	 * returns the English string of the cracked rotation cipher
	 * @param cipherText the text to be deciphered
	 * @return the cracked English string
	 */
//	public static String rotationCipherCrack(String cipherText, String alphabet) {
//		int length = alphabet.length();
//		
//		for (int shift = 1; shift <= length; shift++) {				//loop through all the possible shifts
//			String test = rotationCipherDecrypt(cipherText, shift, alphabet);	//decrypt the cipher with the shift
//			if (isEnglish(test)) return test;						//check if the decrypted cipher is english
//		}
//		return "~FAILURE_TO_DECRYPT";
//	}
//	
	/**
	 * returns the English string of the cracked rotation cipher
	 * @param cipherText the text to be deciphered
	 * @return the cracked English string
	 */
	public static String rotationCipherCrack(String cipherText, String alphabet) {
		int length = alphabet.length();
		
		for (int shift = 1; shift <= length; shift++) {				//loop through all the possible shifts
			String test = rotationCipherDecrypt(cipherText, shift, alphabet);	//decrypt the cipher with the shift
			if (isEnglishFreq(test)) return test;						//check if the decrypted cipher is english
		}
		return "~FAILURE_TO_DECRYPT";
	}
	
	public static String crackVigenereCipher3(String cipherText, String alphabet) {
		
		String text = "";
		StringBuilder code = new StringBuilder();
		int alphabetLength = alphabet.length();
		
		for (int i = 0; i < alphabetLength; i++) 
			for (int j = 0; j < alphabetLength; j++) 
				for (int k = 0; k < alphabetLength; k++) {
					code.append(alphabet.substring(i, i+1));
					code.append(alphabet.substring(j, j+1));
					code.append(alphabet.substring(k, k+1));
					text = vigenereCipherDecrypt(cipherText, code.toString());
					if (isEnglish(text)) return text;
					code.setLength(0);
				}
			
		return "~FAILURE_TO_DECRYPT";
	}
	
	
	public static String crackVigenereCipher2(String cipherText, String alphabet) {
		
		String text = "";
		StringBuilder code = new StringBuilder();
		int alphabetLength = alphabet.length();
		
		for (int i = 0; i < alphabetLength; i++) 
			for (int j = 0; j < alphabetLength; j++) {
				code.append(alphabet.substring(i, i+1));
				code.append(alphabet.substring(j, j+1));
				text = vigenereCipherDecrypt(cipherText, code.toString());
				if (isEnglish(text)) return text;
				code.delete(0, code.length());
			}
			
		return "~FAILURE_TO_DECRYPT";
	}
	
	public static String crackVigenereCipher3(String cipherText) {
		return crackVigenereCipher3(cipherText, DEFAULT_ALPHABET);
	}
	
	public static String crackVigenereCipher2(String cipherText) {
		return crackVigenereCipher2(cipherText, DEFAULT_ALPHABET);
	}
	
	public static String generateCode(int codeLength, String alphabet) {
		StringBuilder code = new StringBuilder();
		int alphabetLength = alphabet.length();
		
		while (code.length() < codeLength) {
			int index = (int) (Math.random()*alphabetLength);
			code.append(alphabet.substring(index, index+1));
		}
		
		return code.toString();
	}
	
	/**
	 * Returns the decrypted vigenere string if the code has 3 letters in it
	 * @param cipher the encrypted cipher
	 * @param alphabet the alphabet used to encrypt the cipher
	 * @return decrypted vigenere string if the code has 3 letters in it
	 */
	public static String vigenereCipherCrackThreeLetter(String cipher, String alphabet) {
		return vigenereCipherCrack(cipher, 3, alphabet);
	}
	
	/**
	 * Cracks and returns a decrypted Vigenere string
	 * @param cipher the cipher to be decrypted
	 * @param codeLength the length of the code
	 * @return the decrypted vigenere string
	 */
	public static String vigenereCipherCrack(String cipher, int codeLength) {
		return vigenereCipherCrack(cipher, codeLength, DEFAULT_ALPHABET);
	}

	/**
	 * Cracks and returns a decrypted Vigenere string
	 * @param cipher the cipher to be decrypted
	 * @param codeLength the length of the code
	 * @param alphabet the alphabet to be used
	 * @return the decrypted vigenere string
	 */
	public static String vigenereCipherCrack(String cipher, int codeLength, String alphabet) {
		String smallCipher = cipher;
		
		if (cipher.length() > 1000) smallCipher = cipher.substring(0, 1000);
		
		String code = decipherCode(smallCipher, codeLength, alphabet);
		
		return vigenereCipherDecrypt(cipher, code);
	}
	
	/**
	 * Cracks and returns code for the cipher
	 * @param cipher the cipher to be decrypted
	 * @param alphabet the alphabet to be used
	 * @return the code to encrypt the cipher
	 */
	public static String vigenereCipherCrack(String cipher, String alphabet) {
		String smallCipher = cipher;
		String code = "";
		
		if (cipher.length() > 1000) smallCipher = cipher.substring(0, 1000);
		
		for (int codeLength = 1; codeLength <= Integer.MAX_VALUE; codeLength++) {
			code = decipherCode(smallCipher, codeLength, alphabet);
			if (code != "") break;
		}
		
		return code;
	}
	
	public static String vigenereCipherCrack(String cipher) {
		return vigenereCipherCrack(cipher, DEFAULT_ALPHABET);
	}
	
	/**
	 * Returns the likely code from a long string of encrypted ciphers
	 * @param cipher the encrypted string
	 * @param codeLength the length of the code
	 * @param alphabet the alphabet of valid characters
	 * @return the most likely code
	 */
	public static String decipherCode(String cipher, int codeLength, String alphabet) {
		int alphabetLength = alphabet.length();
		StringBuilder code = new StringBuilder();
		
		String[] cL = getCorrespondingLetters(cipher, codeLength);
			
		for (int j = 0; j < cL.length; j++) { 				//loop through each of the corresponding letter lists
			for (int i = 0; i < alphabetLength; i++) {		//try each shift
				String decrypted = rotationCipherDecrypt(cL[j], i);	//decrypt with the shift
												
				if(isEnglishFreq(decrypted)) {
					code.append(alphabet.substring(i, i+1));
					break;									//move on to the next code letter
				}
			}
		}
		return code.toString();
	}
	
	/**
	 * Returns if a string is likely English
	 * The string must be very long, since it must meet the average English character use requirements
	 * @param decrypted the string to be checked if it is English
	 * @return if the string is likely English
	 */
	public static boolean isEnglishFreq(String decrypted) {
		LetterBag bag = new LetterBag();
		
		bag.addString(decrypted);					//add all the characters from the string to the bag
			
		String[] mostFrequent = bag.getNMostFrequentArray(10);
		int matchMostFrequent = 0;
		
		for (String k : mostFrequent) 				
			if (k.equals(" ") ||					//check to see if the most frequent items match
					k.toLowerCase().equals("e") ||	//the most frequent letters in the English language
					k.toLowerCase().equals("t") ||
					k.toLowerCase().equals("a") ||
					k.toLowerCase().equals("o") ||
					k.toLowerCase().equals("i") ||
					k.toLowerCase().equals("n") ||
					k.toLowerCase().equals("s") ||
					k.toLowerCase().equals("h") ||
					k.toLowerCase().equals("r")) {
				matchMostFrequent++;
			}
		
		bag.clear();
													//If most of the letters appear as most frequent
		return (matchMostFrequent > 6);				//the String is likely English
	}
	
	
	/**
	 * Returns a String that is made from alternating letters from the corresponding letters
	 * @param correspondingLetters the array containing each of the strings of corresponding letters
	 * @param totalLetters the total letters in the string
	 * @return
	 */
	public static String combineLetters(String[] correspondingLetters, int totalLetters) {
		StringBuilder output = new StringBuilder();
		int[] correspondingIndex = new int[correspondingLetters.length]; //store the current index value for each substring
		
		for (int i = 0; i < totalLetters; i++) {
			int index = i % correspondingLetters.length;
			output.append(correspondingLetters[index].substring(correspondingIndex[index]++, correspondingIndex[index]));
		}
		
		return output.toString();
	}
	
	/**
	 * Returns a string array with the corresponding letters stored separately
	 * @param cipher the cipher to be separated
	 * @param codeLength the number of pieces to break the string into (the length of the code to encrypt)
	 * @return a string array with the corresponding letters stored separately
	 */
	public static String[] getCorrespondingLetters(String cipher, int codeLength) {
		StringBuilder[] correspondingLetters = new StringBuilder[codeLength];
		String[] output = new String[codeLength];
		int index = 0, cipherLength = cipher.length();
		
		for (int i = 0; i < codeLength; i++) 
			correspondingLetters[i] = new StringBuilder();

		for (int i = 0; i < cipherLength; i++) {
			index = i % codeLength;						//alternate between indicies of code values
			correspondingLetters[index].append(cipher.substring(i, i+1));
		}
		
		for (int i = 0; i < codeLength; i++)
			output[i] = correspondingLetters[i].toString();
		
		return output;
	}
	
	public static String crackGeneralVigenereCipherFreq(String cipher) {
		return crackGeneralVigenereCipherFreq(cipher, DEFAULT_ALPHABET);
	}
	
	public static String crackGeneralVigenereCipherFreq(String cipher, String alphabet) {
		int alphabetLength = alphabet.length();
		LetterBag bag = new LetterBag();
		double cipherMinScore = Double.MAX_VALUE;
		String decryptedCipher = "", answer = "";
		
		for (int codeLength = 1; codeLength <= 8; codeLength++) {
			String[] cipherParts = new String[codeLength];
			StringBuilder code = new StringBuilder();
			
			cipherParts = getCorrespondingLetters(cipher, codeLength);
			
			for (int codeLetterIndex = 0; codeLetterIndex < codeLength; codeLetterIndex++) {
				double minScore = Double.MAX_VALUE;
				String codeLetter = "a";
				
				
				for (int shift = 1; shift < alphabetLength; shift++) {
					bag.clear();
				
					String decoded = rotationCipherDecrypt(cipherParts[codeLetterIndex], shift);
					
					bag.addString(decoded);
					
					double score = getSimilarityToEnglishScore(bag);
					
					if (score < minScore) {
						minScore = score;
						codeLetter = alphabet.substring(shift, shift+1);
					}
				}
				code.append(codeLetter);
			}
			bag.clear();
			
			decryptedCipher = vigenereCipherDecrypt(cipher, code.toString(), alphabet);
			bag.addString(decryptedCipher);
			double cipherScore = getSimilarityToEnglishScore(bag);
			
			if (cipherScore < cipherMinScore) {
				cipherMinScore = cipherScore;
				answer = decryptedCipher;
			}
		}
		return answer;
	}
	
	/**
	 * Returns the distance between normal alphabet frequencies to the letter frequencies
	 * @param bag the bag containing all the letters
	 * @return the distance between normal alphabet frequencies to the letter frequencies
	 */
	public static double getSimilarityToEnglishScore(LetterBag bag) {
		double totalWords = bag.getTotalWords(), score = 0;
		String lowerCaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
		int alphabetLength = lowerCaseAlphabet.length();
		double[] actualEnglishFreq = new double[] 
				{8.17, 1.49, 2.78, 4.25, 12.7, 2.23, 2.01, 6.09, 6.97, 0.15, 
				 0.77, 4.03, 2.4, 6.75, 7.5, 1.93, 0.09, 5.99, 6.33, 9.1, 2.8,
				 0.98, 2.36, 0.15, 1.98, 0.07};
		
		for (int i = 0; i < alphabetLength; i++) {
			double numOccurrences = bag.getNumOccurrences(lowerCaseAlphabet.substring(i, i+1))
					+ bag.getNumOccurrences(lowerCaseAlphabet.substring(i, i+1).toUpperCase());
			
			double actualFreq = (numOccurrences / totalWords) * 100;
			
			score += Math.abs(actualEnglishFreq[i] - actualFreq);
		}
		return score;
	}
	
}