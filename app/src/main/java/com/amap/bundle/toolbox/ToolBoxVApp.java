package com.amap.bundle.toolbox;

import android.content.SharedPreferences.Editor;
import com.amap.bundle.mapstorage.MapSharePreference;
import org.json.JSONObject;

public class ToolBoxVApp extends esh {
    private a a = new a() {
        public final void a(JSONObject jSONObject) {
            Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
            String str = "";
            ls lsVar = lt.a().c;
            if (lsVar.f != null) {
                str = lsVar.f;
            }
            edit.putString("allowUsePayEntrance", str).apply();
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        lt.a().a(this.a);
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        lt.a().b(this.a);
    }
}
