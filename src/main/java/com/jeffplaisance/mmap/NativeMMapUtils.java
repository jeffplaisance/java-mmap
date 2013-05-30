package com.jeffplaisance.mmap;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NativeMMapUtils {

    private static final long MAP_FAILED = -1;

    private static final int EACCES = 1;
    private static final int EAGAIN = 2;
    private static final int EBADF = 3;
    private static final int EINVAL = 4;
    private static final int ENFILE = 5;
    private static final int ENODEV = 6;
    private static final int ENOMEM = 7;
    private static final int EPERM = 8;
    private static final int ETXTBSY = 9;
    private static final int EOVERFLOW = 10;

    private static native long mmap0(long addr, long length, int prot, int flags, int fd, long offset);

    private static native int munmap0(long addr, long length);

    private static native int errno();

    static native long getDirectBufferAddress(ByteBuffer byteBuffer);

    static long mmap(long addr, long length, int prot, int flags, int fd, long offset) throws IOException {
        final long ret = mmap0(addr, length, prot, flags, fd, offset);
        if (ret == MAP_FAILED) {
            throw new IOException(String.valueOf("mmap failed with error "+errno()));
        }
        return ret;
    }

    static void munmap(long addr, long length) throws IOException {
        final int ret = munmap0(addr, length);
        if (ret != 0) {
            throw new IOException("munmap failed with error "+errno());
        }
    }
}
