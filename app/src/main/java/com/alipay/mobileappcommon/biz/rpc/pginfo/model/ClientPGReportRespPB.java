package com.alipay.mobileappcommon.biz.rpc.pginfo.model;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class ClientPGReportRespPB extends Message {
    public static final Boolean DEFAULT_SUCCESS = Boolean.valueOf(false);
    public static final int TAG_SUCCESS = 1;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public ClientPGReportRespPB() {
    }

    public ClientPGReportRespPB(ClientPGReportRespPB clientPGReportRespPB) {
        super(clientPGReportRespPB);
        if (clientPGReportRespPB != null) {
            this.success = clientPGReportRespPB.success;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ClientPGReportRespPB)) {
            return false;
        }
        return equals((Object) this.success, (Object) ((ClientPGReportRespPB) obj).success);
    }

    public final ClientPGReportRespPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
        }
        return this;
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i == 0) {
            i = this.success != null ? this.success.hashCode() : 0;
            this.hashCode = i;
        }
        return i;
    }
}
