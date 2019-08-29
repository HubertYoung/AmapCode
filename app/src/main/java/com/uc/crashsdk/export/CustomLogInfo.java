package com.uc.crashsdk.export;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ProGuard */
public class CustomLogInfo {
    public boolean mAddFooter = true;
    public boolean mAddHeader = true;
    public boolean mAddLogcat = false;
    public boolean mAddThreadsDump = false;
    public ArrayList<String> mCachedInfos = null;
    public ArrayList<String> mCallbacks = null;
    public StringBuffer mData;
    public ArrayList<String> mDumpFiles = null;
    public ArrayList<Integer> mDumpTids = null;
    public String mLogType;
    public boolean mUploadNow = false;

    public CustomLogInfo(StringBuffer stringBuffer, String str) {
        this.mData = stringBuffer;
        this.mLogType = str;
    }

    public CustomLogInfo(CustomLogInfo customLogInfo) {
        this.mData = customLogInfo.mData;
        this.mLogType = customLogInfo.mLogType;
        this.mAddHeader = customLogInfo.mAddHeader;
        this.mAddFooter = customLogInfo.mAddFooter;
        this.mAddLogcat = customLogInfo.mAddLogcat;
        this.mUploadNow = customLogInfo.mUploadNow;
        this.mAddThreadsDump = customLogInfo.mAddThreadsDump;
        if (customLogInfo.mDumpFiles != null) {
            this.mDumpFiles = new ArrayList<>();
            Iterator<String> it = customLogInfo.mDumpFiles.iterator();
            while (it.hasNext()) {
                this.mDumpFiles.add(it.next());
            }
        }
        if (customLogInfo.mCallbacks != null) {
            this.mCallbacks = new ArrayList<>();
            Iterator<String> it2 = customLogInfo.mCallbacks.iterator();
            while (it2.hasNext()) {
                this.mCallbacks.add(it2.next());
            }
        }
        if (customLogInfo.mCachedInfos != null) {
            this.mCachedInfos = new ArrayList<>();
            Iterator<String> it3 = customLogInfo.mCachedInfos.iterator();
            while (it3.hasNext()) {
                this.mCachedInfos.add(it3.next());
            }
        }
        if (customLogInfo.mDumpTids != null) {
            this.mDumpTids = new ArrayList<>();
            Iterator<Integer> it4 = customLogInfo.mDumpTids.iterator();
            while (it4.hasNext()) {
                this.mDumpTids.add(it4.next());
            }
        }
    }
}
