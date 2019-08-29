package com.autonavi.minimap.ajx3.widget.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.PointTipViewProperty;
import java.lang.ref.WeakReference;

public class PointTipView extends RelativeLayout implements ViewExtension {
    public static int TYPE_HAS_TIME = 2;
    public static int TYPE_NO_TIME = 1;
    private int arrowId = R.drawable.taxi_start_point_select_tip_call_taxi;
    private IAjxContext mAjxContext;
    private String mContent = "";
    private LoadingHandler mHandler = new LoadingHandler(this);
    private boolean mHasArrow = true;
    private boolean mHasTip = true;
    /* access modifiers changed from: private */
    public final BaseProperty mProperty;
    private View mTipDivider;
    private AnimatorSet mTipEndMoveAnimator;
    private AnimatorSet mTipHideAnimator;
    /* access modifiers changed from: private */
    public ImageView mTipPointBelowView;
    /* access modifiers changed from: private */
    public ImageView mTipPointShadowScale;
    private AnimatorSet mTipShowAnimator;
    /* access modifiers changed from: private */
    public View mTipStatusContainer;
    private TextView mTipStatusInfo;
    private TextView mTipStatusTime;
    private TextView mTipStatusUnit;
    private int mType = TYPE_NO_TIME;

    static class LoadingHandler extends Handler {
        private static final int DURATION = 300;
        private static final int MSG_UPDATE = 16;
        private int index = 0;
        private WeakReference<PointTipView> reference;

        LoadingHandler(PointTipView pointTipView) {
            this.reference = new WeakReference<>(pointTipView);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (this.reference != null) {
                PointTipView pointTipView = (PointTipView) this.reference.get();
                if (pointTipView != null) {
                    switch (this.index % 3) {
                        case 0:
                            pointTipView.updateTipTime(".  ");
                            break;
                        case 1:
                            pointTipView.updateTipTime(".. ");
                            break;
                        case 2:
                            pointTipView.updateTipTime("...");
                            break;
                    }
                    this.index++;
                    sendEmptyMessageDelayed(16, 300);
                }
            }
        }
    }

    public PointTipView(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mProperty = createProperty();
        initView(iAjxContext.getNativeContext());
    }

