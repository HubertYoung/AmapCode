package org.android.spdy;

import java.util.List;
import java.util.Map;

public interface Intenalcb {
    void bioPingRecvCallback(SpdySession spdySession, int i);

    byte[] getSSLMeta(SpdySession spdySession);

    int putSSLMeta(SpdySession spdySession, byte[] bArr);

    void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2);

    void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr);

    void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, long j, SpdyByteArray spdyByteArray, int i);

    void spdyDataRecvCallback(SpdySession spdySession, boolean z, long j, int i, int i2);

    void spdyDataSendCallback(SpdySession spdySession, boolean z, long j, int i, int i2);

    void spdyOnStreamResponse(SpdySession spdySession, long j, Map<String, List<String>> map, int i);

    void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj);

    void spdyRequestRecvCallback(SpdySession spdySession, long j, int i);

    void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i);

    void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo);

    void spdySessionFailedError(SpdySession spdySession, int i, Object obj);

    void spdySessionOnWritable(SpdySession spdySession, Object obj, int i);

    void spdyStreamCloseCallback(SpdySession spdySession, long j, int i, int i2, SuperviseData superviseData);
}
