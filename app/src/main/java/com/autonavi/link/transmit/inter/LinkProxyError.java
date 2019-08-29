package com.autonavi.link.transmit.inter;

public interface LinkProxyError {
    public static final int BT_SOCKET_CLOSED = -1;
    public static final int READ_ERROR = 1;
    public static final int WRITE_ERROR = 2;

    boolean onError(int i);
}
