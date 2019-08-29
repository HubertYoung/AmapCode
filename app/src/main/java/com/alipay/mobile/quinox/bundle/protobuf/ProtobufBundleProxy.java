package com.alipay.mobile.quinox.bundle.protobuf;

import com.alipay.mobile.quinox.bundle.IBundle;
import java.util.ArrayList;
import java.util.List;

public final class ProtobufBundleProxy implements IBundle<ProtobufBundleProxy> {
    private ProtobufBundle protobufBundle;

    public ProtobufBundleProxy(IBundle iBundle) {
        this.protobufBundle = new ProtobufBundle();
        this.protobufBundle.mFormat = Integer.valueOf(iBundle.getVERSION());
        this.protobufBundle.mName = iBundle.getName();
        this.protobufBundle.mVersion = iBundle.getVersion();
        this.protobufBundle.mLocation = iBundle.getLocation();
        this.protobufBundle.mSize = Long.valueOf(iBundle.getSize());
        this.protobufBundle.mMD5 = iBundle.getMD5();
        this.protobufBundle.mInitLevel = Integer.valueOf(iBundle.getInitLevel());
        this.protobufBundle.mPackageId = Integer.valueOf(iBundle.getPackageId());
        this.protobufBundle.mContainRes = Boolean.valueOf(iBundle.containRes());
        this.protobufBundle.mContainCode = Boolean.valueOf(iBundle.containCode());
        if (iBundle.getPackageNames() != null) {
            this.protobufBundle.mPackageNames = new ArrayList(iBundle.getPackageNames());
        }
        if (iBundle.getExportPackages() != null) {
            this.protobufBundle.mExportPackages = new ArrayList(iBundle.getExportPackages());
        }
        if (iBundle.getComponents() != null) {
            this.protobufBundle.mComponents = new ArrayList(iBundle.getComponents());
        }
        if (iBundle.getNativeLibs() != null) {
            this.protobufBundle.mNativeLibs = new ArrayList(iBundle.getNativeLibs());
        }
        if (iBundle.getDependencies() != null) {
            this.protobufBundle.mDependencies = new ArrayList(iBundle.getDependencies());
        }
    }

    public ProtobufBundleProxy() {
        this.protobufBundle = new ProtobufBundle();
    }

    public ProtobufBundleProxy(ProtobufBundle protobufBundle2) {
        this.protobufBundle = protobufBundle2;
    }

    public final ProtobufBundle get() {
        return this.protobufBundle;
    }

    public final String getLocation() {
        return this.protobufBundle.mLocation;
    }

    public final ProtobufBundleProxy setLocation(String str) {
        this.protobufBundle.mLocation = str;
        return this;
    }

    public final String getName() {
        return this.protobufBundle.mName;
    }

    public final ProtobufBundleProxy setName(String str) {
        this.protobufBundle.mName = str;
        return this;
    }

    public final String getVersion() {
        return this.protobufBundle.mVersion;
    }

    public final ProtobufBundleProxy setVersion(String str) {
        this.protobufBundle.mVersion = str;
        return this;
    }

    public final int getInitLevel() {
        if (this.protobufBundle.mInitLevel == null) {
            return Integer.MAX_VALUE;
        }
        return this.protobufBundle.mInitLevel.intValue();
    }

    public final ProtobufBundleProxy setInitLevel(int i) {
        this.protobufBundle.mInitLevel = Integer.valueOf(i);
        return this;
    }

    public final List<String> getPackageNames() {
        return this.protobufBundle.mPackageNames;
    }

    public final ProtobufBundleProxy setPackageNames(List<String> list) {
        this.protobufBundle.mPackageNames = list;
        return this;
    }

    public final List<String> getExportPackages() {
        return this.protobufBundle.mExportPackages;
    }

    public final ProtobufBundleProxy setExportPackages(List<String> list) {
        this.protobufBundle.mExportPackages = list;
        return this;
    }

    public final List<String> getComponents() {
        return this.protobufBundle.mComponents;
    }

    public final ProtobufBundleProxy setComponents(List<String> list) {
        this.protobufBundle.mComponents = list;
        return this;
    }

    public final int getPackageId() {
        if (this.protobufBundle.mPackageId == null) {
            return 127;
        }
        return this.protobufBundle.mPackageId.intValue();
    }

    public final ProtobufBundleProxy setPackageId(int i) {
        this.protobufBundle.mPackageId = Integer.valueOf(i);
        return this;
    }

    public final boolean containRes() {
        return this.protobufBundle.mContainRes != null && this.protobufBundle.mContainRes.booleanValue();
    }

    public final ProtobufBundleProxy setContainRes(boolean z) {
        this.protobufBundle.mContainRes = Boolean.valueOf(z);
        return this;
    }

    public final boolean containCode() {
        return this.protobufBundle.mContainCode != null && this.protobufBundle.mContainCode.booleanValue();
    }

    public final ProtobufBundleProxy setContainCode(boolean z) {
        this.protobufBundle.mContainCode = Boolean.valueOf(z);
        return this;
    }

    public final List<String> getNativeLibs() {
        return this.protobufBundle.mNativeLibs;
    }

    public final ProtobufBundleProxy setNativeLibs(List<String> list) {
        this.protobufBundle.mNativeLibs = list;
        return this;
    }

    public final List<String> getDependencies() {
        return this.protobufBundle.mDependencies;
    }

    public final ProtobufBundleProxy setDependencies(List<String> list) {
        this.protobufBundle.mDependencies = list;
        return this;
    }

    public final long getSize() {
        if (this.protobufBundle.mSize == null) {
            return 0;
        }
        return this.protobufBundle.mSize.longValue();
    }

    public final ProtobufBundleProxy setSize(long j) {
        this.protobufBundle.mSize = Long.valueOf(j);
        return this;
    }

    public final long getAdler32Sum() {
        if (this.protobufBundle.mSize == null) {
            return 0;
        }
        return this.protobufBundle.mSize.longValue();
    }

    public final ProtobufBundleProxy setAdler32Sum(long j) {
        this.protobufBundle.mSize = Long.valueOf(j);
        return this;
    }

    public final String getMD5() {
        return this.protobufBundle.mMD5;
    }

    public final ProtobufBundleProxy setMD5(String str) {
        this.protobufBundle.mMD5 = str;
        return this;
    }

    public final int getVERSION() {
        if (this.protobufBundle.mFormat == null) {
            return 0;
        }
        return this.protobufBundle.mFormat.intValue();
    }

    public final ProtobufBundleProxy setVERSION(int i) {
        this.protobufBundle.mFormat = Integer.valueOf(i);
        return this;
    }
}
