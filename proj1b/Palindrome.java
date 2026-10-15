public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        int n = word.length();
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < n; i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return helper1(deque);
    }

    private boolean helper1(Deque<Character> dq) {
        if (dq.size() <= 1) {
            return true;
        }
        if (dq.removeFirst() == dq.removeLast()) {
            return helper1(dq);
        }
        return false;

    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return helper2(deque, cc);
    }

    private boolean helper2(Deque<Character> dq, CharacterComparator cc) {
        if (dq.size() <= 1) {
            return true;
        }
        if (cc.equalChars(dq.removeFirst(), dq.removeLast())) {
            return helper2(dq, cc);
        }
        return false;
    }
}
