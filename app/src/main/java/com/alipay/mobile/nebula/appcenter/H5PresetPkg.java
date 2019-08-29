package com.alipay.mobile.nebula.appcenter;

import java.util.Map;

public class H5PresetPkg {
    private Map<String, H5PresetInfo> preSetInfo;
    private String presetPath;

    public String getPresetPath() {
        return this.presetPath;
    }

    public void setPresetPath(String presetPath2) {
        this.presetPath = presetPath2;
    }

    public Map<String, H5PresetInfo> getPreSetInfo() {
        return this.preSetInfo;
    }

    public void setPreSetInfo(Map<String, H5PresetInfo> preSetInfo2) {
        this.preSetInfo = preSetInfo2;
    }
}
