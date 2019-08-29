package com.autonavi.bundle.uitemplate.mapwidget.widget.ajxtemplate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.bundle.uitemplate.api.IAJXWidgetProperty;
import com.autonavi.map.template.AbstractViewHolderAdapter.a;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import pl.droidsonroids.gif.GifDrawable;

public class AJXTemplateContainer extends FrameLayout implements IAJXTemplateContainer {
    private Adapter mAdapter;
    /* access modifiers changed from: private */
    public IAJXWidgetProperty mAjxWidgetProperty;
    private OnDataChangeListener mDataChangeListener;
    private boolean mExpandMode;
    /* access modifiers changed from: private */
    public OnItemClickListener mItemClickListener;
    private ListView mListView;
    private Comparator<bel> mPriorityComparator = new Comparator<bel>() {
        public int compare(bel bel, bel bel2) {
            if (bel.f < bel2.f) {
                return 1;
            }
            return bel.f > bel2.f ? -1 : 0;
        }
    };
    List<bel> mRealItemList;
    private final int mShadowOffset = DimensionUtils.dipToPixel(2.0f);
    private final int mShadowRadius = DimensionUtils.dipToPixel(5.0f);
    private final int padding_normal = DimensionUtils.dipToPixel(5.0f);
    private final int padding_small = DimensionUtils.dipToPixel(3.0f);

    static class Adapter extends AbstractViewHolderBaseAdapter<bel, ItemHolder> {
        IAJXWidgetProperty mAJXWidgetProperty;
        Context mContext;
        boolean mExpandMode;

        public int getItemViewHolderType(int i) {
            return i == 0 ? 0 : 1;
        }

        public Adapter(Context context) {
            this.mContext = context;
        }

        public void setAJXWidgetProperty(IAJXWidgetProperty iAJXWidgetProperty) {
            this.mAJXWidgetProperty = iAJXWidgetProperty;
        }

        public void setExpandMode(boolean z) {
            this.mExpandMode = z;
        }

        public View onCreateView(ViewGroup viewGroup, int i) {
            return LayoutInflater.from(this.mContext).inflate(R.layout.map_widget_layout_ajx_template_item, viewGroup, false);
        }

        public ItemHolder onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
            return new ItemHolder(view);
        }

