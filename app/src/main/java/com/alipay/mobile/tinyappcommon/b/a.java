package com.alipay.mobile.tinyappcommon.b;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.android.phone.mobilesdk.socketcraft.util.Base64;
import com.alipay.mobile.common.share.QRCodeShareInterceptor;
import com.alipay.mobile.common.share.ShareContent;
import com.alipay.mobile.framework.app.AppLoadException;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.callback.H5SimpleRpcListener;
import com.alipay.mobile.nebula.provider.H5SimpleRpcProvider;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/* compiled from: TinyAppQRCodeShareWrapper */
public final class a implements QRCodeShareInterceptor {
    private QRCodeShareInterceptor a;

    /* renamed from: com.alipay.mobile.tinyappcommon.b.a$a reason: collision with other inner class name */
    /* compiled from: TinyAppQRCodeShareWrapper */
    interface C0100a<T> {
        void a(T t);
    }

    public a() {
        this(null);
    }

    public a(QRCodeShareInterceptor baseQRCodeShare) {
        this.a = baseQRCodeShare;
    }

    public final void shareQRcode(ShareContent shareContent, String bizType) {
        if ("H5App_XCX".equals(bizType) || "20000067".equals(bizType)) {
            String iconUrl = shareContent.getIconUrl();
            HashMap extraInfo = shareContent.getExtraInfo();
            if (extraInfo != null) {
                String extraIconUrl = (String) extraInfo.get(H5AppHandler.sAppIcon);
                if (!TextUtils.isEmpty(extraIconUrl)) {
                    iconUrl = extraIconUrl;
                }
            }
            a(shareContent, shareContent.getUrl(), iconUrl);
        } else if (this.a != null) {
            this.a.shareQRcode(shareContent, bizType);
        }
    }

    private void a(final ShareContent shareContent, final String url, final String iconUrl) {
        final C0100a makeQRCodeCallback = new C0100a<String>() {
            /* access modifiers changed from: private */
            public void a(String data) {
                if (TextUtils.isEmpty(data)) {
                    H5Log.e((String) "TinyAppQRCodeShare", (String) "make qr code error");
                } else {
                    a.b(shareContent, url, iconUrl, data);
                }
            }
        };
        if (!TextUtils.isEmpty(iconUrl)) {
            a(iconUrl, new C0100a<String>() {
                /* access modifiers changed from: private */
                public void a(String data) {
                    a.this.a(url, data, makeQRCodeCallback);
                }
            });
        } else {
            a(url, (String) "", makeQRCodeCallback);
        }
    }

    /* access modifiers changed from: private */
    public static void b(ShareContent shareContent, String scheme, String iconUrl, String qrCodeUrl) {
        String bgImgUrl = "";
        boolean useMergeService = false;
        JSONObject extraParams = new JSONObject();
        HashMap extraInfo = shareContent.getExtraInfo();
        if (extraInfo != null) {
            bgImgUrl = (String) extraInfo.get("bgImgUrl");
            useMergeService = Boolean.TRUE.equals(extraInfo.get("useMergeService"));
            try {
                extraParams = (JSONObject) extraInfo.get("extraParams");
            } catch (Exception e) {
                H5Log.e((String) "TinyAppQRCodeShare", (Throwable) e);
            }
        }
        JSONObject extraData = new JSONObject();
        extraData.put((String) "shareTitle", (Object) shareContent.getTitle());
        extraData.put((String) "shareDesc", (Object) shareContent.getContent());
        extraData.put((String) "shareQRCodeUrl", (Object) qrCodeUrl);
        extraData.put((String) "shareBgViewUrl", (Object) bgImgUrl);
        extraData.put((String) "shareScheme", (Object) scheme);
        extraData.put((String) "shareIconUrl", (Object) iconUrl);
        extraData.put((String) "useMergeService", (Object) Boolean.valueOf(useMergeService));
        String templateType = H5Utils.getString(extraParams, (String) "templateType");
        extraData.put((String) "shareTemplateType", (Object) templateType);
        if (TextUtils.equals(templateType, "1")) {
            extraData.put((String) "shareDesc", (Object) H5Utils.getString(extraParams, (String) "templateDesc"));
        }
        extraData.put((String) "userShareDesc", (Object) H5Utils.getString(extraParams, (String) "templateDesc"));
        extraData.put((String) "userNickName", (Object) H5Utils.getString(extraParams, (String) "templateUserName"));
        extraData.put((String) "userAvatar", (Object) H5Utils.getString(extraParams, (String) "templateUserIcon"));
        extraData.put((String) "userShareDesc", (Object) H5Utils.getString(extraParams, (String) "templateUserDesc"));
        extraData.put((String) "userShareSecondDesc", (Object) H5Utils.getString(extraParams, (String) "templateUserSubDesc"));
        JSONObject referrerInfo = new JSONObject();
        referrerInfo.put((String) "appId", (Object) "77700109");
        referrerInfo.put((String) "extraData", (Object) extraData);
        JSONObject params = new JSONObject();
        params.put((String) "referrerInfo", (Object) referrerInfo.toString());
        try {
            WalletTinyappUtils.getMultiProcessService().a("77700109", "77700109", params, true);
        } catch (AppLoadException e2) {
            H5Log.e("TinyAppQRCodeShare", "start tiny app error", e2);
        }
    }

