package com.alipay.mobile.nebulauc.input;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.callback.H5InputOperator;
import com.alipay.mobile.nebula.provider.H5InputBoardProvider;
import com.alipay.mobile.nebula.provider.H5WarningTipProvider;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulacore.plugin.H5PagePlugin;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import com.uc.webview.export.WebView;
import com.uc.webview.export.extension.UCExtension.OnSoftKeyboardListener;
import org.json.JSONObject;

public class H5NumInputKeyboardM57 extends H5NumInputKeyboard implements OnSoftKeyboardListener {
    private static final String HIDE_ALL_KEYBOARD = "hideAllKeyboard";
    private static final String HIDE_KEYBOARD = "hideKeyboard";
    private static final String SET_TYPE = "setKeyboardType";
    private static final String TAG = "H5NumInputKeyboardM57";
    private boolean ifUseRandomNumber;
    private final APWebView mApWebView;
    private Context mContext;
    private H5InputBoardProvider mH5InputBoardProvider;
    private H5InputOperator mH5InputOperator = new H5InputOperator() {
        public void resizeAndHideKeyboard() {
            if (H5NumInputKeyboardM57.this.mH5NativeOnSoftKeyboardListener != null) {
                H5NumInputKeyboardM57.this.mH5NativeOnSoftKeyboardListener.onCustomKeyboardHide();
            }
            H5NumInputKeyboardM57.this.sendBlurToFocusElement();
            H5NumInputKeyboardM57.this.hideCustomKeyboard();
        }

        public void onKeyboardConfirm() {
            H5Service h5Service = H5ServiceUtils.getH5Service();
            if (h5Service != null) {
                H5Page h5Page = h5Service.getTopH5Page();
                if (h5Page != null && h5Page.getBridge() != null) {
                    h5Page.getBridge().sendToWeb("keyboardConfirm", null, null);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public ValueCallback<String> mKeyboardCallback;
    private final WebView mWebView;
    private String mWebViewKeyBoardType;

    public H5NumInputKeyboardM57(Context context, WebView webView, APWebView ap) {
        this.mWebView = webView;
        this.mContext = context;
        this.mApWebView = ap;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_PHYSICAL_BACK);
        filter.addAction("pushWindow");
        filter.addAction(CommonEvents.H5_PAGE_PAUSE);
        filter.addAction(HIDE_KEYBOARD);
        filter.addAction(HIDE_ALL_KEYBOARD);
        filter.addAction(SET_TYPE);
        filter.addAction("hideCustomInputMethod4NativeInput");
    }

    public void onRelease() {
        super.onRelease();
        H5Log.debug(TAG, "onRelease");
        this.mContext = null;
        this.mH5InputOperator = null;
        if (this.mH5InputBoardProvider != null) {
            this.mH5InputBoardProvider.hideKeyboard();
            this.mH5InputBoardProvider.onRelease();
            this.mH5InputBoardProvider = null;
        }
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (HIDE_KEYBOARD.equals(action)) {
            hideSoftInputFromWindow();
            H5Log.d(TAG, "hide keyboard");
            return true;
        } else if (HIDE_ALL_KEYBOARD.equals(action)) {
            sendBlurToFocusElement();
            hideAllKeyboard();
            H5Log.d(TAG, "hide all keyboard");
            return true;
        } else if (SET_TYPE.equals(action)) {
            this.mWebViewKeyBoardType = H5Utils.getString(event.getParam(), (String) "type");
            this.ifUseRandomNumber = TextUtils.equals("Y", H5Utils.getString(event.getParam(), (String) "randomnumber"));
            H5Log.d(TAG, "set keyboard type: " + this.mWebViewKeyBoardType + " randomnumber " + this.ifUseRandomNumber);
            return true;
        } else if (!"hideCustomInputMethod4NativeInput".equals(action)) {
            return false;
        } else {
            hideCustomKeyboard();
            return true;
        }
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (getH5InputBoardProvider() == null) {
            return false;
        }
        String action = event.getAction();
        if (CommonEvents.H5_PAGE_PHYSICAL_BACK.equals(action)) {
            H5Log.d(TAG, "H5_PAGE_PHYSICAL_BACK hide keyboard");
            if (this.mH5NativeOnSoftKeyboardListener != null) {
                this.mH5NativeOnSoftKeyboardListener.onBackPressed();
            }
            sendBlurToFocusElement();
            hideSoftInputFromWindow();
            return hideCustomKeyboard();
        } else if ("pushWindow".equals(action)) {
            H5Log.d(TAG, "PUSH_WINDOW hide keyboard");
            if (this.mH5NativeOnSoftKeyboardListener != null) {
                this.mH5NativeOnSoftKeyboardListener.onPushWindow();
            }
            hideAllKeyboard();
            return false;
        } else if (!CommonEvents.H5_PAGE_PAUSE.equals(action)) {
            return false;
        } else {
            H5Log.d(TAG, "H5_PAGE_PAUSE hide keyboard");
            hideAllKeyboard();
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void sendBlurToFocusElement() {
        if (this.mWebView != null) {
            this.mWebView.loadUrl("javascript:document.activeElement && document.activeElement.blur()");
        }
    }

    public boolean displaySoftKeyboard(String content, int inputType, ValueCallback<String> valueCallback) {
        H5Log.d(TAG, "inputType: " + inputType);
        if (needShowDisclaimerPrompt()) {
            H5WarningTipProvider warningTipProvider = (H5WarningTipProvider) H5Utils.getProvider(H5WarningTipProvider.class.getName());
            H5Service h5Service = H5ServiceUtils.getH5Service();
            if (!(warningTipProvider == null || h5Service == null)) {
                warningTipProvider.showWarningTip(h5Service.getTopH5Page(), 3, "");
            }
        }
        if (this.mH5NativeOnSoftKeyboardListener != null) {
            this.mH5NativeOnSoftKeyboardListener.displaySoftKeyboard(content, inputType);
        }
        boolean customKeyboardEnabled = isCustomKeyboardEnabled();
        H5Log.d(TAG, "isCustomKeyboardEnabled: " + customKeyboardEnabled);
        return displayKeyboard(inputType, valueCallback, customKeyboardEnabled);
    }

    private boolean isNumberInputType(int inputType) {
        return (inputType & 12290) != 0;
    }

    private boolean displayKeyboard(int inputType, ValueCallback<String> valueCallback, boolean customKeyboardEnabled) {
        boolean hasCustomInputType;
        H5Log.d(TAG, "displayKeyboard: " + inputType + ", customKeyboardEnabled: " + customKeyboardEnabled);
        this.mKeyboardCallback = valueCallback;
        boolean isNumberInputType = isNumberInputType(inputType);
        if (!customKeyboardEnabled || TextUtils.isEmpty(this.mWebViewKeyBoardType)) {
            hasCustomInputType = false;
        } else {
            hasCustomInputType = true;
        }
        if (isNumberInputType || hasCustomInputType) {
            hideSoftInputFromWindow();
            if (hasCustomInputType) {
                H5Log.d(TAG, "setKeyboardType: " + this.mWebViewKeyBoardType);
                getH5InputBoardProvider().setKeyboardType(this.mWebViewKeyBoardType);
                H5Log.d(TAG, "displayKeyboard ifUseRandomNumber: " + this.ifUseRandomNumber);
                getH5InputBoardProvider().setIfUseRandomNumber(this.ifUseRandomNumber);
                if (sNeedClearType) {
                    this.mWebViewKeyBoardType = null;
                }
            } else {
                getH5InputBoardProvider().setKeyboardType(null);
            }
            showCustomKeyboard();
            return true;
        }
        H5Log.d(TAG, "show system keyboard");
        if (getH5InputBoardProvider() == null || !getH5InputBoardProvider().isKeyboardShown()) {
            return false;
        }
        hideCustomKeyboard();
        return false;
    }

    public boolean hideSoftKeyboard() {
        H5Log.d(TAG, "hideSoftKeyboard");
        hideCustomKeyboard();
        return false;
    }

    public boolean onFinishComposingText() {
        return false;
    }

    private void hideAllKeyboard() {
        hideCustomKeyboard();
        hideSoftInputFromWindow();
    }

    private void showCustomKeyboard() {
        H5Log.d(TAG, "showCustomKeyboard");
        try {
            getH5InputBoardProvider().showKeyboard();
            if (this.mH5InputBoardProvider.getKeyboard() == null) {
                toggleSoftInput();
                showCustomKeyboardCallback(false);
                return;
            }
            showCustomKeyboardCallback(true);
        } catch (Exception exception) {
            H5Log.e((String) TAG, (Throwable) exception);
            toggleSoftInput();
            showCustomKeyboardCallback(false);
        }
    }

    private H5InputBoardProvider getH5InputBoardProvider() {
        if (this.mH5InputBoardProvider != null) {
            H5Log.debug(TAG, "h5InputBoardProvider != null return instance");
            return this.mH5InputBoardProvider;
        }
        H5Log.debug(TAG, "h5InputBoardProvider not set lazy init");
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            this.mH5InputBoardProvider = (H5InputBoardProvider) h5Service.getProviderManager().getProviderUseCache(H5InputBoardProvider.class.getName(), false);
        }
        if (this.mH5InputBoardProvider == null) {
            try {
                this.mH5InputBoardProvider = (H5InputBoardProvider) this.mFallbackClass.newInstance();
            } catch (Throwable throwable) {
                H5Log.e(TAG, "new fallback keyboard exception", throwable);
            }
        }
        if (this.mH5InputBoardProvider != null) {
            this.mH5InputBoardProvider.init(this.mContext, this.mApWebView);
            this.mH5InputBoardProvider.setOperateListener(this.mH5InputOperator);
        }
        return this.mH5InputBoardProvider;
    }

    private int getKeyboardHeight() {
        if (this.mH5InputBoardProvider == null || this.mH5InputBoardProvider.getKeyboard() == null) {
            return H5DimensionUtil.dip2px(this.mContext, 220.0f);
        }
        if (this.mH5InputBoardProvider.getKeyboard().getHeight() == 0) {
            return H5DimensionUtil.dip2px(this.mContext, 220.0f);
        }
        return this.mH5InputBoardProvider.getKeyboard().getHeight();
    }

    /* access modifiers changed from: private */
    public Rect getKeyboardScreenRect() {
        int[] location = new int[2];
        Rect localRc = new Rect();
        Rect frameRc = new Rect();
        if (!(this.mH5InputBoardProvider == null || this.mH5InputBoardProvider.getKeyboard() == null)) {
            this.mH5InputBoardProvider.getKeyboard().getLocationInWindow(location);
            this.mH5InputBoardProvider.getKeyboard().getLocalVisibleRect(localRc);
            this.mH5InputBoardProvider.getKeyboard().getWindowVisibleDisplayFrame(frameRc);
        }
        if (localRc.isEmpty()) {
            localRc.right = TrafficTopic.NOTIFY;
            localRc.bottom = getKeyboardHeight();
        } else {
            localRc.offsetTo(location[0], location[1]);
        }
        return localRc;
    }

    private void toggleSoftInput() {
        if (this.mWebView != null) {
            H5Log.d(TAG, H5PagePlugin.TOGGLE_SOFT_INPUT);
            InputMethodManager imm = (InputMethodManager) this.mWebView.getContext().getSystemService("input_method");
            if (imm != null && !imm.isActive(this.mWebView)) {
                imm.toggleSoftInput(0, 2);
            }
        }
    }

    private void hideSoftInputFromWindow() {
        InputMethodManager imm = (InputMethodManager) this.mWebView.getContext().getSystemService("input_method");
        if (imm != null) {
            imm.hideSoftInputFromWindow(this.mWebView.getWindowToken(), 2);
        }
    }

    /* access modifiers changed from: private */
    public boolean hideCustomKeyboard() {
        H5Log.d(TAG, "hideCustomKeyboard");
        if (!getH5InputBoardProvider().isKeyboardShown()) {
            H5Log.e((String) TAG, (String) "!getH5InputBoardProvider().isKeyboardShown()");
            return false;
        }
        try {
            H5Log.e((String) TAG, (String) "getH5InputBoardProvider().hideKeyboard()");
            getH5InputBoardProvider().hideKeyboard();
        } catch (Exception exception) {
            H5Log.e((String) TAG, (Throwable) exception);
        }
        if (this.mKeyboardCallback != null) {
            try {
                JSONObject params = new JSONObject();
                Rect scrRc = new Rect();
                params.put("keyboard-screen-rect-left", scrRc.left);
                params.put("keyboard-screen-rect-top", scrRc.top);
                params.put("keyboard-screen-rect-right", scrRc.right);
                params.put("keyboard-screen-rect-bottom", scrRc.bottom);
                params.put("CustomKeyBoard", 0);
                H5Log.d(TAG, "rect ltrb = " + scrRc.left + Token.SEPARATOR + scrRc.top + Token.SEPARATOR + scrRc.right + Token.SEPARATOR + scrRc.bottom + ", CustomKeyBoard: " + 0);
                this.mKeyboardCallback.onReceiveValue(params.toString());
            } catch (Exception ex) {
                H5Log.e(TAG, "hideCustomKeyboard", ex);
            }
        }
        return true;
    }

    private void showCustomKeyboardCallback(final boolean showCustom) {
        H5Log.d(TAG, "showCustomKeyboardCallback: " + showCustom);
        if (this.mH5InputBoardProvider != null && this.mH5InputBoardProvider.getKeyboard() != null) {
            this.mH5InputBoardProvider.getKeyboard().post(new Runnable() {
                public void run() {
                    try {
                        JSONObject params = new JSONObject();
                        Rect scrRc = H5NumInputKeyboardM57.this.getKeyboardScreenRect();
                        params.put("keyboard-screen-rect-left", scrRc.left);
                        params.put("keyboard-screen-rect-top", scrRc.top);
                        params.put("keyboard-screen-rect-right", scrRc.right);
                        params.put("keyboard-screen-rect-bottom", scrRc.bottom);
                        params.put("CustomKeyBoard", showCustom ? 1 : 0);
                        if (H5NumInputKeyboardM57.this.mKeyboardCallback != null) {
                            H5NumInputKeyboardM57.this.mKeyboardCallback.onReceiveValue(params.toString());
                        }
                        H5Log.d(H5NumInputKeyboardM57.TAG, "rect ltrb = " + scrRc.left + Token.SEPARATOR + scrRc.top + Token.SEPARATOR + scrRc.right + Token.SEPARATOR + scrRc.bottom + ", CustomKeyBoard: " + showCustom);
                    } catch (Exception ex) {
                        H5Log.e(H5NumInputKeyboardM57.TAG, "onReceiveValue exception", ex);
                    }
                }
            });
        }
    }
}
