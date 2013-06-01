package com.jeffplaisance.mmap;

import sun.misc.Unsafe;

public class NativeEndianHeapDataAccess implements HeapDataAccess {

    private static final Unsafe UNSAFE = NativeUtils.getUnsafe();
    public static final int LONG_ARRAY_BASE_OFFSET = NativeUtils.LONG_ARRAY_BASE_OFFSET;

    @Override
    public byte getByte(long[] data, long offset) {
        return UNSAFE.getByte(data, LONG_ARRAY_BASE_OFFSET+offset);
    }

    @Override
    public short getShort(long[] data, long offset) {
        return UNSAFE.getShort(data, LONG_ARRAY_BASE_OFFSET+offset);
    }

    @Override
    public int getInt(long[] data, long offset) {
        return UNSAFE.getInt(data, LONG_ARRAY_BASE_OFFSET+offset);
    }

    @Override
    public long getLong(long[] data, long offset) {
        return UNSAFE.getLong(data, LONG_ARRAY_BASE_OFFSET+offset);
    }

    @Override
    public char getChar(long[] data, long offset) {
        return UNSAFE.getChar(data, LONG_ARRAY_BASE_OFFSET+offset);
    }

    @Override
    public float getFloat(long[] data, long offset) {
        return UNSAFE.getFloat(data, LONG_ARRAY_BASE_OFFSET+offset);
    }

    @Override
    public double getDouble(long[] data, long offset) {
        return UNSAFE.getDouble(data, LONG_ARRAY_BASE_OFFSET+offset);
    }

    @Override
    public void putByte(long[] data, long offset, byte v) {
        UNSAFE.putByte(data, LONG_ARRAY_BASE_OFFSET+offset, v);
    }

    @Override
    public void putShort(long[] data, long offset, short v) {
        UNSAFE.putShort(data, LONG_ARRAY_BASE_OFFSET+offset, v);
    }

    @Override
    public void putInt(long[] data, long offset, int v) {
        UNSAFE.putInt(data, LONG_ARRAY_BASE_OFFSET+offset, v);
    }

    @Override
    public void putLong(long[] data, long offset, long v) {
        UNSAFE.putLong(data, LONG_ARRAY_BASE_OFFSET+offset, v);
    }

    @Override
    public void putChar(long[] data, long offset, char v) {
        UNSAFE.putChar(data, LONG_ARRAY_BASE_OFFSET+offset, v);
    }

    @Override
    public void putFloat(long[] data, long offset, float v) {
        UNSAFE.putFloat(data, LONG_ARRAY_BASE_OFFSET+offset, v);
    }

    @Override
    public void putDouble(long[] data, long offset, double v) {
        UNSAFE.putDouble(data, LONG_ARRAY_BASE_OFFSET+offset, v);
    }
}
