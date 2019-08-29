package com.huawei.android.pushselfshow.richpush.html;

class g implements Runnable {
    final /* synthetic */ HtmlViewer a;

    g(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public void run() {
        if (this.a.b(this.a.d) < 1000) {
            this.a.f.b();
        } else {
            this.a.b.sendEmptyMessage(1000);
        }
    }
}
