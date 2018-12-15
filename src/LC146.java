import java.util.*;

public class LC146 {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(String.format("%d", cache.get(1)));
        cache.put(3, 3);
        System.out.println(String.format("%d", cache.get(2)));
        cache.put(4, 4);
        System.out.println(String.format("%d", cache.get(1)));
        System.out.println(String.format("%d", cache.get(3)));
        System.out.println(String.format("%d", cache.get(4)));
    }
}

class LRUCache {
    static class Node {
        int key;
        int val;
        Node prev;
        Node next;
        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    Node head;
    Node tail;
    int capacity;
    Map<Integer, Node> map;

    public LRUCache(int capacity) {
        //if (capacity <= 0) {
        //   throw new RuntimeException("Invalid capacity");
        //}
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node n = getNode(key);
        return n == null ? -1 : n.val;
    }

    Node getNode(int key) {
        System.out.println(String.format("Getting %d", key));
        Node n = map.get(key);
        if (n == null) {
            return null;
        }

        n.prev.next = n.next;
        n.next.prev = n.prev;

        n.next = head.next;
        n.prev = head;
        head.next = n;
        n.next.prev = n;

        return n;
    }

    public void put(int key, int value) {
        System.out.println(String.format("Putting %d, %d", key, value));
        Node n = getNode(key);
        if (n == null) {
            // need to add an entry
            if (map.size() == capacity) {
                // need to remove one
                Node rm = tail.prev;
                tail.prev = rm.prev;
                tail.prev.next = tail;
                map.remove(rm.key);
            }

            // add a new node
            n = new Node(key, value);
            map.put(key, n);
            n.prev = head;
            n.next = head.next;
            head.next = n;
            n.next.prev = n;
        }
    }
}

