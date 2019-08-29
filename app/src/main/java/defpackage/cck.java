package defpackage;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.PopupWindow;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;

/* renamed from: cck reason: default package */
/* compiled from: SoftKeyboardShadow */
public final class cck {
    View a;
    public PopupWindow b;
    public int c = 53;
    a d;
    volatile boolean e = false;
    int f = 0;
    private OnLayoutChangeListener g = new OnLayoutChangeListener() {
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            cck cck = cck.this;
            Rect rect = new Rect();
            cck.a.getWindowVisibleDisplayFrame(rect);
            if (((double) (rect.bottom - rect.top)) <= ((double) cck.a.getHeight()) * 0.8d) {
                cck cck2 = cck.this;
                try {
                    Rect rect2 = new Rect();
                    View contentView = cck2.b.getContentView();
                    Activity activity = DoNotUseTool.getActivity();
                    if (activity != null) {
                        if (contentView != null) {
                            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect2);
                            contentView.measure(0, 0);
                            int measuredHeight = rect2.bottom - contentView.getMeasuredHeight();
                            int i9 = rect2.bottom - rect2.top;
                            if (!cck2.b.isShowing() && !cck2.e) {
                                cck2.e = true;
                                cck2.b.showAtLocation(cck2.a, cck2.c, 0, measuredHeight);
                            } else if (cck2.b.isShowing() && (cck2.f - i9 > 10 || i9 - cck2.f > 10)) {
                                cck2.b.update(0, measuredHeight, -1, -1);
                            }
                            cck2.f = i9;
                        }
                    }
                } catch (Exception unused) {
                }
                if (cck.this.d != null) {
                }
            } else {
                cck.this.c();
            }
        }
    };

    /* renamed from: cck$a */
    /* compiled from: SoftKeyboardShadow */
    public interface a {
    }

    public cck(@NonNull bid bid) {
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
        this.a.addOnLayoutChangeListener(this.g);
    }

    private void d() {
        this.a.removeOnLayoutChangeListener(this.g);
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        if (this.b.isShowing() && this.e) {
            this.b.dismiss();
            this.e = false;
        }
    }
}
