import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CipherTest {

	@Test
	public void shiftElement() {
		assertEquals(4, Cipher.ShiftIndex(12, 6, 14));
		assertEquals(12, Cipher.ShiftIndex(4, -6, 14));
	}
	
	@Test
	public void shiftElement1() {
		
		String a = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '" + "\"" + "![]/%-_;?-=:" + '\n' + '\r';
		int b = Cipher.ShiftIndex(a.indexOf("d"), 8, a.length());
		int c = Cipher.ShiftIndex(a.indexOf("l"), -8, a.length());
		assertEquals(b, a.indexOf("l"));
		assertEquals(c, a.indexOf("d"));
	}
	
	@Test
	public void rotationCipherEncryptBy3() {
		String plaintext = "the quick brown fox jumped over the lazy dogs";
		String correctCipherText = "wkh!txlfn!eurzq!irA!mxpshg!ryhu!wkh!odCB!grjv";
		String testCipherText = Cipher.rotationCipherEncrypt(plaintext, 3);
		assertEquals(testCipherText, correctCipherText);
	}
	
	@Test
	public void rotationCipherDecryptBy3() {
		String plaintext = "the quick brown fox jumped over the lazy dogs";
		String correctCipherText = "wkh!txlfn!eurzq!irA!mxpshg!ryhu!wkh!odCB!grjv";
		String testPlainText = Cipher.rotationCipherDecrypt(correctCipherText, 3);
		assertEquals(testPlainText, plaintext);
	}
	
//	@Test
//	public void rotationCipherDecryptBy100() {
//		String plaintext = "the quick brown fox jumped over the lazy dogs";
//		String correctCipherText = "KyvaHLztBasIFNEawFOaALDGvuaFMvIaKyvaCrQPauFxJ";
//		String testPlainText = Cipher.rotationCipherDecrypt(correctCipherText, 100);
//		assertEquals(testPlainText, plaintext);
//	}
	
//	@Test
//	public void rotationCipherEncryptBy100() {
//		String plaintext = "the quick brown fox jumped over the lazy dogs";
//		String correctCipherText = "KyvaHLztBasIFNEawFOaALDGvuaFMvIaKyvaCrQPauFxJ";
//		String testCipherText = Cipher.rotationCipherEncrypt(plaintext, 100);
//		assertEquals(testCipherText, correctCipherText);
//	}
//	
	@Test
	public void rotationCipherEncryptBy3CapsWithPunctuation() {
		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
		String correctCipherText = "]WKHbcTXLFNbEURZQ!IR0 !MXPSHG!RYHU!WKH!OD21!GRJV/]";
		String testCipherText = Cipher.rotationCipherEncrypt(plaintext, 3);
		assertEquals(testCipherText, correctCipherText);
	}
	
	@Test
	public void rotationCipherDecryptBy3CapsWithPunctuation() {
		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
		String correctCipherText = "]WKHbcTXLFNbEURZQ!IR0 !MXPSHG!RYHU!WKH!OD21!GRJV/]";
		String testPlainText = Cipher.rotationCipherDecrypt(correctCipherText, 3);
		assertEquals(testPlainText, plaintext);
	}
	
//	@Test
//	public void rotationCipherDecryptBy100CapsWithPunctuation() {
//		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
//		String correctCipherText = "c,YVpq7.ZT1pS85)4aW5 :a0.36VUa5(V8a,YVa2R\"'aU5X9dc";
//		String testPlainText = Cipher.rotationCipherDecrypt(correctCipherText, 100);
//		assertEquals(testPlainText, plaintext);
//	}
	
//	@Test
//	public void rotationCipherEncryptBy100CapsWithPunctuation() {
//		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
//		String correctCipherText = "c,YVpq7.ZT1pS85)4aW5 :a0.36VUa5(V8a,YVa2R\"'aU5X9dc";
//		String testCipherText = Cipher.rotationCipherEncrypt(plaintext, 100);
//		assertEquals(testCipherText, correctCipherText);
//	}
//	
	@Test
	public void rotationCipherTest1() {
		String test = "This is a rotation \n ;[] !cipher test to see if the method works!";
		String cipher = Cipher.rotationCipherEncrypt(test, 4);
		String decoded = Cipher.rotationCipherDecrypt(cipher, 4);
		assertEquals(test, decoded);
	}
	
	
	@Test
	public void rotationCipherTest2() {
		String test = "I am writing this as a long test for the method that we wrote."
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
				+ "output up too much. Yes! This method works again!\n";
		int shiftAmount = -4;
		String cipher = Cipher.rotationCipherEncrypt(test, shiftAmount);
		String decoded = Cipher.rotationCipherDecrypt(cipher, shiftAmount);
		assertEquals("I am writing this as a long test for the method that we wrote."
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
				+ "output up too much. Yes! This method works again!\n", decoded);
	}
	
	
	@Test
	public void substitutionCipherTest() {
		String answer = Cipher.substitutionCipherEncrypt("abe", new int[] {3,2,1,12,20,16});
		assertEquals("ddf", answer);
	}
	
	@Test
	public void substitutionCipherTest1() {
		String answer = Cipher.substitutionCipherEncrypt("abcz", new int[] {-1,2,4,1,20,0}, "abcdefghijklmnopqrstuvwxyz");
		assertEquals("zdga", answer);
	}
	
	@Test
	public void substitutionCipherTest2() {
		String answer = Cipher.substitutionCipherEncrypt("a", new int[] {19,14,4,1,20,0}, "abcdefghijklmnopqrstuvwxyz");
		assertEquals("t", answer);
	}
	
	@Test
	public void substitutionCipherDecrypt() {
		String answer = Cipher.substitutionCipherDecrypt("t", new int[] {19,14,4,1,20,0}, "abcdefghijklmnopqrstuvwxyz");
		assertEquals("a", answer);
	}
	
	@Test
	public void vigenereCipherTest() {
		String answer = Cipher.vigenereCipherEncrypt("helpmeiamunderattack", "code", "abcdefghijklmnopqrstuvwxyz");
		assertEquals("jsotosleoiqhgfdxvofo", answer);
	}
	
	@Test
	public void vigenereCipherTest1() {
		String answer = Cipher.vigenereCipherEncrypt("avinozik", "ten", "abcdefghijklmnopqrstuvwxyz");
		assertEquals("tzvgsmbo", answer);
	}
	
	@Test
	public void breakWords() {
		String[] answer = Cipher.getWords("hi there buddy");
		String[] actualAnswers = new String[] {"hi", "there", "buddy"};
		assertEquals(actualAnswers[0], answer[0]);
		assertEquals(actualAnswers[1], answer[1]);
		assertEquals(actualAnswers[2], answer[2]);
	}
	
	@Test
	public void vigenereCipherDecryptTest1() {
		String test = Cipher.vigenereCipherEncrypt("avinozik", "ten", "abcdefghijklmnopqrstuvwxyz");
		String answer = Cipher.vigenereCipherDecrypt(test, "ten", "abcdefghijklmnopqrstuvwxyz");
		assertEquals("avinozik", answer);
	}
	
	@Test
	public void crackVigenereCipher2Test1() {
		String test = Cipher.vigenereCipherEncrypt("hello there buddy", "zi");
		String answer = Cipher.crackVigenereCipher2(test);
		assertEquals("hello there buddy", answer);
	}
	
	@Test
	public void crackVigenereCipher3Test1() {
		String test = Cipher.vigenereCipherEncrypt("hello there buddy", "ezi");
		String answer = Cipher.crackVigenereCipher3(test);
		assertEquals("hello there buddy", answer);
	}
	
	@Test
	public void crackVigenereCipher2Test2() {
		String test = Cipher.vigenereCipherEncrypt("hi", "EZ");
		assertEquals("L7", test);
		String test1 = Cipher.vigenereCipherDecrypt(test, "EZ");
		assertEquals("hi", test1);
	}
	
	@Test
	public void crackVigenereCipher2Test3() {
		String test = Cipher.vigenereCipherEncrypt("hello there people", "EZ");
		String answer = Cipher.crackVigenereCipher2(test);
		assertEquals("hello there people", answer.toLowerCase());
	}
	
	@Test
	public void crackRotationCipherTest1() {
		String test = Cipher.rotationCipherEncrypt("I am writing this as a long test for the method that we wrote."
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
				+ "output up too much. Yes! This method works again!\n", 8);
		String answer = Cipher.rotationCipherCrack(test);
		assertEquals("I am writing this as a long test for the method that we wrote."
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
				+ "output up too much. Yes! This method works again!\n", answer);
	}
	
	@Test
	public void crackRotationCipherTest2() {
		String test = Cipher.rotationCipherEncrypt("I am writing this as a long test for the method that we wrote."
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
				+ "output up too much. Yes! This method works again!\n", 23);
		String answer = Cipher.rotationCipherCrack(test);
		assertEquals("I am writing this as a long test for the method that we wrote."
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
				+ "output up too much. Yes! This method works again!\n", answer);
	}

	@Test
	public void crackRotationCipherTest3() {
		String test = Cipher.rotationCipherEncrypt("This is one final test to see if the decryption works with"
				+ " a shift value", -8);
		String answer = Cipher.rotationCipherDecrypt(test, -8);
		assertEquals("This is one final test to see if the decryption works with a shift value", answer);
	}
	
	@Test
	public void getCorrespondingLetterTest1() {
		String[] answer = Cipher.getCorrespondingLetters("hi there", 2);
		assertEquals("h hr", answer[0]);
		assertEquals("itee", answer[1]);
	}
	
	@Test
	public void getCorrespondingLetterTest2() {
		String[] answer = Cipher.getCorrespondingLetters("Hi There Buddy", 3);
		assertEquals("HTrBd", answer[0]);
		assertEquals("iheuy", answer[1]);
		assertEquals(" e d", answer[2]);
	}
	
	@Test
	public void combineLettersTest1() {
		String[] test = Cipher.getCorrespondingLetters("Hi There Buddy", 2);
		String answer = Cipher.combineLetters(test, 14);
		assertEquals("Hi There Buddy", answer);
	}
	
	@Test
	public void crackVigenereByFreqTest1() {
		String cipher = Cipher.vigenereCipherEncrypt("Hello there my friend my buddy everything should work today this"
				+ " will be the most common thing today I realize that I need to add a lot more text in order"
				+ " for this test to work. The String only failed because I did not have enough text to fill up"
				+ " the needs of this method test string test string test string test string test string I need to make"
				+ " sure that this works since it must be long enough to test. How can I make sure it works", "abc");
		String answer = Cipher.vigenereCipherCrack(cipher, 3);
		assertEquals("Hello there my friend my buddy everything should work today this"
				+ " will be the most common thing today I realize that I need to add a lot more text in order"
				+ " for this test to work. The String only failed because I did not have enough text to fill up"
				+ " the needs of this method test string test string test string test string test string I need to make"
				+ " sure that this works since it must be long enough to test. How can I make sure it works", answer);
	}
	
	@Test
	public void crackVigenereByFreqTest2() {
		//no punctuation marks
		String cipher = Cipher.vigenereCipherEncrypt("I am writing this as a long test for the method that we wrote"
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation "
				+ "How many times will this method work Will it always be able to crack long texts like this one "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts As long as the text is long enough "
				+ "it should work", "abc");
		String answer = Cipher.vigenereCipherCrack(cipher, 3);
		assertEquals("I am writing this as a long test for the method that we wrote"
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation "
				+ "How many times will this method work Will it always be able to crack long texts like this one "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts As long as the text is long enough "
				+ "it should work", answer);
	}
	
	
	@Test
	public void crackVigenereByFreqTest3() {
		//punctuation marks
		String cipher = Cipher.vigenereCipherEncrypt("I am writing this as a long test for the method that we wrote."
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
				+ "output up too much. Yes! This method works again!\n", "abc");
		String answer = Cipher.vigenereCipherCrack(cipher, 3);
		assertEquals("I am writing this as a long test for the method that we wrote."
				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
				+ "This will be interesting if it ends up working. This will prove that the current method "
				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
				+ "output up too much. Yes! This method works again!\n", answer);
	}
	
	@Test
	public void crackVigenereByFreqTest4() {
		//punctuation marks
		
		String answer = Cipher.vigenereCipherCrack(
				IOMobilizer.readFileAsString("/Users/naman/Documents/APCS-workspace/Cipher/cipher2.txt"), 2);
		System.out.println(answer);
	}
	
	@Test
	public void crackVigenere() {
		//punctuation marks
		String test = Cipher.vigenereCipherEncrypt("I am writing this as a long test for the method that we wrote."
						+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
						+ "How many times will this method work? Will it always be able to crack long texts like this one? "
						+ "This will be interesting if it ends up working. This will prove that the current method "
						+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
						+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
						+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
						+ "output up too much. Yes! This method works again!\n", "abc");
		String answer = Cipher.vigenereCipherCrack(test);
		System.out.println(answer);
	}
	
//	@Test
//	public void crackVigenereByFreqTest4() {
//		//punctuation marks
//		String cipher = Cipher.vigenereCipherEncrypt("I am writing this as a long test for the method that we wrote."
//				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
//				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
//				+ "This will be interesting if it ends up working. This will prove that the current method "
//				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
//				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
//				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
//				+ "output up too much. Yes! This method works again!", "abc");
//		String answer = Cipher.crackVigenereByFreq(cipher, 2);
//		assertEquals("I am writing this as a long test for the method that we wrote."
//				+ "Hopefully the method will end up working even though this entry string has a lot of puncuation. "
//				+ "How many times will this method work? Will it always be able to crack long texts like this one? "
//				+ "This will be interesting if it ends up working. This will prove that the current method "
//				+ "is a good one and will succeed in almost all texts. As long as the text is long enough "
//				+ "it should work. I will emphasize the long sentances in order to validate the fact that the "
//				+ "punctuation is not causing the test to always fail. The question marks and periods were messing the "
//				+ "output up too much. Yes! This method works again!", answer);
//	}
	
	@Test
	public void validTest1() {
		LetterBag bag = new LetterBag();
		
		String cipher = Cipher.vigenereCipherEncrypt("Hello there my friend my buddy everything should work today this"
				+ " will be the most common thing today I realize that I need to add a lot more text in order"
				+ " for this test to work. The String only failed because I did not have enough text to fill up"
				+ " the needs of this method test string test string test string test string test string I need to make"
				+ " sure that this works since it must be long enough to test. How can I make sure it works", "abc");
		
		String[] arr = Cipher.getCorrespondingLetters(cipher, 3);
			
		assertEquals(true, Cipher.isEnglishFreq(Cipher.vigenereCipherDecrypt(arr[0], "a")));
		assertEquals(true, Cipher.isEnglishFreq(Cipher.vigenereCipherDecrypt(arr[1], "b")));
		assertEquals(true, Cipher.isEnglishFreq(Cipher.vigenereCipherDecrypt(arr[2], "c")));
		
	}
	
//	@Test
//	public void crackGeneralVigenereFreqTest1() {
//		
//		String cipher = Cipher.vigenereCipherEncrypt("Hello there my friend my buddy everything should work today this"
//				+ " will be the most common thing today I realize that I need to add a lot more text in order"
//				+ " for this test to work. The String only failed because I did not have enough text to fill up"
//				+ " the needs of this method test string test string test string test string test string I need to make"
//				+ " sure that this works since it must be long enough to test. How can I make sure it works", "def");
//		
//		String answer = Cipher.crackGeneralVigenereCipherFreq(cipher);
//			
//		assertEquals("Hello there my friend my buddy everything should work today this"
//				+ " will be the most common thing today I realize that I need to add a lot more text in order"
//				+ " for this test to work. The String only failed because I did not have enough text to fill up"
//				+ " the needs of this method test string test string test string test string test string I need to make"
//				+ " sure that this works since it must be long enough to test. How can I make sure it works", answer);
//		
//	}
	
	@Test
	public void scoreTest1() {
		LetterBag bag = new LetterBag();
		bag.addString("Hello there my friend my buddy everything should work today this"
				+ " will be the most common thing today I realize that I need to add a lot more text in order"
				+ " for this test to work. The String only failed because I did not have enough text to fill up"
				+ " the needs of this method test string test string test string test string test string I need to make"
				+ " sure that this works since it must be long enough to test. How can I make sure it works");
		
		System.out.println(Cipher.getSimilarityToEnglishScore(bag));
		
	}
	
	@Test
	public void scoreTest2() {
		LetterBag bag = new LetterBag();
		bag.addString("abc");
		
		System.out.println(Cipher.getSimilarityToEnglishScore(bag));
		
	}
	

	
}
