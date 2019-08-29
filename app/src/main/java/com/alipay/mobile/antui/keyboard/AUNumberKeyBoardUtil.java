package com.alipay.mobile.antui.keyboard;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import com.alipay.mobile.antui.basic.AUEditText;
import com.alipay.mobile.antui.keyboard.AUNumberKeyboardView.OnActionClickListener;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.ToolUtils;
import java.lang.reflect.Method;

public class AUNumberKeyBoardUtil implements OnKeyListener, OnActionClickListener, WindowStateChangeListener {
    /* access modifiers changed from: private */
    public static final String TAG = AUNumberKeyBoardUtil.class.getSimpleName();
    /* access modifiers changed from: private */
    public Handler handler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public EditText mEditText;
    /* access modifiers changed from: private */
    public AUNumberKeyboardView mKeyboardView;
    /* access modifiers changed from: private */
    public ScrollView mScrollView;
    private Context tempContext;

    public AUNumberKeyBoardUtil(Context context, EditText editText, AUNumberKeyboardView keyboardView, int softInputAdjustMode) {
        this.tempContext = context;
        this.mEditText = editText;
        this.mKeyboardView = keyboardView;
        initKeyboardView();
        disableShowSoftInput();
        if (this.tempContext instanceof Activity) {
            ((Activity) this.tempContext).getWindow().setSoftInputMode(softInputAdjustMode | 3);
        }
        this.mEditText.setOnTouchListener(new a(this));
        this.mEditText.setOnKeyListener(this);
    }

    private void initKeyboardView() {
        this.mKeyboardView.setActionClickListener(this);
        this.mKeyboardView.setWindowStateChangeListener(this);
        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);
        ((ViewGroup) this.mKeyboardView.getParent()).setLayoutTransition(transition);
    }

    public void setScrollView(ScrollView view) {
        this.mScrollView = view;
    }

    private void disableShowSoftInput() {
        try {
            Method method = AUEditText.class.getMethod("setShowSoftInputOnFocus", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(this.mEditText, new Object[]{Boolean.valueOf(false)});
        } catch (Exception e) {
            AuiLogger.error(TAG, "disableShowSoftInput Exception" + e);
        }
    }

    public void onNumClick(View view, CharSequence num) {
        this.mEditText.getText().insert(this.mEditText.getSelectionStart(), num);
    }

    public void onDeleteClick(View view) {
        Editable editable = this.mEditText.getText();
        int start = this.mEditText.getSelectionStart();
        if (start > 0) {
            editable.delete(start - 1, start);
        }
    }

    public void onConfirmClick(View view) {
    }

    public void onCloseClick(View view) {
        hideKeyboard();
    }

    private boolean hideSysKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.mEditText.getContext().getSystemService("input_method");
        if (!imm.isActive() || this.mEditText == null || this.mEditText.getWindowToken() == null) {
            return false;
        }
        boolean doHide = imm.hideSoftInputFromWindow(this.mEditText.getWindowToken(), 2, new ResultReceiver() {
            /* access modifiers changed from: protected */
            public void onReceiveResult(int resultCode, Bundle resultData) {
                AuiLogger.debug(AUNumberKeyBoardUtil.TAG, "onReceiveResult");
                AUNumberKeyBoardUtil.this.handler.postDelayed(new b(this), 200);
            }
        });
        AuiLogger.debug(TAG, "hideSoftInputFromWindow hide = " + doHide);
        return doHide;
    }

    public void showKeyboard() {
        if (this.mKeyboardView != null && !this.mKeyboardView.isShow()) {
            AuiLogger.debug(TAG, "showKeyboard");
            if (!hideSysKeyboard()) {
                this.mKeyboardView.show();
            }
        }
    }

    public void hideKeyboard() {
        if (this.mKeyboardView != null && this.mKeyboardView.isShow()) {
            this.mKeyboardView.hide();
        }
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode != 4 || !this.mKeyboardView.isShow()) {
            return false;
        }
        hideKeyboard();
        return true;
    }

    public void stateChange(boolean isShow, int height) {
        if (this.mScrollView != null) {
            AuiLogger.debug(TAG, "isShow = " + isShow);
            if (isShow) {
                int[] location = new int[2];
                this.mEditText.getLocationOnScreen(location);
                int currentLocY = this.mEditText.getHeight() + location[1];
                int screenHeight = ToolUtils.getScreenWidth_Height(this.tempContext)[1];
                if (currentLocY > screenHeight - height) {
                    AuiLogger.debug(TAG, "currentLocY = " + currentLocY + ", height=" + height);
                    this.handler.postDelayed(new c(this, (currentLocY + height) - screenHeight), 200);
                }
            }
        }
    }
}
