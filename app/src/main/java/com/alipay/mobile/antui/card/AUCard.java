package com.alipay.mobile.antui.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUCircleImageView;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.basic.AURelativeLayout;

public class AUCard extends AURelativeLayout {
    private AUEmptyGoneTextView mCardAssitDes;
    private AUCouponsItem mCouponsItem;
    /* access modifiers changed from: private */
    public View mDottdLine;

    public AUCard(Context context) {
        super(context);
        init(context);
    }

    public AUCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AUCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_card_view, this, true);
        this.mCouponsItem = (AUCouponsItem) findViewById(R.id.coupons_card_container);
        this.mDottdLine = findViewById(R.id.dotted_line);
        this.mCardAssitDes = (AUEmptyGoneTextView) findViewById(R.id.coupons_assist_description);
        this.mCardAssitDes.addTextChangedListener(new a(this));
    }

    public void setCouponsAssitDes(String des) {
        this.mCardAssitDes.setText(des);
    }

    public void setCouponsInfo(String assistTitle, String mainTitle, String subTitle) {
        this.mCouponsItem.setCouponsInfo(assistTitle, mainTitle, subTitle);
    }

    public AUEmptyGoneTextView getCouponsAssitDes() {
        return this.mCardAssitDes;
    }

    public AUCircleImageView getCouponsImage() {
        return this.mCouponsItem.getCouponsImage();
    }

    public AUCouponsItem getCouponsItem() {
        return this.mCouponsItem;
    }
}
