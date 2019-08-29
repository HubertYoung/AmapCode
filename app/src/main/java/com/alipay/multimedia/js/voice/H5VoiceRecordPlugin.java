package com.alipay.multimedia.js.voice;

import android.app.Activity;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaAudioService;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioRecordCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioRecordRsp;
import com.alipay.mobile.antui.dialog.AURecordFloatTip;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.permission.H5PermissionCallback;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.file.H5FileDownloadPlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;
import java.lang.ref.WeakReference;

public class H5VoiceRecordPlugin extends MMH5SimplePlugin {
    private WeakReference a;
    /* access modifiers changed from: private */
    public AURecordFloatTip b;

    public class RecordParams {
        @JSONField(name = "business")
        public String business = "apm-h5";
        @JSONField(name = "maxRecordTime")
        public int maxRecordTime = 60;
        @JSONField(name = "minRecordTime")
        public int minRecordTime = 1;

        public RecordParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public H5VoiceRecordPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("startAudioRecord");
        filter.addAction("stopAudioRecord");
        filter.addAction("cancelAudioRecord");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        Logger.debug("H5VoiceRecord", "handleEvent action: " + event.getAction() + ", params: " + event.getParam());
        if ((this.a == null || this.a.get() == null) && event.getActivity() != null) {
            this.a = new WeakReference(event.getActivity());
        }
        if ("startAudioRecord".equals(event.getAction())) {
            return a(context, event);
        }
        if ("stopAudioRecord".equals(event.getAction())) {
            return c(context, event);
        }
        if ("cancelAudioRecord".equals(event.getAction())) {
            return b(context, event);
        }
        Logger.error((String) "H5VoiceRecord", "invalid recordParams or unknown error, action: " + event.getAction());
        return context.sendError(event, Error.INVALID_PARAM);
    }

