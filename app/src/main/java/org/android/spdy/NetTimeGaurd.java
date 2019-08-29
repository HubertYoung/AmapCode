package org.android.spdy;

public class NetTimeGaurd {
    public static final int CREATE = 0;
    public static final int ERROR = 2;
    public static final int PING = 1;
    public static final int STREAM = 3;
    private static final long calltime = 10;
    private static final long total = 50;
    private static long[] totaltime = new long[4];

    static void start(int i) {
        if (SpdyAgent.enableTimeGaurd) {
            totaltime[i] = 0;
        }
    }

    static long begin() {
        if (SpdyAgent.enableTimeGaurd) {
            return System.currentTimeMillis();
        }
        return 0;
    }

    static void end(String str, int i, long j) {
        if (SpdyAgent.enableTimeGaurd) {
            long currentTimeMillis = System.currentTimeMillis() - j;
            long[] jArr = totaltime;
            jArr[i] = jArr[i] + currentTimeMillis;
            StringBuilder sb = new StringBuilder("NetTimeGaurd[end]");
            sb.append(str);
            sb.append(" time=");
            sb.append(currentTimeMillis);
            sb.append(" total=");
            sb.append(totaltime[i]);
            if (currentTimeMillis > calltime) {
                StringBuilder sb2 = new StringBuilder("CallBack:");
                sb2.append(str);
                sb2.append(" timeconsuming:");
                sb2.append(currentTimeMillis);
                sb2.append("  mustlessthan:10");
                throw new SpdyErrorException(sb2.toString(), -1);
            }
        }
    }

    static void finish(int i) {
        if (SpdyAgent.enableTimeGaurd) {
            new StringBuilder("NetTimeGaurd[finish]:time=").append(totaltime[i]);
            if (totaltime[i] > total) {
                StringBuilder sb = new StringBuilder("CallBack totaltimeconsuming:");
                sb.append(totaltime[i]);
                sb.append("  mustlessthan:50");
                throw new SpdyErrorException(sb.toString(), -1);
            }
        }
    }
}
