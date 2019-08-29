package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

/* renamed from: cwk reason: default package */
/* compiled from: TeleScopeSharePreferences */
public class cwk {
    private static cwh<cwk> b = new cwh<cwk>() {
        /* access modifiers changed from: protected */
        public final /* synthetic */ Object a() {
            return new cwk(0);
        }
    };
    private Map<String, SharedPreferences> a;

    /* synthetic */ cwk(byte b2) {
        this();
    }

    private cwk() {
        this.a = new HashMap();
    }

    public static cwk a() {
        return (cwk) b.b();
    }

    public final SharedPreferences a(Context context, String str) {
        SharedPreferences sharedPreferences = this.a.get(str);
        if (sharedPreferences == null) {
            synchronized (cwk.class) {
                try {
                    sharedPreferences = this.a.get(str);
                    if (sharedPreferences == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(cwg.b(context));
                        SharedPreferences sharedPreferences2 = context.getSharedPreferences(sb.toString(), 0);
                        this.a.put(str, sharedPreferences2);
                        sharedPreferences = sharedPreferences2;
                    }
                }
            }
        }
        return sharedPreferences;
    }
}
