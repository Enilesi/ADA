import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.io.File;
import java.io.FileNotFoundException;


class SimpleTrieTree {
    private final int R = 256; // R - size of alphabet.
    // in this example keys(words) are sequences of characters from extended ASCII

    private TrieNode root;      // root of trie tree

    // R-way trie node
    private class TrieNode {
        private Integer val;  // if current node contains end of a key(end of a word)
        // then it has a non-null val associated
        private final TrieNode[] next = new TrieNode[R]; // may have R children
    }

    /**
     * Initializes an empty trie tree
     */
    public SimpleTrieTree() {
        root = null;
    }


    /**
     * Inserts a word into the trie tree - the recursive implementation
     *
     * @param key the word to be inserted
     * @param val the value associated with the word
     */
    public void put(String key, Integer val) {
        if ((key == null) || (val == null)) throw new IllegalArgumentException("key or val argument is null");
        else root = put(root, key, val);
    }

    private TrieNode put(TrieNode x, String key, Integer val) {
        if (x == null) x = new TrieNode();
        if (key.equals("")) {
            x.val = val;
            return x;
        }
        char c = key.charAt(0);
        String restkey = "";
        if (key.length() > 1) restkey = key.substring(1);
        x.next[c] = put(x.next[c], restkey, val);
        return x;
    }

