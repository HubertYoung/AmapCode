package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import java.util.ArrayList;
import java.util.List;

public class PhotoGridLayout extends ViewGroup {
    private static final String TAG = "PhotoGridLayout";
    private int columns;
    private Drawable defaultGridDrawable;
    private int gap = 3;
    private List<PhotoInfo> listData;
    /* access modifiers changed from: private */
    public OnItemClickListener listener;
    private int maxSingleHeight = 300;
    private int maxSingleWidth = 230;
    private int minSingleHeight = 40;
    private int minSingleWidth = 40;
    private boolean needLayout;
    private Size orderedSize;
    private int rows;
    private int singleWidth;

    public interface OnItemClickListener {
        void onItemClick(long j, View view, PhotoGridLayout photoGridLayout);

        void onItemLongClick(long j, View view, PhotoGridLayout photoGridLayout);
    }

    public PhotoGridLayout(Context context) {
        super(context);
        init();
    }

    public PhotoGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.gap = dip2px(getContext(), (float) this.gap);
        this.maxSingleHeight = dip2px(getContext(), (float) this.maxSingleHeight);
        this.maxSingleWidth = dip2px(getContext(), (float) this.maxSingleWidth);
        this.minSingleHeight = dip2px(getContext(), (float) this.minSingleHeight);
        this.minSingleWidth = dip2px(getContext(), (float) this.minSingleWidth);
    }

    public void setSingleImageRegion(int minWidth, int minHeight, int maxWidth, int maxHeight) {
        this.minSingleWidth = dip2px(getContext(), (float) minWidth);
        this.minSingleHeight = dip2px(getContext(), (float) minHeight);
        this.maxSingleWidth = dip2px(getContext(), (float) maxWidth);
        this.maxSingleHeight = dip2px(getContext(), (float) maxHeight);
    }

    public void setOnItemClickListener(OnItemClickListener listener2) {
        this.listener = listener2;
        setChildClickListener();
    }

    public void setDefaultGridDrawable(Drawable drawable) {
        this.defaultGridDrawable = drawable;
    }

    private void setChildClickListener() {
        if (this.listener != null && getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                final int pos = i;
                getChildAt(i).setOnClickListener(new OnClickListener() {
                    public final void onClick(View v) {
                        PhotoGridLayout.this.listener.onItemClick((long) pos, v, PhotoGridLayout.this);
                    }
                });
                getChildAt(i).setOnLongClickListener(new OnLongClickListener() {
                    public final boolean onLongClick(View v) {
                        PhotoGridLayout.this.listener.onItemLongClick((long) pos, v, PhotoGridLayout.this);
                        return false;
                    }
                });
            }
        }
    }

    private int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int singleWidth2 = (width - (this.gap * 2)) / 3;
        if (this.listData != null && this.listData.size() > 1) {
            setMeasuredDimension(width, (this.rows * singleWidth2) + (this.gap * (this.rows - 1)));
        } else if (this.listData != null) {
            LayoutParams layoutParams = getSuggestedLayoutParams(this.listData.get(0));
            setMeasuredDimension(layoutParams.width, layoutParams.height);
        }
        if (this.needLayout) {
            layoutChildrenView();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    private void layoutChildrenView() {
        int childrenCount = this.listData.size();
        int totalWidth = getLayoutParams().width;
        if (totalWidth <= 0) {
            totalWidth = getMeasuredWidth();
        }
        this.singleWidth = (totalWidth - (this.gap * 2)) / 3;
        this.orderedSize = PhotoUtil.reorderSize(PhotoUtil.dp2px(getContext(), 125));
        int singleHeight = this.singleWidth;
        if (childrenCount == 1) {
            CustomImageView childrenView = (CustomImageView) getChildAt(0);
            setSingleImageThumbnail(this.listData.get(0), childrenView);
            childrenView.layout(0, 0, childrenView.getLayoutParams().width, childrenView.getLayoutParams().height);
        } else {
            long preTime = System.currentTimeMillis();
            for (int i = 0; i < childrenCount; i++) {
                CustomImageView childrenView2 = (CustomImageView) getChildAt(i);
                setImageThumbnail(this.listData.get(i), childrenView2);
                int[] position = findPosition(i);
                int left = (this.singleWidth + this.gap) * position[1];
                int top = (this.gap + singleHeight) * position[0];
                childrenView2.layout(left, top, left + this.singleWidth, top + singleHeight);
            }
            PhotoLogger.debug(TAG, "layoutChildren cost: " + childrenCount + " ," + (System.currentTimeMillis() - preTime) + RPCDataParser.TIME_MS);
        }
        this.needLayout = false;
    }

    private void setSingleImageThumbnail(PhotoInfo photoInfo, ImageView imageView) {
        Size viewSize = PhotoUtil.reorderSize(this.maxSingleWidth);
        ImageHelper.load(imageView, photoInfo.getPhotoPath(), this.defaultGridDrawable, viewSize.getWidth(), viewSize.getHeight(), new Size(photoInfo.getPhotoWidth(), photoInfo.getPhotoHeight()));
    }

    private void setImageThumbnail(PhotoInfo photoInfo, ImageView imageView) {
        ImageHelper.load(imageView, photoInfo.getPhotoPath(), this.defaultGridDrawable, this.orderedSize.getWidth(), this.orderedSize.getHeight(), new Size(photoInfo.getPhotoWidth(), photoInfo.getPhotoHeight()));
    }

    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < this.rows; i++) {
            int j = 0;
            while (true) {
                if (j >= this.columns) {
                    break;
                } else if ((this.columns * i) + j == childNum) {
                    position[0] = i;
                    position[1] = j;
                    break;
                } else {
                    j++;
                }
            }
        }
        return position;
    }

    public int getGap() {
        return this.gap;
    }

    public void setGap(int gap2) {
        this.gap = dip2px(getContext(), (float) gap2);
    }

    @Deprecated
    public void setImagesData(List<? extends PhotoInfo> lists) {
        setImagesData(lists, null);
    }

    public void setImagesData(List<? extends PhotoInfo> lists, String businessId) {
        if (lists != null && !lists.isEmpty()) {
            ImageHelper.updateBusinessId(businessId, TAG);
            generateChildrenLayout(lists.size());
            boolean needLayoutChildrenView = true;
            if (this.listData == null) {
                this.listData = new ArrayList();
                if (lists.size() == 1) {
                    addSingleImageView((PhotoInfo) lists.get(0));
                } else {
                    for (int i = 0; i < lists.size(); i++) {
                        addGridImageView();
                    }
                }
            } else {
                int oldViewCount = this.listData.size();
                int newViewCount = lists.size();
                if (newViewCount == 1) {
                    removeAllViews();
                    addSingleImageView((PhotoInfo) lists.get(0));
                } else if (oldViewCount > newViewCount) {
                    removeViews(newViewCount - 1, oldViewCount - newViewCount);
                } else if (oldViewCount < newViewCount) {
                    int addViewCount = newViewCount - oldViewCount;
                    if (oldViewCount == 1) {
                        removeAllViews();
                        addViewCount = newViewCount;
                    }
                    for (int i2 = 0; i2 < addViewCount; i2++) {
                        addGridImageView();
                    }
                }
                if (oldViewCount == newViewCount) {
                    needLayoutChildrenView = false;
                }
                PhotoLogger.debug(TAG, "oldViewCount:" + oldViewCount + ",newViewCount:" + newViewCount);
            }
            this.listData.clear();
            this.listData.addAll(lists);
            if (!needLayoutChildrenView) {
                int childrenViewCount = getChildCount();
                for (int i3 = 0; i3 < childrenViewCount; i3++) {
                    ImageView childrenView = (ImageView) getChildAt(i3);
                    if (childrenViewCount == 1) {
                        setSingleImageThumbnail(this.listData.get(i3), childrenView);
                    } else {
                        setImageThumbnail(this.listData.get(i3), childrenView);
                    }
                }
            } else if (getLayoutParams() == null || getLayoutParams().width <= 0) {
                this.needLayout = true;
            } else {
                layoutChildrenView();
            }
            setChildClickListener();
        }
    }

    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            this.rows = 1;
            this.columns = length;
        } else if (length <= 6) {
            this.rows = 2;
            this.columns = 3;
            if (length == 4) {
                this.columns = 2;
            }
        } else {
            this.rows = 3;
            this.columns = 3;
        }
    }

    private void addGridImageView() {
        CustomImageView iv = new CustomImageView(getContext());
        iv.setScaleType(ScaleType.CENTER_CROP);
        iv.setBackgroundColor(getResources().getColor(R.color.photo_background));
        addView(iv, generateDefaultLayoutParams());
    }

    private void addSingleImageView(PhotoInfo photoInfo) {
        CustomImageView iv = new CustomImageView(getContext());
        iv.setScaleType(ScaleType.CENTER_CROP);
        iv.setBackgroundColor(getResources().getColor(R.color.photo_background));
        addView(iv, getSuggestedLayoutParams(photoInfo));
    }

    private LayoutParams getSuggestedLayoutParams(PhotoInfo photoInfo) {
        LayoutParams params = generateDefaultLayoutParams();
        int[] size = ImageHelper.getSingleImageSize(photoInfo.getPhotoWidth(), photoInfo.getPhotoHeight(), this.maxSingleWidth, 0.22f);
        if (size[0] != 0) {
            params.width = size[0];
            params.height = size[1];
        } else {
            float imageRatio = 0.0f;
            if (photoInfo.getPhotoHeight() > 0) {
                imageRatio = ((float) photoInfo.getPhotoHeight()) / ((float) photoInfo.getPhotoWidth());
            } else {
                params.width = this.maxSingleWidth;
                params.height = this.maxSingleHeight;
            }
            if (((float) this.maxSingleWidth) * imageRatio > ((float) this.maxSingleHeight)) {
                params.height = this.maxSingleHeight;
                params.width = (int) (((float) this.maxSingleHeight) / imageRatio);
                if (params.width < this.minSingleWidth) {
                    params.width = this.minSingleWidth;
                }
            } else if (((float) this.maxSingleHeight) / imageRatio > ((float) this.maxSingleWidth)) {
                params.width = this.maxSingleWidth;
                params.height = (int) (((float) this.maxSingleWidth) * imageRatio);
                if (params.height < this.minSingleHeight) {
                    params.height = this.minSingleHeight;
                }
            } else {
                params.width = this.maxSingleWidth;
                params.height = (int) (((float) this.maxSingleWidth) * imageRatio);
            }
        }
        return params;
    }
}
