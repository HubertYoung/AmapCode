package com.alipay.android.phone.mobilesdk.socketcraft;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;

public class AbstractWrappedByteChannel implements WrappedByteChannel {
    private final ByteChannel channel;

    public AbstractWrappedByteChannel(ByteChannel towrap) {
        this.channel = towrap;
    }

    public AbstractWrappedByteChannel(WrappedByteChannel towrap) {
        this.channel = towrap;
    }

    public int read(ByteBuffer dst) {
        return this.channel.read(dst);
    }

    public boolean isOpen() {
        return this.channel.isOpen();
    }

    public void close() {
        this.channel.close();
    }

    public int write(ByteBuffer src) {
        return this.channel.write(src);
    }

    public boolean isNeedWrite() {
        if (this.channel instanceof WrappedByteChannel) {
            return ((WrappedByteChannel) this.channel).isNeedWrite();
        }
        return false;
    }

    public void writeMore() {
        if (this.channel instanceof WrappedByteChannel) {
            ((WrappedByteChannel) this.channel).writeMore();
        }
    }

    public boolean isNeedRead() {
        if (this.channel instanceof WrappedByteChannel) {
            return ((WrappedByteChannel) this.channel).isNeedRead();
        }
        return false;
    }

    public int readMore(ByteBuffer dst) {
        if (this.channel instanceof WrappedByteChannel) {
            return ((WrappedByteChannel) this.channel).readMore(dst);
        }
        return 0;
    }

    public boolean isBlocking() {
        if (this.channel instanceof SocketChannel) {
            return ((SocketChannel) this.channel).isBlocking();
        }
        if (this.channel instanceof WrappedByteChannel) {
            return ((WrappedByteChannel) this.channel).isBlocking();
        }
        return false;
    }
}
