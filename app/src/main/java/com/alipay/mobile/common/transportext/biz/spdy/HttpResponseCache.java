package com.alipay.mobile.common.transportext.biz.spdy;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Base64;
import com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache;
import com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache.Editor;
import com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache.Snapshot;
import com.alipay.mobile.common.transportext.biz.spdy.internal.StrictLineReader;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpURLConnectionImpl;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpsEngine;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpsURLConnectionImpl;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.RawHeaders;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.ResponseHeaders;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.SecureCacheResponse;
import java.net.URI;
import java.net.URLConnection;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;

public final class HttpResponseCache extends ResponseCache {
    private static final int ENTRY_BODY = 1;
    private static final int ENTRY_COUNT = 2;
    private static final int ENTRY_METADATA = 0;
    private static final int VERSION = 201105;
    private final DiskLruCache cache;
    private int hitCount;
    private int networkCount;
    final OkResponseCache okResponseCache = new OkResponseCache() {
        public CacheResponse get(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) {
            return HttpResponseCache.this.get(uri, requestMethod, requestHeaders);
        }

        public CacheRequest put(URI uri, URLConnection connection) {
            return HttpResponseCache.this.put(uri, connection);
        }

        public void maybeRemove(String requestMethod, URI uri) {
            HttpResponseCache.this.maybeRemove(requestMethod, uri);
        }

        public void update(CacheResponse conditionalCacheHit, HttpURLConnection connection) {
            HttpResponseCache.this.update(conditionalCacheHit, connection);
        }

        public void trackConditionalCacheHit() {
            HttpResponseCache.this.trackConditionalCacheHit();
        }

        public void trackResponse(ResponseSource source) {
            HttpResponseCache.this.trackResponse(source);
        }
    };
    private int requestCount;
    /* access modifiers changed from: private */
    public int writeAbortCount;
    /* access modifiers changed from: private */
    public int writeSuccessCount;

    final class CacheRequestImpl extends CacheRequest {
        private OutputStream body;
        private OutputStream cacheOut;
        /* access modifiers changed from: private */
        public boolean done;
        private final Editor editor;

