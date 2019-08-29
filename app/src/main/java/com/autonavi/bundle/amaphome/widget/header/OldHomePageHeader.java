package com.autonavi.bundle.amaphome.widget.header;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.MiniSearchFrameWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.user.UserCenterMapWidget;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView.a;
import com.autonavi.minimap.R;

public class OldHomePageHeader extends AbstractMapWidget<OldHomePageHeaderPresenter> {
    private MiniSearchFrameWidget mMiniSearchMapWidget;
    private final bid mPageContext;
    private ImageView mScanViewBtn;
    private UserCenterMapWidget mUserCenterMapWidget;
    private aqv mVUIGuideTipViewLayer = new aqv(this.mPageContext, this.mVoiceEmojiView);
    private VUIEmojiView mVoiceEmojiView;

    public View getVoiceBtn() {
        return null;
    }

    public OldHomePageHeader(bid bid) {
        super(bid.getContext());
        this.mPageContext = bid;
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, R.layout.old_home_page_header_widget_layout);
        LinearLayout linearLayout = (LinearLayout) loadLayoutRes.findViewById(R.id.user_and_search_container);
        if (linearLayout != null) {
            addUserIcon(linearLayout, context);
            addMiniSearch(linearLayout, context);
        }
        this.mVoiceEmojiView = (VUIEmojiView) loadLayoutRes.findViewById(R.id.btn_emoji);
        this.mScanViewBtn = (ImageView) loadLayoutRes.findViewById(R.id.btn_qrscan);
        this.mVoiceEmojiView.setOnVuiEmojiViewClickListener(new a() {
            public void onClick(View view) {
                ((OldHomePageHeaderPresenter) OldHomePageHeader.this.mPresenter).addVoiceClickLog();
            }
        });
        this.mVoiceEmojiView.setShowInit(true);
        this.mVoiceEmojiView.setVisibility(0);
        return loadLayoutRes;
    }

    public aqv getVUIGuideTipViewLayer() {
        return this.mVUIGuideTipViewLayer;
    }

    public ImageView getScanViewBtn() {
        return this.mScanViewBtn;
    }

    public bid getPageContext() {
        return this.mPageContext;
    }

    private void addUserIcon(LinearLayout linearLayout, Context context) {
        this.mUserCenterMapWidget = new UserCenterMapWidget(context);
        this.mUserCenterMapWidget.hideBgView();
        this.mUserCenterMapWidget.updateMineIconView(bet.a(context, 57), R.drawable.icon_c28);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 19;
        linearLayout.addView(this.mUserCenterMapWidget.getContentView(), layoutParams);
    }

    public UserCenterMapWidget getUserCenterMapWidget() {
        return this.mUserCenterMapWidget;
    }

    public MiniSearchFrameWidget getMiniSearchMapWidget() {
        return this.mMiniSearchMapWidget;
    }

    private void addMiniSearch(LinearLayout linearLayout, Context context) {
        this.mMiniSearchMapWidget = new MiniSearchFrameWidget(context);
        this.mMiniSearchMapWidget.hideBgView();
        this.mMiniSearchMapWidget.hideSearchIconView();
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 19;
        linearLayout.addView(this.mMiniSearchMapWidget.getContentView(), layoutParams);
    }

    public void onInit(Context context) {
        aqu aqu = a.a;
        if (aqu != null) {
            aqu.a(context, this.mMiniSearchMapWidget.getHotTextView());
        }
    }
}
