package org.java_websocket;

import java.net.InetSocketAddress;
import org.java_websocket.framing.Framedata;

public interface WebSocket {

    public enum a {
        CLIENT,
        SERVER
    }

    InetSocketAddress getLocalSocketAddress();

    void sendFrame(Framedata framedata);
}
