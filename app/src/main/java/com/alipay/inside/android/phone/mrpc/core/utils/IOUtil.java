package com.alipay.inside.android.phone.mrpc.core.utils;

import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class IOUtil {
    public static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        LoggerFactory.f().b("IOUtil", "", e);
                    }
                }
            } catch (IOException e2) {
                LoggerFactory.f().b("IOUtil", "", e2);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    LoggerFactory.f().b("IOUtil", "", e3);
                }
            }
        }
        return sb.toString();
    }

    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LoggerFactory.f().b("IOUtil", "", e);
            }
        }
    }

    public static byte[] InputStreamToByte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read();
            if (read != -1) {
                byteArrayOutputStream.write(read);
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    public static byte[] fileToByte(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, UploadQueueMgr.MSGTYPE_REALTIME);
        try {
            long length = randomAccessFile.length();
            int i = (int) length;
            if (((long) i) != length) {
                throw new IOException("File size >= 2 GB");
            }
            byte[] bArr = new byte[i];
            randomAccessFile.readFully(bArr);
            return bArr;
        } finally {
            randomAccessFile.close();
        }
    }
}
