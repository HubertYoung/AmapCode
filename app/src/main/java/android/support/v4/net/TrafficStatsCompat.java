package android.support.v4.net;

import android.os.Build.VERSION;
import java.net.Socket;
import java.net.SocketException;

public class TrafficStatsCompat {
    private static final TrafficStatsCompatImpl IMPL;

    static class BaseTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        private ThreadLocal<SocketTags> a = new ThreadLocal<SocketTags>() {
            /* access modifiers changed from: protected */
            public /* synthetic */ Object initialValue() {
                return new SocketTags(0);
            }
        };

        static class SocketTags {
            public int a;

            private SocketTags() {
                this.a = -1;
            }

            /* synthetic */ SocketTags(byte b) {
                this();
            }
        }

        public final void a(int i) {
        }

        public final void a(int i, int i2) {
        }

        public final void a(Socket socket) {
        }

        public final void b(Socket socket) {
        }

        BaseTrafficStatsCompatImpl() {
        }

        public final void a() {
            this.a.get().a = -1;
        }

        public final int b() {
            return this.a.get().a;
        }

        public final void b(int i) {
            this.a.get().a = i;
        }
    }

    static class IcsTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        IcsTrafficStatsCompatImpl() {
        }

        public final void a() {
            TrafficStatsCompatIcs.a();
        }

        public final int b() {
            return TrafficStatsCompatIcs.b();
        }

        public final void a(int i) {
            TrafficStatsCompatIcs.a(i);
        }

        public final void a(int i, int i2) {
            TrafficStatsCompatIcs.a(i, i2);
        }

        public final void b(int i) {
            TrafficStatsCompatIcs.b(i);
        }

        public final void a(Socket socket) throws SocketException {
            TrafficStatsCompatIcs.a(socket);
        }

        public final void b(Socket socket) throws SocketException {
            TrafficStatsCompatIcs.b(socket);
        }
    }

    interface TrafficStatsCompatImpl {
        void a();

        void a(int i);

        void a(int i, int i2);

        void a(Socket socket) throws SocketException;

        int b();

        void b(int i);

        void b(Socket socket) throws SocketException;
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new IcsTrafficStatsCompatImpl();
        } else {
            IMPL = new BaseTrafficStatsCompatImpl();
        }
    }

    public static void clearThreadStatsTag() {
        IMPL.a();
    }

    public static int getThreadStatsTag() {
        return IMPL.b();
    }

    public static void incrementOperationCount(int i) {
        IMPL.a(i);
    }

    public static void incrementOperationCount(int i, int i2) {
        IMPL.a(i, i2);
    }

    public static void setThreadStatsTag(int i) {
        IMPL.b(i);
    }

    public static void tagSocket(Socket socket) throws SocketException {
        IMPL.a(socket);
    }

    public static void untagSocket(Socket socket) throws SocketException {
        IMPL.b(socket);
    }
}
