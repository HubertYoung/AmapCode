package com.alipay.mobile.nebula.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;

public class H5TabbarItem {
    private TextView badgeArea = ((TextView) this.rootView.findViewById(R.id.h5_tabbaritem_badge));
    private TextView iconArea = ((TextView) this.rootView.findViewById(R.id.h5_tabbaritem_txticon));
    private RelativeLayout rootView;
    private H5DotView smallDotView = ((H5DotView) this.rootView.findViewById(R.id.h5_tabbaritem_dotView));

    public H5TabbarItem(Context context) {
        this.rootView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.h5_tabbaritem, null);
    }

    public View getRootView() {
        return this.rootView;
    }

    public View getIconAreaView() {
        return this.iconArea;
    }

    public View getBadgeAreaView() {
        return this.badgeArea;
    }

    public H5DotView getSmallDotView() {
        return this.smallDotView;
    }

    public void setTag(String tag) {
        this.rootView.setTag(tag);
    }

    public String getTag() {
        return (String) this.rootView.getTag();
    }
}
