package com.alipay.mobile.common.utils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class MD5Util {
    public static final String ALGORIGTHM_MD5 = "MD5";
    protected static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public MD5Util() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static MessageDigest getMD5Digest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getMD5Byte(byte[] source) {
        MessageDigest messagedigest = getMD5Digest();
        messagedigest.update(source);
        return messagedigest.digest();
    }

    public static byte[] getMD5Byte(String source, String format) {
        byte[] bArr = null;
        if (TextUtils.isEmpty(source)) {
            return bArr;
        }
        if (TextUtils.isEmpty(format)) {
            format = "UTF-8";
        }
        try {
            return getMD5Byte(source.getBytes(format));
        } catch (Throwable e) {
            LogCatUtil.error("MD5Util", e.getMessage(), e);
            return bArr;
        }
    }

    public static byte[] getMD5Byte(String source) {
        return getMD5Byte(source, "UTF-8");
    }

    public static byte[] getFileMD5Byte(File file) {
        InputStream fis = new FileInputStream(file);
        byte[] fileMd5Byte = a(fis);
        IOUtil.closeStream(fis);
        return fileMd5Byte;
    }

    public static String getMD5Base64(String source) {
        try {
            return Base64.encodeToString(getMD5Byte(source.getBytes("UTF-8")), 2);
        } catch (Throwable e) {
            LogCatUtil.error("MD5Util", e.getMessage(), e);
            return null;
        }
    }

    public static String getMD5String(String source) {
        try {
            return HexStringUtil.bytesToHexString(getMD5Byte(source.getBytes("UTF-8")));
        } catch (Throwable e) {
            LogCatUtil.error("MD5Util", e.getMessage(), e);
            return null;
        }
    }

    public static String getMD5String(byte[] source) {
        try {
            return HexStringUtil.bytesToHexString(getMD5Byte(source));
        } catch (Throwable e) {
            LogCatUtil.error("MD5Util", e.getMessage(), e);
            return null;
        }
    }

    public static String getFileMD5String(File file) {
        try {
            return HexStringUtil.bytesToHexString(getFileMD5Byte(file));
        } catch (IOException e) {
            LogCatUtil.error("MD5Util", e.getMessage(), e);
            return null;
        }
    }

    public static String getFileChunkMD5String(File file, int chunkSequence, long chunkSize) {
        if (chunkSequence <= 0 || file == null) {
            throw new IllegalArgumentException("Invalide parameter!");
        }
        long fileLength = file.length();
        long chunkNumer = fileLength / chunkSize;
        if (fileLength % chunkSize != 0) {
            chunkNumer++;
        }
        if (chunkSequence <= 0 || chunkNumer < ((long) chunkSequence)) {
            throw new IllegalArgumentException("Chunk sequence greater than file size !");
        }
        long offset = ((long) (chunkSequence - 1)) * chunkSize;
        long readLength = chunkSize;
        if (((long) chunkSequence) * chunkSize > fileLength) {
            readLength = fileLength - offset;
        }
        return getFileChunkMD5String(file, offset, readLength);
    }

    public static String getByteArrayChunkMD5String(byte[] bytes, int chunkSequence, int chunkSize) {
        if (chunkSequence <= 0 || bytes == null) {
            throw new IllegalArgumentException("Invalide parameter!");
        }
        int length = bytes.length;
        long chunkNumer = (long) (length / chunkSize);
        if (length % chunkSize != 0) {
            chunkNumer++;
        }
        if (chunkSequence <= 0 || chunkNumer < ((long) chunkSequence)) {
            throw new IllegalArgumentException("Chunk sequence greater than file size !");
        }
        int offset = (chunkSequence - 1) * chunkSize;
        int readLength = chunkSize;
        if (chunkSequence * chunkSize > length) {
            readLength = length - offset;
        }
        MessageDigest messagedigest = getMD5Digest();
        messagedigest.update(bytes, offset, readLength);
        return HexStringUtil.bytesToHexString(messagedigest.digest());
    }

    public static String getFileChunkMD5String(File file, long offset, long length) {
        MessageDigest messagedigest = getMD5Digest();
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, UploadQueueMgr.MSGTYPE_REALTIME);
        if (offset > 0) {
            try {
                randomAccessFile.seek(offset);
            } catch (Throwable th) {
                randomAccessFile.close();
                throw th;
            }
        }
        byte[] buffer = new byte[1024];
        long remain = length;
        while (remain > 0) {
            int n = randomAccessFile.read(buffer, 0, (int) Math.min(remain, 1024));
            if (n < 0) {
                break;
            }
            messagedigest.update(buffer, 0, n);
            remain -= (long) n;
        }
        randomAccessFile.close();
        return HexStringUtil.bytesToHexString(messagedigest.digest());
    }

    private static byte[] a(InputStream inputStream) {
        DigestInputStream din = new DigestInputStream(inputStream, getMD5Digest());
        do {
        } while (din.read(new byte[1024]) > 0);
        return din.getMessageDigest().digest();
    }

    public static String encrypt(String inputText) {
        return getMD5String(inputText);
    }
}
