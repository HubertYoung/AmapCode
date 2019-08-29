package com.autonavi.minimap.ajx3.upgrade;

import com.amap.bundle.network.response.AbstractAOSParser;
import org.json.JSONObject;

class Ajx3BundleUpdatableParser extends AbstractAOSParser {
    int code;
    boolean isUserSelect;
    String mData;

    public String getErrorDesc(int i) {
        return null;
    }

    Ajx3BundleUpdatableParser() {
        this.mData = "";
        this.code = 1;
        this.isUserSelect = false;
    }

    Ajx3BundleUpdatableParser(boolean z) {
        this.mData = "";
        this.code = 1;
        this.isUserSelect = z;
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
                Ajx3ActionLogUtil.actionLogAjxWeb(11, 11, "check update error , code: ".concat(String.valueOf(i)), this.isUserSelect, Ajx3UpgradeManager.getInstance().mStatId);
            } else {
                this.mData = parseHeader.getString("data");
            }
        } catch (Exception e) {
            Ajx3ActionLogUtil.actionLogAjxWeb(11, -2, "check update error , CODE_PARSER_FAIL: ".concat(String.valueOf(e)), this.isUserSelect, Ajx3UpgradeManager.getInstance().mStatId);
        }
    }
}
