import java.util.NoSuchElementException;

class IntBST {

    private class Node {
        int key;
        int val;
        Node left, right;
        int size;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.size = 1;
        }
    }

    private Node root;

    public IntBST() {
        root = null;
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(Node x) {
        if (x == null) return;
        inorder(x.left);
        System.out.print("("+x.key+","+x.val+") ");
        inorder(x.right);
    }

    public boolean contains(int key) {
        return contains(root, key);
    }

    private boolean contains(Node x, int key) {
        if (x == null) return false;
        if (key < x.key) return contains(x.left, key);
        else if (key > x.key) return contains(x.right, key);
        else return true;
    }

    public void put(int key, int val) {
        root = put(root, key, val);
    }

    private Node put(Node x, int key, int val) {
        if (x == null) return new Node(key, val);
        if (key < x.key) x.left = put(x.left, key, val);
        else if (key > x.key) x.right = put(x.right, key, val);
        else x.val = val;

        x.size = 1 + Math.max(size(x.left), size(x.right));
        return balance(x);
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

    private int height(Node x) {
        if (x == null) return -1;
        return Math.max(height(x.left), height(x.right)) + 1;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    public int min() {
        if (root == null) throw new NoSuchElementException("calls min() with empty BST");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    public int max() {
        if (root == null) throw new NoSuchElementException("calls max() with empty BST");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    public void delete(int key) {
        root = deleteRecursive(root, key);
    }

    private Node deleteRecursive(Node z, int key) {
        if (z == null) return null;
        if (key < z.key) z.left = deleteRecursive(z.left, key);
        else if (key > z.key) z.right = deleteRecursive(z.right, key);
        else {
            if (z.right == null) return z.left;
            if (z.left == null) return z.right;
            Node y = min(z.right);
            z.right = deleteRecursive(z.right, y.key);
            z.key = y.key;
            z.val = y.val;
        }

        z.size = 1 + Math.max(size(z.left), size(z.right));
        return balance(z);
    }

    private IntBST.Node rotateRight(IntBST.Node y) {
        System.out.println("rotate right at " + y.key);
        IntBST.Node x = y.left;
        y.left = x.right;
        x.right = y;
        y.size = 1 + Math.max(size(y.left), size(y.right));
        x.size = 1 + Math.max(size(x.left), size(x.right));
        return x;
    }

    private IntBST.Node rotateLeft(IntBST.Node x) {
        System.out.println("rotate left at " + x.key);
        IntBST.Node y = x.right;
        x.right = y.left;
        y.left = x;
        x.size = 1 + Math.max(size(x.left), size(x.right));
        y.size = 1 + Math.max(size(y.left), size(y.right));
        return y;
    }

    public static void main(String[] args) {

        int[] a = {20, 30, 15, 1, 7, 9, 29, 11, 12, 4};

        IntBST bst = new IntBST();

        for (int i = 0; i < a.length; i++) {
            bst.put(a[i], 3*a[i]);

        }

        bst.inorder();

        System.out.println();

        bst.delete(15);

        bst.inorder();

        System.out.println();

    }
}
