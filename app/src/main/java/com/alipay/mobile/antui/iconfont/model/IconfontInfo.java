package com.alipay.mobile.antui.iconfont.model;

import android.text.TextUtils;
import java.io.Serializable;
import org.json.JSONArray;

public class IconfontInfo implements Serializable {
    public static final float NO_SIZE = -1.0f;
    public static final String TAG = IconfontInfo.class.getSimpleName();
    private static final long serialVersionUID = -7442005318609615310L;
    private String iconfontColor = "";
    private JSONArray iconfontFonts = null;
    private String iconfontId = "";
    private String iconfontSize = "";
    private String iconfontUnicode = "";

    public String getIconfontId() {
        return this.iconfontId;
    }

    public void setIconfontId(String iconfontId2) {
        this.iconfontId = iconfontId2;
    }

    public String getIconfontSize() {
        return this.iconfontSize;
    }

    public void setIconfontSize(String iconfontSize2) {
        this.iconfontSize = iconfontSize2;
    }

    public String getIconfontUnicode() {
        return this.iconfontUnicode;
    }

    public void setIconfontUnicode(String iconfontUnicode2) {
        this.iconfontUnicode = iconfontUnicode2;
    }

    public JSONArray getIconfontFonts() {
        return this.iconfontFonts;
    }

    public void setIconfontFonts(JSONArray iconfontFonts2) {
        this.iconfontFonts = iconfontFonts2;
    }

    public String getIconfontColor() {
        return this.iconfontColor;
    }

    public void setIconfontColor(String iconfontColor2) {
        this.iconfontColor = iconfontColor2;
    }

    public boolean isValid() {
        boolean z;
        if (!TextUtils.isEmpty(this.iconfontId)) {
            z = true;
        } else {
            z = false;
        }
        return z && (!TextUtils.isEmpty(this.iconfontUnicode) || this.iconfontFonts != null);
    }
}
