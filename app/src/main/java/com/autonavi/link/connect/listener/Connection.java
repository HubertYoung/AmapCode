package com.autonavi.link.connect.listener;

import com.autonavi.link.connect.model.DiscoverInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public abstract class Connection {
    public static final int BT_ACCEPT_ERROR = -4;
    public static final int BT_CONNECT_FAILD = -2;
    public static final int BT_CONNECT_SUCCESS = 1;
    public static final int BT_CREATE_SERVER_ERROR = -3;
    public static final int BT_CREATE_SERVER_SUCCESS = 3;
    public static final int BT_SOCKET_CLOSE_ERROR = -6;
    public static final int BT_SOCKET_DISCONNECT = -1;
    public static final int BT_TURN_OFF = -5;
    public static final int BT_TURN_ON = 2;

    public interface OnBtStateChangeListener {
        void onStateChange(int i, DiscoverInfo discoverInfo);
    }

    public interface OnDiscoverHostListener {
        void onDisconnect();

        void onDiscoverHost(DiscoverInfo discoverInfo);

        void onFindDevice(List<DiscoverInfo> list);
    }

    public interface OnUdpBroadcastListener {
        void onBroadcastEnd(DiscoverInfo discoverInfo);

        void onDisconnect();

        void onFindDevice(List<DiscoverInfo> list);
    }

    public abstract InputStream getInputStream() throws IOException;

    public abstract OutputStream getOutputStream() throws IOException;
}
