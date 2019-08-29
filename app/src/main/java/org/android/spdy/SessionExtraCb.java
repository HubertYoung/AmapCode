package org.android.spdy;

public interface SessionExtraCb {
    void spdySessionOnWritable(SpdySession spdySession, Object obj, int i);
}
