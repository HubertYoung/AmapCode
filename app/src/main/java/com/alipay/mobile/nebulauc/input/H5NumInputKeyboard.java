package com.alipay.mobile.nebulauc.input;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5InputBoardProvider;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThirdDisclaimerUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.nativeinput.H5NativeOnSoftKeyboardListener;
import com.uc.webview.export.extension.UCExtension.OnSoftKeyboardListener;

public abstract class H5NumInputKeyboard extends H5SimplePlugin implements OnSoftKeyboardListener {
    private static final String GET_IF_RANDOM_JS = "document.activeElement && document.activeElement.getAttribute && document.activeElement.getAttribute(\"data-randomnumber\")";
    private static final String GET_INPUT_TYPE_JS = "document.activeElement && document.activeElement.getAttribute && document.activeElement.getAttribute(\"data-keyboard\")";
    private static boolean sConfigured = false;
    private static boolean sEnableCustomKeyboard = true;
    private static boolean sEnableCustomKeyboardInH5 = true;
    static boolean sNeedClearType = false;
    Class<? extends H5InputBoardProvider> mFallbackClass;
    H5NativeOnSoftKeyboardListener mH5NativeOnSoftKeyboardListener;
    private H5Page mH5Page = null;
    private boolean mHasShownDisclaimerHint = false;

    public void setFallback(Class<? extends H5InputBoardProvider> clazz) {
        this.mFallbackClass = clazz;
    }

    public String getInjectJS() {
        if (!isCustomKeyboardEnabled()) {
            return "";
        }
        return "document.addEventListener('click', function(event){if(event.target&&event.target.tagName.toLowerCase()=='input'){" + "AlipayJSBridge.call(\"setKeyboardType\", {\n  type: document.activeElement && document.activeElement.getAttribute && document.activeElement.getAttribute(\"data-keyboard\")  ,randomnumber: document.activeElement && document.activeElement.getAttribute && document.activeElement.getAttribute(\"data-randomnumber\")})" + ";}}, true);document.addEventListener('focus', function(event){" + "AlipayJSBridge.call(\"setKeyboardType\", {\n  type: document.activeElement && document.activeElement.getAttribute && document.activeElement.getAttribute(\"data-keyboard\")  ,randomnumber: document.activeElement && document.activeElement.getAttribute && document.activeElement.getAttribute(\"data-randomnumber\")})" + ";}, true);";
    }

    public void onInitialize(H5CoreNode coreNode) {
        super.onInitialize(coreNode);
        if (coreNode instanceof H5Page) {
            this.mH5Page = (H5Page) coreNode;
        }
    }

    public void setH5NativeOnSoftKeyboardListener(H5NativeOnSoftKeyboardListener h5NativeOnSoftKeyboardListener) {
        this.mH5NativeOnSoftKeyboardListener = h5NativeOnSoftKeyboardListener;
    }

    /* access modifiers changed from: 0000 */
    public boolean isCustomKeyboardEnabled() {
        boolean isTinyApp = false;
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            H5Page h5Page = h5Service.getTopH5Page();
            if (h5Page != null) {
                isTinyApp = H5Utils.getBoolean(h5Page.getParams(), (String) "isTinyApp", false);
            }
        }
        getConfig();
        if (sEnableCustomKeyboardInH5 || isTinyApp) {
            return sEnableCustomKeyboard;
        }
        return true;
    }

    private static void getConfig() {
        if (!sConfigured) {
            sConfigured = true;
            H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (provider != null) {
                if ("NO".equals(provider.getConfig("h5_useCustomKeyboardInH5"))) {
                    sEnableCustomKeyboardInH5 = false;
                }
                String enableCustomKeyboard = provider.getConfig("h5_useCustomKeyboard");
                if (!TextUtils.isEmpty(enableCustomKeyboard)) {
                    sEnableCustomKeyboard = "true".equals(enableCustomKeyboard);
                }
                String customKeyboardClearType = provider.getConfig("h5_customKeyboardClearType");
                if (!TextUtils.isEmpty(customKeyboardClearType)) {
                    sNeedClearType = "true".equals(customKeyboardClearType);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean needShowDisclaimerPrompt() {
        if (this.mH5Page == null || this.mHasShownDisclaimerHint || !H5ThirdDisclaimerUtils.isNeedKeyboardHint() || H5ThirdDisclaimerUtils.needShowDisclaimer(this.mH5Page.getParams(), "") == 0) {
            return false;
        }
        this.mHasShownDisclaimerHint = true;
        return true;
    }
}
