package com.sijla.g;

import android.content.Context;
import com.sijla.g.a.b;
import java.io.File;
import java.util.List;

public class c {
    public static boolean a(Context context, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(b.c(context));
        sb.append(str);
        return a(sb.toString(), str2);
    }

    public static boolean a(Context context, String str, List<String> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        StringBuffer a = a(list);
        StringBuilder sb = new StringBuilder();
        sb.append(b.c(context));
        sb.append(str);
        return a(sb.toString(), a.toString());
    }

    public static boolean b(Context context, String str, List<String> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        return a(str, a(list).toString(), false);
    }

    public static void c(Context context, String str, List<List<String>> list) {
        if (list != null && list.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (List<String> a : list) {
                stringBuffer.append(a(a));
            }
            StringBuilder sb = new StringBuilder();
            sb.append(b.c(context));
            sb.append(str);
            a(sb.toString(), stringBuffer.toString());
        }
    }

    private static StringBuffer a(List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                stringBuffer.append(list.get(i));
                if (i == list.size() - 1) {
                    stringBuffer.append("\n");
                } else {
                    stringBuffer.append("\t");
                }
            }
        }
        return stringBuffer;
    }

    private static boolean a(String str, String str2) {
        return a(str, str2, true);
    }

    private static boolean a(String str, String str2, boolean z) {
        if (z) {
            str2 = b.b(str2);
        }
        if (str2 == null) {
            return false;
        }
        File a = com.sijla.g.a.c.a(str, b.g(str2));
        if (a == null || !a.exists()) {
            return false;
        }
        return true;
    }
}
