package lab4;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class PersonAVL{
    private String ssn;
    private String surname;
    private String lastname;
    private int age;

    public PersonAVL(String ssn, String surname,String lastname, int age) {
        this.ssn=ssn;
        this.surname=surname;
        this.lastname=lastname;
        this.age = age;
    }

    public String getSsn() {return ssn;}
    public String getLastname() {return lastname;}
    public String getSurname() {return surname;}
    public int getAge() {return age;}

    @Override
    public String toString() {
        return ssn+" "+surname+" "+lastname+" "+age;
    }

}


class GenericAVL <K extends Comparable<K>, V> {

    private class AVLNode {
        K key;
        V val;
        AVLNode left;
        AVLNode right;
        int height;

        public AVLNode(K key, V val, int height) {
            this.key = key;
            this.val = val;
            this.height = height;
            this.left = null;
            this.right = null;
        }
    }
    private AVLNode root;



    public GenericAVL(){
        root = null;
    }

    public int height() {
        return height(root);

    }

    private int height(AVLNode x) {
        if (x == null) return -1;
        return x.height;
    }


    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("key is null");
        root = put(root, key, val);
    }

    private AVLNode put(AVLNode x, K key, V val){
        if (x == null) return new AVLNode(key, val, 0);
        if (key.compareTo( x.key)<0) {
            x.left = put(x.left, key, val);
        } else if (key.compareTo( x.key)>0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
            return x;
        }
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }



    private AVLNode balance(AVLNode x) {
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


    private int balanceFactor(AVLNode x) {
        return height(x.left) - height(x.right);
    }

    private AVLNode rotateRight(AVLNode y) {
        System.out.println("rotate right at " + y.key);
        AVLNode x = y.left;
        y.left = x.right;
        x.right = y;
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;
    }


    private AVLNode rotateLeft(AVLNode x) {
        System.out.println("rotate left at " + x.key);
        AVLNode y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }


    private class QueuePair {
        AVLNode node;
        int level;
        QueuePair(AVLNode node, int level){
            this.node=node;
            this.level=level;
        }
    }

    public void displayLevels() {
        Queue<QueuePair> q = new LinkedList<QueuePair>();

        System.out.print("AVL Tree displayed on levels: ");
        AVLNode x = root;
        int oldLevel = -1;
        int level = 0;

        if (root != null) q.add(new QueuePair(x, level));

        while (!q.isEmpty()) {

            QueuePair p = q.remove();
            x= p.node;
            level = p.level;

            if (level > oldLevel) {
                System.out.println();
                System.out.print("Level " + level + ":  ");
                oldLevel = level;
            }

            System.out.print(x.key + " ");
            if (x.left != null) q.add(new QueuePair(x.left, level+1));
            if (x.right != null) q.add(new QueuePair(x.right, level+1));
        }
        System.out.println();
        System.out.println();
    }


    public static void main(String[] args) {
        GenericAVL st = new GenericAVL();
        String arr[]={"Hannah", "Anna", "Banana", "Bomboana"};
        for(int i=0;i<arr.length;i++) {
            st.put(arr[i], arr[i]);
            st.displayLevels();
        }
        PersonAVL p1 =new PersonAVL("12345","Robert","Mutulescu",27);
        PersonAVL p2 =new PersonAVL("1","Luminita","Mutulescu",16);
        PersonAVL p3 =new PersonAVL("234","Raul","Ciocan",24);
        PersonAVL p4 =new PersonAVL("5432","Vlad","Lazarescu",98);
        PersonAVL p5 =new PersonAVL("2","Cosmin","Terches",114);

        GenericAVL st2=new GenericAVL();
        PersonAVL persons[]={p1,p2,p3,p4};
        for (int i=0;i<persons.length;i++) {
            st2.put(persons[i].getSurname(),persons[i]);
            st2.displayLevels();
        }

    }
}

