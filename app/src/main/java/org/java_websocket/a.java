package org.java_websocket;

import java.net.InetSocketAddress;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.e;
import org.java_websocket.handshake.h;

public abstract class a implements c {
    public String getFlashPolicy(WebSocket webSocket) {
        InetSocketAddress localSocketAddress = webSocket.getLocalSocketAddress();
        if (localSocketAddress == null) {
            throw new InvalidHandshakeException((String) "socket not bound");
        }
        StringBuffer stringBuffer = new StringBuffer(90);
        stringBuffer.append("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"");
        stringBuffer.append(localSocketAddress.getPort());
        stringBuffer.append("\" /></cross-domain-policy>\u0000");
        return stringBuffer.toString();
    }

    public void onWebsocketHandshakeReceivedAsClient(WebSocket webSocket, org.java_websocket.handshake.a aVar, ServerHandshake serverHandshake) {
    }

    public h onWebsocketHandshakeReceivedAsServer(WebSocket webSocket, org.java_websocket.drafts.a aVar, org.java_websocket.handshake.a aVar2) {
        return new e();
    }

    public void onWebsocketHandshakeSentAsClient(WebSocket webSocket, org.java_websocket.handshake.a aVar) {
    }

    public void onWebsocketMessageFragment(WebSocket webSocket, Framedata framedata) {
    }

    public void onWebsocketPing(WebSocket webSocket, Framedata framedata) {
        FramedataImpl1 framedataImpl1 = new FramedataImpl1(framedata);
        framedataImpl1.a(Opcode.PONG);
        webSocket.sendFrame(framedataImpl1);
    }

    public void onWebsocketPong(WebSocket webSocket, Framedata framedata) {
    }
}
