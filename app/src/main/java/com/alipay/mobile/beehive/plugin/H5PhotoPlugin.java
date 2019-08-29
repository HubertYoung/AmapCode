package com.alipay.mobile.beehive.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.service.PhotoBrowseListener;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoMenu;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoSelectListener;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.beehive.video.views.StreamPlayCon;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class H5PhotoPlugin extends H5SimplePlugin {
    public static final String ACTION_MEDIA_BROWSE_EVENT = "beehiveMediaBrowseEvent";
    @Deprecated
    public static final String IMAGE_SELECT = "imageSelect";
    public static final String IMAGE_VIEWER = "imageViewer";
    public static final String KEY_EVENT_TYPE = "mediaBrowser.onEvent";
    public static final String KEY_MEDIA_FILE_INDEX = "mediaFileIndex";
    public static final String KEY_VIDEO_HEIGHT = "videoHeight";
    public static final String KEY_VIDEO_WIDTH = "videoWidth";
    public static final String MEDIA_FILE_BROWSER = "mediaBrowser";
    public static final int MEDIA_TYPE_PHOTO = 0;
    public static final int MEDIA_TYPE_VIDEO = 1;
    public static final String TAG = "H5PhotoPlugin";
    public static final IntentFilter sEventFilter = new IntentFilter(ACTION_MEDIA_BROWSE_EVENT);
    private a mEventReceiver;

    static class a extends BroadcastReceiver {
        private H5Page a;

        a() {
        }

        public final void a(H5Page page) {
            this.a = page;
        }

        public final void onReceive(Context context, Intent intent) {
            try {
                JSONObject msg = (JSONObject) intent.getSerializableExtra(H5PhotoPlugin.KEY_EVENT_TYPE);
                if (this.a != null && msg != null) {
                    H5Bridge bridge = this.a.getBridge();
                    if (bridge != null) {
                        bridge.sendDataWarpToWeb(H5PhotoPlugin.KEY_EVENT_TYPE, msg, null);
                        PhotoLogger.d("H5PhotoPlugin", "onEvent: " + msg);
                        return;
                    }
                    PhotoLogger.d("H5PhotoPlugin", "GetBridge return null!");
                }
            } catch (Exception e) {
                PhotoLogger.d("H5PhotoPlugin", "EventReceiver.onReceive exception:" + e.getMessage());
            }
        }
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(IMAGE_VIEWER);
        filter.addAction(IMAGE_SELECT);
        filter.addAction(MEDIA_FILE_BROWSER);
    }

    public void onInitialize(H5CoreNode coreNode) {
        super.onInitialize(coreNode);
        this.mEventReceiver = new a();
        LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).registerReceiver(this.mEventReceiver, sEventFilter);
    }

    public void onRelease() {
        super.onRelease();
        if (this.mEventReceiver != null) {
            LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).unregisterReceiver(this.mEventReceiver);
        }
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (!(event.getTarget() instanceof H5Page)) {
            PhotoLogger.warn((String) "H5PhotoPlugin", (String) "not from h5 page.");
            bridgeContext.sendError(event, Error.INVALID_PARAM);
        } else if (IMAGE_VIEWER.equals(action)) {
            imageViewer(event, bridgeContext);
        } else if (IMAGE_SELECT.equals(action)) {
            imageSelect(event, bridgeContext);
        } else if (MEDIA_FILE_BROWSER.equals(action)) {
            mediaFileBrowser(event, bridgeContext);
        }
        return true;
    }

    @Deprecated
    private void imageSelect(H5Event event, final H5BridgeContext h5BridgeContext) {
        MicroApplication microApp = getMicroApplication(event.getActivity());
        Bundle bundle = new Bundle();
        SpmUtils.addSourceByH5Event(event, bundle);
        ((PhotoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(PhotoService.class.getName())).selectPhoto(microApp, bundle, new PhotoSelectListener() {
            public final void onPhotoSelected(List<PhotoInfo> imageList, Bundle bundle) {
                StringBuilder sb = new StringBuilder();
                for (PhotoInfo info : imageList) {
                    sb.append(info.getPhotoPath());
                }
                PhotoLogger.debug("H5PhotoPlugin", "return photo list：" + sb.toString());
                h5BridgeContext.sendBridgeResult("size", sb.toString());
            }

            public final void onSelectCanceled() {
            }
        });
    }

    public static MicroApplication getMicroApplication(Context context) {
        if (context instanceof BaseActivity) {
            return ((BaseActivity) context).getActivityApplication();
        }
        if (context instanceof BaseFragmentActivity) {
            return ((BaseFragmentActivity) context).getActivityApplication();
        }
        return null;
    }

    private void imageViewer(H5Event event, H5BridgeContext h5BridgeContext) {
        JSONObject params = event.getParam();
        Bundle bundle = new Bundle();
        SpmUtils.addSourceByH5Event(event, bundle);
        JSONArray images = params.getJSONArray("images");
        if (images == null || images.isEmpty()) {
            PhotoLogger.debug("H5PhotoPlugin", "invalid images parameter.");
            JSONObject result = new JSONObject();
            result.put((String) "success", (Object) Boolean.valueOf(false));
            result.put((String) "error", (Object) Integer.valueOf(Error.INVALID_PARAM.ordinal()));
            h5BridgeContext.sendBridgeResult(result);
            return;
        }
        List imageList = new ArrayList();
        for (int index = 0; index < images.size(); index++) {
            JSONObject joImage = images.getJSONObject(index);
            String imageUrl = H5ResourceHandlerUtil.apUrlToFilePath(joImage.getString(H5Param.URL));
            String thumbUrl = H5ResourceHandlerUtil.apUrlToFilePath(joImage.getString(LogItem.MM_C15_K4_TIME));
            if (!TextUtils.isEmpty(imageUrl) || !TextUtils.isEmpty(thumbUrl)) {
                PhotoInfo info = new PhotoInfo(imageUrl);
                info.setThumbPath(thumbUrl);
                imageList.add(info);
            }
        }
        int init = params.getIntValue("init");
        if (init < 0 || init >= imageList.size()) {
            init = 0;
        }
        bundle.putInt(PhotoParam.PREVIEW_POSITION, init);
        bundle.putBoolean(PhotoParam.SHOW_TEXT_INDICATOR, true);
        Boolean enableDeletePhotoObj = params.getBoolean("deletephoto");
        boolean enableDeletePhoto = enableDeletePhotoObj == null ? false : enableDeletePhotoObj.booleanValue();
        bundle.putBoolean(PhotoParam.ENABLE_DELETE, enableDeletePhoto);
        bundle.putBoolean(PhotoParam.KEY_ENABLE_SHOW_PHOTO_DOWNLOAD, params.getBooleanValue(PhotoParam.KEY_ENABLE_SHOW_PHOTO_DOWNLOAD));
        Boolean enableSavePhotoObj = params.getBoolean("enablesavephoto");
        if (enableSavePhotoObj == null ? false : enableSavePhotoObj.booleanValue()) {
            ArrayList photoMenus = new ArrayList();
            PhotoMenu photoMenu = new PhotoMenu("", PhotoMenu.TAG_SAVE);
            photoMenus.add(photoMenu);
            bundle.putParcelableArrayList(PhotoParam.LONG_CLICK_MENU, photoMenus);
        }
        PhotoService photoService = (PhotoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(PhotoService.class.getName());
        MicroApplication microApp = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp();
        PhotoBrowseListener listener = null;
        if (enableDeletePhoto) {
            final H5BridgeContext h5BridgeContext2 = h5BridgeContext;
            listener = new PhotoBrowseListener() {
                public final boolean onLongPressMenuClick(Activity activity, PhotoInfo photoInfo, PhotoMenu menu) {
                    return false;
                }

                public final boolean onBottomMenuClick(Activity activity, List<PhotoInfo> photoList, PhotoMenu menu) {
                    return false;
                }

                public final boolean onPhotoDelete(List<PhotoInfo> imageList, Bundle bundle) {
                    StringBuilder photos = new StringBuilder("");
                    if (imageList != null && !imageList.isEmpty()) {
                        Iterator iterator = imageList.iterator();
                        while (iterator.hasNext()) {
                            photos.append(iterator.next().getPhotoPath());
                            if (iterator.hasNext()) {
                                photos.append(",");
                            }
                        }
                    }
                    PhotoLogger.debug("H5PhotoPlugin", "photos_after_delete:" + photos.toString());
                    h5BridgeContext2.sendBridgeResult("photos_after_delete", photos.toString());
                    return false;
                }
            };
        }
        photoService.browsePhoto(microApp, imageList, bundle, listener);
        h5BridgeContext.sendBridgeResultWithCallbackKept("success", Boolean.valueOf(true));
    }

    private void mediaFileBrowser(H5Event event, H5BridgeContext h5BridgeContext) {
        updateReceiver(event);
        JSONObject params = event.getParam();
        Bundle bundle = new Bundle();
        SpmUtils.addSourceByH5Event(event, bundle);
        JSONArray medias = params.getJSONArray("medias");
        if (medias == null || medias.isEmpty()) {
            PhotoLogger.debug("H5PhotoPlugin", "invalid images parameter.");
            JSONObject result = new JSONObject();
            result.put((String) "success", (Object) Boolean.valueOf(false));
            result.put((String) "error", (Object) Integer.valueOf(Error.INVALID_PARAM.ordinal()));
            h5BridgeContext.sendBridgeResult(result);
            return;
        }
        bundle.putBoolean(PhotoParam.KEY_ENABLE_SHOW_PHOTO_DOWNLOAD, params.getBooleanValue(PhotoParam.KEY_ENABLE_SHOW_PHOTO_DOWNLOAD));
        List imageList = new ArrayList();
        boolean hasVideo = donRenderMediaItem(medias, params, imageList);
        bundle.putInt(PhotoParam.PREVIEW_POSITION, parseInitIndex(params, imageList));
        bundle.putBoolean(PhotoParam.SHOW_TEXT_INDICATOR, !hasVideo);
        bundle.putBoolean(PhotoParam.ENABLE_ORIGINAL_VIDEO_STREAM_PLAY, true);
        enableSavePhoto(params, bundle);
        ((PhotoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(PhotoService.class.getName())).browsePhoto(LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp(), imageList, bundle, enableDeletePhoto(h5BridgeContext, params, bundle));
        h5BridgeContext.sendBridgeResultWithCallbackKept("success", Boolean.valueOf(true));
    }

    private void updateReceiver(H5Event event) {
        if (this.mEventReceiver != null && (event.getTarget() instanceof H5Page)) {
            this.mEventReceiver.a((H5Page) event.getTarget());
        }
    }

    private int parseInitIndex(JSONObject params, List<PhotoInfo> imageList) {
        int init = params.getIntValue("init");
        if (init < 0 || init >= imageList.size()) {
            return 0;
        }
        return init;
    }

    private boolean donRenderMediaItem(JSONArray images, JSONObject params, List<PhotoInfo> imageList) {
        return doRenderItems(images, imageList, getJsonBooleanValue(PhotoParam.KEY_VIDEO_FULL_SCREEN, params, false), getJsonIntValue(PhotoParam.KEY_VIDEO_TOP_BAR_VISIBILITY_STYLE, params, StreamPlayCon.AUTO_SWITCH_VISIBILITY), getJsonIntValue(PhotoParam.KEY_VIDEO_TOOL_BAR_VISIBILITY_STYLE, params, StreamPlayCon.AUTO_SWITCH_VISIBILITY), getJsonBooleanValue(PhotoParam.SINGLE_VIDEO_AUTO_DISMISS_WHEN_PLAY_FINISHED, params, false), onlyOneVideo(images));
    }

    private boolean doRenderItems(JSONArray images, List<PhotoInfo> imageList, boolean enableVideoFullScreen, int hideVideoTopBar, int hideVideoToolBar, boolean isSingleVideoAutoDismiss, boolean onlyOneVideo) {
        boolean hasVideo = false;
        for (int index = 0; index < images.size(); index++) {
            JSONObject joImage = images.getJSONObject(index);
            String imageUrl = H5ResourceHandlerUtil.apUrlToFilePath(joImage.getString(H5Param.URL));
            String thumbUrl = H5ResourceHandlerUtil.apUrlToFilePath(joImage.getString(LogItem.MM_C15_K4_TIME));
            int mediaType = getJsonIntValue("type", joImage, 0);
            if (!TextUtils.isEmpty(imageUrl) || !TextUtils.isEmpty(thumbUrl)) {
                PhotoInfo info = new PhotoInfo(imageUrl);
                info.setThumbPath(thumbUrl);
                if (mediaType == 1) {
                    hasVideo = true;
                    info.setMediaType(2);
                    Bundle videoParams = new Bundle();
                    videoParams.putBoolean(PhotoParam.KEY_VIDEO_FULL_SCREEN, enableVideoFullScreen);
                    videoParams.putInt(PhotoParam.KEY_VIDEO_TOP_BAR_VISIBILITY_STYLE, hideVideoTopBar);
                    videoParams.putInt(PhotoParam.KEY_VIDEO_TOOL_BAR_VISIBILITY_STYLE, hideVideoToolBar);
                    videoParams.putBoolean(PhotoParam.SINGLE_VIDEO_AUTO_DISMISS_WHEN_PLAY_FINISHED, onlyOneVideo && isSingleVideoAutoDismiss);
                    videoParams.putInt(KEY_MEDIA_FILE_INDEX, index);
                    videoParams.putInt(KEY_VIDEO_HEIGHT, getJsonIntValue(KEY_VIDEO_HEIGHT, joImage, -1));
                    videoParams.putInt(KEY_VIDEO_WIDTH, getJsonIntValue(KEY_VIDEO_WIDTH, joImage, -1));
                    info.bizExtraParams = videoParams;
                }
                imageList.add(info);
            }
        }
        return hasVideo;
    }

    private boolean onlyOneVideo(JSONArray images) {
        return images != null && !images.isEmpty() && images.size() == 1 && getJsonIntValue("type", images.getJSONObject(0), 0) == 1;
    }

    @Nullable
    private PhotoBrowseListener enableDeletePhoto(final H5BridgeContext h5BridgeContext, JSONObject params, Bundle bundle) {
        Boolean enableDeletePhotoObj = params.getBoolean("deletephoto");
        boolean enableDeletePhoto = enableDeletePhotoObj == null ? false : enableDeletePhotoObj.booleanValue();
        bundle.putBoolean(PhotoParam.ENABLE_DELETE, enableDeletePhoto);
        if (enableDeletePhoto) {
            return new PhotoBrowseListener() {
                public final boolean onLongPressMenuClick(Activity activity, PhotoInfo photoInfo, PhotoMenu menu) {
                    return false;
                }

                public final boolean onBottomMenuClick(Activity activity, List<PhotoInfo> photoList, PhotoMenu menu) {
                    return false;
                }

                public final boolean onPhotoDelete(List<PhotoInfo> imageList, Bundle bundle) {
                    StringBuilder photos = new StringBuilder("");
                    if (imageList != null && !imageList.isEmpty()) {
                        Iterator iterator = imageList.iterator();
                        while (iterator.hasNext()) {
                            photos.append(iterator.next().getPhotoPath());
                            if (iterator.hasNext()) {
                                photos.append(",");
                            }
                        }
                    }
                    PhotoLogger.debug("H5PhotoPlugin", "photos_after_delete:" + photos.toString());
                    h5BridgeContext.sendBridgeResult("photos_after_delete", photos.toString());
                    return false;
                }
            };
        }
        return null;
    }

    private void enableSavePhoto(JSONObject params, Bundle bundle) {
        Boolean enableSavePhotoObj = params.getBoolean("enablesavephoto");
        if (enableSavePhotoObj == null ? false : enableSavePhotoObj.booleanValue()) {
            ArrayList photoMenus = new ArrayList();
            PhotoMenu saveMenu = new PhotoMenu("", PhotoMenu.TAG_SAVE);
            photoMenus.add(saveMenu);
            saveMenu.setMenuSupportType((byte) (PhotoMenu.SUPPORT_PHOTO.byteValue() | PhotoMenu.SUPPORT_VIDEO_ORI.byteValue()));
            bundle.putParcelableArrayList(PhotoParam.LONG_CLICK_MENU, photoMenus);
        }
    }

    private boolean getJsonBooleanValue(String key, JSONObject jo, boolean defaultValue) {
        if (jo == null) {
            return defaultValue;
        }
        try {
            return jo.getBooleanValue(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private int getJsonIntValue(String key, JSONObject jo, int defaultValue) {
        if (jo == null) {
            return defaultValue;
        }
        try {
            return jo.getIntValue(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
