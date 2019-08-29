package com.autonavi.minimap.ajx3.modules;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.AjxErrorUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.util.AjxLogUtil;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import org.json.JSONObject;

@AjxModule("navi")
public class ModuleNavi extends AbstractModule {
    public static final String MODULE_NAME = "navi";
    private static final int PLAY_RING_CAMERA = 101;
    private static final int PLAY_RING_DOG = 102;
    private static final int PLAY_RING_OFF_ROUTE = 1;
    private static final int PLAY_RING_TRAFFIC_EVENT = 2;
    private static final int PLAY_RING_TURN = 100;

    @AjxMethod(invokeMode = "sync", value = "isDebug")
    public boolean isDebug() {
        return false;
    }

    public ModuleNavi(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("playTTS")
    public void playTTS(String str) {
        PlaySoundUtils.getInstance().playSound(str);
    }

    @AjxMethod("playWarningSound")
    public void playWarningSound(int i) {
        int i2;
        switch (i) {
            case 1:
                i2 = R.raw.autoreroute;
                break;
            case 2:
                i2 = R.raw.navi_traffic_event;
                break;
            default:
                switch (i) {
                    case 100:
                        i2 = R.raw.navi_warning;
                        break;
                    case 101:
                        i2 = R.raw.camera;
                        break;
                    case 102:
                        i2 = R.raw.edog_dingdong;
                        break;
                    default:
                        i2 = 0;
                        break;
                }
        }
        PlaySoundUtils.getInstance().playNaviWarningSound(AMapAppGlobal.getApplication().getApplicationContext(), i2);
    }

    @AjxMethod("muteSwitch")
    public void muteSwitch(boolean z) {
        PlaySoundUtils.getInstance().setSilent(z);
    }

    @AjxMethod("recordLogToTagFile")
    public void recordLogToTagFile(String str, String str2) {
        AjxLogUtil.recordLogToTagFile(str, str2);
    }

    @AjxMethod("releaseSound")
    public void releaseSound() {
        PlaySoundUtils.getInstance().release();
    }

    @AjxMethod("startNavi")
    public void startNavi(String str, final JsFunctionCallback jsFunctionCallback) {
        final dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null) {
            try {
                final POI a = bnx.a(new JSONObject(str).getJSONObject("poi").toString());
                aho.a(new Runnable() {
                    public void run() {
                        dfm.a(a);
                        if (jsFunctionCallback != null) {
                            jsFunctionCallback.callback(new Object[0]);
                        }
                    }
                });
            } catch (Exception unused) {
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(AjxErrorUtil.getError(1, "invalid param", str));
                }
            }
        } else {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(AjxErrorUtil.getError(2, "internal error", new String[0]));
            }
        }
    }
}
