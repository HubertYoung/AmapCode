package com.autonavi.minimap.ajx3.modules.internalmodules;

import com.autonavi.minimap.ajx3.AjxSoftKeyboardListener;
import com.autonavi.minimap.ajx3.AjxSoftKeyboardListener.SoftKeyboardChangeListener;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("ajx.keyboard")
public class AjxModuleKeyboard extends AbstractModule implements SoftKeyboardChangeListener {
    public static final String MODULE_NAME = "ajx.keyboard";
    private JsFunctionCallback mJsKeyboardStateChangeCallback;

    public AjxModuleKeyboard(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("addStatusChangedListener")
    public void regiestKeyboardStatusChangedCallBack(JsFunctionCallback jsFunctionCallback) {
        this.mJsKeyboardStateChangeCallback = jsFunctionCallback;
        AjxSoftKeyboardListener.getInstance().addListener(this);
    }

    @AjxMethod("removeStatusChangedListener")
    public void unregiestKeyboardStatusChangedCallBack() {
        this.mJsKeyboardStateChangeCallback = null;
        AjxSoftKeyboardListener.getInstance().removeListener(this);
    }

    public void onSoftKeyboardShown(int i) {
        if (this.mJsKeyboardStateChangeCallback != null) {
            this.mJsKeyboardStateChangeCallback.callback(Boolean.TRUE);
        }
    }

    public void onSoftKeyboardHidden(int i) {
        if (this.mJsKeyboardStateChangeCallback != null) {
            this.mJsKeyboardStateChangeCallback.callback(Boolean.FALSE);
        }
    }

    public void onModuleDestroy() {
        AjxSoftKeyboardListener.getInstance().removeListener(this);
        super.onModuleDestroy();
    }
}
