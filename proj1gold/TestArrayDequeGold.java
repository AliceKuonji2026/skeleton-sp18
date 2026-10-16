import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void TestArray() {
        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        for (int i = 0; i < 100; i++) {
            Integer x = (int) StdRandom.uniform();
            studentArrayDeque.addLast(x);
            arrayDequeSolution.addLast(x);
            Integer actual = studentArrayDeque.get(i);
            assertEquals("addLast()&get():random int, expected " + x + ",but got " + actual, x, actual);
        }
        for (int i = 0; i < 100; i++) {
            Integer actual = (int) StdRandom.uniform();
            studentArrayDeque.addFirst(actual);
            arrayDequeSolution.addFirst(actual);
            Integer expected = studentArrayDeque.get(0);
            assertEquals("addFirst()&get():random int, expected " + actual + ",but got " + actual, expected, actual);
        }
        for (int i = 0; i < 80; i++) {
            Integer expected = arrayDequeSolution.removeFirst();
            Integer actual = studentArrayDeque.removeFirst();
            assertEquals("removeFirst(): random int ,expected " + expected + ",but got " + actual, expected, actual);
        }
        for (int i = 0; i < 20; i++) {
            Integer expected = arrayDequeSolution.removeLast();
            Integer actual = studentArrayDeque.removeLast();
            assertEquals("removeLast():expected " + expected + " but got " + actual, expected, actual);
        }
        Integer expected = arrayDequeSolution.size();
        Integer actual = studentArrayDeque.size();
        assertEquals("size(): expected " + expected + " but got " + actual, expected, actual);
        actual = studentArrayDeque.size();
        expected = 0;
        assertEquals("size(),expected 0 , but got " + actual, expected, actual);

    }

}
