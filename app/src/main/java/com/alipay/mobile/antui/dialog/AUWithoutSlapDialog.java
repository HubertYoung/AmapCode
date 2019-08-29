package com.alipay.mobile.antui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;
import com.alipay.mobile.antui.basic.AUDialog;

public class AUWithoutSlapDialog extends AUDialog {
    public AUWithoutSlapDialog(Context context) {
        super(context);
    }

    public AUWithoutSlapDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AUWithoutSlapDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != 0 || !isOutOfBounds(event)) {
            return super.onTouchEvent(event);
        }
        Log.d("MessagePopMenu", "touch out of bounds");
        dismiss();
        return true;
    }

    private boolean isOutOfBounds(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        LayoutParams lp = getWindow().getAttributes();
        return x < lp.x || y < lp.y || x > lp.x + lp.width || y > lp.y + lp.height;
    }

    public void show() {
        if (!(getContext() instanceof Activity) || !((Activity) getContext()).isFinishing()) {
            super.show();
        }
    }
}
