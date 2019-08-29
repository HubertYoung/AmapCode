package com.autonavi.minimap.ajx3.audio;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule(isInUiThread = false, value = "ajx.audio")
public class AjxAudioModule extends AbstractModule {
    @AjxMethod("setAutoPlayMode")
    public void setAutoPlayMode(String str, String str2) {
    }

    public AjxAudioModule(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("create")
    public void create(String str, JsFunctionCallback jsFunctionCallback) {
        AjxAudioManager.attachPlayer(str, getContext(), jsFunctionCallback);
    }

    @AjxMethod("prepare")
    public void prepare(String str, String str2) {
        AjxAudioPlayer findPlayer = AjxAudioManager.findPlayer(str);
        if (findPlayer != null) {
            findPlayer.prepare(str2, getTrigger());
        }
    }

    @AjxMethod("play")
    public void play(String str) {
        AjxAudioPlayer findPlayer = AjxAudioManager.findPlayer(str);
        if (findPlayer != null) {
            findPlayer.play(getTrigger());
        }
    }

    @AjxMethod("pause")
    public void pause(String str) {
        AjxAudioPlayer findPlayer = AjxAudioManager.findPlayer(str);
        if (findPlayer != null) {
            findPlayer.pause(getTrigger());
        }
    }

    @AjxMethod("setCurrentTime")
    public void setCurrentTime(String str, int i) {
        AjxAudioPlayer findPlayer = AjxAudioManager.findPlayer(str);
        if (findPlayer != null) {
            findPlayer.setCurrentTime(i);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getCurrentTime")
    public int getCurrentTime(String str) {
        AjxAudioPlayer findPlayer = AjxAudioManager.findPlayer(str);
        if (findPlayer != null) {
            return findPlayer.getCurrentTime();
        }
        return -1;
    }

    @AjxMethod(invokeMode = "sync", value = "getTotalTime")
    public int getTotalTime(String str) {
        AjxAudioPlayer findPlayer = AjxAudioManager.findPlayer(str);
        if (findPlayer != null) {
            return findPlayer.getTotalTime();
        }
        return -1;
    }

    @AjxMethod(invokeMode = "sync", value = "getSrc")
    public String getSrc(String str) {
        AjxAudioPlayer findPlayer = AjxAudioManager.findPlayer(str);
        return findPlayer != null ? findPlayer.getSrc() : "";
    }

    @AjxMethod(invokeMode = "sync", value = "getState")
    public String getState(String str) {
        AjxAudioPlayer findPlayer = AjxAudioManager.findPlayer(str);
        return findPlayer != null ? findPlayer.getState() : "";
    }

    @AjxMethod("destroy")
    public void destroy(String str) {
        AjxAudioManager.destroyPlayer(str, getTrigger());
    }

    public void onModuleDestroy() {
        AjxAudioManager.detachPlayer(getContext(), getTrigger());
        super.onModuleDestroy();
    }

    private String getTrigger() {
        return getContext().getJsPath();
    }
}
