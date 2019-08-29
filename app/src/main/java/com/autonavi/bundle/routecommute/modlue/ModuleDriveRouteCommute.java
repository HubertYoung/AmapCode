package com.autonavi.bundle.routecommute.modlue;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.ajx.tbt.CAjxBLBinaryCenter;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("drive_route_commute")
public class ModuleDriveRouteCommute extends AbstractModule {
    public static final String MODULE_NAME = "drive_route_commute";
    private List<mq> mDriveSwitchSceneCallback = new ArrayList();
    public JsFunctionCallback mJstNonresponsiblityOnclickCallback = null;
    private a mOnRouteBoardCallback = null;
    private AbstractBaseMapPage mPage;

    public interface a {
        void a();

        void a(String str, JsFunctionCallback jsFunctionCallback);
    }

    public ModuleDriveRouteCommute(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setPage(AbstractBaseMapPage abstractBaseMapPage) {
        this.mPage = abstractBaseMapPage;
    }

    public void setOnRouteBoardCallback(a aVar) {
        this.mOnRouteBoardCallback = aVar;
    }

    @AjxMethod("showTrafficEventDetail")
    public void showTrafficEventDetail(String str, JsFunctionCallback jsFunctionCallback) {
        StringBuilder sb = new StringBuilder("showTrafficEventDetail          mOnRouteBoardCallback:");
        sb.append(this.mOnRouteBoardCallback);
        sb.append("    json:");
        sb.append(str);
        azb.a("15142333", sb.toString());
        if (this.mOnRouteBoardCallback != null && !TextUtils.isEmpty(str)) {
            this.mOnRouteBoardCallback.a(str, jsFunctionCallback);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "dismissTrafficEventView")
    public void dismissTrafficEventView() {
        if (this.mOnRouteBoardCallback != null) {
            this.mOnRouteBoardCallback.a();
        }
    }

    @AjxMethod("removeBinaryDataS")
    public void removeBinaryDataS(int i) {
        CAjxBLBinaryCenter.removeBinaryDataS(i);
    }

    public void addSwitchSceneListener(mq mqVar) {
        this.mDriveSwitchSceneCallback.add(mqVar);
    }

    public void removeSwitchSceneListener(mq mqVar) {
        if (mqVar != null && this.mDriveSwitchSceneCallback.contains(mqVar)) {
            this.mDriveSwitchSceneCallback.remove(mqVar);
        }
    }

    @AjxMethod("jump")
    public void startPage(String str, String str2) {
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

    @AjxMethod("getCommuteHomeAndCompany")
    public void getCommuteHomeAndCompany(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && jsFunctionCallback != null) {
            JSONObject jSONObject = new JSONObject();
            if (str.contains("home") && str.contains("company")) {
                jSONObject = azf.a();
            } else if (str.contains("home")) {
                try {
                    jSONObject.putOpt("home", azf.a().optJSONObject("home"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (str.contains("company")) {
                try {
                    jSONObject.putOpt("company", azf.a().optJSONObject("company"));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            jsFunctionCallback.callback(jSONObject.toString());
        }
    }

    @AjxMethod("saveCommuteHomeAndCompany")
    public void saveCommuteHomeAndCompany(final String str, final JsFunctionCallback jsFunctionCallback) {
        azf.a((defpackage.azf.a) new defpackage.azf.a() {
            public final void a(NaviAddress naviAddress) {
                String access$000 = ModuleDriveRouteCommute.this.savePoi(naviAddress, str);
                jsFunctionCallback.callback(access$000);
            }
        });
    }

    @AjxMethod("registerNonresponsiblityOnclick")
    public void registerNonresponsiblityOnclick(JsFunctionCallback jsFunctionCallback) {
        AMapLog.d("Radar", "registerNonresponsiblityOnclick ");
        this.mJstNonresponsiblityOnclickCallback = jsFunctionCallback;
    }

    /* access modifiers changed from: private */
    public String savePoi(NaviAddress naviAddress, String str) {
        com com2 = (com) ank.a(com.class);
        if (com2 == null) {
            return "1";
        }
        cop b = com2.b(com2.a());
        if (b == null) {
            return "1";
        }
        if (str.contains("home")) {
            if (naviAddress.home.getHome() == null) {
                return "1";
            }
            b.f(naviAddress.home.getHome().clone());
            return "0";
        } else if (!str.contains("company") || naviAddress.company.getCompany() == null) {
            return "1";
        } else {
            b.e(naviAddress.company.getCompany().clone());
            return "0";
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getAosUrl")
    public String getAosUrl() {
        return ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.DRIVE_AOS_URL_KEY);
    }
}
