package org.nanohttpd.protocols.http.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import org.nanohttpd.util.IFactoryThrowing;

public class DefaultServerSocketFactory implements IFactoryThrowing<ServerSocket, IOException> {
    public ServerSocket create() {
        return new ServerSocket();
    }
}
