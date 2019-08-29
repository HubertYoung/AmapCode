package org.android.spdy;

public interface SessionCb {
    void bioPingRecvCallback(SpdySession spdySession, int i);

    byte[] getSSLMeta(SpdySession spdySession);

    int putSSLMeta(SpdySession spdySession, byte[] bArr);

    void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2);

    void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr);

    void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj);

    void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i);

    void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo);

    void spdySessionFailedError(SpdySession spdySession, int i, Object obj);
}
