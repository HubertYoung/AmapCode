package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"voiceVolumeListenerFinish", "webAudio", "shock", "cancelFetchActivity", "voiceVolumeListenerRun", "showActivity", "fetchActivity"}, jsActions = {"com.autonavi.minimap.bundle.activities.jsaction.VoiceVolumeListenerFinishAction", "com.autonavi.minimap.bundle.activities.jsaction.WebAudioAction", "com.autonavi.minimap.bundle.activities.jsaction.VibrateAction", "com.autonavi.minimap.bundle.activities.jsaction.CancelFetchActivityAction", "com.autonavi.minimap.bundle.activities.jsaction.VoiceVolumeListenerRunAction", "com.autonavi.minimap.bundle.activities.jsaction.ShowActivityAction", "com.autonavi.minimap.bundle.activities.jsaction.FetchActivityAction"}, module = "activities")
@KeepName
public final class ACTIVITIES_JsAction_DATA extends HashMap<String, Class<?>> {
    public ACTIVITIES_JsAction_DATA() {
        put("voiceVolumeListenerFinish", ctt.class);
        put("webAudio", ctv.class);
        put("shock", cts.class);
        put("cancelFetchActivity", ctn.class);
        put("voiceVolumeListenerRun", ctu.class);
        put("showActivity", ctr.class);
        put("fetchActivity", cto.class);
    }
}
