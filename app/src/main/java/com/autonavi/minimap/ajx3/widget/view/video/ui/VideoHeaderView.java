package com.autonavi.minimap.ajx3.widget.view.video.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;

public class VideoHeaderView extends LinearLayout implements OnClickListener {
    private FullScreenToggleListener mFullScreenToggleListener;
    protected boolean mNormalStateShowTitle = true;
    protected ImageView mVideoFullScreenBackView;
    protected TextView mVideoTitleView;

    public VideoHeaderView(Context context) {
        super(context);
        initWidgetView(context);
    }

    public VideoHeaderView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initWidgetView(context);
    }

    public VideoHeaderView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initWidgetView(context);
    }

    /* access modifiers changed from: protected */
    public void initWidgetView(Context context) {
        setOrientation(0);
        setVisibility(8);
        inflate(context, getVideoTitleViewResLayoutId(), this);
        this.mVideoFullScreenBackView = (ImageView) findViewById(R.id.vp_video_fullScreen_back);
        this.mVideoTitleView = (TextView) findViewById(R.id.vp_video_title);
        this.mVideoFullScreenBackView.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public int getVideoTitleViewResLayoutId() {
        return R.layout.vp_layout_header;
    }

    public void onClick(View view) {
        if (this.mFullScreenToggleListener != null) {
            this.mFullScreenToggleListener.onExitFullScreen();
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.mVideoTitleView.setText(charSequence);
        }
    }

    public void setFullScreenToggleListener(FullScreenToggleListener fullScreenToggleListener) {
        this.mFullScreenToggleListener = fullScreenToggleListener;
    }

    public void onChangeVideoHeaderViewState(int i, boolean z) {
        if (!z) {
            Utils.hideViewIfNeed(this);
        } else if (ScreenState.isFullScreen(i)) {
            Utils.showViewIfNeed(this);
            Utils.showViewIfNeed(this.mVideoFullScreenBackView);
        } else if (!ScreenState.isNormal(i)) {
            Utils.hideViewIfNeed(this);
        } else if (this.mNormalStateShowTitle) {
            Utils.showViewIfNeed(this);
            Utils.hideViewIfNeed(this.mVideoFullScreenBackView);
        } else {
            Utils.hideViewIfNeed(this);
        }
    }

    public void toggleFullScreenBackViewVisibility(boolean z) {
        if (z) {
            Utils.showViewIfNeed(this.mVideoFullScreenBackView);
        } else {
            Utils.hideViewIfNeed(this.mVideoFullScreenBackView);
        }
    }
}
