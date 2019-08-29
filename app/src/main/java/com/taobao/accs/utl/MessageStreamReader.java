package com.taobao.accs.utl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MessageStreamReader extends ByteArrayInputStream {
    public MessageStreamReader(byte[] bArr) {
        super(bArr);
    }

    public int readByte() {
        return read() & 255;
    }

    public int readShort() {
        return (readByte() << 8) | readByte();
    }

    public int readInt() {
        return (readShort() << 16) | readShort();
    }

    public long readLong() {
        return (((long) readInt()) << 32) | ((long) readInt());
    }

    public String readString(int i) throws IOException {
        byte[] bArr = new byte[i];
        int read = read(bArr);
        if (read == i) {
            return new String(bArr, "utf-8");
        }
        StringBuilder sb = new StringBuilder("read len not match. ask for ");
        sb.append(i);
        sb.append(" but read for ");
        sb.append(read);
        throw new IOException(sb.toString());
    }

    public String readAllString() throws IOException {
        return new String(readAll(), "utf-8");
    }

    public byte[] readAll() throws IOException {
        byte[] bArr = new byte[available()];
        read(bArr);
        return bArr;
    }
}
