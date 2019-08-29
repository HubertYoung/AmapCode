package com.autonavi.minimap.route.coach.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.coach.model.CoachPlanData;
import com.autonavi.minimap.route.coach.model.CoachPlanItem;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CoachPlanAdapter extends BaseAdapter {
    private Context mContext;
    private int mImgHeight = getImgContainerHeight();
    private LayoutInflater mInflater;
    public ArrayList<CoachPlanItem> mListData;

    static class a {
        ViewGroup a;
        ViewGroup b;
        TextView c;
        TextView d;
        TextView e;
        TextView f;
        TextView g;
        TextView h;
        TextView i;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public CoachPlanAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    private int getImgContainerHeight() {
        TextView textView = new TextView(this.mContext);
        textView.setText("测试");
        textView.setTextSize(1, 15.0f);
        textView.setIncludeFontPadding(false);
        textView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        return textView.getMeasuredHeight();
    }

    public int getCount() {
        if (this.mListData != null) {
            return this.mListData.size();
        }
        return 0;
    }

    public CoachPlanItem getItem(int i) {
        if (this.mListData == null || i >= this.mListData.size()) {
            return null;
        }
        return this.mListData.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        a aVar;
        if (view == null) {
            aVar = new a(0);
            view2 = this.mInflater.inflate(R.layout.coach_list_plan_item, null);
            aVar.a = (ViewGroup) view2.findViewById(R.id.coach_start_img_container);
            aVar.b = (ViewGroup) view2.findViewById(R.id.coach_arr_img_container);
            aVar.e = (TextView) view2.findViewById(R.id.coach_shift_type);
            aVar.f = (TextView) view2.findViewById(R.id.coach_departure_name);
            aVar.g = (TextView) view2.findViewById(R.id.coach_arrive_name);
            aVar.h = (TextView) view2.findViewById(R.id.coach_plan_price);
            aVar.c = (TextView) view2.findViewById(R.id.coach_departure_time);
            aVar.d = (TextView) view2.findViewById(R.id.coach_departure_time_shift_tip);
            aVar.i = (TextView) view2.findViewById(R.id.coach_plan_count);
            view2.setTag(aVar);
        } else {
            view2 = view;
            aVar = (a) view.getTag();
        }
        dataSet(aVar, i);
        return view2;
    }

    private void dataSet(a aVar, int i) {
        if (i <= getCount()) {
            CoachPlanItem coachPlanItem = this.mListData.get(i);
            fitStationImgHeight(aVar);
            aVar.e.setText(coachPlanItem.shiftType == 1 ? this.mContext.getString(R.string.coach_shift_type) : "");
            aVar.f.setText(coachPlanItem.depName);
            aVar.g.setText(coachPlanItem.arrName);
            aVar.h.setText(getPriceSpan(coachPlanItem.fullPrice, coachPlanItem.stock));
            aVar.c.setText(getDepartSpan(coachPlanItem));
            aVar.d.setVisibility((coachPlanItem.shiftType != 1 || TextUtils.isEmpty(coachPlanItem.lastDepartTime)) ? 8 : 0);
            aVar.i.setText(getStockSpan(this.mContext, coachPlanItem.stock));
        }
    }

    private void fitStationImgHeight(a aVar) {
        aVar.a.getLayoutParams().height = this.mImgHeight;
        aVar.a.requestLayout();
        aVar.b.getLayoutParams().height = this.mImgHeight;
        aVar.b.requestLayout();
    }

    private CharSequence getDepartSpan(CoachPlanItem coachPlanItem) {
        if (coachPlanItem == null) {
            return null;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (coachPlanItem.shiftType != 1 || TextUtils.isEmpty(coachPlanItem.lastDepartTime)) {
            spannableStringBuilder.append(coachPlanItem.departTime);
        } else {
            spannableStringBuilder.append(coachPlanItem.lastDepartTime);
        }
        return spannableStringBuilder;
    }

    public SpannableStringBuilder getPriceSpan(double d, int i) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("¥");
        String format = new DecimalFormat("#0.0").format(d / 100.0d);
        if (TextUtils.equals(format.split("\\.")[1], "0")) {
            format = format.split("\\.")[0];
        }
        spannableStringBuilder.append(format);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(this.mContext.getResources().getDimensionPixelSize(R.dimen.f_s_13)), 0, 1, 33);
        spannableStringBuilder.setSpan(new StyleSpan(1), 1, spannableStringBuilder.length(), 33);
        if (i > 0) {
            spannableStringBuilder.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.f_c_8)), 0, spannableStringBuilder.length(), 33);
        } else {
            spannableStringBuilder.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.f_c_3)), 0, spannableStringBuilder.length(), 33);
        }
        return spannableStringBuilder;
    }

    public SpannableStringBuilder getStockSpan(Context context, int i) {
        StringBuilder sb;
        if (i < 0) {
            return new SpannableStringBuilder("");
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");
        if (i != 0) {
            if (i > 5) {
                sb = new StringBuilder();
            } else {
                sb = new StringBuilder();
                sb.append(this.mContext.getString(R.string.coach_stock_only));
            }
            sb.append(i);
            sb.append(this.mContext.getString(R.string.coach_stock_unit));
            spannableStringBuilder.append(sb.toString());
            spannableStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(i > 5 ? R.color.f_c_3 : R.color.f_c_8)), 0, spannableStringBuilder.length(), 33);
        } else {
            spannableStringBuilder.append(this.mContext.getString(R.string.coach_no_stock));
            spannableStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_3)), 0, spannableStringBuilder.length(), 33);
        }
        return spannableStringBuilder;
    }

    public void setData(CoachPlanData coachPlanData) {
        this.mListData = coachPlanData.planItems;
        notifyDataSetChanged();
    }

    public void setListDataChanged(ArrayList<CoachPlanItem> arrayList) {
        this.mListData = arrayList;
        notifyDataSetChanged();
    }
}
