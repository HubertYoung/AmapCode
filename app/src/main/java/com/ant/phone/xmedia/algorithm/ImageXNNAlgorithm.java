package com.ant.phone.xmedia.algorithm;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.text.TextUtils;
import com.alipay.alipaylogger.Log;
import com.alipay.android.phone.falcon.xmedia.XNNJNI;
import com.alipay.android.phone.falcon.xmedia.XNNResult;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.ant.phone.xmedia.api.utils.OtherUtils;
import com.ant.phone.xmedia.benchmark.AlgorithmBenchmark;
import com.ant.phone.xmedia.params.BoundingBox;
import com.ant.phone.xmedia.params.ErrorInfo;
import com.ant.phone.xmedia.params.XMediaClassifyResult;
import com.ant.phone.xmedia.params.XMediaDetectResult;
import com.ant.phone.xmedia.params.XMediaResponse;
import java.util.ArrayList;

public class ImageXNNAlgorithm {
    private long a = 0;
    private int b = 2;

    public ImageXNNAlgorithm() {
        Log.i("ImageXNNAlgorithm", "ImageXNNAlgorithm construct, id:" + this);
    }

    public final long a(int type, String model_path) {
        Log.i("ImageXNNAlgorithm", "init(), mAlgorithmType:" + type);
        this.b = type;
        a();
        long t = System.currentTimeMillis();
        AlgorithmBenchmark.a((String) "KEY_INIT").putLong("ENGINE_INIT_START", t);
        switch (this.b) {
            case 1:
                this.a = XNNJNI.init(OtherUtils.a(model_path));
                break;
            case 2:
                this.a = XNNJNI.init(OtherUtils.a(model_path));
                break;
        }
        long end = System.currentTimeMillis();
        Log.i("ImageXNNAlgorithm", "init took " + (end - t) + RPCDataParser.TIME_MS);
        AlgorithmBenchmark.a((String) "KEY_INIT").putLong("ENGINE_INIT_END", end);
        if (this.a == 0) {
            Log.i("ImageXNNAlgorithm", "image xnn algorithm init failed.");
        }
        return this.a;
    }

    public final XMediaResponse a(Bitmap bitmap, float[] roi) {
        Log.i("ImageXNNAlgorithm", "classify call begin");
        if (this.a == 0) {
            Log.w("ImageXNNAlgorithm", "xnn not inited. return");
            return null;
        }
        long t = System.currentTimeMillis();
        AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putLong("IMAGE_CLS_START", t);
        ArrayList arrayList = new ArrayList();
        XMediaResponse response = new XMediaResponse();
        response.mResult = arrayList;
        response.mMode = this.b;
        response.mErrInfo = new ErrorInfo(0, "no error");
        try {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            int[] absolute_roi = new int[4];
            if (roi != null) {
                RectF rectF = new RectF(roi[0] * ((float) w), roi[1] * ((float) h), (roi[2] + roi[0]) * ((float) w), (roi[3] + roi[1]) * ((float) h));
                Log.i("ImageXNNAlgorithm", "classify with roi:" + rectF);
                absolute_roi[0] = (int) rectF.left;
                absolute_roi[1] = (int) rectF.top;
                absolute_roi[2] = (int) rectF.width();
                absolute_roi[3] = (int) rectF.height();
            } else {
                absolute_roi = null;
            }
            int[] data = new int[(w * h)];
            bitmap.getPixels(data, 0, w, 0, 0, w, h);
            if (absolute_roi != null) {
                Log.i("ImageXNNAlgorithm", "absolute_roi:" + absolute_roi[0] + "," + absolute_roi[1] + "," + absolute_roi[2] + "," + absolute_roi[3]);
            }
            Log.i("ImageXNNAlgorithm", "image w:" + w + ", h:" + h);
            XNNResult result = XNNJNI.classifyImage(this.a, data, w, h, absolute_roi);
            if (result == null || result.retCode < 0) {
                Log.w("ImageXNNAlgorithm", "retcode < 0, something is wrong.");
                response.mErrInfo = new ErrorInfo(10003, "algorithm error");
                if (result != null) {
                    Log.w("ImageXNNAlgorithm", "retcode:" + result.retCode);
                    response.mErrInfo = new ErrorInfo(result.retCode, "algorithm error");
                }
                long end = System.currentTimeMillis();
                Log.i("ImageXNNAlgorithm", "classify rgb took " + (end - t) + RPCDataParser.TIME_MS);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putLong("IMAGE_CLS_END", end);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putInt("ERROR_CODE", response.mErrInfo.mCode);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putString("ERROR_MSG", response.mErrInfo.mMsg);
                return response;
            } else if (result.labelNums <= 0 || result.confArray == null || result.confArray.length != result.labelNums) {
                Log.w("ImageXNNAlgorithm", "function success but no target classified, label count:" + result.labelNums);
                if (result.confArray != null) {
                    Log.w("ImageXNNAlgorithm", "confArray size:" + result.confArray.length);
                }
                long end2 = System.currentTimeMillis();
                Log.i("ImageXNNAlgorithm", "classify rgb took " + (end2 - t) + RPCDataParser.TIME_MS);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putLong("IMAGE_CLS_END", end2);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putInt("ERROR_CODE", response.mErrInfo.mCode);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putString("ERROR_MSG", response.mErrInfo.mMsg);
                return response;
            } else {
                String[] lables = null;
                if (!TextUtils.isEmpty(result.objectName)) {
                    String str = result.objectName.substring(1, result.objectName.length());
                    if (!TextUtils.isEmpty(str)) {
                        lables = str.split(MetaRecord.LOG_SEPARATOR);
                    }
                }
                if (lables == null || lables.length != result.labelNums) {
                    Log.w("ImageXNNAlgorithm", "wrong result for this frame.");
                    long end22 = System.currentTimeMillis();
                    Log.i("ImageXNNAlgorithm", "classify rgb took " + (end22 - t) + RPCDataParser.TIME_MS);
                    AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putLong("IMAGE_CLS_END", end22);
                    AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putInt("ERROR_CODE", response.mErrInfo.mCode);
                    AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putString("ERROR_MSG", response.mErrInfo.mMsg);
                    return response;
                }
                for (int i = 0; i < result.labelNums; i++) {
                    XMediaClassifyResult classifyResult = new XMediaClassifyResult();
                    classifyResult.mConfidence = result.confArray[i];
                    classifyResult.mLabel = lables[i];
                    arrayList.add(classifyResult);
                }
                long end222 = System.currentTimeMillis();
                Log.i("ImageXNNAlgorithm", "classify rgb took " + (end222 - t) + RPCDataParser.TIME_MS);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putLong("IMAGE_CLS_END", end222);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putInt("ERROR_CODE", response.mErrInfo.mCode);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_CLS").putString("ERROR_MSG", response.mErrInfo.mMsg);
                return response;
            }
        } catch (Throwable throwable) {
            Log.e("ImageXNNAlgorithm", "classify exp:", throwable);
            response.mErrInfo = new ErrorInfo(10003, "algorithm error");
        }
    }

