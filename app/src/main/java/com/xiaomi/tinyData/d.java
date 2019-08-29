package com.xiaomi.tinyData;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.bf;
import com.xiaomi.push.service.bg;
import com.xiaomi.xmpush.thrift.f;
import java.util.HashMap;
import java.util.Map;

public class d {
    private static d a;
    private final Context b;
    private Map<String, e> c = new HashMap();

    private d(Context context) {
        this.b = context;
    }

    public static d a(Context context) {
        if (context == null) {
            b.d("[TinyDataManager]:mContext is null, TinyDataManager.getInstance(Context) failed.");
            return null;
        }
        if (a == null) {
            synchronized (d.class) {
                try {
                    if (a == null) {
                        a = new d(context);
                    }
                }
            }
        }
        return a;
    }

    /* access modifiers changed from: 0000 */
    public e a() {
        e eVar = this.c.get("UPLOADER_PUSH_CHANNEL");
        if (eVar != null) {
            return eVar;
        }
        e eVar2 = this.c.get("UPLOADER_HTTP");
        if (eVar2 != null) {
            return eVar2;
        }
        return null;
    }

    public void a(e eVar, String str) {
        if (eVar == null) {
            b.d("[TinyDataManager]: please do not add null mUploader to TinyDataManager.");
        } else if (TextUtils.isEmpty(str)) {
            b.d("[TinyDataManager]: can not add a provider from unkown resource.");
        } else {
            b().put(str, eVar);
        }
    }

    public boolean a(f fVar, String str) {
        if (TextUtils.isEmpty(str)) {
            b.a((String) "pkgName is null or empty, upload ClientUploadDataItem failed.");
            return false;
        } else if (bf.a(fVar, false)) {
            return false;
        } else {
            if (TextUtils.isEmpty(fVar.m())) {
                fVar.f(bf.a());
            }
            fVar.g(str);
            bg.a(this.b, fVar);
            return true;
        }
    }

    /* access modifiers changed from: 0000 */
    public Map<String, e> b() {
        return this.c;
    }
}
