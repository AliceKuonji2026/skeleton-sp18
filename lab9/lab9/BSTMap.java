package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int c = key.compareTo(p.key);
        if (c == 0) {
            return p.value;
        } else if (c < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }
        int c = key.compareTo(p.key);
        if (c == 0) {
            p.value = value;
        } else if (c < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /// ///////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        collectKeys(set, root);
        return set;
    }

    private void collectKeys(Collection<K> set, Node p) {
        if (p == null) {
            return;
        }
        collectKeys(set, p.left);
        set.add(p.key);
        collectKeys(set, p.right);
    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        V value = get(key);
        if (!containsKey(key)) {
            return null;
        }
        root = removeHelper(key, root);
        size--;
        return value;
    }

    private Node removeHelper(K key, Node p) {
        //返回删除key节点之后的新p节点
        if (p == null) {
            return null;
        }
        int c = key.compareTo(p.key);
        if (c == 0) {
            if(p.left == null) {
                return p.right;
            }
            if(p.right == null) {
                return p.left;
            }
            Node s = getSuccessor(p.right); // 此时 p.right 必定不为 null
            p.key = s.key;
            p.value = s.value;
            p.right = removeHelper(s.key, p.right); // 去右子树把后继删掉
        } else if (c < 0) {
            p.left = removeHelper(key, p.left);
        } else {
            p.right = removeHelper(key, p.right);
        }
        return p;
    }

    private Node getSuccessor(Node p) {
        //just get the successor
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }


    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if(!containsKey(key) || !get(key).equals(value)) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        List<K> list = new ArrayList<>();
        collectKeys(list,root);
        return list.iterator();
    }
}
