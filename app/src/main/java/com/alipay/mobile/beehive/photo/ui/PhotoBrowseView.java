package com.alipay.mobile.beehive.photo.ui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.wallet.spmtracker.SpmTracker;
import com.alipay.mobile.antui.basic.AUToast;
import com.alipay.mobile.antui.dialog.AUListDialog;
import com.alipay.mobile.antui.dialog.AUListDialog.OnItemClickListener;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.antui.dialog.PopMenuItem;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.beehive.imageedit.constant.Constants;
import com.alipay.mobile.beehive.imageedit.modle.ImageInfo;
import com.alipay.mobile.beehive.imageedit.service.ImageEditListener;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.alipay.mobile.beehive.imageedit.service.InImageEditListener;
import com.alipay.mobile.beehive.photo.data.LoadInfo;
import com.alipay.mobile.beehive.photo.data.PhotoContext;
import com.alipay.mobile.beehive.photo.data.PhotoGroup;
import com.alipay.mobile.beehive.photo.data.PhotoItem;
import com.alipay.mobile.beehive.photo.ui.PhotoPagerListener.V3;
import com.alipay.mobile.beehive.photo.ui.PhotoPagerListener.V4;
import com.alipay.mobile.beehive.photo.util.AnimationUtil;
import com.alipay.mobile.beehive.photo.util.BackgroundTaskUtil;
import com.alipay.mobile.beehive.photo.util.CommonUtils;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.util.UserBehavior;
import com.alipay.mobile.beehive.photo.view.IndicatorView;
import com.alipay.mobile.beehive.photo.view.NumberProgressBar;
import com.alipay.mobile.beehive.photo.view.PhotoPreview;
import com.alipay.mobile.beehive.photo.view.PhotoView;
import com.alipay.mobile.beehive.photo.view.PullBackLayout;
import com.alipay.mobile.beehive.photo.view.PullBackLayout.Callback;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper.LoadListener;
import com.alipay.mobile.beehive.service.PhotoBrowseListener.V2;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoMenu;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.impl.PhotoServiceImpl;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.beehive.video.views.EnhancedVideoPlayView;
import com.alipay.mobile.beehive.video.views.StreamPlayCon;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.sdk.util.e;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhotoBrowseView extends FrameLayout implements OnPageChangeListener, OnClickListener, OnGlobalLayoutListener, OnCheckedChangeListener {
    private static final String DISABLE_GIF_DYNAMIC_BROWSE = "disable_gif_dynamic_browse";
    private static final String DISABLE_PULL_DOWN_FINISH = "disable_photo_preview_pull_down_finish";
    private static final String DISABLE_VIDEO_STREAM_PLAY = "disable_video_stream_play";
    private static final int GRID_AUTO_HIDE = 3000;
    private static final String HARDWARE_ACC_SWITCH = "photoview_disable_hardware_acc";
    private static final String LANDSCAPE_BROWSE_PHOTO_SWITCH = "photo_browse_view_disable_landscape_support";
    public static final int NO_CUSTOM_FINISH_BTN_BG_COLOR = -1;
    public static final int PREFER_WIDTH_THUMB_DP = 125;
    public static int PREFER_WIDTH_THUMB_PX = 0;
    private static final String TAG = "PhotoBrowseView";
    public static boolean gDisablePhotoViewHardwareAcc;
    public static boolean hasSameGifInContext = false;
    public static int maxGifPixelCanSend;
    public static int maxGifSizeCanSend;
    private static Rect sDefaultPullBackZoomRect;
    public static boolean sDisableVideoStreamPlay;
    public static boolean selectPhoto;
    /* access modifiers changed from: private */
    public Activity activity;
    private boolean afterSaveInstanceState;
    private boolean autoHideGrid;
    private float beautyImageLevel;
    private RelativeLayout bottomBar;
    private View bottomText;
    private Button btDelete;
    private Button btFinish;
    /* access modifiers changed from: private */
    public PhotoPreview cachePreview;
    private long cancelShowLastTime;
    private CheckBox cbOrigin;
    private CheckBox cbSelectTop;
    private CheckBox cbSelected;
    /* access modifiers changed from: private */
    public boolean clickExit;
    private int compressImageQuality;
    private boolean configDisableGifDynamicPreview;
    private boolean configDisableLandscapeSupport;
    private String contextIndex;
    private Drawable defaultPhoto;
    /* access modifiers changed from: private */
    public IndicatorView dotIndicator;
    private boolean enableDelete;
    private boolean enableEdit;
    private boolean enableGifBrowse = true;
    private boolean enableGridGroup;
    private boolean enableScale;
    private boolean enableSelectOrigin;
    private int finishBtnBgColor;
    private boolean fitSpace;
    private int focusedIndexRecord = -1;
    private boolean forceFetchOriginalPhoto;
    private boolean forceFullScreenPreview;
    private boolean fullscreenPreview;
    private Runnable hideGridRunnable = new Runnable() {
        public final void run() {
            PhotoBrowseView.this.ivGridGroup.setVisibility(4);
            PhotoBrowseView.this.ivDownloadEntry.setVisibility(4);
        }
    };
    private ImageButton ibBack;
    private ImageButton ibCancelShow;
    private boolean isActivityResumed = false;
    private boolean isAutoPlayPosition;
    private boolean isCalledBeforeResume = false;
    private boolean isCancelDownloadWhenQuitPreview = true;
    private boolean isFromPause = false;
    private boolean isShowPhotoDownload;
    private boolean isShowPhotoExactlyProgress = false;
    private boolean isSupportLandscapeBrowse;
    /* access modifiers changed from: private */
    public boolean isSupportVideoEdit;
    /* access modifiers changed from: private */
    public Map<String, View> itemMap = new HashMap();
    /* access modifiers changed from: private */
    public View ivDownloadEntry;
    /* access modifiers changed from: private */
    public View ivGridGroup;
    private long lastClickTimestamp = 0;
    private int lastPagerCount = 0;
    private String leftText;
    private View llOrigin;
    private View llSelect;
    LoadListener loadListener = new LoadListener() {
        public final void onLoadProgress(LoadInfo info, int current, int total) {
            PhotoBrowseView.this.onLoadProgress_(info, current, total);
        }

        public final void onLoadComplete(LoadInfo info) {
            PhotoBrowseView.this.onLoadComplete_(info);
        }

        public final void onLoadCanceled(LoadInfo info) {
            PhotoBrowseView.this.onLoadCanceled_(info);
        }

        public final void onLoadFailed(LoadInfo info, APImageDownloadRsp rsp) {
            PhotoBrowseView.this.onLoadFailedV2(info, rsp);
        }
    };
    /* access modifiers changed from: private */
    public Map<PhotoItem, LoadInfo> loadMap;
    private Handler mAutoHideHandler = new Handler(Looper.getMainLooper());
    private String mBusinessId;
    private boolean mEnableINEditWhenPreview;
    private boolean mEnablePullDownFinish;
    private ImageEditListener mImageEditListener = new ImageEditListener() {
        public final void onImageEdit(boolean isCanceled, String oriPath, ImageInfo info) {
            if (!isCanceled) {
                PhotoItem afterEdit = new PhotoItem(info.path);
                afterEdit.setPhotoWidth(info.width);
                afterEdit.setPhotoHeight(info.height);
                afterEdit.setPhotoOrientation(info.rotation);
                afterEdit.setSelected(true);
                if (PhotoBrowseView.this.photoContext != null && PhotoBrowseView.this.photoContext.selectedList != null) {
                    PhotoBrowseView.this.photoContext.selectedList.clear();
                    PhotoBrowseView.this.photoContext.selectedList.add(afterEdit);
                    PhotoBrowseView.this.onSelected();
                }
            }
        }
    };
    private boolean mIsEnableInPaster;
    private boolean mIsPullDownFinishDisabledByConfig = false;
    /* access modifiers changed from: private */
    public String mScanCodeDescPattern;
    private String mSourceAppId;
    private int maxSelect;
    private String maxSelectMsg;
    private boolean onlyPreviewSelect;
    private RelativeLayout optionBar;
    private ImageButton optionBarBack;
    private OnLongClickListener originalVideoLongClickListener = new OnLongClickListener() {
        public final boolean onLongClick(View view) {
            if (PhotoBrowseView.this.photoPagerListener != null) {
                PhotoItem photoItem = (PhotoItem) PhotoBrowseView.this.photoList.get(PhotoBrowseView.this.pageIndex);
                if (PhotoBrowseView.this.photoGroups != null && photoItem.getPhotoGroupIndex() < PhotoBrowseView.this.photoGroups.size()) {
                    PhotoGroup photoGroup = (PhotoGroup) PhotoBrowseView.this.photoGroups.get(photoItem.getPhotoGroupIndex());
                    PhotoBrowseView.this.photoPagerListener.onPageLongClicked(photoGroup.getId(), PhotoBrowseView.this.pageIndex - photoGroup.getOffset());
                }
            }
            if (PhotoBrowseView.this.photoMenuList != null && !PhotoBrowseView.this.photoMenuList.isEmpty()) {
                List showList = new ArrayList();
                for (int index = 0; index < PhotoBrowseView.this.photoMenuList.size(); index++) {
                    PhotoMenu photoMenu = (PhotoMenu) PhotoBrowseView.this.photoMenuList.get(index);
                    if (photoMenu.enabled && photoMenu.isVideoOriSupport() && !PhotoBrowseView.this.ignoreVideoSaveMenu(photoMenu)) {
                        showList.add(photoMenu);
                    }
                }
                if (!showList.isEmpty() && PhotoBrowseView.this.activity != null && !PhotoBrowseView.this.activity.isFinishing()) {
                    PhotoBrowseView.this.showVideoMenuDialog(showList);
                }
            }
            return true;
        }
    };
    /* access modifiers changed from: private */
    public int pageIndex;
    /* access modifiers changed from: private */
    public b pagerAdapter = new b();
    private NumberProgressBar pbShowOrigin;
    private OnClickListener photoClickListener = new OnClickListener() {
        public final void onClick(View view) {
            if (PhotoBrowseView.this.clickExit) {
                PhotoBrowseView.this.toggleFinish();
                return;
            }
            if (PhotoBrowseView.this.photoPagerListener != null) {
                PhotoBrowseView.this.photoPagerListener.onPageClicked();
            }
            PhotoBrowseView.this.toggleFullscreen();
        }
    };
    /* access modifiers changed from: private */
    public PhotoContext photoContext;
    /* access modifiers changed from: private */
    public List<PhotoGroup> photoGroups;
    /* access modifiers changed from: private */
    public List<PhotoItem> photoList;
    private OnLongClickListener photoLongClickListener = new OnLongClickListener() {
        public final boolean onLongClick(View view) {
            if (PhotoBrowseView.this.photoPagerListener != null) {
                PhotoItem photoItem = (PhotoItem) PhotoBrowseView.this.photoList.get(PhotoBrowseView.this.pageIndex);
                if (PhotoBrowseView.this.photoGroups != null && photoItem.getPhotoGroupIndex() < PhotoBrowseView.this.photoGroups.size()) {
                    PhotoGroup photoGroup = (PhotoGroup) PhotoBrowseView.this.photoGroups.get(photoItem.getPhotoGroupIndex());
                    PhotoBrowseView.this.photoPagerListener.onPageLongClicked(photoGroup.getId(), PhotoBrowseView.this.pageIndex - photoGroup.getOffset());
                }
            }
            if (PhotoBrowseView.this.photoMenuList != null && !PhotoBrowseView.this.photoMenuList.isEmpty()) {
                List showList = new ArrayList();
                PhotoItem photoInfo = (PhotoItem) PhotoBrowseView.this.photoList.get(PhotoBrowseView.this.pageIndex);
                String photoPath = photoInfo.getPhotoPath();
                boolean needCheckScanQR = false;
                for (int index = 0; index < PhotoBrowseView.this.photoMenuList.size(); index++) {
                    PhotoMenu photoMenu = (PhotoMenu) PhotoBrowseView.this.photoMenuList.get(index);
                    if (photoMenu.isPhotoSupport() && photoMenu.enabled) {
                        if (!PhotoMenu.TAG_SCAN_QR.equals(photoMenu.tag) || !photoMenu.enableImpl) {
                            if (PhotoMenu.TAG_SAVE.equals(photoMenu.tag) && TextUtils.isEmpty(photoMenu.title)) {
                                photoMenu.title = PhotoBrowseView.this.getContext().getString(photoInfo.isGif() ? R.string.str_save_gif : R.string.default_save_menu_title);
                            }
                            if (PhotoBrowseView.this.isIgnoreGifSaveMenu(photoMenu)) {
                                PhotoLogger.debug(PhotoBrowseView.TAG, "Gif not ready, ignore save menu.");
                            } else if (!photoInfo.isGif() || !TextUtils.equals(PhotoMenu.TAG_COLLECT, photoMenu.tag)) {
                                showList.add(photoMenu);
                            } else {
                                PhotoLogger.debug(PhotoBrowseView.TAG, "Giff not support collect,ignore it.");
                            }
                        } else {
                            needCheckScanQR = true;
                        }
                    }
                }
                if (!showList.isEmpty() && PhotoBrowseView.this.activity != null && !PhotoBrowseView.this.activity.isFinishing()) {
                    PhotoBrowseView.this.showPhotoMenuDialog(photoPath, showList, needCheckScanQR, photoInfo.isGif());
                }
            }
            return true;
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<PhotoMenu> photoMenuList;
    /* access modifiers changed from: private */
    public PhotoPagerListener photoPagerListener;
    private int photoViewDisplayType;
    private int previewImgHeight;
    private int previewImgWidth;
    private int previewPosition;
    private boolean purePhoto;
    private BroadcastReceiver receiver;
    private String rightText;
    /* access modifiers changed from: private */
    public RelativeLayout rlShowOrigin;
    private String saveFolder;
    /* access modifiers changed from: private */
    public c scanTask = null;
    private boolean selectMode = false;
    private List<PhotoItem> selectedList;
    private boolean showDotIndicator;
    private boolean showOrigin;
    private long showOriginLastClickTime;
    private boolean showTextIndicator;
    private String textFinish;
    private TextView textIndicator;
    private String textOriginPhoto;
    private RelativeLayout topBar;
    private TextView tvEdit;
    private TextView tvEditWhenPreviewPhoto;
    private TextView tvLeft;
    private TextView tvNumberIndex;
    private TextView tvOrigin;
    private TextView tvRight;
    private TextView tvSelected;
    private TextView tvTextDivider;
    private PullBackLayout vPullBackContainer;
    private OnLongClickListener videoLongClickListener = new OnLongClickListener() {
        public final boolean onLongClick(View view) {
            if (PhotoBrowseView.this.photoPagerListener != null) {
                PhotoItem photoItem = (PhotoItem) PhotoBrowseView.this.photoList.get(PhotoBrowseView.this.pageIndex);
                if (PhotoBrowseView.this.photoGroups != null && photoItem.getPhotoGroupIndex() < PhotoBrowseView.this.photoGroups.size()) {
                    PhotoGroup photoGroup = (PhotoGroup) PhotoBrowseView.this.photoGroups.get(photoItem.getPhotoGroupIndex());
                    PhotoBrowseView.this.photoPagerListener.onPageLongClicked(photoGroup.getId(), PhotoBrowseView.this.pageIndex - photoGroup.getOffset());
                }
            }
            if (PhotoBrowseView.this.photoMenuList != null && !PhotoBrowseView.this.photoMenuList.isEmpty()) {
                List showList = new ArrayList();
                for (int index = 0; index < PhotoBrowseView.this.photoMenuList.size(); index++) {
                    PhotoMenu photoMenu = (PhotoMenu) PhotoBrowseView.this.photoMenuList.get(index);
                    if (photoMenu.enabled && photoMenu.isVideoSupport() && !PhotoBrowseView.this.ignoreVideoSaveMenu(photoMenu)) {
                        showList.add(photoMenu);
                    }
                }
                if (!showList.isEmpty() && PhotoBrowseView.this.activity != null && !PhotoBrowseView.this.activity.isFinishing()) {
                    PhotoBrowseView.this.showVideoMenuDialog(showList);
                }
            }
            return true;
        }
    };
    private int viewHeight;
    private int viewWidth;
    /* access modifiers changed from: private */
    public ViewPager vpPhoto;

    class a implements OnItemClickListener {
        private List<PhotoMenu> b;

        public a(List<PhotoMenu> menus) {
            this.b = menus;
        }

        public final void onItemClick(int which) {
            PhotoBrowseView.this.scanTask = null;
            PhotoMenu photoMenu = this.b.get(which);
            if (!photoMenu.enableImpl || !"delete".equals(photoMenu.tag)) {
                PhotoBrowseView.this.processMenu(photoMenu);
            } else {
                PhotoBrowseView.this.showDeleteDialog(photoMenu);
            }
            PhotoBrowseView.this.onMenuClicked(photoMenu);
        }
    }

    class b extends PagerAdapter {
        private List<PhotoItem> b = new ArrayList();

        b() {
        }

        public final /* synthetic */ Object instantiateItem(ViewGroup viewGroup, int i) {
            return a(i);
        }

        public final void a(List<PhotoItem> photoItems) {
            if (photoItems != null && !photoItems.isEmpty()) {
                if (PhotoBrowseView.this.isSupportVideoEdit) {
                    List onlyPhoto = new ArrayList();
                    for (int i = 0; i < photoItems.size(); i++) {
                        PhotoItem pi = photoItems.get(i);
                        if (!pi.isVideo()) {
                            onlyPhoto.add(pi);
                        }
                    }
                    this.b.addAll(onlyPhoto);
                    return;
                }
                this.b.addAll(photoItems);
            }
        }

        public final void a() {
            this.b.clear();
        }

        public final int getCount() {
            if (this.b == null) {
                return 0;
            }
            return this.b.size();
        }

        private View a(int position) {
            View view = (View) PhotoBrowseView.this.itemMap.get(String.valueOf(position));
            if (view != null) {
                return view;
            }
            View view2 = PhotoBrowseView.this.createView(position);
            PhotoBrowseView.this.itemMap.put(String.valueOf(position), view2);
            return view2;
        }

        public final void destroyItem(ViewGroup container, int position, Object object) {
            if (object instanceof PhotoPreview) {
                PhotoPreview photoPreview = (PhotoPreview) object;
                photoPreview.destroy();
                container.removeView(photoPreview);
                Iterator it = PhotoBrowseView.this.loadMap.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    LoadInfo loadInfo = (LoadInfo) it.next();
                    if (loadInfo.getPhotoPreview() == photoPreview) {
                        loadInfo.setPhotoPreview(null);
                        break;
                    }
                }
                PhotoBrowseView.this.itemMap.remove(String.valueOf(position));
                PhotoBrowseView.this.cachePreview = photoPreview;
            }
        }

        public final boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public final List<PhotoItem> b() {
            return this.b;
        }
    }

    private class c implements Runnable {
        public String a;
        public String b;
        private AUListDialog d;
        private List<PhotoMenu> e;
        private String f;

        public c(AUListDialog dialog, List<PhotoMenu> menus) {
            this.d = dialog;
            this.e = menus;
        }

        public final void a(String qrCode) {
            this.a = qrCode;
        }

        /* access modifiers changed from: 0000 */
        public final void b(String type) {
            this.f = type;
        }

        public final void run() {
            this.e.clear();
            ArrayList menusItem = new ArrayList();
            for (int index = 0; index < PhotoBrowseView.this.photoMenuList.size(); index++) {
                PhotoMenu photoMenu = (PhotoMenu) PhotoBrowseView.this.photoMenuList.get(index);
                if (photoMenu.enabled && photoMenu.isPhotoSupport()) {
                    if (PhotoMenu.TAG_SCAN_QR.equals(photoMenu.tag) && photoMenu.enableImpl) {
                        photoMenu.extra = this.a;
                        photoMenu.extra2 = this.f;
                        photoMenu.title = TextUtils.isEmpty(this.b) ? photoMenu.title : this.b;
                    }
                    menusItem.add(new PopMenuItem(photoMenu.title));
                    this.e.add(photoMenu);
                }
            }
            if (this.d != null && this.d.isShowing()) {
                this.d.updateData(menusItem);
            }
        }
    }

    private void getSpecialConfig() {
        try {
            ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(ConfigService.class.getName());
            if (configService != null) {
                if (TextUtils.equals(configService.getConfig(HARDWARE_ACC_SWITCH), "true")) {
                    PhotoLogger.info(TAG, "DISABLE hardware acc");
                    gDisablePhotoViewHardwareAcc = true;
                } else {
                    PhotoLogger.info(TAG, "ENABLE hardware acc");
                    gDisablePhotoViewHardwareAcc = false;
                }
                if (TextUtils.equals(configService.getConfig(LANDSCAPE_BROWSE_PHOTO_SWITCH), "true")) {
                    PhotoLogger.info(TAG, "CloudConfig:Disable landscape browse");
                    this.configDisableLandscapeSupport = true;
                } else {
                    PhotoLogger.info(TAG, "CloudConfig:Enable landscape browse");
                    this.configDisableLandscapeSupport = false;
                }
                if (TextUtils.equals(configService.getConfig(DISABLE_GIF_DYNAMIC_BROWSE), "true")) {
                    PhotoLogger.info(TAG, "CloudConfig:Disable gif dynamic browse");
                    this.configDisableGifDynamicPreview = true;
                } else {
                    PhotoLogger.info(TAG, "CloudConfig:Enable gif dynamic browse");
                    this.configDisableGifDynamicPreview = false;
                }
                if (TextUtils.equals(configService.getConfig(DISABLE_VIDEO_STREAM_PLAY), "true")) {
                    PhotoLogger.info(TAG, "CloudConfig:Disable video stream play");
                    sDisableVideoStreamPlay = true;
                } else {
                    PhotoLogger.info(TAG, "CloudConfig:Enable video stream play");
                    sDisableVideoStreamPlay = false;
                }
                if (TextUtils.equals(configService.getConfig(DISABLE_PULL_DOWN_FINISH), "true")) {
                    PhotoLogger.info(TAG, "DISABLE pull down finish");
                    this.mIsPullDownFinishDisabledByConfig = true;
                    return;
                }
                PhotoLogger.info(TAG, "ENABLE pull down finish");
                this.mIsPullDownFinishDisabledByConfig = false;
            }
        } catch (Exception e) {
            PhotoLogger.warn((String) TAG, "readSpecialConfig exception," + e.getMessage());
        }
    }

    public PhotoBrowseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public PhotoBrowseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PhotoBrowseView(Context context) {
        super(context);
        init(context);
    }

    public void setPhotoPagerListener(PhotoPagerListener listener) {
        this.photoPagerListener = listener;
    }

    private void init(Context context) {
        hasSameGifInContext = false;
        getSpecialConfig();
        LayoutInflater.from(context).inflate(R.layout.photo_browse, this, true);
        this.pageIndex = 0;
        this.defaultPhoto = getResources().getDrawable(R.drawable.default_photo);
        this.loadMap = Collections.synchronizedMap(new HashMap());
        this.ivDownloadEntry = findViewById(R.id.iv_download_entry);
        this.ivDownloadEntry.setOnClickListener(this);
        this.ivGridGroup = findViewById(R.id.iv_grid);
        this.ivGridGroup.setOnClickListener(this);
        this.tvLeft = (TextView) findViewById(R.id.tv_left);
        this.tvLeft.setMovementMethod(new ScrollingMovementMethod());
        this.llSelect = findViewById(R.id.ll_select);
        this.tvRight = (TextView) findViewById(R.id.tv_right);
        this.tvRight.setMovementMethod(new ScrollingMovementMethod());
        this.tvTextDivider = (TextView) findViewById(R.id.tv_text_divider);
        this.bottomText = (LinearLayout) findViewById(R.id.ll_bottom_text);
        this.optionBar = (RelativeLayout) findViewById(R.id.rl_option_bar);
        this.optionBar.setOnClickListener(this);
        this.textIndicator = (TextView) findViewById(R.id.tv_text_indicator);
        this.topBar = (RelativeLayout) findViewById(R.id.rl_top_bar);
        this.topBar.setOnClickListener(this);
        this.bottomBar = (RelativeLayout) findViewById(R.id.rl_bottom_bar);
        this.bottomBar.setOnClickListener(this);
        this.dotIndicator = (IndicatorView) findViewById(R.id.iv_indicator);
        this.rlShowOrigin = (RelativeLayout) findViewById(R.id.rl_show_origin);
        this.rlShowOrigin.setOnClickListener(this);
        this.pbShowOrigin = (NumberProgressBar) findViewById(R.id.pb_show_origin);
        this.pbShowOrigin.setText(getContext().getString(R.string.show_origin)).setProgress(0);
        this.ibCancelShow = (ImageButton) findViewById(R.id.ib_cancel_show);
        this.ibCancelShow.setOnClickListener(this);
        this.ibBack = (ImageButton) findViewById(R.id.bt_back);
        CommonUtils.setTitleBarBackDrawable(this.ibBack);
        this.optionBarBack = (ImageButton) findViewById(R.id.option_bt_back);
        CommonUtils.setTitleBarBackDrawable(this.optionBarBack);
        this.tvNumberIndex = (TextView) findViewById(R.id.tv_index);
        this.vpPhoto = (ViewPager) findViewById(R.id.vp_base_app);
        this.cbSelected = (CheckBox) findViewById(R.id.cb_select);
        this.tvSelected = (TextView) findViewById(R.id.tv_select);
        ViewCompat.setImportantForAccessibility(this.tvSelected, 2);
        this.cbSelected.setOnCheckedChangeListener(this);
        this.tvEdit = (TextView) findViewById(R.id.tv_edit);
        this.tvEdit.setOnClickListener(this);
        this.cbSelectTop = (CheckBox) findViewById(R.id.cb_select_top);
        this.cbSelectTop.setOnCheckedChangeListener(this);
        this.btFinish = (Button) findViewById(R.id.bt_finish);
        this.btFinish.setOnClickListener(this);
        this.cbOrigin = (CheckBox) findViewById(R.id.cb_origin);
        this.cbOrigin.setOnCheckedChangeListener(this);
        this.tvOrigin = (TextView) findViewById(R.id.tv_origin);
        ViewCompat.setImportantForAccessibility(this.tvOrigin, 2);
        this.ibBack.setOnClickListener(this);
        this.optionBarBack.setOnClickListener(this);
        this.vpPhoto.setOnPageChangeListener(this);
        this.btDelete = (Button) findViewById(R.id.bt_delete);
        this.btDelete.setText(getResources().getString(R.string.delete));
        this.btDelete.setOnClickListener(this);
        this.topBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        this.tvEditWhenPreviewPhoto = (TextView) findViewById(R.id.tv_edit_photo);
        this.tvEditWhenPreviewPhoto.setOnClickListener(this);
    }

    private void initPullDownFinish(boolean isEnablePullDownFinish) {
        PhotoLogger.d(TAG, "initPullDownFinish:###");
        this.vPullBackContainer = (PullBackLayout) findViewById(R.id.v_pull_back);
        this.vPullBackContainer.setEnablePullGuesture(isEnablePullDownFinish);
        this.vPullBackContainer.setCallback(new Callback() {
            public final void onPullStart() {
                ((PhotoPreview) PhotoBrowseView.this.vpPhoto.findViewWithTag(Integer.valueOf(PhotoBrowseView.this.pageIndex))).onLoseFocus();
            }

            public final void onPull(float progress) {
                PhotoBrowseView.this.vpPhoto.setScaleY(1.0f - progress);
                PhotoBrowseView.this.vpPhoto.setScaleX(1.0f - progress);
                ((View) PhotoBrowseView.this.getParent()).setBackgroundColor(ColorUtils.setAlphaComponent(-16777216, (int) (255.0f * (1.0f - progress))));
            }

            public final void onPullCancel() {
                ((PhotoPreview) PhotoBrowseView.this.vpPhoto.findViewWithTag(Integer.valueOf(PhotoBrowseView.this.pageIndex))).onFocus(false);
            }

            public final void onPullComplete() {
                PhotoBrowseView.this.onPullDownGestureComplete();
            }

            public final boolean tryCaptureView(View child, int pointerId) {
                return PhotoBrowseView.this.pendingToCaptureView();
            }

            public final boolean tryCaptureViewWhenPullDown() {
                PhotoPreview photoPreview = (PhotoPreview) PhotoBrowseView.this.vpPhoto.findViewWithTag(Integer.valueOf(PhotoBrowseView.this.pageIndex));
                if (!photoPreview.isPhotoType() || !photoPreview.getPhotoView().isPhotoUpTop()) {
                    return true;
                }
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean pendingToCaptureView() {
        PhotoPreview photoPreview = (PhotoPreview) this.vpPhoto.findViewWithTag(Integer.valueOf(this.pageIndex));
        if (!photoPreview.isPhotoType() || !photoPreview.getPhotoView().isPhotoScaled()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void onPullDownGestureComplete() {
        ViewPager viewPager = this.vpPhoto;
        Rect rect = getViewZoomRect(this.photoList.get(this.pageIndex));
        float targetScaleX = ((float) (rect.right - rect.left)) / ((float) viewPager.getWidth());
        float targetScaleY = ((float) (rect.bottom - rect.top)) / ((float) viewPager.getHeight());
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(viewPager, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("translationX", new float[]{0.0f, ((float) (rect.left - viewPager.getLeft())) - ((((float) viewPager.getWidth()) * (1.0f - targetScaleX)) / 2.0f)}), PropertyValuesHolder.ofFloat("translationY", new float[]{0.0f, ((float) (rect.top - viewPager.getTop())) - ((((float) viewPager.getHeight()) * (1.0f - targetScaleY)) / 2.0f)}), PropertyValuesHolder.ofFloat("scaleX", new float[]{viewPager.getScaleX(), targetScaleX}), PropertyValuesHolder.ofFloat("scaleY", new float[]{viewPager.getScaleY(), targetScaleY}), PropertyValuesHolder.ofFloat("alpha", new float[]{1.0f, 0.01f})});
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(500);
        AnonymousClass10 r0 = new AnimatorListener() {
            public final void onAnimationStart(Animator animation) {
                ((Activity) PhotoBrowseView.this.getContext()).getWindow().setFlags(2048, 2048);
            }

            public final void onAnimationEnd(Animator animation) {
                PhotoBrowseView.this.toggleFinish(false);
            }

            public final void onAnimationCancel(Animator animation) {
            }

            public final void onAnimationRepeat(Animator animation) {
            }
        };
        animator.addListener(r0);
        animator.start();
    }

    @NonNull
    private Rect getViewZoomRect(PhotoItem photoInfo) {
        Rect rect = photoInfo.viewBoundsOnScreen;
        if (rect != null) {
            return rect;
        }
        if (sDefaultPullBackZoomRect == null) {
            int l = (getWidth() - 100) / 2;
            int t = getHeight();
            sDefaultPullBackZoomRect = new Rect(l, t, l + 100, t + 100);
        }
        return sDefaultPullBackZoomRect;
    }

    public void setActivity(Activity activity2) {
        if (activity2 != null) {
            if (PREFER_WIDTH_THUMB_PX <= 0) {
                PREFER_WIDTH_THUMB_PX = PhotoUtil.reorderSize(PhotoUtil.dp2px(activity2, 125)).getWidth();
            }
            initPhotoReceiver(activity2);
            this.afterSaveInstanceState = false;
        } else {
            if (this.isCancelDownloadWhenQuitPreview) {
                PhotoLogger.debug(TAG, "Cancel all downloading task.");
                for (LoadInfo loadInfo : this.loadMap.values()) {
                    ImageHelper.cancel(loadInfo.taskId);
                }
            } else {
                PhotoLogger.debug(TAG, "Keep download task in background!");
            }
            this.loadMap.clear();
            if (this.photoContext != null && !this.afterSaveInstanceState) {
                PhotoContext.contextMap.clear();
            }
            this.photoContext = null;
            unregisterReceiver(this.activity);
            clearAutoDismiss();
        }
        this.activity = activity2;
    }

    public void setBundle(Bundle bundle) {
        int i = 8;
        if (bundle != null) {
            this.mIsEnableInPaster = bundle.getBoolean(PhotoParam.KEY_ENABLE_IN_PASTER, true);
            this.isShowPhotoDownload = bundle.getBoolean(PhotoParam.KEY_ENABLE_SHOW_PHOTO_DOWNLOAD, false);
            maxGifPixelCanSend = PhotoParam.DEFAULT_MAX_GIF_PIXEL_CAN_SEND;
            maxGifSizeCanSend = 5242880;
            this.mEnablePullDownFinish = bundle.getBoolean(PhotoParam.ENABLE_PULL_DOWN_FINISH_WHEN_PREVIEW, false);
            if (this.mEnablePullDownFinish && this.mIsPullDownFinishDisabledByConfig) {
                PhotoLogger.d(TAG, "Force disable pull down finish.");
                this.mEnablePullDownFinish = false;
            }
            this.mEnableINEditWhenPreview = bundle.getBoolean(PhotoParam.ENABLE_EDIT_WHEN_PREVIEW_IMAGE, false);
            this.tvEditWhenPreviewPhoto.setVisibility(this.mEnableINEditWhenPreview ? 0 : 8);
            this.mSourceAppId = bundle.getString(PhotoServiceImpl.EXTRA_SOURCE_APP_ID);
            this.isSupportVideoEdit = bundle.getBoolean(PhotoParam.ENABLE_VIDEO_CUT, false);
            this.isSupportLandscapeBrowse = bundle.getBoolean(PhotoParam.ENABLE_PHOTO_LANDSCAPE_BROWSE, false);
            this.enableGifBrowse = bundle.getBoolean(PhotoParam.ENABLE_GIF_DYNAMIC_PREVIEW, true);
            maxGifSizeCanSend = bundle.getInt(PhotoParam.MAX_SIZE_GIF_SEND, 5242880);
            maxGifPixelCanSend = bundle.getInt(PhotoParam.MAX_GIF_PIXEL_CAN_SEND, PhotoParam.DEFAULT_MAX_GIF_PIXEL_CAN_SEND);
            CommonUtils.GifDebugger("GIF LIMIT ###: size = " + maxGifSizeCanSend + ",Pixcel = " + maxGifPixelCanSend);
            adaptLandscapeConfig();
            this.mBusinessId = bundle.getString("businessId");
            ImageHelper.updateBusinessId(this.mBusinessId, TAG);
            this.enableEdit = bundle.getBoolean(PhotoParam.ENABLE_EDIT_PHOTO, false);
            TextView textView = this.tvEdit;
            if (this.enableEdit) {
                i = 0;
            }
            textView.setVisibility(i);
            this.isCancelDownloadWhenQuitPreview = bundle.getBoolean(PhotoParam.CANCEL_DOWNLOAD_WHEN_QUIT_PREVIEW, true);
            this.beautyImageLevel = bundle.getFloat(PhotoParam.BEAUTY_IMAGE_LEVEL, -1.0f);
            this.compressImageQuality = bundle.getInt(PhotoParam.COMPRESS_IMAGE_QUALITY, -1);
            this.isShowPhotoExactlyProgress = bundle.getBoolean(PhotoParam.SHOW_PHOTO_LOAD_PROGRESS, true);
            this.photoViewDisplayType = bundle.getInt(PhotoParam.PHOTOVIEW_DISPLAY_TYPE, 2);
            this.photoMenuList = bundle.getParcelableArrayList(PhotoParam.LONG_CLICK_MENU);
            selectPhoto = bundle.getBoolean(PhotoParam.PHOTO_SELECT, false);
            this.enableDelete = bundle.getBoolean(PhotoParam.ENABLE_DELETE, false);
            this.textOriginPhoto = getResources().getString(R.string.origin_photo);
            this.maxSelect = bundle.getInt(PhotoParam.MAX_SELECT, 9);
            this.maxSelectMsg = bundle.getString(PhotoParam.MAX_SELECT_MSG);
            if (TextUtils.isEmpty(this.maxSelectMsg)) {
                this.maxSelectMsg = getResources().getString(R.string.max_message, new Object[]{Integer.valueOf(this.maxSelect)});
            }
            this.previewImgWidth = bundle.getInt(PhotoParam.PREVIEW_IMG_WIDTH);
            this.previewImgHeight = bundle.getInt(PhotoParam.PREVIEW_IMG_HEIGHT);
            this.showOrigin = bundle.getBoolean(PhotoParam.ENABLE_SHOW_ORIGIN, false);
            this.autoHideGrid = bundle.getBoolean(PhotoParam.AUTO_HIDE_GRID_GROUP, true);
            this.fitSpace = bundle.getBoolean(PhotoParam.FIT_SPACE, true);
            this.enableScale = bundle.getBoolean(PhotoParam.ENABLE_PINCH, true);
            this.saveFolder = bundle.getString(PhotoParam.SAVE_FOLDER);
            if (TextUtils.isEmpty(this.saveFolder)) {
                this.saveFolder = PhotoUtil.getDefaultPhotoFolder();
            }
            this.clickExit = bundle.getBoolean(PhotoParam.PREVIEW_CLICK_EXIT, false);
            this.enableGridGroup = bundle.getBoolean(PhotoParam.ENABLE_GRID_GROUP, false);
            this.enableSelectOrigin = bundle.getBoolean(PhotoParam.ENABLE_SELECT_ORIGIN, true);
            this.previewPosition = bundle.getInt(PhotoParam.PREVIEW_POSITION, 0);
            this.isAutoPlayPosition = bundle.getBoolean(PhotoParam.AUTO_PLAY_PREVIEW_POSITION, false);
            EnhancedVideoPlayView.globalConfig(bundle.getInt(PhotoParam.ORIGINAL_VIDEO_EXTRA_INFO_TYPE, 0), bundle.getBoolean(PhotoParam.SHOW_VIDEO_PLAY_FINISH_HINT, true), bundle.getBoolean(PhotoParam.DISABLE_AUTO_PLAY_IN_POOR_NETWORK_EVEN_FILE_EXIST, false), bundle.getInt(PhotoParam.VIDEO_SHOW_TYPE, 0), bundle.getBoolean(PhotoParam.AUTO_DOWNLOAD_IN_MOBILE_NETWORK, false), bundle.getBoolean(PhotoParam.ENABLE_SMALL_VIDEO_STREAM_PLAY, true), bundle.getBoolean(PhotoParam.ENABLE_ORIGINAL_VIDEO_STREAM_PLAY, false));
            this.textFinish = bundle.getString(PhotoParam.FINISH_TEXT);
            if (TextUtils.isEmpty(this.textFinish)) {
                this.textFinish = getResources().getString(R.string.send);
            }
            this.finishBtnBgColor = bundle.getInt(PhotoParam.FINISH_BTN_BG_COLOR, -1);
            this.fullscreenPreview = bundle.getBoolean(PhotoParam.FULLSCREEN_PREVIEW, false);
            this.showDotIndicator = bundle.getBoolean(PhotoParam.SHOW_DOT_INDICATOR, false);
            this.showTextIndicator = bundle.getBoolean(PhotoParam.SHOW_TEXT_INDICATOR, false);
            this.onlyPreviewSelect = bundle.getBoolean(PhotoParam.ONLY_PREVIEW_SELECT, false);
            this.forceFullScreenPreview = bundle.getBoolean(PhotoParam.FORCE_FULLSCREEN_PREVIEW, false);
            this.selectMode = bundle.getBoolean(PhotoParam.SELECT_GRID, false);
            this.forceFetchOriginalPhoto = bundle.getBoolean(PhotoParam.FORCE_FETCCH_ORIGINAL_PHOTO, false);
            this.contextIndex = bundle.getString(PhotoParam.CONTEXT_INDEX);
            this.photoContext = PhotoContext.get(this.contextIndex);
            PhotoContext.remove(this.contextIndex);
            if (this.photoContext.photoList == null || this.photoContext.photoList.isEmpty()) {
                this.activity.finish();
                AnimationUtil.fadeInFadeOut(this.activity);
                return;
            }
            this.pagerAdapter.a(this.photoContext.photoList);
            this.photoList = this.pagerAdapter.b();
            try {
                updateHasSameGifInContextFlag();
            } catch (Exception e) {
                PhotoLogger.error((String) TAG, "updateHasSameGifInContextFlag Exception :" + e.getMessage());
            }
            this.selectedList = this.photoContext.selectedList;
            this.photoContext.userOriginPhoto = bundle.getBoolean(PhotoParam.USE_ORIGIN_PHOTO, false);
            this.cbOrigin.setChecked(this.photoContext.userOriginPhoto);
        }
        initViews();
    }

    private void updateHasSameGifInContextFlag() {
        List gifFiles = new LinkedList();
        for (int i = 0; i < this.photoList.size(); i++) {
            PhotoInfo pi = this.photoList.get(i);
            if (pi.isGif()) {
                gifFiles.add(pi);
            }
        }
        if (gifFiles.size() > 1) {
            Set gifPathSet = new HashSet();
            for (int i2 = 0; i2 < gifFiles.size(); i2++) {
                gifPathSet.add(((PhotoInfo) gifFiles.get(i2)).getPhotoPath());
            }
            hasSameGifInContext = gifPathSet.size() != gifFiles.size();
        }
        PhotoLogger.debug(TAG, "Has same gif file = " + hasSameGifInContext);
    }

    private void adaptLandscapeConfig() {
        if (!this.isSupportLandscapeBrowse || this.configDisableLandscapeSupport) {
            PhotoLogger.warn((String) TAG, (String) "adaptLandscapeConfig: Disable landscape browse!");
        } else if (this.activity != null) {
            PhotoLogger.warn((String) TAG, (String) "adaptLandscapeConfig: Enable landscape browse!");
            this.activity.setRequestedOrientation(2);
        } else {
            PhotoLogger.warn((String) TAG, (String) "adaptLandscapeConfig:Activity is null.");
        }
    }

    public void setPhotoGroups(List<PhotoGroup> photoGroups2) {
        setPhotoGroups(photoGroups2, 0);
    }

    public void setPhotoGroups(List<PhotoGroup> photoGroups2, int previewGroupIndex) {
        setPhotoGroups(photoGroups2, previewGroupIndex, null);
    }

    public void setPhotoGroups(List<PhotoGroup> photoGroups2, int previewGroupIndex, Bundle bundle) {
        if (this.photoGroups == null) {
            this.photoGroups = new ArrayList();
        }
        this.photoGroups.clear();
        this.photoGroups.addAll(photoGroups2);
        PhotoLogger.debug(TAG, "browsePhoto context index " + TAG);
        ArrayList photoItemList = new ArrayList();
        PhotoContext photoContext2 = PhotoContext.get(TAG);
        int photoCount = 0;
        for (int groupIndex = 0; groupIndex < photoGroups2.size(); groupIndex++) {
            if (groupIndex == previewGroupIndex) {
                this.previewPosition = photoCount;
            }
            PhotoGroup photoGroup = photoGroups2.get(groupIndex);
            photoGroup.setOffset(photoCount);
            if (photoGroup.getPhotoInfoList() != null) {
                photoCount += photoGroup.getPhotoInfoList().size();
                List photoInfoList = photoGroup.getPhotoInfoList();
                for (int index = 0; index < photoInfoList.size(); index++) {
                    PhotoInfo photoInfo = photoInfoList.get(index);
                    photoInfo.setPhotoGroupIndex(groupIndex);
                    PhotoItem photoItem = new PhotoItem(photoInfo);
                    photoItem.setPhotoIndex(index);
                    if (photoItem.getSelected()) {
                        photoContext2.selectedList.add(photoItem);
                    }
                    photoItemList.add(photoItem);
                }
            }
        }
        photoContext2.photoList = photoItemList;
        this.selectedList = photoContext2.selectedList;
        if (bundle != null) {
            bundle.putString(PhotoParam.CONTEXT_INDEX, TAG);
            if (photoGroups2.size() > 1) {
                bundle.putInt(PhotoParam.PREVIEW_POSITION, this.previewPosition);
            }
        }
        setBundle(bundle);
        if (this.pageIndex == 0) {
            onPageSelected(this.pageIndex);
        }
    }

    private void initViews() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean showCbTop;
        int i9;
        int i10;
        this.mScanCodeDescPattern = getContext().getString(R.string.str_recognize_code_from_pic);
        View view = this.ivDownloadEntry;
        if (this.isShowPhotoDownload) {
            i = 0;
        } else {
            i = 8;
        }
        view.setVisibility(i);
        View view2 = this.ivGridGroup;
        if (this.enableGridGroup) {
            i2 = 0;
        } else {
            i2 = 4;
        }
        view2.setVisibility(i2);
        RelativeLayout relativeLayout = this.optionBar;
        if (this.enableDelete || this.showTextIndicator) {
            i3 = 0;
        } else {
            i3 = 8;
        }
        relativeLayout.setVisibility(i3);
        View view3 = this.bottomText;
        if (this.fullscreenPreview) {
            i4 = 8;
        } else {
            i4 = 0;
        }
        view3.setVisibility(i4);
        if (this.fullscreenPreview) {
            this.topBar.setVisibility(8);
            this.bottomBar.setVisibility(8);
        }
        TextView textView = this.textIndicator;
        if (this.enableDelete || this.showTextIndicator) {
            i5 = 0;
        } else {
            i5 = 8;
        }
        textView.setVisibility(i5);
        RelativeLayout relativeLayout2 = this.topBar;
        if (selectPhoto) {
            i6 = 0;
        } else {
            i6 = 8;
        }
        relativeLayout2.setVisibility(i6);
        RelativeLayout relativeLayout3 = this.bottomBar;
        if (selectPhoto) {
            i7 = 0;
        } else {
            i7 = 8;
        }
        relativeLayout3.setVisibility(i7);
        IndicatorView indicatorView = this.dotIndicator;
        if (this.showDotIndicator) {
            i8 = 0;
        } else {
            i8 = 8;
        }
        indicatorView.setVisibility(i8);
        if (!this.selectMode || (!this.fullscreenPreview && (this.showDotIndicator || this.showTextIndicator))) {
            showCbTop = false;
        } else {
            showCbTop = true;
        }
        CheckBox checkBox = this.cbSelectTop;
        if (showCbTop) {
            i9 = 0;
        } else {
            i9 = 8;
        }
        checkBox.setVisibility(i9);
        if (showCbTop) {
            this.enableGridGroup = false;
            this.ivGridGroup.setVisibility(4);
        }
        this.llOrigin = findViewById(R.id.ll_origin);
        this.llOrigin.setOnClickListener(this);
        Button button = this.btDelete;
        if (this.enableDelete) {
            i10 = 0;
        } else {
            i10 = 8;
        }
        button.setVisibility(i10);
        if (this.previewPosition < 0 || this.previewPosition >= this.photoContext.photoList.size()) {
            this.previewPosition = 0;
        }
        if (this.onlyPreviewSelect && this.maxSelect == 1) {
            if (this.photoContext.photoList != null && this.photoContext.photoList.size() > 0) {
                this.photoList.clear();
                this.photoList.add(this.photoContext.photoList.get(this.previewPosition));
                this.previewPosition = 0;
            }
            this.tvNumberIndex.setVisibility(4);
        }
        updateContent();
        this.vpPhoto.setAdapter(this.pagerAdapter);
        this.vpPhoto.setCurrentItem(this.previewPosition);
        this.dotIndicator.setCount(this.photoList.size());
        this.dotIndicator.setSelection(this.previewPosition);
        onPageScrollStateChanged(0);
        if (this.maxSelect == 1) {
            this.llSelect.setVisibility(8);
            this.bottomBar.setVisibility(0);
            this.topBar.setVisibility(0);
        }
        if (this.finishBtnBgColor != -1) {
            this.btFinish.setBackgroundColor(this.finishBtnBgColor);
        }
        initPullDownFinish(this.mEnablePullDownFinish);
        showEntryThenPendingTriggerAutoHide();
    }

    private void initPhotoReceiver(Activity activity2) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(PhotoSelectActivity.ACTION_PHOTO_ADAPTER_CHANGE);
        this.receiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if (PhotoBrowseView.this.pagerAdapter != null) {
                    PhotoBrowseView.this.pagerAdapter.a();
                    PhotoBrowseView.this.pagerAdapter.a(PhotoBrowseView.this.photoContext.photoList);
                    PhotoBrowseView.this.pagerAdapter.notifyDataSetChanged();
                    PhotoBrowseView.this.vpPhoto.setAdapter(PhotoBrowseView.this.pagerAdapter);
                    int count = PhotoBrowseView.this.photoContext.photoList.size();
                    PhotoBrowseView.this.dotIndicator.setCount(count);
                    if (count == 0) {
                        PhotoBrowseView.this.toggleFinish();
                        return;
                    }
                    if (PhotoBrowseView.this.pageIndex >= count) {
                        PhotoBrowseView.this.pageIndex = count - 1;
                    }
                    PhotoBrowseView.this.vpPhoto.setCurrentItem(PhotoBrowseView.this.pageIndex);
                    if (PhotoBrowseView.this.pageIndex == 0) {
                        PhotoBrowseView.this.onPageSelected(0);
                    }
                }
            }
        };
        try {
            activity2.registerReceiver(this.receiver, filter);
        } catch (Throwable e) {
            PhotoLogger.debug(TAG, "registerReceiver exception," + e.getMessage());
            UserBehavior.reportUnusableEvent("PhotoService_PhotoSelect", "registerReceiver", e.b);
        }
    }

    private void unregisterReceiver(Activity activity2) {
        if (activity2 != null) {
            PhotoLogger.debug(TAG, "unregisterReceiver called");
            activity2.unregisterReceiver(this.receiver);
        }
    }

    public void pause() {
        PhotoLogger.debug(TAG, AudioUtils.CMDPAUSE);
        this.isFromPause = true;
        this.lastPagerCount = this.pagerAdapter.getCount();
        PhotoPreview pp = (PhotoPreview) this.itemMap.get(this.focusedIndexRecord);
        if (pp != null) {
            pp.onLoseFocus();
            this.focusedIndexRecord = -1;
        }
        this.isActivityResumed = false;
    }

    public void resume() {
        PhotoLogger.debug(TAG, "resume");
        if (this.isFromPause) {
            if (this.isCalledBeforeResume) {
                PhotoLogger.debug(TAG, "deleteGroup called before resume ,do nothing onResume!");
                this.isCalledBeforeResume = false;
            } else if (this.lastPagerCount != this.pagerAdapter.getCount()) {
                this.pagerAdapter.a();
                this.pagerAdapter.a(this.photoContext.photoList);
                this.pagerAdapter.notifyDataSetChanged();
                this.vpPhoto.setAdapter(this.pagerAdapter);
                int count = this.photoList.size();
                this.dotIndicator.setCount(count);
                if (count == 0) {
                    toggleFinish();
                    return;
                } else if (this.pageIndex >= count) {
                    this.pageIndex = count - 1;
                    this.vpPhoto.setCurrentItem(this.pageIndex);
                } else {
                    this.vpPhoto.setCurrentItem(this.pageIndex);
                    onPageSelected(this.pageIndex);
                }
            } else {
                this.vpPhoto.setCurrentItem(this.pageIndex);
                onPageSelected(this.pageIndex);
            }
        }
        this.isFromPause = false;
        this.isActivityResumed = true;
    }

    public void newIntent(Intent intent) {
        boolean showCbTop;
        PhotoContext.remove(this.contextIndex);
        this.selectMode = intent.getBooleanExtra(PhotoParam.SELECT_GRID, false);
        if (!this.selectMode || (!this.fullscreenPreview && (this.showDotIndicator || this.showTextIndicator))) {
            showCbTop = false;
        } else {
            showCbTop = true;
        }
        this.cbSelectTop.setVisibility(showCbTop ? 0 : 8);
        if (showCbTop) {
            this.enableGridGroup = false;
            this.ivGridGroup.setVisibility(4);
        }
        if (this.autoHideGrid) {
            this.ivGridGroup.removeCallbacks(this.hideGridRunnable);
        }
        int position = intent.getIntExtra(PhotoParam.PREVIEW_POSITION, -1);
        if (position != -1 && position != this.pageIndex && position >= 0 && position < this.photoList.size()) {
            this.vpPhoto.setCurrentItem(position, false);
        }
    }

    public void saveInstanceState(Intent intent, Bundle bundle) {
        PhotoLogger.debug(TAG, "onSaveInstanceState " + this.contextIndex);
        this.afterSaveInstanceState = true;
        bundle.putAll(intent.getExtras());
        bundle.putInt(PhotoParam.PREVIEW_POSITION, this.pageIndex);
        bundle.putString(PhotoParam.CONTEXT_INDEX, this.contextIndex);
        PhotoContext.contextMap.put(this.contextIndex, this.photoContext);
    }

    /* access modifiers changed from: private */
    public View createView(int position) {
        ViewGroup container = this.vpPhoto;
        PhotoPreview photoPreview = this.cachePreview;
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        if (photoPreview == null) {
            PhotoLogger.debug("videoCompact", "viewPager  no cached View,create it!");
            photoPreview = (PhotoPreview) inflater.inflate(R.layout.photo_preview, container, false);
        } else {
            this.cachePreview = null;
        }
        container.addView(photoPreview);
        photoPreview.setSupportGif(isGifBrowseSupport());
        dispatchLoadAction(activeViews(position, photoPreview), photoPreview);
        return photoPreview;
    }

    private PhotoItem activeViews(int position, PhotoPreview photoPreview) {
        PhotoView photoView = photoPreview.getPhotoView();
        photoView.setDisplayType(this.photoViewDisplayType);
        if (!(this.previewImgHeight == 0 || this.previewImgWidth == 0)) {
            LayoutParams params = photoView.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(-2, -2);
            }
            params.height = dip2px(getContext(), (float) this.previewImgHeight);
            params.width = dip2px(getContext(), (float) this.previewImgWidth);
            photoView.setLayoutParams(params);
        }
        photoView.setOnClickListener(this.photoClickListener);
        photoView.setEnableScale(this.enableScale);
        photoView.setFitSpace(this.fitSpace);
        if (this.forceFullScreenPreview) {
            photoView.setDisplayType(4);
        }
        photoPreview.getVideoPlayView().setOnClickListener(this.photoClickListener);
        photoPreview.setTag(Integer.valueOf(position));
        PhotoItem photoInfo = this.photoList.get(position);
        setLongClickListener(photoPreview, photoInfo);
        View gifView = photoPreview.touchGifView();
        if (gifView != null) {
            gifView.setOnClickListener(this.photoClickListener);
            gifView.setOnLongClickListener(this.photoLongClickListener);
        }
        return photoInfo;
    }

    private void setLongClickListener(PhotoPreview photoPreview, PhotoItem photoInfo) {
        switch (photoInfo.getMediaType()) {
            case 0:
                photoPreview.getPhotoView().setOnLongClickListener(this.photoLongClickListener);
                return;
            case 1:
                photoPreview.getVideoPlayView().setOnLongClickListener(this.videoLongClickListener);
                return;
            case 2:
                photoPreview.getVideoPlayView().setOnLongClickListener(this.originalVideoLongClickListener);
                return;
            default:
                return;
        }
    }

    public int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void dispatchLoadAction(PhotoItem photoInfo, PhotoPreview photoPreview) {
        PhotoLogger.debug(TAG, "dispatchLoadAction");
        photoPreview.dismissProgress();
        if (photoInfo.isVideo()) {
            doLoadVideo(photoInfo, photoPreview);
            return;
        }
        photoPreview.getVideoPlayView().loadVideoInfo(null);
        if (isLiveGif(photoInfo)) {
            doLoadGif(photoInfo, photoPreview);
        } else {
            doLoadImage(photoInfo, photoPreview);
        }
    }

    private boolean isLiveGif(PhotoItem photoInfo) {
        return isGifBrowseSupport() && photoInfo.isGif();
    }

    private boolean isGifBrowseSupport() {
        return this.enableGifBrowse && !this.configDisableGifDynamicPreview;
    }

    private void doLoadVideo(PhotoItem photoInfo, PhotoPreview photoPreview) {
        photoPreview.changeViewType(1);
        photoPreview.getVideoPlayView().loadVideoInfo(photoInfo);
    }

    private void doLoadGif(PhotoItem photoInfo, PhotoPreview photoPreview) {
        photoPreview.changeViewType(10);
        photoPreview.getGifViewZone().loadData(photoInfo);
    }

    private void doLoadImage(PhotoItem photoInfo, PhotoPreview photoPreview) {
        boolean z = true;
        photoPreview.changeViewType(0);
        String photoPath = photoInfo.getPhotoPath();
        PhotoView photoView = photoPreview.getPhotoView();
        photoView.setTag(R.id.id_photo_info_tag, photoInfo);
        if (TextUtils.isEmpty(photoPath)) {
            PhotoLogger.debug(TAG, "invalid photo path.");
            Drawable fail = photoInfo.getFail() == null ? getResources().getDrawable(R.drawable.load_fail) : photoInfo.getFail();
            photoPreview.getPhotoView().failDrawableHashCode = fail.hashCode();
            photoPreview.getPhotoView().setImageDrawable(fail);
        } else if (photoInfo.getPhoto() != null) {
            PhotoLogger.debug(TAG, "set photo view from photo info");
            photoView.setImageDrawable(photoInfo.getPhoto());
        } else {
            boolean hasOriPhoto = ImageHelper.hasOriginPhoto(photoPath);
            if (setOriOrBigFromMem(photoView, photoInfo.getPhotoPath(), this.forceFetchOriginalPhoto, hasOriPhoto)) {
                photoInfo.isLoadedOnce = true;
                return;
            }
            Drawable drawable = getAndSetThumbDrawableCode(photoInfo, photoView);
            LoadInfo loadInfo = getLoadInfo(photoInfo, photoPreview);
            if (photoInfo.getPhotoMark() == null && hasOriPhoto) {
                attatchExtraInfo(loadInfo, -1, -1, false);
                performShowProgress(photoPreview, loadInfo);
                ImageHelper.load((ImageView) photoView, photoPath, drawable, -1, -1, loadInfo);
            } else if (this.forceFetchOriginalPhoto) {
                if (loadInfo != null) {
                    loadInfo.loadingOrigin = true;
                    loadInfo.thumbHeight = dip2px(getContext(), (float) drawable.getIntrinsicHeight());
                    loadInfo.thumbWidth = dip2px(getContext(), (float) drawable.getIntrinsicWidth());
                    if (hasOriPhoto) {
                        z = false;
                    }
                    attatchExtraInfo(loadInfo, -1, -1, z);
                }
                performShowProgress(photoPreview, loadInfo);
                ImageHelper.load((ImageView) photoView, photoPath, drawable, -1, -1, loadInfo);
            } else {
                int width = photoInfo.getLargePhotoWidth();
                int height = photoInfo.getLargePhotoHeight();
                if (hasOriPhoto || ImageHelper.hasBigPhoto(photoPath)) {
                    z = false;
                }
                attatchExtraInfo(loadInfo, width, height, z);
                performShowProgress(photoPreview, loadInfo);
                ImageHelper.load((ImageView) photoView, photoPath, drawable, width, height, loadInfo);
            }
        }
    }

    private void performShowProgress(PhotoPreview pv, LoadInfo info) {
        if (info.isShowingPie) {
            pv.showProgress(true);
            pv.setProgress(info.progress);
            return;
        }
        pv.showProgress(false);
    }

    private void attatchExtraInfo(LoadInfo info, int w, int h, boolean isShowingPie) {
        info.loadingWidth = w;
        info.loadingHeight = h;
        info.isShowingPie = this.isShowPhotoExactlyProgress && isShowingPie;
    }

    private boolean setOriOrBigFromMem(ImageView imageView, String photoPath, boolean isForceOri, boolean hasOri) {
        boolean isOriNeed;
        if (TextUtils.isEmpty(photoPath)) {
            return false;
        }
        PhotoLogger.debug(TAG, photoPath + ">>>>>>>>");
        if (hasOri || isForceOri) {
            isOriNeed = true;
        } else {
            isOriNeed = false;
        }
        String target = isOriNeed ? "original photo " : "big photo ";
        Bitmap bmp = ImageHelper.loadFromCache(photoPath, isOriNeed);
        if (bmp != null) {
            PhotoLogger.debug(TAG, target + "cache hits,set photo view from mem cache>>>>>>>>");
            imageView.setImageBitmap(bmp);
            return true;
        }
        PhotoLogger.debug(TAG, target + "cache miss !>>>>>>>>");
        return false;
    }

    private Drawable getAndSetThumbDrawableCode(PhotoItem photoInfo, PhotoView photoView) {
        PhotoLogger.debug(TAG, "getAndSetThumbDrawable()");
        boolean hasThumb = false;
        String thumbPath = photoInfo.getThumbPath();
        if (photoInfo.getThumb() != null) {
            PhotoLogger.debug(TAG, "set photo view from thumb drawable");
            photoView.thumbDrawableHashCode = photoInfo.getThumb().hashCode();
            photoView.setImageDrawable(photoInfo.getThumb());
            hasThumb = true;
        } else {
            Bitmap bitmap = ImageHelper.loadThumbPhoto(photoInfo, PREFER_WIDTH_THUMB_PX);
            if (bitmap != null) {
                PhotoLogger.debug(TAG, "set photo view from cache image");
                setDrawableThumbCode(photoView, bitmap);
                hasThumb = true;
            } else if (!TextUtils.isEmpty(thumbPath)) {
                PhotoLogger.debug(TAG, "photo thumb path " + thumbPath);
                Bitmap bitmap2 = null;
                try {
                    bitmap2 = BitmapFactory.decodeFile(thumbPath);
                } catch (Throwable t) {
                    PhotoLogger.debug(TAG, "decode bitmap exception." + t.toString());
                }
                if (bitmap2 != null) {
                    PhotoLogger.debug(TAG, "set photo view from local thumb path");
                    setDrawableThumbCode(photoView, bitmap2);
                    hasThumb = true;
                }
            }
        }
        Drawable drawable = hasThumb ? photoView.getDrawable() : this.defaultPhoto;
        if (!hasThumb) {
            photoView.defaultDrawableHashCode = drawable.hashCode();
            PhotoLogger.debug(TAG, "cann`t find thumbnail,show default drawable");
        }
        PhotoLogger.debug(TAG, "hasThumb:" + hasThumb);
        if (hasThumb) {
            return photoView.getDrawable();
        }
        return this.defaultPhoto;
    }

    private void setDrawableThumbCode(PhotoView pv, Bitmap bitmap) {
        Drawable d = new BitmapDrawable(getContext().getResources(), bitmap);
        pv.thumbDrawableHashCode = d.hashCode();
        pv.setImageDrawable(d);
    }

    public void onClick(View view) {
        boolean z = true;
        if (System.currentTimeMillis() - this.lastClickTimestamp >= 200) {
            this.lastClickTimestamp = System.currentTimeMillis();
            if ((view.equals(this.ibBack) || view.equals(this.optionBarBack)) && this.activity != null) {
                this.activity.onBackPressed();
            } else if (view == this.llOrigin) {
                boolean checked = this.cbOrigin.isChecked();
                CheckBox checkBox = this.cbOrigin;
                if (checked) {
                    z = false;
                }
                checkBox.setChecked(z);
            } else if (view.equals(this.btFinish)) {
                if (this.maxSelect == 1 && !this.selectedList.isEmpty()) {
                    this.selectedList.clear();
                }
                if (this.selectedList.isEmpty()) {
                    PhotoItem photoInfo = this.photoList.get(this.pageIndex);
                    if (!this.enableGifBrowse || !PhotoSelectActivity.isGifAndCantSelect(getContext(), this.enableGifBrowse, photoInfo, maxGifSizeCanSend, maxGifPixelCanSend)) {
                        photoInfo.setSelected(true);
                        this.selectedList.add(photoInfo);
                    } else {
                        return;
                    }
                }
                onSelected();
            } else if (view.equals(this.ivGridGroup)) {
                select();
            } else if (view.equals(this.btDelete)) {
                showDeleteDialog(new PhotoMenu(null, "delete"));
            } else if (view.equals(this.rlShowOrigin)) {
                performShowOrigin();
            } else if (view.equals(this.ibCancelShow)) {
                cancelShowOrigin();
            } else if (view == this.tvEdit) {
                performEditingPhoto();
            } else if (view == this.tvEditWhenPreviewPhoto) {
                editImageUseIn();
            } else if (view == this.ivDownloadEntry) {
                doSaveMedia(this.photoContext.photoList.get(this.pageIndex));
            }
        }
    }

    private void editImageUseIn() {
        try {
            if (this.photoContext == null || this.photoContext.photoList == null || this.photoContext.photoList.size() <= this.pageIndex) {
                PhotoLogger.warn((String) TAG, (String) "pageIndex is invalid!");
            } else {
                doEditUseIn(this.photoContext.photoList.get(this.pageIndex));
            }
        } catch (Exception e) {
            PhotoLogger.d(TAG, "editImageUseIn:### exception:" + e.getMessage());
        }
    }

    private void doEditUseIn(final PhotoInfo src) {
        PhotoLogger.warn((String) TAG, "Perform editing photo @" + src.getPhotoPath());
        Map pathMap = new HashMap();
        pathMap.put("path", src.getPhotoPath());
        if (!this.mIsEnableInPaster) {
            BrowsePhotoAsListActivity.disableInPaster(pathMap);
        }
        Bitmap bmp = ((PhotoPreview) this.vpPhoto.findViewWithTag(Integer.valueOf(this.pageIndex))).getPhotoView().getValidShowingPhoto();
        pathMap.put(Constants.KEY_BITMAP, bmp);
        if (bmp != null || ImageHelper.isLocalFile(src.getPhotoPath())) {
            ((ImageEditService) MicroServiceUtil.getMicroService(ImageEditService.class)).editImageUseIn(pathMap, new InImageEditListener() {
                public final void onResult(boolean isEdited, String outPath, Bitmap outBitmap, Map<String, Object> extParams) {
                    PhotoBrowseView.this.onInEditReturn(isEdited, outPath, outBitmap, src);
                }
            });
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.str_wait_photo_download), 0).show();
        }
    }

    /* access modifiers changed from: private */
    public void onInEditReturn(boolean isEdited, String outPath, Bitmap outBitmap, PhotoInfo src) {
        PhotoLogger.debug(TAG, "isEdited : " + isEdited + ", outPath : " + outPath + ", outBitmap : " + outBitmap);
        PhotoInfo srcCopy = new PhotoInfo(src);
        if (isEdited && !TextUtils.isEmpty(outPath) && outBitmap != null) {
            src.setPhotoPath(outPath);
            src.setPhotoHeight(outBitmap.getHeight());
            src.setPhotoWidth(outBitmap.getWidth());
            if (src.extraInfo == null) {
                src.extraInfo = new HashMap();
            }
            src.extraInfo.put(Constants.KEY_IN_EDITED, Boolean.valueOf(true));
            PhotoPreview photoPreview = (PhotoPreview) this.vpPhoto.findViewWithTag(Integer.valueOf(this.pageIndex));
            if (photoPreview != null) {
                photoPreview.getPhotoView().setImageBitmap(outBitmap);
            }
            if (this.photoContext != null && (this.photoContext.previewListener instanceof V2)) {
                ((V2) this.photoContext.previewListener).onPhotoEditWithIn(srcCopy, src);
            }
        }
    }

    private void performEditingPhoto() {
        UserBehavior.onPhotoPreviewActivityEditImageClicked();
        ImageEditService service = (ImageEditService) MicroServiceUtil.getMicroService(ImageEditService.class);
        if (service != null) {
            PhotoInfo targetPhotoInfo = null;
            if (this.photoContext != null) {
                if (this.photoContext.selectedList != null && !this.photoContext.selectedList.isEmpty()) {
                    PhotoLogger.debug(TAG, "User selected 1 photo.");
                    targetPhotoInfo = this.photoContext.selectedList.get(0);
                } else if (this.photoContext.photoList == null || this.photoContext.photoList.size() <= this.pageIndex) {
                    PhotoLogger.warn((String) TAG, (String) "pageIndex is invalid!");
                } else {
                    PhotoLogger.debug(TAG, "None photo is selected,pass current showing photo to edit.");
                    targetPhotoInfo = this.photoContext.photoList.get(this.pageIndex);
                }
                if (targetPhotoInfo != null) {
                    PhotoLogger.warn((String) TAG, "Perform editing photo @" + targetPhotoInfo.getPhotoPath());
                    service.editImage(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopApplication(), this.mImageEditListener, targetPhotoInfo.getPhotoPath(), this.mBusinessId, null);
                    return;
                }
                return;
            }
            return;
        }
        PhotoLogger.warn((String) TAG, (String) "Get ImageEditService return null!");
    }

    /* access modifiers changed from: private */
    public void onSelected() {
        this.photoContext.sendSelectedPhoto(this.activity, this.beautyImageLevel, this.compressImageQuality, new Runnable() {
            public final void run() {
                if (PhotoBrowseView.this.activity != null) {
                    PhotoBrowseView.this.activity.setResult(-1);
                } else {
                    PhotoLogger.warn((String) PhotoBrowseView.TAG, (String) "activity is Null.");
                }
                PhotoBrowseView.this.toggleFinish();
            }
        });
    }

    public void backPressed() {
        if (this.activity != null) {
            Intent intent = this.activity.getIntent();
            intent.putExtra(PhotoParam.USE_ORIGIN_PHOTO, this.cbOrigin.isChecked());
            this.activity.setResult(0, intent);
            toggleFinish();
        }
    }

    private boolean canClickCancelShow() {
        if (SystemClock.elapsedRealtime() - this.cancelShowLastTime <= 200) {
            return false;
        }
        this.cancelShowLastTime = SystemClock.elapsedRealtime();
        return true;
    }

    private boolean canClickShowOrigin() {
        if (SystemClock.elapsedRealtime() - this.showOriginLastClickTime <= 200) {
            return false;
        }
        this.showOriginLastClickTime = SystemClock.elapsedRealtime();
        return true;
    }

    private void performShowOrigin() {
        if (!canClickShowOrigin()) {
            return;
        }
        if (this.ibCancelShow.getVisibility() != 0) {
            this.ibCancelShow.setVisibility(0);
            PhotoItem photoInfo = this.photoList.get(this.pageIndex);
            PhotoPreview photoPreview = (PhotoPreview) this.vpPhoto.findViewWithTag(Integer.valueOf(this.pageIndex));
            PhotoView photoView = photoPreview.getPhotoView();
            String photoPath = photoInfo.getPhotoPath();
            LoadInfo loadInfo = getLoadInfo(photoInfo, photoPreview);
            loadInfo.loadingOrigin = true;
            this.pbShowOrigin.setText(loadInfo.progress + "%").setProgress(loadInfo.progress);
            ImageHelper.load((ImageView) photoView, photoPath, photoView.getDrawable(), -1, -1, loadInfo);
            return;
        }
        cancelShowOrigin();
    }

    private LoadInfo getLoadInfo(PhotoItem photoInfo, PhotoPreview photoPreview) {
        LoadInfo loadInfo = this.loadMap.get(photoInfo);
        if (loadInfo == null) {
            loadInfo = new LoadInfo();
            loadInfo.setProxy(this.loadListener);
            loadInfo.loading = false;
            loadInfo.photoItem = photoInfo;
            loadInfo.progress = 0;
        }
        loadInfo.setPhotoPreview(photoPreview);
        this.loadMap.put(photoInfo, loadInfo);
        return loadInfo;
    }

    private void cancelShowOrigin() {
        if (canClickCancelShow()) {
            PhotoLogger.debug(TAG, "cancelShowOrigin");
            PhotoItem photoItem = this.photoList.get(this.pageIndex);
            this.ibCancelShow.setVisibility(8);
            setOriginText(photoItem.getPhotoSize());
            LoadInfo loadInfo = this.loadMap.remove(photoItem);
            if (loadInfo == null) {
                PhotoLogger.debug(TAG, "failed to cancel load!");
                return;
            }
            loadInfo.setProxy(null);
            ImageHelper.cancel(loadInfo.taskId);
        }
    }

    /* access modifiers changed from: private */
    public void showDeleteDialog(final PhotoMenu photoMenu) {
        AUNoticeDialog dialog = new AUNoticeDialog(this.activity, "", getContext().getString(R.string.confirm_delete), getContext().getString(R.string.delete), getContext().getString(R.string.cancel));
        dialog.setPositiveListener(new OnClickPositiveListener() {
            public final void onClick() {
                PhotoBrowseView.this.processMenu(photoMenu);
            }
        });
        dialog.show();
    }

    private void select() {
        if (this.activity != null) {
            PhotoContext.contextMap.put(this.contextIndex, this.photoContext);
            Intent intent = new Intent(this.activity, PhotoSelectActivity.class);
            intent.putExtras(this.activity.getIntent().getExtras());
            intent.putExtra(PhotoParam.PREVIEW_POSITION, this.pageIndex);
            intent.putExtra(PhotoParam.AUTO_PLAY_PREVIEW_POSITION, false);
            intent.addFlags(131072);
            if (this.activity instanceof BaseActivity) {
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().startActivity((MicroApplication) ((BaseActivity) this.activity).getActivityApplication(), intent);
                AnimationUtil.fadeInFadeOut(this.activity);
            }
        }
    }

    public void onPageScrollStateChanged(int state) {
        if (this.autoHideGrid && state == 1) {
            clearAutoDismiss();
        }
        if (state == 0) {
            showEntryThenPendingTriggerAutoHide();
        }
    }

    private void showEntryThenPendingTriggerAutoHide() {
        pendingToShowGridAndDownload();
        if (this.autoHideGrid) {
            triggerAutoDismiss();
        }
    }

    private void triggerAutoDismiss() {
        PhotoLogger.debug(TAG, "triggerAutoDismiss:###");
        this.mAutoHideHandler.postDelayed(this.hideGridRunnable, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    private void clearAutoDismiss() {
        PhotoLogger.debug(TAG, "clearAutoDismiss:###");
        this.mAutoHideHandler.removeCallbacksAndMessages(null);
    }

    private void pendingToShowDownloadEntry() {
        if (this.isShowPhotoDownload) {
            PhotoItem pi = this.photoList.get(this.pageIndex);
            if (pi.getMediaType() != 0 || pi.isGif()) {
                this.ivDownloadEntry.setVisibility(4);
            } else {
                this.ivDownloadEntry.setVisibility(0);
            }
        } else {
            this.ivDownloadEntry.setVisibility(8);
        }
    }

    public int getCurrentPagePosition() {
        if (this.vpPhoto != null) {
            return this.vpPhoto.getCurrentItem();
        }
        return 0;
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        pendingToShowGridAndDownload();
    }

    private void pendingToShowGridAndDownload() {
        if (this.enableGridGroup) {
            this.ivGridGroup.setVisibility(0);
        }
        pendingToShowDownloadEntry();
    }

    public void onPageSelected(int position) {
        String groupId;
        if (this.photoList != null && position >= 0 && position < this.photoList.size()) {
            PhotoLogger.debug(TAG, "onPageSelected index ＝ " + position);
            this.pageIndex = position;
            PhotoItem photoInfo = this.photoList.get(position);
            PhotoPreview photoPreview = (PhotoPreview) this.vpPhoto.findViewWithTag(Integer.valueOf(this.pageIndex));
            if (photoPreview != null) {
                photoPreview.getPhotoView().onShow();
            }
            int originalViewVisibility = (photoInfo.isVideo() || !this.enableSelectOrigin) ? 8 : 0;
            if (isLiveGif(photoInfo)) {
                originalViewVisibility = 8;
            }
            this.llOrigin.setVisibility(originalViewVisibility);
            boolean selected = photoInfo.getSelected();
            this.cbSelected.setChecked(selected);
            this.cbSelectTop.setChecked(selected);
            updatePhotoText(photoInfo);
            updateShowOrigin(photoInfo);
            updateContent();
            controlPlay(position);
            if (this.photoPagerListener != null) {
                if (this.photoGroups == null) {
                    groupId = "";
                } else {
                    groupId = this.photoGroups.get(photoInfo.getPhotoGroupIndex()).getId();
                }
                int index = position - this.photoGroups.get(photoInfo.getPhotoGroupIndex()).getOffset();
                this.photoPagerListener.onPageSelected(index, photoInfo.getPhotoGroupIndex(), groupId);
                if (this.photoPagerListener instanceof V3) {
                    ((V3) this.photoPagerListener).onPageSelected(index, photoInfo);
                }
            }
        }
    }

    private void controlPlay(int target) {
        if (this.focusedIndexRecord != target) {
            PhotoLogger.debug(TAG, "controlPlay index = " + this.pageIndex);
            PhotoPreview historyP = (PhotoPreview) this.itemMap.get(this.focusedIndexRecord);
            if (historyP != null) {
                historyP.onLoseFocus();
            }
            PhotoPreview pp = (PhotoPreview) this.itemMap.get(String.valueOf(target));
            if (pp == null) {
                pp = (PhotoPreview) createView(target);
                this.itemMap.put(String.valueOf(target), pp);
            }
            pp.onFocus(this.isAutoPlayPosition);
            if (this.isAutoPlayPosition) {
                this.isAutoPlayPosition = false;
            }
        } else {
            PhotoLogger.debug(TAG, "controlPlay filter the repeated index at:" + this.pageIndex);
        }
        this.focusedIndexRecord = target;
    }

    private void updateShowOrigin(PhotoItem photoItem) {
        boolean isNaturallyDisabled;
        boolean isDisabledByIntercept = true;
        boolean enableOrigin = photoItem.getLoadOrigin();
        boolean hasOrigin = ImageHelper.hasOriginPhoto(photoItem.getPhotoPath());
        PhotoLogger.debug(TAG, "updateShowOrigin enableOrigin " + enableOrigin + " showOrigin " + this.showOrigin + " hasOrigin " + hasOrigin);
        if (!enableOrigin || !this.showOrigin || hasOrigin) {
            isNaturallyDisabled = true;
        } else {
            isNaturallyDisabled = false;
        }
        if (isNaturallyDisabled || !isInterceptShowOringin(photoItem)) {
            isDisabledByIntercept = false;
        }
        photoItem.isDisabledByIntercept = isDisabledByIntercept;
        if (isNaturallyDisabled || isDisabledByIntercept) {
            PhotoLogger.debug(TAG, "hide show origin area." + (isDisabledByIntercept ? "isDisabledByIntercept" : "isNaturallyDisabled"));
            this.rlShowOrigin.setVisibility(8);
            return;
        }
        this.rlShowOrigin.setVisibility(0);
        LoadInfo loadInfo = this.loadMap.get(photoItem);
        if (loadInfo == null || !loadInfo.loadingOrigin) {
            setOriginText(photoItem.getPhotoSize());
            this.ibCancelShow.setVisibility(8);
            return;
        }
        this.pbShowOrigin.setText(loadInfo.progress + "%").setProgress(loadInfo.progress);
        this.ibCancelShow.setVisibility(0);
    }

    private boolean isInterceptShowOringin(PhotoItem photoItem) {
        if (photoItem == null || !this.isShowPhotoExactlyProgress || photoItem.isLoadedOnce) {
            return false;
        }
        PhotoLogger.debug(TAG, "Intercept show \"load origin image\"");
        return true;
    }

    private void setOriginText(long size) {
        String origin = getResources().getString(R.string.show_origin);
        this.pbShowOrigin.setText(origin + "(" + new DiskFormatter().format((double) size) + ")").setProgress(0);
    }

    /* access modifiers changed from: protected */
    public void updateContent() {
        String indicatorText = (this.pageIndex + 1) + "/" + this.photoList.size();
        this.tvNumberIndex.setText(indicatorText);
        this.textIndicator.setText(indicatorText);
        this.dotIndicator.setSelection(this.pageIndex);
        int selectedCount = 0;
        long selectedSize = 0;
        if (this.maxSelect > 1) {
            for (PhotoItem info : this.selectedList) {
                if (info.getSelected()) {
                    selectedCount++;
                    selectedSize += info.getPhotoSize();
                }
            }
        } else if (this.maxSelect == 1) {
            selectedSize = this.photoList.get(this.pageIndex).getPhotoSize();
        }
        if (selectedCount <= 0 || this.maxSelect == 1) {
            this.btFinish.setText(this.textFinish);
            updateOriginalTextHint(this.textOriginPhoto);
        } else {
            this.btFinish.setText(this.textFinish + "(" + selectedCount + "/" + this.maxSelect + ")");
        }
        this.tvEdit.setEnabled(checkEditable(selectedCount));
        if (this.tvEditWhenPreviewPhoto.getVisibility() != 8) {
            this.tvEditWhenPreviewPhoto.setVisibility(isLiveGif(this.photoList.get(this.pageIndex)) ? 4 : 0);
        }
        if (selectedCount > 0 || this.maxSelect == 1) {
            updateOriginalTextHint(this.textOriginPhoto + "(" + new DiskFormatter().format((double) selectedSize) + ")");
        }
    }

    private void updateOriginalTextHint(String textOriginPhoto2) {
        this.tvOrigin.setText(textOriginPhoto2);
        this.cbOrigin.setContentDescription(textOriginPhoto2);
    }

    private boolean checkEditable(int selectedCount) {
        PhotoLogger.debug(TAG, "checkEditable:###");
        if (selectedCount != 1 || this.photoContext == null || this.photoContext.selectedList == null || this.photoContext.selectedList.isEmpty() || this.photoContext.selectedList.get(0).getMediaType() != 0) {
            if (selectedCount != 0 || this.photoContext == null || this.photoContext.photoList == null || this.photoContext.photoList.size() <= this.pageIndex || this.photoContext.photoList.get(this.pageIndex).getMediaType() != 0) {
                PhotoLogger.debug(TAG, "checkEditable return False.");
                return false;
            } else if (isLiveGif(this.photoContext.photoList.get(this.pageIndex))) {
                PhotoLogger.debug(TAG, "Is live gif,return false.");
                return false;
            } else {
                PhotoLogger.debug(TAG, "Return True.");
                return true;
            }
        } else if (isLiveGif(this.photoContext.selectedList.get(0))) {
            PhotoLogger.debug(TAG, "Is live gif,return false.");
            return false;
        } else {
            PhotoLogger.debug(TAG, "Return True.");
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void toggleFinish() {
        toggleFinish(true);
    }

    /* access modifiers changed from: protected */
    public void toggleFinish(boolean isFadeInFadeOut) {
        if (this.enableDelete && this.photoContext != null) {
            this.photoContext.sendDeletedPhoto();
        }
        if (this.activity != null) {
            this.activity.finish();
            if (isFadeInFadeOut) {
                AnimationUtil.fadeInFadeOut(this.activity);
            } else {
                this.activity.overridePendingTransition(0, 0);
            }
        }
    }

    /* access modifiers changed from: private */
    public void toggleFullscreen() {
        Context context = this.activity;
        if (!this.purePhoto) {
            this.purePhoto = true;
            if (context != null) {
                if (this.topBar.getVisibility() != 8) {
                    new AnimationUtil(context, R.anim.slide_up).setFillAfter(true).startAnimation(this.topBar);
                }
                if (this.optionBar.getVisibility() != 8) {
                    new AnimationUtil(context, R.anim.slide_up).setFillAfter(true).startAnimation(this.optionBar);
                }
                if (this.bottomBar.getVisibility() != 8) {
                    new AnimationUtil(context, R.anim.effect_select_pop_down).setFillAfter(true).startAnimation(this.bottomBar);
                    return;
                }
                return;
            }
            return;
        }
        this.purePhoto = false;
        if (context != null) {
            if (this.topBar.getVisibility() != 8) {
                new AnimationUtil(context, R.anim.slide_down).setFillAfter(true).startAnimation(this.topBar);
            }
            if (this.optionBar.getVisibility() != 8) {
                new AnimationUtil(context, R.anim.slide_down).setFillAfter(true).startAnimation(this.optionBar);
            }
            if (this.bottomBar.getVisibility() != 8) {
                new AnimationUtil(context, R.anim.effect_select_pop_up).setFillAfter(true).startAnimation(this.bottomBar);
            }
        }
    }

    /* access modifiers changed from: private */
    public void showPhotoMenuDialog(final String photoPath, List<PhotoMenu> showList, boolean needCheckScanQR, final boolean isGif) {
        ArrayList menus = new ArrayList();
        for (int index = 0; index < showList.size(); index++) {
            menus.add(new PopMenuItem(showList.get(index).title));
        }
        AUListDialog popDialog = new AUListDialog(menus, (Context) this.activity);
        popDialog.setOnItemClickListener((OnItemClickListener) buildListener(this.photoList.get(this.pageIndex), showList));
        popDialog.setCanceledOnTouchOutside(true);
        popDialog.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialog) {
                PhotoBrowseView.this.scanTask = null;
            }
        });
        popDialog.show();
        if (needCheckScanQR) {
            this.scanTask = new c(popDialog, showList);
            BackgroundTaskUtil.executeNormal(new Runnable() {
                public final void run() {
                    String cachePath = ImageHelper.getCachePath(photoPath, isGif);
                    if (!TextUtils.isEmpty(cachePath) && PhotoBrowseView.this.activity != null) {
                        Map codeResult = ImageHelper.detectVirantQRCode(cachePath);
                        if (PhotoBrowseView.this.activity != null && codeResult != null && !codeResult.isEmpty()) {
                            final String code = codeResult.get("text");
                            final String codeType = codeResult.get("type");
                            final String codeName = codeResult.get("name");
                            if (!TextUtils.isEmpty(code)) {
                                PhotoBrowseView.this.activity.runOnUiThread(new Runnable() {
                                    public final void run() {
                                        if (PhotoBrowseView.this.scanTask != null) {
                                            PhotoBrowseView.this.scanTask.a(code);
                                            PhotoBrowseView.this.scanTask.b(codeType);
                                            if (!TextUtils.isEmpty(codeName)) {
                                                PhotoBrowseView.this.scanTask.b = String.format(PhotoBrowseView.this.mScanCodeDescPattern, new Object[]{codeName});
                                            }
                                            PhotoBrowseView.this.scanTask.run();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
        onMenuExposed(showList);
    }

    /* access modifiers changed from: private */
    public void showVideoMenuDialog(List<PhotoMenu> showList) {
        ArrayList menus = new ArrayList();
        for (int index = 0; index < showList.size(); index++) {
            menus.add(new PopMenuItem(showList.get(index).title));
        }
        AUListDialog popDialog = new AUListDialog(menus, (Context) this.activity);
        popDialog.setOnItemClickListener((OnItemClickListener) buildListener(this.photoList.get(this.pageIndex), showList));
        popDialog.setCanceledOnTouchOutside(true);
        popDialog.show();
        onMenuExposed(showList);
    }

    /* access modifiers changed from: private */
    public boolean ignoreVideoSaveMenu(PhotoMenu photoMenu) {
        try {
            if (TextUtils.equals(PhotoMenu.TAG_SAVE, photoMenu.tag)) {
                MultimediaVideoService vs = (MultimediaVideoService) MicroServiceUtil.getMicroService(MultimediaVideoService.class);
                if (vs == null || !vs.isVideoAvailable(this.photoList.get(this.pageIndex).getPhotoPath())) {
                    PhotoLogger.debug(TAG, "Video not in cache.");
                    return true;
                } else if (TextUtils.isEmpty(photoMenu.title)) {
                    photoMenu.title = getResources().getString(R.string.default_save_menu_title);
                }
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /* access modifiers changed from: private */
    public boolean isIgnoreGifSaveMenu(PhotoMenu photoMenu) {
        try {
            if (TextUtils.equals(PhotoMenu.TAG_SAVE, photoMenu.tag)) {
                PhotoInfo pi = this.photoList.get(this.pageIndex);
                if (pi.isGif() && TextUtils.isEmpty(PhotoUtil.getMediaCachePath(pi))) {
                    PhotoLogger.debug(TAG, "");
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private a buildListener(PhotoInfo info, List<PhotoMenu> menus) {
        switch (info.getMediaType()) {
            case 0:
            case 1:
            case 2:
                return new a(menus);
            default:
                PhotoLogger.warn((String) TAG, (String) "buildListener():mediaType exception!");
                return null;
        }
    }

    /* access modifiers changed from: private */
    public void processMenu(PhotoMenu photoMenu) {
        if (this.photoList == null || this.photoList.isEmpty() || this.photoContext == null) {
            PhotoLogger.warn((String) TAG, (String) "processMenu called when photoList is Empty!");
            return;
        }
        PhotoItem photoInfo = this.photoList.get(this.pageIndex);
        boolean processed = this.photoContext.photoLongPressMenuClick(this.activity, photoInfo, photoMenu);
        if (this.photoPagerListener instanceof V4) {
            processed |= ((V4) this.photoPagerListener).onMenuClicked(photoMenu, photoInfo, (PhotoPreview) this.vpPhoto.findViewWithTag(Integer.valueOf(this.pageIndex)));
        } else if (this.photoPagerListener instanceof PhotoPagerListener.V2) {
            processed |= ((PhotoPagerListener.V2) this.photoPagerListener).onMenuClicked(photoMenu);
        }
        if (processed) {
            PhotoLogger.debug(TAG, "press listener processed");
        } else if (!photoMenu.enableImpl) {
            PhotoLogger.debug(TAG, "default impl not enabled for " + photoMenu.tag);
        } else if (PhotoMenu.TAG_SAVE.equals(photoMenu.tag)) {
            doSaveMedia(photoInfo);
        } else if (PhotoMenu.TAG_SCAN_QR.equals(photoMenu.tag)) {
            ImageHelper.processQRCode(photoMenu.extra, getSourceAppId(), photoMenu.extra2);
        } else if ("delete".equals(photoMenu.tag)) {
            deletePage();
        }
    }

    private void doSaveMedia(PhotoItem photoInfo) {
        Drawable drawable;
        PhotoView photoView = ((PhotoPreview) this.vpPhoto.findViewWithTag(Integer.valueOf(this.pageIndex))).getPhotoView();
        String str = this.saveFolder;
        if (photoView == null) {
            drawable = null;
        } else {
            drawable = photoView.getDrawable();
        }
        boolean result = PhotoUtil.savePhoto(photoInfo, str, drawable);
        if (!result) {
            PhotoUtil.reportSaveFailed();
        }
        AUToast.makeToast(getContext(), 0, (CharSequence) result ? getContext().getString(R.string.str_save_to_album) : getContext().getString(photoInfo.isVideo() ? R.string.save_video_failed : R.string.save_photo_failed), 0).show();
        notifyAction(photoInfo.getPhotoPath(), photoInfo.getMediaType(), "saveMediaFile", this.pageIndex, result);
    }

    private String getSourceAppId() {
        String appId = this.mSourceAppId;
        if (!TextUtils.isEmpty(this.mSourceAppId)) {
            return appId;
        }
        ActivityApplication app = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopApplication();
        if (app != null) {
            appId = app.getAppId();
        }
        return appId;
    }

    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
        if (this.photoList != null) {
            PhotoItem photoInfo = this.photoList.get(this.pageIndex);
            int selectedCount = this.selectedList.size();
            if (view.equals(this.cbOrigin)) {
                this.photoContext.userOriginPhoto = isChecked;
                if (selectedCount == 0) {
                    this.cbSelected.setChecked(true);
                } else {
                    updateContent();
                }
            } else if (view.equals(this.cbSelected) || view.equals(this.cbSelectTop)) {
                boolean selected = photoInfo.getSelected();
                if (isChecked && selected) {
                    return;
                }
                if (isChecked || selected) {
                    if (!isChecked || selected) {
                        photoInfo.setSelected(false);
                        this.selectedList.remove(photoInfo);
                    } else if (selectedCount >= this.maxSelect) {
                        this.cbSelected.setChecked(false);
                        AUToast.makeToast(getContext(), 0, (CharSequence) this.maxSelectMsg, 0).show();
                        return;
                    } else if (PhotoSelectActivity.isGifAndCantSelect(getContext(), this.enableGifBrowse, photoInfo, maxGifSizeCanSend, maxGifPixelCanSend)) {
                        this.cbSelected.setChecked(false);
                        return;
                    } else {
                        photoInfo.setSelected(true);
                        this.selectedList.add(photoInfo);
                    }
                    photoInfo.setSelected(isChecked);
                    updateContent();
                }
            }
        }
    }

    private void updatePhotoText(PhotoItem photoInfo) {
        boolean emptyText;
        int i;
        boolean showDivider;
        int i2;
        int i3;
        int i4 = 8;
        this.leftText = photoInfo.getLeftText();
        this.rightText = photoInfo.getRightText();
        if (!TextUtils.isEmpty(this.leftText) || !TextUtils.isEmpty(this.rightText)) {
            emptyText = false;
        } else {
            emptyText = true;
        }
        View view = this.bottomText;
        if (emptyText) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        if (TextUtils.isEmpty(this.leftText) || TextUtils.isEmpty(this.rightText)) {
            showDivider = false;
        } else {
            showDivider = true;
        }
        TextView textView = this.tvTextDivider;
        if (showDivider) {
            i2 = 0;
        } else {
            i2 = 8;
        }
        textView.setVisibility(i2);
        this.tvLeft.setText(this.leftText);
        this.tvLeft.scrollTo(0, 0);
        TextView textView2 = this.tvLeft;
        if (TextUtils.isEmpty(this.leftText)) {
            i3 = 8;
        } else {
            i3 = 0;
        }
        textView2.setVisibility(i3);
        this.tvRight.setText(this.rightText);
        this.tvRight.scrollTo(0, 0);
        TextView textView3 = this.tvRight;
        if (!TextUtils.isEmpty(this.rightText)) {
            i4 = 0;
        }
        textView3.setVisibility(i4);
    }

    public void deleteGroup(int groupIndex) {
        PhotoLogger.debug(TAG, "deleteGroup:" + groupIndex);
        if (this.photoGroups != null && groupIndex >= 0 && groupIndex < this.photoGroups.size()) {
            if (!this.isActivityResumed) {
                this.isCalledBeforeResume = true;
            }
            PhotoGroup removedPhotoGroup = this.photoGroups.get(groupIndex);
            int removedOffset = removedPhotoGroup.getOffset();
            int removedSize = removedPhotoGroup.getPhotoInfoList().size();
            Iterator iterator = this.photoList.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                PhotoItem photoItem = iterator.next();
                if (i >= removedOffset && i < removedOffset + removedSize) {
                    iterator.remove();
                } else if (i >= removedOffset + removedSize) {
                    photoItem.setPhotoGroupIndex(photoItem.getPhotoGroupIndex() - 1);
                }
                i++;
            }
            for (int j = groupIndex + 1; j < this.photoGroups.size(); j++) {
                PhotoGroup photoGroup = this.photoGroups.get(j);
                photoGroup.setOffset(photoGroup.getOffset() - removedSize);
            }
            this.photoGroups.remove(groupIndex);
            this.pagerAdapter.notifyDataSetChanged();
            this.vpPhoto.setAdapter(this.pagerAdapter);
            int count = this.photoList.size();
            this.dotIndicator.setCount(count);
            if (count == 0) {
                toggleFinish();
                return;
            }
            if (groupIndex == this.photoGroups.size()) {
                this.pageIndex = this.photoGroups.get(this.photoGroups.size() - 1).getOffset();
            } else {
                this.pageIndex = this.photoGroups.get(groupIndex).getOffset();
            }
            this.focusedIndexRecord = -1;
            this.vpPhoto.setCurrentItem(this.pageIndex);
            if (this.pageIndex == 0) {
                onPageSelected(this.pageIndex);
            }
        }
    }

    public void deletePage() {
        PhotoItem pi = this.photoList.get(this.pageIndex);
        this.photoContext.photoList.remove(pi);
        this.photoList.remove(this.pageIndex);
        PhotoLogger.debug(TAG, "deletePage index = " + this.pageIndex);
        if (pi != null) {
            notifyAction(pi.getPhotoPath(), pi.getMediaType(), "deleteMediaFile", this.pageIndex, true);
        }
        this.focusedIndexRecord = -1;
        this.vpPhoto.setAdapter(this.pagerAdapter);
        int count = this.photoList.size();
        this.dotIndicator.setCount(count);
        if (count == 0) {
            toggleFinish();
            return;
        }
        if (this.pageIndex == count) {
            this.pageIndex--;
        }
        this.vpPhoto.setCurrentItem(this.pageIndex);
        if (this.pageIndex == 0) {
            onPageSelected(0);
        }
    }

    private void notifyAction(String photoPath, int type, String deleteMediaFile, int pageIndex2, boolean success) {
        Map extra = new HashMap();
        extra.put("mediaPath", photoPath);
        extra.put("mediaType", (type > 0 ? 1 : 0));
        extra.put("success", String.valueOf(success));
        StreamPlayCon.broadCastMediaEvent(deleteMediaFile, pageIndex2, extra);
    }

    public void onGlobalLayout() {
        this.topBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        this.viewWidth = this.vpPhoto.getMeasuredWidth();
        this.viewHeight = this.vpPhoto.getMeasuredHeight();
        PhotoLogger.debug(TAG, "photoViewWidth " + this.viewWidth + " photoViewHeight " + this.viewHeight);
        onPageSelected(this.previewPosition);
    }

    private void onOriginLoad(LoadInfo info) {
        PhotoItem photoInfo = this.photoList.get(this.pageIndex);
        if (photoInfo != null && photoInfo == info.photoItem) {
            this.pbShowOrigin.setText(getResources().getString(R.string.finished));
            this.rlShowOrigin.postDelayed(new Runnable() {
                public final void run() {
                    PhotoBrowseView.this.rlShowOrigin.setVisibility(8);
                    PhotoUtil.startAnimation(PhotoBrowseView.this.rlShowOrigin, R.anim.fade_out);
                }
            }, 500);
        }
    }

    /* access modifiers changed from: private */
    public void onLoadFailedV2(LoadInfo info, APImageDownloadRsp rsp) {
        printFailMsg(rsp);
        APImageRetMsg retMsg = rsp.getRetmsg();
        if (retMsg == null || retMsg.getCode() != RETCODE.REUSE) {
            String specialToast = null;
            if (retMsg != null && retMsg.getCode() == RETCODE.CURRENT_LIMIT) {
                specialToast = getContext().getString(R.string.current_limit);
            }
            onLoadFailed_(info, specialToast);
            return;
        }
        PhotoLogger.debug(TAG, "Reuse fail ####");
        if (loadOriginalPhotoAgain(info)) {
            PhotoLogger.debug(TAG, "Reuse fail,but perform load original photo again.");
        } else if (info.getPhotoPreview() == null) {
            PhotoLogger.debug(TAG, "Remove loadInfo when no view is related.");
            this.loadMap.remove(info.photoItem);
        } else {
            PhotoLogger.debug(TAG, "Ignore Reuse fail ####");
        }
    }

    private boolean loadOriginalPhotoAgain(LoadInfo info) {
        if (info.loadingOrigin && info.progress >= 100) {
            PhotoPreview pv = info.getPhotoPreview();
            if (pv == null) {
                int index = this.photoList.indexOf(info.photoItem);
                if (index >= 0) {
                    pv = (PhotoPreview) this.itemMap.get(String.valueOf(index));
                }
            }
            if (pv != null) {
                info.setPhotoPreview(pv);
                ImageHelper.load((ImageView) pv.getPhotoView(), info.photoItem.getPhotoPath(), pv.getPhotoView().getDrawable(), -1, -1, info);
                return true;
            }
        }
        return false;
    }

    private void printFailMsg(APImageDownloadRsp rsp) {
        String info = "file at :" + rsp.getSourcePath() + "\n---> fail msg :";
        APImageRetMsg r1 = rsp.getRetmsg();
        String info2 = info + (r1 != null ? "code = " + r1.getCode() + "," + r1.getMsg() : "");
        APImageRetMsg r2 = rsp.originalRetMsg;
        PhotoLogger.debug(TAG, info2 + (r2 != null ? "code = " + r2.getCode() + "," + r2.getMsg() : ""));
    }

    /* access modifiers changed from: private */
    public void onLoadProgress_(LoadInfo info, int current, int total) {
        if (info != null) {
            info.loading = true;
            info.progress = current;
            updatePieProgress(info);
            if (!info.loadingOrigin) {
                PhotoLogger.debug(TAG, "not loading origin image");
                return;
            }
            try {
                if (info.photoItem != this.photoList.get(this.pageIndex)) {
                    PhotoLogger.debug(TAG, "This loadInfo`s target view is not in Foreground.");
                } else {
                    this.pbShowOrigin.setText(current + "%").setProgress(current);
                }
            } catch (IndexOutOfBoundsException e) {
                PhotoLogger.debug(TAG, "onLoadProgress_ get photo at " + this.pageIndex + " exception!PhotoList may changed already.");
            }
        }
    }

    private void updatePieProgress(LoadInfo info) {
        if (this.isShowPhotoExactlyProgress && info != null) {
            PhotoPreview pv = info.getPhotoPreview();
            if (pv != null) {
                pv.setProgress(info.progress);
            } else {
                PhotoLogger.debug(TAG, "ignore pie progress," + info.progress);
            }
        }
    }

    /* access modifiers changed from: private */
    public void onLoadComplete_(LoadInfo info) {
        float largeWidth;
        float largeHeight;
        PhotoLogger.debug(TAG, "onLoadComplete_, path:" + info.toString());
        LoadInfo loadInfo = this.loadMap.remove(info.photoItem);
        checkNeedUpdateShowOrigin(loadInfo);
        if (loadInfo != null && loadInfo.getPhotoPreview() != null) {
            loadInfo.loading = false;
            PhotoPreview photoPreview = loadInfo.getPhotoPreview();
            photoPreview.dismissProgress();
            if (loadInfo.loadingOrigin) {
                PhotoLogger.debug(TAG, "rect width:" + photoPreview.getPhotoView().getPhotoRect().width() + ",rect height:" + photoPreview.getPhotoView().getPhotoRect().height());
                if (photoPreview.getPhotoView().getPhotoRect().width() <= 0.0f || photoPreview.getPhotoView().getPhotoRect().height() <= 0.0f) {
                    largeWidth = (float) photoPreview.getPhotoView().getWidth();
                    largeHeight = (float) photoPreview.getPhotoView().getHeight();
                } else {
                    largeWidth = photoPreview.getPhotoView().getPhotoRect().width();
                    largeHeight = photoPreview.getPhotoView().getPhotoRect().height();
                }
                if (loadInfo.thumbWidth > 0 && loadInfo.thumbHeight > 0 && largeHeight > 0.0f && largeWidth > 0.0f) {
                    float ration = Math.max(((float) loadInfo.thumbWidth) / largeWidth, ((float) loadInfo.thumbHeight) / largeHeight);
                    if (ration < 1.0f) {
                        ScaleAnimation animation = new ScaleAnimation(ration, 1.0f, ration, 1.0f, 1, 0.5f, 1, 0.5f);
                        animation.setDuration(300);
                        photoPreview.getPhotoView().startAnimation(animation);
                    }
                }
                onOriginLoad(info);
            }
        }
    }

    private void checkNeedUpdateShowOrigin(LoadInfo info) {
        if (info != null) {
            PhotoItem pi = info.photoItem;
            if (pi != null) {
                PhotoLogger.debug(TAG, "mark path:" + pi.getPhotoPath() + " has loaded");
                pi.isLoadedOnce = true;
                if (pi.isDisabledByIntercept && this.photoList.get(this.pageIndex) == pi) {
                    PhotoLogger.debug(TAG, "When \"updateShowOrigin\" isDisabledByIntercept ,Call \"updateShowOrigin()\" again.");
                    updateShowOrigin(info.photoItem);
                    return;
                }
                return;
            }
            PhotoLogger.debug(TAG, "Photoitem should not be null!");
        }
    }

    /* access modifiers changed from: private */
    public void onLoadCanceled_(LoadInfo info) {
        onLoadFailed_(info, null);
    }

    private void onLoadFailed_(LoadInfo info, String toastString) {
        LoadInfo loadInfo = this.loadMap.remove(info.photoItem);
        if (loadInfo != null && loadInfo.getPhotoPreview() != null) {
            PhotoItem photoInfo = loadInfo.photoItem;
            PhotoPreview photoPreview = loadInfo.getPhotoPreview();
            photoPreview.dismissProgress();
            if (photoPreview.getPhotoView().isShowingThumbnail()) {
                PhotoLogger.debug(TAG, "Thumbnail is showing when load failed,do nothing but toast.");
            } else {
                Drawable fail = photoInfo.getFail();
                if (fail != null) {
                    photoPreview.getPhotoView().failDrawableHashCode = fail.hashCode();
                    photoPreview.getPhotoView().setImageDrawable(fail);
                } else {
                    Drawable failD = getResources().getDrawable(R.drawable.load_fail);
                    photoPreview.getPhotoView().failDrawableHashCode = failD.hashCode();
                    photoPreview.getPhotoView().setImageDrawable(failD);
                }
            }
            if (this.photoList.get(this.pageIndex) == photoInfo) {
                String toToast = getResources().getString(R.string.download_fail);
                if (!TextUtils.isEmpty(toastString)) {
                    toToast = toastString;
                }
                AUToast.makeToast(getContext(), 0, (CharSequence) toToast, 0).show();
            }
        }
    }

    private void onMenuExposed(List<PhotoMenu> menus) {
        if (menus != null && !menus.isEmpty()) {
            for (PhotoMenu m : menus) {
                if (!TextUtils.isEmpty(m.spmID)) {
                    SpmTracker.expose(getContext(), m.spmID, m.bizCode, m.spmExtra);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void onMenuClicked(PhotoMenu m) {
        if (!TextUtils.isEmpty(m.spmID)) {
            SpmTracker.click(getContext(), m.spmID, m.bizCode, m.spmExtra);
        }
    }
}
