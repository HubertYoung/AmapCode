package com.amap.bundle.drive.result.tip.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class TipsView extends RelativeLayout implements AnimatorUpdateListener, OnClickListener {
    private TextView mCountDownText;
    private LinearInterpolator mLinearInterpolator;
    private a mOnTipClickListener;
    private px mRouteCarResultTipItem;
    private RelativeLayout mTipBg;
    private TextView mTipConfirm;
    private View mTipRoot;
    private TextView mTipSubTitle;
    private TextView mTipTitle;
    private LinearLayout mTipsCarNumLayout;
    public final int targetHeight;

    public interface a {
        void b(int i);

        void c(int i);
    }

    public static class b implements TypeEvaluator<Rect> {
        private Rect a;

        public final /* synthetic */ Object evaluate(float f, Object obj, Object obj2) {
            Rect rect = (Rect) obj;
            Rect rect2 = (Rect) obj2;
            int i = rect.left + ((int) (((float) (rect2.left - rect.left)) * f));
            int i2 = rect.top + ((int) (((float) (rect2.top - rect.top)) * f));
            int i3 = rect.right + ((int) (((float) (rect2.right - rect.right)) * f));
            int i4 = rect.bottom + ((int) (((float) (rect2.bottom - rect.bottom)) * f));
            if (this.a == null) {
                return new Rect(i, i2, i3, i4);
            }
            this.a.set(i, i2, i3, i4);
            return this.a;
        }
    }

    public void setText() {
    }

    public void setTipsInfo(px pxVar) {
        this.mRouteCarResultTipItem = pxVar;
        updateTipInfo();
        updateCarNumInfo();
        initClickListener();
    }

    public TipsView(Context context) {
        this(context, null);
    }

    public TipsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.targetHeight = getResources().getDimensionPixelSize(R.dimen.tips_height);
        initView(context);
    }

    public TipsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.targetHeight = getResources().getDimensionPixelSize(R.dimen.tips_height);
        initView(context);
    }

    private void initView(Context context) {
        this.mTipRoot = LayoutInflater.from(context).inflate(R.layout.tips_layout, this, true);
        this.mTipBg = (RelativeLayout) this.mTipRoot.findViewById(R.id.tip_root);
        this.mTipTitle = (TextView) this.mTipRoot.findViewById(R.id.tip_title);
        this.mTipSubTitle = (TextView) this.mTipRoot.findViewById(R.id.tip_sub_title);
        this.mCountDownText = (TextView) this.mTipRoot.findViewById(R.id.tip_auto_cancel);
        this.mTipConfirm = (TextView) this.mTipRoot.findViewById(R.id.tips_confirm);
        this.mTipsCarNumLayout = (LinearLayout) this.mTipRoot.findViewById(R.id.tips_title_car_num_layout);
    }

    public void setOnTipClickListener(a aVar) {
        this.mOnTipClickListener = aVar;
    }

    private void initClickListener() {
        this.mCountDownText.setOnClickListener(this);
        this.mCountDownText.setVisibility(0);
        this.mTipConfirm.setOnClickListener(this);
        this.mTipConfirm.setVisibility(0);
        findViewById(R.id.tips_vertical_divider).setVisibility(0);
        findViewById(R.id.tips_horizontal_divider).setVisibility(0);
        findViewById(R.id.tips_title_area).setOnClickListener(this);
    }

    public void dismissCancelButton() {
        this.mCountDownText.setVisibility(8);
        findViewById(R.id.tips_horizontal_divider).setVisibility(8);
    }

    public void dismissConfirmButton() {
        this.mTipConfirm.setVisibility(8);
        findViewById(R.id.tips_horizontal_divider).setVisibility(8);
    }

    public void setConfirmText(int i) {
        this.mTipConfirm.setText(getResources().getString(i));
    }

    public void setConfirmColor(int i) {
        if (i >= 0) {
            this.mTipConfirm.setTextColor(getResources().getColor(i));
        }
    }

    public void setCancelColor(int i) {
        if (i >= 0) {
            this.mCountDownText.setTextColor(getResources().getColor(i));
        }
    }

    public TextView getCountDownView() {
        return this.mCountDownText;
    }

    private void setTipTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mTipTitle.setText(str);
        }
    }

    private void setTipSubTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mTipSubTitle.setText(str);
            this.mTipSubTitle.setVisibility(0);
            this.mTipTitle.setMaxLines(1);
            this.mTipTitle.setLineSpacing(0.0f, 1.0f);
            this.mTipTitle.setEllipsize(TruncateAt.END);
            return;
        }
        this.mTipSubTitle.setVisibility(8);
    }

    private void updateTipInfo() {
        setTipTitle(this.mRouteCarResultTipItem.b);
        setTipSubTitle(this.mRouteCarResultTipItem.c);
    }

    private void updateCarNumInfo() {
        char c;
        px pxVar = this.mRouteCarResultTipItem;
        if (pxVar.e == null) {
            c = 65535;
        } else {
            int length = pxVar.e.length;
            if (length == 5) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < length; i++) {
                    arrayList.add(i, Integer.valueOf(pxVar.e[i]));
                }
                if (arrayList.contains(Integer.valueOf(1)) && arrayList.contains(Integer.valueOf(3)) && arrayList.contains(Integer.valueOf(5)) && arrayList.contains(Integer.valueOf(7)) && arrayList.contains(Integer.valueOf(9))) {
                    c = 1;
                } else if (arrayList.contains(Integer.valueOf(2)) && arrayList.contains(Integer.valueOf(4)) && arrayList.contains(Integer.valueOf(6)) && arrayList.contains(Integer.valueOf(8)) && arrayList.contains(Integer.valueOf(0))) {
                    c = 2;
                }
            }
            c = 0;
        }
        if (c == 0) {
            byte[] bArr = this.mRouteCarResultTipItem.e;
            for (byte append : bArr) {
                StringBuilder sb = new StringBuilder();
                sb.append(append);
                TextView carNumView = getCarNumView(sb.toString(), true);
                this.mTipsCarNumLayout.addView(carNumView);
                LayoutParams layoutParams = (LayoutParams) carNumView.getLayoutParams();
                layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.route_2dp);
                layoutParams.width = getResources().getDimensionPixelOffset(R.dimen.route_14dp);
                layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
                layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
                layoutParams.height = -1;
            }
            this.mTipsCarNumLayout.setVisibility(0);
        } else if (c == 1) {
            TextView carNumView2 = getCarNumView("单", false);
            TextView carNumView3 = getCarNumView("号", false);
            this.mTipsCarNumLayout.addView(carNumView2);
            LayoutParams layoutParams2 = (LayoutParams) carNumView2.getLayoutParams();
            layoutParams2.rightMargin = getResources().getDimensionPixelOffset(R.dimen.route_2dp);
            layoutParams2.topMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
            layoutParams2.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
            layoutParams2.width = getResources().getDimensionPixelOffset(R.dimen.route_14dp);
            layoutParams2.height = -1;
            this.mTipsCarNumLayout.addView(carNumView3);
            LayoutParams layoutParams3 = (LayoutParams) carNumView3.getLayoutParams();
            layoutParams3.topMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
            layoutParams3.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
            layoutParams3.width = getResources().getDimensionPixelOffset(R.dimen.route_14dp);
            layoutParams3.height = -1;
            this.mTipsCarNumLayout.setVisibility(0);
        } else if (c == 2) {
            TextView carNumView4 = getCarNumView("双", false);
            TextView carNumView5 = getCarNumView("号", false);
            this.mTipsCarNumLayout.addView(carNumView4);
            LayoutParams layoutParams4 = (LayoutParams) carNumView4.getLayoutParams();
            layoutParams4.rightMargin = getResources().getDimensionPixelOffset(R.dimen.route_2dp);
            layoutParams4.topMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
            layoutParams4.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
            layoutParams4.width = getResources().getDimensionPixelOffset(R.dimen.route_14dp);
            layoutParams4.height = -1;
            this.mTipsCarNumLayout.addView(carNumView5);
            LayoutParams layoutParams5 = (LayoutParams) carNumView5.getLayoutParams();
            layoutParams5.topMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
            layoutParams5.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.route_1dp);
            layoutParams5.width = getResources().getDimensionPixelOffset(R.dimen.route_14dp);
            layoutParams5.height = -1;
            this.mTipsCarNumLayout.setVisibility(0);
        } else {
            this.mTipsCarNumLayout.setVisibility(8);
        }
    }

    private TextView getCarNumView(String str, boolean z) {
        TextView textView = (TextView) inflate(getContext(), R.layout.tips_car_num_layout, null);
        textView.setText(str);
        if (this.mRouteCarResultTipItem.a == 1) {
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.f_c_1));
            textView.setBackgroundResource(R.drawable.route_result_carnum_unavoid_restriction_tip_bg);
        } else {
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.f_c_8));
            textView.setBackgroundResource(R.drawable.route_result_carnum_avoid_restriction_tip_bg);
        }
        TextPaint paint = textView.getPaint();
        if (z) {
            paint.setTextSize(getResources().getDimension(R.dimen.f_s_14));
        } else {
            paint.setTextSize(getResources().getDimension(R.dimen.f_s_11));
        }
        return textView;
    }

    public void setTipBackGround(int i) {
        if (i > 0) {
            this.mTipBg.setBackgroundResource(i);
            updatePadding();
        }
    }

    private void updatePadding() {
        this.mTipBg.setPadding(getResources().getDimensionPixelOffset(R.dimen.tips_bg_horizontal_padding), getResources().getDimensionPixelOffset(R.dimen.tips_bg_vertical_padding), getResources().getDimensionPixelOffset(R.dimen.tips_bg_horizontal_padding), getResources().getDimensionPixelOffset(R.dimen.tips_bg_vertical_padding));
    }

    public void setTextColor(int i) {
        if (i > 0) {
            this.mTipTitle.setTextColor(getResources().getColor(i));
            this.mTipSubTitle.setTextColor(getResources().getColor(i));
            this.mCountDownText.setTextColor(getResources().getColor(i));
            this.mTipConfirm.setTextColor(getResources().getColor(i));
        }
    }

    public void dismissVerticalDivider() {
        findViewById(R.id.tips_vertical_divider).setVisibility(8);
    }

    public void setDividerColor(int i) {
        if (i > 0) {
            View findViewById = findViewById(R.id.tips_vertical_divider);
            View findViewById2 = findViewById(R.id.tips_horizontal_divider);
            findViewById.setBackgroundColor(getResources().getColor(i));
            findViewById2.setBackgroundColor(getResources().getColor(i));
            findViewById2.setVisibility(0);
            findViewById.setVisibility(0);
        }
    }

    public void hideDivider() {
        View findViewById = findViewById(R.id.tips_vertical_divider);
        if (findViewById != null) {
            findViewById.setVisibility(4);
        }
    }

    public Animator getTopTipAnimation() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mTipRoot, View.ALPHA, new float[]{0.9f, 1.0f});
        ofFloat.setDuration(200);
        return ofFloat;
    }

    public Animator getTranslateAnimation(long j, long j2) {
        if (this.mLinearInterpolator == null) {
            this.mLinearInterpolator = new LinearInterpolator();
        }
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{0.3f, 1.0f});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, new float[]{0.0f, ((float) this.targetHeight) + (((float) getResources().getDimensionPixelOffset(R.dimen.tips_vertical_margin)) * 0.5f), (float) this.targetHeight});
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.mTipRoot, new PropertyValuesHolder[]{ofFloat, ofFloat2});
        ofPropertyValuesHolder.setDuration(j);
        ofPropertyValuesHolder.setInterpolator(this.mLinearInterpolator);
        ofPropertyValuesHolder.setStartDelay(j2);
        return ofPropertyValuesHolder;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (getAlpha() != 1.0f) {
            setAlpha(1.0f);
        }
        invalidate();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (this.mOnTipClickListener != null) {
            if (id == R.id.tip_auto_cancel) {
                this.mOnTipClickListener.c(this.mRouteCarResultTipItem.a);
            } else if (id == R.id.tips_confirm) {
                this.mOnTipClickListener.b(this.mRouteCarResultTipItem.a);
            }
        }
    }

    public ValueAnimator getZoomOutAnimation() {
        Rect rect = new Rect(getLeft(), getTop(), getRight(), getBottom());
        Rect rect2 = new Rect((int) (((float) getMeasuredWidth()) * 0.1f), getTop() + getResources().getDimensionPixelOffset(R.dimen.tips_vertical_margin), (int) ((((float) getMeasuredWidth()) * 0.1f) + ((float) (getResources().getDimensionPixelOffset(R.dimen.tips_default_width) / 2))), getTop() + (getResources().getDimensionPixelOffset(R.dimen.tips_vertical_margin) * 2));
        ValueAnimator ofObject = ValueAnimator.ofObject(new b(), new Object[]{rect, rect2});
        ofObject.setDuration(200);
        return ofObject;
    }
}
