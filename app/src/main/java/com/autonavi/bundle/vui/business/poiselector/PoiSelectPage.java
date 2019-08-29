package com.autonavi.bundle.vui.business.poiselector;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.amap.bundle.voiceservice.dispatch.IVoicePoiSelectorDispatcher;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.bundle.vui.business.poiselector.module.ModulePoiSelector;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.vcs.NativeVcsManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PoiSelectPage extends Ajx3Page implements bgm, IVoiceCmdResponder {
    private boolean a = false;
    /* access modifiers changed from: private */
    public aia b;
    private ModulePoiSelector c;
    private asv d;
    private aif e = new aif() {
        public final void a(int i) {
            if (PoiSelectPage.this.b == null || !PoiSelectPage.this.b.a(PoiSelectPage.b(PoiSelectPage.this), (String) "cancel", i)) {
                bft.a(i);
            }
        }

        public final void a(int i, String str) {
            if (PoiSelectPage.this.b != null) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    int optInt = jSONObject.optInt("index", -1);
                    int optInt2 = jSONObject.optInt("loopIndex", -1);
                    int optInt3 = jSONObject.optInt("type", -1);
                    ArrayList arrayList = new ArrayList();
                    Pair pair = new Pair("type", Integer.valueOf(optInt3));
                    Pair pair2 = new Pair("index", Integer.valueOf(optInt));
                    Pair pair3 = new Pair("loopIndex", Integer.valueOf(optInt2));
                    arrayList.add(pair);
                    arrayList.add(pair2);
                    arrayList.add(pair3);
                    if (PoiSelectPage.this.b.a(PoiSelectPage.b(PoiSelectPage.this), (String) "selectPoi", i, (List<Pair<String, Object>>) arrayList)) {
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            bft.a(i);
        }
    };

    public static class a {
        public static WeakReference<bid> a;
    }

    public long getScene() {
        return 2199023255552L;
    }

    public long getScenesID() {
        return 2199023255552L;
    }

    public boolean isInnerPage() {
        return true;
    }

    public boolean needKeepSessionAlive() {
        return true;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.b = (aia) defpackage.esb.a.a.a(aia.class);
        this.c = (ModulePoiSelector) Ajx.getInstance().getModuleIns(iAjxContext, ModulePoiSelector.MODULE_NAME);
        if (this.c != null && this.d != null) {
            this.c.setPoiSelectorResult(this.d);
        }
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments.containsKey("requestData")) {
            arguments.putString("jsData", arguments.getString("requestData"));
        }
        if (arguments.containsKey("onPoiSelectorResult")) {
            this.d = (asv) arguments.get("onPoiSelectorResult");
        }
        getArguments().putString("url", "path://amap_bundle_globalvoice/src/business/selectpoi/pages/VuiSelectPoiPage.page.js");
        super.onCreate(context);
        a.a = new WeakReference<>(this);
        aho.a(new Runnable() {
            public final void run() {
                if (PoiSelectPage.this.mAjxView != null) {
                    ModuleVUI moduleVUI = (ModuleVUI) PoiSelectPage.this.mAjxView.getJsModule(ModuleVUI.MODULE_NAME);
                    if (moduleVUI != null) {
                        moduleVUI.onPoiSelectNotifyResult();
                    }
                }
            }
        }, 1000);
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            this.a = mapView.s();
        }
        IVoicePoiSelectorDispatcher iVoicePoiSelectorDispatcher = (IVoicePoiSelectorDispatcher) ank.a(IVoicePoiSelectorDispatcher.class);
        if (iVoicePoiSelectorDispatcher != null) {
            iVoicePoiSelectorDispatcher.setPoiSelectorApiControlListener(this.e);
        }
    }

    public void resume() {
        super.resume();
        bty mapView = getMapManager().getMapView();
        boolean booleanValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        if (mapView != null) {
            mapView.b(booleanValue);
        }
    }

    public bgo getPresenter() {
        return new bgo() {
            public final boolean handleVUICmd(bgb bgb, bfb bfb) {
                if (bgb == null) {
                    return false;
                }
                String str = bgb.d;
                char c = 65535;
                int hashCode = str.hashCode();
                if (hashCode != -934813676) {
                    if (hashCode == 114843 && str.equals("tip")) {
                        c = 0;
                    }
                } else if (str.equals("refuse")) {
                    c = 1;
                }
                switch (c) {
                    case 0:
                        d.a.a(bgb.a, 10000, (String) null, false);
                        return true;
                    case 1:
                        PoiSelectPage.a(PoiSelectPage.this, bgb);
                        return true;
                    default:
                        return false;
                }
            }
        };
    }

    public JSONObject getScenesData() {
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("jsData"))) {
            try {
                JSONArray jSONArray = new JSONObject(getArguments().getString("jsData")).getJSONArray("poi_list");
                if (jSONArray != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("dynamic_list", jSONArray);
                    return jSONObject;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public void finishSelf() {
        finish();
    }

    public void pause() {
        super.pause();
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            mapView.b(this.a);
        }
    }

    public void destroy() {
        super.destroy();
        IVoicePoiSelectorDispatcher iVoicePoiSelectorDispatcher = (IVoicePoiSelectorDispatcher) ank.a(IVoicePoiSelectorDispatcher.class);
        if (iVoicePoiSelectorDispatcher != null) {
            iVoicePoiSelectorDispatcher.setPoiSelectorApiControlListener(null);
        }
        if (this.c != null) {
            this.c.setPoiSelectorResult(null);
        }
    }

    static /* synthetic */ IAjxContext b(PoiSelectPage poiSelectPage) {
        if (poiSelectPage.mAjxView == null) {
            return null;
        }
        return poiSelectPage.mAjxView.getAjxContext();
    }

    static /* synthetic */ void a(PoiSelectPage poiSelectPage, bgb bgb) {
        poiSelectPage.finish();
        NativeVcsManager.getInstance().stopListening();
        final int i = bgb.a;
        aho.a(new Runnable() {
            public final void run() {
                d.a.a(i, 10000, (String) null, false);
            }
        });
    }
}
