package com.autonavi.minimap.ajx3.audio;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

class AjxAudioManager {
    private static ConcurrentHashMap<String, AjxAudioPlayer> sPlayer = new ConcurrentHashMap<>();

    AjxAudioManager() {
    }

    public static synchronized void attachPlayer(String str, IAjxContext iAjxContext, JsFunctionCallback jsFunctionCallback) {
        synchronized (AjxAudioManager.class) {
            AjxAudioPlayer ajxAudioPlayer = sPlayer.get(str);
            if (ajxAudioPlayer == null) {
                ajxAudioPlayer = new AjxAudioPlayer(iAjxContext.getNativeContext(), str);
                sPlayer.put(str, ajxAudioPlayer);
            }
            ajxAudioPlayer.attach(iAjxContext, jsFunctionCallback);
        }
    }

    public static synchronized void detachPlayer(IAjxContext iAjxContext, String str) {
        synchronized (AjxAudioManager.class) {
            Iterator<Entry<String, AjxAudioPlayer>> it = sPlayer.entrySet().iterator();
            while (it.hasNext()) {
                AjxAudioPlayer ajxAudioPlayer = (AjxAudioPlayer) it.next().getValue();
                ajxAudioPlayer.detach(iAjxContext);
                if (ajxAudioPlayer.releasable()) {
                    ajxAudioPlayer.destroy(str);
                    it.remove();
                }
            }
        }
    }

    public static synchronized AjxAudioPlayer findPlayer(String str) {
        AjxAudioPlayer ajxAudioPlayer;
        synchronized (AjxAudioManager.class) {
            try {
                ajxAudioPlayer = sPlayer.get(str);
            }
        }
        return ajxAudioPlayer;
    }

    public static synchronized void destroyPlayer(String str, String str2) {
        synchronized (AjxAudioManager.class) {
            AjxAudioPlayer ajxAudioPlayer = sPlayer.get(str);
            if (ajxAudioPlayer != null) {
                ajxAudioPlayer.destroy(str2);
                sPlayer.remove(str);
            }
        }
    }
}
