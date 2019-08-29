package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.alipay.mobile.beehive.R;

public class CustomImgTextView extends GridCustomView {
    private int background;
    private ImageView imageView;
    private TextView textView;

    public CustomImgTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomImgTextView(Context context) {
        super(context);
    }

    public CustomImgTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void inflateView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_custom_image_text, this, true);
        setForegroundGravity(83);
        this.imageView = (ImageView) findViewById(R.id.custom_img);
        this.textView = (TextView) findViewById(R.id.custom_text);
        this.background = context.getResources().getColor(R.color.custon_text_backgroud);
    }

    public Drawable getDrawable() {
        return this.imageView.getDrawable();
    }

    public void setScaleType(ScaleType scaleType) {
        this.imageView.setScaleType(scaleType);
    }

    public void clear() {
        this.imageView.setImageDrawable(null);
        setForeground(null);
        setText(0);
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public TextView getTextView() {
        return this.textView;
    }

    public void setText(int num) {
        if (num == 0) {
            this.textView.setText("");
            this.textView.setVisibility(8);
            this.textView.setBackgroundColor(0);
            return;
        }
        this.textView.setText("+ " + String.valueOf(num));
        this.textView.setVisibility(0);
        this.textView.setBackgroundColor(this.background);
    }

    public void setFontDrawable(Drawable drawable) {
        setForeground(drawable);
    }
}
