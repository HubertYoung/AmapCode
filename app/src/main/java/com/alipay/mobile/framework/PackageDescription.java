package com.alipay.mobile.framework;

import android.text.TextUtils;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PackageDescription extends MicroDescription<PackageDescription> {
    public static final String TYPE_LAZY_BUNDLE = "lazy_bundle";
    public static final String TYPE_PACKAGE_NAME = "package_name";
    private String[] a;

    public PackageDescription() {
        super(0);
    }

    public String[] getInfo() {
        return this.a;
    }

    public void setInfo(String[] info) {
        this.a = info;
    }

    public Set<String> getInfoSet() {
        String[] strArr;
        Set infoSet = new HashSet();
        if (this.a != null && this.a.length > 0) {
            for (String info : this.a) {
                if (!TextUtils.isEmpty(info)) {
                    infoSet.add(info);
                }
            }
        }
        return infoSet;
    }

    public String toString() {
        return "PackageDescription [mInfo=" + Arrays.toString(this.a) + "]";
    }

    public PackageDescription serialize(BufferedOutputStream bos) {
        super.serialize(bos);
        ByteOrderDataUtil.writeStringArray(bos, this.a);
        return this;
    }

    public PackageDescription deserialize(BufferedInputStream bis) {
        super.deserialize(bis);
        this.a = ByteOrderDataUtil.readStringArray(bis);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o) || !(o instanceof PackageDescription)) {
            return false;
        }
        if (this.a == null && ((PackageDescription) o).a == null) {
            return true;
        }
        if (this.a == null || ((PackageDescription) o).a == null || !Arrays.equals(this.a, ((PackageDescription) o).a)) {
            return false;
        }
        return true;
    }
}
