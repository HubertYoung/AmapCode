package com.alipay.mobile.quinox.bundle.bytedata;

import com.alipay.mobile.quinox.bundle.IBundle;
import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

public class ByteDataBundle implements IBundle<ByteDataBundle> {
    long mAdler32Sum = -1;
    List<String> mComponents;
    boolean mContainCode;
    boolean mContainRes;
    List<String> mDependencies;
    List<String> mExportPackages;
    int mInitLevel = Integer.MAX_VALUE;
    String mLocation;
    String mMD5;
    String mName;
    List<String> mNativeLibs;
    int mPackageId = 127;
    List<String> mPackageNames;
    String mVersion;
    private int version = 5;

    public ByteDataBundle() {
    }

    public ByteDataBundle(IBundle iBundle) {
        this.version = iBundle.getVERSION();
        this.mName = iBundle.getName();
        this.mVersion = iBundle.getVersion();
        this.mInitLevel = iBundle.getInitLevel();
        this.mLocation = iBundle.getLocation();
        this.mAdler32Sum = iBundle.getAdler32Sum();
        this.mPackageNames = iBundle.getPackageNames();
        this.mExportPackages = iBundle.getExportPackages();
        this.mComponents = iBundle.getComponents();
        this.mPackageId = iBundle.getPackageId();
        this.mContainRes = iBundle.containRes();
        this.mContainCode = iBundle.containCode();
        this.mNativeLibs = iBundle.getNativeLibs();
        this.mDependencies = iBundle.getDependencies();
        this.mMD5 = iBundle.getMD5();
    }

    public ByteDataBundle read(BufferedInputStream bufferedInputStream) throws IOException {
        if (bufferedInputStream == null) {
            throw new IOException("null == inputStream");
        }
        this.version = ByteOrderDataUtil.readInt(bufferedInputStream);
        if (this.version >= 5) {
            this.mName = ByteOrderDataUtil.readString2(bufferedInputStream);
        } else {
            this.mName = ByteOrderDataUtil.readString(bufferedInputStream);
        }
        if (this.version >= 5) {
            this.mVersion = ByteOrderDataUtil.readString2(bufferedInputStream);
        } else {
            this.mVersion = ByteOrderDataUtil.readString(bufferedInputStream);
        }
        this.mInitLevel = ByteOrderDataUtil.readInt(bufferedInputStream);
        if (this.version >= 5) {
            this.mPackageNames = ByteOrderDataUtil.readStringList2(bufferedInputStream);
        } else {
            this.mPackageNames = ByteOrderDataUtil.readStringList(bufferedInputStream);
        }
        if (this.version >= 5) {
            this.mComponents = ByteOrderDataUtil.readStringList2(bufferedInputStream);
        } else {
            this.mComponents = ByteOrderDataUtil.readStringList(bufferedInputStream);
        }
        if (this.version >= 2) {
            this.mPackageId = ByteOrderDataUtil.readInt(bufferedInputStream);
        } else {
            String readString = ByteOrderDataUtil.readString(bufferedInputStream);
            if (!StringUtil.isEmpty(readString)) {
                try {
                    this.mPackageId = Integer.parseInt(readString);
                } catch (Throwable unused) {
                    StringBuilder sb = new StringBuilder("Wrong packageId : ");
                    sb.append(readString);
                    sb.append(": mName=");
                    sb.append(this.mName);
                    sb.append(", version=");
                    sb.append(this.version);
                    Log.w((String) IBundle.TAG, sb.toString());
                }
            }
            this.mPackageId = 127;
        }
        if (this.version >= 5) {
            this.mContainCode = ByteOrderDataUtil.readBoolean2(bufferedInputStream);
        } else {
            this.mContainCode = ByteOrderDataUtil.readBoolean(bufferedInputStream);
        }
        if (this.version >= 5) {
            this.mContainRes = ByteOrderDataUtil.readBoolean2(bufferedInputStream);
        } else {
            this.mContainRes = ByteOrderDataUtil.readBoolean(bufferedInputStream);
        }
        if (this.version >= 5) {
            this.mNativeLibs = ByteOrderDataUtil.readStringList2(bufferedInputStream);
        } else {
            this.mNativeLibs = ByteOrderDataUtil.readStringList(bufferedInputStream);
        }
        if (this.version >= 5) {
            this.mDependencies = ByteOrderDataUtil.readStringList2(bufferedInputStream);
        } else {
            this.mDependencies = ByteOrderDataUtil.readStringList(bufferedInputStream);
        }
        if (this.version >= 5) {
            this.mLocation = ByteOrderDataUtil.readString2(bufferedInputStream);
        } else {
            this.mLocation = ByteOrderDataUtil.readString(bufferedInputStream);
        }
        if (this.version >= 5) {
            this.mExportPackages = ByteOrderDataUtil.readStringList2(bufferedInputStream);
        } else if (this.version > 0) {
            this.mExportPackages = ByteOrderDataUtil.readStringList(bufferedInputStream);
        }
        if (this.version >= 3) {
            this.mAdler32Sum = ByteOrderDataUtil.readLong(bufferedInputStream);
        }
        if (this.version >= 5) {
            this.mMD5 = ByteOrderDataUtil.readString2(bufferedInputStream);
        } else if (this.version >= 4) {
            this.mMD5 = ByteOrderDataUtil.readString(bufferedInputStream);
        }
        return this;
    }

