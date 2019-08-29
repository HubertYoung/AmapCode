package com.alipay.mobile.beehive.photo.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import com.alipay.android.phone.falcon.falconimg.layout.CellDetail;
import com.alipay.android.phone.falcon.falconimg.layout.SmartCalcLayout;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions.Builder;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.antui.dialog.AUListDialog;
import com.alipay.mobile.antui.dialog.AUListDialog.OnItemClickListener;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.service.CaptureListener;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.CaptureService;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseActivity;
import com.alipay.mobile.beehive.imageedit.constant.Constants;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.alipay.mobile.beehive.imageedit.service.InImageEditListener;
import com.alipay.mobile.beehive.photo.util.CommonUtils;
import com.alipay.mobile.beehive.photo.util.PhotoConfig;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.view.NoMultiClickListener;
import com.alipay.mobile.beehive.photo.view.TextureSizeRecognizeImageView;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoSelectListener;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.beehive.service.impl.PhotoServiceImpl;
import com.alipay.mobile.beehive.service.session.BrowsePhotoAsListSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BrowsePhotoAsListActivity extends BeehiveBaseActivity implements OnClickListener {
    private static final String FILE_PREFIX = "file:";
    private static final String TAG = "BrowsePhotoAsListActivity";
    /* access modifiers changed from: private */
    public boolean configShowEdit = true;
    private ListView lvPhotos;
    /* access modifiers changed from: private */
    public a mAdapter = new a();
    private String mBizType;
    /* access modifiers changed from: private */
    public String mBusinessId;
    /* access modifiers changed from: private */
    public CaptureListener mCaptureListener = new CaptureListener() {
        public final void onAction(boolean isCanceled, MediaInfo mediaInfo) {
            if (!isCanceled && mediaInfo != null) {
                BrowsePhotoAsListActivity.this.mPhotos.add(CommonUtils.covertMediaInfo2PhotoInfo(mediaInfo));
                BrowsePhotoAsListActivity.this.mAdapter.notifyDataSetChanged();
                BrowsePhotoAsListActivity.this.updateAddBtn();
            }
        }
    };
    /* access modifiers changed from: private */
    public CaptureService mCaptureService;
    /* access modifiers changed from: private */
    public boolean mCountLocalPhotoOnly;
    private Drawable mDefaultDrawable;
    /* access modifiers changed from: private */
    public ImageEditService mEditService;
    private boolean mEnableAdd;
    /* access modifiers changed from: private */
    public boolean mEnableDelete;
    /* access modifiers changed from: private */
    public boolean mEnableEdit;
    private MultimediaImageService mImageService;
    private int mInitScrollTo;
    /* access modifiers changed from: private */
    public int mMaxSelect;
    /* access modifiers changed from: private */
    public Bundle mParams;
    /* access modifiers changed from: private */
    public PhotoSelectListener mPhotoSelectListener = new PhotoSelectListener() {
        public final void onPhotoSelected(List<PhotoInfo> localSelectedPhotos, Bundle bundle) {
            List toMerge = new LinkedList();
            if (BrowsePhotoAsListActivity.this.mPhotos != null && !BrowsePhotoAsListActivity.this.mPhotos.isEmpty()) {
                for (PhotoInfo pi : BrowsePhotoAsListActivity.this.mPhotos) {
                    if (!BrowsePhotoAsListActivity.this.isLocalPhoto(pi.getPhotoPath())) {
                        toMerge.add(pi);
                    }
                }
            }
            BrowsePhotoAsListActivity.this.updateWidthHeightByOrientation(localSelectedPhotos);
            PhotoLogger.debug(BrowsePhotoAsListActivity.TAG, "Remote photo count = " + toMerge.size());
            toMerge.addAll(localSelectedPhotos);
            PhotoLogger.debug(BrowsePhotoAsListActivity.TAG, "Merged photo count = " + toMerge.size());
            PhotoServiceImpl.getCurrentPLBSession().after = toMerge;
            BrowsePhotoAsListActivity.this.mPhotos = PhotoServiceImpl.getCurrentPLBSession().after;
            BrowsePhotoAsListActivity.this.mAdapter.notifyDataSetChanged();
            BrowsePhotoAsListActivity.this.updateAddBtn();
        }

        public final void onSelectCanceled() {
        }
    };
    /* access modifiers changed from: private */
    public PhotoService mPhotoService;
    /* access modifiers changed from: private */
    public List<PhotoInfo> mPhotos;
    /* access modifiers changed from: private */
    public int mScreenHeight;
    /* access modifiers changed from: private */
    public int mScreenWidth;
    /* access modifiers changed from: private */
    public boolean misEnableInPaster;
    private AUTitleBar titleBar;
    private View vFooter;

    class a extends BaseAdapter {
        private OnClickListener b = new OnClickListener() {
            public final void onClick(View v) {
                PhotoInfo pi = ((b) v.getTag()).d;
                BrowsePhotoAsListActivity.this.mPhotos.remove(pi);
                a.this.notifyDataSetChanged();
                BrowsePhotoAsListActivity.this.updateAddBtn();
                PhotoServiceImpl.getCurrentPLBSession().onPhotoDelete(BrowsePhotoAsListActivity.this, pi);
            }
        };
        private OnClickListener c = new NoMultiClickListener() {
            public final void onNoMultiClick(View v) {
                PhotoLogger.debug(BrowsePhotoAsListActivity.TAG, "mOnEditClickListener , onNoMultiClick()");
                if (v.getTag() instanceof b) {
                    final b holder = (b) v.getTag();
                    Map pathMap = new HashMap();
                    pathMap.put("path", holder.d.getPhotoPath());
                    if (!BrowsePhotoAsListActivity.this.misEnableInPaster) {
                        BrowsePhotoAsListActivity.disableInPaster(pathMap);
                    }
                    BrowsePhotoAsListActivity.this.mEditService.editImageUseIn(pathMap, new InImageEditListener() {
                        public final void onResult(boolean isEdited, String outPath, Bitmap outBitmap, Map<String, Object> extParams) {
                            PhotoLogger.debug(BrowsePhotoAsListActivity.TAG, "isEdited : " + isEdited + ", outPath : " + outPath + ", outBitmap : " + outBitmap);
                            if (isEdited && outBitmap != null) {
                                holder.d.setPhotoPath(outPath);
                                holder.d.setPhotoHeight(outBitmap.getHeight());
                                holder.d.setPhotoWidth(outBitmap.getWidth());
                                if (holder.d.extraInfo == null) {
                                    holder.d.extraInfo = new HashMap();
                                }
                                holder.d.extraInfo.put(Constants.KEY_IN_EDITED, Boolean.valueOf(true));
                                LayoutParams params = holder.a.getLayoutParams();
                                Size wh = a.this.a(holder.d, holder.a);
                                params.height = wh.getHeight();
                                params.width = wh.getWidth();
                                holder.a.setLayoutParams(params);
                                holder.a.setImageBitmap(outBitmap);
                            }
                        }
                    });
                }
            }
        };
        private OnClickListener d = new OnClickListener() {
            public final void onClick(View v) {
                b vh = (b) v.getTag();
                BrowsePhotoAsListSession session = PhotoServiceImpl.getCurrentPLBSession();
                if (session != null && !session.onPhotoClick(BrowsePhotoAsListActivity.this, v, BrowsePhotoAsListActivity.this.mPhotos.indexOf(vh.d))) {
                    Bundle params = new Bundle(BrowsePhotoAsListActivity.this.mParams);
                    params.putBoolean(PhotoParam.ENABLE_DELETE, false);
                    params.putBoolean(PhotoParam.PREVIEW_CLICK_EXIT, true);
                    params.putInt(PhotoParam.PREVIEW_POSITION, BrowsePhotoAsListActivity.this.mPhotos.indexOf(vh.d));
                    params.putBoolean(PhotoParam.SHOW_DOT_INDICATOR, true);
                    ((PhotoService) CommonUtils.serviceHolder(PhotoService.class, BrowsePhotoAsListActivity.this.mPhotoService)).browsePhoto(BrowsePhotoAsListActivity.this.mApp, BrowsePhotoAsListActivity.this.mPhotos, params, null);
                }
            }
        };
        private OnLongClickListener e = new OnLongClickListener() {
            public final boolean onLongClick(View v) {
                PhotoServiceImpl.getCurrentPLBSession().onPhotoLongClick(BrowsePhotoAsListActivity.this, v, BrowsePhotoAsListActivity.this.mPhotos.indexOf(((b) v.getTag()).d));
                return true;
            }
        };

        a() {
        }

        public final int getCount() {
            if (BrowsePhotoAsListActivity.this.mPhotos == null) {
                return 0;
            }
            return BrowsePhotoAsListActivity.this.mPhotos.size();
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public PhotoInfo getItem(int position) {
            return (PhotoInfo) BrowsePhotoAsListActivity.this.mPhotos.get(position);
        }

        public final long getItemId(int position) {
            return (long) position;
        }

        public final View getView(int position, View v, ViewGroup parent) {
            if (v == null) {
                v = LayoutInflater.from(BrowsePhotoAsListActivity.this).inflate(R.layout.item_photo, null);
                b vh = new b();
                vh.a = (ImageView) v.findViewById(R.id.iv_photo);
                ImageView ivDelete = (ImageView) v.findViewById(R.id.iv_delete);
                vh.b = ivDelete;
                View tvEdit = v.findViewById(R.id.tv_edit);
                vh.c = tvEdit;
                vh.e = (ImageView) v.findViewById(R.id.iv_gif_logo);
                v.setTag(vh);
                vh.b.setTag(vh);
                vh.b.setVisibility(BrowsePhotoAsListActivity.this.mEnableDelete ? 0 : 8);
                vh.c.setTag(vh);
                v.setOnClickListener(this.d);
                ivDelete.setOnClickListener(this.b);
                tvEdit.setOnClickListener(this.c);
                v.setOnLongClickListener(this.e);
            }
            b vh2 = (b) v.getTag();
            vh2.d = getItem(position);
            FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) vh2.a.getLayoutParams();
            Size wh = a(vh2.d, vh2.a);
            flp.height = wh.getHeight();
            flp.width = wh.getWidth();
            vh2.a.setLayoutParams(flp);
            PhotoInfo pi = vh2.d;
            vh2.e.setVisibility(pi.isGif() ? 0 : 8);
            vh2.c.setVisibility((!PhotoUtil.isNativePhoto(pi.getPhotoPath()) || !BrowsePhotoAsListActivity.this.mEnableEdit || !BrowsePhotoAsListActivity.this.configShowEdit) ? 8 : 0);
            BrowsePhotoAsListActivity.this.loadImage(pi.getPhotoPath(), vh2.a, PhotoUtil.calcViewSize(Math.min(flp.width, 800), ((float) wh.getWidth()) / ((float) wh.getHeight())), pi.bizExtraParams);
            return v;
        }

        /* access modifiers changed from: private */
        public Size a(PhotoInfo info, ImageView imageView) {
            if (info.getPhotoWidth() <= 0 || info.getPhotoHeight() <= 0) {
                PhotoLogger.warn((String) BrowsePhotoAsListActivity.TAG, info.getPhotoPath() + ",width or height <=0");
                imageView.setScaleType(ScaleType.CENTER_CROP);
                return new Size(BrowsePhotoAsListActivity.this.mScreenWidth, BrowsePhotoAsListActivity.this.mScreenWidth);
            }
            CellDetail cd = new SmartCalcLayout().getOnePicLayoutDetail(info.getPhotoWidth(), info.getPhotoHeight(), BrowsePhotoAsListActivity.this.mScreenWidth, BrowsePhotoAsListActivity.this.mScreenHeight);
            imageView.setScaleType(cd.scaleType);
            PhotoLogger.debug(BrowsePhotoAsListActivity.TAG, imageView.getScaleType().toString());
            return new Size(BrowsePhotoAsListActivity.this.mScreenWidth, (int) (cd.height * ((float) BrowsePhotoAsListActivity.this.mScreenWidth)));
        }
    }

    static class b {
        public ImageView a;
        public ImageView b;
        public View c;
        public PhotoInfo d;
        public ImageView e;

        b() {
        }
    }

    /* access modifiers changed from: private */
    public void updateWidthHeightByOrientation(List<PhotoInfo> selectedPhotos) {
        if (selectedPhotos != null && !selectedPhotos.isEmpty()) {
            for (PhotoInfo pi : selectedPhotos) {
                if (pi.getPhotoOrientation() == 90 || pi.getPhotoOrientation() == 270) {
                    int temp = pi.getPhotoHeight();
                    pi.setPhotoHeight(pi.getPhotoWidth());
                    pi.setPhotoWidth(temp);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_browse_photo);
        init();
    }

    private void init() {
        this.mImageService = (MultimediaImageService) CommonUtils.serviceHolder(MultimediaImageService.class, this.mImageService);
        this.mPhotoService = (PhotoService) CommonUtils.serviceHolder(PhotoService.class, this.mPhotoService);
        this.mCaptureService = (CaptureService) CommonUtils.serviceHolder(CaptureService.class, this.mCaptureService);
        this.mEditService = (ImageEditService) CommonUtils.serviceHolder(ImageEditService.class, this.mEditService);
        this.titleBar = (AUTitleBar) findViewById(R.id.title_bar);
        this.lvPhotos = (ListView) findViewById(R.id.lv_photos);
        this.vFooter = LayoutInflater.from(this).inflate(R.layout.view_bottom_add, null);
        this.vFooter.setOnClickListener(this);
        Point wh = new Point();
        getWindowManager().getDefaultDisplay().getSize(wh);
        this.mScreenWidth = wh.x;
        this.mScreenHeight = wh.y;
        this.mDefaultDrawable = getResources().getDrawable(R.drawable.image_place_holder);
        setBundle(getIntent().getExtras());
        this.configShowEdit = PhotoConfig.getInstance().getShowInEditConfig();
    }

    private void setBundle(Bundle params) {
        if (params == null) {
            params = new Bundle();
        }
        this.mParams = params;
        this.misEnableInPaster = this.mParams.getBoolean(PhotoParam.KEY_ENABLE_IN_PASTER, true);
        this.mBusinessId = this.mParams.getString("businessId");
        this.mMaxSelect = this.mParams.getInt(PhotoParam.MAX_SELECT, 9);
        this.mEnableDelete = this.mParams.getBoolean(PhotoParam.BROWSE_AS_LIST_ENABLE_DELETE, false);
        this.mEnableEdit = this.mParams.getBoolean(PhotoParam.BROWSE_AS_LIST_ENABLE_EDIT, false);
        this.mEnableAdd = this.mParams.getBoolean(PhotoParam.BROWSE_AS_LIST_ENABLE_ADD, true);
        this.mInitScrollTo = this.mParams.getInt(PhotoParam.BROWSE_AS_LIST_INIT_SCROLL_TO, 0);
        this.mCountLocalPhotoOnly = this.mParams.getBoolean(PhotoParam.BROWSE_PHOTO_AS_LIST_COUNT_LOCAL_PHOTO_ONLY, false);
        this.mBizType = this.mParams.getString(PhotoParam.MULTI_MEDIA_BIZ_TYPE);
        this.titleBar.setTitleText(getString(R.string.preview));
        this.mPhotos = PhotoServiceImpl.getCurrentPLBSession().after;
        this.lvPhotos.addFooterView(this.vFooter);
        this.lvPhotos.setAdapter(this.mAdapter);
        ((MultimediaImageService) CommonUtils.serviceHolder(MultimediaImageService.class, this.mImageService)).optimizeView(this.lvPhotos, null);
        if (this.mPhotos == null || this.mPhotos.size() < this.mInitScrollTo) {
            PhotoLogger.warn((String) TAG, (String) "InitScrollTo is out of range");
        } else {
            this.lvPhotos.setSelection(this.mInitScrollTo);
        }
        PhotoLogger.debug(TAG, "setBundle: businessId = " + this.mBusinessId + "maxSelect = " + this.mMaxSelect + "enableDelete =" + this.mEnableDelete + "enableAdd = " + this.mEnableAdd + "enableEdit =" + this.mEnableEdit + "initScrollTo = " + this.mInitScrollTo + "bizType = " + this.mBizType);
        updateAddBtn();
    }

    /* access modifiers changed from: private */
    public void updateAddBtn() {
        if (!this.mEnableAdd || (this.mPhotos != null && getValidCount() >= this.mMaxSelect)) {
            if (this.lvPhotos.getFooterViewsCount() > 0) {
                this.lvPhotos.removeFooterView(this.vFooter);
            }
        } else if (this.lvPhotos.getFooterViewsCount() <= 0) {
            this.lvPhotos.addFooterView(this.vFooter);
        }
    }

    private int getValidCount() {
        int ret = 0;
        if (this.mPhotos == null || this.mPhotos.isEmpty()) {
            return 0;
        }
        if (!this.mCountLocalPhotoOnly) {
            return this.mPhotos.size();
        }
        for (int i = 0; i < this.mPhotos.size(); i++) {
            if (isLocalPhoto(this.mPhotos.get(i).getPhotoPath())) {
                ret++;
            }
        }
        return ret;
    }

    public void onClick(View v) {
        if (v == this.vFooter) {
            showAddPhotoDialog();
        }
    }

    private void showAddPhotoDialog() {
        ArrayList list = new ArrayList();
        list.add(new MessagePopItem(getString(R.string.add_by_take_photo)));
        list.add(new MessagePopItem(getString(R.string.select_from_alum)));
        final AUListDialog dialog = new AUListDialog((Context) this, (List<MessagePopItem>) list);
        dialog.setOnItemClickListener((OnItemClickListener) new OnItemClickListener() {
            public final void onItemClick(int index) {
                dialog.dismiss();
                if (index == 0) {
                    Bundle params = new Bundle();
                    params.putInt(CaptureParam.CAPTURE_MODE, 2);
                    ((CaptureService) CommonUtils.serviceHolder(CaptureService.class, BrowsePhotoAsListActivity.this.mCaptureService)).capture(BrowsePhotoAsListActivity.this.mApp, BrowsePhotoAsListActivity.this.mCaptureListener, BrowsePhotoAsListActivity.this.mBusinessId, params);
                    return;
                }
                PhotoService ps = (PhotoService) CommonUtils.serviceHolder(PhotoService.class, BrowsePhotoAsListActivity.this.mPhotoService);
                List selected = new ArrayList();
                int remotePhotoCount = 0;
                if (BrowsePhotoAsListActivity.this.mPhotos != null && !BrowsePhotoAsListActivity.this.mPhotos.isEmpty()) {
                    for (PhotoInfo pi : BrowsePhotoAsListActivity.this.mPhotos) {
                        if (BrowsePhotoAsListActivity.this.isLocalPhoto(pi.getPhotoPath())) {
                            selected.add(pi);
                        }
                    }
                    remotePhotoCount = BrowsePhotoAsListActivity.this.mPhotos.size() - selected.size();
                    PhotoLogger.debug(BrowsePhotoAsListActivity.TAG, "Remote photo count = " + remotePhotoCount);
                }
                Bundle params2 = new Bundle(BrowsePhotoAsListActivity.this.mParams);
                params2.putInt(PhotoParam.MAX_SELECT, BrowsePhotoAsListActivity.this.mCountLocalPhotoOnly ? BrowsePhotoAsListActivity.this.mMaxSelect : BrowsePhotoAsListActivity.this.mMaxSelect - remotePhotoCount);
                params2.putBoolean(PhotoParam.ENABLE_SELECT_ORIGIN, false);
                ps.selectPhotoWithExtraInfoKept(BrowsePhotoAsListActivity.this.mApp, selected, params2, BrowsePhotoAsListActivity.this.mPhotoSelectListener);
            }
        });
        dialog.show();
    }

    /* access modifiers changed from: private */
    public boolean isLocalPhoto(String pathOrUrlOrId) {
        if (!TextUtils.isEmpty(pathOrUrlOrId)) {
            return pathOrUrlOrId.startsWith(FILE_PREFIX) || pathOrUrlOrId.startsWith(File.separator);
        }
        return false;
    }

    public static void disableInPaster(Map<String, Object> pathMap) {
        pathMap.put("editTypes", new String[]{ImageEditService.IN_EDIT_TYPE_CROP, ImageEditService.IN_EDIT_TYPE_ROTATE, ImageEditService.IN_EDIT_TYPE_MAGIC, "text"});
    }

    /* access modifiers changed from: private */
    public void loadImage(String path, ImageView imageView, Size viewSize, Bundle extraParams) {
        int w = Math.min(TextureSizeRecognizeImageView.MAX_TEXTURE_SIZE, viewSize.getWidth());
        int h = Math.min(TextureSizeRecognizeImageView.MAX_TEXTURE_SIZE, viewSize.getHeight());
        PhotoLogger.debug(TAG, "MAX_TEXTURE_SIZE =" + TextureSizeRecognizeImageView.MAX_TEXTURE_SIZE + ",before w = " + viewSize.getWidth() + ", h = " + viewSize.getHeight() + ", after w = " + w + ", h = " + h);
        DisplayImageOptions options = new Builder().imageScaleType(CutScaleType.KEEP_RATIO).width(Integer.valueOf(w)).height(Integer.valueOf(h)).showImageOnLoading(this.mDefaultDrawable).build();
        options.setProgressive(true);
        options.bundle = extraParams;
        if (!TextUtils.isEmpty(this.mBizType)) {
            options.setBizType(this.mBizType);
        }
        ((MultimediaImageService) CommonUtils.serviceHolder(MultimediaImageService.class, this.mImageService)).loadImage(path, imageView, options, (APImageDownLoadCallback) null, this.mBusinessId);
    }

    public void finish() {
        if (!isFinishing()) {
            PhotoLogger.debug(TAG, "Finish browse,at finish first time called.");
            PhotoServiceImpl.getCurrentPLBSession().onBrowseFinish();
            PhotoServiceImpl.finishPhotoListBrowseService();
        } else {
            PhotoLogger.debug(TAG, "Filter duplicate finish Calling");
        }
        super.finish();
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3483";
    }

    public void onConfigurationChanged(Configuration newConfig) {
        PhotoLogger.debug(TAG, "onConfigurationChanged###");
        super.onConfigurationChanged(newConfig);
        pendingToRelayout();
    }

    private void pendingToRelayout() {
        try {
            Point wh = new Point();
            getWindowManager().getDefaultDisplay().getSize(wh);
            if (wh.x != this.mScreenWidth || wh.y != this.mScreenHeight) {
                PhotoLogger.debug(TAG, "pendingToRelayout### reset data.");
                this.mScreenWidth = wh.x;
                this.mScreenHeight = wh.y;
                int p = this.lvPhotos.getFirstVisiblePosition();
                this.lvPhotos.setAdapter(null);
                this.lvPhotos.setAdapter(this.mAdapter);
                this.lvPhotos.setSelection(p);
            }
        } catch (Exception e) {
            PhotoLogger.error((String) TAG, "pendingToRelayout exception :" + e.getMessage());
        }
    }
}
