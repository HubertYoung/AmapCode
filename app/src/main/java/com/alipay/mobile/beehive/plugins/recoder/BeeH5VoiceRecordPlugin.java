package com.alipay.mobile.beehive.plugins.recoder;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaAudioService;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APExAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo.AudioOptions;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo.AudioOptions.Builder;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo.AudioSource;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioRecordRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.AudioFormat;
import com.alipay.android.phone.mobilecommon.multimedia.audio.interf.IAudioService;
import com.alipay.mobile.antui.dialog.AURecordFloatTip;
import com.alipay.mobile.beehive.plugins.BaseBeehivePlugin;
import com.alipay.mobile.beehive.plugins.utils.H5PLogger;
import com.alipay.mobile.beehive.plugins.utils.H5ParamParser;
import com.alipay.mobile.beehive.plugins.utils.PathToLocalUtil;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.permission.H5PermissionCallback;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import java.lang.ref.WeakReference;

public class BeeH5VoiceRecordPlugin extends BaseBeehivePlugin {
    private static final String ACTION_CANCEL_RECORD = "cancelAudioRecord";
    private static final String ACTION_GET_AVAILABLE_AUDIO_SRC = "getAvailableAudioSources";
    private static final String ACTION_PAUSE_RECORD = "pauseAudioRecord";
    private static final String ACTION_RESUME_RECORD = "resumeAudioRecord";
    private static final String ACTION_START_RECORD = "startAudioRecord";
    private static final String ACTION_STOP_RECORD = "stopAudioRecord";
    private static final String[] ANDROID_AVAILABLE_AUDIO_SRC = {"auto", AUDIO_SRC_MIC, AUDIO_SRC_CAMCORDER, AUDIO_SRC_VOICE_COMMUNICATION, AUDIO_SRC_VOICE_RECOGNITION};
    private static final String AUDIO_FORMAT_AAC = "aac";
    private static final String AUDIO_FORMAT_MP3 = "mp3";
    private static final String AUDIO_FORMAT_SILK = "silk";
    private static final String AUDIO_SRC_AUTO = "auto";
    private static final String AUDIO_SRC_CAMCORDER = "camcorder";
    private static final String AUDIO_SRC_MIC = "mic";
    private static final String AUDIO_SRC_VOICE_COMMUNICATION = "voice_communication";
    private static final String AUDIO_SRC_VOICE_RECOGNITION = "voice_recognition";
    private static final String EVENT_ON_FRAME_RECORD = "recordFrameRecorded";
    private static final String EVENT_ON_RECORD_ERROR = "recordError";
    private static final String EVENT_ON_RECORD_PAUSE = "recordPause";
    private static final String EVENT_ON_RECORD_START = "recordStart";
    private static final String EVENT_ON_RECORD_STOP = "recordStop";
    private static final String RESULT_DURATION = "duration";
    private static final String RESULT_ERROR = "error";
    private static final String RESULT_ERROR_MSG = "errorMessage";
    private static final String RESULT_IDENTIFIER = "identifier";
    private static final String RESULT_PATH = "apFilePath";
    private static final int STATUS_CANCEL = -1;
    private static final int STATUS_ERROR = -2;
    private static final int STATUS_SUCCESS = 0;
    /* access modifiers changed from: private */
    public AURecordFloatTip mAURecordFloatTip;
    private WeakReference<Activity> mActivity;
    private MultimediaAudioService mAudioService;
    /* access modifiers changed from: private */
    public H5PLogger mLogger = H5PLogger.getLogger((String) "BeeH5VoiceRecordPlugin");
    private IAudioService mRecordService;

