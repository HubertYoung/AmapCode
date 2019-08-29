package com.autonavi.bundle.scenicarea.ajx;

import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("search_scenicarea")
@KeepPublicClassMembers
@KeepName
public class ModuleSearchScenic extends AbstractModule {
    public static final String MODULE_NAME = "search_scenicarea";
    private JsFunctionCallback mMoveSearchListener;
    private JsFunctionCallback mNativeJSBridge;
    private JsFunctionCallback mShowDetailPageCallback;
    private a mUiListener;

    public interface a {
        void a();

        void a(String str);

        void b();

        void b(String str);

        String c(String str);
    }

    public ModuleSearchScenic(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setUiListener(a aVar) {
        this.mUiListener = aVar;
    }

    @AjxMethod("locateMe")
    public void locateMe() {
        if (this.mUiListener != null) {
            this.mUiListener.a();
        }
    }

    @AjxMethod("tabHeightDidChange")
    public void tabHeightDidChange(String str) {
        if (this.mUiListener != null) {
            this.mUiListener.a(str);
        }
    }

    @AjxMethod("changeWidgetVisibility")
    public void changeWidgetVisibility(String str) {
        if (this.mUiListener != null) {
            this.mUiListener.b(str);
        }
    }

    @AjxMethod("getScenicOpenLayerData")
    public void getScenicOpenLayerData(String str, JsFunctionCallback jsFunctionCallback) {
        if (this.mUiListener != null && jsFunctionCallback != null) {
            jsFunctionCallback.callback(this.mUiListener.c(str));
        }
    }

    @AjxMethod("backToMainMap")
    public void backToMainMap() {
        if (this.mUiListener != null) {
            this.mUiListener.b();
        }
    }

    @AjxMethod(invokeMode = "sync", value = "tid")
    public String tid() {
        return NetworkParam.getTaobaoID();
    }
}
