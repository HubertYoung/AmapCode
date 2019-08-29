package com.autonavi.map.search.album.upload.inner;

import android.os.IBinder;

public interface IRealSceneUploadService {

    public interface IRealSceneUploadServiceBinder extends IBinder {
        IRealSceneUploadService getService();
    }
}
