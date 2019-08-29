package com.autonavi.bundle.photoUpload.impl;

import com.autonavi.map.search.photoupload.entity.ImageInfo;
import java.util.ArrayList;

public class PhotoUploadExporter$1 extends ArrayList<ImageInfo> {
    final /* synthetic */ awr a;

    public PhotoUploadExporter$1(awr awr) {
        this.a = awr;
    }

    public boolean contains(Object obj) {
        if (obj instanceof ImageInfo) {
            ImageInfo imageInfo = (ImageInfo) obj;
            for (int i = 0; i < size(); i++) {
                if (((ImageInfo) get(i)).b.equals(imageInfo.b)) {
                    return true;
                }
            }
        }
        return false;
    }
}
