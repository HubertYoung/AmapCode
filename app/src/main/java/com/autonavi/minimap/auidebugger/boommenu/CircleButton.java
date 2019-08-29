package com.autonavi.minimap.auidebugger.boommenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.auidebugger.boommenu.Types.ClickEffectType;

public class CircleButton extends FrameLayout {
    private ClickEffectType clickEffectType;
    private FrameLayout frameLayout;
    private ImageButton imageButton;
    private ImageView imageView;
    /* access modifiers changed from: private */
    public int index;
    private Context mContext;
    /* access modifiers changed from: private */
    public a onCircleButtonClickListener;
    private int radius;
    private View ripple;
    private ShadowLayout shadowLayout;
    private TextView textView;

    public interface a {
        void onClick(int i);
    }

    public CircleButton(Context context) {
        this(context, null);
    }

    public CircleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.clickEffectType = ClickEffectType.RIPPLE;
        cnu.a();
        this.radius = ((int) cnu.a(80.0f)) / 2;
        this.mContext = context;
        if (VERSION.SDK_INT >= 21) {
            LayoutInflater.from(this.mContext).inflate(R.layout.circle_button, this, true);
        } else {
            LayoutInflater.from(this.mContext).inflate(R.layout.circle_button_below_lollipop, this, true);
        }
        this.shadowLayout = (ShadowLayout) findViewById(R.id.shadow_layout);
        this.frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        this.imageButton = (ImageButton) findViewById(R.id.image_button);
        this.ripple = findViewById(R.id.ripple);
        this.imageView = (ImageView) findViewById(R.id.image_view);
        this.textView = (TextView) findViewById(R.id.text);
    }

    public void setOnCircleButtonClickListener(a aVar, int i) {
        this.onCircleButtonClickListener = aVar;
        this.index = i;
        setRipple(this.clickEffectType);
    }

    public void setDrawable(Drawable drawable) {
        if (this.imageView != null) {
            this.imageView.setImageDrawable(drawable);
        }
    }

    public void setText(String str) {
        if (this.textView != null) {
            this.textView.setText(str);
        }
    }

    public FrameLayout getFrameLayout() {
        return this.frameLayout;
    }

    public ImageButton getImageButton() {
        return this.imageButton;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public ShadowLayout getShadowLayout() {
        return this.shadowLayout;
    }

    public TextView getTextView() {
        return this.textView;
    }

    public void setColor(int i, int i2) {
        cnu.a();
        cnu.a(this.imageButton, this.radius, i, i2);
    }

    public void setRipple(ClickEffectType clickEffectType2) {
        this.clickEffectType = clickEffectType2;
        if (VERSION.SDK_INT < 21 || !clickEffectType2.equals(ClickEffectType.RIPPLE)) {
            this.ripple.setVisibility(8);
            this.imageButton.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    CircleButton.this.onCircleButtonClickListener.onClick(CircleButton.this.index);
                }
            });
            return;
        }
        this.ripple.setVisibility(0);
        this.ripple.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                CircleButton.this.onCircleButtonClickListener.onClick(CircleButton.this.index);
            }
        });
    }

    public void setShadowColor(int i) {
        this.shadowLayout.setShadowColor(i);
    }

    public void setShadowDx(float f) {
        this.shadowLayout.setmDx(f);
    }

    public void setShadowDy(float f) {
        this.shadowLayout.setmDy(f);
    }
}
