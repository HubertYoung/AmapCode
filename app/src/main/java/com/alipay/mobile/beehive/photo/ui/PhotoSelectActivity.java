package com.alipay.mobile.beehive.photo.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.video.CompressLevel;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.imageedit.modle.ImageInfo;
import com.alipay.mobile.beehive.imageedit.service.ImageEditListener;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.alipay.mobile.beehive.photo.data.BucketAdapter;
import com.alipay.mobile.beehive.photo.data.BucketInfo;
import com.alipay.mobile.beehive.photo.data.GridAdapter;
import com.alipay.mobile.beehive.photo.data.GridAdapter.OnGridListener;
import com.alipay.mobile.beehive.photo.data.PhotoContext;
import com.alipay.mobile.beehive.photo.data.PhotoItem;
import com.alipay.mobile.beehive.photo.data.PhotoResolver;
import com.alipay.mobile.beehive.photo.data.PhotoResolver.BucketUpdateListener;
import com.alipay.mobile.beehive.photo.data.VideoEditInfo;
import com.alipay.mobile.beehive.photo.util.AnimationUtil;
import com.alipay.mobile.beehive.photo.util.CommonUtils;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.util.UserBehavior;
import com.alipay.mobile.beehive.photo.view.PhotoGrid;
import com.alipay.mobile.beehive.photo.view.PhotoGridView;
import com.alipay.mobile.beehive.photo.view.PhotoGridView.OnOverScrolledListener;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.photo.wrapper.PhotoActivity;
import com.alipay.mobile.beehive.service.PhotoBrowseListener;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoMenu;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.alipay.multimedia.adjuster.api.APMSandboxProcessor;
import com.alipay.multimedia.adjuster.api.data.APMInsertReq.Builder;
import com.autonavi.map.core.MapCustomizeManager;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class PhotoSelectActivity extends PhotoActivity implements OnClickListener, OnScrollListener, OnItemClickListener, OnGridListener, OnOverScrolledListener {
    public static final String ACTION_PHOTO_ADAPTER_CHANGE = "com.alipay.mobile.beehive.photo.ACTION_PHOTO_ADAPTER_CHANGE";
    public static final int CODE_EDIT_VIDEO = 1001;
    public static final int GRID_WIDTH = 60;
    private static final int PERMISSIONS_REQUEST_CAMERA = 1001;
    public static final int REQUEST_CAMERA = 701;
    public static final int REQUEST_PREVIEW = 702;
    public static final String TAG = "PhotoSelectActivity";
    public static boolean enableGifDynamicPreview;
    public static boolean selectGif;
    private boolean afterSaveInstanceState;
    private String allPhotoBucketName;
    private float beautyImageLevel;
    private OnClickListener bottomMenuListener = new OnClickListener() {
        public final void onClick(View view) {
            Object tag = view.getTag();
            PhotoBrowseListener listener = PhotoSelectActivity.this.photoContext.previewListener;
            if (listener == null) {
                Log.w(PhotoSelectActivity.TAG, "invalid listener");
            } else if (tag instanceof PhotoMenu) {
                List imageList = new ArrayList();
                for (PhotoItem photoInfo : PhotoSelectActivity.this.photoContext.selectedList) {
                    imageList.add(photoInfo);
                }
                PhotoMenu photoMenu = (PhotoMenu) tag;
                Activity activity = PhotoSelectActivity.this;
                if ("delete".equals(photoMenu.tag)) {
                    PhotoSelectActivity.this.showDeleteDialog(new PhotoMenu(null, "delete"), imageList);
                } else if (listener.onBottomMenuClick(activity, imageList, photoMenu)) {
                    PhotoLogger.debug(PhotoSelectActivity.TAG, "bottom listener processed");
                } else if (!photoMenu.enableImpl) {
                    PhotoLogger.debug(PhotoSelectActivity.TAG, "default impl not enabled for " + photoMenu.tag);
                } else {
                    if (PhotoMenu.TAG_SAVE.equals(photoMenu.tag)) {
                        PhotoSelectActivity.this.performSaveSelected();
                    }
                    PhotoSelectActivity.this.toggleSelectPhoto();
                }
            }
        }
    };
    private ImageView btBack;
    private Button btFinish;
    private Button btOption;
    private BucketAdapter bucketAdapter;
    private TextView bucketEmptyTips;
    private int bucketIndex;
    private String cameraContext;
    private PhotoItem cameraItem;
    /* access modifiers changed from: private */
    public CheckBox cbSelectOriginal;
    private int compressImageQuality;
    private String contextIndex;
    private boolean enableBucket;
    private boolean enableCamera;
    private boolean enableEdit;
    private boolean enableOption;
    private boolean enablePreview;
    private boolean enableSelectOrigin;
    private int firstVisibleItem;
    private FrameLayout flBuckets;
    /* access modifiers changed from: private */
    public PhotoGridView gvPhoto;
    private Runnable hideTimeRunnable = new Runnable() {
        public final void run() {
            PhotoSelectActivity.this.tvHint.setVisibility(8);
        }
    };
    /* access modifiers changed from: private */
    public boolean isScanFinished = false;
    /* access modifiers changed from: private */
    public boolean isSelOrigin;
    private boolean isSelectVideoOnly;
    private boolean isShowRationale;
    private boolean isSupportVideoEdit;
    private boolean isVideoContain = false;
    private View ivBucket;
    private LinearLayout llBottomMenu;
    private LinearLayout llBuckets;
    private LinearLayout llSelectOriginal;
    private ListView lvBuckets;
    private String mBusinessId;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ImageEditListener mImageEditListener = new ImageEditListener() {
        public final void onImageEdit(boolean isCanceled, String oriPath, ImageInfo info) {
            if (!isCanceled) {
                PhotoItem afterEdit = new PhotoItem(info.path);
                afterEdit.setPhotoWidth(info.width);
                afterEdit.setPhotoHeight(info.height);
                afterEdit.setPhotoOrientation(info.rotation);
                if (PhotoSelectActivity.this.photoContext != null && PhotoSelectActivity.this.photoContext.selectedList != null) {
                    PhotoSelectActivity.this.photoContext.selectedList.clear();
                    PhotoSelectActivity.this.photoContext.selectedList.add(afterEdit);
                    PhotoSelectActivity.this.onSelected();
                }
            }
        }
    };
    private int maxGifPixelCanSend;
    private int maxGifSizeCanSend;
    private int maxSelect;
    private String maxSelectMsg;
    private int minPhotoHeight;
    private int minPhotoSize;
    private int minPhotoWidth;
    private boolean optionActive;
    /* access modifiers changed from: private */
    public View pbLoading;
    private GridAdapter photoAdapter;
    /* access modifiers changed from: private */
    public PhotoContext photoContext;
    private List<PhotoItem> photoList;
    /* access modifiers changed from: private */
    public PhotoResolver photoResolver;
    private RelativeLayout rlBottomBar;
    private String saveFolder;
    private List<PhotoMenu> selectBottomMenu;
    private boolean selectPhoto;
    private String selectedBucketRecord;
    private LinkedHashSet<String> selectedPhotoPaths;
    private File tempPhotoFile;
    private String textFinish;
    private String textPreview;
    private String textTitle;
    private TextView tvAlbum;
    private TextView tvEdit;
    /* access modifiers changed from: private */
    public TextView tvHint;
    private TextView tvPreview;
    private TextView tvTitle;
    private int videoEditTimeLimit;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        PhotoLogger.debug("videoCompact", "version:6");
        super.onCreate(bundle);
        requestWindowFeature(1);
        this.afterSaveInstanceState = false;
        this.firstVisibleItem = 0;
        if (bundle == null) {
            bundle = getIntent().getExtras();
            if (bundle == null) {
                finish();
                return;
            }
        } else {
            PhotoLogger.debug(TAG, "initialize photo select with save instance.");
        }
        setContentView(R.layout.activity_photo_select);
        initViews();
        parseParams(bundle);
        updateViewsByConfig(bundle);
    }

    private void initViews() {
        this.llSelectOriginal = (LinearLayout) findViewById(R.id.ll_select_original_photo);
        this.cbSelectOriginal = (CheckBox) findViewById(R.id.cb_origin);
        this.cbSelectOriginal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public final void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (PhotoSelectActivity.this.photoContext != null) {
                    PhotoSelectActivity.this.photoContext.userOriginPhoto = isChecked;
                    PhotoSelectActivity.this.isSelOrigin = PhotoSelectActivity.this.photoContext.userOriginPhoto;
                }
            }
        });
        this.llBottomMenu = (LinearLayout) findViewById(R.id.ll_bottom_menu);
        this.tvHint = (TextView) findViewById(R.id.tv_timetext);
        this.btOption = (Button) findViewById(R.id.bt_select);
        this.llBuckets = (LinearLayout) findViewById(R.id.ll_buckets);
        this.btBack = (ImageView) findViewById(R.id.bt_back);
        CommonUtils.setTitleBarBackDrawable(this.btBack);
        this.btFinish = (Button) findViewById(R.id.bt_finish);
        this.tvAlbum = (TextView) findViewById(R.id.tv_bucket);
        this.ivBucket = findViewById(R.id.iv_bucket);
        this.tvPreview = (TextView) findViewById(R.id.tv_preview);
        this.tvEdit = (TextView) findViewById(R.id.tv_edit);
        this.tvEdit.setOnClickListener(this);
        this.gvPhoto = (PhotoGridView) findViewById(R.id.gv_photo);
        this.rlBottomBar = (RelativeLayout) findViewById(R.id.rl_bottom_bar);
        this.lvBuckets = (ListView) findViewById(R.id.lv_buckets);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.pbLoading = findViewById(R.id.pb_loading_data);
        this.flBuckets = (FrameLayout) findViewById(R.id.fl_buckets);
        this.flBuckets.setOnClickListener(this);
        this.lvBuckets.setOnItemClickListener(this);
        this.bucketEmptyTips = (TextView) findViewById(R.id.empty_tips);
        this.btBack.setOnClickListener(this);
        this.btFinish.setOnClickListener(this);
        this.tvPreview.setOnClickListener(this);
    }

    private void updateViewsByConfig(Bundle bundle) {
        int i;
        int i2;
        int i3;
        boolean checkable;
        boolean bottomVisible;
        int i4;
        boolean finishVisible;
        int i5;
        int i6 = 0;
        if (this.enableCamera) {
            this.cameraItem = new PhotoItem();
            this.cameraItem.takePhoto = true;
        }
        if (TextUtils.isEmpty(this.textPreview)) {
            this.textPreview = getString(R.string.preview);
        }
        if (TextUtils.isEmpty(this.maxSelectMsg)) {
            this.maxSelectMsg = getString(R.string.max_message, new Object[]{Integer.valueOf(this.maxSelect)});
        }
        if (TextUtils.isEmpty(this.textFinish)) {
            this.textFinish = getString(R.string.send);
        }
        if (TextUtils.isEmpty(this.textTitle)) {
            this.textTitle = getString(R.string.all_bucket);
            if (this.isVideoContain) {
                this.textTitle = getString(R.string.all_bucket_with_video);
            }
        }
        this.tvTitle.setText(this.textTitle);
        LinearLayout linearLayout = this.llBuckets;
        if (this.enableBucket) {
            i = 0;
        } else {
            i = 8;
        }
        linearLayout.setVisibility(i);
        this.tvPreview.setText(this.textPreview);
        TextView textView = this.tvPreview;
        if (!this.enablePreview || this.maxSelect <= 1) {
            i2 = 8;
        } else {
            i2 = 0;
        }
        textView.setVisibility(i2);
        TextView textView2 = this.tvEdit;
        if (this.enableEdit) {
            i3 = 0;
        } else {
            i3 = 8;
        }
        textView2.setVisibility(i3);
        if (!this.selectPhoto || this.maxSelect <= 1) {
            checkable = false;
        } else {
            checkable = true;
        }
        this.photoAdapter = new GridAdapter(this, null, checkable);
        this.photoAdapter.setGridListener(this);
        this.photoAdapter.setEnableVideoEdit(this.isSupportVideoEdit);
        this.gvPhoto.setAdapter(this.photoAdapter);
        if (!this.selectPhoto || this.maxSelect <= 0) {
            bottomVisible = false;
        } else {
            bottomVisible = true;
        }
        RelativeLayout relativeLayout = this.rlBottomBar;
        if (bottomVisible) {
            i4 = 0;
        } else {
            i4 = 8;
        }
        relativeLayout.setVisibility(i4);
        if (!this.selectPhoto || this.maxSelect <= 1) {
            finishVisible = false;
        } else {
            finishVisible = true;
        }
        Button button = this.btFinish;
        if (finishVisible) {
            i5 = 0;
        } else {
            i5 = 8;
        }
        button.setVisibility(i5);
        this.photoContext = PhotoContext.get(this.contextIndex);
        PhotoContext.remove(this.contextIndex);
        this.photoList = this.photoContext.photoList;
        ArrayList photoPaths = bundle.getStringArrayList(PhotoParam.SELECTED_PHOTO_PATHS);
        if (photoPaths != null && !photoPaths.isEmpty()) {
            this.selectedPhotoPaths = new LinkedHashSet<>();
            this.selectedPhotoPaths.addAll(photoPaths);
        }
        this.allPhotoBucketName = getString(R.string.all_bucket);
        if (this.isVideoContain) {
            this.allPhotoBucketName = getString(R.string.all_bucket_with_video);
        }
        this.tvAlbum.setText(this.allPhotoBucketName);
        this.tvHint.setVisibility(8);
        if (!this.selectPhoto) {
            this.ivBucket.setVisibility(8);
        }
        this.btOption.setOnClickListener(this);
        Button button2 = this.btOption;
        if (!this.enableOption) {
            i6 = 8;
        }
        button2.setVisibility(i6);
        ImageHelper.optimizeView(this.gvPhoto, null);
        this.gvPhoto.setOnOverScrolledListener(this);
        initBottomMenu();
        if (this.optionActive) {
            updateOption();
        }
        long startAt = System.currentTimeMillis();
        PhotoLogger.debug("PhotoDisplayLink", "updateViewsByConfig:###");
        initPhotoResolver();
        setupInitData();
        PhotoLogger.debug(TAG, "initPhotoResolver cost time(ms) = " + (System.currentTimeMillis() - startAt));
        setupSelectOriginal(bundle);
    }

    private void setupSelectOriginal(Bundle bundle) {
        this.llSelectOriginal.setVisibility(this.enableSelectOrigin ? 0 : 8);
        if (this.enableSelectOrigin) {
            this.photoContext.userOriginPhoto = bundle.getBoolean(PhotoParam.USE_ORIGIN_PHOTO, false);
            this.cbSelectOriginal.setChecked(this.photoContext.userOriginPhoto);
            this.llSelectOriginal.findViewById(R.id.tv_origin).setOnClickListener(new OnClickListener() {
                public final void onClick(View v) {
                    PhotoSelectActivity.this.cbSelectOriginal.toggle();
                }
            });
        }
    }

    private void parseParams(Bundle bundle) {
        boolean z;
        this.enableSelectOrigin = bundle.getBoolean(PhotoParam.ENABLE_SELECT_ORIGIN, true);
        this.saveFolder = bundle.getString(PhotoParam.SAVE_FOLDER);
        if (TextUtils.isEmpty(this.saveFolder)) {
            this.saveFolder = PhotoUtil.getDefaultPhotoFolder();
        }
        this.maxGifSizeCanSend = bundle.getInt(PhotoParam.MAX_SIZE_GIF_SEND, 5242880);
        this.maxGifPixelCanSend = bundle.getInt(PhotoParam.MAX_GIF_PIXEL_CAN_SEND, PhotoParam.DEFAULT_MAX_GIF_PIXEL_CAN_SEND);
        this.mBusinessId = bundle.getString("businessId");
        ImageHelper.updateBusinessId(this.mBusinessId, TAG);
        this.isVideoContain = bundle.getBoolean(PhotoParam.PHOTO_SELECT_CONTAIN_VIDEO, false);
        this.isSelectVideoOnly = bundle.getBoolean(PhotoParam.SELECT_VIDEO_ONLY, false);
        if (this.isSelectVideoOnly || this.isVideoContain) {
            z = true;
        } else {
            z = false;
        }
        this.isVideoContain = z;
        this.beautyImageLevel = bundle.getFloat(PhotoParam.BEAUTY_IMAGE_LEVEL, -1.0f);
        this.compressImageQuality = bundle.getInt(PhotoParam.COMPRESS_IMAGE_QUALITY, -1);
        this.selectPhoto = bundle.getBoolean(PhotoParam.PHOTO_SELECT, false);
        this.enableCamera = bundle.getBoolean(PhotoParam.ENABLE_CAMERA, true);
        this.enablePreview = bundle.getBoolean(PhotoParam.ENABLE_PREVIEW, true);
        this.enableEdit = bundle.getBoolean(PhotoParam.ENABLE_EDIT_PHOTO, false);
        this.enableBucket = bundle.getBoolean(PhotoParam.ENABLE_BUCKET, true);
        this.enableOption = bundle.getBoolean(PhotoParam.ENABLE_GRID_OPTION, false);
        this.isSupportVideoEdit = bundle.getBoolean(PhotoParam.ENABLE_VIDEO_CUT, false);
        this.videoEditTimeLimit = bundle.getInt(PhotoParam.VIDEO_TIME_LIMIT, 10000);
        this.textPreview = bundle.getString(PhotoParam.PREVIEW_BUTTON);
        this.maxSelect = bundle.getInt(PhotoParam.MAX_SELECT, 9);
        this.maxSelectMsg = bundle.getString(PhotoParam.MAX_SELECT_MSG);
        this.textFinish = bundle.getString(PhotoParam.FINISH_TEXT);
        this.selectBottomMenu = bundle.getParcelableArrayList(PhotoParam.SELECT_BOTTOM_MENU);
        this.contextIndex = bundle.getString(PhotoParam.CONTEXT_INDEX);
        PhotoLogger.debug(TAG, "PhotoSelect context index " + this.contextIndex);
        this.textTitle = bundle.getString(PhotoParam.BUCKET_NAME);
        int finishBtnBgColor = bundle.getInt(PhotoParam.FINISH_BTN_BG_COLOR, -1);
        if (finishBtnBgColor != -1) {
            this.btFinish.setBackgroundColor(finishBtnBgColor);
        }
        this.optionActive = bundle.getBoolean("optionActive", false);
        selectGif = bundle.getBoolean(PhotoParam.SELECT_GIF, false);
        enableGifDynamicPreview = bundle.getBoolean(PhotoParam.ENABLE_GIF_DYNAMIC_PREVIEW, true);
        this.minPhotoSize = bundle.getInt(PhotoParam.MIN_PHOTO_SIZE, 10240);
        this.minPhotoWidth = bundle.getInt(PhotoParam.KEY_MIN_PHOTO_WIDTH, 0);
        this.minPhotoHeight = bundle.getInt(PhotoParam.KEY_MIN_PHOTO_HEIGHT, 0);
        this.cameraContext = this.contextIndex + "_camera";
    }

    private void initPhotoResolver() {
        PhotoLogger.debug("PhotoDisplayLink", "initPhotoResolver:###");
        if (!this.enableBucket || !this.selectPhoto || !isPhotoListEmpty()) {
            this.enableBucket = false;
        } else if (this.photoContext.photoResolver == null) {
            doInitPhotoResolver();
            callResolverToStartScan();
            this.llBuckets.setOnClickListener(this);
        } else {
            this.photoResolver = this.photoContext.photoResolver;
        }
    }

    private void setupInitData() {
        if (this.enableBucket) {
            onBucketSelected(this.allPhotoBucketName, "onBucketReady");
            this.bucketAdapter = new BucketAdapter(this, this.photoResolver.getBucketList());
            this.lvBuckets.setAdapter(this.bucketAdapter);
            checkBuketEmpty();
            this.bucketIndex = 0;
            return;
        }
        if (isPhotoListEmpty()) {
            ((TextView) findViewById(R.id.tv_no_photo)).setVisibility(0);
        } else {
            this.photoAdapter.setData(this.photoList);
        }
        PhotoLogger.debug(TAG, "disable bucket for user set photoList.");
        this.ivBucket.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public boolean isPhotoListEmpty() {
        return this.photoList == null || this.photoList.isEmpty();
    }

    private void callResolverToStartScan() {
        PhotoLogger.debug("PhotoDisplayLink", "callResolverToStartScan:###");
        this.isScanFinished = false;
        if (this.isSelectVideoOnly) {
            this.photoResolver.asyncScanPhotoAndVideo(false, true);
        } else {
            this.photoResolver.asyncScanPhotoAndVideo(true, this.isVideoContain);
        }
        this.mHandler.postDelayed(new Runnable() {
            public final void run() {
                if (PhotoSelectActivity.this.isScanFinished || PhotoSelectActivity.this.isFinishing()) {
                    PhotoLogger.d(PhotoSelectActivity.TAG, "Cost less than 1000ms.");
                    return;
                }
                PhotoLogger.d(PhotoSelectActivity.TAG, "Cost more than 1000ms ,display progressbar.");
                PhotoSelectActivity.this.pbLoading.setVisibility(0);
            }
        }, 1000);
    }

    private void doInitPhotoResolver() {
        PhotoLogger.debug("PhotoDisplayLink", "doInitPhotoResolver:###");
        this.photoResolver = new PhotoResolver(this, new BucketUpdateListener() {
            public final void onScanFinished() {
                if (!(PhotoSelectActivity.this.photoResolver == null || PhotoSelectActivity.this.photoResolver.scanStatistics == null)) {
                    PhotoSelectActivity.this.photoResolver.scanStatistics.userLeaveBeforeScanFinish = PhotoSelectActivity.this.isFinishing();
                }
                PhotoSelectActivity.this.isScanFinished = true;
                PhotoSelectActivity.this.pbLoading.setVisibility(8);
                PhotoSelectActivity.this.restorePreSelectedPhotos();
                PhotoSelectActivity.this.onScanFinisUpdateBucket();
            }

            public final void onScanStep() {
                if (!PhotoSelectActivity.this.isFinishing()) {
                    PhotoSelectActivity.this.updateFirstDataArriveTime();
                    PhotoSelectActivity.this.doUpdatePhotoData("onScanStep");
                }
            }
        });
        this.photoResolver.setEnableStepScan(this.selectedPhotoPaths == null || this.selectedPhotoPaths.isEmpty());
        this.photoResolver.setAllBucketName(this.allPhotoBucketName);
        this.photoResolver.setEnableGif(selectGif);
        this.photoResolver.setMinPhotoSize(this.minPhotoSize);
        this.photoResolver.setMinPhotoWidth(this.minPhotoWidth);
        this.photoResolver.setMinPhotoHeight(this.minPhotoHeight);
        this.photoResolver.setSelectedPhotoPaths(this.selectedPhotoPaths);
        this.photoContext.photoResolver = this.photoResolver;
    }

    /* access modifiers changed from: private */
    public void updateFirstDataArriveTime() {
        if (this.photoResolver != null && this.photoResolver.scanStatistics != null && this.photoResolver.scanStatistics.firstDataTimePast <= 0) {
            this.photoResolver.scanStatistics.firstDataTimePast = System.currentTimeMillis() - this.photoResolver.scanStatistics.triggerScanTime;
        }
    }

    private void checkBuketEmpty() {
        this.bucketEmptyTips.setVisibility(this.bucketAdapter.getData().isEmpty() ? 0 : 8);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PhotoLogger.debug(TAG, "onSaveInstanceState " + this.contextIndex);
        this.afterSaveInstanceState = true;
        bundle.putAll(getIntent().getExtras());
        bundle.putInt(PhotoParam.PREVIEW_POSITION, this.gvPhoto.getFirstVisiblePosition());
        bundle.putString(PhotoParam.CONTEXT_INDEX, this.contextIndex);
        bundle.putBoolean("optionActive", this.optionActive);
        PhotoContext.contextMap.put(this.contextIndex, this.photoContext);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PhotoContext.remove(this.contextIndex);
        final int position = intent.getIntExtra(PhotoParam.PREVIEW_POSITION, -1);
        int first = this.gvPhoto.getFirstVisiblePosition();
        int last = this.gvPhoto.getLastVisiblePosition();
        if (position != -1 && (position > last || position < first)) {
            this.gvPhoto.setSelection(position);
        }
        if (position == 0) {
            PhotoLogger.debug(TAG, "no need to indicate current position.");
        } else {
            this.gvPhoto.postDelayed(new Runnable() {
                public final void run() {
                    PhotoGrid photoGrid = (PhotoGrid) PhotoSelectActivity.this.gvPhoto.getChildAt(position - PhotoSelectActivity.this.gvPhoto.getFirstVisiblePosition());
                    if (photoGrid != null) {
                        photoGrid.animateSelection();
                    }
                }
            }, 400);
        }
    }

    private void initBottomMenu() {
        int size;
        if (this.selectBottomMenu == null) {
            size = 0;
        } else {
            size = this.selectBottomMenu.size();
        }
        for (int index = 0; index < size; index++) {
            PhotoMenu photoMenu = this.selectBottomMenu.get(index);
            View view = getLayoutInflater().inflate(R.layout.photo_bottom_menu_item, this.llBottomMenu, false);
            ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            Drawable drawable = getDrawable(photoMenu.tag);
            TextView tvTitle2 = (TextView) view.findViewById(R.id.tv_title);
            if (drawable != null) {
                ivIcon.setImageDrawable(drawable);
                ivIcon.setVisibility(0);
                tvTitle2.setVisibility(8);
            } else {
                ivIcon.setVisibility(8);
                tvTitle2.setVisibility(0);
                tvTitle2.setText(photoMenu.title);
            }
            view.setOnClickListener(this.bottomMenuListener);
            view.setTag(photoMenu);
            LayoutParams params = new LayoutParams(0, -1);
            params.weight = 1.0f;
            this.llBottomMenu.addView(view, index, params);
        }
        this.llBottomMenu.setVisibility(8);
    }

    private Drawable getDrawable(String tag) {
        if (PhotoMenu.TAG_COLLECT.equals(tag)) {
            return getResources().getDrawable(R.drawable.bottom_collect_selector);
        }
        if (PhotoMenu.TAG_SAVE.equals(tag)) {
            return getResources().getDrawable(R.drawable.bottom_save_selector);
        }
        if ("delete".equals(tag)) {
            return getResources().getDrawable(R.drawable.bottom_delete_selector);
        }
        if ("share".equals(tag)) {
            return getResources().getDrawable(R.drawable.bottom_share_selector);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void showDeleteDialog(final PhotoMenu photoMenu, final List<PhotoInfo> toDelete) {
        DialogInterface.OnClickListener positive = new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PhotoSelectActivity.this.performDeleteSelected();
                PhotoSelectActivity.this.toggleSelectPhoto();
                if (PhotoSelectActivity.this.isPhotoListEmpty()) {
                    ((TextView) PhotoSelectActivity.this.findViewById(R.id.tv_no_photo)).setVisibility(0);
                }
                PhotoBrowseListener listener = PhotoSelectActivity.this.photoContext.previewListener;
                if (listener != null) {
                    listener.onBottomMenuClick(PhotoSelectActivity.this.getParent(), toDelete, photoMenu);
                }
            }
        };
        alert("", getString(R.string.confirm_delete), getString(R.string.delete), positive, getString(R.string.cancel), null);
    }

    /* access modifiers changed from: private */
    public void performDeleteSelected() {
        if (!this.photoContext.selectedList.isEmpty()) {
            this.photoContext.photoList.removeAll(this.photoContext.selectedList);
            this.photoAdapter.getData().removeAll(this.photoContext.selectedList);
            this.photoContext.selectedList.clear();
            this.photoAdapter.notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: private */
    public void performSaveSelected() {
        String message;
        boolean hasVideo = false;
        Iterator<PhotoItem> it = this.photoContext.selectedList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PhotoInfo photoInfo = it.next();
            if (photoInfo.getMediaType() == 1) {
                hasVideo = true;
                break;
            }
            PhotoUtil.savePhoto(photoInfo, this.saveFolder, null);
        }
        if (hasVideo) {
            message = getString(R.string.can_not_save_video);
        } else {
            message = getString(R.string.str_save_to_album);
        }
        toast(message, 0);
        if (!hasVideo) {
            this.photoContext.selectedList.clear();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        updateContent();
        updateGridStat();
    }

    private void updateContent() {
        boolean z;
        boolean z2;
        boolean z3;
        PhotoLogger.debug(TAG, "updateContent:###");
        int selectedCount = 0;
        if (this.photoContext.selectedList != null) {
            selectedCount = this.photoContext.selectedList.size();
        }
        PhotoLogger.debug(TAG, "selectedCount =" + selectedCount);
        if (this.llBottomMenu.getVisibility() == 0) {
            int count = this.llBottomMenu.getChildCount();
            for (int index = 0; index < count; index++) {
                View childAt = this.llBottomMenu.getChildAt(index);
                if (selectedCount > 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                childAt.setEnabled(z3);
            }
        }
        Button button = this.btFinish;
        if (selectedCount > 0) {
            z = true;
        } else {
            z = false;
        }
        button.setEnabled(z);
        this.tvPreview.setText(selectedCount > 0 ? this.textPreview + "(" + selectedCount + ")" : this.textPreview);
        TextView textView = this.tvPreview;
        if (selectedCount > 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        textView.setEnabled(z2);
        if (selectedCount != 1 || this.photoContext == null || this.photoContext.selectedList == null || this.photoContext.selectedList.isEmpty() || this.photoContext.selectedList.get(0).getMediaType() != 0 || (this.photoContext.selectedList.get(0).isGif() && enableGifDynamicPreview)) {
            this.tvEdit.setEnabled(false);
        } else {
            this.tvEdit.setEnabled(true);
        }
        this.btFinish.setText(selectedCount > 0 ? this.textFinish + "(" + selectedCount + "/" + this.maxSelect + ")" : this.textFinish);
        if (!this.enableOption) {
            return;
        }
        if (!this.optionActive) {
            this.tvTitle.setText(this.textTitle);
        } else {
            this.tvTitle.setText(getString(R.string.select_photo_count, new Object[]{Integer.valueOf(selectedCount)}));
        }
    }

    public void onClick(View view) {
        if (view.equals(this.btFinish)) {
            onSelected();
        } else if (view.equals(this.llBuckets)) {
            toggleBucketList();
        } else if (view.equals(this.tvPreview)) {
            previewSelectedPhotos();
        } else if (view.equals(this.btBack)) {
            toggleFinish();
        } else if (view.equals(this.btOption)) {
            toggleSelectPhoto();
        } else if (view.equals(this.flBuckets)) {
            toggleBucketList();
        } else if (view == this.tvEdit) {
            editSelectedPhoto();
        }
    }

    private void editSelectedPhoto() {
        ImageEditService service = (ImageEditService) MicroServiceUtil.getMicroService(ImageEditService.class);
        UserBehavior.onPhotoSelectActivityEditImageClicked();
        if (service == null) {
            PhotoLogger.warn((String) TAG, (String) "Get ImageEditService return null!");
        } else if (this.photoContext == null || this.photoContext.selectedList == null || this.photoContext.selectedList.isEmpty()) {
            PhotoLogger.warn((String) TAG, (String) "None photo is selected when edit called!");
        } else {
            service.editImage(this.mApp, this.mImageEditListener, this.photoContext.selectedList.get(0).getPhotoPath(), this.mBusinessId, null);
        }
    }

    private void previewSelectedPhotos() {
        List pList = new ArrayList();
        pList.addAll(this.photoContext.selectedList);
        List sList = new ArrayList();
        sList.addAll(this.photoContext.selectedList);
        this.photoContext.photoList = pList;
        this.photoContext.selectedList = sList;
        preview(0, true);
    }

    /* access modifiers changed from: private */
    public void onSelected() {
        this.photoContext.sendSelectedPhoto(this, this.beautyImageLevel, this.compressImageQuality, new Runnable() {
            public final void run() {
                PhotoSelectActivity.this.toggleFinish();
            }
        });
    }

    /* access modifiers changed from: private */
    public void toggleFinish() {
        PhotoLogger.debug(TAG, "toggleFinish " + this.contextIndex);
        finish();
    }

    /* access modifiers changed from: private */
    public void toggleSelectPhoto() {
        boolean z;
        if (!this.optionActive) {
            z = true;
        } else {
            z = false;
        }
        this.optionActive = z;
        if (!this.optionActive) {
            for (PhotoItem selected : this.photoList) {
                selected.setSelected(false);
            }
            this.photoContext.selectedList.clear();
        }
        updateOption();
    }

    private void updateOption() {
        boolean checkable = false;
        if (!this.optionActive) {
            this.btOption.setText(getString(R.string.select));
            this.llBottomMenu.setVisibility(8);
        } else {
            this.btOption.setText(getString(R.string.cancel));
            this.llBottomMenu.setVisibility(0);
        }
        if (this.optionActive || (this.selectPhoto && this.maxSelect > 1)) {
            checkable = true;
        }
        this.photoAdapter.setCheckable(checkable);
        updateGridStat();
        updateContent();
    }

    private void updateGridStat() {
        boolean checkable = true;
        int first = this.gvPhoto.getFirstVisiblePosition();
        int last = this.gvPhoto.getLastVisiblePosition();
        if ((this.maxSelect <= 1 || !this.selectPhoto) && !this.optionActive) {
            checkable = false;
        }
        for (int index = first; index <= last; index++) {
            PhotoGrid photoGrid = (PhotoGrid) this.gvPhoto.getChildAt(index - first);
            photoGrid.setCheckable(checkable);
            photoGrid.updateGrid();
        }
    }

    public void finish() {
        super.finish();
        AnimationUtil.fadeInFadeOut(this);
        if (this.photoContext == null) {
            PhotoLogger.warn((String) TAG, (String) "finish called,photoContent happend to be Null!");
        } else if (!this.photoContext.selectSent && this.photoContext.selectListener != null) {
            this.photoContext.selectListener.onSelectCanceled();
        }
    }

    private void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File cameraDir = getCameraDirPath();
        cameraDir.mkdirs();
        if (PhotoUtil.isQCompact()) {
            this.tempPhotoFile = new File(Environment.getExternalStorageDirectory(), "alipay/camera/" + System.currentTimeMillis() + ".jpg");
            if (!this.tempPhotoFile.getParentFile().exists()) {
                this.tempPhotoFile.getParentFile().mkdirs();
            }
            intent.setFlags(2);
            intent.putExtra(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, FileProvider.getUriForFile(this, "com.alipay.mobile.camera.fileprovider", this.tempPhotoFile));
        } else {
            this.tempPhotoFile = new File(cameraDir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg");
            intent.putExtra(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, Uri.fromFile(this.tempPhotoFile));
        }
        try {
            startExtActivityForResult(intent, 701);
        } catch (Exception e) {
            toast(getString(R.string.no_camera_permission), 0);
        }
    }

    private File getCameraDirPath() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (path.exists()) {
            File test1 = new File(path, "100MEDIA/");
            if (test1.exists()) {
                return test1;
            }
            File test2 = new File(path, "100ANDRO/");
            if (test2.exists()) {
                return test2;
            }
            File test3 = new File(path, "Camera/");
            if (!test3.exists()) {
                test3.mkdirs();
            }
            return test3;
        }
        File path2 = new File(path, "Camera/");
        path2.mkdirs();
        return path2;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 701 && resultCode == -1) {
            onTakePhoto();
        } else if (requestCode == 702 && resultCode == -1) {
            this.photoContext.selectSent = true;
            toggleFinish();
        } else if (requestCode == 702 && resultCode == 0) {
            if (data != null) {
                this.isSelOrigin = data.getBooleanExtra(PhotoParam.USE_ORIGIN_PHOTO, false);
                if (this.cbSelectOriginal.getVisibility() == 0) {
                    this.cbSelectOriginal.setChecked(this.isSelOrigin);
                }
            }
            this.photoContext.photoList = this.photoList;
            if (PhotoContext.contextMap.containsKey(this.cameraContext)) {
                PhotoContext.contextMap.remove(this.cameraContext);
            }
        } else if (requestCode == 1001 && resultCode == -1) {
            toggleFinish();
        }
    }

    private void onTakePhoto() {
        Uri fileUri;
        if (this.tempPhotoFile == null) {
            PhotoLogger.error((String) TAG, (String) "CameraPath empty!");
            return;
        }
        if (PhotoUtil.isQCompact()) {
            fileUri = FileProvider.getUriForFile(this, "com.alipay.mobile.camera.fileprovider", this.tempPhotoFile);
        } else {
            fileUri = Uri.fromFile(this.tempPhotoFile);
        }
        String photoPath = "file://" + this.tempPhotoFile.getAbsolutePath();
        if (!this.tempPhotoFile.exists()) {
            PhotoLogger.debug(TAG, "failed to get camera file");
            return;
        }
        if (PhotoUtil.isQCompact()) {
            APMSandboxProcessor.insertMediaFile(new Builder(Media.EXTERNAL_CONTENT_URI, fileUri, this.tempPhotoFile.getName()).mimeType("image/jpeg").build());
        } else {
            Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            mediaScanIntent.setData(fileUri);
            sendBroadcast(mediaScanIntent);
        }
        PhotoLogger.debug(TAG, "onTakePhoto from camera " + photoPath);
        PhotoItem photoInfo = new PhotoItem(photoPath, true);
        photoInfo.setIsPicCurrentlyTaked(true);
        photoInfo.setPhotoSize(this.tempPhotoFile.length());
        photoInfo.setPhotoOrientation(readPictureDegree(this.tempPhotoFile.getAbsolutePath()));
        if (this.maxSelect == 1) {
            if (this.photoList == null) {
                this.photoList = new ArrayList();
            }
            this.photoList.add(photoInfo);
            this.photoContext.selectedList.add(photoInfo);
            onSelected();
            return;
        }
        PhotoContext tempContext = PhotoContext.get(this.cameraContext);
        tempContext.selectedList = new ArrayList();
        tempContext.photoList = new ArrayList();
        if (!(this.photoContext == null || this.photoContext.selectedList == null || this.photoContext.selectedList.size() <= 0)) {
            tempContext.selectedList.addAll(this.photoContext.selectedList);
            tempContext.photoList.addAll(this.photoContext.selectedList);
        }
        tempContext.selectedList.add(photoInfo);
        tempContext.photoList.add(photoInfo);
        tempContext.selectListener = this.photoContext.selectListener;
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        intent.putExtras(getIntent().getExtras());
        int previewPosition = tempContext.photoList.size() - 1;
        if (previewPosition < 0) {
            previewPosition = 0;
        }
        intent.putExtra(PhotoParam.PREVIEW_POSITION, previewPosition);
        intent.putExtra(PhotoParam.PHOTO_SELECT, this.selectPhoto);
        intent.putExtra(PhotoParam.MAX_SELECT, this.maxSelect);
        intent.putExtra(PhotoParam.CONTEXT_INDEX, this.cameraContext);
        intent.putExtra(PhotoParam.USE_ORIGIN_PHOTO, this.isSelOrigin);
        startActivityForResult(intent, 702);
        AnimationUtil.fadeInFadeOut(this);
    }

    public static int readPictureDegree(String path) {
        try {
            switch (new ExifInterface(path).getAttributeInt("Orientation", 1)) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            PhotoLogger.warn((String) TAG, (Throwable) e);
            return 0;
        }
    }

    private void preview(int index, boolean isPreviewSelected) {
        int targetIndex;
        PhotoLogger.w(TAG, "preview:### index = " + index);
        if (isPreviewSelected || !preventSelect(getPhotoInfoAtIndex(index))) {
            PhotoContext.contextMap.put(this.contextIndex, this.photoContext);
            if (isPreviewSelected || !goToEditVideo(getPhotoInfoAtIndex(index), this.contextIndex)) {
                Intent intent = new Intent(this, PhotoPreviewActivity.class);
                intent.putExtras(getIntent().getExtras());
                if (this.isSupportVideoEdit) {
                    targetIndex = getIndexAfterFilterVideo(index);
                } else {
                    targetIndex = index;
                }
                intent.putExtra(PhotoParam.PREVIEW_POSITION, targetIndex);
                intent.putExtra(PhotoParam.PHOTO_SELECT, this.selectPhoto);
                intent.addFlags(131072);
                intent.addFlags(16777216);
                intent.putExtra(PhotoParam.USE_ORIGIN_PHOTO, this.isSelOrigin);
                intent.putExtra(PhotoParam.SELECT_GRID, this.optionActive);
                intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
                startActivityForResult(intent, 702);
                AnimationUtil.fadeInFadeOut(this);
                return;
            }
            PhotoLogger.w(TAG, "Go to edit video.");
            return;
        }
        PhotoLogger.w(TAG, "Prevent from selecting video after photo selected.");
    }

    private PhotoInfo getPhotoInfoAtIndex(int index) {
        PhotoInfo ret = null;
        try {
            return this.photoList.get(index);
        } catch (Exception e) {
            PhotoLogger.d(TAG, "getPhotoInfoAtIndex:###Exception, " + e.getMessage());
            return ret;
        }
    }

    private boolean preventSelect(PhotoInfo target) {
        if (!isVideoAndEditable(target) || this.photoContext == null || this.photoContext.selectedList == null || this.photoContext.selectedList.isEmpty()) {
            return false;
        }
        toast(getString(R.string.str_cant_choose_both), 0);
        PhotoLogger.d(TAG, "Has select photo,prevent to edit video.");
        return true;
    }

    private boolean goToEditVideo(PhotoInfo target, String contextIndex2) {
        if (!isVideoAndEditable(target)) {
            return false;
        }
        Intent editIntent = new Intent(this, VideoPreviewActivity.class);
        editIntent.putExtra(VideoPreviewActivity.KEY_EXTRA_TIME_LIMIT, this.videoEditTimeLimit);
        editIntent.putExtra(VideoPreviewActivity.KEY_EXTRA_VIDEO_INFO, new VideoEditInfo(target));
        editIntent.putExtra(PhotoParam.CONTEXT_INDEX, contextIndex2);
        editIntent.putExtra(VideoPreviewActivity.KEY_HIDE_EDIT, getIntent().getBooleanExtra(VideoPreviewActivity.KEY_HIDE_EDIT, false));
        editIntent.putExtra(PhotoParam.KEY_VIDEO_COMPRESS_LEVEL, getIntent().getIntExtra(PhotoParam.KEY_VIDEO_COMPRESS_LEVEL, CompressLevel.V540P.getValue()));
        editIntent.putExtra(PhotoParam.KEY_SHOW_VIDEO_TIME_INDICATOR, getIntent().getBooleanExtra(PhotoParam.KEY_SHOW_VIDEO_TIME_INDICATOR, false));
        startActivityForResult(editIntent, 1001);
        return true;
    }

    private boolean isVideoAndEditable(PhotoInfo target) {
        if (target != null && this.isSupportVideoEdit && target.isVideo()) {
            return true;
        }
        return false;
    }

    private int getIndexAfterFilterVideo(int index) {
        PhotoLogger.w(TAG, "getIndexAfterFilterVideo:###");
        int ret = index;
        try {
            if (this.photoList != null && !this.photoList.isEmpty()) {
                PhotoInfo target = this.photoList.get(index);
                int j = 0;
                int i = 0;
                while (true) {
                    if (i >= this.photoList.size()) {
                        break;
                    }
                    PhotoItem pi = this.photoList.get(i);
                    if (!pi.isVideo()) {
                        if (pi == target) {
                            ret = j;
                            break;
                        }
                        j++;
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            PhotoLogger.w(TAG, "getIndexAfterFilterVideo:Should not be here. " + e.getMessage());
        }
        PhotoLogger.w(TAG, "Before = " + index + " after = " + ret);
        return ret;
    }

    private void toggleBucketList() {
        if (this.lvBuckets.getVisibility() != 0) {
            this.flBuckets.setVisibility(0);
            this.flBuckets.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
            this.lvBuckets.setVisibility(0);
            this.lvBuckets.startAnimation(AnimationUtils.loadAnimation(this, R.anim.effect_select_pop_up));
            return;
        }
        this.flBuckets.setVisibility(4);
        this.flBuckets.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        this.lvBuckets.setVisibility(4);
        this.lvBuckets.startAnimation(AnimationUtils.loadAnimation(this, R.anim.effect_select_pop_down));
    }

    public void onBackPressed() {
        if (this.lvBuckets.getVisibility() == 0) {
            toggleBucketList();
        } else if (!this.enableOption || !this.optionActive) {
            super.onBackPressed();
        } else {
            toggleSelectPhoto();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BucketInfo bucket = (BucketInfo) parent.getItemAtPosition(position);
        for (int index = 0; index < parent.getCount(); index++) {
            BucketInfo bi = (BucketInfo) parent.getItemAtPosition(index);
            if (index == position) {
                bi.setSelected(true);
            } else {
                bi.setSelected(false);
            }
        }
        this.bucketIndex = position;
        this.allPhotoBucketName = bucket.getName();
        this.bucketAdapter.notifyDataSetChanged();
        this.tvAlbum.setText(this.allPhotoBucketName);
        toggleBucketList();
        onBucketSelected(this.allPhotoBucketName, "onItemClick");
    }

    private void onBucketSelected(String bucket, String caller) {
        PhotoLogger.debug("PhotoDisplayLink", "onBucketSelected: " + bucket + ",caller -> " + caller);
        PhotoLogger.debug(TAG, "选中专辑：" + bucket);
        if (this.photoResolver != null) {
            this.photoList = this.photoResolver.getBucketList(bucket);
            if (this.isScanFinished) {
                updateContent();
            }
        }
        this.photoAdapter.cameraItem = (!this.enableCamera || this.bucketIndex != 0) ? null : this.cameraItem;
        if (this.photoContext == null) {
            PhotoLogger.warn((String) TAG, (String) "onBucketSelected photoContext null!");
            return;
        }
        this.photoContext.photoList = this.photoList;
        this.photoAdapter.setData(this.photoList);
        if (!TextUtils.equals(this.selectedBucketRecord, bucket)) {
            this.gvPhoto.post(new Runnable() {
                public final void run() {
                    PhotoSelectActivity.this.gvPhoto.setSelection(0);
                }
            });
        }
        this.selectedBucketRecord = bucket;
    }

    /* access modifiers changed from: private */
    public void restorePreSelectedPhotos() {
        PhotoLogger.d(TAG, "restorePreSelectedPhotos:###");
        List allPhotos = this.photoResolver.getBucketList(this.allPhotoBucketName);
        if (this.photoContext.selectedList != null && allPhotos != null && !allPhotos.isEmpty()) {
            try {
                for (PhotoItem photoItem : allPhotos) {
                    if (photoItem != null && photoItem.getSelected() && !this.photoContext.selectedList.contains(photoItem)) {
                        this.photoContext.selectedList.add(photoItem);
                    }
                }
                doSort();
            } catch (Exception e) {
                PhotoLogger.w(TAG, "restorePreSelectedPhotos Exception :" + e.getMessage());
            }
            updateContent();
        }
    }

    private void doSort() {
        if (this.selectedPhotoPaths != null && !this.selectedPhotoPaths.isEmpty()) {
            Collections.sort(this.photoContext.selectedList, new Comparator<PhotoItem>() {
                /* access modifiers changed from: private */
                /* renamed from: a */
                public int compare(PhotoItem lhs, PhotoItem rhs) {
                    return PhotoSelectActivity.this.getPathIndex(lhs.getPhotoPath()) - PhotoSelectActivity.this.getPathIndex(rhs.getPhotoPath());
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public int getPathIndex(String path) {
        Iterator iterator = this.selectedPhotoPaths.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            if (((String) iterator.next()).equals(path)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void onGridClick(PhotoGrid grid, int position) {
        if (this.bucketIndex == 0 && position == -1 && this.enableCamera) {
            onTakePhotoGridClicked();
        } else if (this.enablePreview) {
            onGridClickedAndPreviewEnabled(position);
        } else if (this.maxSelect == 1) {
            onGridClickedAndOnlySelected1(position);
        }
    }

    private void onGridClickedAndOnlySelected1(int position) {
        if (this.photoList == null || position >= this.photoList.size()) {
            PhotoLogger.w(TAG, "OnGridClick,but index invalid!");
            return;
        }
        PhotoItem photoInfo = this.photoList.get(position);
        photoInfo.setSelected(true);
        this.photoContext.selectedList.add(photoInfo);
        onSelected();
    }

    private void onGridClickedAndPreviewEnabled(int position) {
        if (this.photoList == null || position >= this.photoList.size()) {
            PhotoLogger.w(TAG, "OnGridClick,but index invalid!");
            return;
        }
        this.photoContext.photoList = this.photoList;
        preview(position, false);
    }

    private void onTakePhotoGridClicked() {
        if (this.photoContext == null || this.photoContext.selectedList == null || this.photoContext.selectedList.size() < this.maxSelect) {
            takePhotoWithPermissionCheck();
        } else {
            toast(this.maxSelectMsg, 0);
        }
    }

    private void takePhotoWithPermissionCheck() {
        if (checkSelfPermission()) {
            takePhoto();
            return;
        }
        ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA");
        requestCameraPerm();
    }

    private boolean checkSelfPermission() {
        boolean hasPermission;
        if (VERSION.SDK_INT < 23) {
            hasPermission = true;
        } else {
            hasPermission = false;
        }
        try {
            if (PermissionChecker.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            PhotoLogger.debug(TAG, "checkSelfPermission exception:" + e.getMessage());
            return hasPermission;
        }
    }

    private void requestCameraPerm() {
        this.isShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA");
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 1001);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 1001) {
            return;
        }
        if (grantResults.length <= 0 || grantResults[0] != 0) {
            PhotoLogger.debug(TAG, "user denie the PERMISSION_CAMERA");
            boolean isShowRationalNow = ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA");
            if (!this.isShowRationale && !isShowRationalNow) {
                toast(getString(R.string.permisson_rationale), 1);
                return;
            }
            return;
        }
        takePhoto();
    }

    public void onGridChecked(PhotoGrid grid, int position) {
        int i = 0;
        if (!CommonUtils.isIndexValidInList(this.photoList, position)) {
            StringBuilder append = new StringBuilder("onGridChecked:invalid index = ").append(position).append(",list size = ");
            if (this.photoList != null) {
                i = this.photoList.size();
            }
            PhotoLogger.warn((String) TAG, append.append(i).toString());
            return;
        }
        PhotoItem photoInfo = this.photoList.get(position);
        boolean selected = photoInfo.getSelected();
        boolean checked = grid.isChecked();
        if (checked && selected) {
            return;
        }
        if (selected || checked) {
            int selectedCount = this.photoContext.selectedList.size();
            if (!checked || selected) {
                if (!checked && selected) {
                    photoInfo.setSelected(false);
                    this.photoContext.selectedList.remove(photoInfo);
                }
            } else if (selectedCount >= this.maxSelect) {
                toast(this.maxSelectMsg, 0);
                grid.setChecked(false);
                return;
            } else if (isGifAndCantSelect(this, enableGifDynamicPreview, photoInfo, this.maxGifSizeCanSend, this.maxGifPixelCanSend)) {
                grid.setChecked(false);
                return;
            } else {
                photoInfo.setSelected(true);
                this.photoContext.selectedList.add(photoInfo);
            }
            updateContent();
        }
    }

    public static boolean isGifAndCantSelect(Context context, boolean enableGifDynamicBrowse, PhotoItem pi, int maxGifSizeCanSend2, int maxGifSideLengthCanSend) {
        if (!enableGifDynamicBrowse || !pi.isGif()) {
            return false;
        }
        String alertStr = "";
        if (pi.getPhotoSize() > ((long) maxGifSizeCanSend2)) {
            alertStr = context.getString(R.string.gif_size_too_large);
        } else if (pi.getPhotoWidth() * pi.getPhotoHeight() > maxGifSideLengthCanSend) {
            alertStr = context.getString(R.string.gif_resolution_too_large);
        }
        if (TextUtils.isEmpty(alertStr)) {
            return false;
        }
        CommonUtils.alipaySingleAlert(context, alertStr, context.getString(R.string.i_know));
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.photoResolver != null) {
            this.photoResolver.setBucketListener(null);
        }
        this.bottomMenuListener = null;
        this.photoResolver = null;
        this.hideTimeRunnable = null;
        if (this.photoContext != null && !this.afterSaveInstanceState) {
            PhotoContext.contextMap.clear();
        }
        this.photoContext = null;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        handleUserTouch(event);
        return super.dispatchTouchEvent(event);
    }

    private void handleUserTouch(MotionEvent event) {
        if (this.lvBuckets.getVisibility() != 0) {
            int action = event.getAction();
            if (action == 0) {
                this.tvHint.removeCallbacks(this.hideTimeRunnable);
            } else if (action == 1) {
                this.tvHint.postDelayed(this.hideTimeRunnable, 1000);
            }
        }
    }

    @SuppressLint({"SimpleDateFormat"})
    private void updateTime() {
        String timeText;
        if (this.photoContext.photoList != null) {
            try {
                long modifiedTime = this.photoContext.photoList.get(this.gvPhoto.getFirstVisiblePosition()).getModifiedTime();
                if (modifiedTime <= 0) {
                    PhotoLogger.debug(TAG, "photo modified time not set!");
                    return;
                }
                if (modifiedTime >= PhotoUtil.getThisMonth()) {
                    timeText = getString(R.string.this_month);
                } else {
                    timeText = new SimpleDateFormat(getString(R.string.time_pattern)).format(new Date(modifiedTime));
                }
                this.tvHint.setVisibility(0);
                this.tvHint.setText(timeText);
            } catch (Exception e) {
                PhotoLogger.warn((String) TAG, "updateTime called when gvPhoto`s Adapter`s related data is not same as photoContext.photoList. Exception :" + e.getMessage());
            }
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public void onScroll(AbsListView view, int firstVisibleItem2, int visibleItemCount, int totalItemCount) {
        if (this.firstVisibleItem != firstVisibleItem2) {
            this.firstVisibleItem = firstVisibleItem2;
            updateTime();
        }
    }

    public void onOverScrolled(int scrollX, int scrollY) {
        if (this.tvHint.getVisibility() != 0) {
            updateTime();
        }
    }

    /* access modifiers changed from: private */
    public void onScanFinisUpdateBucket() {
        if (this.bucketAdapter != null) {
            PhotoLogger.debug("PhotoDisplayLink", "Update bucket.");
            doUpdatePhotoData("onScanFinish");
            Intent in = new Intent();
            in.setAction(ACTION_PHOTO_ADAPTER_CHANGE);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);
        }
    }

    /* access modifiers changed from: private */
    public void doUpdatePhotoData(String caller) {
        this.bucketAdapter.setData(this.photoResolver.getBucketList());
        this.bucketAdapter.notifyDataSetChanged();
        onBucketSelected(this.allPhotoBucketName, caller);
        checkBuketEmpty();
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3478";
    }
}
