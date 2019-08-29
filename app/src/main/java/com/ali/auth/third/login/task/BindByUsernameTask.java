package com.ali.auth.third.login.task;

import com.ali.auth.third.core.message.Message;
import com.ali.auth.third.core.message.MessageUtils;
import com.ali.auth.third.core.model.LoginReturnData;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.model.RpcResponse;
import com.ali.auth.third.core.task.AbsAsyncTask;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.core.util.ResourceUtils;
import com.ali.auth.third.login.LoginComponent;
import com.ali.auth.third.ui.LoginWebViewActivity;
import com.ali.auth.third.ui.context.a;
import org.json.JSONException;
import org.json.JSONObject;

public class BindByUsernameTask extends AbsAsyncTask<String, Void, Void> {
    private a a;

    public BindByUsernameTask(a aVar) {
        this.a = aVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void asyncExecute(String... strArr) {
        a aVar;
        String jSONObject;
        if (!CommonUtils.isNetworkAvailable()) {
            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.code = -1;
            rpcResponse.message = ResourceUtils.getString("com_taobao_tae_sdk_network_not_available_message");
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("code", rpcResponse.code);
                jSONObject2.put("message", rpcResponse.message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.a.a(jSONObject2.toString());
            return null;
        }
        RpcResponse<LoginReturnData> loginByUserName = LoginComponent.INSTANCE.loginByUserName(strArr[0]);
        if (loginByUserName == null) {
            this.a.b("");
            return null;
        }
        try {
            if (loginByUserName.code == 3000) {
                com.ali.auth.third.login.a.a.b.refreshWhenLogin((LoginReturnData) loginByUserName.returnValue);
                this.a.a().setResult(ResultCode.SUCCESS.code);
                this.a.a().finish();
                return null;
            }
            if (loginByUserName.code == 13010) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("code", 1007);
                jSONObject3.put("message", loginByUserName.message);
                jSONObject3.put("success", false);
                JSONObject jSONObject4 = new JSONObject();
                if (loginByUserName.returnValue != null) {
                    jSONObject4.put("checkCodeId", ((LoginReturnData) loginByUserName.returnValue).checkCodeId);
                    jSONObject4.put("checkCodeUrl", ((LoginReturnData) loginByUserName.returnValue).checkCodeUrl);
                }
                jSONObject3.put("data", jSONObject4);
                aVar = this.a;
                jSONObject = jSONObject3.toString();
            } else if (loginByUserName.code == 13011) {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("code", 1008);
                jSONObject5.put("message", loginByUserName.message);
                JSONObject jSONObject6 = new JSONObject();
                if (loginByUserName.returnValue != null) {
                    jSONObject6.put("checkCodeId", ((LoginReturnData) loginByUserName.returnValue).checkCodeId);
                    jSONObject6.put("checkCodeUrl", ((LoginReturnData) loginByUserName.returnValue).checkCodeUrl);
                }
                jSONObject5.put("data", jSONObject6);
                aVar = this.a;
                jSONObject = jSONObject5.toString();
            } else if (loginByUserName.code == 13060) {
                JSONObject jSONObject7 = new JSONObject();
                jSONObject7.put("code", 1013);
                jSONObject7.put("message", loginByUserName.message);
                JSONObject jSONObject8 = new JSONObject();
                if (loginByUserName.returnValue != null) {
                    jSONObject8.put("doubleCheckUrl", ((LoginReturnData) loginByUserName.returnValue).h5Url);
                }
                jSONObject7.put("data", jSONObject8);
                if (loginByUserName.returnValue != null) {
                    this.a.a();
                    LoginWebViewActivity.token = ((LoginReturnData) loginByUserName.returnValue).token;
                    this.a.a();
                    LoginWebViewActivity.scene = ((LoginReturnData) loginByUserName.returnValue).scene;
                }
                aVar = this.a;
                jSONObject = jSONObject7.toString();
            } else {
                JSONObject jSONObject9 = new JSONObject();
                jSONObject9.put("code", loginByUserName.code);
                jSONObject9.put("message", loginByUserName.message);
                aVar = this.a;
                jSONObject = jSONObject9.toString();
            }
            aVar.a(jSONObject);
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void doFinally() {
    }

    public void doWhenException(Throwable th) {
        Message createMessage = MessageUtils.createMessage(10010, th.getMessage());
        SDKLogger.d("login", createMessage.toString());
        this.a.a(createMessage.code, createMessage.message);
    }
}
