package org.nanohttpd.protocols.http;

import com.alipay.mobile.aspect.AliAspectCenter;
import com.alipay.multimedia.common.logging.MLog;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.internal.AroundClosure;
import org.aspectj.runtime.reflect.Factory;
import org.nanohttpd.protocols.http.NanoHTTPD.ResponseException;
import org.nanohttpd.protocols.http.content.ContentType;
import org.nanohttpd.protocols.http.content.CookieHandler;
import org.nanohttpd.protocols.http.request.Method;
import org.nanohttpd.protocols.http.response.Status;
import org.nanohttpd.protocols.http.tempfiles.ITempFile;
import org.nanohttpd.protocols.http.tempfiles.ITempFileManager;

public class HTTPSession implements IHTTPSession {
    public static final int BUFSIZE = 8192;
    public static final int MAX_HEADER_SIZE = 1024;
    public static final String POST_DATA = "postData";
    private static final /* synthetic */ StaticPart p = null;
    private final NanoHTTPD a;
    private final ITempFileManager b;
    private final OutputStream c;
    private final BufferedInputStream d;
    private int e;
    private int f;
    private String g;
    private Method h;
    private Map<String, List<String>> i;
    private Map<String, String> j;
    private CookieHandler k;
    private String l;
    private String m;
    private String n;
    private String o;

    public class AjcClosure1 extends AroundClosure {
        public AjcClosure1(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            return ((InetAddress) this.state[1]).getHostAddress();
        }
    }

    static {
        b();
    }

