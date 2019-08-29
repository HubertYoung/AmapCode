package com.alipay.mobile.nebulacore.search;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.search.H5InputListen;

public class H5SearchInputListen implements H5InputListen {
    private H5Page a;

    public H5SearchInputListen(H5Page h5Page) {
        this.a = h5Page;
    }

    public void onChange(String text) {
        if (this.a != null && this.a.getBridge() != null) {
            JSONObject value = new JSONObject();
            value.put((String) "value", (Object) text);
            JSONObject packet = new JSONObject();
            packet.put((String) "data", (Object) value);
            this.a.getBridge().sendToWeb("h5SearchBar_didTextChange", packet, null);
        }
    }

    public void onFocus() {
        if (this.a != null && this.a.getBridge() != null) {
            this.a.getBridge().sendToWeb("h5SearchBar_didFocus", null, null);
        }
    }

    public void onBlur() {
        if (this.a != null && this.a.getBridge() != null) {
            this.a.getBridge().sendToWeb("h5SearchBar_didBlur", null, null);
        }
    }

    public void onSubmit(String text) {
        if (this.a != null && this.a.getBridge() != null) {
            JSONObject value = new JSONObject();
            value.put((String) "value", (Object) text);
            JSONObject packet = new JSONObject();
            packet.put((String) "data", (Object) value);
            this.a.getBridge().sendToWeb("h5SearchBar_didSubmit", packet, null);
        }
    }

    public void onCancel() {
        if (this.a != null && this.a.getBridge() != null) {
            this.a.getBridge().sendToWeb("h5SearchBar_didCancel", null, null);
        }
    }

    public void onVoiceBtnClick() {
        if (this.a != null && this.a.getBridge() != null) {
            this.a.getBridge().sendToWeb("h5SearchBar_didVoiceBtnClick", null, null);
        }
    }

    public void entranceClick() {
        if (this.a != null && this.a.getBridge() != null) {
            this.a.getBridge().sendToWeb("h5SearchBar_didEntranceClick", null, null);
        }
    }

    public void back() {
        if (this.a != null) {
            this.a.sendEvent(CommonEvents.H5_TOOLBAR_BACK, null);
        }
    }
}
