package org.nanohttpd.protocols.http;

import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.alipay.multimedia.common.logging.MLog;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import org.nanohttpd.protocols.http.response.IStatus;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;
import org.nanohttpd.protocols.http.sockets.DefaultServerSocketFactory;
import org.nanohttpd.protocols.http.sockets.SecureServerSocketFactory;
import org.nanohttpd.protocols.http.tempfiles.DefaultTempFileManagerFactory;
import org.nanohttpd.protocols.http.tempfiles.ITempFileManager;
import org.nanohttpd.protocols.http.threading.DefaultAsyncRunner;
import org.nanohttpd.protocols.http.threading.IAsyncRunner;
import org.nanohttpd.util.IFactory;
import org.nanohttpd.util.IFactoryThrowing;
import org.nanohttpd.util.IHandler;

public abstract class NanoHTTPD {
    public static final Pattern CONTENT_DISPOSITION_ATTRIBUTE_PATTERN = Pattern.compile(CONTENT_DISPOSITION_ATTRIBUTE_REGEX);
    public static final String CONTENT_DISPOSITION_ATTRIBUTE_REGEX = "[ |\t]*([a-zA-Z]*)[ |\t]*=[ |\t]*['|\"]([^\"^']*)['|\"]";
    public static final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile(CONTENT_DISPOSITION_REGEX, 2);
    public static final String CONTENT_DISPOSITION_REGEX = "([ |\t]*Content-Disposition[ |\t]*:)(.*)";
    public static final Pattern CONTENT_TYPE_PATTERN = Pattern.compile(CONTENT_TYPE_REGEX, 2);
    public static final String CONTENT_TYPE_REGEX = "([ |\t]*content-type[ |\t]*:)(.*)";
    public static final Logger LOG = Logger.getLogger(NanoHTTPD.class.getName());
    public static final String MIME_HTML = "text/html";
    public static final String MIME_PLAINTEXT = "text/plain";
    protected static Map<String, String> MIME_TYPES = null;
    private static final String QUERY_STRING_PARAMETER = "NanoHttpd.QUERY_STRING";
    public static final int SOCKET_READ_TIMEOUT = 5000;
    private static final String TAG = "NanoHTTPD";
    protected IAsyncRunner asyncRunner;
    public final String hostname;
    private IHandler<IHTTPSession, Response> httpHandler;
    protected List<IHandler<IHTTPSession, Response>> interceptors;
    public final int myPort;
    private volatile ServerSocket myServerSocket;
    private Thread myThread;
    private IFactoryThrowing<ServerSocket, IOException> serverSocketFactory;
    private IFactory<ITempFileManager> tempFileManagerFactory;

    public static final class ResponseException extends Exception {
        private static final long serialVersionUID = 6569838532917408380L;
        private final Status a;

        public ResponseException(Status status, String message) {
            super(message);
            this.a = status;
        }

        public ResponseException(Status status, String message, Exception e) {
            super(message, e);
            this.a = status;
        }

        public final Status getStatus() {
            return this.a;
        }
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

    /* JADX INFO: finally extract failed */
    private static void loadMimeTypes(Map<String, String> result, String resourceName) {
        try {
            Enumeration resources = NanoHTTPD.class.getClassLoader().getResources(resourceName);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                Properties properties = new Properties();
                InputStream stream = null;
                try {
                    stream = url.openStream();
                    properties.load(stream);
                    safeClose(stream);
                } catch (IOException e) {
                    LOG.log(Level.SEVERE, "could not load mimetypes from " + url, e);
                    safeClose(stream);
                } catch (Throwable th) {
                    safeClose(stream);
                    throw th;
                }
                result.putAll(properties);
            }
        } catch (IOException e2) {
            LOG.log(Level.INFO, "no mime types available at " + resourceName);
        }
    }

