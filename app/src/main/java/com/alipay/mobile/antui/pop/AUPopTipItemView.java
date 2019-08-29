package com.alipay.mobile.antui.pop;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AURoundImageView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;

public class AUPopTipItemView extends AULinearLayout {
    public static final int TRIANGLE_VISIBLE_BOTTOM = -1;
    public static final int TRIANGLE_VISIBLE_NONE = 0;
    public static final int TRIANGLE_VISIBLE_TOP = 1;
    private int arrowWidth = 0;
    private AUIconView mBottomIcon;
    private View mCloseBtn;
    private Context mContext;
    private AURoundImageView mLeftIcon;
    private AUTextView mTipButton;
    private AULinearLayout mTipContainer;
    private AUTextView mTipDescTextView;
    private AUTextView mTipTextView;
    private AUIconView mTopIcon;
    private int triangleVisible = 0;

    public AUPopTipItemView(Context context) {
        super(context);
        init(context);
    }

    public AUPopTipItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOrientation(1);
        LayoutInflater.from(context).inflate(R.layout.au_tip_pop_item_view, this, true);
        this.mTipContainer = (AULinearLayout) findViewById(R.id.tip_container);
        this.mTipTextView = (AUTextView) findViewById(R.id.tip_text);
        this.mTipButton = (AUTextView) findViewById(R.id.tip_btn);
        this.mTopIcon = (AUIconView) findViewById(R.id.tip_up_icon);
        this.mBottomIcon = (AUIconView) findViewById(R.id.tip_down_icon);
        this.mCloseBtn = findViewById(R.id.cancel_ly);
        this.mTipDescTextView = (AUTextView) findViewById(R.id.tip_desc);
        this.mLeftIcon = (AURoundImageView) findViewById(R.id.left_icon);
        this.arrowWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.au_tip_pop_arrow_width);
        setTriangleVisible(this.triangleVisible);
    }

    public void setTriangleVisible(int triangleVisible2) {
        int marginTopOrBottom = this.mContext.getResources().getDimensionPixelOffset(R.dimen.au_tip_pop_arrow_margin_top_or_bottom);
        LayoutParams bottomLayoutParams = (LayoutParams) this.mBottomIcon.getLayoutParams();
        bottomLayoutParams.topMargin = marginTopOrBottom;
        bottomLayoutParams.gravity |= 48;
        LayoutParams topLayoutParams = (LayoutParams) this.mTopIcon.getLayoutParams();
        topLayoutParams.bottomMargin = marginTopOrBottom;
        topLayoutParams.gravity |= 80;
        if (triangleVisible2 == -1) {
            this.mTopIcon.setVisibility(8);
            this.mBottomIcon.setVisibility(0);
            this.triangleVisible = triangleVisible2;
        } else if (triangleVisible2 == 1) {
            this.mTopIcon.setVisibility(0);
            this.mBottomIcon.setVisibility(8);
            this.triangleVisible = triangleVisible2;
        } else if (triangleVisible2 == 0) {
            this.mTopIcon.setVisibility(8);
            this.mBottomIcon.setVisibility(0);
            this.triangleVisible = triangleVisible2;
        }
    }

    public void setTriangleLeftMargin(int left) {
        int position = left - (this.arrowWidth / 2);
        LayoutParams bottomLayoutParams = (LayoutParams) this.mBottomIcon.getLayoutParams();
        bottomLayoutParams.leftMargin = position;
        bottomLayoutParams.rightMargin = 0;
        bottomLayoutParams.gravity = 51;
        LayoutParams topLayoutParams = (LayoutParams) this.mTopIcon.getLayoutParams();
        topLayoutParams.leftMargin = position;
        topLayoutParams.rightMargin = 0;
        topLayoutParams.gravity = 83;
        requestLayout();
    }

    public void setTriangleRightMargin(int right) {
        int position = right - (this.arrowWidth / 2);
        LayoutParams bottomLayoutParams = (LayoutParams) this.mBottomIcon.getLayoutParams();
        bottomLayoutParams.rightMargin = position;
        bottomLayoutParams.leftMargin = 0;
        bottomLayoutParams.gravity = 53;
        LayoutParams topLayoutParams = (LayoutParams) this.mTopIcon.getLayoutParams();
        topLayoutParams.rightMargin = position;
        topLayoutParams.leftMargin = 0;
        topLayoutParams.gravity = 85;
        requestLayout();
    }

    public void setTipText(CharSequence tipText) {
        this.mTipTextView.setVisibility(0);
        this.mTipTextView.setText(tipText);
    }

    public void setTipDescText(CharSequence tipText) {
        if (TextUtils.isEmpty(tipText)) {
            this.mTipDescTextView.setVisibility(8);
        } else {
            this.mTipDescTextView.setVisibility(0);
        }
        this.mTipDescTextView.setText(tipText);
    }

    public void setCloseButtonOnClickListener(OnClickListener listener) {
        this.mCloseBtn.setOnClickListener(listener);
        if (listener != null) {
            this.mCloseBtn.setVisibility(0);
        } else {
            this.mCloseBtn.setVisibility(8);
        }
    }

    public void setTipButton(CharSequence actionText, OnClickListener listener) {
        if (TextUtils.isEmpty(actionText)) {
            this.mTipButton.setVisibility(8);
            return;
        }
        this.mTipButton.setVisibility(0);
        this.mTipButton.setText(actionText);
        this.mTipButton.setOnClickListener(listener);
    }

    public void setTipOnClickListener(OnClickListener listener) {
        this.mTipContainer.setOnClickListener(listener);
    }

    public int getTriangleVisible() {
        return this.triangleVisible;
    }

    public View getCloseButton() {
        return this.mCloseBtn;
    }

    public AUTextView getTipTextView() {
        return this.mTipTextView;
    }

    public AUTextView getTipDescTextView() {
        return this.mTipDescTextView;
    }

    public AUTextView getTipButton() {
        return this.mTipButton;
    }

    public AURoundImageView getLeftIcon() {
        return this.mLeftIcon;
    }
}
