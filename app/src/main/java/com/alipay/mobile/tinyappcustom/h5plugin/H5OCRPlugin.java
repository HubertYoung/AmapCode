package com.alipay.mobile.tinyappcustom.h5plugin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption.QUALITITY;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUpRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUploadRsp;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.h5.H5HttpUrlResponse;
import com.alipay.mobile.common.transport.h5.H5NetworkManager;
import com.alipay.mobile.common.transport.http.HttpException;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.util.H5CookieUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.ui.APGenericProgressDialog;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

public class H5OCRPlugin extends H5SimplePlugin {
    public static final String TAG = H5OCRPlugin.class.getSimpleName();
    private static final Map<String, String> c = new HashMap<String, String>() {
        {
            put("ocrIdCard", "ocr_idcard");
            put("ocrGeneral", "ocr_general");
            put("ocrBankCard", "ocr_bank_card");
            put("ocrVehicle", "ocr_vehicle");
            put("ocrBusinessLicense", "ocr_business_license");
            put("ocrTrainTicket", "ocr_train_ticket");
            put("ocrDriverLicense", "ocr_driver_license");
            put("ocrBusinessCard", "ocr_business_card");
            put("ocrPassport", "ocr_passport");
            put("ocrVehiclePlate", "ocr_vehicle_plate");
            put("ocrVin", "ocr_vin");
        }
    };
    APGenericProgressDialog a;
    private long b;
    private H5Page d;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("ocr");
        for (String action : c.keySet()) {
            filter.addAction(action);
        }
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (event.getTarget() instanceof H5Page) {
            this.d = (H5Page) event.getTarget();
        }
        String action = event.getAction();
        if ("ocr".equals(action)) {
            this.b = System.currentTimeMillis();
            a(event, context, H5Utils.getString(event.getParam(), (String) "ocrType"));
        } else if (c.keySet().contains(action)) {
            this.b = System.currentTimeMillis();
            a(event, context, c.get(action));
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext context, String ocrType) {
        if (event.getParam() == null) {
            context.sendError(event, Error.INVALID_PARAM);
        } else if (event.getParam().containsKey("path") && !(event.getParam().get("path") instanceof String)) {
            H5Log.d(TAG, "ocr... has value for path but not string");
            context.sendError(event, Error.INVALID_PARAM);
        } else if (!event.getParam().containsKey("imageBase64") || (event.getParam().get("imageBase64") instanceof String)) {
            final String path = H5Utils.getString(event.getParam(), (String) "path");
            final String image = H5Utils.getString(event.getParam(), (String) "imageBase64");
            if (TextUtils.isEmpty(path) && TextUtils.isEmpty(image)) {
                H5Log.d(TAG, "ocr...path and imageBase64 is null");
                context.sendError(1001, (String) "path和imageBase64不可同时为空");
            } else if (TextUtils.isEmpty(ocrType)) {
                H5Log.d(TAG, "ocr...ocrType is null");
                context.sendError(event, Error.INVALID_PARAM);
            } else {
                String tempSide = H5Utils.getString(event.getParam(), (String) "side");
                if (("ocr_idcard".equals(ocrType) || "ocr_driver_license".equals(ocrType)) && TextUtils.isEmpty(tempSide)) {
                    H5Log.d(TAG, "ocr...side is null");
                    context.sendError(event, Error.INVALID_PARAM);
                    return;
                }
                final String side = tempSide;
                H5Log.d(TAG, "ocr...url=" + path + ", imageBase64 is empty = " + TextUtils.isEmpty(image) + " , side=" + side + ",type=" + ocrType);
                if (this.a == null) {
                    this.a = a();
                } else if (!this.a.isShowing()) {
                    this.a.show();
                } else {
                    return;
                }
                final String str = ocrType;
                final H5Event h5Event = event;
                final H5BridgeContext h5BridgeContext = context;
                ((TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).execute(new Runnable() {
                    public void run() {
                        try {
                            if (!TextUtils.isEmpty(path)) {
                                if (path.startsWith("https://resource/") && path.endsWith(Constant.AL_IMAGE_SUFFIX)) {
                                    H5OCRPlugin.this.a(path, side, str, h5Event, h5BridgeContext);
                                } else if (path.startsWith("http")) {
                                    H5OCRPlugin.this.c(path, side, str, h5Event, h5BridgeContext);
                                } else {
                                    H5OCRPlugin.this.b(path, side, str, h5Event, h5BridgeContext);
                                }
                            } else if (H5OCRPlugin.b(image)) {
                                h5BridgeContext.sendError(1002, (String) "请检查图片大小是否超过api限制");
                            } else {
                                H5OCRPlugin.this.a((String) "", image, side, str, h5Event, h5BridgeContext);
                            }
                            H5OCRPlugin.this.a(H5OCRPlugin.this.a);
                        } catch (Throwable e2) {
                            H5Log.e(H5OCRPlugin.TAG, "ocr...e=" + e2);
                            H5OCRPlugin.b(h5BridgeContext);
                        }
                    }
                });
            }
        } else {
            H5Log.d(TAG, "ocr... has value for imageBase64 but not string");
            context.sendError(event, Error.INVALID_PARAM);
        }
    }

    private APGenericProgressDialog a() {
        try {
            if (!(this.d == null || this.d.getContext() == null || this.d.getContext().getContext() == null)) {
                APGenericProgressDialog apGenericProgressDialog = new APGenericProgressDialog(this.d.getContext().getContext());
                apGenericProgressDialog.setCancelable(true);
                apGenericProgressDialog.setCanceledOnTouchOutside(false);
                apGenericProgressDialog.setMessage("识别中...");
                apGenericProgressDialog.setProgressVisiable(true);
                apGenericProgressDialog.show();
                return apGenericProgressDialog;
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "showLoadingDialog...e=" + e);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void a(final APGenericProgressDialog apGenericProgressDialog) {
        if (apGenericProgressDialog != null) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    try {
                        apGenericProgressDialog.dismiss();
                    } catch (Throwable throwable) {
                        H5Log.e(H5OCRPlugin.TAG, throwable);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(String path, String side, String ocrType, H5Event event, H5BridgeContext context) {
        try {
            a(TinyappUtils.transferApPathToLocalPath(path), (InputStream) null, side, ocrType, event, context);
        } catch (Exception e) {
            H5Log.e(TAG, "doOCRFromApPath...e=" + e);
            b(context);
        }
    }

    /* access modifiers changed from: private */
    public void b(String path, String side, String ocrType, H5Event event, H5BridgeContext context) {
        String realPath = a(path, this.d.getParams());
        H5Log.d(TAG, "ocr realPath " + realPath);
        final H5BridgeContext h5BridgeContext = context;
        final String str = side;
        final String str2 = ocrType;
        final H5Event h5Event = event;
        a(realPath, (ResponseListen) new ResponseListen() {
            public void onGetResponse(WebResourceResponse webResourceResponse) {
                if (webResourceResponse == null) {
                    H5Log.e(H5OCRPlugin.TAG, (String) "ocr getImageInfoFromPkg .. webResourceResponse is null");
                    h5BridgeContext.sendError(1006, (String) "读取图片内容失败");
                    return;
                }
                try {
                    InputStream inputStream = webResourceResponse.getData();
                    inputStream.reset();
                    H5OCRPlugin.this.a((String) null, inputStream, str, str2, h5Event, h5BridgeContext);
                } catch (Exception e2) {
                    H5Log.e(H5OCRPlugin.TAG, "ocr getImageInfoFromPkg .. e=" + e2);
                    H5OCRPlugin.b(h5BridgeContext);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.lang.String r27, java.lang.String r28, java.lang.String r29, java.lang.String r30, com.alipay.mobile.h5container.api.H5Event r31, com.alipay.mobile.h5container.api.H5BridgeContext r32) {
        /*
            r26 = this;
            r16 = 0
            r22 = 0
            java.lang.String r25 = com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter.STATUS_SUCCESS     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r10 = "0"
            com.alipay.mobile.h5container.api.H5CoreNode r3 = r31.getTarget()     // Catch:{ Throwable -> 0x00a7 }
            boolean r3 = r3 instanceof com.alipay.mobile.h5container.api.H5Page     // Catch:{ Throwable -> 0x00a7 }
            if (r3 == 0) goto L_0x002a
            com.alipay.mobile.h5container.api.H5CoreNode r18 = r31.getTarget()     // Catch:{ Throwable -> 0x00a7 }
            com.alipay.mobile.h5container.api.H5Page r18 = (com.alipay.mobile.h5container.api.H5Page) r18     // Catch:{ Throwable -> 0x00a7 }
            android.os.Bundle r3 = r18.getParams()     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r4 = "appId"
            java.lang.String r16 = com.alipay.mobile.nebula.util.H5Utils.getString(r3, r4)     // Catch:{ Throwable -> 0x00a7 }
            android.os.Bundle r3 = r18.getParams()     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r4 = "u"
            java.lang.String r22 = com.alipay.mobile.nebula.util.H5Utils.getString(r3, r4)     // Catch:{ Throwable -> 0x00a7 }
        L_0x002a:
            boolean r3 = android.text.TextUtils.isEmpty(r16)     // Catch:{ Throwable -> 0x00a7 }
            if (r3 == 0) goto L_0x0041
            java.lang.String r3 = TAG     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r4 = "doOCRRpc...current appId is null"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)     // Catch:{ Throwable -> 0x00a7 }
            com.alipay.mobile.h5container.api.H5Event$Error r3 = com.alipay.mobile.h5container.api.H5Event.Error.INVALID_PARAM     // Catch:{ Throwable -> 0x00a7 }
            r0 = r32
            r1 = r31
            r0.sendError(r1, r3)     // Catch:{ Throwable -> 0x00a7 }
        L_0x0040:
            return
        L_0x0041:
            java.lang.String r2 = "alipay.mmtcapi.recoOCR"
            com.alibaba.fastjson.JSONObject r24 = new com.alibaba.fastjson.JSONObject     // Catch:{ Throwable -> 0x00a7 }
            r24.<init>()     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r3 = "url"
            r0 = r24
            r1 = r27
            r0.put(r3, r1)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r3 = "appId"
            r0 = r24
            r1 = r16
            r0.put(r3, r1)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r3 = "side"
            r0 = r24
            r1 = r29
            r0.put(r3, r1)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r3 = "ocrType"
            r0 = r24
            r1 = r30
            r0.put(r3, r1)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r3 = "imageBase64"
            r0 = r24
            r1 = r28
            r0.put(r3, r1)     // Catch:{ Throwable -> 0x00a7 }
            com.alibaba.fastjson.JSONArray r20 = new com.alibaba.fastjson.JSONArray     // Catch:{ Throwable -> 0x00a7 }
            r20.<init>()     // Catch:{ Throwable -> 0x00a7 }
            r0 = r20
            r1 = r24
            r0.add(r1)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r3 = r20.toString()     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r4 = ""
            r5 = 0
            com.alibaba.fastjson.JSONObject r6 = new com.alibaba.fastjson.JSONObject     // Catch:{ Throwable -> 0x00a7 }
            r6.<init>()     // Catch:{ Throwable -> 0x00a7 }
            r7 = 0
            com.alipay.mobile.h5container.api.H5Page r8 = r31.getH5page()     // Catch:{ Throwable -> 0x00a7 }
            r9 = 0
            com.alipay.mobile.tinyappcustom.b.a r19 = com.alipay.mobile.tinyappcustom.b.c.a(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x00a7 }
            if (r19 == 0) goto L_0x00a3
            java.lang.String r3 = r19.b()     // Catch:{ Throwable -> 0x00a7 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x00a7 }
            if (r3 == 0) goto L_0x00c3
        L_0x00a3:
            b(r32)     // Catch:{ Throwable -> 0x00a7 }
            goto L_0x0040
        L_0x00a7:
            r17 = move-exception
            java.lang.String r3 = TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "doOCRRpc...e="
            r4.<init>(r5)
            r0 = r17
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            com.alipay.mobile.nebula.util.H5Log.e(r3, r4)
            b(r32)
            goto L_0x0040
        L_0x00c3:
            java.lang.String r3 = r19.b()     // Catch:{ Exception -> 0x00d2 }
            com.alibaba.fastjson.JSONObject r23 = com.alibaba.fastjson.JSONObject.parseObject(r3)     // Catch:{ Exception -> 0x00d2 }
            if (r23 != 0) goto L_0x010a
            b(r32)     // Catch:{ Exception -> 0x00d2 }
            goto L_0x0040
        L_0x00d2:
            r3 = move-exception
            r9 = r25
        L_0x00d5:
            r3 = 1004(0x3ec, float:1.407E-42)
            java.lang.String r4 = r19.b()     // Catch:{ Throwable -> 0x00a7 }
            r0 = r32
            r0.sendError(r3, r4)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r9 = com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter.STATUS_FAIL     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r10 = "1004"
        L_0x00e4:
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00a7 }
            r0 = r26
            long r6 = r0.b     // Catch:{ Throwable -> 0x00a7 }
            long r4 = r4 - r6
            java.lang.String r11 = java.lang.String.valueOf(r4)     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r3 = com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter.TYPE_OCR     // Catch:{ Throwable -> 0x00a7 }
            java.lang.String r6 = ""
            java.lang.String r7 = ""
            java.lang.String r8 = ""
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            java.lang.String r14 = ""
            java.lang.String r15 = ""
            r4 = r16
            r5 = r22
            com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter.report(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)     // Catch:{ Throwable -> 0x00a7 }
            goto L_0x0040
        L_0x010a:
            java.lang.String r3 = TAG     // Catch:{ Exception -> 0x00d2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r5 = "doOCRRpc result ="
            r4.<init>(r5)     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r5 = r23.toJSONString()     // Catch:{ Exception -> 0x00d2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00d2 }
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r3 = "success"
            r0 = r23
            java.lang.Boolean r3 = r0.getBoolean(r3)     // Catch:{ Exception -> 0x00d2 }
            boolean r3 = r3.booleanValue()     // Catch:{ Exception -> 0x00d2 }
            if (r3 == 0) goto L_0x016a
            java.lang.String r3 = "result"
            r0 = r23
            java.lang.String r3 = r0.getString(r3)     // Catch:{ Exception -> 0x00d2 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x00d2 }
            if (r3 != 0) goto L_0x016a
            com.alibaba.fastjson.JSONObject r21 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x00d2 }
            r21.<init>()     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r3 = "ocrType"
            r0 = r21
            r1 = r30
            r0.put(r3, r1)     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r3 = "result"
            java.lang.String r4 = "result"
            r0 = r23
            java.lang.String r4 = r0.getString(r4)     // Catch:{ Exception -> 0x00d2 }
            com.alibaba.fastjson.JSONObject r4 = com.alibaba.fastjson.JSONObject.parseObject(r4)     // Catch:{ Exception -> 0x00d2 }
            r0 = r21
            r0.put(r3, r4)     // Catch:{ Exception -> 0x00d2 }
            r0 = r32
            r1 = r21
            r0.sendBridgeResult(r1)     // Catch:{ Exception -> 0x00d2 }
            r9 = r25
            goto L_0x00e4
        L_0x016a:
            r3 = 1004(0x3ec, float:1.407E-42)
            java.lang.String r4 = "errorMessage"
            r0 = r23
            java.lang.String r4 = r0.getString(r4)     // Catch:{ Exception -> 0x00d2 }
            r0 = r32
            r0.sendError(r3, r4)     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r9 = com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter.STATUS_FAIL     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r10 = "1004"
            goto L_0x00e4
        L_0x017f:
            r3 = move-exception
            goto L_0x00d5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcustom.h5plugin.H5OCRPlugin.a(java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.alipay.mobile.h5container.api.H5Event, com.alipay.mobile.h5container.api.H5BridgeContext):void");
    }

    private static String a(String oriUrl, Bundle startParams) {
        String entryUrl = H5Utils.getString(startParams, (String) "url");
        if (!TextUtils.isEmpty(entryUrl)) {
            return H5Utils.getAbsoluteUrlV2(entryUrl, oriUrl, null);
        }
        return null;
    }

    private void a(String url, ResponseListen listener) {
        H5Log.d(TAG, "getImageInfoFromPkg...url=" + url);
        if (this.d != null) {
            H5Session h5Session = this.d.getSession();
            if (h5Session != null) {
                H5ContentProvider h5ContentProvider = h5Session.getWebProvider();
                if (h5ContentProvider != null) {
                    h5ContentProvider.getContent(url, listener);
                } else {
                    listener.onGetResponse(null);
                }
            } else {
                listener.onGetResponse(null);
            }
        } else {
            listener.onGetResponse(null);
        }
    }

    /* access modifiers changed from: private */
    public void c(String url, String side, String ocrType, H5Event event, H5BridgeContext context) {
        Header[] allHeaders;
        if (!TinyappUtils.checkSuffixSupported(url)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "error", (Object) Integer.valueOf(1005));
            jsonObject.put((String) "errorMessage", (Object) "不支持的图片地址后缀名");
            context.sendBridgeResult(jsonObject);
            return;
        }
        try {
            H5NetworkManager h5NetworkManager = new H5NetworkManager(H5Utils.getContext());
            H5HttpUrlRequest h5HttpUrlRequest = new H5HttpUrlRequest(url);
            r0 = "GET";
            h5HttpUrlRequest.setRequestMethod("GET");
            h5HttpUrlRequest.setReqData(null);
            h5HttpUrlRequest.setTimeout(60000);
            h5HttpUrlRequest.linkType = 2;
            String cookieStr = H5CookieUtil.getCookie(url);
            if (!TextUtils.isEmpty(cookieStr)) {
                h5HttpUrlRequest.addHeader("Cookie", cookieStr);
                H5Log.d(TAG, "ocr add cookie:" + cookieStr + " , for h5HttpUrlRequest");
            }
            H5HttpUrlResponse httpUrlResponse = (H5HttpUrlResponse) h5NetworkManager.enqueue(h5HttpUrlRequest).get();
            if (httpUrlResponse == null || httpUrlResponse.getHeader() == null) {
                context.sendError(1008, (String) "图片下载失败");
                return;
            }
            for (Header header : httpUrlResponse.getHeader().getAllHeaders()) {
                String headerName = header.getName();
                if (headerName != null) {
                    String headerValue = header.getValue();
                    H5Log.d(TAG, "ocr name:" + headerName + " - value:" + headerValue);
                    if (headerName.equalsIgnoreCase("set-cookie")) {
                        H5CookieUtil.setCookie(url, headerValue);
                        H5Log.d(TAG, "insert cookie:" + headerValue + " , for " + url);
                    }
                }
            }
            InputStream inputStream = httpUrlResponse.getInputStream();
            a((String) null, inputStream, side, ocrType, event, context);
            inputStream.close();
        } catch (Exception e) {
            H5Log.e(TAG, "ocr...download=" + e);
            if (e.getCause() == null || !(e.getCause() instanceof HttpException)) {
                b(context);
                return;
            }
            H5Log.e(TAG, "ocr...e=" + e);
            context.sendError(1008, (String) "图片下载失败");
        }
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context) {
        context.sendError(1003, (String) "OCR 失败");
    }

    /* access modifiers changed from: private */
    public void a(String path, InputStream inputStream, String side, String ocrType, H5Event event, H5BridgeContext context) {
        try {
            APImageUploadOption option = new APImageUploadOption();
            option.setQua(QUALITITY.ORIGINAL);
            option.setPublic = Boolean.valueOf(false);
            APImageUpRequest req = new APImageUpRequest();
            req.option = option;
            if (TextUtils.isEmpty(path)) {
                byte[] imageBytes = TinyappUtils.toByteArray(inputStream, false);
                Bitmap bitmap = a(imageBytes);
                req.height = bitmap.getHeight();
                req.width = bitmap.getWidth();
                req.fileData = imageBytes;
            } else {
                req.path = TinyappUtils.transferApPathToLocalPath(path);
            }
            final H5BridgeContext h5BridgeContext = context;
            final String str = side;
            final String str2 = ocrType;
            final H5Event h5Event = event;
            req.callback = new APImageUploadCallback() {
                public void onCompressSucc(Drawable drawable) {
                }

                public void onStartUpload(APMultimediaTaskModel taskModel) {
                }

                public void onProcess(APMultimediaTaskModel task, int percentage) {
                }

                public void onSuccess(APImageUploadRsp apImageUploadRsp) {
                    String multimediaID = (apImageUploadRsp == null || apImageUploadRsp.getTaskStatus() == null || apImageUploadRsp.getTaskStatus().getCloudId() == null) ? "" : apImageUploadRsp.getTaskStatus().getCloudId();
                    String url = (apImageUploadRsp == null || TextUtils.isEmpty(apImageUploadRsp.getPublicUrl())) ? "" : apImageUploadRsp.getPublicUrl();
                    long totalSize = (apImageUploadRsp == null || apImageUploadRsp.getTaskStatus() == null) ? 0 : apImageUploadRsp.getTaskStatus().getTotalSize();
                    H5Log.d(H5OCRPlugin.TAG, "ocr...multimediaID:" + multimediaID + ", url: " + url + " totalSize = " + totalSize);
                    if (totalSize > 1048576) {
                        h5BridgeContext.sendError(1002, (String) "请检查图片大小是否超过api限制");
                    } else {
                        H5OCRPlugin.this.a(multimediaID, (String) null, str, str2, h5Event, h5BridgeContext);
                    }
                }

                public void onError(APImageUploadRsp apImageUploadRsp, Exception e2) {
                    H5Log.d(H5OCRPlugin.TAG, "ocr..onError: " + e2);
                    if (h5BridgeContext != null) {
                        JSONObject jsonObject = new JSONObject();
                        if (!(apImageUploadRsp == null || apImageUploadRsp.getRetmsg() == null)) {
                            jsonObject.put((String) "error", (Object) Integer.valueOf(1007));
                            jsonObject.put((String) "errorMessage", (Object) "图片上传失败");
                        }
                        h5BridgeContext.sendBridgeResult(jsonObject);
                    }
                }
            };
            ((MultimediaImageService) H5Utils.findServiceByInterface(MultimediaImageService.class.getName())).uploadImage(req, (String) "multiMedia");
        } catch (Throwable e) {
            H5Log.e(TAG, "ocr...e=" + e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "error", (Object) Integer.valueOf(1007));
            jsonObject.put((String) "errorMessage", (Object) "图片上传失败");
            context.sendBridgeResult(jsonObject);
        }
    }

    private static Bitmap a(byte[] imgByte) {
        Options options = new Options();
        options.inSampleSize = 8;
        InputStream input = new ByteArrayInputStream(imgByte);
        Bitmap bitmap = (Bitmap) new SoftReference(BitmapFactory.decodeStream(input, null, options)).get();
        try {
            input.close();
        } catch (IOException e) {
            H5Log.e(TAG, "byteToBitmap...e=" + e);
        }
        return bitmap;
    }

    /* access modifiers changed from: private */
    public static boolean b(String imageBase64) {
        H5Log.d(TAG, "ocr isImageOverSize ..  imageBase64 isEmpty = " + TextUtils.isEmpty(imageBase64));
        if (TextUtils.isEmpty(imageBase64)) {
            return false;
        }
        try {
            byte[] imageBytes = Base64.decode(imageBase64, 0);
            if (imageBytes != null && imageBytes.length > 1048576) {
                return true;
            }
            H5Log.d(TAG, "ocr isImageOverSize ..  not over size ");
            return false;
        } catch (Exception e) {
            H5Log.e(TAG, "ocr isImageOverSize ..  error", e);
            return false;
        }
    }
}
