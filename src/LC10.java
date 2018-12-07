import java.util.*;
public class LC10 {
    // regular expression matching
    public boolean isMatch(String s, String p) {
        // TODO: handle special cases
        // and parameter validation

        int slen = s.length();
        int plen = p.length();
        // tokenize p
        List<Integer> t = new ArrayList<>(p.length());
        for (int i = 0; i < plen; i++) {
            char c = p.charAt(i);
            if (c == '*') {
                int index = t.size() - 1;
                int n = t.get(index);
                t.set(index, -n);
            } else {
                t.add((int)c);
            }
        }

        plen = t.size();

        boolean [][] m = new boolean[slen + 1][plen + 1];
        m[0][0] = true;
        for(int j = 0; j < plen; j++) {
            int n = t.get(j);
            if (n > 0) {
                m[0][j+1] = false;
            } else {
                m[0][j+1] = m[0][j];
            }
        }
        for(int i=0; i < slen; i++) {
            for (int j=0; j < plen; j ++) {
                int n = t.get(j);
                if (n > 0) {
                    char c = (char)n;
                    if (c == '.' || s.charAt(i) == c) {
                        m[i+1][j+1] = m[i][j];
                    } else {
                        m[i+1][j+1] = false;
                    }
                } else if (m[i + 1][j]) {
                    m[i+1][j+1] = true;
                } else {
                    char c = (char)(-n);
                    for (int k = 0; k <= i; k++) {
                        if (c == '.' || s.charAt(i-k) == c) {
                            if(m[i-k][j]) {
                                m[i+1][j+1] = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return m[slen][plen];
    }
}
