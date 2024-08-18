package SpellingChecker;

import java.util.Map;
import java.util.HashMap;

//Tire Class implimentation 
public class Trie {

    private class TrieNode {

        Map<Character, TrieNode> children;
        boolean endOfWord;

        public TrieNode() {
            children = new HashMap<>();
            endOfWord = false;
        }

    }

    private final TrieNode root;

    public Trie() {

        root = new TrieNode();
    }

    /**
     * Inserts a word into the Trie structure character by character.
     *
     * @param word The word to be inserted into the Trie.
     */
    public void insert(String word) {

        TrieNode currentNode = root;

        for (int i = 0; i < word.length(); i++) {
            char charOfWord = word.charAt(i);
            TrieNode node = currentNode.children.get(charOfWord);
            if (node == null) {
                node = new TrieNode();
                currentNode.children.put(charOfWord, node);
            }
            currentNode = node;
        }
        currentNode.endOfWord = true;
    }

    /**
     * Checks if the complete word exists in the Trie.
     *
     * @param word The word to search for in the Trie.
     * @return True if the word exists in the Trie; otherwise, false.
     */
    public boolean search(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char charOfWord = word.charAt(i);
            TrieNode node = currentNode.children.get(charOfWord);
            if (node == null) {
                return false;
            }
            currentNode = node;
        }
        return currentNode.endOfWord;
    }

    /**
     * Finds the closest word in the Trie by traversing the Trie structure.
     *
     * @param word The word for which to find the closest match.
     * @return The closest matching word in the Trie.
     */
    public String getClosest(String word) {
        TrieNode currentNode = root;
        String closest = "";

        for (int i = 0; i < word.length(); i++) {
            char charOfWord = word.charAt(i);
            TrieNode node = currentNode.children.get(charOfWord);
            if (node == null) {
                break;
            }
            currentNode = node;
            closest += charOfWord;
        }
        if (currentNode.endOfWord) {
            return closest;
        }
        return DFS(currentNode, closest);
    }

    /**
     * Depth-first search (DFS) method to find the closest matching word in the
     * Trie.
     *
     * @param node        The current Trie node being processed.
     * @param wordBuilder A StringBuilder to build the current word.
     * @return The closest matching word found in the Trie.
     */
    private String DFS(TrieNode node, String word) {

        if (node.endOfWord) {
            return word;
        }
        String closest = "";
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            String tempWord = DFS(entry.getValue(), word + entry.getKey());

            if (closest.length() == 0 || tempWord.length() < closest.length()) {
                closest = tempWord;
            }
        }
        return closest;
    }
}