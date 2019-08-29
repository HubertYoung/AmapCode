package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import com.alipay.mobile.antui.R;

public abstract class AUBasePwdInputBox extends AULinearLayout {
    protected boolean isWithKeyboard;

    public interface PWDInputListener2 {
        void pwdInputed(int i, Editable editable);
    }

    public abstract EditText getEditText();

    public abstract String getInputedPwd(int i);

    public abstract void setPwdInputListener(PWDInputListener2 pWDInputListener2);

    public AUBasePwdInputBox(Context context) {
        super(context);
    }

    public AUBasePwdInputBox(Context context, AttributeSet set) {
        super(context, set);
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(set, R.styleable.sixCharInputBox);
            this.isWithKeyboard = a.getBoolean(1, true);
            if (a != null) {
                a.recycle();
            }
        } catch (Exception e) {
            if (a != null) {
                a.recycle();
            }
        } catch (Throwable th) {
            if (a != null) {
                a.recycle();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public void callInputMethod() {
        AUEditText editText = (AUEditText) getEditText();
        if (editText != null) {
            editText.requestFocus();
            showInputPannel(editText);
        }
    }

    /* access modifiers changed from: protected */
    public void showInputPannel(View view) {
        view.postDelayed(new a(this, view), 500);
    }
}
