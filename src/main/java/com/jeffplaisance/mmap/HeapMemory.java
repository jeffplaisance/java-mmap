package com.jeffplaisance.mmap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class HeapMemory implements Memory {

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

    @Override
    public int getByte(long offset) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public byte getSignedByte(long offset) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public short getShort(long offset) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getInt(long offset) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getLong(long offset) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public char getChar(long offset) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float getFloat(long offset) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getDouble(long offset) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putByte(long offset, int v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putShort(long offset, short v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putInt(long offset, int v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putLong(long offset, long v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putChar(long offset, char v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putFloat(long offset, float v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putDouble(long offset, double v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getBytes(long offset, byte[] dest, int destPos, int length) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getBytes(long offset, byte[] dest) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putBytes(long offset, byte[] src, int srcPos, int length) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putBytes(long offset, byte[] src) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getBytes(long srcPos, Memory dest, long destPos, long length) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getBytes(long offset, Memory dest) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putBytes(long destPos, Memory src, long srcPos, long length) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putBytes(long offset, Memory src) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getBytes(long srcPos, DirectMemory dest, long destPos, long length) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getBytes(long offset, DirectMemory dest) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putBytes(long destPos, DirectMemory src, long srcPos, long length) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putBytes(long offset, DirectMemory src) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getBytes(long srcPos, HeapMemory dest, long destPos, long length) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getBytes(long offset, HeapMemory dest) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putBytes(long destPos, HeapMemory src, long srcPos, long length) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putBytes(long offset, HeapMemory src) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getBytes(long offset, ByteBuffer dest) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putBytes(long offset, ByteBuffer src) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long length() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Memory slice(long offset, long length) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ByteOrder order() {
        return byteOrder;
    }
}
