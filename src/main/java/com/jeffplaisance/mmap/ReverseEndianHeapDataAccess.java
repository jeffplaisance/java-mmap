package com.jeffplaisance.mmap;

public class ReverseEndianHeapDataAccess implements HeapDataAccess {

    private static final NativeEndianHeapDataAccess delegate = new NativeEndianHeapDataAccess();

    @Override
    public byte getByte(long[] data, long offset) {
        return delegate.getByte(data, offset);
    }

    @Override
    public short getShort(long[] data, long offset) {
        return Short.reverseBytes(delegate.getShort(data, offset));
    }

    @Override
    public int getInt(long[] data, long offset) {
        return Integer.reverseBytes(delegate.getInt(data, offset));
    }

    @Override
    public long getLong(long[] data, long offset) {
        return Long.reverseBytes(delegate.getLong(data, offset));
    }

    @Override
    public char getChar(long[] data, long offset) {
        return Character.reverseBytes(delegate.getChar(data, offset));
    }

    @Override
    public float getFloat(long[] data, long offset) {
        return Float.intBitsToFloat(Integer.reverseBytes(delegate.getInt(data, offset)));
    }

    @Override
    public double getDouble(long[] data, long offset) {
        return Double.longBitsToDouble(Long.reverseBytes(delegate.getLong(data, offset)));
    }

    @Override
    public void putByte(long[] data, long offset, byte v) {
        delegate.putByte(data, offset, v);
    }

    @Override
    public void putShort(long[] data, long offset, short v) {
        delegate.putShort(data, offset, Short.reverseBytes(v));
    }

    @Override
    public void putInt(long[] data, long offset, int v) {
        delegate.putInt(data, offset, Integer.reverseBytes(v));
    }

    @Override
    public void putLong(long[] data, long offset, long v) {
        delegate.putLong(data, offset, Long.reverseBytes(v));
    }

    @Override
    public void putChar(long[] data, long offset, char v) {
        delegate.putChar(data, offset, Character.reverseBytes(v));
    }

    @Override
    public void putFloat(long[] data, long offset, float v) {
        delegate.putInt(data, offset, Integer.reverseBytes(Float.floatToRawIntBits(v)));
    }

    @Override
    public void putDouble(long[] data, long offset, double v) {
        delegate.putLong(data, offset, Long.reverseBytes(Double.doubleToRawLongBits(v)));
    }
}
