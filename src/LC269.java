import java.util.*;

// LC 269 Alien Directory
public class LC269 {
    static String alienOrder(String[] words) {
        // create the graph without edges
        Graph g = new Graph();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                g.addNode(word.charAt(i));
            }
        }

        if (words.length > 1) {
            // find the edges and add to the graph
            for (int i=0; i<words.length - 1; i++) {
                addEdges(words[i], words[i+1], g);
            }
        }

        // do a topological sort of the graph
        // the result is the order
        return g.topoSort();
    }

    static void addEdges(String w1, String w2, Graph g) {
        int len1 = w1.length();
        int len2 = w2.length();
        int len = len1 <= len2 ? len1 : len2;
        for (int i = 0; i < len; i++) {
            char c1 = w1.charAt(i);
            char c2 = w2.charAt(i);
            if (c1 != c2) {
                g.addEdge(c1, c2);
                break;
            }
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

    static class GraphNode {
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

    static class Graph {
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
}
