package com.jeffplaisance.mmap;

public interface HeapDataAccess {
    //primitive getters
    byte getByte(long[] data, long offset);
    short getShort(long[] data, long offset);
    int getInt(long[] data, long offset);
    long getLong(long[] data, long offset);
    char getChar(long[] data, long offset);
    float getFloat(long[] data, long offset);
    double getDouble(long[] data, long offset);

    //primitive setters
    void putByte(long[] data, long offset, byte v);
    void putShort(long[] data, long offset, short v);
    void putInt(long[] data, long offset, int v);
    void putLong(long[] data, long offset, long v);
    void putChar(long[] data, long offset, char v);
    void putFloat(long[] data, long offset, float v);
    void putDouble(long[] data, long offset, double v);
}
