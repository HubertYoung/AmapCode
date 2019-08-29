package com.alipay.mobile.nebulauc.nativeinput;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import com.alipay.mobile.nebula.util.H5Log;

public class H5NativeEditText extends EditText {
    private static final String TAG = "H5NativeEditText";
    public H5NativeOnSoftKeyboardListener h5NativeOnSoftKeyboardListener;

    public void setH5NativeOnSoftKeyboardListener(H5NativeOnSoftKeyboardListener h5NativeOnSoftKeyboardListener2) {
        this.h5NativeOnSoftKeyboardListener = h5NativeOnSoftKeyboardListener2;
    }

    public H5NativeEditText(Context context) {
        super(context);
        initViews(context);
    }

    public H5NativeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public H5NativeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    public void initViews(Context context) {
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        H5Log.debug(TAG, "onKeyPreIme keyCode " + keyCode + " event " + event);
        if (keyCode == 4 && event != null && event.getAction() == 0 && this.h5NativeOnSoftKeyboardListener != null) {
            this.h5NativeOnSoftKeyboardListener.onKeyPreIme();
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
