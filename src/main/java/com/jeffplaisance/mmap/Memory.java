package com.jeffplaisance.mmap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public interface Memory {

    //primitive getters
    int getByte(long offset);
    byte getSignedByte(long offset);
    short getShort(long offset);
    int getInt(long offset);
    long getLong(long offset);
    char getChar(long offset);
    float getFloat(long offset);
    double getDouble(long offset);

    //primitive setters
    void putByte(long offset, int v);
    void putShort(long offset, short v);
    void putInt(long offset, int v);
    void putLong(long offset, long v);
    void putChar(long offset, char v);
    void putFloat(long offset, float v);
    void putDouble(long offset, double v);

    //byte[] bulk get/set
    void getBytes(long srcPos, byte[] dest, int destPos, int length);
    void getBytes(long offset, byte[] dest);
    void putBytes(long destPos, byte[] src, int srcPos, int length);
    void putBytes(long offset, byte[] src);

    //memory bulk get/set
    void getBytes(long srcPos, Memory dest, long destPos, long length);
    void getBytes(long offset, Memory dest);
    void putBytes(long destPos, Memory src, long srcPos, long length);
    void putBytes(long offset, Memory src);

    //direct memory specialized get/set
    void getBytes(long srcPos, DirectMemory dest, long destPos, long length);
    void getBytes(long offset, DirectMemory dest);
    void putBytes(long destPos, DirectMemory src, long srcPos, long length);
    void putBytes(long offset, DirectMemory src);

    //heap memory specialized get/set
    void getBytes(long srcPos, HeapMemory dest, long destPos, long length);
    void getBytes(long offset, HeapMemory dest);
    void putBytes(long destPos, HeapMemory src, long srcPos, long length);
    void putBytes(long offset, HeapMemory src);

    //ByteBuffer bulk get/set
    void getBytes(long offset, ByteBuffer dest);
    void putBytes(long offset, ByteBuffer src);

    long length();

    Memory slice(long offset, long length);

    ByteOrder order();
}
