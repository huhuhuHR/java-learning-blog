package mem;

import java.util.ArrayList;

public class HeapOutOfMemoryDemo {
    public static void main(String[] args) throws Exception {
        ArrayList<String> arrayList = new ArrayList<>(100000000);
        for (int i = 0; i <= 100000000; ++i) {
            arrayList.add(Integer.toString(i));
            if (i % 10000 == 0) {
                System.out.println(i);
            }
        }
    }
}
