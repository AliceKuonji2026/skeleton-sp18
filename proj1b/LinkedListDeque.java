public class LinkedListDeque<T> implements Deque<T>{
    private Node sentinel;
    private int size;

    private class Node {
        public Node prev;
        public T item;
        public Node next;
    }

    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
    @Override
    public void addFirst(T item) {
        Node tmp = new Node();
        tmp.item = item;
        tmp.next = sentinel.next;
        tmp.prev = sentinel;
        sentinel.next = tmp;
        tmp.next.prev = tmp;
        size++;
    }
    @Override
    public void addLast(T item) {
        Node tmp = new Node();
        tmp.item = item;
        tmp.next = sentinel;
        tmp.prev = sentinel.prev;
        sentinel.prev = tmp;
        tmp.prev.next = tmp;
        size++;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            if (p.next != sentinel) {
                System.out.print(' ');
            }
            p = p.next;
        }
        System.out.print('\n');
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node tmp = sentinel.next;
        sentinel.next = tmp.next;
        sentinel.next.prev = sentinel;
        size--;
        return tmp.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node tmp = sentinel.prev;
        sentinel.prev = tmp.prev;
        sentinel.prev.next = sentinel;
        size--;
        return tmp.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int i = 0;
        Node p = sentinel.next;
        while (i != index) {
            p = p.next;
            i++;
        }
        return p.item;
    }

    private T get_helper(Node p, int i) {
        if (i == 0) {
            return p.item;
        } else {
            return get_helper(p.next, i - 1);
        }
    }

        @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return get_helper(sentinel.next, index);
    }
}
