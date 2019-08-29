package com.autonavi.common.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.model.POI;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONObject;

public class PhoneUtil$1 implements OnItemClickListener {
    final /* synthetic */ Activity val$context;
    final /* synthetic */ bje val$dlg;
    final /* synthetic */ String val$fromPage;
    final /* synthetic */ POI val$poi;

    public PhoneUtil$1(bje bje, Activity activity, String str, POI poi) {
        this.val$dlg = bje;
        this.val$context = activity;
        this.val$fromPage = str;
        this.val$poi = poi;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.val$dlg.dismiss();
        String str = (String) this.val$dlg.a.getItem(i);
        if (!TextUtils.isEmpty(str)) {
            if (str.contains("$")) {
                str = str.substring(str.lastIndexOf("$") + 1);
            }
            bnz.a(str);
        }
        if (this.val$fromPage.equals(LogConstant.SEARCH_POI_DETAIL)) {
            try {
                JSONObject jSONObject = new JSONObject();
                if (this.val$poi != null) {
                    jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, this.val$poi.getId());
                    jSONObject.put("type", this.val$poi.getType());
                    jSONObject.put("name", this.val$poi.getName());
                    jSONObject.put("itemId", i);
                }
                LogManager.actionLogV2(this.val$fromPage, "B001", jSONObject);
            } catch (Exception unused) {
            }
        }
    }
}
