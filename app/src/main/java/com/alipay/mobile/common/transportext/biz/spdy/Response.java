package com.alipay.mobile.common.transportext.biz.spdy;

import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.RawHeaders;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;

public final class Response {
    private final Body body;
    private final int code;
    private final RawHeaders headers;
    private final Response redirectedBy;
    private final Request request;

    public abstract class Body {
        public abstract InputStream byteStream();

        public String contentType() {
            return null;
        }

        public long contentLength() {
            return -1;
        }

        public byte[] bytes() {
            long contentLength = contentLength();
            if (contentLength > 2147483647L) {
                throw new IOException("Cannot buffer entire body for content length: " + contentLength);
            } else if (contentLength != -1) {
                byte[] content = new byte[((int) contentLength)];
                InputStream in = byteStream();
                Util.readFully(in, content);
                if (in.read() == -1) {
                    return content;
                }
                throw new IOException("Content-Length and stream length disagree");
            } else {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Util.copy(byteStream(), out);
                return out.toByteArray();
            }
        }

        public Reader charStream() {
            return new InputStreamReader(byteStream(), "UTF-8");
        }

        public String string() {
            return new String(bytes(), "UTF-8");
        }
    }

    public class Builder {
        /* access modifiers changed from: private */
        public Body body;
        /* access modifiers changed from: private */
        public final int code;
        /* access modifiers changed from: private */
        public final RawHeaders headers = new RawHeaders();
        /* access modifiers changed from: private */
        public Response redirectedBy;
        /* access modifiers changed from: private */
        public final Request request;

        public Builder(Request request2, int code2) {
            if (request2 == null) {
                throw new IllegalArgumentException("request == null");
            } else if (code2 <= 0) {
                throw new IllegalArgumentException("code <= 0");
            } else {
                this.request = request2;
                this.code = code2;
            }
        }

        public Builder header(String name, String value) {
            this.headers.set(name, value);
            return this;
        }

        public Builder addHeader(String name, String value) {
            this.headers.add(name, value);
            return this;
        }

        public Builder body(Body body2) {
            this.body = body2;
            return this;
        }

        public Builder redirectedBy(Response redirectedBy2) {
            this.redirectedBy = redirectedBy2;
            return this;
        }

        public Response build() {
            if (this.request == null) {
                throw new IllegalStateException("Response has no request.");
            } else if (this.code != -1) {
                return new Response(this);
            } else {
                throw new IllegalStateException("Response has no code.");
            }
        }
    }

    public interface Receiver {
        void onFailure(Failure failure);

        void onResponse(Response response);
    }

    private Response(Builder builder) {
        this.request = builder.request;
        this.code = builder.code;
        this.headers = new RawHeaders(builder.headers);
        this.body = builder.body;
        this.redirectedBy = builder.redirectedBy;
    }

    public final Request request() {
        return this.request;
    }

    public final int code() {
        return this.code;
    }

    public final String header(String name) {
        return header(name, null);
    }

    public final String header(String name, String defaultValue) {
        String result = this.headers.get(name);
        return result != null ? result : defaultValue;
    }

    public final List<String> headers(String name) {
        return this.headers.values(name);
    }

    public final Set<String> headerNames() {
        return this.headers.names();
    }

    public final int headerCount() {
        return this.headers.length();
    }

    public final String headerName(int index) {
        return this.headers.getFieldName(index);
    }

    public final String headerValue(int index) {
        return this.headers.getValue(index);
    }

    public final Body body() {
        return this.body;
    }

    public final Response redirectedBy() {
        return this.redirectedBy;
    }
}
