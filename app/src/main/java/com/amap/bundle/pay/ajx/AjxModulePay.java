package com.amap.bundle.pay.ajx;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.pay.payment.PayInfo;
import com.amap.bundle.pay.wechat.WechatScoreQuery;
import com.autonavi.gdtaojin.basemap.UiExecutor;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;

public class AjxModulePay extends AbstractModulePay {
    private static final String GROUP = "paas.pay";
    private static final String TAG = "ModulePay";
    /* access modifiers changed from: private */
    public boolean mIsDebug = false;

    public AjxModulePay(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void pay(String str, final String str2, final JsFunctionCallback jsFunctionCallback) {
        StringBuilder sb = new StringBuilder("pay() called with: type = [");
        sb.append(str);
        sb.append("], request = [");
        sb.append(str2);
        sb.append("], jsCallback = [");
        sb.append(jsFunctionCallback);
        sb.append("]");
        AMapLog.info(GROUP, str, sb.toString());
        int parseInt = parseInt(str);
        if (TextUtils.isEmpty(str2)) {
            StringBuilder sb2 = new StringBuilder("pay request error: type=");
            sb2.append(parseInt);
            sb2.append(", param=");
            sb2.append(str2);
            AMapLog.warning(GROUP, TAG, sb2.toString());
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new PayInfo(str2, PayInfo.CODE_PARAM_ERROR, "").toJson());
            }
            return;
        }
        final abt a = aby.a(parseInt, getNativeContext());
        if (a != null) {
            UiExecutor.post(new Runnable() {
                public final void run() {
                    a.setDebug(AjxModulePay.this.mIsDebug);
                    a.a(new PayInfo(str2), new abw() {
                        public final void a(PayInfo payInfo) {
                            String json = payInfo.toJson();
                            AMapLog.info(AjxModulePay.GROUP, AjxModulePay.TAG, "pay request result=".concat(String.valueOf(json)));
                            if (jsFunctionCallback != null) {
                                jsFunctionCallback.callback(json);
                            }
                        }
                    });
                }
            });
            return;
        }
        AMapLog.warning(GROUP, TAG, "pay unsupported ".concat(String.valueOf(parseInt)));
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(new PayInfo(str2, PayInfo.CODE_PAYMENT_UNSUPPORT, "unsupported").toJson());
        }
    }

    public void login(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        StringBuilder sb = new StringBuilder("login() called with: type = [");
        sb.append(str);
        sb.append("], request = [");
        sb.append(str2);
        sb.append("], jsCallback = [");
        sb.append(jsFunctionCallback);
        sb.append("]");
        AMapLog.info(GROUP, TAG, sb.toString());
        int parseInt = parseInt(str);
        final abs c = aby.c(parseInt, getNativeContext());
        if (c != null) {
            UiExecutor.post(new Runnable() {
                public final void run() {
                    c.setDebug(AjxModulePay.this.mIsDebug);
                    c.a(new abw() {
                        public final void a(PayInfo payInfo) {
                            String json = payInfo.toJson();
                            AMapLog.info(AjxModulePay.GROUP, AjxModulePay.TAG, "login request result=".concat(String.valueOf(json)));
                            if (jsFunctionCallback != null) {
                                jsFunctionCallback.callback(json);
                            }
                        }
                    });
                }
            });
            return;
        }
        AMapLog.warning(GROUP, TAG, "login unsupported ".concat(String.valueOf(parseInt)));
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(new PayInfo(str2, PayInfo.CODE_PAYMENT_UNSUPPORT, "unsupported").toJson());
        }
    }

    public void sign(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        StringBuilder sb = new StringBuilder("sign() called with: type = [");
        sb.append(str);
        sb.append("], request = [");
        sb.append(str2);
        sb.append("], jsCallback = [");
        sb.append(jsFunctionCallback);
        sb.append("]");
        AMapLog.info(GROUP, TAG, sb.toString());
        int parseInt = parseInt(str);
        if (TextUtils.isEmpty(str2)) {
            StringBuilder sb2 = new StringBuilder("sign request error: type=");
            sb2.append(parseInt);
            sb2.append(", param=");
            sb2.append(str2);
            AMapLog.warning(GROUP, TAG, sb2.toString());
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new PayInfo(str2, PayInfo.CODE_PARAM_ERROR, "").toJson());
            }
            return;
        }
        final abv b = aby.b(parseInt, getNativeContext());
        final PayInfo payInfo = new PayInfo(str2);
        if (b != null) {
            UiExecutor.post(new Runnable() {
                public final void run() {
                    b.setDebug(AjxModulePay.this.mIsDebug);
                    b.sign(payInfo, new abw() {
                        public final void a(PayInfo payInfo) {
                            String json = payInfo.toJson();
                            AMapLog.info(AjxModulePay.GROUP, AjxModulePay.TAG, "sign request result=".concat(String.valueOf(json)));
                            if (jsFunctionCallback != null) {
                                jsFunctionCallback.callback(json);
                            }
                        }
                    });
                }
            });
            return;
        }
        AMapLog.warning(GROUP, TAG, "sign unsupported ".concat(String.valueOf(parseInt)));
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(new PayInfo(str2, PayInfo.CODE_PAYMENT_UNSUPPORT, "unsupported").toJson());
        }
    }

    public void query(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        final abu abu;
        StringBuilder sb = new StringBuilder("query() called with: type = [");
        sb.append(str);
        sb.append("], request = [");
        sb.append(str2);
        sb.append("], jsCallback = [");
        sb.append(jsFunctionCallback);
        sb.append("]");
        AMapLog.info(GROUP, TAG, sb.toString());
        int parseInt = parseInt(str);
        if (TextUtils.isEmpty(str2)) {
            StringBuilder sb2 = new StringBuilder("query request error: type=");
            sb2.append(parseInt);
            sb2.append(", param=");
            sb2.append(str2);
            AMapLog.warning(GROUP, TAG, sb2.toString());
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new PayInfo(str2, PayInfo.CODE_PARAM_ERROR, "").toJson());
            }
            return;
        }
        Context nativeContext = getNativeContext();
        if (parseInt != 10) {
            abu = null;
        } else {
            abu = new WechatScoreQuery(nativeContext);
        }
        final PayInfo payInfo = new PayInfo(str2);
        if (abu != null) {
            UiExecutor.post(new Runnable() {
                public final void run() {
                    abu.setDebug(AjxModulePay.this.mIsDebug);
                    abu.query(payInfo, new abw() {
                        public final void a(PayInfo payInfo) {
                            String json = payInfo.toJson();
                            AMapLog.info(AjxModulePay.GROUP, AjxModulePay.TAG, "query request result=".concat(String.valueOf(json)));
                            if (jsFunctionCallback != null) {
                                jsFunctionCallback.callback(json);
                            }
                        }
                    });
                }
            });
            return;
        }
        AMapLog.warning(GROUP, TAG, "query unsupported ".concat(String.valueOf(parseInt)));
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(new PayInfo(str2, PayInfo.CODE_PAYMENT_UNSUPPORT, "unsupported").toJson());
        }
    }

    public String isInstalled(String str) {
        StringBuilder sb = new StringBuilder("isInstalled() called with: type = [");
        sb.append(str);
        sb.append("]");
        AMapLog.info(GROUP, TAG, sb.toString());
        abr d = aby.d(parseInt(str), getNativeContext());
        if (d == null) {
            return "0";
        }
        String str2 = d.a() ? "1" : "0";
        StringBuilder sb2 = new StringBuilder("isInstalled() result: type = [");
        sb2.append(str);
        sb2.append("], result = ");
        sb2.append(str2);
        AMapLog.info(GROUP, TAG, sb2.toString());
        return str2;
    }

    private int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return -1;
        }
    }

    public void setDebug(String str) {
        StringBuilder sb = new StringBuilder("setDebug() called with: debug = [");
        sb.append(str);
        sb.append("]");
        AMapLog.info(GROUP, TAG, sb.toString());
        if (TextUtils.isEmpty(str) || !"1".equals(str)) {
            this.mIsDebug = false;
        } else {
            this.mIsDebug = true;
        }
    }
}
