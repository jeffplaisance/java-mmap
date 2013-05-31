package com.jeffplaisance.mmap;

public class ReverseEndianDirectDataAccess implements DirectDataAccess {

    private static final NativeEndianDirectDataAccess delegate = new NativeEndianDirectDataAccess();

    @Override
    public byte getByte(long offset) {
        return delegate.getByte(offset);
    }

    @Override
    public short getShort(long offset) {
        return Short.reverseBytes(delegate.getShort(offset));
    }

    @Override
    public int getInt(long offset) {
        return Integer.reverseBytes(delegate.getInt(offset));
    }

    @Override
    public long getLong(long offset) {
        return Long.reverseBytes(delegate.getLong(offset));
    }

    @Override
    public char getChar(long offset) {
        return Character.reverseBytes(delegate.getChar(offset));
    }

    @Override
    public float getFloat(long offset) {
        return Float.intBitsToFloat(Integer.reverseBytes(delegate.getInt(offset)));
    }

    @Override
    public double getDouble(long offset) {
        return Double.longBitsToDouble(Long.reverseBytes(delegate.getLong(offset)));
    }

    @Override
    public void putByte(long offset, byte v) {
        delegate.putByte(offset, v);
    }

    @Override
    public void putShort(long offset, short v) {
        delegate.putShort(offset, Short.reverseBytes(v));
    }

    @Override
    public void putInt(long offset, int v) {
        delegate.putInt(offset, Integer.reverseBytes(v));
    }

    @Override
    public void putLong(long offset, long v) {
        delegate.putLong(offset, Long.reverseBytes(v));
    }

    @Override
    public void putChar(long offset, char v) {
        delegate.putChar(offset, Character.reverseBytes(v));
    }

    @Override
    public void putFloat(long offset, float v) {
        delegate.putInt(offset, Integer.reverseBytes(Float.floatToRawIntBits(v)));
    }

    @Override
    public void putDouble(long offset, double v) {
        delegate.putLong(offset, Long.reverseBytes(Double.doubleToRawLongBits(v)));
    }
}
