package com.alipay.android.phone.mobilesdk.socketcraft;

import com.alipay.android.phone.mobilesdk.socketcraft.drafts.Draft;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.InvalidHandshakeException;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata.Opcode;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.FramedataImpl1;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ClientHandshake;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.HandshakeImpl1Server;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ServerHandshake;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ServerHandshakeBuilder;
import java.net.InetSocketAddress;

public abstract class WebSocketAdapter implements WebSocketListener {
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) {
        return new HandshakeImpl1Server();
    }

    public void onWebsocketHandshakeReceivedAsClient(WebSocket conn, ClientHandshake request, ServerHandshake response) {
    }

    public void onWebsocketHandshakeSentAsClient(WebSocket conn, ClientHandshake request) {
    }

    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
    }

    public void onWebsocketPing(WebSocket conn, Framedata f) {
        FramedataImpl1 resp = new FramedataImpl1(f);
        resp.setOptcode(Opcode.PONG);
        conn.sendFrame(resp);
    }

    public void onWebsocketPong(WebSocket conn, Framedata f) {
    }

    public String getFlashPolicy(WebSocket conn) {
        InetSocketAddress adr = conn.getLocalSocketAddress();
        if (adr == null) {
            throw new InvalidHandshakeException((String) "socket not bound");
        }
        StringBuffer sb = new StringBuffer(90);
        sb.append("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"");
        sb.append(adr.getPort());
        sb.append("\" /></cross-domain-policy>\u0000");
        return sb.toString();
    }
}
