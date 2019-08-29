package com.autonavi.map.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class MapGuideView extends LinearLayout implements cel {
    private ImageView mGuideView;
    private TextView mGuideViewBubble;

    public void setMapGuideIcon(int i) {
    }

    public MapGuideView(Context context) {
        this(context, null);
    }

    public MapGuideView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MapGuideView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(0);
        initView(LayoutInflater.from(context).inflate(R.layout.map_guid_view, this, true));
    }

    private void initView(View view) {
        this.mGuideView = (ImageView) view.findViewById(R.id.btn_map_guide_entry);
        this.mGuideViewBubble = (TextView) view.findViewById(R.id.map_guide_bubble);
    }

    public ImageView getGuideView() {
        return this.mGuideView;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mGuideView.setOnClickListener(onClickListener);
    }

    public TextView getGuideViewBubble() {
        return this.mGuideViewBubble;
    }

    public void setOnBubbleClickListener(OnClickListener onClickListener) {
        this.mGuideViewBubble.setOnClickListener(onClickListener);
    }

    public int getViewVisibility() {
        return getVisibility();
    }

    public void setViewVisibility(int i) {
        setVisibility(i);
    }
}
