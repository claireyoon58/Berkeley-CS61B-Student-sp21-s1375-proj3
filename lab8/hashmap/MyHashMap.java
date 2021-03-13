package hashmap;

import java.util.Collection;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Claire Yoon
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int initial= 16;
    private static final double LoadFactor = 0.75;

    private HashSet<K> keys;


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadFactor;
    private int size;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(initial, LoadFactor);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, LoadFactor);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        this.loadFactor = loadFactor;
        this.keys = new HashSet<K>();
        this.loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        Node create = new Node(key, value);
        return create;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    @Override
    public void clear() {
        size = 0;
        buckets = createTable(initial);
        keys = new HashSet<K>();
    }

//    private int wrap(K key) {
//        return wrap(key, buckets.length);
//    }
    private int wrap(K k, int m) {
        return Math.floorMod(k.hashCode(), m);
    }

    private Node getNode(K key) {
        int bucketindex = wrap(key, buckets.length);
        Collection<Node> bucket = buckets[bucketindex];
        if (bucket == null) {
            return null;
        }
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }



    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public int size() {
        return size;
    }

    public void put(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
        } else {
            int bucketi = wrap(key, buckets.length);
            Collection<Node> bucket = buckets[bucketi];
            Node Add = new Node(key, value);
            if (bucket == null) {
                buckets[bucketi] = new ArrayList<Node>();
            }
            buckets[bucketi].add(Add);
            size += 1;
            keys.add(key);
            double newLF = ((double) size) / buckets.length;
            if (newLF > this.loadFactor) {
                resize(2 * buckets.length);
            }
        }
    }

    private void resize(int newSize) {
        Collection<Node>[] newBuckets = createTable(newSize);
        for (int bucketi= 0; bucketi < buckets.length; bucketi += 1) {
            Collection<Node> bucket = this.buckets[bucketi];
            if (bucket == null) {
                continue;
            }
            for (Node n : bucket) {
                int newBucketi = wrap(n.key, newSize);
                if (newBuckets[newBucketi] == null) {
                    newBuckets[newBucketi] = new ArrayList<Node>();
                }
                Collection<Node> newBucket = newBuckets[newBucketi];

                newBucket.add(n);
            }
        }
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }


    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    // Your code won't compile until you do so!

}
