package com.autonavi.bundle.searchresult.ajx;

import android.support.annotation.UiThread;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.common.SuperId;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.taobao.agoo.control.data.BaseDO;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("idqplus")
@KeepPublicClassMembers
@KeepName
public class ModuleIdqPlus extends AbstractModule {
    public static final String FULL = "1";
    public static final String HALF = "2";
    public static final String INVALID = "invalid";
    public static final String MODULE_NAME = "idqplus";
    public static final String MOVE = "4";
    public static final String TIPS = "3";
    private b mCallbackReadyListener;
    private a mEventListener;
    private JsFunctionCallback mMapallback;
    private c mOverlayEventListener;
    private JsFunctionCallback mSetViewStateChangeCallback;

    public enum IdqMaxCMD {
        CMD_OVERLAY_CLEAR(0),
        CMD_OVERLAY_CLICK_SUB(1),
        CMD_GPS_CLICK(2),
        CMD_SUB_BACK(3),
        CMD_IDQ_MAX_BACK(4),
        CMD_IDQ_MAX_TRAFFIC_CLICKED(6);
        
        private int cmd;

        private IdqMaxCMD(int i) {
            this.cmd = i;
        }

        public final int getCmd() {
            return this.cmd;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public interface a {
        @UiThread
        void a(int i);

        @UiThread
        void a(String str, String str2);

        @UiThread
        void a(String str, String str2, boolean z);
    }

    public interface b {
        void a();
    }

    public interface c {
        void a();

        void a(String str);
    }

    public ModuleIdqPlus(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void sendIdqMaxCmd(IdqMaxCMD idqMaxCMD, Map<String, String> map) {
        if (this.mMapallback != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(BaseDO.JSON_CMD, idqMaxCMD.getCmd());
                if (map != null && map.size() > 0) {
                    for (Entry next : map.entrySet()) {
                        jSONObject.put((String) next.getKey(), next.getValue());
                    }
                }
                this.mMapallback.callback(jSONObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @AjxMethod("registerSetPageStateCallback")
    public void setDetailViewStateCallback(JsFunctionCallback jsFunctionCallback) {
        this.mSetViewStateChangeCallback = jsFunctionCallback;
        if (this.mCallbackReadyListener != null) {
            this.mCallbackReadyListener.a();
        }
    }

    @AjxMethod("registerCallback")
    public void registerCallback(JsFunctionCallback jsFunctionCallback) {
        this.mMapallback = jsFunctionCallback;
    }

    @AjxMethod("onPageStateDidChanged")
    public void onPageStateDidChanged(String str, String str2) {
        if (this.mEventListener != null) {
            this.mEventListener.a(str, str2, true);
        }
    }

    @AjxMethod("onStartAnimateToState")
    public void onStartAnimateToState(String str, String str2) {
        if (this.mEventListener != null) {
            this.mEventListener.a(str, str2);
        }
    }

    @AjxMethod("onCitySearchWithAdcodeAndKeyword")
    public void onCitySearchWithAdcodeAndKeyword(String str, String str2) {
        bwx bwx = new bwx(str2, -1, false);
        InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), str2, DoNotUseTool.getPixel20Bound());
        a2.utd_sceneid = "400002";
        a2.search_operate = 1;
        a2.superid = SuperId.getInstance().getScenceId();
        a2.city = String.valueOf(str);
        ekv ekv = new ekv();
        bvt bvt = new bvt();
        bvt.e = bwx.e;
        bvt.b = bwx.a;
        bvt.d = bwx;
        SearchUtils.getCenterPoint(a2.geoobj);
        ekv.a(a2, 1, bvt);
    }

    @AjxMethod("onSubPoiClicked")
    public void onSubPoiClick(String str) {
        if (this.mOverlayEventListener != null) {
            this.mOverlayEventListener.a(str);
        }
    }

    @AjxMethod("onMainPoiClicked")
    public void onMainPoiClicked() {
        if (this.mOverlayEventListener != null) {
            this.mOverlayEventListener.a();
        }
    }

    public void setOnAjxCallbackReadyListener(b bVar) {
        this.mCallbackReadyListener = bVar;
    }

    public void setOnStateChangeListener(a aVar) {
        this.mEventListener = aVar;
    }

    public void setOverlayEventListener(c cVar) {
        this.mOverlayEventListener = cVar;
    }

    public void setViewState(String str, boolean z) {
        if (this.mSetViewStateChangeCallback != null) {
            this.mSetViewStateChangeCallback.callback(str, Boolean.valueOf(z));
        }
    }

    public void onTopHeightChange(int i) {
        if (this.mEventListener != null) {
            this.mEventListener.a(i);
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        this.mCallbackReadyListener = null;
        this.mOverlayEventListener = null;
        this.mEventListener = null;
    }

    @AjxMethod("getScenicOpenLayerData")
    public void getScenicOpenLayerData(String str, JsFunctionCallback jsFunctionCallback) {
        String str2 = "";
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null) {
            Iterator<LayerItem> it = awo.i().iterator();
            while (it.hasNext()) {
                LayerItem next = it.next();
                try {
                    if (next.getLayer_id() == Integer.parseInt(str)) {
                        str2 = next.getData();
                    }
                } catch (NumberFormatException unused) {
                }
            }
        }
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(str2);
        }
    }
}
