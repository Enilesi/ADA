package lab3;

class WordTranslation{
    String italianWord;
    String romanianWord;
    String frenchWord;

    public WordTranslation(String italianWord, String romanianWord, String frenchWord){
        this.italianWord = italianWord;
        this.romanianWord = romanianWord;
        this.frenchWord = frenchWord;

    }

    public String toString(){
        return "{"+italianWord + " " + romanianWord + " " + frenchWord +"}";
    }

    public String getItalianWord(){ return italianWord; }
    public String getRomanianWord(){ return romanianWord; }
    public String getFrenchWord(){ return frenchWord; }

}

class WordNode{
    String key;
    WordTranslation value;
    WordNode left, right;
    public WordNode(String key, WordTranslation value) {
        this.key = key;
        this.value = value;
    }
    public String getKey(){ return key; }
    public WordTranslation getValue(){ return value; }
}

class WordsBST{
    WordNode root;
    public  void put(String key, WordTranslation value) {
        root = put(root,key,value);
    }

    private WordNode put(WordNode  rootWordNode, String key, WordTranslation value){
        if(rootWordNode == null){
            return new WordNode(key, value);
        }

        if(rootWordNode.key.compareTo(key)>0){
            rootWordNode.left = put(rootWordNode.left, key, value);

        }
        else if (rootWordNode.key.compareTo(key)<0){
            rootWordNode.right = put(rootWordNode.right, key, value);

        }
        else{
            rootWordNode.value = value;

        }
        return rootWordNode;
    }

    public void printIndentedBst(WordNode node, int depth){
        if(node == null)return;
        printIndentedBst(node.right,depth+1);

        System.out.println("     ".repeat(depth)+node.key+ " "+node.value);
        printIndentedBst(node.right,depth+1);
    }
}

class MainHomework3{
    public static void main(String[] args) {
        WordsBST bst=new WordsBST();

        WordTranslation w1=new WordTranslation("semplice","mere","pommes");
        WordTranslation w2=new WordTranslation("fiore","floare","fleur");
        WordTranslation w3=new WordTranslation("blanca","alb","blanc");
        bst.put("apples",w1);
        bst.put("flower",w2);
        bst.put("white",w3);

        bst.printIndentedBst(bst.root,0);

    }
}