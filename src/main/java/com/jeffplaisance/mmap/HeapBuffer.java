package com.jeffplaisance.mmap;

import java.nio.ByteOrder;

public class HeapBuffer implements BufferResource {

    private final HeapMemory memory;

    public HeapBuffer(long size, ByteOrder byteOrder) {
        memory = new HeapMemory(new long[(int) ((size-1)/8+1)], 0, size, byteOrder);
    }

    @Override
    public Memory memory() {
        return memory;
    }

    @Override
    public void close() {}
}
