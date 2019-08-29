package com.ant.phone.xmedia.hybrid;

import android.graphics.BitmapFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.alipaylogger.Log;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoEffect;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.ant.phone.xmedia.XMediaEngine;
import com.ant.phone.xmedia.XMediaEngine.XMediaCallback;
import com.ant.phone.xmedia.api.utils.OtherUtils;
import com.ant.phone.xmedia.params.ErrorInfo;
import com.ant.phone.xmedia.params.XMediaFilterItem;
import com.ant.phone.xmedia.params.XMediaResponse;
import com.ant.phone.xmedia.params.XMediaResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class H5XMediaPlugin extends XMediaH5SimplePlugin {
    public static final String ACTION_START = "startXMedia";
    public static final String ACTION_STOP = "stopXMedia";
    public static final String RESULT_ERROR = "error";
    public static final String RESULT_ERROR_MSG = "errorMessage";
    public static final String RESULT_TMP_PATH = "tmpPath";
    private static final String TAG = "H5XMediaPlugin";

    public class Filter {
        @JSONField(name = "item")
        public HashMap<String, XMediaFilterItem> filter;
        @JSONField(name = "threshold")
        public int threshold;
        @JSONField(name = "type")
        public String type;
    }

    public class Params {
        @JSONField(name = "biz")
        public String biz;
        @JSONField(name = "filter")
        public Filter filter;
        @JSONField(name = "imagePath")
        public String imagePath;
        @JSONField(name = "mode")
        public int mode;
        @JSONField(name = "model")
        public String model;
        @JSONField(name = "modelId")
        public String modelId;
        @JSONField(name = "roi")
        public ArrayList<Object[]> roi;
        @JSONField(name = "secret")
        public String secret;
        @JSONField(name = "source")
        public int source;
    }

    public class ReturnParams {
        @JSONField(name = "error")
        public ErrorInfo error;
        @JSONField(name = "mode")
        public int mode;
        @JSONField(name = "result")
        public List<XMediaResult> result;
    }

    public class ReturnParamsWithROI {
        @JSONField(name = "error")
        public ErrorInfo error;
        @JSONField(name = "mode")
        public int mode;
        @JSONField(name = "result")
        public List<List<XMediaResult>> result;
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(ACTION_START);
        filter.addAction(ACTION_STOP);
    }

    public void onRelease() {
        Log.i(TAG, "onRelease");
        XMediaEngine.getInstance().stopRunning();
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext context) {
        Log.i(TAG, "handleEvent action: " + event.getAction() + ", params: " + event.getParam());
        if (ACTION_START.equals(event.getAction())) {
            final Params params = (Params) parseParams(event, Params.class);
            if (params == null || params.biz == null) {
                return context.sendError(event, Error.INVALID_PARAM);
            }
            OtherUtils.a((Runnable) new Runnable() {
                public void run() {
                    try {
                        HashMap map = new HashMap();
                        map.put("model_path", params.model);
                        map.put("model_id", params.modelId);
                        HashMap options = new HashMap();
                        options.put(APVideoEffect.TYPE_FILTER, params.filter);
                        options.put("roi", params.roi);
                        if (params.source == 1) {
                            XMediaResponse response = XMediaEngine.getInstance().init(params.biz, params.secret, map);
                            Log.i(H5XMediaPlugin.TAG, "init, response.toString():" + response.toString());
                            if (response.mErrInfo.mCode != 0) {
                                ReturnParams returnParams = new ReturnParams();
                                returnParams.error = response.mErrInfo;
                                returnParams.mode = response.mMode;
                                returnParams.result = response.mResult;
                                context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams));
                                return;
                            }
                            XMediaEngine.getInstance().startRunning(params.mode, new XMediaCallback() {
                                public boolean onResponse(XMediaResponse xMediaResponse) {
                                    if (xMediaResponse == null || xMediaResponse.mResult == null) {
                                        Log.i(H5XMediaPlugin.TAG, "xMediaResponse return nothing, skip sendBridgeResult");
                                    } else {
                                        Log.i(H5XMediaPlugin.TAG, "onResponse, xMediaResponse.toString:" + xMediaResponse.toString());
                                        ReturnParams returnParams = new ReturnParams();
                                        returnParams.error = xMediaResponse.mErrInfo;
                                        returnParams.mode = xMediaResponse.mMode;
                                        returnParams.result = xMediaResponse.mResult;
                                        JSONObject object = (JSONObject) JSON.toJSON(returnParams);
                                        if (returnParams.error.mCode != 0) {
                                            context.sendBridgeResult(object);
                                        } else {
                                            context.sendBridgeResultWithCallbackKept(object);
                                        }
                                    }
                                    return false;
                                }

                                public boolean onTrack(XMediaResponse xMediaResponse) {
                                    if (xMediaResponse == null || xMediaResponse.mResult == null) {
                                        Log.i(H5XMediaPlugin.TAG, "xMediaResponse return nothing, skip sendBridgeResult");
                                    } else {
                                        Log.i(H5XMediaPlugin.TAG, "onTrack, xMediaResponse.toString:" + xMediaResponse.toString());
                                        ReturnParams returnParams = new ReturnParams();
                                        returnParams.error = xMediaResponse.mErrInfo;
                                        returnParams.mode = xMediaResponse.mMode;
                                        returnParams.result = xMediaResponse.mResult;
                                        context.sendBridgeResultWithCallbackKept((JSONObject) JSON.toJSON(returnParams));
                                    }
                                    return false;
                                }
                            }, options);
                        } else if (params.source == 0) {
                            switch (params.mode) {
                                case 1:
                                    XMediaResponse response2 = XMediaEngine.getInstance().init(params.biz, params.secret, map);
                                    Log.i(H5XMediaPlugin.TAG, "init, response.toString():" + response2.toString());
                                    if (response2.mErrInfo.mCode != 0) {
                                        ReturnParams returnParams2 = new ReturnParams();
                                        returnParams2.error = response2.mErrInfo;
                                        returnParams2.mode = response2.mMode;
                                        returnParams2.result = response2.mResult;
                                        context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams2));
                                        return;
                                    }
                                    List list = XMediaEngine.getInstance().detect(BitmapFactory.decodeFile(params.imagePath), options);
                                    List results = new ArrayList();
                                    for (int i = 0; i < list.size(); i++) {
                                        results.add(list.get(i).mResult);
                                    }
                                    Log.i(H5XMediaPlugin.TAG, "image detect, results:" + results.toString());
                                    if (params.roi == null || params.roi.size() <= 0) {
                                        ReturnParams returnParams3 = new ReturnParams();
                                        returnParams3.error = list.get(0).mErrInfo;
                                        returnParams3.mode = list.get(0).mMode;
                                        returnParams3.result = (List) results.get(0);
                                        context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams3));
                                        return;
                                    }
                                    ReturnParamsWithROI returnParams4 = new ReturnParamsWithROI();
                                    returnParams4.error = list.get(0).mErrInfo;
                                    returnParams4.mode = list.get(0).mMode;
                                    returnParams4.result = results;
                                    context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams4));
                                    return;
                                case 2:
                                    XMediaResponse response3 = XMediaEngine.getInstance().init(params.biz, params.secret, map);
                                    Log.i(H5XMediaPlugin.TAG, "init, response.toString():" + response3.toString());
                                    if (response3.mErrInfo.mCode != 0) {
                                        ReturnParams returnParams5 = new ReturnParams();
                                        returnParams5.error = response3.mErrInfo;
                                        returnParams5.mode = response3.mMode;
                                        returnParams5.result = response3.mResult;
                                        context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams5));
                                        return;
                                    }
                                    List list2 = XMediaEngine.getInstance().classify(BitmapFactory.decodeFile(params.imagePath), options);
                                    List results2 = new ArrayList();
                                    for (int i2 = 0; i2 < list2.size(); i2++) {
                                        results2.add(list2.get(i2).mResult);
                                    }
                                    Log.i(H5XMediaPlugin.TAG, "image classify, results:" + results2.toString());
                                    if (params.roi == null || params.roi.size() <= 0) {
                                        ReturnParams returnParams6 = new ReturnParams();
                                        returnParams6.error = list2.get(0).mErrInfo;
                                        returnParams6.mode = list2.get(0).mMode;
                                        returnParams6.result = (List) results2.get(0);
                                        context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams6));
                                        return;
                                    }
                                    ReturnParamsWithROI returnParams7 = new ReturnParamsWithROI();
                                    returnParams7.error = list2.get(0).mErrInfo;
                                    returnParams7.mode = list2.get(0).mMode;
                                    returnParams7.result = results2;
                                    context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams7));
                                    return;
                                default:
                                    context.sendError(event, Error.INVALID_PARAM);
                                    return;
                            }
                        } else {
                            context.sendError(event, Error.INVALID_PARAM);
                        }
                    } catch (Exception e) {
                        Log.e(H5XMediaPlugin.TAG, "h5 plugin exp:", e);
                    }
                }
            });
        } else if (!ACTION_STOP.equals(event.getAction())) {
            return context.sendError(event, Error.INVALID_PARAM);
        } else {
            XMediaEngine.getInstance().stopRunning();
        }
        return true;
    }
}
