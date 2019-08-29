package com.autonavi.bundle.banner.view;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.minimap.R;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class PageNumber extends LinearLayout {
    LinkedList<View> ivs = new LinkedList<>();
    private volatile int latest = 0;
    ReentrantLock lock = new ReentrantLock();
    LayoutParams lp = new LayoutParams(-2, -2);
    private int mHeight = 16;
    private int mMargin = 18;
    private int mNormalColorRes = R.color.black;
    private int mSelectedColorRes = R.color.white;
    private int mWidth = 16;
    LinearLayout self;

    public PageNumber(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.self = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.banner_page_number, this, true);
    }

    /* JADX INFO: finally extract failed */
    public void init(int i) {
        this.lp = new LayoutParams(this.mWidth, this.mHeight);
        this.lp.setMargins(this.mMargin / 2, 0, this.mMargin / 2, 0);
        this.lock.lock();
        try {
            this.latest = 0;
            this.ivs = new LinkedList<>();
            LinearLayout linearLayout = this.self;
            linearLayout.removeAllViews();
            for (int i2 = 0; i2 < i; i2++) {
                View view = new View(getContext());
                if (i2 == 0) {
                    if (VERSION.SDK_INT >= 16) {
                        view.setBackground(getShapeDrawable(this.mSelectedColorRes));
                    } else {
                        view.setBackgroundDrawable(getShapeDrawable(this.mSelectedColorRes));
                    }
                } else if (VERSION.SDK_INT >= 16) {
                    view.setBackground(getShapeDrawable(this.mNormalColorRes));
                } else {
                    view.setBackgroundDrawable(getShapeDrawable(this.mNormalColorRes));
                }
                linearLayout.addView(view, this.lp);
                this.ivs.add(view);
            }
            this.lock.unlock();
            invalidate();
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    public void setCurrent(int i) {
        this.lock.lock();
        try {
            int size = this.ivs.size();
            if (i < 0) {
                i = size - 1;
            } else if (i >= size) {
                i = 0;
            }
            if (i < size && this.latest != i) {
                if (VERSION.SDK_INT >= 16) {
                    this.ivs.get(this.latest).setBackground(getShapeDrawable(this.mNormalColorRes));
                    this.ivs.get(i).setBackground(getShapeDrawable(this.mSelectedColorRes));
                } else {
                    this.ivs.get(this.latest).setBackgroundDrawable(getShapeDrawable(this.mNormalColorRes));
                    this.ivs.get(i).setBackgroundDrawable(getShapeDrawable(this.mSelectedColorRes));
                }
                this.latest = i;
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void setColorRes(int i, int i2) {
        this.mNormalColorRes = i;
        this.mSelectedColorRes = i2;
    }

    public void setWidth(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    public void setMargin(int i) {
        this.mMargin = i;
    }

    private ShapeDrawable getShapeDrawable(int i) {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        OvalShape ovalShape = new OvalShape();
        shapeDrawable.getPaint().setColor(getResources().getColor(i));
        shapeDrawable.setShape(ovalShape);
        return shapeDrawable;
    }
}
