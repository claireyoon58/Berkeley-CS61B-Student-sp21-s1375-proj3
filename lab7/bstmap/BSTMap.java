package bstmap;
import java.util.Set;
import java.util.Iterator;


public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        private Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }


    public void printInOrder() {
        System.out.print(this);
    }

    @Override
    public void clear(){
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key){
        return get(key) != null;
    }

    @Override
    public V get(K key){
        return get(root, key);
    }

    private V get(Node n, K key){
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp == 0){
            return n.val;
        }
        else if (cmp < 0) {
            return get(n.left, key);
        }
        else if (cmp > 0) {
            return get(n.right, key);
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = insert(root, key, value);
    }

    private Node insert(Node n, K key, V value) {
        if(n == null) {
            n = new Node(key, value);
            size += 1;
        }

        int cmp = key.compareTo(n.key);
        if (cmp == 0) {
            n.val = value;
        }
        else if (cmp < 0) {
            n.left = insert(n.left, key, value);
        }
        else {
            n.right = insert(n.right,key,value);
        }
        return n;
    }

    private int size(Node n){
        if(n == null) {
            return 0;
        }
        return size;
    }

    public BSTMap() {
        root = null;
        size = 0;
    }





    public  V remove(K key) {
        throw new UnsupportedOperationException();
    }
    public  V remove(K key, V value){
        throw new UnsupportedOperationException();
    }
    public  Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
