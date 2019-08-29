package com.alipay.mobile.common.transport.http;

import android.support.v4.net.TrafficStatsCompat;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.PoolEntryRequest;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

public class ZThreadSafeClientConnManager extends ThreadSafeClientConnManager {
    public ZThreadSafeClientConnManager(HttpParams params, SchemeRegistry schreg) {
        super(params, schreg);
    }

    /* access modifiers changed from: protected */
    public ClientConnectionOperator createConnectionOperator(SchemeRegistry schreg) {
        return new ZClientConnectionOperator(schreg);
    }

    public ClientConnectionRequest requestConnection(final HttpRoute route, Object state) {
        final PoolEntryRequest poolRequest = this.connectionPool.requestPoolEntry(route, state);
        return new ClientConnectionRequest() {
            public void abortRequest() {
                poolRequest.abortRequest();
            }

            public ManagedClientConnection getConnection(long timeout, TimeUnit tunit) {
                if (route == null) {
                    throw new IllegalArgumentException("Route may not be null.");
                }
                LogCatUtil.info("ZThreadSafeClientConnManager", "ThreadSafeClientConnManager.getConnection: " + route + ", timeout = " + timeout);
                BasicPoolEntry entry = poolRequest.getPoolEntry(timeout, tunit);
                ZThreadSafeClientConnManager.a(entry);
                return new ZBasicPooledConnAdapter(ZThreadSafeClientConnManager.this, entry);
            }
        };
    }

    /* access modifiers changed from: private */
    public static void a(BasicPoolEntry entry) {
        try {
            Method getConnectionMethod = entry.getClass().getDeclaredMethod("getConnection", new Class[0]);
            getConnectionMethod.setAccessible(true);
            Socket socket = ((OperatedClientConnection) getConnectionMethod.invoke(entry, new Object[0])).getSocket();
            if (socket == null) {
                LogCatUtil.warn((String) "ZThreadSafeClientConnManager", (String) "[recordSocketInfo] socket is null.");
                return;
            }
            TrafficStatsCompat.tagSocket(socket);
            SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
            if (remoteSocketAddress == null) {
                LogCatUtil.warn((String) "ZThreadSafeClientConnManager", (String) "[recordSocketInfo] remoteSocketAddress is null.");
            } else if (!(remoteSocketAddress instanceof InetSocketAddress)) {
                LogCatUtil.warn((String) "ZThreadSafeClientConnManager", "[recordSocketInfo] remoteSocketAddress it's not InetSocketAddress, remoteSocketAddress = " + remoteSocketAddress.getClass().getName());
            } else {
                InetAddress inetAddress = ((InetSocketAddress) remoteSocketAddress).getAddress();
                if (inetAddress == null) {
                    LogCatUtil.warn((String) "ZThreadSafeClientConnManager", (String) "[recordSocketInfo] inetAddress is null.");
                    return;
                }
                String remoteHostAddress = inetAddress.getHostAddress();
                String targetHost = remoteHostAddress + ":" + socket.getPort();
                TransportContextThreadLocalUtils.setTargetHost(targetHost);
                LogCatUtil.info("ZThreadSafeClientConnManager", "requestConnection target host=[" + targetHost + "]");
            }
        } catch (Throwable iox) {
            LogCatUtil.error("ZThreadSafeClientConnManager", "Problem tagging socket.", iox);
        }
    }
}
