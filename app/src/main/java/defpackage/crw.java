package defpackage;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.widget.AmapButton;

/* renamed from: crw reason: default package */
/* compiled from: ErrorReportInputDialog */
public final class crw {
    public PopupWindow a;
    Context b;
    public LayoutInflater c;
    public EditText d;
    public AmapButton e;
    public View f;
    public TextView g;
    public crx h;
    public String i;
    public boolean j = false;
    public int k = 0;
    public String l = "";
    public String m = "";
    private int n = 0;
    private boolean o = false;
    private Handler p = new Handler();
    private final Runnable q = new Runnable() {
        public final void run() {
            crw.this.f.setVisibility(8);
        }
    };

    public crw(Context context, LayoutInflater layoutInflater) {
        this.b = context;
        this.c = layoutInflater;
    }

    public final void a(String str, long j2) {
        if (this.f.getVisibility() == 0) {
            this.p.removeCallbacks(this.q);
        } else {
            this.f.setVisibility(0);
        }
        this.g.setText(str);
        if (j2 != -1) {
            this.p.postDelayed(this.q, j2);
            if (this.g.getResources().getConfiguration().orientation == 2) {
                ToastHelper.showToast(str);
            }
        }
    }

    private boolean a() {
        return ((InputMethodManager) this.b.getSystemService("input_method")).hideSoftInputFromWindow(this.d.getWindowToken(), 0);
    }

    public static /* synthetic */ void a(crw crw) {
        if (crw.h != null) {
            crw.h.a(crw.d.getText().toString().trim());
            if (crw.a != null) {
                crw.a();
            }
        }
    }

    public static /* synthetic */ void b(crw crw) {
        if (crw.f.getVisibility() == 0) {
            crw.p.removeCallbacks(crw.q);
        }
        crw.a();
        if (crw.a != null) {
            crw.a.dismiss();
            crw.a = null;
        }
        crw.j = false;
        crw.d.setOnEditorActionListener(null);
    }
}
