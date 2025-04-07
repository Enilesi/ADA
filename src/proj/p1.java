package proj;

class Node<K, V> {
    K key;
    V value;
    Node<K, V> left, right;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

class IndexTree {
    private Node<Character, Integer> root;

    public IndexTree() {
        root = null;
    }

    public void insert(char key, int value) {
        root = insert(root, key, value);
    }

    private Node<Character, Integer> insert(Node<Character, Integer> node, char key, int value) {
        if (node == null)
            return new Node<>(key, value);

        if (key < node.key)
            node.left = insert(node.left, key, value);
        else if (key > node.key)
            node.right = insert(node.right, key, value);

        return node;
    }

    public Integer search(char key) {
        Node<Character, Integer> node = search(root, key);
        return (node != null) ? node.value : null;
    }

    private Node<Character, Integer> search(Node<Character, Integer> node, char key) {
        if (node == null || node.key == key)
            return node;

        if (key < node.key)
            return search(node.left, key);

        return search(node.right, key);
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(Node<Character, Integer> node) {
        if (node != null) {
            inorder(node.left);
            System.out.print("(" + node.key + ", " + node.value + ")\n");
            inorder(node.right);
        }
    }
}

class IndexMain {
    public static void main(String[] args) {
        IndexTree index = new IndexTree();

        index.insert('G', 120);
        index.insert('C', 60);
        index.insert('K', 140);
        index.insert('A', 80);
        index.insert('E', 100);
        index.insert('N', 150);
        index.insert('P', 160);
        index.insert('S', 165);

        System.out.print("indexes:\n");
        index.inorder();

        System.out.println("\nval for 'E': " + index.search('E'));
        System.out.println("val for 'Z': " + index.search('Z'));
    }
}

