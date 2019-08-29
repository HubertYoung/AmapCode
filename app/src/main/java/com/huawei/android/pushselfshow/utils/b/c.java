package com.huawei.android.pushselfshow.utils.b;

class c implements Runnable {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    public void run() {
        try {
            if (!(this.a.b == null || this.a.c == null)) {
                String a2 = this.a.a(this.a.b, this.a.c, this.a.d);
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "getDownloadFileWithHandler success, and localfile =  ".concat(String.valueOf(a2)));
                if (a2 != null) {
                    this.a.a(a2);
                    return;
                }
            }
        } catch (Exception e) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "getDownloadFileWithHandler failed ", e);
        }
        this.a.c();
    }
}
