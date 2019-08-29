package com.alipay.android.phone.inside.commonbiz.report.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class DeviceLocationResPbPB extends Message {
    public static final String DEFAULT_CLIENTREPORTCONFIG = "";
    public static final String DEFAULT_ERRORMSG = "";
    public static final String DEFAULT_SERVERTIME = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.FALSE;
    public static final int TAG_CLIENTREPORTCONFIG = 3;
    public static final int TAG_ERRORMSG = 2;
    public static final int TAG_SERVERTIME = 4;
    public static final int TAG_SUCCESS = 1;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String clientReportConfig;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String errorMsg;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String serverTime;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public DeviceLocationResPbPB(DeviceLocationResPbPB deviceLocationResPbPB) {
        super(deviceLocationResPbPB);
        if (deviceLocationResPbPB != null) {
            this.success = deviceLocationResPbPB.success;
            this.errorMsg = deviceLocationResPbPB.errorMsg;
            this.clientReportConfig = deviceLocationResPbPB.clientReportConfig;
            this.serverTime = deviceLocationResPbPB.serverTime;
        }
    }

    public DeviceLocationResPbPB() {
    }

    public final DeviceLocationResPbPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.errorMsg = (String) obj;
                break;
            case 3:
                this.clientReportConfig = (String) obj;
                break;
            case 4:
                this.serverTime = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DeviceLocationResPbPB)) {
            return false;
        }
        DeviceLocationResPbPB deviceLocationResPbPB = (DeviceLocationResPbPB) obj;
        return equals((Object) this.success, (Object) deviceLocationResPbPB.success) && equals((Object) this.errorMsg, (Object) deviceLocationResPbPB.errorMsg) && equals((Object) this.clientReportConfig, (Object) deviceLocationResPbPB.clientReportConfig) && equals((Object) this.serverTime, (Object) deviceLocationResPbPB.serverTime);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((this.success != null ? this.success.hashCode() : 0) * 37) + (this.errorMsg != null ? this.errorMsg.hashCode() : 0)) * 37) + (this.clientReportConfig != null ? this.clientReportConfig.hashCode() : 0)) * 37;
        if (this.serverTime != null) {
            i2 = this.serverTime.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
