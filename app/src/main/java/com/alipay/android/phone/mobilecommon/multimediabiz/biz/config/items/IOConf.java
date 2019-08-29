package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class IOConf {
    @JSONField(name = "cfwt")
    public int checkFileWaitTime = 500;
    @JSONField(name = "acf")
    public int enableAsyncCheckFile = 1;
    @JSONField(name = "asdb")
    public int enableAsyncSaveDB = 1;
    @JSONField(name = "asd")
    public int enableAsyncSaveData = 1;
    @JSONField(name = "lcs")
    public int enableLockSync = 0;

    public boolean isEnableAsyncCheckFile() {
        return 1 == this.enableAsyncCheckFile;
    }

    public boolean isEnableAsyncSaveDB() {
        return 1 == this.enableAsyncSaveDB;
    }

    public boolean isEnableAsyncSaveData() {
        return 1 == this.enableAsyncSaveData;
    }

    public boolean isEnableLockSync() {
        return 1 == this.enableLockSync;
    }

    public String toString() {
        return "IOConf{enableAsyncCheckFile=" + this.enableAsyncCheckFile + ", checkFileWaitTime=" + this.checkFileWaitTime + ", enableAsyncSaveDB=" + this.enableAsyncSaveDB + ", enableLockSync=" + this.enableLockSync + ", enableAsyncSaveData=" + this.enableAsyncSaveData + '}';
    }
}
