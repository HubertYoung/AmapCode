package android.support.v4.net;

import android.net.TrafficStats;
import java.net.Socket;
import java.net.SocketException;

class TrafficStatsCompatIcs {
    TrafficStatsCompatIcs() {
    }

    public static void a() {
        TrafficStats.clearThreadStatsTag();
    }

    public static int b() {
        return TrafficStats.getThreadStatsTag();
    }

    public static void a(int i) {
        TrafficStats.incrementOperationCount(i);
    }

    public static void a(int i, int i2) {
        TrafficStats.incrementOperationCount(i, i2);
    }

    public static void b(int i) {
        TrafficStats.setThreadStatsTag(i);
    }

    public static void a(Socket socket) throws SocketException {
        TrafficStats.tagSocket(socket);
    }

    public static void b(Socket socket) throws SocketException {
        TrafficStats.untagSocket(socket);
    }
}
