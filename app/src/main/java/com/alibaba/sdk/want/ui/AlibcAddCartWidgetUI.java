package com.alibaba.sdk.want.ui;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.sdk.trade.component.cart.AlibcCartParams;
import com.alibaba.sdk.trade.container.utils.AlibcComponentLog;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;
import com.alibaba.sdk.want.AlibcWantConstant;
import com.alibaba.sdk.want.AlibcWantEventDispatch;
import com.alibaba.sdk.want.widget.IWantWidget;
import com.alipay.mobile.common.share.widget.ResUtils;
import java.util.ArrayList;
import java.util.List;

public class AlibcAddCartWidgetUI implements OnClickListener, AlibcIWidgetUI {
    /* access modifiers changed from: private */
    public final int CART_LOGO_ID;
    private final String TAG = "AlibcAddCartWidgetUI";
    /* access modifiers changed from: private */
    public final int TAOBAO_LOGO_ID;
    /* access modifiers changed from: private */
    public boolean isAddingCart;
    /* access modifiers changed from: private */
    public Activity mActivity;
    /* access modifiers changed from: private */
    public Runnable mAddingCartAnimRunnable;
    private AlibcAddcartResultDialog mDialog;
    /* access modifiers changed from: private */
    public ImageButton mIBtnLogo;
    private List<String> mListNoToast = new ArrayList<String>() {
        {
            add(AlibcComponentTrack.ERRNO_WANT_CART_NO_PACKEGE);
            add(AlibcComponentTrack.ERRNO_COMPONENT_CART_CANCEL_LOGIN);
            add(AlibcComponentTrack.ERRNO_COMPONENT_CART_CANCEL_AUTH);
        }
    };
    /* access modifiers changed from: private */
    public LinearLayout mLlLogo;
    private Animation mLogoClickAnim;
    /* access modifiers changed from: private */
    public Animation mLogoShowAnim;
    /* access modifiers changed from: private */
    public ViewGroup mRootView;
    /* access modifiers changed from: private */
    public AlibcCartParams mShowData;
    /* access modifiers changed from: private */
    public TextView mTvTips;
    /* access modifiers changed from: private */
    public IWantWidget mWidget;

    class AddingCartAnimRunnable implements Runnable {
        /* access modifiers changed from: private */
        public int logo = AlibcAddCartWidgetUI.this.TAOBAO_LOGO_ID;
        private Animation mHideAnim;

        AddingCartAnimRunnable() {
            if (AlibcAddCartWidgetUI.this.mActivity != null && this.mHideAnim == null) {
                this.mHideAnim = AnimationUtils.loadAnimation(AlibcAddCartWidgetUI.this.mActivity, AlibcAddCartWidgetUI.this.mActivity.getResources().getIdentifier("alibc_want_anim_adding_cart_logo_hide", ResUtils.ANIM, AlibcAddCartWidgetUI.this.mActivity.getPackageName()));
                this.mHideAnim.setAnimationListener(new AnimationListener(AlibcAddCartWidgetUI.this) {
                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        if (AlibcAddCartWidgetUI.this.mActivity != null) {
                            if (AddingCartAnimRunnable.this.logo == AlibcAddCartWidgetUI.this.TAOBAO_LOGO_ID) {
                                AddingCartAnimRunnable.this.logo = AlibcAddCartWidgetUI.this.CART_LOGO_ID;
                            } else {
                                AddingCartAnimRunnable.this.logo = AlibcAddCartWidgetUI.this.TAOBAO_LOGO_ID;
                            }
                            AlibcAddCartWidgetUI.this.mIBtnLogo.startAnimation(AnimationUtils.loadAnimation(AlibcAddCartWidgetUI.this.mActivity, AlibcAddCartWidgetUI.this.mActivity.getResources().getIdentifier("alibc_want_anim_adding_cart_logo_show", ResUtils.ANIM, AlibcAddCartWidgetUI.this.mActivity.getPackageName())));
                            AlibcAddCartWidgetUI.this.mIBtnLogo.setImageDrawable(AlibcAddCartWidgetUI.this.mActivity.getResources().getDrawable(AddingCartAnimRunnable.this.logo));
                            AlibcAddCartWidgetUI.this.mRootView.postDelayed(AlibcAddCartWidgetUI.this.mAddingCartAnimRunnable, 500);
                        }
                    }
                });
            }
        }

