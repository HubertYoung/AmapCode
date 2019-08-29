package com.autonavi.map.search.common;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.minimap.widget.ListDialog;
import java.util.ArrayList;

public class PoiSearcherDispatchImpl$3 implements OnItemClickListener {
    final /* synthetic */ bwz this$0;
    final /* synthetic */ Handler val$handler;
    final /* synthetic */ ListDialog val$listDialog;
    final /* synthetic */ int val$msg;
    final /* synthetic */ String val$oldstring;
    final /* synthetic */ ArrayList val$suggestions;

    public PoiSearcherDispatchImpl$3(bwz bwz, ArrayList arrayList, String str, int i, Handler handler, ListDialog listDialog) {
        this.this$0 = bwz;
        this.val$suggestions = arrayList;
        this.val$oldstring = str;
        this.val$msg = i;
        this.val$handler = handler;
        this.val$listDialog = listDialog;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.this$0.a.e = this.this$0.a.e.replaceFirst(this.val$oldstring, (String) this.val$suggestions.get(0));
        Message message = new Message();
        message.what = this.val$msg;
        this.val$handler.sendMessage(message);
        this.val$listDialog.dismiss();
    }
}
