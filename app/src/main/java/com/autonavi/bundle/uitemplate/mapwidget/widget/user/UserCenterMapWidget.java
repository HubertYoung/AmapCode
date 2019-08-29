package com.autonavi.bundle.uitemplate.mapwidget.widget.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.CircleImageView;

public class UserCenterMapWidget extends AbstractMapWidget<UserCenterWidgetPresenter> {
    /* access modifiers changed from: private */
    public int mDefaultMineIconRes = R.drawable.avatar_normal;
    private ImageView mSmallDotIcon;
    private FrameLayout mUserCenterContainer;
    /* access modifiers changed from: private */
    public CircleImageView mUserHeadIcon;
    private RelativeLayout mUserLevelLayout;
    private TextView mUserLevelText;

    public UserCenterMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, R.layout.map_widget_user_center);
        loadLayoutRes.setContentDescription(context.getResources().getString(R.string.ctdes_text_user));
        this.mUserCenterContainer = (FrameLayout) loadLayoutRes.findViewById(R.id.user_center_container);
        this.mUserHeadIcon = (CircleImageView) loadLayoutRes.findViewById(R.id.mine_icon);
        this.mSmallDotIcon = (ImageView) loadLayoutRes.findViewById(R.id.mine_dot_icon);
        this.mUserLevelLayout = (RelativeLayout) loadLayoutRes.findViewById(R.id.mine_level_layout);
        this.mUserLevelText = (TextView) loadLayoutRes.findViewById(R.id.mine_level_text);
        return loadLayoutRes;
    }

    public void hideBgView() {
        if (this.mContentView != null) {
            this.mContentView.findViewById(R.id.bg_view).setVisibility(8);
        }
    }

    public void updateMineIconView(int i, int i2) {
        this.mDefaultMineIconRes = i2;
        if (this.mUserHeadIcon != null) {
            this.mUserHeadIcon.setImageResource(this.mDefaultMineIconRes);
        }
        if (this.mUserCenterContainer != null) {
            this.mUserCenterContainer.setLayoutParams(new LayoutParams(-1, i));
        }
        LayoutParams layoutParams = (LayoutParams) this.mSmallDotIcon.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.topMargin = 0;
            layoutParams.rightMargin = 0;
        }
    }

    public void updateUserHeadIcon(final String str, final bkf bkf) {
        if (TextUtils.isEmpty(str)) {
            setDefaultIconForHeadIcon();
            return;
        }
        if (this.mUserHeadIcon != null) {
            this.mUserHeadIcon.setVisibility(0);
            this.mUserHeadIcon.post(new Runnable() {
                public void run() {
                    ko.a(UserCenterMapWidget.this.mUserHeadIcon, str, null, UserCenterMapWidget.this.mDefaultMineIconRes, bkf);
                }
            });
        }
    }

    public void updateUserLevel(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mUserLevelLayout.setVisibility(8);
            return;
        }
        this.mUserLevelLayout.setVisibility(0);
        this.mUserLevelText.setText("Lv.".concat(String.valueOf(str)));
    }

    public void setDefaultIconForHeadIcon() {
        this.mUserHeadIcon.setImageResource(this.mDefaultMineIconRes);
    }

    public void setSmallDotVisibility(int i) {
        if (this.mSmallDotIcon != null) {
            this.mSmallDotIcon.setVisibility(i);
        }
    }

    public boolean isSmallDotVisibility() {
        if (this.mSmallDotIcon == null || this.mSmallDotIcon.getVisibility() != 0) {
            return false;
        }
        return true;
    }
}
