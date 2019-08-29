package org.nanohttpd.protocols.http.response;

import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.multimedia.common.logging.MLog;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDownloadLogRequest;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.zip.GZIPOutputStream;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.content.ContentType;
import org.nanohttpd.protocols.http.request.Method;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class Response implements Closeable {
    private IStatus a;
    private String b;
    private InputStream c;
    private long d;
    private final Map<String, String> e = new HashMap<String, String>() {
        public final String put(String key, String value) {
            Response.this.f.put(key == null ? key : key.toLowerCase(), value);
            return (String) super.put(key, value);
        }
    };
    /* access modifiers changed from: private */
    public final Map<String, String> f = new HashMap();
    private Method g;
    private boolean h;
    private boolean i;
    private List<String> j;
    private int k = a.DEFAULT$5937917e;

    private enum a {
        ;
        
        public static final int ALWAYS$5937917e = 0;
        public static final int DEFAULT$5937917e = 0;
        public static final int NEVER$5937917e = 0;

        public static int[] values$62d40078() {
            return (int[]) a.clone();
        }

        static {
            DEFAULT$5937917e = 1;
            ALWAYS$5937917e = 2;
            NEVER$5937917e = 3;
            a = new int[]{DEFAULT$5937917e, ALWAYS$5937917e, NEVER$5937917e};
        }
    }

    private Response(IStatus status, String mimeType, InputStream data, long totalBytes) {
        boolean z = false;
        this.a = status;
        this.b = mimeType;
        if (data == null) {
            this.c = new ByteArrayInputStream(new byte[0]);
            this.d = 0;
        } else {
            this.c = data;
            this.d = totalBytes;
        }
        this.h = this.d < 0 ? true : z;
        this.i = true;
        this.j = new ArrayList(10);
    }

    public void close() {
        if (this.c != null) {
            this.c.close();
        }
    }

    public void addCookieHeader(String cookie) {
        this.j.add(cookie);
    }

    public List<String> getCookieHeaders() {
        return this.j;
    }

    public void addHeader(String name, String value) {
        this.e.put(name, value);
    }

    public void closeConnection(boolean close) {
        if (close) {
            this.e.put("connection", DataflowMonitorModel.METHOD_NAME_CLOSE);
        } else {
            this.e.remove("connection");
        }
    }

    public boolean isCloseConnection() {
        return DataflowMonitorModel.METHOD_NAME_CLOSE.equals(getHeader("connection"));
    }

    public InputStream getData() {
        return this.c;
    }

    public String getHeader(String name) {
        return this.f.get(name.toLowerCase());
    }

    public String getMimeType() {
        return this.b;
    }

    public Method getRequestMethod() {
        return this.g;
    }

    public IStatus getStatus() {
        return this.a;
    }

    public void setKeepAlive(boolean useKeepAlive) {
        this.i = useKeepAlive;
    }

    public void send(OutputStream outputStream) {
        SimpleDateFormat gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            if (this.a == null) {
                throw new Error("sendResponse(): Status can't be null.");
            }
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, new ContentType(this.b).getEncoding())), false);
            pw.append("HTTP/1.1 ").append(this.a.getDescription()).append(" \r\n");
            if (this.b != null) {
                a(pw, "Content-Type", this.b);
            }
            if (getHeader("date") == null) {
                a(pw, AutoDownloadLogRequest.AUTO_KEY_DATE, gmtFrmt.format(new Date()));
            }
            for (Entry entry : this.e.entrySet()) {
                a(pw, (String) entry.getKey(), (String) entry.getValue());
            }
            for (String cookieHeader : this.j) {
                a(pw, "Set-Cookie", cookieHeader);
            }
            if (getHeader("connection") == null) {
                a(pw, H5AppHttpRequest.HEADER_CONNECTION, this.i ? "keep-alive" : DataflowMonitorModel.METHOD_NAME_CLOSE);
            }
            if (getHeader("content-length") != null) {
                setUseGzip(false);
            }
            if (useGzipWhenAccepted()) {
                a(pw, TransportConstants.KEY_X_CONTENT_ENCODING, "gzip");
                setChunkedTransfer(true);
            }
            long pending = this.c != null ? this.d : 0;
            if (this.g != Method.HEAD && this.h) {
                a(pw, "Transfer-Encoding", "chunked");
            } else if (!useGzipWhenAccepted()) {
                pending = a(pw, pending);
            }
            pw.append(MultipartUtility.LINE_FEED);
            pw.flush();
            a(outputStream, pending);
            outputStream.flush();
            NanoHTTPD.safeClose(this.c);
        } catch (IOException ioe) {
            MLog.e("Response", "Could not send response to the client.exp=" + ioe);
        }
    }

    private static void a(PrintWriter pw, String key, String value) {
        pw.append(key).append(": ").append(value).append(MultipartUtility.LINE_FEED);
    }

    private long a(PrintWriter pw, long defaultSize) {
        String contentLengthString = getHeader("content-length");
        long size = defaultSize;
        if (contentLengthString != null) {
            try {
                return Long.parseLong(contentLengthString);
            } catch (NumberFormatException e2) {
                MLog.e("Response", "content-length was no number " + contentLengthString);
                return size;
            }
        } else {
            pw.print("Content-Length: " + size + MultipartUtility.LINE_FEED);
            return size;
        }
    }

    private void a(OutputStream outputStream, long pending) {
        if (this.g == Method.HEAD || !this.h) {
            b(outputStream, pending);
            return;
        }
        ChunkedOutputStream chunkedOutputStream = new ChunkedOutputStream(outputStream);
        b(chunkedOutputStream, -1);
        chunkedOutputStream.finish();
    }

    private void b(OutputStream outputStream, long pending) {
        if (useGzipWhenAccepted()) {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
            c(gzipOutputStream, -1);
            gzipOutputStream.finish();
            return;
        }
        c(outputStream, pending);
    }

    private void c(OutputStream outputStream, long pending) {
        boolean sendEverything;
        byte[] buff = new byte[65536];
        if (pending == -1) {
            sendEverything = true;
        } else {
            sendEverything = false;
        }
        while (true) {
            if (pending > 0 || sendEverything) {
                int read = this.c.read(buff, 0, (int) (sendEverything ? 65536 : Math.min(pending, IjkMediaMeta.AV_CH_TOP_BACK_CENTER)));
                if (read > 0) {
                    try {
                        outputStream.write(buff, 0, read);
                        outputStream.flush();
                    } catch (Exception e2) {
                        if (this.c != null) {
                            this.c.close();
                        }
                    }
                    if (!sendEverything) {
                        pending -= (long) read;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public void setChunkedTransfer(boolean chunkedTransfer) {
        this.h = chunkedTransfer;
    }

    public void setData(InputStream data) {
        this.c = data;
    }

    public void setMimeType(String mimeType) {
        this.b = mimeType;
    }

    public void setRequestMethod(Method requestMethod) {
        this.g = requestMethod;
    }

    public void setStatus(IStatus status) {
        this.a = status;
    }

    public static Response newChunkedResponse(IStatus status, String mimeType, InputStream data) {
        return new Response(status, mimeType, data, -1);
    }

    public static Response newFixedLengthResponse(IStatus status, String mimeType, byte[] data) {
        return newFixedLengthResponse(status, mimeType, new ByteArrayInputStream(data), (long) data.length);
    }

    public static Response newFixedLengthResponse(IStatus status, String mimeType, InputStream data, long totalBytes) {
        return new Response(status, mimeType, data, totalBytes);
    }

    public static Response newFixedLengthResponse(IStatus status, String mimeType, String txt) {
        byte[] bytes;
        ContentType contentType = new ContentType(mimeType);
        if (txt == null) {
            return newFixedLengthResponse(status, mimeType, new ByteArrayInputStream(new byte[0]), 0);
        }
        try {
            if (!Charset.forName(contentType.getEncoding()).newEncoder().canEncode(txt)) {
                contentType = contentType.tryUTF8();
            }
            bytes = txt.getBytes(contentType.getEncoding());
        } catch (UnsupportedEncodingException e2) {
            MLog.e("Response", "encoding problem, responding nothing" + e2);
            bytes = new byte[0];
        }
        return newFixedLengthResponse(status, contentType.getContentTypeHeader(), new ByteArrayInputStream(bytes), (long) bytes.length);
    }

    public static Response newFixedLengthResponse(String msg) {
        return newFixedLengthResponse((IStatus) Status.OK, (String) "text/html", msg);
    }

    public Response setUseGzip(boolean useGzip) {
        this.k = useGzip ? a.ALWAYS$5937917e : a.NEVER$5937917e;
        return this;
    }

    public boolean useGzipWhenAccepted() {
        if (this.k == a.DEFAULT$5937917e) {
            if (getMimeType() == null || (!getMimeType().toLowerCase().contains("text/") && !getMimeType().toLowerCase().contains("/json"))) {
                return false;
            }
            return true;
        } else if (this.k != a.ALWAYS$5937917e) {
            return false;
        } else {
            return true;
        }
    }
}
