package com.autonavi.miniapp.plugin.mtop;

import com.alipay.mobile.securitycommon.aliauth.AliAuthResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SsoLoginInfo implements Serializable {
    private AliAuthResult mTaobaoAutoLoginResult;

    public String getCookie() {
        return "";
    }

    public SsoLoginInfo(AliAuthResult aliAuthResult) {
        this.mTaobaoAutoLoginResult = aliAuthResult;
    }

    public boolean getSuccess() {
        return this.mTaobaoAutoLoginResult.success;
    }

    public String getResultStatus() {
        return this.mTaobaoAutoLoginResult.resultStatus;
    }

    public String getMemo() {
        return this.mTaobaoAutoLoginResult.memo;
    }

    public String getSid() {
        return this.mTaobaoAutoLoginResult.sid;
    }

    public String getEcode() {
        return this.mTaobaoAutoLoginResult.ecode;
    }

    public String getTbUserId() {
        return this.mTaobaoAutoLoginResult.tbUserId;
    }

    public String getTbNick() {
        return this.mTaobaoAutoLoginResult.tbNick;
    }

    public List<String> getDomains() {
        return new ArrayList();
    }

    public String getNoticeUrl() {
        return this.mTaobaoAutoLoginResult.noticeUrl;
    }
}
