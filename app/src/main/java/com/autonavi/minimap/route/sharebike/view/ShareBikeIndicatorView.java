package com.autonavi.minimap.route.sharebike.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

public class ShareBikeIndicatorView extends RelativeLayout implements OnClickListener {
    private static final int ANIM_DURATION_TIP_JUMP = 800;
    private static final int ANIM_DURATION_TIP_SHADOW = 600;
    public static final int NET_STATUS_AOS_FAIL = 3;
    public static final int NET_STATUS_NET_FAIL = 2;
    public static final int NET_STATUS_NO_BIKES = 4;
    public static final int NET_STATUS_SUC = 1;
    private AnimatorSet mAnimator;
    /* access modifiers changed from: private */
    public Object mAnimatorTarget;
    private ImageView mIndicatorView;
    public a mListener;
    /* access modifiers changed from: private */
    public LinearLayout mNetStatus;
    private ImageView mNetStatusRefreshIv;
    private TextView mNetStatusTv;
    private AnimatorSet mNetTextAnimator;
    /* access modifiers changed from: private */
    public TextView mPoiName;
    /* access modifiers changed from: private */
    public ImageView mTipPointShadowScale;

    public interface a {
        void a(View view);
    }

    public ShareBikeIndicatorView(Context context) {
        super(context);
        initView();
    }

