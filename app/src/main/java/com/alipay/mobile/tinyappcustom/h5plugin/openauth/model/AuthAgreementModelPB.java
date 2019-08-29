package com.alipay.mobile.tinyappcustom.h5plugin.openauth.model;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class AuthAgreementModelPB extends Message {
    public static final String DEFAULT_CONTENT = "";
    public static final String DEFAULT_LINK = "";
    public static final String DEFAULT_NAME = "";
    public static final int TAG_CONTENT = 3;
    public static final int TAG_LINK = 2;
    public static final int TAG_NAME = 1;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String content;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String link;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String name;

    public AuthAgreementModelPB(AuthAgreementModelPB message) {
        super(message);
        if (message != null) {
            this.name = message.name;
            this.link = message.link;
            this.content = message.content;
        }
    }

    public AuthAgreementModelPB() {
    }

    public final AuthAgreementModelPB fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.name = (String) value;
                break;
            case 2:
                this.link = (String) value;
                break;
            case 3:
                this.content = (String) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AuthAgreementModelPB)) {
            return false;
        }
        AuthAgreementModelPB o = (AuthAgreementModelPB) other;
        if (!equals((Object) this.name, (Object) o.name) || !equals((Object) this.link, (Object) o.link) || !equals((Object) this.content, (Object) o.content)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.name != null ? this.name.hashCode() : 0) * 37;
        if (this.link != null) {
            i = this.link.hashCode();
        } else {
            i = 0;
        }
        int i3 = (i + hashCode) * 37;
        if (this.content != null) {
            i2 = this.content.hashCode();
        }
        int result2 = i3 + i2;
        this.hashCode = result2;
        return result2;
    }
}
