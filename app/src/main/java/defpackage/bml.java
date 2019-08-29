package defpackage;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.autonavi.minimap.R;

/* renamed from: bml reason: default package */
/* compiled from: AlipayWebDialog */
public final class bml extends Dialog {
    private Activity a;
    private View b;

    public bml(Activity activity) {
        super(activity, R.style.alipaytoken_web_dialog);
        this.a = activity;
        if (this.a != null) {
            this.b = LayoutInflater.from(this.a).inflate(R.layout.alipaytoken_webloading_dialog, null);
            setContentView(this.b);
        }
    }

    public final void a() {
        b();
        show();
    }

    private void b() {
        if (this.a != null) {
            Display defaultDisplay = this.a.getWindowManager().getDefaultDisplay();
            Window window = getWindow();
            LayoutParams attributes = window.getAttributes();
            attributes.height = defaultDisplay.getHeight();
            attributes.windowAnimations = 0;
            window.setAttributes(attributes);
            window.setLayout(-1, -1);
        }
    }
}
