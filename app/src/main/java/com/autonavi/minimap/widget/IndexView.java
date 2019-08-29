package com.autonavi.minimap.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class IndexView extends View {
    private Context context;
    private ArrayList<cew> dataList;
    private int divider;
    private int end;
    private int height;
    private ExpandableListView listView;
    private int pageId = 0;
    private PopupWindow popupwindow;
    private RectF rectF;
    private int select = 0;
    private int start;
    private String[] strArray = {"常", "A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    private int tempY;
    private int textHeight;
    private Paint textPaint;
    private TextView textView;
    private int viewId = 0;
    private int width;

    public IndexView(Context context2) {
        super(context2);
    }

    public void setPageViewId(int i, int i2) {
        this.pageId = i;
        this.viewId = i2;
    }

    public IndexView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
    }

    public IndexView(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
    }

    public void init(Context context2, ArrayList<cew> arrayList, ExpandableListView expandableListView) {
        this.dataList = arrayList;
        this.listView = expandableListView;
        this.context = context2;
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.start = 5;
        this.end = 5;
    }

    public void setIndexValue(int i, String str) {
        for (int i2 = 0; i2 < this.strArray.length; i2++) {
            if (i == i2) {
                this.strArray[i2] = str;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        motionEvent.getAction();
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
            this.tempY = (int) ((motionEvent.getY() - ((float) (this.start * 2))) / ((float) (this.textHeight + this.divider)));
            if (this.tempY >= 0 && this.tempY < this.strArray.length) {
                int i = -1;
                for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                    String str = this.dataList.get(i2).a;
                    if ("常用城市".equals(str)) {
                        str = "常";
                    } else if ("热门城市".equals(str)) {
                        str = "热";
                    }
                    if (str.equals(this.strArray[this.tempY])) {
                        i = i2;
                    }
                }
                if (i != -1) {
                    this.listView.setSelectedGroup(i);
                }
                showPopupWindow(this.strArray[this.tempY]);
                this.select = this.tempY;
                postInvalidate();
            }
            return true;
        }
        hidePopupWindow();
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        this.width = i;
        this.height = i2;
        this.textHeight = ((i2 - this.start) - this.end) / this.strArray.length;
        this.divider = this.textHeight / 3;
        this.textHeight -= this.divider;
        super.onSizeChanged(i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.textPaint == null) {
            this.textPaint = new Paint(1);
            this.textPaint.setFakeBoldText(false);
            this.textPaint.setColor(-13421773);
            this.textPaint.setStyle(Style.FILL);
        }
        this.textPaint.setTextSize((float) this.textHeight);
        if (this.rectF == null) {
            float[] fArr = new float[1];
            this.textPaint.getTextWidths("W", fArr);
            this.rectF = new RectF((((float) this.width) / 2.0f) - (fArr[0] / 2.0f), 0.0f, (float) this.width, (float) this.height);
        } else {
            this.rectF.right = (float) this.width;
            this.rectF.bottom = (float) this.height;
        }
        FontMetrics fontMetrics = this.textPaint.getFontMetrics();
        for (int i = 0; i < this.strArray.length; i++) {
            float[] fArr2 = new float[1];
            this.textPaint.getTextWidths(this.strArray[i], fArr2);
            String str = this.strArray[i];
            canvas.drawText(str, (float) ((int) (this.rectF.left + ((this.rectF.width() - fArr2[0]) / 2.0f))), ((float) (this.textHeight * i)) + (((float) (this.textHeight / 2)) - ((fontMetrics.ascent + fontMetrics.descent) / 2.0f)) + ((float) (this.start * 2)) + ((float) (this.divider * i)), this.textPaint);
        }
    }

    public void showPopupWindow(String str) {
        if (this.popupwindow == null) {
            View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.popupwindow, null);
            this.textView = (TextView) inflate.findViewById(R.id.text);
            int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.index_view_selected_size);
            this.popupwindow = new PopupWindow(inflate, dimensionPixelSize, dimensionPixelSize);
        }
        this.textView.setText(str);
        this.popupwindow.showAtLocation(this.listView, 17, 0, -80);
    }

    public void hidePopupWindow() {
        try {
            this.popupwindow.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
