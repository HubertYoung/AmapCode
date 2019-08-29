package com.autonavi.link.transmit.inter;

public interface Connection {
    boolean isConnected();

    int read(byte[] bArr, int i, int i2);

    int write(byte[] bArr, int i, int i2);
}
