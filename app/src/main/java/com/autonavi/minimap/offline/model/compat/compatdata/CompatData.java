package com.autonavi.minimap.offline.model.compat.compatdata;

import com.autonavi.minimap.offline.model.FilePathHelper;
import java.util.List;

public abstract class CompatData {
    public abstract void dataCheck();

    /* access modifiers changed from: protected */
    public void deleteVoicetData() {
        FilePathHelper instance = FilePathHelper.getInstance();
        if (instance != null) {
            List<String> voiceTtsOldPath = instance.getVoiceTtsOldPath();
            if (voiceTtsOldPath != null && voiceTtsOldPath.size() > 0) {
                for (String next : voiceTtsOldPath) {
                    if (next != null) {
                        FilePathHelper.deleteFolder(next, true);
                    }
                }
            }
        }
    }
}