    private PointTipViewProperty createProperty() {
        return new PointTipViewProperty(this, this.mAjxContext);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    private void initView(Context context) {
        RelativeLayout relativeLayout = (RelativeLayout) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.point_select_tip, this, true);
        this.mTipStatusContainer = relativeLayout.findViewById(R.id.tip_status_container);
        this.mTipStatusInfo = (TextView) relativeLayout.findViewById(R.id.tip_status_info);
        this.mTipStatusContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if ((PointTipView.this.mProperty instanceof PointTipViewProperty) && ((PointTipViewProperty) PointTipView.this.mProperty).hasClickTip()) {
                    ((PointTipViewProperty) PointTipView.this.mProperty).invokeClickTip();
                }
            }
        });
        this.mTipStatusTime = (TextView) relativeLayout.findViewById(R.id.tip_status_time);
        this.mTipStatusUnit = (TextView) relativeLayout.findViewById(R.id.tip_status_unit);
        this.mTipDivider = relativeLayout.findViewById(R.id.tip_divider);
        this.mTipPointBelowView = (ImageView) relativeLayout.findViewById(R.id.tip_marker_below);
        this.mTipPointShadowScale = (ImageView) relativeLayout.findViewById(R.id.tip_shadow_scale);
        setTipVisible(false);
        setTipTimeVisible(false);
    }

    public void hideTipWithAnimation() {
        if (this.mTipShowAnimator != null && this.mTipShowAnimator.isRunning()) {
            this.mTipShowAnimator.cancel();
        }
        if (this.mTipHideAnimator == null) {
            PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{1.0f, 0.6f});
            PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{1.0f, 0.6f});
            PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{1.0f, 0.0f});
            ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this.mTipStatusContainer, new PropertyValuesHolder[]{ofFloat, ofFloat2, ofFloat3}).setDuration(300);
            this.mTipHideAnimator = new AnimatorSet();
            this.mTipHideAnimator.play(duration);
            this.mTipHideAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    PointTipView.this.mTipStatusContainer.setAlpha(0.0f);
                }

                public void onAnimationEnd(Animator animator) {
                    PointTipView.this.mTipStatusContainer.setAlpha(0.0f);
                }
            });
        }
        this.mTipHideAnimator.start();
    }

    public void showTipWithAnimation() {
        preUpdateTipTime();
        if (this.mTipHideAnimator != null && this.mTipHideAnimator.isRunning()) {
            this.mTipHideAnimator.cancel();
        }
        if (this.mTipShowAnimator == null) {
            PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{0.6f, 1.0f});
            PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{0.6f, 1.0f});
            PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{0.0f, 1.0f});
            ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this.mTipStatusContainer, new PropertyValuesHolder[]{ofFloat, ofFloat2, ofFloat3}).setDuration(300);
            this.mTipShowAnimator = new AnimatorSet();
            this.mTipShowAnimator.play(duration);
            this.mTipShowAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    PointTipView.this.mTipStatusContainer.setAlpha(1.0f);
                    PointTipView.this.mTipStatusContainer.setScaleX(1.0f);
                    PointTipView.this.mTipStatusContainer.setScaleY(1.0f);
                }

                public void onAnimationEnd(Animator animator) {
                    PointTipView.this.mTipStatusContainer.setAlpha(1.0f);
                    PointTipView.this.mTipStatusContainer.setScaleX(1.0f);
                    PointTipView.this.mTipStatusContainer.setScaleY(1.0f);
                }
            });
        }
        this.mTipShowAnimator.start();
    }

    public void updateHasTip(boolean z) {
        this.mHasTip = z;
        setTipVisible(this.mHasTip);
    }

    public void setTipVisible(boolean z) {
        this.mTipStatusContainer.setVisibility((!this.mHasTip || !z) ? 8 : 0);
    }

    public void updateArrow(boolean z) {
        this.mHasArrow = z;
        if (z) {
            this.mTipStatusInfo.setGravity(3);
            this.mTipStatusInfo.setCompoundDrawablePadding(DimensionUtils.dipToPixel(8.0f));
            this.mTipStatusInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(this.arrowId), null);
            return;
        }
        this.mTipStatusInfo.setGravity(17);
        this.mTipStatusInfo.setCompoundDrawablePadding(DimensionUtils.dipToPixel(0.0f));
        this.mTipStatusInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    private void updateTipText(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            this.mTipStatusInfo.setText(str);
            if (z) {
                this.mTipStatusInfo.setGravity(3);
                this.mTipStatusInfo.setCompoundDrawablePadding(DimensionUtils.dipToPixel(8.0f));
                this.mTipStatusInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(this.arrowId), null);
            } else {
                this.mTipStatusInfo.setGravity(17);
                this.mTipStatusInfo.setCompoundDrawablePadding(DimensionUtils.dipToPixel(0.0f));
                this.mTipStatusInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            setTipVisible(true);
        }
    }

    public void setTipTimeVisible(boolean z) {
        int i = z ? 0 : 8;
        this.mTipStatusTime.setVisibility(i);
        this.mTipStatusUnit.setVisibility(i);
        this.mTipDivider.setVisibility(i);
        if (z) {
            Object attributeValue = this.mProperty.getNode().getAttributeValue("time");
            if (attributeValue instanceof String) {
                String str = (String) attributeValue;
                if (!TextUtils.isEmpty(str)) {
                    this.mTipStatusTime.setText(str);
                    return;
                }
            }
            this.mTipStatusTime.setText("");
            preUpdateTipTime();
        }
    }

    public void setType(int i) {
        this.mType = i;
        setTipTimeVisible(i == TYPE_HAS_TIME);
    }

    public void updateTipTime(String str) {
        if (this.mType != TYPE_HAS_TIME) {
            clear();
            setTipTimeVisible(false);
            return;
        }
        this.mHandler.removeMessages(16);
        if (!TextUtils.isEmpty(str)) {
            this.mTipStatusTime.setText(str);
            setTipVisible(true);
            return;
        }
        this.mTipStatusTime.setText("");
        preUpdateTipTime();
    }

    public void playTipPinDownAnimator(boolean z) {
        if (this.mTipEndMoveAnimator != null && this.mTipEndMoveAnimator.isRunning()) {
            this.mTipEndMoveAnimator.cancel();
        }
        if (z || this.mTipStatusContainer.getAlpha() == 0.0f) {
            prepareMoveEndAnimatorState();
            if (this.mTipEndMoveAnimator == null) {
                AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
                PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, new float[]{0.0f, 5.0f, -20.0f, 0.0f, 0.0f});
                PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{1.0f, 1.0f, 1.0f, 1.1f, 1.0f});
                PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{1.0f, 0.9f, 1.0f, 1.1f, 1.0f});
                ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this.mTipPointBelowView, new PropertyValuesHolder[]{ofFloat, ofFloat2, ofFloat3}).setDuration(800);
                duration.setInterpolator(accelerateInterpolator);
                OvershootInterpolator overshootInterpolator = new OvershootInterpolator(1.2f);
                PropertyValuesHolder ofFloat4 = PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{1.0f, 0.0f});
                PropertyValuesHolder ofFloat5 = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{0.1f, 1.0f});
                PropertyValuesHolder ofFloat6 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{0.1f, 1.0f});
                ObjectAnimator duration2 = ObjectAnimator.ofPropertyValuesHolder(this.mTipPointShadowScale, new PropertyValuesHolder[]{ofFloat5, ofFloat6, ofFloat4}).setDuration(600);
                duration2.setInterpolator(overshootInterpolator);
                duration2.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        PointTipView.this.mTipPointShadowScale.setVisibility(8);
                    }
                });
                this.mTipEndMoveAnimator = new AnimatorSet();
                this.mTipEndMoveAnimator.play(duration).before(duration2);
                this.mTipEndMoveAnimator.play(duration2);
                this.mTipEndMoveAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationCancel(Animator animator) {
                        PointTipView.this.mTipPointBelowView.setTranslationY(0.0f);
                        PointTipView.this.mTipPointBelowView.setScaleX(1.0f);
                        PointTipView.this.mTipPointBelowView.setScaleY(1.0f);
                        PointTipView.this.mTipPointBelowView.setAlpha(1.0f);
                        PointTipView.this.mTipPointShadowScale.setAlpha(0.0f);
                        PointTipView.this.mTipPointShadowScale.setScaleX(1.0f);
                        PointTipView.this.mTipPointShadowScale.setScaleY(1.0f);
                    }
                });
            }
            this.mTipEndMoveAnimator.start();
        }
    }

    private void prepareMoveEndAnimatorState() {
        setTipVisible(true);
        this.mTipStatusInfo.setText(this.mContent);
        this.mTipStatusContainer.setAlpha(0.0f);
        this.mTipPointShadowScale.setVisibility(0);
        this.mTipPointShadowScale.setAlpha(0.0f);
        this.mTipPointBelowView.setAlpha(1.0f);
    }

    public void updateContent(String str) {
        this.mContent = str;
        updateTipText(str, this.mHasArrow);
    }

    public void updateTheme(Object obj) {
        if (!(obj instanceof String) || !TextUtils.equals("dark", (String) obj)) {
            this.mTipStatusUnit.setTextColor(getResources().getColor(R.color.f_c_6));
            this.mTipStatusInfo.setTextColor(getResources().getColor(R.color.f_c_2));
            this.mTipStatusTime.setTextColor(getResources().getColor(R.color.f_c_6));
            this.mTipDivider.setBackgroundColor(getResources().getColor(R.color.c_26));
            this.mTipStatusContainer.setBackgroundResource(R.drawable.taxi_start_point_tip_circle_bg);
            Drawable drawable = getResources().getDrawable(R.drawable.taxi_start_point_select_tip_call_taxi);
            this.arrowId = R.drawable.taxi_start_point_select_tip_call_taxi;
            this.mTipStatusInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            return;
        }
        this.mTipStatusUnit.setTextColor(getResources().getColor(R.color.f_c_1));
        this.mTipStatusInfo.setTextColor(getResources().getColor(R.color.f_c_1));
        this.mTipStatusTime.setTextColor(getResources().getColor(R.color.f_c_1));
        this.mTipDivider.setBackgroundColor(getResources().getColor(R.color.c_1_g));
        this.mTipStatusContainer.setBackgroundResource(R.drawable.taxi_start_point_tip_circle_bg_drak);
        Drawable drawable2 = getResources().getDrawable(R.drawable.taxi_start_point_select_tip_call_taxi_dark);
        this.arrowId = R.drawable.taxi_start_point_select_tip_call_taxi_dark;
        this.mTipStatusInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable2, null);
    }

    public void clear() {
        this.mHandler.removeMessages(16);
    }

    public void preUpdateTipTime() {
        if (this.mType != TYPE_HAS_TIME) {
            clear();
            setTipTimeVisible(false);
            return;
        }
        updateTipTime(".  ");
        this.mHandler.sendEmptyMessageDelayed(16, 300);
    }
}
