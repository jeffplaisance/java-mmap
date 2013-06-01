package com.jeffplaisance.mmap;

import sun.misc.Unsafe;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DirectMemory implements Memory {

    private static final Unsafe UNSAFE = NativeUtils.getUnsafe();
    public static final int BYTE_ARRAY_BASE_OFFSET = NativeUtils.BYTE_ARRAY_BASE_OFFSET;

    private final long address;
    private final long length;
    private final ByteOrder byteOrder;
    private final DirectDataAccess access;

    DirectMemory(long address, long length, ByteOrder byteOrder) {
        this.address = address;
        this.length = length;
        this.byteOrder = byteOrder;
        access = byteOrder.equals(ByteOrder.nativeOrder()) ? new NativeEndianDirectDataAccess() : new ReverseEndianDirectDataAccess();
    }

    void checkBounds(long offset, long length) {
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
        return access.getByte(address+offset)&0xFF;
    }

    @Override
    public byte getSignedByte(long offset) {
        checkBounds(offset, 1);
        return access.getByte(address+offset);
    }

    @Override
    public short getShort(long offset) {
        checkBounds(offset, 2);
        return access.getShort(address+offset);
    }

    @Override
    public int getInt(long offset) {
        checkBounds(offset, 4);
        return access.getInt(address+offset);
    }

    @Override
    public long getLong(long offset) {
        checkBounds(offset, 8);
        return access.getLong(address+offset);
    }

    @Override
    public char getChar(long offset) {
        checkBounds(offset, 2);
        return access.getChar(address+offset);
    }

    @Override
    public float getFloat(long offset) {
        checkBounds(offset, 4);
        return access.getFloat(address+offset);
    }

    @Override
    public double getDouble(long offset) {
        checkBounds(offset, 8);
        return access.getDouble(address+offset);
    }

    @Override
    public void putByte(long offset, int v) {
        checkBounds(offset, 1);
        access.putByte(address+offset, (byte) v);
    }

    @Override
    public void putShort(long offset, short v) {
        checkBounds(offset, 2);
        access.putShort(address+offset, v);
    }

    @Override
    public void putInt(long offset, int v) {
        checkBounds(offset, 4);
        access.putInt(address+offset, v);
    }

    @Override
    public void putLong(long offset, long v) {
        checkBounds(offset, 8);
        access.putLong(address+offset, v);
    }

    @Override
    public void putChar(long offset, char v) {
        checkBounds(offset, 2);
        access.putChar(address+offset, v);
    }

    @Override
    public void putFloat(long offset, float v) {
        checkBounds(offset, 4);
        access.putFloat(address+offset, v);
    }

    @Override
    public void putDouble(long offset, double v) {
        checkBounds(offset, 8);
        access.putDouble(address+offset, v);
    }

    @Override
    public void getBytes(long srcPos, byte[] dest, int destPos, int length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        checkBounds(srcPos, length);
        if (destPos < 0) {
            throw new IllegalArgumentException();
        }
        if (destPos + length > dest.length) {
            throw new IllegalArgumentException();
        }
        UNSAFE.copyMemory(null, address+srcPos, dest, BYTE_ARRAY_BASE_OFFSET+destPos, length);
    }

    @Override
    public void getBytes(long offset, byte[] dest) {
        getBytes(offset, dest, 0, dest.length);
    }

    @Override
    public void putBytes(long destPos, byte[] src, int srcPos, int length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        checkBounds(destPos, length);
        if (srcPos < 0) {
            throw new IllegalArgumentException();
        }
        if (srcPos + length > src.length) {
            throw new IllegalArgumentException();
        }
        UNSAFE.copyMemory(src, BYTE_ARRAY_BASE_OFFSET+srcPos, null, address+destPos, length);
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
        UNSAFE.copyMemory(address+srcPos, dest.address+destPos, length);
    }

    @Override
    public void getBytes(long offset, DirectMemory dest) {
        getBytes(offset, dest, 0, dest.length());
    }

    @Override
    public void putBytes(long destPos, DirectMemory src, long srcPos, long length) {
        src.getBytes(srcPos, this, destPos, length);
    }

    @Override
    public void putBytes(long offset, DirectMemory src) {
        putBytes(offset, src, 0, src.length());
    }

    @Override
    public void getBytes(long srcPos, HeapMemory dest, long destPos, long length) {
        dest.putBytes(destPos, this, srcPos, length);
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
            final long destAddress = NativeUtils.getDirectBufferAddress(dest);
            if (destAddress != 0) {
                UNSAFE.copyMemory(address+offset, destAddress+dest.position(), dest.remaining());
                dest.position(dest.limit());
                return;
            }
        }
        if (dest.hasArray()) {
            UNSAFE.copyMemory(null, address+offset, dest.array(), BYTE_ARRAY_BASE_OFFSET+dest.arrayOffset()+dest.position(), dest.remaining());
            dest.position(dest.limit());
        } else {
            for (long l = offset; dest.hasRemaining(); l++) {
                dest.put(access.getByte(address+l));
            }
        }
    }

    @Override
    public void putBytes(long offset, ByteBuffer src) {
        checkBounds(offset, src.remaining());
        if (src.isDirect()) {
            final long srcAddress = NativeUtils.getDirectBufferAddress(src);
            if (srcAddress != 0) {
                UNSAFE.copyMemory(srcAddress+src.position(), address+offset, src.remaining());
                src.position(src.limit());
                return;
            }
        }
        if (src.hasArray()) {
            UNSAFE.copyMemory(src.array(), BYTE_ARRAY_BASE_OFFSET+src.arrayOffset()+src.position(), null, address+offset, src.remaining());
            src.position(src.limit());
        } else {
            for (long l = offset; src.hasRemaining(); l++) {
                access.putByte(address+l, src.get());
            }
        }
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public DirectMemory slice(long offset, long length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        checkBounds(offset, length);
        return new DirectMemory(address+offset, length, byteOrder);
    }

    @Override
    public ByteOrder order() {
        return byteOrder;
    }

    //this is for jni calls and HeapBuffer optimizations only
    public long address() {
        return address;
    }
}
