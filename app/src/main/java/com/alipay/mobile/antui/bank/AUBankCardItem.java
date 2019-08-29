package com.alipay.mobile.antui.bank;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUCircleImageView;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.tablelist.AUBaseListItem;

public class AUBankCardItem extends AUBaseListItem {
    private AUCircleImageView mBankImage;
    private AUEmptyGoneTextView mBankName;
    private AUEmptyGoneTextView mBankNumber;

    public AUBankCardItem(Context context) {
        super(context);
        initView();
    }

    public AUBankCardItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AUBankCardItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /* access modifiers changed from: protected */
    public void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_bank_card_item, this.mListLayout, true);
        this.mListLayout.setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.AU_SPACE14));
    }

    private void initView() {
        this.mBankName = (AUEmptyGoneTextView) findViewById(R.id.bank_name);
        this.mBankNumber = (AUEmptyGoneTextView) findViewById(R.id.bank_number);
        this.mBankImage = (AUCircleImageView) findViewById(R.id.bank_circle_image);
    }

    public AUEmptyGoneTextView getBankName() {
        return this.mBankName;
    }

    public AUEmptyGoneTextView getBankNumber() {
        return this.mBankNumber;
    }

    public AUCircleImageView getBankImage() {
        this.mBankImage.setVisibility(0);
        return this.mBankImage;
    }

    public void setBankInfo(String bankName, String bankNum) {
        this.mBankName.setText(bankName);
        this.mBankNumber.setText(bankNum);
    }
}
