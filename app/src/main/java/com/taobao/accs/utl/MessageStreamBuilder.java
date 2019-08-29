package com.taobao.accs.utl;

import java.io.ByteArrayOutputStream;

public class MessageStreamBuilder extends ByteArrayOutputStream {
    public MessageStreamBuilder(int i) {
        super(i);
    }

    public MessageStreamBuilder() {
    }

    public MessageStreamBuilder writeByte(byte b) {
        write(b);
        return this;
    }

    public MessageStreamBuilder writeShort(short s) {
        write(s >> 8);
        write(s);
        return this;
    }

    public MessageStreamBuilder writeInt(int i) {
        write(i >> 24);
        write(i >> 16);
        write(i >> 8);
        write(i);
        return this;
    }

    public MessageStreamBuilder writeLong(long j) {
        writeInt((int) (j >> 32));
        writeInt((int) j);
        return this;
    }
}
