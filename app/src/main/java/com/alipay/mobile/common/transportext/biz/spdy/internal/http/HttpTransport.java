package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.internal.AbstractOutputStream;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.mwallet.ErrorMsgHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.ProtocolException;
import java.net.Socket;

public final class HttpTransport implements Transport {
    public static final int DEFAULT_CHUNK_LENGTH = 1024;
    private static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;
    /* access modifiers changed from: private */
    public final HttpEngine httpEngine;
    private OutputStream requestOut;
    /* access modifiers changed from: private */
    public final InputStream socketIn;
    private final OutputStream socketOut;

    class ChunkedInputStream extends AbstractHttpInputStream {
        private static final int NO_CHUNK_YET = -1;
        private int bytesRemainingInChunk = -1;
        private boolean hasMoreChunks = true;
        private final HttpTransport transport;

        ChunkedInputStream(InputStream is, CacheRequest cacheRequest, HttpTransport transport2) {
            super(is, transport2.httpEngine, cacheRequest);
            this.transport = transport2;
        }

        public int read(byte[] buffer, int offset, int count) {
            Util.checkOffsetAndCount(buffer.length, offset, count);
            checkNotClosed();
            if (!this.hasMoreChunks) {
                return -1;
            }
            if (this.bytesRemainingInChunk == 0 || this.bytesRemainingInChunk == -1) {
                readChunkSize();
                if (!this.hasMoreChunks) {
                    return -1;
                }
            }
            int read = this.in.read(buffer, offset, Math.min(count, this.bytesRemainingInChunk));
            if (read == -1) {
                unexpectedEndOfInput();
                throw new IOException("unexpected end of stream");
            }
            this.bytesRemainingInChunk -= read;
            cacheWrite(buffer, offset, read);
            return read;
        }

