package com.jeffplaisance.mmap;

import junit.framework.TestCase;

import java.io.File;
import java.nio.ByteOrder;

public class TestMMap extends TestCase {

    File tmpDir;

    @Override
    public void setUp() throws Exception {
        tmpDir = File.createTempFile("tmp", "");
        tmpDir.delete();
        tmpDir.mkdir();
    }

    public void testStuff() throws Exception {
        final File tmp = File.createTempFile("tmp", ".tmp", tmpDir);
        MMapBuffer buffer = new MMapBuffer(tmp, 0, 1024 * 1024, true, false, ByteOrder.LITTLE_ENDIAN);
        Memory memory = buffer.memory();
        for (int i = 0; i < 1024 * 1024 / 8; i++) {
            memory.putLong(i * 8, i);
        }
        for (int i = 0; i < 1024 * 1024 / 8; i++) {
            assertEquals(memory.getLong(i * 8), i);
        }
        HeapBuffer heapBuffer = new HeapBuffer(1024 * 1024, ByteOrder.LITTLE_ENDIAN);
        heapBuffer.memory().putBytes(0, memory);
        buffer.close();
        buffer = new MMapBuffer(tmp, 0, 1024 * 1024, true, true, ByteOrder.LITTLE_ENDIAN);
        memory = buffer.memory();
        for (int i = 0; i < 1024 * 1024; i++) {
            assertEquals(heapBuffer.memory().getByte(i), memory.getByte(i));
        }
    }

    @Override
    public void tearDown() throws Exception {
        final Process process = Runtime.getRuntime().exec(new String[]{"rm", "-rf", tmpDir.getPath()});
        process.waitFor();
    }
}
