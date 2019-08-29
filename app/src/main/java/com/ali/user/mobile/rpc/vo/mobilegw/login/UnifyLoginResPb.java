package com.ali.user.mobile.rpc.vo.mobilegw.login;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class UnifyLoginResPb extends Message {
    public static final String DEFAULT_ALIPAYLOGINID = "";
    public static final String DEFAULT_CHECKCODEID = "";
    public static final String DEFAULT_CHECKCODEURL = "";
    public static final String DEFAULT_CODE = "";
    public static final List<ExternParams> DEFAULT_EXTMAP = Collections.emptyList();
    public static final String DEFAULT_H5URL = "";
    public static final String DEFAULT_HEADIMG = "";
    public static final Long DEFAULT_HID = Long.valueOf(0);
    public static final String DEFAULT_MOBILENO = "";
    public static final String DEFAULT_MSG = "";
    public static final String DEFAULT_RESULTDATA = "";
    public static final String DEFAULT_SCENE = "";
    public static final String DEFAULT_SESSIONID = "";
    public static final String DEFAULT_SIGNDATA = "";
    public static final String DEFAULT_SSOTOKEN = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.FALSE;
    public static final String DEFAULT_TAOBAONICK = "";
    public static final Long DEFAULT_TAOBAOUSERID = Long.valueOf(0);
    public static final String DEFAULT_TBLOGINID = "";
    public static final String DEFAULT_TOKEN = "";
    public static final String DEFAULT_USERID = "";
    public static final int TAG_ALIPAYLOGINID = 14;
    public static final int TAG_CHECKCODEID = 18;
    public static final int TAG_CHECKCODEURL = 19;
    public static final int TAG_CODE = 2;
    public static final int TAG_EXTMAP = 20;
    public static final int TAG_H5URL = 17;
    public static final int TAG_HEADIMG = 13;
    public static final int TAG_HID = 9;
    public static final int TAG_MOBILENO = 21;
    public static final int TAG_MSG = 4;
    public static final int TAG_RESULTDATA = 16;
    public static final int TAG_SCENE = 8;
    public static final int TAG_SESSIONID = 3;
    public static final int TAG_SIGNDATA = 6;
    public static final int TAG_SSOTOKEN = 7;
    public static final int TAG_SUCCESS = 1;
    public static final int TAG_TAOBAONICK = 12;
    public static final int TAG_TAOBAOUSERID = 11;
    public static final int TAG_TBLOGINID = 15;
    public static final int TAG_TOKEN = 5;
    public static final int TAG_USERID = 10;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String alipayLoginId;
    @ProtoField(tag = 18, type = Datatype.STRING)
    public String checkCodeId;
    @ProtoField(tag = 19, type = Datatype.STRING)
    public String checkCodeUrl;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String code;
    @ProtoField(label = Label.REPEATED, tag = 20)
    public List<ExternParams> extMap;
    @ProtoField(tag = 17, type = Datatype.STRING)
    public String h5Url;
    @ProtoField(tag = 13, type = Datatype.STRING)
    public String headImg;
    @ProtoField(tag = 9, type = Datatype.INT64)
    public Long hid;
    @ProtoField(tag = 21, type = Datatype.STRING)
    public String mobileNo;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String msg;
    @ProtoField(tag = 16, type = Datatype.STRING)
    public String resultData;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String scene;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String sessionId;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String signData;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String ssoToken;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String taobaoNick;
    @ProtoField(tag = 11, type = Datatype.INT64)
    public Long taobaoUserId;
    @ProtoField(tag = 15, type = Datatype.STRING)
    public String tbLoginId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String token;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String userId;

    public UnifyLoginResPb(UnifyLoginResPb unifyLoginResPb) {
        super(unifyLoginResPb);
        if (unifyLoginResPb != null) {
            this.success = unifyLoginResPb.success;
            this.code = unifyLoginResPb.code;
            this.sessionId = unifyLoginResPb.sessionId;
            this.msg = unifyLoginResPb.msg;
            this.token = unifyLoginResPb.token;
            this.signData = unifyLoginResPb.signData;
            this.ssoToken = unifyLoginResPb.ssoToken;
            this.scene = unifyLoginResPb.scene;
            this.hid = unifyLoginResPb.hid;
            this.userId = unifyLoginResPb.userId;
            this.taobaoUserId = unifyLoginResPb.taobaoUserId;
            this.taobaoNick = unifyLoginResPb.taobaoNick;
            this.headImg = unifyLoginResPb.headImg;
            this.alipayLoginId = unifyLoginResPb.alipayLoginId;
            this.tbLoginId = unifyLoginResPb.tbLoginId;
            this.resultData = unifyLoginResPb.resultData;
            this.h5Url = unifyLoginResPb.h5Url;
            this.checkCodeId = unifyLoginResPb.checkCodeId;
            this.checkCodeUrl = unifyLoginResPb.checkCodeUrl;
            this.extMap = copyOf(unifyLoginResPb.extMap);
            this.mobileNo = unifyLoginResPb.mobileNo;
        }
    }

    public UnifyLoginResPb() {
    }

    public final UnifyLoginResPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.code = (String) obj;
                break;
            case 3:
                this.sessionId = (String) obj;
                break;
            case 4:
                this.msg = (String) obj;
                break;
            case 5:
                this.token = (String) obj;
                break;
            case 6:
                this.signData = (String) obj;
                break;
            case 7:
                this.ssoToken = (String) obj;
                break;
            case 8:
                this.scene = (String) obj;
                break;
            case 9:
                this.hid = (Long) obj;
                break;
            case 10:
                this.userId = (String) obj;
                break;
            case 11:
                this.taobaoUserId = (Long) obj;
                break;
            case 12:
                this.taobaoNick = (String) obj;
                break;
            case 13:
                this.headImg = (String) obj;
                break;
            case 14:
                this.alipayLoginId = (String) obj;
                break;
            case 15:
                this.tbLoginId = (String) obj;
                break;
            case 16:
                this.resultData = (String) obj;
                break;
            case 17:
                this.h5Url = (String) obj;
                break;
            case 18:
                this.checkCodeId = (String) obj;
                break;
            case 19:
                this.checkCodeUrl = (String) obj;
                break;
            case 20:
                this.extMap = immutableCopyOf((List) obj);
                break;
            case 21:
                this.mobileNo = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnifyLoginResPb)) {
            return false;
        }
        UnifyLoginResPb unifyLoginResPb = (UnifyLoginResPb) obj;
        return equals((Object) this.success, (Object) unifyLoginResPb.success) && equals((Object) this.code, (Object) unifyLoginResPb.code) && equals((Object) this.sessionId, (Object) unifyLoginResPb.sessionId) && equals((Object) this.msg, (Object) unifyLoginResPb.msg) && equals((Object) this.token, (Object) unifyLoginResPb.token) && equals((Object) this.signData, (Object) unifyLoginResPb.signData) && equals((Object) this.ssoToken, (Object) unifyLoginResPb.ssoToken) && equals((Object) this.scene, (Object) unifyLoginResPb.scene) && equals((Object) this.hid, (Object) unifyLoginResPb.hid) && equals((Object) this.userId, (Object) unifyLoginResPb.userId) && equals((Object) this.taobaoUserId, (Object) unifyLoginResPb.taobaoUserId) && equals((Object) this.taobaoNick, (Object) unifyLoginResPb.taobaoNick) && equals((Object) this.headImg, (Object) unifyLoginResPb.headImg) && equals((Object) this.alipayLoginId, (Object) unifyLoginResPb.alipayLoginId) && equals((Object) this.tbLoginId, (Object) unifyLoginResPb.tbLoginId) && equals((Object) this.resultData, (Object) unifyLoginResPb.resultData) && equals((Object) this.h5Url, (Object) unifyLoginResPb.h5Url) && equals((Object) this.checkCodeId, (Object) unifyLoginResPb.checkCodeId) && equals((Object) this.checkCodeUrl, (Object) unifyLoginResPb.checkCodeUrl) && equals(this.extMap, unifyLoginResPb.extMap) && equals((Object) this.mobileNo, (Object) unifyLoginResPb.mobileNo);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((((((((((((((((((this.success != null ? this.success.hashCode() : 0) * 37) + (this.code != null ? this.code.hashCode() : 0)) * 37) + (this.sessionId != null ? this.sessionId.hashCode() : 0)) * 37) + (this.msg != null ? this.msg.hashCode() : 0)) * 37) + (this.token != null ? this.token.hashCode() : 0)) * 37) + (this.signData != null ? this.signData.hashCode() : 0)) * 37) + (this.ssoToken != null ? this.ssoToken.hashCode() : 0)) * 37) + (this.scene != null ? this.scene.hashCode() : 0)) * 37) + (this.hid != null ? this.hid.hashCode() : 0)) * 37) + (this.userId != null ? this.userId.hashCode() : 0)) * 37) + (this.taobaoUserId != null ? this.taobaoUserId.hashCode() : 0)) * 37) + (this.taobaoNick != null ? this.taobaoNick.hashCode() : 0)) * 37) + (this.headImg != null ? this.headImg.hashCode() : 0)) * 37) + (this.alipayLoginId != null ? this.alipayLoginId.hashCode() : 0)) * 37) + (this.tbLoginId != null ? this.tbLoginId.hashCode() : 0)) * 37) + (this.resultData != null ? this.resultData.hashCode() : 0)) * 37) + (this.h5Url != null ? this.h5Url.hashCode() : 0)) * 37) + (this.checkCodeId != null ? this.checkCodeId.hashCode() : 0)) * 37) + (this.checkCodeUrl != null ? this.checkCodeUrl.hashCode() : 0)) * 37) + (this.extMap != null ? this.extMap.hashCode() : 1)) * 37;
        if (this.mobileNo != null) {
            i2 = this.mobileNo.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
