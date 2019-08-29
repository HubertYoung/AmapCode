package com.alipay.mobile.beehive.service;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.alipay.mobile.beehive.photo.data.PhotoRpcRunnable;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.List;

public abstract class PhotoService extends ExternalService {
    public abstract void browsePhoto(MicroApplication microApplication, List<PhotoInfo> list, Bundle bundle, PhotoBrowseListener photoBrowseListener);

    public abstract void browsePhotoAsList(MicroApplication microApplication, List<PhotoInfo> list, String str, Bundle bundle, BrowsePhotoAsListListener browsePhotoAsListListener);

    public abstract View createRemotePhotoGridView(Context context, PhotoRpcRunnable photoRpcRunnable, PhotoRpcRunnable photoRpcRunnable2, Bundle bundle);

    public abstract View createRemotePhotoHorizontalListView(Context context, PhotoRpcRunnable photoRpcRunnable, PhotoRpcRunnable photoRpcRunnable2, Bundle bundle);

    public abstract void editPhoto(MicroApplication microApplication, PhotoInfo photoInfo, Bundle bundle, PhotoEditListener photoEditListener);

    public abstract void selectPhoto(MicroApplication microApplication, Bundle bundle, PhotoSelectListener photoSelectListener);

    public abstract void selectPhoto(MicroApplication microApplication, List<PhotoInfo> list, Bundle bundle, PhotoSelectListener photoSelectListener);

    public abstract void selectPhotoWithExtraInfoKept(MicroApplication microApplication, List<PhotoInfo> list, Bundle bundle, PhotoSelectListener photoSelectListener);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle arg0) {
    }
}
