package com.jeffplaisance.mmap;

import sun.misc.Unsafe;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class HeapMemory implements Memory {

    private static final Unsafe UNSAFE = NativeMMapUtils.getUnsafe();
    public static final int LONG_ARRAY_BASE_OFFSET = NativeMMapUtils.LONG_ARRAY_BASE_OFFSET;
    public static final int BYTE_ARRAY_BASE_OFFSET = NativeMMapUtils.BYTE_ARRAY_BASE_OFFSET;

    private final long[] data;
    private final long base;
    private final long length;
    private final ByteOrder byteOrder;
    private final HeapDataAccess access;

    public HeapMemory(long[] data, long base, long length, ByteOrder byteOrder) {
        this.data = data;
        this.base = base;
        this.length = length;
        this.byteOrder = byteOrder;
        access = byteOrder.equals(ByteOrder.nativeOrder()) ? new NativeEndianHeapDataAccess() : new ReverseEndianHeapDataAccess();
    }

    public void checkBounds(long offset, long length) {
        if (offset < 0) {
            throw new IllegalArgumentException();
        }
        if (offset + length > this.length) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getByte(long offset) {
        checkBounds(offset, 1);
        return access.getByte(data, base+offset)&0xFF;
    }

    @Override
    public byte getSignedByte(long offset) {
        checkBounds(offset, 1);
        return access.getByte(data, base+offset);
    }

    @Override
    public short getShort(long offset) {
        checkBounds(offset, 2);
        return access.getShort(data, base+offset);
    }

    @Override
    public int getInt(long offset) {
        checkBounds(offset, 4);
        return access.getInt(data, base+offset);
    }

    @Override
    public long getLong(long offset) {
        checkBounds(offset, 8);
        return access.getLong(data, base+offset);
    }

    @Override
    public char getChar(long offset) {
        checkBounds(offset, 2);
        return access.getChar(data, base+offset);
    }

    @Override
    public float getFloat(long offset) {
        checkBounds(offset, 4);
        return access.getFloat(data, base+offset);
    }

    @Override
    public double getDouble(long offset) {
        checkBounds(offset, 8);
        return access.getDouble(data, base+offset);
    }

    @Override
    public void putByte(long offset, int v) {
        checkBounds(offset, 1);
        access.putByte(data, base+offset, (byte)v);
    }

    @Override
    public void putShort(long offset, short v) {
        checkBounds(offset, 2);
        access.putShort(data, base+offset, v);
    }

    @Override
    public void putInt(long offset, int v) {
        checkBounds(offset, 4);
        access.putInt(data, base+offset, v);
    }

    @Override
    public void putLong(long offset, long v) {
        checkBounds(offset, 8);
        access.putLong(data, base+offset, v);
    }

    @Override
    public void putChar(long offset, char v) {
        checkBounds(offset, 2);
        access.putChar(data, base+offset, v);
    }

    @Override
    public void putFloat(long offset, float v) {
        checkBounds(offset, 4);
        access.putFloat(data, base+offset, v);
    }

    @Override
    public void putDouble(long offset, double v) {
        checkBounds(offset, 8);
        access.putDouble(data, base+offset, v);
    }

    @Override
    public void getBytes(long offset, byte[] dest, int destPos, int length) {
        checkBounds(offset, length);
        checkByteArrayBounds(dest, destPos, length);
        UNSAFE.copyMemory(data, LONG_ARRAY_BASE_OFFSET+base+offset, dest, BYTE_ARRAY_BASE_OFFSET+destPos, length);
    }

    private void checkByteArrayBounds(byte[] dest, int destPos, int length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        if (destPos < 0) {
            throw new IllegalArgumentException();
        }
        if (destPos+length > dest.length) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void getBytes(long offset, byte[] dest) {
        getBytes(offset, dest, 0, dest.length);
    }

    @Override
    public void putBytes(long offset, byte[] src, int srcPos, int length) {
        checkBounds(offset, length);
        checkByteArrayBounds(src, srcPos, length);
        UNSAFE.copyMemory(src, BYTE_ARRAY_BASE_OFFSET+srcPos, data, LONG_ARRAY_BASE_OFFSET+base+offset, length);
    }

    @Override
    public void putBytes(long offset, byte[] src) {
        putBytes(offset, src, 0, src.length);
    }

    @Override
    public void getBytes(long srcPos, Memory dest, long destPos, long length) {
        dest.putBytes(destPos, this, srcPos, length);
    }

    @Override
    public void getBytes(long offset, Memory dest) {
        getBytes(offset, dest, 0, dest.length());
    }

    @Override
    public void putBytes(long destPos, Memory src, long srcPos, long length) {
        src.getBytes(srcPos, this, destPos, length);
    }

    @Override
    public void putBytes(long offset, Memory src) {
        putBytes(offset, src, 0, src.length());
    }

    @Override
    public void getBytes(long srcPos, DirectMemory dest, long destPos, long length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        checkBounds(srcPos, length);
        dest.checkBounds(destPos, length);
        UNSAFE.copyMemory(data, LONG_ARRAY_BASE_OFFSET+base+srcPos, null, dest.address()+destPos, length);
    }

    @Override
    public void getBytes(long offset, DirectMemory dest) {
        getBytes(offset, dest, 0, dest.length());
    }

    @Override
    public void putBytes(long destPos, DirectMemory src, long srcPos, long length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        checkBounds(destPos, length);
        src.checkBounds(srcPos, length);
        UNSAFE.copyMemory(null, src.address()+srcPos, data, LONG_ARRAY_BASE_OFFSET+base+destPos, length);
    }

    @Override
    public void putBytes(long offset, DirectMemory src) {
        putBytes(offset, src, 0, src.length());
    }

    @Override
    public void getBytes(long srcPos, HeapMemory dest, long destPos, long length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        checkBounds(srcPos, length);
        dest.checkBounds(destPos, length);
        UNSAFE.copyMemory(data, LONG_ARRAY_BASE_OFFSET+base+srcPos, dest.data, LONG_ARRAY_BASE_OFFSET+dest.base+destPos, length);
    }

    @Override
    public void getBytes(long offset, HeapMemory dest) {
        getBytes(offset, dest, 0, dest.length());
    }

    @Override
    public void putBytes(long destPos, HeapMemory src, long srcPos, long length) {
        src.getBytes(srcPos, this, destPos, length);
    }

    @Override
    public void putBytes(long offset, HeapMemory src) {
        putBytes(offset, src, 0, src.length());
    }

    @Override
    public void getBytes(long offset, ByteBuffer dest) {
        checkBounds(offset, dest.remaining());
        if (dest.isDirect()) {
            final long destAddress = NativeMMapUtils.getDirectBufferAddress(dest);
            if (destAddress != 0) {
                UNSAFE.copyMemory(data, LONG_ARRAY_BASE_OFFSET+base+offset, null, destAddress+dest.position(), dest.remaining());
                dest.position(dest.limit());
                return;
            }
        }
        if (dest.hasArray()) {
            UNSAFE.copyMemory(data, LONG_ARRAY_BASE_OFFSET+base+offset, dest.array(), BYTE_ARRAY_BASE_OFFSET+dest.arrayOffset()+dest.position(), dest.remaining());
            dest.position(dest.limit());
        } else {
            for (long l = offset; dest.hasRemaining(); l++) {
                dest.put(access.getByte(data, base+l));
            }
        }
    }

    @Override
    public void putBytes(long offset, ByteBuffer src) {
        checkBounds(offset, src.remaining());
        if (src.isDirect()) {
            final long srcAddress = NativeMMapUtils.getDirectBufferAddress(src);
            if (srcAddress != 0) {
                UNSAFE.copyMemory(null, srcAddress+src.position(), data, LONG_ARRAY_BASE_OFFSET+base+offset, src.remaining());
                src.position(src.limit());
                return;
            }
        }
        if (src.hasArray()) {
            UNSAFE.copyMemory(src.array(), BYTE_ARRAY_BASE_OFFSET+src.arrayOffset()+src.position(), data, LONG_ARRAY_BASE_OFFSET+base+offset, src.remaining());
            src.position(src.limit());
        } else {
            for (long l = offset; src.hasRemaining(); l++) {
                access.putByte(data, base+l, src.get());
            }
        }
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public Memory slice(long offset, long length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        checkBounds(offset, length);
        return new HeapMemory(data, base+offset, length, byteOrder);
    }

    @Override
    public ByteOrder order() {
        return byteOrder;
    }
}
