package com.autonavi.minimap.ajx3.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

class AjxAudioPlayer implements OnAudioFocusChangeListener, OnCompletionListener, OnErrorListener, OnPreparedListener {
    private static final int DEFAULT_STREAM_MODE = 3;
    private static final String EVENT_DESTROY = "onDestroyed";
    private static final String EVENT_ERROR = "onError";
    private static final String EVENT_FINISH = "onFinished";
    private static final String EVENT_FOCUS = "onFocusChanged";
    private static final String EVENT_IDLE = "onIdle";
    private static final String EVENT_PAUSE = "onPaused";
    private static final String EVENT_PLAY = "onPlaying";
    private static final String EVENT_PREPARE = "onPreparing";
    private static final String EVENT_READY = "onReady";
    private static final String STATE_DESTROY = "destroyed";
    private static final String STATE_FINISH = "finished";
    private static final String STATE_IDLE = "idle";
    private static final String STATE_PAUSE = "paused";
    private static final String STATE_PLAY = "playing";
    private static final String STATE_PREPARE = "preparing";
    private static final String STATE_READY = "ready";
    private ConcurrentHashMap<Long, HashSet<AjxAudioContext>> mAjxAudioContexts = new ConcurrentHashMap<>();
    private final AudioManager mAudioManager;
    private String mCurrentSrc;
    private String mId;
    private MediaPlayer mMediaPlayer;
    private String mPrepareTrigger;
    private AtomicBoolean mPrepared = new AtomicBoolean(false);
    private String mState = STATE_IDLE;

    public AjxAudioPlayer(Context context, String str) {
        this.mId = str;
        this.mAudioManager = (AudioManager) context.getApplicationContext().getSystemService("audio");
    }