        public CacheRequestImpl(final Editor editor2) {
            this.editor = editor2;
            this.cacheOut = editor2.newOutputStream(1);
            this.body = new FilterOutputStream(this.cacheOut, HttpResponseCache.this) {
                public void close() {
                    synchronized (HttpResponseCache.this) {
                        if (!CacheRequestImpl.this.done) {
                            CacheRequestImpl.this.done = true;
                            HttpResponseCache.this.writeSuccessCount = HttpResponseCache.this.writeSuccessCount + 1;
                            super.close();
                            editor2.commit();
                        }
                    }
                }

                public void write(byte[] buffer, int offset, int length) {
                    this.out.write(buffer, offset, length);
                }
            };
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void abort() {
            /*
                r4 = this;
                com.alipay.mobile.common.transportext.biz.spdy.HttpResponseCache r2 = com.alipay.mobile.common.transportext.biz.spdy.HttpResponseCache.this
                monitor-enter(r2)
                boolean r1 = r4.done     // Catch:{ all -> 0x0037 }
                if (r1 == 0) goto L_0x0009
                monitor-exit(r2)     // Catch:{ all -> 0x0037 }
            L_0x0008:
                return
            L_0x0009:
                r1 = 1
                r4.done = r1     // Catch:{ all -> 0x0037 }
                com.alipay.mobile.common.transportext.biz.spdy.HttpResponseCache r1 = com.alipay.mobile.common.transportext.biz.spdy.HttpResponseCache.this     // Catch:{ all -> 0x0037 }
                r1.writeAbortCount = r1.writeAbortCount + 1     // Catch:{ all -> 0x0037 }
                monitor-exit(r2)     // Catch:{ all -> 0x0037 }
                java.io.OutputStream r1 = r4.cacheOut
                com.alipay.mobile.common.transportext.biz.spdy.internal.Util.closeQuietly(r1)
                com.alipay.mobile.common.transportext.biz.spdy.internal.DiskLruCache$Editor r1 = r4.editor     // Catch:{ IOException -> 0x001d }
                r1.abort()     // Catch:{ IOException -> 0x001d }
                goto L_0x0008
            L_0x001d:
                r0 = move-exception
                java.lang.String r1 = "MWALLET_SPDY_LOG"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "editor.abort() exception: "
                r2.<init>(r3)
                java.lang.String r3 = r0.toString()
                java.lang.StringBuilder r2 = r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
                goto L_0x0008
            L_0x0037:
                r1 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0037 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.HttpResponseCache.CacheRequestImpl.abort():void");
        }

        public final OutputStream getBody() {
            return this.body;
        }
    }

    final class Entry {
        /* access modifiers changed from: private */
        public final String cipherSuite;
        /* access modifiers changed from: private */
        public final Certificate[] localCertificates;
        /* access modifiers changed from: private */
        public final Certificate[] peerCertificates;
        private final String requestMethod;
        /* access modifiers changed from: private */
        public final RawHeaders responseHeaders;
        private final String uri;
        private final RawHeaders varyHeaders;

        public Entry(InputStream in) {
            try {
                StrictLineReader reader = new StrictLineReader(in, Util.US_ASCII);
                this.uri = reader.readLine();
                this.requestMethod = reader.readLine();
                this.varyHeaders = new RawHeaders();
                int varyRequestHeaderLineCount = reader.readInt();
                for (int i = 0; i < varyRequestHeaderLineCount; i++) {
                    this.varyHeaders.addLine(reader.readLine());
                }
                this.responseHeaders = new RawHeaders();
                this.responseHeaders.setStatusLine(reader.readLine());
                int responseHeaderLineCount = reader.readInt();
                for (int i2 = 0; i2 < responseHeaderLineCount; i2++) {
                    this.responseHeaders.addLine(reader.readLine());
                }
                if (isHttps()) {
                    String blank = reader.readLine();
                    if (blank.length() > 0) {
                        throw new IOException("expected \"\" but was \"" + blank + "\"");
                    }
                    this.cipherSuite = reader.readLine();
                    this.peerCertificates = readCertArray(reader);
                    this.localCertificates = readCertArray(reader);
                } else {
                    this.cipherSuite = null;
                    this.peerCertificates = null;
                    this.localCertificates = null;
                }
            } finally {
                in.close();
            }
        }

        public Entry(URI uri2, RawHeaders varyHeaders2, HttpURLConnection httpConnection) {
            this.uri = uri2.toString();
            this.varyHeaders = varyHeaders2;
            this.requestMethod = httpConnection.getRequestMethod();
            this.responseHeaders = RawHeaders.fromMultimap(httpConnection.getHeaderFields(), true);
            SSLSocket sslSocket = getSslSocket(httpConnection);
            if (sslSocket != null) {
                this.cipherSuite = sslSocket.getSession().getCipherSuite();
                Certificate[] peerCertificatesNonFinal = null;
                try {
                    peerCertificatesNonFinal = sslSocket.getSession().getPeerCertificates();
                } catch (SSLPeerUnverifiedException ignored) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "SSLPeerUnverifiedException exception: " + ignored.toString());
                }
                this.peerCertificates = peerCertificatesNonFinal;
                this.localCertificates = sslSocket.getSession().getLocalCertificates();
                return;
            }
            this.cipherSuite = null;
            this.peerCertificates = null;
            this.localCertificates = null;
        }

        private SSLSocket getSslSocket(HttpURLConnection httpConnection) {
            HttpEngine engine;
            if (httpConnection instanceof HttpsURLConnectionImpl) {
                engine = ((HttpsURLConnectionImpl) httpConnection).getHttpEngine();
            } else {
                engine = ((HttpURLConnectionImpl) httpConnection).getHttpEngine();
            }
            if (engine instanceof HttpsEngine) {
                return ((HttpsEngine) engine).getSslSocket();
            }
            return null;
        }

        public final void writeTo(Editor editor) {
            Writer writer = new BufferedWriter(new OutputStreamWriter(editor.newOutputStream(0), Util.UTF_8));
            writer.write(this.uri + 10);
            writer.write(this.requestMethod + 10);
            writer.write(Integer.toString(this.varyHeaders.length()) + 10);
            for (int i = 0; i < this.varyHeaders.length(); i++) {
                writer.write(this.varyHeaders.getFieldName(i) + ": " + this.varyHeaders.getValue(i) + 10);
            }
            writer.write(this.responseHeaders.getStatusLine() + 10);
            writer.write(Integer.toString(this.responseHeaders.length()) + 10);
            for (int i2 = 0; i2 < this.responseHeaders.length(); i2++) {
                writer.write(this.responseHeaders.getFieldName(i2) + ": " + this.responseHeaders.getValue(i2) + 10);
            }
            if (isHttps()) {
                writer.write(10);
                writer.write(this.cipherSuite + 10);
                writeCertArray(writer, this.peerCertificates);
                writeCertArray(writer, this.localCertificates);
            }
            writer.close();
        }

