package com.jiuyan.inimage.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.http.impl.EncryptCore;
import com.jiuyan.inimage.http.impl.HttpCore;
import com.jiuyan.inimage.http.impl.JsonCore;
import com.jiuyan.inimage.http.interfaces.OnCompleteListener;
import com.jiuyan.inimage.http.interfaces.OnRequestCompleteListener;
import com.jiuyan.inimage.http.utils.CommonUtils;
import com.taobao.accs.common.Constants;

public class HttpLauncher {
    private static final int CODE_COMPLETE = 1;
    private static final int CODE_FAILED = 2;
    private static final int ERROR_CODE_JSON_PARSE = 1001;
    private static final String KEY_DECARG = "_dcarg";
    public static final int METHOD_GET = 0;
    public static final int METHOD_POST = 1;
    private Context mContext;
    private EncryptCore mEncryptCore;
    /* access modifiers changed from: private */
    public UIHandler mHandler = new UIHandler();
    private HttpCore mHttpCore;
    /* access modifiers changed from: private */
    public JsonCore mJsonCore;
    private int mMethod;

    class UIHandler extends Handler {
        public OnCompleteListener mOnCompleteListener;

        public UIHandler() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
            this.mOnCompleteListener = onCompleteListener;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.mOnCompleteListener.onSuccess(message.obj);
                    return;
                case 2:
                    this.mOnCompleteListener.onFailed(((Integer) message.obj).intValue());
                    return;
                default:
                    return;
            }
        }
    }

    public HttpLauncher(Context context, int i, String str, String str2) {
        this.mContext = context;
        this.mMethod = i;
        this.mHttpCore = new HttpCore(i, str, str2);
        this.mJsonCore = new JsonCore();
        this.mEncryptCore = new EncryptCore(i);
        putParamCommon();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.mHandler.setOnCompleteListener(onCompleteListener);
    }

    public void putParam(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            String trim = str.trim();
            String trim2 = str2.trim();
            this.mEncryptCore.putEncrypt(trim, trim2);
            if (this.mMethod == 1) {
                this.mHttpCore.putParam(trim, trim2);
            }
        }
    }

    private void putParamCommon() {
        String str;
        try {
            str = CommonUtils.getCurrentNetType(this.mContext);
            if (!"WIFI".equals(str)) {
                str = str + "," + CommonUtils.getMoreInfo(this.mContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
            str = "";
        }
        this.mEncryptCore.putEncrypt("_res", CommonUtils.getScreenWidth(this.mContext) + "");
        this.mEncryptCore.putEncrypt("_osv", CommonUtils.getOSV() + "");
        this.mEncryptCore.putEncrypt(Constants.KEY_MODEL, CommonUtils.getDeviceModel());
        this.mEncryptCore.putEncrypt("_s", "android");
        this.mEncryptCore.putEncrypt("_n", str);
        this.mEncryptCore.putEncrypt("_udid", CommonUtils.getAID(this.mContext) + "");
        this.mEncryptCore.putEncrypt("_gps", "");
        this.mEncryptCore.putEncrypt("_v", "");
    }

    private void generateParam() {
        String fetchDcarg = this.mEncryptCore.fetchDcarg();
        switch (this.mMethod) {
            case 0:
                this.mHttpCore.putParam(KEY_DECARG, fetchDcarg);
                return;
            case 1:
                this.mHttpCore.buildUrl(KEY_DECARG, fetchDcarg);
                return;
            default:
                return;
        }
    }

    public void execute(final Class cls) {
        generateParam();
        this.mHttpCore.execute(new OnRequestCompleteListener() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void onSuccess(byte[] bArr) {
                if (bArr == null) {
                    Message obtain = Message.obtain();
                    obtain.what = 2;
                    obtain.obj = Integer.valueOf(1001);
                    HttpLauncher.this.mHandler.sendMessage(obtain);
                    return;
                }
                String str = new String(bArr);
                Message obtain2 = Message.obtain();
                Object parse = HttpLauncher.this.mJsonCore.parse(str, cls);
                if (parse != null) {
                    obtain2.what = 1;
                    obtain2.obj = parse;
                } else {
                    obtain2.what = 2;
                    obtain2.obj = Integer.valueOf(1001);
                }
                HttpLauncher.this.mHandler.sendMessage(obtain2);
            }

            public void onFailed(int i) {
                Message obtain = Message.obtain();
                obtain.what = 2;
                obtain.obj = Integer.valueOf(i);
                HttpLauncher.this.mHandler.sendMessage(obtain);
            }
        });
    }
}
