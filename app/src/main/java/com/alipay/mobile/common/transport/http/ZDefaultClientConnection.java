package com.alipay.mobile.common.transport.http;

import java.net.Socket;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.impl.io.SocketOutputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.HttpParams;

public class ZDefaultClientConnection extends DefaultClientConnection {
    /* access modifiers changed from: protected */
    public SessionOutputBuffer createSessionOutputBuffer(Socket socket, int buffersize, HttpParams params) {
        try {
            return new ZHttpClientSocketOutputBuffer(socket, buffersize, params);
        } catch (Throwable th) {
            return new SocketOutputBuffer(socket, buffersize, params);
        }
    }
}
