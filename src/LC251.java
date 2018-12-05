import java.util.*;

public class LC251 {
    // Flatten 2D Vector

    static class MyIterator implements Iterator<Integer> {
        List<List<Integer>> twoDList;
        int row;
        int col;
        int count;
        int pos;
        public MyIterator(List<List<Integer>> input) {
            twoDList = input;
            row = 0;
            col = 0;
            pos = 0;
            count = 0;
            for (List<Integer> lst : input) {
                count += lst.size();
            }
        }

        @Override
        public boolean hasNext() {
            return pos < count;
        }

        @Override
        public Integer next() {
            if (pos >= count) {
                throw new NoSuchElementException();
            }
            List<Integer> theRow = twoDList.get(row);
            int ret = theRow.get(col);
            pos ++;

            if (col == theRow.size() -1) {
                col = 0;
                row ++;
            } else {
                col ++;
            }

            return ret;
        }
    }

    static Iterator<Integer> flatten(List<List<Integer>> input) {
        return new MyIterator(input);
    }

    static void addToList(int[] ints, List<List<Integer>> list) {
        List<Integer> l = new ArrayList<>();
        for (int n : ints) {
            l.add(n);
        }
        list.add(l);
    }

    public static void main(String[] args) {
        List<List<Integer>> input = new ArrayList<>();
        addToList(new int[]{1,2,3,4}, input);
        addToList(new int[]{5}, input);
        addToList(new int[]{6,7}, input);
        addToList(new int[]{8,9}, input);

        Iterator<Integer> iter = flatten(input);
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}

