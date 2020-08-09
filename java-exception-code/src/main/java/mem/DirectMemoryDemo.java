package mem;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DirectMemoryDemo {
    public static void main(String[] args) {
        List<ByteBuffer> buffers = new ArrayList<>();
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 1024);
            buffers.add(buffer);
        }
    }
}