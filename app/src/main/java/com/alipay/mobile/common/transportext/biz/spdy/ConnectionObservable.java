package com.alipay.mobile.common.transportext.biz.spdy;

import java.util.Observable;

public class ConnectionObservable extends Observable {
    private static ConnectionObservable connectionObservable;

    public class ConnectionEvent {
        public static final int CONNECTED = 0;
        public static final int CONNECTING = 2;
        public static final int DISCONNECTED = 1;
        public Connection connection;
        public int event = -1;

        public ConnectionEvent(int event2, Connection connection2) {
            this.event = event2;
            this.connection = connection2;
        }
    }

    public static final ConnectionObservable getInstance() {
        if (connectionObservable != null) {
            return connectionObservable;
        }
        ConnectionObservable connectionObservable2 = new ConnectionObservable();
        connectionObservable = connectionObservable2;
        return connectionObservable2;
    }

    private ConnectionObservable() {
    }

    public void notifyObservers(Object data) {
        setChanged();
        super.notifyObservers(data);
    }
}