    public ShareBikeIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public ShareBikeIndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        LayoutInflater.from(AMapPageUtil.getAppContext()).inflate(R.layout.sharebike_location_point_view, this, true);
        this.mNetStatus = (LinearLayout) findViewById(R.id.sharebike_net_status);
        this.mNetStatusRefreshIv = (ImageView) findViewById(R.id.sharebike_net_status_iv);
        this.mNetStatusTv = (TextView) findViewById(R.id.sharebike_net_status_tv);
        this.mIndicatorView = (ImageView) findViewById(R.id.sharebike_location_point_iv);
        this.mTipPointShadowScale = (ImageView) findViewById(R.id.sharebike_tip_shadow_scale);
        this.mPoiName = (TextView) findViewById(R.id.sharebike_center_poi_name);
        initAnimitor();
    }

    private void initAnimitor() {
        this.mNetTextAnimator = new AnimatorSet();
        this.mNetTextAnimator.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                if (ShareBikeIndicatorView.this.mAnimatorTarget == ShareBikeIndicatorView.this.mNetStatus) {
                    ShareBikeIndicatorView.this.mNetStatus.setAlpha(1.0f);
                    ShareBikeIndicatorView.this.mNetStatus.setScaleX(1.0f);
                    ShareBikeIndicatorView.this.mNetStatus.setScaleY(1.0f);
                    return;
                }
                if (ShareBikeIndicatorView.this.mAnimatorTarget == ShareBikeIndicatorView.this.mPoiName) {
                    ShareBikeIndicatorView.this.mPoiName.setAlpha(1.0f);
                    ShareBikeIndicatorView.this.mPoiName.setScaleX(1.0f);
                    ShareBikeIndicatorView.this.mPoiName.setScaleY(1.0f);
                }
            }
        });
    }

    public void playTipPinDownAnimator() {
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        prepareMoveEndAnimatorState();
        if (this.mAnimator == null) {
            AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
            PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, new float[]{0.0f, 5.0f, -20.0f, 0.0f, 0.0f});
            PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{1.0f, 1.0f, 1.0f, 1.1f, 1.0f});
            PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{1.0f, 0.9f, 1.0f, 1.1f, 1.0f});
            ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this.mIndicatorView, new PropertyValuesHolder[]{ofFloat, ofFloat2, ofFloat3}).setDuration(800);
            duration.setInterpolator(accelerateInterpolator);
            OvershootInterpolator overshootInterpolator = new OvershootInterpolator(1.2f);
            PropertyValuesHolder ofFloat4 = PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{1.0f, 0.0f});
            PropertyValuesHolder ofFloat5 = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{0.1f, 1.0f});
            PropertyValuesHolder ofFloat6 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{0.1f, 1.0f});
            ObjectAnimator duration2 = ObjectAnimator.ofPropertyValuesHolder(this.mTipPointShadowScale, new PropertyValuesHolder[]{ofFloat5, ofFloat6, ofFloat4}).setDuration(600);
            duration2.setInterpolator(overshootInterpolator);
            duration2.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    ShareBikeIndicatorView.this.mTipPointShadowScale.setVisibility(8);
                }
            });
            this.mAnimator = new AnimatorSet();
            this.mAnimator.play(duration).before(duration2);
            this.mAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationCancel(Animator animator) {
                    ShareBikeIndicatorView.this.mTipPointShadowScale.setAlpha(0.0f);
                    ShareBikeIndicatorView.this.mTipPointShadowScale.setScaleX(1.0f);
                    ShareBikeIndicatorView.this.mTipPointShadowScale.setScaleY(1.0f);
                    ShareBikeIndicatorView.this.mNetStatus.setAlpha(1.0f);
                    ShareBikeIndicatorView.this.mNetStatus.setScaleX(1.0f);
                    ShareBikeIndicatorView.this.mNetStatus.setScaleY(1.0f);
                }
            });
        }
        this.mAnimator.start();
    }

    /* access modifiers changed from: private */
    public void playNetStatusAnim(Object obj) {
        if (this.mNetTextAnimator != null && obj != null) {
            if (this.mNetTextAnimator.isRunning()) {
                this.mNetTextAnimator.cancel();
            }
            this.mAnimatorTarget = obj;
            this.mNetTextAnimator.play(getNetStatusTextAnim(obj));
            this.mNetTextAnimator.start();
        }
    }

    private ObjectAnimator getNetStatusTextAnim(Object obj) {
        return ObjectAnimator.ofPropertyValuesHolder(obj, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{0.6f, 1.0f}), PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{0.6f, 1.0f}), PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{0.0f, 1.0f})}).setDuration(300);
    }

    private void prepareMoveEndAnimatorState() {
        this.mNetStatus.setAlpha(0.0f);
        this.mTipPointShadowScale.setVisibility(0);
        this.mTipPointShadowScale.setAlpha(0.0f);
    }

    public void setNetStatus(int i) {
        NoDBClickUtil.a((View) this.mNetStatus, (OnClickListener) null);
        if (i == 1) {
            this.mNetStatus.setVisibility(8);
            this.mNetStatusRefreshIv.setVisibility(8);
            this.mNetStatusTv.setVisibility(8);
        } else if (i == 2) {
            this.mNetStatus.setVisibility(0);
            this.mNetStatusRefreshIv.setVisibility(0);
            this.mNetStatusTv.setVisibility(0);
            this.mPoiName.setVisibility(8);
            this.mNetStatusTv.setText(AMapPageUtil.getAppContext().getString(R.string.sharebike_net_fail_msg));
            this.mNetStatusTv.setTextColor(getResources().getColor(R.color.f_c_6));
            this.mNetStatus.setTag(this.mNetStatusTv);
            NoDBClickUtil.a((View) this.mNetStatus, (OnClickListener) this);
            aho.a(new Runnable() {
                public final void run() {
                    ShareBikeIndicatorView.this.playNetStatusAnim(ShareBikeIndicatorView.this.mNetStatus);
                }
            }, 800);
        } else if (i == 3) {
            this.mNetStatus.setVisibility(0);
            this.mNetStatusRefreshIv.setVisibility(8);
            this.mNetStatusTv.setVisibility(0);
            this.mPoiName.setVisibility(8);
            this.mNetStatusTv.setText(AMapPageUtil.getAppContext().getString(R.string.sharebike_net_aos_fail_msg));
            this.mNetStatusTv.setTextColor(getResources().getColor(R.color.f_c_2));
            playNetStatusAnim(this.mNetStatus);
        } else {
            if (i == 4) {
                this.mNetStatus.setVisibility(0);
                this.mNetStatusRefreshIv.setVisibility(8);
                this.mNetStatusTv.setVisibility(0);
                this.mPoiName.setVisibility(8);
                this.mNetStatusTv.setText(AMapPageUtil.getAppContext().getString(R.string.sharebike_net_no_bikes_msg));
                this.mNetStatusTv.setTextColor(getResources().getColor(R.color.f_c_2));
                playNetStatusAnim(this.mNetStatus);
            }
        }
    }

    public void showPoiNameViewWithAnim(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mNetStatus.setVisibility(8);
            this.mPoiName.setVisibility(0);
            this.mPoiName.setText(str);
            playNetStatusAnim(this.mPoiName);
        }
    }

    public void dismissPoiNameView() {
        this.mPoiName.setVisibility(8);
    }

    public Rect getCenterPoiRect() {
        int bottom = this.mPoiName.getBottom() + agn.a(getContext(), 8.0f);
        return new Rect(this.mPoiName.getLeft(), this.mPoiName.getTop(), this.mPoiName.getRight(), bottom);
    }

    public void onClick(View view) {
        if (this.mListener != null) {
            this.mListener.a(view);
        }
    }

    public void setOnIndicatorRefreshClickListener(a aVar) {
        this.mListener = aVar;
    }
}
