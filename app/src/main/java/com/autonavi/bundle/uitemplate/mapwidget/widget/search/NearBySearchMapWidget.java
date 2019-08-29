package com.autonavi.bundle.uitemplate.mapwidget.widget.search;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;

public class NearBySearchMapWidget extends AbstractMapWidget<NearBySearchWidgetPresenter> {
    protected LinearLayout mBottomRegion;
    private View mDeliverLine;
    private TextView mRestrictLabel;
    protected LinearLayout mWeatherContainer;
    private ImageView mWeatherIcon;
    protected TextView mWeatherLabel;

    public void setCutLineVisible(int i) {
    }

    public NearBySearchMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, getLayoutId());
        this.mBottomRegion = (LinearLayout) loadLayoutRes.findViewById(R.id.attachment_msg_container);
        this.mDeliverLine = loadLayoutRes.findViewById(R.id.deliver_line);
        this.mWeatherIcon = (ImageView) loadLayoutRes.findViewById(R.id.weather_icon);
        this.mRestrictLabel = (TextView) loadLayoutRes.findViewById(R.id.restrict_label);
        this.mWeatherLabel = (TextView) loadLayoutRes.findViewById(R.id.weather_temperature_label);
        this.mWeatherContainer = (LinearLayout) loadLayoutRes.findViewById(R.id.weather_container);
        return loadLayoutRes;
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.map_widget_nearby_search_layout;
    }

    public void setWeatherIcon(String str) {
        if (this.mWeatherIcon != null && !TextUtils.isEmpty(str)) {
            ko.a(this.mWeatherIcon, str);
        }
    }

    public void setWeatherLabel(CharSequence charSequence) {
        if (this.mWeatherLabel != null && !TextUtils.isEmpty(charSequence)) {
            this.mWeatherLabel.setText(charSequence);
        }
    }

    public void setWeatherContainerVisibility(int i) {
        if (this.mWeatherContainer != null) {
            this.mWeatherContainer.setVisibility(i);
        }
    }

    public void setRestrictLabel(CharSequence charSequence) {
        if (this.mRestrictLabel != null && !TextUtils.isEmpty(charSequence)) {
            this.mRestrictLabel.setText(charSequence);
        }
    }

    public void setRestrictLabelVisibility(int i) {
        if (8 == i) {
            this.mWeatherContainer.setGravity(19);
        } else {
            this.mWeatherContainer.setGravity(21);
        }
        setRestrictVisible(i);
    }

    public void setRestrictVisible(int i) {
        if (this.mRestrictLabel != null) {
            this.mRestrictLabel.setVisibility(i);
        }
    }

    public void setBottomRegionVisibility(int i) {
        if (8 == i) {
            updateContainerHeight(54);
        } else {
            updateContainerHeight(32);
        }
        setWeatherRestrictVisible(i);
        if (this.mDeliverLine != null) {
            this.mDeliverLine.setVisibility(i);
        }
    }

    public void setWeatherRestrictVisible(int i) {
        if (this.mBottomRegion != null) {
            this.mBottomRegion.setVisibility(i);
        }
    }

    private void updateContainerHeight(int i) {
        if (this.mContentView != null) {
            RelativeLayout relativeLayout = (RelativeLayout) this.mContentView.findViewById(R.id.nearby_search_container);
            if (relativeLayout != null) {
                relativeLayout.getLayoutParams().height = bet.a(getContext(), i);
            }
        }
    }
}
