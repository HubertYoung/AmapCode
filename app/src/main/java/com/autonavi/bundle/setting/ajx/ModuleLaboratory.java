package com.autonavi.bundle.setting.ajx;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("laboratory")
public class ModuleLaboratory extends AbstractModule {
    public ModuleLaboratory(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("laboratoryItemSwitchChange")
    public void laboratoryItemSwitchChange(String str, boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(str, z);
        bnu.a().a(str, z);
    }

    @AjxMethod(invokeMode = "sync", value = "getLabItems")
    public String getLabItems() {
        String str;
        String a = lo.a().a((String) "lab_lablist_order");
        if (TextUtils.isEmpty(a)) {
            return null;
        }
        try {
            HashMap hashMap = new HashMap();
            JSONObject jSONObject = new JSONObject(a);
            Iterator<String> keys = jSONObject.keys();
            JSONArray jSONArray = new JSONArray();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, Integer.valueOf(jSONObject.getInt(next)));
            }
            ArrayList<Entry> arrayList = new ArrayList<>(hashMap.entrySet());
            Collections.sort(arrayList, new Comparator<Entry<String, Integer>>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return ((Integer) ((Entry) obj).getValue()).compareTo((Integer) ((Entry) obj2).getValue());
                }
            });
            for (Entry key : arrayList) {
                String a2 = lo.a().a((String) key.getKey());
                if (a2 != null) {
                    jSONArray.put(new JSONObject(a2));
                }
            }
            str = jSONArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str = null;
        }
        return str;
    }

    @AjxMethod(invokeMode = "sync", value = "getLaboratoryItemSwitch")
    public boolean getLaboratoryItemSwitch(String str) {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(str, false);
    }

    @AjxMethod(invokeMode = "sync", value = "isLaboratoryRedFlag")
    public boolean isLaboratoryRedFlag() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("laboratory_red_flag", false);
    }
}
