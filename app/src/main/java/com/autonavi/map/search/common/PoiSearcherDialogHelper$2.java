package com.autonavi.map.search.common;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class PoiSearcherDialogHelper$2 implements OnItemClickListener {
    final /* synthetic */ bwy this$0;
    final /* synthetic */ Handler val$handler;
    final /* synthetic */ int val$what;

    public PoiSearcherDialogHelper$2(bwy bwy, int i, Handler handler) {
        this.this$0 = bwy;
        this.val$what = i;
        this.val$handler = handler;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Message message = new Message();
        message.obj = this.this$0.d.getItem(i - this.this$0.c.getListView().getHeaderViewsCount());
        message.what = this.val$what;
        this.val$handler.sendMessage(message);
        this.this$0.c.dismiss();
    }
}
