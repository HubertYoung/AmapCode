package org.java_websocket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.a;
import org.java_websocket.handshake.f;
import org.java_websocket.handshake.h;

public interface c {
    String getFlashPolicy(WebSocket webSocket);

    InetSocketAddress getLocalSocketAddress(WebSocket webSocket);

    InetSocketAddress getRemoteSocketAddress(WebSocket webSocket);

    void onWebsocketClose(WebSocket webSocket, int i, String str, boolean z);

    void onWebsocketCloseInitiated(WebSocket webSocket, int i, String str);

    void onWebsocketClosing(WebSocket webSocket, int i, String str, boolean z);

    void onWebsocketError(WebSocket webSocket, Exception exc);

    void onWebsocketHandshakeReceivedAsClient(WebSocket webSocket, a aVar, ServerHandshake serverHandshake);

    h onWebsocketHandshakeReceivedAsServer(WebSocket webSocket, org.java_websocket.drafts.a aVar, a aVar2);

    void onWebsocketHandshakeSentAsClient(WebSocket webSocket, a aVar);

    void onWebsocketMessage(WebSocket webSocket, String str);

    void onWebsocketMessage(WebSocket webSocket, ByteBuffer byteBuffer);

    void onWebsocketMessageFragment(WebSocket webSocket, Framedata framedata);

    void onWebsocketOpen(WebSocket webSocket, f fVar);

    void onWebsocketPing(WebSocket webSocket, Framedata framedata);

    void onWebsocketPong(WebSocket webSocket, Framedata framedata);

    void onWriteDemand(WebSocket webSocket);
}
