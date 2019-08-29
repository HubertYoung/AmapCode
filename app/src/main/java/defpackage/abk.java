package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: abk reason: default package */
/* compiled from: Parser */
public final class abk extends AbstractAOSParser {
    public awq a = null;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) {
        JSONObject parseHeader = parseHeader(bArr);
        if (parseHeader != null && this.result) {
            this.a = new awq();
            awp awp = new awp();
            ArrayList<LayerItem> arrayList = null;
            JSONArray optJSONArray = parseHeader.optJSONArray("layer_list");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                arrayList = new ArrayList<>();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        LayerItem layerItem = new LayerItem();
                        layerItem.setOrder(i);
                        layerItem.setItem_id(optJSONObject.optInt("id"));
                        layerItem.setLayer_id(optJSONObject.optInt("layer_id"));
                        layerItem.setLayer_type(optJSONObject.optInt("layer_type"));
                        layerItem.setName(optJSONObject.optString("name", "图层"));
                        layerItem.setIcon(optJSONObject.optString(H5Param.MENU_ICON));
                        layerItem.setData(optJSONObject.optString("data"));
                        layerItem.setAction_url(optJSONObject.optString("action_url"));
                        layerItem.setStart_time(optJSONObject.optLong(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME));
                        layerItem.setEnd_time(optJSONObject.optLong(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME));
                        layerItem.setControl_status(optJSONObject.optInt("control"));
                        layerItem.setSwitch_Status(optJSONObject.optInt(FunctionSupportConfiger.SWITCH_TAG));
                        layerItem.setLevel(optJSONObject.optInt(H5PermissionManager.level));
                        layerItem.setToast(optJSONObject.optString("toast"));
                        arrayList.add(layerItem);
                    }
                }
                awp.c = arrayList;
            }
            String optString = parseHeader.optString("md5");
            awp.b = optString;
            String c = abm.c();
            if ((arrayList == null || arrayList.size() == 0) && !TextUtils.isEmpty(optString) && !optString.equals(c)) {
                awp.a = true;
            }
            this.a.a = awp;
        }
    }
}
