package com.autonavi.bundle.onekeycheck.ajx;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("common_detection")
@KeepPublicClassMembers
@KeepName
public class ModuleDetection extends AbstractModule implements a {
    public static final String MODULE_NAME = "common_detection";
    private JsFunctionCallback mJcFuncCallBack;
    private dsl mOneKeyCheckManager;

    public ModuleDetection(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("startDetect")
    public void startDetect(JsFunctionCallback jsFunctionCallback) {
        cancelCheck();
        this.mOneKeyCheckManager = new dsl();
        this.mJcFuncCallBack = jsFunctionCallback;
        dsl dsl = this.mOneKeyCheckManager;
        dsl.b = this;
        if (dsl.a == null) {
            dsl.a = new dsn(dsl);
        }
        dsl.a.start();
    }

    @AjxMethod("cancelDetect")
    public void cancelDetect() {
        cancelCheck();
    }

    private void cancelCheck() {
        if (this.mOneKeyCheckManager != null) {
            this.mOneKeyCheckManager.a();
        }
    }

    public void onFinish(String str) {
        if (this.mJcFuncCallBack != null) {
            if (TextUtils.isEmpty(str)) {
                str = "";
            }
            this.mJcFuncCallBack.callback(str);
            this.mOneKeyCheckManager = null;
        }
    }
}
