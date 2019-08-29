package com.ut.mini;

class UTVariables {
    private static UTVariables s_instance = new UTVariables();
    private UTMI1010_2001Event m1010and2001EventInstance = null;
    private String mBackupH5Url = null;
    private String mH5RefPage = null;
    private String mH5Url = null;
    private String mRefPage = null;

    UTVariables() {
    }

    public synchronized void setUTMI1010_2001EventInstance(UTMI1010_2001Event uTMI1010_2001Event) {
        this.m1010and2001EventInstance = uTMI1010_2001Event;
    }

    public synchronized UTMI1010_2001Event getUTMI1010_2001EventInstance() {
        return this.m1010and2001EventInstance;
    }

    public static UTVariables getInstance() {
        return s_instance;
    }

    public String getH5Url() {
        return this.mH5Url;
    }

    public void setH5Url(String str) {
        this.mH5Url = str;
    }

    public void setBackupH5Url(String str) {
        this.mBackupH5Url = str;
    }

    public String getBackupH5Url() {
        return this.mBackupH5Url;
    }

    public String getRefPage() {
        return this.mRefPage;
    }

    public void setRefPage(String str) {
        this.mRefPage = str;
    }

    public String getH5RefPage() {
        return this.mH5RefPage;
    }

    public void setH5RefPage(String str) {
        this.mH5RefPage = str;
    }
}
