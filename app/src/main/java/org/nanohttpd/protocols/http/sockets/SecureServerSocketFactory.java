package org.nanohttpd.protocols.http.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import org.nanohttpd.util.IFactoryThrowing;

public class SecureServerSocketFactory implements IFactoryThrowing<ServerSocket, IOException> {
    private SSLServerSocketFactory a;
    private String[] b;

    public SecureServerSocketFactory(SSLServerSocketFactory sslServerSocketFactory, String[] sslProtocols) {
        this.a = sslServerSocketFactory;
        this.b = sslProtocols;
    }

    public ServerSocket create() {
        SSLServerSocket ss = (SSLServerSocket) this.a.createServerSocket();
        if (this.b != null) {
            ss.setEnabledProtocols(this.b);
        } else {
            ss.setEnabledProtocols(ss.getSupportedProtocols());
        }
        ss.setUseClientMode(false);
        ss.setWantClientAuth(false);
        ss.setNeedClientAuth(false);
        return ss;
    }
}
