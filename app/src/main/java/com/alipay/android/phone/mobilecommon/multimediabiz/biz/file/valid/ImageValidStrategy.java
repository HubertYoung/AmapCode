package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.valid;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;

public class ImageValidStrategy implements FileValidStrategy {
    public boolean checkFileValid(String filePath) {
        return ImageUtils.isImage(filePath);
    }
}
