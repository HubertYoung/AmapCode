package defpackage;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.Callback.a;
import com.autonavi.minimap.R;

/* renamed from: dcz reason: default package */
/* compiled from: ProgressDlg */
public final class dcz extends CompatDialog {
    private ProgressBar a = ((ProgressBar) findViewById(R.id.progressbar));
    private TextView b = ((TextView) findViewById(R.id.msg));
    private a c;

    public dcz(Activity activity, String str, String str2) {
        super(activity, R.style.custom_dlg);
        requestWindowFeature(1);
        setContentView(R.layout.custom_progress_dialog_layout);
        TextView textView = (TextView) findViewById(R.id.additional_msg);
        if (str != null && !str.equals("")) {
            this.b.setText(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            textView.setText(str2);
            textView.setVisibility(0);
            return;
        }
        textView.setVisibility(8);
    }

    public final void a(String str) {
        if (str != null && !str.equals("")) {
            this.b.setText(str);
        }
    }

    /* access modifiers changed from: protected */
    public final void onStop() {
        super.onStop();
        if (this.c != null) {
            this.c.cancel();
        }
    }
}
