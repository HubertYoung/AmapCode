package com.ali.user.mobile.rpc.vo.sso;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class UnifyVerifySSOTokenResultPb extends Message {
    public static final String DEFAULT_CHANNELAPP = "";
    public static final String DEFAULT_H5URL = "";
    public static final String DEFAULT_HEADIMG = "";
    public static final Boolean DEFAULT_ISDIRECTLOGIN = Boolean.FALSE;
    public static final String DEFAULT_LOGINTOKEN = "";
    public static final String DEFAULT_RESULTCODE = "";
    public static final String DEFAULT_RESULTMSG = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.FALSE;
    public static final String DEFAULT_TAOBAONICK = "";
    public static final int TAG_CHANNELAPP = 2;
    public static final int TAG_H5URL = 9;
    public static final int TAG_HEADIMG = 8;
    public static final int TAG_ISDIRECTLOGIN = 6;
    public static final int TAG_LOGINTOKEN = 5;
    public static final int TAG_RESULTCODE = 3;
    public static final int TAG_RESULTMSG = 4;
    public static final int TAG_SUCCESS = 1;
    public static final int TAG_TAOBAONICK = 7;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String channelApp;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String h5Url;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String headImg;
    @ProtoField(tag = 6, type = Datatype.BOOL)
    public Boolean isDirectLogin;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String loginToken;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String resultCode;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String resultMsg;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String taobaoNick;

    public UnifyVerifySSOTokenResultPb(UnifyVerifySSOTokenResultPb unifyVerifySSOTokenResultPb) {
        super(unifyVerifySSOTokenResultPb);
        if (unifyVerifySSOTokenResultPb != null) {
            this.success = unifyVerifySSOTokenResultPb.success;
            this.channelApp = unifyVerifySSOTokenResultPb.channelApp;
            this.resultCode = unifyVerifySSOTokenResultPb.resultCode;
            this.resultMsg = unifyVerifySSOTokenResultPb.resultMsg;
            this.loginToken = unifyVerifySSOTokenResultPb.loginToken;
            this.isDirectLogin = unifyVerifySSOTokenResultPb.isDirectLogin;
            this.taobaoNick = unifyVerifySSOTokenResultPb.taobaoNick;
            this.headImg = unifyVerifySSOTokenResultPb.headImg;
            this.h5Url = unifyVerifySSOTokenResultPb.h5Url;
        }
    }

    public UnifyVerifySSOTokenResultPb() {
    }

    public final UnifyVerifySSOTokenResultPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.channelApp = (String) obj;
                break;
            case 3:
                this.resultCode = (String) obj;
                break;
            case 4:
                this.resultMsg = (String) obj;
                break;
            case 5:
                this.loginToken = (String) obj;
                break;
            case 6:
                this.isDirectLogin = (Boolean) obj;
                break;
            case 7:
                this.taobaoNick = (String) obj;
                break;
            case 8:
                this.headImg = (String) obj;
                break;
            case 9:
                this.h5Url = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnifyVerifySSOTokenResultPb)) {
            return false;
        }
        UnifyVerifySSOTokenResultPb unifyVerifySSOTokenResultPb = (UnifyVerifySSOTokenResultPb) obj;
        return equals((Object) this.success, (Object) unifyVerifySSOTokenResultPb.success) && equals((Object) this.channelApp, (Object) unifyVerifySSOTokenResultPb.channelApp) && equals((Object) this.resultCode, (Object) unifyVerifySSOTokenResultPb.resultCode) && equals((Object) this.resultMsg, (Object) unifyVerifySSOTokenResultPb.resultMsg) && equals((Object) this.loginToken, (Object) unifyVerifySSOTokenResultPb.loginToken) && equals((Object) this.isDirectLogin, (Object) unifyVerifySSOTokenResultPb.isDirectLogin) && equals((Object) this.taobaoNick, (Object) unifyVerifySSOTokenResultPb.taobaoNick) && equals((Object) this.headImg, (Object) unifyVerifySSOTokenResultPb.headImg) && equals((Object) this.h5Url, (Object) unifyVerifySSOTokenResultPb.h5Url);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((this.success != null ? this.success.hashCode() : 0) * 37) + (this.channelApp != null ? this.channelApp.hashCode() : 0)) * 37) + (this.resultCode != null ? this.resultCode.hashCode() : 0)) * 37) + (this.resultMsg != null ? this.resultMsg.hashCode() : 0)) * 37) + (this.loginToken != null ? this.loginToken.hashCode() : 0)) * 37) + (this.isDirectLogin != null ? this.isDirectLogin.hashCode() : 0)) * 37) + (this.taobaoNick != null ? this.taobaoNick.hashCode() : 0)) * 37) + (this.headImg != null ? this.headImg.hashCode() : 0)) * 37;
        if (this.h5Url != null) {
            i2 = this.h5Url.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
