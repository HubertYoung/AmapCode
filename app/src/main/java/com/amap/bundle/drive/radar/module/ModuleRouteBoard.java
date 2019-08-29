package com.amap.bundle.drive.radar.module;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.util.ArrayList;
import java.util.List;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@AjxModule("route_board")
@Keep
public final class ModuleRouteBoard extends AbstractModule {
    public static final String MODULE_NAME = "route_board";
    private final int WIDTH_BACKUP = agn.a((Context) AMapAppGlobal.getApplication(), 22.0f);
    private List<mq> mDriveSwitchSceneCallback = new ArrayList();
    public JsFunctionCallback mJstNonresponsiblityOnclickCallback = null;
    private a mOnRouteBoardCallback = null;

    public interface a {
        void a();

        void a(String str, JsFunctionCallback jsFunctionCallback);
    }

    private static float get3DLineWidthScaleByLevel(float f) {
        if (f >= 20.0f) {
            return 2.0f;
        }
        if (f >= 19.0f) {
            return 1.6f;
        }
        return f >= 18.0f ? 1.0f : 1.0f;
    }

    public ModuleRouteBoard(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public final void setOnRouteBoardCallback(a aVar) {
        this.mOnRouteBoardCallback = aVar;
    }

    @AjxMethod("showTrafficEventDetail")
    public final void showTrafficEventDetail(String str, JsFunctionCallback jsFunctionCallback) {
        ku a2 = ku.a();
        StringBuilder sb = new StringBuilder("showTrafficEventDetail          mOnRouteBoardCallback:");
        sb.append(this.mOnRouteBoardCallback);
        sb.append("    json:");
        sb.append(str);
        a2.c("15142333", sb.toString());
        if (this.mOnRouteBoardCallback != null && !TextUtils.isEmpty(str)) {
            this.mOnRouteBoardCallback.a(str, jsFunctionCallback);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "dismissTrafficEventView")
    public final void dismissTrafficEventView() {
        if (this.mOnRouteBoardCallback != null) {
            this.mOnRouteBoardCallback.a();
        }
    }

    public final void addSwitchSceneListener(mq mqVar) {
        this.mDriveSwitchSceneCallback.add(mqVar);
    }

    public final void removeSwitchSceneListener(mq mqVar) {
        if (mqVar != null && this.mDriveSwitchSceneCallback.contains(mqVar)) {
            this.mDriveSwitchSceneCallback.remove(mqVar);
        }
    }

    @AjxMethod("jump")
    public final void startPage(String str, String str2) {
        StringBuilder sb = new StringBuilder("jump = ");
        sb.append(str);
        sb.append("\n");
        sb.append(str2);
        AMapLog.d("Radar", sb.toString());
        if (this.mDriveSwitchSceneCallback != null) {
            for (mq a2 : this.mDriveSwitchSceneCallback) {
                a2.a(str, str2);
            }
        }
    }

    @AjxMethod("registerNonresponsiblityOnclick")
    public final void registerNonresponsiblityOnclick(JsFunctionCallback jsFunctionCallback) {
        AMapLog.d("Radar", "registerNonresponsiblityOnclick ");
        this.mJstNonresponsiblityOnclickCallback = jsFunctionCallback;
    }
}
