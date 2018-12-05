import java.util.ArrayList;
import java.util.List;

public class LC68 {
    static class LineInfo {
        boolean done;
        int start; // inclusive
        int end; // exclusive
        int totalCharCount;
        int totalSpaces;
    }

    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> lines = new ArrayList<>();
        int start = 0;
        for(;;) {
            LineInfo info = getNext(words, start, maxWidth);
            String line = toLine(info, words);
            if (line != null) {
                lines.add(line);
            }
            if (info.done) {
                break;
            }
            start = info.end;
        }
        return lines;
    }

    static LineInfo getNext(String[] words, int start, int maxWidth) {
        LineInfo info = new LineInfo();
        info.start = start;
        int i = start;
        info.totalCharCount = 0;
        int remaining = maxWidth;
        while (i < words.length) {
            String w = words[i];
            int len = w.length();
            int need = len;
            if (i != start) {
                need++;
            }
            if (remaining >= need) {
                info.totalCharCount += len;
                i ++;
                info.end = i;
                remaining -= need;
            } else {
                break;
            }
        }

        info.done = i == words.length;
        info.totalSpaces = maxWidth - info.totalCharCount;
        return info;
    }

    static String toLine(LineInfo info, String[] words) {
        StringBuilder sb = new StringBuilder();
        int wordCount = info.end - info.start;
        sb.append(words[info.start]);
        // case 1: single word
        if (wordCount == 1) {
            for (int i = 0; i < info.totalSpaces; i++) {
                sb.append(" ");
            }
        } else if (info.done) {
            // last line
            for (int i = info.start+1; i < info.end; i++) {
                sb.append(" ").append(words[i]);
            }
            for (int i = 0; i < info.totalSpaces - info.end + info.start + 1; i++) {
                sb.append(" ");
            }
        } else {
            int n = info.totalSpaces / (info.end - info.start - 1);
            int m = info.totalSpaces % (info.end - info.start - 1);
            for (int i = info.start + 1; i < info.end; i++) {
                int spaces = m > 0 ? n + 1 : n;
                for (int j = 0; j < spaces; j++) {
                    sb.append(" ");
                }
                sb.append(words[i]);
                m --;
            }
        }

        return sb.toString();
    }

    static void printList(List<String> lst) {
        for (String str : lst) {
            System.out.println(str);
        }
    }

    public static void main(String[] args) {
        String[] words = new String[] {"This", "is", "an", "example", "of", "text", "justification."};
        int maxWidth = 16;

        List<String> lst = fullJustify(words, maxWidth);
        printList(lst);
    }
}
