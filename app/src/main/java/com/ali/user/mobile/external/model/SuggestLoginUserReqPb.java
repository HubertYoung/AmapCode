package com.ali.user.mobile.external.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class SuggestLoginUserReqPb extends Message {
    public static final String DEFAULT_APDID = "";
    public static final List<String> DEFAULT_LOGINIDS = Collections.emptyList();
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_PRODUCTVERSION = "";
    public static final String DEFAULT_TID = "";
    public static final String DEFAULT_UMIDTOKEN = "";
    public static final String DEFAULT_UTDID = "";
    public static final int TAG_APDID = 4;
    public static final int TAG_LOGINIDS = 1;
    public static final int TAG_PRODUCTID = 2;
    public static final int TAG_PRODUCTVERSION = 3;
    public static final int TAG_TID = 5;
    public static final int TAG_UMIDTOKEN = 6;
    public static final int TAG_UTDID = 7;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String apdid;
    @ProtoField(label = Label.REPEATED, tag = 1, type = Datatype.STRING)
    public List<String> loginIds;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String tid;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String umidToken;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String utdid;

    public SuggestLoginUserReqPb(SuggestLoginUserReqPb suggestLoginUserReqPb) {
        super(suggestLoginUserReqPb);
        if (suggestLoginUserReqPb != null) {
            this.loginIds = copyOf(suggestLoginUserReqPb.loginIds);
            this.productId = suggestLoginUserReqPb.productId;
            this.productVersion = suggestLoginUserReqPb.productVersion;
            this.apdid = suggestLoginUserReqPb.apdid;
            this.tid = suggestLoginUserReqPb.tid;
            this.umidToken = suggestLoginUserReqPb.umidToken;
            this.utdid = suggestLoginUserReqPb.utdid;
        }
    }

    public SuggestLoginUserReqPb() {
    }

    public final SuggestLoginUserReqPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.loginIds = immutableCopyOf((List) obj);
                break;
            case 2:
                this.productId = (String) obj;
                break;
            case 3:
                this.productVersion = (String) obj;
                break;
            case 4:
                this.apdid = (String) obj;
                break;
            case 5:
                this.tid = (String) obj;
                break;
            case 6:
                this.umidToken = (String) obj;
                break;
            case 7:
                this.utdid = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SuggestLoginUserReqPb)) {
            return false;
        }
        SuggestLoginUserReqPb suggestLoginUserReqPb = (SuggestLoginUserReqPb) obj;
        return equals(this.loginIds, suggestLoginUserReqPb.loginIds) && equals((Object) this.productId, (Object) suggestLoginUserReqPb.productId) && equals((Object) this.productVersion, (Object) suggestLoginUserReqPb.productVersion) && equals((Object) this.apdid, (Object) suggestLoginUserReqPb.apdid) && equals((Object) this.tid, (Object) suggestLoginUserReqPb.tid) && equals((Object) this.umidToken, (Object) suggestLoginUserReqPb.umidToken) && equals((Object) this.utdid, (Object) suggestLoginUserReqPb.utdid);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((this.loginIds != null ? this.loginIds.hashCode() : 1) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37) + (this.productVersion != null ? this.productVersion.hashCode() : 0)) * 37) + (this.apdid != null ? this.apdid.hashCode() : 0)) * 37) + (this.tid != null ? this.tid.hashCode() : 0)) * 37) + (this.umidToken != null ? this.umidToken.hashCode() : 0)) * 37;
        if (this.utdid != null) {
            i2 = this.utdid.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
