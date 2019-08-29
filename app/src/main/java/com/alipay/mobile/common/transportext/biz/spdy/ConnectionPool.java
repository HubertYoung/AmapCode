package com.alipay.mobile.common.transportext.biz.spdy;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Platform;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.Closeable;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 1800000;
    private static final int MAX_CONNECTIONS_TO_CLEANUP = 2;
    private static ConnectionPool systemDefault;
    /* access modifiers changed from: private */
    public final LinkedList<Connection> connections = new LinkedList<>();
    private final Callable<Void> connectionsCleanupCallable = new ConnectionsCleanupCallable();
    private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.daemonThreadFactory("OkHttp ConnectionPool"));
    /* access modifiers changed from: private */
    public final long keepAliveDurationNs;
    /* access modifiers changed from: private */
    public final int maxIdleConnections;

    class ConnectionsCleanupCallable implements Callable<Void> {
        ConnectionsCleanupCallable() {
        }

        public Void call() {
            List<Connection> expiredConnections = new ArrayList<>(2);
            int idleConnectionCount = 0;
            synchronized (ConnectionPool.this) {
                ListIterator i = ConnectionPool.this.connections.listIterator(ConnectionPool.this.connections.size());
                while (i.hasPrevious()) {
                    Connection connection = (Connection) i.previous();
                    if (!connection.isAlive() || connection.isExpired(ConnectionPool.this.keepAliveDurationNs)) {
                        if (!connection.isAlive()) {
                            LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "===>Warning: Connection [dead]. hashcode=" + connection.hashCode());
                        } else {
                            LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "===>Warning: Connection [expired]. hashcode=" + connection.hashCode());
                        }
                        i.remove();
                        expiredConnections.add(connection);
                        if (expiredConnections.size() == 2) {
                            break;
                        }
                    } else if (connection.isIdle()) {
                        idleConnectionCount++;
                        LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "===>Warning: Connection [idle (Temporary safety)]. hashcode=" + connection.hashCode());
                    }
                }
                ListIterator i2 = ConnectionPool.this.connections.listIterator(ConnectionPool.this.connections.size());
                while (i2.hasPrevious() && idleConnectionCount > ConnectionPool.this.maxIdleConnections) {
                    Connection connection2 = (Connection) i2.previous();
                    if (connection2.isIdle()) {
                        LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "===>Warning: Connection [idle]. hashcode=" + connection2.hashCode());
                        expiredConnections.add(connection2);
                        i2.remove();
                        idleConnectionCount--;
                    }
                }
            }
            for (Connection expiredConnection : expiredConnections) {
                Util.closeQuietly((Closeable) expiredConnection);
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "===>Warning: Connection [Expiration is closed] . hashcode=" + expiredConnection.hashCode());
            }
            return null;
        }
    }

    private static ConnectionPool createConnectionPoolInstance() {
        String keepAlive = System.getProperty("alipay.spdy.keepAlive");
        String keepAliveDuration = System.getProperty("alipay.spdy.keepAliveDuration");
        String maxIdleConnections2 = System.getProperty("alipay.spdy.maxConnections");
        long keepAliveDurationMs = keepAliveDuration != null ? Long.parseLong(keepAliveDuration) : 1800000;
        if (keepAlive != null && !Boolean.parseBoolean(keepAlive)) {
            systemDefault = new ConnectionPool(0, keepAliveDurationMs);
        } else if (maxIdleConnections2 != null) {
            systemDefault = new ConnectionPool(Integer.parseInt(maxIdleConnections2), keepAliveDurationMs);
        } else {
            systemDefault = new ConnectionPool(5, keepAliveDurationMs);
        }
        return systemDefault;
    }

    public ConnectionPool(int maxIdleConnections2, long keepAliveDurationMs) {
        this.maxIdleConnections = maxIdleConnections2;
        this.keepAliveDurationNs = keepAliveDurationMs * 1000 * 1000;
    }

    /* access modifiers changed from: 0000 */
    public List<Connection> getConnections() {
        ArrayList arrayList;
        waitForCleanupCallableToRun();
        synchronized (this) {
            arrayList = new ArrayList(this.connections);
        }
        return arrayList;
    }

    private void waitForCleanupCallableToRun() {
        try {
            this.executorService.submit(new Runnable() {
                public void run() {
                }
            }).get();
        } catch (Exception e) {
            throw new AssertionError();
        }
    }

    public static ConnectionPool getDefault() {
        if (systemDefault != null) {
            return systemDefault;
        }
        synchronized (ConnectionPool.class) {
            if (systemDefault != null) {
                ConnectionPool connectionPool = systemDefault;
                return connectionPool;
            }
            systemDefault = createConnectionPoolInstance();
            return systemDefault;
        }
    }

    public synchronized int getConnectionCount() {
        return this.connections.size();
    }

    public synchronized int getSpdyConnectionCount() {
        int total;
        total = 0;
        Iterator it = this.connections.iterator();
        while (it.hasNext()) {
            if (((Connection) it.next()).isSpdy()) {
                total++;
            }
        }
        return total;
    }

    public synchronized int getHttpConnectionCount() {
        int total;
        total = 0;
        Iterator it = this.connections.iterator();
        while (it.hasNext()) {
            if (!((Connection) it.next()).isSpdy()) {
                total++;
            }
        }
        return total;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x009d, code lost:
        r2 = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.alipay.mobile.common.transportext.biz.spdy.Connection get(com.alipay.mobile.common.transportext.biz.spdy.Address r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            r2 = 0
            java.util.LinkedList<com.alipay.mobile.common.transportext.biz.spdy.Connection> r6 = r10.connections     // Catch:{ all -> 0x0041 }
            java.util.LinkedList<com.alipay.mobile.common.transportext.biz.spdy.Connection> r7 = r10.connections     // Catch:{ all -> 0x0041 }
            int r7 = r7.size()     // Catch:{ all -> 0x0041 }
            java.util.ListIterator r3 = r6.listIterator(r7)     // Catch:{ all -> 0x0041 }
        L_0x000e:
            boolean r6 = r3.hasPrevious()     // Catch:{ all -> 0x0041 }
            if (r6 == 0) goto L_0x009e
            java.lang.Object r0 = r3.previous()     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transportext.biz.spdy.Connection r0 = (com.alipay.mobile.common.transportext.biz.spdy.Connection) r0     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transportext.biz.spdy.Route r6 = r0.getRoute()     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transportext.biz.spdy.Address r6 = r6.getAddress()     // Catch:{ all -> 0x0041 }
            boolean r6 = r6.equals(r11)     // Catch:{ all -> 0x0041 }
            if (r6 != 0) goto L_0x0044
            java.lang.String r6 = "MWALLET_SPDY_LOG"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0041 }
            java.lang.String r8 = "Address not equals!   hashcode="
            r7.<init>(r8)     // Catch:{ all -> 0x0041 }
            int r8 = r0.hashCode()     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0041 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r6, r7)     // Catch:{ all -> 0x0041 }
            goto L_0x000e
        L_0x0041:
            r6 = move-exception
            monitor-exit(r10)
            throw r6
        L_0x0044:
            boolean r6 = r0.isAlive()     // Catch:{ all -> 0x0041 }
            if (r6 != 0) goto L_0x0052
            java.lang.String r6 = "MWALLET_SPDY_LOG"
            java.lang.String r7 = "Connection has died"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r6, r7)     // Catch:{ all -> 0x0041 }
            goto L_0x000e
        L_0x0052:
            long r6 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0041 }
            long r8 = r0.getIdleStartTimeNs()     // Catch:{ all -> 0x0041 }
            long r4 = r6 - r8
            long r6 = r10.keepAliveDurationNs     // Catch:{ all -> 0x0041 }
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 < 0) goto L_0x0089
            java.lang.String r6 = "MWALLET_SPDY_LOG"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0041 }
            java.lang.String r8 = "Connection keeplive timeout. [idleTimeNs("
            r7.<init>(r8)     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r7 = r7.append(r4)     // Catch:{ all -> 0x0041 }
            java.lang.String r8 = ")>=keepAliveDurationNs("
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0041 }
            long r8 = r10.keepAliveDurationNs     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0041 }
            java.lang.String r8 = ")]"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0041 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r6, r7)     // Catch:{ all -> 0x0041 }
            goto L_0x000e
        L_0x0089:
            r3.remove()     // Catch:{ all -> 0x0041 }
            boolean r6 = r0.isSpdy()     // Catch:{ all -> 0x0041 }
            if (r6 != 0) goto L_0x009d
            com.alipay.mobile.common.transportext.biz.spdy.internal.Platform r6 = com.alipay.mobile.common.transportext.biz.spdy.internal.Platform.get()     // Catch:{ SocketException -> 0x00ea }
            java.net.Socket r7 = r0.getSocket()     // Catch:{ SocketException -> 0x00ea }
            r6.tagSocket(r7)     // Catch:{ SocketException -> 0x00ea }
        L_0x009d:
            r2 = r0
        L_0x009e:
            if (r2 == 0) goto L_0x00e1
            boolean r6 = r2.isSpdy()     // Catch:{ all -> 0x0041 }
            if (r6 == 0) goto L_0x00e1
            java.util.LinkedList<com.alipay.mobile.common.transportext.biz.spdy.Connection> r6 = r10.connections     // Catch:{ all -> 0x0041 }
            r6.addFirst(r2)     // Catch:{ all -> 0x0041 }
            java.lang.String r6 = "LL_TRANSPORT"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0041 }
            java.lang.String r8 = "Temp Log ConnectionPool:"
            r7.<init>(r8)     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transportext.biz.spdy.Route r8 = r2.getRoute()     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transportext.biz.spdy.Address r8 = r8.getAddress()     // Catch:{ all -> 0x0041 }
            java.lang.String r8 = r8.getUriHost()     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0041 }
            java.lang.String r8 = ","
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transportext.biz.spdy.Route r8 = r2.getRoute()     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transportext.biz.spdy.Address r8 = r8.getAddress()     // Catch:{ all -> 0x0041 }
            int r8 = r8.getUriPort()     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0041 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r6, r7)     // Catch:{ all -> 0x0041 }
        L_0x00e1:
            java.util.concurrent.ExecutorService r6 = r10.executorService     // Catch:{ all -> 0x0041 }
            java.util.concurrent.Callable<java.lang.Void> r7 = r10.connectionsCleanupCallable     // Catch:{ all -> 0x0041 }
            r6.submit(r7)     // Catch:{ all -> 0x0041 }
            monitor-exit(r10)
            return r2
        L_0x00ea:
            r1 = move-exception
            com.alipay.mobile.common.transportext.biz.spdy.internal.Util.closeQuietly(r0)     // Catch:{ all -> 0x0041 }
            com.alipay.mobile.common.transportext.biz.spdy.internal.Platform r6 = com.alipay.mobile.common.transportext.biz.spdy.internal.Platform.get()     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0041 }
            java.lang.String r8 = "Unable to tagSocket(): "
            r7.<init>(r8)     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r7 = r7.append(r1)     // Catch:{ all -> 0x0041 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0041 }
            r6.logW(r7)     // Catch:{ all -> 0x0041 }
            goto L_0x000e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.ConnectionPool.get(com.alipay.mobile.common.transportext.biz.spdy.Address):com.alipay.mobile.common.transportext.biz.spdy.Connection");
    }

    public synchronized Connection getSpdyConnection(String host, int port) {
        Connection foundConnection;
        foundConnection = null;
        ListIterator i = this.connections.listIterator(this.connections.size());
        while (true) {
            if (!i.hasPrevious()) {
                break;
            }
            Connection connection = i.previous();
            if (connection.isSpdy() && isAddressEquals(connection.getRoute().getAddress(), host, port) && connection.isAlive() && System.nanoTime() - connection.getIdleStartTimeNs() < this.keepAliveDurationNs) {
                i.remove();
                foundConnection = connection;
                break;
            }
        }
        if (foundConnection != null) {
            this.connections.addFirst(foundConnection);
        }
        this.executorService.submit(this.connectionsCleanupCallable);
        return foundConnection;
    }

    public void recycle(Connection connection) {
        if (!connection.isSpdy()) {
            if (!connection.isAlive()) {
                Util.closeQuietly((Closeable) connection);
                return;
            }
            try {
                Platform.get().untagSocket(connection.getSocket());
                synchronized (this) {
                    this.connections.addFirst(connection);
                    connection.resetIdleStartTime();
                }
                this.executorService.submit(this.connectionsCleanupCallable);
            } catch (SocketException e) {
                Platform.get().logW("Unable to untagSocket(): " + e);
                Util.closeQuietly((Closeable) connection);
            }
        }
    }

    public void maybeShare(Connection connection) {
        this.executorService.submit(this.connectionsCleanupCallable);
        if (connection.isSpdy() && connection.isAlive()) {
            synchronized (this) {
                this.connections.addFirst(connection);
            }
        }
    }

    public void evictAll() {
        List<Connection> connections2;
        synchronized (this) {
            connections2 = new ArrayList<>(this.connections);
            this.connections.clear();
        }
        for (Connection closeQuietly : connections2) {
            Util.closeQuietly((Closeable) closeQuietly);
        }
    }

    private boolean isAddressEquals(Address address, String host, int port) {
        if (address != null && TextUtils.equals(address.getUriHost(), host) && address.getUriPort() == port) {
            return true;
        }
        return false;
    }
}
