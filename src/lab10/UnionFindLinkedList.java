package lab10;

import java.util.*;

public class UnionFindLinkedList implements IUnionFind {


    static class Node {
        
        int value;
        Set set;
        Node nextNode;
        Node(int value) {
            this.value = value; 
        }
        
    }

    static class Set {

        Node head, tail;
        int size;
        Set(Node x) {
            head = tail = x;
            size = 1;
        }

    }


    private Node[] nodes;
    private int count;

    
    public UnionFindLinkedList(int N) {
        
        init(N);
        
    }

    @Override
    public void init(int N) {

        nodes = new Node[N];
        count = N;

        for (int i = 0; i < N; i++) {
            
            Node node = new Node(i);
            Set set = new Set(node);
            node.set = set;
            nodes[i] = node;
            
        }
    }

    @Override
    public int find(int p) {
        
        return nodes[p].set.head.value;
        
    }

    @Override
    public void union(int f, int s) {
        
        Node nF = nodes[f];
        Node nS = nodes[s];

        if (nF.set == nS.set) return;

        Set setF = nF.set;
        Set setS = nS.set;

        if (setF.size < setS.size) {

            Set aux = setF;
            setF = setS;
            setS = aux;

        }

        Node curr = setS.head;

        while (curr != null) {

            curr.set = setF;
            curr = curr.nextNode;

        }

        setF.tail.nextNode = setS.head;
        setF.tail = setS.tail;
        setF.size += setS.size;
        count--;
    }

    @Override
    public int count() {
        return count;
    }
}
