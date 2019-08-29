package com.autonavi.link.protocol.http;

import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.common.nbnet.api.NBNetStatus;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.link.transmit.proxy.LinkProxy;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDownloadLogRequest;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public abstract class NanoHTTPD {
    /* access modifiers changed from: private */
    public static final Pattern CONTENT_DISPOSITION_ATTRIBUTE_PATTERN = Pattern.compile("[ |\t]*([a-zA-Z]*)[ |\t]*=[ |\t]*['|\"]([^\"^']*)['|\"]");
    private static final String CONTENT_DISPOSITION_ATTRIBUTE_REGEX = "[ |\t]*([a-zA-Z]*)[ |\t]*=[ |\t]*['|\"]([^\"^']*)['|\"]";
    /* access modifiers changed from: private */
    public static final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile("([ |\t]*Content-Disposition[ |\t]*:)(.*)", 2);
    private static final String CONTENT_DISPOSITION_REGEX = "([ |\t]*Content-Disposition[ |\t]*:)(.*)";
    /* access modifiers changed from: private */
    public static final Pattern CONTENT_TYPE_PATTERN = Pattern.compile("([ |\t]*content-type[ |\t]*:)(.*)", 2);
    private static final String CONTENT_TYPE_REGEX = "([ |\t]*content-type[ |\t]*:)(.*)";
    /* access modifiers changed from: private */
    public static final Logger LOG = Logger.getLogger(NanoHTTPD.class.getName());
    public static final String MIME_HTML = "text/html";
    public static final String MIME_PLAINTEXT = "text/plain";
    protected static Map<String, String> MIME_TYPES = null;
    private static final String QUERY_STRING_PARAMETER = "NanoHttpd.QUERY_STRING";
    public static final int SOCKET_READ_TIMEOUT = 30000;
    protected AsyncRunner asyncRunner;
    /* access modifiers changed from: private */
    public final String hostname;
    /* access modifiers changed from: private */
    public final int myPort;
    /* access modifiers changed from: private */
    public volatile ServerSocket myServerSocket;
    private Thread myThread;
    private ServerSocketFactory serverSocketFactory;
    /* access modifiers changed from: private */
    public TempFileManagerFactory tempFileManagerFactory;

    public interface AsyncRunner {
        void closeAll();

        void closed(ClientHandler clientHandler);

        void exec(ClientHandler clientHandler);
    }

    public class ClientHandler implements Runnable {
        private final InputStream b;
        private final Socket c;

        private ClientHandler(InputStream inputStream, Socket socket) {
            this.b = inputStream;
            this.c = socket;
        }

        public void close() {
            NanoHTTPD.safeClose(this.b);
            NanoHTTPD.safeClose(this.c);
        }

        public void run() {
            OutputStream outputStream;
            Exception e;
            try {
                outputStream = this.c.getOutputStream();
                try {
                    HTTPSession hTTPSession = new HTTPSession(NanoHTTPD.this.tempFileManagerFactory.create(), this.b, outputStream, this.c.getInetAddress());
                    while (!this.c.isClosed()) {
                        hTTPSession.execute();
                    }
                } catch (Exception e2) {
                    e = e2;
                    try {
                        com.autonavi.link.utils.Logger.d((String) "ClientHandler", e.getMessage(), new Object[0]);
                        NanoHTTPD.LOG.log(Level.SEVERE, "Communication with the client broken, or an bug in the handler code", e);
                        NanoHTTPD.safeClose(outputStream);
                        NanoHTTPD.safeClose(this.b);
                        NanoHTTPD.safeClose(this.c);
                        NanoHTTPD.this.asyncRunner.closed(this);
                    } catch (Throwable th) {
                        th = th;
                        NanoHTTPD.safeClose(outputStream);
                        NanoHTTPD.safeClose(this.b);
                        NanoHTTPD.safeClose(this.c);
                        NanoHTTPD.this.asyncRunner.closed(this);
                        throw th;
                    }
                }
            } catch (Exception e3) {
                Exception exc = e3;
                outputStream = null;
                e = exc;
                com.autonavi.link.utils.Logger.d((String) "ClientHandler", e.getMessage(), new Object[0]);
                if ((!(e instanceof SocketException) || !"NanoHttpd Shutdown".equals(e.getMessage())) && !(e instanceof SocketTimeoutException)) {
                    NanoHTTPD.LOG.log(Level.SEVERE, "Communication with the client broken, or an bug in the handler code", e);
                }
                NanoHTTPD.safeClose(outputStream);
                NanoHTTPD.safeClose(this.b);
                NanoHTTPD.safeClose(this.c);
                NanoHTTPD.this.asyncRunner.closed(this);
            } catch (Throwable th2) {
                Throwable th3 = th2;
                outputStream = null;
                th = th3;
                NanoHTTPD.safeClose(outputStream);
                NanoHTTPD.safeClose(this.b);
                NanoHTTPD.safeClose(this.c);
                NanoHTTPD.this.asyncRunner.closed(this);
                throw th;
            }
            NanoHTTPD.safeClose(outputStream);
            NanoHTTPD.safeClose(this.b);
            NanoHTTPD.safeClose(this.c);
            NanoHTTPD.this.asyncRunner.closed(this);
        }
    }

    public static class ContentType {
        private static final Pattern a = Pattern.compile("[ |\t]*([^/^ ^;^,]+/[^ ^;^,]+)", 2);
        private static final Pattern b = Pattern.compile("[ |\t]*(charset)[ |\t]*=[ |\t]*['|\"]?([^\"^'^;^,]*)['|\"]?", 2);
        private static final Pattern c = Pattern.compile("[ |\t]*(boundary)[ |\t]*=[ |\t]*['|\"]?([^\"^'^;^,]*)['|\"]?", 2);
        private final String d;
        private final String e;
        private final String f;
        private final String g;

        public ContentType(String str) {
            this.d = str;
            if (str != null) {
                this.e = a(str, a, "", 1);
                this.f = a(str, b, null, 2);
            } else {
                this.e = "";
                this.f = "UTF-8";
            }
            if ("multipart/form-data".equalsIgnoreCase(this.e)) {
                this.g = a(str, c, null, 2);
            } else {
                this.g = null;
            }
        }

        private String a(String str, Pattern pattern, String str2, int i) {
            Matcher matcher = pattern.matcher(str);
            return matcher.find() ? matcher.group(i) : str2;
        }

        public String getContentTypeHeader() {
            return this.d;
        }

        public String getContentType() {
            return this.e;
        }

        public String getEncoding() {
            return this.f == null ? "US-ASCII" : this.f;
        }

        public String getBoundary() {
            return this.g;
        }

        public boolean isMultipart() {
            return "multipart/form-data".equalsIgnoreCase(this.e);
        }

        public ContentType tryUTF8() {
            if (this.f != null) {
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.d);
            sb.append("; charset=UTF-8");
            return new ContentType(sb.toString());
        }
    }

    public static class Cookie {
        private final String a;
        private final String b;
        private final String c;

        public String getHTTPHeader() {
            return String.format("%s=%s; expires=%s", new Object[]{this.a, this.b, this.c});
        }
    }

    public class CookieHandler implements Iterable<String> {
        private final HashMap<String, String> b = new HashMap<>();
        private final ArrayList<Cookie> c = new ArrayList<>();

        public CookieHandler(Map<String, String> map) {
            String str = map.get("cookie");
            if (str != null) {
                for (String trim : str.split(";")) {
                    String[] split = trim.trim().split("=");
                    if (split.length == 2) {
                        this.b.put(split[0], split[1]);
                    }
                }
            }
        }

        public Iterator<String> iterator() {
            return this.b.keySet().iterator();
        }

        public void unloadQueue(Response response) {
            Iterator<Cookie> it = this.c.iterator();
            while (it.hasNext()) {
                response.addHeader("Set-Cookie", it.next().getHTTPHeader());
            }
        }
    }

    public static class DefaultAsyncRunner implements AsyncRunner {
        private long a;
        private final List<ClientHandler> b = Collections.synchronizedList(new ArrayList());

        public void closeAll() {
            Iterator it = new ArrayList(this.b).iterator();
            while (it.hasNext()) {
                ((ClientHandler) it.next()).close();
            }
        }

        public void closed(ClientHandler clientHandler) {
            this.b.remove(clientHandler);
        }

        public void exec(ClientHandler clientHandler) {
            this.a++;
            Thread thread = new Thread(clientHandler);
            thread.setDaemon(true);
            StringBuilder sb = new StringBuilder("NanoHttpd Request Processor (#");
            sb.append(this.a);
            sb.append(")");
            thread.setName(sb.toString());
            this.b.add(clientHandler);
            thread.start();
        }
    }

    public static class DefaultServerSocketFactory implements ServerSocketFactory {
        public ServerSocket create() throws IOException {
            return new ServerSocket();
        }
    }

    public static class DefaultTempFile implements TempFile {
        private final File a;
        private final OutputStream b = new FileOutputStream(this.a);

        public DefaultTempFile(File file) throws IOException {
            this.a = File.createTempFile("NanoHTTPD-", "", file);
        }

        public void delete() throws Exception {
            NanoHTTPD.safeClose(this.b);
            if (!this.a.delete()) {
                com.autonavi.link.utils.Logger.d((String) "DefaultTempFile", (String) "delete-----------> ", new Object[0]);
                throw new Exception("could not delete temporary file");
            }
        }

        public String getName() {
            return this.a.getAbsolutePath();
        }
    }

    public static class DefaultTempFileManager implements TempFileManager {
        private final File a = new File(System.getProperty("java.io.tmpdir"));
        private final List<TempFile> b;

        public DefaultTempFileManager() {
            if (!this.a.exists()) {
                this.a.mkdirs();
            }
            this.b = new ArrayList();
        }

        public void clear() {
            for (TempFile delete : this.b) {
                try {
                    delete.delete();
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder("Exception-----------> ");
                    sb.append(e.getMessage());
                    com.autonavi.link.utils.Logger.d((String) "DefaultTempFileManager", sb.toString(), new Object[0]);
                    NanoHTTPD.LOG.log(Level.WARNING, "could not delete file ", e);
                }
            }
            this.b.clear();
        }

        public TempFile createTempFile(String str) throws Exception {
            DefaultTempFile defaultTempFile = new DefaultTempFile(this.a);
            this.b.add(defaultTempFile);
            return defaultTempFile;
        }
    }

    class DefaultTempFileManagerFactory implements TempFileManagerFactory {
        private DefaultTempFileManagerFactory() {
        }

        public TempFileManager create() {
            return new DefaultTempFileManager();
        }
    }

    public class HTTPSession implements IHTTPSession {
        private final TempFileManager b;
        private final OutputStream c;
        private final BufferedInputStream d;
        private int e;
        private int f;
        private String g;
        private Method h;
        private Map<String, String> i;
        private Map<String, String> j;
        private CookieHandler k;
        private String l;
        private String m;
        private String n;
        private String o;
        private boolean p;
        private HttpProgresser q;
        private int r = 0;

        public HTTPSession(TempFileManager tempFileManager, InputStream inputStream, OutputStream outputStream, InetAddress inetAddress) {
            this.b = tempFileManager;
            this.d = new BufferedInputStream(inputStream, 8192);
            this.c = outputStream;
            this.m = (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) ? "127.0.0.1" : inetAddress.getHostAddress().toString();
            this.n = (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) ? "localhost" : inetAddress.getHostName().toString();
            this.j = new HashMap();
        }

        private void a(BufferedReader bufferedReader, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) throws ResponseException {
            String str;
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    StringTokenizer stringTokenizer = new StringTokenizer(readLine);
                    if (!stringTokenizer.hasMoreTokens()) {
                        com.autonavi.link.utils.Logger.d((String) "decodeHeader", (String) "111----hasMoreTokens-------> ", new Object[0]);
                        throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
                    }
                    map.put("method", stringTokenizer.nextToken());
                    if (!stringTokenizer.hasMoreTokens()) {
                        com.autonavi.link.utils.Logger.d((String) "decodeHeader", (String) "222----hasMoreTokens-------> ", new Object[0]);
                        throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
                    }
                    String nextToken = stringTokenizer.nextToken();
                    int indexOf = nextToken.indexOf(63);
                    if (indexOf >= 0) {
                        a(nextToken.substring(indexOf + 1), map2);
                        str = NanoHTTPD.decodePercent(nextToken.substring(0, indexOf));
                    } else {
                        str = NanoHTTPD.decodePercent(nextToken);
                    }
                    if (stringTokenizer.hasMoreTokens()) {
                        this.o = stringTokenizer.nextToken();
                    } else {
                        this.o = "HTTP/1.1";
                        NanoHTTPD.LOG.log(Level.FINE, "no protocol version specified, strange. Assuming HTTP/1.1.");
                    }
                    String readLine2 = bufferedReader.readLine();
                    while (readLine2 != null && !readLine2.trim().isEmpty()) {
                        int indexOf2 = readLine2.indexOf(58);
                        if (indexOf2 >= 0) {
                            map3.put(readLine2.substring(0, indexOf2).trim().toLowerCase(Locale.US), readLine2.substring(indexOf2 + 1).trim());
                        }
                        readLine2 = bufferedReader.readLine();
                    }
                    map.put("uri", str);
                }
            } catch (IOException e2) {
                com.autonavi.link.utils.Logger.d((String) "decodeHeader", e2.getMessage(), new Object[0]);
                Status status = Status.INTERNAL_ERROR;
                StringBuilder sb = new StringBuilder("SERVER INTERNAL ERROR: IOException: ");
                sb.append(e2.getMessage());
                throw new ResponseException(status, sb.toString(), e2);
            }
        }

        private int a(byte[] bArr, int i2) {
            while (bArr[i2] != 10) {
                i2++;
            }
            return i2 + 1;
        }

        private void a(String str, Map<String, String> map) {
            if (str == null) {
                this.l = "";
                return;
            }
            this.l = str;
            StringTokenizer stringTokenizer = new StringTokenizer(str, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken();
                int indexOf = nextToken.indexOf(61);
                if (indexOf >= 0) {
                    map.put(NanoHTTPD.decodePercent(nextToken.substring(0, indexOf)).trim(), NanoHTTPD.decodePercent(nextToken.substring(indexOf + 1)));
                } else {
                    map.put(NanoHTTPD.decodePercent(nextToken).trim(), "");
                }
            }
        }

        private boolean b(byte[] bArr, int i2) {
            if (i2 > 64) {
                i2 = 64;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr, 0, i2)));
            try {
                boolean checkConnectId = LinkProxy.getInstance().checkConnectId(bufferedReader.readLine());
                try {
                    return checkConnectId;
                } catch (IOException e2) {
                    com.autonavi.link.utils.Logger.d((String) "checkPermissions", e2.getMessage(), new Object[0]);
                    e2.printStackTrace();
                    return checkConnectId;
                }
            } catch (IOException e3) {
                com.autonavi.link.utils.Logger.d((String) "checkPermissions", e3.getMessage(), new Object[0]);
                e3.printStackTrace();
                try {
                } catch (IOException e4) {
                    com.autonavi.link.utils.Logger.d((String) "checkPermissions", e4.getMessage(), new Object[0]);
                    e4.printStackTrace();
                }
                return false;
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException e5) {
                    com.autonavi.link.utils.Logger.d((String) "checkPermissions", e5.getMessage(), new Object[0]);
                    e5.printStackTrace();
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:105:0x0282, code lost:
            r2 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x0360, code lost:
            r2 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:0x037b, code lost:
            r2 = e;
         */
        /* JADX WARNING: Removed duplicated region for block: B:105:0x0282 A[ExcHandler: ResponseException (e com.autonavi.link.protocol.http.NanoHTTPD$ResponseException), Splitter:B:1:0x0002] */
        /* JADX WARNING: Removed duplicated region for block: B:110:0x02b9 A[Catch:{ SSLException -> 0x0264, IOException -> 0x0238, SocketException -> 0x037b, SocketTimeoutException -> 0x0360, ResponseException -> 0x0282, SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282, all -> 0x027f }] */
        /* JADX WARNING: Removed duplicated region for block: B:119:0x0360 A[Catch:{ SSLException -> 0x0264, IOException -> 0x0238, SocketException -> 0x037b, SocketTimeoutException -> 0x0360, ResponseException -> 0x0282, SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282, all -> 0x027f }, ExcHandler: SocketTimeoutException (e java.net.SocketTimeoutException), Splitter:B:1:0x0002] */
        /* JADX WARNING: Removed duplicated region for block: B:122:0x037b A[Catch:{ SSLException -> 0x0264, IOException -> 0x0238, SocketException -> 0x037b, SocketTimeoutException -> 0x0360, ResponseException -> 0x0282, SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282, all -> 0x027f }, ExcHandler: SocketException (e java.net.SocketException), Splitter:B:1:0x0002] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void execute() throws java.io.IOException {
            /*
                r10 = this;
                r0 = 0
                r1 = 0
                java.lang.String r2 = r10.getRemoteIpAddress()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r3 = 1
                if (r2 == 0) goto L_0x0017
                java.lang.String r2 = r10.getRemoteIpAddress()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r4 = "127.0.0.1"
                boolean r2 = r2.equals(r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r2 == 0) goto L_0x0017
                r2 = 1
                goto L_0x0018
            L_0x0017:
                r2 = 0
            L_0x0018:
                boolean r4 = r10.p     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r4 != 0) goto L_0x0056
                if (r2 == 0) goto L_0x0056
                r4 = 64
                byte[] r5 = new byte[r4]     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.BufferedInputStream r6 = r10.d     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r4 = r6.read(r5, r0, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r4 > 0) goto L_0x003b
                java.lang.String r2 = "execute"
                java.lang.String r3 = "111---> NanoHttpd通过该异常关闭连接"
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.utils.Logger.d(r2, r3, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.net.SocketException r2 = new java.net.SocketException     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r3 = "NanoHttpd Shutdown"
                r2.<init>(r3)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                throw r2     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x003b:
                boolean r4 = r10.b(r5, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r4 != 0) goto L_0x0054
                java.lang.String r2 = "execute"
                java.lang.String r3 = "222--->  throw new ResponseException --> permission denied,checkPermissions failed"
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.utils.Logger.d(r2, r3, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$ResponseException r2 = new com.autonavi.link.protocol.http.NanoHTTPD$ResponseException     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$Response$Status r3 = com.autonavi.link.protocol.http.NanoHTTPD.Response.Status.FORBIDDEN     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r4 = "permission denied,checkPermissions failed"
                r2.<init>(r3, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                throw r2     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x0054:
                r10.p = r3     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x0056:
                r4 = 8192(0x2000, float:1.14794E-41)
                byte[] r5 = new byte[r4]     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r10.e = r0     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r10.f = r0     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.BufferedInputStream r6 = r10.d     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r6.mark(r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.BufferedInputStream r6 = r10.d     // Catch:{ SSLException -> 0x0264, IOException -> 0x0238, SocketException -> 0x037b, SocketTimeoutException -> 0x0360, ResponseException -> 0x0282 }
                int r6 = r6.read(r5, r0, r4)     // Catch:{ SSLException -> 0x0264, IOException -> 0x0238, SocketException -> 0x037b, SocketTimeoutException -> 0x0360, ResponseException -> 0x0282 }
                r7 = -1
                if (r6 != r7) goto L_0x0087
                java.lang.String r2 = "execute"
                java.lang.String r3 = "555---> read == -1"
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.utils.Logger.d(r2, r3, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.BufferedInputStream r2 = r10.d     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r2)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.OutputStream r2 = r10.c     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r2)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.net.SocketException r2 = new java.net.SocketException     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r3 = "NanoHttpd Shutdown"
                r2.<init>(r3)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                throw r2     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x0087:
                if (r6 <= 0) goto L_0x00a7
                int r7 = r10.f     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r7 = r7 + r6
                r10.f = r7     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r6 = r10.f     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r6 = r10.c(r5, r6)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r10.e = r6     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r6 = r10.e     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r6 > 0) goto L_0x00a7
                java.io.BufferedInputStream r6 = r10.d     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r7 = r10.f     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r8 = r10.f     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r8 = 8192 - r8
                int r6 = r6.read(r5, r7, r8)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                goto L_0x0087
            L_0x00a7:
                int r4 = r10.e     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r6 = r10.f     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r4 >= r6) goto L_0x00ba
                java.io.BufferedInputStream r4 = r10.d     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.reset()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.BufferedInputStream r4 = r10.d     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r6 = r10.e     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                long r6 = (long) r6     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.skip(r6)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x00ba:
                java.util.HashMap r4 = new java.util.HashMap     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.<init>()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r10.i = r4     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.util.Map<java.lang.String, java.lang.String> r4 = r10.j     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r4 != 0) goto L_0x00cd
                java.util.HashMap r4 = new java.util.HashMap     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.<init>()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r10.j = r4     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                goto L_0x00d2
            L_0x00cd:
                java.util.Map<java.lang.String, java.lang.String> r4 = r10.j     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.clear()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x00d2:
                java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.ByteArrayInputStream r7 = new java.io.ByteArrayInputStream     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                int r8 = r10.f     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r7.<init>(r5, r0, r8)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r6.<init>(r7)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.<init>(r6)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.util.HashMap r5 = new java.util.HashMap     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r5.<init>()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.util.Map<java.lang.String, java.lang.String> r6 = r10.i     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.util.Map<java.lang.String, java.lang.String> r7 = r10.j     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r10.a(r4, r5, r6, r7)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r4 = r10.m     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r4 == 0) goto L_0x0105
                java.util.Map<java.lang.String, java.lang.String> r4 = r10.j     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r6 = "remote-addr"
                java.lang.String r7 = r10.m     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.put(r6, r7)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.util.Map<java.lang.String, java.lang.String> r4 = r10.j     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r6 = "http-client-ip"
                java.lang.String r7 = r10.m     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.put(r6, r7)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x0105:
                java.lang.String r4 = "method"
                java.lang.Object r4 = r5.get(r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r4 = (java.lang.String) r4     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$Method r4 = com.autonavi.link.protocol.http.NanoHTTPD.Method.a(r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r10.h = r4     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$Method r4 = r10.h     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r4 != 0) goto L_0x0143
                java.lang.String r2 = "execute"
                java.lang.String r3 = "666---> this.method == null"
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.utils.Logger.d(r2, r3, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$ResponseException r2 = new com.autonavi.link.protocol.http.NanoHTTPD$ResponseException     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$Response$Status r3 = com.autonavi.link.protocol.http.NanoHTTPD.Response.Status.BAD_REQUEST     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r6 = "BAD REQUEST: Syntax error. HTTP verb "
                r4.<init>(r6)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r6 = "method"
                java.lang.Object r5 = r5.get(r6)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r5 = (java.lang.String) r5     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.append(r5)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r5 = " unhandled."
                r4.append(r5)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r4 = r4.toString()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r2.<init>(r3, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                throw r2     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x0143:
                java.lang.String r4 = "uri"
                java.lang.Object r4 = r5.get(r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r4 = (java.lang.String) r4     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r10.g = r4     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$CookieHandler r4 = new com.autonavi.link.protocol.http.NanoHTTPD$CookieHandler     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD r5 = com.autonavi.link.protocol.http.NanoHTTPD.this     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.util.Map<java.lang.String, java.lang.String> r6 = r10.j     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.<init>(r6)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r10.k = r4     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.util.Map<java.lang.String, java.lang.String> r4 = r10.j     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r5 = "connection"
                java.lang.Object r4 = r4.get(r5)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r4 = (java.lang.String) r4     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r5 = "HTTP/1.1"
                java.lang.String r6 = r10.o     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                boolean r5 = r5.equals(r6)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r5 == 0) goto L_0x0179
                if (r4 == 0) goto L_0x0177
                java.lang.String r5 = "(?i).*close.*"
                boolean r4 = r4.matches(r5)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r4 != 0) goto L_0x0179
            L_0x0177:
                r4 = 1
                goto L_0x017a
            L_0x0179:
                r4 = 0
            L_0x017a:
                if (r2 != 0) goto L_0x01a5
                java.util.Map<java.lang.String, java.lang.String> r2 = r10.i     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r5 = "connectionId"
                java.lang.Object r2 = r2.get(r5)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r2 = (java.lang.String) r2     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.connect.a.a r5 = com.autonavi.link.connect.a.a.a()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                boolean r2 = r5.a(r2)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r2 != 0) goto L_0x01a3
                java.lang.String r2 = "execute"
                java.lang.String r3 = "777---> permission denied,invalid connectionId"
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.utils.Logger.d(r2, r3, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$ResponseException r2 = new com.autonavi.link.protocol.http.NanoHTTPD$ResponseException     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$Response$Status r3 = com.autonavi.link.protocol.http.NanoHTTPD.Response.Status.FORBIDDEN     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r4 = "permission denied,invalid connectionId"
                r2.<init>(r3, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                throw r2     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x01a3:
                r10.p = r3     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x01a5:
                com.autonavi.link.protocol.http.NanoHTTPD r2 = com.autonavi.link.protocol.http.NanoHTTPD.this     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD$Response r2 = r2.serve(r10)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                if (r2 != 0) goto L_0x01e2
                java.lang.String r1 = "execute"
                java.lang.String r3 = "888---> SERVER INTERNAL ERROR: Serve() returned a null response."
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                com.autonavi.link.utils.Logger.d(r1, r3, r4)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                com.autonavi.link.protocol.http.NanoHTTPD$ResponseException r1 = new com.autonavi.link.protocol.http.NanoHTTPD$ResponseException     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                com.autonavi.link.protocol.http.NanoHTTPD$Response$Status r3 = com.autonavi.link.protocol.http.NanoHTTPD.Response.Status.INTERNAL_ERROR     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                java.lang.String r4 = "SERVER INTERNAL ERROR: Serve() returned a null response."
                r1.<init>(r3, r4)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                throw r1     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
            L_0x01c0:
                r0 = move-exception
                r1 = r2
                goto L_0x0396
            L_0x01c4:
                r1 = move-exception
                r9 = r2
                r2 = r1
                r1 = r9
                goto L_0x0283
            L_0x01ca:
                r1 = move-exception
                r9 = r2
                r2 = r1
                r1 = r9
                goto L_0x02e0
            L_0x01d0:
                r1 = move-exception
                r9 = r2
                r2 = r1
                r1 = r9
                goto L_0x0320
            L_0x01d6:
                r1 = move-exception
                r9 = r2
                r2 = r1
                r1 = r9
                goto L_0x0361
            L_0x01dc:
                r1 = move-exception
                r9 = r2
                r2 = r1
                r1 = r9
                goto L_0x037c
            L_0x01e2:
                java.util.Map<java.lang.String, java.lang.String> r1 = r10.j     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                java.lang.String r5 = "accept-encoding"
                java.lang.Object r1 = r1.get(r5)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                java.lang.String r1 = (java.lang.String) r1     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                com.autonavi.link.protocol.http.NanoHTTPD$CookieHandler r5 = r10.k     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                r5.unloadQueue(r2)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                com.autonavi.link.protocol.http.NanoHTTPD$Method r5 = r10.h     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                r2.setRequestMethod(r5)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                com.autonavi.link.protocol.http.NanoHTTPD r5 = com.autonavi.link.protocol.http.NanoHTTPD.this     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                boolean r5 = r5.useGzipWhenAccepted(r2)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                if (r5 == 0) goto L_0x0209
                if (r1 == 0) goto L_0x0209
                java.lang.String r5 = "gzip"
                boolean r1 = r1.contains(r5)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                if (r1 == 0) goto L_0x0209
                goto L_0x020a
            L_0x0209:
                r3 = 0
            L_0x020a:
                r2.setGzipEncoding(r3)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                r2.setKeepAlive(r4)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                java.io.OutputStream r1 = r10.c     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                r2.a(r1)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                if (r4 == 0) goto L_0x0227
                boolean r1 = r2.isCloseConnection()     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                if (r1 == 0) goto L_0x021e
                goto L_0x0227
            L_0x021e:
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r2)
            L_0x0221:
                com.autonavi.link.protocol.http.NanoHTTPD$TempFileManager r0 = r10.b
                r0.clear()
                return
            L_0x0227:
                java.lang.String r1 = "execute"
                java.lang.String r3 = "999---> NanoHttpd Shutdown"
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                com.autonavi.link.utils.Logger.d(r1, r3, r4)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                java.net.SocketException r1 = new java.net.SocketException     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                java.lang.String r3 = "NanoHttpd Shutdown"
                r1.<init>(r3)     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
                throw r1     // Catch:{ SocketException -> 0x01dc, SocketTimeoutException -> 0x01d6, SSLException -> 0x01d0, IOException -> 0x01ca, ResponseException -> 0x01c4, all -> 0x01c0 }
            L_0x0238:
                r2 = move-exception
                java.lang.String r3 = "execute"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r5 = "444---> "
                r4.<init>(r5)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r2 = r2.getMessage()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.append(r2)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r2 = r4.toString()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.utils.Logger.d(r3, r2, r4)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.BufferedInputStream r2 = r10.d     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r2)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.io.OutputStream r2 = r10.c     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r2)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.net.SocketException r2 = new java.net.SocketException     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r3 = "NanoHttpd Shutdown"
                r2.<init>(r3)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                throw r2     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x0264:
                r2 = move-exception
                java.lang.String r3 = "execute"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r5 = "333---> "
                r4.<init>(r5)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r5 = r2.getMessage()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                r4.append(r5)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.String r4 = r4.toString()     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                java.lang.Object[] r5 = new java.lang.Object[r0]     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                com.autonavi.link.utils.Logger.d(r3, r4, r5)     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
                throw r2     // Catch:{ SocketException -> 0x037b, SocketTimeoutException -> 0x0360, SSLException -> 0x031f, IOException -> 0x02df, ResponseException -> 0x0282 }
            L_0x027f:
                r0 = move-exception
                goto L_0x0396
            L_0x0282:
                r2 = move-exception
            L_0x0283:
                java.lang.String r3 = "execute"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
                java.lang.String r5 = "eee---> "
                r4.<init>(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x027f }
                r4.append(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x027f }
                java.lang.Object[] r5 = new java.lang.Object[r0]     // Catch:{ all -> 0x027f }
                com.autonavi.link.utils.Logger.d(r3, r4, r5)     // Catch:{ all -> 0x027f }
                com.autonavi.link.protocol.http.NanoHTTPD$Response$Status r3 = r2.getStatus()     // Catch:{ all -> 0x027f }
                java.lang.String r4 = "text/plain"
                java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x027f }
                com.autonavi.link.protocol.http.NanoHTTPD$Response r3 = com.autonavi.link.protocol.http.NanoHTTPD.newFixedLengthResponse(r3, r4, r5)     // Catch:{ all -> 0x027f }
                java.io.OutputStream r4 = r10.c     // Catch:{ all -> 0x027f }
                r3.a(r4)     // Catch:{ all -> 0x027f }
                java.io.OutputStream r3 = r10.c     // Catch:{ all -> 0x027f }
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r3)     // Catch:{ all -> 0x027f }
                boolean r3 = r10.p     // Catch:{ all -> 0x027f }
                if (r3 != 0) goto L_0x02da
                java.lang.String r3 = "execute"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
                java.lang.String r5 = "fff---> NanoHttpd通过该异常关闭连接"
                r4.<init>(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x027f }
                r4.append(r2)     // Catch:{ all -> 0x027f }
                java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x027f }
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x027f }
                com.autonavi.link.utils.Logger.d(r3, r2, r0)     // Catch:{ all -> 0x027f }
                java.net.SocketException r0 = new java.net.SocketException     // Catch:{ all -> 0x027f }
                java.lang.String r2 = "NanoHttpd Shutdown"
                r0.<init>(r2)     // Catch:{ all -> 0x027f }
                throw r0     // Catch:{ all -> 0x027f }
            L_0x02da:
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r1)
                goto L_0x0221
            L_0x02df:
                r2 = move-exception
            L_0x02e0:
                java.lang.String r3 = "execute"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
                java.lang.String r5 = "ddd---> "
                r4.<init>(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x027f }
                r4.append(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x027f }
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x027f }
                com.autonavi.link.utils.Logger.d(r3, r4, r0)     // Catch:{ all -> 0x027f }
                com.autonavi.link.protocol.http.NanoHTTPD$Response$Status r0 = com.autonavi.link.protocol.http.NanoHTTPD.Response.Status.INTERNAL_ERROR     // Catch:{ all -> 0x027f }
                java.lang.String r3 = "text/plain"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
                java.lang.String r5 = "SERVER INTERNAL ERROR: IOException: "
                r4.<init>(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x027f }
                r4.append(r2)     // Catch:{ all -> 0x027f }
                java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x027f }
                com.autonavi.link.protocol.http.NanoHTTPD$Response r0 = com.autonavi.link.protocol.http.NanoHTTPD.newFixedLengthResponse(r0, r3, r2)     // Catch:{ all -> 0x027f }
                java.io.OutputStream r2 = r10.c     // Catch:{ all -> 0x027f }
                r0.a(r2)     // Catch:{ all -> 0x027f }
                java.io.OutputStream r0 = r10.c     // Catch:{ all -> 0x027f }
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r0)     // Catch:{ all -> 0x027f }
                goto L_0x02da
            L_0x031f:
                r2 = move-exception
            L_0x0320:
                java.lang.String r3 = "execute"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
                java.lang.String r5 = "ccc---> "
                r4.<init>(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x027f }
                r4.append(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x027f }
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x027f }
                com.autonavi.link.utils.Logger.d(r3, r4, r0)     // Catch:{ all -> 0x027f }
                com.autonavi.link.protocol.http.NanoHTTPD$Response$Status r0 = com.autonavi.link.protocol.http.NanoHTTPD.Response.Status.INTERNAL_ERROR     // Catch:{ all -> 0x027f }
                java.lang.String r3 = "text/plain"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
                java.lang.String r5 = "SSL PROTOCOL FAILURE: "
                r4.<init>(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x027f }
                r4.append(r2)     // Catch:{ all -> 0x027f }
                java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x027f }
                com.autonavi.link.protocol.http.NanoHTTPD$Response r0 = com.autonavi.link.protocol.http.NanoHTTPD.newFixedLengthResponse(r0, r3, r2)     // Catch:{ all -> 0x027f }
                java.io.OutputStream r2 = r10.c     // Catch:{ all -> 0x027f }
                r0.a(r2)     // Catch:{ all -> 0x027f }
                java.io.OutputStream r0 = r10.c     // Catch:{ all -> 0x027f }
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r0)     // Catch:{ all -> 0x027f }
                goto L_0x02da
            L_0x0360:
                r2 = move-exception
            L_0x0361:
                java.lang.String r3 = "execute"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
                java.lang.String r5 = "bbb---> "
                r4.<init>(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x027f }
                r4.append(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x027f }
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x027f }
                com.autonavi.link.utils.Logger.d(r3, r4, r0)     // Catch:{ all -> 0x027f }
                throw r2     // Catch:{ all -> 0x027f }
            L_0x037b:
                r2 = move-exception
            L_0x037c:
                java.lang.String r3 = "execute"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
                java.lang.String r5 = "aaa---> "
                r4.<init>(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x027f }
                r4.append(r5)     // Catch:{ all -> 0x027f }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x027f }
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x027f }
                com.autonavi.link.utils.Logger.d(r3, r4, r0)     // Catch:{ all -> 0x027f }
                throw r2     // Catch:{ all -> 0x027f }
            L_0x0396:
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r1)
                com.autonavi.link.protocol.http.NanoHTTPD$TempFileManager r1 = r10.b
                r1.clear()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.protocol.http.NanoHTTPD.HTTPSession.execute():void");
        }

        private int c(byte[] bArr, int i2) {
            int i3 = 0;
            while (true) {
                int i4 = i3 + 1;
                if (i4 >= i2) {
                    return 0;
                }
                if (bArr[i3] == 13 && bArr[i4] == 10) {
                    int i5 = i3 + 3;
                    if (i5 < i2 && bArr[i3 + 2] == 13 && bArr[i5] == 10) {
                        return i3 + 4;
                    }
                }
                if (bArr[i3] == 10 && bArr[i4] == 10) {
                    return i3 + 2;
                }
                i3 = i4;
            }
        }

        private int[] a(byte[] bArr, byte[] bArr2, int[] iArr, int i2) {
            if (i2 < bArr2.length) {
                return null;
            }
            List<Integer> b2 = b(bArr, bArr2, iArr, i2);
            int[] iArr2 = new int[b2.size()];
            for (int i3 = 0; i3 < b2.size(); i3++) {
                iArr2[i3] = b2.get(i3).intValue();
            }
            return iArr2;
        }

        private int[] a(byte[] bArr) {
            int[] iArr = new int[256];
            if (bArr == null) {
                return iArr;
            }
            int length = bArr.length;
            for (int i2 = 0; i2 < 256; i2++) {
                iArr[i2] = length + 1;
            }
            for (int i3 = 0; i3 < length; i3++) {
                iArr[bArr[i3]] = length - i3;
            }
            return iArr;
        }

        private List<Integer> b(byte[] bArr, byte[] bArr2, int[] iArr, int i2) {
            boolean z;
            ArrayList arrayList = new ArrayList();
            if (bArr == null || bArr2 == null) {
                return null;
            }
            int length = bArr2.length;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int i5 = i3 + length;
                if (i5 > i4 + i2) {
                    break;
                }
                int i6 = i3;
                int i7 = 0;
                while (i7 < length) {
                    try {
                        if (bArr2[i7] != bArr[i6]) {
                            break;
                        }
                        i7++;
                        i6++;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (i7 == length) {
                    arrayList.add(Integer.valueOf(i3));
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    i3 = i5;
                } else {
                    i3 += iArr[bArr[i5] & 255];
                }
                if (i3 + length >= i2) {
                    break;
                }
                i4 = i6;
            }
            return arrayList;
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

        public final Map<String, String> getParms() {
            return this.i;
        }

        public String getQueryParameterString() {
            return this.l;
        }

        private RandomAccessFile a() {
            try {
                return new RandomAccessFile(this.b.createTempFile(null).getName(), "rw");
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder("getTmpBucket ");
                sb.append(e2.getMessage());
                com.autonavi.link.utils.Logger.d((String) "getTmpBucket", sb.toString(), new Object[0]);
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

        private String a(ContentType contentType, byte[] bArr, int[] iArr, int i2) throws ResponseException, IOException, Exception {
            try {
                byte[] bArr2 = new byte[1024];
                int i3 = iArr[0];
                int i4 = i2 - i3;
                if (i4 >= 1024) {
                    i4 = 1024;
                }
                System.arraycopy(bArr, i3, bArr2, 0, i4);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr2, 0, i4), Charset.forName(contentType.getEncoding())), i4);
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    if (readLine.contains(contentType.getBoundary())) {
                        String readLine2 = bufferedReader.readLine();
                        String str = null;
                        int i5 = 2;
                        while (readLine2 != null && readLine2.trim().length() > 0) {
                            Matcher matcher = NanoHTTPD.CONTENT_DISPOSITION_PATTERN.matcher(readLine2);
                            if (matcher.matches()) {
                                Matcher matcher2 = NanoHTTPD.CONTENT_DISPOSITION_ATTRIBUTE_PATTERN.matcher(matcher.group(2));
                                while (matcher2.find()) {
                                    if ("name".equalsIgnoreCase(matcher2.group(1))) {
                                        str = matcher2.group(2);
                                    }
                                }
                            }
                            readLine2 = bufferedReader.readLine();
                            i5++;
                        }
                        int i6 = 0;
                        while (true) {
                            int i7 = i5 - 1;
                            if (i5 <= 0) {
                                break;
                            }
                            i6 = a(bArr2, i6);
                            i5 = i7;
                        }
                        if (i6 >= i4 - 4) {
                            com.autonavi.link.utils.Logger.d((String) "getBoundaryData", (String) "Multipart header size exceeds MAX_HEADER_SIZE. ", new Object[0]);
                            throw new ResponseException(Status.INTERNAL_ERROR, "Multipart header size exceeds MAX_HEADER_SIZE.");
                        }
                        this.r = iArr[0] + i6;
                        return str;
                    }
                }
                com.autonavi.link.utils.Logger.d((String) "getBoundaryData", (String) "BAD REQUEST: Content type is multipart/form-data but chunk does not start with boundary. ", new Object[0]);
                throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but chunk does not start with boundary.");
            } catch (ResponseException e2) {
                StringBuilder sb = new StringBuilder("111---> ");
                sb.append(e2.getMessage());
                com.autonavi.link.utils.Logger.d((String) "getBoundaryData", sb.toString(), new Object[0]);
                throw e2;
            } catch (Exception e3) {
                StringBuilder sb2 = new StringBuilder("222---> ");
                sb2.append(e3.getMessage());
                com.autonavi.link.utils.Logger.d((String) "getBoundaryData", sb2.toString(), new Object[0]);
                throw new ResponseException(Status.INTERNAL_ERROR, e3.toString());
            }
        }

        public void parseBody(Map<String, Object> map) throws IOException, ResponseException, Exception {
            parseBody(map, null);
        }

        /* JADX WARNING: type inference failed for: r5v3, types: [java.io.DataOutput, java.lang.Object] */
        /* JADX WARNING: type inference failed for: r4v6, types: [java.io.RandomAccessFile, java.lang.Object] */
        /* JADX WARNING: type inference failed for: r4v7, types: [java.io.RandomAccessFile] */
        /* JADX WARNING: type inference failed for: r5v4 */
        /* JADX WARNING: type inference failed for: r5v5, types: [java.io.DataOutputStream] */
        /* JADX WARNING: type inference failed for: r4v9 */
        /* JADX WARNING: type inference failed for: r4v24 */
        /* JADX WARNING: type inference failed for: r5v38 */
        /* JADX WARNING: type inference failed for: r4v25 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:146:0x034f A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:72:0x02dd A[Catch:{ all -> 0x0362 }] */
        /* JADX WARNING: Removed duplicated region for block: B:77:0x031d A[Catch:{ all -> 0x0362 }] */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void parseBody(java.util.Map<java.lang.String, java.lang.Object> r51, java.util.Map<java.lang.String, java.lang.String> r52) throws java.io.IOException, com.autonavi.link.protocol.http.NanoHTTPD.ResponseException, java.lang.Exception {
            /*
                r50 = this;
                r1 = r50
                r2 = r52
                java.lang.String r3 = "parseBody"
                java.lang.String r4 = "parseBody --> to begin"
                r5 = 0
                java.lang.Object[] r6 = new java.lang.Object[r5]
                com.autonavi.link.utils.Logger.d(r3, r4, r6)
                com.autonavi.link.protocol.http.NanoHTTPD$Method r3 = com.autonavi.link.protocol.http.NanoHTTPD.Method.POST
                com.autonavi.link.protocol.http.NanoHTTPD$Method r4 = r1.h
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x036c
                com.autonavi.link.protocol.http.NanoHTTPD$ContentType r3 = new com.autonavi.link.protocol.http.NanoHTTPD$ContentType
                java.util.Map<java.lang.String, java.lang.String> r14 = r1.j
                java.lang.String r15 = "content-type"
                java.lang.Object r14 = r14.get(r15)
                java.lang.String r14 = (java.lang.String) r14
                r3.<init>(r14)
                boolean r14 = r3.isMultipart()
                if (r14 == 0) goto L_0x036c
                r14 = 20480(0x5000, float:2.8699E-41)
                byte[] r15 = new byte[r14]
                r4 = 40960(0xa000, float:5.7397E-41)
                byte[] r4 = new byte[r4]
                long r17 = r50.getBodySize()
                long r19 = java.lang.System.currentTimeMillis()
                r1.r = r5
                java.lang.String r8 = r3.getBoundary()     // Catch:{ all -> 0x0365 }
                byte[] r8 = r8.getBytes()     // Catch:{ all -> 0x0365 }
                int[] r9 = r1.a(r8)     // Catch:{ all -> 0x0365 }
                long r21 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0365 }
                r28 = r21
                r10 = 0
                r21 = 0
                r23 = 0
                r25 = 0
                r26 = 0
            L_0x005b:
                java.lang.String r11 = "parseBody"
                java.lang.String r6 = "read begin---------------------> ================================"
                java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r11, r6, r7)     // Catch:{ all -> 0x0362 }
                r36 = r3
                r12 = r17
                r2 = r23
                r6 = r25
            L_0x006c:
                if (r6 > r14) goto L_0x00a2
                int r7 = r1.f     // Catch:{ all -> 0x0362 }
                if (r7 <= 0) goto L_0x00a2
                r17 = 0
                int r7 = (r12 > r17 ? 1 : (r12 == r17 ? 0 : -1))
                if (r7 <= 0) goto L_0x00a2
                java.io.BufferedInputStream r7 = r1.d     // Catch:{ all -> 0x0362 }
                r37 = r6
                r5 = 20480(0x5000, double:1.01185E-319)
                long r5 = java.lang.Math.min(r12, r5)     // Catch:{ all -> 0x0362 }
                int r5 = (int) r5     // Catch:{ all -> 0x0362 }
                r6 = 0
                int r5 = r7.read(r15, r6, r5)     // Catch:{ all -> 0x0362 }
                r1.f = r5     // Catch:{ all -> 0x0362 }
                int r5 = r1.f     // Catch:{ all -> 0x0362 }
                int r6 = r1.f     // Catch:{ all -> 0x0362 }
                long r6 = (long) r6     // Catch:{ all -> 0x0362 }
                long r2 = r2 + r6
                int r6 = r1.f     // Catch:{ all -> 0x0362 }
                long r6 = (long) r6     // Catch:{ all -> 0x0362 }
                long r21 = r21 + r6
                int r6 = r1.f     // Catch:{ all -> 0x0362 }
                long r6 = (long) r6     // Catch:{ all -> 0x0362 }
                long r12 = r12 - r6
                r6 = r37
                r7 = 0
                java.lang.System.arraycopy(r15, r7, r4, r6, r5)     // Catch:{ all -> 0x0362 }
                int r6 = r6 + r5
                r5 = 0
                goto L_0x006c
            L_0x00a2:
                java.lang.String r5 = "parseBody"
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0362 }
                java.lang.String r11 = "read begin end----------------> this.rlen->"
                r7.<init>(r11)     // Catch:{ all -> 0x0362 }
                int r11 = r1.f     // Catch:{ all -> 0x0362 }
                r7.append(r11)     // Catch:{ all -> 0x0362 }
                java.lang.String r11 = " , currentBufferReadLength--> "
                r7.append(r11)     // Catch:{ all -> 0x0362 }
                r7.append(r6)     // Catch:{ all -> 0x0362 }
                java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0362 }
                r11 = 0
                java.lang.Object[] r14 = new java.lang.Object[r11]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r5, r7, r14)     // Catch:{ all -> 0x0362 }
                int[] r5 = r1.a(r4, r8, r9, r6)     // Catch:{ all -> 0x0362 }
                int r7 = r5.length     // Catch:{ all -> 0x0362 }
                if (r7 == 0) goto L_0x028b
                java.lang.String r7 = "parseBody"
                java.lang.String r14 = "有boundary"
                r39 = r2
                r11 = 0
                java.lang.Object[] r2 = new java.lang.Object[r11]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r7, r14, r2)     // Catch:{ all -> 0x0362 }
                int r2 = r5.length     // Catch:{ all -> 0x0362 }
                r3 = 1
                if (r2 <= r3) goto L_0x0183
                java.lang.String r2 = "parseBody"
                java.lang.String r7 = "多个boundary"
                r11 = 0
                java.lang.Object[] r14 = new java.lang.Object[r11]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r2, r7, r14)     // Catch:{ all -> 0x0362 }
                if (r10 == 0) goto L_0x0110
                java.lang.String r2 = "parseBody"
                java.lang.String r3 = "randomAccessFile不为null，说明已经有文件写入流了，得将下一个boundary的起始位置之前的数据流写到文件里面 2222222222222"
                java.lang.Object[] r7 = new java.lang.Object[r11]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r2, r3, r7)     // Catch:{ all -> 0x0362 }
                r2 = r5[r11]     // Catch:{ all -> 0x0362 }
                int r3 = r1.r     // Catch:{ all -> 0x0362 }
                int r5 = r2 + -4
                int r7 = r1.r     // Catch:{ all -> 0x0362 }
                int r5 = r5 - r7
                r10.write(r4, r3, r5)     // Catch:{ all -> 0x0362 }
                int r6 = r6 - r2
                r3 = 0
                java.lang.System.arraycopy(r4, r2, r4, r3, r6)     // Catch:{ all -> 0x0362 }
                r1.r = r3     // Catch:{ all -> 0x0362 }
                r25 = r6
                r43 = r8
                r2 = r36
                r41 = r39
                r10 = 0
                r11 = r52
                goto L_0x02be
            L_0x0110:
                java.lang.String r2 = "parseBody"
                java.lang.String r7 = "randomAccessFile为null，说明还没有文件写入流 22222222"
                r11 = 0
                java.lang.Object[] r14 = new java.lang.Object[r11]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r2, r7, r14)     // Catch:{ all -> 0x0362 }
                r2 = r36
                java.lang.String r7 = r1.a(r2, r4, r5, r6)     // Catch:{ all -> 0x0362 }
                r41 = r39
                r11 = r52
                java.lang.Object r7 = r11.get(r7)     // Catch:{ all -> 0x0362 }
                java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x0362 }
                boolean r14 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0362 }
                if (r14 == 0) goto L_0x0142
                java.lang.String r2 = "parseBody"
                java.lang.String r3 = "222 no set file path "
                r4 = 0
                java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r2, r3, r4)     // Catch:{ all -> 0x0362 }
                java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0362 }
                java.lang.String r3 = "no set file path"
                r2.<init>(r3)     // Catch:{ all -> 0x0362 }
                throw r2     // Catch:{ all -> 0x0362 }
            L_0x0142:
                java.io.File r14 = new java.io.File     // Catch:{ all -> 0x0362 }
                r14.<init>(r7)     // Catch:{ all -> 0x0362 }
                java.io.File r17 = r14.getParentFile()     // Catch:{ all -> 0x0362 }
                if (r17 == 0) goto L_0x0154
                java.io.File r14 = r14.getParentFile()     // Catch:{ all -> 0x0362 }
                r14.mkdirs()     // Catch:{ all -> 0x0362 }
            L_0x0154:
                java.io.RandomAccessFile r14 = new java.io.RandomAccessFile     // Catch:{ all -> 0x0362 }
                java.lang.String r3 = "rw"
                r14.<init>(r7, r3)     // Catch:{ all -> 0x0362 }
                r43 = r8
                long r7 = r14.length()     // Catch:{ all -> 0x017e }
                r14.seek(r7)     // Catch:{ all -> 0x017e }
                r3 = 1
                r3 = r5[r3]     // Catch:{ all -> 0x017e }
                int r5 = r1.r     // Catch:{ all -> 0x017e }
                int r7 = r3 + -4
                int r8 = r1.r     // Catch:{ all -> 0x017e }
                int r7 = r7 - r8
                r14.write(r4, r5, r7)     // Catch:{ all -> 0x017e }
                int r6 = r6 - r3
                r5 = 0
                java.lang.System.arraycopy(r4, r3, r4, r5, r6)     // Catch:{ all -> 0x017e }
                r1.r = r5     // Catch:{ all -> 0x017e }
            L_0x0179:
                r25 = r6
                r10 = 0
                goto L_0x02be
            L_0x017e:
                r0 = move-exception
                r2 = r0
                r10 = r14
                goto L_0x0368
            L_0x0183:
                r43 = r8
                r2 = r36
                r41 = r39
                r11 = r52
                java.lang.String r3 = "parseBody"
                java.lang.String r7 = "一个 boundary"
                r8 = 0
                java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r3, r7, r14)     // Catch:{ all -> 0x0362 }
                if (r10 == 0) goto L_0x01f1
                java.lang.String r3 = "parseBody"
                java.lang.String r7 = "randomAccessFile不为null，说明已经有文件写入流了，得将下一个boundary的起始位置之前的数据流写到文件里面 1111111111"
                java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r3, r7, r14)     // Catch:{ all -> 0x0362 }
                r3 = r5[r8]     // Catch:{ all -> 0x0362 }
                java.lang.String r5 = "parseBody"
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0362 }
                java.lang.String r8 = "go----mFileOffset---> "
                r7.<init>(r8)     // Catch:{ all -> 0x0362 }
                int r8 = r1.r     // Catch:{ all -> 0x0362 }
                r7.append(r8)     // Catch:{ all -> 0x0362 }
                java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0362 }
                r8 = 0
                java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r5, r7, r14)     // Catch:{ all -> 0x0362 }
                java.lang.String r5 = "parseBody"
                java.lang.String r7 = "go----boundaryIndex---> "
                java.lang.String r8 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0362 }
                java.lang.String r7 = r7.concat(r8)     // Catch:{ all -> 0x0362 }
                r8 = 0
                java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r5, r7, r14)     // Catch:{ all -> 0x0362 }
                java.lang.String r5 = "parseBody"
                java.lang.String r7 = "go----currentBufferReadLength---> "
                java.lang.String r8 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x0362 }
                java.lang.String r7 = r7.concat(r8)     // Catch:{ all -> 0x0362 }
                r8 = 0
                java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r5, r7, r14)     // Catch:{ all -> 0x0362 }
                int r5 = r1.r     // Catch:{ all -> 0x0362 }
                int r7 = r3 + -4
                int r8 = r1.r     // Catch:{ all -> 0x0362 }
                int r7 = r7 - r8
                r10.write(r4, r5, r7)     // Catch:{ all -> 0x0362 }
                int r6 = r6 - r3
                r5 = 0
                java.lang.System.arraycopy(r4, r3, r4, r5, r6)     // Catch:{ all -> 0x0362 }
                r1.r = r5     // Catch:{ all -> 0x0362 }
                goto L_0x0179
            L_0x01f1:
                java.lang.String r3 = "parseBody"
                java.lang.String r7 = "randomAccessFile为null，说明还没有文件写入流 111111111"
                r8 = 0
                java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r3, r7, r14)     // Catch:{ all -> 0x0362 }
                java.lang.String r3 = "haha"
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0362 }
                java.lang.String r8 = "size--> "
                r7.<init>(r8)     // Catch:{ all -> 0x0362 }
                r7.append(r12)     // Catch:{ all -> 0x0362 }
                java.lang.String r8 = " , currentBufferReadLength--> "
                r7.append(r8)     // Catch:{ all -> 0x0362 }
                r7.append(r6)     // Catch:{ all -> 0x0362 }
                java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0362 }
                r8 = 0
                java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r3, r7, r14)     // Catch:{ all -> 0x0362 }
                r7 = 0
                int r3 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
                if (r3 != 0) goto L_0x0221
                r6 = 0
            L_0x0221:
                if (r6 == 0) goto L_0x02bc
                java.lang.String r3 = r1.a(r2, r4, r5, r6)     // Catch:{ all -> 0x0362 }
                java.lang.Object r3 = r11.get(r3)     // Catch:{ all -> 0x0362 }
                java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0362 }
                boolean r5 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x0362 }
                if (r5 == 0) goto L_0x0245
                java.lang.String r2 = "parseBody"
                java.lang.String r3 = "222 no set file path "
                r4 = 0
                java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r2, r3, r4)     // Catch:{ all -> 0x0362 }
                java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0362 }
                java.lang.String r3 = "no set file path"
                r2.<init>(r3)     // Catch:{ all -> 0x0362 }
                throw r2     // Catch:{ all -> 0x0362 }
            L_0x0245:
                java.io.File r5 = new java.io.File     // Catch:{ all -> 0x0362 }
                r5.<init>(r3)     // Catch:{ all -> 0x0362 }
                java.io.File r7 = r5.getParentFile()     // Catch:{ all -> 0x0362 }
                if (r7 == 0) goto L_0x0257
                java.io.File r5 = r5.getParentFile()     // Catch:{ all -> 0x0362 }
                r5.mkdirs()     // Catch:{ all -> 0x0362 }
            L_0x0257:
                java.io.RandomAccessFile r5 = new java.io.RandomAccessFile     // Catch:{ all -> 0x0362 }
                java.lang.String r7 = "rw"
                r5.<init>(r3, r7)     // Catch:{ all -> 0x0362 }
                long r7 = r5.length()     // Catch:{ all -> 0x0286 }
                r5.seek(r7)     // Catch:{ all -> 0x0286 }
                int r3 = r6 + -30
                int r7 = r1.r     // Catch:{ all -> 0x0286 }
                int r7 = r3 - r7
                if (r7 <= 0) goto L_0x0280
                int r6 = r1.r     // Catch:{ all -> 0x0286 }
                int r7 = r3 - r6
                r5.write(r4, r6, r7)     // Catch:{ all -> 0x0286 }
                r6 = 0
                r7 = 30
                java.lang.System.arraycopy(r4, r3, r4, r6, r7)     // Catch:{ all -> 0x0286 }
                r1.r = r6     // Catch:{ all -> 0x0286 }
                r38 = 30
                goto L_0x0282
            L_0x0280:
                r38 = r6
            L_0x0282:
                r10 = r5
                r25 = r38
                goto L_0x02be
            L_0x0286:
                r0 = move-exception
                r2 = r0
                r10 = r5
                goto L_0x0368
            L_0x028b:
                r41 = r2
                r43 = r8
                r2 = r36
                r11 = r52
                java.lang.String r3 = "parseBody"
                java.lang.String r5 = "没有boundary-->randomAccessFile---> "
                java.lang.String r7 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x0362 }
                java.lang.String r5 = r5.concat(r7)     // Catch:{ all -> 0x0362 }
                r7 = 0
                java.lang.Object[] r8 = new java.lang.Object[r7]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r3, r5, r8)     // Catch:{ all -> 0x0362 }
                if (r10 == 0) goto L_0x02bc
                int r6 = r6 + -30
                int r3 = r1.r     // Catch:{ all -> 0x0362 }
                int r5 = r6 - r3
                r10.write(r4, r3, r5)     // Catch:{ all -> 0x0362 }
                r3 = 0
                r5 = 30
                java.lang.System.arraycopy(r4, r6, r4, r3, r5)     // Catch:{ all -> 0x0362 }
                r1.r = r3     // Catch:{ all -> 0x0362 }
                r25 = 30
                goto L_0x02be
            L_0x02bc:
                r25 = r6
            L_0x02be:
                java.lang.String r3 = "parseBody"
                java.lang.String r5 = "end---------------------> ================================readNum--> "
                r6 = r41
                java.lang.String r8 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x0362 }
                java.lang.String r5 = r5.concat(r8)     // Catch:{ all -> 0x0362 }
                r8 = 0
                java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ all -> 0x0362 }
                com.autonavi.link.utils.Logger.d(r3, r5, r14)     // Catch:{ all -> 0x0362 }
                long r17 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0362 }
                r3 = 0
                long r17 = r17 - r28
                com.autonavi.link.protocol.http.HttpProgresser r3 = r1.q     // Catch:{ all -> 0x0362 }
                if (r3 == 0) goto L_0x0315
                r23 = 1000(0x3e8, double:4.94E-321)
                int r3 = (r17 > r23 ? 1 : (r17 == r23 ? 0 : -1))
                if (r3 <= 0) goto L_0x0315
                long r17 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0362 }
                com.autonavi.link.protocol.http.HttpProgresser r3 = r1.q     // Catch:{ all -> 0x0362 }
                r44 = r4
                double r4 = (double) r6     // Catch:{ all -> 0x0362 }
                long r23 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0362 }
                r8 = 0
                r45 = r9
                long r8 = r23 - r19
                double r8 = (double) r8     // Catch:{ all -> 0x0362 }
                r23 = 4652218415073722368(0x4090000000000000, double:1024.0)
                double r8 = r8 * r23
                double r4 = r4 / r8
                r8 = 4652007308841189376(0x408f400000000000, double:1000.0)
                double r4 = r4 * r8
                float r4 = (float) r4     // Catch:{ all -> 0x0362 }
                r30 = r3
                r31 = r21
                r33 = r6
                r35 = r4
                r30.onProgress(r31, r33, r35)     // Catch:{ all -> 0x0362 }
                r28 = r17
                r31 = r21
                r21 = 0
                goto L_0x031b
            L_0x0315:
                r44 = r4
                r45 = r9
                r31 = r26
            L_0x031b:
                if (r25 != 0) goto L_0x034f
                r3 = 0
                int r5 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
                if (r5 == 0) goto L_0x0324
                goto L_0x034f
            L_0x0324:
                if (r5 != 0) goto L_0x034b
                com.autonavi.link.protocol.http.HttpProgresser r2 = r1.q     // Catch:{ all -> 0x0362 }
                if (r2 == 0) goto L_0x034b
                com.autonavi.link.protocol.http.HttpProgresser r2 = r1.q     // Catch:{ all -> 0x0362 }
                double r3 = (double) r6     // Catch:{ all -> 0x0362 }
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0362 }
                r5 = 0
                long r8 = r8 - r19
                double r8 = (double) r8     // Catch:{ all -> 0x0362 }
                r11 = 4652218415073722368(0x4090000000000000, double:1024.0)
                double r8 = r8 * r11
                double r3 = r3 / r8
                r8 = 4652007308841189376(0x408f400000000000, double:1000.0)
                double r3 = r3 * r8
                float r3 = (float) r3     // Catch:{ all -> 0x0362 }
                r30 = r2
                r33 = r6
                r35 = r3
                r30.onProgress(r31, r33, r35)     // Catch:{ all -> 0x0362 }
            L_0x034b:
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r10)
                return
            L_0x034f:
                r3 = r2
                r23 = r6
                r2 = r11
                r17 = r12
                r26 = r31
                r8 = r43
                r4 = r44
                r9 = r45
                r5 = 0
                r14 = 20480(0x5000, float:2.8699E-41)
                goto L_0x005b
            L_0x0362:
                r0 = move-exception
                r2 = r0
                goto L_0x0368
            L_0x0365:
                r0 = move-exception
                r2 = r0
                r10 = 0
            L_0x0368:
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r10)
                throw r2
            L_0x036c:
                long r2 = r50.getBodySize()     // Catch:{ all -> 0x04b3 }
                r4 = 1024(0x400, double:5.06E-321)
                int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r4 >= 0) goto L_0x0383
                java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x04b3 }
                r4.<init>()     // Catch:{ all -> 0x04b3 }
                java.io.DataOutputStream r5 = new java.io.DataOutputStream     // Catch:{ all -> 0x04b3 }
                r5.<init>(r4)     // Catch:{ all -> 0x04b3 }
                r6 = r4
                r4 = 0
                goto L_0x0389
            L_0x0383:
                java.io.RandomAccessFile r4 = r50.a()     // Catch:{ all -> 0x04b3 }
                r5 = r4
                r6 = 0
            L_0x0389:
                r7 = 512(0x200, float:7.175E-43)
                byte[] r7 = new byte[r7]     // Catch:{ all -> 0x04b0 }
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04b0 }
                long r10 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04b0 }
                r13 = 0
            L_0x0397:
                int r12 = r1.f     // Catch:{ all -> 0x04b0 }
                if (r12 < 0) goto L_0x040a
                r15 = 0
                int r12 = (r2 > r15 ? 1 : (r2 == r15 ? 0 : -1))
                if (r12 <= 0) goto L_0x040a
                java.io.BufferedInputStream r12 = r1.d     // Catch:{ all -> 0x04b0 }
                r46 = r8
                r8 = 512(0x200, double:2.53E-321)
                long r8 = java.lang.Math.min(r2, r8)     // Catch:{ all -> 0x04b0 }
                int r8 = (int) r8     // Catch:{ all -> 0x04b0 }
                r9 = 0
                int r8 = r12.read(r7, r9, r8)     // Catch:{ all -> 0x04b0 }
                r1.f = r8     // Catch:{ all -> 0x04b0 }
                int r8 = r1.f     // Catch:{ all -> 0x04b0 }
                long r8 = (long) r8     // Catch:{ all -> 0x04b0 }
                long r2 = r2 - r8
                int r8 = r1.f     // Catch:{ all -> 0x04b0 }
                if (r8 <= 0) goto L_0x03c1
                int r8 = r1.f     // Catch:{ all -> 0x04b0 }
                r9 = 0
                r5.write(r7, r9, r8)     // Catch:{ all -> 0x04b0 }
            L_0x03c1:
                int r8 = r1.f     // Catch:{ all -> 0x04b0 }
                long r8 = (long) r8     // Catch:{ all -> 0x04b0 }
                long r8 = r8 + r13
                long r12 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04b0 }
                r14 = 0
                long r12 = r12 - r10
                com.autonavi.link.protocol.http.HttpProgresser r14 = r1.q     // Catch:{ all -> 0x04b0 }
                if (r14 == 0) goto L_0x0400
                r21 = 1000(0x3e8, double:4.94E-321)
                int r12 = (r12 > r21 ? 1 : (r12 == r21 ? 0 : -1))
                if (r12 <= 0) goto L_0x03fd
                long r10 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04b0 }
                com.autonavi.link.protocol.http.HttpProgresser r15 = r1.q     // Catch:{ all -> 0x04b0 }
                r16 = 0
                double r12 = (double) r8     // Catch:{ all -> 0x04b0 }
                long r18 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04b0 }
                r14 = 0
                r48 = r2
                long r2 = r18 - r46
                double r2 = (double) r2     // Catch:{ all -> 0x04b0 }
                r18 = 4652218415073722368(0x4090000000000000, double:1024.0)
                double r2 = r2 * r18
                double r12 = r12 / r2
                r2 = 4652007308841189376(0x408f400000000000, double:1000.0)
                double r12 = r12 * r2
                float r2 = (float) r12     // Catch:{ all -> 0x04b0 }
                r18 = r8
                r20 = r2
                r15.onProgress(r16, r18, r20)     // Catch:{ all -> 0x04b0 }
                goto L_0x0404
            L_0x03fd:
                r48 = r2
                goto L_0x0404
            L_0x0400:
                r48 = r2
                r21 = 1000(0x3e8, double:4.94E-321)
            L_0x0404:
                r13 = r8
                r8 = r46
                r2 = r48
                goto L_0x0397
            L_0x040a:
                r46 = r8
                r7 = 0
                int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
                if (r2 != 0) goto L_0x0433
                com.autonavi.link.protocol.http.HttpProgresser r2 = r1.q     // Catch:{ all -> 0x04b0 }
                if (r2 == 0) goto L_0x0433
                com.autonavi.link.protocol.http.HttpProgresser r10 = r1.q     // Catch:{ all -> 0x04b0 }
                r11 = 0
                double r2 = (double) r13     // Catch:{ all -> 0x04b0 }
                long r7 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04b0 }
                r9 = 0
                long r7 = r7 - r46
                double r7 = (double) r7     // Catch:{ all -> 0x04b0 }
                r15 = 4652218415073722368(0x4090000000000000, double:1024.0)
                double r7 = r7 * r15
                double r2 = r2 / r7
                r7 = 4652007308841189376(0x408f400000000000, double:1000.0)
                double r2 = r2 * r7
                float r15 = (float) r2     // Catch:{ all -> 0x04b0 }
                r10.onProgress(r11, r13, r15)     // Catch:{ all -> 0x04b0 }
            L_0x0433:
                if (r6 == 0) goto L_0x0443
                byte[] r2 = r6.toByteArray()     // Catch:{ all -> 0x04b0 }
                int r3 = r6.size()     // Catch:{ all -> 0x04b0 }
                r6 = 0
                java.nio.ByteBuffer r2 = java.nio.ByteBuffer.wrap(r2, r6, r3)     // Catch:{ all -> 0x04b0 }
                goto L_0x0458
            L_0x0443:
                java.nio.channels.FileChannel r6 = r4.getChannel()     // Catch:{ all -> 0x04b0 }
                java.nio.channels.FileChannel$MapMode r7 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ all -> 0x04b0 }
                r8 = 0
                long r10 = r4.length()     // Catch:{ all -> 0x04b0 }
                java.nio.MappedByteBuffer r2 = r6.map(r7, r8, r10)     // Catch:{ all -> 0x04b0 }
                r6 = 0
                r4.seek(r6)     // Catch:{ all -> 0x04b0 }
            L_0x0458:
                com.autonavi.link.protocol.http.NanoHTTPD$Method r3 = com.autonavi.link.protocol.http.NanoHTTPD.Method.POST     // Catch:{ all -> 0x04b0 }
                com.autonavi.link.protocol.http.NanoHTTPD$Method r6 = r1.h     // Catch:{ all -> 0x04b0 }
                boolean r3 = r3.equals(r6)     // Catch:{ all -> 0x04b0 }
                if (r3 == 0) goto L_0x04a9
                com.autonavi.link.protocol.http.NanoHTTPD$ContentType r3 = new com.autonavi.link.protocol.http.NanoHTTPD$ContentType     // Catch:{ all -> 0x04b0 }
                java.util.Map<java.lang.String, java.lang.String> r6 = r1.j     // Catch:{ all -> 0x04b0 }
                java.lang.String r7 = "content-type"
                java.lang.Object r6 = r6.get(r7)     // Catch:{ all -> 0x04b0 }
                java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x04b0 }
                r3.<init>(r6)     // Catch:{ all -> 0x04b0 }
                boolean r6 = r3.isMultipart()     // Catch:{ all -> 0x04b0 }
                if (r6 != 0) goto L_0x04a9
                int r6 = r2.remaining()     // Catch:{ all -> 0x04b0 }
                byte[] r6 = new byte[r6]     // Catch:{ all -> 0x04b0 }
                r2.get(r6)     // Catch:{ all -> 0x04b0 }
                java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x04b0 }
                java.lang.String r7 = r3.getEncoding()     // Catch:{ all -> 0x04b0 }
                r2.<init>(r6, r7)     // Catch:{ all -> 0x04b0 }
                java.lang.String r2 = r2.trim()     // Catch:{ all -> 0x04b0 }
                java.lang.String r7 = "application/x-www-form-urlencoded"
                java.lang.String r3 = r3.getContentType()     // Catch:{ all -> 0x04b0 }
                boolean r3 = r7.equalsIgnoreCase(r3)     // Catch:{ all -> 0x04b0 }
                if (r3 == 0) goto L_0x049f
                java.util.Map<java.lang.String, java.lang.String> r3 = r1.i     // Catch:{ all -> 0x04b0 }
                r1.a(r2, r3)     // Catch:{ all -> 0x04b0 }
                goto L_0x04a9
            L_0x049f:
                int r2 = r6.length     // Catch:{ all -> 0x04b0 }
                if (r2 == 0) goto L_0x04a9
                java.lang.String r2 = "rawContent"
                r3 = r51
                r3.put(r2, r6)     // Catch:{ all -> 0x04b0 }
            L_0x04a9:
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r4)
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r5)
                return
            L_0x04b0:
                r0 = move-exception
                r2 = r0
                goto L_0x04b7
            L_0x04b3:
                r0 = move-exception
                r2 = r0
                r4 = 0
                r5 = 0
            L_0x04b7:
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r4)
                com.autonavi.link.protocol.http.NanoHTTPD.safeClose(r5)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.protocol.http.NanoHTTPD.HTTPSession.parseBody(java.util.Map, java.util.Map):void");
        }

        public String getRemoteIpAddress() {
            return this.m;
        }

        public String getRemoteHostName() {
            return this.n;
        }

        public void setHttpProgresser(HttpProgresser httpProgresser) {
            this.q = httpProgresser;
        }
    }

    public interface IHTTPSession {
        public static final String POST_RAW_CONTENT = "rawContent";

        void execute() throws IOException;

        CookieHandler getCookies();

        Map<String, String> getHeaders();

        InputStream getInputStream();

        Method getMethod();

        Map<String, String> getParms();

        String getQueryParameterString();

        String getRemoteHostName();

        String getRemoteIpAddress();

        String getUri();

        void parseBody(Map<String, Object> map) throws IOException, ResponseException, Exception;

        void parseBody(Map<String, Object> map, Map<String, String> map2) throws IOException, ResponseException, Exception;

        void setHttpProgresser(HttpProgresser httpProgresser);
    }

    public enum Method {
        GET,
        PUT,
        POST,
        DELETE,
        HEAD,
        OPTIONS,
        TRACE,
        CONNECT,
        PATCH,
        PROPFIND,
        PROPPATCH,
        MKCOL,
        MOVE,
        COPY,
        LOCK,
        UNLOCK;

        static Method a(String str) {
            if (str == null) {
                return null;
            }
            try {
                return valueOf(str);
            } catch (IllegalArgumentException e) {
                com.autonavi.link.utils.Logger.d((String) "Method", e.getMessage(), new Object[0]);
                return null;
            }
        }
    }

    public static class Response implements Closeable {
        private HttpResponseFinishCallback a;
        private IStatus b;
        private String c;
        private InputStream d;
        private long e;
        private final Map<String, String> f = new HashMap<String, String>() {
            public String put(String str, String str2) {
                Response.this.g.put(str == null ? str : str.toLowerCase(), str2);
                return (String) super.put(str, str2);
            }
        };
        /* access modifiers changed from: private */
        public final Map<String, String> g = new HashMap();
        private Method h;
        private boolean i;
        private boolean j;
        private boolean k;

        static class ChunkedOutputStream extends FilterOutputStream {
            public ChunkedOutputStream(OutputStream outputStream) {
                super(outputStream);
            }

            public void write(int i) throws IOException {
                write(new byte[]{(byte) i}, 0, 1);
            }

            public void write(byte[] bArr) throws IOException {
                write(bArr, 0, bArr.length);
            }

            public void write(byte[] bArr, int i, int i2) throws IOException {
                if (i2 != 0) {
                    this.out.write(String.format("%x\r\n", new Object[]{Integer.valueOf(i2)}).getBytes());
                    this.out.write(bArr, i, i2);
                    this.out.write(MultipartUtility.LINE_FEED.getBytes());
                }
            }

            public void finish() throws IOException {
                this.out.write("0\r\n\r\n".getBytes());
            }
        }

        public interface HttpResponseFinishCallback {
            void onResponseFinished();
        }

        public interface IStatus {
            String getDescription();
        }

        public enum Status implements IStatus {
            SWITCH_PROTOCOL(101, "Switching Protocols"),
            OK(200, "OK"),
            CREATED(201, "Created"),
            ACCEPTED(202, "Accepted"),
            NO_CONTENT(204, "No Content"),
            PARTIAL_CONTENT(206, "Partial Content"),
            MULTI_STATUS(207, "Multi-Status"),
            REDIRECT(301, "Moved Permanently"),
            REDIRECT_SEE_OTHER(303, "See Other"),
            NOT_MODIFIED(304, "Not Modified"),
            BAD_REQUEST(400, "Bad Request"),
            UNAUTHORIZED(401, "Unauthorized"),
            FORBIDDEN(403, "Forbidden"),
            NOT_FOUND(404, "Not Found"),
            METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
            NOT_ACCEPTABLE(406, "Not Acceptable"),
            REQUEST_TIMEOUT(408, "Request Timeout"),
            CONFLICT(H5ErrorCode.HTTP_CONFLICT, "Conflict"),
            RANGE_NOT_SATISFIABLE(NBNetStatus.SC_HTTP_RANGE_NOT_SATISFIABLE, "Requested Range Not Satisfiable"),
            INTERNAL_ERROR(500, "Internal Server Error"),
            NOT_IMPLEMENTED(501, "Not Implemented"),
            UNSUPPORTED_HTTP_VERSION(505, "HTTP Version Not Supported");
            
            private final int a;
            private final String b;

            private Status(int i, String str) {
                this.a = i;
                this.b = str;
            }

            public final String getDescription() {
                StringBuilder sb = new StringBuilder();
                sb.append(this.a);
                sb.append(Token.SEPARATOR);
                sb.append(this.b);
                return sb.toString();
            }
        }

        public void setHttpResponseFinishCallback(HttpResponseFinishCallback httpResponseFinishCallback) {
            this.a = httpResponseFinishCallback;
        }

        protected Response(IStatus iStatus, String str, InputStream inputStream, long j2) {
            this.b = iStatus;
            this.c = str;
            boolean z = false;
            if (inputStream == null) {
                this.d = new ByteArrayInputStream(new byte[0]);
                this.e = 0;
            } else {
                this.d = inputStream;
                this.e = j2;
            }
            this.i = this.e < 0 ? true : z;
            this.k = true;
        }

        public void close() throws IOException {
            if (this.d != null) {
                this.d.close();
            }
        }

        public void addHeader(String str, String str2) {
            this.f.put(str, str2);
        }

        public void closeConnection(boolean z) {
            if (z) {
                this.f.put("connection", DataflowMonitorModel.METHOD_NAME_CLOSE);
            } else {
                this.f.remove("connection");
            }
        }

        public boolean isCloseConnection() {
            return DataflowMonitorModel.METHOD_NAME_CLOSE.equals(getHeader("connection"));
        }

        public InputStream getData() {
            return this.d;
        }

        public String getHeader(String str) {
            return this.g.get(str.toLowerCase());
        }

        public String getMimeType() {
            return this.c;
        }

        public Method getRequestMethod() {
            return this.h;
        }

        public IStatus getStatus() {
            return this.b;
        }

        public void setGzipEncoding(boolean z) {
            this.j = z;
        }

        public void setKeepAlive(boolean z) {
            this.k = z;
        }

        /* access modifiers changed from: protected */
        public void a(OutputStream outputStream) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                if (this.b == null) {
                    com.autonavi.link.utils.Logger.d((String) DataflowMonitorModel.METHOD_NAME_SEND, (String) "sendResponse(): Status can't be null.", new Object[0]);
                    throw new Error("sendResponse(): Status can't be null.");
                }
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, new ContentType(this.c).getEncoding())), false);
                printWriter.append("HTTP/1.1 ").append(this.b.getDescription()).append(" \r\n");
                if (this.c != null) {
                    a(printWriter, "Content-Type", this.c);
                }
                if (getHeader("date") == null) {
                    a(printWriter, AutoDownloadLogRequest.AUTO_KEY_DATE, simpleDateFormat.format(new Date()));
                }
                for (Entry next : this.f.entrySet()) {
                    a(printWriter, (String) next.getKey(), (String) next.getValue());
                }
                if (getHeader("connection") == null) {
                    a(printWriter, H5AppHttpRequest.HEADER_CONNECTION, this.k ? "keep-alive" : DataflowMonitorModel.METHOD_NAME_CLOSE);
                }
                if (getHeader("content-length") != null) {
                    this.j = false;
                }
                if (this.j) {
                    a(printWriter, TransportConstants.KEY_X_CONTENT_ENCODING, "gzip");
                    setChunkedTransfer(true);
                }
                long j2 = this.d != null ? this.e : 0;
                if (this.h != Method.HEAD && this.i) {
                    a(printWriter, "Transfer-Encoding", "chunked");
                } else if (!this.j) {
                    j2 = a(printWriter, j2);
                }
                printWriter.append(MultipartUtility.LINE_FEED);
                printWriter.flush();
                a(outputStream, j2);
                outputStream.flush();
                NanoHTTPD.safeClose(this.d);
                if (this.a != null) {
                    this.a.onResponseFinished();
                    this.a = null;
                }
            } catch (IOException e2) {
                this.a = null;
                StringBuilder sb = new StringBuilder("IOException() --> ");
                sb.append(e2.getMessage());
                com.autonavi.link.utils.Logger.d((String) DataflowMonitorModel.METHOD_NAME_SEND, sb.toString(), new Object[0]);
                NanoHTTPD.LOG.log(Level.SEVERE, "Could not send response to the client", e2);
            }
        }

        /* access modifiers changed from: protected */
        public void a(PrintWriter printWriter, String str, String str2) {
            printWriter.append(str).append(": ").append(str2).append(MultipartUtility.LINE_FEED);
        }

        /* access modifiers changed from: protected */
        public long a(PrintWriter printWriter, long j2) {
            String header = getHeader("content-length");
            if (header != null) {
                try {
                    j2 = Long.parseLong(header);
                } catch (NumberFormatException unused) {
                    NanoHTTPD.LOG.severe("content-length was no number ".concat(String.valueOf(header)));
                }
            }
            StringBuilder sb = new StringBuilder("Content-Length: ");
            sb.append(j2);
            sb.append(MultipartUtility.LINE_FEED);
            printWriter.print(sb.toString());
            return j2;
        }

        private void a(OutputStream outputStream, long j2) throws IOException {
            if (this.h == Method.HEAD || !this.i) {
                b(outputStream, j2);
                return;
            }
            ChunkedOutputStream chunkedOutputStream = new ChunkedOutputStream(outputStream);
            b(chunkedOutputStream, -1);
            chunkedOutputStream.finish();
        }

        private void b(OutputStream outputStream, long j2) throws IOException {
            if (this.j) {
                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(outputStream);
                c(gZIPOutputStream, -1);
                gZIPOutputStream.finish();
                return;
            }
            c(outputStream, j2);
        }

        private void c(OutputStream outputStream, long j2) throws IOException {
            byte[] bArr = new byte[16384];
            boolean z = j2 == -1;
            while (true) {
                if (j2 > 0 || z) {
                    long j3 = 16384;
                    if (!z) {
                        j3 = Math.min(j2, 16384);
                    }
                    int read = this.d.read(bArr, 0, (int) j3);
                    if (read > 0) {
                        outputStream.write(bArr, 0, read);
                        if (!z) {
                            j2 -= (long) read;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        public void setChunkedTransfer(boolean z) {
            this.i = z;
        }

        public void setData(InputStream inputStream) {
            this.d = inputStream;
        }

        public void setMimeType(String str) {
            this.c = str;
        }

        public void setRequestMethod(Method method) {
            this.h = method;
        }

        public void setStatus(IStatus iStatus) {
            this.b = iStatus;
        }
    }

    public static final class ResponseException extends Exception {
        private final Status a;

        public ResponseException(Status status, String str) {
            super(str);
            this.a = status;
        }

        public ResponseException(Status status, String str, Exception exc) {
            super(str, exc);
            this.a = status;
        }

        public final Status getStatus() {
            return this.a;
        }
    }

    public static class SecureServerSocketFactory implements ServerSocketFactory {
        private SSLServerSocketFactory a;
        private String[] b;

        public SecureServerSocketFactory(SSLServerSocketFactory sSLServerSocketFactory, String[] strArr) {
            this.a = sSLServerSocketFactory;
            this.b = strArr;
        }

        public ServerSocket create() throws IOException {
            SSLServerSocket sSLServerSocket = (SSLServerSocket) this.a.createServerSocket();
            if (this.b != null) {
                sSLServerSocket.setEnabledProtocols(this.b);
            } else {
                sSLServerSocket.setEnabledProtocols(sSLServerSocket.getSupportedProtocols());
            }
            sSLServerSocket.setUseClientMode(false);
            sSLServerSocket.setWantClientAuth(false);
            sSLServerSocket.setNeedClientAuth(false);
            return sSLServerSocket;
        }
    }

    public class ServerRunnable implements Runnable {
        private final int b;
        /* access modifiers changed from: private */
        public IOException c;
        /* access modifiers changed from: private */
        public boolean d;

        private ServerRunnable(int i) {
            this.d = false;
            this.b = i;
        }

        public void run() {
            try {
                NanoHTTPD.this.myServerSocket.bind(NanoHTTPD.this.hostname != null ? new InetSocketAddress(NanoHTTPD.this.hostname, NanoHTTPD.this.myPort) : new InetSocketAddress(NanoHTTPD.this.myPort));
                this.d = true;
                do {
                    try {
                        Socket accept = NanoHTTPD.this.myServerSocket.accept();
                        new StringBuilder("accept socket: ").append(accept);
                        if (this.b > 0) {
                            accept.setSoTimeout(this.b);
                        }
                        NanoHTTPD.this.asyncRunner.exec(NanoHTTPD.this.createClientHandler(accept, accept.getInputStream()));
                    } catch (IOException e) {
                        StringBuilder sb = new StringBuilder("222 IOException() Communication with the client broken--> ");
                        sb.append(e.getMessage());
                        com.autonavi.link.utils.Logger.d((String) "ServerRunnable", sb.toString(), new Object[0]);
                        NanoHTTPD.LOG.log(Level.FINE, "Communication with the client broken", e);
                    }
                } while (!NanoHTTPD.this.myServerSocket.isClosed());
            } catch (IOException e2) {
                StringBuilder sb2 = new StringBuilder("111 IOException() --> ");
                sb2.append(e2.getMessage());
                com.autonavi.link.utils.Logger.d((String) "ServerRunnable", sb2.toString(), new Object[0]);
                this.c = e2;
            }
        }
    }

    public interface ServerSocketFactory {
        ServerSocket create() throws IOException;
    }

    public interface TempFile {
        void delete() throws Exception;

        String getName();
    }

    public interface TempFileManager {
        void clear();

        TempFile createTempFile(String str) throws Exception;
    }

    public interface TempFileManagerFactory {
        TempFileManager create();
    }

    public static Map<String, String> mimeTypes() {
        if (MIME_TYPES == null) {
            HashMap hashMap = new HashMap();
            MIME_TYPES = hashMap;
            loadMimeTypes(hashMap, "META-INF/nanohttpd/default-mimetypes.properties");
            loadMimeTypes(MIME_TYPES, "META-INF/nanohttpd/mimetypes.properties");
            if (MIME_TYPES.isEmpty()) {
                LOG.log(Level.WARNING, "no mime types found in the classpath! please provide mimetypes.properties");
            }
        }
        return MIME_TYPES;
    }

    private static void loadMimeTypes(Map<String, String> map, String str) {
        Object obj;
        IOException e;
        try {
            Enumeration<URL> resources = NanoHTTPD.class.getClassLoader().getResources(str);
            while (resources.hasMoreElements()) {
                URL nextElement = resources.nextElement();
                Properties properties = new Properties();
                try {
                    obj = nextElement.openStream();
                    try {
                        properties.load(nextElement.openStream());
                    } catch (IOException e2) {
                        e = e2;
                        try {
                            StringBuilder sb = new StringBuilder("IOException --> ");
                            sb.append(e.getMessage());
                            com.autonavi.link.utils.Logger.d((String) "loadMimeTypes", sb.toString(), new Object[0]);
                            LOG.log(Level.SEVERE, "could not load mimetypes from ".concat(String.valueOf(nextElement)), e);
                            safeClose(obj);
                            map.putAll(properties);
                        } catch (Throwable th) {
                            th = th;
                        }
                    }
                } catch (IOException e3) {
                    IOException iOException = e3;
                    obj = null;
                    e = iOException;
                    StringBuilder sb2 = new StringBuilder("IOException --> ");
                    sb2.append(e.getMessage());
                    com.autonavi.link.utils.Logger.d((String) "loadMimeTypes", sb2.toString(), new Object[0]);
                    LOG.log(Level.SEVERE, "could not load mimetypes from ".concat(String.valueOf(nextElement)), e);
                    safeClose(obj);
                    map.putAll(properties);
                } catch (Throwable th2) {
                    th = th2;
                    obj = null;
                    safeClose(obj);
                    throw th;
                }
                safeClose(obj);
                map.putAll(properties);
            }
        } catch (IOException e4) {
            StringBuilder sb3 = new StringBuilder("IOException -no mime types available at -> ");
            sb3.append(e4.getMessage());
            com.autonavi.link.utils.Logger.d((String) "loadMimeTypes", sb3.toString(), new Object[0]);
            LOG.log(Level.INFO, "no mime types available at ".concat(String.valueOf(str)));
        }
    }

    public static SSLServerSocketFactory makeSSLSocketFactory(KeyStore keyStore, KeyManager[] keyManagerArr) throws IOException {
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance.init(keyStore);
            SSLContext instance2 = SSLContext.getInstance("TLS");
            instance2.init(keyManagerArr, instance.getTrustManagers(), null);
            return instance2.getServerSocketFactory();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("makeSSLSocketFactory  Exception -> ");
            sb.append(e.getMessage());
            com.autonavi.link.utils.Logger.d((String) "makeSSLSocketFactory", sb.toString(), new Object[0]);
            throw new IOException(e.getMessage());
        }
    }

    public static SSLServerSocketFactory makeSSLSocketFactory(KeyStore keyStore, KeyManagerFactory keyManagerFactory) throws IOException {
        try {
            return makeSSLSocketFactory(keyStore, keyManagerFactory.getKeyManagers());
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("aaa  Exception -> ");
            sb.append(e.getMessage());
            com.autonavi.link.utils.Logger.d((String) "makeSSLSocketFactory", sb.toString(), new Object[0]);
            throw new IOException(e.getMessage());
        }
    }

    public static SSLServerSocketFactory makeSSLSocketFactory(String str, char[] cArr) throws IOException {
        try {
            KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream resourceAsStream = NanoHTTPD.class.getResourceAsStream(str);
            if (resourceAsStream == null) {
                com.autonavi.link.utils.Logger.d((String) "makeSSLSocketFactory", (String) "keystoreStream == null", new Object[0]);
                throw new IOException("Unable to load keystore from classpath: ".concat(String.valueOf(str)));
            }
            instance.load(resourceAsStream, cArr);
            KeyManagerFactory instance2 = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            instance2.init(instance, cArr);
            return makeSSLSocketFactory(instance, instance2);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Exception ");
            sb.append(e.getMessage());
            com.autonavi.link.utils.Logger.d((String) "makeSSLSocketFactory", sb.toString(), new Object[0]);
            throw new IOException(e.getMessage());
        }
    }

    public static String getMimeTypeForFile(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        String str2 = lastIndexOf >= 0 ? mimeTypes().get(str.substring(lastIndexOf + 1).toLowerCase()) : null;
        return str2 == null ? FilePart.DEFAULT_CONTENT_TYPE : str2;
    }

    /* access modifiers changed from: private */
    public static final void safeClose(Object obj) {
        if (obj != null) {
            try {
                if (obj instanceof Closeable) {
                    ((Closeable) obj).close();
                } else if (obj instanceof Socket) {
                    ((Socket) obj).close();
                } else if (obj instanceof ServerSocket) {
                    ((ServerSocket) obj).close();
                } else {
                    com.autonavi.link.utils.Logger.d((String) "safeClose", (String) "Unknown object to close", new Object[0]);
                    throw new IllegalArgumentException("Unknown object to close");
                }
            } catch (IOException e) {
                com.autonavi.link.utils.Logger.d((String) "safeClose", (String) "IOException Could not close", new Object[0]);
                LOG.log(Level.SEVERE, "Could not close", e);
            }
        }
    }

    public NanoHTTPD(int i) {
        this(null, i);
    }

    public NanoHTTPD(String str, int i) {
        this.serverSocketFactory = new DefaultServerSocketFactory();
        this.hostname = str;
        this.myPort = i;
        setTempFileManagerFactory(new DefaultTempFileManagerFactory());
        setAsyncRunner(new DefaultAsyncRunner());
    }

    public synchronized void closeAllConnections() {
        stop();
    }

    /* access modifiers changed from: protected */
    public ClientHandler createClientHandler(Socket socket, InputStream inputStream) {
        return new ClientHandler(inputStream, socket);
    }

    /* access modifiers changed from: protected */
    public ServerRunnable createServerRunnable(int i) {
        return new ServerRunnable(i);
    }

    protected static Map<String, List<String>> decodeParameters(Map<String, String> map) {
        return decodeParameters(map.get(QUERY_STRING_PARAMETER));
    }

    protected static Map<String, List<String>> decodeParameters(String str) {
        HashMap hashMap = new HashMap();
        if (str != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken();
                int indexOf = nextToken.indexOf(61);
                String trim = (indexOf >= 0 ? decodePercent(nextToken.substring(0, indexOf)) : decodePercent(nextToken)).trim();
                if (!hashMap.containsKey(trim)) {
                    hashMap.put(trim, new ArrayList());
                }
                String decodePercent = indexOf >= 0 ? decodePercent(nextToken.substring(indexOf + 1)) : null;
                if (decodePercent != null) {
                    ((List) hashMap.get(trim)).add(decodePercent);
                }
            }
        }
        return hashMap;
    }

    protected static String decodePercent(String str) {
        try {
            return URLDecoder.decode(str, "UTF8");
        } catch (UnsupportedEncodingException e) {
            com.autonavi.link.utils.Logger.d((String) "decodePercent", (String) "UnsupportedEncodingException UnsupportedEncodingException", new Object[0]);
            LOG.log(Level.WARNING, "Encoding not supported, ignored", e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public boolean useGzipWhenAccepted(Response response) {
        return response.getMimeType() != null && response.getMimeType().toLowerCase().contains("text/");
    }

    public final int getListeningPort() {
        if (this.myServerSocket == null) {
            return -1;
        }
        return this.myServerSocket.getLocalPort();
    }

    public final boolean isAlive() {
        return wasStarted() && !this.myServerSocket.isClosed() && this.myThread.isAlive();
    }

    public ServerSocketFactory getServerSocketFactory() {
        return this.serverSocketFactory;
    }

    public void setServerSocketFactory(ServerSocketFactory serverSocketFactory2) {
        this.serverSocketFactory = serverSocketFactory2;
    }

    public String getHostname() {
        return this.hostname;
    }

    public TempFileManagerFactory getTempFileManagerFactory() {
        return this.tempFileManagerFactory;
    }

    public void makeSecure(SSLServerSocketFactory sSLServerSocketFactory, String[] strArr) {
        this.serverSocketFactory = new SecureServerSocketFactory(sSLServerSocketFactory, strArr);
    }

    public static Response newChunkedResponse(IStatus iStatus, String str, InputStream inputStream) {
        Response response = new Response(iStatus, str, inputStream, -1);
        return response;
    }

    public static Response newFixedLengthResponse(IStatus iStatus, String str, InputStream inputStream, long j) {
        com.autonavi.link.utils.Logger.d((String) "newFixedLengthResponse", (String) "newFixedLengthResponse 111", new Object[0]);
        Response response = new Response(iStatus, str, inputStream, j);
        return response;
    }

    public static Response newFixedLengthResponse(IStatus iStatus, String str, String str2) {
        byte[] bArr;
        ContentType contentType = new ContentType(str);
        if (str2 == null) {
            com.autonavi.link.utils.Logger.d((String) "newFixedLengthResponse", (String) "newFixedLengthResponse 222", new Object[0]);
            return newFixedLengthResponse(iStatus, str, new ByteArrayInputStream(new byte[0]), 0);
        }
        try {
            if (!Charset.forName(contentType.getEncoding()).newEncoder().canEncode(str2)) {
                contentType = contentType.tryUTF8();
            }
            bArr = str2.getBytes(contentType.getEncoding());
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb = new StringBuilder("newFixedLengthResponse 333 ");
            sb.append(e.getMessage());
            com.autonavi.link.utils.Logger.d((String) "newFixedLengthResponse", sb.toString(), new Object[0]);
            LOG.log(Level.SEVERE, "encoding problem, responding nothing", e);
            bArr = new byte[0];
        }
        return newFixedLengthResponse(iStatus, contentType.getContentTypeHeader(), new ByteArrayInputStream(bArr), (long) bArr.length);
    }

    public static Response newFixedLengthResponse(String str) {
        com.autonavi.link.utils.Logger.d((String) "newFixedLengthResponse", "newFixedLengthResponse 444 ".concat(String.valueOf(str)), new Object[0]);
        return newFixedLengthResponse(Status.OK, "text/html", str);
    }

    public Response serve(IHTTPSession iHTTPSession) {
        HashMap hashMap = new HashMap();
        Method method = iHTTPSession.getMethod();
        if (Method.PUT.equals(method) || Method.POST.equals(method)) {
            try {
                iHTTPSession.parseBody(hashMap, null);
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder("111 IOException ");
                sb.append(e.getMessage());
                com.autonavi.link.utils.Logger.d((String) "serve", sb.toString(), new Object[0]);
                Status status = Status.INTERNAL_ERROR;
                StringBuilder sb2 = new StringBuilder("SERVER INTERNAL ERROR: IOException: ");
                sb2.append(e.getMessage());
                return newFixedLengthResponse(status, "text/plain", sb2.toString());
            } catch (ResponseException e2) {
                StringBuilder sb3 = new StringBuilder("222 ResponseException ");
                sb3.append(e2.getMessage());
                com.autonavi.link.utils.Logger.d((String) "serve", sb3.toString(), new Object[0]);
                return newFixedLengthResponse(e2.getStatus(), "text/plain", e2.getMessage());
            } catch (Exception e3) {
                StringBuilder sb4 = new StringBuilder("333 Exception ");
                sb4.append(e3.getMessage());
                com.autonavi.link.utils.Logger.d((String) "serve", sb4.toString(), new Object[0]);
                return newFixedLengthResponse(Status.INTERNAL_ERROR, "text/plain", e3.getMessage());
            }
        }
        Map<String, String> parms = iHTTPSession.getParms();
        parms.put(QUERY_STRING_PARAMETER, iHTTPSession.getQueryParameterString());
        return serve(iHTTPSession.getUri(), method, iHTTPSession.getHeaders(), parms, hashMap);
    }

    @Deprecated
    public Response serve(String str, Method method, Map<String, String> map, Map<String, String> map2, Map<String, Object> map3) {
        com.autonavi.link.utils.Logger.d((String) "serve", (String) "555 Not Found ", new Object[0]);
        return newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "Not Found");
    }

    public void setAsyncRunner(AsyncRunner asyncRunner2) {
        this.asyncRunner = asyncRunner2;
    }

    public void setTempFileManagerFactory(TempFileManagerFactory tempFileManagerFactory2) {
        this.tempFileManagerFactory = tempFileManagerFactory2;
    }

    public void start() throws IOException {
        start(30000);
    }

    public void start(int i) throws IOException {
        start(i, true);
    }

    public void start(int i, boolean z) throws IOException {
        this.myServerSocket = getServerSocketFactory().create();
        this.myServerSocket.setReuseAddress(true);
        ServerRunnable createServerRunnable = createServerRunnable(i);
        this.myThread = new Thread(createServerRunnable);
        this.myThread.setDaemon(z);
        this.myThread.setName("NanoHttpd Main Listener");
        this.myThread.start();
        while (!createServerRunnable.d && createServerRunnable.c == null) {
            try {
                Thread.sleep(10);
            } catch (Throwable th) {
                StringBuilder sb = new StringBuilder("Throwable--> ");
                sb.append(th.getMessage());
                com.autonavi.link.utils.Logger.d((String) H5PageData.KEY_UC_START, sb.toString(), new Object[0]);
            }
        }
        if (createServerRunnable.c != null) {
            com.autonavi.link.utils.Logger.d((String) H5PageData.KEY_UC_START, (String) "serverRunnable.bindException != null--> ", new Object[0]);
            throw createServerRunnable.c;
        }
    }

    public void stop() {
        try {
            com.autonavi.link.utils.Logger.d((String) AudioUtils.CMDSTOP, (String) "http--> stop", new Object[0]);
            safeClose(this.myServerSocket);
            this.asyncRunner.closeAll();
            if (this.myThread != null) {
                this.myThread.interrupt();
                this.myThread = null;
                com.autonavi.link.utils.Logger.d((String) AudioUtils.CMDSTOP, (String) "http--> stop--> this.myThread = null;", new Object[0]);
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Could not stop all connections --> ");
            sb.append(e.getMessage());
            com.autonavi.link.utils.Logger.d((String) AudioUtils.CMDSTOP, sb.toString(), new Object[0]);
            LOG.log(Level.SEVERE, "Could not stop all connections", e);
        }
    }

    public final boolean wasStarted() {
        return (this.myServerSocket == null || this.myThread == null) ? false : true;
    }

    public void cancelReceivingData() {
        try {
            this.asyncRunner.closeAll();
        } catch (Exception e) {
            new StringBuilder("cancelReceivingData--> ").append(e);
            e.printStackTrace();
        }
    }
}
