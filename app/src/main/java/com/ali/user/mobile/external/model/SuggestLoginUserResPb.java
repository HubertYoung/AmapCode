package com.ali.user.mobile.external.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class SuggestLoginUserResPb extends Message {
    public static final List<ExternMap> DEFAULT_CLIENTCONFIGMAP = Collections.emptyList();
    public static final String DEFAULT_LOGINID = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.FALSE;
    public static final int TAG_CLIENTCONFIGMAP = 3;
    public static final int TAG_LOGINID = 2;
    public static final int TAG_SUCCESS = 1;
    @ProtoField(label = Label.REPEATED, tag = 3)
    public List<ExternMap> clientConfigMap;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String loginId;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public SuggestLoginUserResPb(SuggestLoginUserResPb suggestLoginUserResPb) {
        super(suggestLoginUserResPb);
        if (suggestLoginUserResPb != null) {
            this.success = suggestLoginUserResPb.success;
            this.loginId = suggestLoginUserResPb.loginId;
            this.clientConfigMap = copyOf(suggestLoginUserResPb.clientConfigMap);
        }
    }

    public SuggestLoginUserResPb() {
    }

    public final SuggestLoginUserResPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.loginId = (String) obj;
                break;
            case 3:
                this.clientConfigMap = immutableCopyOf((List) obj);
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SuggestLoginUserResPb)) {
            return false;
        }
        SuggestLoginUserResPb suggestLoginUserResPb = (SuggestLoginUserResPb) obj;
        return equals((Object) this.success, (Object) suggestLoginUserResPb.success) && equals((Object) this.loginId, (Object) suggestLoginUserResPb.loginId) && equals(this.clientConfigMap, suggestLoginUserResPb.clientConfigMap);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (this.success != null ? this.success.hashCode() : 0) * 37;
        if (this.loginId != null) {
            i2 = this.loginId.hashCode();
        }
        int hashCode2 = ((hashCode + i2) * 37) + (this.clientConfigMap != null ? this.clientConfigMap.hashCode() : 1);
        this.hashCode = hashCode2;
        return hashCode2;
    }
}
