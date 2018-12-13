public class LC291 {
    boolean matchPattern(String str, String pattern) {
        return false;
    }

    public static void main(String [] args) {
        test("redblueredblue","abab");
    }

    static void test(String str, String pattern) {
        LC291 lc = new LC291();
        boolean ret = lc.matchPattern(str, pattern);
        System.out.println(String.format("\"%s\", \"%s\" => %b ", str, pattern, ret));
    }
}
