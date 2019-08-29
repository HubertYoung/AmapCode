package com.huawei.android.pushagent.a.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public class a {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static int a() {
        String str;
        String str2;
        int i;
        Class[] clsArr = {String.class, Integer.TYPE};
        Object[] objArr = {"ro.build.hw_emui_api_level", Integer.valueOf(0)};
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            i = ((Integer) cls.getDeclaredMethod("getInt", clsArr).invoke(cls, objArr)).intValue();
            try {
                c.a("PushLogSC2816", "getEmuiLevel:".concat(String.valueOf(i)));
                return i;
            } catch (ClassNotFoundException unused) {
                str2 = "PushLogSC2816";
                str = " getEmuiLevel wrong, ClassNotFoundException";
                c.d(str2, str);
                return i;
            } catch (ExceptionInInitializerError unused2) {
                str2 = "PushLogSC2816";
                str = " getEmuiLevel wrong, ExceptionInInitializerError";
                c.d(str2, str);
                return i;
            } catch (LinkageError unused3) {
                str2 = "PushLogSC2816";
                str = " getEmuiLevel wrong, LinkageError";
                c.d(str2, str);
                return i;
            } catch (NoSuchMethodException unused4) {
                str2 = "PushLogSC2816";
                str = " getEmuiLevel wrong, NoSuchMethodException";
                c.d(str2, str);
                return i;
            } catch (NullPointerException unused5) {
                str2 = "PushLogSC2816";
                str = " getEmuiLevel wrong, NullPointerException";
                c.d(str2, str);
                return i;
            } catch (IllegalAccessException unused6) {
                str2 = "PushLogSC2816";
                str = " getEmuiLevel wrong, IllegalAccessException";
                c.d(str2, str);
                return i;
            } catch (IllegalArgumentException unused7) {
                str2 = "PushLogSC2816";
                str = " getEmuiLevel wrong, IllegalArgumentException";
                c.d(str2, str);
                return i;
            } catch (InvocationTargetException unused8) {
                str2 = "PushLogSC2816";
                str = " getEmuiLevel wrong, InvocationTargetException";
                c.d(str2, str);
                return i;
            }
        } catch (ClassNotFoundException unused9) {
            i = 0;
            str2 = "PushLogSC2816";
            str = " getEmuiLevel wrong, ClassNotFoundException";
            c.d(str2, str);
            return i;
        } catch (ExceptionInInitializerError unused10) {
            i = 0;
            str2 = "PushLogSC2816";
            str = " getEmuiLevel wrong, ExceptionInInitializerError";
            c.d(str2, str);
            return i;
        } catch (LinkageError unused11) {
            i = 0;
            str2 = "PushLogSC2816";
            str = " getEmuiLevel wrong, LinkageError";
            c.d(str2, str);
            return i;
        } catch (NoSuchMethodException unused12) {
            i = 0;
            str2 = "PushLogSC2816";
            str = " getEmuiLevel wrong, NoSuchMethodException";
            c.d(str2, str);
            return i;
        } catch (NullPointerException unused13) {
            i = 0;
            str2 = "PushLogSC2816";
            str = " getEmuiLevel wrong, NullPointerException";
            c.d(str2, str);
            return i;
        } catch (IllegalAccessException unused14) {
            i = 0;
            str2 = "PushLogSC2816";
            str = " getEmuiLevel wrong, IllegalAccessException";
            c.d(str2, str);
            return i;
        } catch (IllegalArgumentException unused15) {
            i = 0;
            str2 = "PushLogSC2816";
            str = " getEmuiLevel wrong, IllegalArgumentException";
            c.d(str2, str);
            return i;
        } catch (InvocationTargetException unused16) {
            i = 0;
            str2 = "PushLogSC2816";
            str = " getEmuiLevel wrong, InvocationTargetException";
            c.d(str2, str);
            return i;
        }
    }

    public static int a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return -1;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo == null) {
            return -1;
        }
        for (int i = 0; i < allNetworkInfo.length; i++) {
            if (allNetworkInfo[i].getState() == State.CONNECTED) {
                return allNetworkInfo[i].getType();
            }
        }
        return -1;
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return "";
        }
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            int i2 = i * 2;
            cArr[i2] = a[(b & 240) >> 4];
            cArr[i2 + 1] = a[b & 15];
        }
        return new String(cArr);
    }

    public static byte[] a(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        try {
            byte[] bytes = str.getBytes("UTF-8");
            for (int i = 0; i < length; i++) {
                StringBuilder sb = new StringBuilder("0x");
                int i2 = i * 2;
                sb.append(new String(new byte[]{bytes[i2]}, "UTF-8"));
                StringBuilder sb2 = new StringBuilder("0x");
                sb2.append(new String(new byte[]{bytes[i2 + 1]}, "UTF-8"));
                bArr[i] = (byte) (((byte) (Byte.decode(sb.toString()).byteValue() << 4)) ^ Byte.decode(sb2.toString()).byteValue());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bArr;
    }
}
