package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io;

import android.os.ParcelFileDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

public class IOUtils {
    private static char[] a;
    private static byte[] b;

    public static void close(URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    public static void closeQuietly(Reader input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(Writer output) {
        closeQuietly((Closeable) output);
    }

    public static void closeQuietly(InputStream input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(OutputStream output) {
        closeQuietly((Closeable) output);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(ParcelFileDescriptor pfd) {
        if (pfd != null) {
            try {
                pfd.close();
            } catch (Throwable th) {
            }
        }
    }

    public static void closeQuietly(Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
            }
        }
    }

    @Deprecated
    public static String toString(byte[] input) {
        return new String(input);
    }

    public static String toString(byte[] input, String encoding) {
        return new String(input, Charsets.toCharset(encoding).name());
    }

    public static void write(byte[] data, OutputStream output) {
        if (data != null) {
            output.write(data);
        }
    }

    public static void write(byte[] data, Writer output) {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(byte[] data, Writer output, Charset encoding) {
        if (data != null) {
            output.write(new String(data, Charsets.toCharset(encoding)));
        }
    }

    public static void write(byte[] data, Writer output, String encoding) {
        write(data, output, Charsets.toCharset(encoding));
    }

    public static void write(char[] data, Writer output) {
        if (data != null) {
            output.write(data);
        }
    }

    public static void write(char[] data, OutputStream output) {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(char[] data, OutputStream output, Charset encoding) {
        if (data != null) {
            output.write(new String(data).getBytes(Charsets.toCharset(encoding)));
        }
    }

    public static void write(char[] data, OutputStream output, String encoding) {
        write(data, output, Charsets.toCharset(encoding));
    }

    public static void write(CharSequence data, Writer output) {
        if (data != null) {
            write(data.toString(), output);
        }
    }

    public static void write(CharSequence data, OutputStream output) {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(CharSequence data, OutputStream output, Charset encoding) {
        if (data != null) {
            write(data.toString(), output, encoding);
        }
    }

    public static void write(CharSequence data, OutputStream output, String encoding) {
        write(data, output, Charsets.toCharset(encoding));
    }

    public static void write(String data, Writer output) {
        if (data != null) {
            output.write(data);
        }
    }

    public static void write(String data, OutputStream output) {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(String data, OutputStream output, Charset encoding) {
        if (data != null) {
            output.write(data.getBytes(Charsets.toCharset(encoding)));
        }
    }

    public static void write(String data, OutputStream output, String encoding) {
        write(data, output, Charsets.toCharset(encoding));
    }

    @Deprecated
    public static void write(StringBuffer data, Writer output) {
        if (data != null) {
            output.write(data.toString());
        }
    }

    @Deprecated
    public static void write(StringBuffer data, OutputStream output) {
        write(data, output, (String) null);
    }

    @Deprecated
    public static void write(StringBuffer data, OutputStream output, String encoding) {
        if (data != null) {
            output.write(data.toString().getBytes(Charsets.toCharset(encoding)));
        }
    }

    public static int copy(InputStream input, OutputStream output) {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int) count;
    }

    public static long copyLarge(InputStream input, OutputStream output) {
        return copyLarge(input, output, new byte[4096]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) {
        long count = 0;
        while (true) {
            int n = input.read(buffer);
            if (-1 == n) {
                return count;
            }
            output.write(buffer, 0, n);
            count += (long) n;
        }
    }

    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length, byte[] buffer) {
        if (inputOffset > 0) {
            skipFully(input, inputOffset);
        }
        if (length == 0) {
            return 0;
        }
        int bufferLength = buffer.length;
        int bytesToRead = bufferLength;
        if (length > 0 && length < ((long) bufferLength)) {
            bytesToRead = (int) length;
        }
        long totalRead = 0;
        while (bytesToRead > 0) {
            int read = input.read(buffer, 0, bytesToRead);
            if (-1 == read) {
                return totalRead;
            }
            output.write(buffer, 0, read);
            totalRead += (long) read;
            if (length > 0) {
                bytesToRead = (int) Math.min(length - totalRead, (long) bufferLength);
            }
        }
        return totalRead;
    }

    public static void copy(InputStream input, Writer output) {
        copy(input, output, Charset.defaultCharset());
    }

    public static void copy(InputStream input, Writer output, Charset encoding) {
        copy((Reader) new InputStreamReader(input, Charsets.toCharset(encoding)), output);
    }

    public static void copy(InputStream input, Writer output, String encoding) {
        copy(input, output, Charsets.toCharset(encoding));
    }

    public static int copy(Reader input, Writer output) {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int) count;
    }

    public static long copyLarge(Reader input, Writer output) {
        return copyLarge(input, output, new char[4096]);
    }

    public static long copyLarge(Reader input, Writer output, char[] buffer) {
        long count = 0;
        while (true) {
            int n = input.read(buffer);
            if (-1 == n) {
                return count;
            }
            output.write(buffer, 0, n);
            count += (long) n;
        }
    }

    public static void copy(Reader input, OutputStream output) {
        copy(input, output, Charset.defaultCharset());
    }

    public static void copy(Reader input, OutputStream output, Charset encoding) {
        OutputStreamWriter out = new OutputStreamWriter(output, Charsets.toCharset(encoding));
        copy(input, (Writer) out);
        out.flush();
    }

    public static void copy(Reader input, OutputStream output, String encoding) {
        copy(input, output, Charsets.toCharset(encoding));
    }

    public static long skip(InputStream input, long toSkip) {
        if (toSkip < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        if (b == null) {
            b = new byte[2048];
        }
        long remain = toSkip;
        while (remain > 0) {
            long n = (long) input.read(b, 0, (int) Math.min(remain, 2048));
            if (n < 0) {
                break;
            }
            remain -= n;
        }
        return toSkip - remain;
    }

    public static long skip(Reader input, long toSkip) {
        if (toSkip < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        if (a == null) {
            a = new char[2048];
        }
        long remain = toSkip;
        while (remain > 0) {
            long n = (long) input.read(a, 0, (int) Math.min(remain, 2048));
            if (n < 0) {
                break;
            }
            remain -= n;
        }
        return toSkip - remain;
    }

    public static void skipFully(InputStream input, long toSkip) {
        if (toSkip < 0) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
        }
        long skipped = skip(input, toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
        }
    }

    public static int read(Reader input, char[] buffer, int offset, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + length);
        }
        int remaining = length;
        while (remaining > 0) {
            int count = input.read(buffer, offset + (length - remaining), remaining);
            if (-1 == count) {
                break;
            }
            remaining -= count;
        }
        return length - remaining;
    }

    public static int read(Reader input, char[] buffer) {
        return read(input, buffer, 0, buffer.length);
    }

    public static int read(InputStream input, byte[] buffer, int offset, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + length);
        }
        int remaining = length;
        while (remaining > 0) {
            int count = input.read(buffer, offset + (length - remaining), remaining);
            if (-1 == count) {
                break;
            }
            remaining -= count;
        }
        return length - remaining;
    }

    public static int read(InputStream input, byte[] buffer) {
        return read(input, buffer, 0, buffer.length);
    }

    public static void readFully(Reader input, char[] buffer, int offset, int length) {
        int actual = read(input, buffer, offset, length);
        if (actual != length) {
            throw new EOFException("Length to read: " + length + " actual: " + actual);
        }
    }

    public static void readFully(InputStream input, byte[] buffer, int offset, int length) {
        int actual = read(input, buffer, offset, length);
        if (actual != length) {
            throw new EOFException("Length to read: " + length + " actual: " + actual);
        }
    }

    public static void readFully(InputStream input, byte[] buffer) {
        readFully(input, buffer, 0, buffer.length);
    }

    public static byte[] readToBytes(InputStream input, boolean closeInput) {
        if (input == null) {
            return null;
        }
        byte[] data = new byte[4096];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            try {
                int read = input.read(data);
                if (read == -1) {
                    break;
                }
                baos.write(data, 0, read);
            } catch (Throwable th) {
                closeQuietly((OutputStream) baos);
                if (closeInput) {
                    closeQuietly(input);
                }
                throw th;
            }
        }
        byte[] data2 = baos.toByteArray();
        closeQuietly((OutputStream) baos);
        if (!closeInput) {
            return data2;
        }
        closeQuietly(input);
        return data2;
    }
}
