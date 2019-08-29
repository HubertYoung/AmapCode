package com.autonavi.minimap.life.marketdetail.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class TitleViewWithBackText extends LinearLayout {
    private ImageButton mBackImageButton;
    private TextView mTitleTextView;

    public TitleViewWithBackText(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.common_title_back_text, this, true);
        intiview();
    }

    public TitleViewWithBackText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.common_title_back_text, this, true);
        intiview();
    }

    private void intiview() {
        this.mBackImageButton = (ImageButton) findViewById(R.id.ib_back);
        this.mTitleTextView = (TextView) findViewById(R.id.txt_title);
    }

    public void setOnBackImageButtonClickListener(OnClickListener onClickListener) {
        this.mBackImageButton.setOnClickListener(onClickListener);
    }

    public void setTitleText(String str) {
        this.mTitleTextView.setText(str);
    }
}
