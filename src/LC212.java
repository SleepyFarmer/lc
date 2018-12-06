import java.util.*;

public class LC212 {
    // Word Search II
    public static List<String> findWords(char[][] board, String[] words) {
        // build trie for all words
        TrieNode root = new TrieNode('\0');
        for (String word : words) {
            root.addSubString(word, 0);
        }

        // initialize visited status
        int row = board.length;
        int col = board[0].length;
        boolean[][] visited = new boolean[row][col];
        clearVisited(visited);

        // try find the words
        Set<String> foundWords = new HashSet<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                find(board, i, j, visited, root, foundWords);
            }
        }

        List<String> lst = new ArrayList<>(foundWords.size());
        for (String word : foundWords) {
            lst.add(word);
        }

        return lst;
    }

    static void find(char[][] board, int i, int j, boolean[][] visited, TrieNode node, Set<String> foundWords) {
        if (visited[i][j]) {
            return;
        }

        char c = board[i][j];
        Map<Character, TrieNode> children = node.getChildren();
        TrieNode t = children.get(c);
        if (t != null) {
            visited[i][j] = true;
            String word = t.getWord();
            if (word != null) {
                // found one
                foundWords.add(word);
            }
            if (j > 0) {
                find(board, i, j -1 , visited, t, foundWords);
            }

            if (j < board[i].length - 1) {
                find(board, i, j + 1, visited, t, foundWords);
            }

            if (i > 0) {
                find(board, i - 1, j, visited, t, foundWords);
            }

            if (i < board.length - 1) {
                find(board, i + 1, j, visited, t, foundWords);
            }

            visited[i][j] = false;
        }
    }

    static void clearVisited(boolean[][] visited) {
        for(int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                visited[i][j] = false;
            }
        }
    }

    static class TrieNode {
        char ch;
        Map<Character, TrieNode> children;
        String word;

        TrieNode(char ch) {
            this.ch = ch;
            children = new HashMap<>();
            word = null;
        }

        void addSubString(String word, int start) {
            char c = word.charAt(start);
            TrieNode t = children.get(c);
            if (t == null) {
                t = new TrieNode(c);
                children.put(c, t);
            }

            if (start == word.length() - 1) {
                t.word = word;
            } else {
                t.addSubString(word, start + 1);
            }
        }

        Map<Character, TrieNode> getChildren() {
            return children;
        }

        String getWord() {
            return word;
        }
    }
}
