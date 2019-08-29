package com.autonavi.minimap.route.sharebike.model;

public class UserTagInfo extends BaseNetResult {
    private String allInfoJson = "";
    private UserTagData data;

    public String getAllInfoJson() {
        return this.allInfoJson;
    }

    public void setAllInfoJson(String str) {
        this.allInfoJson = str;
    }

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean z) {
        this.result = z;
    }

    public UserTagData getData() {
        return this.data;
    }

    public void setData(UserTagData userTagData) {
        this.data = userTagData;
    }
}
