package com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class UnifyRegisterAllResPb extends Message {
    public static final Boolean DEFAULT_DIRECTLOGIN = Boolean.FALSE;
    public static final Boolean DEFAULT_EXISTUSERHASQUERYPASSWORD = Boolean.FALSE;
    public static final List<ExternKVParam> DEFAULT_EXTINFOS = Collections.emptyList();
    public static final String DEFAULT_LOGINTOKEN = "";
    public static final String DEFAULT_MEMO = "";
    public static final String DEFAULT_MOBILENO = "";
    public static final Boolean DEFAULT_NEWUSERHASQUERYPASSWORD = Boolean.FALSE;
    public static final Boolean DEFAULT_REMOVEPAYMENTPASS = Boolean.FALSE;
    public static final Long DEFAULT_RESULTSTATUS = Long.valueOf(0);
    public static final String DEFAULT_SECURITYID = "";
    public static final String DEFAULT_SIMPLEPASSWORD = "";
    public static final String DEFAULT_TOKEN = "";
    public static final int TAG_DIRECTLOGIN = 7;
    public static final int TAG_EXISTUSERHASQUERYPASSWORD = 11;
    public static final int TAG_EXISTUSERINFO = 8;
    public static final int TAG_EXTINFOS = 13;
    public static final int TAG_LOGINTOKEN = 9;
    public static final int TAG_MEMO = 4;
    public static final int TAG_MOBILENO = 3;
    public static final int TAG_NEWUSERHASQUERYPASSWORD = 12;
    public static final int TAG_REMOVEPAYMENTPASS = 10;
    public static final int TAG_RESULTSTATUS = 1;
    public static final int TAG_SECURITYID = 6;
    public static final int TAG_SIMPLEPASSWORD = 5;
    public static final int TAG_TOKEN = 2;
    @ProtoField(tag = 7, type = Datatype.BOOL)
    public Boolean directLogin;
    @ProtoField(tag = 11, type = Datatype.BOOL)
    public Boolean existUserHasQueryPassword;
    @ProtoField(tag = 8)
    public ExistUserInfo existUserInfo;
    @ProtoField(label = Label.REPEATED, tag = 13)
    public List<ExternKVParam> extInfos;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String loginToken;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String memo;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String mobileNo;
    @ProtoField(tag = 12, type = Datatype.BOOL)
    public Boolean newUserHasQueryPassword;
    @ProtoField(tag = 10, type = Datatype.BOOL)
    public Boolean removePaymentPass;
    @ProtoField(tag = 1, type = Datatype.INT64)
    public Long resultStatus;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String securityId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String simplePassword;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String token;

    public UnifyRegisterAllResPb(UnifyRegisterAllResPb unifyRegisterAllResPb) {
        super(unifyRegisterAllResPb);
        if (unifyRegisterAllResPb != null) {
            this.resultStatus = unifyRegisterAllResPb.resultStatus;
            this.token = unifyRegisterAllResPb.token;
            this.mobileNo = unifyRegisterAllResPb.mobileNo;
            this.memo = unifyRegisterAllResPb.memo;
            this.simplePassword = unifyRegisterAllResPb.simplePassword;
            this.securityId = unifyRegisterAllResPb.securityId;
            this.directLogin = unifyRegisterAllResPb.directLogin;
            this.existUserInfo = unifyRegisterAllResPb.existUserInfo;
            this.loginToken = unifyRegisterAllResPb.loginToken;
            this.removePaymentPass = unifyRegisterAllResPb.removePaymentPass;
            this.existUserHasQueryPassword = unifyRegisterAllResPb.existUserHasQueryPassword;
            this.newUserHasQueryPassword = unifyRegisterAllResPb.newUserHasQueryPassword;
            this.extInfos = copyOf(unifyRegisterAllResPb.extInfos);
        }
    }

    public UnifyRegisterAllResPb() {
    }

    public final UnifyRegisterAllResPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.resultStatus = (Long) obj;
                break;
            case 2:
                this.token = (String) obj;
                break;
            case 3:
                this.mobileNo = (String) obj;
                break;
            case 4:
                this.memo = (String) obj;
                break;
            case 5:
                this.simplePassword = (String) obj;
                break;
            case 6:
                this.securityId = (String) obj;
                break;
            case 7:
                this.directLogin = (Boolean) obj;
                break;
            case 8:
                this.existUserInfo = (ExistUserInfo) obj;
                break;
            case 9:
                this.loginToken = (String) obj;
                break;
            case 10:
                this.removePaymentPass = (Boolean) obj;
                break;
            case 11:
                this.existUserHasQueryPassword = (Boolean) obj;
                break;
            case 12:
                this.newUserHasQueryPassword = (Boolean) obj;
                break;
            case 13:
                this.extInfos = immutableCopyOf((List) obj);
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnifyRegisterAllResPb)) {
            return false;
        }
        UnifyRegisterAllResPb unifyRegisterAllResPb = (UnifyRegisterAllResPb) obj;
        return equals((Object) this.resultStatus, (Object) unifyRegisterAllResPb.resultStatus) && equals((Object) this.token, (Object) unifyRegisterAllResPb.token) && equals((Object) this.mobileNo, (Object) unifyRegisterAllResPb.mobileNo) && equals((Object) this.memo, (Object) unifyRegisterAllResPb.memo) && equals((Object) this.simplePassword, (Object) unifyRegisterAllResPb.simplePassword) && equals((Object) this.securityId, (Object) unifyRegisterAllResPb.securityId) && equals((Object) this.directLogin, (Object) unifyRegisterAllResPb.directLogin) && equals((Object) this.existUserInfo, (Object) unifyRegisterAllResPb.existUserInfo) && equals((Object) this.loginToken, (Object) unifyRegisterAllResPb.loginToken) && equals((Object) this.removePaymentPass, (Object) unifyRegisterAllResPb.removePaymentPass) && equals((Object) this.existUserHasQueryPassword, (Object) unifyRegisterAllResPb.existUserHasQueryPassword) && equals((Object) this.newUserHasQueryPassword, (Object) unifyRegisterAllResPb.newUserHasQueryPassword) && equals(this.extInfos, unifyRegisterAllResPb.extInfos);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((this.resultStatus != null ? this.resultStatus.hashCode() : 0) * 37) + (this.token != null ? this.token.hashCode() : 0)) * 37) + (this.mobileNo != null ? this.mobileNo.hashCode() : 0)) * 37) + (this.memo != null ? this.memo.hashCode() : 0)) * 37) + (this.simplePassword != null ? this.simplePassword.hashCode() : 0)) * 37) + (this.securityId != null ? this.securityId.hashCode() : 0)) * 37) + (this.directLogin != null ? this.directLogin.hashCode() : 0)) * 37) + (this.existUserInfo != null ? this.existUserInfo.hashCode() : 0)) * 37) + (this.loginToken != null ? this.loginToken.hashCode() : 0)) * 37) + (this.removePaymentPass != null ? this.removePaymentPass.hashCode() : 0)) * 37) + (this.existUserHasQueryPassword != null ? this.existUserHasQueryPassword.hashCode() : 0)) * 37;
        if (this.newUserHasQueryPassword != null) {
            i2 = this.newUserHasQueryPassword.hashCode();
        }
        int hashCode2 = ((hashCode + i2) * 37) + (this.extInfos != null ? this.extInfos.hashCode() : 1);
        this.hashCode = hashCode2;
        return hashCode2;
    }
}
