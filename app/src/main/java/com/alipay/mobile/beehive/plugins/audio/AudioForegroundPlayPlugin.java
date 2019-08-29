package com.alipay.mobile.beehive.plugins.audio;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.beehive.plugins.BaseBeehivePlugin;
import com.alipay.mobile.beehive.plugins.utils.H5PLogger;
import com.alipay.mobile.beehive.plugins.utils.H5ParamParser;
import com.alipay.mobile.beehive.plugins.utils.PathToLocalUtil;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.multimedia.apxmmusic.APMusicPlayerService;
import com.alipay.multimedia.apxmmusic.PlayError;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnBufferingUpdateListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnCompletionListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnErrorListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnInfoListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPausedListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPlayProgressUpdateListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPreparedListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPreparingListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnSeekCompleteListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnSeekingListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnStartListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnStopListener;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class AudioForegroundPlayPlugin extends BaseBeehivePlugin {
    private static final String ACTION_DESTROY = "destroyForegroundAudio";
    private static final String ACTION_GET_FOREGROUND_AUDIO_OPTION = "getForegroundAudioOption";
    private static final String ACTION_PAUSE = "pauseForegroundAudio";
    private static final String ACTION_PLAY = "playForegroundAudio";
    private static final String ACTION_SEEK = "seekForegroundAudio";
    private static final String ACTION_SET_FOREGROUND_AUDIO_OPTION = "setForegroundAudioOption";
    private static final String ACTION_START_MONITOR_FOREGROUND_AUDIO = "startMonitorForegroundAudio";
    private static final String ACTION_STOP = "stopForegroundAudio";
    private static final String ACTION_STOP_MONITOR_FOREGROUND_AUDIO = "stopMonitorForegroundAudio";
    private static final String ATTR_AUTO_PLAY = "autoplay";
    private static final String ATTR_BUFFERED = "buffered";
    private static final String ATTR_CURRENT_TIME = "currentTime";
    private static final String ATTR_DURATION = "duration";
    private static final String ATTR_LOOP = "loop";
    private static final String ATTR_PAUSED = "paused";
    private static final String ATTR_SRC = "src";
    private static final String ATTR_START_TIME = "startTime";
    private static final String ATTR_VOLUME = "volume";
    private static final String KEY_AUDIO_PLAYER_ID = "audioPlayerID";
    private static final String KEY_OPTION_ENTRY = "option";
    private static final String KEY_OPTION_NAME = "optionName";
    private static final String ON_CAN_PLAY = "onForegroundAudioCanPlay";
    private static final String ON_ENDED = "onForegroundAudioEnded";
    private static final String ON_ERROR = "onForegroundAudioError";
    private static final String ON_PAUSE = "onForegroundAudioPause";
    private static final String ON_PLAY = "onForegroundAudioPlay";
    private static final String ON_SEEKED = "onForegroundAudioSeeked";
    private static final String ON_SEEKING = "onForegroundAudioSeeking";
    private static final String ON_STOP = "onForegroundAudioStop";
    private static final String ON_TIME_UPDATE = "onForegroundAudioTimeUpdate";
    private static final String ON_WAITING = "onForegroundAudioWaiting";
    private a audioAdvice;
    private H5PLogger mLogger = H5PLogger.getLogger((String) "AudioForegroundPlayPlugin");
    /* access modifiers changed from: private */
    public Map<String, b> players = new HashMap();
    private String[] pointCut = {PointCutConstants.BASEACTIVITY_ONSTOP, PointCutConstants.BASEFRAGMENTACTIVITY_ONSTOP};

    static class a implements Advice {
        private WeakReference<AudioForegroundPlayPlugin> a;
        private H5PLogger b = H5PLogger.getLogger((String) "AudioAdvice");

        public a(AudioForegroundPlayPlugin wrapper) {
            this.a = new WeakReference<>(wrapper);
        }

        public final void onCallBefore(String s, Object o, Object[] objects) {
        }

        public final Pair<Boolean, Object> onCallAround(String s, Object o, Object[] objects) {
            return null;
        }

        public final void onCallAfter(String s, Object o, Object[] objects) {
        }

        public final void onExecutionBefore(String s, Object o, Object[] objects) {
        }

        public final Pair<Boolean, Object> onExecutionAround(String s, Object o, Object[] objects) {
            return null;
        }

        public final void onExecutionAfter(String s, Object o, Object[] objects) {
            this.b.d("onExecutionAfter:###");
            if (this.a != null) {
                AudioForegroundPlayPlugin w = (AudioForegroundPlayPlugin) this.a.get();
                if (w != null && w.players != null && !w.players.isEmpty()) {
                    for (b c : w.players.values()) {
                        c.b();
                    }
                    this.b.d("For each call pause.");
                }
            }
        }
    }

    private static class b implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPausedListener, OnPlayProgressUpdateListener, OnPreparedListener, OnPreparingListener, OnSeekCompleteListener, OnSeekingListener, OnStartListener, OnStopListener, Serializable {
        H5PLogger a = H5PLogger.getLogger((String) "PlayerInstance");
        /* access modifiers changed from: private */
        public APMediaPlayerService b;
        private String c;
        private boolean d;
        private boolean e;
        private String f;
        private H5Bridge g;
        private H5BridgeContext h;
        private Map<String, String> i = new HashMap();

        b(String id) {
            this.a.d("PlayerInstance<init> :id = " + id);
            APMusicPlayerService musicService = (APMusicPlayerService) MicroServiceUtil.getMicroService(APMusicPlayerService.class);
            if (musicService != null) {
                this.b = musicService.createPlayService(new Bundle());
            }
            this.f = id;
        }

        /* access modifiers changed from: private */
        public void a(H5BridgeContext c2) {
            this.a.d("play:### id=" + this.f);
            if (this.b.isPlaying() || this.b.getMediaPlayerState() == 1) {
                this.a.d("Playing ,ignore play calling.");
                return;
            }
            this.b.start();
            e(c2);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void b() {
            b(this.h);
        }

        /* access modifiers changed from: private */
        public void b(H5BridgeContext c2) {
            this.a.d("pause:### id=" + this.f);
            this.b.pause();
            e(c2);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void c(H5BridgeContext c2) {
            this.a.d("stop:### id=" + this.f);
            this.b.stop();
            e(c2);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void a(H5Event event, H5BridgeContext c2) {
            this.a.d("seek:### id=" + this.f + ",param = " + event.getParam());
            try {
                this.b.seekTo(event.getParam().getIntValue("position") * 1000);
                e(c2);
            } catch (Exception e2) {
                this.a.d("seek Exception :" + e2.getMessage());
                JSONObject res = new JSONObject();
                res.put((String) "success", (Object) Boolean.valueOf(false));
                res.put((String) "errorCode", (Object) Integer.valueOf(-1));
                res.put((String) AudioForegroundPlayPlugin.KEY_AUDIO_PLAYER_ID, (Object) this.f);
                c2.sendBridgeResultWithCallbackKept(res);
            }
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void d(H5BridgeContext c2) {
            this.a.d("destroy:### id=" + this.f);
            this.b.stop();
            e(c2);
            this.h = c2;
        }

        private void a(H5BridgeContext context, String key, String value) {
            JSONObject res = new JSONObject();
            res.put((String) "success", (Object) Boolean.valueOf(true));
            res.put(key, (Object) value);
            res.put((String) AudioForegroundPlayPlugin.KEY_AUDIO_PLAYER_ID, (Object) this.f);
            context.sendBridgeResultWithCallbackKept(res);
        }

        /* access modifiers changed from: private */
        public JSONObject c() {
            JSONObject res = new JSONObject();
            res.put((String) "success", (Object) Boolean.valueOf(true));
            res.put((String) AudioForegroundPlayPlugin.KEY_AUDIO_PLAYER_ID, (Object) this.f);
            return res;
        }

        /* access modifiers changed from: private */
        public void e(H5BridgeContext c2) {
            JSONObject res = new JSONObject();
            res.put((String) "success", (Object) Boolean.valueOf(true));
            res.put((String) AudioForegroundPlayPlugin.KEY_AUDIO_PLAYER_ID, (Object) this.f);
            c2.sendBridgeResultWithCallbackKept(res);
        }

        /* access modifiers changed from: private */
        public void f(H5BridgeContext c2) {
            this.a.d("getSrc:### id=" + this.f);
            String src = this.c;
            String apFilePath = this.i.get(src);
            if (!TextUtils.isEmpty(apFilePath)) {
                src = apFilePath;
            }
            a(c2, (String) AudioForegroundPlayPlugin.ATTR_SRC, src);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void a(String src, H5BridgeContext c2) {
            this.a.d("setSrc:### id=" + this.f);
            if (PathToLocalUtil.isAPFilePath(src, "audio")) {
                String absPath = PathToLocalUtil.decodeAbsPath(src, "audio");
                this.a.d("Path is apFilePath , parse AbsPath = " + src);
                this.i.put(absPath, src);
                src = absPath;
            }
            this.c = src;
            this.b.setDataSource(this.c);
            if (this.d) {
                this.a.d("OnAutoPlay true,call play when setSrc = " + src);
                this.b.start();
            }
            e(c2);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void g(H5BridgeContext c2) {
            this.a.d("getStartTime:### id=" + this.f);
            JSONObject r = c();
            r.put((String) "startTime", (Object) Float.valueOf(((float) this.b.getStartPosition()) / 1000.0f));
            c2.sendBridgeResultWithCallbackKept(r);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void a(int startTime, H5BridgeContext c2) {
            this.a.d("setStartTime:### id=" + this.f);
            this.b.setStartPosition(startTime * 1000);
            e(c2);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void a(float volume, H5BridgeContext c2) {
            this.a.d("setVolume:### id=" + this.f);
            if (volume < 0.0f || volume > 1.0f) {
                this.a.d("setVolume invalid param.");
                BaseBeehivePlugin.notifyInvalidParam(c2);
                return;
            }
            this.b.setVolume(volume);
            e(c2);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void h(H5BridgeContext c2) {
            this.a.d("getVolume:### id=" + this.f);
            JSONObject r = c();
            r.put((String) AudioForegroundPlayPlugin.ATTR_VOLUME, (Object) Float.valueOf(this.b.getVolume()));
            c2.sendBridgeResultWithCallbackKept(r);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void a(boolean loop, H5BridgeContext c2) {
            this.a.d("setLoop:### id=" + this.f);
            this.e = loop;
            this.b.setLooping(this.e);
            e(c2);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void i(H5BridgeContext c2) {
            this.a.d("getLoop:### id=" + this.f);
            JSONObject r = c();
            r.put((String) AudioForegroundPlayPlugin.ATTR_LOOP, (Object) Boolean.valueOf(this.e));
            c2.sendBridgeResultWithCallbackKept(r);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void j(H5BridgeContext c2) {
            this.a.d("getAutoPlay:### id=" + this.f);
            JSONObject r = c();
            r.put((String) AudioForegroundPlayPlugin.ATTR_AUTO_PLAY, (Object) Boolean.valueOf(this.d));
            c2.sendBridgeResultWithCallbackKept(r);
            this.h = c2;
        }

        /* access modifiers changed from: private */
        public void a(H5BridgeContext c2, boolean autoPlay) {
            this.a.d("setAutoPlay:### id=" + this.f);
            this.d = autoPlay;
            e(c2);
            this.h = c2;
        }

        public final void onBufferingUpdate(APMediaPlayerService apMediaPlayerService, String s, int i2) {
        }

        public final void onCompletion(APMediaPlayerService apMediaPlayerService, String s) {
            a((String) AudioForegroundPlayPlugin.ON_ENDED);
        }

        public final void onError(APMediaPlayerService apMediaPlayerService, String source, int what, int extra) {
            JSONObject obj = new JSONObject();
            obj.put((String) AudioForegroundPlayPlugin.KEY_AUDIO_PLAYER_ID, (Object) this.f);
            PlayError error = this.b.getError();
            String srcError = "Error NULL!";
            int errorCode = -1;
            if (error != null) {
                srcError = error.errorCode;
                errorCode = AudioForegroundPlayPlugin.mapErrorCode(error.errorCode);
            }
            this.a.d("onError:### id=" + this.f + ",srcError = " + srcError + ",dstError = " + errorCode);
            obj.put((String) "errCode", (Object) Integer.valueOf(errorCode));
            if (this.g != null) {
                this.g.sendDataWarpToWeb(AudioForegroundPlayPlugin.ON_ERROR, obj, null);
            }
        }

        public final void onInfo(APMediaPlayerService apMediaPlayerService, String s, int what, int extra) {
            if (701 == what) {
                a((String) AudioForegroundPlayPlugin.ON_WAITING);
            }
        }

        public final void onPaused(APMediaPlayerService apMediaPlayerService, String s) {
            a((String) AudioForegroundPlayPlugin.ON_PAUSE);
        }

        public final void onProgressUpdate(APMediaPlayerService apMediaPlayerService, String s, int i2, int i1) {
            a((String) AudioForegroundPlayPlugin.ON_TIME_UPDATE);
        }

        public final void onPrepared(APMediaPlayerService apMediaPlayerService, String s) {
            a((String) AudioForegroundPlayPlugin.ON_CAN_PLAY);
        }

        public final void onPreparing(APMediaPlayerService apMediaPlayerService, String s) {
            a((String) AudioForegroundPlayPlugin.ON_WAITING);
        }

        public final void onSeekComplete(APMediaPlayerService apMediaPlayerService, String s) {
            a((String) AudioForegroundPlayPlugin.ON_SEEKED);
        }

        public final void onSeeking(APMediaPlayerService apMediaPlayerService, String s) {
            a((String) AudioForegroundPlayPlugin.ON_SEEKING);
        }

        public final void onStart(APMediaPlayerService apMediaPlayerService, String s) {
            a((String) AudioForegroundPlayPlugin.ON_PLAY);
        }

        public final void onStop(APMediaPlayerService apMediaPlayerService, String s) {
            a((String) AudioForegroundPlayPlugin.ON_STOP);
        }

        private void a(String eventName) {
            this.a.d("broadcastEvent:### id=" + this.f + ",event = " + eventName);
            JSONObject obj = new JSONObject();
            obj.put((String) AudioForegroundPlayPlugin.KEY_AUDIO_PLAYER_ID, (Object) this.f);
            if (this.g != null) {
                this.g.sendDataWarpToWeb(eventName, obj, null);
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(H5Event event) {
            this.a.d("addMonitors:###");
            this.b.addOnBufferingUpdateListener(this);
            this.b.addOnCompletionListener(this);
            this.b.addOnErrorListener(this);
            this.b.addOnInfoListener(this);
            this.b.addOnPlayProgressUpdateListener(this);
            this.b.addOnPausedListener(this);
            this.b.addOnPreparingListener(this);
            this.b.addOnSeekCompleteListener(this);
            this.b.addOnPreparedListener(this);
            this.b.addOnStopListener(this);
            this.b.addOnSeekingListener(this);
            this.b.addOnStartListener(this);
            if (event != null) {
                H5Page p = event.getH5page();
                if (p != null) {
                    this.g = p.getBridge();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            this.a.d("removeMonitors:###");
            this.b.removeOnBufferingUpdateListener(this);
            this.b.removeOnCompletionListener(this);
            this.b.removeOnErrorListener(this);
            this.b.removeOnInfoListener(this);
            this.b.removeOnPlayProgressUpdateListener(this);
            this.b.removeOnPausedListener(this);
            this.b.removeOnPreparingListener(this);
            this.b.removeOnSeekCompleteListener(this);
            this.b.removeOnPreparedListener(this);
            this.b.removeOnStopListener(this);
            this.b.removeOnSeekingListener(this);
            this.b.removeOnStartListener(this);
            this.g = null;
        }
    }

    /* access modifiers changed from: protected */
    public String[] registerAction() {
        return new String[]{ACTION_PLAY, ACTION_PAUSE, ACTION_STOP, ACTION_SEEK, ACTION_DESTROY, ACTION_GET_FOREGROUND_AUDIO_OPTION, ACTION_SET_FOREGROUND_AUDIO_OPTION, ACTION_START_MONITOR_FOREGROUND_AUDIO, ACTION_STOP_MONITOR_FOREGROUND_AUDIO};
    }

    /* access modifiers changed from: protected */
    public boolean onActionCalled(String action, H5Event event, H5BridgeContext c, Bundle bundle) {
        JSONObject param = event.getParam();
        String id = H5ParamParser.getString(param, KEY_AUDIO_PLAYER_ID);
        if (TextUtils.isEmpty(id)) {
            id = "DEFAULT_PLAYER";
        }
        b p = this.players.get(id);
        if (p == null) {
            p = new b(id);
            this.players.put(id, p);
        }
        dispatchAction(id, action, event, c, param, p);
        return true;
    }

    private void dispatchAction(String id, String action, H5Event event, H5BridgeContext c, JSONObject param, b p) {
        this.mLogger.d("dispatchAction:### " + action);
        if (TextUtils.equals(ACTION_DESTROY, action)) {
            this.players.remove(id);
            p.d(c);
        } else if (TextUtils.equals(ACTION_PLAY, action)) {
            p.a(c);
        } else if (TextUtils.equals(ACTION_PAUSE, action)) {
            p.b(c);
        } else if (TextUtils.equals(ACTION_STOP, action)) {
            p.c(c);
        } else if (TextUtils.equals(ACTION_SEEK, action)) {
            p.a(event, c);
        } else if (TextUtils.equals(ACTION_SET_FOREGROUND_AUDIO_OPTION, action)) {
            onSetOption(c, param, p);
        } else if (TextUtils.equals(ACTION_GET_FOREGROUND_AUDIO_OPTION, action)) {
            onGetOption(c, param, p);
        } else if (TextUtils.equals(ACTION_START_MONITOR_FOREGROUND_AUDIO, action)) {
            p.a(event);
            p.e(c);
        } else if (TextUtils.equals(ACTION_STOP_MONITOR_FOREGROUND_AUDIO, action)) {
            p.a();
            p.e(c);
        }
    }

    private void onSetOption(H5BridgeContext c, JSONObject param, b p) {
        this.mLogger.d("onSetOption:### " + param);
        if (param == null) {
            notifyInvalidParam(c);
            return;
        }
        JSONObject kv = param.getJSONObject(KEY_OPTION_ENTRY);
        if (kv == null) {
            notifyInvalidParam(c);
        } else if (kv.containsKey(ATTR_SRC)) {
            p.a(kv.getString(ATTR_SRC), c);
        } else if (kv.containsKey(ATTR_AUTO_PLAY)) {
            p.a(c, kv.getBooleanValue(ATTR_AUTO_PLAY));
        } else if (kv.containsKey(ATTR_LOOP)) {
            p.a(kv.getBooleanValue(ATTR_LOOP), c);
        } else if (kv.containsKey("startTime")) {
            p.a(kv.getIntValue("startTime"), c);
        } else if (kv.containsKey(ATTR_VOLUME)) {
            p.a(kv.getFloatValue(ATTR_VOLUME), c);
        } else {
            notifyInvalidParam(c);
        }
    }

    private void onGetOption(H5BridgeContext c, JSONObject param, b p) {
        this.mLogger.d("onGetOption:### " + param);
        if (param != null) {
            String optionName = param.getString(KEY_OPTION_NAME);
            if (ATTR_SRC.equalsIgnoreCase(optionName)) {
                p.f(c);
            } else if (ATTR_AUTO_PLAY.equalsIgnoreCase(optionName)) {
                p.j(c);
            } else if (ATTR_LOOP.equalsIgnoreCase(optionName)) {
                p.i(c);
            } else if ("startTime".equalsIgnoreCase(optionName)) {
                p.g(c);
            } else if (ATTR_VOLUME.equalsIgnoreCase(optionName)) {
                p.h(c);
            } else if (ATTR_DURATION.equalsIgnoreCase(optionName)) {
                JSONObject r = p.c();
                r.put((String) ATTR_DURATION, (Object) Long.valueOf(p.b.getDuration() / 1000));
                c.sendBridgeResultWithCallbackKept(r);
            } else if (ATTR_CURRENT_TIME.equalsIgnoreCase(optionName)) {
                JSONObject r2 = p.c();
                r2.put((String) ATTR_CURRENT_TIME, (Object) Float.valueOf(((float) p.b.getCurrentPosition()) / 1000.0f));
                c.sendBridgeResultWithCallbackKept(r2);
            } else if (ATTR_BUFFERED.equalsIgnoreCase(optionName)) {
                JSONObject r3 = p.c();
                r3.put((String) ATTR_BUFFERED, (Object) Integer.valueOf(p.b.getBufferedPercent()));
                c.sendBridgeResultWithCallbackKept(r3);
            } else if (ATTR_PAUSED.equalsIgnoreCase(optionName)) {
                boolean paused = p.b.getMediaPlayerState() == 6;
                JSONObject r4 = p.c();
                r4.put((String) ATTR_PAUSED, (Object) Boolean.valueOf(paused));
                c.sendBridgeResultWithCallbackKept(r4);
            } else {
                notifyInvalidParam(c);
            }
        } else {
            notifyInvalidParam(c);
        }
    }

    public void onRelease() {
        this.mLogger.d("onRelease:### ");
        super.onRelease();
        if (this.players != null && !this.players.isEmpty()) {
            for (b b2 : this.players.values()) {
                b2.b.stop();
            }
            this.players.clear();
        }
        if (this.audioAdvice != null) {
            FrameworkPointCutManager.getInstance().unRegisterPointCutAdvice(this.audioAdvice);
        }
    }

    public static int mapErrorCode(int errorCode) {
        switch (errorCode) {
            case -2046:
            case 100:
                return 1002;
            case -1010:
            case -110:
                return 1001;
            case -1007:
            case 200:
                return 1004;
            case -1004:
                return 1003;
            default:
                return -1;
        }
    }

    public void onInitialize(H5CoreNode coreNode) {
        this.mLogger.d("onInitialize:### ");
        super.onInitialize(coreNode);
        if (this.audioAdvice == null) {
            this.audioAdvice = new a(this);
            FrameworkPointCutManager.getInstance().registerPointCutAdvice(this.pointCut, (Advice) this.audioAdvice);
        }
    }
}
