package com.ali.user.mobile.util;

import android.view.View;
import com.ali.user.mobile.util.ResizeScrollView.OnSizeChangedListener;

public class ShowRegionHelper {
    private final ResizeScrollView a;
    private View b;
    private View c;
    private boolean d;
    /* access modifiers changed from: private */
    public int e = -1;
    /* access modifiers changed from: private */
    public RegionChangeListener f;

    public interface RegionChangeListener {
        void a(int i, int i2);
    }

    public ShowRegionHelper(ResizeScrollView resizeScrollView) {
        this.a = resizeScrollView;
        this.a.setOnSizeChangedListener(new OnSizeChangedListener() {
            public final void a(int i, int i2) {
                ShowRegionHelper.this.e = i;
                ShowRegionHelper.this.a();
                if (ShowRegionHelper.this.f != null) {
                    ShowRegionHelper.this.f.a(i, i2);
                }
            }
        });
    }

    public final void a(View view, View view2, boolean z) {
        this.b = view;
        this.c = view2;
        this.d = z;
        this.e = this.a.getHeight();
        a();
    }

    public final void a(RegionChangeListener regionChangeListener) {
        this.f = regionChangeListener;
    }

    private int a(View view) {
        if (view == null) {
            return 0;
        }
        int[] iArr = new int[2];
        this.a.getLocationInWindow(iArr);
        int i = iArr[1];
        view.getLocationInWindow(iArr);
        return iArr[1] - i;
    }

    /* access modifiers changed from: private */
    public void a() {
        if (-1 != this.e) {
            if (this.b != null || this.c != null) {
                int a2 = a(this.b);
                int a3 = (a(this.c) + (this.c == null ? 0 : this.c.getHeight())) - this.e;
                if (this.d) {
                    if (this.b != null) {
                        this.a.scrollBy(0, Math.min(a3, a2));
                    } else if (a3 >= 0) {
                        this.a.scrollBy(0, a3);
                        return;
                    }
                    return;
                }
                if (this.c == null) {
                    if (a2 <= 0) {
                        this.a.scrollBy(0, a2);
                    }
                } else if (a3 >= 0) {
                    this.a.scrollBy(0, a3);
                }
            }
        }
    }
}
