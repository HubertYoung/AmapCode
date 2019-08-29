package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.alipay.android.phone.falcon.falconimg.layout.CellDetail;
import com.alipay.android.phone.falcon.falconimg.layout.LayoutDetail;
import com.alipay.android.phone.falcon.falconimg.layout.PhotoDetail;
import com.alipay.mobile.beehive.photo.util.DefaultCalcLayout;
import com.alipay.mobile.beehive.photo.util.PhotoConfig;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class GridMatchBaseLayout extends FrameLayout {
    private static final String TAG = GridMatchBaseLayout.class.getSimpleName();
    public int defaultDrawableID = 0;
    private String falcoType;
    private LayoutDetail layoutDetail = new LayoutDetail();
    private List<PhotoInfo> listData;
    /* access modifiers changed from: private */
    public OnGridItemClickListener listener;
    public String mBizType;
    public String mBusinessId;
    private int maxSize = 9;
    private boolean needLayout;
    public int screenWidth;

    public abstract void addGridImageView();

    public abstract void setImageThumbnail(PhotoInfo photoInfo, GridCustomView gridCustomView, CellDetail cellDetail, boolean z);

    public GridMatchBaseLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public GridMatchBaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GridMatchBaseLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        getConfig();
        this.screenWidth = getScreenWidth(context);
    }

    private void getConfig() {
        try {
            int size = PhotoConfig.getInstance().getGridSizeConfig();
            if (size != 0) {
                this.maxSize = size;
            }
        } catch (Exception e) {
            PhotoLogger.warn(TAG, "readConfig exception," + e.getMessage());
        }
    }

    public void setDefaultDrawableID(int defaultDrawableID2) {
        this.defaultDrawableID = defaultDrawableID2;
    }

    public void setOnGirdItemClickListener(OnGridItemClickListener listener2) {
        this.listener = listener2;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        PhotoLogger.debug(TAG, "onMeasure, dimension=" + MeasureSpec.getSize(widthMeasureSpec) + DictionaryKeys.CTRLXY_X + MeasureSpec.getSize(heightMeasureSpec));
        this.screenWidth = getScreenWidth(getContext());
        if (this.layoutDetail != null) {
            setMeasuredDimension((int) (((double) (((float) this.screenWidth) * this.layoutDetail.fullWidth)) + 0.5d), (int) (((double) (((float) this.screenWidth) * this.layoutDetail.fullHeight)) + 0.5d));
        }
        if (this.needLayout) {
            layoutChildrenView();
        }
    }

    @Deprecated
    public void setImagesData(List<? extends PhotoInfo> lists) {
        setImagesData(lists, null, null);
    }

    public void setImagesData(List<? extends PhotoInfo> lists, String businessId, String bizType) {
        boolean isSingle;
        boolean z;
        if (lists == null || lists.isEmpty()) {
            PhotoLogger.error(TAG, (String) "lists is empty");
            return;
        }
        long preTime1 = System.currentTimeMillis();
        this.mBusinessId = businessId;
        this.mBizType = bizType;
        if (this.listData == null) {
            this.listData = new ArrayList();
            for (int i = 0; i < this.maxSize; i++) {
                addGridImageView();
            }
        }
        this.layoutDetail = DefaultCalcLayout.getLayoutRules(lists.size(), getPhotoDetailList(lists));
        if (this.layoutDetail == null || this.layoutDetail.itemList == null || this.layoutDetail.itemList.isEmpty()) {
            setVisibility(8);
            PhotoLogger.error(TAG, (String) "layoutDetail or detailList is null");
            return;
        }
        setVisibility(0);
        int childrenViewCount = this.layoutDetail.itemList.size();
        if (TextUtils.equals(this.falcoType, this.layoutDetail.layoutType)) {
            if (TextUtils.equals(this.falcoType, "1-1")) {
                z = true;
            } else {
                z = false;
            }
            this.needLayout = z;
        } else {
            this.falcoType = this.layoutDetail.layoutType;
            this.needLayout = true;
            setChildViewVisible(childrenViewCount);
        }
        this.listData.clear();
        this.listData.addAll(lists);
        if (this.needLayout) {
            if (getLayoutParams() == null || getLayoutParams().width <= 0) {
                requestLayout();
            } else {
                layoutChildrenView();
            }
        } else if (getLayoutParams() == null || getLayoutParams().width <= 0) {
            this.needLayout = true;
            requestLayout();
        } else {
            if (this.listData.size() == 1) {
                isSingle = true;
            } else {
                isSingle = false;
            }
            for (int i2 = 0; i2 < childrenViewCount; i2++) {
                setImageThumbnail(this.listData.get(i2), (GridCustomView) getChildAt(i2), (CellDetail) this.layoutDetail.itemList.get(i2), isSingle);
            }
        }
        setChildClickListener(this.layoutDetail.itemList);
        PhotoLogger.debug(TAG, "setImagesData function cost:" + (System.currentTimeMillis() - preTime1));
    }

    private void setChildViewVisible(int size) {
        for (int i = 0; i < size; i++) {
            getChildAt(i).setVisibility(0);
        }
        for (int i2 = size; i2 < this.maxSize; i2++) {
            GridCustomView child = (GridCustomView) getChildAt(i2);
            child.clear();
            child.setVisibility(8);
        }
        PhotoLogger.debug(TAG, "layoutCount:" + size);
    }

    private void layoutChildrenView() {
        boolean isSingle = true;
        ArrayList cellDetailList = this.layoutDetail.itemList;
        if (cellDetailList == null || cellDetailList.isEmpty()) {
            PhotoLogger.debug(TAG, "layoutDetail.itemList is null");
            return;
        }
        long preTime = System.currentTimeMillis();
        int count = 0;
        int totalWidth = this.screenWidth;
        if (this.listData.size() != 1) {
            isSingle = false;
        }
        Iterator it = cellDetailList.iterator();
        while (it.hasNext()) {
            CellDetail cellDetail = (CellDetail) it.next();
            CustomImgTextView childrenView = (CustomImgTextView) getChildAt(count);
            layoutCustomView(cellDetail, childrenView, totalWidth);
            setImageThumbnail(this.listData.get(count), childrenView, cellDetail, isSingle);
            count++;
        }
        this.needLayout = false;
        PhotoLogger.debug(TAG, "layoutChildren cost: " + count + " ," + (System.currentTimeMillis() - preTime) + RPCDataParser.TIME_MS);
    }

    private void layoutCustomView(CellDetail cellDetail, GridCustomView view, int totalWidth) {
        int left = (int) (((double) (cellDetail.upLeftx * ((float) totalWidth))) + 0.5d);
        int top = (int) (((double) (cellDetail.upLefty * ((float) totalWidth))) + 0.5d);
        view.layout(left, top, left + ((int) (((double) (cellDetail.width * ((float) totalWidth))) + 0.5d)), top + ((int) (((double) (cellDetail.height * ((float) totalWidth))) + 0.5d)));
        view.setScaleType(cellDetail.scaleType);
    }

    private void setChildClickListener(ArrayList<CellDetail> detailList) {
        final Boolean hasMore;
        if (this.listener != null) {
            for (int i = 0; i < detailList.size(); i++) {
                final int pos = i;
                if (detailList.get(i).addNum > 0) {
                    hasMore = Boolean.valueOf(true);
                } else {
                    hasMore = Boolean.valueOf(false);
                }
                getChildAt(i).setOnClickListener(new OnClickListener() {
                    public final void onClick(View v) {
                        GridMatchBaseLayout.this.listener.onItemClick(pos, v, hasMore.booleanValue());
                    }
                });
                getChildAt(i).setOnLongClickListener(new OnLongClickListener() {
                    public final boolean onLongClick(View v) {
                        GridMatchBaseLayout.this.listener.onItemLongClick(pos, v, hasMore.booleanValue());
                        return false;
                    }
                });
            }
        }
    }

    private ArrayList<PhotoDetail> getPhotoDetailList(List<? extends PhotoInfo> list) {
        if (list == null) {
            return null;
        }
        ArrayList detailList = new ArrayList();
        for (PhotoInfo info : list) {
            PhotoDetail detail = new PhotoDetail();
            detail.width = info.getPhotoWidth();
            detail.height = info.getPhotoHeight();
            detailList.add(detail);
        }
        return detailList;
    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth();
    }
}
