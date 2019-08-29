package com.taobao.applink.c;

import android.os.AsyncTask;
import com.taobao.applink.c.b.a;

class c extends AsyncTask {
    final /* synthetic */ a a;
    final /* synthetic */ b b;

    c(b bVar, a aVar) {
        this.b = bVar;
        this.a = aVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void doInBackground(Void... voidArr) {
        this.b.b(this.a);
        return null;
    }
}