        /* access modifiers changed from: private */
        public boolean isHttps() {
            return this.uri.startsWith(AjxHttpLoader.DOMAIN_HTTPS);
        }

        private Certificate[] readCertArray(StrictLineReader reader) {
            int length = reader.readInt();
            if (length == -1) {
                return null;
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                Certificate[] result = new Certificate[length];
                for (int i = 0; i < length; i++) {
                    result[i] = certificateFactory.generateCertificate(new ByteArrayInputStream(Base64.decode(reader.readLine().getBytes("US-ASCII"))));
                }
                return result;
            } catch (CertificateException e) {
                throw new IOException(e.getMessage());
            }
        }

        private void writeCertArray(Writer writer, Certificate[] certificates) {
            if (certificates == null) {
                writer.write("-1\n");
                return;
            }
            try {
                writer.write(Integer.toString(certificates.length) + 10);
                for (Certificate encoded : certificates) {
                    writer.write(Base64.encode(encoded.getEncoded()) + 10);
                }
            } catch (CertificateEncodingException e) {
                throw new IOException(e.getMessage());
            }
        }

        public final boolean matches(URI uri2, String requestMethod2, Map<String, List<String>> requestHeaders) {
            if (!this.uri.equals(uri2.toString()) || !TextUtils.equals(this.requestMethod, requestMethod2) || !new ResponseHeaders(uri2, this.responseHeaders).varyMatches(this.varyHeaders.toMultimap(false), requestHeaders)) {
                return false;
            }
            return true;
        }
    }

    class EntryCacheResponse extends CacheResponse {
        private final Entry entry;
        private final InputStream in;
        /* access modifiers changed from: private */
        public final Snapshot snapshot;

        public EntryCacheResponse(Entry entry2, Snapshot snapshot2) {
            this.entry = entry2;
            this.snapshot = snapshot2;
            this.in = HttpResponseCache.newBodyInputStream(snapshot2);
        }

        public Map<String, List<String>> getHeaders() {
            return this.entry.responseHeaders.toMultimap(true);
        }

        public InputStream getBody() {
            return this.in;
        }
    }

    class EntrySecureCacheResponse extends SecureCacheResponse {
        private final Entry entry;
        private final InputStream in;
        /* access modifiers changed from: private */
        public final Snapshot snapshot;

        public EntrySecureCacheResponse(Entry entry2, Snapshot snapshot2) {
            this.entry = entry2;
            this.snapshot = snapshot2;
            this.in = HttpResponseCache.newBodyInputStream(snapshot2);
        }

        public Map<String, List<String>> getHeaders() {
            return this.entry.responseHeaders.toMultimap(true);
        }

        public InputStream getBody() {
            return this.in;
        }

        public String getCipherSuite() {
            return this.entry.cipherSuite;
        }

        public List<Certificate> getServerCertificateChain() {
            if (this.entry.peerCertificates != null && this.entry.peerCertificates.length != 0) {
                return Arrays.asList((Object[]) this.entry.peerCertificates.clone());
            }
            throw new SSLPeerUnverifiedException(null);
        }

        public Principal getPeerPrincipal() {
            if (this.entry.peerCertificates != null && this.entry.peerCertificates.length != 0) {
                return ((X509Certificate) this.entry.peerCertificates[0]).getSubjectX500Principal();
            }
            throw new SSLPeerUnverifiedException(null);
        }

        public List<Certificate> getLocalCertificateChain() {
            if (this.entry.localCertificates == null || this.entry.localCertificates.length == 0) {
                return null;
            }
            return Arrays.asList((Object[]) this.entry.localCertificates.clone());
        }

        public Principal getLocalPrincipal() {
            if (this.entry.localCertificates == null || this.entry.localCertificates.length == 0) {
                return null;
            }
            return ((X509Certificate) this.entry.localCertificates[0]).getSubjectX500Principal();
        }
    }

    public HttpResponseCache(File directory, long maxSize) {
        this.cache = DiskLruCache.open(directory, VERSION, 2, maxSize);
    }

    private String uriToKey(URI uri) {
        return Util.hash(uri.toString());
    }

