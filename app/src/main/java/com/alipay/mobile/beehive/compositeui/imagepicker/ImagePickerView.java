package com.alipay.mobile.beehive.compositeui.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.WindowManager;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton.OnPickerClickListener;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton.PickeInfo;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.service.PhotoBrowseListener.V2;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoMenu;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoSelectListener;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.beehive.util.Logger;
import com.alipay.mobile.beehive.util.MultiThreadUtil;
import com.alipay.mobile.beehive.util.ServiceUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImagePickerView extends AUFrameLayout {
    private static final String PICKER_DRAG_SWITCH = "beeviews_image_picker_drag_switch";
    /* access modifiers changed from: private */
    public static String TAG = "ImagePickerView";
    private int MAXPICS = 9;
    private String bizId;
    private Bundle extBrowseBundle;
    private Bundle extPickerBundle;
    /* access modifiers changed from: private */
    public boolean isInitSelect = false;
    private PhotoService photoService;
    /* access modifiers changed from: private */
    public List<PickeInfo> pickerPhotoInfos = new ArrayList();
    /* access modifiers changed from: private */
    public AUImagePickerSkeleton pickerSkeleton;

    public interface OnDataChangeListener {
        void onDataChanged();

        void onDataExchanged();
    }

    public ImagePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPicker(context);
    }

    public ImagePickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPicker(context);
    }

    public ImagePickerView(Context context) {
        super(context);
        initPicker(context);
    }

    public void setIsInitSelect(boolean initSelect) {
        this.isInitSelect = initSelect;
    }

    private void initScreenWidth(Context context) {
        PickerPhotoUtils.picWidth = (((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth() - DensityUtil.dip2px(context, 56.0f)) / 4;
        LoggerFactory.getTraceLogger().info("PickerPhotoUtils", "execute one times");
    }

    private void initPicker(Context context) {
        this.pickerSkeleton = new AUImagePickerSkeleton(context);
        LoggerFactory.getTraceLogger().info("setClipToPadding", "setClipToPadding  false");
        removeAllViews();
        addView(this.pickerSkeleton);
        this.photoService = (PhotoService) ServiceUtil.getServiceByInterface(PhotoService.class);
        initScreenWidth(context);
        try {
            if (Boolean.parseBoolean(PickerPhotoUtils.getConfigValue(PICKER_DRAG_SWITCH))) {
                this.pickerSkeleton.isAllowDrag(false);
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(TAG, String.valueOf(e));
        }
        this.pickerSkeleton.setPickerClickListener(new OnPickerClickListener() {
            public void onPickerClick(int i) {
                ImagePickerView.this.addPickerPhoto();
            }

            public void onImageClick(int i) {
                ImagePickerView.this.setPreViewPhotoService(i);
            }
        });
    }

    private List<PhotoInfo> getPhotoList() {
        List tmps = new ArrayList();
        if (!(this.pickerPhotoInfos == null || this.pickerPhotoInfos.size() == 0)) {
            for (PickeInfo info0 : this.pickerPhotoInfos) {
                if (info0 instanceof PickerPhotoInfo) {
                    tmps.add(((PickerPhotoInfo) info0).photoInfo);
                }
            }
        }
        return tmps;
    }

    /* access modifiers changed from: private */
    public void setPreViewPhotoService(int pos) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(PhotoParam.ENABLE_DELETE, true);
        bundle.putBoolean(PhotoParam.PREVIEW_CLICK_EXIT, true);
        if (this.pickerPhotoInfos.size() > 1) {
            bundle.putBoolean(PhotoParam.SHOW_DOT_INDICATOR, true);
        }
        bundle.putBoolean(PhotoParam.ENABLE_EDIT_WHEN_PREVIEW_IMAGE, true);
        bundle.putInt(PhotoParam.PREVIEW_POSITION, pos);
        bundle.putBoolean(PhotoParam.ENABLE_DELETE, true);
        if (this.extBrowseBundle != null) {
            bundle.putAll(this.extBrowseBundle);
        }
        this.photoService.browsePhoto(LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp(), getPhotoList(), bundle, new V2() {
            public void onPhotoEditWithIn(final PhotoInfo src, final PhotoInfo dst) {
                LoggerFactory.getTraceLogger().info("ImagePickerView", "编辑后图片回调" + src + dst);
                MultiThreadUtil.runOnUiThread(new Runnable() {
                    public void run() {
                        ImagePickerView.this.updateEditPhoto(src, dst);
                    }
                });
            }

            public boolean onLongPressMenuClick(Activity activity, final PhotoInfo photoInfo, PhotoMenu menu) {
                if (TextUtils.equals(menu.tag, "delete")) {
                    LoggerFactory.getTraceLogger().info(ImagePickerView.TAG, " delete one " + photoInfo.getPhotoPath() + "\r\n PhotoMenu menu:delete");
                    MultiThreadUtil.runOnUiThread(new Runnable() {
                        public void run() {
                            ImagePickerView.this.deletePhoto(photoInfo);
                        }
                    });
                }
                return false;
            }

            public boolean onBottomMenuClick(Activity activity, List<PhotoInfo> photoList, PhotoMenu menu) {
                return false;
            }

            public boolean onPhotoDelete(List<PhotoInfo> imageList, Bundle bundle) {
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateEditPhoto(PhotoInfo src, PhotoInfo dst) {
        if (src != null && dst != null) {
            Iterator<PickeInfo> it = this.pickerPhotoInfos.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                PickeInfo info = it.next();
                if ((info instanceof PickerPhotoInfo) && PickerPhotoUtils.isSamePhoto(src, ((PickerPhotoInfo) info).photoInfo)) {
                    ((PickerPhotoInfo) info).photoInfo = dst;
                    break;
                }
            }
            addPicShow(this.pickerPhotoInfos);
        }
    }

    /* access modifiers changed from: private */
    public void deletePhoto(PhotoInfo photoInfo) {
        if (photoInfo != null) {
            int delIndex = -1;
            for (int i = 0; i < this.pickerPhotoInfos.size(); i++) {
                PickerPhotoInfo info = (PickerPhotoInfo) this.pickerPhotoInfos.get(i);
                if (!(info == null || info.photoInfo == null || !PickerPhotoUtils.isSamePhoto(photoInfo, info.photoInfo))) {
                    delIndex = i;
                }
            }
            this.pickerPhotoInfos.remove(delIndex);
            addPicShow(this.pickerPhotoInfos);
        }
    }

    public void setMaxNum(int maxSize) {
        this.MAXPICS = maxSize;
        this.pickerSkeleton.setMaxNum(maxSize);
    }

    /* access modifiers changed from: private */
    public void addPickerPhoto() {
        Bundle bundle = new Bundle();
        ArrayList localSelectedPhotos = new ArrayList();
        for (PickeInfo photoInfo0 : this.pickerPhotoInfos) {
            if (photoInfo0 instanceof PickerPhotoInfo) {
                PickerPhotoInfo photoInfo = (PickerPhotoInfo) photoInfo0;
                if (TextUtils.isEmpty(photoInfo.pickerPhtotourl)) {
                    localSelectedPhotos.add(photoInfo.photoInfo.getPhotoPath());
                }
            }
        }
        setPickerPhotoArgs(bundle, localSelectedPhotos);
        if (this.extPickerBundle != null) {
            bundle.putAll(this.extPickerBundle);
        }
        this.photoService.selectPhoto(LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp(), bundle, new PhotoSelectListener() {
            public void onPhotoSelected(final List<PhotoInfo> imageList, Bundle bundle) {
                if (imageList != null && imageList.size() != 0) {
                    MultiThreadUtil.runOnUiThread(new Runnable() {
                        public void run() {
                            if (ImagePickerView.this.isInitSelect) {
                                List toMerge = new ArrayList();
                                try {
                                    ImagePickerView.this.addOnLinePhotos(toMerge);
                                    ImagePickerView.this.addLocalPhotoList(imageList, toMerge);
                                } catch (Exception e) {
                                    Logger.e(ImagePickerView.TAG, ImagePickerView.TAG, e);
                                }
                                ImagePickerView.this.pickerPhotoInfos = toMerge;
                            } else {
                                try {
                                    ImagePickerView.this.addLocalPhotos(imageList, ImagePickerView.this.pickerPhotoInfos);
                                } catch (Exception e2) {
                                    Logger.e(ImagePickerView.TAG, ImagePickerView.TAG, e2);
                                }
                            }
                            ImagePickerView.this.addPicShow(ImagePickerView.this.pickerPhotoInfos);
                        }
                    });
                }
            }

            public void onSelectCanceled() {
            }
        });
    }

    public void setPics(final List<PhotoInfo> infos, String businessId) {
        if (this.pickerPhotoInfos != null && infos != null) {
            this.bizId = businessId;
            LoggerFactory.getTraceLogger().info(TAG, "bizId:" + this.bizId);
            MultiThreadUtil.runOnUiThread(new Runnable() {
                public void run() {
                    ImagePickerView.this.pickerPhotoInfos.clear();
                    for (PhotoInfo info : infos) {
                        PickerPhotoInfo pickerPhotoInfo = new PickerPhotoInfo();
                        if (!PhotoUtil.isNativePhoto(info.getPhotoPath())) {
                            pickerPhotoInfo.pickerPhtotourl = info.getPhotoPath();
                        }
                        pickerPhotoInfo.photoInfo = info;
                        ImagePickerView.this.pickerPhotoInfos.add(pickerPhotoInfo);
                    }
                    ImagePickerView.this.pickerSkeleton.setData(ImagePickerView.this.pickerPhotoInfos);
                }
            });
        }
    }

    public List<PhotoInfo> getPics() {
        List photoInfos = new ArrayList();
        for (PickeInfo info : this.pickerPhotoInfos) {
            if ((info instanceof PickerPhotoInfo) && ((PickerPhotoInfo) info).photoInfo != null) {
                photoInfos.add(((PickerPhotoInfo) info).photoInfo);
            }
        }
        return photoInfos;
    }

    /* access modifiers changed from: private */
    public void addPicShow(List<PickeInfo> photoInfos) {
        this.pickerSkeleton.setData(photoInfos);
    }

    /* access modifiers changed from: private */
    public void addOnLinePhotos(List<PickeInfo> toMerge) {
        if (this.pickerPhotoInfos != null && !this.pickerPhotoInfos.isEmpty()) {
            for (int i = 0; i < this.pickerPhotoInfos.size(); i++) {
                PickerPhotoInfo photoInfo = (PickerPhotoInfo) this.pickerPhotoInfos.get(i);
                if (photoInfo != null && !TextUtils.isEmpty(photoInfo.pickerPhtotourl)) {
                    toMerge.add(photoInfo);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void addLocalPhotoList(List<PhotoInfo> localPhotos, List<PickeInfo> toMerge) {
        for (PhotoInfo photoInfo : localPhotos) {
            boolean isConsumed = false;
            Iterator<PickeInfo> it = this.pickerPhotoInfos.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                PickeInfo info = it.next();
                if (info instanceof PickerPhotoInfo) {
                    PickerPhotoInfo pickerPhotoInfo = (PickerPhotoInfo) info;
                    if (TextUtils.isEmpty(pickerPhotoInfo.pickerPhtotourl) && TextUtils.equals(pickerPhotoInfo.photoInfo.getPhotoPath(), photoInfo.getPhotoPath())) {
                        toMerge.add(pickerPhotoInfo);
                        isConsumed = true;
                        break;
                    }
                }
            }
            if (!isConsumed) {
                PickerPhotoInfo tmp = new PickerPhotoInfo();
                tmp.photoInfo = photoInfo;
                toMerge.add(tmp);
            }
        }
    }

    /* access modifiers changed from: private */
    public void addLocalPhotos(List<PhotoInfo> localPhotos, List<PickeInfo> toMerge) {
        for (PhotoInfo photoInfo : localPhotos) {
            PickerPhotoInfo tmp = new PickerPhotoInfo();
            tmp.photoInfo = photoInfo;
            toMerge.add(tmp);
        }
    }

    private void setPickerPhotoArgs(Bundle bundle, ArrayList<String> localSelectedPhotos) {
        bundle.putBoolean(PhotoParam.ENABLE_CAMERA, true);
        bundle.putBoolean(PhotoParam.ENABLE_PREVIEW, true);
        bundle.putBoolean(PhotoParam.ENABLE_BUCKET, true);
        bundle.putBoolean(PhotoParam.ENABLE_SELECT_ORIGIN, false);
        bundle.putBoolean(PhotoParam.FULLSCREEN_PREVIEW, false);
        bundle.putBoolean(PhotoParam.PHOTO_SELECT_CONTAIN_VIDEO, false);
        bundle.putString("businessId", this.bizId);
        int selected = 0;
        if (this.isInitSelect) {
            bundle.putStringArrayList(PhotoParam.SELECTED_PHOTO_PATHS, localSelectedPhotos);
            if (this.pickerPhotoInfos != null && !this.pickerPhotoInfos.isEmpty()) {
                selected = this.pickerPhotoInfos.size() - localSelectedPhotos.size();
            }
        } else if (this.pickerPhotoInfos != null && !this.pickerPhotoInfos.isEmpty()) {
            selected = this.pickerPhotoInfos.size();
        }
        try {
            String finish = getResources().getString(R.string.select_photo_edit_finish);
            String maxMsg = getResources().getString(R.string.select_photo_maxsmag, new Object[]{Integer.valueOf(this.MAXPICS - selected)});
            bundle.putString(PhotoParam.FINISH_TEXT, finish);
            bundle.putString(PhotoParam.MAX_SELECT_MSG, maxMsg);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(TAG, (Throwable) e);
        }
        bundle.putInt(PhotoParam.MAX_SELECT, this.MAXPICS - selected);
    }

    public void setOnDataChangeListener(final OnDataChangeListener listener) {
        this.pickerSkeleton.setOnDataChangeListener(new com.alipay.mobile.antui.picker.AUImagePickerSkeleton.OnDataChangeListener() {
            public void onDataChanged() {
                if (listener != null) {
                    listener.onDataChanged();
                }
            }

            public void onDataExchanged() {
                if (listener != null) {
                    listener.onDataExchanged();
                }
            }
        });
    }

    public void setExtPickerBundle(Bundle extPickerBundle2) {
        this.extPickerBundle = extPickerBundle2;
    }

    public void setExtBrowseBundle(Bundle extBrowseBundle2) {
        this.extBrowseBundle = extBrowseBundle2;
    }
}
