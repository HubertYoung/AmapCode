package com.alipay.mobile.antui.amount;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;

public class AUAmountHeadView extends AURelativeLayout {
    public static final int BANKCARD_STYLE = 16;
    public static final int SINGLE_STYLE = 18;
    public static final int TEXT_STYLE = 17;
    private View mDivider;
    private AUTextView mHeadTitle;
    private AUImageView mLogoView;
    private AUTextView mMainInfo;
    private AUTextView mMarkTextView;
    private AUTextView mSubInfo;

    public AUAmountHeadView(Context context) {
        super(context);
        init(context);
    }

    public AUAmountHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AUAmountHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_amount_head_view, this, true);
        this.mHeadTitle = (AUTextView) findViewById(R.id.title_text);
        this.mLogoView = (AUImageView) findViewById(R.id.title_logo);
        this.mMainInfo = (AUTextView) findViewById(R.id.head_mainInfo);
        this.mSubInfo = (AUTextView) findViewById(R.id.head_subInfo);
        this.mMarkTextView = (AUTextView) findViewById(R.id.bankcard_mark);
        this.mDivider = findViewById(R.id.head_divider);
    }

    public void setStyle(int style) {
        this.mHeadTitle.setVisibility(8);
        this.mLogoView.setVisibility(8);
        this.mMarkTextView.setVisibility(8);
        this.mDivider.setVisibility(8);
        switch (style) {
            case 16:
                this.mLogoView.setVisibility(0);
                this.mMarkTextView.setVisibility(0);
                this.mDivider.setVisibility(0);
                return;
            case 17:
                this.mHeadTitle.setVisibility(0);
                this.mDivider.setVisibility(0);
                return;
            case 18:
                this.mLogoView.setVisibility(0);
                setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_bg_top_bottom_line));
                return;
            default:
                return;
        }
    }

    public void setTextStyleInfo(String title, String mainInfo, String subInfo) {
        if (!TextUtils.isEmpty(title)) {
            this.mHeadTitle.setText(title);
        }
        if (!TextUtils.isEmpty(mainInfo)) {
            this.mMainInfo.setText(mainInfo);
        }
        if (!TextUtils.isEmpty(subInfo)) {
            this.mSubInfo.setVisibility(0);
            this.mSubInfo.setText(subInfo);
            return;
        }
        this.mSubInfo.setVisibility(8);
    }

    public void setBankcardInfo(String bankcardInfo, String subInfo, String markInfo) {
        if (!TextUtils.isEmpty(bankcardInfo)) {
            this.mMainInfo.setText(bankcardInfo);
        }
        if (!TextUtils.isEmpty(markInfo)) {
            this.mMarkTextView.setText(markInfo);
        }
        if (!TextUtils.isEmpty(subInfo)) {
            this.mSubInfo.setVisibility(0);
            this.mSubInfo.setText(subInfo);
            return;
        }
        this.mSubInfo.setVisibility(8);
    }

    public AUImageView getLogoView() {
        return this.mLogoView;
    }
}