    public ByteDataBundle write(BufferedOutputStream bufferedOutputStream) throws IOException {
        ByteOrderDataUtil.writeInt(bufferedOutputStream, this.version);
        if (this.version >= 5) {
            ByteOrderDataUtil.writeString2(bufferedOutputStream, this.mName);
        } else {
            ByteOrderDataUtil.writeString(bufferedOutputStream, this.mName);
        }
        if (this.version >= 5) {
            ByteOrderDataUtil.writeString2(bufferedOutputStream, this.mVersion);
        } else {
            ByteOrderDataUtil.writeString(bufferedOutputStream, this.mVersion);
        }
        ByteOrderDataUtil.writeInt(bufferedOutputStream, this.mInitLevel);
        if (this.version >= 5) {
            ByteOrderDataUtil.writeStringList2(bufferedOutputStream, this.mPackageNames);
        } else {
            ByteOrderDataUtil.writeStringList(bufferedOutputStream, this.mPackageNames);
        }
        if (this.version >= 5) {
            ByteOrderDataUtil.writeStringList2(bufferedOutputStream, this.mComponents);
        } else {
            ByteOrderDataUtil.writeStringList(bufferedOutputStream, this.mComponents);
        }
        if (this.version >= 2) {
            ByteOrderDataUtil.writeInt(bufferedOutputStream, this.mPackageId);
        } else {
            ByteOrderDataUtil.writeString(bufferedOutputStream, String.valueOf(this.mPackageId));
        }
        if (this.version >= 5) {
            ByteOrderDataUtil.writeBoolean2(bufferedOutputStream, this.mContainCode);
        } else {
            ByteOrderDataUtil.writeBoolean(bufferedOutputStream, this.mContainCode);
        }
        if (this.version >= 5) {
            ByteOrderDataUtil.writeBoolean2(bufferedOutputStream, this.mContainRes);
        } else {
            ByteOrderDataUtil.writeBoolean(bufferedOutputStream, this.mContainRes);
        }
        if (this.version >= 5) {
            ByteOrderDataUtil.writeStringList2(bufferedOutputStream, this.mNativeLibs);
        } else {
            ByteOrderDataUtil.writeStringList(bufferedOutputStream, this.mNativeLibs);
        }
        if (this.version >= 5) {
            ByteOrderDataUtil.writeStringList2(bufferedOutputStream, this.mDependencies);
        } else {
            ByteOrderDataUtil.writeStringList(bufferedOutputStream, this.mDependencies);
        }
        if (this.version >= 5) {
            ByteOrderDataUtil.writeString2(bufferedOutputStream, this.mLocation);
        } else {
            ByteOrderDataUtil.writeString(bufferedOutputStream, this.mLocation);
        }
        if (this.version >= 5) {
            ByteOrderDataUtil.writeStringList2(bufferedOutputStream, this.mExportPackages);
        } else if (this.version > 0) {
            ByteOrderDataUtil.writeStringList(bufferedOutputStream, this.mExportPackages);
        }
        if (this.version >= 3) {
            ByteOrderDataUtil.writeLong(bufferedOutputStream, this.mAdler32Sum);
        }
        if (this.version >= 5) {
            ByteOrderDataUtil.writeString2(bufferedOutputStream, this.mMD5);
        } else if (this.version >= 4) {
            ByteOrderDataUtil.writeString(bufferedOutputStream, this.mMD5);
        }
        return this;
    }

