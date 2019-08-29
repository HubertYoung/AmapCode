package com.mpaas.nebula.provider;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout.LayoutParams;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.keyboard.AUNumberKeyboardView;
import com.alipay.mobile.antui.keyboard.AUNumberKeyboardView.OnActionClickListener;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.callback.H5InputOperator;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5InputBoardProvider;
import com.alipay.mobile.nebula.provider.H5InputBoardProvider.OnKeyboardEventListener;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.HashMap;
import java.util.Map;

public class H5InputBoardProviderImpEx implements H5InputBoardProvider {
    /* access modifiers changed from: private */
    public static final Map<String, Integer> a = new HashMap();
    /* access modifiers changed from: private */
    public static Boolean h = null;
    private static Boolean i = null;
    private static JSONArray j;
    private HashMap<String, OnKeyboardEventListener> b;
    private OnKeyboardEventListener c = null;
    private Activity d;
    private APWebView e;
    /* access modifiers changed from: private */
    public H5InputOperator f;
    /* access modifiers changed from: private */
    public boolean g = false;
    private long k = 0;

    private class NumberKeyboardEventListener implements OnKeyboardEventListener {
        private AUNumberKeyboardView a;
        private int b = 3;

        NumberKeyboardEventListener(int style) {
            this.b = style;
        }

