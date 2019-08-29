package com.alipay.mobile.beehive.capture.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.alipay.mobile.beehive.R;

public class GuideView extends FrameLayout implements OnClickListener {
    private View effectGuide;
    private ImageView effectIKnow;

    public enum GuideType {
        NONE,
        RECORD,
        EFFECT
    }

    public GuideView(Context context) {
        this(context, null, 0);
    }

    public GuideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_guide, this, true);
        this.effectGuide = findViewById(R.id.effectGuide);
        this.effectIKnow = (ImageView) findViewById(R.id.effectIKnow);
        this.effectIKnow.setOnClickListener(this);
        setOnClickListener(this);
    }

    public void onClick(View v) {
        setVisibility(8);
    }

    public void setmGuideType(GuideType type) {
        switch (type) {
            case NONE:
                setVisibility(8);
                return;
            case EFFECT:
                setVisibility(0);
                this.effectGuide.setVisibility(0);
                return;
            default:
                return;
        }
    }
}
