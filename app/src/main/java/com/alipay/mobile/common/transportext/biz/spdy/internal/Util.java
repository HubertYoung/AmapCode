package com.alipay.mobile.common.transportext.biz.spdy.internal;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

public final class Util {
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    private static AtomicReference<byte[]> skipBuffer = new AtomicReference<>();

    private Util() {
    }

    public static int getEffectivePort(URI uri) {
        return getEffectivePort(uri.getScheme(), uri.getPort());
    }

    public static int getEffectivePort(URL url) {
        return getEffectivePort(url.getProtocol(), url.getPort());
    }

    private static int getEffectivePort(String scheme, int specifiedPort) {
        return specifiedPort != -1 ? specifiedPort : getDefaultPort(scheme);
    }

    public static int getDefaultPort(String scheme) {
        if ("http".equalsIgnoreCase(scheme)) {
            return 80;
        }
        if ("https".equalsIgnoreCase(scheme)) {
            return 443;
        }
        return -1;
    }

    public static void checkOffsetAndCount(int arrayLength, int offset, int count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static void pokeInt(byte[] dst, int offset, int value, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            int offset2 = offset + 1;
            dst[offset] = (byte) ((value >> 24) & 255);
            int offset3 = offset2 + 1;
            dst[offset2] = (byte) ((value >> 16) & 255);
            int offset4 = offset3 + 1;
            dst[offset3] = (byte) ((value >> 8) & 255);
            dst[offset4] = (byte) ((value >> 0) & 255);
            int i = offset4;
            return;
        }
        int offset5 = offset + 1;
        dst[offset] = (byte) ((value >> 0) & 255);
        int offset6 = offset5 + 1;
        dst[offset5] = (byte) ((value >> 8) & 255);
        int offset7 = offset6 + 1;
        dst[offset6] = (byte) ((value >> 16) & 255);
        dst[offset7] = (byte) ((value >> 24) & 255);
        int i2 = offset7;
    }

    public static boolean equal(Object a, Object b) {
        return a == b || !(a == null || b == null || !a.equals(b));
    }

    public static boolean equals(InetAddress[] a, InetAddress[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.length == 0 && b.length == 0) {
            return true;
        }
        try {
            for (InetAddress inetAddressB : b) {
                for (InetAddress inetAddressA : a) {
                    if (TextUtils.equals(inetAddressB.getHostAddress(), inetAddressA.getHostAddress())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "force inetAddressB: " + e.toString());
        }
        return false;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "===>Warning: Connection [closed] . closeable=[" + closeable.getClass().getName() + "] closeable hashcode=" + String.valueOf(closeable.hashCode()));
                closeable.close();
                if (closeable instanceof Connection) {
                    LoggerFactory.getTraceLogger().warn((String) "monitor", (Throwable) new RuntimeException("Connection [closed]"));
                }
            } catch (RuntimeException rethrown) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) rethrown);
            } catch (Exception ignored) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) ignored);
            }
        }
    }

    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception ignored) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "closeQuietly: " + ignored.toString());
            }
        }
    }

    public static void closeQuietly(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception ignored) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "closeQuietly2 : " + ignored.toString());
            }
        }
    }

    public static synchronized void closeAll(Closeable a, Closeable b) {
        synchronized (Util.class) {
            Throwable thrown = null;
            try {
                a.close();
            } catch (Throwable th) {
                thrown = th;
            }
            try {
                b.close();
            } catch (Throwable e) {
                if (thrown == null) {
                    thrown = e;
                }
            }
            if (thrown != null) {
                if (thrown instanceof IOException) {
                    throw ((IOException) thrown);
                } else if (thrown instanceof RuntimeException) {
                    throw ((RuntimeException) thrown);
                } else if (thrown instanceof Error) {
                    throw ((Error) thrown);
                } else {
                    throw new AssertionError(thrown);
                }
            }
        }
    }

    public static void deleteContents(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IOException("not a readable directory: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteContents(file);
            }
            if (!file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }

    public static int readSingleByte(InputStream in) {
        byte[] buffer = new byte[1];
        if (in.read(buffer, 0, 1) != -1) {
            return buffer[0] & 255;
        }
        return -1;
    }

    public static void writeSingleByte(OutputStream out, int b) {
        out.write(new byte[]{(byte) (b & 255)});
    }

    public static void readFully(InputStream in, byte[] dst) {
        readFully(in, dst, 0, dst.length);
    }

    public static void readFully(InputStream in, byte[] dst, int offset, int byteCount) {
        if (byteCount != 0) {
            if (in == null) {
                throw new NullPointerException("in == null");
            } else if (dst == null) {
                throw new NullPointerException("dst == null");
            } else {
                checkOffsetAndCount(dst.length, offset, byteCount);
                while (byteCount > 0) {
                    int bytesRead = in.read(dst, offset, byteCount);
                    if (bytesRead < 0) {
                        throw new EOFException();
                    }
                    offset += bytesRead;
                    byteCount -= bytesRead;
                }
            }
        }
    }

    public static String readFully(Reader reader) {
        try {
            StringWriter writer = new StringWriter();
            char[] buffer = new char[1024];
            while (true) {
                int count = reader.read(buffer);
                if (count == -1) {
                    return writer.toString();
                }
                writer.write(buffer, 0, count);
            }
        } finally {
            reader.close();
        }
    }

    public static void skipAll(InputStream in) {
        do {
            in.skip(Long.MAX_VALUE);
        } while (in.read() != -1);
    }

    public static long skipByReading(InputStream in, long byteCount) {
        long skipped = 0;
        if (byteCount != 0) {
            byte[] buffer = skipBuffer.getAndSet(null);
            if (buffer == null) {
                buffer = new byte[4096];
            }
            skipped = 0;
            while (skipped < byteCount) {
                int toRead = (int) Math.min(byteCount - skipped, (long) buffer.length);
                int read = in.read(buffer, 0, toRead);
                if (read == -1) {
                    break;
                }
                skipped += (long) read;
                if (read < toRead) {
                    break;
                }
            }
            skipBuffer.set(buffer);
        }
        return skipped;
    }

    public static int copy(InputStream in, OutputStream out) {
        int total = 0;
        byte[] buffer = new byte[8192];
        while (true) {
            int c = in.read(buffer);
            if (c == -1) {
                return total;
            }
            total += c;
            out.write(buffer, 0, c);
        }
    }

    public static String readAsciiLine(InputStream in) {
        StringBuilder result = new StringBuilder(80);
        while (true) {
            int c = in.read();
            if (c == -1) {
                throw new EOFException();
            } else if (c != 10) {
                result.append((char) c);
            } else {
                int length = result.length();
                if (length > 0 && result.charAt(length - 1) == 13) {
                    result.setLength(length - 1);
                }
                return result.toString();
            }
        }
    }

    public static String hash(String s) {
        try {
            return bytesToHexString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        char[] digits = DIGITS;
        char[] buf = new char[(bytes.length * 2)];
        int c = 0;
        for (byte b : bytes) {
            int c2 = c + 1;
            buf[c] = digits[(b >> 4) & 15];
            c = c2 + 1;
            buf[c2] = digits[b & 15];
        }
        return new String(buf);
    }

    public static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(new ArrayList(list));
    }

    public static ThreadFactory daemonThreadFactory(final String name) {
        return new ThreadFactory() {
            public final Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(true);
                return result;
            }
        };
    }
}
