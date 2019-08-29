package com.alipay.android.phone.inside.offlinecode.gen;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Pair;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.offlinecode.engine.IJSEngine;
import com.alipay.android.phone.inside.offlinecode.engine.JSEngineCallback;
import com.alipay.android.phone.inside.offlinecode.engine.JSEngineFactory;
import com.alipay.android.phone.inside.offlinecode.engine.ScriptContextPlugin;
import com.alipay.android.phone.inside.offlinecode.model.ScriptGenCodeException;
import com.alipay.android.phone.inside.offlinecode.rpc.response.base.OfflineDataInfo;
import com.alipay.android.phone.inside.offlinecode.utils.ScriptManager;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

public class ScriptGenCodeProtocol extends ICodeProtocol {
    /* access modifiers changed from: private */
    public static final String TAG = "ScriptGenCodeProtocol";
    /* access modifiers changed from: private */
    public volatile IJSEngine engine;
    private OfflineDataInfo offlineData;

    class MyCallback implements JSEngineCallback {
        CountDownLatch latch = new CountDownLatch(1);
        Pair<String, String> result;

        MyCallback() {
        }

        public void onComplete(String str) {
            this.result = Pair.create("true", str);
            this.latch.countDown();
        }

        public void onError(Throwable th) {
            this.result = Pair.create("false", th.getMessage());
            this.latch.countDown();
        }

        public Pair<String, String> getResult() {
            try {
                this.latch.await();
                return this.result;
            } catch (Throwable unused) {
                return this.result;
            }
        }
    }

    public ScriptGenCodeProtocol(@NonNull OfflineDataInfo offlineDataInfo) {
        this.offlineData = offlineDataInfo;
        createEngine(LauncherApplication.a());
    }

    public String generateCode(String str, String str2) throws Exception {
        String str3;
        String str4 = this.offlineData.scriptName;
        String str5 = this.offlineData.scriptType;
        String str6 = this.offlineData.certType;
        String str7 = this.offlineData.scriptMac;
        String str8 = this.offlineData.qrcode;
        TraceLogger f = LoggerFactory.f();
        String str9 = TAG;
        StringBuilder sb = new StringBuilder("scriptName:");
        sb.append(str4);
        sb.append(", certType:");
        sb.append(str6);
        f.d(str9, sb.toString());
        MyCallback myCallback = new MyCallback();
        try {
            str3 = ScriptManager.getInstance().get(str6, str5, str4, str7);
            try {
                TraceLogger f2 = LoggerFactory.f();
                StringBuilder sb2 = new StringBuilder("\nscript code:\n ");
                sb2.append(str3);
                sb2.append("\n");
                f2.b((String) "buscode/script", sb2.toString());
                for (int i = 50; i > 0 && (this.engine == null || !this.engine.hasPrepared()); i--) {
                    Thread.sleep(100);
                }
                if (this.engine != null) {
                    if (this.engine.hasPrepared()) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("hexData", str);
                        jSONObject.put("privateKey", str2);
                        jSONObject.put("qrcode", str8);
                        this.engine.callJSMethod(str3, jSONObject, myCallback);
                        Pair<String, String> result = myCallback.getResult();
                        TraceLogger f3 = LoggerFactory.f();
                        StringBuilder sb3 = new StringBuilder("\nscript result: ");
                        sb3.append(result);
                        sb3.append("\n\n");
                        f3.b((String) "buscode/script", sb3.toString());
                        destroyEngine();
                        if ("true".equals(result.first)) {
                            return (String) result.second;
                        }
                        Behavior a = LoggerFactory.d().a(TAG, BehaviorType.EVENT, "BusScriptProtocolGenFail");
                        a.g = str5;
                        a.h = str4;
                        throw new ScriptGenCodeException();
                    }
                }
                throw new Exception("engine init fail");
            } catch (Exception e) {
                e = e;
                Behavior a2 = LoggerFactory.d().a(TAG, BehaviorType.EVENT, "BusScriptProtocolGenEx");
                a2.g = str5;
                a2.h = str4;
                StringBuilder sb4 = new StringBuilder("script:");
                sb4.append(str3);
                sb4.append(" | ");
                sb4.append(e.getLocalizedMessage());
                a2.a(sb4.toString());
                throw new ScriptGenCodeException();
            }
        } catch (Exception e2) {
            e = e2;
            str3 = "";
            Behavior a22 = LoggerFactory.d().a(TAG, BehaviorType.EVENT, "BusScriptProtocolGenEx");
            a22.g = str5;
            a22.h = str4;
            StringBuilder sb42 = new StringBuilder("script:");
            sb42.append(str3);
            sb42.append(" | ");
            sb42.append(e.getLocalizedMessage());
            a22.a(sb42.toString());
            throw new ScriptGenCodeException();
        }
    }

    private void createEngine(final Context context) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                try {
                    ScriptGenCodeProtocol.this.engine = JSEngineFactory.getEngine();
                    ScriptGenCodeProtocol.this.engine.init(context);
                    ScriptGenCodeProtocol.this.engine.registerPlugin(new ScriptContextPlugin());
                } catch (Throwable th) {
                    LoggerFactory.f().d(ScriptGenCodeProtocol.TAG, "Create script engine failed.");
                    LoggerFactory.f().b(ScriptGenCodeProtocol.TAG, th);
                }
            }
        });
    }

    private void destroyEngine() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (ScriptGenCodeProtocol.this.engine != null) {
                    ScriptGenCodeProtocol.this.engine.destroy();
                    ScriptGenCodeProtocol.this.engine = null;
                }
            }
        });
    }
}