    /**
     * Inserts a word into the trie tree - the iterative version
     *
     * @param key the word to be inserted
     * @param val the value associated with the word
     */
    public void put_v2(String key, Integer val) {
        if (root == null) {
            root = new TrieNode();
        }
        TrieNode node = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (node.next[c] == null) {
                node.next[c] = new TrieNode();
            }
            node = node.next[c];
        }
        node.val = val;
    }


    public boolean contains(String key){
        TrieNode node=findPrefix(root,key);
        if (node==null) return false;
        if(node.val==null) return false;
        return true;
    }

    public void printAllKeys() {
        if(root == null) return;
        printAllKeysWithPrefix(root, "");
    }

    private void printAllKeysWithPrefix(TrieNode node, String prefix) {
        if(node == null) return;
        if(node.val!=null) System.out.println(prefix);
        for (char c = 0; c < R; c++) {
            if (node.next[c] != null) {
                printAllKeysWithPrefix(node.next[c], prefix + c);
            }
        }

    }

    private TrieNode findPrefix(TrieNode x, String prefix) {
        for (int i = 0; i < prefix.length(); i++) {
            if (x == null) return null;
            x = x.next[prefix.charAt(i)];
        }
        return x;
    }


    public void printAllWithPrefix(String prefix) {
        TrieNode node = findPrefix(root, prefix);
        if(node == null) return;
        printAllKeysWithPrefix(node, prefix);
    }

    public String findFirstWord(){
        if (root == null) return null;
        return findFirstWord(root,"");
    }

    private String findFirstWord(TrieNode node, String prefix) {
        if (node == null) return null;
        if(node.val!=null) return prefix;
        for(char c=0; c < R; c++) {
            String resultedWord = findFirstWord(node.next[c], prefix + c);
            if (resultedWord != null) return resultedWord;
        }
        return null;
    }

    public String findLastWord(){
        if (root == null) return null;
        return findLastWord(root, "");
    }

    private String findLastWord(TrieNode node, String prefix) {
        if (node == null) return null;
        String resultedWord = null;
        for (int c = R - 1; c >= 0; c--) {
            String res = findLastWord(node.next[c], prefix + (char)c);
            if (res != null) {
                resultedWord = res;
                break;
            }
        }
        if (resultedWord != null) return resultedWord;
        if (node.val != null) return prefix;
        return null;
    }


    public String findLongestWord(){
        if (root == null) return null;
        return findLongestWord(root,"","");
    }

    private String findLongestWord(TrieNode node, String prefix,String maxi) {
        if (node == null) return maxi;
        if (node.val != null && prefix.length() > maxi.length()) maxi = prefix;
        for (char c = 0; c < R; c++) {
            maxi = findLongestWord(node.next[c], prefix + c, maxi);
        }
        return maxi;
    }

    public String findShortestWord() {
        if (root == null) return null;
        return findShortestWord(root, "", null);
    }

    private String findShortestWord(TrieNode node, String prefix, String mini) {
        if (node == null) return mini;
        if (node.val != null && (mini == null || prefix.length() < mini.length())) mini = prefix;

        for (char i = 0; i < R; i++) {
            if (node.next[i] != null) {
                mini = findShortestWord(node.next[i], prefix +i, mini);
            }
        }
        return mini;
    }

    public Iterable<String> getAllKeys(){
        if (root == null) return null;
        Set<String> keys = new TreeSet<>();
        getAllKeys(root, "",keys);
        return keys;
    }
    private void getAllKeys(TrieNode node, String prefix, Set<String> keys){
        if (node == null) return;
        if (node.val!=null) keys.add(prefix);
        for (char c = 0; c < R; c++) {
            getAllKeys(node.next[c], prefix + c, keys);
        }
    }

    Iterable<String> getAllKeysWithPrefix(String prefix){
        if (root == null) return null;
        TrieNode node = findPrefix(root, prefix);
        Set<String> keys = new TreeSet<>();
        getAllKeys(node, prefix,keys);
        return keys;
    }

    int countAllKeysWithPrefix(String prefix){
        if (root == null) return 0;
        Iterable<String> keys=getAllKeysWithPrefix(prefix);
        int count = 0;
        for (String key : keys) {
            count++;
        }
        return count;

    }

    public void removeWord(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        root = removeWord(root, key, 0);
    }

    private TrieNode removeWord(TrieNode node, String key, int d) {
        if (node == null) return null;
        if (d == key.length()) {
            node.val = null;
        } else {
            char c = key.charAt(d);
            node.next[c] = removeWord(node.next[c], key, d + 1);
        }
        if (node.val != null) return node;
        for (int c = 0; c < R; c++) {
            if (node.next[c] != null) return node;
        }
        return null;
    }

    public void initFromFile(String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            int i = 0;
            while (sc.hasNextLine()) {
                put(sc.nextLine(), i++);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void printAllKeysToFile(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            printAllKeysToFile(root, "", writer);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printAllKeysToFile(TrieNode node, String prefix, PrintWriter writer) {
        if (node == null) return;
        if (node.val != null) writer.println(prefix);
        for (int c = 0; c < R; c++) {
            printAllKeysToFile(node.next[c], prefix + (char)c, writer);
        }
    }

    void hardcodeTrieTree(){
        root = new TrieNode();

        root.next['t'] = new TrieNode();
        root.next['t'].next['o'] = new TrieNode();
        root.next['t'].next['o'].val = 7;

        root.next['t'].next['e'] = new TrieNode();
        root.next['t'].next['e'].next['a'] = new TrieNode();
        root.next['t'].next['e'].next['a'].val = 3;

        root.next['t'].next['e'].next['d'] = new TrieNode();
        root.next['t'].next['e'].next['d'].val = 4;

        root.next['t'].next['e'].next['n'] = new TrieNode();
        root.next['t'].next['e'].next['n'].val = 12;

        root.next['i'] = new TrieNode();
        root.next['i'].val = 11;

        root.next['i'].next['n'] = new TrieNode();
        root.next['i'].next['n'].val = 5;

        root.next['i'].next['n'].next['n'] = new TrieNode();
        root.next['i'].next['n'].next['n'].val = 9;
    }
















    public static void main(String[] args) {

        String[] inputs = {"bar", "sea", "sunday", "bark", "ban", "bandage", "sun", "banana", "band"};

        SimpleTrieTree st = new SimpleTrieTree();


        for (int i = 0; i < inputs.length; i++) {
            String key = inputs[i];
            st.put(key, i);
            System.out.println("Have inserted into trie tree key="+key+" with value="+i);
        }

        System.out.println("All  keys in ascending order:");


        String testprefix = "ban";
        System.out.println("keysWithPrefix " + testprefix);
        st.printAllWithPrefix(testprefix);   //Keys with prefix ban: ban banana band bandage

         System.out.println(st.contains("banana"));  //true
         System.out.println(st.contains("blabla"));  //false
         System.out.println(st.contains("ban"));     //true
         System.out.println(st.contains("ba"));      //false

        System.out.println(st.findFirstWord());
        System.out.println(st.findLastWord());
        System.out.println(st.findLongestWord());
        System.out.println(st.findShortestWord());
        System.out.println(st.getAllKeys());
        System.out.println(st.getAllKeysWithPrefix("ba"));
        System.out.println(st.countAllKeysWithPrefix("ba"));
        st.removeWord("ban");
        st.printAllKeys();

        System.out.println("\n");


        SimpleTrieTree st2 = new SimpleTrieTree();
        st2.initFromFile("random_words.txt");
//        st2.printAllKeysToFile("output.txt");

        System.out.println("first word in ascending order from random_words.txt: " + st2.findFirstWord());//aardvark
        System.out.println("last word in ascending order from random_words.txt: " + st2.findLastWord());//zulus
        System.out.println("longest word of from random_words.txt: " + st2.findLongestWord());//counterrevolutionaries
        System.out.println("number of words starting with prefix 'ban' from random_words.txt: " + st2.countAllKeysWithPrefix("ban"));//77


        System.out.println("\n");
        SimpleTrieTree givenTree = new SimpleTrieTree();

        givenTree.hardcodeTrieTree();
        givenTree.printAllKeys();


    }
}