    public final CacheResponse get(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) {
        try {
            Snapshot snapshot = this.cache.get(uriToKey(uri));
            if (snapshot == null) {
                return null;
            }
            Entry entry = new Entry(snapshot.getInputStream(0));
            if (entry.matches(uri, requestMethod, requestHeaders)) {
                return entry.isHttps() ? new EntrySecureCacheResponse(entry, snapshot) : new EntryCacheResponse(entry, snapshot);
            }
            snapshot.close();
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public final CacheRequest put(URI uri, URLConnection urlConnection) {
        if (!(urlConnection instanceof HttpURLConnection)) {
            return null;
        }
        HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
        String requestMethod = httpConnection.getRequestMethod();
        if (maybeRemove(requestMethod, uri) || !TextUtils.equals(requestMethod, "GET")) {
            return null;
        }
        HttpEngine httpEngine = getHttpEngine(httpConnection);
        if (httpEngine == null) {
            return null;
        }
        ResponseHeaders response = httpEngine.getResponseHeaders();
        if (response.hasVaryAll()) {
            return null;
        }
        Entry entry = new Entry(uri, httpEngine.getRequestHeaders().getHeaders().getAll(response.getVaryFields()), httpConnection);
        try {
            Editor editor = this.cache.edit(uriToKey(uri));
            if (editor == null) {
                return null;
            }
            entry.writeTo(editor);
            return new CacheRequestImpl(editor);
        } catch (IOException e) {
            abortQuietly(null);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public boolean maybeRemove(String requestMethod, URI uri) {
        if (!TextUtils.equals(requestMethod, "POST") && !TextUtils.equals(requestMethod, RequestMethodConstants.PUT_METHOD) && !TextUtils.equals(requestMethod, RequestMethodConstants.DELETE_METHOD)) {
            return false;
        }
        try {
            this.cache.remove(uriToKey(uri));
        } catch (IOException e) {
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void update(CacheResponse conditionalCacheHit, HttpURLConnection httpConnection) {
        Snapshot snapshot;
        HttpEngine httpEngine = getHttpEngine(httpConnection);
        Entry entry = new Entry(httpEngine.getUri(), httpEngine.getRequestHeaders().getHeaders().getAll(httpEngine.getResponseHeaders().getVaryFields()), httpConnection);
        if (conditionalCacheHit instanceof EntryCacheResponse) {
            snapshot = ((EntryCacheResponse) conditionalCacheHit).snapshot;
        } else {
            snapshot = ((EntrySecureCacheResponse) conditionalCacheHit).snapshot;
        }
        try {
            Editor editor = snapshot.edit();
            if (editor != null) {
                entry.writeTo(editor);
                editor.commit();
            }
        } catch (IOException e) {
            abortQuietly(null);
        }
    }

    private void abortQuietly(Editor editor) {
        if (editor != null) {
            try {
                editor.abort();
            } catch (IOException ignored) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "abortQuietly exception: " + ignored.toString());
            }
        }
    }

    private HttpEngine getHttpEngine(URLConnection httpConnection) {
        if (httpConnection instanceof HttpURLConnectionImpl) {
            return ((HttpURLConnectionImpl) httpConnection).getHttpEngine();
        }
        if (httpConnection instanceof HttpsURLConnectionImpl) {
            return ((HttpsURLConnectionImpl) httpConnection).getHttpEngine();
        }
        return null;
    }

    public final void delete() {
        this.cache.delete();
    }

    public final synchronized int getWriteAbortCount() {
        return this.writeAbortCount;
    }

    public final synchronized int getWriteSuccessCount() {
        return this.writeSuccessCount;
    }

    public final long getSize() {
        return this.cache.size();
    }

    public final long getMaxSize() {
        return this.cache.getMaxSize();
    }

    public final void flush() {
        this.cache.flush();
    }

    public final void close() {
        this.cache.close();
    }

    public final File getDirectory() {
        return this.cache.getDirectory();
    }

    public final boolean isClosed() {
        return this.cache.isClosed();
    }

    /* access modifiers changed from: private */
    public synchronized void trackResponse(ResponseSource source) {
        this.requestCount++;
        switch (source) {
            case CACHE:
                this.hitCount++;
                break;
            case CONDITIONAL_CACHE:
            case NETWORK:
                this.networkCount++;
                break;
        }
    }

    /* access modifiers changed from: private */
    public synchronized void trackConditionalCacheHit() {
        this.hitCount++;
    }

    public final synchronized int getNetworkCount() {
        return this.networkCount;
    }

    public final synchronized int getHitCount() {
        return this.hitCount;
    }

    public final synchronized int getRequestCount() {
        return this.requestCount;
    }

    /* access modifiers changed from: private */
    public static InputStream newBodyInputStream(final Snapshot snapshot) {
        return new FilterInputStream(snapshot.getInputStream(1)) {
            public final void close() {
                snapshot.close();
                super.close();
            }
        };
    }
}
