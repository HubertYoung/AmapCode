package com.autonavi.bundle.photoUpload;

import com.autonavi.annotation.VirtualApp;

@VirtualApp(priority = 10000)
public class PhotoUploadVApp extends esh {
    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        bvj.a();
    }
}
