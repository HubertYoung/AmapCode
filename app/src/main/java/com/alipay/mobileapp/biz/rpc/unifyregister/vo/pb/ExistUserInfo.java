package com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.List;

public final class ExistUserInfo extends Message {
    public static final int TAG_ACCOUNTTYPE = 1;
    public static final int TAG_AVATAR = 6;
    public static final int TAG_BUTTONFSTMEMO = 11;
    public static final int TAG_BUTTONSEDMEMO = 12;
    public static final int TAG_CERTNO = 5;
    public static final int TAG_DISPLAYTAGS = 9;
    public static final int TAG_IDCARD = 4;
    public static final int TAG_LOGONAPPS = 8;
    public static final int TAG_REALNAME = 7;
    public static final int TAG_REALNAMED = 3;
    public static final int TAG_REGTIME = 10;
    public static final int TAG_TAOBAONICK = 2;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String ButtonFstMemo;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String ButtonSedMemo;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String accountType;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String avatar;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String certNo;
    @ProtoField(label = Label.REPEATED, tag = 9, type = Datatype.STRING)
    public List<String> displayTags;
    @ProtoField(tag = 4, type = Datatype.BOOL)
    public Boolean idCard;
    @ProtoField(label = Label.REPEATED, tag = 8, type = Datatype.STRING)
    public List<String> logonApps;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String realName;
    @ProtoField(tag = 3, type = Datatype.BOOL)
    public Boolean realNamed;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String regTime;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String taobaoNick;

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ExistUserInfo)) {
            return false;
        }
        ExistUserInfo existUserInfo = (ExistUserInfo) obj;
        return equals((Object) this.accountType, (Object) existUserInfo.accountType) && equals((Object) this.taobaoNick, (Object) existUserInfo.taobaoNick) && equals((Object) this.realNamed, (Object) existUserInfo.realNamed) && equals((Object) this.idCard, (Object) existUserInfo.idCard) && equals((Object) this.certNo, (Object) existUserInfo.certNo) && equals((Object) this.avatar, (Object) existUserInfo.avatar) && equals((Object) this.realName, (Object) existUserInfo.realName) && equals(this.logonApps, existUserInfo.logonApps) && equals(this.displayTags, existUserInfo.displayTags) && equals((Object) this.regTime, (Object) existUserInfo.regTime) && equals((Object) this.ButtonFstMemo, (Object) existUserInfo.ButtonFstMemo) && equals((Object) this.ButtonSedMemo, (Object) existUserInfo.ButtonSedMemo);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int i3 = 1;
        int hashCode = (((((((((((((((this.accountType != null ? this.accountType.hashCode() : 0) * 37) + (this.taobaoNick != null ? this.taobaoNick.hashCode() : 0)) * 37) + (this.realNamed != null ? this.realNamed.hashCode() : 0)) * 37) + (this.idCard != null ? this.idCard.hashCode() : 0)) * 37) + (this.certNo != null ? this.certNo.hashCode() : 0)) * 37) + (this.avatar != null ? this.avatar.hashCode() : 0)) * 37) + (this.realName != null ? this.realName.hashCode() : 0)) * 37) + (this.logonApps != null ? this.logonApps.hashCode() : 1)) * 37;
        if (this.displayTags != null) {
            i3 = this.displayTags.hashCode();
        }
        int hashCode2 = (((((hashCode + i3) * 37) + (this.regTime != null ? this.regTime.hashCode() : 0)) * 37) + (this.ButtonFstMemo != null ? this.ButtonFstMemo.hashCode() : 0)) * 37;
        if (this.ButtonSedMemo != null) {
            i2 = this.ButtonSedMemo.hashCode();
        }
        int i4 = hashCode2 + i2;
        this.hashCode = i4;
        return i4;
    }
}
