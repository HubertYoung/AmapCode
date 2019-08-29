package com.alipay.mobile.beehive.cityselect.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import com.alipay.mobile.beehive.R;
import java.util.List;

public class CityBladeView extends View {
    private int currSelection = -1;
    private Runnable dismissRunnable = new Runnable() {
        public final void run() {
            if (CityBladeView.this.popupWindow != null) {
                CityBladeView.this.popupWindow.dismiss();
            }
        }
    };
    private Handler handler = new Handler();
    private int maxSectionLength = 2;
    private OnCityBladeViewItemClickListener onItemClickListener;
    private Paint paint = new Paint();
    /* access modifiers changed from: private */
    public PopupWindow popupWindow;
    private String[] sections;
    private boolean showBkg = false;

    public interface OnCityBladeViewItemClickListener {
        void onItemClick(String str);
    }

    public CityBladeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.sections = context.getResources().getStringArray(R.array.cityselect_default_section_list);
    }

    public CityBladeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.sections = context.getResources().getStringArray(R.array.cityselect_default_section_list);
    }

    public CityBladeView(Context context) {
        super(context);
        this.sections = context.getResources().getStringArray(R.array.cityselect_default_section_list);
    }

    public void setSections(List<String> sections2) {
        if (sections2 != null && !sections2.isEmpty()) {
            this.sections = (String[]) sections2.toArray(new String[sections2.size()]);
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float textSize;
        super.onDraw(canvas);
        if (this.showBkg) {
            canvas.drawColor(Color.parseColor("#00000000"));
        }
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / this.sections.length;
        this.paint.setAntiAlias(true);
        if (height < 400) {
            textSize = getResources().getDimension(R.dimen.lifepay_letters_item_little_fontsize);
        } else {
            textSize = getResources().getDimension(R.dimen.lifepay_letters_item_fontsize);
        }
        this.paint.setTextSize(textSize);
        int charSizePx = (int) this.paint.measureText("A");
        if (singleHeight > charSizePx * 3) {
            singleHeight = charSizePx * 3;
        }
        for (int i = 0; i < this.sections.length; i++) {
            this.paint.setColor(Color.parseColor("#108EE9"));
            if (i == this.currSelection) {
                this.paint.setColor(Color.parseColor("#66108EE9"));
            }
            String section = this.sections[i];
            if (TextUtils.isEmpty(section)) {
                section = "";
            }
            String tag = section.toUpperCase();
            if (tag.length() > this.maxSectionLength) {
                tag = section.substring(0, this.maxSectionLength);
            }
            canvas.drawText(tag, ((float) (width / 2)) - (this.paint.measureText(tag) / 2.0f), (float) ((singleHeight * i) + singleHeight), this.paint);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldSelection = this.currSelection;
        int realHeight = getHeight();
        int charSizePx = (int) this.paint.measureText("A");
        if (getHeight() / this.sections.length > charSizePx * 3) {
            realHeight = charSizePx * 3 * this.sections.length;
        }
        int newSelection = (int) ((y / ((float) realHeight)) * ((float) this.sections.length));
        switch (action) {
            case 0:
                this.showBkg = true;
                if (oldSelection != newSelection && newSelection >= 0 && newSelection < this.sections.length) {
                    performItemClicked(newSelection);
                    this.currSelection = newSelection;
                    invalidate();
                    break;
                }
            case 1:
                this.showBkg = false;
                this.currSelection = -1;
                dismissPopup();
                invalidate();
                break;
            case 2:
                if (oldSelection != newSelection && newSelection >= 0 && newSelection < this.sections.length) {
                    performItemClicked(newSelection);
                    this.currSelection = newSelection;
                    invalidate();
                    break;
                }
        }
        return true;
    }

    private void dismissPopup() {
        this.handler.postDelayed(this.dismissRunnable, 800);
    }

    public void setOnItemClickListener(OnCityBladeViewItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private void performItemClicked(int item) {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClick(this.sections[item]);
        }
    }
}
