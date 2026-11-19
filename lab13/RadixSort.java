import java.util.Arrays;
import java.util.List;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        System.out.println("LSD");
        if(asciis.length <= 1) return asciis;
        int maxLen = asciis[0].length();
        for (String s : asciis) {
            maxLen = Math.max(maxLen, s.length());
        }
        String[] res = new String[asciis.length];
        System.arraycopy(asciis, 0, res, 0, asciis.length);
        for (int i = maxLen - 1; i >= 0; i--) {
            sortHelperLSD(res, i);
        }
        return res;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] counts = new int[256];
        for (String s : asciis) {
            char c = (index >= s.length()) ? 0 : s.charAt(index);
            counts[c]++;
        }
        int[] starts = new int[256];
        int pos = 0;
        for (int i = 0; i < starts.length; i++) {
            starts[i] = pos;
            pos += counts[i];
        }
        String[] res = new String[asciis.length];
        for (String item : asciis) {
            char c = (index >= item.length()) ? 0 : item.charAt(index);
            int place = starts[c];
            res[place] = item;
            starts[c]++;
        }
        System.arraycopy(res, 0, asciis, 0, asciis.length);
        System.out.println(Arrays.toString(res));
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        if (end - start <= 1) return;
        int[] counts = new int[256];
        for (int i = start; i < end; i++) {
            char c = index >= asciis[i].length() ? 0 : asciis[i].charAt(index);
            counts[c]++;
        }
        int[] starts = new int[256];
        int pos = 0;
        for (int i = 0; i < starts.length; i++) {
            starts[i] = pos;
            pos += counts[i];
        }
        String[] res = new String[end - start];
        for (int i = start; i < end; i++) {
            String item = asciis[i];
            char c = index >= item.length() ? 0 : item.charAt(index);
            int place = starts[c];
            res[place] = item;
            starts[c]++;
        }
        System.arraycopy(res, 0, asciis, start, res.length);

        System.out.println(Arrays.toString(res).toCharArray());


        for (int b = 0; b <= 255; b++) {
            if (counts[b] > 1) {
                sortHelperMSD(asciis, start + starts[b] - counts[b], start + starts[b], index + 1);
            }
        }
    }

    public static String[] sort1(String[] ascii) {
        System.out.println("MSD");


        String[] res = new String[ascii.length];
        System.arraycopy(ascii, 0, res, 0, ascii.length);
        sortHelperMSD(res, 0, res.length, 0);
        return res;
    }
}
