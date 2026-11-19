import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;

public class RadixSortTester {
    private static String[] asciis = {"123","231","4213","42","112","345","355","ab"};
    private static String[] a = {"","\0",""};
    @Test
    public void testLSD() {
        assertArrayEquals(Arrays.stream(asciis).sorted().toArray(),RadixSort.sort(asciis));
        assertArrayEquals(Arrays.stream(a).sorted().toArray(),RadixSort.sort(a));
    }

    @Test
    public void testMSD() {
        assertArrayEquals(Arrays.stream(asciis).sorted().toArray(),RadixSort.sort1(asciis));
        assertArrayEquals(Arrays.stream(a).sorted().toArray(),RadixSort.sort1(a));
    }
}
