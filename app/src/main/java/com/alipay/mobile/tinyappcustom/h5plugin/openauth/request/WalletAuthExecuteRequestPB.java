package com.alipay.mobile.tinyappcustom.h5plugin.openauth.request;

import com.alipay.mobile.tinyappcustom.h5plugin.openauth.AuthRequestContextPB;
import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class WalletAuthExecuteRequestPB extends Message {
    public static final String DEFAULT_APPID = "";
    public static final String DEFAULT_CURRENTPAGEURL = "";
    public static final String DEFAULT_FROMSYSTEM = "";
    public static final String DEFAULT_ISVAPPID = "";
    public static final List<String> DEFAULT_SCOPENICKS = Collections.emptyList();
    public static final String DEFAULT_STATE = "";
    public static final int TAG_APPEXTINFO = 9;
    public static final int TAG_APPID = 3;
    public static final int TAG_AUTHREQUESTCONTEXT = 2;
    public static final int TAG_CURRENTPAGEURL = 6;
    public static final int TAG_EXTINFO = 8;
    public static final int TAG_FROMSYSTEM = 1;
    public static final int TAG_ISVAPPID = 7;
    public static final int TAG_SCOPENICKS = 4;
    public static final int TAG_STATE = 5;
    @ProtoField(tag = 9)
    public MapStringString appExtInfo;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String appId;
    @ProtoField(tag = 2)
    public AuthRequestContextPB authRequestContext;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String currentPageUrl;
    @ProtoField(tag = 8)
    public MapStringString extInfo;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String fromSystem;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String isvAppId;
    @ProtoField(label = Label.REPEATED, tag = 4, type = Datatype.STRING)
    public List<String> scopeNicks;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String state;

    public WalletAuthExecuteRequestPB(WalletAuthExecuteRequestPB message) {
        super(message);
        if (message != null) {
            this.fromSystem = message.fromSystem;
            this.authRequestContext = message.authRequestContext;
            this.appId = message.appId;
            this.scopeNicks = copyOf(message.scopeNicks);
            this.state = message.state;
            this.currentPageUrl = message.currentPageUrl;
            this.isvAppId = message.isvAppId;
            this.extInfo = message.extInfo;
            this.appExtInfo = message.appExtInfo;
        }
    }

    public WalletAuthExecuteRequestPB() {
    }

    public final WalletAuthExecuteRequestPB fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.fromSystem = (String) value;
                break;
            case 2:
                this.authRequestContext = (AuthRequestContextPB) value;
                break;
            case 3:
                this.appId = (String) value;
                break;
            case 4:
                this.scopeNicks = immutableCopyOf((List) value);
                break;
            case 5:
                this.state = (String) value;
                break;
            case 6:
                this.currentPageUrl = (String) value;
                break;
            case 7:
                this.isvAppId = (String) value;
                break;
            case 8:
                this.extInfo = (MapStringString) value;
                break;
            case 9:
                this.appExtInfo = (MapStringString) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof WalletAuthExecuteRequestPB)) {
            return false;
        }
        WalletAuthExecuteRequestPB o = (WalletAuthExecuteRequestPB) other;
        if (!equals((Object) this.fromSystem, (Object) o.fromSystem) || !equals((Object) this.authRequestContext, (Object) o.authRequestContext) || !equals((Object) this.appId, (Object) o.appId) || !equals(this.scopeNicks, o.scopeNicks) || !equals((Object) this.state, (Object) o.state) || !equals((Object) this.currentPageUrl, (Object) o.currentPageUrl) || !equals((Object) this.isvAppId, (Object) o.isvAppId) || !equals((Object) this.extInfo, (Object) o.extInfo) || !equals((Object) this.appExtInfo, (Object) o.appExtInfo)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.fromSystem != null ? this.fromSystem.hashCode() : 0) * 37;
        if (this.authRequestContext != null) {
            i = this.authRequestContext.hashCode();
        } else {
            i = 0;
        }
        int i8 = (i + hashCode) * 37;
        if (this.appId != null) {
            i2 = this.appId.hashCode();
        } else {
            i2 = 0;
        }
        int hashCode2 = ((this.scopeNicks != null ? this.scopeNicks.hashCode() : 1) + ((i2 + i8) * 37)) * 37;
        if (this.state != null) {
            i3 = this.state.hashCode();
        } else {
            i3 = 0;
        }
        int i9 = (i3 + hashCode2) * 37;
        if (this.currentPageUrl != null) {
            i4 = this.currentPageUrl.hashCode();
        } else {
            i4 = 0;
        }
        int i10 = (i4 + i9) * 37;
        if (this.isvAppId != null) {
            i5 = this.isvAppId.hashCode();
        } else {
            i5 = 0;
        }
        int i11 = (i5 + i10) * 37;
        if (this.extInfo != null) {
            i6 = this.extInfo.hashCode();
        } else {
            i6 = 0;
        }
        int i12 = (i6 + i11) * 37;
        if (this.appExtInfo != null) {
            i7 = this.appExtInfo.hashCode();
        }
        int result2 = i12 + i7;
        this.hashCode = result2;
        return result2;
    }
}
