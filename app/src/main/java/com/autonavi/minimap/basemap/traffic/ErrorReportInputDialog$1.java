package com.autonavi.minimap.basemap.traffic;

import android.widget.PopupWindow.OnDismissListener;

public class ErrorReportInputDialog$1 implements OnDismissListener {
    final /* synthetic */ crw this$0;

    public ErrorReportInputDialog$1(crw crw) {
        this.this$0 = crw;
    }

    public void onDismiss() {
        crw.a(this.this$0);
        if (this.this$0.h != null) {
            this.this$0.h.a();
        }
    }
}
