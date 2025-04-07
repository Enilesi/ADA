import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.AbstractMap;

interface MySortedMap <Key extends Comparable<Key>, Value>{

    boolean containsKey(Key key);

    Value get(Key key);

    void put(Key key, Value val);

    void remove(Key key);

    Iterable<Key> getKeys();

    Iterable<Map.Entry<Key,  Value>> getEntries();
}

class MyAVLMap<Key extends Comparable<Key>, Value> implements MySortedMap<Key, Value> {
    private Node root;

    private class Node {
        private final Key key;
        private Value val;
        private int height;
        private Node left, right;

        public Node(Key key, Value val, int height) {
            this.key = key;
            this.val = val;
            this.height = height;
        }
    }

    public MyAVLMap() {
        root = null;
    }

    @Override
    public boolean containsKey(Key key) {
        return containsKey(key, root);
    }

    private boolean containsKey(Key key, Node node) {
        if (node == null) return false;
        if (node.key.compareTo(key) < 0) return containsKey(key, node.right);
        if (node.key.compareTo(key) > 0) return containsKey(key, node.left);
        return true;
    }

    @Override
    public Value get(Key key){
        if (key == null) return null;
        if(root==null) return null;
        return get(key,root);
    }

    private Value get(Key key, Node node){
        if (node == null) return null;
        if (node.key.compareTo(key) < 0) return get(key, node.right);
        if (node.key.compareTo(key) > 0) return get(key, node.left);
        return node.val;
    }

    @Override
    public Iterable<Key> getKeys() {
        Queue<Key> q= new LinkedList<Key>();
        keys(root, q);
        return q;
    }

    private void keys(Node x, Queue<Key> q) {
        if (x==null) return;
        keys(x.left, q);
        q.add(x.key);
        keys(x.right,q);
    }

    @Override
    public Iterable<Map.Entry<Key, Value>> getEntries() {
        Queue<Map.Entry<Key, Value>> q= new LinkedList<Map.Entry<Key, Value>>();
        entries(root,q);
        return q;
    }

    private void entries (Node x, Queue<Map.Entry<Key,Value>> q){
        if (x==null) return;
        entries(x.left, q);
        q.add(new AbstractMap.SimpleEntry<>(x.key, x.val));
        entries(x.right,q);
    }

    @Override
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            remove(key);
            return;
        }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 0);
        if (key.compareTo(x.key)<0) {
            x.left = put(x.left, key, val);
        } else if (key.compareTo(x.key)>0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
            return x;
        }
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return x.height;
    }

    private Node balance(Node x) {
        if (balanceFactor(x) < -1) {
            if (balanceFactor(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        } else if (balanceFactor(x) > 1) {
            if (balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }

    private int balanceFactor(Node x) {
        return height(x.left) - height(x.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    @Override
    public void remove(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
//        here I don't know whether you would have been prefered to add return; or throw an exception
//        so I throwed an exception taking as example previous methods where key might have been null
        root = remove(root, key);
    }

    private Node remove(Node x, Key key) {
        if (x == null) return null;
        if (key.compareTo(x.key) < 0) x.left = remove(x.left, key);
        else if (key.compareTo(x.key) > 0) x.right = remove(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            Node y = min(x.right);
            y.right = remove(x.right, y.key);
            y.left = x.left;
            return balance(y);
        }
        return balance(x);
    }



    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key firstKey(){
        if (root == null) return null;
        return firstKey(root);
    }

    private Key firstKey ( Node node){
        if (node.left == null) return node.key;
        return firstKey(node.left);
    }

    public Key lastKey(){
        if (root == null) return null;
        return lastKey(root);
    }

    private Key lastKey ( Node node){
        if (node.right == null) return node.key;
        return lastKey(node.right);
    }
}

class MyAVLMapClient {

    private static String getRandomAlphaString(int n)
    {
        String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int)(Alphabet.length() * Math.random());
            sb.append(Alphabet.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MySortedMap<Integer, Integer> map1= new MyAVLMap<Integer, Integer>();
        MySortedMap<Integer, String> map2 = new MyAVLMap<Integer, String>();

        SortedMap<Integer, Integer> map1j= new TreeMap<Integer, Integer>();
        SortedMap<Integer, String> map2j = new TreeMap<Integer, String>();

        Integer intVals[] = {23, 1, 5, 6, 10, 41, 34, 99 };

        for (int i=0; i<intVals.length; i++) {
            int rand_int1 = intVals[i];
            map1.put(rand_int1, 2*rand_int1);
            map1j.put(rand_int1, 2*rand_int1);
            String aValue = getRandomAlphaString(4);
            map2.put(rand_int1, aValue );
            map2j.put(rand_int1, aValue );
        }

        System.out.println("Keys of map1j Java's TreeMap in ascending order: ");
        for(Integer i: map1j.keySet()){
            System.out.print(" "+i);
        }
        System.out.println("\n");

        System.out.println("Keys of map1 MySortedMap in ascending order: ");
        for(Integer i:map1.getKeys()){
            System.out.print(" "+i);
        }
        System.out.println("\n");

        System.out.println("Entries of map2j Java's TreeMap in ascending order: ");
        for(Map.Entry<Integer, String> s:map2j.entrySet()){
            System.out.print(" "+s);
        }
        System.out.println("\n");

        System.out.println("Entries of map2 MySortedMap in ascending order: ");
        for(Map.Entry<Integer, String> s:map2.getEntries()){
            System.out.print(" "+s);
        }
        System.out.println("\n");

        int toDelete = intVals[2];

        System.out.println("Delete key "+toDelete+ " from map2j Java's TreeMap ");
        map2j.remove(toDelete);
        System.out.println("Entries of map2j after deleting: ");
        for(Map.Entry<Integer, String> s:map2j.entrySet()){
            System.out.print(" "+s);
        }
        System.out.println("\n");

        System.out.println("Delete key "+toDelete+ " from map2 MyTreeMap ");
        map2.remove(toDelete);
        System.out.println("Entries of map2 after deleting: ");
        for(Map.Entry<Integer, String> s:map2.getEntries()){
            System.out.print(" "+s);
        }
        System.out.println("\n");
    }
}
