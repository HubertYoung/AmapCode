package org.android.spdy;

import java.util.List;
import java.util.Map;

public interface Spdycb {
    void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, long j, SpdyByteArray spdyByteArray, Object obj);

    void spdyDataRecvCallback(SpdySession spdySession, boolean z, long j, int i, Object obj);

    void spdyDataSendCallback(SpdySession spdySession, boolean z, long j, int i, Object obj);

    void spdyOnStreamResponse(SpdySession spdySession, long j, Map<String, List<String>> map, Object obj);

    void spdyRequestRecvCallback(SpdySession spdySession, long j, Object obj);

    void spdyStreamCloseCallback(SpdySession spdySession, long j, int i, Object obj, SuperviseData superviseData);
}
