package org.nanohttpd.protocols.http.threading;

import org.nanohttpd.protocols.http.ClientHandler;

public interface IAsyncRunner {
    void closeAll();

    void closed(ClientHandler clientHandler);

    void exec(ClientHandler clientHandler);
}
