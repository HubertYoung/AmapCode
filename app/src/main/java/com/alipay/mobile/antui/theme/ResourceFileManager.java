package com.alipay.mobile.antui.theme;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.antui.theme.model.AUResourceModel;
import com.alipay.mobile.antui.theme.model.AUThemeModel;
import java.util.HashMap;
import java.util.Map;

public class ResourceFileManager implements ThemeCallback {
    private static ResourceFileManager mInstance;
    private Map<String, AUResourceModel> themeMap = new HashMap();

    public static synchronized ResourceFileManager getInstance() {
        ResourceFileManager resourceFileManager;
        synchronized (ResourceFileManager.class) {
            if (mInstance == null) {
                mInstance = new ResourceFileManager();
            }
            resourceFileManager = mInstance;
        }
        return resourceFileManager;
    }

    private ResourceFileManager() {
    }

    public void registerTheme(String bundle, String code_key) {
        ThemeInfoProcessor.getConfig(bundle, code_key, this);
    }

    public int getColor(String key, String bundle, int defaultColor) {
        int color = getColor(key, bundle);
        return color == 0 ? defaultColor : color;
    }

    public int getColor(String key, String bundle) {
        if (this.themeMap.get(bundle) != null) {
            return this.themeMap.get(bundle).getColor(key);
        }
        return 0;
    }

    public int getDimen(Context context, String key, String bundle, int defaultDimen) {
        int dimen = getDimen(context, key, bundle);
        return dimen == 0 ? defaultDimen : dimen;
    }

    public int getDimen(Context context, String key, String bundle) {
        if (this.themeMap.get(bundle) != null) {
            return this.themeMap.get(bundle).getDimen(context, key);
        }
        return 0;
    }

    public String getDrawable(String key, String bundle, String defaultUrl) {
        String url = getDrawable(key, bundle);
        return TextUtils.isEmpty(url) ? defaultUrl : url;
    }

    public String getDrawable(String key, String bundle) {
        if (this.themeMap.get(bundle) != null) {
            return this.themeMap.get(bundle).getDrawable(key);
        }
        return "";
    }

    public void updateTheme(String bundle, AUThemeModel themeModel) {
        this.themeMap.put(bundle, new AUResourceModel(themeModel));
    }
}
