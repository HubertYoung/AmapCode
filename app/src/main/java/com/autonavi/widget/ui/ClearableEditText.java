package com.autonavi.widget.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import com.autonavi.minimap.R;

public class ClearableEditText extends EditText implements TextWatcher, OnFocusChangeListener, OnTouchListener {
    private Drawable mClearDrawable;
    private Drawable mClearPressedDrawable;
    private Drawable mEmptyDrawable;
    private a mEmptyDrawableOnClickListener;
    private Drawable mEmptyPressDrawable;
    private OnFocusChangeListener mOnFocusChangeListener;
    private OnTouchListener mOnTouchListener;

    public interface a {
        void onEmptyClick();
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public ClearableEditText(Context context) {
        super(context);
        init(context);
    }

    public ClearableEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ClearableEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public void setOnEmptyDrawableClickListener(a aVar) {
        this.mEmptyDrawableOnClickListener = aVar;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.mOnFocusChangeListener = onFocusChangeListener;
    }

    public void setEmptyDrawable(Drawable drawable, Drawable drawable2) {
        this.mEmptyDrawable = drawable;
        if (drawable2 == null) {
            this.mEmptyPressDrawable = this.mEmptyDrawable;
        } else {
            this.mEmptyPressDrawable = drawable2;
        }
    }

    public void setClearDrawable(Drawable drawable, Drawable drawable2) {
        this.mClearDrawable = drawable;
        if (drawable2 == null) {
            this.mClearPressedDrawable = this.mEmptyDrawable;
        } else {
            this.mClearPressedDrawable = drawable2;
        }
    }

    public void showEmptyDrawable() {
        setCompoundDrawables(getCompoundDrawables()[0], null, this.mEmptyDrawable, null);
    }

    public void showNullDrawable() {
        setCompoundDrawables(null, null, null, null);
    }

    public void setRightDrawableAlpha(int i) {
        Drawable drawable = getCompoundDrawables()[2];
        if (drawable != null) {
            drawable.setAlpha(i);
            if (VERSION.SDK_INT < 14) {
                drawable.invalidateSelf();
            }
        }
    }

    public void onFocusChange(View view, boolean z) {
        setClearIconVisible(!TextUtils.isEmpty(getText()));
        if (this.mOnFocusChangeListener != null) {
            this.mOnFocusChangeListener.onFocusChange(view, z);
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        setClearIconVisible(!TextUtils.isEmpty(charSequence));
    }

    private void init(Context context) {
        this.mClearDrawable = getCompoundDrawables()[2];
        if (this.mClearDrawable == null) {
            this.mClearDrawable = getResources().getDrawable(R.drawable.icon_b4_normal);
        }
        if (this.mClearDrawable != null) {
            this.mClearDrawable.setBounds(0, 0, this.mClearDrawable.getIntrinsicWidth(), this.mClearDrawable.getIntrinsicHeight());
        }
        this.mClearPressedDrawable = context.getResources().getDrawable(R.drawable.icon_b4_press);
        if (this.mClearPressedDrawable != null) {
            this.mClearPressedDrawable.setBounds(0, 0, this.mClearPressedDrawable.getIntrinsicWidth(), this.mClearPressedDrawable.getIntrinsicHeight());
        }
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    private boolean isClearIconVisible() {
        Drawable drawable = getCompoundDrawables()[2];
        return (drawable == null || drawable == this.mEmptyDrawable || drawable == this.mEmptyPressDrawable) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void setClearIconVisible(boolean z) {
        if (z != isClearIconVisible()) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], z ? this.mClearDrawable : this.mEmptyDrawable, getCompoundDrawables()[3]);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (getCompoundDrawables()[2] != null) {
            boolean isClearIconVisible = isClearIconVisible();
            int x = (int) motionEvent.getX();
            if (motionEvent.getAction() == 0) {
                if (isClearTapped(x, isClearIconVisible)) {
                    setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], isClearIconVisible ? this.mClearPressedDrawable : this.mEmptyPressDrawable, getCompoundDrawables()[3]);
                    return true;
                }
            } else if (isClearTapped(x, isClearIconVisible) && (motionEvent.getAction() == 1 || motionEvent.getAction() == 3)) {
                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], isClearIconVisible ? this.mClearDrawable : this.mEmptyDrawable, getCompoundDrawables()[3]);
                if (motionEvent.getAction() == 1) {
                    onActionUp(isClearIconVisible);
                    return true;
                }
            }
        }
        return this.mOnTouchListener != null && this.mOnTouchListener.onTouch(view, motionEvent);
    }

    private void onActionUp(boolean z) {
        if (z) {
            setText("");
            return;
        }
        if (this.mEmptyDrawableOnClickListener != null) {
            this.mEmptyDrawableOnClickListener.onEmptyClick();
        }
    }

    private boolean isClearTapped(int i, boolean z) {
        if (z) {
            if (i <= (getWidth() - getPaddingRight()) - this.mClearDrawable.getIntrinsicWidth()) {
                return false;
            }
        } else if (this.mEmptyDrawable == null || i <= (getWidth() - getPaddingRight()) - this.mEmptyDrawable.getIntrinsicWidth()) {
            return false;
        }
        return true;
    }
}
