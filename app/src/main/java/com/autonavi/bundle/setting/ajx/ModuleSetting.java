package com.autonavi.bundle.setting.ajx;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.bundle.setting.api.ISettingService.SharePreferenceName;

@AjxModule("setting")
public class ModuleSetting extends AbstractModule {
    private static final String MAP_TEXT_SIZE_EXTRA = "extra";
    private static final float MAP_TEXT_SIZE_EXTRA_FLOAT = 1.4f;
    private static final String MAP_TEXT_SIZE_KEY = "map_text_size";
    private static final String MAP_TEXT_SIZE_LARGE = "large";
    private static final float MAP_TEXT_SIZE_LARGE_FLOAT = 1.2f;
    private static final String MAP_TEXT_SIZE_SMALL = "small";
    private static final float MAP_TEXT_SIZE_SMALL_FLOAT = 0.9f;
    private static final String MAP_TEXT_SIZE_STD = "std";
    private static final float MAP_TEXT_SIZE_STD_FLOAT = 1.0f;

    public ModuleSetting(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setMapFontSize")
    public void setMapFontSize(String str) {
        String textSizeToTextType = textSizeToTextType(str);
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.MapTextSizeSet.toString());
        mapSharePreference.edit().putString(MAP_TEXT_SIZE_KEY, textSizeToTextType);
        mapSharePreference.edit().apply();
    }

    @AjxMethod(invokeMode = "sync", value = "getMapFontSize")
    public String getMapFontSize() {
        String string = new MapSharePreference(SharePreferenceName.MapTextSizeSet.toString()).sharedPrefs().getString(MAP_TEXT_SIZE_KEY, "");
        if (TextUtils.isEmpty(string)) {
            string = MAP_TEXT_SIZE_STD;
        }
        return textTypeToTextSize(string);
    }

    private String textSizeToTextType(String str) {
        float f;
        try {
            f = Float.parseFloat(str);
        } catch (Exception unused) {
            f = 1.0f;
        }
        if (f == 0.9f) {
            return MAP_TEXT_SIZE_SMALL;
        }
        if (f == 1.0f) {
            return MAP_TEXT_SIZE_STD;
        }
        if (f == MAP_TEXT_SIZE_LARGE_FLOAT) {
            return MAP_TEXT_SIZE_LARGE;
        }
        return f == MAP_TEXT_SIZE_EXTRA_FLOAT ? "extra" : MAP_TEXT_SIZE_STD;
    }

    private String textTypeToTextSize(String str) {
        float f = 1.0f;
        if (str.contentEquals(MAP_TEXT_SIZE_SMALL)) {
            f = 0.9f;
        } else if (!str.contentEquals(MAP_TEXT_SIZE_STD)) {
            if (str.contentEquals(MAP_TEXT_SIZE_LARGE)) {
                f = MAP_TEXT_SIZE_LARGE_FLOAT;
            } else if (str.contentEquals("extra")) {
                f = MAP_TEXT_SIZE_EXTRA_FLOAT;
            }
        }
        return String.valueOf(f);
    }
}