        public void onBindViewHolderWithData(ItemHolder itemHolder, bel bel, int i, int i2) {
            if (bel != null) {
                itemHolder.onBindData(this.mAJXWidgetProperty, bel, this.mExpandMode, i2 == 0);
            }
        }
    }

    static class ItemHolder extends a {
        View imageTextContainer;
        ImageView mImgView;
        View mRedDotView;
        View mSplitView;
        TextView mTextView;

        public ItemHolder(View view) {
            super(view);
            this.imageTextContainer = view.findViewById(R.id.widget_item_container);
            this.mSplitView = view.findViewById(R.id.widget_split_line);
            this.mImgView = (ImageView) view.findViewById(R.id.widget_img);
            this.mTextView = (TextView) view.findViewById(R.id.widget_desc);
            this.mRedDotView = view.findViewById(R.id.widget_red_tip);
        }

        /* access modifiers changed from: protected */
        public void onBindData(IAJXWidgetProperty iAJXWidgetProperty, bel bel, boolean z, boolean z2) {
            int itemTextSize = (int) (((float) iAJXWidgetProperty.getItemTextSize()) * 1.5f);
            LayoutParams layoutParams = this.itemView.getLayoutParams();
            if (z) {
                layoutParams.height = iAJXWidgetProperty.getItemImageSlideLength() + iAJXWidgetProperty.getItemTextImageMargin() + itemTextSize + (iAJXWidgetProperty.getItemTopBottomPadding() * 2);
            } else {
                layoutParams.height = iAJXWidgetProperty.getItemImageSlideLength() + (iAJXWidgetProperty.getItemTopBottomPadding() * 2);
            }
            new StringBuilder("onBindData: itemView height = ").append(layoutParams.height);
            layoutParams.width = iAJXWidgetProperty.getItemImageSlideLength() + (iAJXWidgetProperty.getItemLeftRightPadding() * 2);
            this.itemView.setLayoutParams(layoutParams);
            new StringBuilder("onBindData: itemModel.isEnable = ").append(bel.h);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mImgView.getLayoutParams();
            if (bel.h) {
                this.itemView.setVisibility(0);
                layoutParams2.leftMargin = iAJXWidgetProperty.getItemLeftRightPadding();
                layoutParams2.rightMargin = iAJXWidgetProperty.getItemLeftRightPadding();
                this.mImgView.setLayoutParams(layoutParams2);
                layoutParams2.width = iAJXWidgetProperty.getItemImageSlideLength();
                layoutParams2.height = iAJXWidgetProperty.getItemImageSlideLength();
                this.mImgView.setLayoutParams(layoutParams2);
                Ajx.getInstance().lookupLoader(bel.b.url).preLoadImage(bel.b, new ImageCallback() {
                    public void onGifLoaded(GifDrawable gifDrawable) {
                    }

                    public void onPrepareLoad(Drawable drawable) {
                    }

                    public void onBitmapLoaded(Bitmap bitmap) {
                        ItemHolder.this.mImgView.setImageBitmap(bitmap);
                    }

                    public void onBitmapFailed(Drawable drawable) {
                        ItemHolder.this.mImgView.setImageBitmap(null);
                    }
                });
                if (TextUtils.isEmpty(bel.c)) {
                    this.mTextView.setVisibility(8);
                } else {
                    this.mTextView.setVisibility(0);
                    this.mTextView.getPaint().setFakeBoldText(bel.e);
                    this.mTextView.setText(bel.c);
                    this.mTextView.setTextColor(Color.parseColor(bel.d));
                    this.mTextView.setTextSize(0, (float) iAJXWidgetProperty.getItemTextSize());
                    RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.mTextView.getLayoutParams();
                    layoutParams3.topMargin = iAJXWidgetProperty.getItemTextImageMargin();
                    layoutParams3.height = (int) (((float) iAJXWidgetProperty.getItemTextSize()) * 1.5f);
                    this.mTextView.setLayoutParams(layoutParams3);
                }
                if (bel.g) {
                    this.mRedDotView.setVisibility(0);
                    RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.mRedDotView.getLayoutParams();
                    layoutParams4.topMargin = -DimensionUtils.dipToPixel(4.0f);
                    layoutParams4.rightMargin = iAJXWidgetProperty.getItemLeftRightPadding() - DimensionUtils.dipToPixel(4.0f);
                    this.mRedDotView.setLayoutParams(layoutParams4);
                } else {
                    this.mRedDotView.setVisibility(8);
                }
                if (z2) {
                    this.mSplitView.setVisibility(8);
                    return;
                }
                this.mSplitView.setVisibility(0);
                LayoutParams layoutParams5 = this.mSplitView.getLayoutParams();
                layoutParams5.width = ((layoutParams2.width + layoutParams2.leftMargin) + layoutParams2.rightMargin) - DimensionUtils.dipToPixel(10.0f);
                this.mSplitView.setLayoutParams(layoutParams5);
                return;
            }
            this.itemView.setVisibility(8);
        }
    }

    public interface OnDataChangeListener {
        void onDataChange(List<bel> list);
    }

    public AJXTemplateContainer(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.map_widget_layout_ajx_template_list, this, true);
        this.mListView = (ListView) findViewById(R.id.list_view);
        this.mAdapter = new Adapter(getContext());
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (AJXTemplateContainer.this.mItemClickListener != null) {
                    AdapterView<?> adapterView2 = adapterView;
                    View view2 = view;
                    AJXTemplateContainer.this.mItemClickListener.onItemClick(adapterView2, view2, AJXTemplateContainer.this.mAjxWidgetProperty.getWidgetBeans().indexOf(AJXTemplateContainer.this.mRealItemList.get(i)), j);
                }
            }
        });
    }

    public void setData(IAJXWidgetProperty iAJXWidgetProperty) {
        this.mAjxWidgetProperty = iAJXWidgetProperty;
        this.mExpandMode = containsDesc();
        this.mAdapter.setExpandMode(this.mExpandMode);
        adjustListPadding(iAJXWidgetProperty.getWidgetBeans());
        adjustListSizeAndShadow();
        setDataAndChangeDataSet(iAJXWidgetProperty.getWidgetBeans());
        measure(0, 0);
    }

    public void updateDataList(List<bel> list, boolean z) {
        adjustListPadding(list);
        adjustListSizeAndShadow();
        setDataAndChangeDataSet(list);
        measure(0, 0);
        if (z) {
            notifyDataChangeIfNeeded(list);
        }
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.mDataChangeListener = onDataChangeListener;
    }

    private void notifyDataChangeIfNeeded(List<bel> list) {
        if (this.mDataChangeListener != null) {
            this.mDataChangeListener.onDataChange(list);
        }
    }

    private void adjustListSizeAndShadow() {
        int measureContainerMaxHeight = measureContainerMaxHeight();
        int measureContainerMaxWidth = measureContainerMaxWidth();
        LayoutParams layoutParams = this.mListView.getLayoutParams();
        layoutParams.height = measureContainerMaxHeight;
        layoutParams.width = measureContainerMaxWidth;
        this.mListView.setLayoutParams(layoutParams);
        Bitmap createShadowBitmap = createShadowBitmap(measureContainerMaxWidth, measureContainerMaxHeight, (float) this.mAjxWidgetProperty.getContainerRadius(), (float) this.mShadowRadius);
        if (createShadowBitmap != null) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), createShadowBitmap);
            if (VERSION.SDK_INT <= 16) {
                setBackgroundDrawable(bitmapDrawable);
                return;
            }
            setBackground(bitmapDrawable);
        }
    }

    private void adjustListPadding(List<bel> list) {
        if (list != null && list.size() != 0) {
            int containerTopBottomPadding = this.mAjxWidgetProperty.getContainerTopBottomPadding();
            if (containerTopBottomPadding >= 0) {
                int i = this.mShadowRadius;
                this.mListView.setPadding(this.mShadowRadius, this.mShadowRadius + containerTopBottomPadding, i, containerTopBottomPadding + i);
                return;
            }
            boolean z = true;
            int i2 = 0;
            if (list.size() > 1) {
                Iterator<bel> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    bel next = it.next();
                    if (!TextUtils.isEmpty(next.c) && next.h) {
                        break;
                    }
                }
                i2 = z ? this.padding_small : this.padding_normal;
            }
            int i3 = this.mShadowRadius;
            this.mListView.setPadding(this.mShadowRadius, this.mShadowRadius + i2, i3, i2 + i3);
        }
    }

    public MarginLayoutParams getLayoutOffsetParams() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.leftMargin = -this.mShadowRadius;
        layoutParams.topMargin = -this.mShadowRadius;
        layoutParams.rightMargin = -this.mShadowRadius;
        layoutParams.bottomMargin = -this.mShadowRadius;
        return layoutParams;
    }

    private void setDataAndChangeDataSet(List<bel> list) {
        this.mRealItemList = new ArrayList();
        for (bel next : list) {
            if (next.h) {
                this.mRealItemList.add(next);
            }
        }
        this.mAdapter.setAJXWidgetProperty(this.mAjxWidgetProperty);
        this.mAdapter.setDataAndChangeDataSet(this.mRealItemList);
    }

    private boolean containsDesc() {
        for (bel bel : this.mAjxWidgetProperty.getWidgetBeans()) {
            if (!TextUtils.isEmpty(bel.c)) {
                return true;
            }
        }
        return false;
    }

    public void setBackgroundRadius(int i) {
        ((GradientDrawable) this.mListView.getBackground().mutate()).setCornerRadius((float) i);
    }

    private int getItemHeight() {
        return this.mAjxWidgetProperty.getItemImageSlideLength() + (this.mExpandMode ? ((int) (((double) this.mAjxWidgetProperty.getItemTextSize()) * 1.5d)) + this.mAjxWidgetProperty.getItemTextImageMargin() : 0) + (this.mAjxWidgetProperty.getItemTopBottomPadding() * 2);
    }

    public boolean relayoutOfMaxHeight(int i) {
        int i2;
        List<bel> widgetBeans = this.mAjxWidgetProperty.getWidgetBeans();
        boolean z = this.mAjxWidgetProperty.getContainerTopBottomPadding() >= 0;
        if (z) {
            i2 = i - (this.mAjxWidgetProperty.getContainerTopBottomPadding() * 2);
        } else {
            i2 = i - (this.padding_small * 2);
        }
        ArrayList arrayList = new ArrayList(widgetBeans);
        Collections.sort(arrayList, this.mPriorityComparator);
        int minSubWidgetCount = this.mAjxWidgetProperty.getMinSubWidgetCount();
        int i3 = i2 - (this.mShadowRadius * 2);
        int i4 = 0;
        boolean z2 = false;
        boolean z3 = true;
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            bel bel = (bel) arrayList.get(i5);
            if (i3 > 0) {
                i3 -= getItemHeight();
                if (!z2 && this.mExpandMode) {
                    if (!z) {
                        i3 -= (this.padding_normal - this.padding_small) * 2;
                    }
                    z2 = true;
                }
                if (i3 >= 0) {
                    bel.h = true;
                    i4++;
                }
            }
            bel.h = false;
            z3 = false;
        }
        boolean z4 = i4 < minSubWidgetCount;
        if (z4) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((bel) it.next()).h = false;
            }
        }
        updateDataList(widgetBeans, !z4);
        return z3;
    }

    private int measureContainerMaxWidth() {
        return this.mAjxWidgetProperty.getItemImageSlideLength() + (this.mAjxWidgetProperty.getItemLeftRightPadding() * 2) + (this.mShadowRadius * 2);
    }

    private int measureContainerMaxHeight() {
        List<bel> widgetBeans = this.mAjxWidgetProperty.getWidgetBeans();
        boolean z = false;
        boolean z2 = false;
        int i = 0;
        for (int i2 = 0; i2 < widgetBeans.size(); i2++) {
            if (widgetBeans.get(i2).h) {
                i += getItemHeight();
                z2 = true;
            }
        }
        if (!z2) {
            return i;
        }
        if (this.mAjxWidgetProperty.getContainerTopBottomPadding() >= 0) {
            z = true;
        }
        if (z) {
            i += this.mAjxWidgetProperty.getContainerTopBottomPadding() * 2;
        } else if (this.mExpandMode) {
            i += this.padding_small * 2;
        } else if (widgetBeans.size() > 1) {
            i += this.padding_normal * 2;
        }
        return i + (this.mShadowRadius * 2);
    }

    @Nullable
    private Bitmap createShadowBitmap(int i, int i2, float f, float f2) {
        if (i2 <= 0 || i <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f3 = ((float) i) - f2;
        float f4 = ((float) i2) - f2;
        RectF rectF = new RectF(f2, f2, f3, ((float) this.mShadowOffset) + f4);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#33000000"));
        paint.setStyle(Style.FILL);
        paint.setMaskFilter(new BlurMaskFilter(f2 - ((float) this.mShadowOffset), Blur.NORMAL));
        canvas.drawRoundRect(rectF, f, f, paint);
        RectF rectF2 = new RectF(f2, f2, f3, f4);
        paint.setColor(-1);
        paint.setMaskFilter(null);
        canvas.drawRoundRect(rectF2, f, f, paint);
        return createBitmap;
    }
}
