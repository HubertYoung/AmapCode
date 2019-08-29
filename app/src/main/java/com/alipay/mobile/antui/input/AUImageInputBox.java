package com.alipay.mobile.antui.input;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;

public class AUImageInputBox extends AUInputBox {
    private AUIconView mLastImgBtn;
    private AUTextView mLastTextView;

    public AUImageInputBox(Context context) {
        super(context);
        init(context, null);
    }

    public AUImageInputBox(Context context, AttributeSet set) {
        super(context, set);
        init(context, set);
    }

    private void init(Context context, AttributeSet set) {
        View rightView = LayoutInflater.from(context).inflate(R.layout.au_image_input_view, null);
        this.mInputContainer.addView(rightView);
        this.mLastImgBtn = (AUIconView) rightView.findViewById(R.id.input_right_icon);
        this.mLastTextView = (AUTextView) rightView.findViewById(R.id.input_right_text);
        if (set != null) {
            TypedArray a = context.obtainStyledAttributes(set, R.styleable.AUInputBox);
            if (a.hasValue(2)) {
                setLastImgUnicode(a.getString(2));
            }
            if (a.hasValue(3)) {
                setLastImgDrawable(a.getDrawable(3));
            }
            if (a.hasValue(6)) {
                setLastTextView(a.getString(6));
            }
            a.recycle();
        }
    }

    public void setLastImgDrawable(Drawable drawable) {
        if (drawable != null) {
            this.mLastImgBtn.setImageDrawable(drawable);
            setLastImgBtnVisible(true);
            return;
        }
        setLastImgBtnVisible(false);
    }

    public void setLastImgUnicode(String unicode) {
        if (!TextUtils.isEmpty(unicode)) {
            this.mLastImgBtn.setIconfontUnicode(unicode);
            setLastImgBtnVisible(true);
            return;
        }
        setLastImgBtnVisible(false);
    }

    public void setLastImgBtnVisible(boolean visible) {
        this.mLastImgBtn.setVisibility(visible ? 0 : 8);
    }

    public void setLastImgClickListener(OnClickListener l) {
        this.mLastImgBtn.setOnClickListener(l);
    }

    public void setLastTextView(String lastText) {
        if (!TextUtils.isEmpty(lastText)) {
            this.mLastTextView.setText(lastText);
            this.mLastTextView.setVisibility(0);
            return;
        }
        this.mLastTextView.setVisibility(8);
    }

    public AUTextView getLastTextView() {
        return this.mLastTextView;
    }

    public AUIconView getLastImgBtn() {
        return this.mLastImgBtn;
    }
}