    public String getName() {
        return this.mName;
    }

    public ByteDataBundle setName(String str) {
        this.mName = str;
        return this;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public ByteDataBundle setVersion(String str) {
        this.mVersion = str;
        return this;
    }

    public int getInitLevel() {
        return this.mInitLevel;
    }

    public ByteDataBundle setInitLevel(int i) {
        this.mInitLevel = i;
        return this;
    }

    public String getLocation() {
        return this.mLocation;
    }

    public ByteDataBundle setLocation(String str) {
        this.mLocation = str;
        return this;
    }

    public List<String> getPackageNames() {
        return this.mPackageNames;
    }

    public ByteDataBundle setPackageNames(List<String> list) {
        this.mPackageNames = list;
        return this;
    }

    public ByteDataBundle setExportPackages(List<String> list) {
        this.mExportPackages = list;
        return this;
    }

    public List<String> getExportPackages() {
        return this.mExportPackages;
    }

    public ByteDataBundle setComponents(List<String> list) {
        this.mComponents = list;
        return this;
    }

    public List<String> getComponents() {
        return this.mComponents;
    }

    public int getPackageId() {
        return this.mPackageId;
    }

    public ByteDataBundle setPackageId(int i) {
        this.mPackageId = i;
        return this;
    }

    public boolean containRes() {
        return this.mContainRes;
    }

    public ByteDataBundle setContainRes(boolean z) {
        this.mContainRes = z;
        return this;
    }

    public boolean containCode() {
        return this.mContainCode;
    }

    public ByteDataBundle setContainCode(boolean z) {
        this.mContainCode = z;
        return this;
    }

    public List<String> getNativeLibs() {
        return this.mNativeLibs;
    }

    public ByteDataBundle setNativeLibs(List<String> list) {
        this.mNativeLibs = list;
        return this;
    }

    public ByteDataBundle setDependencies(List<String> list) {
        this.mDependencies = list;
        return this;
    }

    public List<String> getDependencies() {
        return this.mDependencies;
    }

    public long getSize() {
        return getAdler32Sum();
    }

    public ByteDataBundle setSize(long j) {
        return setAdler32Sum(j);
    }

    public long getAdler32Sum() {
        return this.mAdler32Sum;
    }

    public ByteDataBundle setAdler32Sum(long j) {
        this.mAdler32Sum = j;
        return this;
    }

    public String getMD5() {
        return this.mMD5;
    }

    public ByteDataBundle setMD5(String str) {
        this.mMD5 = str;
        return this;
    }

    public int getVERSION() {
        return this.version;
    }

    public ByteDataBundle setVERSION(int i) {
        this.version = i;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ByteDataBundle{version=");
        sb.append(this.version);
        sb.append(", mName='");
        sb.append(this.mName);
        sb.append('\'');
        sb.append(", mVersion='");
        sb.append(this.mVersion);
        sb.append('\'');
        sb.append(", mInitLevel=");
        sb.append(this.mInitLevel);
        sb.append(", mLocation='");
        sb.append(this.mLocation);
        sb.append('\'');
        sb.append(", mAdler32Sum=");
        sb.append(this.mAdler32Sum);
        sb.append(", mMD5=");
        sb.append(this.mMD5);
        sb.append(", mPackageNames=");
        sb.append(StringUtil.collection2String(this.mPackageNames));
        sb.append(", mExportPackages=");
        sb.append(StringUtil.collection2String(this.mExportPackages));
        sb.append(", mComponents=");
        sb.append(StringUtil.collection2String(this.mComponents));
        sb.append(", mPackageId=");
        sb.append(this.mPackageId);
        sb.append(", mContainRes=");
        sb.append(this.mContainRes);
        sb.append(", mContainCode=");
        sb.append(this.mContainCode);
        sb.append(", mNativeLibs=");
        sb.append(StringUtil.collection2String(this.mNativeLibs));
        sb.append(", mDependencies=");
        sb.append(StringUtil.collection2String(this.mDependencies));
        sb.append('}');
        return sb.toString();
    }
}
