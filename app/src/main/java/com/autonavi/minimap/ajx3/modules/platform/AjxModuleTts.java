package com.autonavi.minimap.ajx3.modules.platform;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleTts;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.PlaySoundUtils.OnSoundPlayListener;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxModuleTts extends AbstractModuleTts {
    /* access modifiers changed from: private */
    public OnSoundPlayListenerWrapper onSoundPlayListener;

    class OnSoundPlayListenerWrapper implements OnSoundPlayListener {
        private JsFunctionCallback callback;

        public void onPlaySentenceEnd(String str) {
        }

        public void onPlaySoundStart(String str) {
        }

        private OnSoundPlayListenerWrapper() {
        }

        /* access modifiers changed from: private */
        public void setCallback(JsFunctionCallback jsFunctionCallback) {
            this.callback = jsFunctionCallback;
        }

        public void onPlayEnd() {
            if (this.callback != null) {
                this.callback.callback(Boolean.TRUE);
            }
            PlaySoundUtils.getInstance().removeSoundPlayListener(AjxModuleTts.this.onSoundPlayListener);
        }
    }

    public AjxModuleTts(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void play(String str, JsFunctionCallback jsFunctionCallback) {
        if (this.onSoundPlayListener == null) {
            this.onSoundPlayListener = new OnSoundPlayListenerWrapper();
        }
        this.onSoundPlayListener.setCallback(jsFunctionCallback);
        PlaySoundUtils.getInstance().addSoundPlayListener(this.onSoundPlayListener);
        PlaySoundUtils.getInstance().playSound(str);
    }

    public void stop() {
        PlaySoundUtils.getInstance().clear();
    }

    public void stopAll() {
        PlaySoundUtils.getInstance().clear();
    }

    public boolean isPlaying() {
        return PlaySoundUtils.getInstance().isPlaying();
    }

    public void destroy() {
        PlaySoundUtils.getInstance().release();
    }

    public void setConfig(String str) {
        if (TextUtils.isEmpty(str)) {
            AMapLog.e(AjxModuleTts.class.getName(), "setCallingSpeak param == null");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("mixedmusic")) {
                PlaySoundUtils.getInstance().setTTSMixedMusic(jSONObject.optBoolean("mixedmusic"));
            }
            if (jSONObject.has("callingspeak")) {
                PlaySoundUtils.getInstance().setCallingSpeakTTS(jSONObject.optBoolean("callingspeak"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
