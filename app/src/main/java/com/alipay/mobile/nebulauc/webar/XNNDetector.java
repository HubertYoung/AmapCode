package com.alipay.mobile.nebulauc.webar;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.DownloadService;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.multimedia.js.file.H5FileUploadPlugin.Params;
import com.ant.phone.xmedia.XMediaEngine;
import com.ant.phone.xmedia.XMediaEngine.XMediaCallback;
import com.ant.phone.xmedia.params.XMediaResponse;
import com.uc.webview.export.extension.IARDetector;
import com.uc.webview.export.extension.IARDetector.ARSessionFrame;
import com.uc.webview.export.extension.IARDetector.ResultListener;
import java.io.File;
import java.util.HashMap;

public class XNNDetector implements IARDetector {
    public static final String TAG = "XNNDetector";
    public static final String XNN_DETECTOR_VERSION = "0.1.0";
    private int mDetectorMode = 1;
    /* access modifiers changed from: private */
    public boolean mIsDownloadingModel = false;
    /* access modifiers changed from: private */
    public String mModelUrl = null;
    /* access modifiers changed from: private */
    public ResultListener mResultListener = null;
    /* access modifiers changed from: private */
    public STATUS mStatus = STATUS.STOP;
    private XMediaEngine mXMediaEngine = null;

    private enum STATUS {
        INIT,
        START,
        RESUME,
        PAUSE,
        STOP
    }

    public void setARSessionFrame(ARSessionFrame arSessionFrame) {
        if (this.mStatus == STATUS.INIT || this.mStatus == STATUS.START || this.mStatus == STATUS.RESUME) {
            detectFrame(arSessionFrame);
        }
    }

    private void detectFrame(ARSessionFrame arSessionFrame) {
        transYV12ToNV21(arSessionFrame);
        if (this.mXMediaEngine != null) {
            this.mXMediaEngine.feedYuvFrame(arSessionFrame.width, arSessionFrame.height, arSessionFrame.data, arSessionFrame.rotation);
        }
    }

    private void transYV12ToNV21(ARSessionFrame arSessionFrame) {
        if (arSessionFrame.format == 3) {
            convertYV12ToNV21(arSessionFrame.data, arSessionFrame.width, arSessionFrame.height);
        }
    }

    private void convertYV12ToNV21(byte[] data, int width, int height) {
        byte[] mDataFormatConvertArray = new byte[data.length];
        int yFrameSize = width * height;
        if (((double) data.length) == ((double) yFrameSize) * 1.5d) {
            System.arraycopy(data, yFrameSize, mDataFormatConvertArray, yFrameSize, data.length - yFrameSize);
            int i = yFrameSize;
            int j = yFrameSize;
            while (i < (yFrameSize / 4) + yFrameSize) {
                data[j] = mDataFormatConvertArray[i];
                i++;
                j += 2;
            }
            int i2 = yFrameSize + (yFrameSize / 4);
            int j2 = yFrameSize + 1;
            while (i2 < data.length) {
                data[j2] = mDataFormatConvertArray[i2];
                i2++;
                j2 += 2;
            }
        }
    }

    public String setARSessionFrameFilter(ARSessionFrame arSessionFrame) {
        return null;
    }

    public void setResultListener(ResultListener resultListener) {
        this.mResultListener = resultListener;
    }

    public void init(int i, int i1, int i2, int i3, int i4) {
    }

    public void stop() {
        if (this.mXMediaEngine != null) {
            this.mXMediaEngine.stopRunning();
        }
        this.mStatus = STATUS.STOP;
    }

    public void pause() {
        this.mStatus = STATUS.PAUSE;
    }

    public void resume() {
        this.mStatus = STATUS.RESUME;
    }

    public void setMarkers(String[] strings) {
    }

    public void removeMarkers() {
    }