    public final XMediaResponse b(Bitmap bitmap, float[] roi) {
        int crop_h;
        Log.i("ImageXNNAlgorithm", "detect call begin");
        if (this.a == 0) {
            Log.w("ImageXNNAlgorithm", "xnn not inited. return");
            return null;
        }
        long t = System.currentTimeMillis();
        AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putLong("IMAGE_DET_START", t);
        ArrayList arrayList = new ArrayList();
        XMediaResponse response = new XMediaResponse();
        response.mResult = arrayList;
        response.mMode = this.b;
        response.mErrInfo = new ErrorInfo(0, "no error");
        try {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            int[] absolute_roi = new int[4];
            if (roi != null) {
                RectF rectF = new RectF(roi[0] * ((float) w), roi[1] * ((float) h), (roi[2] + roi[0]) * ((float) w), (roi[3] + roi[1]) * ((float) h));
                Log.i("ImageXNNAlgorithm", "detect with roi:" + rectF);
                absolute_roi[0] = (int) rectF.left;
                absolute_roi[1] = (int) rectF.top;
                absolute_roi[2] = (int) rectF.width();
                absolute_roi[3] = (int) rectF.height();
            } else {
                absolute_roi = null;
            }
            int[] data = new int[(w * h)];
            bitmap.getPixels(data, 0, w, 0, 0, w, h);
            if (absolute_roi != null) {
                Log.i("ImageXNNAlgorithm", "absolute_roi:" + absolute_roi[0] + "," + absolute_roi[1] + "," + absolute_roi[2] + "," + absolute_roi[3]);
            }
            Log.i("ImageXNNAlgorithm", "image w:" + w + ", h:" + h);
            XNNResult result = XNNJNI.detectImage(this.a, data, w, h, absolute_roi);
            if (result == null || result.retCode < 0) {
                Log.w("ImageXNNAlgorithm", "retcode < 0, something is wrong.");
                response.mErrInfo = new ErrorInfo(10003, "algorithm error");
                if (result != null) {
                    Log.w("ImageXNNAlgorithm", "retcode:" + result.retCode);
                    response.mErrInfo = new ErrorInfo(result.retCode, "algorithm error");
                }
                long end = System.currentTimeMillis();
                AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putLong("IMAGE_DET_END", end);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putInt("ERROR_CODE", response.mErrInfo.mCode);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putString("ERROR_MSG", response.mErrInfo.mMsg);
                Log.i("ImageXNNAlgorithm", "detect rgb took " + (end - t) + RPCDataParser.TIME_MS);
                return response;
            } else if (result.labelNums <= 0 || result.confArray == null || result.confArray.length != result.labelNums || result.posArray == null || result.posArray.length / 4 != result.labelNums) {
                Log.w("ImageXNNAlgorithm", "function success but no target detected, label count:" + result.labelNums);
                if (result.confArray != null) {
                    Log.w("ImageXNNAlgorithm", "confArray size:" + result.confArray.length);
                }
                if (result.posArray != null) {
                    Log.w("ImageXNNAlgorithm", "posArray size:" + result.posArray.length);
                }
                long end2 = System.currentTimeMillis();
                AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putLong("IMAGE_DET_END", end2);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putInt("ERROR_CODE", response.mErrInfo.mCode);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putString("ERROR_MSG", response.mErrInfo.mMsg);
                Log.i("ImageXNNAlgorithm", "detect rgb took " + (end2 - t) + RPCDataParser.TIME_MS);
                return response;
            } else {
                String[] lables = null;
                if (!TextUtils.isEmpty(result.objectName)) {
                    String str = result.objectName.substring(1, result.objectName.length());
                    if (!TextUtils.isEmpty(str)) {
                        lables = str.split(MetaRecord.LOG_SEPARATOR);
                    }
                }
                if (lables == null || lables.length != result.labelNums) {
                    Log.w("ImageXNNAlgorithm", "wrong result for this frame.");
                    long end22 = System.currentTimeMillis();
                    AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putLong("IMAGE_DET_END", end22);
                    AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putInt("ERROR_CODE", response.mErrInfo.mCode);
                    AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putString("ERROR_MSG", response.mErrInfo.mMsg);
                    Log.i("ImageXNNAlgorithm", "detect rgb took " + (end22 - t) + RPCDataParser.TIME_MS);
                    return response;
                }
                int j = 0;
                for (int i = 0; i < result.labelNums; i++) {
                    XMediaDetectResult detectResult = new XMediaDetectResult();
                    detectResult.mConfidence = result.confArray[i];
                    detectResult.mLabel = lables[i];
                    float crop_x1 = (float) (absolute_roi == null ? 0 : absolute_roi[0]);
                    float crop_y1 = (float) (absolute_roi == null ? 0 : absolute_roi[1]);
                    int crop_w = absolute_roi == null ? w : absolute_roi[2];
                    if (absolute_roi == null) {
                        crop_h = h;
                    } else {
                        crop_h = absolute_roi[3];
                    }
                    float rectx1 = result.posArray[j];
                    float recty1 = result.posArray[j + 1];
                    float x1 = crop_x1 + (((float) crop_w) * rectx1);
                    float y1 = crop_y1 + (((float) crop_h) * recty1);
                    detectResult.mBoundingBox = new BoundingBox(x1 / ((float) w), y1 / ((float) h), ((x1 + ((result.posArray[j + 2] - rectx1) * ((float) crop_w))) - x1) / ((float) w), ((y1 + ((result.posArray[j + 3] - recty1) * ((float) crop_h))) - y1) / ((float) h));
                    arrayList.add(detectResult);
                    j += 4;
                }
                long end222 = System.currentTimeMillis();
                AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putLong("IMAGE_DET_END", end222);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putInt("ERROR_CODE", response.mErrInfo.mCode);
                AlgorithmBenchmark.a((String) "KEY_IMAGE_DET").putString("ERROR_MSG", response.mErrInfo.mMsg);
                Log.i("ImageXNNAlgorithm", "detect rgb took " + (end222 - t) + RPCDataParser.TIME_MS);
                return response;
            }
        } catch (Throwable throwable) {
            Log.e("ImageXNNAlgorithm", "detect exp:", throwable);
            response.mErrInfo = new ErrorInfo(10003, "algorithm error");
        }
    }

    public final void a() {
        Log.i("ImageXNNAlgorithm", "uninit()");
        long t = System.currentTimeMillis();
        if (this.a != 0) {
            switch (this.b) {
                case 1:
                    XNNJNI.release(this.a);
                    break;
                case 2:
                    XNNJNI.release(this.a);
                    break;
            }
            this.a = 0;
        }
        Log.i("ImageXNNAlgorithm", "uninit took " + (System.currentTimeMillis() - t) + RPCDataParser.TIME_MS);
    }
}
