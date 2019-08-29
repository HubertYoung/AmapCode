package com.jiuyan.inimage.util;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.LinkedList;
import java.util.List;

/* compiled from: SoftKeyboardStateHelper */
public class o implements OnGlobalLayoutListener {
    private final List<p> a;
    private final View b;
    private int c;
    private boolean d;

    public o(View view) {
        this(view, false);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public o(View view, boolean z) {
        this.a = new LinkedList();
        this.b = view;
        this.d = z;
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void onGlobalLayout() {
        Rect rect = new Rect();
        this.b.getWindowVisibleDisplayFrame(rect);
        int height = this.b.getRootView().getHeight() - (rect.bottom - rect.top);
        if (!this.d && height > 100) {
            this.d = true;
            a(height);
        } else if (this.d && height < 100) {
            this.d = false;
            a();
        }
    }

    public void a(p pVar) {
        this.a.add(pVar);
    }

    private void a(int i) {
        this.c = i;
        for (p next : this.a) {
            if (next != null) {
                next.a(i);
            }
        }
    }

    private void a() {
        for (p next : this.a) {
            if (next != null) {
                next.a();
            }
        }
    }
}
