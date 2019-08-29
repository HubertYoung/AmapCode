package com.alipay.mobile.common.nbnet.biz.util;

import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.utils.HexStringUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static final String a = MD5Utils.class.getSimpleName();

    public static byte[] a(byte[] source) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(source);
            return messagedigest.digest();
        } catch (NoSuchAlgorithmException e) {
            NBNetLogCat.b(a, (Throwable) e);
            return null;
        }
    }

    public static byte[] a(String source) {
        try {
            return a(source.getBytes("UTF-8"));
        } catch (Throwable e) {
            NBNetLogCat.b(a, e);
            return null;
        }
    }

    public static String b(byte[] source) {
        try {
            return HexStringUtil.bytesToHexString(a(source));
        } catch (Throwable e) {
            NBNetLogCat.b(a, e);
            return null;
        }
    }

    public static byte[] a(File file) {
        DigestInputStream din = null;
        try {
            DigestInputStream din2 = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance("MD5"));
            try {
                do {
                } while (din2.read(new byte[1024]) > 0);
                byte[] digest = din2.getMessageDigest().digest();
                IOUtils.a((Closeable) din2);
                DigestInputStream digestInputStream = din2;
                return digest;
            } catch (Exception e) {
                e = e;
                din = din2;
                try {
                    NBNetLogCat.b(a, (Throwable) e);
                    IOUtils.a((Closeable) din);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    IOUtils.a((Closeable) din);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                din = din2;
                IOUtils.a((Closeable) din);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            NBNetLogCat.b(a, (Throwable) e);
            IOUtils.a((Closeable) din);
            return null;
        }
    }

    public static String b(File file) {
        return HexStringUtil.bytesToHexString(a(file));
    }
}
