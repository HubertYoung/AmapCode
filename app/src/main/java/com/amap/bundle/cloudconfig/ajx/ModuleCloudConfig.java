package com.amap.bundle.cloudconfig.ajx;

import android.text.TextUtils;
import android.util.Log;
import com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.a;
import com.autonavi.minimap.ajx3.AjxConstant;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@AjxModule("amap_cloudConfig")
public class ModuleCloudConfig extends AbstractModule {
    public static final String MODULE_NAME = "amap_cloudConfig";

    public ModuleCloudConfig(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("getConfig")
    public void getConfig(String str, JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(lo.a().a(str));
    }

    @AjxMethod(invokeMode = "sync", value = "getConfigSync")
    @Deprecated
    public String getConfigSync(String str) {
        return lo.a().a(str);
    }

    @AjxMethod(invokeMode = "sync", value = "getCloudConfigSync")
    public String getCloudConfigSync(String str) {
        return lo.a().a(str);
    }

    @AjxMethod("getConfigs")
    public void getConfigs(String str, JsFunctionCallback jsFunctionCallback) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            JSONObject jSONObject = new JSONObject();
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                String string = jSONArray.getString(i);
                jSONObject.put(string, lo.a().a(string));
            }
            jsFunctionCallback.callback(jSONObject.toString());
        } catch (Exception e) {
            if (AjxConstant.IS_DEBUG) {
                LogHelper.loge(Log.getStackTraceString(e));
            }
            e.printStackTrace();
        }
    }

    @AjxMethod("updateConfig")
    public void updateConfig(String str, final JsFunctionCallback jsFunctionCallback) {
        lo a = lo.a();
        AnonymousClass1 r1 = new lp() {
            public final void onConfigResultCallBack(int i, String str) {
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }

            public final void onConfigCallBack(int i) {
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(Integer.valueOf(i));
                }
            }
        };
        if (!TextUtils.isEmpty(str) && a.e("updateModuleConfig() not init")) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            a.a.a((List<String>) arrayList, (a) new a(str, r1) {
                final /* synthetic */ String a;
                final /* synthetic */ lp b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public final boolean a(ArrayList<lq> arrayList) {
                    lq lqVar = arrayList.get(0);
                    if (lqVar == null || !this.a.equals(lqVar.a)) {
                        return false;
                    }
                    if (lqVar.b == 2) {
                        String a2 = lo.this.a(this.a);
                        this.b.onConfigCallBack(2);
                        this.b.onConfigResultCallBack(4, a2);
                    } else {
                        this.b.onConfigResultCallBack(lqVar.b, lqVar.d);
                    }
                    return true;
                }

                public final void a(int i, List<String> list) {
                    this.b.onConfigCallBack(i);
                }
            });
        }
    }

    @AjxMethod("addCloudListener")
    public void addCloudListener(String str, final JsFunctionCallback jsFunctionCallback) {
        lo.a().a(str, (lp) new lp() {
            public final void onConfigCallBack(int i) {
            }

            public final void onConfigResultCallBack(int i, String str) {
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }
        });
    }
}
