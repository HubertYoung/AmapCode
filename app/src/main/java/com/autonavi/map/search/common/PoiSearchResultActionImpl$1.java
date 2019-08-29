package com.autonavi.map.search.common;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.common.SuperId;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

public class PoiSearchResultActionImpl$1 implements OnItemClickListener {
    final /* synthetic */ bww this$0;
    final /* synthetic */ String[] val$F_SELECT_ACTION;

    PoiSearchResultActionImpl$1(bww bww, String[] strArr) {
        this.this$0 = bww;
        this.val$F_SELECT_ACTION = strArr;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.this$0.a.e = this.val$F_SELECT_ACTION[i];
        SuperId.getInstance().setBit3("09");
        this.this$0.a.c.a((String) "101000", (String) "", SuperId.getInstance());
        bcb bcb = (bcb) a.a.a(bcb.class);
        if (bcb != null) {
            bcb.a().c(this.this$0.a.e).a(AMapPageUtil.getPageContext());
        }
    }
}
