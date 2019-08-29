package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.data.GridAdapter.OnGridListener;
import com.alipay.mobile.beehive.photo.data.PhotoItem;
import com.alipay.mobile.beehive.photo.ui.PhotoSelectActivity;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class PhotoGrid extends SquareGrid implements OnClickListener {
    private static final String EMPTY_STRING = "";
    private static Size REORDED_SIZE = null;
    public static final String TAG = "PhotoGrid";
    private ImageView cameraIv;
    private CheckBox cbSelected;
    private boolean checkable;
    private Drawable defaultPhoto;
    private View gifIndicator;
    private OnGridListener gridListener;
    private boolean isEnableVideoEdit;
    private ImageView ivPhoto;
    private View llCamera;
    private String photoDescPattern;
    private PhotoItem photoInfo;
    private int position;
    private RelativeLayout rlSelect;
    private String selected;
    /* access modifiers changed from: private */
    public TextView tvSelection;
    private String unSelected;
    private String videoDescPattern;
    private TextView videoTimeTv;

    public PhotoGrid(Context context) {
        this(context, null);
        initDescStrs(context);
    }

    private void initDescStrs(Context context) {
        this.selected = context.getString(R.string.checkbox_selected);
        this.unSelected = context.getString(R.string.checkbox_unSelected);
        this.photoDescPattern = context.getString(R.string.str_photo_desc);
        this.videoDescPattern = context.getString(R.string.str_video_desc);
    }

    public void setDefaultDrawable(Drawable defaultDrawable) {
        this.defaultPhoto = defaultDrawable;
    }

    public PhotoGrid(Context context, AttributeSet as) {
        super(context, as);
        this.checkable = true;
        if (REORDED_SIZE == null) {
            REORDED_SIZE = PhotoUtil.reorderSize(PhotoUtil.dp2px(getContext(), 125));
        }
        initDescStrs(context);
    }

    public void setPhotoInfo(PhotoItem info, int position2) {
        this.position = position2;
        if (this.photoInfo == null || info != this.photoInfo) {
            this.photoInfo = info;
            updateGrid();
            if (this.photoInfo.takePhoto) {
                this.ivPhoto.setContentDescription("");
            } else if (processVideo(info)) {
                addGridContentDesc(info, position2, true);
            } else {
                loadPhotoThumb(info);
                addGridContentDesc(info, position2, false);
            }
        }
    }

    private void addGridContentDesc(PhotoItem info, int position2, boolean isVideo) {
        String checkState = this.rlSelect.getVisibility() == 0 ? this.cbSelected.isChecked() ? this.selected : this.unSelected : "";
        String desc = buildDesc(position2 + 1, info, checkState, isVideo);
        if (isVideo) {
            desc = desc + "，" + this.videoTimeTv.getText();
        }
        this.ivPhoto.setContentDescription(desc);
    }

    private String buildDesc(int index, PhotoItem info, String selectStatusDesc, boolean isVideo) {
        if (isVideo) {
            try {
                String str = this.videoDescPattern;
                Object[] objArr = new Object[3];
                objArr[0] = Integer.valueOf(index);
                objArr[1] = TextUtils.isEmpty(info.modifyTimeDesc) ? "，" : info.modifyTimeDesc;
                objArr[2] = selectStatusDesc;
                return String.format(str, objArr);
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().warn((String) TAG, "buildDesc exception." + e.getMessage());
                return "";
            }
        } else {
            String str2 = this.photoDescPattern;
            Object[] objArr2 = new Object[3];
            objArr2[0] = Integer.valueOf(index);
            objArr2[1] = TextUtils.isEmpty(info.modifyTimeDesc) ? "，" : info.modifyTimeDesc;
            objArr2[2] = selectStatusDesc;
            return String.format(str2, objArr2);
        }
    }

    private void loadPhotoThumb(PhotoItem info) {
        if (info.getPhoto() != null) {
            this.ivPhoto.setImageDrawable(info.getPhoto());
        } else if (info.getThumb() != null) {
            this.ivPhoto.setImageDrawable(info.getThumb());
        } else {
            String photoPath = info.getPhotoPath();
            if (TextUtils.isEmpty(photoPath)) {
                Log.e(TAG, "no valid path found!");
                return;
            }
            if (this.defaultPhoto == null) {
                this.defaultPhoto = getResources().getDrawable(R.drawable.default_photo);
            }
            ImageHelper.loadWidthThumbnailPath(this.ivPhoto, photoPath, this.defaultPhoto, REORDED_SIZE.getWidth(), REORDED_SIZE.getWidth(), PhotoUtil.getPhotoSize(info), info.getThumbPath(), info.getMediaId(), info.getIsAlbumMedia());
        }
    }

    private boolean processVideo(PhotoItem info) {
        final String photoPath = info.getPhotoPath();
        if (this.photoInfo.isVideo()) {
            this.cameraIv.setVisibility(0);
            if (1 == info.getMediaType()) {
                this.videoTimeTv.setVisibility(8);
                this.cameraIv.setImageResource(R.drawable.eye);
            } else {
                this.videoTimeTv.setVisibility(0);
                this.cameraIv.setImageResource(R.drawable.camera);
            }
            this.videoTimeTv.setText(PhotoUtil.formatDuration(this.photoInfo.getVideoDuration()));
            MultimediaVideoService mlvs = (MultimediaVideoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaVideoService.class.getName());
            if (this.defaultPhoto == null) {
                this.defaultPhoto = getResources().getDrawable(R.drawable.default_photo);
            }
            mlvs.loadAlbumVideo(photoPath, this.ivPhoto, Integer.valueOf(REORDED_SIZE.getWidth()), Integer.valueOf(REORDED_SIZE.getWidth()), this.defaultPhoto, new APImageDownLoadCallback() {
                public final void onSucc(APImageDownloadRsp arg0) {
                }

                public final void onProcess(String arg0, int arg1) {
                }

                public final void onError(APImageDownloadRsp arg0, Exception e) {
                    PhotoLogger.error((String) PhotoGrid.TAG, photoPath + "视频缩略图加载失败" + e.getMessage());
                }
            }, ImageHelper.getBusinessId());
            return true;
        }
        this.cameraIv.setVisibility(8);
        this.videoTimeTv.setVisibility(8);
        return false;
    }

    public void setGridListener(OnGridListener listener) {
        this.gridListener = listener;
    }

    public void setCheckable(boolean checkable2) {
        this.checkable = checkable2;
    }

    public void setEnableVideoEdit(boolean enable) {
        this.isEnableVideoEdit = enable;
    }

    public boolean isChecked() {
        return this.cbSelected.isChecked();
    }

    public void setChecked(boolean checked) {
        setGridCheckStatus(checked);
    }

    private void setGridCheckStatus(boolean checked) {
        this.cbSelected.setChecked(checked);
        updateContentDesc(checked);
        this.tvSelection.setVisibility(checked ? 0 : 8);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.tvSelection = (TextView) findViewById(R.id.tv_cover);
        this.llCamera = findViewById(R.id.ll_camera);
        this.llCamera.setOnClickListener(this);
        this.cbSelected = (CheckBox) findViewById(R.id.cb_selected);
        this.ivPhoto = (ImageView) findViewById(R.id.iv_photo);
        this.ivPhoto.setOnClickListener(this);
        this.rlSelect = (RelativeLayout) findViewById(R.id.rl_select);
        this.rlSelect.setOnClickListener(this);
        this.cameraIv = (ImageView) findViewById(R.id.cameraIv);
        this.videoTimeTv = (TextView) findViewById(R.id.videoTimeTv);
        if (PhotoSelectActivity.selectGif && PhotoSelectActivity.enableGifDynamicPreview) {
            this.gifIndicator = ((ViewStub) findViewById(R.id.gif_indicator_stub)).inflate();
        }
    }

    private void setGifIndicatorVisibility(int visibility) {
        if (this.gifIndicator != null) {
            this.gifIndicator.setVisibility(visibility);
        }
    }

    public void updateGrid() {
        int cameraVisibility;
        boolean isVisible;
        int i;
        int photoVisibility;
        int i2 = 0;
        if (this.photoInfo.takePhoto) {
            cameraVisibility = 0;
        } else {
            cameraVisibility = 8;
        }
        this.llCamera.setVisibility(cameraVisibility);
        if (cameraVisibility == 0) {
            this.llCamera.setTag(Integer.valueOf(0));
        }
        if (!this.checkable || this.photoInfo.takePhoto) {
            isVisible = false;
        } else {
            isVisible = true;
        }
        if (isVisible && this.photoInfo.isVideo() && this.isEnableVideoEdit) {
            isVisible = false;
        }
        RelativeLayout relativeLayout = this.rlSelect;
        if (isVisible) {
            i = 0;
        } else {
            i = 8;
        }
        relativeLayout.setVisibility(i);
        if (this.photoInfo.takePhoto) {
            photoVisibility = 8;
        } else {
            photoVisibility = 0;
        }
        this.ivPhoto.setVisibility(photoVisibility);
        setChecked(this.photoInfo.getSelected());
        if (!this.photoInfo.isVideo()) {
            this.cameraIv.setVisibility(8);
            this.videoTimeTv.setVisibility(8);
        }
        if (!this.photoInfo.isGif()) {
            i2 = 8;
        }
        setGifIndicatorVisibility(i2);
    }

    public void onClick(View view) {
        if (view.equals(this.rlSelect)) {
            setGridCheckStatus(!this.cbSelected.isChecked());
            if (this.gridListener != null) {
                this.gridListener.onGridChecked(this, this.position);
            }
        } else if ((view.equals(this.ivPhoto) || view.equals(this.llCamera)) && this.gridListener != null) {
            this.gridListener.onGridClick(this, this.position);
        }
    }

    private void updateContentDesc(boolean isChecked) {
        String currentSelectedStatus;
        if (isChecked) {
            try {
                currentSelectedStatus = this.selected;
            } catch (Exception e) {
                PhotoLogger.warn((String) TAG, (String) "Should not be here");
                return;
            }
        } else {
            currentSelectedStatus = this.unSelected;
        }
        String target2Replace = isChecked ? this.unSelected : this.selected;
        String desc = (String) this.ivPhoto.getContentDescription();
        if (!TextUtils.isEmpty(desc)) {
            this.ivPhoto.setContentDescription(desc.replace(target2Replace, currentSelectedStatus));
        }
    }

    public void animateSelection() {
        this.tvSelection.setVisibility(0);
        Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(1200);
        this.tvSelection.startAnimation(fadeOut);
        this.tvSelection.postDelayed(new Runnable() {
            public final void run() {
                PhotoGrid.this.tvSelection.setVisibility(8);
            }
        }, 1000);
    }
}
