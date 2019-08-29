package com.jiuyan.inimage.paster;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.ArrayList;
import java.util.List;

public class ViewOperation extends View implements a {
    private List<d> b = new ArrayList();
    private int c = -1;
    private Paint d;
    private PointF e = new PointF();
    private j f;
    private boolean g = false;

    public ViewOperation(Context context) {
        super(context);
        f();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public ViewOperation(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        f();
    }

    public ViewOperation(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        f();
    }

    private void f() {
        this.d = new Paint();
        this.d.setColor(Color.parseColor("#FFE7513D"));
        this.d.setStrokeWidth(2.0f);
    }

    public void setOnCustomEventListener(j jVar) {
        this.f = jVar;
    }

    public void setIsLeakEventToBottomView(boolean z) {
        this.g = z;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("ViewOperation", "onDraw " + this.b.size());
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.b.size()) {
                Log.d("ViewOperation", "onDraw aaa " + this.b.get(i2));
                this.b.get(i2).a(canvas);
                i = i2 + 1;
            } else {
                if (this.c != -1) {
                }
                return;
            }
        }
    }

    public List<d> getObjects() {
        return this.b;
    }

    public d getCurrentObject() {
        if (this.c < 0 || this.c >= this.b.size()) {
            return null;
        }
        try {
            return this.b.get(this.c);
        } catch (Exception e2) {
            return null;
        }
    }

    public void a(d dVar, int i, boolean z) {
        if (i > this.b.size()) {
            i = this.b.size();
        }
        this.b.add(i, dVar);
        if (z) {
            if (this.b != null) {
                for (d a : this.b) {
                    a.a(false);
                }
            }
            setSelected(i);
        }
        invalidate();
    }

    public void a(d dVar, boolean z) {
        if (dVar != null) {
            this.b.add(dVar);
            if (z) {
                setSelected(this.b.size() - 1);
            }
            invalidate();
        }
    }

    public void b() {
        this.b.clear();
        this.c = -1;
        invalidate();
    }

    public void a(d dVar) {
        this.b.remove(dVar);
        this.c = -1;
        invalidate();
    }

    public void a(List<d> list) {
        this.b.removeAll(list);
        this.c = -1;
        invalidate();
    }

    public void c() {
        this.b.clear();
        this.c = -1;
        invalidate();
    }

    public void d() {
        if (this.c != -1) {
            d dVar = this.b.get(this.c);
            if (dVar != null) {
                dVar.a(false);
            }
        }
        this.c = -1;
        invalidate();
    }

    public void a() {
        invalidate();
    }

    public int getViewWidth() {
        return getWidth();
    }

    public int getViewHeight() {
        return getHeight();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        boolean z;
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.e.set(motionEvent.getX(), motionEvent.getY());
                if (this.c != -1) {
                    if (this.f != null && this.b.get(this.c).a(this.e.x, this.e.y) < 1) {
                        this.f.a(true);
                        break;
                    }
                } else if (this.g && a(this.e) == -1) {
                    return false;
                }
            case 1:
            case 3:
                if (a(this.e.x, this.e.y, motionEvent.getX(), motionEvent.getY()) < ((float) a(getContext(), 4.0f))) {
                    int b2 = b(this.e);
                    if (b2 == this.c) {
                        i = a(this.e);
                    } else {
                        i = b2;
                    }
                    if (i != -1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (i == -1) {
                        if (this.c != -1) {
                            d();
                            if (this.f != null) {
                                this.f.a();
                            }
                        } else if (this.f != null) {
                            this.f.a(new PointF(this.e.x, this.e.y));
                        }
                        if (this.f != null) {
                            this.f.a(false);
                        }
                        return true;
                    }
                    d dVar = this.b.get(i);
                    if (this.c == -1 || this.b.get(this.c).a(this.e.x, this.e.y) < 1) {
                        a(i, z);
                    } else {
                        dVar = this.b.get(this.c);
                    }
                    if (this.f != null) {
                        this.f.a(dVar);
                    }
                    if (dVar.b(this.e) && this.f != null) {
                        this.f.b(dVar);
                    }
                }
                this.e.set(0.0f, 0.0f);
                if (!(this.c == -1 || this.f == null || this.b.get(this.c).a(this.e.x, this.e.y) >= 1)) {
                    this.f.a(false);
                    break;
                }
                break;
        }
        if (this.c != -1) {
            return this.b.get(this.c).a(motionEvent);
        }
        return true;
    }

    private int a(Context context, float f2) {
        return (int) ((context.getResources().getDisplayMetrics().density * f2) + 0.5f);
    }

    private float a(float f2, float f3, float f4, float f5) {
        float f6 = f2 - f4;
        float f7 = f3 - f5;
        return (float) Math.sqrt((double) ((f6 * f6) + (f7 * f7)));
    }

    private int a(PointF pointF) {
        float f2 = pointF.x;
        float f3 = pointF.y;
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            if (this.b.get(i).a(new PointF(f2, f3))) {
                return i;
            }
        }
        return -1;
    }

    private int b(PointF pointF) {
        float f2 = pointF.x;
        float f3 = pointF.y;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).a(new PointF(f2, f3))) {
                return size;
            }
        }
        return -1;
    }

    private void setSelected(int i) {
        if (this.c != -1) {
            d dVar = this.b.get(this.c);
            if (dVar != null) {
                dVar.a(false);
            }
        }
        d dVar2 = this.b.get(i);
        if (dVar2 != null) {
            dVar2.a(true);
            this.c = i;
        }
        invalidate();
    }

    private void a(int i, boolean z) {
        if (this.c == -1 || this.c != i) {
            if (this.c != -1) {
                this.b.get(this.c).a(false);
            }
            if (z) {
                i = e();
                this.b.add(i, this.b.remove(i));
            }
            this.b.get(i).a(true);
            this.c = i;
            invalidate();
        }
    }

    public int e() {
        int i = -1;
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            i = i2;
        }
        return i + 1;
    }
}
