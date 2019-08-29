package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

import android.support.v4.view.ViewCompat;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Platform;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.FrameReader.Handler;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.LongLinkTransportManager;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl;
import com.alipay.mobile.common.transportext.biz.spdy.mwallet.ErrorMsgHelper;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.util.List;
import java.util.zip.Deflater;

final class Spdy3 implements Variant {
    static final byte[] DICTIONARY;
    static final int FLAG_FIN = 1;
    static final int FLAG_UNIDIRECTIONAL = 2;
    static final int TYPE_CREDENTIAL = 16;
    static final int TYPE_DATA = 0;
    static final int TYPE_GOAWAY = 7;
    static final int TYPE_HEADERS = 8;
    static final int TYPE_NOOP = 5;
    static final int TYPE_PING = 6;
    static final int TYPE_RST_STREAM = 3;
    static final int TYPE_SETTINGS = 4;
    static final int TYPE_SYN_REPLY = 2;
    static final int TYPE_SYN_STREAM = 1;
    static final int TYPE_TCP_DATA = 100;
    static final int TYPE_WINDOW_UPDATE = 9;
    static final int VERSION = 3;

    final class Reader implements FrameReader {
        private final boolean client;
        private final DataInputStream in;
        private final NameValueBlockReader nameValueBlockReader;

        Reader(InputStream in2, boolean client2) {
            this.in = new DataInputStream(in2);
            this.nameValueBlockReader = new NameValueBlockReader(in2);
            this.client = client2;
        }

        public final void readConnectionHeader() {
        }