    public static class RecordParams {
        @JSONField(name = "audioSource")
        public String audioSource = "auto";
        @JSONField(name = "business")
        public String business = "apm-h5";
        @JSONField(name = "duration")
        public int duration = 60000;
        @JSONField(name = "encodeBitRate")
        public int encodeBitRate = 32000;
        @JSONField(name = "format")
        public String format = BeeH5VoiceRecordPlugin.AUDIO_FORMAT_SILK;
        @JSONField(name = "frameSize")
        public int frameSize = 0;
        @JSONField(name = "maxRecordTime")
        public int maxRecordTime = 60;
        @JSONField(name = "minRecordTime")
        public int minRecordTime = 1;
        @JSONField(name = "numberOfChannels")
        public int numberOfChannels = 1;
        @JSONField(name = "sampleRate")
        public int sampleRate = 16000;
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        this.mAudioService = (MultimediaAudioService) MicroServiceUtil.getMicroService(MultimediaAudioService.class);
    }

    /* access modifiers changed from: protected */
    public String[] registerAction() {
        return new String[]{ACTION_START_RECORD, ACTION_STOP_RECORD, ACTION_CANCEL_RECORD, ACTION_PAUSE_RECORD, ACTION_RESUME_RECORD, ACTION_GET_AVAILABLE_AUDIO_SRC};
    }

    /* access modifiers changed from: protected */
    public boolean onActionCalled(String action, H5Event event, H5BridgeContext bridgeContext, Bundle bundle) {
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        this.mLogger.d("handleEvent action: " + event.getAction() + ", params: " + event.getParam());
        if ((this.mActivity == null || this.mActivity.get() == null) && event.getActivity() != null) {
            this.mActivity = new WeakReference<>(event.getActivity());
        }
        String action = event.getAction();
        if (ACTION_START_RECORD.equals(action)) {
            return checkAndStartRecord(context, event);
        }
        if (ACTION_STOP_RECORD.equals(action)) {
            handleStopRecord(context, event);
            return true;
        } else if (ACTION_CANCEL_RECORD.equals(action)) {
            handleCancelRecord(context, event);
            return true;
        } else if (ACTION_PAUSE_RECORD.equalsIgnoreCase(action)) {
            onPauseRecord(event, context);
            return true;
        } else if (ACTION_RESUME_RECORD.equalsIgnoreCase(action)) {
            onResumeRecord(event, context);
            return true;
        } else if (ACTION_GET_AVAILABLE_AUDIO_SRC.equalsIgnoreCase(action)) {
            JSONObject ret = new JSONObject();
            ret.put((String) "success", (Object) Boolean.valueOf(true));
            ret.put((String) "audioSources", (Object) ANDROID_AVAILABLE_AUDIO_SRC);
            context.sendBridgeResultWithCallbackKept(ret);
            return true;
        } else {
            this.mLogger.e("invalid recordParams or unknown error, action: " + event.getAction());
            return context.sendError(event, Error.INVALID_PARAM);
        }
    }

    private void onResumeRecord(H5Event event, H5BridgeContext context) {
        if (this.mRecordService != null) {
            this.mRecordService.resumeRecord();
            context.sendBridgeResultWithCallbackKept("error", Integer.valueOf(0));
            return;
        }
        context.sendError(event, Error.UNKNOWN_ERROR);
    }

    private void onPauseRecord(H5Event event, H5BridgeContext context) {
        if (this.mRecordService != null) {
            this.mRecordService.pauseRecord();
            context.sendBridgeResultWithCallbackKept("error", Integer.valueOf(0));
            return;
        }
        context.sendError(event, Error.UNKNOWN_ERROR);
    }

