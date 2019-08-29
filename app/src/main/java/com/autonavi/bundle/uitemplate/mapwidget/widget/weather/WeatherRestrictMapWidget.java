package com.autonavi.bundle.uitemplate.mapwidget.widget.weather;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.map.widget.StrokeTextView;
import com.autonavi.minimap.R;

public class WeatherRestrictMapWidget extends AbstractMapWidget<WeatherRestrictWidgetPresenter> {
    private final int HEIGHT_38DP = 48;
    private final int HEIGHT_48DP = 58;
    private ImageView mCutLineView;
    private LinearLayout mRestrictContainer;
    private StrokeTextView mRestrictLabel;
    private TextView mRestrictNum;
    private LinearLayout mWeatherContainer;
    private ImageView mWeatherIcon;
    private StrokeTextView mWeatherLabel;

    public WeatherRestrictMapWidget(Context context, IWidgetProperty iWidgetProperty) {
        super(context, iWidgetProperty);
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, R.layout.map_widget_weather_restrict_layout);
        this.mWeatherIcon = (ImageView) loadLayoutRes.findViewById(R.id.weather_icon);
        this.mWeatherLabel = (StrokeTextView) loadLayoutRes.findViewById(R.id.weather_temperature_label);
        setStrokeProperty(this.mWeatherLabel);
        this.mWeatherContainer = (LinearLayout) loadLayoutRes.findViewById(R.id.weather_container);
        this.mRestrictContainer = (LinearLayout) loadLayoutRes.findViewById(R.id.restrict_info_container);
        this.mRestrictNum = (TextView) loadLayoutRes.findViewById(R.id.restrict_num_label);
        this.mRestrictLabel = (StrokeTextView) loadLayoutRes.findViewById(R.id.restrict_label);
        setStrokeProperty(this.mRestrictLabel);
        this.mCutLineView = (ImageView) loadLayoutRes.findViewById(R.id.cut_line_view);
        return loadLayoutRes;
    }

    private void setStrokeProperty(StrokeTextView strokeTextView) {
        if (strokeTextView != null) {
            strokeTextView.setStrokeWidth(4);
            strokeTextView.setShadowColor(-1);
        }
    }

    public void setWeatherIcon(String str) {
        if (this.mWeatherIcon == null || TextUtils.isEmpty(str)) {
            this.mWeatherIcon.setVisibility(8);
            return;
        }
        this.mWeatherIcon.setVisibility(0);
        ko.a(this.mWeatherIcon, str);
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

    public void setRestrictLabel(CharSequence charSequence, CharSequence charSequence2) {
        if (this.mRestrictNum != null) {
            if (TextUtils.isEmpty(charSequence)) {
                this.mRestrictNum.setVisibility(8);
            } else {
                this.mRestrictNum.setText(charSequence);
                this.mRestrictNum.setVisibility(0);
            }
        }
        if (this.mRestrictLabel != null && !TextUtils.isEmpty(charSequence2)) {
            this.mRestrictLabel.setText(charSequence2);
        }
    }

    public void setRestrictContainerVisible(int i) {
        if (this.mRestrictContainer != null) {
            this.mRestrictContainer.setVisibility(i);
        }
    }

    public void setCutLineVisible(int i) {
        if (this.mCutLineView != null) {
            if (i == 0) {
                reSetContainerHeight(58);
            } else {
                reSetContainerHeight(48);
            }
            this.mCutLineView.setVisibility(i);
        }
    }

    public void setWidgetContainerVisible(int i) {
        if (this.mContentView != null) {
            if (i == 0) {
                this.mContentView.setBackgroundResource(R.drawable.map_widget_weather_bg);
            } else {
                this.mContentView.setBackgroundResource(0);
            }
            this.mContentView.setVisibility(i);
        }
    }

    private void reSetContainerHeight(int i) {
        if (this.mContentView != null) {
            LayoutParams layoutParams = this.mContentView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = bet.a(this.mContentView.getContext(), i);
            }
        }
    }
}
