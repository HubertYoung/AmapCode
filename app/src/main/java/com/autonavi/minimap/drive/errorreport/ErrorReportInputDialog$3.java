package com.autonavi.minimap.drive.errorreport;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ErrorReportInputDialog$3 implements OnEditorActionListener {
    final /* synthetic */ dex this$0;

    public ErrorReportInputDialog$3(dex dex) {
        this.this$0 = dex;
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6) {
            return false;
        }
        if (dex.a(this.this$0)) {
            this.this$0.a();
        }
        return true;
    }
}
