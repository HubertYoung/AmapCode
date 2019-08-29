package com.alipay.mobile.nebulauc.impl.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.nebula.callback.H5InputOperator;
import com.alipay.mobile.nebula.provider.H5InputBoardProvider;
import com.alipay.mobile.nebula.provider.H5InputBoardProvider.OnKeyboardEventListener;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulauc.R;
import java.util.HashMap;

public class H5InputBoardProviderImpl implements H5InputBoardProvider {
    private APWebView mAPWebView;
    private OnKeyboardEventListener mActiveKeyboard = null;
    private Activity mActivity;
    private OnKeyboardEventListener mDefaultImpl = new OnKeyboardEventListener() {
        private KeyboardView mKeyboard;

        public boolean onRelease() {
            if (this.mKeyboard == null) {
                return false;
            }
            ViewParent parent = this.mKeyboard.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mKeyboard);
            }
            this.mKeyboard = null;
            return true;
        }

        public boolean onShow(Activity activity, final APWebView webView) {
            if (this.mKeyboard == null) {
                Keyboard numKeyboard = new Keyboard(activity, R.xml.h5_input_num);
                View view = LayoutInflater.from(activity).inflate(R.layout.h5_keyboard, null);
                LayoutParams params = new LayoutParams(-2, -2);
                params.bottomMargin = 0;
                params.gravity = 81;
                if (VERSION.SDK_INT >= 16) {
                    view.setBackground(new BitmapDrawable());
                } else {
                    view.setBackgroundDrawable(new BitmapDrawable());
                }
                activity.addContentView(view, params);
                this.mKeyboard = (KeyboardView) view.findViewById(R.id.keyboard_view);
                this.mKeyboard.setKeyboard(numKeyboard);
                this.mKeyboard.setEnabled(true);
                this.mKeyboard.setPreviewEnabled(false);
                this.mKeyboard.setOnKeyboardActionListener(new OnKeyboardActionListener() {
                    public void onPress(int primaryCode) {
                    }

                    public void onRelease(int primaryCode) {
                    }

                    public void onKey(int primaryCode, int[] keyCodes) {
                        if (primaryCode == -3) {
                            if (H5InputBoardProviderImpl.this.mH5InputOperator != null) {
                                H5InputBoardProviderImpl.this.mH5InputOperator.resizeAndHideKeyboard();
                                H5InputBoardProviderImpl.this.mH5InputOperator.onKeyboardConfirm();
                            } else {
                                H5InputBoardProviderImpl.this.hideKeyboard();
                            }
                        }
                        webView.dispatchKeyEvent(H5InputBoardProviderImpl.this.createKeyEvent(69889, 0, primaryCode));
                        webView.dispatchKeyEvent(H5InputBoardProviderImpl.this.createKeyEvent(69889, 1, primaryCode));
                    }

                    public void onText(CharSequence text) {
                    }

                    public void swipeLeft() {
                    }

                    public void swipeRight() {
                    }

                    public void swipeDown() {
                    }

                    public void swipeUp() {
                    }
                });
            }
            int visibility = this.mKeyboard.getVisibility();
            if (visibility == 8 || visibility == 4) {
                this.mKeyboard.setVisibility(0);
            }
            return true;
        }

        public boolean onHide() {
            if (this.mKeyboard == null) {
                return false;
            }
            if (this.mKeyboard.getVisibility() == 0) {
                this.mKeyboard.setVisibility(8);
            }
            return true;
        }

        public boolean isKeyboardShown() {
            return this.mKeyboard != null && this.mKeyboard.getVisibility() == 0;
        }

        public View getKeyboard() {
            return this.mKeyboard;
        }
    };
    /* access modifiers changed from: private */
    public H5InputOperator mH5InputOperator;
    private HashMap<String, OnKeyboardEventListener> mKeyboardMap;

    public void init(Context context, APWebView webView) {
        if (context instanceof Activity) {
            this.mActivity = (Activity) context;
            this.mAPWebView = webView;
            this.mKeyboardMap = new HashMap<>();
            register(null, this.mDefaultImpl);
            register("number", this.mDefaultImpl);
        }
    }

    public void register(String type, OnKeyboardEventListener listener) {
        if (this.mKeyboardMap != null) {
            this.mKeyboardMap.put(type, listener);
        }
    }

    public View getKeyboard() {
        if (this.mActiveKeyboard == null) {
            return null;
        }
        return this.mActiveKeyboard.getKeyboard();
    }

    public void setKeyboardType(String type) {
        if (this.mKeyboardMap != null) {
            OnKeyboardEventListener newActiveKeyboard = this.mKeyboardMap.get(type);
            if (newActiveKeyboard != this.mActiveKeyboard) {
                hideKeyboard();
            }
            this.mActiveKeyboard = newActiveKeyboard;
        }
    }

    public void setIfUseRandomNumber(boolean b) {
    }

    public void showKeyboard() {
        if (this.mActiveKeyboard != null) {
            this.mActiveKeyboard.onShow(this.mActivity, this.mAPWebView);
        }
    }

    public void hideKeyboard() {
        if (this.mActiveKeyboard != null) {
            this.mActiveKeyboard.onHide();
        }
    }

    public void setOperateListener(H5InputOperator operator) {
        this.mH5InputOperator = operator;
    }

    public boolean isKeyboardShown() {
        return this.mActiveKeyboard != null && this.mActiveKeyboard.isKeyboardShown();
    }

    public void onRelease() {
        hideKeyboard();
        if (this.mActiveKeyboard != null) {
            this.mActiveKeyboard.onRelease();
        }
        this.mActiveKeyboard = null;
        this.mKeyboardMap.clear();
        this.mActivity = null;
        this.mAPWebView = null;
    }

    /* access modifiers changed from: private */
    public KeyEvent createKeyEvent(int inputSource, int action, int keyCode) {
        long now = SystemClock.uptimeMillis();
        return new KeyEvent(now, now, action, keyCode, 0, 0, -1, 0, 0, inputSource);
    }
}