        public final boolean nextFrame(Handler handler) {
            try {
                int w1 = this.in.readInt();
                int w2 = this.in.readInt();
                int flags = (-16777216 & w2) >>> 24;
                int length = w2 & ViewCompat.MEASURED_SIZE_MASK;
                if ((Integer.MIN_VALUE & w1) != 0) {
                    int version = (2147418112 & w1) >>> 16;
                    int type = w1 & 65535;
                    if (type != 6) {
                        Spdy3.notifyNetworkActive(0);
                    }
                    if (version != 3) {
                        throw new ProtocolException(String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.SPDY_VERSION_ERROR), new Object[]{Integer.valueOf(version)}));
                    }
                    switch (type) {
                        case 1:
                            readSynStream(handler, flags, length);
                            return true;
                        case 2:
                            readSynReply(handler, flags, length);
                            return true;
                        case 3:
                            readRstStream(handler, flags, length);
                            return true;
                        case 4:
                            readSettings(handler, flags, length);
                            return true;
                        case 5:
                            if (length != 0) {
                                throw ioException("TYPE_NOOP length: %d != 0", Integer.valueOf(length));
                            }
                            handler.noop();
                            return true;
                        case 6:
                            readPing(handler, flags, length);
                            return true;
                        case 7:
                            readGoAway(handler, flags, length);
                            return true;
                        case 8:
                            readHeaders(handler, flags, length);
                            return true;
                        case 9:
                            readWindowUpdate(handler, flags, length);
                            return true;
                        case 16:
                            Util.skipByReading(this.in, (long) length);
                            throw new UnsupportedOperationException("TODO");
                        case 100:
                            handler.tcpData(this.in, length);
                            return true;
                        default:
                            throw new IOException("Unexpected frame");
                    }
                } else {
                    int streamId = w1 & Integer.MAX_VALUE;
                    boolean inFinished = (flags & 1) != 0;
                    LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "nextFrame readData:" + length + ", streamId=" + streamId + ", inFinished=" + inFinished);
                    handler.data(inFinished, streamId, this.in, length);
                    Spdy3.notifyNetworkActive(0);
                    return true;
                }
            } catch (IOException e) {
                LogCatUtil.error(InnerLogUtil.MWALLET_SPDY_TAG, "Spdy3#Reader#nextFrame Exception.", e);
                return false;
            }
        }

        private void readSynStream(Handler handler, int flags, int length) {
            InnerLogUtil.log4AtlsTest("readSynStream:" + length);
            int streamId = this.in.readInt() & Integer.MAX_VALUE;
            int associatedStreamId = this.in.readInt() & Integer.MAX_VALUE;
            int priority = (57344 & this.in.readShort()) >>> 13;
            List nameValueBlock = this.nameValueBlockReader.readNameValueBlock(length - 10);
            handler.headers((flags & 2) != 0, (flags & 1) != 0, streamId, associatedStreamId, priority, nameValueBlock, HeadersMode.SPDY_SYN_STREAM);
        }

        private void readSynReply(Handler handler, int flags, int length) {
            boolean inFinished;
            InnerLogUtil.log4AtlsTest("readSynReply:" + length);
            int streamId = this.in.readInt() & Integer.MAX_VALUE;
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "readSynReply.  streamId=" + streamId);
            handler.preReadSynReplyHeaders(streamId);
            List nameValueBlock = this.nameValueBlockReader.readNameValueBlock(length - 4);
            if ((flags & 1) != 0) {
                inFinished = true;
            } else {
                inFinished = false;
            }
            handler.headers(false, inFinished, streamId, -1, -1, nameValueBlock, HeadersMode.SPDY_REPLY);
        }

        private void readRstStream(Handler handler, int flags, int length) {
            InnerLogUtil.log4AtlsTest("readRstStream:" + length);
            if (length != 8) {
                throw ioException("TYPE_RST_STREAM length: %d != 8", Integer.valueOf(length));
            }
            int streamId = this.in.readInt() & Integer.MAX_VALUE;
            int errorCodeInt = this.in.readInt();
            ErrorCode errorCode = ErrorCode.fromSpdy3Rst(errorCodeInt);
            if (errorCode == null) {
                throw ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(errorCodeInt));
            } else {
                handler.rstStream(streamId, errorCode);
            }
        }

        private void readHeaders(Handler handler, int flags, int length) {
            InnerLogUtil.log4AtlsTest("readHeaders:" + length + ", flags:" + flags);
            handler.headers(false, false, this.in.readInt() & Integer.MAX_VALUE, -1, -1, this.nameValueBlockReader.readNameValueBlock(length - 4), HeadersMode.SPDY_HEADERS);
        }

        private void readWindowUpdate(Handler handler, int flags, int length) {
            if (length != 8) {
                throw ioException("TYPE_WINDOW_UPDATE length: %d != 8", Integer.valueOf(length));
            }
            handler.windowUpdate(this.in.readInt() & Integer.MAX_VALUE, this.in.readInt() & Integer.MAX_VALUE, false);
        }

        private void readPing(Handler handler, int flags, int length) {
            boolean z;
            boolean reply = true;
            if (length != 4) {
                throw ioException("TYPE_PING length: %d != 4", Integer.valueOf(length));
            }
            int id = this.in.readInt();
            boolean z2 = this.client;
            if (id % 2 == 1) {
                z = true;
            } else {
                z = false;
            }
            if (z2 != z) {
                reply = false;
            }
            handler.ping(reply, id, 0);
        }

        private void readGoAway(Handler handler, int flags, int length) {
            InnerLogUtil.log4AtlsTest("readGoAway:" + length);
            if (length != 8) {
                throw ioException("TYPE_GOAWAY length: %d != 8", Integer.valueOf(length));
            }
            int lastGoodStreamId = this.in.readInt() & Integer.MAX_VALUE;
            int errorCodeInt = this.in.readInt();
            ErrorCode errorCode = ErrorCode.fromSpdyGoAway(errorCodeInt);
            if (errorCode == null) {
                throw ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(errorCodeInt));
            } else {
                handler.goAway(lastGoodStreamId, errorCode);
            }
        }

        private void readSettings(Handler handler, int flags, int length) {
            boolean clearPrevious = true;
            InnerLogUtil.log4AtlsTest("readSettings:" + length);
            int numberOfEntries = this.in.readInt();
            if (length != (numberOfEntries * 8) + 4) {
                throw ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", Integer.valueOf(length), Integer.valueOf(numberOfEntries));
            }
            Settings settings = new Settings();
            for (int i = 0; i < numberOfEntries; i++) {
                int w1 = this.in.readInt();
                int id = w1 & ViewCompat.MEASURED_SIZE_MASK;
                settings.set(id, (-16777216 & w1) >>> 24, this.in.readInt());
            }
            if ((flags & 1) == 0) {
                clearPrevious = false;
            }
            handler.settings(clearPrevious, settings);
        }

        private static IOException ioException(String message, Object... args) {
            throw new IOException(String.format(message, args));
        }

        public final void close() {
            Util.closeAll(this.in, this.nameValueBlockReader);
        }
    }

    final class Writer implements FrameWriter {
        private final boolean client;
        private final ByteArrayOutputStream nameValueBlockBuffer = new ByteArrayOutputStream();
        private final DataOutputStream nameValueBlockOut;
        private final DataOutputStream out;

        Writer(OutputStream out2, boolean client2) {
            this.out = new DataOutputStream(out2);
            this.client = client2;
            Deflater deflater = new Deflater();
            deflater.setDictionary(Spdy3.DICTIONARY);
            this.nameValueBlockOut = new DataOutputStream(Platform.get().newDeflaterOutputStream(this.nameValueBlockBuffer, deflater, true));
        }

        public final synchronized void connectionHeader() {
        }

        public final synchronized void flush() {
            this.out.flush();
        }

        public final synchronized void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, int priority, int slot, List<String> nameValueBlock) {
            int i;
            int i2 = 0;
            synchronized (this) {
                writeNameValueBlockToBuffer(nameValueBlock);
                int length = this.nameValueBlockBuffer.size() + 10;
                if (outFinished) {
                    i = 1;
                } else {
                    i = 0;
                }
                if (inFinished) {
                    i2 = 2;
                }
                this.out.writeInt(-2147287039);
                this.out.writeInt((((i | i2) & 255) << 24) | (16777215 & length));
                this.out.writeInt(streamId & Integer.MAX_VALUE);
                this.out.writeInt(associatedStreamId & Integer.MAX_VALUE);
                this.out.writeShort(((priority & 7) << 13) | 0 | (slot & 255));
                this.nameValueBlockBuffer.writeTo(this.out);
                InnerLogUtil.log4AtlsTest("spdy-synStream:" + length + ", streamId=" + streamId);
            }
        }

        public final synchronized void synReply(boolean outFinished, int streamId, List<String> nameValueBlock) {
            InnerLogUtil.log4AtlsTest("synReply");
            writeNameValueBlockToBuffer(nameValueBlock);
            int flags = outFinished ? 1 : 0;
            this.out.writeInt(-2147287038);
            this.out.writeInt(((flags & 255) << 24) | (16777215 & (this.nameValueBlockBuffer.size() + 4)));
            this.out.writeInt(Integer.MAX_VALUE & streamId);
            this.nameValueBlockBuffer.writeTo(this.out);
            this.out.flush();
        }

        public final synchronized void headers(int streamId, List<String> nameValueBlock) {
            InnerLogUtil.log4AtlsTest("headers");
            writeNameValueBlockToBuffer(nameValueBlock);
            this.out.writeInt(-2147287032);
            this.out.writeInt((16777215 & (this.nameValueBlockBuffer.size() + 4)) | 0);
            this.out.writeInt(Integer.MAX_VALUE & streamId);
            this.nameValueBlockBuffer.writeTo(this.out);
            this.out.flush();
        }

        public final synchronized void rstStream(int streamId, ErrorCode errorCode) {
            InnerLogUtil.log4AtlsTest("spdy-rstStream:8");
            if (errorCode.spdyRstCode == -1) {
                throw new IllegalArgumentException();
            }
            this.out.writeInt(-2147287037);
            this.out.writeInt(8);
            this.out.writeInt(Integer.MAX_VALUE & streamId);
            this.out.writeInt(errorCode.spdyRstCode);
            this.out.flush();
        }

        public final synchronized void data(boolean outFinished, int streamId, byte[] data) {
            data(outFinished, streamId, data, 0, data.length);
        }

        public final synchronized void data(boolean outFinished, int streamId, byte[] data, int offset, int byteCount) {
            InnerLogUtil.log4AtlsTest("spdy-data:" + byteCount);
            int flags = outFinished ? 1 : 0;
            this.out.writeInt(Integer.MAX_VALUE & streamId);
            this.out.writeInt(((flags & 255) << 24) | (16777215 & byteCount));
            this.out.write(data, offset, byteCount);
        }

        private void writeNameValueBlockToBuffer(List<String> nameValueBlock) {
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "writeNameValueBlockToBuffer: " + nameValueBlock.toString());
            this.nameValueBlockBuffer.reset();
            this.nameValueBlockOut.writeInt(nameValueBlock.size() / 2);
            for (String bytes : nameValueBlock) {
                byte[] headerBytes = bytes.getBytes("UTF-8");
                this.nameValueBlockOut.writeInt(headerBytes.length);
                this.nameValueBlockOut.write(headerBytes);
            }
            this.nameValueBlockOut.flush();
        }

        public final synchronized void settings(Settings settings) {
            int size = settings.size();
            int length = (size * 8) + 4;
            InnerLogUtil.log4AtlsTest("spdy-settings：" + length);
            this.out.writeInt(-2147287036);
            this.out.writeInt((length & ViewCompat.MEASURED_SIZE_MASK) | 0);
            this.out.writeInt(size);
            for (int i = 0; i <= 10; i++) {
                if (settings.isSet(i)) {
                    this.out.writeInt(((settings.flags(i) & 255) << 24) | (i & ViewCompat.MEASURED_SIZE_MASK));
                    this.out.writeInt(settings.get(i));
                }
            }
            this.out.flush();
        }

        public final synchronized void noop() {
            this.out.writeInt(-2147287035);
            this.out.writeInt(0);
            this.out.flush();
        }

        public final synchronized void ping(boolean reply, int payload1, int payload2) {
            boolean payloadIsReply = true;
            synchronized (this) {
                if (this.client == (payload1 % 2 == 1)) {
                    payloadIsReply = false;
                }
                if (reply != payloadIsReply) {
                    throw new IllegalArgumentException("payload != reply");
                }
                this.out.writeInt(-2147287034);
                this.out.writeInt(4);
                this.out.writeInt(payload1);
                this.out.flush();
            }
        }

        public final synchronized void goAway(int lastGoodStreamId, ErrorCode errorCode) {
            InnerLogUtil.log4AtlsTest("spdy-goAway:8,  errorCode:" + errorCode);
            if (errorCode.spdyGoAwayCode == -1) {
                throw new IllegalArgumentException();
            }
            this.out.writeInt(-2147287033);
            this.out.writeInt(8);
            this.out.writeInt(lastGoodStreamId);
            this.out.writeInt(errorCode.spdyGoAwayCode);
            this.out.flush();
        }

        public final synchronized void windowUpdate(int streamId, int deltaWindowSize) {
            this.out.writeInt(-2147287031);
            this.out.writeInt(8);
            this.out.writeInt(streamId);
            this.out.writeInt(deltaWindowSize);
            this.out.flush();
        }

        public final synchronized void tcpData(byte[] data) {
            this.out.writeInt(-2147286940);
            this.out.writeInt((data.length & ViewCompat.MEASURED_SIZE_MASK) | 0);
            this.out.write(data, 0, data.length);
            LogCatUtil.info(LongLinkTransportManager.TAG, "LongLink Send Tcp Data:" + data.length);
            this.out.flush();
        }

        public final synchronized void close() {
            Util.closeAll(this.out, this.nameValueBlockOut);
        }
    }

    Spdy3() {
    }

    static {
        try {
            DICTIONARY = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(Util.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    public final FrameReader newReader(InputStream in, boolean client) {
        return new Reader(in, client);
    }

    public final FrameWriter newWriter(OutputStream out, boolean client) {
        return new Writer(out, client);
    }

    public static void notifyNetworkActive(int ioType) {
        SpdyLongLinkConnManagerImpl.getInstance().notifyNetworkActive(ioType);
    }
}
