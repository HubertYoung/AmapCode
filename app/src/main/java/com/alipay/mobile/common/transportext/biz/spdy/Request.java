package com.alipay.mobile.common.transportext.biz.spdy;

import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.RawHeaders;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

public final class Request {
    private final Body body;
    private final RawHeaders headers;
    private final String method;
    private final Object tag;
    private final URL url;

    public abstract class Body {
        public abstract void writeTo(OutputStream outputStream);

        public MediaType contentType() {
            return null;
        }

        public long contentLength() {
            return -1;
        }

        public static Body create(MediaType contentType, String content) {
            if (contentType.charset() == null) {
                contentType = MediaType.parse(contentType + "; charset=utf-8");
            }
            try {
                return create(contentType, content.getBytes(contentType.charset().name()));
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError();
            }
        }

        public static Body create(final MediaType contentType, final byte[] content) {
            if (contentType == null) {
                throw new NullPointerException("contentType == null");
            } else if (content != null) {
                return new Body() {
                    public final MediaType contentType() {
                        return MediaType.this;
                    }

                    public final long contentLength() {
                        return (long) content.length;
                    }

                    public final void writeTo(OutputStream out) {
                        out.write(content);
                    }
                };
            } else {
                throw new NullPointerException("content == null");
            }
        }

        public static Body create(final MediaType contentType, final File file) {
            if (contentType == null) {
                throw new NullPointerException("contentType == null");
            } else if (file != null) {
                return new Body() {
                    public final MediaType contentType() {
                        return MediaType.this;
                    }

                    public final long contentLength() {
                        return file.length();
                    }

                    public final void writeTo(OutputStream out) {
                        long length = contentLength();
                        if (length != 0) {
                            InputStream in = null;
                            try {
                                InputStream in2 = new FileInputStream(file);
                                try {
                                    byte[] buffer = new byte[((int) Math.min(8192, length))];
                                    while (true) {
                                        int c = in2.read(buffer);
                                        if (c != -1) {
                                            out.write(buffer, 0, c);
                                        } else {
                                            Util.closeQuietly((Closeable) in2);
                                            return;
                                        }
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    in = in2;
                                    Util.closeQuietly((Closeable) in);
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                Util.closeQuietly((Closeable) in);
                                throw th;
                            }
                        }
                    }
                };
            } else {
                throw new NullPointerException("content == null");
            }
        }
    }

    public class Builder {
        /* access modifiers changed from: private */
        public Body body;
        /* access modifiers changed from: private */
        public final RawHeaders headers = new RawHeaders();
        /* access modifiers changed from: private */
        public String method = "GET";
        /* access modifiers changed from: private */
        public Object tag;
        /* access modifiers changed from: private */
        public URL url;

        public Builder(String url2) {
            url(url2);
        }

        public Builder(URL url2) {
            url(url2);
        }

        public Builder url(String url2) {
            try {
                this.url = new URL(url2);
                return this;
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Malformed URL: " + url2);
            }
        }

        public Builder url(URL url2) {
            if (url2 == null) {
                throw new IllegalStateException("url == null");
            }
            this.url = url2;
            return this;
        }

        public Builder header(String name, String value) {
            this.headers.set(name, value);
            return this;
        }

        public Builder addHeader(String name, String value) {
            this.headers.add(name, value);
            return this;
        }

        public Builder removeHeader(String fieldName) {
            this.headers.removeAll(fieldName);
            return this;
        }

        public Builder get() {
            return method("GET", null);
        }

        public Builder head() {
            return method(RequestMethodConstants.HEAD_METHOD, null);
        }

        public Builder post(Body body2) {
            return method("POST", body2);
        }

        public Builder put(Body body2) {
            return method(RequestMethodConstants.PUT_METHOD, body2);
        }

        public Builder method(String method2, Body body2) {
            if (method2 == null || method2.length() == 0) {
                throw new IllegalArgumentException("method == null || method.length() == 0");
            }
            this.method = method2;
            this.body = body2;
            return this;
        }

        public Builder tag(Object tag2) {
            this.tag = tag2;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }

    private Request(Builder builder) {
        Object obj;
        this.url = builder.url;
        this.method = builder.method;
        this.headers = new RawHeaders(builder.headers);
        this.body = builder.body;
        if (builder.tag != null) {
            obj = builder.tag;
        } else {
            obj = this;
        }
        this.tag = obj;
    }

    public final URL url() {
        return this.url;
    }

    public final String urlString() {
        return this.url.toString();
    }

    public final String method() {
        return this.method;
    }

    public final String header(String name) {
        return this.headers.get(name);
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

    public final Object tag() {
        return this.tag;
    }
}
