package org.java_websocket.drafts;

import org.java_websocket.drafts.a.b;
import org.java_websocket.handshake.a;

public class Draft_17 extends b {
    public final b a(a aVar) {
        return d(aVar) == 13 ? b.MATCHED : b.NOT_MATCHED;
    }

    public final a a() {
        return new Draft_17();
    }

    public final org.java_websocket.handshake.b a(org.java_websocket.handshake.b bVar) {
        super.a(bVar);
        bVar.a("Sec-WebSocket-Version", "13");
        return bVar;
    }
}
