package com.autonavi.minimap.agroup.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.IViewLayerExt;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;

public class AGroupProtocolAlertView extends FrameLayout implements IViewLayer, IViewLayerExt {
    private View mNegativeBtn;
    private View mPositiveBtn;

    public View getView() {
        return this;
    }

    public boolean isDismiss() {
        return false;
    }

    public boolean onBackPressed() {
        return true;
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void showBackground(boolean z) {
    }

    public AGroupProtocolAlertView(@NonNull Context context) {
        super(context);
        try {
            setBackgroundColor(Color.parseColor("#66000000"));
        } catch (IllegalArgumentException unused) {
            setBackgroundColor(-1);
        }
        setClickable(true);
        View inflate = LayoutInflater.from(context).inflate(R.layout.alert_agroup_protocol_layout, this, true);
        this.mNegativeBtn = inflate.findViewById(R.id.agroup_protocol_alert_disagree_btn);
        this.mPositiveBtn = inflate.findViewById(R.id.agroup_protocol_alert_agree_btn);
        cjr.a = new WeakReference<>(this);
    }

    public void setNegativeListener(OnClickListener onClickListener) {
        this.mNegativeBtn.setOnClickListener(onClickListener);
    }

    public void setPositiveListener(OnClickListener onClickListener) {
        this.mPositiveBtn.setOnClickListener(onClickListener);
    }
}
