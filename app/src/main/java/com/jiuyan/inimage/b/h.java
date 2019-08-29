package com.jiuyan.inimage.b;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: AlipayBatchDownLoader */
public class h {
    /* access modifiers changed from: private */
    public List<k> a = new ArrayList();
    private r b;

    public h() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(r rVar) {
        this.b = rVar;
    }

    private List<p> a(List<p> list) {
        HashMap hashMap = new HashMap();
        for (p next : list) {
            hashMap.put(next.a, next);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(hashMap.values());
        return arrayList;
    }

    public void a(Context context, List<p> list) {
        List<p> a2 = a(list);
        list.clear();
        list.addAll(a2);
        new Handler().post(new i(this, list, context));
    }

    /* access modifiers changed from: private */
    public void a(Context context, k kVar) {
        p pVar = kVar.d;
        kVar.e.a(pVar.a, pVar.b, pVar.c, new j(this));
    }

    /* access modifiers changed from: private */
    public k b(String str) {
        for (k next : this.a) {
            if (next.d.a.equals(str)) {
                return next;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void a() {
        int size = this.a.size();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        for (k next : this.a) {
            if (next.a && next.b) {
                i2++;
            }
            if (next.a) {
                i++;
            }
            if (next.a && next.c) {
                arrayList.add(next.d);
            }
        }
        if (i2 == size) {
            if (this.b != null) {
                this.b.a();
            }
        } else if (i != size || arrayList.size() <= 0) {
            int i3 = (int) ((((float) i2) / ((float) size)) * 100.0f);
            if (this.b != null) {
                this.b.a(i3);
            }
        } else if (this.b != null) {
            this.b.a((List<p>) arrayList);
        }
    }

    /* access modifiers changed from: private */
    public static boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || file.length() <= 0) {
            return false;
        }
        return true;
    }
}
