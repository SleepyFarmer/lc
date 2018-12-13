import java.util.Iterator;

public class LC385 {
    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     * public interface NestedInteger {
     *     // Constructor initializes an empty nested list.
     *     public NestedInteger();
     *
     *     // Constructor initializes a single integer.
     *     public NestedInteger(int value);
     *
     *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
     *     public boolean isInteger();
     *
     *     // @return the single integer that this NestedInteger holds, if it holds a single integer
     *     // Return null if this NestedInteger holds a nested list
     *     public Integer getInteger();
     *
     *     // Set this NestedInteger to hold a single integer.
     *     public void setInteger(int value);
     *
     *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
     *     public void add(NestedInteger ni);
     *
     *     // @return the nested list that this NestedInteger holds, if it holds a nested list
     *     // Return null if this NestedInteger holds a single integer
     *     public List<NestedInteger> getList();
     * }
     */

    // dummy NestedInteger to get the code to compile
    static class NestedInteger {
        public NestedInteger() {

        }

        public NestedInteger(int n) {

        }

        public void add(NestedInteger ni) {

        }
    }
    static class Token {
        char c;
        int n;

        Token(char c, int n) {
            this.c = c;
            this.n = n;
        }
    }

    static class TokenIterator implements Iterator<Token> {
        String s;
        int len;
        int index;

        TokenIterator(String s) {
            this.s = s;
            this.len = s.length();
            this.index = 0;
        }

        public Token next() {
            char c = s.charAt(index);
            Token t;
            switch(c) {
                case '[':
                    t = new Token('[', 0);
                    index ++;
                    return t;
                case ']':
                    t = new Token(']', 0);
                    index ++;
                    if (index < len) {
                        // test for ,
                        if (s.charAt(index) == ',') {
                            if (index + 1 == len) {
                                // ended with ,
                                throw new RuntimeException("bad input");
                            } else {
                                index ++; // skip the ,
                            }
                        }
                    }
                    return t;
                case '-':
                    if (index >= len) {
                        throw new RuntimeException("Bad Input");
                    }

                    index ++;
                    return new Token('\0', 0 - parseInt());

                default:
                    return new Token('\0', parseInt());
            }
        }

        public boolean hasNext() {
            return index < len;
        }

        int parseInt() {
            int n = 0;
            for (int i = index; i < len; i++) {
                char c = s.charAt(i);
                if (c == ',') {
                    if (i != index) {
                        index = i + 1;
                        return n;
                    } else {
                        throw new RuntimeException("Bad Input");
                    }
                } else if (c == ']') {
                    index = i;
                    return n;
                }

                int d = (int)c - (int)'0';
                if (d < 0 || d > 9) {
                    throw new RuntimeException("Bad Input");
                }

                n = n * 10 + d;
            }

            index = len;
            return n;
        }
    }

    public NestedInteger deserialize(String s) {
        Iterator<Token> iter = new TokenIterator(s);

        if (!iter.hasNext()) {
            return null;
        }

        try {
            Token t = iter.next();
            NestedInteger ni;
            if (t.c == '[') {
                ni = deserializeList(iter);
            } else if (t.c == '\0') {
                ni = new NestedInteger(t.n);
            } else {
                return null;
            }

            // make sure there are no more tokens
            return iter.hasNext() ? null : ni;
        } catch (Exception e) {
            return null;
        }
    }

    NestedInteger deserializeList(Iterator<Token> iter) {
        NestedInteger ni = new NestedInteger();
        while (iter.hasNext()) {
            Token t = iter.next();
            switch (t.c) {
                case '[':
                    ni.add(deserializeList(iter));
                    break;
                case ']':
                    return ni;
                default:
                    ni.add(new NestedInteger(t.n));
                    break;
            }
        }

        throw new RuntimeException("Bad Input");
    }
}
