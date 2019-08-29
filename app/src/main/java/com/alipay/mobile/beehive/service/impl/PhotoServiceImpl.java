package com.alipay.mobile.beehive.service.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.alipay.mobile.beehive.photo.data.PhotoContext;
import com.alipay.mobile.beehive.photo.data.PhotoItem;
import com.alipay.mobile.beehive.photo.data.PhotoRpcRunnable;
import com.alipay.mobile.beehive.photo.ui.BrowsePhotoAsListActivity;
import com.alipay.mobile.beehive.photo.ui.HorizontalListViewFactory;
import com.alipay.mobile.beehive.photo.ui.PhotoEditActivity;
import com.alipay.mobile.beehive.photo.ui.PhotoPreviewActivity;
import com.alipay.mobile.beehive.photo.ui.PhotoSelectActivity;
import com.alipay.mobile.beehive.photo.util.AnimationUtil;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.UserBehavior;
import com.alipay.mobile.beehive.photo.view.RemotePhotoGridView;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.service.BrowsePhotoAsListListener;
import com.alipay.mobile.beehive.service.PhotoBrowseListener;
import com.alipay.mobile.beehive.service.PhotoEditListener;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoSelectListener;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.beehive.service.session.BrowsePhotoAsListSession;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.sdk.util.e;
import com.autonavi.map.core.MapCustomizeManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PhotoServiceImpl extends PhotoService {
    public static final String EXTRA_KEY_BUSINESS_ID = "businessId";
    public static final String EXTRA_SOURCE_APP_ID = "SourceAppId";
    public static final String TAG = "PhotoServiceImpl";
    private static BrowsePhotoAsListSession currentPBSession;
    public static String sCallerAPPId;
    private AtomicInteger atomicIndex;

    public PhotoServiceImpl() {
        PhotoLogger.debug("beehive-photo", "PhotoServiceImpl initialized");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
        this.atomicIndex = new AtomicInteger(0);
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle arg0) {
        PhotoLogger.debug(TAG, "onDestroy:do nothing.");
    }

    public void selectPhoto(MicroApplication app, Bundle bundle, PhotoSelectListener listener) {
        selectPhoto(app, null, bundle, listener);
    }

    public void selectPhotoWithExtraInfoKept(MicroApplication app, List<PhotoInfo> localSelectedPhotos, Bundle bundle, PhotoSelectListener listener) {
        if (listener == null || app == null) {
            PhotoLogger.error((String) TAG, (String) "invalid selectPhoto parameters!");
            return;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        preHandleBusinessId(app, bundle);
        String contextIndex = createContextInfo();
        PhotoLogger.debug(TAG, "selectPhoto context index " + contextIndex);
        bundle.putString(PhotoParam.CONTEXT_INDEX, contextIndex);
        bundle.putBoolean(PhotoParam.PHOTO_SELECT, true);
        PhotoContext photoContext = PhotoContext.get(contextIndex);
        photoContext.photoList = null;
        photoContext.selectListener = listener;
        Intent intent = new Intent(getMicroApplicationContext().getApplicationContext(), bundle.getBoolean(PhotoParam.SELECT_GRID, true) ? PhotoSelectActivity.class : PhotoPreviewActivity.class);
        recordSelectedPhotoAndExtraInfo(localSelectedPhotos, bundle, photoContext);
        doSelect(app, bundle, intent);
    }

    private void recordSelectedPhotoAndExtraInfo(List<PhotoInfo> localSelectedPhotos, Bundle bundle, PhotoContext photoContext) {
        if (localSelectedPhotos != null && !localSelectedPhotos.isEmpty()) {
            Map photosExtraInfo = new HashMap();
            ArrayList selected = new ArrayList();
            for (PhotoInfo pi : localSelectedPhotos) {
                selected.add(TextUtils.isEmpty(pi.shadowPathInQ) ? pi.getPhotoPath() : pi.shadowPathInQ);
                if (pi.extraInfo != null) {
                    photosExtraInfo.put(pi.getPhotoPath(), pi.extraInfo);
                }
            }
            bundle.putStringArrayList(PhotoParam.SELECTED_PHOTO_PATHS, selected);
            photoContext.photosExtraInfo = photosExtraInfo;
        }
    }

    private void doSelect(MicroApplication app, Bundle bundle, Intent intent) {
        bundle.putInt(PhotoParam.VIDEO_SHOW_TYPE, 1);
        Activity topActivity = (Activity) getMicroApplicationContext().getTopActivity().get();
        SpmUtils.addSourceAndBizTypeByTop(app, topActivity, bundle);
        intent.putExtras(bundle);
        getMicroApplicationContext().startActivity(app, intent);
        AnimationUtil.fadeInFadeOut(topActivity);
    }

    public void selectPhoto(MicroApplication app, List<PhotoInfo> photoList, Bundle bundle, PhotoSelectListener listener) {
        if (listener == null || app == null) {
            PhotoLogger.error((String) TAG, (String) "invalid selectPhoto parameters!");
            return;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        preHandleBusinessId(app, bundle);
        String contextIndex = createContextInfo();
        PhotoLogger.debug(TAG, "selectPhoto context index " + contextIndex);
        bundle.putString(PhotoParam.CONTEXT_INDEX, contextIndex);
        bundle.putBoolean(PhotoParam.PHOTO_SELECT, true);
        PhotoContext photoContext = PhotoContext.get(contextIndex);
        photoContext.photoList = null;
        photoContext.selectListener = listener;
        Intent intent = new Intent(getMicroApplicationContext().getApplicationContext(), bundle.getBoolean(PhotoParam.SELECT_GRID, true) ? PhotoSelectActivity.class : PhotoPreviewActivity.class);
        if (photoList != null && !photoList.isEmpty()) {
            ArrayList photoItemList = new ArrayList();
            for (PhotoInfo photoInfo : photoList) {
                PhotoItem photoItem = new PhotoItem(photoInfo);
                if (photoItem.getSelected()) {
                    photoContext.selectedList.add(photoItem);
                }
                photoItemList.add(photoItem);
            }
            if (!photoItemList.isEmpty()) {
                photoContext.photoList = photoItemList;
            }
        }
        doSelect(app, bundle, intent);
    }

    public void browsePhoto(MicroApplication app, List<PhotoInfo> photoList, Bundle bundle, PhotoBrowseListener listener) {
        if (app == null) {
            PhotoLogger.error((String) TAG, (String) "invalid browsePhoto parameters!");
            return;
        }
        if (photoList == null) {
            photoList = new ArrayList<>();
        }
        String contextIndex = createContextInfo();
        PhotoLogger.debug(TAG, "browsePhoto context index " + contextIndex);
        PhotoContext.get(contextIndex).previewListener = listener;
        if (bundle == null) {
            bundle = new Bundle();
        }
        preHandleBusinessId(app, bundle);
        bundle.putString(PhotoParam.CONTEXT_INDEX, contextIndex);
        bundle.putString(EXTRA_SOURCE_APP_ID, app.getAppId());
        Intent intent = new Intent(getMicroApplicationContext().getApplicationContext(), bundle.getBoolean(PhotoParam.BROWSE_GRID, false) ? PhotoSelectActivity.class : PhotoPreviewActivity.class);
        ArrayList photoItemList = new ArrayList();
        PhotoContext photoContext = PhotoContext.get(contextIndex);
        for (int index = 0; index < photoList.size(); index++) {
            PhotoItem photoItem = new PhotoItem(photoList.get(index));
            photoItem.setPhotoIndex(index);
            if (photoItem.getSelected()) {
                photoContext.selectedList.add(photoItem);
            }
            photoItemList.add(photoItem);
        }
        photoContext.photoList = photoItemList;
        bundle.putInt(PhotoParam.VIDEO_SHOW_TYPE, 0);
        Activity topActivity = (Activity) getMicroApplicationContext().getTopActivity().get();
        SpmUtils.addSourceAndBizTypeByTop(app, topActivity, bundle);
        intent.putExtras(bundle);
        app.getMicroApplicationContext().startActivity(app, intent);
        AnimationUtil.fadeInFadeOut(topActivity);
    }

    public void editPhoto(MicroApplication app, PhotoInfo photoInfo, Bundle bundle, PhotoEditListener listener) {
        if (app == null || photoInfo == null || listener == null) {
            PhotoLogger.error((String) TAG, (String) "invalid editPhoto parameters!");
            return;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        preHandleBusinessId(app, bundle);
        String contextIndex = createContextInfo();
        bundle.putString(PhotoParam.CONTEXT_INDEX, contextIndex);
        PhotoContext.get(contextIndex).editListener = listener;
        PhotoContext.get(contextIndex).editPhotoInfo = photoInfo;
        Intent intent = new Intent(getMicroApplicationContext().getApplicationContext(), PhotoEditActivity.class);
        SpmUtils.addSourceAndBizTypeByTop(app, (Activity) getMicroApplicationContext().getTopActivity().get(), bundle);
        intent.putExtras(bundle);
        getMicroApplicationContext().startActivity(app, intent);
    }

    private String createContextInfo() {
        return "photoSvs" + this.atomicIndex.getAndIncrement();
    }

    private void preHandleBusinessId(MicroApplication app, Bundle bundle) {
        String info;
        String businessId = bundle.getString("businessId");
        if (TextUtils.isEmpty(businessId)) {
            String callerAppId = app.getAppId();
            sCallerAPPId = callerAppId;
            bundle.putString("businessId", callerAppId);
            info = "Caller did not supply BUSINESS_ID,simply take APP_ID,which  = " + callerAppId;
        } else {
            info = "Caller BUSINESS_ID = " + businessId;
        }
        PhotoLogger.info(TAG, info);
        ImageHelper.updateBizType(bundle.getString(PhotoParam.MULTI_MEDIA_BIZ_TYPE));
    }

    public View createRemotePhotoGridView(Context context, PhotoRpcRunnable refreshRpcRunnable, PhotoRpcRunnable loadMoreRpcRunnable, Bundle params) {
        if (context == null) {
            return null;
        }
        return new RemotePhotoGridView(context, null, refreshRpcRunnable, loadMoreRpcRunnable, params);
    }

    public View createRemotePhotoHorizontalListView(Context context, PhotoRpcRunnable refreshRpcRunnable, PhotoRpcRunnable loadMoreRpcRunnable, Bundle params) {
        return HorizontalListViewFactory.getHorizontalListView(context, refreshRpcRunnable, params);
    }

    public static synchronized BrowsePhotoAsListSession getCurrentPLBSession() {
        BrowsePhotoAsListSession browsePhotoAsListSession;
        synchronized (PhotoServiceImpl.class) {
            try {
                if (currentPBSession == null) {
                    PhotoLogger.warn((String) TAG, (String) "getCurrentPhotoListBrowseSession return null,return a sessionSub !");
                    UserBehavior.reportUnusableEvent("browsePhotoAsList", "getCurrentPLBSession", e.b);
                    browsePhotoAsListSession = new BrowsePhotoAsListSession(new LinkedList(), new BrowsePhotoAsListListener() {
                        public final boolean onPhotoClick(Activity activity, View v, List<PhotoInfo> photos, int which) {
                            PhotoLogger.warn((String) PhotoServiceImpl.TAG, (String) "onPhotoClick Sub!");
                            return false;
                        }

                        public final boolean onPhotoLongClick(Activity activity, View v, List<PhotoInfo> photos, int which) {
                            PhotoLogger.warn((String) PhotoServiceImpl.TAG, (String) "onPhotoLongClick Sub!");
                            return false;
                        }

                        public final void onPhotoDelete(Activity activity, List<PhotoInfo> photos, PhotoInfo which) {
                            PhotoLogger.warn((String) PhotoServiceImpl.TAG, (String) "onPhotoDelete Sub!");
                        }

                        public final void onBrowseFinish(List<PhotoInfo> before, List<PhotoInfo> after) {
                            PhotoLogger.warn((String) PhotoServiceImpl.TAG, (String) "onBrowseFinish Sub!");
                        }
                    });
                } else {
                    browsePhotoAsListSession = currentPBSession;
                }
            }
        }
        return browsePhotoAsListSession;
    }

    public static synchronized boolean setUpCurrentSession(List<PhotoInfo> photos, BrowsePhotoAsListListener listener) {
        boolean concurrent;
        synchronized (PhotoServiceImpl.class) {
            concurrent = false;
            if (currentPBSession != null) {
                PhotoLogger.warn((String) TAG, "PhotoListBrowse job concurrent,cancel the old one! " + currentPBSession);
                concurrent = true;
            }
            currentPBSession = new BrowsePhotoAsListSession(photos, listener);
            PhotoLogger.debug(TAG, "Update BrowsePhotoAsListSession to " + currentPBSession);
        }
        return concurrent;
    }

    public static void finishPhotoListBrowseService() {
        PhotoLogger.debug(TAG, "Browse done,release session.");
        currentPBSession = null;
    }

    public void browsePhotoAsList(MicroApplication app, List<PhotoInfo> photoList, String businessId, Bundle params, BrowsePhotoAsListListener listener) {
        if (!TextUtils.isEmpty(businessId)) {
            setUpCurrentSession(photoList, listener);
            if (params == null) {
                params = new Bundle();
            }
            params.putString("businessId", businessId);
            Intent intent = new Intent(LauncherApplicationAgent.getInstance().getApplicationContext(), BrowsePhotoAsListActivity.class);
            intent.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            SpmUtils.addSourceAndBizTypeByTop(app, (Activity) getMicroApplicationContext().getTopActivity().get(), params);
            intent.putExtras(params);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startActivity(app, intent);
            return;
        }
        PhotoLogger.debug(TAG, "browsePhotoAsList action called,but args is not valid: businessId is empty.");
    }
}
