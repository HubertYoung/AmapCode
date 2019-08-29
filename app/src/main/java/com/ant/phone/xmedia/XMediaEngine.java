package com.ant.phone.xmedia;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.alipaylogger.Log;
import com.alipay.android.phone.mobilecommon.dynamicrelease.DynamicReleaseApi;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoEffect;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.streammedia.cvengine.tracking.TargetRect;
import com.ant.phone.xmedia.algorithm.FrameXNNAlgorithm;
import com.ant.phone.xmedia.algorithm.ImageXNNAlgorithm;
import com.ant.phone.xmedia.algorithm.TrackAlgorithm;
import com.ant.phone.xmedia.api.utils.NativeSupportHelper;
import com.ant.phone.xmedia.benchmark.AlgorithmBenchmark;
import com.ant.phone.xmedia.config.ConfigManager;
import com.ant.phone.xmedia.hybrid.H5XMediaPlugin.Filter;
import com.ant.phone.xmedia.params.ErrorInfo;
import com.ant.phone.xmedia.params.TrackItem;
import com.ant.phone.xmedia.params.TrackerItem;
import com.ant.phone.xmedia.params.XMediaResponse;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XMediaEngine {
    private static final String TAG = "XMediaEngine";
    private static XMediaEngine mInstance = null;
    private String mBizId;
    private String mModelId;
    private String mModelPath;
    private TrackAlgorithm mTrackAlgorithm;
    private FrameXNNAlgorithm mXNNFrameClassify;
    private FrameXNNAlgorithm mXNNFrameDetect;
    private ImageXNNAlgorithm mXNNImageClassify;
    private ImageXNNAlgorithm mXNNImageDetect;

    public interface XMediaCallback {
        boolean onResponse(XMediaResponse xMediaResponse);

        boolean onTrack(XMediaResponse xMediaResponse);
    }

    public static XMediaEngine getInstance() {
        if (mInstance == null) {
            synchronized (XMediaEngine.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new XMediaEngine();
                    }
                }
            }
        }
        return mInstance;
    }

    public XMediaResponse init(String biz, String secretKey, HashMap<String, Object> option) {
        Log.i(TAG, "init");
        this.mBizId = biz;
        Object obj = option.get("model_path");
        if (obj instanceof String) {
            this.mModelPath = (String) obj;
        }
        Object obj1 = option.get("model_id");
        if (obj1 instanceof String) {
            this.mModelId = (String) obj1;
        } else {
            this.mModelId = "";
        }
        XMediaResponse response = new XMediaResponse();
        response.mMode = -1;
        response.mResult = new ArrayList();
        if (!NativeSupportHelper.a() || VERSION.SDK_INT < 18) {
            response.mErrInfo = new ErrorInfo(10004, "xnn not supported on the device.");
        } else {
            ConfigManager.a().a(false);
            if (!ConfigManager.a().a((String) "XMEDIA_NEON_INCOMPATIBLE").a()) {
                response.mErrInfo = new ErrorInfo(10004, "xnn not supported on the device..");
            } else if (TextUtils.isEmpty(this.mModelPath)) {
                response.mErrInfo = new ErrorInfo(10001, "model path is not specified.");
            } else if (!new File(this.mModelPath).exists()) {
                response.mErrInfo = new ErrorInfo(10001, "model file not exist.");
            } else {
                response.mErrInfo = new ErrorInfo(0, "no error");
            }
        }
        return response;
    }

    public void startRunning(int mode, XMediaCallback callback, HashMap<String, Object> option) {
        Object obj;
        Log.i(TAG, "startRunning, mode:" + mode);
        setInitUC("KEY_INIT");
        setInitUC("KEY_FRAME_CLS");
        setInitUC("KEY_FRAME_DET");
        setInitUC("KEY_FRAME_DET_TRACK");
        Object filter = null;
        Object roi = null;
        if (option != null) {
            if (option.get(APVideoEffect.TYPE_FILTER) instanceof Filter) {
                filter = option.get(APVideoEffect.TYPE_FILTER);
            }
            if (option.get("roi") instanceof float[]) {
                roi = option.get("roi");
                obj = filter;
            } else {
                obj = filter;
            }
        } else {
            obj = null;
        }
        if ((mode & 2) != 0) {
            if (this.mXNNFrameClassify == null) {
                this.mXNNFrameClassify = new FrameXNNAlgorithm();
                this.mXNNFrameClassify.a(this.mModelPath, 2, (float[]) roi, (Filter) obj, callback);
            }
            this.mXNNFrameClassify.a();
        } else if ((mode & 1) != 0) {
            if ((mode & 8) == 0) {
                if (this.mXNNFrameDetect == null) {
                    this.mXNNFrameDetect = new FrameXNNAlgorithm();
                    this.mXNNFrameDetect.a(this.mModelPath, 1, (float[]) roi, (Filter) obj, callback);
                }
            } else if (!trackAvailable()) {
                XMediaResponse response = new XMediaResponse();
                response.mMode = mode;
                response.mResult = new ArrayList();
                response.mErrInfo = new ErrorInfo(10002, "mode not supported now.");
                callback.onResponse(response);
                return;
            } else if (this.mXNNFrameDetect == null) {
                this.mXNNFrameDetect = new FrameXNNAlgorithm();
                this.mXNNFrameDetect.a(this.mModelPath, 9, (float[]) roi, (Filter) obj, callback);
            }
            this.mXNNFrameDetect.a();
        } else if ((mode & 8) != 0) {
            Log.i(TAG, "track");
            if (!trackAvailable()) {
                XMediaResponse response2 = new XMediaResponse();
                response2.mMode = mode;
                response2.mResult = new ArrayList();
                response2.mErrInfo = new ErrorInfo(10002, "mode not supported now.");
                callback.onResponse(response2);
            }
        } else if ((mode & 4) != 0) {
            Log.i(TAG, "predict");
        } else {
            XMediaResponse response3 = new XMediaResponse();
            response3.mMode = mode;
            response3.mResult = new ArrayList();
            response3.mErrInfo = new ErrorInfo(10002, "mode not supported now.");
            callback.onResponse(response3);
        }
    }

    public void stopRunning() {
        Log.i(TAG, "stopRunning");
        if (this.mXNNFrameClassify != null) {
            this.mXNNFrameClassify.b();
        }
        this.mXNNFrameClassify = null;
        if (this.mXNNFrameDetect != null) {
            this.mXNNFrameDetect.b();
        }
        this.mXNNFrameDetect = null;
        if (this.mTrackAlgorithm != null) {
            this.mTrackAlgorithm.a();
        }
        this.mTrackAlgorithm = null;
    }

    public List<XMediaResponse> classify(Bitmap image, HashMap<String, Object> option) {
        XMediaResponse response;
        Log.i(TAG, "classify, model path:" + this.mModelPath);
        setInitUC("KEY_INIT");
        setInitUC("KEY_IMAGE_CLS");
        ArrayList list = new ArrayList();
        if (this.mXNNImageClassify == null) {
            this.mXNNImageClassify = new ImageXNNAlgorithm();
            long instance = this.mXNNImageClassify.a(2, this.mModelPath);
            AlgorithmBenchmark.a(instance == 0 ? 1 : 0);
            if (instance == 0) {
                XMediaResponse response2 = new XMediaResponse();
                response2.mResult = new ArrayList();
                response2.mMode = 2;
                response2.mErrInfo = new ErrorInfo(10003, "image xnn algorithm init failed.");
                list.add(response2);
                return list;
            }
        }
        ArrayList roi = null;
        if (option != null) {
            Object obj = option.get("roi");
            if (obj instanceof ArrayList) {
                roi = (ArrayList) obj;
            }
        }
        AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putString("FILE_SIZE", image.getWidth() + "*" + image.getHeight());
        int i = 0;
        do {
            if (roi == null || roi.size() <= 0) {
                response = this.mXNNImageClassify.a(image, (float[]) null);
            } else {
                Object[] array = (Object[]) roi.get(i);
                float[] floats = new float[4];
                for (int j = 0; j < array.length; j++) {
                    if (array[j] instanceof Double) {
                        floats[j] = ((Double) array[j]).floatValue();
                    } else if (array[j] instanceof Integer) {
                        floats[j] = ((Integer) array[j]).floatValue();
                    }
                }
                response = this.mXNNImageClassify.a(image, floats);
            }
            list.add(response);
            Log.i(TAG, "classify[" + i + "] done, list:" + response.toString());
            AlgorithmBenchmark.b(response.mErrInfo.mCode == 0 ? 0 : 1);
            i++;
            if (roi == null) {
                break;
            }
        } while (i < roi.size());
        if (this.mXNNImageClassify != null) {
            this.mXNNImageClassify.a();
        }
        this.mXNNImageClassify = null;
        return list;
    }

    public List<XMediaResponse> detect(Bitmap image, HashMap<String, Object> option) {
        XMediaResponse response;
        Log.i(TAG, "detect, model path:" + this.mModelPath);
        setInitUC("KEY_INIT");
        setInitUC("KEY_IMAGE_DET");
        ArrayList list = new ArrayList();
        if (this.mXNNImageDetect == null) {
            this.mXNNImageDetect = new ImageXNNAlgorithm();
            long instance = this.mXNNImageDetect.a(1, this.mModelPath);
            AlgorithmBenchmark.a(instance == 0 ? 1 : 0);
            if (instance == 0) {
                XMediaResponse response2 = new XMediaResponse();
                response2.mResult = new ArrayList();
                response2.mMode = 1;
                response2.mErrInfo = new ErrorInfo(10003, "image xnn algorithm init failed.");
                list.add(response2);
                return list;
            }
        }
        ArrayList roi = null;
        if (option != null) {
            Object obj = option.get("roi");
            if (obj instanceof ArrayList) {
                roi = (ArrayList) obj;
            }
        }
        AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putString("FILE_SIZE", image.getWidth() + "*" + image.getHeight());
        int i = 0;
        do {
            if (roi == null || roi.size() <= 0) {
                response = this.mXNNImageDetect.b(image, null);
            } else {
                Object[] array = (Object[]) roi.get(i);
                float[] floats = new float[4];
                for (int j = 0; j < array.length; j++) {
                    if (array[j] instanceof Double) {
                        floats[j] = ((Double) array[j]).floatValue();
                    } else if (array[j] instanceof Integer) {
                        floats[j] = ((Integer) array[j]).floatValue();
                    }
                }
                response = this.mXNNImageDetect.b(image, floats);
            }
            list.add(response);
            Log.i(TAG, "detect[" + i + "] done, list:" + response.toString());
            AlgorithmBenchmark.c(response.mErrInfo.mCode == 0 ? 0 : 1);
            i++;
            if (roi == null) {
                break;
            }
        } while (i < roi.size());
        if (this.mXNNImageDetect != null) {
            this.mXNNImageDetect.a();
        }
        this.mXNNImageDetect = null;
        return list;
    }

    public void startTrack(List<TrackItem> list, int w, int h, byte[] nv21data, XMediaCallback callback) {
        if (this.mTrackAlgorithm != null) {
            Log.w(TAG, "track is working, just skip...");
            return;
        }
        AlgorithmBenchmark.a((String) "KEY_INIT").putInt("ENGINE_TYPE", AlgorithmBenchmark.b);
        AlgorithmBenchmark.a((String) "KEY_INIT").putString("MODEL_SIZE", "0");
        this.mTrackAlgorithm = new TrackAlgorithm();
        List targetList = new ArrayList();
        int i = 0;
        while (list != null && i < list.size()) {
            TargetRect target = new TargetRect();
            Rect rect = list.get(i).mRect;
            target.X = rect.left;
            target.Y = rect.top;
            target.width = rect.width();
            target.height = rect.height();
            TrackerItem item = new TrackerItem();
            item.mLabel = list.get(i).mLabel;
            item.mConfidence = list.get(i).mConfidence;
            item.mRect = target;
            targetList.add(item);
            i++;
        }
        this.mTrackAlgorithm.a(targetList, w, h, nv21data, callback);
    }

    private boolean trackAvailable() {
        if (DynamicReleaseApi.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).isBundleExist("android-phone-wallet-slam")) {
            return true;
        }
        Log.i(TAG, "slam-build bundle is not exist..");
        return false;
    }

    private void setInitUC(String key) {
        AlgorithmBenchmark.a(key).putString("BUSSINESS_ID", this.mBizId);
        AlgorithmBenchmark.a(key).putString("MODEL_FILE_ID", this.mModelId);
        AlgorithmBenchmark.a(key).putString("MODEL_SIZE", new DecimalFormat(DiskFormatter.FORMAT).format((((double) new File(this.mModelPath).length()) * 1.0d) / 1024.0d));
        AlgorithmBenchmark.a(key).putInt("ENGINE_TYPE", AlgorithmBenchmark.a);
    }
}
