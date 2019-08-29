package com.autonavi.map.search.page;

import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import defpackage.cau;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractSearchBasePage<Presenter extends cau> extends AbstractBasePage<Presenter> {
    public static final int INVALID_REQUEST_CODE = -1;

    public final <T extends View> T findView(int i) {
        return findViewById(i);
    }

    public final int getColor(int i) {
        return getResources().getColor(i);
    }

    public final int getDimensionPixelOffset(int i) {
        return getResources().getDimensionPixelOffset(i);
    }

    public final void recordActionLog(String str, String str2) {
        LogManager.actionLogV2(str, str2);
    }

    public final void recordActionLog(String str, String str2, JSONObject jSONObject) {
        LogManager.actionLogV2(str, str2, jSONObject);
    }

    public final void recordActionLog(String str, String str2, String str3, String str4) {
        try {
            new JSONObject().put(str3, str4);
        } catch (Exception unused) {
        }
        LogManager.actionLogV2(str, str2);
    }

    public void recordActionLog(String str, String str2, Entry... entryArr) {
        JSONObject jSONObject = null;
        if (entryArr != null) {
            try {
                if (entryArr.length > 0) {
                    jSONObject = new JSONObject();
                    for (Entry entry : entryArr) {
                        jSONObject.put(entry.getKey().toString(), entry.getValue());
                    }
                }
            } catch (JSONException unused) {
                recordActionLog(str, str2);
                return;
            }
        }
        recordActionLog(str, str2, jSONObject);
    }
}
