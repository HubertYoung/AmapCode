package com.alipay.apmobilesecuritysdk.rpc.gen;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class ReportResult extends Message {
    public static final String DEFAULT_EXTRESULTDATA = "";
    public static final String DEFAULT_RESULTCODE = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.FALSE;
    public static final int TAG_EXTRESULTDATA = 4;
    public static final int TAG_RESULTCODE = 2;
    public static final int TAG_RESULTDATA = 3;
    public static final int TAG_SUCCESS = 1;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String extResultData;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String resultCode;
    @ProtoField(tag = 3)
    public ResultData resultData;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public ReportResult(ReportResult reportResult) {
        super(reportResult);
        if (reportResult != null) {
            this.success = reportResult.success;
            this.resultCode = reportResult.resultCode;
            this.resultData = reportResult.resultData;
            this.extResultData = reportResult.extResultData;
        }
    }

    public ReportResult() {
    }

    public final ReportResult fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.resultCode = (String) obj;
                break;
            case 3:
                this.resultData = (ResultData) obj;
                break;
            case 4:
                this.extResultData = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ReportResult)) {
            return false;
        }
        ReportResult reportResult = (ReportResult) obj;
        return equals((Object) this.success, (Object) reportResult.success) && equals((Object) this.resultCode, (Object) reportResult.resultCode) && equals((Object) this.resultData, (Object) reportResult.resultData) && equals((Object) this.extResultData, (Object) reportResult.extResultData);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((this.success != null ? this.success.hashCode() : 0) * 37) + (this.resultCode != null ? this.resultCode.hashCode() : 0)) * 37) + (this.resultData != null ? this.resultData.hashCode() : 0)) * 37;
        if (this.extResultData != null) {
            i2 = this.extResultData.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
