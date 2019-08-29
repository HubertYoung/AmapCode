package com.xiaomi.push.mpcd.job;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.mpcd.BroadcastActionsReceiver;
import com.xiaomi.xmpush.thrift.d;

public class e extends f {
    public static String a = "";
    public static String b = "";

    public e(Context context, int i) {
        super(context, i);
    }

    private String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        String[] split = str2.split(",");
        if (split.length <= 10) {
            return str2;
        }
        int length = split.length;
        while (true) {
            length--;
            if (length < split.length - 10) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(split[length]);
            str = sb.toString();
        }
    }

    public int a() {
        return 12;
    }

    public String b() {
        String str = "";
        if (!TextUtils.isEmpty(a)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(a(BroadcastActionsReceiver.a, a));
            str = sb.toString();
            a = "";
        }
        if (TextUtils.isEmpty(b)) {
            return str;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(a(BroadcastActionsReceiver.b, b));
        String sb3 = sb2.toString();
        b = "";
        return sb3;
    }

    public d d() {
        return d.BroadcastAction;
    }
}
