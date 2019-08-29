package com.alipay.mobile.antui.lottie;

import com.alipay.mobile.antui.excutor.FileLoadCallback;
import com.alipay.mobile.antui.utils.AuiLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: NetErrorLottieFile */
final class a implements FileLoadCallback {
    final /* synthetic */ LoadLottieCallback a;
    final /* synthetic */ int b;
    final /* synthetic */ NetErrorLottieFile c;

    a(NetErrorLottieFile this$0, LoadLottieCallback loadLottieCallback, int i) {
        this.c = this$0;
        this.a = loadLottieCallback;
        this.b = i;
    }

    public final void onFinished(String fileName, String fileInfo) {
        try {
            LottieCache.getInstance().putFileJson(fileName, fileInfo);
            this.a.onLottieLoadFinish(new JSONObject(fileInfo), this.b);
        } catch (JSONException e) {
            AuiLogger.mtBizReport("AUNetErrorView", "String can not case to JSONObject");
        }
    }

    public final void onError(String fileName, String errorMessage) {
        AuiLogger.mtBizReport("AUNetErrorView", errorMessage + " when load " + fileName);
    }
}