    private boolean a(final H5BridgeContext context, final H5Event event) {
        H5Utils.requestPermissions(event.getActivity(), new String[]{"android.permission.RECORD_AUDIO"}, new H5PermissionCallback() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void onRequestPermissionsResult(boolean granted) {
                if (granted) {
                    H5VoiceRecordPlugin.this.d(context, event);
                } else {
                    context.sendNotGrantPermission();
                }
            }
        });
        return true;
    }

    private static boolean b(H5BridgeContext context, H5Event event) {
        MultimediaAudioService service = (MultimediaAudioService) Utils.getService(MultimediaAudioService.class);
        if (service == null) {
            return context.sendError(event, Error.UNKNOWN_ERROR);
        }
        service.cancelRecord();
        return context.sendBridgeResult("error", Integer.valueOf(0));
    }

    private static boolean c(H5BridgeContext context, H5Event event) {
        MultimediaAudioService service = (MultimediaAudioService) Utils.getService(MultimediaAudioService.class);
        if (service == null) {
            return context.sendError(event, Error.UNKNOWN_ERROR);
        }
        service.stopRecord();
        return context.sendBridgeResult("error", Integer.valueOf(0));
    }

    /* access modifiers changed from: private */
    public boolean d(final H5BridgeContext context, final H5Event event) {
        RecordParams recordParams = (RecordParams) parseParams(event, RecordParams.class);
        if (recordParams != null) {
            MultimediaAudioService service = (MultimediaAudioService) Utils.getService(MultimediaAudioService.class);
            if (service != null) {
                APAudioInfo info = new APAudioInfo();
                info.setRecordMaxTime(recordParams.maxRecordTime * 1000);
                info.setRecordMinTime(recordParams.minRecordTime * 1000);
                try {
                    service.startRecord(info, (APAudioRecordCallback) new APAudioRecordCallback() {
                        {
                            if (Boolean.FALSE.booleanValue()) {
                                Log.v("hackbyte ", ClassVerifier.class.toString());
                            }
                        }

                        public void onRecordStart(APAudioInfo apAudioInfo) {
                            H5VoiceRecordPlugin.this.a(event.getActivity(), true);
                        }

                        public void onRecordAmplitudeChange(APAudioInfo apAudioInfo, int i) {
                        }

                        public void onRecordProgressUpdate(APAudioInfo apAudioInfo, int i) {
                        }

                        public void onRecordCancel(APAudioInfo apAudioInfo) {
                            H5VoiceRecordPlugin.this.a(event.getActivity(), false);
                            H5VoiceRecordPlugin.b(context, apAudioInfo);
                        }

                        public void onRecordFinished(APAudioInfo apAudioInfo) {
                            H5VoiceRecordPlugin.this.a(event.getActivity(), false);
                            H5VoiceRecordPlugin.this.c(context, apAudioInfo);
                        }

                        public void onRecordError(APAudioRecordRsp apAudioRecordRsp) {
                            H5VoiceRecordPlugin.this.a(event.getActivity(), false);
                            H5VoiceRecordPlugin.b(context, apAudioRecordRsp);
                        }
                    }, recordParams.business);
                    return true;
                } catch (Exception e) {
                    Logger.debug("H5VoiceRecord", "handleStartRecord exp=" + e.toString());
                    return context.sendError(event, Error.UNKNOWN_ERROR);
                }
            }
        }
        return context.sendError(event, Error.UNKNOWN_ERROR);
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, APAudioInfo audioInfo) {
        Logger.debug("H5VoiceRecord", "notifyRecordCancel " + audioInfo);
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(-1));
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public void c(H5BridgeContext context, APAudioInfo audioInfo) {
        Logger.debug("H5VoiceRecord", "notifyRecordFinish " + audioInfo);
        JSONObject result = new JSONObject();
        if (audioInfo != null) {
            result.put((String) "error", (Object) Integer.valueOf(0));
            result.put((String) H5FileDownloadPlugin.RESULT_IDENTIFIER, (Object) audioInfo.getLocalId());
            result.put((String) Constant.AL_MEDIA_FILE, (Object) H5ResourceHandlerUtil.localIdToUrl(encodeToLocalId(audioInfo.getSavePath()), "audio"));
            result.put((String) "duration", (Object) Float.valueOf(((float) audioInfo.getDuration()) / 1000.0f));
        } else {
            result.put((String) "error", (Object) Integer.valueOf(-2));
            result.put((String) "errorMessage", (Object) "record response is null");
        }
        context.sendBridgeResult(result);
    }

    public void onRelease() {
        a();
        super.onRelease();
    }

    private void a() {
        Activity activity = (this.a == null || this.a.get() == null) ? null : (Activity) this.a.get();
        if (activity != null && this.b != null) {
            activity.runOnUiThread(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    H5VoiceRecordPlugin.this.b.dismiss();
                    H5VoiceRecordPlugin.this.b = null;
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, APAudioRecordRsp audioRecordRsp) {
        Logger.debug("H5VoiceRecord", "notifyRecordError " + audioRecordRsp);
        JSONObject result = new JSONObject();
        if (audioRecordRsp != null) {
            result.put((String) "error", (Object) Integer.valueOf(-audioRecordRsp.getRetCode()));
            result.put((String) "errorMessage", (Object) audioRecordRsp.getMsg());
        } else {
            result.put((String) "error", (Object) Integer.valueOf(-2));
            result.put((String) "errorMessage", (Object) "record error response is null");
        }
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public void a(final Activity activity, final boolean show) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    if (show) {
                        if (H5VoiceRecordPlugin.this.b == null) {
                            H5VoiceRecordPlugin.this.b = new AURecordFloatTip(activity, activity.getString(R.string.h5_record));
                        }
                        H5VoiceRecordPlugin.this.b.show();
                    } else if (H5VoiceRecordPlugin.this.b != null) {
                        H5VoiceRecordPlugin.this.b.dismiss();
                    }
                }
            });
        }
    }
}