    public void setOption(String s) {
        if (TextUtils.isEmpty(s)) {
            sendErrorMsg("setOptionFailed: option string is empty");
        } else if (!TextUtils.isEmpty(this.mModelUrl)) {
            sendErrorMsg("setOptionFailed: model url has already set");
        } else if (this.mIsDownloadingModel) {
            sendErrorMsg("setOptionFailed: model is downloading");
        } else {
            final JSONObject info = H5Utils.parseObject(s);
            final String modelUrl = info.getString("modelPath");
            File cacheDir = LauncherApplicationAgent.getInstance().getApplicationContext().getExternalCacheDir();
            if (cacheDir != null) {
                final String fileName = cacheDir.getAbsolutePath() + "/model/" + modelUrl.hashCode();
                if (!new File(fileName).exists()) {
                    this.mIsDownloadingModel = true;
                    DownloadRequest request = new DownloadRequest(modelUrl);
                    request.setPath(fileName);
                    request.setTransportCallback(new TransportCallback() {
                        public void onCancelled(Request request) {
                            XNNDetector.this.mIsDownloadingModel = false;
                        }

                        public void onPreExecute(Request request) {
                        }

                        public void onPostExecute(Request request, Response response) {
                            H5Log.d(XNNDetector.TAG, "download model success");
                            XNNDetector.this.initXMediaEngine(fileName, info);
                            XNNDetector.this.mModelUrl = modelUrl;
                            XNNDetector.this.mIsDownloadingModel = false;
                        }

                        public void onProgressUpdate(Request request, double v) {
                        }

                        public void onFailed(Request request, int i, String s) {
                            H5Log.d(XNNDetector.TAG, "download model failed");
                            if (XNNDetector.this.mResultListener != null) {
                                JSONObject rsObj = new JSONObject();
                                rsObj.put((String) "type", (Object) "error");
                                rsObj.put((String) "value", (Object) "model download failed");
                                rsObj.put((String) Params.TYPE_FILE_URL, (Object) modelUrl);
                                XNNDetector.this.mResultListener.onResult(rsObj.toJSONString());
                            }
                            XNNDetector.this.mIsDownloadingModel = false;
                        }
                    });
                    ((DownloadService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(DownloadService.class.getName())).addDownload(request);
                    return;
                }
                initXMediaEngine(fileName, info);
            }
        }
    }

    /* access modifiers changed from: private */
    public XMediaEngine initXMediaEngine(String modelSavedPath, JSONObject info) {
        HashMap map = new HashMap();
        map.put("model_path", modelSavedPath);
        map.put("model_path_type", Integer.valueOf(1));
        this.mXMediaEngine = XMediaEngine.getInstance();
        XMediaResponse response = this.mXMediaEngine.init(null, null, map);
        if (response == null || response.mErrInfo == null || response.mErrInfo.mCode == 0) {
            JSONArray roiArray = H5Utils.getJSONArray(info, "roi", null);
            int[] roiIntArray = new int[4];
            if (roiArray == null || roiArray.isEmpty() || roiArray.size() <= 0) {
                return null;
            }
            int i = 0;
            while (i < roiArray.size() && i <= 4) {
                roiIntArray[i] = roiArray.getIntValue(i);
                i++;
            }
            HashMap optMap = new HashMap();
            optMap.put("roi", roiIntArray);
            this.mDetectorMode = H5Utils.getInt(info, (String) "detectorMode") == 2 ? 2 : 1;
            this.mXMediaEngine.startRunning(this.mDetectorMode, new XMediaCallback() {
                public boolean onResponse(XMediaResponse xMediaResponse) {
                    if (!(xMediaResponse == null || xMediaResponse.mResult == null)) {
                        XNNDetector.this.mStatus = STATUS.START;
                        if (!(XNNDetector.this.mResultListener == null || xMediaResponse == null)) {
                            JSONObject rsObj = new JSONObject();
                            rsObj.put((String) "type", (Object) "resignResult");
                            rsObj.put((String) "value", (Object) xMediaResponse);
                            XNNDetector.this.mResultListener.onResult(rsObj.toJSONString());
                        }
                    }
                    return false;
                }

                public boolean onTrack(XMediaResponse xMediaResponse) {
                    if (!(xMediaResponse == null || xMediaResponse.mResult == null)) {
                        XNNDetector.this.mStatus = STATUS.START;
                        if (!(XNNDetector.this.mResultListener == null || xMediaResponse == null)) {
                            JSONObject rsObj = new JSONObject();
                            rsObj.put((String) "type", (Object) "resignResult");
                            rsObj.put((String) "value", (Object) xMediaResponse);
                            XNNDetector.this.mResultListener.onResult(rsObj.toJSONString());
                        }
                    }
                    return false;
                }
            }, optMap);
            this.mStatus = STATUS.INIT;
            H5Log.e((String) TAG, (String) "xmedia init success");
            return this.mXMediaEngine;
        }
        H5Log.e((String) TAG, (String) "xmedia init failed");
        if (this.mResultListener != null) {
            JSONObject rsObj = new JSONObject();
            rsObj.put((String) "type", (Object) "error");
            rsObj.put((String) "message", (Object) "xmedia engine init failed");
            rsObj.put((String) Params.TYPE_FILE_URL, (Object) modelSavedPath);
            this.mResultListener.onResult(rsObj.toJSONString());
        }
        this.mStatus = STATUS.STOP;
        return null;
    }

    public boolean isDetector() {
        return false;
    }

    public String getVersion() {
        return XNN_DETECTOR_VERSION;
    }

    private void sendErrorMsg(String message) {
        H5Log.d(TAG, "send error msg: " + message);
        if (this.mResultListener != null) {
            JSONObject rsObj = new JSONObject();
            rsObj.put((String) "type", (Object) "error");
            rsObj.put((String) "value", (Object) message);
            this.mResultListener.onResult(rsObj.toJSONString());
        }
    }
}
