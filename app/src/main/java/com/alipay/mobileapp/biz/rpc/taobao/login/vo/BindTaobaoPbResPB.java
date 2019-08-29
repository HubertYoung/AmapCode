package com.alipay.mobileapp.biz.rpc.taobao.login.vo;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class BindTaobaoPbResPB extends Message {
    public static final String DEFAULT_BTNMEMO = "";
    public static final String DEFAULT_H5URL = "";
    public static final String DEFAULT_MEMO = "";
    public static final String DEFAULT_RESULTSTATUS = "";
    public static final String DEFAULT_TAOBAOID = "";
    public static final String DEFAULT_TXTMEMO = "";
    public static final int TAG_BTNMEMO = 5;
    public static final int TAG_H5URL = 4;
    public static final int TAG_MEMO = 2;
    public static final int TAG_RESULTSTATUS = 1;
    public static final int TAG_TAOBAOID = 3;
    public static final int TAG_TXTMEMO = 6;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String btnMemo;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String h5Url;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String memo;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String resultStatus;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String taobaoId;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String txtMemo;

    public BindTaobaoPbResPB(BindTaobaoPbResPB bindTaobaoPbResPB) {
        super(bindTaobaoPbResPB);
        if (bindTaobaoPbResPB != null) {
            this.resultStatus = bindTaobaoPbResPB.resultStatus;
            this.memo = bindTaobaoPbResPB.memo;
            this.taobaoId = bindTaobaoPbResPB.taobaoId;
            this.h5Url = bindTaobaoPbResPB.h5Url;
            this.btnMemo = bindTaobaoPbResPB.btnMemo;
            this.txtMemo = bindTaobaoPbResPB.txtMemo;
        }
    }

    public BindTaobaoPbResPB() {
    }

    public final BindTaobaoPbResPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.resultStatus = (String) obj;
                break;
            case 2:
                this.memo = (String) obj;
                break;
            case 3:
                this.taobaoId = (String) obj;
                break;
            case 4:
                this.h5Url = (String) obj;
                break;
            case 5:
                this.btnMemo = (String) obj;
                break;
            case 6:
                this.txtMemo = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BindTaobaoPbResPB)) {
            return false;
        }
        BindTaobaoPbResPB bindTaobaoPbResPB = (BindTaobaoPbResPB) obj;
        return equals((Object) this.resultStatus, (Object) bindTaobaoPbResPB.resultStatus) && equals((Object) this.memo, (Object) bindTaobaoPbResPB.memo) && equals((Object) this.taobaoId, (Object) bindTaobaoPbResPB.taobaoId) && equals((Object) this.h5Url, (Object) bindTaobaoPbResPB.h5Url) && equals((Object) this.btnMemo, (Object) bindTaobaoPbResPB.btnMemo) && equals((Object) this.txtMemo, (Object) bindTaobaoPbResPB.txtMemo);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((this.resultStatus != null ? this.resultStatus.hashCode() : 0) * 37) + (this.memo != null ? this.memo.hashCode() : 0)) * 37) + (this.taobaoId != null ? this.taobaoId.hashCode() : 0)) * 37) + (this.h5Url != null ? this.h5Url.hashCode() : 0)) * 37) + (this.btnMemo != null ? this.btnMemo.hashCode() : 0)) * 37;
        if (this.txtMemo != null) {
            i2 = this.txtMemo.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
