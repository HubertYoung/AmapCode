package com.alipay.mobile.common.utils;

import android.util.Log;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class IOUtil {
    public IOUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    sb.append(line);
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        Log.e("IOUtil", "", e);
                    }
                }
            } catch (IOException e2) {
                Log.e("IOUtil", "", e2);
                try {
                } catch (IOException e3) {
                    Log.e("IOUtil", "", e3);
                }
            } finally {
                try {
                    is.close();
                } catch (IOException e4) {
                    Log.e("IOUtil", "", e4);
                }
            }
        }
        return sb.toString();
    }

    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                Log.e("IOUtil", "", e);
            }
        }
    }

    public static byte[] InputStreamToByte(InputStream is) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        while (true) {
            int ch = is.read();
            if (ch != -1) {
                bytestream.write(ch);
            } else {
                byte[] byteData = bytestream.toByteArray();
                bytestream.close();
                return byteData;
            }
        }
    }

    public static byte[] fileToByte(File file) {
        RandomAccessFile f = new RandomAccessFile(file, UploadQueueMgr.MSGTYPE_REALTIME);
        try {
            long longlength = f.length();
            int length = (int) longlength;
            if (((long) length) != longlength) {
                throw new IOException("File size >= 2 GB");
            }
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }
}
