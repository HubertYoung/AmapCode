package com.autonavi.jni.ae.data;

public final class InitParams {
    private String configFileContent;
    private String configFilePath;
    private String dataFilePath;
    private String p3dCrossPath;
    private String rootPath;

    public final void setRootPath(String str) {
        this.rootPath = str;
    }

    public final String getConfigFilePath() {
        return this.configFilePath == null ? "" : this.configFilePath;
    }

    public final void setConfigFilePath(String str) {
        this.configFilePath = str;
    }

    public final void setConfigFileContent(String str) {
        this.configFileContent = str;
    }

    public final void setDataFilePath(String str) {
        this.dataFilePath = str;
    }

    public final void set3dCrossPath(String str) {
        this.p3dCrossPath = str;
    }

    public final String getRootPath() {
        return this.rootPath == null ? "" : this.rootPath;
    }

    public final String getConfigFileContent() {
        return this.configFileContent == null ? "" : this.configFileContent;
    }

    public final String getDataFilePath() {
        return this.dataFilePath == null ? "" : this.dataFilePath;
    }

    public final String get3dCrossPath() {
        return this.p3dCrossPath == null ? "" : this.p3dCrossPath;
    }
}
