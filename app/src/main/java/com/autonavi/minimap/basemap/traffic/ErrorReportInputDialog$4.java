package com.autonavi.minimap.basemap.traffic;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ErrorReportInputDialog$4 implements OnEditorActionListener {
    final /* synthetic */ crw this$0;

    public ErrorReportInputDialog$4(crw crw) {
        this.this$0 = crw;
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6) {
            return false;
        }
        crw.a(this.this$0);
        crw.b(this.this$0);
        return true;
    }
}
