package com.alipay.android.phone.mobilesdk.socketcraft.client;

import com.alipay.android.phone.mobilesdk.socketcraft.AbstractWrappedByteChannel;
import com.autonavi.minimap.ajx3.util.EncodingUtils;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public abstract class AbstractClientProxyChannel extends AbstractWrappedByteChannel {
    protected final ByteBuffer proxyHandshake;

    public abstract String buildHandShake();

    public AbstractClientProxyChannel(ByteChannel towrap) {
        super(towrap);
        try {
            this.proxyHandshake = ByteBuffer.wrap(buildHandShake().getBytes(EncodingUtils.ASCII));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public int write(ByteBuffer src) {
        if (!this.proxyHandshake.hasRemaining()) {
            return super.write(src);
        }
        return super.write(this.proxyHandshake);
    }
}
