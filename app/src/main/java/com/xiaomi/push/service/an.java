package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Pair;
import com.xiaomi.channel.commonutils.misc.c;
import java.util.List;

public class an {
    private static volatile an b;
    protected SharedPreferences a;

    private an(Context context) {
        this.a = context.getSharedPreferences("mipush_extra", 0);
    }

    public static an a(Context context) {
        if (b == null) {
            synchronized (an.class) {
                try {
                    if (b == null) {
                        b = new an(context);
                    }
                }
            }
        }
        return b;
    }

    private String a(int i) {
        return "normal_oc_".concat(String.valueOf(i));
    }

    private void a(Editor editor, Pair<Integer, Object> pair, String str) {
        if (pair.second instanceof Integer) {
            editor.putInt(str, ((Integer) pair.second).intValue());
        } else if (pair.second instanceof Long) {
            editor.putLong(str, ((Long) pair.second).longValue());
        } else if (pair.second instanceof String) {
            editor.putString(str, (String) pair.second);
        } else {
            if (pair.second instanceof Boolean) {
                editor.putBoolean(str, ((Boolean) pair.second).booleanValue());
            }
        }
    }

    private String b(int i) {
        return "custom_oc_".concat(String.valueOf(i));
    }

    public int a(int i, int i2) {
        String b2 = b(i);
        if (this.a.contains(b2)) {
            return this.a.getInt(b2, 0);
        }
        String a2 = a(i);
        return this.a.contains(a2) ? this.a.getInt(a2, 0) : i2;
    }

    public void a(List<Pair<Integer, Object>> list) {
        if (!c.a(list)) {
            Editor edit = this.a.edit();
            for (Pair next : list) {
                if (!(next.first == null || next.second == null)) {
                    a(edit, next, a(((Integer) next.first).intValue()));
                }
            }
            edit.commit();
        }
    }

    public boolean a(int i, boolean z) {
        String b2 = b(i);
        if (this.a.contains(b2)) {
            return this.a.getBoolean(b2, false);
        }
        String a2 = a(i);
        return this.a.contains(a2) ? this.a.getBoolean(a2, false) : z;
    }

    public void b(List<Pair<Integer, Object>> list) {
        if (!c.a(list)) {
            Editor edit = this.a.edit();
            for (Pair next : list) {
                if (next.first != null) {
                    String b2 = b(((Integer) next.first).intValue());
                    if (next.second == null) {
                        edit.remove(b2);
                    } else {
                        a(edit, next, b2);
                    }
                }
            }
            edit.commit();
        }
    }
}
