package com.alipay.mobile.quinox.bundle.protobuf;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class ProtobufBundle extends Message {
    public static final List<String> DEFAULT_MCOMPONENTS = Collections.emptyList();
    public static final Boolean DEFAULT_MCONTAINCODE = Boolean.FALSE;
    public static final Boolean DEFAULT_MCONTAINRES = Boolean.FALSE;
    public static final List<String> DEFAULT_MDEPENDENCIES = Collections.emptyList();
    public static final List<String> DEFAULT_MEXPORTPACKAGES = Collections.emptyList();
    public static final Integer DEFAULT_MFORMAT = Integer.valueOf(0);
    public static final Integer DEFAULT_MINITLEVEL = Integer.valueOf(0);
    public static final String DEFAULT_MLOCATION = "";
    public static final String DEFAULT_MMD5 = "";
    public static final String DEFAULT_MNAME = "";
    public static final List<String> DEFAULT_MNATIVELIBS = Collections.emptyList();
    public static final Integer DEFAULT_MPACKAGEID = Integer.valueOf(0);
    public static final List<String> DEFAULT_MPACKAGENAMES = Collections.emptyList();
    public static final Long DEFAULT_MSIZE = Long.valueOf(0);
    public static final String DEFAULT_MVERSION = "";
    public static final int TAG_MCOMPONENTS = 13;
    public static final int TAG_MCONTAINCODE = 10;
    public static final int TAG_MCONTAINRES = 9;
    public static final int TAG_MDEPENDENCIES = 15;
    public static final int TAG_MEXPORTPACKAGES = 12;
    public static final int TAG_MFORMAT = 1;
    public static final int TAG_MINITLEVEL = 7;
    public static final int TAG_MLOCATION = 4;
    public static final int TAG_MMD5 = 6;
    public static final int TAG_MNAME = 2;
    public static final int TAG_MNATIVELIBS = 14;
    public static final int TAG_MPACKAGEID = 8;
    public static final int TAG_MPACKAGENAMES = 11;
    public static final int TAG_MSIZE = 5;
    public static final int TAG_MVERSION = 3;
    @ProtoField(label = Label.REPEATED, tag = 13, type = Datatype.STRING)
    public List<String> mComponents;
    @ProtoField(tag = 10, type = Datatype.BOOL)
    public Boolean mContainCode;
    @ProtoField(tag = 9, type = Datatype.BOOL)
    public Boolean mContainRes;
    @ProtoField(label = Label.REPEATED, tag = 15, type = Datatype.STRING)
    public List<String> mDependencies;
    @ProtoField(label = Label.REPEATED, tag = 12, type = Datatype.STRING)
    public List<String> mExportPackages;
    @ProtoField(tag = 1, type = Datatype.INT32)
    public Integer mFormat;
    @ProtoField(tag = 7, type = Datatype.INT32)
    public Integer mInitLevel;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String mLocation;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String mMD5;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String mName;
    @ProtoField(label = Label.REPEATED, tag = 14, type = Datatype.STRING)
    public List<String> mNativeLibs;
    @ProtoField(tag = 8, type = Datatype.INT32)
    public Integer mPackageId;
    @ProtoField(label = Label.REPEATED, tag = 11, type = Datatype.STRING)
    public List<String> mPackageNames;
    @ProtoField(tag = 5, type = Datatype.INT64)
    public Long mSize;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String mVersion;

    public ProtobufBundle(ProtobufBundle protobufBundle) {
        super(protobufBundle);
        if (protobufBundle != null) {
            this.mFormat = protobufBundle.mFormat;
            this.mName = protobufBundle.mName;
            this.mVersion = protobufBundle.mVersion;
            this.mLocation = protobufBundle.mLocation;
            this.mSize = protobufBundle.mSize;
            this.mMD5 = protobufBundle.mMD5;
            this.mInitLevel = protobufBundle.mInitLevel;
            this.mPackageId = protobufBundle.mPackageId;
            this.mContainRes = protobufBundle.mContainRes;
            this.mContainCode = protobufBundle.mContainCode;
            this.mPackageNames = copyOf(protobufBundle.mPackageNames);
            this.mExportPackages = copyOf(protobufBundle.mExportPackages);
            this.mComponents = copyOf(protobufBundle.mComponents);
            this.mNativeLibs = copyOf(protobufBundle.mNativeLibs);
            this.mDependencies = copyOf(protobufBundle.mDependencies);
        }
    }

    public ProtobufBundle() {
    }

    public final ProtobufBundle fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.mFormat = (Integer) obj;
                break;
            case 2:
                this.mName = (String) obj;
                break;
            case 3:
                this.mVersion = (String) obj;
                break;
            case 4:
                this.mLocation = (String) obj;
                break;
            case 5:
                this.mSize = (Long) obj;
                break;
            case 6:
                this.mMD5 = (String) obj;
                break;
            case 7:
                this.mInitLevel = (Integer) obj;
                break;
            case 8:
                this.mPackageId = (Integer) obj;
                break;
            case 9:
                this.mContainRes = (Boolean) obj;
                break;
            case 10:
                this.mContainCode = (Boolean) obj;
                break;
            case 11:
                this.mPackageNames = immutableCopyOf((List) obj);
                break;
            case 12:
                this.mExportPackages = immutableCopyOf((List) obj);
                break;
            case 13:
                this.mComponents = immutableCopyOf((List) obj);
                break;
            case 14:
                this.mNativeLibs = immutableCopyOf((List) obj);
                break;
            case 15:
                this.mDependencies = immutableCopyOf((List) obj);
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ProtobufBundle)) {
            return false;
        }
        ProtobufBundle protobufBundle = (ProtobufBundle) obj;
        return equals((Object) this.mFormat, (Object) protobufBundle.mFormat) && equals((Object) this.mName, (Object) protobufBundle.mName) && equals((Object) this.mVersion, (Object) protobufBundle.mVersion) && equals((Object) this.mLocation, (Object) protobufBundle.mLocation) && equals((Object) this.mSize, (Object) protobufBundle.mSize) && equals((Object) this.mMD5, (Object) protobufBundle.mMD5) && equals((Object) this.mInitLevel, (Object) protobufBundle.mInitLevel) && equals((Object) this.mPackageId, (Object) protobufBundle.mPackageId) && equals((Object) this.mContainRes, (Object) protobufBundle.mContainRes) && equals((Object) this.mContainCode, (Object) protobufBundle.mContainCode) && equals(this.mPackageNames, protobufBundle.mPackageNames) && equals(this.mExportPackages, protobufBundle.mExportPackages) && equals(this.mComponents, protobufBundle.mComponents) && equals(this.mNativeLibs, protobufBundle.mNativeLibs) && equals(this.mDependencies, protobufBundle.mDependencies);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((this.mFormat != null ? this.mFormat.hashCode() : 0) * 37) + (this.mName != null ? this.mName.hashCode() : 0)) * 37) + (this.mVersion != null ? this.mVersion.hashCode() : 0)) * 37) + (this.mLocation != null ? this.mLocation.hashCode() : 0)) * 37) + (this.mSize != null ? this.mSize.hashCode() : 0)) * 37) + (this.mMD5 != null ? this.mMD5.hashCode() : 0)) * 37) + (this.mInitLevel != null ? this.mInitLevel.hashCode() : 0)) * 37) + (this.mPackageId != null ? this.mPackageId.hashCode() : 0)) * 37) + (this.mContainRes != null ? this.mContainRes.hashCode() : 0)) * 37;
        if (this.mContainCode != null) {
            i2 = this.mContainCode.hashCode();
        }
        int i3 = 1;
        int hashCode2 = (((((((((hashCode + i2) * 37) + (this.mPackageNames != null ? this.mPackageNames.hashCode() : 1)) * 37) + (this.mExportPackages != null ? this.mExportPackages.hashCode() : 1)) * 37) + (this.mComponents != null ? this.mComponents.hashCode() : 1)) * 37) + (this.mNativeLibs != null ? this.mNativeLibs.hashCode() : 1)) * 37;
        if (this.mDependencies != null) {
            i3 = this.mDependencies.hashCode();
        }
        int i4 = hashCode2 + i3;
        this.hashCode = i4;
        return i4;
    }
}
