package com.alipay.mobile.beehive.photo.data;

import com.alipay.mobile.beehive.service.PhotoInfo;

public class BucketInfo {
    public boolean allPhoto;
    private int count;
    private PhotoInfo coverPhoto;
    private String name;
    private boolean selected;

    public BucketInfo(String name2, int count2, PhotoInfo coverPhoto2) {
        this(name2, count2, coverPhoto2, false);
    }

    public BucketInfo(String name2, int count2, PhotoInfo coverPhoto2, boolean selected2) {
        this.name = name2;
        this.count = count2;
        this.coverPhoto = coverPhoto2;
        this.selected = selected2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count2) {
        this.count = count2;
    }

    public PhotoInfo getPhoto() {
        return this.coverPhoto;
    }

    public void setPhoto(PhotoInfo photo) {
        this.coverPhoto = photo;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected2) {
        this.selected = selected2;
    }

    public void increaseCount() {
        this.count++;
    }
}
