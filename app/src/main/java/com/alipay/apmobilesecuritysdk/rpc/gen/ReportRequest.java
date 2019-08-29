package com.alipay.apmobilesecuritysdk.rpc.gen;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class ReportRequest extends Message {
    public static final String DEFAULT_EXTBIZDATA = "";
    public static final String DEFAULT_EXTDEVICEDATA = "";
    public static final int TAG_BIZDATA = 1;
    public static final int TAG_DEVICEDATA = 2;
    public static final int TAG_EXTBIZDATA = 3;
    public static final int TAG_EXTDEVICEDATA = 4;
    @ProtoField(tag = 1)
    public BizData bizData;
    @ProtoField(tag = 2)
    public DeviceData deviceData;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String extBizData;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String extDeviceData;

    public ReportRequest(ReportRequest reportRequest) {
        super(reportRequest);
        if (reportRequest != null) {
            this.bizData = reportRequest.bizData;
            this.deviceData = reportRequest.deviceData;
            this.extBizData = reportRequest.extBizData;
            this.extDeviceData = reportRequest.extDeviceData;
        }
    }

    public ReportRequest() {
    }

    public final ReportRequest fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.bizData = (BizData) obj;
                break;
            case 2:
                this.deviceData = (DeviceData) obj;
                break;
            case 3:
                this.extBizData = (String) obj;
                break;
            case 4:
                this.extDeviceData = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ReportRequest)) {
            return false;
        }
        ReportRequest reportRequest = (ReportRequest) obj;
        return equals((Object) this.bizData, (Object) reportRequest.bizData) && equals((Object) this.deviceData, (Object) reportRequest.deviceData) && equals((Object) this.extBizData, (Object) reportRequest.extBizData) && equals((Object) this.extDeviceData, (Object) reportRequest.extDeviceData);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((this.bizData != null ? this.bizData.hashCode() : 0) * 37) + (this.deviceData != null ? this.deviceData.hashCode() : 0)) * 37) + (this.extBizData != null ? this.extBizData.hashCode() : 0)) * 37;
        if (this.extDeviceData != null) {
            i2 = this.extDeviceData.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
