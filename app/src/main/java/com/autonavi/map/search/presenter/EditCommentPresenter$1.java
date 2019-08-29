package com.autonavi.map.search.presenter;

import com.autonavi.map.search.photoupload.entity.ImageInfo;
import java.util.ArrayList;

public class EditCommentPresenter$1 extends ArrayList<ImageInfo> {
    final /* synthetic */ caw a;

    public EditCommentPresenter$1(caw caw) {
        this.a = caw;
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
