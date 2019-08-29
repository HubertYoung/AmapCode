package defpackage;

import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* renamed from: ewt reason: default package */
/* compiled from: PLog */
public final class ewt {
    private static Boolean a = Boolean.FALSE;
    private static Boolean b = Boolean.FALSE;
    private static Boolean c = Boolean.FALSE;
    private static char d = 'v';
    private static String e = null;
    private static int f = 0;
    private static String g = "_mm_log.txt";
    private static String[] h = {"autonavi", "mmlog"};

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.pathSeparator);
        e = sb.toString();
    }

    public static void a(boolean z) {
        a = Boolean.FALSE;
        b = Boolean.TRUE;
        c = Boolean.valueOf(z);
    }

    public static void a(String str, String str2) {
        a(str, str2, 'e');
    }

    public static void b(String str, String str2) {
        a(str, str2, 'd');
    }

    public static void c(String str, String str2) {
        a(str, str2, 'i');
    }

    private static void a(String str, String str2, char c2) {
        if (a.booleanValue()) {
            b.booleanValue();
            if (c.booleanValue()) {
                a(String.valueOf(c2), str, str2);
            }
        }
    }

    private static void a(String str, String str2, String str3) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
        StringBuilder sb = new StringBuilder();
        sb.append(simpleDateFormat.format(date));
        sb.append("    ");
        sb.append(str);
        sb.append("    ");
        sb.append(str2);
        sb.append("    ");
        sb.append(str3);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(a(h));
        sb3.append(File.separator);
        sb3.append(format);
        sb3.append(g);
        try {
            FileWriter fileWriter = new FileWriter(new File(sb3.toString()), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(sb2);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException unused) {
        }
    }

    private static String a(String... strArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        String sb2 = sb.toString();
        if (TextUtils.isEmpty(sb2)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sb2);
        if (sb2.lastIndexOf(47) != stringBuffer.length() - 1) {
            stringBuffer.append(File.separator);
        }
        for (int i = 0; i < strArr.length; i++) {
            stringBuffer.append(strArr[i]);
            if (i != strArr.length - 1) {
                stringBuffer.append(File.separator);
            }
        }
        String stringBuffer2 = stringBuffer.toString();
        File file = new File(stringBuffer2);
        try {
            if (!file.exists()) {
                file.mkdir();
            }
            return stringBuffer2;
        } catch (Exception unused) {
            return "";
        }
    }
}
