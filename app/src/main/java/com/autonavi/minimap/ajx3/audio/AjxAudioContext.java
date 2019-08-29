package com.autonavi.minimap.ajx3.audio;

import android.content.Context;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import org.json.JSONObject;

class AjxAudioContext {
    final JsFunctionCallback callback;
    final long jsContextId;
    final Context nativeContext;
    final String playerId;

    public AjxAudioContext(IAjxContext iAjxContext, String str, JsFunctionCallback jsFunctionCallback) {
        this.nativeContext = iAjxContext.getNativeContext();
        this.jsContextId = iAjxContext.getId();
        this.playerId = str;
        this.callback = jsFunctionCallback;
    }

    public void notifyJS(String str, JSONObject jSONObject) {
        this.callback.callback(str, jSONObject);
    }
}
