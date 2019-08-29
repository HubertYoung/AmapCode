package com.autonavi.minimap.route.sharebike.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;

public class ShareBikeRidingPopView extends RelativeLayout {
    /* access modifiers changed from: private */
    public boolean doAnimation;
    private LinearLayout mActionSelectList;
    private TextView mHelpTextView;
    /* access modifiers changed from: private */
    public a mListener;
    /* access modifiers changed from: private */
    public View mMask;
    private TextView mTrack;
    private TextView mWallet;

    public interface a {
        void a();

        void b();

        void c();
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.doAnimation) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public ShareBikeRidingPopView(Context context) {
        this(context, null);
    }

    public ShareBikeRidingPopView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShareBikeRidingPopView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.doAnimation = false;
        initView();
        initListener();
    }

    public void setListener(a aVar) {
        this.mListener = aVar;
    }

    private void initListener() {
        this.mTrack.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (ShareBikeRidingPopView.this.mListener != null) {
                    ShareBikeRidingPopView.this.mListener.a();
                }
            }
        });
        this.mWallet.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (ShareBikeRidingPopView.this.mListener != null) {
                    ShareBikeRidingPopView.this.mListener.b();
                }
            }
        });
        this.mMask.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ShareBikeRidingPopView.this.dismissSelectView();
            }
        });
        NoDBClickUtil.a((View) this.mHelpTextView, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (ShareBikeRidingPopView.this.mListener != null) {
                    ShareBikeRidingPopView.this.mListener.c();
                }
            }
        });
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.share_bike_riding_pop_layout, this, true);
        this.mTrack = (TextView) inflate.findViewById(R.id.tv_sharebike_riding_track);
        this.mWallet = (TextView) inflate.findViewById(R.id.tv_sharebike_riding_mywallet);
        this.mHelpTextView = (TextView) inflate.findViewById(R.id.tv_sharebike_riding_help);
        this.mMask = inflate.findViewById(R.id.sharebike_mask);
        this.mActionSelectList = (LinearLayout) findViewById(R.id.sharebike_action_select_list);
    }

    public void showSelectView() {
        setVisibility(0);
        this.mMask.setVisibility(0);
        if (this.mActionSelectList != null) {
            this.mActionSelectList.setVisibility(0);
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 1.0f, 1, 0.0f);
            scaleAnimation.setDuration(400);
            this.mActionSelectList.setAnimation(scaleAnimation);
            scaleAnimation.startNow();
        }
    }

    public synchronized void dismissSelectView() {
        if (!this.doAnimation) {
            setVisibility(0);
            this.mActionSelectList.setVisibility(8);
            this.doAnimation = true;
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, 1.0f, 1, 0.0f);
            scaleAnimation.setDuration(400);
            scaleAnimation.setAnimationListener(new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    ShareBikeRidingPopView.this.setVisibility(8);
                    ShareBikeRidingPopView.this.mMask.setVisibility(8);
                    ShareBikeRidingPopView.this.doAnimation = false;
                }
            });
            this.mActionSelectList.setAnimation(scaleAnimation);
            scaleAnimation.startNow();
        }
    }
}
