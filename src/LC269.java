import java.util.*;

// LC 269 Alien Directory
public class LC269 {
    public static String alienOrder(String[] words) {
        // create the graph without edges
        Graph g = new Graph();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                g.addNode(word.charAt(i));
            }
        }

        // build the trie
        TrieNode trieRoot = new TrieNode('\0');
        for (String word : words) {
            trieRoot.addSequence(word);
        }

        // traverse the trie to build the graph
        trieToGraph(trieRoot, g);

        // do a topological sort of the graph
        // the result is the order
        return g.topoSort();
    }

    static void trieToGraph(TrieNode t, Graph g) {
        List<TrieNode> children = t.getChildren();
        int len = children.size();
        if (len == 0) {
            return;
        }

        if (len > 1) {
            for (int i = 0; i < len - 1; i++) {
                TrieNode t1 = children.get(i);
                TrieNode t2 = children.get(i + 1);
                g.addEdge(t1.getCh(), t2.getCh());
            }
        }

        for (TrieNode child : children) {
            trieToGraph(child, g);
        }
    }

    public static void main(String[] args) {
        String[] words = {
                "wrt",
                "wrf",
                "er",
                "ett",
                "rftt"
        };

        String order = alienOrder(words);
        System.out.println(order);
    }
}

class TrieNode {
    private char ch;
    private Map<Character, TrieNode> childrenMap;
    private List<TrieNode> children;

    TrieNode(char ch) {
        this.ch = ch;
        this.childrenMap = new HashMap<>();
        this.children = new ArrayList<>();
    }

    void addSequence(CharSequence seq) {
        char c = seq.charAt(0);
        TrieNode t = childrenMap.get(c);
        if (t == null) {
            t = new TrieNode(c);
            childrenMap.put(c, t);
            children.add(t);
        }
        int len = seq.length();
        if (len > 1) {
            seq = seq.subSequence(1, len);
            t.addSequence(seq);
        }
    }

    List<TrieNode> getChildren() {
        return children;
    }

    char getCh() {
        return ch;
    }
}

class GraphNode {
    private char ch;
    private int state; // 0 - not visited, 1 - visiting, 2 - visited
    private Map<Character, GraphNode> adjacencyList;

    GraphNode(char ch) {
        this.ch = ch;
        adjacencyList = new HashMap<>();
        state = 0;
    }

    Map<Character, GraphNode> getAdjacencyList() {
        return adjacencyList;
    }

    int getState() {
        return state;
    }

    void setState(int value) {
        state = value;
    }

    char getCh() {
        return ch;
    }
}

class Graph {
    private Map<Character, GraphNode> nodes;

    Graph() {
        nodes = new HashMap<>();
    }

    void addNode(char c) {
        GraphNode node = nodes.get(c);
        if (node == null) {
            node = new GraphNode(c);
            nodes.put(c, node);
        }
    }

    void addEdge(char from, char to) {
        GraphNode n1 = nodes.get(from);
        GraphNode n2 = nodes.get(to);
        Map<Character, GraphNode> adjList = n1.getAdjacencyList();
        if (!adjList.containsKey(to)) {
            adjList.put(to, n2);
        }
    }

    /**
     * Topological sort of the graph
     * @return the ordered characters in a string. empty if the graph is not a DAG.
     */
    String topoSort() {
        StringBuilder sb = new StringBuilder();
        boolean success = true;
        for (GraphNode n: nodes.values()) {
            if (!topoSort(n, sb)) {
                success = false;
                break;
            }
        }

        if (!success) {
            return "";
        } else {
            // reverse the string and return
            return sb.reverse().toString();
        }
    }

    static boolean topoSort(GraphNode node, StringBuilder sb) {
        int state = node.getState();
        if (state == 1) {
            // not a DAG
            return false;
        }

        if (state == 2) {
            return true;
        }

        node.setState(1);

        Map<Character, GraphNode> adjList = node.getAdjacencyList();
        for (GraphNode n : adjList.values()) {
            if (!topoSort(n, sb)) {
                return false;
            }
        }
        sb.append(node.getCh());
        node.setState(2);
        return true;
    }
}
