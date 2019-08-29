package com.alibaba.baichuan.android.trade.c.b;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.utils.i;

class e implements OnTouchListener {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.a.a.setTag(i.a(AlibcContext.context, "id", "com_taobao_nb_sdk_webview_click"), Boolean.TRUE);
        return false;
    }
}
