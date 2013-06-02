package com.jeffplaisance.mmap;

import junit.framework.TestCase;

import java.nio.ByteOrder;

public class TestDirect extends TestCase {
    public void testStuff() throws Exception {
        final long[] data = new long[1024*1024];
        final HeapMemory heap = new HeapMemory(data, 0, 1024*1024*8, ByteOrder.nativeOrder());
        for (int i = 0; i < data.length; i++) {
            heap.putLong(i * 8, i);
        }
        for (int i = 0; i < data.length; i++) {
            assertEquals(data[i], i);
        }
        final DirectBuffer directBuffer = new DirectBuffer(1024*1024*8, ByteOrder.nativeOrder());
        DirectMemory direct = directBuffer.memory();
        direct.putBytes(0, heap);
        for (int i = 0; i < data.length; i++) {
            assertEquals(data[i], direct.getLong(i*8));
        }
        directBuffer.close();
    }
}
