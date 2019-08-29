package com.taobao.applink.h;

import android.os.AsyncTask;
import com.taobao.applink.util.f;

class b extends AsyncTask {
    final /* synthetic */ String a;
    final /* synthetic */ a b;

    b(a aVar, String str) {
        this.b = aVar;
        this.a = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void doInBackground(Void... voidArr) {
        f.a(this.a);
        return null;
    }
}
