package com.alipay.zoloz.toyger.workspace;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.security.bio.config.bean.Coll;
import com.alipay.mobile.security.bio.config.bean.FaceTips;
import com.alipay.mobile.security.bio.config.bean.NavigatePage;
import com.alipay.mobile.security.bio.config.bean.SceneEnv;
import com.alipay.mobile.security.bio.utils.StringUtil;
import com.alipay.mobile.security.faceauth.circle.protocol.DeviceSetting;

public class FaceRemoteConfig {
    private JSONObject algorithm;
    private Coll coll = new Coll();
    private DeviceSetting[] deviceSettings = new DeviceSetting[0];
    private int env = 0;
    private FaceTips faceTips = new FaceTips();
    private NavigatePage navi = new NavigatePage();
    private SceneEnv sceneEnv = new SceneEnv();
    private int ui = 991;
    private JSONObject upload;

    public void setSceneEnv(SceneEnv sceneEnv2) {
        this.sceneEnv = sceneEnv2;
    }

    public SceneEnv getSceneEnv() {
        return this.sceneEnv;
    }

    public void setNavi(NavigatePage navigatePage) {
        this.navi = navigatePage;
    }

    public NavigatePage getNavi() {
        return this.navi;
    }

    public void setColl(Coll coll2) {
        this.coll = coll2;
    }

    public Coll getColl() {
        return this.coll;
    }

    public void setUpload(JSONObject jSONObject) {
        this.upload = jSONObject;
    }

    public JSONObject getUpload() {
        return this.upload;
    }

    public void setAlgorithm(JSONObject jSONObject) {
        this.algorithm = jSONObject;
    }

    public JSONObject getAlgorithm() {
        return this.algorithm;
    }

    public void setEnv(int i) {
        this.env = i;
    }

    public int getEnv() {
        return this.env;
    }

    public void setUi(int i) {
        this.ui = i;
    }

    public int getUi() {
        return this.ui;
    }

    public FaceTips getFaceTips() {
        return this.faceTips;
    }

    public void setFaceTips(FaceTips faceTips2) {
        this.faceTips = faceTips2;
    }

    public DeviceSetting[] getDeviceSettings() {
        return this.deviceSettings;
    }

    public void setDeviceSettings(DeviceSetting[] deviceSettingArr) {
        this.deviceSettings = deviceSettingArr;
    }

    public String toString() {
        return "FaceRemoteConfig{sceneEnv=" + this.sceneEnv + ", navi=" + this.navi + ", coll=" + this.coll + ", upload=" + this.upload + ", algorithm=" + this.algorithm + ", faceTips=" + this.faceTips + ", deviceSettings=" + StringUtil.array2String(this.deviceSettings) + ", env=" + this.env + ", ui=" + this.ui + '}';
    }
}
