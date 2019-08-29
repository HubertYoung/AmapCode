package com.autonavi.widget.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class CommonTips extends RelativeLayout {
    public static final int TIP_A1 = 0;
    public static final int TIP_A2 = 1;
    public static final int TIP_A3 = 2;
    public static final int TIP_A4 = 3;
    public static final int TIP_B1 = 4;
    public static final int TIP_B2 = 5;
    public static final int TIP_B3 = 6;
    public static final int TIP_B4 = 7;
    public static final int TIP_C1 = 8;
    public static final int TIP_C2 = 9;
    public static final int TIP_C3 = 10;
    public static final int TIP_C4 = 11;
    public static final int TIP_D1 = 12;
    public static final int TIP_D2 = 13;
    public static final int TIP_D3 = 14;
    public static final int TIP_D4 = 15;
    public static final int TIP_E1 = 16;
    public static final int TIP_E2 = 17;
    public static final int TIP_F1 = 18;
    public static final int TIP_F2 = 19;
    public static final int TIP_F3 = 20;
    public static final int TIP_F4 = 21;
    private int mCurrentStyle;
    private Button mRightButton;
    private ImageView mRightImage;
    private TextView mTipText;

    public CommonTips(Context context, int i) {
        super(context);
        init(context, null, i);
    }

    public CommonTips(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        CharSequence charSequence = "";
        CharSequence charSequence2 = "";
        this.mCurrentStyle = i;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CommonTips);
            this.mCurrentStyle = obtainStyledAttributes.getInt(R.styleable.CommonTips_tip_style, 0);
            charSequence = obtainStyledAttributes.getText(R.styleable.CommonTips_tipText);
            charSequence2 = obtainStyledAttributes.getText(R.styleable.CommonTips_buttonText);
            obtainStyledAttributes.recycle();
        }
        inflateViewByStyle(charSequence, charSequence2);
    }

    private void inflateViewByStyle(CharSequence charSequence, CharSequence charSequence2) {
        LayoutInflater from = LayoutInflater.from(getContext());
        switch (this.mCurrentStyle) {
            case 0:
                from.inflate(R.layout.view_tips_a1, this, true);
                break;
            case 1:
                from.inflate(R.layout.view_tips_a2, this, true);
                break;
            case 2:
                from.inflate(R.layout.view_tips_a3, this, true);
                break;
            case 3:
                from.inflate(R.layout.view_tips_a4, this, true);
                break;
            case 4:
                from.inflate(R.layout.view_tips_b1, this, true);
                break;
            case 5:
                from.inflate(R.layout.view_tips_b2, this, true);
                break;
            case 6:
                from.inflate(R.layout.view_tips_b3, this, true);
                break;
            case 7:
                from.inflate(R.layout.view_tips_b4, this, true);
                break;
            case 8:
                from.inflate(R.layout.view_tips_c1, this, true);
                break;
            case 9:
                from.inflate(R.layout.view_tips_c2, this, true);
                break;
            case 10:
                from.inflate(R.layout.view_tips_c3, this, true);
                break;
            case 11:
                from.inflate(R.layout.view_tips_c4, this, true);
                break;
            case 12:
                from.inflate(R.layout.view_tips_d1, this, true);
                break;
            case 13:
                from.inflate(R.layout.view_tips_d2, this, true);
                break;
            case 14:
                from.inflate(R.layout.view_tips_d3, this, true);
                break;
            case 15:
                from.inflate(R.layout.view_tips_d4, this, true);
                break;
            case 16:
                from.inflate(R.layout.view_tips_e1, this, true);
                break;
            case 17:
                from.inflate(R.layout.view_tips_e2, this, true);
                break;
            case 18:
                from.inflate(R.layout.view_tips_f1, this, true);
                break;
            case 19:
                from.inflate(R.layout.view_tips_f2, this, true);
                break;
            case 20:
                from.inflate(R.layout.view_tips_f3, this, true);
                break;
            case 21:
                from.inflate(R.layout.view_tips_f4, this, true);
                break;
        }
        initView(charSequence, charSequence2);
    }

    private void initView(CharSequence charSequence, CharSequence charSequence2) {
        this.mTipText = (TextView) findViewById(R.id.tip_text);
        this.mRightImage = (ImageView) findViewById(R.id.tip_image);
        this.mRightButton = (Button) findViewById(R.id.tip_button);
        setTipText(charSequence);
        setRightButtonText(charSequence2);
        setClickable(true);
    }

    public void setTipText(int i) {
        if (this.mTipText != null) {
            this.mTipText.setText(i);
        }
    }

    public void setTipText(CharSequence charSequence) {
        if (this.mTipText != null) {
            this.mTipText.setText(charSequence);
        }
    }

    public void setTipTextColor(int i) {
        if ((this.mCurrentStyle == 3 || this.mCurrentStyle == 7 || this.mCurrentStyle == 8 || this.mCurrentStyle == 15 || this.mCurrentStyle == 21) && this.mTipText != null) {
            this.mTipText.setTextColor(i);
        }
    }

    public void setRightButtonText(int i) {
        if (this.mRightButton != null) {
            this.mRightButton.setText(i);
        }
    }

    public void setRightButtonText(CharSequence charSequence) {
        if (this.mRightButton != null) {
            this.mRightButton.setText(charSequence);
        }
    }

    public void setRightViewOnClickListener(OnClickListener onClickListener) {
        if (this.mRightImage != null) {
            this.mRightImage.setOnClickListener(onClickListener);
            return;
        }
        if (this.mRightButton != null) {
            this.mRightButton.setOnClickListener(onClickListener);
        }
    }
}
