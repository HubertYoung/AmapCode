package defpackage;

import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.iflytek.tts.TtsService.PlaySoundUtils.OnSoundPlayListener;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bba reason: default package */
/* compiled from: SoundListenerImpl */
public final class bba implements OnSoundPlayListener {
    private JsFunctionCallback a;

    public final void onPlaySentenceEnd(String str) {
    }

    public final void onPlaySoundStart(String str) {
    }

    public final void onPlayEnd() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("successfully", true);
            if (this.a != null) {
                this.a.callback(jSONObject);
            }
        } catch (JSONException unused) {
        }
    }
}
