package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Base64;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import org.json.JSONObject;

public final class d extends AsyncTask<Void, Void, a> {
    private static final String h;
    private static String i = "https://open.weixin.qq.com/connect/sdk/qrconnect?appid=%s&noncestr=%s&timestamp=%s&scope=%s&signature=%s";
    private String appId;
    private String j;
    private String k;
    private OAuthListener l;
    private f m;
    private String scope;
    private String signature;

    static class a {
        public OAuthErrCode n;
        public String o;
        public String p;
        public String q;
        public int r;
        public String s;
        public byte[] t;

        private a() {
        }

        public static a a(byte[] bArr) {
            OAuthErrCode oAuthErrCode;
            String str;
            String str2;
            Object[] objArr;
            a aVar = new a();
            if (bArr == null || bArr.length == 0) {
                Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, buf is null");
                oAuthErrCode = OAuthErrCode.WechatAuth_Err_NetworkErr;
            } else {
                try {
                } catch (Exception e) {
                    str = "MicroMsg.SDK.GetQRCodeResult";
                    str2 = "parse fail, build String fail, ex = %s";
                    objArr = new Object[]{e.getMessage()};
                    Log.e(str, String.format(str2, objArr));
                    oAuthErrCode = OAuthErrCode.WechatAuth_Err_NormalErr;
                    aVar.n = oAuthErrCode;
                    return aVar;
                }
                try {
                    JSONObject jSONObject = new JSONObject(new String(bArr, "utf-8"));
                    int i = jSONObject.getInt("errcode");
                    if (i != 0) {
                        Log.e("MicroMsg.SDK.GetQRCodeResult", String.format("resp errcode = %d", new Object[]{Integer.valueOf(i)}));
                        aVar.n = OAuthErrCode.WechatAuth_Err_NormalErr;
                        aVar.r = i;
                        aVar.s = jSONObject.optString("errmsg");
                        return aVar;
                    }
                    String string = jSONObject.getJSONObject("qrcode").getString("qrcodebase64");
                    if (string != null) {
                        if (string.length() != 0) {
                            byte[] decode = Base64.decode(string, 0);
                            if (decode != null) {
                                if (decode.length != 0) {
                                    aVar.n = OAuthErrCode.WechatAuth_Err_OK;
                                    aVar.t = decode;
                                    aVar.o = jSONObject.getString("uuid");
                                    aVar.p = jSONObject.getString("appname");
                                    Log.d("MicroMsg.SDK.GetQRCodeResult", String.format("parse succ, save in memory, uuid = %s, appname = %s, imgBufLength = %d", new Object[]{aVar.o, aVar.p, Integer.valueOf(aVar.t.length)}));
                                    return aVar;
                                }
                            }
                            Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBuf is null");
                            aVar.n = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
                            return aVar;
                        }
                    }
                    Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBase64 is null");
                    aVar.n = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
                    return aVar;
                } catch (Exception e2) {
                    str = "MicroMsg.SDK.GetQRCodeResult";
                    str2 = "parse json fail, ex = %s";
                    objArr = new Object[]{e2.getMessage()};
                    Log.e(str, String.format(str2, objArr));
                    oAuthErrCode = OAuthErrCode.WechatAuth_Err_NormalErr;
                    aVar.n = oAuthErrCode;
                    return aVar;
                }
            }
            aVar.n = oAuthErrCode;
            return aVar;
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/tencent/MicroMsg/oauth_qrcode.png");
        h = sb.toString();
    }

    public d(String str, String str2, String str3, String str4, String str5, OAuthListener oAuthListener) {
        this.appId = str;
        this.scope = str2;
        this.j = str3;
        this.k = str4;
        this.signature = str5;
        this.l = oAuthListener;
    }

    public final boolean a() {
        Log.i("MicroMsg.SDK.GetQRCodeTask", "cancelTask");
        return this.m == null ? cancel(true) : this.m.cancel(true);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object doInBackground(Object[] objArr) {
        Log.i("MicroMsg.SDK.GetQRCodeTask", "external storage available = false");
        String format = String.format(i, new Object[]{this.appId, this.j, this.k, this.scope, this.signature});
        long currentTimeMillis = System.currentTimeMillis();
        byte[] a2 = e.a(format);
        Log.d("MicroMsg.SDK.GetQRCodeTask", String.format("doInBackground, url = %s, time consumed = %d(ms)", new Object[]{format, Long.valueOf(System.currentTimeMillis() - currentTimeMillis)}));
        return a.a(a2);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        a aVar = (a) obj;
        if (aVar.n == OAuthErrCode.WechatAuth_Err_OK) {
            Log.d("MicroMsg.SDK.GetQRCodeTask", "onPostExecute, get qrcode success");
            this.l.onAuthGotQrcode(aVar.q, aVar.t);
            this.m = new f(aVar.o, this.l);
            f fVar = this.m;
            if (VERSION.SDK_INT >= 11) {
                fVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            } else {
                fVar.execute(new Void[0]);
            }
        } else {
            Log.e("MicroMsg.SDK.GetQRCodeTask", String.format("onPostExecute, get qrcode fail, OAuthErrCode = %s", new Object[]{aVar.n}));
            this.l.onAuthFinish(aVar.n, null);
        }
    }
}
