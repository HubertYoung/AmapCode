package defpackage;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.TextView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.minimap.R;

/* renamed from: dgt reason: default package */
/* compiled from: FixWidthProgressDlg */
public final class dgt extends CompatDialog {
    public TextView a;

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public dgt(Activity activity) {
        this(activity, 0);
    }

    private dgt(Activity activity, byte b) {
        super(activity, R.style.custom_dlg);
        requestWindowFeature(1);
        setContentView(R.layout.widget_fix_progress_dlg);
        this.a = (TextView) findViewById(R.id.msg);
    }
}
