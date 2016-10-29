
public class LetterBag {
	private static final String ALPHABET = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '" + "\"" + "![]/%-_;?-=:" + '\n' + '\r');

	private int[] letterFrequencies;
	
	public LetterBag() {
		letterFrequencies = new int[ALPHABET.length()]; //one per letter
	}
	
	/**
	 * Adds a letter to the letter bag
	 * @param letter the letter to be added
	 */
	public void add(String letter) {
		int index = getIndexForLetter(letter);
		letterFrequencies[index]++;
	}
	
	/**
	 * Adds a string to the letter bag
	 * @param str the string containing the letters to be added
	 */
	public void addString(String str) {
		int strLength = str.length();
		
		for (int i = 0; i < strLength; i++) {
			int index = getIndexForLetter(str.substring(i, i+1));
			letterFrequencies[index]++;
		}
	}
	
	private int getIndexForLetter(String lower) {
		return ALPHABET.indexOf(lower);
	}
	
	/**
	 * Returns the total number of items in the bag
	 * @return the total number of items in the bag
	 */
	public int getTotalWords() {
		int totalLetters = 0, alphabetLength = ALPHABET.length();
		
		for (int i = 0; i < alphabetLength; i++)
			totalLetters += letterFrequencies[i];
		
		return totalLetters;
	}
	
	/**
	 * Returns the total unique number of items in the bag
	 * @return the total unique number of items in the bag
	 */
	public int getNumUniqueWords() {
		int totalUniqueLetters = 0, alphabetLength = ALPHABET.length();
		
		for (int i = 0; i < alphabetLength; i++)
			if (letterFrequencies[i] > 0) totalUniqueLetters++;
		
		return totalUniqueLetters;
	}
	
	/**
	 * Returns the number of times a letter occurs
	 * @param letter the letter to search for
	 * @return the number of times the letter occurred in the bag
	 */
	public int getNumOccurrences(String letter) {
		int index = getIndexForLetter(letter.toLowerCase());
		return letterFrequencies[index];
	}
	
	/**
	 * Returns the letter that is most frequent
	 * @return the letter that is most Frequent
	 */
	public String getMostFrequent() {
		int mostFrequent = 0, mostFrequentIndex = 0, alphabetLength = ALPHABET.length();
		
		for (int i = 0; i < alphabetLength; i++) 	//loop through all the letter frequencies and find the most common one
			if (letterFrequencies[i] > mostFrequent) {
				mostFrequentIndex = i;
				mostFrequent = letterFrequencies[i];
			}
		
		return ALPHABET.substring(mostFrequentIndex, mostFrequentIndex+1);
	}
	
	/**
	 * Returns n letters that are most frequent in order from most frequent to least frequent
	 * @return n letters that are most frequent in order from most frequent to least frequent
	 */
	public String getNMostFrequent(int num) {
		int[] letterFrequenciesTemp = letterFrequencies;				//store a copy to remember the bag contents
		StringBuilder nMostFrequent = new StringBuilder();
		
		nMostFrequent.append("[");										//printing format
		
		for (int i = 0; i < num; i++) {									//loop through all the letters in the ALPHABET
			String mostFrequentLetter = getMostFrequent();				//find the most frequent letter
			if (i != num-1)
				nMostFrequent.append(mostFrequentLetter + ", ");		//add the most frequent letter into the output
			else 
				nMostFrequent.append(mostFrequentLetter + "]");
			letterFrequencies[ALPHABET.indexOf(mostFrequentLetter)] = 0;//remove all the repeats of the letter
		}
		letterFrequencies = letterFrequenciesTemp;						//place the most frequent letters back into the bag
		
		return nMostFrequent.toString();
	} 
	
	/**
	 * Returns n letters that are most frequent in order from most frequent to least frequent
	 * @return n letters that are most frequent in order from most frequent to least frequent
	 */
	public String[] getNMostFrequentArray(int num) {
		int[] letterFrequenciesTemp = letterFrequencies;				//store a copy to remember the bag contents
		String[] mostFrequent = new String[num];
		
		
		for (int i = 0; i < num; i++) {									//loop through all the letters in the ALPHABET
			String mostFrequentLetter = getMostFrequent();				//find the most frequent letter
			mostFrequent[i] = mostFrequentLetter;
			letterFrequencies[ALPHABET.indexOf(mostFrequentLetter)] = 0;//remove all the repeats of the letter
		}
		letterFrequencies = letterFrequenciesTemp;						//place the most frequent letters back into the bag
		
		return mostFrequent;
	} 
	
	/**
	 * Removes all the items in the bag
	 */
	public void clear() {
		letterFrequencies = new int[ALPHABET.length()];
	}
	
}
