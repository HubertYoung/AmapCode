package com.autonavi.minimap.ajx3.htmcompat;

import java.util.HashMap;

class ImgSpan {
    HashMap<String, String> mAttributeMap;
    AjxImageGetter mImageGetter;

    ImgSpan(HashMap<String, String> hashMap, AjxImageGetter ajxImageGetter) {
        this.mAttributeMap = hashMap;
        this.mImageGetter = ajxImageGetter;
    }
}
