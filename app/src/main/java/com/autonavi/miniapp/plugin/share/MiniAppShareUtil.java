package com.autonavi.miniapp.plugin.share;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MiniAppShareUtil {
    /* access modifiers changed from: private */
    public static final Map<Integer, String> AMAP_MINIAPP_SHARE_TYPE_MAPPING;
    private static final String BIZ_TYPE = "H5App_DD";
    private static final String COPY_LINK = "CopyLink";
    private static final String DING_DING = "DingTalkSession";
    private static final String SHARE_H5_PAGE_PREFIX = "http://wap.amap.com/callnative/?schema=";
    private static final String WEIXIN_FRIEND = "Weixin";
    private static final String WEIXIN_FRIEND_CIRCLE = "WeixinTimeLine";
    /* access modifiers changed from: private */
    public static boolean mCancelTask = false;
    private static ProgressDlg mProgressDialog;

    static class MiniAppShareStatusCallback extends dcd {
        private String mContent;
        private String mDescription;
        private H5BridgeContext mH5BridgeContext;
        private String mImgUrl;
        private Bitmap mThumbBitmap;
        private String mTitle;
        private String mUrl;

        public void onEntrySelected(int i) {
        }

        public MiniAppShareStatusCallback(String str, String str2, String str3, String str4, String str5, Bitmap bitmap, H5BridgeContext h5BridgeContext) {
            this.mUrl = str;
            this.mTitle = str2;
            this.mDescription = str3;
            if (TextUtils.isEmpty(str3)) {
                str3 = AMapAppGlobal.getApplication().getString(R.string.miniapp_share_guide);
            }
            this.mContent = str3;
            this.mImgUrl = str5;
            this.mH5BridgeContext = h5BridgeContext;
            this.mThumbBitmap = bitmap;
        }

        public ShareParam getShareDataByType(int i) {
            if (i != 11) {
                switch (i) {
                    case 3:
                        e eVar = new e(0);
                        eVar.f = this.mTitle;
                        eVar.a = this.mContent;
                        eVar.g = this.mThumbBitmap;
                        eVar.b = this.mUrl;
                        eVar.c = false;
                        return eVar;
                    case 4:
                        e eVar2 = new e(1);
                        eVar2.b = this.mUrl;
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mTitle);
                        sb.append(" (");
                        sb.append(AMapAppGlobal.getApplication().getString(R.string.miniapp));
                        sb.append(")");
                        eVar2.f = sb.toString();
                        eVar2.a = this.mContent;
                        eVar2.g = this.mThumbBitmap;
                        eVar2.c = false;
                        return eVar2;
                    default:
                        return null;
                }
            } else {
                DingDingParam dingDingParam = new DingDingParam();
                dingDingParam.f = this.mTitle;
                dingDingParam.a = this.mContent;
                if (this.mThumbBitmap != null) {
                    dingDingParam.g = this.mThumbBitmap;
                } else {
                    dingDingParam.h = this.mImgUrl;
                }
                dingDingParam.b = this.mUrl;
                dingDingParam.c = false;
                return dingDingParam;
            }
        }

        public void onFinish(int i, int i2) {
            boolean z = i2 == 0;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "channelName", MiniAppShareUtil.AMAP_MINIAPP_SHARE_TYPE_MAPPING.get(Integer.valueOf(i)));
            jSONObject.put((String) "shareResult", (Object) Boolean.valueOf(z));
            jSONObject.put((String) "bizType", (Object) MiniAppShareUtil.BIZ_TYPE);
            if (!z) {
                jSONObject.put((String) "error", (Object) Integer.valueOf(10));
            }
            if (this.mH5BridgeContext != null) {
                this.mH5BridgeContext.sendBridgeResult(jSONObject);
            }
        }
    }

    static {
        HashMap hashMap = new HashMap();
        AMAP_MINIAPP_SHARE_TYPE_MAPPING = hashMap;
        hashMap.put(Integer.valueOf(3), WEIXIN_FRIEND);
        AMAP_MINIAPP_SHARE_TYPE_MAPPING.put(Integer.valueOf(4), WEIXIN_FRIEND_CIRCLE);
        AMAP_MINIAPP_SHARE_TYPE_MAPPING.put(Integer.valueOf(6), COPY_LINK);
        AMAP_MINIAPP_SHARE_TYPE_MAPPING.put(Integer.valueOf(11), DING_DING);
    }

    public static String createShareUrl(String str) {
        StringBuilder sb = new StringBuilder(SHARE_H5_PAGE_PREFIX);
        sb.append(URLEncoder.encode(str));
        return sb.toString();
    }

    public static void startShare(String str, String str2, String str3, String str4, String str5, H5Page h5Page, Context context, H5BridgeContext h5BridgeContext) {
        final dct dct = new dct(0);
        dct.d = true;
        dct.e = true;
        dct.l = true;
        dct.g = true;
        dct.h = true;
        if (isUIThread()) {
            shareTraceView(context, dct, str, str2, str3, str4, str5, h5BridgeContext);
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        final Context context2 = context;
        final String str6 = str;
        final String str7 = str2;
        final String str8 = str3;
        final String str9 = str4;
        final String str10 = str5;
        final H5BridgeContext h5BridgeContext2 = h5BridgeContext;
        AnonymousClass1 r0 = new Runnable() {
            public final void run() {
                MiniAppShareUtil.shareTraceView(context2, dct, str6, str7, str8, str9, str10, h5BridgeContext2);
            }
        };
        handler.post(r0);
    }

    private static boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /* access modifiers changed from: private */
    @MainThread
    public static void shareTraceView(Context context, dct dct, String str, String str2, String str3, String str4, String str5, H5BridgeContext h5BridgeContext) {
        if (!aaw.c(AMapPageUtil.getAppContext())) {
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_msg));
        } else if (!TextUtils.isEmpty(str5)) {
            showProgressDialog((Activity) context, "获取分享内容中..");
            final String str6 = str5;
            final Context context2 = context;
            final dct dct2 = dct;
            final String str7 = str;
            final String str8 = str2;
            final String str9 = str3;
            final String str10 = str4;
            final H5BridgeContext h5BridgeContext2 = h5BridgeContext;
            AnonymousClass2 r1 = new Runnable() {
                public final void run() {
                    final boolean z;
                    final Bitmap bitmap;
                    try {
                        bitmap = MiniAppShareUtil.getThumbBitmapForWechat(str6);
                        z = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        z = true;
                        bitmap = null;
                    }
                    aho.a(new Runnable() {
                        public void run() {
                            MiniAppShareUtil.dismissProgressDialog();
                            if (MiniAppShareUtil.mCancelTask) {
                                MiniAppShareUtil.mCancelTask = false;
                                if (bitmap != null && !bitmap.isRecycled()) {
                                    bitmap.recycle();
                                }
                            } else if (!z) {
                                MiniAppShareUtil.callShareAgent(context2, dct2, str7, str8, str9, str10, str6, bitmap, h5BridgeContext2);
                            }
                        }
                    });
                }
            };
            ahm.a(r1);
        } else {
            callShareAgent(context, dct, str, str2, str3, str4, str5, null, h5BridgeContext);
        }
    }

    /* access modifiers changed from: private */
    public static Bitmap getThumbBitmapForWechat(String str) throws Exception {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return getBitmap(str, getBytesFromUrl(str).length > 32768);
        } catch (Exception e) {
            throw e;
        }
    }

    private static Bitmap getBitmap(String str, boolean z) throws IOException {
        if (!z) {
            return BitmapFactory.decodeStream(new URL(str).openStream(), null, null);
        }
        Options options = new Options();
        options.inSampleSize = 2;
        options.inPreferredConfig = Config.ARGB_4444;
        return BitmapFactory.decodeStream(new URL(str).openStream(), null, options);
    }

    /* access modifiers changed from: private */
    public static void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private static void showProgressDialog(Activity activity, String str) {
        try {
            if (mProgressDialog == null) {
                ProgressDlg progressDlg = new ProgressDlg(activity, str, "");
                mProgressDialog = progressDlg;
                progressDlg.setCancelable(true);
                mProgressDialog.setOnCancelListener(new OnCancelListener() {
                    public final void onCancel(DialogInterface dialogInterface) {
                        MiniAppShareUtil.mCancelTask = true;
                    }
                });
            }
            mCancelTask = false;
            mProgressDialog.setMessage(str);
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream getRequest(String str) throws Exception {
        URL url = new URL(str);
        Proxy a = ahi.a(true);
        HttpURLConnection httpURLConnection = (HttpURLConnection) (a == null ? url.openConnection() : url.openConnection(a));
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(5000);
        if (httpURLConnection.getResponseCode() == 200) {
            return httpURLConnection.getInputStream();
        }
        return null;
    }

    private static byte[] readInputStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                byteArrayOutputStream.close();
                inputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static byte[] getBytesFromUrl(String str) throws Exception {
        return readInputStream(getRequest(str));
    }

    /* access modifiers changed from: private */
    public static void callShareAgent(Context context, dct dct, String str, String str2, String str3, String str4, String str5, Bitmap bitmap, H5BridgeContext h5BridgeContext) {
        dcb dcb = (dcb) a.a.a(dcb.class);
        if (dcb != null) {
            MiniAppShareStatusCallback miniAppShareStatusCallback = new MiniAppShareStatusCallback(str, str2, str3, str4, str5, bitmap, h5BridgeContext);
            dcb.a(context, dct, (dcd) miniAppShareStatusCallback);
        }
    }
}
