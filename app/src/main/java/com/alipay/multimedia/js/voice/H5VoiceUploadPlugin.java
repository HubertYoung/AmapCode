package com.alipay.multimedia.js.voice;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaAudioService;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.file.H5FileDownloadPlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;

public class H5VoiceUploadPlugin extends MMH5SimplePlugin {
    public H5VoiceUploadPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("uploadAudio");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        Logger.debug("H5VoiceUpload", "handleEvent action: " + event.getAction() + ", param: " + event.getParam());
        if ("uploadAudio".equals(event.getAction())) {
            return a(event, context);
        }
        return context.sendError(event, Error.INVALID_PARAM);
    }

    private boolean a(H5Event event, final H5BridgeContext context) {
        String identifier = getStringParam(event, H5FileDownloadPlugin.RESULT_IDENTIFIER);
        if (!TextUtils.isEmpty(identifier)) {
            MultimediaAudioService service = (MultimediaAudioService) Utils.getService(MultimediaAudioService.class);
            if (service != null) {
                service.uploadRecord(APAudioInfo.fromLocalId(decodeToPath(identifier)), new APAudioUploadCallback() {
                    {
                        if (Boolean.FALSE.booleanValue()) {
                            Log.v("hackbyte ", ClassVerifier.class.toString());
                        }
                    }

                    public void onUploadStart(APAudioInfo apAudioInfo) {
                    }

                    public void onUploadFinished(APAudioUploadRsp apAudioUploadRsp) {
                        Logger.debug("H5VoiceUpload", "onUploadFinished rsp: " + apAudioUploadRsp);
                        JSONObject result = new JSONObject();
                        result.put((String) "error", (Object) Integer.valueOf(0));
                        result.put((String) H5FileDownloadPlugin.RESULT_IDENTIFIER, (Object) apAudioUploadRsp.getAudioInfo().getCloudId());
                        context.sendBridgeResult(result);
                    }

                    public void onUploadError(APAudioUploadRsp apAudioUploadRsp) {
                        JSONObject result = new JSONObject();
                        result.put((String) "error", (Object) Integer.valueOf(-apAudioUploadRsp.getRetCode()));
                        result.put((String) "errorMessage", (Object) apAudioUploadRsp.getMsg());
                        context.sendBridgeResult(result);
                    }
                }, getStringParam(event, Constants.KEY_AUDIO_BUSINESS_ID, "apm-h5"));
                return true;
            }
        }
        return context.sendError(event, Error.INVALID_PARAM);
    }
}
