package com.jeffplaisance.mmap;

import sun.misc.Unsafe;

public class NativeEndianDirectDataAccess implements DirectDataAccess {

    private static final Unsafe UNSAFE = NativeMMapUtils.getUnsafe();

    @Override
    public byte getByte(long offset) {
        return UNSAFE.getByte(offset);
    }

    @Override
    public short getShort(long offset) {
        return UNSAFE.getShort(offset);
    }

    @Override
    public int getInt(long offset) {
        return UNSAFE.getInt(offset);
    }

    @Override
    public long getLong(long offset) {
        return UNSAFE.getLong(offset);
    }

    @Override
    public char getChar(long offset) {
        return UNSAFE.getChar(offset);
    }

    @Override
    public float getFloat(long offset) {
        return UNSAFE.getFloat(offset);
    }

    @Override
    public double getDouble(long offset) {
        return UNSAFE.getDouble(offset);
    }

    @Override
    public void putByte(long offset, byte v) {
        UNSAFE.putByte(offset, v);
    }

    @Override
    public void putShort(long offset, short v) {
        UNSAFE.putShort(offset, v);
    }

    @Override
    public void putInt(long offset, int v) {
        UNSAFE.putInt(offset, v);
    }

    @Override
    public void putLong(long offset, long v) {
        UNSAFE.putLong(offset, v);
    }

    @Override
    public void putChar(long offset, char v) {
        UNSAFE.putChar(offset, v);
    }

    @Override
    public void putFloat(long offset, float v) {
        UNSAFE.putFloat(offset, v);
    }

    @Override
    public void putDouble(long offset, double v) {
        UNSAFE.putDouble(offset, v);
    }
}
