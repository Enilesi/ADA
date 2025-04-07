package lab3;

import java.util.*;


class GenericBST<K extends Comparable<K>, V> {


    private class Node {
        K key;
        V val;
        Node left, right;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        private Node root;
    }

    private Node root;



    public void inorder() {
        if (root == null) return;
        inorder(root);
    }


    public void inorder(Node node) {
        if (node == null) return;
        inorder(node.left);
        System.out.println(node.key);
        inorder(node.right);

    }


    public boolean contains(K key) {
        return contains(key, root);
    }

    public boolean contains(K key, Node node) {
        if (node == null) return false;
        if (node.key.compareTo(key) < 0) return contains(key, node.right);
        if (node.key.compareTo(key) > 0) return contains(key, node.left);
        return true;
    }


    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("calls put with a null key");
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) return new Node(key, val);
        if (key.compareTo(x.key) < 0) x.left = put(x.left, key, val);
        else if (key.compareTo(x.key) > 0) x.right = put(x.right, key, val);
        else x.val = val;
        return x;
    }


    public K min() {
        if (root == null) throw new NoSuchElementException("tries to find minimum with an empty tree");
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        return min(node.left);

    }


    public K max() {
        if (root == null) throw new NoSuchElementException("tries to find maximum with an empty tree");
        return max(root).key;
    }

    private Node max(Node node) {
        if (node.right == null) return node;
        return max(node.right);
    }


    public void delete(K key) {
        if (key == null) throw new IllegalArgumentException("calls delete with a null key");
        root = deleteRecursive(root, key);
    }

    private Node deleteRecursive(Node z, K key) {

        if (z == null) return null;
        if (key.compareTo(z.key) < 0) z.left = deleteRecursive(z.left, key);
        else if (key.compareTo(z.key) > 0) z.right = deleteRecursive(z.right, key);
        else {
            if (z.right == null) return z.left;
            else if (z.left == null) return z.right;
            Node y = min(z.right);
            z.right = deleteRecursive(z.right, y.key);
            z.key = y.key;
        }
        return z;
    }

    public void preorder() {
        if (root == null) return;
        preorder(root);
    }


    public void preorder(Node node) {
        if (node == null) return;
        System.out.println(node.key);
        inorder(node.left);
        inorder(node.right);

    }

    public void postorder(Node node) {
        if (node == null) return;
        inorder(node.left);
        inorder(node.right);
        System.out.println(node.key);

    }

    public void postorder() {
        if (root == null) return;
        postorder(root);
    }

    public int height() {
        if (root == null) return 0;
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean isBST() {
        if (root == null) return true;
        return isBST(root);
    }

    private boolean isBST(Node node) {
        if (node == null) return true;
        if ( node.left !=null && node.left.key.compareTo(node.key) > 0) return false;
        else if ( node.right != null && node.right.key.compareTo(node.key) < 0) return false;
        else return isBST(node.left) && isBST(node.right);

    }

    public void spoilValues() {
        List<V> vals = new ArrayList<>();
        arrayGiveValues(root, vals);
        Collections.shuffle(vals);
        Iterator<V> valIterator = vals.iterator();
        treeGiveValues(root, valIterator);
    }

    private void arrayGiveValues(Node node, List<V> vals) {
        if (node == null) return;
        arrayGiveValues(node.left, vals);
        vals.add(node.val);
        arrayGiveValues(node.right, vals);
    }

    private void treeGiveValues(Node node,Iterator<V> valIterator) {
        if (node == null) return;
        treeGiveValues(node.left,  valIterator);
        node.val = valIterator.next();
        treeGiveValues(node.right,  valIterator);
    }


    public Node findNode(K key) {
        if (key == null) throw new IllegalArgumentException("null key");
        return findNode(root);
    }

    private Node findNode(Node node) {
        if (node == null) return null;
        if (node.key.compareTo(root.key) < 0) return findNode(node.left);
        else if (node.key.compareTo(root.key) > 0) return findNode(node.right);
        else return node;
    }

    public Node succesor(K key) {
        return min(findNode(key).right);
    }

    public Node predecessor(K key) {
        return max(findNode(key).left);
    }
}
class MainPB32{
    public static void main(String[] args) {
        GenericBST<Integer, Integer> bst = new GenericBST<Integer,Integer>();
        bst.put(15, 15);
        bst.put(7, 7);
        bst.put(18, 18);
        bst.put(3, 3);
        bst.put(8, 8);
        bst.put(17, 17);
        bst.put(20, 20);

        System.out.println("inorder traversal:");
        bst.inorder();

        System.out.println("preorder traversal:");
        bst.preorder();

        System.out.println("postorder traversal:");
        bst.postorder();


        System.out.println("\nis BST: " + bst.isBST());


        bst.spoilValues();
        System.out.println("\nafter spoilValues, Is BST? " + bst.isBST());


        System.out.println("inorder Traversal (After Spoiling Values):");
        bst.inorder();




        System.out.println("\npuccessors:");
        System.out.println("for15: " + (bst.succesor(15) != null ? bst.succesor(15) : "None"));
        System.out.println("for 7: " + (bst.succesor(7) != null ? bst.succesor(7) : "None"));
        System.out.println("for 22: " + (bst.succesor(22) != null ? bst.succesor(22) : "None"));


        System.out.println("\npredecessors:");
        System.out.println("for 15: " + (bst.predecessor(15) != null ? bst.predecessor(15) : "None"));
        System.out.println("for 7: " + (bst.predecessor(7) != null ? bst.predecessor(7) : "None"));
        System.out.println("for 3: " + (bst.predecessor(3) != null ? bst.predecessor(3) : "None"));


        System.out.println("\nminimum key: " + bst.min());
        System.out.println("maximum key: " + bst.max());


        System.out.println("\nheight: " + bst.height());



        System.out.println("\ndeleting key 7...");
        bst.delete(7);
        System.out.println("inorder traversal (sfter Deleting 7):");
        bst.inorder();

        System.out.println("\nis BST after deletion? " + bst.isBST());
    }
}












