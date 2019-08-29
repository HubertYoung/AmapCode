package com.alipay.mobileappcommon.biz.rpc.pginfo.model;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class PgTemplateInfoPB extends Message {
    public static final String DEFAULT_IMGURL = "";
    public static final Integer DEFAULT_MOBILEJUMPPAGE = Integer.valueOf(0);
    public static final String DEFAULT_MOBILEPGTEMPLATECODE = "";
    public static final String DEFAULT_MOBILEPGTEMPLATECODELIST = "";
    public static final String DEFAULT_MOBILETEMPLATECONFIGIMGURL = "";
    public static final String DEFAULT_PGACTION = "";
    public static final String DEFAULT_PGACTIONCONTENT = "";
    public static final String DEFAULT_PGCATEGORY = "";
    public static final String DEFAULT_PGCODE = "";
    public static final String DEFAULT_PGCONTENT = "";
    public static final String DEFAULT_PGCONTENTPGSUBTITLE = "";
    public static final String DEFAULT_PGCONTENTTITLE = "";
    public static final Integer DEFAULT_PGFATIGUELEVEL = Integer.valueOf(0);
    public static final Integer DEFAULT_PGPRIORITY = Integer.valueOf(0);
    public static final String DEFAULT_PGTIME = "";
    public static final Integer DEFAULT_PGTYPE = Integer.valueOf(0);
    public static final String DEFAULT_PLATFORM = "";
    public static final int TAG_IMGURL = 10;
    public static final int TAG_MOBILEJUMPPAGE = 17;
    public static final int TAG_MOBILEPGTEMPLATECODE = 1;
    public static final int TAG_MOBILEPGTEMPLATECODELIST = 14;
    public static final int TAG_MOBILETEMPLATECONFIGIMGURL = 16;
    public static final int TAG_PGACTION = 9;
    public static final int TAG_PGACTIONCONTENT = 15;
    public static final int TAG_PGCATEGORY = 4;
    public static final int TAG_PGCODE = 2;
    public static final int TAG_PGCONTENT = 11;
    public static final int TAG_PGCONTENTPGSUBTITLE = 13;
    public static final int TAG_PGCONTENTTITLE = 12;
    public static final int TAG_PGFATIGUELEVEL = 6;
    public static final int TAG_PGPRIORITY = 7;
    public static final int TAG_PGTIME = 8;
    public static final int TAG_PGTYPE = 3;
    public static final int TAG_PLATFORM = 5;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String imgUrl;
    @ProtoField(tag = 17, type = Datatype.INT32)
    public Integer mobileJumpPage;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String mobilePgTemplateCode;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String mobilePgTemplateCodeList;
    @ProtoField(tag = 16, type = Datatype.STRING)
    public String mobileTemplateconfigImgurl;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String pgAction;
    @ProtoField(tag = 15, type = Datatype.STRING)
    public String pgActionContent;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String pgCategory;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String pgCode;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String pgContent;
    @ProtoField(tag = 13, type = Datatype.STRING)
    public String pgContentPgSubTitle;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String pgContentTitle;
    @ProtoField(tag = 6, type = Datatype.INT32)
    public Integer pgFatigueLevel;
    @ProtoField(tag = 7, type = Datatype.INT32)
    public Integer pgPriority;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String pgTime;
    @ProtoField(tag = 3, type = Datatype.INT32)
    public Integer pgType;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String platform;

    public PgTemplateInfoPB() {
    }

    public PgTemplateInfoPB(PgTemplateInfoPB pgTemplateInfoPB) {
        super(pgTemplateInfoPB);
        if (pgTemplateInfoPB != null) {
            this.mobilePgTemplateCode = pgTemplateInfoPB.mobilePgTemplateCode;
            this.pgCode = pgTemplateInfoPB.pgCode;
            this.pgType = pgTemplateInfoPB.pgType;
            this.pgCategory = pgTemplateInfoPB.pgCategory;
            this.platform = pgTemplateInfoPB.platform;
            this.pgFatigueLevel = pgTemplateInfoPB.pgFatigueLevel;
            this.pgPriority = pgTemplateInfoPB.pgPriority;
            this.pgTime = pgTemplateInfoPB.pgTime;
            this.pgAction = pgTemplateInfoPB.pgAction;
            this.imgUrl = pgTemplateInfoPB.imgUrl;
            this.pgContent = pgTemplateInfoPB.pgContent;
            this.pgContentTitle = pgTemplateInfoPB.pgContentTitle;
            this.pgContentPgSubTitle = pgTemplateInfoPB.pgContentPgSubTitle;
            this.mobilePgTemplateCodeList = pgTemplateInfoPB.mobilePgTemplateCodeList;
            this.pgActionContent = pgTemplateInfoPB.pgActionContent;
            this.mobileTemplateconfigImgurl = pgTemplateInfoPB.mobileTemplateconfigImgurl;
            this.mobileJumpPage = pgTemplateInfoPB.mobileJumpPage;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PgTemplateInfoPB)) {
            return false;
        }
        PgTemplateInfoPB pgTemplateInfoPB = (PgTemplateInfoPB) obj;
        return equals((Object) this.mobilePgTemplateCode, (Object) pgTemplateInfoPB.mobilePgTemplateCode) && equals((Object) this.pgCode, (Object) pgTemplateInfoPB.pgCode) && equals((Object) this.pgType, (Object) pgTemplateInfoPB.pgType) && equals((Object) this.pgCategory, (Object) pgTemplateInfoPB.pgCategory) && equals((Object) this.platform, (Object) pgTemplateInfoPB.platform) && equals((Object) this.pgFatigueLevel, (Object) pgTemplateInfoPB.pgFatigueLevel) && equals((Object) this.pgPriority, (Object) pgTemplateInfoPB.pgPriority) && equals((Object) this.pgTime, (Object) pgTemplateInfoPB.pgTime) && equals((Object) this.pgAction, (Object) pgTemplateInfoPB.pgAction) && equals((Object) this.imgUrl, (Object) pgTemplateInfoPB.imgUrl) && equals((Object) this.pgContent, (Object) pgTemplateInfoPB.pgContent) && equals((Object) this.pgContentTitle, (Object) pgTemplateInfoPB.pgContentTitle) && equals((Object) this.pgContentPgSubTitle, (Object) pgTemplateInfoPB.pgContentPgSubTitle) && equals((Object) this.mobilePgTemplateCodeList, (Object) pgTemplateInfoPB.mobilePgTemplateCodeList) && equals((Object) this.pgActionContent, (Object) pgTemplateInfoPB.pgActionContent) && equals((Object) this.mobileTemplateconfigImgurl, (Object) pgTemplateInfoPB.mobileTemplateconfigImgurl) && equals((Object) this.mobileJumpPage, (Object) pgTemplateInfoPB.mobileJumpPage);
    }

    public final PgTemplateInfoPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.mobilePgTemplateCode = (String) obj;
                break;
            case 2:
                this.pgCode = (String) obj;
                break;
            case 3:
                this.pgType = (Integer) obj;
                break;
            case 4:
                this.pgCategory = (String) obj;
                break;
            case 5:
                this.platform = (String) obj;
                break;
            case 6:
                this.pgFatigueLevel = (Integer) obj;
                break;
            case 7:
                this.pgPriority = (Integer) obj;
                break;
            case 8:
                this.pgTime = (String) obj;
                break;
            case 9:
                this.pgAction = (String) obj;
                break;
            case 10:
                this.imgUrl = (String) obj;
                break;
            case 11:
                this.pgContent = (String) obj;
                break;
            case 12:
                this.pgContentTitle = (String) obj;
                break;
            case 13:
                this.pgContentPgSubTitle = (String) obj;
                break;
            case 14:
                this.mobilePgTemplateCodeList = (String) obj;
                break;
            case 15:
                this.pgActionContent = (String) obj;
                break;
            case 16:
                this.mobileTemplateconfigImgurl = (String) obj;
                break;
            case 17:
                this.mobileJumpPage = (Integer) obj;
                break;
        }
        return this;
    }

    public final int hashCode() {
        int i = 0;
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int hashCode = ((this.mobileTemplateconfigImgurl != null ? this.mobileTemplateconfigImgurl.hashCode() : 0) + (((this.pgActionContent != null ? this.pgActionContent.hashCode() : 0) + (((this.mobilePgTemplateCodeList != null ? this.mobilePgTemplateCodeList.hashCode() : 0) + (((this.pgContentPgSubTitle != null ? this.pgContentPgSubTitle.hashCode() : 0) + (((this.pgContentTitle != null ? this.pgContentTitle.hashCode() : 0) + (((this.pgContent != null ? this.pgContent.hashCode() : 0) + (((this.imgUrl != null ? this.imgUrl.hashCode() : 0) + (((this.pgAction != null ? this.pgAction.hashCode() : 0) + (((this.pgTime != null ? this.pgTime.hashCode() : 0) + (((this.pgPriority != null ? this.pgPriority.hashCode() : 0) + (((this.pgFatigueLevel != null ? this.pgFatigueLevel.hashCode() : 0) + (((this.platform != null ? this.platform.hashCode() : 0) + (((this.pgCategory != null ? this.pgCategory.hashCode() : 0) + (((this.pgType != null ? this.pgType.hashCode() : 0) + (((this.pgCode != null ? this.pgCode.hashCode() : 0) + ((this.mobilePgTemplateCode != null ? this.mobilePgTemplateCode.hashCode() : 0) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37;
        if (this.mobileJumpPage != null) {
            i = this.mobileJumpPage.hashCode();
        }
        int i3 = hashCode + i;
        this.hashCode = i3;
        return i3;
    }
}