    /* access modifiers changed from: private */
    public void a(String url, String logoBase64, C0100a<String> callback) {
        H5SimpleRpcProvider h5SimpleRpcProvider = (H5SimpleRpcProvider) H5Utils.getProvider(H5SimpleRpcProvider.class.getName());
        if (h5SimpleRpcProvider == null) {
            callback.a("");
            return;
        }
        JSONObject contextData = new JSONObject();
        contextData.put((String) "url", (Object) url);
        JSONObject params = new JSONObject();
        params.put((String) GlobalConstants.CODE_TYPE, (Object) "qr_code");
        params.put((String) "bizType", (Object) "SPROGRAM_SHARE");
        params.put((String) "contextData", (Object) contextData.toString());
        if (!TextUtils.isEmpty(logoBase64)) {
            params.put((String) "logo", (Object) logoBase64);
        }
        JSONArray requestData = new JSONArray();
        requestData.add(params);
        final C0100a<String> aVar = callback;
        h5SimpleRpcProvider.sendSimpleRpc("alipay.mobilecodec.shakeCode.encode", requestData.toString(), null, true, null, null, false, null, new H5SimpleRpcListener() {
            public final void onSuccess(String s) {
                try {
                    JSONObject result = JSON.parseObject(s);
                    if (H5Utils.getBoolean(result, (String) "success", false)) {
                        String code = H5Utils.getString(result, (String) "code");
                        String dynamicImgUrl = H5Utils.getString(result, (String) "dynamicImgUrl");
                        H5Log.d("TinyAppQRCodeShare", "success code: " + code + " img: " + dynamicImgUrl);
                        aVar.a(dynamicImgUrl);
                        return;
                    }
                    aVar.a("");
                } catch (Exception e) {
                    H5Log.e("TinyAppQRCodeShare", "error", e);
                    aVar.a("");
                }
            }

            public final void onFailed(int i, String s) {
                aVar.a("");
            }
        });
    }

    private void a(final String iconUrl, final C0100a<String> callback) {
        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
            public final void run() {
                InputStream inputStream = null;
                ByteArrayOutputStream bos = null;
                try {
                    H5Log.d("TinyAppQRCodeShare", "load icon from net");
                    URLConnection conn = new URL(iconUrl).openConnection();
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    H5Log.d("TinyAppQRCodeShare", "load icon response length " + conn.getContentLength());
                    inputStream = conn.getInputStream();
                    byte[] buffer = new byte[1024];
                    ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                    while (true) {
                        try {
                            int count = inputStream.read(buffer);
                            if (count > 0) {
                                bos2.write(buffer, 0, count);
                            } else {
                                callback.a(Base64.encodeBytes(bos2.toByteArray()));
                                H5IOUtils.closeQuietly(inputStream);
                                H5IOUtils.closeQuietly(bos2);
                                ByteArrayOutputStream byteArrayOutputStream = bos2;
                                return;
                            }
                        } catch (Throwable th) {
                            th = th;
                            bos = bos2;
                            H5IOUtils.closeQuietly(inputStream);
                            H5IOUtils.closeQuietly(bos);
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    t = th2;
                    H5Log.e("TinyAppQRCodeShare", "load icon response exception", t);
                    callback.a("");
                    H5IOUtils.closeQuietly(inputStream);
                    H5IOUtils.closeQuietly(bos);
                }
            }
        });
    }
}
