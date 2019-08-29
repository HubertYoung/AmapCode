package com.alipay.android.phone.secauthenticator.kcart;

import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class UploadedVariables extends Message {
    public static final Integer DEFAULT_D1D_CNT_FIN = Integer.valueOf(0);
    public static final Integer DEFAULT_D1D_CNT_FRIEND = Integer.valueOf(0);
    public static final Integer DEFAULT_D1D_CNT_HOME = Integer.valueOf(0);
    public static final Integer DEFAULT_D1D_CNT_MERCHANT = Integer.valueOf(0);
    public static final Integer DEFAULT_D1D_CNT_SETTING = Integer.valueOf(0);
    public static final Integer DEFAULT_D1D_CNT_YEBHOME = Integer.valueOf(0);
    public static final Integer DEFAULT_D1D_MX_CARDFILL = Integer.valueOf(0);
    public static final Integer DEFAULT_D1H_ST_TRANSFERHOME = Integer.valueOf(0);
    public static final Integer DEFAULT_DSM_FUNDSAFESECUR = Integer.valueOf(0);
    public static final Integer DEFAULT_DSM_TRSFERCARD = Integer.valueOf(0);
    public static final Integer DEFAULT_DSM_YEBFP = Integer.valueOf(0);
    public static final ByteString DEFAULT_EDGE_RISK_DATA = ByteString.EMPTY;
    public static final Integer DEFAULT_RT2_BILL_1H_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_RT2_YEB_1D_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_RT_ACCTDTL_1D_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_RT_CNT_ASSET_1D_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_RT_CNT_RELATION_1D_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_RT_FUNDCARD_1D_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_RT_FUND_1D_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_RT_PAY_1D_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_RT_SECURITYSAFE_1D_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_RT_TALK_1D_1MTH = Integer.valueOf(0);
    public static final Integer DEFAULT_VERSION = Integer.valueOf(0);
    public static final int TAG_D1D_CNT_FIN = 27;
    public static final int TAG_D1D_CNT_FRIEND = 24;
    public static final int TAG_D1D_CNT_HOME = 11;
    public static final int TAG_D1D_CNT_MERCHANT = 23;
    public static final int TAG_D1D_CNT_SETTING = 21;
    public static final int TAG_D1D_CNT_YEBHOME = 18;
    public static final int TAG_D1D_MX_CARDFILL = 28;
    public static final int TAG_D1H_ST_TRANSFERHOME = 13;
    public static final int TAG_DSM_FUNDSAFESECUR = 19;
    public static final int TAG_DSM_TRSFERCARD = 22;
    public static final int TAG_DSM_YEBFP = 20;
    public static final int TAG_EDGE_RISK_DATA = 31;
    public static final int TAG_RT2_BILL_1H_1MTH = 12;
    public static final int TAG_RT2_YEB_1D_1MTH = 14;
    public static final int TAG_RT_ACCTDTL_1D_1MTH = 26;
    public static final int TAG_RT_CNT_ASSET_1D_1MTH = 17;
    public static final int TAG_RT_CNT_RELATION_1D_1MTH = 29;
    public static final int TAG_RT_FUNDCARD_1D_1MTH = 16;
    public static final int TAG_RT_FUND_1D_1MTH = 10;
    public static final int TAG_RT_PAY_1D_1MTH = 30;
    public static final int TAG_RT_SECURITYSAFE_1D_1MTH = 25;
    public static final int TAG_RT_TALK_1D_1MTH = 15;
    public static final int TAG_VERSION = 1;
    @ProtoField(tag = 27, type = Datatype.INT32)
    public Integer D1D_CNT_FIN;
    @ProtoField(tag = 24, type = Datatype.INT32)
    public Integer D1D_CNT_FRIEND;
    @ProtoField(tag = 11, type = Datatype.INT32)
    public Integer D1D_CNT_HOME;
    @ProtoField(tag = 23, type = Datatype.INT32)
    public Integer D1D_CNT_MERCHANT;
    @ProtoField(tag = 21, type = Datatype.INT32)
    public Integer D1D_CNT_SETTING;
    @ProtoField(tag = 18, type = Datatype.INT32)
    public Integer D1D_CNT_YEBHOME;
    @ProtoField(tag = 28, type = Datatype.INT32)
    public Integer D1D_MX_CARDFILL;
    @ProtoField(tag = 13, type = Datatype.INT32)
    public Integer D1H_ST_TRANSFERHOME;
    @ProtoField(tag = 19, type = Datatype.INT32)
    public Integer DSM_FUNDSAFESECUR;
    @ProtoField(tag = 22, type = Datatype.INT32)
    public Integer DSM_TRSFERCARD;
    @ProtoField(tag = 20, type = Datatype.INT32)
    public Integer DSM_YEBFP;
    @ProtoField(tag = 31, type = Datatype.BYTES)
    public ByteString EDGE_RISK_DATA;
    @ProtoField(tag = 12, type = Datatype.INT32)
    public Integer RT2_BILL_1H_1MTH;
    @ProtoField(tag = 14, type = Datatype.INT32)
    public Integer RT2_YEB_1D_1MTH;
    @ProtoField(tag = 26, type = Datatype.INT32)
    public Integer RT_ACCTDTL_1D_1MTH;
    @ProtoField(tag = 17, type = Datatype.INT32)
    public Integer RT_CNT_ASSET_1D_1MTH;
    @ProtoField(tag = 29, type = Datatype.INT32)
    public Integer RT_CNT_RELATION_1D_1MTH;
    @ProtoField(tag = 16, type = Datatype.INT32)
    public Integer RT_FUNDCARD_1D_1MTH;
    @ProtoField(tag = 10, type = Datatype.INT32)
    public Integer RT_FUND_1D_1MTH;
    @ProtoField(tag = 30, type = Datatype.INT32)
    public Integer RT_PAY_1D_1MTH;
    @ProtoField(tag = 25, type = Datatype.INT32)
    public Integer RT_SECURITYSAFE_1D_1MTH;
    @ProtoField(tag = 15, type = Datatype.INT32)
    public Integer RT_TALK_1D_1MTH;
    @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.INT32)
    public Integer VERSION;

    public UploadedVariables(UploadedVariables uploadedVariables) {
        super(uploadedVariables);
        if (uploadedVariables != null) {
            this.VERSION = uploadedVariables.VERSION;
            this.RT_FUND_1D_1MTH = uploadedVariables.RT_FUND_1D_1MTH;
            this.D1D_CNT_HOME = uploadedVariables.D1D_CNT_HOME;
            this.RT2_BILL_1H_1MTH = uploadedVariables.RT2_BILL_1H_1MTH;
            this.D1H_ST_TRANSFERHOME = uploadedVariables.D1H_ST_TRANSFERHOME;
            this.RT2_YEB_1D_1MTH = uploadedVariables.RT2_YEB_1D_1MTH;
            this.RT_TALK_1D_1MTH = uploadedVariables.RT_TALK_1D_1MTH;
            this.RT_FUNDCARD_1D_1MTH = uploadedVariables.RT_FUNDCARD_1D_1MTH;
            this.RT_CNT_ASSET_1D_1MTH = uploadedVariables.RT_CNT_ASSET_1D_1MTH;
            this.D1D_CNT_YEBHOME = uploadedVariables.D1D_CNT_YEBHOME;
            this.DSM_FUNDSAFESECUR = uploadedVariables.DSM_FUNDSAFESECUR;
            this.DSM_YEBFP = uploadedVariables.DSM_YEBFP;
            this.D1D_CNT_SETTING = uploadedVariables.D1D_CNT_SETTING;
            this.DSM_TRSFERCARD = uploadedVariables.DSM_TRSFERCARD;
            this.D1D_CNT_MERCHANT = uploadedVariables.D1D_CNT_MERCHANT;
            this.D1D_CNT_FRIEND = uploadedVariables.D1D_CNT_FRIEND;
            this.RT_SECURITYSAFE_1D_1MTH = uploadedVariables.RT_SECURITYSAFE_1D_1MTH;
            this.RT_ACCTDTL_1D_1MTH = uploadedVariables.RT_ACCTDTL_1D_1MTH;
            this.D1D_CNT_FIN = uploadedVariables.D1D_CNT_FIN;
            this.D1D_MX_CARDFILL = uploadedVariables.D1D_MX_CARDFILL;
            this.RT_CNT_RELATION_1D_1MTH = uploadedVariables.RT_CNT_RELATION_1D_1MTH;
            this.RT_PAY_1D_1MTH = uploadedVariables.RT_PAY_1D_1MTH;
            this.EDGE_RISK_DATA = uploadedVariables.EDGE_RISK_DATA;
        }
    }

    public UploadedVariables() {
    }

    public final UploadedVariables fillTagValue(int i, Object obj) {
        if (i != 1) {
            switch (i) {
                case 10:
                    this.RT_FUND_1D_1MTH = (Integer) obj;
                    break;
                case 11:
                    this.D1D_CNT_HOME = (Integer) obj;
                    break;
                case 12:
                    this.RT2_BILL_1H_1MTH = (Integer) obj;
                    break;
                case 13:
                    this.D1H_ST_TRANSFERHOME = (Integer) obj;
                    break;
                case 14:
                    this.RT2_YEB_1D_1MTH = (Integer) obj;
                    break;
                case 15:
                    this.RT_TALK_1D_1MTH = (Integer) obj;
                    break;
                case 16:
                    this.RT_FUNDCARD_1D_1MTH = (Integer) obj;
                    break;
                case 17:
                    this.RT_CNT_ASSET_1D_1MTH = (Integer) obj;
                    break;
                case 18:
                    this.D1D_CNT_YEBHOME = (Integer) obj;
                    break;
                case 19:
                    this.DSM_FUNDSAFESECUR = (Integer) obj;
                    break;
                case 20:
                    this.DSM_YEBFP = (Integer) obj;
                    break;
                case 21:
                    this.D1D_CNT_SETTING = (Integer) obj;
                    break;
                case 22:
                    this.DSM_TRSFERCARD = (Integer) obj;
                    break;
                case 23:
                    this.D1D_CNT_MERCHANT = (Integer) obj;
                    break;
                case 24:
                    this.D1D_CNT_FRIEND = (Integer) obj;
                    break;
                case 25:
                    this.RT_SECURITYSAFE_1D_1MTH = (Integer) obj;
                    break;
                case 26:
                    this.RT_ACCTDTL_1D_1MTH = (Integer) obj;
                    break;
                case 27:
                    this.D1D_CNT_FIN = (Integer) obj;
                    break;
                case 28:
                    this.D1D_MX_CARDFILL = (Integer) obj;
                    break;
                case 29:
                    this.RT_CNT_RELATION_1D_1MTH = (Integer) obj;
                    break;
                case 30:
                    this.RT_PAY_1D_1MTH = (Integer) obj;
                    break;
                case 31:
                    this.EDGE_RISK_DATA = (ByteString) obj;
                    break;
            }
        } else {
            this.VERSION = (Integer) obj;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UploadedVariables)) {
            return false;
        }
        UploadedVariables uploadedVariables = (UploadedVariables) obj;
        return equals((Object) this.VERSION, (Object) uploadedVariables.VERSION) && equals((Object) this.RT_FUND_1D_1MTH, (Object) uploadedVariables.RT_FUND_1D_1MTH) && equals((Object) this.D1D_CNT_HOME, (Object) uploadedVariables.D1D_CNT_HOME) && equals((Object) this.RT2_BILL_1H_1MTH, (Object) uploadedVariables.RT2_BILL_1H_1MTH) && equals((Object) this.D1H_ST_TRANSFERHOME, (Object) uploadedVariables.D1H_ST_TRANSFERHOME) && equals((Object) this.RT2_YEB_1D_1MTH, (Object) uploadedVariables.RT2_YEB_1D_1MTH) && equals((Object) this.RT_TALK_1D_1MTH, (Object) uploadedVariables.RT_TALK_1D_1MTH) && equals((Object) this.RT_FUNDCARD_1D_1MTH, (Object) uploadedVariables.RT_FUNDCARD_1D_1MTH) && equals((Object) this.RT_CNT_ASSET_1D_1MTH, (Object) uploadedVariables.RT_CNT_ASSET_1D_1MTH) && equals((Object) this.D1D_CNT_YEBHOME, (Object) uploadedVariables.D1D_CNT_YEBHOME) && equals((Object) this.DSM_FUNDSAFESECUR, (Object) uploadedVariables.DSM_FUNDSAFESECUR) && equals((Object) this.DSM_YEBFP, (Object) uploadedVariables.DSM_YEBFP) && equals((Object) this.D1D_CNT_SETTING, (Object) uploadedVariables.D1D_CNT_SETTING) && equals((Object) this.DSM_TRSFERCARD, (Object) uploadedVariables.DSM_TRSFERCARD) && equals((Object) this.D1D_CNT_MERCHANT, (Object) uploadedVariables.D1D_CNT_MERCHANT) && equals((Object) this.D1D_CNT_FRIEND, (Object) uploadedVariables.D1D_CNT_FRIEND) && equals((Object) this.RT_SECURITYSAFE_1D_1MTH, (Object) uploadedVariables.RT_SECURITYSAFE_1D_1MTH) && equals((Object) this.RT_ACCTDTL_1D_1MTH, (Object) uploadedVariables.RT_ACCTDTL_1D_1MTH) && equals((Object) this.D1D_CNT_FIN, (Object) uploadedVariables.D1D_CNT_FIN) && equals((Object) this.D1D_MX_CARDFILL, (Object) uploadedVariables.D1D_MX_CARDFILL) && equals((Object) this.RT_CNT_RELATION_1D_1MTH, (Object) uploadedVariables.RT_CNT_RELATION_1D_1MTH) && equals((Object) this.RT_PAY_1D_1MTH, (Object) uploadedVariables.RT_PAY_1D_1MTH) && equals((Object) this.EDGE_RISK_DATA, (Object) uploadedVariables.EDGE_RISK_DATA);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((this.VERSION != null ? this.VERSION.hashCode() : 0) * 37) + (this.RT_FUND_1D_1MTH != null ? this.RT_FUND_1D_1MTH.hashCode() : 0)) * 37) + (this.D1D_CNT_HOME != null ? this.D1D_CNT_HOME.hashCode() : 0)) * 37) + (this.RT2_BILL_1H_1MTH != null ? this.RT2_BILL_1H_1MTH.hashCode() : 0)) * 37) + (this.D1H_ST_TRANSFERHOME != null ? this.D1H_ST_TRANSFERHOME.hashCode() : 0)) * 37) + (this.RT2_YEB_1D_1MTH != null ? this.RT2_YEB_1D_1MTH.hashCode() : 0)) * 37) + (this.RT_TALK_1D_1MTH != null ? this.RT_TALK_1D_1MTH.hashCode() : 0)) * 37) + (this.RT_FUNDCARD_1D_1MTH != null ? this.RT_FUNDCARD_1D_1MTH.hashCode() : 0)) * 37) + (this.RT_CNT_ASSET_1D_1MTH != null ? this.RT_CNT_ASSET_1D_1MTH.hashCode() : 0)) * 37) + (this.D1D_CNT_YEBHOME != null ? this.D1D_CNT_YEBHOME.hashCode() : 0)) * 37) + (this.DSM_FUNDSAFESECUR != null ? this.DSM_FUNDSAFESECUR.hashCode() : 0)) * 37) + (this.DSM_YEBFP != null ? this.DSM_YEBFP.hashCode() : 0)) * 37) + (this.D1D_CNT_SETTING != null ? this.D1D_CNT_SETTING.hashCode() : 0)) * 37) + (this.DSM_TRSFERCARD != null ? this.DSM_TRSFERCARD.hashCode() : 0)) * 37) + (this.D1D_CNT_MERCHANT != null ? this.D1D_CNT_MERCHANT.hashCode() : 0)) * 37) + (this.D1D_CNT_FRIEND != null ? this.D1D_CNT_FRIEND.hashCode() : 0)) * 37) + (this.RT_SECURITYSAFE_1D_1MTH != null ? this.RT_SECURITYSAFE_1D_1MTH.hashCode() : 0)) * 37) + (this.RT_ACCTDTL_1D_1MTH != null ? this.RT_ACCTDTL_1D_1MTH.hashCode() : 0)) * 37) + (this.D1D_CNT_FIN != null ? this.D1D_CNT_FIN.hashCode() : 0)) * 37) + (this.D1D_MX_CARDFILL != null ? this.D1D_MX_CARDFILL.hashCode() : 0)) * 37) + (this.RT_CNT_RELATION_1D_1MTH != null ? this.RT_CNT_RELATION_1D_1MTH.hashCode() : 0)) * 37) + (this.RT_PAY_1D_1MTH != null ? this.RT_PAY_1D_1MTH.hashCode() : 0)) * 37;
        if (this.EDGE_RISK_DATA != null) {
            i2 = this.EDGE_RISK_DATA.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
