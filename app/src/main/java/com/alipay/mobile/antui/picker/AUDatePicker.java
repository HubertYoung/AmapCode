package com.alipay.mobile.antui.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DateUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AUDatePicker extends AUWheelPicker {
    public static final int MONTH_DAY = 2;
    public static final int YEAR_MONTH = 1;
    public static final int YEAR_MONTH_DAY = 0;
    private final String TAG;
    private String dayLabel;
    /* access modifiers changed from: private */
    public ArrayList<String> days;
    private boolean isAuto;
    /* access modifiers changed from: private */
    public int mode;
    private String monthLabel;
    /* access modifiers changed from: private */
    public ArrayList<String> months;
    private OnDatePickListener onDatePickListener;
    private View outterView;
    /* access modifiers changed from: private */
    public int selectedDayIndex;
    /* access modifiers changed from: private */
    public int selectedMonthIndex;
    /* access modifiers changed from: private */
    public int selectedYearIndex;
    private String yearLabel;
    /* access modifiers changed from: private */
    public ArrayList<String> years;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public interface OnDatePickListener {
    }

    public interface OnMonthDayPickListener extends OnDatePickListener {
        void onDatePicked(String str, String str2);
    }

    public interface OnYearMonthDayPickListener extends OnDatePickListener {
        void onDatePicked(String str, String str2, String str3);
    }

    public interface OnYearMonthPickListener extends OnDatePickListener {
        void onDatePicked(String str, String str2);
    }

    public AUDatePicker(Activity activity) {
        this(activity, 0);
    }

    public AUDatePicker(Activity activity, int mode2) {
        super(activity);
        this.years = new ArrayList<>();
        this.months = new ArrayList<>();
        this.days = new ArrayList<>();
        this.yearLabel = "年";
        this.monthLabel = "月";
        this.dayLabel = "日";
        this.selectedYearIndex = 0;
        this.selectedMonthIndex = 0;
        this.selectedDayIndex = 0;
        this.mode = 0;
        this.isAuto = true;
        this.TAG = AUDatePicker.class.getSimpleName();
        this.mode = mode2;
        setTitleText((CharSequence) activity.getResources().getString(R.string.datePickerDefaultTitle));
        for (int i = 2000; i <= 2050; i++) {
            this.years.add(String.valueOf(i));
        }
        for (int i2 = 1; i2 <= 12; i2++) {
            this.months.add(DateUtils.fillZero(i2));
        }
        for (int i3 = 1; i3 <= 31; i3++) {
            this.days.add(DateUtils.fillZero(i3));
        }
    }

    public void setLabel(String yearLabel2, String monthLabel2, String dayLabel2) {
        this.yearLabel = yearLabel2;
        this.monthLabel = monthLabel2;
        this.dayLabel = dayLabel2;
    }

    public void setRange(int startYear, int endYear) {
        this.years.clear();
        for (int i = startYear; i <= endYear; i++) {
            this.years.add(String.valueOf(i));
        }
    }

    public void setTimeDate(Date minDate, Date maxDate) {
        setRange(minDate.getYear(), maxDate.getYear());
    }

    private int findItemIndex(ArrayList<String> items, int item) {
        int index = Collections.binarySearch(items, Integer.valueOf(item), new d(this));
        if (index < 0) {
            return 0;
        }
        return index;
    }

    public void setSelectedItem(int year, int month, int day) {
        this.selectedYearIndex = findItemIndex(this.years, year);
        this.selectedMonthIndex = findItemIndex(this.months, month);
        this.selectedDayIndex = findItemIndex(this.days, day);
    }

    public void setSelectedItem(int yearOrMonth, int monthOrDay) {
        if (this.mode == 2) {
            this.selectedMonthIndex = findItemIndex(this.months, yearOrMonth);
            this.selectedDayIndex = findItemIndex(this.days, monthOrDay);
            return;
        }
        this.selectedYearIndex = findItemIndex(this.years, yearOrMonth);
        this.selectedMonthIndex = findItemIndex(this.months, monthOrDay);
    }

    private void setDefaultDate() {
        if (this.isAuto) {
            Calendar c = Calendar.getInstance();
            int year = c.get(1);
            int month = c.get(2);
            int day = c.get(5);
            setSelectedItem(year, month + 1, day);
            this.isAuto = false;
            AuiLogger.info(this.TAG, "setDefaultDate: year " + year + " month:" + month + " day:" + day);
        }
    }

    public void setOnDatePickListener(OnDatePickListener listener) {
        this.onDatePickListener = listener;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public View makeCenterView() {
        View view = LayoutInflater.from(this.activity).inflate(R.layout.date_picker, null);
        AUWheelView yearView = (AUWheelView) view.findViewById(R.id.years);
        yearView.setTextSize(this.textSize);
        yearView.setLineVisible(this.lineVisible);
        yearView.setOffset(this.offset);
        AUWheelView monthView = (AUWheelView) view.findViewById(R.id.month);
        monthView.setTextSize(this.textSize);
        monthView.setLineVisible(this.lineVisible);
        monthView.setOffset(this.offset);
        AUWheelView dayView = (AUWheelView) view.findViewById(R.id.days);
        dayView.setTextSize(this.textSize);
        dayView.setLineVisible(this.lineVisible);
        dayView.setOffset(this.offset);
        setDefaultDate();
        if (this.mode == 1) {
            dayView.setVisibility(8);
        } else if (this.mode == 2) {
            yearView.setVisibility(8);
        }
        if (this.mode != 2) {
            if (this.selectedYearIndex == 0) {
                yearView.setItems(this.years);
            } else {
                yearView.setItems((List<String>) this.years, this.selectedYearIndex);
            }
            yearView.setOnWheelViewListener(new e(this, dayView));
        }
        if (this.selectedMonthIndex == 0) {
            monthView.setItems(this.months);
        } else {
            monthView.setItems((List<String>) this.months, this.selectedMonthIndex);
        }
        monthView.setOnWheelViewListener(new f(this, dayView));
        if (this.mode != 1) {
            if (this.selectedDayIndex == 0) {
                dayView.setItems(this.days);
            } else {
                dayView.setItems((List<String>) this.days, this.selectedDayIndex);
            }
            dayView.setOnWheelViewListener(new g(this));
        }
        this.outterView = view;
        return view;
    }

    public View getOutterView() {
        return this.outterView;
    }

    /* access modifiers changed from: private */
    public int stringToYearMonthDay(String text) {
        if (text.startsWith("0")) {
            text = text.substring(1);
        }
        return Integer.parseInt(text);
    }

    /* access modifiers changed from: protected */
    public void onSubmit() {
        if (this.onDatePickListener != null) {
            String year = getSelectedYear();
            String month = getSelectedMonth();
            String day = getSelectedDay();
            switch (this.mode) {
                case 1:
                    ((OnYearMonthPickListener) this.onDatePickListener).onDatePicked(year, month);
                    return;
                case 2:
                    ((OnMonthDayPickListener) this.onDatePickListener).onDatePicked(month, day);
                    return;
                default:
                    ((OnYearMonthDayPickListener) this.onDatePickListener).onDatePicked(year, month, day);
                    return;
            }
        }
    }

    public String getSelectedYear() {
        return this.years.get(this.selectedYearIndex);
    }

    public String getSelectedMonth() {
        return this.months.get(this.selectedMonthIndex);
    }

    public String getSelectedDay() {
        return this.days.get(this.selectedDayIndex);
    }
}
