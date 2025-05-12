import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

class IntegerBTree {

    private int T;

    class BTreeNode {
        int n;
        Integer key[] = new Integer[2 * T - 1];
        BTreeNode child[] = new BTreeNode[2 * T];
        boolean leaf = true;

        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(" [ ");
            for(int i = 0; i < n; i++)
                sb.append(" " + key[i]);
            sb.append(" ] ");
            return sb.toString();
        }
    }

    public IntegerBTree(int t) {
        T = t;
        root = new BTreeNode();
        root.n = 0;
        root.leaf = true;
    }

    private BTreeNode root;

    public void insert(Integer key) {
        BTreeNode r = root;
        if(r.n == 2 * T - 1){
            BTreeNode s = new BTreeNode();
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child[0] = r;
            split(s, 0, r);
            insertNonfullStart(s, key);
        } else {
            insertNonfullStart(r, key);
        }
    }



    private void split(BTreeNode x, int pos, BTreeNode y) {
        BTreeNode z = new BTreeNode();
        z.leaf = y.leaf;
        z.n = T - 1;
        for(int j = 0; j < T - 1; j++)
            z.key[j] = y.key[j + T];
        if(!y.leaf){
            for(int j = 0; j < T; j++)
                z.child[j] = y.child[j + T];
        }
        y.n = T - 1;
        for(int j = x.n; j >= pos + 1; j--)
            x.child[j + 1] = x.child[j];
        x.child[pos + 1] = z;
        for(int j = x.n - 1; j >= pos; j--)
            x.key[j + 1] = x.key[j];
        x.key[pos] = y.key[T - 1];
        x.n = x.n + 1;
    }


    final private void insertNonfullStart(BTreeNode x, int k) {
        if(x.leaf){
            int i = 0;
            for(i = x.n - 1; i >= 0 && k < x.key[i]; i--)
                x.key[i + 1] = x.key[i];
            x.key[i + 1] = k;
            x.n = x.n + 1;
        } else {
            int i = 0;
            for(i = x.n - 1; i >= 0 && k < x.key[i]; i--);
            i++;
            BTreeNode tmp = x.child[i];
            if(tmp.n == 2 * T - 1){
                split(x, i, tmp);
                if(k > x.key[i])
                    i++;
            }
            insertNonfullStart(x.child[i], k);
        }
    }


    private class QueuePair {
        BTreeNode node;
        int level;
        QueuePair(BTreeNode node, int level){
            this.node = node;
            this.level = level;
        }
    }


    public void displayLevels() {
        // I have added a '\n' for me to see clearer. I hope you don't mind
        Queue<QueuePair> q = new LinkedList<QueuePair>();
        BTreeNode x = root;
        int oldLevel = 0;
        int level;
        q.add(new QueuePair(x, oldLevel));
        while(!q.isEmpty()){
            QueuePair p = q.remove();
            x = p.node;
            level = p.level;
            if(level > oldLevel){
                System.out.println();
                oldLevel = level;
            }
            System.out.print(x.toString());
            if(!x.leaf){
                for(int i = 0; i <= x.n; i++)
                    q.add(new QueuePair(x.child[i], level + 1));
            }
        }
        System.out.println('\n');
    }





    public boolean contains(int key){
        return search(root, key) != null;
    }


    private BTreeNode search(BTreeNode node, int key) {
        int i = 0;
        for(i = 0; i < node.n && key > node.key[i]; i++);

        if(i < node.n && key == node.key[i]) return node;

        if(node.leaf) return null;

        return search(node.child[i], key);
    }



    public int height() {
        return height(root);
    }

    private int height(BTreeNode node) {
        if(node.leaf) return 1;
        return 1 + height(node.child[0]);
    }



    public int level(int key) {
        if(root == null || root.n == 0) throw new NoSuchElementException("calls level() with empty tree");
        else if(!contains(key)) throw new NoSuchElementException("calls level() with non existing Key");

        return level(root, key, 1);
    }
    private int findIndex(BTreeNode node, int key) {
        int i = 0;
        while(i < node.n && key > node.key[i]) i++;
        return i;
    }

    private int level(BTreeNode node, int key, int depth) {
        int i = findIndex(node, key);
        if(i < node.n && key == node.key[i]) return depth - 1;
        return level(node.child[i], key, depth + 1);
    }



    public int min() {
        if(root == null || root.n == 0) throw new NoSuchElementException("calls min() with empty tree");
        return min(root);
    }

    private int min(BTreeNode node) {
        while(!node.leaf) node = node.child[0];

        return node.key[0];
    }



    public int max() {
        if(root == null || root.n == 0) throw new NoSuchElementException("calls max() with empty tree");
        return max(root);
    }

    private int max(BTreeNode node) {
        while(!node.leaf) node = node.child[node.n];

        return node.key[node.n - 1];
    }



    public int successor(int key) {
        if(root == null || root.n == 0) throw new NoSuchElementException("calls successor() with empty tree");

        if(!contains(key)) throw new NoSuchElementException("calls successor() with missing key");

        return successor(root, key, 0, false);
    }

    private int successor(BTreeNode node, int key, int succ, boolean existsSucc) {
        int i = 0;
        while(i < node.n && key >= node.key[i]) i++;
        if(i < node.n){
            succ = node.key[i];
            existsSucc = true;

        }

        if(node.leaf) {
            if(!existsSucc) throw new NoSuchElementException("No successor exists");
            return succ;
        }
        return successor(node.child[i], key, succ, existsSucc);
    }



    public int predecessor(int key) {
        if(root == null || root.n == 0) throw new NoSuchElementException("calls predecessor() with empty tree");

        if(!contains(key)) throw new NoSuchElementException("calls predecessor() with missing key");

        return predecessor(root, key, 0, false);
    }

    private int predecessor(BTreeNode node, int key, int pred, boolean existsPred) {
        int i = node.n - 1;
        while(i >= 0 && key <= node.key[i]) i--;
        if(i >= 0) {
            pred = node.key[i];
            existsPred = true;

        }

        if(node.leaf) {
            if(!existsPred) throw new NoSuchElementException("No predecessor exists");
            return pred;
        }

        return predecessor(node.child[i + 1], key, pred, existsPred);
    }



    public void inorder() {
        inorder(root);
        System.out.print('\n');
    }

    private void inorder(BTreeNode node) {
        if(node == null) return;
        for(int i = 0; i < node.n; i++) {
            inorder(node.child[i]);
            System.out.print(node.key[i] + " ");
        }
        inorder(node.child[node.n]);
    }



    public static void main(String[] args) {
        IntegerBTree b = new IntegerBTree(3);
        int[] a = {8, 9, 10, 11, 15, 20, 17, 22, 25, 16, 12, 13, 14, 26, 27, 29, 33, 40, 50, 51, 52, 53};
        for(int i = 0; i < a.length; i++){
            b.insert(a[i]);
            b.displayLevels();
        }


        System.out.println("min: " + b.min());
        System.out.println("max: " + b.max());
        System.out.println("contains 13: " + b.contains(13));
        System.out.println("height: " + b.height());
        System.out.println("level of 13: " + b.level(13));
        System.out.println("successor of 13: " + b.successor(13));
        System.out.println("predecessor of 13: " + b.predecessor(13));
        b.inorder();


    }
}