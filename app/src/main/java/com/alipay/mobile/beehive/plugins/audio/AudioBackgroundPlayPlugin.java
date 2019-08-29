package com.alipay.mobile.beehive.plugins.audio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.beehive.audio.model.AudioDetail;
import com.alipay.mobile.beehive.audio.utils.BundleLogger;
import com.alipay.mobile.beehive.audio.v2.AudioPlayerStateDetector;
import com.alipay.mobile.beehive.audio.v2.GlobalAudioPlayer;
import com.alipay.mobile.beehive.audio.v2.PlayerState;
import com.alipay.mobile.beehive.plugins.BaseBeehivePlugin;
import com.alipay.mobile.beehive.plugins.utils.H5PLogger;
import com.alipay.mobile.beehive.plugins.utils.H5ParamParser;
import com.alipay.mobile.beehive.plugins.utils.PathToLocalUtil;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AudioBackgroundPlayPlugin extends BaseBeehivePlugin {
    private static final String ACTION_CANCEL_MONITOR_AUDIO_PLAYER_STATE = "stopMonitorBackgroundAudio";
    private static final String ACTION_GET_BACKGROUND_AUDIO_OPTION = "getBackgroundAudioOption";
    private static final String ACTION_GET_STATE = "getBackgroundAudioPlayerState";
    private static final String ACTION_MONITOR_AUDIO_PLAYER_STATE = "startMonitorBackgroundAudio";
    private static final String ACTION_PAUSE = "pauseBackgroundAudio";
    private static final String ACTION_PLAY = "playBackgroundAudio";
    private static final String ACTION_SEEK = "seekBackgroundAudio";
    private static final String ACTION_SET_BACKGROUND_AUDIO_OPTION = "setBackgroundAudioOption";
    private static final String ACTION_STOP = "stopBackgroundAudio";
    private static final String ATTR_BUFFERED = "buffered";
    private static final String ATTR_COVER_IMAGE_URL = "coverImgUrl";
    private static final String ATTR_CURRENT_TIME = "currentTime";
    private static final String ATTR_DURATION = "duration";
    private static final String ATTR_EPNAME = "epname";
    private static final String ATTR_PAUSED = "paused";
    private static final String ATTR_SINGER = "singer";
    private static final String ATTR_SRC = "src";
    private static final String ATTR_START_TIME = "startTime";
    private static final String ATTR_TITLE = "title";
    private static final String ATTR_WEB_URL = "webUrl";
    private static final int CODE_FAIL = 0;
    private static final int CODE_SUCCESS = 1;
    public static final String JS_STATE_UPDATE_CALLBACK = "getBackgroundAudioPlayedStateInfo";
    private static final String KEY_AUDIO_EXTRA_INFO = "bizExtraParamsJsonObj";
    private static final String KEY_BIZ_IDENTIFIER = "bizIdentifier";
    private static final String KEY_OPTION_ENTRY = "option";
    private static final String KEY_OPTION_NAME = "optionName";
    private static final String KEY_PLAY_WITH_NO_PARAM = "playWithNoParam";
    private static Map<String, String> mAPFilePathMap = new HashMap();
    private static H5PLogger sLogger = H5PLogger.getLogger((String) "AudioBackgroundPlayPlugin");
    /* access modifiers changed from: private */
    public static Map<String, H5BridgeContext> sMotoringPages = new HashMap();
    private static a stateDetector = new a(AudioPlayerStateDetector.CARE_EVERY_SONG);
    private H5PLogger mLogger = H5PLogger.getLogger((String) "AudioBackgroundPlayPlugin");

    private static class a extends AudioPlayerStateDetector {
        private BundleLogger a = BundleLogger.getLogger(a.class);

        public a(String url) {
            super(url);
        }

        /* access modifiers changed from: protected */
        public void onStateChange(AudioDetail audioDetail, PlayerState state, Map<String, Object> extraInfo) {
            this.a.d("onStateChange:###");
            List monitors = new LinkedList();
            for (H5BridgeContext c : AudioBackgroundPlayPlugin.sMotoringPages.values()) {
                monitors.add(c);
            }
            if (monitors.isEmpty()) {
                this.a.d("No monitor!");
            } else if (audioDetail != null) {
                this.a.d("Monitors :" + monitors);
                JSONObject result = new JSONObject();
                result.put((String) AudioBackgroundPlayPlugin.ATTR_DURATION, (Object) Float.valueOf(((float) audioDetail.duration) / 1000.0f));
                result.put((String) "currentPosition", (Object) Float.valueOf(((float) audioDetail.playedTime) / 1000.0f));
                result.put((String) "downloadPercent", (Object) Integer.valueOf(audioDetail.bufferedPercent));
                int stateAfterCovert = AudioBackgroundPlayPlugin.convertState(state);
                result.put((String) "status", (Object) Integer.valueOf(stateAfterCovert));
                result.put((String) Constants.KEY_AUDIO_URL, (Object) AudioBackgroundPlayPlugin.checkAPFilePathMap(audioDetail.url));
                this.a.d("AudioStateLink## dst = " + stateAfterCovert + " middle = " + state);
                AudioBackgroundPlayPlugin.sendBizExtBack(audioDetail, result);
                a(monitors, result, a(extraInfo, result), a(extraInfo));
            } else {
                this.a.d("AudioDetail null!");
            }
        }

        private void a(List<H5BridgeContext> monitors, JSONObject result, JSONObject newError, String newEvent) {
            JSONObject wrapper = new JSONObject();
            wrapper.put((String) "data", (Object) result);
            for (H5BridgeContext p : monitors) {
                this.a.d("doSendEvents to web");
                p.sendToWeb(AudioBackgroundPlayPlugin.JS_STATE_UPDATE_CALLBACK, wrapper, null);
                a(newError, newEvent, p);
            }
        }

        @Nullable
        private static String a(Map<String, Object> extraInfo) {
            if (extraInfo == null || !extraInfo.containsKey(AudioPlayerStateDetector.KEY_AUDIO_EVENT)) {
                return null;
            }
            return (String) extraInfo.get(AudioPlayerStateDetector.KEY_AUDIO_EVENT);
        }

        @Nullable
        private static JSONObject a(Map<String, Object> extraInfo, JSONObject result) {
            if (extraInfo == null || !extraInfo.containsKey("errorCode")) {
                return null;
            }
            int errorCode = ((Integer) extraInfo.get("errorCode")).intValue();
            result.put((String) "errorCode", (Object) Integer.valueOf(errorCode));
            JSONObject newError = new JSONObject();
            newError.put((String) "errCode", (Object) Integer.valueOf(AudioForegroundPlayPlugin.mapErrorCode(errorCode)));
            return newError;
        }

        private void a(JSONObject newError, String newEvent, H5BridgeContext bridge) {
            if (!TextUtils.isEmpty(newEvent)) {
                JSONObject o = null;
                if (AudioPlayerStateDetector.ON_ERROR.equalsIgnoreCase(newEvent)) {
                    o = newError;
                }
                bridge.sendToWeb(newEvent, o, null);
                this.a.d("Do Send event :### " + newEvent + ",obj = " + o);
                return;
            }
            this.a.d("Event empty!");
        }
    }

    /* access modifiers changed from: private */
    public static void sendBizExtBack(AudioDetail audioDetail, JSONObject result) {
        if (audioDetail != null && audioDetail.extraInfo != null) {
            Serializable bizExt = audioDetail.extraInfo.getSerializable(KEY_AUDIO_EXTRA_INFO);
            if (bizExt != null) {
                result.put((String) KEY_AUDIO_EXTRA_INFO, (Object) bizExt);
            }
        }
    }

    /* access modifiers changed from: protected */
    public String[] registerAction() {
        return new String[]{ACTION_PLAY, ACTION_PAUSE, ACTION_STOP, ACTION_SEEK, ACTION_GET_STATE, ACTION_MONITOR_AUDIO_PLAYER_STATE, ACTION_CANCEL_MONITOR_AUDIO_PLAYER_STATE, ACTION_SET_BACKGROUND_AUDIO_OPTION, ACTION_GET_BACKGROUND_AUDIO_OPTION};
    }

    /* access modifiers changed from: protected */
    public boolean onActionCalled(String action, H5Event event, H5BridgeContext context, Bundle bundle) {
        this.mLogger.d("handleEvent param: " + event.getParam() + "action: " + action);
        if (ACTION_PLAY.equals(action)) {
            handleActionPlay(event, context);
            return true;
        } else if (ACTION_PAUSE.equals(action)) {
            handleActionPause(context);
            return true;
        } else if (ACTION_SEEK.equals(action)) {
            handleActionSeek(event, context);
            return true;
        } else if (ACTION_STOP.equals(action)) {
            handleActionStop(context);
            return true;
        } else if (ACTION_GET_STATE.equals(action)) {
            handleActionGetState(context);
            return true;
        } else if (ACTION_MONITOR_AUDIO_PLAYER_STATE.equals(action)) {
            handleMonitorPlayerState(event, context);
            return true;
        } else if (ACTION_CANCEL_MONITOR_AUDIO_PLAYER_STATE.equals(action)) {
            handleCancelMonitorPlayerState(event, context);
            return true;
        } else if (TextUtils.equals(ACTION_SET_BACKGROUND_AUDIO_OPTION, action)) {
            onSetOption(context, event.getParam());
            return true;
        } else if (!TextUtils.equals(ACTION_GET_BACKGROUND_AUDIO_OPTION, action)) {
            return context.sendError(event, Error.INVALID_PARAM);
        } else {
            onGetOption(context, event.getParam());
            return true;
        }
    }

    private void onSetOption(H5BridgeContext c, JSONObject param) {
        this.mLogger.d("onSetOption:### " + param);
        if (param == null) {
            notifyInvalidParam(c);
            return;
        }
        JSONObject kv = param.getJSONObject(KEY_OPTION_ENTRY);
        if (kv == null) {
            notifyInvalidParam(c);
            return;
        }
        checkBizIdentifierSame(param);
        AudioDetail d = GlobalAudioPlayer.getInstance().getLatestSongDetail();
        GlobalAudioPlayer p = GlobalAudioPlayer.getInstance();
        boolean setSuccess = false;
        if (kv.containsKey(ATTR_SRC)) {
            d.url = parseAPFilePath(kv.getString(ATTR_SRC));
            p.playAudio();
            setSuccess = true;
            this.mLogger.d("Play when set src = " + d.url);
        } else if (kv.containsKey("startTime")) {
            p.setStartTime(kv.getIntValue("startTime") * 1000);
            setSuccess = true;
        } else if (kv.containsKey("title")) {
            d.name = kv.getString("title");
            setSuccess = true;
        } else if (kv.containsKey(ATTR_EPNAME)) {
            d.epname = kv.getString(ATTR_EPNAME);
            setSuccess = true;
        } else if (kv.containsKey(ATTR_SINGER)) {
            d.author = kv.getString(ATTR_SINGER);
            setSuccess = true;
        } else if (kv.containsKey("coverImgUrl")) {
            d.coverImg = kv.getString("coverImgUrl");
            setSuccess = true;
        } else if (kv.containsKey(ATTR_WEB_URL)) {
            d.webUrl = kv.getString(ATTR_WEB_URL);
            setSuccess = true;
        } else {
            notifyInvalidParam(c);
        }
        if (setSuccess) {
            JSONObject r = new JSONObject();
            r.put((String) "success", (Object) Integer.valueOf(1));
            c.sendBridgeResultWithCallbackKept(r);
            GlobalAudioPlayer.getInstance().notifyUpdate();
        }
    }

    private String parseAPFilePath(String path) {
        if (!PathToLocalUtil.isAPFilePath(path, "audio")) {
            return path;
        }
        String absPath = PathToLocalUtil.decodeAbsPath(path, "audio");
        this.mLogger.d("Covert apFilePath to absPath : " + path + " -> " + absPath);
        mAPFilePathMap.put(absPath, path);
        return absPath;
    }

    /* access modifiers changed from: private */
    public static String checkAPFilePathMap(String src) {
        String retPath = src;
        if (!mAPFilePathMap.containsKey(src)) {
            return retPath;
        }
        String retPath2 = mAPFilePathMap.get(src);
        sLogger.d("Return mapped APFilePath : absPath = " + src + " -> " + retPath2);
        return retPath2;
    }

    private void checkBizIdentifierSame(JSONObject param) {
        this.mLogger.d("checkBizIdentifierSame:### " + param);
        String bizIdentifier = param.getString(KEY_BIZ_IDENTIFIER);
        if (TextUtils.isEmpty(bizIdentifier)) {
            bizIdentifier = AudioDetail.DEFAULT_BIZ_IDENTIFIER;
        }
        AudioDetail d = GlobalAudioPlayer.getInstance().getLatestSongDetail();
        if (d == null || !TextUtils.equals(d.bizIdentifier, bizIdentifier)) {
            this.mLogger.d("biz different : src = " + (d == null ? MiscUtil.NULL_STR : d.bizIdentifier) + ",dst = " + bizIdentifier);
            GlobalAudioPlayer.getInstance().stopAudio();
            AudioDetail newDetail = new AudioDetail();
            newDetail.bizIdentifier = bizIdentifier;
            GlobalAudioPlayer.getInstance().setAudioDetail(newDetail);
            this.mLogger.d("Reset audio detail.");
        }
    }

    private void onGetOption(H5BridgeContext c, JSONObject param) {
        this.mLogger.d("onGetOption:### " + param);
        if (param == null) {
            notifyInvalidParam(c);
            return;
        }
        AudioDetail d = GlobalAudioPlayer.getInstance().getLatestSongDetail();
        GlobalAudioPlayer p = GlobalAudioPlayer.getInstance();
        String op = param.getString(KEY_OPTION_NAME);
        if (ATTR_SRC.equalsIgnoreCase(op)) {
            notifyAttr(c, ATTR_SRC, checkAPFilePathMap(d.url));
        } else if ("startTime".equalsIgnoreCase(op)) {
            JSONObject r = new JSONObject();
            r.put((String) "success", (Object) Integer.valueOf(1));
            r.put((String) "startTime", (Object) Float.valueOf(((float) p.getStartTime()) / 1000.0f));
            c.sendBridgeResultWithCallbackKept(r);
        } else if ("title".equalsIgnoreCase(op)) {
            notifyAttr(c, "title", d.name);
        } else if (ATTR_EPNAME.equalsIgnoreCase(op)) {
            notifyAttr(c, ATTR_EPNAME, d.epname);
        } else if (ATTR_SINGER.equalsIgnoreCase(op)) {
            notifyAttr(c, ATTR_SINGER, d.author);
        } else if (ATTR_SINGER.equalsIgnoreCase(op)) {
            notifyAttr(c, ATTR_SINGER, d.author);
        } else if ("coverImgUrl".equalsIgnoreCase(op)) {
            notifyAttr(c, "coverImgUrl", d.coverImg);
        } else if (ATTR_WEB_URL.equalsIgnoreCase(op)) {
            notifyAttr(c, ATTR_WEB_URL, d.webUrl);
        } else if (ATTR_DURATION.equalsIgnoreCase(op)) {
            JSONObject r2 = new JSONObject();
            r2.put((String) "success", (Object) Integer.valueOf(1));
            r2.put((String) ATTR_DURATION, (Object) Float.valueOf(((float) p.getDuration()) / 1000.0f));
            c.sendBridgeResultWithCallbackKept(r2);
        } else if (ATTR_CURRENT_TIME.equalsIgnoreCase(op)) {
            JSONObject r3 = new JSONObject();
            r3.put((String) "success", (Object) Integer.valueOf(1));
            r3.put((String) ATTR_CURRENT_TIME, (Object) Float.valueOf(((float) p.getCurrentPosition()) / 1000.0f));
            c.sendBridgeResultWithCallbackKept(r3);
        } else if (ATTR_BUFFERED.equalsIgnoreCase(op)) {
            JSONObject r4 = new JSONObject();
            r4.put((String) "success", (Object) Integer.valueOf(1));
            r4.put((String) ATTR_BUFFERED, (Object) Integer.valueOf(p.getBufferedPercent()));
            c.sendBridgeResultWithCallbackKept(r4);
        } else if (ATTR_PAUSED.equalsIgnoreCase(op)) {
            JSONObject r5 = new JSONObject();
            r5.put((String) "success", (Object) Integer.valueOf(1));
            r5.put((String) ATTR_PAUSED, (Object) Boolean.valueOf(p.isPaused()));
            c.sendBridgeResultWithCallbackKept(r5);
        } else {
            notifyInvalidParam(c);
        }
    }

    private void notifyAttr(H5BridgeContext context, String key, String value) {
        JSONObject res = new JSONObject();
        res.put((String) "success", (Object) Integer.valueOf(1));
        res.put(key, (Object) value);
        context.sendBridgeResultWithCallbackKept(res);
    }

    private boolean handleActionPlay(H5Event event, H5BridgeContext context) {
        this.mLogger.d("handleActionPlay:###");
        JSONObject param = event.getParam();
        if (H5ParamParser.getBoolean(param, KEY_PLAY_WITH_NO_PARAM)) {
            this.mLogger.d("Play with no param.");
            GlobalAudioPlayer.getInstance().playAudio();
            return context.sendBridgeResult("success", Integer.valueOf(1));
        }
        String url = H5ParamParser.getString(param, Constants.KEY_AUDIO_URL);
        String name = H5ParamParser.getString(param, Constants.KEY_AUDIO_NAME);
        String author = H5ParamParser.getString(param, Constants.KEY_AUDIO_SINGER_NAME);
        String extraDesc = H5ParamParser.getString(param, Constants.KEY_AUDIO_DESCRIBE);
        String coverImage = H5ParamParser.getString(param, "coverImgUrl");
        String business = H5ParamParser.getString(param, Constants.KEY_AUDIO_BUSINESS_ID);
        String shareSrcLogo = H5ParamParser.getString(param, Constants.KEY_AUDIO_SHARE_BY_APP_LOGO_URL);
        String shareSrcName = H5ParamParser.getString(param, "appName");
        JSONObject bizExtraParams = H5ParamParser.getJsonObj(param, KEY_AUDIO_EXTRA_INFO);
        String bizIdentifier = H5ParamParser.getString(param, KEY_BIZ_IDENTIFIER);
        String epname = H5ParamParser.getString(param, ATTR_EPNAME);
        String webUrl = H5ParamParser.getString(param, ATTR_WEB_URL);
        AudioDetail sd = new AudioDetail();
        if (!TextUtils.isEmpty(bizIdentifier)) {
            sd.bizIdentifier = bizIdentifier;
        }
        sd.epname = epname;
        sd.webUrl = webUrl;
        sd.url = parseAPFilePath(url);
        sd.name = name;
        sd.author = author;
        sd.coverImg = coverImage;
        sd.extraDesc = extraDesc;
        Bundle extraInfo = new Bundle();
        extraInfo.putString(Constants.KEY_AUDIO_BUSINESS_ID, business);
        extraInfo.putString("appName", shareSrcName);
        extraInfo.putString(Constants.KEY_AUDIO_SHARE_BY_APP_LOGO_URL, shareSrcLogo);
        if (bizExtraParams != null) {
            extraInfo.putSerializable(KEY_AUDIO_EXTRA_INFO, bizExtraParams);
        }
        sd.extraInfo = extraInfo;
        this.mLogger.d("AudioDetail=" + sd.toString());
        GlobalAudioPlayer.getInstance().playAudio(sd);
        return context.sendBridgeResult("success", Integer.valueOf(1));
    }

    private boolean handleActionPause(H5BridgeContext context) {
        GlobalAudioPlayer.getInstance().pauseAudio();
        return context.sendBridgeResult("success", Integer.valueOf(1));
    }

    private boolean handleActionSeek(H5Event event, H5BridgeContext context) {
        GlobalAudioPlayer.getInstance().seekTo(H5ParamParser.getInt(event.getParam(), "position", 0) * 1000);
        return context.sendBridgeResult("success", Integer.valueOf(1));
    }

    private boolean handleActionStop(H5BridgeContext context) {
        GlobalAudioPlayer.getInstance().stopAudio();
        return context.sendBridgeResult("success", Integer.valueOf(1));
    }

    private boolean handleActionGetState(H5BridgeContext context) {
        GlobalAudioPlayer gPlayer = GlobalAudioPlayer.getInstance();
        long duration = gPlayer.getDuration();
        long currentPosition = gPlayer.getCurrentPosition();
        int bufferPercent = gPlayer.getBufferedPercent();
        int state = gPlayer.getMediaPlayerState();
        String dataUrl = gPlayer.getDataSource();
        JSONObject result = new JSONObject();
        result.put((String) ATTR_DURATION, (Object) Float.valueOf(((float) duration) / 1000.0f));
        result.put((String) "currentPosition", (Object) Float.valueOf(((float) currentPosition) / 1000.0f));
        result.put((String) "downloadPercent", (Object) Integer.valueOf(bufferPercent));
        PlayerState middle = AudioPlayerStateDetector.wrapState(state);
        int dst = convertState(AudioPlayerStateDetector.wrapState(state));
        result.put((String) "status", (Object) Integer.valueOf(dst));
        this.mLogger.d("AudioStateLink## dst = " + dst + " middle = " + middle + " src = " + state);
        result.put((String) Constants.KEY_AUDIO_URL, (Object) checkAPFilePathMap(dataUrl));
        sendBizExtBack(gPlayer.getLatestSongDetail(), result);
        return context.sendBridgeResult(result);
    }

    private boolean handleMonitorPlayerState(H5Event event, H5BridgeContext context) {
        this.mLogger.d("handleMonitorPlayerState:###");
        try {
            sMotoringPages.put(event.getH5page().getPageData().getPageUrl(), context);
            if (!stateDetector.isActive()) {
                stateDetector.active();
            }
            JSONObject result = new JSONObject();
            result.put((String) "success", (Object) Integer.valueOf(1));
            return context.sendBridgeResultWithCallbackKept(result);
        } catch (Exception e) {
            this.mLogger.d("Failed to get page url,ignore.");
            notifyInvalidParam(context);
            return true;
        }
    }

    private boolean handleCancelMonitorPlayerState(H5Event event, H5BridgeContext context) {
        try {
            String url = event.getH5page().getPageData().getPageUrl();
            JSONObject result = new JSONObject();
            if (sMotoringPages.remove(url) != null) {
                result.put((String) "success", (Object) Integer.valueOf(1));
            } else {
                result.put((String) "success", (Object) Integer.valueOf(0));
                result.put((String) "describe", (Object) "Page not monitoring.");
            }
            if (sMotoringPages == null || sMotoringPages.isEmpty()) {
                stateDetector.disActive();
            }
            return context.sendBridgeResult(result);
        } catch (Exception e) {
            this.mLogger.d("Failed to get page url,ignore.");
            notifyInvalidParam(context);
            return true;
        }
    }

    public static int convertState(PlayerState ps) {
        switch (ps) {
            case PLAYING:
                return 1;
            case PREPARING:
                return 3;
            case PAUSING:
                return 0;
            case ERROR:
                return -1;
            default:
                return 2;
        }
    }
}
