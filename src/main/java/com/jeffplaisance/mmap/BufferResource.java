package com.jeffplaisance.mmap;

import java.io.Closeable;

public interface BufferResource extends Closeable {
    Memory memory();
}
