import static java.lang.Math.max;

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
        return (change + index + capacity) % capacity;
    }

    private void sizeUpdate(boolean expand) {
        if(expand) {
            capacity *= 2;
            T[] tmp = item;
            item = (T[]) new Object[capacity];
            System.arraycopy(tmp, first, item, 0, size - first);
            System.arraycopy(tmp, 0, item, size-first, first);
            first=0;
            lastnext = size%capacity;
        }else{
            T[] tmp = item;
            item = (T[]) new Object[max((int) capacity/2,1)];
            for(int i=first,count=0;count<size;i=indexUpdate(i,1),count++){
                item[count]=tmp[i];
            }
            capacity/=2;
            first=0;
            lastnext=size%capacity;
        }
    }

    public void addFirst(T t) {
        if (size == capacity) {
            sizeUpdate(true);
        }
        int index=indexUpdate(first,-1);
        item[index]=t;
        first=index;
        size++;
    }
    public void addLast(T t){
        if(size == capacity) {
            sizeUpdate(true);
        }
        item[lastnext]=t;
        lastnext= indexUpdate(lastnext,1);
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
            System.out.print('\n');
        }
    }
    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        T tmp=item[first];
        first=indexUpdate(first,1);
        size--;
        if(size*2==capacity){
            sizeUpdate(false);
        }
        return tmp;
    }
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        lastnext=indexUpdate(lastnext,-1);
        T tmp=item[lastnext];
        size--;
        if(size*2==capacity){
            sizeUpdate(false);
        }
        return tmp;
    }
    public T get(int index){
        if(index<0 || index>=size){
            return null;
        }
        return item[indexUpdate(first,index)];
    }
}