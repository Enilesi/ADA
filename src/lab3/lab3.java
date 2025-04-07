////RAYSCAPE
//
//class Node <K,V>{
//    K key;
//    V value;
//    Node left, right;
//    public Node(K key, V value) {
//        this.key = key;
//        this.value = value;
//
//    }
//}
//
//class BST<K extends Comparable<K>,V>{
//    Node <K,V> root;
//    public  void put(K key, V value) {
//        root = put(root,key,value);
//    }
//
//    Node put(Node   <K,V> rootNode, K key, V value){
//        if(rootNode == null){
//            return new Node(key, value);
//        }
//
//        if(key.compareTo(key)>0){
//            rootNode.left = put(rootNode.left, key, value);
//            return root.left;
//        }
//        else if (key.compareTo(key)<0){
//            rootNode.right = put(rootNode.right, key, value);
//            return rootNode.right;
//        }
//        else{
//            rootNode.value = value;
//            return rootNode;
//        }
//    }
//
//
//}
//
//class MainLab2{
//    public static void main(String[] args) {
//        BST<Integer,Integer> bst=new BST<>();
//        bst.put(5,10);
//
//        bst.root=bst.put(bst.root,5,10);
//
//
//    }
//}