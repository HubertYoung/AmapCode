package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

public interface IncomingStreamHandler {
    public static final IncomingStreamHandler REFUSE_INCOMING_STREAMS = new IncomingStreamHandler() {
        public final void receive(SpdyStream stream) {
            stream.close(ErrorCode.REFUSED_STREAM);
        }
    };

    void receive(SpdyStream spdyStream);
}