        private void readChunkSize() {
            if (this.bytesRemainingInChunk != -1) {
                Util.readAsciiLine(this.in);
            }
            String chunkSizeString = Util.readAsciiLine(this.in);
            int index = chunkSizeString.indexOf(";");
            if (index != -1) {
                chunkSizeString = chunkSizeString.substring(0, index);
            }
            try {
                this.bytesRemainingInChunk = Integer.parseInt(chunkSizeString.trim(), 16);
                if (this.bytesRemainingInChunk == 0) {
                    this.hasMoreChunks = false;
                    RawHeaders rawResponseHeaders = this.httpEngine.responseHeaders.getHeaders();
                    RawHeaders.readHeaders(this.transport.socketIn, rawResponseHeaders);
                    this.httpEngine.receiveHeaders(rawResponseHeaders);
                    endOfInput();
                }
            } catch (NumberFormatException e) {
                throw new ProtocolException(String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.CHUNK_SIZE_IS_NOT_NUM), new Object[]{chunkSizeString}));
            }
        }

        public int available() {
            checkNotClosed();
            if (!this.hasMoreChunks || this.bytesRemainingInChunk == -1) {
                return 0;
            }
            return Math.min(this.in.available(), this.bytesRemainingInChunk);
        }

        public void close() {
            if (!this.closed) {
                if (this.hasMoreChunks && !HttpTransport.discardStream(this.httpEngine, this)) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    final class ChunkedOutputStream extends AbstractOutputStream {
        private static final byte[] CRLF = {13, 10};
        private static final byte[] FINAL_CHUNK = {48, 13, 10, 13, 10};
        private static final byte[] HEX_DIGITS = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
        private final ByteArrayOutputStream bufferedChunk;
        private final byte[] hex;
        private final int maxChunkLength;
        private final OutputStream socketOut;

        private ChunkedOutputStream(OutputStream socketOut2, int maxChunkLength2) {
            this.hex = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 13, 10};
            this.socketOut = socketOut2;
            this.maxChunkLength = Math.max(1, dataLength(maxChunkLength2));
            this.bufferedChunk = new ByteArrayOutputStream(maxChunkLength2);
        }

        private int dataLength(int dataPlusHeaderLength) {
            int headerLength = 4;
            for (int i = dataPlusHeaderLength - 4; i > 0; i >>= 4) {
                headerLength++;
            }
            return dataPlusHeaderLength - headerLength;
        }

        public final synchronized void write(byte[] buffer, int offset, int count) {
            int numBytesWritten;
            checkNotClosed();
            Util.checkOffsetAndCount(buffer.length, offset, count);
            while (count > 0) {
                if (this.bufferedChunk.size() > 0 || count < this.maxChunkLength) {
                    numBytesWritten = Math.min(count, this.maxChunkLength - this.bufferedChunk.size());
                    this.bufferedChunk.write(buffer, offset, numBytesWritten);
                    if (this.bufferedChunk.size() == this.maxChunkLength) {
                        writeBufferedChunkToSocket();
                    }
                } else {
                    numBytesWritten = this.maxChunkLength;
                    writeHex(numBytesWritten);
                    this.socketOut.write(buffer, offset, numBytesWritten);
                    this.socketOut.write(CRLF);
                }
                offset += numBytesWritten;
                count -= numBytesWritten;
            }
        }

        private void writeHex(int i) {
            int cursor = 8;
            do {
                cursor--;
                this.hex[cursor] = HEX_DIGITS[i & 15];
                i >>>= 4;
            } while (i != 0);
            this.socketOut.write(this.hex, cursor, this.hex.length - cursor);
        }

        public final synchronized void flush() {
            if (!this.closed) {
                writeBufferedChunkToSocket();
                this.socketOut.flush();
            }
        }

        public final synchronized void close() {
            if (!this.closed) {
                this.closed = true;
                writeBufferedChunkToSocket();
                this.socketOut.write(FINAL_CHUNK);
            }
        }

        private void writeBufferedChunkToSocket() {
            int size = this.bufferedChunk.size();
            if (size > 0) {
                writeHex(size);
                this.bufferedChunk.writeTo(this.socketOut);
                this.bufferedChunk.reset();
                this.socketOut.write(CRLF);
            }
        }
    }

    class FixedLengthInputStream extends AbstractHttpInputStream {
        private int bytesRemaining;

        public FixedLengthInputStream(InputStream is, CacheRequest cacheRequest, HttpEngine httpEngine, int length) {
            super(is, httpEngine, cacheRequest);
            this.bytesRemaining = length;
            if (this.bytesRemaining == 0) {
                endOfInput();
            }
        }

        public int read(byte[] buffer, int offset, int count) {
            Util.checkOffsetAndCount(buffer.length, offset, count);
            checkNotClosed();
            if (this.bytesRemaining == 0) {
                return -1;
            }
            int read = this.in.read(buffer, offset, Math.min(count, this.bytesRemaining));
            if (read == -1) {
                unexpectedEndOfInput();
                throw new ProtocolException(ErrorMsgHelper.getMessage(ErrorMsgHelper.UNEXPECTED_END_STREAM));
            }
            this.bytesRemaining -= read;
            cacheWrite(buffer, offset, read);
            if (this.bytesRemaining != 0) {
                return read;
            }
            endOfInput();
            return read;
        }

        public int available() {
            checkNotClosed();
            if (this.bytesRemaining == 0) {
                return 0;
            }
            return Math.min(this.in.available(), this.bytesRemaining);
        }

        public void close() {
            if (!this.closed) {
                if (this.bytesRemaining != 0 && !HttpTransport.discardStream(this.httpEngine, this)) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    final class FixedLengthOutputStream extends AbstractOutputStream {
        private long bytesRemaining;
        private final OutputStream socketOut;

        private FixedLengthOutputStream(OutputStream socketOut2, long bytesRemaining2) {
            this.socketOut = socketOut2;
            this.bytesRemaining = bytesRemaining2;
        }

        public final void write(byte[] buffer, int offset, int count) {
            checkNotClosed();
            Util.checkOffsetAndCount(buffer.length, offset, count);
            if (((long) count) > this.bytesRemaining) {
                throw new ProtocolException(String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.BYTES_REMAIN_NO_EQ_COUNT), new Object[]{Long.valueOf(this.bytesRemaining), Integer.valueOf(count)}));
            }
            this.socketOut.write(buffer, offset, count);
            this.bytesRemaining -= (long) count;
        }

        public final void flush() {
            if (!this.closed) {
                this.socketOut.flush();
            }
        }

        public final void close() {
            if (!this.closed) {
                this.closed = true;
                if (this.bytesRemaining > 0) {
                    throw new ProtocolException(ErrorMsgHelper.getMessage(ErrorMsgHelper.UNEXPECTED_END_STREAM));
                }
            }
        }
    }

    public HttpTransport(HttpEngine httpEngine2, OutputStream outputStream, InputStream inputStream) {
        this.httpEngine = httpEngine2;
        this.socketOut = outputStream;
        this.requestOut = outputStream;
        this.socketIn = inputStream;
    }

    public final OutputStream createRequestBody() {
        boolean chunked = this.httpEngine.requestHeaders.isChunked();
        if (!chunked && this.httpEngine.policy.getChunkLength() > 0 && this.httpEngine.connection.getHttpMinorVersion() != 0) {
            this.httpEngine.requestHeaders.setChunked();
            chunked = true;
        }
        if (chunked) {
            int chunkLength = this.httpEngine.policy.getChunkLength();
            if (chunkLength == -1) {
                chunkLength = 1024;
            }
            writeRequestHeaders();
            return new ChunkedOutputStream(this.requestOut, chunkLength);
        }
        long fixedContentLength = this.httpEngine.policy.getFixedContentLength();
        if (fixedContentLength != -1) {
            this.httpEngine.requestHeaders.setContentLength(fixedContentLength);
            writeRequestHeaders();
            return new FixedLengthOutputStream(this.requestOut, fixedContentLength);
        }
        long contentLength = this.httpEngine.requestHeaders.getContentLength();
        if (contentLength > 2147483647L) {
            throw new IllegalArgumentException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
        } else if (contentLength == -1) {
            return new RetryableOutputStream();
        } else {
            writeRequestHeaders();
            return new RetryableOutputStream((int) contentLength);
        }
    }

    public final void flushRequest() {
        this.requestOut.flush();
        this.requestOut = this.socketOut;
    }

    public final void writeRequestBody(RetryableOutputStream requestBody) {
        requestBody.writeToSocket(this.requestOut);
    }

    public final void writeRequestHeaders() {
        this.httpEngine.writingRequestHeaders();
        this.requestOut.write(this.httpEngine.requestHeaders.getHeaders().toBytes());
    }

    public final ResponseHeaders readResponseHeaders() {
        RawHeaders rawHeaders = RawHeaders.fromBytes(this.socketIn);
        this.httpEngine.connection.setHttpMinorVersion(rawHeaders.getHttpMinorVersion());
        this.httpEngine.receiveHeaders(rawHeaders);
        ResponseHeaders headers = new ResponseHeaders(this.httpEngine.uri, rawHeaders);
        headers.setTransport("http/1.1");
        return headers;
    }

    public final boolean makeReusable(boolean streamCanceled, OutputStream requestBodyOut, InputStream responseBodyIn) {
        if (streamCanceled) {
            return false;
        }
        if ((requestBodyOut != null && !((AbstractOutputStream) requestBodyOut).isClosed()) || this.httpEngine.requestHeaders.hasConnectionClose()) {
            return false;
        }
        if ((this.httpEngine.responseHeaders != null && this.httpEngine.responseHeaders.hasConnectionClose()) || (responseBodyIn instanceof UnknownLengthHttpInputStream)) {
            return false;
        }
        if (responseBodyIn != null) {
            return discardStream(this.httpEngine, responseBodyIn);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean discardStream(HttpEngine httpEngine2, InputStream responseBodyIn) {
        int socketTimeout;
        Connection connection = httpEngine2.connection;
        if (connection == null) {
            return false;
        }
        Socket socket = connection.getSocket();
        if (socket == null) {
            return false;
        }
        try {
            socketTimeout = socket.getSoTimeout();
            socket.setSoTimeout(100);
            Util.skipAll(responseBodyIn);
            socket.setSoTimeout(socketTimeout);
            return true;
        } catch (IOException e) {
            return false;
        } catch (Throwable th) {
            socket.setSoTimeout(socketTimeout);
            throw th;
        }
    }

    public final InputStream getTransferStream(CacheRequest cacheRequest) {
        if (!this.httpEngine.hasResponseBody()) {
            return new FixedLengthInputStream(this.socketIn, cacheRequest, this.httpEngine, 0);
        }
        if (this.httpEngine.responseHeaders.isChunked()) {
            return new ChunkedInputStream(this.socketIn, cacheRequest, this);
        }
        if (this.httpEngine.responseHeaders.getContentLength() != -1) {
            return new FixedLengthInputStream(this.socketIn, cacheRequest, this.httpEngine, this.httpEngine.responseHeaders.getContentLength());
        }
        return new UnknownLengthHttpInputStream(this.socketIn, cacheRequest, this.httpEngine);
    }
}
