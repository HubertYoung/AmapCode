package com.alipay.mobile.beehive.photo.data;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.multimediaext.filter.ImageBeautyFilter;
import com.alipay.android.multimediaext.filter.ImageBeautyFilter.APBeautyOptions;
import com.alipay.android.multimediaext.filter.ImageBeautyFilter.APFilterResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeResult;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.service.PhotoBrowseListener;
import com.alipay.mobile.beehive.service.PhotoEditListener;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoMenu;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoSelectListener;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.multimedia.adjuster.api.APMSandboxProcessor;
import com.alipay.multimedia.adjuster.api.data.APMSaveReq.Builder;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoContext {
    private static final String FILE_SCHEME = "file://";
    public static final String TAG = "PhotoContext";
    private static final String TAG_BEAUTY = "PhotoContext_beauty_img";
    private static final String TAG_COMPRESS = "PhotoContextCompressImage";
    public static Map<String, PhotoContext> contextMap = new HashMap();
    public String contextIndex;
    public PhotoEditListener editListener;
    public PhotoInfo editPhotoInfo;
    public boolean editSent;
    public boolean gridPageRunning;
    public List<PhotoItem> photoList = new ArrayList();
    public PhotoResolver photoResolver;
    public Map<String, Map<String, Object>> photosExtraInfo;
    public PhotoBrowseListener previewListener;
    public boolean previewPageRunning;
    public PhotoSelectListener selectListener;
    public boolean selectSent;
    public List<PhotoItem> selectedList = new ArrayList();
    public boolean userOriginPhoto;

    public static void remove(String index) {
        if (contextMap.containsKey(index)) {
            contextMap.remove(index);
        }
    }

    public static PhotoContext get(String index) {
        PhotoContext context;
        if (contextMap.containsKey(index)) {
            context = contextMap.get(index);
        } else {
            context = new PhotoContext(index);
            contextMap.put(index, context);
        }
        PhotoLogger.debug(TAG, "context map size " + contextMap.size());
        return context;
    }

    private PhotoContext(String contextIndex2) {
        this.contextIndex = contextIndex2;
        this.userOriginPhoto = false;
        this.previewPageRunning = false;
        this.selectSent = false;
        this.gridPageRunning = false;
        this.editSent = false;
    }

    public void sendDeletedPhoto() {
        if (this.previewListener == null) {
            Log.w(TAG, "previewListener is null");
            return;
        }
        List imageList = new ArrayList();
        Bundle bundle = new Bundle();
        for (PhotoItem photoInfo : this.photoList) {
            imageList.add(photoInfo);
        }
        PhotoLogger.debug(TAG, "sendDeletePhoto size " + imageList.size());
        this.previewListener.onPhotoDelete(imageList, bundle);
    }

    public void sendSelectedPhoto() {
        if (this.selectListener == null) {
            Log.w(TAG, "selectListener is null");
        } else if (this.selectedList.isEmpty()) {
            PhotoLogger.debug(TAG, "no photo selected!");
        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean(PhotoParam.USE_ORIGIN_PHOTO, this.userOriginPhoto);
            List infoList = new ArrayList();
            for (PhotoItem photoInfo : this.selectedList) {
                infoList.add(photoInfo);
            }
            PhotoLogger.debug(TAG, "sendSelectedPhoto size " + infoList.size());
            this.selectListener.onPhotoSelected(infoList, bundle);
            this.selectSent = true;
        }
    }

    public void sendSelectedPhoto(Activity activity, float beautyImageLevel, int compressQuality, Runnable followAction) {
        if (this.selectListener == null) {
            Log.w(TAG, "selectListener is null");
            nextStep(followAction);
        } else if (this.selectedList.isEmpty()) {
            PhotoLogger.debug(TAG, "no photo selected!");
            nextStep(followAction);
        } else {
            final Bundle bundle = new Bundle();
            bundle.putBoolean(PhotoParam.USE_ORIGIN_PHOTO, this.userOriginPhoto);
            final ArrayList arrayList = new ArrayList();
            for (PhotoItem photoInfo : this.selectedList) {
                arrayList.add(photoInfo);
                restoreExtraInfo(photoInfo);
            }
            PhotoLogger.debug(TAG, "sendSelectedPhoto size " + arrayList.size());
            if (PhotoUtil.isQCompact()) {
                PhotoLogger.debug(TAG, "QCompact case.");
                handleProgressDialog(activity, true);
                final Activity activity2 = activity;
                final float f = beautyImageLevel;
                final Runnable runnable = followAction;
                final int i = compressQuality;
                ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).execute(new Runnable() {
                    public final void run() {
                        long startAt = System.currentTimeMillis();
                        PhotoContext.this.doCopyInQ(arrayList, activity2);
                        PhotoLogger.debug(PhotoContext.TAG, "Image copy cost = " + (System.currentTimeMillis() - startAt));
                        PhotoContext.this.beautyOrCompress(f, activity2, runnable, bundle, arrayList, i);
                    }
                });
                return;
            }
            beautyOrCompress(beautyImageLevel, activity, followAction, bundle, arrayList, compressQuality);
        }
    }

    /* access modifiers changed from: private */
    public void doCopyInQ(List<PhotoInfo> infoList, Activity activity) {
        PhotoLogger.debug(TAG, "doCopyInQ###");
        for (int i = 0; i < infoList.size(); i++) {
            PhotoInfo pi = infoList.get(i);
            if (pi.getMediaType() == 0 && pi.getPhotoPath().startsWith("content://")) {
                String contentUri = pi.getPhotoPath();
                String path = APMSandboxProcessor.copyContentUriFile(new Builder(pi.getPhotoPath(), ImageHelper.getBizType()).context(activity.getApplicationContext()).build());
                pi.shadowPathInQ = pi.getPhotoPath();
                pi.setPhotoPath("file://" + path);
                PhotoLogger.debug(TAG, "Copy " + contentUri + " to " + path);
            }
        }
    }

    /* access modifiers changed from: private */
    public void beautyOrCompress(float beautyImageLevel, Activity activity, Runnable followAction, Bundle bundle, List<PhotoInfo> infoList, int compressQuality) {
        final float f = beautyImageLevel;
        final Activity activity2 = activity;
        final Runnable runnable = followAction;
        final Bundle bundle2 = bundle;
        final List<PhotoInfo> list = infoList;
        final int i = compressQuality;
        activity.runOnUiThread(new Runnable() {
            public final void run() {
                if (f > 0.0f) {
                    PhotoLogger.debug(PhotoContext.TAG, "Need beauty");
                    PhotoContext.this.needBeauty(activity2, f, runnable, bundle2, list);
                } else if (i >= 0) {
                    PhotoLogger.debug(PhotoContext.TAG, "Need compress");
                    PhotoContext.this.needCompress(activity2, i, runnable, bundle2, list);
                } else {
                    PhotoLogger.debug(PhotoContext.TAG, "No need beauty.");
                    PhotoContext.this.selectListener.onPhotoSelected(list, bundle2);
                    PhotoContext.this.selectSent = true;
                    PhotoContext.this.nextStep(runnable);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void needBeauty(Activity activity, float beautyImageLevel, Runnable followAction, Bundle bundle, List<PhotoInfo> infoList) {
        PhotoLogger.debug(TAG_BEAUTY, "Need beauty, level =  " + beautyImageLevel);
        handleProgressDialog(activity, true);
        final List<PhotoInfo> list = infoList;
        final float f = beautyImageLevel;
        final Activity activity2 = activity;
        final Bundle bundle2 = bundle;
        final Runnable runnable = followAction;
        ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).execute(new Runnable() {
            public final void run() {
                long startAt = System.currentTimeMillis();
                PhotoContext.this.doBeauty(list, f);
                PhotoLogger.debug(PhotoContext.TAG_BEAUTY, "Image beauty cost = " + (System.currentTimeMillis() - startAt));
                PhotoContext.this.handleProgressDialog(activity2, false);
                PhotoContext.this.postDoFollowJob(list, bundle2, runnable);
            }
        });
    }

    /* access modifiers changed from: private */
    public void needCompress(Activity activity, int compressQuality, Runnable followAction, Bundle bundle, List<PhotoInfo> infoList) {
        handleProgressDialog(activity, true);
        final List<PhotoInfo> list = infoList;
        final int i = compressQuality;
        final Activity activity2 = activity;
        final Bundle bundle2 = bundle;
        final Runnable runnable = followAction;
        ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).execute(new Runnable() {
            public final void run() {
                long startAt = System.currentTimeMillis();
                PhotoContext.this.doCompress(list, i);
                PhotoLogger.debug(PhotoContext.TAG_COMPRESS, "Image compress cost = " + (System.currentTimeMillis() - startAt));
                PhotoContext.this.handleProgressDialog(activity2, false);
                PhotoContext.this.postDoFollowJob(list, bundle2, runnable);
            }
        });
    }

    /* access modifiers changed from: private */
    public void doCompress(List<PhotoInfo> infoList, int compressLevel) {
        PhotoLogger.debug(TAG_COMPRESS, "Called doCompress,level = " + compressLevel);
        try {
            MultimediaImageProcessor m = (MultimediaImageProcessor) MicroServiceUtil.getMicroService(MultimediaImageProcessor.class);
            if (m == null) {
                PhotoLogger.warn((String) TAG_COMPRESS, (String) "MultimediaImageProcessor NULL!");
                return;
            }
            for (PhotoInfo pi : infoList) {
                if (pi.getMediaType() == 0 && !pi.isGif()) {
                    File input = new File(Uri.parse(pi.getPhotoPath()).getPath());
                    APEncodeOptions options = new APEncodeOptions();
                    options.quality = compressLevel;
                    options.outputFile = null;
                    options.requireOutputInfo = true;
                    try {
                        APEncodeResult ret = m.compressToTempFile(input, options);
                        PhotoLogger.debug(TAG_COMPRESS, "Do Beauty @  = " + input.getAbsolutePath());
                        if (ret == null || !ret.isSuccess() || ret.imageInfo == null || TextUtils.isEmpty(ret.encodeFilePath)) {
                            PhotoLogger.warn((String) TAG_BEAUTY, (String) "Beauty return error,or Output file error,use the original photo.");
                        } else {
                            String cachePath = ret.encodeFilePath;
                            String photoPath = "file://" + cachePath;
                            File f = new File(cachePath);
                            long length = f.length();
                            if (f.exists() && f.isFile() && length > 0) {
                                PhotoLogger.debug(TAG_COMPRESS, "Beauty success,out put to:" + photoPath);
                                pi.setPhotoPath(photoPath);
                                pi.setPhotoHeight(ret.imageInfo.height);
                                pi.setPhotoWidth(ret.imageInfo.width);
                                pi.setPhotoSize(length);
                            }
                        }
                    } catch (Exception e) {
                        PhotoLogger.debug(TAG_COMPRESS, pi.getPhotoPath() + " compress image exception:" + e.getMessage());
                    }
                }
            }
        } catch (Exception e2) {
            PhotoLogger.warn((String) TAG_COMPRESS, (Throwable) e2);
        }
    }

    private void restoreExtraInfo(PhotoItem photoInfo) {
        if (this.photosExtraInfo != null && this.photosExtraInfo.containsKey(photoInfo.getPhotoPath())) {
            photoInfo.extraInfo = this.photosExtraInfo.get(photoInfo.getPhotoPath());
        }
    }

    /* access modifiers changed from: private */
    public void postDoFollowJob(final List<PhotoInfo> infoList, final Bundle bundle, final Runnable followAction) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                PhotoContext.this.selectListener.onPhotoSelected(infoList, bundle);
                PhotoContext.this.selectSent = true;
                PhotoContext.this.nextStep(followAction);
            }
        });
    }

    /* access modifiers changed from: private */
    public void nextStep(Runnable followAction) {
        if (followAction != null) {
            followAction.run();
        }
    }

    /* access modifiers changed from: private */
    public void handleProgressDialog(Activity activity, boolean isVisiable) {
        if (isVisiable) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).showProgressDialog("", false, null);
            } else if (activity instanceof BaseFragmentActivity) {
                ((BaseFragmentActivity) activity).showProgressDialog("", false, null);
            }
        } else if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).dismissProgressDialog();
        } else if (activity instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) activity).dismissProgressDialog();
        }
    }

    /* access modifiers changed from: private */
    public void doBeauty(List<PhotoInfo> infoList, float beautyImageLevel) {
        PhotoLogger.debug(TAG_BEAUTY, "Called doBeauty,beautyImageLevel = " + beautyImageLevel);
        try {
            APBeautyOptions options = new APBeautyOptions();
            options.beautyProgress = ((int) beautyImageLevel) * 100;
            ImageBeautyFilter imgFilter = new ImageBeautyFilter();
            for (PhotoInfo pi : infoList) {
                if (pi.getMediaType() == 0) {
                    File input = new File(Uri.parse(pi.getPhotoPath()).getPath());
                    PhotoLogger.debug(TAG_BEAUTY, "Do Beauty @  = " + input.getAbsolutePath());
                    APFilterResult ret = imgFilter.beautyImage(input, null, options);
                    if (ret == null || ret.outputFile == null || !ret.outputFile.exists() || !ret.outputFile.canRead()) {
                        PhotoLogger.warn((String) TAG_BEAUTY, (String) "Beauty return error,or Output file error,use the original photo.");
                    } else {
                        String photoPath = "file://" + ret.outputFile.getAbsolutePath();
                        PhotoLogger.debug(TAG_BEAUTY, "Beauty success,out put to:" + photoPath);
                        pi.setPhotoPath(photoPath);
                        pi.setPhotoHeight(ret.height);
                        pi.setPhotoWidth(ret.width);
                    }
                }
            }
        } catch (Exception e) {
            PhotoLogger.warn((String) TAG_BEAUTY, (Throwable) e);
        }
    }

    public boolean photoLongPressMenuClick(Activity activity, PhotoInfo imageInfo, PhotoMenu photoMenu) {
        if (this.previewListener == null || imageInfo == null) {
            return false;
        }
        PhotoLogger.debug(TAG, "photoLongPressMenuClick " + photoMenu.title);
        return this.previewListener.onLongPressMenuClick(activity, imageInfo, photoMenu);
    }

    public void notifyVideoSelected(PhotoInfo videoInfo) {
        if (this.selectListener != null) {
            List videoInfoList = new ArrayList();
            videoInfoList.add(videoInfo);
            this.selectSent = true;
            this.selectListener.onPhotoSelected(videoInfoList, new Bundle());
        }
        if (contextMap != null) {
            contextMap.clear();
        }
    }
}
