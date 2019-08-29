package com.autonavi.jni.vcs;

import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;
import com.amap.location.sdk.fusion.LocationParams;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class NuiConfig {
    private static final String TAG = "NuiConfig";
    private String adiu;
    private String debugPath;
    private String dic;
    private String dip = "15221";
    private String diu;
    private String div;
    private String enableWwv;
    private String env;
    private String keepAlive = H5AppPrepareData.PREPARE_FAIL;
    private String ossupload = "false";
    private String tid;
    private String workspace;

    public String getEnv() {
        return this.env;
    }

    public void setEnv(String str) {
        this.env = str;
    }

    public String getWorkspace() {
        return this.workspace;
    }

    public void setWorkspace(String str) {
        this.workspace = str;
    }

    public String getDebugPath() {
        return this.debugPath;
    }

    public void setDebugPath(String str) {
        this.debugPath = str;
    }

    public String getKeepAlive() {
        return this.keepAlive;
    }

    public void setKeepAlive(String str) {
        this.keepAlive = str;
    }

    public String getDip() {
        return this.dip;
    }

    public void setDip(String str) {
        this.dip = str;
    }

    public String getDic() {
        return this.dic;
    }

    public void setDic(String str) {
        this.dic = str;
    }

    public String getDiv() {
        return this.div;
    }

    public void setDiv(String str) {
        this.div = str;
    }

    public String getTid() {
        return this.tid;
    }

    public void setTid(String str) {
        this.tid = str;
    }

    public String getDiu() {
        return this.diu;
    }

    public void setDiu(String str) {
        this.diu = str;
    }

    public String getAdiu() {
        return this.adiu;
    }

    public void setAdiu(String str) {
        this.adiu = str;
    }

    public String getEnableWwv() {
        return this.enableWwv;
    }

    public void setEnableWwv(String str) {
        this.enableWwv = str;
    }

    public String getOssupload() {
        return this.ossupload;
    }

    public void setOssupload(String str) {
        this.ossupload = str;
    }

    public boolean valid() {
        if (TextUtils.isEmpty(this.workspace) || TextUtils.isEmpty(this.dic) || TextUtils.isEmpty(this.div) || TextUtils.isEmpty(this.tid) || TextUtils.isEmpty(this.diu)) {
            StringBuilder sb = new StringBuilder();
            if (TextUtils.isEmpty(this.workspace)) {
                sb.append("|workspace字段空");
            }
            if (TextUtils.isEmpty(this.dic)) {
                sb.append("|dic字段空");
            }
            if (TextUtils.isEmpty(this.div)) {
                sb.append("|div字段空");
            }
            if (TextUtils.isEmpty(this.tid)) {
                sb.append("|tid字段空");
            }
            if (TextUtils.isEmpty(this.diu)) {
                sb.append("|diu字段空");
            }
            return false;
        } else if (!new File(this.workspace).exists()) {
            return false;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.workspace);
            sb2.append("/nui.json");
            if (!new File(sb2.toString()).exists()) {
                return false;
            }
            return true;
        }
    }

    public String toJSONString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("workspace", this.workspace);
            jSONObject.put("debug_path", this.debugPath);
            jSONObject.put("keep_alive", this.keepAlive);
            jSONObject.put(LocationParams.PARA_COMMON_DIP, this.dip);
            jSONObject.put(LocationParams.PARA_COMMON_DIC, this.dic);
            jSONObject.put(LocationParams.PARA_COMMON_DIV, this.div);
            jSONObject.put("tid", this.tid);
            jSONObject.put(LocationParams.PARA_COMMON_DIU, this.diu);
            jSONObject.put(LocationParams.PARA_COMMON_ADIU, this.adiu);
            jSONObject.put("env", this.env);
            jSONObject.put("enable_wwv", this.enableWwv);
            jSONObject.put("ossupload", this.ossupload);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
