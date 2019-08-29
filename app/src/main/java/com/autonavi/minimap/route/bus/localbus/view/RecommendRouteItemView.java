package com.autonavi.minimap.route.bus.localbus.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.ViewClickAction;

public class RecommendRouteItemView extends RelativeLayout implements dxy<dvu> {
    protected dxz mClickListener;
    /* access modifiers changed from: private */
    public dvu mData;
    protected int mPositionId;
    public TextView nameText;
    public ImageView selectIcon;

    public boolean isDataChanged(dvu dvu) {
        return true;
    }

    public RecommendRouteItemView(Context context) {
        super(context);
    }

    public RecommendRouteItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RecommendRouteItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.nameText = (TextView) findViewById(R.id.recommendroute_name);
        this.selectIcon = (ImageView) findViewById(R.id.recommendroute_img);
    }

    public void bindData(int i, dvu dvu, Bundle bundle) {
        if (dvu != null) {
            this.mPositionId = i;
            this.mData = dvu;
            this.nameText.setText(this.mData.a);
            this.selectIcon.setVisibility(this.mData.b ? 0 : 4);
        }
    }

    public dvu getData() {
        return this.mData;
    }

    public void setOnViewClickListener(dxz dxz) {
        this.mClickListener = dxz;
        NoDBClickUtil.a((View) this, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (RecommendRouteItemView.this.mClickListener != null) {
                    RecommendRouteItemView.this.mClickListener.onViewClicked(RecommendRouteItemView.this.mPositionId, ViewClickAction.RECOMMEND_ROUTE_SOLUTION, RecommendRouteItemView.this.mData, view);
                }
            }
        });
    }
}
