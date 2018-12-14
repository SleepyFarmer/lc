import java.util.*;

public class LC291 {
    boolean matchPattern(String str, String pattern) {
        if (str == null) {
            return pattern == null;
        }
        int lenS = str.length();
        int lenP = pattern.length();
        if (lenS < lenP) {
            return false;
        } else if (lenS == 0) {
            return lenP == 0;
        }

        Map<Character, CharSequence> lookup = new HashMap<>();
        return match(lookup, str, pattern);
    }

    void debug(Map<Character, CharSequence> lookup, CharSequence content, CharSequence pattern) {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(content);
        System.out.println(pattern);
        for(Map.Entry<Character, CharSequence> kv : lookup.entrySet()) {
            System.out.println(String.format("%c => %s", kv.getKey(), kv.getValue()));
        }
    }

    boolean match(Map<Character, CharSequence> lookup, CharSequence content, CharSequence pattern) {
        //debug(lookup, content, pattern);
        int lenC = content.length();
        int lenP = pattern.length();
        if (lenP == 0) {
            return lenC == 0;
        } else if (lenC < lenP) {
            return false;
        }

        char p = pattern.charAt(0);
        CharSequence seq = lookup.get(p);
        if (seq != null) {
            int len = seq.length();
            if (lenC < len) {
                return false;
            }
            if (!content.subSequence(0, len).equals(seq)) {
                return false;
            }

            return match(lookup, content.subSequence(len, lenC), pattern.subSequence(1, lenP));
        }

        if (lenP == 1) {
            // last one
            return lenC > 0;
        }

        for (int i = 1; i <= lenC - lenP + 1; i++) {
            seq = content.subSequence(0, i);
            lookup.put(p, seq);
            boolean matched = match(lookup, content.subSequence(i, lenC), pattern.subSequence(1, lenP));
            if (matched) {
                return true;
            }

            lookup.remove(p);
        }
        return false;
    }

    public static void main(String [] args) {
        test("redblueredblue","abab");
        test("asdasdasdasd", "aaaa");
        test("xyzabcxzyabc", "aabb");
    }

    static void test(String str, String pattern) {
        LC291 lc = new LC291();
        boolean ret = lc.matchPattern(str, pattern);
        System.out.println(String.format("\"%s\", \"%s\" => %b ", str, pattern, ret));
    }
}
