package com.alipay.mobile.security.bio.config.bean;

public class SceneEnv {
    private String a = "";
    private String b = "normal";

    public void setSceneCode(String str) {
        this.a = str;
    }

    public String getSceneCode() {
        return this.a;
    }

    public void setSceneType(String str) {
        this.b = str;
    }

    public String getSceneType() {
        return this.b;
    }

    public String toString() {
        return "SceneEnv{sceneCode='" + this.a + '\'' + ", sceneType='" + this.b + '\'' + '}';
    }
}
