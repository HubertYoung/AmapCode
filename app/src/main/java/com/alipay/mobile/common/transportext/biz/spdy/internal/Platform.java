package com.alipay.mobile.common.transportext.biz.spdy.internal;

import android.text.TextUtils;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import javax.net.ssl.SSLSocket;

public class Platform {
    private static final Platform PLATFORM = findPlatform();
    private Constructor<DeflaterOutputStream> deflaterConstructor;

    class Android23 extends Java6 {
        protected final Class<?> openSslSocketClass;
        private final Method setHostname;
        private final Method setUseSessionTickets;

        private Android23(Method getMtu, Class<?> openSslSocketClass2, Method setUseSessionTickets2, Method setHostname2) {
            super(getMtu);
            this.openSslSocketClass = openSslSocketClass2;
            this.setUseSessionTickets = setUseSessionTickets2;
            this.setHostname = setHostname2;
        }

        public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) {
            try {
                socket.connect(address, connectTimeout);
            } catch (SecurityException se) {
                IOException ioException = new IOException("Exception in connect");
                ioException.initCause(se);
                throw ioException;
            }
        }

        public void enableTlsExtensions(SSLSocket socket, String uriHost) {
            super.enableTlsExtensions(socket, uriHost);
            if (this.openSslSocketClass.isInstance(socket)) {
                try {
                    this.setUseSessionTickets.invoke(socket, new Object[]{Boolean.valueOf(true)});
                    this.setHostname.invoke(socket, new Object[]{uriHost});
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e2) {
                    throw new AssertionError(e2);
                }
            }
        }
    }

    class Android41 extends Android23 {
        private final Method getNpnSelectedProtocol;
        private final Method setNpnProtocols;

        private Android41(Method getMtu, Class<?> openSslSocketClass, Method setUseSessionTickets, Method setHostname, Method setNpnProtocols2, Method getNpnSelectedProtocol2) {
            super(getMtu, openSslSocketClass, setUseSessionTickets, setHostname);
            this.setNpnProtocols = setNpnProtocols2;
            this.getNpnSelectedProtocol = getNpnSelectedProtocol2;
        }

        public void setNpnProtocols(SSLSocket socket, byte[] npnProtocols) {
            if (this.openSslSocketClass.isInstance(socket)) {
                try {
                    this.setNpnProtocols.invoke(socket, new Object[]{npnProtocols});
                } catch (IllegalAccessException e) {
                    throw new AssertionError(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }

        public byte[] getNpnSelectedProtocol(SSLSocket socket) {
            if (!this.openSslSocketClass.isInstance(socket)) {
                return null;
            }
            try {
                return (byte[]) this.getNpnSelectedProtocol.invoke(socket, new Object[0]);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }
    }

    class Java6 extends Platform {
        private final Method getMtu;

        private Java6(Method getMtu2) {
            this.getMtu = getMtu2;
        }

        public int getMtu(Socket socket) {
            try {
                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(socket.getLocalAddress());
                if (networkInterface == null) {
                    return Platform.super.getMtu(socket);
                }
                return ((Integer) this.getMtu.invoke(networkInterface, new Object[0])).intValue();
            } catch (NullPointerException e) {
                return Platform.super.getMtu(socket);
            } catch (SocketException e2) {
                return Platform.super.getMtu(socket);
            } catch (IllegalAccessException e3) {
                throw new AssertionError(e3);
            } catch (InvocationTargetException e4) {
                if (e4.getCause() instanceof IOException) {
                    throw ((IOException) e4.getCause());
                }
                throw new RuntimeException(e4.getCause());
            }
        }
    }

    class JdkWithJettyNpnPlatform extends Java6 {
        private final Class<?> clientProviderClass;
        private final Method getMethod;
        private final Method putMethod;
        private final Class<?> serverProviderClass;

        public JdkWithJettyNpnPlatform(Method getMtu, Method putMethod2, Method getMethod2, Class<?> clientProviderClass2, Class<?> serverProviderClass2) {
            super(getMtu);
            this.putMethod = putMethod2;
            this.getMethod = getMethod2;
            this.clientProviderClass = clientProviderClass2;
            this.serverProviderClass = serverProviderClass2;
        }

        public void setNpnProtocols(SSLSocket socket, byte[] npnProtocols) {
            try {
                List strings = new ArrayList();
                int i = 0;
                while (i < npnProtocols.length) {
                    int i2 = i + 1;
                    byte length = npnProtocols[i];
                    strings.add(new String(npnProtocols, i2, length, "US-ASCII"));
                    i = i2 + length;
                }
                Object provider = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNpnProvider(strings));
                this.putMethod.invoke(null, new Object[]{socket, provider});
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            } catch (InvocationTargetException e2) {
                throw new AssertionError(e2);
            } catch (IllegalAccessException e3) {
                throw new AssertionError(e3);
            }
        }

        public byte[] getNpnSelectedProtocol(SSLSocket socket) {
            try {
                JettyNpnProvider provider = (JettyNpnProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, new Object[]{socket}));
                if (!provider.unsupported && provider.selected == null) {
                    Logger.getLogger("com.squareup.okhttp.OkHttpClient").log(Level.INFO, "NPN callback dropped so SPDY is disabled. Is npn-boot on the boot class path?");
                    return null;
                } else if (!provider.unsupported) {
                    return provider.selected.getBytes("US-ASCII");
                } else {
                    return null;
                }
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError();
            } catch (InvocationTargetException e2) {
                throw new AssertionError();
            } catch (IllegalAccessException e3) {
                throw new AssertionError();
            }
        }
    }

    class JettyNpnProvider implements InvocationHandler {
        private final List<String> protocols;
        /* access modifiers changed from: private */
        public String selected;
        public boolean unsupported;

        public JettyNpnProvider(List<String> protocols2) {
            this.protocols = protocols2;
        }

        public Object invoke(Object proxy, Method method, Object[] args) {
            String methodName = method.getName();
            Class returnType = method.getReturnType();
            if (args == null) {
                args = Util.EMPTY_STRING_ARRAY;
            }
            if (TextUtils.equals(methodName, "supports") && Boolean.TYPE == returnType) {
                return Boolean.valueOf(true);
            }
            if (TextUtils.equals(methodName, "unsupported") && Void.TYPE == returnType) {
                this.unsupported = true;
                return null;
            } else if (TextUtils.equals(methodName, "protocols") && args.length == 0) {
                return this.protocols;
            } else {
                if (TextUtils.equals(methodName, "selectProtocol") && String.class == returnType && args.length == 1 && (args[0] == null || (args[0] instanceof List))) {
                    this.selected = this.protocols.get(0);
                    return this.selected;
                } else if (!TextUtils.equals(methodName, "protocolSelected") || args.length != 1) {
                    return method.invoke(this, args);
                } else {
                    this.selected = (String) args[0];
                    return null;
                }
            }
        }
    }

    public static Platform get() {
        return PLATFORM;
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public void logW(String warning) {
        LogCatUtil.warn((String) "Platform", warning);
    }

    public void tagSocket(Socket socket) {
    }

    public void untagSocket(Socket socket) {
    }

    public URI toUriLenient(URL url) {
        return url.toURI();
    }

    public void enableTlsExtensions(SSLSocket socket, String uriHost) {
    }

    public void supportTlsIntolerantServer(SSLSocket socket) {
        socket.setEnabledProtocols(new String[]{"SSLv3"});
    }

    public byte[] getNpnSelectedProtocol(SSLSocket socket) {
        return null;
    }

    public void setNpnProtocols(SSLSocket socket, byte[] npnProtocols) {
    }

    public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) {
        socket.connect(address, connectTimeout);
    }

    public OutputStream newDeflaterOutputStream(OutputStream out, Deflater deflater, boolean syncFlush) {
        RuntimeException runtimeException;
        try {
            Constructor constructor = this.deflaterConstructor;
            if (constructor == null) {
                constructor = DeflaterOutputStream.class.getConstructor(new Class[]{OutputStream.class, Deflater.class, Boolean.TYPE});
                this.deflaterConstructor = constructor;
            }
            return constructor.newInstance(new Object[]{out, deflater, Boolean.valueOf(syncFlush)});
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException("Cannot SPDY; no SYNC_FLUSH available");
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                runtimeException = (RuntimeException) e2.getCause();
            } else {
                runtimeException = new RuntimeException(e2.getCause());
            }
            throw runtimeException;
        } catch (InstantiationException e3) {
            throw new RuntimeException(e3);
        } catch (IllegalAccessException e4) {
            throw new AssertionError();
        }
    }

    public int getMtu(Socket socket) {
        return SecExceptionCode.SEC_ERROR_SECURITYBODY;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return new com.alipay.mobile.common.transportext.biz.spdy.internal.Platform.Android23(r1, r2, r3, r4, null);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.alipay.mobile.common.transportext.biz.spdy.internal.Platform findPlatform() {
        /*
            java.lang.Class<java.net.NetworkInterface> r0 = java.net.NetworkInterface.class
            java.lang.String r7 = "getMTU"
            r8 = 0
            java.lang.Class[] r8 = new java.lang.Class[r8]     // Catch:{ NoSuchMethodException -> 0x004c }
            java.lang.reflect.Method r1 = r0.getMethod(r7, r8)     // Catch:{ NoSuchMethodException -> 0x004c }
            java.lang.String r0 = "com.android.org.conscrypt.OpenSSLSocketImpl"
            java.lang.Class r2 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x0053, NoSuchMethodException -> 0x00e5 }
        L_0x0011:
            java.lang.String r0 = "setUseSessionTickets"
            r7 = 1
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            r8 = 0
            java.lang.Class r16 = java.lang.Boolean.TYPE     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            r7[r8] = r16     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            java.lang.reflect.Method r3 = r2.getMethod(r0, r7)     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            java.lang.String r0 = "setHostname"
            r7 = 1
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            r8 = 0
            java.lang.Class<java.lang.String> r16 = java.lang.String.class
            r7[r8] = r16     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            java.lang.reflect.Method r4 = r2.getMethod(r0, r7)     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            java.lang.String r0 = "setNpnProtocols"
            r7 = 1
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ NoSuchMethodException -> 0x005b, ClassNotFoundException -> 0x0067 }
            r8 = 0
            java.lang.Class<byte[]> r16 = byte[].class
            r7[r8] = r16     // Catch:{ NoSuchMethodException -> 0x005b, ClassNotFoundException -> 0x0067 }
            java.lang.reflect.Method r5 = r2.getMethod(r0, r7)     // Catch:{ NoSuchMethodException -> 0x005b, ClassNotFoundException -> 0x0067 }
            java.lang.String r0 = "getNpnSelectedProtocol"
            r7 = 0
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ NoSuchMethodException -> 0x005b, ClassNotFoundException -> 0x0067 }
            java.lang.reflect.Method r6 = r2.getMethod(r0, r7)     // Catch:{ NoSuchMethodException -> 0x005b, ClassNotFoundException -> 0x0067 }
            com.alipay.mobile.common.transportext.biz.spdy.internal.Platform$Android41 r0 = new com.alipay.mobile.common.transportext.biz.spdy.internal.Platform$Android41     // Catch:{ NoSuchMethodException -> 0x005b, ClassNotFoundException -> 0x0067 }
            r7 = 0
            r0.<init>(r1, r2, r3, r4, r5, r6)     // Catch:{ NoSuchMethodException -> 0x005b, ClassNotFoundException -> 0x0067 }
            r7 = r0
        L_0x004b:
            return r7
        L_0x004c:
            r0 = move-exception
            com.alipay.mobile.common.transportext.biz.spdy.internal.Platform r7 = new com.alipay.mobile.common.transportext.biz.spdy.internal.Platform
            r7.<init>()
            goto L_0x004b
        L_0x0053:
            r0 = move-exception
            java.lang.String r0 = "org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl"
            java.lang.Class r2 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            goto L_0x0011
        L_0x005b:
            r0 = move-exception
            com.alipay.mobile.common.transportext.biz.spdy.internal.Platform$Android23 r7 = new com.alipay.mobile.common.transportext.biz.spdy.internal.Platform$Android23     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            r12 = 0
            r8 = r1
            r9 = r2
            r10 = r3
            r11 = r4
            r7.<init>(r8, r9, r10, r11)     // Catch:{ ClassNotFoundException -> 0x0067, NoSuchMethodException -> 0x00e5 }
            goto L_0x004b
        L_0x0067:
            r0 = move-exception
        L_0x0068:
            java.lang.String r14 = "org.eclipse.jetty.npn.NextProtoNego"
            java.lang.Class r13 = java.lang.Class.forName(r14)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.StringBuilder r0 = r0.append(r14)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.String r7 = "$Provider"
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.Class r15 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.StringBuilder r0 = r0.append(r14)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.String r7 = "$ClientProvider"
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.Class r11 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.StringBuilder r0 = r0.append(r14)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.String r7 = "$ServerProvider"
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.Class r12 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.String r0 = "put"
            r7 = 2
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            r8 = 0
            java.lang.Class<javax.net.ssl.SSLSocket> r16 = javax.net.ssl.SSLSocket.class
            r7[r8] = r16     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            r8 = 1
            r7[r8] = r15     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.reflect.Method r9 = r13.getMethod(r0, r7)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.String r0 = "get"
            r7 = 1
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            r8 = 0
            java.lang.Class<javax.net.ssl.SSLSocket> r16 = javax.net.ssl.SSLSocket.class
            r7[r8] = r16     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            java.lang.reflect.Method r10 = r13.getMethod(r0, r7)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            com.alipay.mobile.common.transportext.biz.spdy.internal.Platform$JdkWithJettyNpnPlatform r7 = new com.alipay.mobile.common.transportext.biz.spdy.internal.Platform$JdkWithJettyNpnPlatform     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            r8 = r1
            r7.<init>(r8, r9, r10, r11, r12)     // Catch:{ ClassNotFoundException -> 0x00da, NoSuchMethodException -> 0x00e3 }
            goto L_0x004b
        L_0x00da:
            r0 = move-exception
        L_0x00db:
            com.alipay.mobile.common.transportext.biz.spdy.internal.Platform$Java6 r7 = new com.alipay.mobile.common.transportext.biz.spdy.internal.Platform$Java6
            r0 = 0
            r7.<init>(r1)
            goto L_0x004b
        L_0x00e3:
            r0 = move-exception
            goto L_0x00db
        L_0x00e5:
            r0 = move-exception
            goto L_0x0068
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.internal.Platform.findPlatform():com.alipay.mobile.common.transportext.biz.spdy.internal.Platform");
    }
}