        public void run() {
            AlibcComponentLog.d("AlibcAddCartWidgetUI", "add cart anim");
            AlibcAddCartWidgetUI.this.mIBtnLogo.startAnimation(this.mHideAnim);
        }
    }

    class UpdateUIRunable implements Runnable {
        private final String mTip;

        public UpdateUIRunable(String str) {
            this.mTip = str;
        }

        public void run() {
            AlibcAddCartWidgetUI.this.stopAddingCartAnim();
            if (TextUtils.isEmpty(this.mTip)) {
                AlibcAddCartWidgetUI.this.hideWantWidget();
                return;
            }
            if (AlibcAddCartWidgetUI.this.mRootView.getVisibility() == 8) {
                if (AlibcAddCartWidgetUI.this.mLlLogo != null) {
                    AlibcAddCartWidgetUI.this.mLlLogo.startAnimation(AlibcAddCartWidgetUI.this.mLogoShowAnim);
                }
                AlibcAddCartWidgetUI.this.mRootView.setVisibility(0);
                AlibcWantEventDispatch.onEvent(AlibcWantConstant.WANT_VIEW_VISIBLE, null);
            }
            AlibcAddCartWidgetUI.this.updateTips(this.mTip);
        }
    }

    public AlibcAddCartWidgetUI(Activity activity, ViewGroup viewGroup) {
        this.mActivity = activity;
        this.mRootView = viewGroup;
        this.isAddingCart = false;
        this.TAOBAO_LOGO_ID = this.mActivity.getResources().getIdentifier("alibc_want_taobao_icon", ResUtils.DRAWABLE, this.mActivity.getPackageName());
        this.CART_LOGO_ID = this.mActivity.getResources().getIdentifier("alibc_want_cart_icon", ResUtils.DRAWABLE, this.mActivity.getPackageName());
    }

    public void setWidget(IWantWidget iWantWidget) {
        this.mWidget = iWantWidget;
    }

    public int getWidgetUIVisible() {
        if (this.mRootView != null) {
            return this.mRootView.getVisibility();
        }
        return 8;
    }

    public void updateUI(AlibcCartParams alibcCartParams) {
        if (!this.mActivity.isFinishing() && !this.isAddingCart) {
            String tip = getTip(alibcCartParams);
            showUserTrack(tip, alibcCartParams);
            if (this.mRootView.getVisibility() == 8) {
                initWantWidgetUI(tip);
            } else {
                updateWantWidgetUI(tip);
            }
        }
    }

    public void removeData() {
        if (this.mRootView != null && this.mRootView.getVisibility() == 0) {
            if (this.mIBtnLogo != null) {
                this.mIBtnLogo.setClickable(false);
            }
            if (this.mTvTips != null) {
                this.mTvTips.setClickable(false);
            }
            updateWantWidgetUI(null);
        }
    }

    public void updateResult(String str, String str2) {
        this.isAddingCart = false;
        if (TextUtils.equals(str, AlibcWantConstant.WANT_ADDCART_SUCCESS)) {
            AlibcComponentTrack.sentUserTrack(2201, AlibcComponentTrack.UT_CONTROL_NAME_WANT_DISAPPEAR, null, null);
        }
        stopAddingCartAnimWithRunable();
        if (canShowToast(str)) {
            showToast(str, str2);
        }
    }

    public void destroy() {
        stopAddingCartAnimWithRunable();
        if (this.mDialog != null) {
            this.mDialog.destroy();
        }
        this.isAddingCart = false;
        this.mDialog = null;
        this.mRootView = null;
        this.mActivity = null;
    }

    private void initWantWidgetUI(String str) {
        if (str == null || str.isEmpty()) {
            AlibcComponentLog.d("AlibcAddCartWidgetUI", "tip is null, ignore update data");
            AlibcWantEventDispatch.onEvent(AlibcWantConstant.WANT_ADDCART_FAIL, "tip is null, ignore update data");
            return;
        }
        if (this.mRootView.getVisibility() != 0) {
            this.mRootView.setVisibility(0);
            AlibcWantEventDispatch.onEvent(AlibcWantConstant.WANT_VIEW_VISIBLE, null);
        }
        initLogoView();
        initLogoLayout();
        updateTips(str);
        this.mRootView.invalidate();
    }

    private void updateWantWidgetUI(String str) {
        this.mRootView.post(new UpdateUIRunable(str));
    }

    private String getTip(AlibcCartParams alibcCartParams) {
        if (alibcCartParams == null || alibcCartParams.mItemID == null || alibcCartParams.mItemID.isEmpty()) {
            return null;
        }
        return alibcCartParams.mTips;
    }

    private void showUserTrack(String str, AlibcCartParams alibcCartParams) {
        if (str == null) {
            return;
        }
        if (this.mShowData == null || !TextUtils.equals(alibcCartParams.mTips, this.mShowData.mTips) || !TextUtils.equals(alibcCartParams.mItemID, this.mShowData.mItemID)) {
            this.mShowData = alibcCartParams;
            AlibcComponentTrack.sentUserTrack(2201, AlibcComponentTrack.UT_CONTROL_NAME_WANT_CART, this.mShowData.mYbhpssParams, this.mShowData.mItemID);
        }
    }

    public void onClick(View view) {
        if ((view.getId() == this.mActivity.getResources().getIdentifier("ibtn_addcart_logo", "id", this.mActivity.getPackageName()) || view.getId() == this.mActivity.getResources().getIdentifier("tv_goods_tips", "id", this.mActivity.getPackageName())) && !this.isAddingCart) {
            this.isAddingCart = true;
            AlibcComponentTrack.sentUserTrack(2101, AlibcComponentTrack.UT_CONTROL_NAME_WANT_CART, this.mShowData.mYbhpssParams, this.mShowData.mItemID);
            this.mWidget.addData();
            startAddCartClickAnim();
        }
    }

    private void initLogoView() {
        if (this.mIBtnLogo == null) {
            this.mIBtnLogo = (ImageButton) this.mRootView.findViewById(this.mActivity.getResources().getIdentifier("ibtn_addcart_logo", "id", this.mActivity.getPackageName()));
            this.mIBtnLogo.setOnClickListener(this);
        }
        this.mIBtnLogo.clearAnimation();
        this.mIBtnLogo.setImageDrawable(this.mActivity.getResources().getDrawable(this.TAOBAO_LOGO_ID));
    }

    private void initLogoLayout() {
        if (this.mLlLogo == null) {
            this.mLlLogo = (LinearLayout) this.mRootView.findViewById(this.mActivity.getResources().getIdentifier("ll_addcart_logo", "id", this.mActivity.getPackageName()));
        }
        if (this.mLogoShowAnim == null) {
            this.mLogoShowAnim = AnimationUtils.loadAnimation(this.mActivity, this.mActivity.getResources().getIdentifier("alibc_want_anim_show_logo", ResUtils.ANIM, this.mActivity.getPackageName()));
        }
        this.mLlLogo.startAnimation(this.mLogoShowAnim);
    }

    /* access modifiers changed from: private */
    public void updateTips(String str) {
        if (this.mTvTips == null) {
            this.mTvTips = (TextView) this.mRootView.findViewById(this.mActivity.getResources().getIdentifier("tv_goods_tips", "id", this.mActivity.getPackageName()));
            this.mTvTips.setOnClickListener(this);
        }
        this.mTvTips.startAnimation(AnimationUtils.loadAnimation(this.mActivity, this.mActivity.getResources().getIdentifier("alibc_want_anim_show_tip", ResUtils.ANIM, this.mActivity.getPackageName())));
        this.mTvTips.setText(str);
        if (this.mTvTips.getVisibility() != 0) {
            this.mTvTips.setVisibility(0);
        }
        if (this.mIBtnLogo != null) {
            this.mIBtnLogo.setClickable(true);
        }
        if (this.mTvTips != null) {
            this.mTvTips.setClickable(true);
        }
    }

    private void startAddCartClickAnim() {
        if (this.mLlLogo == null) {
            this.mLlLogo = (LinearLayout) this.mRootView.findViewById(this.mActivity.getResources().getIdentifier("ll_addcart_logo", "id", this.mActivity.getPackageName()));
        }
        if (this.mLogoClickAnim == null) {
            this.mLogoClickAnim = AnimationUtils.loadAnimation(this.mActivity, this.mActivity.getResources().getIdentifier("alibc_want_anim_logo_click", ResUtils.ANIM, this.mActivity.getPackageName()));
            this.mLogoClickAnim.setAnimationListener(new AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    AlibcAddCartWidgetUI.this.startAddingCartAnim();
                }
            });
        }
        this.mLlLogo.startAnimation(this.mLogoClickAnim);
        this.mLlLogo.invalidate();
    }

    /* access modifiers changed from: private */
    public void startAddingCartAnim() {
        if (this.mRootView != null && this.isAddingCart) {
            if (this.mAddingCartAnimRunnable == null) {
                this.mAddingCartAnimRunnable = new AddingCartAnimRunnable();
            }
            this.mRootView.post(this.mAddingCartAnimRunnable);
        }
    }

    /* access modifiers changed from: private */
    public void stopAddingCartAnim() {
        if (!(this.mLlLogo == null || this.mActivity == null)) {
            this.mLlLogo.clearAnimation();
        }
        if (!(this.mAddingCartAnimRunnable == null || this.mRootView == null)) {
            this.mRootView.removeCallbacks(this.mAddingCartAnimRunnable);
        }
        if (this.mIBtnLogo != null && this.mActivity != null) {
            this.mIBtnLogo.clearAnimation();
            this.mIBtnLogo.setImageDrawable(this.mActivity.getResources().getDrawable(this.TAOBAO_LOGO_ID));
        }
    }

    private void stopAddingCartAnimWithRunable() {
        this.mRootView.post(new Runnable() {
            public void run() {
                AlibcAddCartWidgetUI.this.stopAddingCartAnim();
            }
        });
    }

    /* access modifiers changed from: private */
    public void hideWantWidget() {
        if (this.mTvTips != null && this.mTvTips.getVisibility() != 4) {
            this.isAddingCart = true;
            this.mTvTips.setVisibility(4);
            Animation loadAnimation = AnimationUtils.loadAnimation(this.mActivity, this.mActivity.getResources().getIdentifier("alibc_want_anim_hide_tip", ResUtils.ANIM, this.mActivity.getPackageName()));
            loadAnimation.setAnimationListener(new AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    if (AlibcAddCartWidgetUI.this.mIBtnLogo != null) {
                        AlibcAddCartWidgetUI.this.mIBtnLogo.setClickable(false);
                    }
                    if (AlibcAddCartWidgetUI.this.mTvTips != null) {
                        AlibcAddCartWidgetUI.this.mTvTips.setClickable(false);
                    }
                }

                public void onAnimationEnd(Animation animation) {
                    Animation loadAnimation = AnimationUtils.loadAnimation(AlibcAddCartWidgetUI.this.mActivity, AlibcAddCartWidgetUI.this.mActivity.getResources().getIdentifier("alibc_want_anim_hide_logo", ResUtils.ANIM, AlibcAddCartWidgetUI.this.mActivity.getPackageName()));
                    loadAnimation.setAnimationListener(new AnimationListener() {
                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationStart(Animation animation) {
                        }

                        public void onAnimationEnd(Animation animation) {
                            AlibcComponentLog.d("AlibcAddCartWidgetUI", "add cart want hided");
                            AlibcAddCartWidgetUI.this.isAddingCart = false;
                            AlibcAddCartWidgetUI.this.mShowData = null;
                            if (AlibcAddCartWidgetUI.this.mRootView != null && AlibcAddCartWidgetUI.this.mRootView.getVisibility() == 0) {
                                AlibcAddCartWidgetUI.this.mRootView.setVisibility(8);
                                AlibcWantEventDispatch.onEvent(AlibcWantConstant.WANT_VIEW_GONE, null);
                            }
                            if (AlibcAddCartWidgetUI.this.mWidget != null) {
                                AlibcAddCartWidgetUI.this.mWidget.removeDataEnd();
                            }
                        }
                    });
                    if (AlibcAddCartWidgetUI.this.mLlLogo != null) {
                        AlibcAddCartWidgetUI.this.mLlLogo.startAnimation(loadAnimation);
                    }
                }
            });
            this.mTvTips.startAnimation(loadAnimation);
        }
    }

    private boolean canShowToast(String str) {
        for (String equals : this.mListNoToast) {
            if (TextUtils.equals(equals, str)) {
                return false;
            }
        }
        return true;
    }

    private void showToast(String str, String str2) {
        this.mDialog = new AlibcAddcartResultDialog(this.mActivity, str, str2);
        this.mDialog.show();
    }
}
