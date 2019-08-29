package com.autonavi.minimap.drive.restrictedarea;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.autonavi.minimap.R;
import java.util.List;

public class DriveIndexView extends View {
    private int DIVIDER_HEIGHT_DEFAULT = 3;
    private int TEXT_SIZE_DEFAULT = 9;
    private Context context;
    private List<dhs> dataList;
    private int divider;
    private int height;
    private ExpandableListView listView;
    private PopupWindow popupwindow;
    private RectF rectF;
    private String[] strArray = {"常", "A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    private int tempY;
    private int textHeight;
    private Paint textPaint;
    private TextView textView;
    private int width;

    public DriveIndexView(Context context2) {
        super(context2);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public DriveIndexView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R.styleable.DriveIndexView);
        this.textHeight = (int) obtainStyledAttributes.getDimension(R.styleable.DriveIndexView_indexTextSize, (float) this.TEXT_SIZE_DEFAULT);
        this.divider = (int) obtainStyledAttributes.getDimension(R.styleable.DriveIndexView_indexDividerHeight, (float) this.DIVIDER_HEIGHT_DEFAULT);
        obtainStyledAttributes.recycle();
        this.textPaint = new Paint(1);
        this.textPaint.setFakeBoldText(false);
        this.textPaint.setColor(context2.getResources().getColor(R.color.f_c_2));
        this.textPaint.setStyle(Style.FILL);
        this.textPaint.setTextSize((float) this.textHeight);
    }

    public void init(Context context2, ExpandableListView expandableListView) {
        this.listView = expandableListView;
        this.context = context2;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void updateIndex(List<dhs> list, String[] strArr) {
        this.dataList = list;
        this.strArray = strArr;
        requestLayout();
    }

    public void setIndexValue(int i, String str) {
        for (int i2 = 0; i2 < this.strArray.length; i2++) {
            if (i == i2) {
                this.strArray[i2] = str;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
            this.tempY = (int) (motionEvent.getY() / ((float) (this.textHeight + this.divider)));
            if (this.tempY >= 0 && this.strArray != null && this.strArray.length > 0 && this.dataList != null && this.dataList.size() > 0 && this.tempY < this.strArray.length) {
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
        super.onSizeChanged(i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            size = (int) (((float) getPaddingLeft()) + getWrapWidth() + ((float) getPaddingRight()));
        }
        if (mode2 != 1073741824) {
            size2 = (int) (((float) getPaddingTop()) + getWrapHeight() + ((float) getPaddingBottom()));
        }
        setMeasuredDimension(size, size2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
            canvas.drawText(str, (float) ((int) (this.rectF.left + ((this.rectF.width() - fArr2[0]) / 2.0f))), ((float) (this.textHeight * i)) + (((float) (this.textHeight / 2)) - ((fontMetrics.ascent + fontMetrics.descent) / 2.0f)) + ((float) (this.divider * i)), this.textPaint);
        }
    }

    private float getWrapWidth() {
        float[] fArr = new float[1];
        this.textPaint.getTextWidths("W", fArr);
        return fArr[0];
    }

    private float getWrapHeight() {
        return (float) (this.strArray.length * (this.textHeight + this.divider));
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
