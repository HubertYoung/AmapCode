package com.autonavi.minimap.route.train.stationlist;

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
import java.util.Arrays;
import java.util.List;

public class ListIndexView extends View {
    private static final String[] ALPHABET = {"A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "O", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    private static final String HISTORY = "历史";
    private static final String HOT = "热门";
    private static final String LOCATION = "定位";
    private String[] mAlphabets;
    private Context mContext;
    private List<ejq> mDataList;
    private int mDivider;
    private int mHeight;
    private ArrayList<String> mIndexList = new ArrayList<>();
    private ExpandableListView mListView;
    private int mMarginBottom;
    private int mMarginTop;
    private Paint mPaint;
    private PopupWindow mPopupWindow;
    private RectF mRectF;
    private int mTextHeight;
    private TextView mTextView;
    private int mWidth;
    private float[] mWordWidths = new float[2];

    public ListIndexView(Context context) {
        super(context);
    }

    public ListIndexView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ListIndexView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void init(Context context, List<ejq> list, ExpandableListView expandableListView) {
        this.mDataList = list;
        this.mListView = expandableListView;
        this.mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void setAlphabet(String[] strArr) {
        this.mAlphabets = strArr;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
            int y = (int) ((motionEvent.getY() - ((float) this.mMarginTop)) / ((float) (this.mTextHeight + this.mDivider)));
            if (y >= 0 && y < this.mIndexList.size()) {
                int i = -1;
                for (int i2 = 0; i2 < this.mDataList.size(); i2++) {
                    String str = this.mDataList.get(i2).a;
                    if ("定位城市".equals(str)) {
                        str = LOCATION;
                    } else if ("历史城市".equals(str)) {
                        str = HISTORY;
                    } else if ("热门城市".equals(str)) {
                        str = HOT;
                    }
                    if (str.equalsIgnoreCase(this.mIndexList.get(y))) {
                        i = i2;
                    }
                }
                if (i != -1) {
                    this.mListView.setSelectedGroup(i);
                }
                showPopupWindow(this.mIndexList.get(y));
                postInvalidate();
            }
            return true;
        }
        hidePopupWindow();
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mWidth = i;
        this.mHeight = i2;
        initTextAttribute();
        super.onSizeChanged(i, i2, i3, i4);
    }

    private void initVerticalMargin(int i) {
        this.mMarginTop = 5;
        this.mMarginBottom = 5;
        double dimension = (double) (((((int) getResources().getDimension(R.dimen.f_s_11)) * this.mIndexList.size()) * 4) / 3);
        double d = (double) i;
        if (dimension < 0.9d * d && dimension >= d * 0.8d) {
            this.mMarginTop = (int) (d * 0.05d);
        } else if (dimension < 0.8d * d && dimension >= d * 0.7d) {
            this.mMarginTop = (int) (d * 0.1d);
        } else if (dimension < 0.7d * d) {
            this.mMarginTop = (int) (d * 0.15d);
        }
        this.mMarginBottom = this.mMarginTop;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mTextHeight == 0) {
            initTextAttribute();
        }
    }

    private void initTextAttribute() {
        initVerticalMargin(this.mHeight);
        this.mTextHeight = ((this.mHeight - this.mMarginTop) - this.mMarginBottom) / (this.mIndexList.size() < ALPHABET.length ? ALPHABET.length : this.mIndexList.size());
        this.mDivider = this.mTextHeight / 3;
        this.mTextHeight -= this.mDivider;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPaint == null) {
            this.mPaint = new Paint(1);
            this.mPaint.setFakeBoldText(false);
            this.mPaint.setColor(-13421773);
            this.mPaint.setStyle(Style.FILL);
        }
        this.mPaint.setTextSize((float) this.mTextHeight);
        if (this.mRectF == null) {
            this.mRectF = new RectF(0.0f, 0.0f, (float) this.mWidth, (float) this.mHeight);
        } else {
            this.mRectF.right = (float) this.mWidth;
            this.mRectF.bottom = (float) this.mHeight;
        }
        FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        for (int i = 0; i < this.mIndexList.size(); i++) {
            this.mWordWidths[0] = 0.0f;
            this.mWordWidths[1] = 0.0f;
            this.mPaint.getTextWidths(this.mIndexList.get(i), this.mWordWidths);
            String str = this.mIndexList.get(i);
            canvas.drawText(str, (float) ((int) (this.mRectF.left + ((this.mRectF.width() - (this.mWordWidths[0] + this.mWordWidths[1])) / 2.0f))), ((float) (this.mTextHeight * i)) + ((((float) this.mTextHeight) / 2.0f) - ((fontMetrics.ascent + fontMetrics.descent) / 2.0f)) + ((float) this.mMarginTop) + ((float) (this.mDivider * i)), this.mPaint);
        }
    }

    public void showPopupWindow(String str) {
        if (this.mPopupWindow == null) {
            View inflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.popupwindow, null);
            this.mTextView = (TextView) inflate.findViewById(R.id.text);
            int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.index_view_selected_size);
            this.mPopupWindow = new PopupWindow(inflate, dimensionPixelSize, dimensionPixelSize);
        }
        if (str.length() > 1) {
            this.mTextView.setTextSize(1, 25.0f);
        } else {
            this.mTextView.setTextSize(1, 30.0f);
        }
        this.mTextView.setText(str);
        this.mPopupWindow.showAtLocation(this.mListView, 17, 0, -80);
    }

    public void hidePopupWindow() {
        try {
            this.mPopupWindow.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIndexViewVisible(boolean z, boolean z2) {
        this.mIndexList.clear();
        this.mIndexList.add(LOCATION);
        if (z) {
            this.mIndexList.add(HISTORY);
        }
        if (z2) {
            this.mIndexList.add(HOT);
        }
        this.mIndexList.addAll(Arrays.asList((this.mAlphabets == null || this.mAlphabets.length <= 0) ? ALPHABET : this.mAlphabets));
    }
}
