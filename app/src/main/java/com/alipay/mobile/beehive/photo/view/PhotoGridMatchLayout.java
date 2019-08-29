package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoConfig;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.service.PhotoInfo;
import java.util.List;

public class PhotoGridMatchLayout extends FrameLayout {
    /* access modifiers changed from: private */
    public static final String TAG = PhotoGridMatchLayout.class.getSimpleName();
    private boolean configShowEdit = true;
    private ViewStub mEditStub;
    private View mEditView;
    private PhotoMatchLayout mPhotoMatchLayout;
    private boolean showEdit = false;

    public interface OnEditClickListener {
        void onClick(int i, View view);
    }

    public interface OnItemClickListener {
        void onItemClick(int i, View view, boolean z, PhotoGridMatchLayout photoGridMatchLayout);

        void onItemLongClick(int i, View view, boolean z, PhotoGridMatchLayout photoGridMatchLayout);
    }

    public PhotoGridMatchLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public PhotoGridMatchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PhotoGridMatchLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_photo_grid_match, this, true);
        this.mPhotoMatchLayout = (PhotoMatchLayout) findViewById(R.id.photo_match_layout);
        this.mEditStub = (ViewStub) findViewById(R.id.viewstub_edit);
        this.configShowEdit = PhotoConfig.getInstance().getShowInEditConfig();
    }

    @Deprecated
    public void setImagesData(List<? extends PhotoInfo> lists, String businessId) {
        setImagesData(lists, businessId, null);
    }

    public void setImagesData(List<? extends PhotoInfo> lists, String businessId, String bizType) {
        this.mPhotoMatchLayout.setImagesData(lists, businessId, bizType);
        if (this.mEditView == null) {
            return;
        }
        if (!this.configShowEdit || !this.showEdit || lists == null || lists.size() != 1 || lists.get(0) == null || !PhotoUtil.isNativePhoto(((PhotoInfo) lists.get(0)).getPhotoPath())) {
            this.mEditView.setVisibility(8);
        } else {
            this.mEditView.setVisibility(0);
        }
    }

    public void setDefaultDrawableID(int defaultDrawableID) {
        if (this.mPhotoMatchLayout != null) {
            this.mPhotoMatchLayout.setDefaultDrawableID(defaultDrawableID);
        }
    }

    public void setOptions(DisplayImageOptions options) {
        if (this.mPhotoMatchLayout != null) {
            this.mPhotoMatchLayout.setOptions(options);
        }
    }

    public void setOnItemClickListener(final OnItemClickListener listener) {
        this.mPhotoMatchLayout.setOnGirdItemClickListener(new OnGridItemClickListener() {
            public final void onItemClick(int position, View view, boolean hasMore) {
                if (listener != null) {
                    listener.onItemClick(position, view, hasMore, null);
                }
            }

            public final void onItemLongClick(int position, View view, boolean hasMore) {
                if (listener != null) {
                    listener.onItemLongClick(position, view, hasMore, null);
                }
            }
        });
    }

    public void setShowEdit(boolean isShow, final OnEditClickListener listener) {
        this.showEdit = isShow;
        if (isShow && this.configShowEdit) {
            this.mEditView = this.mEditStub.inflate();
            this.mEditView.setOnClickListener(new NoMultiClickListener() {
                public final void onNoMultiClick(View v) {
                    PhotoLogger.debug(PhotoGridMatchLayout.TAG, "mEditView , onNoMultiClick()");
                    if (listener != null) {
                        listener.onClick(0, v);
                    }
                }
            });
        }
    }

    public void startDynamicPreview(Bundle param) {
        PhotoLogger.debug(TAG, "startDynamicPreview");
        this.mPhotoMatchLayout.startDynamicPreview(param);
    }

    public void pauseDynamicPreview() {
        PhotoLogger.debug(TAG, "pauseDynamicPreview");
        this.mPhotoMatchLayout.pauseDynamicPreview();
    }

    public void resetDynamicPreview() {
        PhotoLogger.debug(TAG, "resetDynamicPreview");
        this.mPhotoMatchLayout.resetDynamicPreview(true);
    }

    public int getOneVideoLeftRightMargin() {
        return (int) (((float) GridMatchBaseLayout.getScreenWidth(getContext())) * 0.033333335f);
    }
}
