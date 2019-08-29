package com.autonavi.minimap.ajx3.upgrade;

import com.amap.bundle.network.response.AbstractAOSParser;
import org.json.JSONObject;

public class Ajx3WebCloudConfigParser extends AbstractAOSParser {
    String mData = "";
    private String mStatId;
    private String mType = "hot_start";

    public String getErrorDesc(int i) {
        return null;
    }

    Ajx3WebCloudConfigParser(String str, String str2) {
        this.mType = str;
        this.mStatId = str2;
    }

    public void parser(byte[] bArr) {
        try {
            JSONObject parseHeader = parseHeader(bArr);
            this.mData = "";
            int i = -1;
            if (parseHeader.has("code")) {
                i = parseHeader.getInt("code");
            }
            if (i != 1 || !parseHeader.has("data")) {
                Ajx3ActionLogUtil.actionLogAjxWebCloudOnLine(this.mType, this.mStatId, "", i);
                return;
            }
            this.mData = parseHeader.getString("data");
            Ajx3ActionLogUtil.actionLogAjxWebCloud("B002", this.mType, this.mStatId, parseHeader.toString());
        } catch (Exception unused) {
            Ajx3ActionLogUtil.actionLogAjxWebCloudOnLine(this.mType, this.mStatId, "", -2);
        }
    }
}
