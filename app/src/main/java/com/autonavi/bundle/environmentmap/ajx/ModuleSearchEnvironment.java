package com.autonavi.bundle.environmentmap.ajx;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("search_envmap")
@KeepPublicClassMembers
@KeepName
public class ModuleSearchEnvironment extends AbstractModule {
    public static final String MODULE_NAME = "search_envmap";
    private a mUiListener;

    public interface a {
        void a(JsFunctionCallback jsFunctionCallback);

        void a(String str);

        void b(JsFunctionCallback jsFunctionCallback);

        void b(String str);

        void c();

        void c(String str);

        void d();

        void d(String str);
    }

    public ModuleSearchEnvironment(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setmUiListener(a aVar) {
        this.mUiListener = aVar;
    }

    @AjxMethod("changeLayer")
    public void changeLayer(String str) {
        if (this.mUiListener != null) {
            this.mUiListener.b(str);
        }
    }

    @AjxMethod("registerPoiHighlightedCallback")
    public void registerPoiHighlightedCallback(JsFunctionCallback jsFunctionCallback) {
        if (this.mUiListener != null) {
            this.mUiListener.a(jsFunctionCallback);
        }
    }

    @AjxMethod("registerPoiUnhighlightedCallback")
    public void registerPoiUnhighlightedCallback(JsFunctionCallback jsFunctionCallback) {
        if (this.mUiListener != null) {
            this.mUiListener.b(jsFunctionCallback);
        }
    }

    @AjxMethod("unHighlightPoi")
    public void unHighlightPoi() {
        if (this.mUiListener != null) {
            this.mUiListener.c();
        }
    }

    @AjxMethod("tipHeightDidChange")
    public void tipHeightDidChange(String str) {
        if (this.mUiListener != null) {
            this.mUiListener.c(str);
        }
    }

    @AjxMethod("tipAnimationWillStart")
    public void tipAnimationWillStart() {
        if (this.mUiListener != null) {
            this.mUiListener.d();
        }
    }

    @AjxMethod("didReceiveBubbleLabel")
    public void didReceiveBubbleLabel(String str) {
        if (this.mUiListener != null) {
            this.mUiListener.d(str);
        }
    }

    @AjxMethod("share")
    public void share(String str) {
        if (this.mUiListener != null && !TextUtils.isEmpty("mapPath") && !TextUtils.isEmpty("ajxPath")) {
            this.mUiListener.a(str);
        }
    }
}
