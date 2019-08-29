package defpackage;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.PopupWindow;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;

/* renamed from: dke reason: default package */
/* compiled from: SoftKeyboardShadow */
public final class dke {
    View a;
    PopupWindow b;
    int c = 85;
    public a d;
    private OnLayoutChangeListener e = new OnLayoutChangeListener() {
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            dke dke = dke.this;
            Rect rect = new Rect();
            dke.a.getWindowVisibleDisplayFrame(rect);
            if (((double) (rect.bottom - rect.top)) <= ((double) dke.a.getHeight()) * 0.8d) {
                dke dke2 = dke.this;
                if (!dke2.b.isShowing()) {
                    if (VERSION.SDK_INT < 23) {
                        dke2.b.showAtLocation(dke2.a, dke2.c, 0, 0);
                    } else {
                        Rect rect2 = new Rect();
                        dke2.a.getWindowVisibleDisplayFrame(rect2);
                        dke2.b.showAtLocation(dke2.a, dke2.c, 0, AMapAppGlobal.getTopActivity().getWindowManager().getDefaultDisplay().getHeight() - rect2.bottom);
                    }
                }
                if (dke.this.d != null) {
                }
            } else {
                dke.this.c();
            }
        }
    };

    /* renamed from: dke$a */
    /* compiled from: SoftKeyboardShadow */
    public interface a {
    }

    public dke(@NonNull bid bid) {
        Activity activity = DoNotUseTool.getActivity();
        this.b = new PopupWindow(activity);
        this.b.setWidth(-2);
        this.b.setHeight(-2);
        this.b.setTouchable(true);
        this.b.setFocusable(false);
        this.b.setOutsideTouchable(false);
        this.b.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.transparent));
        this.b.setInputMethodMode(1);
        this.b.setSoftInputMode(16);
        this.a = bid.getContentView();
    }

    public final void a() {
        c();
        d();
    }

    public final void b() {
        d();
        this.a.addOnLayoutChangeListener(this.e);
    }

    private void d() {
        this.a.removeOnLayoutChangeListener(this.e);
    }

    public final void c() {
        if (this.b.isShowing()) {
            this.b.dismiss();
        }
    }
}
