package com.autonavi.minimap.route.run.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class RouteHorizontalSelectorView extends LinearLayout {
    private static final int DEFAULT_SELECT_ITEM = 2;
    private static final String HALF_MARATHON_DISTANCE = "21000";
    private static float TEXTSIZE_KM_MAX = 16.0f;
    private static float TEXTSIZE_KM_MIN = 9.0f;
    private static float TEXTSIZE_MAX = 36.0f;
    private static float TEXTSIZE_MIN = 18.0f;
    OnClickListener mClickListener = new OnClickListener() {
        public final void onClick(View view) {
            RouteHorizontalSelectorView.this.setCurrentItem(((Integer) view.getTag()).intValue());
        }
    };
    /* access modifiers changed from: private */
    public int mCurrenMath;
    /* access modifiers changed from: private */
    public int mCurrentX;
    /* access modifiers changed from: private */
    public ArrayList<TextView> mDistanceTvList;
    private a mListener;
    private int mPaddingWeight;
    private LinearLayout mRootLin;
    private int mScreenWeight;
    private RunRecommendScrollView mScrollView;
    private ArrayList<TextView> mUnitTvList;
    private int mUnitWeight;

    public interface a {
        void a(int i, String str);
    }

    public RouteHorizontalSelectorView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.run_remmend_horizontal_view, this, true);
        initView();
    }

    public RouteHorizontalSelectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.run_remmend_horizontal_view, this, true);
        initView();
    }

    public RouteHorizontalSelectorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.run_remmend_horizontal_view, this, true);
        initView();
    }

    private void initView() {
        this.mScreenWeight = ags.a(getContext()).width();
        this.mScrollView = (RunRecommendScrollView) findViewById(R.id.horizontal_scroll);
        this.mRootLin = (LinearLayout) findViewById(R.id.ll_scroll_root);
        setScrollListener();
        addViews(bindData());
    }

    public void setDefaultSelectItem(final int i) {
        ebr.a(true).postDelayed(new Runnable() {
            public final void run() {
                if (i < RouteHorizontalSelectorView.this.mDistanceTvList.size()) {
                    if (i < 0) {
                        RouteHorizontalSelectorView.this.setCurrentItem(2);
                    } else {
                        RouteHorizontalSelectorView.this.setCurrentItem(i);
                    }
                }
            }
        }, 500);
    }

    private void addViews(ArrayList<String> arrayList) {
        this.mUnitWeight = this.mScreenWeight / 5;
        if (this.mUnitWeight < agn.a(AMapPageUtil.getAppContext(), TEXTSIZE_MAX) * 2) {
            TEXTSIZE_MAX -= 2.0f;
            TEXTSIZE_MIN -= 2.0f;
            TEXTSIZE_KM_MAX -= 4.0f;
            TEXTSIZE_KM_MIN -= 2.0f;
        }
        LayoutParams layoutParams = new LayoutParams(this.mUnitWeight / 2, -2);
        this.mPaddingWeight = (this.mScreenWeight - this.mUnitWeight) / 2;
        this.mRootLin.removeAllViews();
        this.mDistanceTvList = new ArrayList<>();
        this.mUnitTvList = new ArrayList<>();
        TextView textView = new TextView(getContext());
        LayoutParams layoutParams2 = new LayoutParams(this.mPaddingWeight, 100);
        textView.setLayoutParams(layoutParams2);
        textView.setTextSize(1, TEXTSIZE_MAX);
        textView.setText(getContext().getString(R.string.text_start));
        textView.setVisibility(4);
        this.mRootLin.addView(textView);
        for (int i = 0; i < arrayList.size(); i++) {
            TextView textView2 = new TextView(getContext());
            textView2.setText(arrayList.get(i));
            textView2.setTextSize(1, TEXTSIZE_MIN);
            textView2.setTextColor(getResources().getColor(R.color.f_c_4));
            textView2.setGravity(5);
            textView2.setTag(Integer.valueOf(i));
            NoDBClickUtil.a((View) textView2, this.mClickListener);
            TextView textView3 = new TextView(AMapPageUtil.getAppContext());
            textView3.setLayoutParams(layoutParams);
            textView3.setText(getContext().getString(R.string.text_km));
            textView3.setTextSize(1, TEXTSIZE_KM_MIN);
            textView3.setTextColor(getResources().getColor(R.color.f_c_4));
            efx.a(textView3);
            textView3.setTag(Integer.valueOf(i));
            NoDBClickUtil.a((View) textView3, this.mClickListener);
            if (i == arrayList.size() - 1) {
                textView2.setLayoutParams(new LayoutParams(this.mUnitWeight, agn.a(AMapPageUtil.getAppContext(), 50.0f)));
                textView2.setGravity(1);
            } else {
                efx.a(textView2);
                textView2.setLayoutParams(layoutParams);
            }
            this.mRootLin.addView(textView2);
            if (i != arrayList.size() - 1) {
                this.mRootLin.addView(textView3);
            }
            this.mDistanceTvList.add(textView2);
            this.mUnitTvList.add(textView3);
        }
        TextView textView4 = new TextView(getContext());
        textView4.setLayoutParams(layoutParams2);
        textView4.setText(getContext().getString(R.string.text_end));
        textView4.setTextSize(1, TEXTSIZE_MAX);
        textView4.setVisibility(4);
        this.mRootLin.addView(textView4);
    }

    private void setScrollListener() {
        this.mScrollView.setmScrollViewListener(new com.autonavi.minimap.route.run.view.RunRecommendScrollView.a() {
            public final void a(ScrollType scrollType, int i) {
                if (scrollType == ScrollType.IDLE) {
                    RouteHorizontalSelectorView.this.setCurrentItem(RouteHorizontalSelectorView.this.getCurrentItemPoint(i));
                    return;
                }
                int abs = Math.abs(i - RouteHorizontalSelectorView.this.mCurrentX);
                if (abs > 10 && Math.abs(abs - RouteHorizontalSelectorView.this.mCurrenMath) > 10) {
                    RouteHorizontalSelectorView.this.mCurrentX = i;
                    RouteHorizontalSelectorView.this.mCurrenMath = abs;
                    RouteHorizontalSelectorView.this.setItemTextStyle(i);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public int getCurrentItemPoint(int i) {
        if (this.mUnitWeight == 0) {
            return 0;
        }
        int i2 = (this.mScreenWeight / 2) + i;
        if (this.mDistanceTvList == null || this.mDistanceTvList.isEmpty()) {
            return 0;
        }
        for (int i3 = 0; i3 < this.mDistanceTvList.size() + 1; i3++) {
            if ((this.mUnitWeight * i3) + this.mPaddingWeight > i2) {
                return i3 - 1;
            }
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public void setCurrentItem(int i) {
        String str;
        int i2 = ((this.mUnitWeight * (i + 1)) - ((this.mScreenWeight / 2) + (this.mUnitWeight / 2))) + this.mPaddingWeight;
        RunRecommendScrollView runRecommendScrollView = this.mScrollView;
        runRecommendScrollView.smoothScrollTo(i2, runRecommendScrollView.getScrollY());
        setItemTextStyle(i2);
        if (this.mListener != null && this.mDistanceTvList != null && i >= 0 && i < this.mDistanceTvList.size()) {
            if (i == this.mDistanceTvList.size() - 1) {
                str = HALF_MARATHON_DISTANCE;
            } else {
                str = String.valueOf(Float.parseFloat(this.mDistanceTvList.get(i).getText().toString()) * 1000.0f);
            }
            this.mListener.a(i, str);
        }
    }

    /* access modifiers changed from: private */
    public void setItemTextStyle(int i) {
        int i2 = 0;
        while (i2 < this.mDistanceTvList.size()) {
            TextView textView = this.mDistanceTvList.get(i2);
            int abs = Math.abs((this.mScreenWeight / 2) - ((((this.mUnitWeight * i2) + this.mPaddingWeight) - i) + (this.mUnitWeight / 2)));
            if (abs <= this.mScreenWeight / 2) {
                textView.setTextSize(1, getDistanceTextSize(abs, i2 == this.mDistanceTvList.size() - 1));
                textView.setTextColor(getTextColor(abs));
                if (i2 < this.mUnitTvList.size()) {
                    TextView textView2 = this.mUnitTvList.get(i2);
                    textView2.setTextSize(1, getUnitTextSize(abs));
                    textView2.setTextColor(getTextColor(abs));
                }
            }
            i2++;
        }
    }

    private float getDistanceTextSize(int i, boolean z) {
        if (!z) {
            return TEXTSIZE_MAX - (((float) i) * ((TEXTSIZE_MAX - TEXTSIZE_MIN) / ((float) (this.mScreenWeight / 2))));
        }
        return 27.0f - (((float) i) * (12.0f / ((float) (this.mScreenWeight / 2))));
    }

    private float getUnitTextSize(int i) {
        return TEXTSIZE_KM_MAX - (((float) i) * ((TEXTSIZE_KM_MAX - TEXTSIZE_KM_MIN) / ((float) (this.mScreenWeight / 2))));
    }

    private int getTextColor(int i) {
        int i2;
        int i3;
        int i4;
        if (i < this.mUnitWeight) {
            float f = ((float) i) / ((float) this.mUnitWeight);
            i4 = 66 - ((int) (-111.0f * f));
            i3 = 135 - ((int) (-42.0f * f));
            i2 = 255 - ((int) (f * 78.0f));
        } else {
            i4 = 158 - ((int) ((((float) (i - this.mUnitWeight)) / ((float) this.mUnitWeight)) * -19.0f));
            i2 = i4;
            i3 = i2;
        }
        return Color.rgb(i4, i3, i2);
    }

    public void setOnHorScrollSelectListener(a aVar) {
        this.mListener = aVar;
    }

    private ArrayList bindData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 3; i < 11; i++) {
            arrayList.add(String.valueOf(i));
        }
        arrayList.add(getContext().getString(R.string.run_recommend_half_marathon));
        return arrayList;
    }
}
