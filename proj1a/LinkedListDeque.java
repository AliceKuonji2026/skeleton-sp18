public class LinkedListDeque<T> {
    private Node sentinel;
    private Node first;
    private Node last;
    private int size;

    public class Node {
        public Node prev;
        public T item;
        public Node next;
    }

    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        first=sentinel;
        last=sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node tmp = new Node();
        tmp.item = item;
        sentinel.next = tmp;
        tmp.prev = sentinel;
        tmp.next = first;
        first.prev = tmp;
        first = tmp;
        size++;
    }

    public void addLast(T item) {
        Node tmp = new Node();
        tmp.item = item;
        sentinel.prev = tmp;
        tmp.next = sentinel;
        tmp.prev = last;
        last.next = tmp;
        last = tmp;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        Node p=sentinel.next;
        while(p!=sentinel){
            System.out.print(p.item);
            if(p.next!=sentinel){
                System.out.print(' ');
            }
            p=p.next;
        }
    }

    public T removeFirst() {
        if(isEmpty()){
            return null;
        }
        Node tmp=first;
        sentinel.next=first.next;
        sentinel.next.prev=sentinel;
        size--;
        return tmp.item;
    }

    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        Node tmp=last;
        sentinel.prev=last.prev;
        sentinel.prev.next=sentinel;
        size--;
        return tmp.item;
    }

    public T get(int index) {
        if(index<0 || index>=size){
            return null;
        }
        int i=0;
        Node p=first;
        while(i!=index){
            p=p.next;
            i++;
        }
        return p.item;
    }

    public T get_helper(Node p, int i){
        if(i==0){
            return p.item;
        }else{
            return get_helper(p.next,i-1);
        }
    }
    public T getRecursive(int index){
        if(index<0 || index>=size){
            return null;
        }
        return get_helper(sentinel.next,index);
    }
}
