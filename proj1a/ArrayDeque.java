public class ArrayDeque<T> {
    private int first;
    private int lastnext;
    private int size;
    private int capacity;
    private T[] item;


    public ArrayDeque() {
        capacity = 32;
        size = 0;
        first = 0;
        lastnext = 0;
        item =(T[]) new Object[capacity];
    }

   private int indexUpdate(int index, int change) {
        return (change + index) % capacity;
    }

    private void sizeUpdate() {
        capacity*=2;
        T[] tmp=item;
        item=(T[]) new Object[capacity];
        System.arraycopy(tmp,first,item,first,size-first);
        System.arraycopy(tmp,0,item,size-first+1,first);
    }

    public void addFirst(T t) {
        if (size == capacity) {
            sizeUpdate();
        }
        int index=indexUpdate(first,-1);
        item[index]=t;
        first=index;
        size++;
    }
    public void addLast(T t){
        if(size == capacity) {
            sizeUpdate();
        }
        int index=indexUpdate(lastnext,1);
        item[index]=t;
        lastnext=index;
        size++;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void printDeque(){
        if(!isEmpty()){
            int i=first;
            while(i!=lastnext){
                System.out.print(item[i]);
                i=indexUpdate(i,1);
                if(i!=lastnext){
                    System.out.print(' ');
                }
            }
        }
    }
    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        T tmp=item[first];
        first=indexUpdate(first,1);
        size--;
        return tmp;
    }
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        T tmp=item[lastnext];
        lastnext=indexUpdate(lastnext,-1);
        size--;
        return tmp;
    }
    public T get(int index){
        if(index<0 || index>=size){
            return null;
        }
        return item[indexUpdate(first,index)];
    }
}