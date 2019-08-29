package com.alipay.mobile.quinox.bundle.protobuf;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class ProtobufBundleCfg extends Message {
    public static final List<ProtobufBundle> DEFAULT_MALLBUNDLES = Collections.emptyList();
    public static final List<String> DEFAULT_MSTATICLINKBUNDLENAMES = Collections.emptyList();
    public static final String DEFAULT_MVERSIONNAME = "";
    public static final int TAG_MALLBUNDLES = 3;
    public static final int TAG_MSTATICLINKBUNDLENAMES = 2;
    public static final int TAG_MVERSIONNAME = 1;
    @ProtoField(label = Label.REPEATED, tag = 3)
    public List<ProtobufBundle> mAllBundles;
    @ProtoField(label = Label.REPEATED, tag = 2, type = Datatype.STRING)
    public List<String> mStaticLinkBundleNames;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String mVersionName;

    public ProtobufBundleCfg(ProtobufBundleCfg protobufBundleCfg) {
        super(protobufBundleCfg);
        if (protobufBundleCfg != null) {
            this.mVersionName = protobufBundleCfg.mVersionName;
            this.mStaticLinkBundleNames = copyOf(protobufBundleCfg.mStaticLinkBundleNames);
            this.mAllBundles = copyOf(protobufBundleCfg.mAllBundles);
        }
    }

    public ProtobufBundleCfg() {
    }

    public final ProtobufBundleCfg fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.mVersionName = (String) obj;
                break;
            case 2:
                this.mStaticLinkBundleNames = immutableCopyOf((List) obj);
                break;
            case 3:
                this.mAllBundles = immutableCopyOf((List) obj);
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ProtobufBundleCfg)) {
            return false;
        }
        ProtobufBundleCfg protobufBundleCfg = (ProtobufBundleCfg) obj;
        return equals((Object) this.mVersionName, (Object) protobufBundleCfg.mVersionName) && equals(this.mStaticLinkBundleNames, protobufBundleCfg.mStaticLinkBundleNames) && equals(this.mAllBundles, protobufBundleCfg.mAllBundles);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 1;
        int hashCode = (((this.mVersionName != null ? this.mVersionName.hashCode() : 0) * 37) + (this.mStaticLinkBundleNames != null ? this.mStaticLinkBundleNames.hashCode() : 1)) * 37;
        if (this.mAllBundles != null) {
            i2 = this.mAllBundles.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
