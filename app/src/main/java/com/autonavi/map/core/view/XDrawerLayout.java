package com.autonavi.map.core.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class XDrawerLayout extends DrawerLayout implements DrawerListener {
    private a mDrawerBuilder;
    private int mDrawerState;
    private boolean mIsDrawerOpened;
    private h mLayoutFinishListener;
    private List<DrawerListener> mListeners;

    public XDrawerLayout(Context context) {
        this(context, null);
    }

    public XDrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XDrawerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsDrawerOpened = false;
        this.mDrawerState = 0;
        setDrawerListener(this);
    }

    @Deprecated
    public void setDrawerListener(DrawerListener drawerListener) {
        super.setDrawerListener(drawerListener);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        if (this.mDrawerBuilder != null) {
            i = this.mDrawerBuilder.f;
        } else {
            i = GravityCompat.START;
        }
        if (this.mDrawerState != 0 || (getDrawerLockMode(i) != 1 && this.mIsDrawerOpened)) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public void openDrawer() {
        if (this.mDrawerBuilder != null) {
            openDrawer(this.mDrawerBuilder.f);
        }
    }

    public void closeDrawer() {
        if (this.mDrawerBuilder != null) {
            closeDrawer(this.mDrawerBuilder.f);
        }
    }

    public void setDrawerBuilder(a aVar) {
        this.mDrawerBuilder = aVar;
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mLayoutFinishListener != null) {
            this.mLayoutFinishListener.g();
        }
    }

    public void addDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener != null) {
            if (this.mListeners == null) {
                this.mListeners = new ArrayList();
            }
            this.mListeners.add(drawerListener);
        }
    }

    public void removeDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener != null && this.mListeners != null) {
            this.mListeners.remove(drawerListener);
        }
    }

    public void registerLayoutListener(h hVar) {
        this.mLayoutFinishListener = hVar;
    }

    public void unregisterLayoutListener() {
        this.mLayoutFinishListener = null;
    }

    public void onDrawerSlide(View view, float f) {
        if (this.mListeners != null) {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                this.mListeners.get(size).onDrawerSlide(view, f);
            }
        }
    }

    public void onDrawerOpened(View view) {
        this.mIsDrawerOpened = true;
        if (this.mListeners != null) {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                this.mListeners.get(size).onDrawerOpened(view);
            }
        }
    }

    public void onDrawerClosed(View view) {
        this.mIsDrawerOpened = false;
        if (this.mListeners != null) {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                this.mListeners.get(size).onDrawerClosed(view);
            }
        }
    }

    public void onDrawerStateChanged(int i) {
        this.mDrawerState = i;
        if (this.mListeners != null) {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                this.mListeners.get(size).onDrawerStateChanged(i);
            }
        }
    }
}
