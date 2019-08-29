package com.autonavi.jni.vcs.ability;

public class VoiceAbility {
    private String mBundle;
    private int mCmdType;
    private String mCommandName;
    private boolean mIsBlocking;
    private boolean mIsMultipleSession;
    private long mScene;

    public VoiceAbility(long j, String str, String str2, boolean z, boolean z2, int i) {
        this.mScene = j;
        this.mBundle = str;
        this.mCommandName = str2;
        this.mIsBlocking = z;
        this.mIsMultipleSession = z2;
        this.mCmdType = i;
    }

    public long getScene() {
        return this.mScene;
    }

    public void setScene(long j) {
        this.mScene = j;
    }

    public String getBundle() {
        return this.mBundle;
    }

    public void setBundle(String str) {
        this.mBundle = str;
    }

    public String getCommandName() {
        return this.mCommandName;
    }

    public void setCommandName(String str) {
        this.mCommandName = str;
    }

    public boolean isIsBlocking() {
        return this.mIsBlocking;
    }

    public void setIsBlocking(boolean z) {
        this.mIsBlocking = z;
    }

    public boolean isIsMultipleSession() {
        return this.mIsMultipleSession;
    }

    public void setIsMultipleSession(boolean z) {
        this.mIsMultipleSession = z;
    }

    public int getCmdType() {
        return this.mCmdType;
    }

    public void setCmdType(int i) {
        this.mCmdType = i;
    }
}
