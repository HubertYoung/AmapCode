package com.amap.bundle.openlayer.ajx;

import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

@AjxModule("openLayer")
public class ModuleOpenLayer extends AbstractModule {
    private awo openLayerService;

    public ModuleOpenLayer(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.openLayerService = null;
        this.openLayerService = (awo) a.a.a(awo.class);
    }

    @AjxMethod("getLayerList")
    public void getLayerList(JsFunctionCallback jsFunctionCallback) {
        try {
            ArrayList<LayerItem> i = this.openLayerService.i();
            JSONArray jSONArray = new JSONArray();
            Iterator<LayerItem> it = i.iterator();
            while (it.hasNext()) {
                LayerItem next = it.next();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("item_id", next.getItem_id());
                jSONObject.put("layer_id", next.getLayer_id());
                jSONObject.put("layer_type", next.getLayer_type());
                jSONObject.put("name", next.getName());
                jSONObject.put(H5Param.MENU_ICON, next.getIcon());
                jSONObject.put("data", next.getData());
                jSONObject.put("action_url", next.getAction_url());
                jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, next.getStart_time());
                jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, next.getEnd_time());
                jSONObject.put("control", next.getControl_status());
                jSONObject.put(FunctionSupportConfiger.SWITCH_TAG, next.getSwitch_status());
                jSONObject.put(H5PermissionManager.level, next.getLevel());
                jSONObject.put("toast", next.getToast());
                jSONObject.put("is_new", next.isIs_new());
                jSONArray.put(jSONObject);
            }
            jsFunctionCallback.callback(jSONArray.toString());
        } catch (Throwable unused) {
        }
    }
}