    private boolean checkAndStartRecord(final H5BridgeContext context, final H5Event event) {
        this.mLogger.d("checkAndStartRecord:###");
        H5Utils.requestPermissions(event.getActivity(), new String[]{"android.permission.RECORD_AUDIO"}, new H5PermissionCallback() {
            public final void onRequestPermissionsResult(boolean granted) {
                if (granted) {
                    BeeH5VoiceRecordPlugin.this.handleStartRecord(context, event);
                    return;
                }
                BeeH5VoiceRecordPlugin.this.mLogger.d("checkAndStartRecord:### NO PERMISSION!");
                context.sendNotGrantPermission();
                BeeH5VoiceRecordPlugin.this.sendNoPermissionErrorEvent(context);
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public void sendNoPermissionErrorEvent(H5BridgeContext context) {
        JSONObject obj = new JSONObject();
        obj.put((String) "error", (Object) Integer.valueOf(10));
        obj.put((String) "errorMessage", (Object) "Not grant permission for audio record.");
        sendErrorEvent(context, obj);
    }

    private boolean handleCancelRecord(H5BridgeContext context, H5Event event) {
        if (this.mRecordService == null) {
            return context.sendError(event, Error.UNKNOWN_ERROR);
        }
        this.mRecordService.cancelRecord();
        return context.sendBridgeResultWithCallbackKept("error", Integer.valueOf(0));
    }

    private boolean handleStopRecord(H5BridgeContext context, H5Event event) {
        if (this.mRecordService == null) {
            return context.sendError(event, Error.UNKNOWN_ERROR);
        }
        this.mRecordService.stopRecord();
        return context.sendBridgeResultWithCallbackKept("error", Integer.valueOf(0));
    }

    /* access modifiers changed from: private */
    public void handleStartRecord(H5BridgeContext context, H5Event event) {
        this.mLogger.d("handleStartRecord:###");
        RecordParams p = (RecordParams) H5ParamParser.parseParams(event, RecordParams.class);
        if (p == null) {
            this.mLogger.w("parseParams return NULL!");
        } else if (!isAudioSrcValid(p.audioSource)) {
            sendInvalidParamEvent(context, "Audio source invalid!");
            this.mLogger.d("Audio src invalid, src = " + p.audioSource);
            return;
        } else if (!isAudioFormatValid(p.format)) {
            sendInvalidParamEvent(context, "Audio format invalid!");
            this.mLogger.d("Audio format invalid ,format = " + p.format);
            return;
        } else {
            AudioFormat audioFormat = parseAudioFormat(p);
            this.mRecordService = this.mAudioService.createAudioService(audioFormat, null);
            if (this.mRecordService != null) {
                APAudioInfo info = new APAudioInfo();
                int maxDuration = parseTimeLimit(event, p);
                info.setRecordMaxTime(maxDuration);
                info.setRecordMinTime(p.minRecordTime * 1000);
                info.setAudioOptions(buildOptions(p, audioFormat, maxDuration));
                try {
                    this.mRecordService.startRecord(info, (APAudioRecordCallback) buildRecordListener(context, event), p.business);
                } catch (Exception e) {
                    this.mLogger.d("handleStartRecord exp=" + e.toString());
                    context.sendError(event, Error.UNKNOWN_ERROR);
                }
            } else {
                this.mLogger.w("Failed to create RecordService.");
            }
        }
        context.sendError(event, Error.UNKNOWN_ERROR);
    }

    private void sendInvalidParamEvent(H5BridgeContext context, String invalidParamDesc) {
        JSONObject o = new JSONObject();
        o.put((String) "error", (Object) Integer.valueOf(Error.INVALID_PARAM.ordinal()));
        o.put((String) "errorMessage", (Object) invalidParamDesc);
        sendErrorEvent(context, o);
    }

    @NonNull
    private APExAudioRecordCallback buildRecordListener(final H5BridgeContext context, final H5Event event) {
        return new APExAudioRecordCallback() {
            public final void onRecordPause(APAudioInfo apAudioInfo) {
                context.sendToWeb(BeeH5VoiceRecordPlugin.EVENT_ON_RECORD_PAUSE, null, null);
            }

            public final void onFrameRecorded(byte[] frame, boolean isLastFrame) {
                if (frame == null || frame.length <= 0) {
                    BeeH5VoiceRecordPlugin.this.mLogger.d("onFrameRecorded called but frame data is empty.");
                    return;
                }
                JSONObject wrapper = new JSONObject();
                JSONObject ret = new JSONObject();
                ret.put((String) "frameBuffer", (Object) new String(Base64.encode(frame, 0)));
                ret.put((String) "isLastFrame", (Object) Boolean.valueOf(isLastFrame));
                wrapper.put((String) "data", (Object) ret);
                context.sendToWeb(BeeH5VoiceRecordPlugin.EVENT_ON_FRAME_RECORD, wrapper, null);
            }

            public final void onRecordStop(APAudioInfo apAudioInfo) {
            }

            public final void onRecordStart(APAudioInfo apAudioInfo) {
                BeeH5VoiceRecordPlugin.this.toggleTips(event.getActivity(), true);
                context.sendToWeb(BeeH5VoiceRecordPlugin.EVENT_ON_RECORD_START, null, null);
            }

            public final void onRecordAmplitudeChange(APAudioInfo info, int amplitude) {
            }

            public final void onRecordProgressUpdate(APAudioInfo apAudioInfo, int recordDuration) {
            }

            public final void onRecordCancel(APAudioInfo apAudioInfo) {
                BeeH5VoiceRecordPlugin.this.toggleTips(event.getActivity(), false);
                BeeH5VoiceRecordPlugin.this.notifyRecordCancel(context, apAudioInfo);
            }

            public final void onRecordFinished(APAudioInfo apAudioInfo) {
                BeeH5VoiceRecordPlugin.this.toggleTips(event.getActivity(), false);
                BeeH5VoiceRecordPlugin.this.notifyRecordFinish(context, apAudioInfo);
            }

            public final void onRecordError(APAudioRecordRsp apAudioRecordRsp) {
                BeeH5VoiceRecordPlugin.this.toggleTips(event.getActivity(), false);
                BeeH5VoiceRecordPlugin.this.notifyRecordError(context, apAudioRecordRsp);
            }
        };
    }

    private AudioOptions buildOptions(RecordParams p, AudioFormat audioFormat, int maxDuration) {
        return new Builder().audioSource(parseAudioSrc(p)).channels(p.numberOfChannels).encodeBitRate(p.encodeBitRate).format(audioFormat).frameSize(p.frameSize * 1024).sampleRate(p.sampleRate).duration(maxDuration).build();
    }

    private int parseTimeLimit(H5Event event, RecordParams p) {
        int timeLimit = p.maxRecordTime * 1000;
        JSONObject obj = event.getParam();
        if (obj == null || !obj.containsKey(RESULT_DURATION)) {
            return timeLimit;
        }
        return p.duration;
    }

    @NonNull
    private AudioSource parseAudioSrc(RecordParams p) {
        AudioSource audioSrc = AudioSource.SOURCE_AUTO;
        if (AUDIO_SRC_MIC.equalsIgnoreCase(p.audioSource)) {
            return AudioSource.SOURCE_MIC;
        }
        if (AUDIO_SRC_CAMCORDER.equalsIgnoreCase(p.audioSource)) {
            return AudioSource.SOURCE_CAMCORDER;
        }
        if (AUDIO_SRC_VOICE_COMMUNICATION.equalsIgnoreCase(p.audioSource)) {
            return AudioSource.SOURCE_VOICE_COMMUNICATION;
        }
        if (AUDIO_SRC_VOICE_RECOGNITION.equalsIgnoreCase(p.audioSource)) {
            return AudioSource.SOURCE_VOICE_RECOGNITION;
        }
        return audioSrc;
    }

    private boolean isAudioSrcValid(String audioSrc) {
        return "auto".equalsIgnoreCase(audioSrc) || AUDIO_SRC_CAMCORDER.equalsIgnoreCase(audioSrc) || AUDIO_SRC_MIC.equalsIgnoreCase(audioSrc) || AUDIO_SRC_VOICE_COMMUNICATION.equalsIgnoreCase(audioSrc) || AUDIO_SRC_VOICE_RECOGNITION.equalsIgnoreCase(audioSrc);
    }

    private boolean isAudioFormatValid(String format) {
        return AUDIO_FORMAT_SILK.equalsIgnoreCase(format) || AUDIO_FORMAT_MP3.equalsIgnoreCase(format) || AUDIO_FORMAT_AAC.equalsIgnoreCase(format);
    }

    @NonNull
    private AudioFormat parseAudioFormat(RecordParams recordParams) {
        AudioFormat audioFormat = AudioFormat.SILK;
        if (AUDIO_FORMAT_AAC.equalsIgnoreCase(recordParams.format)) {
            return AudioFormat.AAC;
        }
        if (AUDIO_FORMAT_MP3.equalsIgnoreCase(recordParams.format)) {
            return AudioFormat.MP3;
        }
        return audioFormat;
    }

    /* access modifiers changed from: private */
    public void notifyRecordCancel(H5BridgeContext context, APAudioInfo audioInfo) {
        this.mLogger.d("notifyRecordCancel " + audioInfo);
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(-1));
        context.sendBridgeResultWithCallbackKept(result);
    }

    /* access modifiers changed from: private */
    public void notifyRecordFinish(H5BridgeContext context, APAudioInfo audioInfo) {
        this.mLogger.d("notifyRecordFinish " + audioInfo);
        JSONObject result = new JSONObject();
        if (audioInfo != null) {
            result.put((String) "error", (Object) Integer.valueOf(0));
            result.put((String) "identifier", (Object) audioInfo.getLocalId());
            String apFilePath = H5ResourceHandlerUtil.localIdToUrl(PathToLocalUtil.encodeToLocalId(audioInfo.getSavePath()), "audio");
            result.put((String) "apFilePath", (Object) apFilePath);
            result.put((String) "tempFilePath", (Object) apFilePath);
            result.put((String) RESULT_DURATION, (Object) Float.valueOf(((float) audioInfo.getDuration()) / 1000.0f));
        } else {
            result.put((String) "error", (Object) Integer.valueOf(-2));
            result.put((String) "errorMessage", (Object) "record response is null");
        }
        this.mLogger.d("notifyRecordFinish :result = " + result);
        context.sendBridgeResultWithCallbackKept(result);
        JSONObject wrapper = new JSONObject();
        wrapper.put((String) "data", (Object) result);
        context.sendToWeb(EVENT_ON_RECORD_STOP, wrapper, null);
    }

    public void onRelease() {
        releaseTips();
        super.onRelease();
    }

    private void releaseTips() {
        Activity activity = (this.mActivity == null || this.mActivity.get() == null) ? null : (Activity) this.mActivity.get();
        if (activity != null && this.mAURecordFloatTip != null) {
            activity.runOnUiThread(new Runnable() {
                public final void run() {
                    BeeH5VoiceRecordPlugin.this.mAURecordFloatTip.dismiss();
                    BeeH5VoiceRecordPlugin.this.mAURecordFloatTip = null;
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void notifyRecordError(H5BridgeContext context, APAudioRecordRsp audioRecordRsp) {
        this.mLogger.d("notifyRecordError " + audioRecordRsp);
        JSONObject result = new JSONObject();
        if (audioRecordRsp != null) {
            result.put((String) "error", (Object) Integer.valueOf(-audioRecordRsp.getRetCode()));
            result.put((String) "errorMessage", (Object) audioRecordRsp.getMsg());
        } else {
            result.put((String) "error", (Object) Integer.valueOf(-2));
            result.put((String) "errorMessage", (Object) "record error response is null");
        }
        context.sendBridgeResultWithCallbackKept(result);
        sendErrorEvent(context, result);
    }

    private void sendErrorEvent(H5BridgeContext context, JSONObject result) {
        JSONObject wrapper = new JSONObject();
        wrapper.put((String) "data", (Object) result);
        context.sendToWeb(EVENT_ON_RECORD_ERROR, wrapper, null);
    }

    /* access modifiers changed from: private */
    public void toggleTips(final Activity activity, final boolean show) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public final void run() {
                    if (show) {
                        if (BeeH5VoiceRecordPlugin.this.mAURecordFloatTip == null) {
                            BeeH5VoiceRecordPlugin.this.mAURecordFloatTip = new AURecordFloatTip(activity, activity.getString(R.string.h5_record));
                        }
                        BeeH5VoiceRecordPlugin.this.mAURecordFloatTip.show();
                    } else if (BeeH5VoiceRecordPlugin.this.mAURecordFloatTip != null) {
                        BeeH5VoiceRecordPlugin.this.mAURecordFloatTip.dismiss();
                    }
                }
            });
        }
    }
}
