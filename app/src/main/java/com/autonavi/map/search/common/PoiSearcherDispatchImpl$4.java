package com.autonavi.map.search.common;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.widget.ListDialog;
import java.util.ArrayList;

public class PoiSearcherDispatchImpl$4 implements OnItemClickListener {
    final /* synthetic */ bwz this$0;
    final /* synthetic */ ListDialog val$listDialog;
    final /* synthetic */ String val$oldstring;
    final /* synthetic */ ArrayList val$suggestions;

    public PoiSearcherDispatchImpl$4(bwz bwz, ArrayList arrayList, String str, ListDialog listDialog) {
        this.this$0 = bwz;
        this.val$suggestions = arrayList;
        this.val$oldstring = str;
        this.val$listDialog = listDialog;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        String str = this.this$0.a.e;
        StringBuffer stringBuffer = new StringBuffer(new StringBuffer(str).reverse().toString().replaceFirst(new StringBuffer(this.val$oldstring).reverse().toString(), new StringBuffer((String) this.val$suggestions.get(0)).reverse().toString()));
        this.this$0.a.e = stringBuffer.reverse().toString();
        if ((this.this$0.a.h == null || this.this$0.a.h.size() <= 0) && (this.this$0.a.i == null || this.this$0.a.i.size() <= 0)) {
            this.val$listDialog.dismiss();
            return;
        }
        this.this$0.a((String) "101000", (String) "", SuperId.getInstance());
        this.val$listDialog.dismiss();
    }
}
