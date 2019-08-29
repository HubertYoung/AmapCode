package com.autonavi.minimap.offline.koala;

import android.content.Context;
import com.autonavi.minimap.offline.koala.internal.KoalaDownloadClient;
import com.autonavi.minimap.offline.koala.internal.KoalaDownloadProxy;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadDashboard;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadListener;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadProfile;

public class Koala implements IKoalaDownloadDashboard {
    private static final String DEFAULT_KEY = "koala";
    private static KoalaConfig GLOBAL_CONFIG;
    private IKoalaDownloadDashboard mDownloadProxy;

    private Koala(String str, KoalaConfig koalaConfig) {
        if (koalaConfig == null) {
            throw new IllegalArgumentException("parameter config is null! ");
        }
        this.mDownloadProxy = new KoalaDownloadProxy(str, koalaConfig.clone());
    }

    public static KoalaConfig getGlobalConfig() {
        return GLOBAL_CONFIG;
    }

    public static void initialize(KoalaConfig koalaConfig) {
        GLOBAL_CONFIG = koalaConfig;
    }

    public static Koala create(String str, KoalaConfig koalaConfig) {
        return new Koala(str, koalaConfig);
    }

    public static Koala create(String str) {
        return create(str, GLOBAL_CONFIG);
    }

    public static Koala create() {
        return create((String) DEFAULT_KEY);
    }

    public static Koala create(KoalaConfig koalaConfig) {
        return create(DEFAULT_KEY, koalaConfig);
    }

    public void start(String str) {
        this.mDownloadProxy.start(str);
    }

    public void start(String[] strArr) {
        this.mDownloadProxy.start(strArr);
    }

    public void stop(int i) {
        this.mDownloadProxy.stop(i);
    }

    public void stopAll() {
        this.mDownloadProxy.stopAll();
    }

    public void pause(int i) {
        this.mDownloadProxy.pause(i);
    }

    public void pauseAll() {
        this.mDownloadProxy.pauseAll();
    }

    public void resume(int i) {
        this.mDownloadProxy.resume(i);
    }

    public void resumeAll() {
        this.mDownloadProxy.resumeAll();
    }

    public void remove(int i) {
        this.mDownloadProxy.remove(i);
    }

    public void removeAll() {
        this.mDownloadProxy.removeAll();
    }

    public void bind(IKoalaDownloadListener iKoalaDownloadListener) {
        this.mDownloadProxy.bind(iKoalaDownloadListener);
    }

    public boolean isBind(IKoalaDownloadListener iKoalaDownloadListener) {
        return this.mDownloadProxy.isBind(iKoalaDownloadListener);
    }

    public void unbind(IKoalaDownloadListener iKoalaDownloadListener) {
        this.mDownloadProxy.unbind(iKoalaDownloadListener);
    }

    public void forcePersistence() {
        this.mDownloadProxy.forcePersistence();
    }

    public KoalaDownloadProfile getProfile(int i) {
        return this.mDownloadProxy.getProfile(i);
    }

    public boolean hasRunningTask() {
        return this.mDownloadProxy.hasRunningTask();
    }

    public boolean isRunning(int i) {
        return this.mDownloadProxy.isRunning(i);
    }

    public void destroy() {
        this.mDownloadProxy.destroy();
    }

    public static void bindService(Context context) {
        KoalaDownloadClient.getInstance().bindService(context);
    }

    public static void unbindService(Context context) {
        KoalaDownloadClient.getInstance().unbindService(context);
    }

    public static void release(String str) {
        KoalaDownloadClient.getInstance().releaseDashboard(str);
    }
}
