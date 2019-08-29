package com.autonavi.minimap.ajx3.widget.view.toast.impl;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.view.toast.IToast;
import com.autonavi.minimap.ajx3.widget.view.toast.ToastLooper;
import com.autonavi.minimap.ajx3.widget.view.toast.ToastLooper.Operator;
import java.lang.reflect.Method;

abstract class AbstractToast implements IToast, Operator {
    private Method getInputMethodWindowVisibleHeight;
    private InputMethodManager imm;
    private boolean isAboveKeyboard;
    private int mAnimation = 16973828;
    private boolean mCenterGravityFlag;
    private View mContent;
    private Context mContext;
    protected long mDuration = 2000;
    protected int mGravity = 81;
    protected int mPriority;
    private TextView mTextContainer;
    protected long mTimestamp;
    protected int mXOffset;
    protected int mYOffset;

    public abstract boolean display();

    public abstract boolean hide();

    public abstract boolean isShowing();

    public AbstractToast(Context context) {
        this.mContext = context;
        this.mContent = LayoutInflater.from(context).inflate(R.layout.toast_content_default, null);
        this.mTextContainer = (TextView) this.mContent.findViewById(R.id.text_toast);
        this.mXOffset = 0;
        this.mYOffset = DimensionUtils.dipToPixel(100.0f);
        init();
    }

    private void init() {
        this.imm = (InputMethodManager) this.mContext.getSystemService("input_method");
        if (VERSION.SDK_INT >= 21) {
            try {
                this.getInputMethodWindowVisibleHeight = this.imm.getClass().getMethod("getInputMethodWindowVisibleHeight", new Class[0]);
                this.getInputMethodWindowVisibleHeight.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
        }
    }

    public void show() {
        ToastLooper.instance().add(this);
    }

    public void cancel() {
        ToastLooper.instance().clear();
    }

    public IToast setView(View view) {
        if (view != null) {
            this.mContent = view;
            this.mTextContainer = null;
        }
        return this;
    }

    public IToast setText(String str) {
        if (this.mTextContainer != null) {
            this.mTextContainer.setText(str);
        }
        return this;
    }

    public View getView() {
        return this.mContent;
    }

    public IToast setDuration(long j) {
        this.mDuration = j;
        return this;
    }

    public IToast setGravity(int i) {
        this.mGravity = i;
        return this;
    }

    public IToast setAboveKeyboard(boolean z) {
        this.isAboveKeyboard = z;
        return this;
    }

    public IToast setGravity(int i, int i2, int i3) {
        this.mGravity = i;
        this.mXOffset = i2;
        this.mYOffset = i3;
        return this;
    }

    public IToast setAnimation(int i) {
        this.mAnimation = i;
        return this;
    }

    public int getAnimation() {
        return this.mAnimation;
    }

    public IToast setPriority(int i) {
        this.mPriority = i;
        return this;
    }

    public int getGravity() {
        if (this.mCenterGravityFlag) {
            return 17;
        }
        return this.mGravity;
    }

    public int getXOffset() {
        return this.mXOffset;
    }

    public int getYOffset() {
        return calcYOffset();
    }

    private int calcYOffset() {
        if (!this.isAboveKeyboard) {
            return this.mYOffset;
        }
        if (this.imm.isActive()) {
            if (this.getInputMethodWindowVisibleHeight != null) {
                try {
                    int intValue = ((Integer) this.getInputMethodWindowVisibleHeight.invoke(this.imm, new Object[0])).intValue();
                    if (intValue > 0) {
                        return intValue + 40;
                    }
                } catch (Exception unused) {
                }
            } else {
                this.mCenterGravityFlag = true;
                return 0;
            }
        }
        return this.mYOffset;
    }

    public void setTimestamp(long j) {
        this.mTimestamp = j;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public int getPriority() {
        return this.mPriority;
    }
}
