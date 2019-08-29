package org.nanohttpd.protocols.http.threading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.nanohttpd.protocols.http.ClientHandler;

public class DefaultAsyncRunner implements IAsyncRunner {
    protected long a;
    private final List<ClientHandler> b = Collections.synchronizedList(new ArrayList());

    public List<ClientHandler> getRunning() {
        return this.b;
    }

    public void closeAll() {
        Iterator it = new ArrayList(this.b).iterator();
        while (it.hasNext()) {
            ((ClientHandler) it.next()).close();
        }
    }

    public void closed(ClientHandler clientHandler) {
        this.b.remove(clientHandler);
    }

    public void exec(ClientHandler clientHandler) {
        this.a++;
        this.b.add(clientHandler);
        a(clientHandler).start();
    }

    private Thread a(ClientHandler clientHandler) {
        Thread t = new Thread(clientHandler);
        t.setDaemon(true);
        t.setName("NanoHttpd Request Processor (#" + this.a + ")");
        return t;
    }
}