    public static SSLServerSocketFactory makeSSLSocketFactory(KeyStore loadedKeyStore, KeyManager[] keyManagers) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(loadedKeyStore);
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(keyManagers, trustManagerFactory.getTrustManagers(), null);
            return ctx.getServerSocketFactory();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public static SSLServerSocketFactory makeSSLSocketFactory(KeyStore loadedKeyStore, KeyManagerFactory loadedKeyFactory) {
        try {
            return makeSSLSocketFactory(loadedKeyStore, loadedKeyFactory.getKeyManagers());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public static SSLServerSocketFactory makeSSLSocketFactory(String keyAndTrustStoreClasspathPath, char[] passphrase) {
        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream keystoreStream = NanoHTTPD.class.getResourceAsStream(keyAndTrustStoreClasspathPath);
            if (keystoreStream == null) {
                throw new IOException("Unable to load keystore from classpath: " + keyAndTrustStoreClasspathPath);
            }
            keystore.load(keystoreStream, passphrase);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keystore, passphrase);
            return makeSSLSocketFactory(keystore, keyManagerFactory);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public static String getMimeTypeForFile(String uri) {
        int dot = uri.lastIndexOf(46);
        String mime = null;
        if (dot >= 0) {
            mime = mimeTypes().get(uri.substring(dot + 1).toLowerCase());
        }
        return mime == null ? FilePart.DEFAULT_CONTENT_TYPE : mime;
    }

    public static final void safeClose(Object closeable) {
        if (closeable != null) {
            try {
                if (closeable instanceof Closeable) {
                    ((Closeable) closeable).close();
                } else if (closeable instanceof Socket) {
                    ((Socket) closeable).close();
                } else if (closeable instanceof ServerSocket) {
                    ((ServerSocket) closeable).close();
                } else {
                    throw new IllegalArgumentException("Unknown object to close");
                }
            } catch (IOException e) {
                MLog.e(TAG, "Could not close.e=" + e);
            }
        }
    }

    public ServerSocket getMyServerSocket() {
        return this.myServerSocket;
    }

    public NanoHTTPD(int port) {
        this(null, port);
    }

    public NanoHTTPD(String hostname2, int port) {
        this.serverSocketFactory = new DefaultServerSocketFactory();
        this.interceptors = new ArrayList(4);
        this.hostname = hostname2;
        this.myPort = port;
        setTempFileManagerFactory(new DefaultTempFileManagerFactory());
        setAsyncRunner(new DefaultAsyncRunner());
        this.httpHandler = new IHandler<IHTTPSession, Response>() {
            public final Response handle(IHTTPSession input) {
                return NanoHTTPD.this.serve(input);
            }
        };
    }

    public void setHTTPHandler(IHandler<IHTTPSession, Response> handler) {
        this.httpHandler = handler;
    }

    public void addHTTPInterceptor(IHandler<IHTTPSession, Response> interceptor) {
        this.interceptors.add(interceptor);
    }

    public synchronized void closeAllConnections() {
        stop();
    }

    /* access modifiers changed from: protected */
    public ClientHandler createClientHandler(Socket finalAccept, InputStream inputStream) {
        return new ClientHandler(this, inputStream, finalAccept);
    }

    /* access modifiers changed from: protected */
    public ServerRunnable createServerRunnable(int timeout) {
        return new ServerRunnable(this, timeout);
    }

    protected static Map<String, List<String>> decodeParameters(Map<String, String> parms) {
        return decodeParameters(parms.get(QUERY_STRING_PARAMETER));
    }

    protected static Map<String, List<String>> decodeParameters(String queryString) {
        Map parms = new HashMap();
        if (queryString != null) {
            StringTokenizer st = new StringTokenizer(queryString, "&");
            while (st.hasMoreTokens()) {
                String e = st.nextToken();
                int sep = e.indexOf(61);
                String propertyName = sep >= 0 ? decodePercent(e.substring(0, sep)).trim() : decodePercent(e).trim();
                if (!parms.containsKey(propertyName)) {
                    parms.put(propertyName, new ArrayList());
                }
                String propertyValue = sep >= 0 ? decodePercent(e.substring(sep + 1)) : null;
                if (propertyValue != null) {
                    ((List) parms.get(propertyName)).add(propertyValue);
                }
            }
        }
        return parms;
    }

    public static String decodePercent(String str) {
        String decoded = null;
        try {
            return URLDecoder.decode(str, "UTF8");
        } catch (UnsupportedEncodingException ignored) {
            MLog.e(TAG, "Encoding not supported, ignored:" + ignored);
            return decoded;
        }
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

    public IFactoryThrowing<ServerSocket, IOException> getServerSocketFactory() {
        return this.serverSocketFactory;
    }

    public void setServerSocketFactory(IFactoryThrowing<ServerSocket, IOException> serverSocketFactory2) {
        this.serverSocketFactory = serverSocketFactory2;
    }

    public String getHostname() {
        return this.hostname;
    }

    public IFactory<ITempFileManager> getTempFileManagerFactory() {
        return this.tempFileManagerFactory;
    }

    public void makeSecure(SSLServerSocketFactory sslServerSocketFactory, String[] sslProtocols) {
        this.serverSocketFactory = new SecureServerSocketFactory(sslServerSocketFactory, sslProtocols);
    }

    public Response handle(IHTTPSession session) {
        for (IHandler<IHTTPSession, Response> handle : this.interceptors) {
            Response response = (Response) handle.handle(session);
            if (response != null) {
                return response;
            }
        }
        return (Response) this.httpHandler.handle(session);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Response serve(IHTTPSession session) {
        return Response.newFixedLengthResponse((IStatus) Status.NOT_FOUND, (String) "text/plain", (String) "Not Found");
    }

    public void setAsyncRunner(IAsyncRunner asyncRunner2) {
        this.asyncRunner = asyncRunner2;
    }

    public void setTempFileManagerFactory(IFactory<ITempFileManager> tempFileManagerFactory2) {
        this.tempFileManagerFactory = tempFileManagerFactory2;
    }

    public void start() {
        start(5000);
    }

    public void start(int timeout) {
        start(timeout, true);
    }

    public void start(int timeout, boolean daemon) {
        this.myServerSocket = (ServerSocket) getServerSocketFactory().create();
        this.myServerSocket.setReuseAddress(true);
        ServerRunnable serverRunnable = createServerRunnable(timeout);
        this.myThread = new Thread(serverRunnable);
        this.myThread.setDaemon(daemon);
        this.myThread.setName("NanoHttpd Main Listener");
        this.myThread.start();
        while (!serverRunnable.hasBinded() && serverRunnable.getBindException() == null) {
            try {
                Thread.sleep(10);
            } catch (Throwable th) {
            }
        }
        if (serverRunnable.getBindException() != null) {
            throw serverRunnable.getBindException();
        }
    }

    public void stop() {
        try {
            safeClose(this.myServerSocket);
            this.asyncRunner.closeAll();
            if (this.myThread != null) {
                this.myThread.join();
            }
        } catch (Exception e) {
            MLog.e(TAG, "Could not stop all connections.e=" + e);
        }
    }

    public final boolean wasStarted() {
        return (this.myServerSocket == null || this.myThread == null) ? false : true;
    }
}
