package com.alipay.mobile.antui.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUCircleImageView;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.tablelist.AUBaseListItem;

public class AUCouponsItem extends AUBaseListItem {
    private AUEmptyGoneTextView mCouponsAssitTitle;
    private AUCircleImageView mCouponsImage;
    private AUEmptyGoneTextView mCouponsMainTitle;
    private AUEmptyGoneTextView mCouponsSubTitle;

    public AUCouponsItem(Context context) {
        super(context);
        initView();
    }

    public AUCouponsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AUCouponsItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /* access modifiers changed from: protected */
    public void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_coupons_item, this.mListLayout, true);
        LayoutParams layoutParams = (LayoutParams) this.mListLayout.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.coupons_card_height);
        this.mListLayout.setLayoutParams(layoutParams);
    }

    private void initView() {
        setBackgroundColor(0);
        this.mCouponsAssitTitle = (AUEmptyGoneTextView) findViewById(R.id.coupons_assist_title);
        this.mCouponsMainTitle = (AUEmptyGoneTextView) findViewById(R.id.coupons_main_title);
        this.mCouponsSubTitle = (AUEmptyGoneTextView) findViewById(R.id.coupons_sub_title);
        this.mCouponsImage = (AUCircleImageView) findViewById(R.id.coupons_circle_image);
    }

    public AUCircleImageView getCouponsImage() {
        this.mCouponsImage.setVisibility(0);
        return this.mCouponsImage;
    }

    public AUEmptyGoneTextView getCouponsMainTitle() {
        return this.mCouponsMainTitle;
    }

    public AUEmptyGoneTextView getCouponsAssitTitle() {
        return this.mCouponsAssitTitle;
    }

    public AUEmptyGoneTextView getCouponsSubTitle() {
        return this.mCouponsSubTitle;
    }

    public void setCouponsInfo(String assistTitle, String mainTitle, String subTitle) {
        this.mCouponsAssitTitle.setText(assistTitle);
        this.mCouponsMainTitle.setText(mainTitle);
        this.mCouponsSubTitle.setText(subTitle);
    }
}
