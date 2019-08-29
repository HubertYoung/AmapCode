package com.autonavi.bundle.scenicarea.scenicplay;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.autonavi.bundle.life.api.api.IScenicPlayCloseCallback;
import com.autonavi.bundle.life.api.api.IScenicPlaySelectCallback;
import com.autonavi.minimap.R;

public class ScenicPlayView extends RelativeLayout {
    /* access modifiers changed from: private */
    public boolean mIsSelected = false;
    private ImageView mPlayIcon;
    private ViewGroup mRootView;

    public ScenicPlayView(Context context) {
        super(context);
        init(context);
    }

    public ScenicPlayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.scenic_paly_view, this);
        initView();
        setVisible(false);
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootView = (ViewGroup) findViewById(R.id.scenic_play_name_layout);
        this.mPlayIcon = (ImageView) this.mRootView.findViewById(R.id.scenic_play_icon);
    }

    public void initRootView(final IScenicPlaySelectCallback iScenicPlaySelectCallback) {
        this.mIsSelected = false;
        this.mPlayIcon.setImageResource(R.drawable.map_widget_guide_play_normal);
        this.mRootView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!ScenicPlayView.this.mIsSelected) {
                    ScenicPlayView.this.setPlaySelected();
                    iScenicPlaySelectCallback.a(true);
                    return;
                }
                ScenicPlayView.this.setPlayUnselected();
                iScenicPlaySelectCallback.a(false);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setPlayUnselected() {
        this.mPlayIcon.setImageResource(R.drawable.map_widget_guide_play_normal);
        this.mIsSelected = false;
    }

    /* access modifiers changed from: private */
    public void setPlaySelected() {
        this.mPlayIcon.setImageResource(R.drawable.map_widget_guide_play_selected);
        this.mIsSelected = true;
    }

    public void closeScenicPlayWidget(boolean z, IScenicPlayCloseCallback iScenicPlayCloseCallback) {
        if (z && this.mIsSelected) {
            setPlayUnselected();
        }
        iScenicPlayCloseCallback.a();
    }

    public void setVisible(boolean z) {
        int i = z ? 0 : 8;
        if (getVisibility() != i) {
            setVisibility(i);
        }
    }
}
