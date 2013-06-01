package com.jeffplaisance.mmap;

import junit.framework.TestCase;

import java.nio.ByteOrder;

public class TestMemory extends TestCase {
    public void testStuff() {
        final long[] data = new long[8];
        final HeapMemory heap = new HeapMemory(data, 0, 64, ByteOrder.nativeOrder());
        for (int i = 0; i < data.length; i++) {
            heap.putLong(i * 8, i);
        }
        for (int i = 0; i < data.length; i++) {
            assertEquals(data[i], i);
        }
        DirectMemory direct = new DirectMemory(NativeUtils.getUnsafe().allocateMemory(64), 64, ByteOrder.nativeOrder());
        direct.putBytes(0, heap);
        for (int i = 0; i < data.length; i++) {
            assertEquals(data[i], direct.getLong(i*8));
        }
    }
}
