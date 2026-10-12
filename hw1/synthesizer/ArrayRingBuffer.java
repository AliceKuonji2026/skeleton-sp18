// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;
package synthesizer;

import synthesizer.AbstractBoundedQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    @SuppressWarnings("unchecked")
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        this.fillCount = 0;
        first = 0;
        last = 0;
        this.rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.fillCount == this.capacity)
            throw new RuntimeException("Ring buffer overflow");
        this.rb[this.last] = x;
        this.last = (this.last + 1) % capacity;
        this.fillCount++;
    }

    /**
     * Dequeue the oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {

        if (this.fillCount == 0)
            throw new RuntimeException("Ring buffer underflow");
        int tmp = this.first;
        this.first = (this.first + 1) % capacity;
        this.fillCount = (this.fillCount - 1) % this.capacity;
        return this.rb[tmp];
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {

        if (this.fillCount == 0)
            throw new RuntimeException("There is no element");
        return rb[this.first];
    }


    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int index;
        private int count;

        public ArrayRingBufferIterator() {
            index = first;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < fillCount;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = rb[index];
            index = (index + 1) % capacity;
            count++;
            return item;
        }
    }

}


