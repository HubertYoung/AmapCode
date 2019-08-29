package com.alibaba.baichuan.android.jsbridge;

final class b implements Runnable {
    final /* synthetic */ AlibcWebview a;
    final /* synthetic */ String b;

    b(AlibcWebview alibcWebview, String str) {
        this.a = alibcWebview;
        this.b = str;
    }

    public final void run() {
        AlibcJsCallbackContext.evaluateJavascript(this.a.getWebView(), this.b);
    }
}
