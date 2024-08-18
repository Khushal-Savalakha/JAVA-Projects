/**
 * The SpellChecker class leverages a Trie-based dictionary to efficiently check and correct spelling errors 
 * in a given input string. Each word is validated against the dictionary, and misspelled words are 
 * highlighted with ANSI escape codes for red text. For each incorrect word, the class also suggests 
 * the closest matching correct word, which is displayed in blue. This class provides a seamless and 
 * visually intuitive spell-checking experience within the terminal.
 * 
 * Features:
 * - Efficient word lookup using a Trie data structure.
 * - Real-time spelling error detection and correction suggestions.
 * - Color-coded output for clear identification of errors and corrections.
 * @version 1.2
 * @author Khushal Savalakha
 * @since 2023-09-10
 */

package SpellingChecker;

public class SpellChecker {
    private static Trie dictionary;

    /**
     * Initializes the SpellChecker with a Trie-based dictionary.
     *
     * @param trie The Trie containing the dictionary of correctly spelled words.
     */
    public SpellChecker(Trie trie) {
        SpellChecker.dictionary = trie;
    }

    // ANSI escape codes for setting colors while printing words
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_Pink = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * Checks the spelling of words in the input string and prints the result.
     *
     * @param input The input string to be spell-checked.
     */
    public void check(String input) {
        // Splitting the input string into words
        String[] words = input.split("\\s+");
        // Printing the spell checked words
        printCheckedTrie(words);
    }

    /**
     * Prints the spell-checked words along with the closest correct word.
     *
     * @param words An array of words to be spell-checked.
     */
    public static void printCheckedTrie(String[] words) {
        int m = 0;
        for (String word : words) {
            // Checking if the word exists in the dictionary
            if (!dictionary.search(word)) {
                // If the word doesn't exist, print the word in red
                System.out.print(ANSI_Pink + word + " " + ANSI_RESET);
                m++;
            } else {
                // If the word exists, print the word in blue
                System.out.print(ANSI_BLUE + word + " " + ANSI_RESET);
            }
        }
        System.out.println();
        if (m == 0)
            System.out.println("No mistakes, you're good.");
        else {
            // Printing the header for the possible corrections for misspelled words
            System.out.println(ANSI_YELLOW + "**Possible Corrections for misspelled words**" + ANSI_RESET);
            // Iterating through each word in the input
            for (String word : words) {
                // Checking if the word exists in the dictionary
                if (!dictionary.search(word)) {
                    // If the word doesn't exist, print the misspelled word in red along with the
                    // closest correct word in blue
                    System.out.println(ANSI_Pink + word + ANSI_RESET + " --> " + ANSI_BLUE + dictionary.getClosest(word)
                            + ANSI_RESET);
                }
            }
        }
    }
}
