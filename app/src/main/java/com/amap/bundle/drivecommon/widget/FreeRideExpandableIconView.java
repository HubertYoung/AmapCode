package com.amap.bundle.drivecommon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.CircleImageView;
import java.util.List;

public class FreeRideExpandableIconView extends LinearLayout {
    public static final int BLUE_BG = R.drawable.layer_tip_left;
    private ViewGroup content_container;
    private int iconId;
    private FrameLayout icon_container;
    private ImageView mIconView;
    private View mRootView;
    private TextView mTextView;

    public FreeRideExpandableIconView(Context context) {
        super(context);
        init(null);
    }

    public FreeRideExpandableIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public FreeRideExpandableIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ExpandableIconView);
            this.iconId = obtainStyledAttributes.getResourceId(R.styleable.ExpandableIconView_childIconId, R.id.expandable_icon_icon);
            obtainStyledAttributes.recycle();
        }
        this.mRootView = inflate(getContext(), R.layout.expandable_icon_free_ride, this);
        this.mIconView = (ImageView) this.mRootView.findViewById(R.id.expandable_free_ride_icon);
        this.mTextView = (TextView) this.mRootView.findViewById(R.id.expandable_icon_text);
        this.content_container = (LinearLayout) this.mRootView.findViewById(R.id.free_ride_content_container);
        this.icon_container = (FrameLayout) this.mRootView.findViewById(R.id.icon_container);
        this.mIconView.setId(this.iconId);
    }

    public void setIconId(int i) {
        this.mIconView.setImageResource(i);
    }

    public void setText(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
    }

    public void setTipVisible(boolean z) {
        this.content_container.setVisibility(z ? 0 : 8);
    }

    public void setIconList(List<String> list) {
        if (list != null && list.size() != 0) {
            this.icon_container.removeAllViews();
            for (int size = list.size() - 1; size >= 0; size--) {
                LayoutParams layoutParams = new LayoutParams(agn.a(getContext(), 40.0f), -2);
                CircleImageView circleImageView = new CircleImageView(AMapPageUtil.getAppContext());
                circleImageView.setBorderWidth(4);
                circleImageView.setBorderColor(getContext().getResources().getColor(R.color.c_1));
                circleImageView.setScaleType(ScaleType.CENTER_CROP);
                ko.a(circleImageView, getSmallImgUrl(list.get(size)), null, R.drawable.freeride_default_user_header_portrait, null);
                if (size != 0) {
                    layoutParams.setMargins(size * 30, 0, 0, 0);
                }
                this.icon_container.addView(circleImageView, layoutParams);
            }
        }
    }

    private String getSmallImgUrl(String str) {
        if (TextUtils.isEmpty(str) || str.indexOf("?") <= 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        sb.insert(str.indexOf("?"), "@160h_160w_1e_1c");
        return sb.toString();
    }
}