    private static /* synthetic */ void b() {
        Factory factory = new Factory("HTTPSession.java", HTTPSession.class);
        p = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "getHostAddress", (String) "java.net.InetAddress", (String) "", (String) "", (String) "", (String) "java.lang.String"), 132);
    }

    public HTTPSession(NanoHTTPD httpd, ITempFileManager tempFileManager, InputStream inputStream, OutputStream outputStream) {
        this.a = httpd;
        this.b = tempFileManager;
        this.d = new BufferedInputStream(inputStream, 8192);
        this.c = outputStream;
    }

    public HTTPSession(NanoHTTPD httpd, ITempFileManager tempFileManager, InputStream inputStream, OutputStream outputStream, InetAddress inetAddress) {
        String str;
        this.a = httpd;
        this.b = tempFileManager;
        this.d = new BufferedInputStream(inputStream, 8192);
        this.c = outputStream;
        if (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) {
            str = "127.0.0.1";
        } else {
            str = ((String) AliAspectCenter.aspectOf().doAspect(new AjcClosure1(new Object[]{this, inetAddress, Factory.makeJP(p, this, inetAddress)}).linkClosureAndJoinPoint(4112))).toString();
        }
        this.m = str;
        this.n = (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) ? "localhost" : inetAddress.getHostName().toString();
        this.j = new HashMap();
    }

    private void a(BufferedReader in, Map<String, String> pre, Map<String, List<String>> parms, Map<String, String> headers) {
        String uri;
        try {
            String inLine = in.readLine();
            if (inLine != null) {
                StringTokenizer st = new StringTokenizer(inLine);
                if (!st.hasMoreTokens()) {
                    throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
                }
                pre.put("method", st.nextToken());
                if (!st.hasMoreTokens()) {
                    throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
                }
                String uri2 = st.nextToken();
                int qmi = uri2.indexOf(63);
                if (qmi >= 0) {
                    a(uri2.substring(qmi + 1), parms);
                    uri = NanoHTTPD.decodePercent(uri2.substring(0, qmi));
                } else {
                    uri = NanoHTTPD.decodePercent(uri2);
                }
                if (st.hasMoreTokens()) {
                    this.o = st.nextToken();
                } else {
                    this.o = "HTTP/1.1";
                    MLog.i("HTTPSession", "no protocol version specified, strange. Assuming HTTP/1.1.");
                }
                String line = in.readLine();
                while (line != null && !line.trim().isEmpty()) {
                    int p2 = line.indexOf(58);
                    if (p2 >= 0) {
                        headers.put(line.substring(0, p2).trim().toLowerCase(Locale.US), line.substring(p2 + 1).trim());
                    }
                    line = in.readLine();
                }
                pre.put("uri", uri);
            }
        } catch (IOException ioe) {
            throw new ResponseException(Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage(), ioe);
        }
    }

    private void a(ContentType contentType, ByteBuffer fbuf, Map<String, List<String>> parms, Map<String, String> files) {
        int pcount;
        int pcount2 = 0;
        try {
            int[] boundaryIdxs = a(fbuf, contentType.getBoundary().getBytes());
            if (boundaryIdxs.length < 2) {
                throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but contains less than two boundary strings.");
            }
            byte[] partHeaderBuff = new byte[1024];
            for (int boundaryIdx = 0; boundaryIdx < boundaryIdxs.length - 1; boundaryIdx++) {
                fbuf.position(boundaryIdxs[boundaryIdx]);
                int len = fbuf.remaining() < 1024 ? fbuf.remaining() : 1024;
                fbuf.get(partHeaderBuff, 0, len);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(partHeaderBuff, 0, len);
                BufferedReader in = new BufferedReader(new InputStreamReader(byteArrayInputStream, Charset.forName(contentType.getEncoding())), len);
                String mpline = in.readLine();
                int headerLines = 0 + 1;
                if (mpline == null || !mpline.contains(contentType.getBoundary())) {
                    throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but chunk does not start with boundary.");
                }
                String partName = null;
                String fileName = null;
                String partContentType = null;
                String mpline2 = in.readLine();
                int headerLines2 = headerLines + 1;
                while (mpline2 != null && mpline2.trim().length() > 0) {
                    Matcher matcher = NanoHTTPD.CONTENT_DISPOSITION_PATTERN.matcher(mpline2);
                    if (matcher.matches()) {
                        Matcher matcher2 = NanoHTTPD.CONTENT_DISPOSITION_ATTRIBUTE_PATTERN.matcher(matcher.group(2));
                        int pcount3 = pcount2;
                        while (matcher2.find()) {
                            try {
                                String key = matcher2.group(1);
                                if ("name".equalsIgnoreCase(key)) {
                                    partName = matcher2.group(2);
                                } else {
                                    if ("filename".equalsIgnoreCase(key)) {
                                        fileName = matcher2.group(2);
                                        if (!fileName.isEmpty()) {
                                            if (pcount3 > 0) {
                                                int pcount4 = pcount3 + 1;
                                                partName = partName + String.valueOf(pcount3);
                                                pcount3 = pcount4;
                                            } else {
                                                pcount = pcount3 + 1;
                                                pcount3 = pcount;
                                            }
                                        }
                                    }
                                    pcount = pcount3;
                                    pcount3 = pcount;
                                }
                            } catch (ResponseException e2) {
                                e = e2;
                                int i2 = pcount3;
                                throw e;
                            } catch (Exception e3) {
                                e = e3;
                                int i3 = pcount3;
                                throw new ResponseException(Status.INTERNAL_ERROR, e.toString());
                            }
                        }
                        pcount2 = pcount3;
                    }
                    Matcher matcher3 = NanoHTTPD.CONTENT_TYPE_PATTERN.matcher(mpline2);
                    if (matcher3.matches()) {
                        partContentType = matcher3.group(2).trim();
                    }
                    mpline2 = in.readLine();
                    headerLines2++;
                }
                int partHeaderLength = 0;
                int headerLines3 = headerLines2;
                while (true) {
                    int headerLines4 = headerLines3 - 1;
                    if (headerLines3 <= 0) {
                        break;
                    }
                    partHeaderLength = a(partHeaderBuff, partHeaderLength);
                    headerLines3 = headerLines4;
                }
                if (partHeaderLength >= len - 4) {
                    throw new ResponseException(Status.INTERNAL_ERROR, "Multipart header size exceeds MAX_HEADER_SIZE.");
                }
                int partDataStart = boundaryIdxs[boundaryIdx] + partHeaderLength;
                int partDataEnd = boundaryIdxs[boundaryIdx + 1] - 4;
                fbuf.position(partDataStart);
                List values = parms.get(partName);
                if (values == null) {
                    values = new ArrayList();
                    parms.put(partName, values);
                }
                if (partContentType == null) {
                    byte[] data_bytes = new byte[(partDataEnd - partDataStart)];
                    fbuf.get(data_bytes);
                    String str = new String(data_bytes, contentType.getEncoding());
                    values.add(str);
                } else {
                    String path = a(fbuf, partDataStart, partDataEnd - partDataStart, fileName);
                    if (!files.containsKey(partName)) {
                        files.put(partName, path);
                    } else {
                        int count = 2;
                        while (files.containsKey(partName + count)) {
                            count++;
                        }
                        files.put(partName + count, path);
                    }
                    values.add(fileName);
                }
            }
        } catch (ResponseException e4) {
            e = e4;
            throw e;
        } catch (Exception e5) {
            e = e5;
            throw new ResponseException(Status.INTERNAL_ERROR, e.toString());
        }
    }

    private static int a(byte[] partHeaderBuff, int index) {
        while (partHeaderBuff[index] != 10) {
            index++;
        }
        return index + 1;
    }

    private void a(String parms, Map<String, List<String>> p2) {
        String key;
        String value;
        if (parms == null) {
            this.l = "";
            return;
        }
        this.l = parms;
        StringTokenizer st = new StringTokenizer(parms, "&");
        while (st.hasMoreTokens()) {
            String e2 = st.nextToken();
            int sep = e2.indexOf(61);
            if (sep >= 0) {
                key = NanoHTTPD.decodePercent(e2.substring(0, sep)).trim();
                value = NanoHTTPD.decodePercent(e2.substring(sep + 1));
            } else {
                key = NanoHTTPD.decodePercent(e2).trim();
                value = "";
            }
            List values = p2.get(key);
            if (values == null) {
                values = new ArrayList();
                p2.put(key, values);
            }
            values.add(value);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0031, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        throw r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003f, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        throw r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x016a, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        org.nanohttpd.protocols.http.response.Response.newFixedLengthResponse((org.nanohttpd.protocols.http.response.IStatus) r8.getStatus(), (java.lang.String) "text/plain", r8.getMessage()).send(r15.c);
        org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r15.c);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0183, code lost:
        org.nanohttpd.protocols.http.NanoHTTPD.safeClose(null);
        r15.b.clear();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0031 A[ExcHandler: SocketException (r11v15 'e' java.net.SocketException A[CUSTOM_DECLARE]), Splitter:B:1:0x0004] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003f A[ExcHandler: SocketTimeoutException (r11v13 'e' java.net.SocketTimeoutException A[CUSTOM_DECLARE]), Splitter:B:1:0x0004] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x016a A[ExcHandler: ResponseException (r8v0 're' org.nanohttpd.protocols.http.NanoHTTPD$ResponseException A[CUSTOM_DECLARE]), Splitter:B:1:0x0004] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execute() {
        /*
            r15 = this;
            r5 = 0
            r7 = 0
            r11 = 8192(0x2000, float:1.14794E-41)
            byte[] r1 = new byte[r11]     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11 = 0
            r15.e = r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11 = 0
            r15.f = r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.io.BufferedInputStream r11 = r15.d     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r12 = 8192(0x2000, float:1.14794E-41)
            r11.mark(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.io.BufferedInputStream r11 = r15.d     // Catch:{ SSLException -> 0x003d, IOException -> 0x0041, SocketException -> 0x0031, SocketTimeoutException -> 0x003f, ResponseException -> 0x016a }
            r12 = 0
            r13 = 8192(0x2000, float:1.14794E-41)
            int r9 = r11.read(r1, r12, r13)     // Catch:{ SSLException -> 0x003d, IOException -> 0x0041, SocketException -> 0x0031, SocketTimeoutException -> 0x003f, ResponseException -> 0x016a }
            r11 = -1
            if (r9 != r11) goto L_0x0083
            java.io.BufferedInputStream r11 = r15.d     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.io.OutputStream r11 = r15.c     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.net.SocketException r11 = new java.net.SocketException     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r12 = "NanoHttpd Shutdown"
            r11.<init>(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            throw r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x0031:
            r11 = move-exception
            throw r11     // Catch:{ all -> 0x0033 }
        L_0x0033:
            r11 = move-exception
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r7)
            org.nanohttpd.protocols.http.tempfiles.ITempFileManager r12 = r15.b
            r12.clear()
            throw r11
        L_0x003d:
            r11 = move-exception
            throw r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x003f:
            r11 = move-exception
            throw r11     // Catch:{ all -> 0x0033 }
        L_0x0041:
            r11 = move-exception
            java.io.BufferedInputStream r11 = r15.d     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.io.OutputStream r11 = r15.c     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.net.SocketException r11 = new java.net.SocketException     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r12 = "NanoHttpd Shutdown"
            r11.<init>(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            throw r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x0054:
            r10 = move-exception
            org.nanohttpd.protocols.http.response.Status r11 = org.nanohttpd.protocols.http.response.Status.INTERNAL_ERROR     // Catch:{ all -> 0x0033 }
            java.lang.String r12 = "text/plain"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0033 }
            java.lang.String r14 = "SSL PROTOCOL FAILURE: "
            r13.<init>(r14)     // Catch:{ all -> 0x0033 }
            java.lang.String r14 = r10.getMessage()     // Catch:{ all -> 0x0033 }
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x0033 }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0033 }
            org.nanohttpd.protocols.http.response.Response r11 = org.nanohttpd.protocols.http.response.Response.newFixedLengthResponse(r11, r12, r13)     // Catch:{ all -> 0x0033 }
            java.io.OutputStream r12 = r15.c     // Catch:{ all -> 0x0033 }
            r11.send(r12)     // Catch:{ all -> 0x0033 }
            java.io.OutputStream r11 = r15.c     // Catch:{ all -> 0x0033 }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r11)     // Catch:{ all -> 0x0033 }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r7)
            org.nanohttpd.protocols.http.tempfiles.ITempFileManager r11 = r15.b
            r11.clear()
        L_0x0082:
            return
        L_0x0083:
            if (r9 <= 0) goto L_0x00a3
            int r11 = r15.f     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            int r11 = r11 + r9
            r15.f = r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            int r11 = r15.f     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            int r11 = b(r1, r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r15.e = r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            int r11 = r15.e     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r11 > 0) goto L_0x00a3
            java.io.BufferedInputStream r11 = r15.d     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            int r12 = r15.f     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            int r13 = r15.f     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            int r13 = 8192 - r13
            int r9 = r11.read(r1, r12, r13)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            goto L_0x0083
        L_0x00a3:
            int r11 = r15.e     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            int r12 = r15.f     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r11 >= r12) goto L_0x00b6
            java.io.BufferedInputStream r11 = r15.d     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.reset()     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.io.BufferedInputStream r11 = r15.d     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            int r12 = r15.e     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            long r12 = (long) r12     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.skip(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x00b6:
            java.util.HashMap r11 = new java.util.HashMap     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.<init>()     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r15.i = r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.util.Map<java.lang.String, java.lang.String> r11 = r15.j     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r11 != 0) goto L_0x0163
            java.util.HashMap r11 = new java.util.HashMap     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.<init>()     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r15.j = r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x00c8:
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.io.InputStreamReader r11 = new java.io.InputStreamReader     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.io.ByteArrayInputStream r12 = new java.io.ByteArrayInputStream     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r13 = 0
            int r14 = r15.f     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r12.<init>(r1, r13, r14)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.<init>(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r3.<init>(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r6.<init>()     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r11 = r15.i     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.util.Map<java.lang.String, java.lang.String> r12 = r15.j     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r15.a(r3, r6, r11, r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r11 = r15.m     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r11 == 0) goto L_0x00fc
            java.util.Map<java.lang.String, java.lang.String> r11 = r15.j     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r12 = "remote-addr"
            java.lang.String r13 = r15.m     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.put(r12, r13)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.util.Map<java.lang.String, java.lang.String> r11 = r15.j     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r12 = "http-client-ip"
            java.lang.String r13 = r15.m     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.put(r12, r13)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x00fc:
            java.lang.String r11 = "method"
            java.lang.Object r11 = r6.get(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.request.Method r11 = org.nanohttpd.protocols.http.request.Method.lookup(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r15.h = r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.request.Method r11 = r15.h     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r11 != 0) goto L_0x018d
            org.nanohttpd.protocols.http.NanoHTTPD$ResponseException r12 = new org.nanohttpd.protocols.http.NanoHTTPD$ResponseException     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.response.Status r13 = org.nanohttpd.protocols.http.response.Status.BAD_REQUEST     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r11 = "BAD REQUEST: Syntax error. HTTP verb "
            r14.<init>(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r11 = "method"
            java.lang.Object r11 = r6.get(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.StringBuilder r11 = r14.append(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r14 = " unhandled."
            java.lang.StringBuilder r11 = r11.append(r14)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r11 = r11.toString()     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r12.<init>(r13, r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            throw r12     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x0133:
            r4 = move-exception
            org.nanohttpd.protocols.http.response.Status r11 = org.nanohttpd.protocols.http.response.Status.INTERNAL_ERROR     // Catch:{ all -> 0x0033 }
            java.lang.String r12 = "text/plain"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0033 }
            java.lang.String r14 = "SERVER INTERNAL ERROR: IOException: "
            r13.<init>(r14)     // Catch:{ all -> 0x0033 }
            java.lang.String r14 = r4.getMessage()     // Catch:{ all -> 0x0033 }
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x0033 }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0033 }
            org.nanohttpd.protocols.http.response.Response r11 = org.nanohttpd.protocols.http.response.Response.newFixedLengthResponse(r11, r12, r13)     // Catch:{ all -> 0x0033 }
            java.io.OutputStream r12 = r15.c     // Catch:{ all -> 0x0033 }
            r11.send(r12)     // Catch:{ all -> 0x0033 }
            java.io.OutputStream r11 = r15.c     // Catch:{ all -> 0x0033 }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r11)     // Catch:{ all -> 0x0033 }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r7)
            org.nanohttpd.protocols.http.tempfiles.ITempFileManager r11 = r15.b
            r11.clear()
            goto L_0x0082
        L_0x0163:
            java.util.Map<java.lang.String, java.lang.String> r11 = r15.j     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.clear()     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            goto L_0x00c8
        L_0x016a:
            r8 = move-exception
            org.nanohttpd.protocols.http.response.Status r11 = r8.getStatus()     // Catch:{ all -> 0x0033 }
            java.lang.String r12 = "text/plain"
            java.lang.String r13 = r8.getMessage()     // Catch:{ all -> 0x0033 }
            org.nanohttpd.protocols.http.response.Response r11 = org.nanohttpd.protocols.http.response.Response.newFixedLengthResponse(r11, r12, r13)     // Catch:{ all -> 0x0033 }
            java.io.OutputStream r12 = r15.c     // Catch:{ all -> 0x0033 }
            r11.send(r12)     // Catch:{ all -> 0x0033 }
            java.io.OutputStream r11 = r15.c     // Catch:{ all -> 0x0033 }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r11)     // Catch:{ all -> 0x0033 }
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r7)
            org.nanohttpd.protocols.http.tempfiles.ITempFileManager r11 = r15.b
            r11.clear()
            goto L_0x0082
        L_0x018d:
            java.lang.String r11 = "uri"
            java.lang.Object r11 = r6.get(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r15.g = r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.content.CookieHandler r11 = new org.nanohttpd.protocols.http.content.CookieHandler     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.util.Map<java.lang.String, java.lang.String> r12 = r15.j     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.<init>(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r15.k = r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.util.Map<java.lang.String, java.lang.String> r11 = r15.j     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r12 = "connection"
            java.lang.Object r2 = r11.get(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r11 = "HTTP/1.1"
            java.lang.String r12 = r15.o     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            boolean r11 = r11.equals(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r11 == 0) goto L_0x01bf
            if (r2 == 0) goto L_0x01be
            java.lang.String r11 = "(?i).*close.*"
            boolean r11 = r2.matches(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r11 != 0) goto L_0x01bf
        L_0x01be:
            r5 = 1
        L_0x01bf:
            org.nanohttpd.protocols.http.NanoHTTPD r11 = r15.a     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.response.Response r7 = r11.handle(r15)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r7 != 0) goto L_0x01d1
            org.nanohttpd.protocols.http.NanoHTTPD$ResponseException r11 = new org.nanohttpd.protocols.http.NanoHTTPD$ResponseException     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.response.Status r12 = org.nanohttpd.protocols.http.response.Status.INTERNAL_ERROR     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r13 = "SERVER INTERNAL ERROR: Serve() returned a null response."
            r11.<init>(r12, r13)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            throw r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x01d1:
            java.util.Map<java.lang.String, java.lang.String> r11 = r15.j     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r12 = "accept-encoding"
            java.lang.Object r0 = r11.get(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.content.CookieHandler r11 = r15.k     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r11.unloadQueue(r7)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            org.nanohttpd.protocols.http.request.Method r11 = r15.h     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r7.setRequestMethod(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r0 == 0) goto L_0x01ef
            java.lang.String r11 = "gzip"
            boolean r11 = r0.contains(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r11 != 0) goto L_0x01f3
        L_0x01ef:
            r11 = 0
            r7.setUseGzip(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x01f3:
            r7.setKeepAlive(r5)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.io.OutputStream r11 = r15.c     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            r7.send(r11)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r5 == 0) goto L_0x0203
            boolean r11 = r7.isCloseConnection()     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            if (r11 == 0) goto L_0x020b
        L_0x0203:
            java.net.SocketException r11 = new java.net.SocketException     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            java.lang.String r12 = "NanoHttpd Shutdown"
            r11.<init>(r12)     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
            throw r11     // Catch:{ SocketException -> 0x0031, SocketTimeoutException -> 0x003f, SSLException -> 0x0054, IOException -> 0x0133, ResponseException -> 0x016a }
        L_0x020b:
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r7)
            org.nanohttpd.protocols.http.tempfiles.ITempFileManager r11 = r15.b
            r11.clear()
            goto L_0x0082
        */
        throw new UnsupportedOperationException("Method not decompiled: org.nanohttpd.protocols.http.HTTPSession.execute():void");
    }

    private static int b(byte[] buf, int rlen) {
        for (int splitbyte = 0; splitbyte + 1 < rlen; splitbyte++) {
            if (buf[splitbyte] == 13 && buf[splitbyte + 1] == 10 && splitbyte + 3 < rlen && buf[splitbyte + 2] == 13 && buf[splitbyte + 3] == 10) {
                return splitbyte + 4;
            }
            if (buf[splitbyte] == 10 && buf[splitbyte + 1] == 10) {
                return splitbyte + 2;
            }
        }
        return 0;
    }

    private static int[] a(ByteBuffer b2, byte[] boundary) {
        int[] res = new int[0];
        if (b2.remaining() < boundary.length) {
            return res;
        }
        int search_window_pos = 0;
        byte[] search_window = new byte[(boundary.length + 4096)];
        int first_fill = b2.remaining() < search_window.length ? b2.remaining() : search_window.length;
        b2.get(search_window, 0, first_fill);
        int new_bytes = first_fill - boundary.length;
        do {
            int j2 = 0;
            while (j2 < new_bytes) {
                int i2 = 0;
                while (i2 < boundary.length && search_window[j2 + i2] == boundary[i2]) {
                    if (i2 == boundary.length - 1) {
                        int[] new_res = new int[(res.length + 1)];
                        System.arraycopy(res, 0, new_res, 0, res.length);
                        new_res[res.length] = search_window_pos + j2;
                        res = new_res;
                    }
                    i2++;
                }
                j2++;
            }
            search_window_pos += new_bytes;
            System.arraycopy(search_window, search_window.length - boundary.length, search_window, 0, boundary.length);
            new_bytes = search_window.length - boundary.length;
            if (b2.remaining() < new_bytes) {
                new_bytes = b2.remaining();
            }
            b2.get(search_window, boundary.length, new_bytes);
        } while (new_bytes > 0);
        return res;
    }

    public CookieHandler getCookies() {
        return this.k;
    }

    public final Map<String, String> getHeaders() {
        return this.j;
    }

    public final InputStream getInputStream() {
        return this.d;
    }

    public final Method getMethod() {
        return this.h;
    }

    @Deprecated
    public final Map<String, String> getParms() {
        Map result = new HashMap();
        for (String key : this.i.keySet()) {
            result.put(key, this.i.get(key).get(0));
        }
        return result;
    }

    public final Map<String, List<String>> getParameters() {
        return this.i;
    }

    public String getQueryParameterString() {
        return this.l;
    }

    private RandomAccessFile a() {
        try {
            return new RandomAccessFile(this.b.createTempFile(null).getName(), "rw");
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }

    public final String getUri() {
        return this.g;
    }

    public long getBodySize() {
        if (this.j.containsKey("content-length")) {
            return Long.parseLong(this.j.get("content-length"));
        }
        if (this.e < this.f) {
            return (long) (this.f - this.e);
        }
        return 0;
    }

    /* JADX WARNING: type inference failed for: r14v0 */
    /* JADX WARNING: type inference failed for: r14v2, types: [java.io.RandomAccessFile] */
    /* JADX WARNING: type inference failed for: r15v0 */
    /* JADX WARNING: type inference failed for: r15v1, types: [java.io.DataOutput] */
    /* JADX WARNING: type inference failed for: r14v3, types: [java.io.RandomAccessFile, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r15v2, types: [java.io.DataOutputStream] */
    /* JADX WARNING: type inference failed for: r14v6 */
    /* JADX WARNING: type inference failed for: r14v7 */
    /* JADX WARNING: type inference failed for: r15v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseBody(java.util.Map<java.lang.String, java.lang.String> r19) {
        /*
            r18 = this;
            r14 = 0
            long r16 = r18.getBodySize()     // Catch:{ all -> 0x0052 }
            r8 = 0
            r2 = 1024(0x400, double:5.06E-321)
            int r2 = (r16 > r2 ? 1 : (r16 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0057
            java.io.ByteArrayOutputStream r8 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0052 }
            r8.<init>()     // Catch:{ all -> 0x0052 }
            java.io.DataOutputStream r15 = new java.io.DataOutputStream     // Catch:{ all -> 0x0052 }
            r15.<init>(r8)     // Catch:{ all -> 0x0052 }
        L_0x0016:
            r2 = 512(0x200, float:7.175E-43)
            byte[] r9 = new byte[r2]     // Catch:{ all -> 0x0052 }
        L_0x001a:
            r0 = r18
            int r2 = r0.f     // Catch:{ all -> 0x0052 }
            if (r2 < 0) goto L_0x005d
            r2 = 0
            int r2 = (r16 > r2 ? 1 : (r16 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x005d
            r0 = r18
            java.io.BufferedInputStream r2 = r0.d     // Catch:{ all -> 0x0052 }
            r3 = 0
            r4 = 512(0x200, double:2.53E-321)
            r0 = r16
            long r4 = java.lang.Math.min(r0, r4)     // Catch:{ all -> 0x0052 }
            int r4 = (int) r4     // Catch:{ all -> 0x0052 }
            int r2 = r2.read(r9, r3, r4)     // Catch:{ all -> 0x0052 }
            r0 = r18
            r0.f = r2     // Catch:{ all -> 0x0052 }
            r0 = r18
            int r2 = r0.f     // Catch:{ all -> 0x0052 }
            long r2 = (long) r2     // Catch:{ all -> 0x0052 }
            long r16 = r16 - r2
            r0 = r18
            int r2 = r0.f     // Catch:{ all -> 0x0052 }
            if (r2 <= 0) goto L_0x001a
            r2 = 0
            r0 = r18
            int r3 = r0.f     // Catch:{ all -> 0x0052 }
            r15.write(r9, r2, r3)     // Catch:{ all -> 0x0052 }
            goto L_0x001a
        L_0x0052:
            r2 = move-exception
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r14)
            throw r2
        L_0x0057:
            java.io.RandomAccessFile r14 = r18.a()     // Catch:{ all -> 0x0052 }
            r15 = r14
            goto L_0x0016
        L_0x005d:
            if (r8 == 0) goto L_0x009f
            byte[] r2 = r8.toByteArray()     // Catch:{ all -> 0x0052 }
            r3 = 0
            int r4 = r8.size()     // Catch:{ all -> 0x0052 }
            java.nio.ByteBuffer r11 = java.nio.ByteBuffer.wrap(r2, r3, r4)     // Catch:{ all -> 0x0052 }
        L_0x006c:
            org.nanohttpd.protocols.http.request.Method r2 = org.nanohttpd.protocols.http.request.Method.POST     // Catch:{ all -> 0x0052 }
            r0 = r18
            org.nanohttpd.protocols.http.request.Method r3 = r0.h     // Catch:{ all -> 0x0052 }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x00fe
            org.nanohttpd.protocols.http.content.ContentType r10 = new org.nanohttpd.protocols.http.content.ContentType     // Catch:{ all -> 0x0052 }
            r0 = r18
            java.util.Map<java.lang.String, java.lang.String> r2 = r0.j     // Catch:{ all -> 0x0052 }
            java.lang.String r3 = "content-type"
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x0052 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0052 }
            r10.<init>(r2)     // Catch:{ all -> 0x0052 }
            boolean r2 = r10.isMultipart()     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x00c4
            java.lang.String r2 = r10.getBoundary()     // Catch:{ all -> 0x0052 }
            if (r2 != 0) goto L_0x00b5
            org.nanohttpd.protocols.http.NanoHTTPD$ResponseException r2 = new org.nanohttpd.protocols.http.NanoHTTPD$ResponseException     // Catch:{ all -> 0x0052 }
            org.nanohttpd.protocols.http.response.Status r3 = org.nanohttpd.protocols.http.response.Status.BAD_REQUEST     // Catch:{ all -> 0x0052 }
            java.lang.String r4 = "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html"
            r2.<init>(r3, r4)     // Catch:{ all -> 0x0052 }
            throw r2     // Catch:{ all -> 0x0052 }
        L_0x009f:
            java.nio.channels.FileChannel r2 = r14.getChannel()     // Catch:{ all -> 0x0052 }
            java.nio.channels.FileChannel$MapMode r3 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ all -> 0x0052 }
            r4 = 0
            long r6 = r14.length()     // Catch:{ all -> 0x0052 }
            java.nio.MappedByteBuffer r11 = r2.map(r3, r4, r6)     // Catch:{ all -> 0x0052 }
            r2 = 0
            r14.seek(r2)     // Catch:{ all -> 0x0052 }
            goto L_0x006c
        L_0x00b5:
            r0 = r18
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r2 = r0.i     // Catch:{ all -> 0x0052 }
            r0 = r18
            r1 = r19
            r0.a(r10, r11, r2, r1)     // Catch:{ all -> 0x0052 }
        L_0x00c0:
            org.nanohttpd.protocols.http.NanoHTTPD.safeClose(r14)
            return
        L_0x00c4:
            int r2 = r11.remaining()     // Catch:{ all -> 0x0052 }
            byte[] r12 = new byte[r2]     // Catch:{ all -> 0x0052 }
            r11.get(r12)     // Catch:{ all -> 0x0052 }
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x0052 }
            java.lang.String r3 = r10.getEncoding()     // Catch:{ all -> 0x0052 }
            r2.<init>(r12, r3)     // Catch:{ all -> 0x0052 }
            java.lang.String r13 = r2.trim()     // Catch:{ all -> 0x0052 }
            java.lang.String r2 = "application/x-www-form-urlencoded"
            java.lang.String r3 = r10.getContentType()     // Catch:{ all -> 0x0052 }
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x00f0
            r0 = r18
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r2 = r0.i     // Catch:{ all -> 0x0052 }
            r0 = r18
            r0.a(r13, r2)     // Catch:{ all -> 0x0052 }
            goto L_0x00c0
        L_0x00f0:
            int r2 = r13.length()     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x00c0
            java.lang.String r2 = "postData"
            r0 = r19
            r0.put(r2, r13)     // Catch:{ all -> 0x0052 }
            goto L_0x00c0
        L_0x00fe:
            org.nanohttpd.protocols.http.request.Method r2 = org.nanohttpd.protocols.http.request.Method.PUT     // Catch:{ all -> 0x0052 }
            r0 = r18
            org.nanohttpd.protocols.http.request.Method r3 = r0.h     // Catch:{ all -> 0x0052 }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x00c0
            java.lang.String r2 = "content"
            r3 = 0
            int r4 = r11.limit()     // Catch:{ all -> 0x0052 }
            r5 = 0
            r0 = r18
            java.lang.String r3 = r0.a(r11, r3, r4, r5)     // Catch:{ all -> 0x0052 }
            r0 = r19
            r0.put(r2, r3)     // Catch:{ all -> 0x0052 }
            goto L_0x00c0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.nanohttpd.protocols.http.HTTPSession.parseBody(java.util.Map):void");
    }

    private String a(ByteBuffer b2, int offset, int len, String filename_hint) {
        String path = "";
        if (len > 0) {
            FileOutputStream fileOutputStream = null;
            try {
                ITempFile tempFile = this.b.createTempFile(filename_hint);
                ByteBuffer src = b2.duplicate();
                FileOutputStream fileOutputStream2 = new FileOutputStream(tempFile.getName());
                try {
                    FileChannel dest = fileOutputStream2.getChannel();
                    src.position(offset).limit(offset + len);
                    dest.write(src.slice());
                    path = tempFile.getName();
                    NanoHTTPD.safeClose(fileOutputStream2);
                } catch (Exception e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    try {
                        throw new Error(e);
                    } catch (Throwable th) {
                        th = th;
                        NanoHTTPD.safeClose(fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = fileOutputStream2;
                    NanoHTTPD.safeClose(fileOutputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                throw new Error(e);
            }
        }
        return path;
    }

    public String getRemoteIpAddress() {
        return this.m;
    }

    public String getRemoteHostName() {
        return this.n;
    }
}
