package com.ali.user.mobile.login;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LoginParam implements Serializable {
    private static final long serialVersionUID = 1;
    public String alipayEnvJson;
    public Map<String, String> externParams;
    public String loginAccount;
    public String loginPassword;
    public String loginType;
    public Map<String, String> monitorParams;
    public String smsCode;
    public String ssoToken;
    public String taobaoEnvJson;
    public String token;
    public String validateTpye;

    public void addExternalParam(String str, String str2) {
        if (this.externParams == null) {
            this.externParams = new HashMap();
        }
        this.externParams.put(str, str2);
    }

    public void addMonitorParam(String str, String str2) {
        if (this.monitorParams == null) {
            this.monitorParams = new HashMap();
        }
        this.monitorParams.put(str, str2);
    }

    public boolean trustLoginEnable() {
        return !TextUtils.isEmpty(this.loginAccount) && !TextUtils.isEmpty(this.token) && !TextUtils.isEmpty(this.validateTpye);
    }

    public void disableTustLogin() {
        this.token = "";
        this.validateTpye = "";
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LoginParam [loginAccount:");
        sb.append(this.loginAccount);
        sb.append("token:");
        sb.append(this.token);
        sb.append("validateTpye:");
        sb.append(this.validateTpye);
        sb.append("]");
        return sb.toString();
    }
}
