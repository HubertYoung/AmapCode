package com.autonavi.minimap.auidebugger.boommenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.auidebugger.boommenu.Types.ClickEffectType;
import java.lang.ref.WeakReference;

public class HamButton extends FrameLayout {
    private ClickEffectType clickEffectType;
    private FrameLayout frameLayout;
    private int height;
    private ImageView imageView;
    /* access modifiers changed from: private */
    public int index;
    private Context mContext;
    /* access modifiers changed from: private */
    public a onHamButtonClickListener;
    private View ripple;
    private ShadowLayout shadowLayout;
    private TextView textView;
    private int width;

    public interface a {
        void onClick(int i);
    }

    public HamButton(Context context) {
        this(context, null);
    }

    public HamButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.clickEffectType = ClickEffectType.RIPPLE;
        this.width = 0;
        cnu.a();
        this.height = (int) cnu.a(66.0f);
        this.mContext = context;
        if (VERSION.SDK_INT >= 21) {
            LayoutInflater.from(this.mContext).inflate(R.layout.ham_button, this, true);
        } else {
            LayoutInflater.from(this.mContext).inflate(R.layout.ham_button_below_lollipop, this, true);
        }
        this.shadowLayout = (ShadowLayout) findViewById(R.id.shadow_layout);
        this.frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        this.ripple = findViewById(R.id.ripple);
        this.imageView = (ImageView) findViewById(R.id.image);
        this.textView = (TextView) findViewById(R.id.text);
        cnu.a();
        this.width = (cnu.a(getContext()) * 8) / 9;
        cnu.a();
        this.height = (int) cnu.a(66.0f);
        LayoutParams layoutParams = (LayoutParams) this.frameLayout.getLayoutParams();
        int i = this.width;
        cnu.a();
        layoutParams.width = i - ((int) cnu.a(8.0f));
        this.frameLayout.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = this.shadowLayout.getLayoutParams();
        layoutParams2.width = this.width;
        int i2 = this.height;
        cnu.a();
        layoutParams2.height = i2 + ((int) cnu.a(4.0f));
        this.shadowLayout.setLayoutParams(layoutParams2);
    }

    public void setOnHamButtonClickListener(a aVar, int i) {
        this.onHamButtonClickListener = aVar;
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

    public ImageView getImageView() {
        return this.imageView;
    }

    public TextView getTextView() {
        return this.textView;
    }

    public ShadowLayout getShadowLayout() {
        return this.shadowLayout;
    }

    public void setRipple(ClickEffectType clickEffectType2) {
        this.clickEffectType = clickEffectType2;
        if (VERSION.SDK_INT < 21 || !clickEffectType2.equals(ClickEffectType.RIPPLE)) {
            this.ripple.setVisibility(8);
            this.frameLayout.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    HamButton.this.onHamButtonClickListener.onClick(HamButton.this.index);
                }
            });
            return;
        }
        this.ripple.setVisibility(0);
        this.ripple.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                HamButton.this.onHamButtonClickListener.onClick(HamButton.this.index);
            }
        });
    }

    public void setColor(int i, int i2) {
        cnu.a();
        FrameLayout frameLayout2 = this.frameLayout;
        int i3 = this.width;
        int i4 = this.height;
        WeakReference weakReference = new WeakReference(Bitmap.createBitmap(i3, i4, Config.ARGB_8888));
        Canvas canvas = new Canvas((Bitmap) weakReference.get());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(i);
        float f = (float) i3;
        float f2 = (float) i4;
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, f, f2), cnu.a(3.0f), cnu.a(3.0f), paint);
        WeakReference weakReference2 = new WeakReference(Bitmap.createBitmap(i3, i4, Config.ARGB_8888));
        Canvas canvas2 = new Canvas((Bitmap) weakReference2.get());
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(i2);
        canvas2.drawRoundRect(new RectF(0.0f, 0.0f, f, f2), cnu.a(3.0f), cnu.a(3.0f), paint2);
        WeakReference weakReference3 = new WeakReference(new StateListDrawable());
        ((StateListDrawable) weakReference3.get()).addState(new int[]{16842919}, (Drawable) new WeakReference(new BitmapDrawable(frameLayout2.getContext().getResources(), (Bitmap) weakReference.get())).get());
        ((StateListDrawable) weakReference3.get()).addState(StateSet.WILD_CARD, (Drawable) new WeakReference(new BitmapDrawable(frameLayout2.getContext().getResources(), (Bitmap) weakReference2.get())).get());
        if (VERSION.SDK_INT >= 16) {
            frameLayout2.setBackground((Drawable) weakReference3.get());
        } else {
            frameLayout2.setBackgroundDrawable((Drawable) weakReference3.get());
        }
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
