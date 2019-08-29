package com.alipay.mobile.security.faceauth.circle.protocol;

import com.alipay.mobile.security.bio.config.bean.Algorithm;
import com.alipay.mobile.security.bio.config.bean.Coll;
import com.alipay.mobile.security.bio.config.bean.FaceTips;
import com.alipay.mobile.security.bio.config.bean.NavigatePage;
import com.alipay.mobile.security.bio.config.bean.SceneEnv;
import com.alipay.mobile.security.bio.config.bean.Upload;
import com.alipay.mobile.security.bio.utils.StringUtil;

public class FaceRemoteConfig {
    private SceneEnv a = new SceneEnv();
    private NavigatePage b = new NavigatePage();
    private Coll c = new Coll();
    private Upload d = new Upload();
    private Algorithm e = new Algorithm();
    private FaceTips f = new FaceTips();
    private DeviceSetting[] g = new DeviceSetting[0];
    private int h = 0;
    private int i = 991;

    public void setSceneEnv(SceneEnv sceneEnv) {
        this.a = sceneEnv;
    }

    public SceneEnv getSceneEnv() {
        return this.a;
    }

    public void setNavi(NavigatePage navigatePage) {
        this.b = navigatePage;
    }

    public NavigatePage getNavi() {
        return this.b;
    }

    public void setColl(Coll coll) {
        this.c = coll;
    }

    public Coll getColl() {
        return this.c;
    }

    public void setUpload(Upload upload) {
        this.d = upload;
    }

    public Upload getUpload() {
        return this.d;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.e = algorithm;
    }

    public Algorithm getAlgorithm() {
        return this.e;
    }

    public void setEnv(int i2) {
        this.h = i2;
    }

    public int getEnv() {
        return this.h;
    }

    public void setUi(int i2) {
        this.i = i2;
    }

    public int getUi() {
        return this.i;
    }

    public FaceTips getFaceTips() {
        return this.f;
    }

    public void setFaceTips(FaceTips faceTips) {
        this.f = faceTips;
    }

    public DeviceSetting[] getDeviceSettings() {
        return this.g;
    }

    public void setDeviceSettings(DeviceSetting[] deviceSettingArr) {
        this.g = deviceSettingArr;
    }

    public String toString() {
        return "FaceRemoteConfig{sceneEnv=" + this.a + ", navi=" + this.b + ", coll=" + this.c + ", upload=" + this.d + ", algorithm=" + this.e + ", faceTips=" + this.f + ", deviceSettings=" + StringUtil.array2String(this.g) + ", env=" + this.h + ", ui=" + this.i + '}';
    }
}
