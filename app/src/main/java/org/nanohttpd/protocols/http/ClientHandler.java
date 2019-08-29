package org.nanohttpd.protocols.http;

import com.alipay.multimedia.common.logging.MLog;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import org.nanohttpd.protocols.http.tempfiles.ITempFileManager;

public class ClientHandler implements Runnable {
    private final NanoHTTPD a;
    private final InputStream b;
    private final Socket c;

    public ClientHandler(NanoHTTPD httpd, InputStream inputStream, Socket acceptSocket) {
        this.a = httpd;
        this.b = inputStream;
        this.c = acceptSocket;
    }

    public void close() {
        NanoHTTPD.safeClose(this.b);
        NanoHTTPD.safeClose(this.c);
    }

    public void run() {
        OutputStream outputStream = null;
        try {
            outputStream = this.c.getOutputStream();
            HTTPSession session = new HTTPSession(this.a, (ITempFileManager) this.a.getTempFileManagerFactory().create(), this.b, outputStream, this.c.getInetAddress());
            while (!this.c.isClosed()) {
                session.execute();
            }
        } catch (Exception e) {
            if ((!(e instanceof SocketException) || !"NanoHttpd Shutdown".equals(e.getMessage())) && !(e instanceof SocketTimeoutException)) {
                MLog.i("ClientHandler", "Communication with the client broken, or an bug in the handler code.e=" + e);
            }
        } finally {
            NanoHTTPD.safeClose(outputStream);
            NanoHTTPD.safeClose(this.b);
            NanoHTTPD.safeClose(this.c);
            this.a.asyncRunner.closed(this);
        }
    }
}
