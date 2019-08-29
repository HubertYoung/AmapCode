package com.autonavi.minimap.route.common.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.route.ajx.attribute.Ajx3RouteBannerProperty;
import com.autonavi.widget.ui.CommonTips;
import com.uc.webview.export.extension.UCCore;
import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;

public class RouteBanner extends AbsoluteLayout implements ViewExtension {
    public static final String REQUEST_KEY_BUS = "25";
    public static final String REQUEST_KEY_BUS_RESULT = "26";
    public static final String REQUEST_KEY_COACH = "37";
    public static final String REQUEST_KEY_FOOT = "38";
    public static final String REQUEST_KEY_RIDE = "36";
    public static final String REQUEST_KEY_SHAREBIKE = "40";
    public static final String REQUEST_KEY_TRAIN = "33";
    /* access modifiers changed from: private */
    public boolean isClick = false;
    private boolean isShow;
    private IAjxContext mAjxContext;
    private CommonTips mCommonTips;
    /* access modifiers changed from: private */
    public int mItemCount = -1;
    protected Ajx3RouteBannerProperty mProperty;
    /* access modifiers changed from: private */
    public int mShowIndex = -1;

    public RouteBanner(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mProperty = new Ajx3RouteBannerProperty(this, iAjxContext);
        initView();
    }

    public RouteBanner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    private void initView() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.route_common_banner, this, true);
        this.mCommonTips = (CommonTips) findViewById(R.id.bus_tips);
    }

    private void setListener(final String str, final BannerItem bannerItem) {
        this.mCommonTips.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (bannerItem != null && !TextUtils.isEmpty(bannerItem.action)) {
                    Uri parse = Uri.parse(bannerItem.action);
                    if (parse != null && !TextUtils.isEmpty(parse.getQuery()) && !TextUtils.isEmpty(parse.getQueryParameter("url"))) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(parse);
                        intent.putExtra("owner", "banner");
                        ((brr) RouteBanner.this.getContext()).b(intent);
                    }
                }
            }
        });
        this.mCommonTips.setRightViewOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                BannerItem b2;
                RouteBanner.this.isClick = true;
                ash.a(str, RouteBanner.this.mShowIndex);
                do {
                    RouteBanner.this.mShowIndex = RouteBanner.this.mShowIndex + 1;
                    b2 = ash.b(str, RouteBanner.this.mShowIndex);
                    if (b2 == null) {
                        RouteBanner.this.setViewVisibility(8);
                    } else if (!b2.mIsHide) {
                        RouteBanner.this.toggleRouteBannerView(str, b2);
                        RouteBanner.this.setViewVisibility(0);
                    }
                    if (b2 == null) {
                        return;
                    }
                } while (b2.mIsHide);
            }
        });
    }

    public void resetbanner() {
        this.mShowIndex = -1;
        this.isShow = false;
    }

    public void loadbanner(final String str) {
        ash.a(str, false, false, new a() {
            public final void a(LinkedList<BannerItem> linkedList, long j) {
                if (linkedList != null) {
                    RouteBanner.this.mItemCount = linkedList.size();
                }
                RouteBanner.this.showBannerItem(str);
            }
        });
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    public int getShowIndex() {
        return this.mShowIndex;
    }

    public void setbannerShowStatus() {
        setViewVisibility(this.isShow ? 0 : 8);
    }

    /* access modifiers changed from: private */
    public void showBannerItem(String str) {
        BannerItem b;
        do {
            this.mShowIndex++;
            b = ash.b(str, this.mShowIndex);
            if (b == null) {
                setViewVisibility(8);
                return;
            }
        } while (b.mIsHide);
        this.isShow = true;
        toggleRouteBannerView(str, b);
        setViewVisibility(0);
        requestLayout();
    }

    public void setViewVisibility(int i) {
        setVisibility(i);
        if (this.mProperty != null && this.mAjxContext != null) {
            notifyJs(i == 0 ? 1 : 0);
        }
    }

    private void notifyJs(int i) {
        measure(MeasureSpec.makeMeasureSpec(getResources().getDisplayMetrics().widthPixels, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(0, 0));
        int pixelToStandardUnit = (int) DimensionUtils.pixelToStandardUnit((float) getMeasuredWidth());
        int pixelToStandardUnit2 = (int) DimensionUtils.pixelToStandardUnit((float) getMeasuredHeight());
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("display", i);
            jSONObject.put("width", pixelToStandardUnit);
            jSONObject.put("height", pixelToStandardUnit2);
            jSONObject.put("click", this.isClick);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Parcel parcel = new Parcel();
        parcel.writeInt(2);
        parcel.writeString("tipdata");
        parcel.writeString(jSONObject.toString());
        this.mAjxContext.invokeJsEvent("tipcallback", this.mAjxContext.getDomTree().getNodeId(this), parcel, null);
        this.isClick = false;
    }

    /* access modifiers changed from: private */
    public void toggleRouteBannerView(String str, BannerItem bannerItem) {
        if (bannerItem != null) {
            if (!TextUtils.isEmpty(bannerItem.title)) {
                this.mCommonTips.setTipText((CharSequence) bannerItem.title);
            }
            if (!TextUtils.isEmpty(bannerItem.background)) {
                try {
                    this.mCommonTips.setBackgroundColor(Color.parseColor(bannerItem.background));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            setListener(str, bannerItem);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }
}
