package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

import java.io.Closeable;
import java.util.List;

public interface FrameWriter extends Closeable {
    void connectionHeader();

    void data(boolean z, int i, byte[] bArr);

    void data(boolean z, int i, byte[] bArr, int i2, int i3);

    void flush();

    void goAway(int i, ErrorCode errorCode);

    void headers(int i, List<String> list);

    void noop();

    void ping(boolean z, int i, int i2);

    void rstStream(int i, ErrorCode errorCode);

    void settings(Settings settings);

    void synReply(boolean z, int i, List<String> list);

    void synStream(boolean z, boolean z2, int i, int i2, int i3, int i4, List<String> list);

    void tcpData(byte[] bArr);

    void windowUpdate(int i, int i2);
}
