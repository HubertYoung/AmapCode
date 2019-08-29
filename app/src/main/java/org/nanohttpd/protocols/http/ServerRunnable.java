package org.nanohttpd.protocols.http;

import com.alipay.multimedia.common.logging.MLog;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerRunnable implements Runnable {
    private NanoHTTPD a;
    private final int b;
    private IOException c;
    private boolean d = false;

    public ServerRunnable(NanoHTTPD httpd, int timeout) {
        this.a = httpd;
        this.b = timeout;
    }

    public void run() {
        try {
            this.a.getMyServerSocket().bind(this.a.hostname != null ? new InetSocketAddress(this.a.hostname, this.a.myPort) : new InetSocketAddress(this.a.myPort));
            this.d = true;
            do {
                try {
                    Socket finalAccept = this.a.getMyServerSocket().accept();
                    if (this.b > 0) {
                        finalAccept.setSoTimeout(this.b);
                    }
                    this.a.asyncRunner.exec(this.a.createClientHandler(finalAccept, finalAccept.getInputStream()));
                } catch (IOException e) {
                    MLog.e("ServerRunnable", "Communication with the client broken.e=" + e);
                }
            } while (!this.a.getMyServerSocket().isClosed());
        } catch (IOException e2) {
            this.c = e2;
        }
    }

    public IOException getBindException() {
        return this.c;
    }

    public boolean hasBinded() {
        return this.d;
    }
}
