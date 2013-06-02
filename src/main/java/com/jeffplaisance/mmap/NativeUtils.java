package com.jeffplaisance.mmap;

import sun.misc.Unsafe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class NativeUtils {

    static {
        final String os = System.getProperty("os.name");
        final String arch = System.getProperty("os.arch");
        final String libName;
        final String extension;
        if (os.equals("Mac OS X")) {
            libName = "libjavammap";
            extension = "dylib";
        } else {
            throw new UnsupportedOperationException("combination of os: "+os+" and arch: "+arch+" is not supported");
        }
        final String path = "/nativelib/" + os.replaceAll("\\s", "_") + "-" + arch + "/" + libName + "." + extension;
        final InputStream in = NativeUtils.class.getResourceAsStream(path);
        if (in == null) {
            throw new UnsupportedOperationException(path+" does not exist in classpath");
        }
        try {
            final File tmp = File.createTempFile("libjavammap", "."+extension);
            final FileOutputStream out = new FileOutputStream(tmp);
            final byte[] bytes = new byte[4096];
            while (true) {
                final int read = in.read(bytes);
                if (read < 0) break;
                out.write(bytes, 0, read);
            }
            in.close();
            out.close();
            System.load(tmp.getPath());
            tmp.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final long MAP_FAILED = -1;

    public static final int EACCES = 1;
    public static final int EAGAIN = 2;
    public static final int EBADF = 3;
    public static final int EINVAL = 4;
    public static final int ENFILE = 5;
    public static final int ENODEV = 6;
    public static final int ENOMEM = 7;
    public static final int EPERM = 8;
    public static final int ETXTBSY = 9;
    public static final int EOVERFLOW = 10;
    public static final int EINTR = 11;
    public static final int EIO = 12;
    public static final int EEXIST = 13;
    public static final int EFBIG = 14;
    public static final int EISDIR = 15;
    public static final int EMFILE = 16;
    public static final int ENAMETOOLONG = 17;
    public static final int EDQUOT = 18;
    public static final int ELOOP = 19;
    public static final int ENOENT = 20;
    public static final int ENOSPC = 21;
    public static final int ENOTDIR = 22;
    public static final int ENXIO = 23;
    public static final int EROFS = 24;

    public static final int PROT_EXEC = 1;
    public static final int PROT_READ = 2;
    public static final int PROT_WRITE = 4;
    public static final int PROT_NONE = 8;

    public static final int MAP_SHARED = 1;
    public static final int MAP_PRIVATE = 2;
    public static final int MAP_ANON = 4;

    public static final int O_RDONLY = 1;
    public static final int O_WRONLY = 2;
    public static final int O_RDWR = 4;

    public static final int O_CREAT = 8;
    public static final int O_TRUNC = 16;
    public static final int O_APPEND = 32;
    public static final int O_EXCL = 64;

    private static final Unsafe UNSAFE;
    public static final int BYTE_ARRAY_BASE_OFFSET;
    public static final int LONG_ARRAY_BASE_OFFSET;

    static {
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
            BYTE_ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
            LONG_ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(long[].class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static native long mmap0(long addr, long length, int prot, int flags, int fd, long offset);

    private static native int munmap0(long addr, long length);

    private static native int open(String pathname, int flags, int mode);

    private static native int close0(int fd);

    private static native int ftruncate0(int fd, long newSize);

    private static native int errno();

    static native long getDirectBufferAddress(ByteBuffer byteBuffer);

    static long mmap(long addr, long length, int prot, int flags, int fd, long offset) throws IOException {
        final long ret = mmap0(addr, length, prot, flags, fd, offset);
        if (ret == MAP_FAILED) {
            throw new IOException("mmap failed with error "+errno());
        }
        return ret;
    }

    static void munmap(long addr, long length) throws IOException {
        final int ret = munmap0(addr, length);
        if (ret != 0) {
            throw new IOException("munmap failed with error "+errno());
        }
    }

    static int open(String path, boolean readOnly) throws IOException {
        final int fd = open(path, readOnly ? O_RDONLY : (O_RDWR | O_CREAT), (6<<6)|(4<<3)|4);
        if (fd < 0) {
            throw new FileNotFoundException("open failed with error "+errno());
        }
        return fd;
    }

    static void close(int fd) throws IOException {
        if (close0(fd) != 0) {
            throw new IOException("close failed with error "+errno());
        }
    }

    static void ftruncate(int fd, long newLength) throws IOException {
        if (ftruncate0(fd, newLength) != 0) {
            throw new IOException("ftruncate failed with error "+errno());
        }
    }

    static Unsafe getUnsafe() {
        return UNSAFE;
    }
}
