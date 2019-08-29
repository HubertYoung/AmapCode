package com.alipay.mobile.beehive.compositeui.wheelview.picker;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.compositeui.dialog.CustomDialog;
import com.alipay.mobile.beehive.compositeui.wheelview.widget.WheelView;
import com.alipay.mobile.beehive.compositeui.wheelview.widget.WheelView.OnWheelViewListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LimitedHoursPickerDialog extends CustomDialog {
    private final String HOUR_UNIT = ":00";
    private Activity activity;
    private final String curDayString;
    private final Calendar curTimeCalendar;
    private final long duartion;
    private final String endDayString;
    private final int endHour;
    private final Calendar endTimeCalendar;
    private WheelView mLeftOptionView;
    protected List<String> mLeftOptions = new ArrayList();
    /* access modifiers changed from: private */
    public int mLeftSelectedIndex = -1;
    /* access modifiers changed from: private */
    public String mLeftSelectedOption = "";
    /* access modifiers changed from: private */
    public WheelView mRightOptionView;
    protected List<String> mRightOptions = new ArrayList();
    /* access modifiers changed from: private */
    public int mRightSelectedIndex = -1;
    /* access modifiers changed from: private */
    public String mRightSelectedOption = "";
    /* access modifiers changed from: private */
    public Handler mUIHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public OnOptionPickListener onOptionPickListener;
    /* access modifiers changed from: private */
    public Runnable refreshRightOptionsTask = new Runnable() {
        public final void run() {
            LimitedHoursPickerDialog.this.mRightOptions = LimitedHoursPickerDialog.this.getRightList();
            LimitedHoursPickerDialog.this.mRightOptionView.setItems(LimitedHoursPickerDialog.this.mRightOptions);
        }
    };
    private final String startDayString;
    private final int startHour;
    private final Calendar startTimeCalendar;
    private final long startTimestamp;

    public interface OnOptionPickListener {
        void onOptionPicked(long j);
    }

    public LimitedHoursPickerDialog(Activity activity2, String title, String positiveString, String negativeString, final long startTimestamp2, long duartion2, int startHour2, int endHour2) {
        super(activity2, title, positiveString, negativeString);
        this.activity = activity2;
        this.startTimestamp = startTimestamp2;
        this.duartion = duartion2;
        this.startHour = startHour2;
        this.endHour = endHour2;
        this.startTimeCalendar = Calendar.getInstance();
        this.startTimeCalendar.setTimeInMillis(startTimestamp2);
        this.startDayString = getDayString(this.startTimeCalendar);
        this.endTimeCalendar = Calendar.getInstance();
        this.endTimeCalendar.setTimeInMillis(startTimestamp2 + duartion2);
        this.endDayString = getDayString(this.endTimeCalendar);
        this.curTimeCalendar = Calendar.getInstance();
        this.curTimeCalendar.setTimeInMillis(System.currentTimeMillis());
        this.curDayString = getDayString(this.curTimeCalendar);
        this.mLeftOptions.addAll(getLeftList());
        this.mRightOptions.addAll(getRightList());
        getPositiveBtn().setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!LimitedHoursPickerDialog.this.mRightSelectedOption.equals(LimitedHoursPickerDialog.this.getResources().getString(R.string.not_optional))) {
                    LimitedHoursPickerDialog.this.dismiss();
                    if (LimitedHoursPickerDialog.this.onOptionPickListener != null) {
                        Calendar result = Calendar.getInstance();
                        result.setTimeInMillis(startTimestamp2 + ((long) (LimitedHoursPickerDialog.this.mLeftSelectedIndex * 86400 * 1000)));
                        result.set(11, Integer.valueOf(LimitedHoursPickerDialog.this.mRightSelectedOption.replace(":00", "")).intValue());
                        result.set(12, 0);
                        result.set(13, 0);
                        result.set(14, 0);
                        LimitedHoursPickerDialog.this.onOptionPickListener.onOptionPicked(result.getTimeInMillis());
                    }
                }
            }
        });
        init();
    }

    @NonNull
    private List<String> getLeftList() {
        Calendar startDate = Calendar.getInstance();
        long numDay = this.duartion / 86400000;
        List leftOptions = new ArrayList();
        for (int i = 0; ((long) i) <= numDay; i++) {
            startDate.setTimeInMillis(this.startTimestamp + ((long) (86400000 * i)));
            String dayOption = getDayString(startDate);
            if (this.curDayString.equals(dayOption)) {
                leftOptions.add(getResources().getString(R.string.today));
            } else {
                leftOptions.add(dayOption);
            }
            if (((long) i) == numDay && !this.endDayString.equals(dayOption)) {
                leftOptions.add(this.endDayString);
            }
        }
        return leftOptions;
    }

    @NonNull
    private String getDayString(Calendar startDate) {
        return (startDate.get(2) + 1) + "月" + startDate.get(5) + "日";
    }

    /* access modifiers changed from: private */
    @NonNull
    public List<String> getRightList() {
        List rightOptions = new ArrayList();
        if (this.mLeftSelectedIndex == 0) {
            int start = this.startTimeCalendar.get(11);
            if (start < this.startHour) {
                start = this.startHour;
            }
            int end = this.endHour;
            if (this.startDayString.equals(this.endTimeCalendar) && this.endTimeCalendar.get(11) < this.endHour) {
                end = this.endTimeCalendar.get(11);
            }
            for (int j = start; j <= end; j++) {
                rightOptions.add(j + ":00");
            }
        } else if (this.mLeftSelectedIndex == this.mLeftOptions.size() - 1) {
            int start2 = this.startHour;
            int end2 = this.endTimeCalendar.get(11);
            if (end2 > this.endHour) {
                end2 = this.endHour;
            }
            for (int j2 = start2; j2 <= end2; j2++) {
                rightOptions.add(j2 + ":00");
            }
        } else {
            for (int j3 = this.startHour; j3 <= this.endHour; j3++) {
                rightOptions.add(j3 + ":00");
            }
        }
        if (rightOptions.isEmpty()) {
            rightOptions.add(getResources().getString(R.string.not_optional));
        }
        return rightOptions;
    }

    private void init() {
        TextView titleView = getTitleView();
        MarginLayoutParams lp = (MarginLayoutParams) titleView.getLayoutParams();
        if (lp == null) {
            lp = new MarginLayoutParams(-2, -2);
        }
        int margin = DensityUtil.dip2px(getContext(), 14.0f);
        lp.setMargins(margin, margin, margin, margin);
        titleView.setLayoutParams(lp);
        initContentView();
    }

    public void setRightSelected(String selectedOption) {
        this.mRightSelectedOption = selectedOption;
        this.mRightOptionView.setSelectedItem(selectedOption);
        this.mRightSelectedIndex = this.mRightOptionView.getSelectedIndex();
    }

    public void setRightSelectedIndex(int selectedOptionIndex) {
        this.mRightSelectedIndex = selectedOptionIndex;
        this.mRightSelectedOption = this.mRightOptions.get(selectedOptionIndex);
        this.mRightOptionView.setSelectedItem(this.mRightSelectedOption);
    }

    public void setLeftSelected(String selectedOption) {
        this.mLeftSelectedOption = selectedOption;
        this.mLeftOptionView.setSelectedItem(selectedOption);
        this.mLeftSelectedIndex = this.mRightOptionView.getSelectedIndex();
    }

    public void setLeftSelectedIndex(int selectedOptionIndex) {
        this.mLeftSelectedIndex = selectedOptionIndex;
        this.mLeftSelectedOption = this.mLeftOptions.get(selectedOptionIndex);
        this.mLeftOptionView.setSelectedItem(this.mLeftSelectedOption);
    }

    public void setOnOptionPickListener(OnOptionPickListener listener) {
        this.onOptionPickListener = listener;
    }

    public View getContentView() {
        if (this.mRightOptions == null || this.mRightOptions.size() == 0) {
            return null;
        }
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(1);
        layout.setGravity(48);
        layout.setLayoutParams(new LayoutParams(-1, -2));
        addLine(layout);
        addWheelView(layout);
        return layout;
    }

    private void addWheelView(LinearLayout layout) {
        LinearLayout wheelLayout = new LinearLayout(getContext());
        wheelLayout.setOrientation(0);
        wheelLayout.setGravity(48);
        this.mLeftOptionView = getWheelView(this.mLeftOptions, new OnWheelViewListener() {
            public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                LimitedHoursPickerDialog.this.mLeftSelectedOption = item;
                LimitedHoursPickerDialog.this.mLeftSelectedIndex = selectedIndex;
                LimitedHoursPickerDialog.this.mUIHandler.removeCallbacks(LimitedHoursPickerDialog.this.refreshRightOptionsTask);
                LimitedHoursPickerDialog.this.mUIHandler.postDelayed(LimitedHoursPickerDialog.this.refreshRightOptionsTask, 200);
            }
        });
        this.mRightOptionView = getWheelView(this.mRightOptions, new OnWheelViewListener() {
            public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                LimitedHoursPickerDialog.this.mRightSelectedOption = item;
                LimitedHoursPickerDialog.this.mRightSelectedIndex = selectedIndex;
            }
        });
        addWheelToLL(wheelLayout, this.mLeftOptionView);
        addWheelToLL(wheelLayout, this.mRightOptionView);
        LayoutParams lp = new LayoutParams(-1, 500);
        lp.setMargins(DensityUtil.dip2px(getContext(), 30.0f), 0, DensityUtil.dip2px(getContext(), 30.0f), 0);
        layout.addView(wheelLayout, lp);
    }

    private void addWheelToLL(LinearLayout wheelLayout, WheelView wheelView) {
        LayoutParams childviewLP = (LayoutParams) wheelView.getLayoutParams();
        if (childviewLP == null) {
            childviewLP = new LayoutParams(0, -2);
        }
        childviewLP.weight = 1.0f;
        wheelLayout.addView(wheelView, childviewLP);
    }

    @NonNull
    private WheelView getWheelView(List<String> options, OnWheelViewListener onWheelViewListener) {
        WheelView optionView = new WheelView(this.activity);
        optionView.setTextSize(16);
        optionView.setTextColor(getResources().getColor(R.color.wheelview_textcolor_normal), getResources().getColor(R.color.wheelview_textcolor_focus));
        optionView.setLineVisible(true);
        optionView.setLineColor(getResources().getColor(R.color.wheelview_linecolor));
        optionView.setOffset(1);
        optionView.setItems(options);
        optionView.setOnWheelViewListener(onWheelViewListener);
        return optionView;
    }

    private void addLine(LinearLayout layout) {
        View line = new View(this.activity);
        LayoutParams lp = new LayoutParams(-1, 1);
        lp.setMargins(DensityUtil.dip2px(getContext(), 10.0f), 0, DensityUtil.dip2px(getContext(), 10.0f), DensityUtil.dip2px(getContext(), 20.0f));
        line.setBackgroundResource(R.color.linecolor);
        layout.addView(line, lp);
    }

    public Resources getResources() {
        return getContext().getResources();
    }

    public String getRightSelectedOption() {
        return this.mRightSelectedOption;
    }

    public int getRightSelectedIndex() {
        return this.mRightSelectedIndex;
    }

    public int getLeftSelectedIndex() {
        return this.mLeftSelectedIndex;
    }

    public String getLeftSelectedOption() {
        return this.mLeftSelectedOption;
    }
}
