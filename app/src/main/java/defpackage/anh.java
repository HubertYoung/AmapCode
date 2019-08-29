package defpackage;

import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* renamed from: anh reason: default package */
/* compiled from: TLog */
public final class anh {
    private static Boolean a = Boolean.FALSE;
    private static Boolean b = Boolean.FALSE;
    private static Boolean c = Boolean.FALSE;
    private static char d = 'v';
    private static String e = null;
    private static int f = 0;
    private static String g = "_mm_log.txt";
    private static String[] h = {"autonavi", "tlog"};

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.pathSeparator);
        e = sb.toString();
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
        return stringBuffer.toString();
    }

    public static void a(String str, String str2) {
        if (a.booleanValue()) {
            b.booleanValue();
            if (c.booleanValue()) {
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
                StringBuilder sb = new StringBuilder();
                sb.append(simpleDateFormat.format(date));
                sb.append("    ");
                sb.append("e");
                sb.append("    ");
                sb.append(str);
                sb.append("    ");
                sb.append(str2);
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(a(h));
                sb3.append(File.separator);
                sb3.append(format);
                sb3.append(g);
                File file = new File(sb3.toString());
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    FileWriter fileWriter = new FileWriter(file, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(sb2);
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    fileWriter.close();
                } catch (IOException unused) {
                }
            }
        }
    }
}