        public boolean onRelease() {
            if (this.a == null) {
                return false;
            }
            ViewParent parent = this.a.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.a);
            }
            this.a = null;
            return true;
        }

        public boolean onShow(Activity activity, final APWebView apWebView) {
            if (this.a == null) {
                H5Log.d("H5InputBoardProviderImpEx", "initKeyboardView @ H5InputBoardProviderImpEx: " + this.b);
                LayoutParams params = new LayoutParams(-1, -2);
                params.bottomMargin = 0;
                params.gravity = 81;
                this.a = new AUNumberKeyboardView(activity, null, this.b, H5InputBoardProviderImpEx.h.booleanValue(), new OnActionClickListener() {
                    public void onNumClick(View view, CharSequence num) {
                        H5Log.debug("H5InputBoardProviderImpEx", "onNumClick:" + num);
                        if (num != null) {
                            String input = num.toString();
                            if (H5InputBoardProviderImpEx.a.containsKey(input) && apWebView != null) {
                                if ("X".equals(num)) {
                                    H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                                    if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_customKeyboardCapsLockOn"))) {
                                        apWebView.dispatchKeyEvent(H5InputBoardProviderImpEx.a(69889, 0, 52, 1048576));
                                    }
                                } else {
                                    apWebView.dispatchKeyEvent(H5InputBoardProviderImpEx.a(69889, 0, ((Integer) H5InputBoardProviderImpEx.a.get(input)).intValue()));
                                }
                                apWebView.dispatchKeyEvent(H5InputBoardProviderImpEx.a(69889, 1, ((Integer) H5InputBoardProviderImpEx.a.get(input)).intValue()));
                            }
                        }
                    }

                    public void onDeleteClick(View view) {
                        H5Log.debug("H5InputBoardProviderImpEx", "onDeleteClick");
                        if (apWebView != null) {
                            apWebView.dispatchKeyEvent(H5InputBoardProviderImpEx.a(69889, 0, 67));
                            apWebView.dispatchKeyEvent(H5InputBoardProviderImpEx.a(69889, 1, 67));
                        }
                    }

                    public void onConfirmClick(View view) {
                        H5Log.debug("H5InputBoardProviderImpEx", "onConfirmClick");
                        if (H5InputBoardProviderImpEx.this.f != null) {
                            H5InputBoardProviderImpEx.this.f.resizeAndHideKeyboard();
                            H5InputBoardProviderImpEx.this.f.onKeyboardConfirm();
                        }
                        if (apWebView != null) {
                            apWebView.dispatchKeyEvent(H5InputBoardProviderImpEx.a(69889, 0, 66));
                            apWebView.dispatchKeyEvent(H5InputBoardProviderImpEx.a(69889, 1, 66));
                        }
                    }

                    public void onCloseClick(View view) {
                        H5Log.debug("H5InputBoardProviderImpEx", "onCloseClick");
                        if (H5InputBoardProviderImpEx.this.f != null) {
                            H5InputBoardProviderImpEx.this.f.resizeAndHideKeyboard();
                        }
                    }
                });
                activity.addContentView(this.a, params);
            }
            if (!H5InputBoardProviderImpEx.this.g) {
                this.a.setNumKeyRandom(false);
            } else if (!H5InputBoardProviderImpEx.this.isDuplicate()) {
                this.a.setNumKeyRandom(true);
            }
            this.a.setVisibility(0);
            return true;
        }

        public boolean onHide() {
            if (this.a == null) {
                return false;
            }
            if (isKeyboardShown()) {
                this.a.setVisibility(8);
            }
            return true;
        }

        public boolean isKeyboardShown() {
            return this.a != null && this.a.getVisibility() == 0;
        }

        public View getKeyboard() {
            return this.a;
        }
    }

    static {
        a.put("0", Integer.valueOf(7));
        a.put("1", Integer.valueOf(8));
        a.put("2", Integer.valueOf(9));
        a.put("3", Integer.valueOf(10));
        a.put("4", Integer.valueOf(11));
        a.put("5", Integer.valueOf(12));
        a.put("6", Integer.valueOf(13));
        a.put("7", Integer.valueOf(14));
        a.put("8", Integer.valueOf(15));
        a.put("9", Integer.valueOf(16));
        a.put(".", Integer.valueOf(56));
        a.put(DictionaryKeys.CTRLXY_X, Integer.valueOf(52));
        a.put("X", Integer.valueOf(52));
        a.put("-", Integer.valueOf(69));
    }

    public void init(Context context, APWebView apWebView) {
        if (context instanceof Activity) {
            this.d = (Activity) context;
            this.e = apWebView;
            this.b = new HashMap<>();
            boolean isTinyApp = false;
            H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
            String appId = null;
            if (h5Service != null) {
                H5Page h5Page = h5Service.getTopH5Page();
                if (h5Page != null) {
                    isTinyApp = H5Utils.getBoolean(h5Page.getParams(), (String) "isTinyApp", false);
                    appId = H5Utils.getString(h5Page.getParams(), (String) "appId", (String) "");
                }
            }
            if (h == null) {
                H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (h5ConfigProvider != null) {
                    h = Boolean.valueOf("yes".equalsIgnoreCase(h5ConfigProvider.getConfig("h5_useOldDefaultNumKeyboard")));
                    JSONObject useAntUIDefaultKeyboard = h5ConfigProvider.getConfigJSONObject("h5_useAntUIDefaultKeyboard");
                    i = Boolean.valueOf("yes".equalsIgnoreCase(H5Utils.getString(useAntUIDefaultKeyboard, (String) "enable")));
                    j = H5Utils.getJSONArray(useAntUIDefaultKeyboard, "appId", null);
                } else {
                    h = Boolean.valueOf(false);
                    i = Boolean.valueOf(false);
                }
            }
            if (!isTinyApp) {
                if (a(appId)) {
                    register(null, new NumberKeyboardEventListener(1));
                } else {
                    register(null, new NumberKeyboardEventListener(4));
                }
            }
            register("number", new NumberKeyboardEventListener(3));
            register("digit", new NumberKeyboardEventListener(1));
            register("idcard", new NumberKeyboardEventListener(2));
            register("phone", new NumberKeyboardEventListener(4));
        }
    }

    public void register(String type, OnKeyboardEventListener listener) {
        if (this.b != null) {
            this.b.put(type, listener);
        }
    }

    public View getKeyboard() {
        if (this.c == null) {
            return null;
        }
        return this.c.getKeyboard();
    }

    public void setKeyboardType(String type) {
        if (this.b != null) {
            OnKeyboardEventListener newActiveKeyboard = this.b.get(type);
            if (newActiveKeyboard != this.c) {
                if (newActiveKeyboard == null) {
                    this.f.resizeAndHideKeyboard();
                } else {
                    hideKeyboard();
                }
            }
            this.c = newActiveKeyboard;
        }
    }

    public void setIfUseRandomNumber(boolean b2) {
        this.g = b2;
    }

    public void hideKeyboard() {
        H5Log.debug("H5InputBoardProviderImpEx", "hideKeyboard");
        if (this.c != null) {
            this.c.onHide();
        }
    }

    public void showKeyboard() {
        H5Log.debug("H5InputBoardProviderImpEx", "showKeyboard");
        if (this.c != null) {
            this.c.onShow(this.d, this.e);
        }
    }

    public void setOperateListener(H5InputOperator operator) {
        this.f = operator;
    }

    public boolean isKeyboardShown() {
        try {
            if (this.c != null) {
                return this.c.isKeyboardShown();
            }
        } catch (Exception e2) {
            H5Log.e((String) "H5InputBoardProviderImpEx", (Throwable) e2);
        }
        return false;
    }

    public void onRelease() {
        H5Log.debug("H5InputBoardProviderImpEx", "onRelease");
        hideKeyboard();
        if (this.c != null) {
            this.c.onRelease();
        }
        this.f = null;
        if (this.b != null) {
            this.b.clear();
        }
        this.d = null;
        this.e = null;
    }

    /* access modifiers changed from: private */
    public static KeyEvent a(int inputSource, int action, int keyCode) {
        long now = SystemClock.uptimeMillis();
        return new KeyEvent(now, now, action, keyCode, 0, 0, -1, 0, 0, inputSource);
    }

    /* access modifiers changed from: private */
    public static KeyEvent a(int inputSource, int action, int keyCode, int metaState) {
        long now = SystemClock.uptimeMillis();
        return new KeyEvent(now, now, action, keyCode, 0, metaState, -1, 0, 0, inputSource);
    }

    public boolean isDuplicate() {
        boolean isDuplicate;
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.k > 200) {
            isDuplicate = false;
        } else {
            isDuplicate = true;
        }
        this.k = currentTime;
        return isDuplicate;
    }

    private static boolean a(String appId) {
        if (i.booleanValue()) {
            if (j == null || j.isEmpty()) {
                return true;
            }
            for (int i2 = 0; i2 < j.size(); i2++) {
                if (appId.equals(j.getString(i2))) {
                    return true;
                }
            }
        }
        return false;
    }
}
