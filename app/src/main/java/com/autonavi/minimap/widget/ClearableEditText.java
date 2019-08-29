package com.autonavi.minimap.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import com.autonavi.minimap.R;

public class ClearableEditText extends EditText implements TextWatcher, OnFocusChangeListener {
    private OnFocusChangeListener f;
    private ClearListener mClearListener = null;
    private EditorListener mListener = null;
    private Drawable xD;

    public interface ClearListener {
        void clickClearBtn();
    }

    public interface EditorListener {
        void haveContent(EditText editText, CharSequence charSequence);
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void setContentListener(EditorListener editorListener) {
        this.mListener = editorListener;
    }

    public void setClearListener(ClearListener clearListener) {
        this.mClearListener = clearListener;
    }

    private void init() {
        this.xD = getCompoundDrawables()[2];
        if (this.xD == null) {
            this.xD = getResources().getDrawable(R.drawable.search_input_clean);
        }
        this.xD.setBounds(0, 0, this.xD.getIntrinsicWidth(), this.xD.getIntrinsicHeight());
        setClearIconVisible(false);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.f = onFocusChangeListener;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (getCompoundDrawables()[2] != null) {
            boolean z = true;
            if (motionEvent.getAction() == 1) {
                if (motionEvent.getX() <= ((float) ((getWidth() - getPaddingRight()) - this.xD.getIntrinsicWidth()))) {
                    z = false;
                }
                if (z) {
                    setText("");
                    if (this.mClearListener != null) {
                        this.mClearListener.clickClearBtn();
                    }
                    motionEvent.setAction(3);
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void onFocusChange(View view, boolean z) {
        boolean z2 = false;
        if (z) {
            if (getText().length() > 0) {
                z2 = true;
            }
            setClearIconVisible(z2);
        } else {
            setClearIconVisible(false);
        }
        if (this.f != null) {
            this.f.onFocusChange(view, z);
        }
    }

    /* access modifiers changed from: protected */
    public void setClearIconVisible(boolean z) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], z ? this.xD : null, getCompoundDrawables()[3]);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (hasFocus()) {
            setClearIconVisible(charSequence.length() > 0);
        }
        if (this.mListener != null) {
            this.mListener.haveContent(this, charSequence);
        }
    }
}
