package com.alipay.mobile.beehive.compositeui.imagepicker;

import android.text.TextUtils;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton.ImageCallBack;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton.PickeInfo;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.Serializable;

public class PickerPhotoInfo implements PickeInfo, Serializable {
    public PhotoInfo photoInfo;
    public String pickerPhtotourl;
    public int state;

    public PickerPhotoInfo() {
    }

    public PickerPhotoInfo(String url, PhotoInfo info, int state2) {
        this.pickerPhtotourl = url;
        this.photoInfo = info;
        this.state = state2;
    }

    public void getImage(ImageCallBack imageCallBack) {
        LoggerFactory.getTraceLogger().info("ImagePickerAdapter", "PhotoInfo" + this.pickerPhtotourl);
        if (TextUtils.isEmpty(this.pickerPhtotourl)) {
            PickerPhotoUtils.getPhoto(this.photoInfo.getPhotoPath(), imageCallBack);
        } else {
            PickerPhotoUtils.getPhoto(this.pickerPhtotourl, imageCallBack);
        }
    }

    public int getState() {
        return this.state;
    }

    public boolean isGif() {
        return this.photoInfo.isGif();
    }
}
