package com.autonavi.minimap.ajx3;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.core.PageConfig;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.HashMap;

public class JsRunInfo {
    private static final String PREFIX_ID = "id://";
    private String ajxPageConfigPath = null;
    private Object data;
    private String environment;
    private int height = 0;
    private HashMap<String, Object> mRunParams;
    private PageConfig pageConfig = null;
    private String pageId;
    private long parentCtxID = -1;
    private float runHeight;
    private float runWidth;
    private String tag;
    private String url;
    private int width = 0;

    public JsRunInfo(String str, Object obj) {
        this.url = str;
        this.data = obj;
    }

    public JsRunInfo(String str, Object obj, int i, int i2) {
        this.url = str;
        this.data = obj;
        this.width = i;
        this.height = i2;
        this.runWidth = DimensionUtils.pixelToStandardUnit((float) i);
        this.runHeight = DimensionUtils.pixelToStandardUnit((float) i2);
    }

    public String getPageId() {
        return this.pageId;
    }

    public void setPageId(String str) {
        this.pageId = str;
    }

    public String getUrl() {
        return this.url;
    }

    public Object getData() {
        return this.data;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public float getRunWidth() {
        return this.runWidth;
    }

    public float getRunHeight() {
        return this.runHeight;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(String str) {
        this.environment = str;
    }

    public long getParentCtxID() {
        return this.parentCtxID;
    }

    public void setParentCtxID(long j) {
        this.parentCtxID = j;
    }

    public String getAjxPageConfigPath() {
        return this.ajxPageConfigPath;
    }

    public void setAjxPageConfigPath(String str) {
        this.ajxPageConfigPath = str;
    }

    public PageConfig getPageConfig() {
        return this.pageConfig;
    }

    public void setPageConfig(PageConfig pageConfig2) {
        this.pageConfig = pageConfig2;
    }

    public void updateWidthAndHeight(int i, int i2) {
        this.width = i;
        this.height = i2;
        this.runWidth = DimensionUtils.pixelToStandardUnit((float) i);
        this.runHeight = DimensionUtils.pixelToStandardUnit((float) i2);
    }

    public void setRunParams(HashMap<String, Object> hashMap) {
        this.mRunParams = hashMap;
    }

    public HashMap<String, Object> getRunParams() {
        return this.mRunParams;
    }

    public void checkUrl() {
        if (!TextUtils.isEmpty(this.url) && this.url.startsWith(PREFIX_ID)) {
            this.url = this.url.substring(5);
        }
    }
}