    public synchronized void prepare(String str, String str2) {
        this.mPrepared.set(false);
        this.mCurrentSrc = str;
        destroyMediaPlayer();
        this.mMediaPlayer = createMediaPlayer();
        if (!STATE_IDLE.equals(this.mState)) {
            this.mState = STATE_IDLE;
            notifyJS(EVENT_IDLE, getEventParams(str2));
        }
        String str3 = "";
        try {
            if (!TextUtils.isEmpty(str)) {
                loadResource(str);
                this.mPrepareTrigger = str2;
                this.mState = STATE_PREPARE;
                notifyJS(EVENT_PREPARE, getEventParams(str2));
                this.mMediaPlayer.prepareAsync();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            str3 = e.getMessage();
        }
        this.mState = STATE_IDLE;
        JSONObject eventParams = getEventParams(str2);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("errorCode", -1);
            jSONObject.put("errorType", "resourceNotReady");
            jSONObject.put("errorMessage", "音频路径异常.".concat(String.valueOf(str3)));
            eventParams.put("error", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        notifyJS(EVENT_ERROR, eventParams);
    }

    public synchronized void play(String str) {
        if (this.mPrepared.get()) {
            if (this.mAudioManager != null) {
                this.mAudioManager.requestAudioFocus(this, 3, 1);
            }
            if (!this.mMediaPlayer.isPlaying()) {
                this.mMediaPlayer.start();
                this.mState = STATE_PLAY;
                notifyJS(EVENT_PLAY, getEventParams(str));
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void pause(java.lang.String r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.concurrent.atomic.AtomicBoolean r0 = r1.mPrepared     // Catch:{ all -> 0x0028 }
            boolean r0 = r0.get()     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0026
            android.media.MediaPlayer r0 = r1.mMediaPlayer     // Catch:{ all -> 0x0028 }
            boolean r0 = r0.isPlaying()     // Catch:{ all -> 0x0028 }
            if (r0 != 0) goto L_0x0012
            goto L_0x0026
        L_0x0012:
            android.media.MediaPlayer r0 = r1.mMediaPlayer     // Catch:{ all -> 0x0028 }
            r0.pause()     // Catch:{ all -> 0x0028 }
            java.lang.String r0 = "paused"
            r1.mState = r0     // Catch:{ all -> 0x0028 }
            java.lang.String r0 = "onPaused"
            org.json.JSONObject r2 = r1.getEventParams(r2)     // Catch:{ all -> 0x0028 }
            r1.notifyJS(r0, r2)     // Catch:{ all -> 0x0028 }
            monitor-exit(r1)
            return
        L_0x0026:
            monitor-exit(r1)
            return
        L_0x0028:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.audio.AjxAudioPlayer.pause(java.lang.String):void");
    }

    public synchronized void setCurrentTime(int i) {
        if (this.mPrepared.get()) {
            int totalTime = getTotalTime();
            if (i < 0) {
                i = 0;
            } else if (i > totalTime) {
                i = totalTime;
            }
            this.mMediaPlayer.seekTo(i);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0016, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int getCurrentTime() {
        /*
            r2 = this;
            monitor-enter(r2)
            java.util.concurrent.atomic.AtomicBoolean r0 = r2.mPrepared     // Catch:{ all -> 0x0017 }
            boolean r0 = r0.get()     // Catch:{ all -> 0x0017 }
            r1 = 0
            if (r0 != 0) goto L_0x000c
            monitor-exit(r2)
            return r1
        L_0x000c:
            android.media.MediaPlayer r0 = r2.mMediaPlayer     // Catch:{ all -> 0x0017 }
            int r0 = r0.getCurrentPosition()     // Catch:{ all -> 0x0017 }
            if (r0 >= 0) goto L_0x0015
            r0 = 0
        L_0x0015:
            monitor-exit(r2)
            return r0
        L_0x0017:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.audio.AjxAudioPlayer.getCurrentTime():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0016, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int getTotalTime() {
        /*
            r2 = this;
            monitor-enter(r2)
            java.util.concurrent.atomic.AtomicBoolean r0 = r2.mPrepared     // Catch:{ all -> 0x0017 }
            boolean r0 = r0.get()     // Catch:{ all -> 0x0017 }
            r1 = 0
            if (r0 != 0) goto L_0x000c
            monitor-exit(r2)
            return r1
        L_0x000c:
            android.media.MediaPlayer r0 = r2.mMediaPlayer     // Catch:{ all -> 0x0017 }
            int r0 = r0.getDuration()     // Catch:{ all -> 0x0017 }
            if (r0 >= 0) goto L_0x0015
            r0 = 0
        L_0x0015:
            monitor-exit(r2)
            return r0
        L_0x0017:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.audio.AjxAudioPlayer.getTotalTime():int");
    }

    public synchronized String getSrc() {
        try {
        }
        return this.mCurrentSrc;
    }

    public synchronized String getState() {
        try {
        }
        return this.mState;
    }

    public void onAudioFocusChange(int i) {
        JSONObject eventParams = getEventParams("Android System");
        try {
            eventParams.put("audioFocus", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyJS(EVENT_FOCUS, eventParams);
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        this.mPrepared.set(true);
        this.mState = "ready";
        notifyJS(EVENT_READY, getEventParams(this.mPrepareTrigger));
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        this.mState = STATE_FINISH;
        JSONObject eventParams = getEventParams("Android System");
        try {
            eventParams.put("currentTime", getTotalTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyJS(EVENT_FINISH, eventParams);
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        this.mState = STATE_IDLE;
        JSONObject eventParams = getEventParams("Android System");
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("errorCode", i);
            jSONObject.put("errorExtra", i2);
            if (i == -1010) {
                jSONObject.put("errorType", "contentUnavailable");
                jSONObject.put("errorMessage", "Bitstream is conforming to the related coding standard or file spec, but the media framework does not support the feature.");
            } else if (i == -1007) {
                jSONObject.put("errorType", "contentUnavailable");
                jSONObject.put("errorMessage", "Bitstream is not conforming to the related coding standard or file spec.");
            } else if (i == -1004) {
                jSONObject.put("errorType", "contentUnavailable");
                jSONObject.put("errorMessage", "File or network related operation errors.");
            } else if (i == -110) {
                this.mState = "ready";
                jSONObject.put("errorType", "shouldRetry");
                jSONObject.put("errorMessage", "Some operation takes too long to complete, usually more than 3-5 seconds.");
            } else if (i == -38) {
                jSONObject.put("errorType", "resourceNotReady");
                jSONObject.put("errorMessage", "请先prepare()");
            } else if (i != 100) {
                jSONObject.put("errorType", "contentUnavailable");
                jSONObject.put("errorMessage", "UNKNOWN Error !!!");
            } else {
                jSONObject.put("errorType", "contentUnavailable");
                jSONObject.put("errorMessage", "Media server died. In this case, the application must release the MediaPlayer object and instantiate a new one.");
            }
            eventParams.put("error", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyJS(EVENT_ERROR, eventParams);
        return true;
    }

    public void attach(IAjxContext iAjxContext, JsFunctionCallback jsFunctionCallback) {
        HashSet hashSet = this.mAjxAudioContexts.get(Long.valueOf(iAjxContext.getId()));
        if (hashSet == null) {
            hashSet = new HashSet();
            this.mAjxAudioContexts.put(Long.valueOf(iAjxContext.getId()), hashSet);
        }
        hashSet.add(new AjxAudioContext(iAjxContext, this.mId, jsFunctionCallback));
    }

    public void detach(IAjxContext iAjxContext) {
        this.mAjxAudioContexts.remove(Long.valueOf(iAjxContext.getId()));
    }

    public boolean releasable() {
        return this.mAjxAudioContexts.isEmpty();
    }

    public void destroy(String str) {
        this.mState = STATE_DESTROY;
        notifyJS(EVENT_DESTROY, getEventParams(str));
        destroyMediaPlayer();
        this.mAjxAudioContexts.clear();
    }

    private synchronized MediaPlayer createMediaPlayer() {
        MediaPlayer mediaPlayer;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(3);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        return mediaPlayer;
    }

    private synchronized void destroyMediaPlayer() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setOnPreparedListener(null);
            this.mMediaPlayer.setOnCompletionListener(null);
            this.mMediaPlayer.setOnErrorListener(null);
            if (this.mMediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
            }
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
        }
        this.mMediaPlayer = null;
        if (this.mAudioManager != null) {
            this.mAudioManager.abandonAudioFocus(this);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0030, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void loadResource(java.lang.String r2) throws java.io.IOException {
        /*
            r1 = this;
            monitor-enter(r1)
            java.lang.String r0 = "http://"
            boolean r0 = r2.startsWith(r0)     // Catch:{ all -> 0x0038 }
            if (r0 != 0) goto L_0x0031
            java.lang.String r0 = "https://"
            boolean r0 = r2.startsWith(r0)     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x0012
            goto L_0x0031
        L_0x0012:
            java.lang.String r0 = "/"
            boolean r0 = r2.startsWith(r0)     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x002f
            r0 = 1
            java.lang.String r2 = r2.substring(r0)     // Catch:{ all -> 0x0038 }
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x0038 }
            r0.<init>(r2)     // Catch:{ all -> 0x0038 }
            boolean r0 = r0.exists()     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x002f
            android.media.MediaPlayer r0 = r1.mMediaPlayer     // Catch:{ all -> 0x0038 }
            r0.setDataSource(r2)     // Catch:{ all -> 0x0038 }
        L_0x002f:
            monitor-exit(r1)
            return
        L_0x0031:
            android.media.MediaPlayer r0 = r1.mMediaPlayer     // Catch:{ all -> 0x0038 }
            r0.setDataSource(r2)     // Catch:{ all -> 0x0038 }
            monitor-exit(r1)
            return
        L_0x0038:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.audio.AjxAudioPlayer.loadResource(java.lang.String):void");
    }

    private synchronized void notifyJS(String str, JSONObject jSONObject) {
        for (HashSet<AjxAudioContext> it : this.mAjxAudioContexts.values()) {
            Iterator it2 = it.iterator();
            while (it2.hasNext()) {
                ((AjxAudioContext) it2.next()).notifyJS(str, jSONObject);
            }
        }
    }

    private synchronized JSONObject getEventParams(String str) {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        try {
            jSONObject.put("name", this.mId);
            jSONObject.put("src", this.mCurrentSrc);
            jSONObject.put("state", this.mState);
            jSONObject.put("trigger", str);
            jSONObject.put("currentTime", getCurrentTime());
            jSONObject.put("totalTime", getTotalTime());
            jSONObject.put("error", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
