package com.alipay.mobile.tinyappcommon.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyapp.R;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.dynamicpanel.H5TinyPopMenu.TitleBarTheme;

public class TitleBarRightButtonView extends LinearLayout {
    private static final String a = TitleBarRightButtonView.class.getSimpleName();
    /* access modifiers changed from: private */
    public ImageView b;
    private ImageView c;
    /* access modifiers changed from: private */
    public ImageView d;

    public TitleBarRightButtonView(Context context, TitleBarTheme initialTheme) {
        super(context);
        a(context, initialTheme);
    }

    public TitleBarRightButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        a(context, null);
    }

    public TitleBarRightButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context, null);
    }

    public TitleBarRightButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        a(context, null);
    }

    private void a(Context context, TitleBarTheme initialTheme) {
        if (initialTheme == null) {
            initialTheme = TitleBarTheme.TITLE_BAR_THEME_BLUE;
        }
        setBackgroundResource(R.drawable.tiny_close_btn_bg);
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        layoutParams.gravity = 1;
        setLayoutParams(layoutParams);
        int padding = (int) (7.0f * TinyappUtils.getDensity(context));
        int paddingLarge = padding + (padding / 2);
        LayoutParams btnLayoutParams = new LayoutParams(-2, -1);
        this.b = new ImageView(context);
        this.b.setPadding(paddingLarge, 0, paddingLarge, 0);
        this.b.setLayoutParams(btnLayoutParams);
        this.b.setContentDescription(context.getResources().getString(R.string.tiny_more));
        this.c = new ImageView(context);
        LayoutParams dividerLp = new LayoutParams(1, -1);
        dividerLp.setMargins(0, padding, 0, padding);
        this.c.setLayoutParams(dividerLp);
        this.d = new ImageView(context);
        this.d.setLayoutParams(btnLayoutParams);
        this.d.setPadding(paddingLarge, 0, paddingLarge, 0);
        this.d.setContentDescription(context.getResources().getString(R.string.tiny_close));
        switchTheme(initialTheme);
        setOrientation(0);
        addView(this.b);
        addView(this.c);
        addView(this.d);
    }

    public void setOptionMenuOnClickListener(OnClickListener listener) {
        if (this.b != null) {
            this.b.setOnClickListener(listener);
        }
    }

    public void setCloseButtonOnClickListener(OnClickListener listener) {
        if (this.d != null) {
            this.d.setOnClickListener(listener);
        }
    }

    public void setCloseButtonOnLongClickListener(OnLongClickListener listener) {
        if (this.d != null) {
            this.d.setOnLongClickListener(listener);
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        H5Log.d(a, "switchToWhiteTheme...optionMenu=" + this.b);
        setBackgroundResource(R.drawable.tiny_close_btn_bg_white);
        if (this.b != null) {
            this.b.setImageResource(com.alipay.mobile.nebula.R.drawable.h5_white_titlebar_more_normal);
            final int bgColor = this.b.getDrawingCacheBackgroundColor();
            this.b.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case 0:
                            TitleBarRightButtonView.this.b.setBackgroundColor(Color.parseColor("#1A000000"));
                            break;
                        case 1:
                            TitleBarRightButtonView.this.b.setBackgroundColor(bgColor);
                            break;
                    }
                    return false;
                }
            });
        }
        if (this.c != null) {
            this.c.setBackgroundColor(Color.parseColor("#80FFFFFF"));
        }
        if (this.d != null) {
            this.d.setImageResource(R.drawable.close_white);
            final int bgColor2 = this.d.getDrawingCacheBackgroundColor();
            this.d.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case 0:
                            TitleBarRightButtonView.this.d.setBackgroundColor(Color.parseColor("#1A000000"));
                            break;
                        case 1:
                            TitleBarRightButtonView.this.d.setBackgroundColor(bgColor2);
                            break;
                    }
                    return false;
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        H5Log.d(a, "switchToBlueTheme...optionMenu=" + this.b);
        setBackgroundResource(R.drawable.tiny_close_btn_bg);
        if (this.b != null) {
            this.b.setImageResource(com.alipay.mobile.nebula.R.drawable.h5_titlebar_more_normal);
            final int bgColor = this.b.getDrawingCacheBackgroundColor();
            this.b.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case 0:
                            TitleBarRightButtonView.this.b.setBackgroundColor(Color.parseColor("#1A108EE9"));
                            break;
                        case 1:
                            TitleBarRightButtonView.this.b.setBackgroundColor(bgColor);
                            break;
                    }
                    return false;
                }
            });
        }
        if (this.c != null) {
            this.c.setBackgroundColor(Color.parseColor("#80B0B0B0"));
        }
        if (this.d != null) {
            this.d.setImageResource(R.drawable.close);
            final int bgColor2 = this.d.getDrawingCacheBackgroundColor();
            this.d.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case 0:
                            TitleBarRightButtonView.this.d.setBackgroundColor(Color.parseColor("#1A108EE9"));
                            break;
                        case 1:
                            TitleBarRightButtonView.this.d.setBackgroundColor(bgColor2);
                            break;
                    }
                    return false;
                }
            });
        }
    }

    public void switchTheme(final TitleBarTheme titleBarTheme) {
        post(new Runnable() {
            public final void run() {
                if (titleBarTheme == TitleBarTheme.TITLE_BAR_THEME_WHITE) {
                    TitleBarRightButtonView.this.a();
                } else if (titleBarTheme == TitleBarTheme.TITLE_BAR_THEME_BLUE) {
                    TitleBarRightButtonView.this.b();
                }
            }
        });
    }
}
