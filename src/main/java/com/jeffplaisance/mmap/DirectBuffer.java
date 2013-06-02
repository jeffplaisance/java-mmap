package com.jeffplaisance.mmap;

import java.io.IOException;
import java.nio.ByteOrder;

public class DirectBuffer implements BufferResource {

    private static final long MMAP_THRESHOLD = 256*1024;

    private final DirectMemory memory;
    private final boolean isMmapped;

    public DirectBuffer(long length, ByteOrder endianness) {
        final long address;
        if (length < MMAP_THRESHOLD) {
            address = NativeUtils.getUnsafe().allocateMemory(length);
            isMmapped = false;
        } else {
            try {
                address = NativeUtils.mmap(0, length, NativeUtils.PROT_READ | NativeUtils.PROT_WRITE, NativeUtils.MAP_SHARED | NativeUtils.MAP_ANON, -1, 0);
            } catch (IOException e) {
                throw new OutOfMemoryError("could not allocate "+length+" bytes using mmap");
            }
            isMmapped = true;
        }
        memory = new DirectMemory(address, length, endianness);
    }

    @Override
    public DirectMemory memory() {
        return memory;
    }

    @Override
    public void close() throws IOException {
        if (isMmapped) {
            NativeUtils.munmap(memory.address(), memory.length());
        } else {
            NativeUtils.getUnsafe().freeMemory(memory.address());
        }
    }
}
