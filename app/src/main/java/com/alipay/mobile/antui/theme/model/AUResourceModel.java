package com.alipay.mobile.antui.theme.model;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.utils.ToolUtils;
import java.util.HashMap;
import java.util.Map;

public class AUResourceModel {
    private static final String COLOR_KEY = "COLOR";
    private static final String DIMEN_KEY = "DIMEN";
    private static final String DRAWABLE_KEY = "DRAWABLE";
    private Map<String, String> colorMap;
    private Map<String, String> dimenMap;
    private Map<String, String> drawableMap;
    private AUThemeModel themeModel = new AUThemeModel();

    public AUResourceModel(AUThemeModel model) {
        if (model != null) {
            this.colorMap = new HashMap();
            this.dimenMap = new HashMap();
            this.drawableMap = new HashMap();
            JSONObject jsonObject = JSON.parseObject(model.themeJson);
            if (jsonObject != null) {
                updateColorMap(jsonObject.get(COLOR_KEY));
                updateDimenMap(jsonObject.get(DIMEN_KEY));
                updateDrawableMap(jsonObject.get(DRAWABLE_KEY));
            }
            this.themeModel = model;
        }
    }

    public int getColor(String key) {
        long currentTime = System.currentTimeMillis();
        if (this.themeModel == null || currentTime >= this.themeModel.endTime || currentTime <= this.themeModel.startTime || this.colorMap.get(key) == null) {
            return 0;
        }
        return ToolUtils.parseColor(this.colorMap.get(key));
    }

    public String getDrawable(String key) {
        long currentTime = System.currentTimeMillis();
        if (this.themeModel == null || currentTime >= this.themeModel.endTime || currentTime <= this.themeModel.startTime || this.colorMap.get(key) == null) {
            return "";
        }
        return this.drawableMap.get(key);
    }

    public int getDimen(Context context, String key) {
        long currentTime = System.currentTimeMillis();
        if (this.themeModel == null || currentTime >= this.themeModel.endTime || currentTime <= this.themeModel.startTime || this.dimenMap.get(key) == null) {
            return 0;
        }
        return ToolUtils.parseDimen(context, this.dimenMap.get(key));
    }

    private void updateColorMap(Object object) {
        if (object instanceof JSONObject) {
            JSONObject colorJson = (JSONObject) object;
            for (String key : colorJson.keySet()) {
                this.colorMap.put(key, colorJson.getString(key));
            }
        }
    }

    private void updateDimenMap(Object object) {
        if (object instanceof JSONObject) {
            JSONObject dimenJson = (JSONObject) object;
            for (String key : dimenJson.keySet()) {
                this.colorMap.put(key, dimenJson.getString(key));
            }
        }
    }

    private void updateDrawableMap(Object object) {
        if (object instanceof JSONObject) {
            JSONObject drawableJson = (JSONObject) object;
            for (String key : drawableJson.keySet()) {
                this.drawableMap.put(key, drawableJson.getString(key));
            }
        }
    }
}
