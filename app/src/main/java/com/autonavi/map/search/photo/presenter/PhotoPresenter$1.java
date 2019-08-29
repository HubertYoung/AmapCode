package com.autonavi.map.search.photo.presenter;

import com.autonavi.map.search.photoupload.entity.ImageInfo;
import java.util.ArrayList;

public class PhotoPresenter$1 extends ArrayList<ImageInfo> {
    final /* synthetic */ cac a;

    public PhotoPresenter$1(cac cac) {
        this.a = cac;
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
