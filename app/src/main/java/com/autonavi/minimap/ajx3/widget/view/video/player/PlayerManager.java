package com.autonavi.minimap.ajx3.widget.view.video.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.widget.view.video.player.Config.Builder;
import com.autonavi.minimap.ajx3.widget.view.video.player.IPlayer.PlayCallback;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class PlayerManager implements PlayCallback {
    private static final String ACTION_VIDEO_FOCUS = "com.autonavi.minimap.ajx3.action.VIDEO_FOCUS";
    private static final String DATA_FOCUS_STATE = "STATE";
    private static final String DATA_VIDEO_ID = "ID";
    private static final String TAG = "PlayerManager";
    private static volatile PlayerManager sPlayerManager;
    private Config mConfig;
    private boolean mLastFocused = false;
    private int mObserverHash = -1;
    private PlayStateObservable mPlayStateObservable;
    private AbstractPlayer mPlayer;
    private int mScreenState = 1;
    private String mVideoUrl;

    static class PlayStateObservable extends Observable {
        PlayStateObservable() {
        }

        private void setObservableChanged() {
            setChanged();
        }

        public void notify(Message message) {
            setObservableChanged();
            notifyObservers(message);
        }
    }

    public void onInfo(long j, long j2) {
    }

    public static PlayerManager getInstance() {
        if (sPlayerManager == null) {
            synchronized (PlayerManager.class) {
                if (sPlayerManager == null) {
                    loadConfig(new Builder().debug(false).build());
                }
            }
        }
        return sPlayerManager;
    }

    public static void init(Context context) {
        loadConfig(new Builder(context).debug(false).cache(true).build());
    }

    private static void loadConfig(Config config) {
        if (sPlayerManager == null) {
            sPlayerManager = new PlayerManager(config);
            Utils.setDebug(config.isDebugEnable());
        }
    }

    private PlayerManager(Config config) {
        this.mConfig = config;
        this.mPlayer = config.getPlayerFactory().create();
        this.mPlayer.setPlayCallback(this);
        this.mPlayStateObservable = new PlayStateObservable();
    }

    public Config getConfig() {
        return this.mConfig;
    }

    public void removeTextureView() {
        if (this.mPlayer.getTextureView() != null && this.mPlayer.getTextureView().getParent() != null) {
            ((ViewGroup) this.mPlayer.getTextureView().getParent()).removeView(this.mPlayer.getTextureView());
            setTextureView(null);
            if (this.mPlayer.getTextureView() != null) {
                StringBuilder sb = new StringBuilder("remove TextureView:");
                sb.append(this.mPlayer.getTextureView().toString());
                Utils.log(sb.toString());
            }
        }
    }

    public void setTextureView(TextureView textureView) {
        if (textureView != null) {
            StringBuilder sb = new StringBuilder("set TextureView:");
            sb.append(textureView.toString());
            Utils.log(sb.toString());
        }
        this.mPlayer.setTextureView(textureView);
    }

    public synchronized String getVideoUrl() {
        return this.mVideoUrl;
    }

    public boolean isCached(String str) {
        return this.mConfig.isCacheEnable() && this.mConfig.getCacheProxy().a(str);
    }

    public boolean isCaching(String str) {
        if (this.mConfig.isCacheEnable()) {
            etm cacheProxy = this.mConfig.getCacheProxy();
            etr.a(str, (String) "Url can't be null!");
            File file = cacheProxy.d.a;
            String generate = cacheProxy.d.b.generate(str);
            StringBuilder sb = new StringBuilder();
            sb.append(generate);
            sb.append(".download");
            if (new File(file, sb.toString()).exists()) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void bindPlayerView(String str, int i) {
        this.mVideoUrl = str;
        this.mObserverHash = i;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:6|7|8|9|10) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0041 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void start(java.lang.String r8, int r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            r7.bindPlayerView(r8, r9)     // Catch:{ all -> 0x0079 }
            r9 = 1
            r7.onPlayStateChanged(r9)     // Catch:{ all -> 0x0079 }
            java.lang.String r0 = "start loading video, hash=%d, url=%s"
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ all -> 0x0079 }
            int r3 = r7.mObserverHash     // Catch:{ all -> 0x0079 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0079 }
            r4 = 0
            r2[r4] = r3     // Catch:{ all -> 0x0079 }
            java.lang.String r3 = r7.mVideoUrl     // Catch:{ all -> 0x0079 }
            r2[r9] = r3     // Catch:{ all -> 0x0079 }
            java.lang.String r0 = java.lang.String.format(r0, r2)     // Catch:{ all -> 0x0079 }
            com.autonavi.minimap.ajx3.widget.view.video.util.Utils.log(r0)     // Catch:{ all -> 0x0079 }
            com.autonavi.minimap.ajx3.widget.view.video.player.Config r0 = r7.mConfig     // Catch:{ all -> 0x0079 }
            boolean r0 = r0.isCacheEnable()     // Catch:{ all -> 0x0079 }
            if (r0 == 0) goto L_0x006f
            com.autonavi.minimap.ajx3.widget.view.video.player.Config r0 = r7.mConfig     // Catch:{ all -> 0x0079 }
            etm r0 = r0.getCacheProxy()     // Catch:{ all -> 0x0079 }
            boolean r2 = r0.a(r8)     // Catch:{ all -> 0x0079 }
            if (r2 == 0) goto L_0x004a
            java.io.File r8 = r0.b(r8)     // Catch:{ all -> 0x0079 }
            etj r0 = r0.d     // Catch:{ IOException -> 0x0041 }
            etx r0 = r0.c     // Catch:{ IOException -> 0x0041 }
            r0.a(r8)     // Catch:{ IOException -> 0x0041 }
        L_0x0041:
            android.net.Uri r8 = android.net.Uri.fromFile(r8)     // Catch:{ all -> 0x0079 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0079 }
            goto L_0x006f
        L_0x004a:
            etq r2 = r0.e     // Catch:{ all -> 0x0079 }
            boolean r2 = r2.a()     // Catch:{ all -> 0x0079 }
            if (r2 == 0) goto L_0x006f
            java.util.Locale r2 = java.util.Locale.US     // Catch:{ all -> 0x0079 }
            java.lang.String r3 = "http://%s:%d/%s"
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0079 }
            java.lang.String r6 = "127.0.0.1"
            r5[r4] = r6     // Catch:{ all -> 0x0079 }
            int r0 = r0.c     // Catch:{ all -> 0x0079 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x0079 }
            r5[r9] = r0     // Catch:{ all -> 0x0079 }
            java.lang.String r8 = defpackage.ett.a(r8)     // Catch:{ all -> 0x0079 }
            r5[r1] = r8     // Catch:{ all -> 0x0079 }
            java.lang.String r8 = java.lang.String.format(r2, r3, r5)     // Catch:{ all -> 0x0079 }
        L_0x006f:
            com.autonavi.minimap.ajx3.widget.view.video.player.AbstractPlayer r0 = r7.mPlayer     // Catch:{ all -> 0x0079 }
            r0.start(r8)     // Catch:{ all -> 0x0079 }
            r7.notifyVideoFocus(r9)     // Catch:{ all -> 0x0079 }
            monitor-exit(r7)
            return
        L_0x0079:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.view.video.player.PlayerManager.start(java.lang.String, int):void");
    }

    public synchronized void play() {
        onPlayStateChanged(1);
        Utils.log(String.format("play video, hash=%d, url=%s", new Object[]{Integer.valueOf(this.mObserverHash), this.mVideoUrl}));
        this.mPlayer.play();
        onPlayStateChanged(2);
        notifyVideoFocus(true);
    }

    public synchronized void resume() {
        if (getState() == 5) {
            Utils.log(String.format("resume video, hash=%d, url=%s", new Object[]{Integer.valueOf(this.mObserverHash), this.mVideoUrl}));
            play();
        }
    }

    public synchronized void pause() {
        if (!(getState() == 2 || getState() == 3 || getState() == 4)) {
            if (getState() != 8) {
                Utils.log(String.format("pause video for state: %d, hash=%d, url=%s", new Object[]{Integer.valueOf(getState()), Integer.valueOf(this.mObserverHash), this.mVideoUrl}));
                return;
            }
        }
        Utils.log(String.format("pause video, hash=%d, url=%s", new Object[]{Integer.valueOf(this.mObserverHash), this.mVideoUrl}));
        this.mPlayer.pause();
        onPlayStateChanged(5);
        notifyVideoFocus(false);
    }

    public synchronized void stop() {
        if (!(getState() == -1 || this.mVideoUrl == null || this.mObserverHash == -1)) {
            Utils.log(String.format("stop video, hash=%d, url=%s", new Object[]{Integer.valueOf(this.mObserverHash), this.mVideoUrl}));
            onPlayStateChanged(0);
            this.mPlayer.stop();
            removeTextureView();
            this.mObserverHash = -1;
            this.mVideoUrl = null;
            this.mScreenState = 1;
            notifyVideoFocus(false);
        }
    }

    public synchronized void release() {
        if (getState() != -1) {
            Utils.log("release player");
            onPlayStateChanged(0);
            removeTextureView();
            this.mPlayer.release();
            this.mObserverHash = -1;
            this.mVideoUrl = null;
            this.mScreenState = 1;
            notifyVideoFocus(false);
        }
    }

    public synchronized boolean hasViewPlaying() {
        return this.mObserverHash != -1;
    }

    public boolean isPlaying() {
        return this.mPlayer.isPlaying();
    }

    public synchronized boolean isViewPlaying(int i) {
        return this.mObserverHash == i;
    }

    public long getCurrentPosition() {
        return this.mPlayer.getCurrentPosition();
    }

    public void seekTo(long j) {
        if (isPlaying()) {
            onPlayStateChanged(1);
        }
        this.mPlayer.seekTo(j);
    }

    public int getState() {
        if (this.mPlayer == null) {
            return -1;
        }
        return this.mPlayer.getState();
    }

    public synchronized void onError(String str) {
        String str2;
        if ("error video, error= ".concat(String.valueOf(str)) == null) {
            str2 = "null";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(", url=");
            sb.append(this.mVideoUrl);
            str2 = sb.toString();
        }
        Utils.log(str2);
        TextUtils.isEmpty(str);
        this.mPlayer.stop();
        changeUIState(7);
    }

    public void onComplete() {
        changeUIState(6);
    }

    public void onPlayStateChanged(int i) {
        changeUIState(i);
    }

    public synchronized void onDurationChanged(long j) {
        this.mPlayStateObservable.notify(new DurationMessage(this.mObserverHash, this.mVideoUrl, j));
    }

    public synchronized void onSizeChanged(int i, int i2) {
        this.mPlayStateObservable.notify(new VideoSizeMessage(this.mObserverHash, this.mVideoUrl, i, i2));
    }

    public void setScreenState(int i) {
        this.mScreenState = i;
    }

    private synchronized void changeUIState(int i) {
        this.mPlayer.setState(i);
        this.mPlayStateObservable.notify(new UIStateMessage(this.mObserverHash, this.mVideoUrl, i));
    }

    public void addObserver(Observer observer) {
        this.mPlayStateObservable.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        this.mPlayStateObservable.deleteObserver(observer);
    }

    private void notifyVideoFocus(boolean z) {
        Context context = getConfig().getContext();
        if (context != null && z != this.mLastFocused) {
            this.mLastFocused = z;
            Intent intent = new Intent();
            intent.setPackage(context.getPackageName());
            intent.setAction(ACTION_VIDEO_FOCUS);
            intent.putExtra("ID", String.valueOf(this.mObserverHash));
            intent.putExtra(DATA_FOCUS_STATE, z ? 1 : 0);
            context.sendBroadcast(intent);
        }
    }

    private void testVideoFocus() {
        getConfig().getContext().registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                StringBuilder sb = new StringBuilder("ID = ");
                sb.append(intent.getStringExtra("ID"));
                sb.append(" STATE= ");
                sb.append(intent.getIntExtra(PlayerManager.DATA_FOCUS_STATE, -1));
            }
        }, new IntentFilter(ACTION_VIDEO_FOCUS));
    }
}
