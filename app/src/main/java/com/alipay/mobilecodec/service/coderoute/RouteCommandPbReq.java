package com.alipay.mobilecodec.service.coderoute;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class RouteCommandPbReq extends Message {
    public static final String DEFAULT_CHANNELID = "";
    public static final String DEFAULT_CLIENTCONTEXT = "";
    public static final String DEFAULT_CODETYPE = "";
    public static final String DEFAULT_EXTDATA = "";
    public static final String DEFAULT_TOKEN = "";
    public static final int TAG_CHANNELID = 3;
    public static final int TAG_CLIENTCONTEXT = 5;
    public static final int TAG_CODETYPE = 2;
    public static final int TAG_EXTDATA = 4;
    public static final int TAG_TOKEN = 1;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String channelId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String clientContext;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String codeType;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String extData;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String token;

    public RouteCommandPbReq(RouteCommandPbReq message) {
        super(message);
        if (message != null) {
            this.token = message.token;
            this.codeType = message.codeType;
            this.channelId = message.channelId;
            this.extData = message.extData;
            this.clientContext = message.clientContext;
        }
    }

    public RouteCommandPbReq() {
    }

    public RouteCommandPbReq fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.token = (String) value;
                break;
            case 2:
                this.codeType = (String) value;
                break;
            case 3:
                this.channelId = (String) value;
                break;
            case 4:
                this.extData = (String) value;
                break;
            case 5:
                this.clientContext = (String) value;
                break;
        }
        return this;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RouteCommandPbReq)) {
            return false;
        }
        RouteCommandPbReq o = (RouteCommandPbReq) other;
        if (!equals((Object) this.token, (Object) o.token) || !equals((Object) this.codeType, (Object) o.codeType) || !equals((Object) this.channelId, (Object) o.channelId) || !equals((Object) this.extData, (Object) o.extData) || !equals((Object) this.clientContext, (Object) o.clientContext)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.token != null ? this.token.hashCode() : 0) * 37;
        if (this.codeType != null) {
            i = this.codeType.hashCode();
        } else {
            i = 0;
        }
        int i5 = (hashCode + i) * 37;
        if (this.channelId != null) {
            i2 = this.channelId.hashCode();
        } else {
            i2 = 0;
        }
        int i6 = (i5 + i2) * 37;
        if (this.extData != null) {
            i3 = this.extData.hashCode();
        } else {
            i3 = 0;
        }
        int i7 = (i6 + i3) * 37;
        if (this.clientContext != null) {
            i4 = this.clientContext.hashCode();
        }
        int result2 = i7 + i4;
        this.hashCode = result2;
        return result2;
    }
}
