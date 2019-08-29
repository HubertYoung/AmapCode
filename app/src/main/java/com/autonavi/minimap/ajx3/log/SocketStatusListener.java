package com.autonavi.minimap.ajx3.log;

public interface SocketStatusListener {
    void onMsgRecv(String str);

    void onStatusChange(int i);
}
