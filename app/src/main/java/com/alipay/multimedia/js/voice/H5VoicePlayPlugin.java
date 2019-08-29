package com.alipay.multimedia.js.voice;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaAudioService;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioDownloadPlayCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioPlayRsp;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;

public class H5VoicePlayPlugin extends MMH5SimplePlugin {

    public class PlayParams {
        @JSONField(name = "apFilePath")
        public String apFilePath;
        @JSONField(name = "business")
        public String business = "apm-h5";
        @JSONField(name = "identifier")
        public String identifier;

        public PlayParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public String toString() {
            return "PlayParams{identifier='" + this.identifier + '\'' + ", business='" + this.business + '\'' + '}';
        }
    }

    public H5VoicePlayPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("startPlayAudio");
        filter.addAction("pauseAudioPlay");
        filter.addAction("resumeAudioPlay");
        filter.addAction("stopAudioPlay");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        Logger.debug("H5VoicePlay", "handleEvent action: " + event.getAction() + ", playParams: " + event.getParam());
        if ("startPlayAudio".equals(event.getAction()) || "resumeAudioPlay".equals(event.getAction())) {
            return a(event, context);
        }
        if ("pauseAudioPlay".equals(event.getAction())) {
            return b(event, context);
        }
        if ("stopAudioPlay".equals(event.getAction())) {
            return c(event, context);
        }
        return context.sendError(event, Error.INVALID_PARAM);
    }

    private boolean a(H5Event event, final H5BridgeContext context) {
        PlayParams params = (PlayParams) parseParams(event, PlayParams.class);
        if (params == null) {
            return context.sendError(event, Error.INVALID_PARAM);
        }
        MultimediaAudioService service = (MultimediaAudioService) Utils.getService(MultimediaAudioService.class);
        if (service != null) {
            if (service.getPlayingAudioInfo() != null) {
                service.resumePlay();
            } else {
                String id = H5ResourceHandlerUtil.apUrlToFilePath(TextUtils.isEmpty(params.identifier) ? params.apFilePath : params.identifier);
                Logger.debug("H5VoicePlay", "playVoice id: " + id);
                service.startPlay(new APAudioInfo(id, id, id), new APAudioDownloadPlayCallback() {
                    {
                        if (Boolean.FALSE.booleanValue()) {
                            Log.v("hackbyte ", ClassVerifier.class.toString());
                        }
                    }

                    public void onDownloadStart(APAudioInfo apAudioInfo) {
                    }

                    public void onDownloadFinished(APAudioInfo apAudioInfo) {
                    }

                    public void onDownloadError(APAudioInfo apAudioInfo, APAudioDownloadRsp apAudioDownloadRsp) {
                    }

                    public void onPlayStart(APAudioInfo apAudioInfo) {
                        Logger.debug("H5VoicePlay", "notify startPlay success, info: " + apAudioInfo);
                        context.sendBridgeResult("error", Integer.valueOf(0));
                    }

                    public void onPlayCancel(APAudioInfo apAudioInfo) {
                    }

                    public void onPlayCompletion(APAudioInfo apAudioInfo) {
                    }

                    public void onPlayError(APAudioPlayRsp apAudioPlayRsp) {
                        Logger.debug("H5VoicePlay", "notify startPlay error, rsp: " + apAudioPlayRsp);
                        JSONObject result = new JSONObject();
                        result.put((String) "error", (Object) Integer.valueOf(-apAudioPlayRsp.getRetCode()));
                        result.put((String) "errorMessage", (Object) apAudioPlayRsp.getMsg());
                        context.sendBridgeResult(result);
                    }
                }, params.business);
            }
            return true;
        }
        Logger.debug("H5VoicePlay", "startPlay error, params: " + params);
        return context.sendError(event, Error.UNKNOWN_ERROR);
    }

    private static boolean b(H5Event event, H5BridgeContext context) {
        Logger.debug("H5VoicePlay", "notify pauseVoice");
        MultimediaAudioService service = (MultimediaAudioService) Utils.getService(MultimediaAudioService.class);
        if (service == null) {
            return context.sendError(event, Error.UNKNOWN_ERROR);
        }
        service.pausePlay();
        return context.sendBridgeResult("error", Integer.valueOf(0));
    }

    private static boolean c(H5Event event, H5BridgeContext context) {
        Logger.debug("H5VoicePlay", "notify stopVoice");
        MultimediaAudioService service = (MultimediaAudioService) Utils.getService(MultimediaAudioService.class);
        if (service == null) {
            return context.sendError(event, Error.UNKNOWN_ERROR);
        }
        service.stopPlay();
        return context.sendBridgeResult("error", Integer.valueOf(0));
    }
}
