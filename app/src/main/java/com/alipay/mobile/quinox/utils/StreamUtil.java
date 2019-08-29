package com.alipay.mobile.quinox.utils;

import com.alipay.mobile.quinox.log.Log;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public final class StreamUtil {
    public static final int STREAM_BUFFER_SIZE = 8192;
    private static final String TAG = "StreamUtils";

    private StreamUtil() {
    }

    public static byte[] streamToBytes(InputStream inputStream) {
        if (inputStream == null) {
            Log.d((String) TAG, (String) "streamToBytes(InputStream): null == is");
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (-1 == read) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } catch (Exception e) {
            Log.w(TAG, "streamToBytes(InputStream) Exception occur.", e);
        } catch (Throwable th) {
            closeSafely(inputStream);
            throw th;
        }
        closeSafely(inputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String streamToString(InputStream inputStream) {
        return streamToString(inputStream, "UTF-8");
    }

    public static String streamToString(InputStream inputStream, String str) {
        if (inputStream == null) {
            StringBuilder sb = new StringBuilder("streamToString(InputStream, String[");
            sb.append(str);
            sb.append("]): null == is");
            Log.w((String) TAG, sb.toString());
            return null;
        }
        StringBuilder sb2 = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, str), 8192);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb2.append(readLine);
            }
        } catch (Exception e) {
            StringBuilder sb3 = new StringBuilder("streamToString(InputStream, String[");
            sb3.append(str);
            sb3.append("]) Exception occur.");
            Log.w(TAG, sb3.toString(), e);
        } catch (Throwable th) {
            closeSafely(inputStream);
            throw th;
        }
        closeSafely(inputStream);
        return sb2.toString();
    }

    public static boolean streamToStream(InputStream inputStream, OutputStream outputStream) {
        boolean z = false;
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                outputStream.write(bArr, 0, read);
            }
            outputStream.flush();
            z = true;
        } catch (Exception e) {
            Log.w(TAG, "streamToStream(InputStream, OutputStream): Exception occur.", e);
        } catch (Throwable th) {
            closeSafely(outputStream);
            closeSafely(inputStream);
            throw th;
        }
        closeSafely(outputStream);
        closeSafely(inputStream);
        return z;
    }

    public static boolean streamToFile(InputStream inputStream, File file) {
        boolean z;
        boolean z2;
        if (inputStream == null || file == null) {
            StringBuilder sb = new StringBuilder("streamToFile(InputStream, File[");
            sb.append(file);
            sb.append("]): null == is || null == file");
            Log.w((String) TAG, sb.toString());
        } else {
            File parentFile = file.getParentFile();
            if (parentFile.exists()) {
                StringBuilder sb2 = new StringBuilder("streamToFile(InputStream, File[");
                sb2.append(file);
                sb2.append("]): parent dir already exist, no need to call mkdirs().");
                Log.v((String) TAG, sb2.toString());
                z = true;
            } else {
                z = parentFile.mkdirs();
            }
            if (z) {
                if (file.exists()) {
                    z2 = !file.delete();
                } else {
                    StringBuilder sb3 = new StringBuilder("streamToFile(InputStream, File[");
                    sb3.append(file);
                    sb3.append("]): target file don't exist, no need to delete it.");
                    Log.v((String) TAG, sb3.toString());
                    z2 = false;
                }
                if (z2) {
                    StringBuilder sb4 = new StringBuilder("streamToFile(InputStream, File[");
                    sb4.append(file);
                    sb4.append("]): failed to delete exist file.");
                    Log.w((String) TAG, sb4.toString());
                } else {
                    FileOutputStream fileOutputStream = null;
                    try {
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                        try {
                            return streamToStream(inputStream, fileOutputStream2);
                        } catch (Exception e) {
                            e = e;
                            fileOutputStream = fileOutputStream2;
                            closeSafely(fileOutputStream);
                            StringBuilder sb5 = new StringBuilder("streamToFile(InputStream, File[");
                            sb5.append(file);
                            sb5.append("]): Exception occur.");
                            Log.w(TAG, sb5.toString(), e);
                            return false;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        closeSafely(fileOutputStream);
                        StringBuilder sb52 = new StringBuilder("streamToFile(InputStream, File[");
                        sb52.append(file);
                        sb52.append("]): Exception occur.");
                        Log.w(TAG, sb52.toString(), e);
                        return false;
                    }
                }
            } else {
                StringBuilder sb6 = new StringBuilder("streamToFile(InputStream, File[");
                sb6.append(file);
                sb6.append("]): failed to make parent dir.");
                Log.i((String) TAG, sb6.toString());
            }
        }
        return false;
    }

    public static void closeSafely(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                Log.w(TAG, "closeSafely(Closeable): Exception occur.", e);
            }
        }
    }

    public static void copyStreamWithoutClosing(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }
}
