package com.autonavi.minimap.offline.model.impl;

import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.offline.model.IFilePathHelper;

public class FilePathHelperImpl implements IFilePathHelper {
    public String getPoiFileDir() {
        return FilePathHelper.getInstance().getPoiFileDir();
    }
}
