package com.jeffplaisance.mmap;

public interface DirectDataAccess {
    //primitive getters
    byte getByte(long offset);
    short getShort(long offset);
    int getInt(long offset);
    long getLong(long offset);
    char getChar(long offset);
    float getFloat(long offset);
    double getDouble(long offset);

    //primitive setters
    void putByte(long offset, byte v);
    void putShort(long offset, short v);
    void putInt(long offset, int v);
    void putLong(long offset, long v);
    void putChar(long offset, char v);
    void putFloat(long offset, float v);
    void putDouble(long offset, double v);
}
