package com.jeffplaisance.mmap;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;

public class MMapBuffer implements BufferResource {

    private final DirectMemory memory;

    public MMapBuffer(File file, long offset, long length, boolean shared, boolean readOnly, ByteOrder endianness) throws IOException {
        final int fd = NativeUtils.open(file.getPath(), readOnly);
        try {
            if (file.length() < offset+length) {
                NativeUtils.ftruncate(fd, offset+length);
            }
            final long address = NativeUtils.mmap(
                    0,
                    length,
                    readOnly ? NativeUtils.PROT_READ : NativeUtils.PROT_READ | NativeUtils.PROT_WRITE,
                    shared ? NativeUtils.MAP_SHARED : NativeUtils.MAP_PRIVATE,
                    fd,
                    offset
            );
            memory = new DirectMemory(address, length, endianness);
        } finally {
            NativeUtils.close(fd);
        }
    }

    @Override
    public Memory memory() {
        return memory;
    }

    @Override
    public void close() throws IOException {
        NativeUtils.munmap(memory.address(), memory.length());
    }
}
