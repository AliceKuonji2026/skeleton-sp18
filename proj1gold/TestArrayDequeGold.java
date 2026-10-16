import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void TestArray() {
        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 2000; i++) {
            int op = StdRandom.uniform(4);
            int val = StdRandom.uniform(100);
            switch (op) {
                case 0:
                    s.append("addFirst(").append(val).append(")\n");
                    studentArrayDeque.addFirst(val);
                    arrayDequeSolution.addFirst(val);
                    break;
                case 1:
                    s.append("addLast(").append(val).append(")\n");
                    studentArrayDeque.addLast(val);
                    arrayDequeSolution.addLast(val);
                    break;
                case 2:
                    s.append("removeFirst()\n");
                    studentArrayDeque.removeFirst();
                    arrayDequeSolution.removeFirst();
                    break;
                case 3:
                    s.append("removeLast()\n");
                    studentArrayDeque.removeLast();
                    arrayDequeSolution.removeLast();
                    break;
            }
            s.append("size()\n");
            int expected = arrayDequeSolution.size();
            int actual = studentArrayDeque.size();
            assertEquals(s.toString(), expected, actual);

        }

    }

}
