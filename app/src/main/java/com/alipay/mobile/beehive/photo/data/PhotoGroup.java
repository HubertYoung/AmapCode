package com.alipay.mobile.beehive.photo.data;

import com.alipay.mobile.beehive.service.PhotoInfo;
import java.util.ArrayList;
import java.util.List;

public class PhotoGroup {
    private String id;
    private int offset;
    private List<PhotoInfo> photoInfoList;

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public List<PhotoInfo> getPhotoInfoList() {
        return this.photoInfoList;
    }

    public void setPhotoInfoList(List<? extends PhotoInfo> photoInfoList2) {
        if (this.photoInfoList == null) {
            this.photoInfoList = new ArrayList();
        } else if (!this.photoInfoList.isEmpty()) {
            this.photoInfoList.clear();
        }
        this.photoInfoList.addAll(photoInfoList2);
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset2) {
        this.offset = offset2;
    }
}
