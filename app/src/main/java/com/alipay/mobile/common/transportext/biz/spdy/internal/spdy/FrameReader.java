package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

import java.io.Closeable;
import java.io.InputStream;
import java.util.List;

public interface FrameReader extends Closeable {

    public interface Handler {
        void data(boolean z, int i, InputStream inputStream, int i2);

        void goAway(int i, ErrorCode errorCode);

        void headers(boolean z, boolean z2, int i, int i2, int i3, List<String> list, HeadersMode headersMode);

        void noop();

        void ping(boolean z, int i, int i2);

        void preReadSynReplyHeaders(int i);

        void priority(int i, int i2);

        void rstStream(int i, ErrorCode errorCode);

        void settings(boolean z, Settings settings);

        void tcpData(InputStream inputStream, int i);

        void windowUpdate(int i, int i2, boolean z);
    }

    boolean nextFrame(Handler handler);

    void readConnectionHeader();
}
