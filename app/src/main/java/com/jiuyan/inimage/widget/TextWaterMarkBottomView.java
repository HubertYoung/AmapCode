package com.jiuyan.inimage.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.c.a;
import com.jiuyan.inimage.c.b;
import com.jiuyan.inimage.util.DisplayUtil;
import com.jiuyan.inimage.util.q;

public class TextWaterMarkBottomView extends FrameLayout implements OnClickListener {
    private static final int[] b = {-1, -12733541, -890277, -83157, -12608540, -16777216};
    private ac a;
    private ViewGroup c;
    private int d = 0;
    private View e;
    private View f;
    private int[] g = new int[2];
    private int[] h = new int[2];
    private a i;
    private a j;

    public TextWaterMarkBottomView(Context context) {
        super(context);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public TextWaterMarkBottomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TextWaterMarkBottomView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        b();
    }

    /* access modifiers changed from: protected */
    public void setOnTextColorChangedListener(ac acVar) {
        this.a = acVar;
    }

    private void b() {
        setOnClickListener(this);
        this.c = (ViewGroup) findViewById(R.id.colorParent);
        this.d = 0;
        int childCount = this.c.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            ImageView imageView = (ImageView) this.c.getChildAt(i2);
            a aVar = new a();
            aVar.a((float) DisplayUtil.dip2px(getContext(), 2.0f));
            aVar.b((float) DisplayUtil.dip2px(getContext(), 2.0f));
            aVar.a(b.SOLID);
            aVar.a(b[i2]);
            imageView.setBackgroundDrawable(aVar);
            imageView.setOnClickListener(this);
        }
        this.i = new a();
        this.i.a(-10132123);
        this.i.a((float) DisplayUtil.dip2px(getContext(), 1.0f));
        this.i.b((float) DisplayUtil.dip2px(getContext(), 2.0f));
        this.i.a(b.HOLLOW);
        this.j = new a();
        this.j.a((float) DisplayUtil.dip2px(getContext(), 1.5f));
        this.j.b((float) DisplayUtil.dip2px(getContext(), 3.0f));
        this.j.a(b.HOLLOW);
        this.j.a(-1);
        this.e = findViewById(R.id.cover_small);
        this.f = findViewById(R.id.cover_large);
        this.e.setBackgroundDrawable(this.i);
        this.f.setBackgroundDrawable(this.j);
    }

    /* access modifiers changed from: protected */
    public int getSelectedIndex() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public int getSelectedColor() {
        return b[this.d];
    }

    /* access modifiers changed from: protected */
    public void a() {
        this.d = 0;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void setSelectedIndex(int i2) {
        if (i2 >= 0 && i2 < b.length) {
            this.d = i2;
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void setSelectedColor(int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < b.length; i4++) {
            if (b[i4] == i2) {
                i3 = i4;
            }
        }
        this.d = i3;
        invalidate();
    }

    public void onClick(View view) {
        if (view != this) {
            int indexOfChild = this.c.indexOfChild(view);
            if (indexOfChild >= 0) {
                q.a("watermardk", "color select = " + indexOfChild);
                if (this.a != null) {
                    this.a.a(b[indexOfChild]);
                }
                this.d = indexOfChild;
                invalidate();
            }
        }
    }

    private void c() {
        getLocationInWindow(this.g);
        View childAt = this.c.getChildAt(this.d);
        childAt.getLocationInWindow(this.h);
        int i2 = this.h[0] - this.g[0];
        int i3 = this.h[1] - this.g[1];
        this.e.setTranslationX((float) (i2 - ((this.e.getWidth() - childAt.getWidth()) / 2)));
        this.e.setTranslationY((float) (i3 - ((this.e.getHeight() - childAt.getHeight()) / 2)));
        this.f.setTranslationX((float) (i2 - ((this.f.getWidth() - childAt.getWidth()) / 2)));
        this.f.setTranslationY((float) (i3 - ((this.f.getHeight() - childAt.getHeight()) / 2)));
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        c();
        super.dispatchDraw(canvas);
    }
}
