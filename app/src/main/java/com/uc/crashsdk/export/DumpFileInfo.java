package com.uc.crashsdk.export;

/* compiled from: ProGuard */
public class DumpFileInfo {
    public String mCategory;
    public boolean mDeleteAfterDump = false;
    public String mFileTobeDump;
    public boolean mIsEncrypted = false;
    public int mLogType;
    public boolean mWriteCategory = true;

    public DumpFileInfo(String str, String str2, int i) {
        this.mCategory = str;
        this.mFileTobeDump = str2;
        this.mLogType = i;
    }

    public DumpFileInfo(DumpFileInfo dumpFileInfo) {
        this.mCategory = dumpFileInfo.mCategory;
        this.mFileTobeDump = dumpFileInfo.mFileTobeDump;
        this.mLogType = dumpFileInfo.mLogType;
        this.mWriteCategory = dumpFileInfo.mWriteCategory;
        this.mDeleteAfterDump = dumpFileInfo.mDeleteAfterDump;
        this.mIsEncrypted = dumpFileInfo.mIsEncrypted;
    }
}
