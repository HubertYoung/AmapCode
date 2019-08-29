package com.alipay.mobile.antui.picker;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.utils.DateUtils;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AUDatePickerView extends AURelativeLayout {
    public static final int COMPARE_MODE_ALL = Integer.parseInt("111", 2);
    public static final int COMPARE_MODE_EQUAL = Integer.parseInt("100", 2);
    public static final int COMPARE_MODE_MAX = Integer.parseInt("001", 2);
    public static final int COMPARE_MODE_MIN = Integer.parseInt("010", 2);
    public static final int MONTH_DAY = Integer.parseInt("01100", 2);
    public static final int YEAR_MONTH = Integer.parseInt("11000", 2);
    public static final int YEAR_MONTH_DAY = Integer.parseInt("11100", 2);
    private String dayLabel;
    private AUWheelView dayView;
    private ArrayList<String> days;
    private AUWheelView hourView;
    private ArrayList<String> hours;
    private boolean isAuto;
    private String lastDatePickerValue;
    protected int lineColor;
    protected boolean lineVisible;
    private AULinearLayout mContainer;
    private final int maxDay;
    private final int maxHour;
    private final int maxMinute;
    private final int maxMonth;
    private final int maxYear;
    private final int minDay;
    private final int minHour;
    private final int minMinute;
    private final int minMonth;
    private final int minYear;
    private AUWheelView minuteView;
    private ArrayList<String> minutes;
    private String monthLabel;
    private AUWheelView monthView;
    private ArrayList<String> months;
    protected int offset;
    private OnDatePickListener onDatePickListener;
    private final Calendar selectedDate;
    /* access modifiers changed from: private */
    public int selectedDayIndex;
    /* access modifiers changed from: private */
    public int selectedHourIndex;
    /* access modifiers changed from: private */
    public int selectedMinuteIndex;
    /* access modifiers changed from: private */
    public int selectedMonthIndex;
    /* access modifiers changed from: private */
    public int selectedYearIndex;
    protected int textColorFocus;
    protected int textColorNormal;
    protected int textSize;
    private String yearLabel;
    private AUWheelView yearView;
    private ArrayList<String> years;

    public interface OnDatePickListener {
        void onPickerChange(int i, int i2, int i3, int i4, int i5);
    }

    public AUDatePickerView(Activity activity) {
        this(activity, null, null, null, YEAR_MONTH_DAY);
    }

    public AUDatePickerView(Activity activity, Calendar minDate, Calendar maxDate, Calendar selectedDate2, int mode) {
        super(activity);
        this.years = new ArrayList<>();
        this.months = new ArrayList<>();
        this.days = new ArrayList<>();
        this.hours = new ArrayList<>();
        this.minutes = new ArrayList<>();
        this.yearLabel = "年";
        this.monthLabel = "月";
        this.dayLabel = "日";
        this.selectedYearIndex = 0;
        this.selectedMonthIndex = 0;
        this.selectedDayIndex = 0;
        this.selectedHourIndex = 0;
        this.selectedMinuteIndex = 0;
        this.isAuto = true;
        this.textSize = 16;
        this.textColorNormal = -4473925;
        this.textColorFocus = -16611122;
        this.lineColor = -8139290;
        this.lineVisible = true;
        this.offset = 1;
        selectedDate2 = selectedDate2 == null ? Calendar.getInstance() : selectedDate2;
        if (minDate == null) {
            minDate = Calendar.getInstance();
            minDate.set(2000, 0, 1);
        }
        if (maxDate == null) {
            maxDate = Calendar.getInstance();
            maxDate.set(2030, 11, 31);
        }
        if (maxDate.getTimeInMillis() < minDate.getTimeInMillis()) {
            Calendar temp = maxDate;
            maxDate = minDate;
            minDate = temp;
        }
        this.selectedDate = (selectedDate2.getTimeInMillis() > maxDate.getTimeInMillis() || selectedDate2.getTimeInMillis() < minDate.getTimeInMillis()) ? minDate : selectedDate2;
        this.minYear = minDate.get(1);
        this.maxYear = maxDate.get(1);
        this.minMonth = minDate.get(2) + 1;
        this.maxMonth = maxDate.get(2) + 1;
        this.minDay = minDate.get(5);
        this.maxDay = maxDate.get(5);
        this.minHour = minDate.get(11);
        this.maxHour = maxDate.get(11);
        this.minMinute = minDate.get(12);
        this.maxMinute = maxDate.get(12);
        LayoutInflater.from(activity).inflate(R.layout.au_date_picker_view, this, true);
        this.mContainer = (AULinearLayout) findViewById(R.id.container);
        makeCenterView(this.mContainer, mode);
    }

    public void setLabel(String yearLabel2, String monthLabel2, String dayLabel2) {
        this.yearLabel = yearLabel2;
        this.monthLabel = monthLabel2;
        this.dayLabel = dayLabel2;
    }

    private void setDefaultDate() {
        if (this.isAuto) {
            this.selectedYearIndex = findItemIndex(this.years, this.selectedDate.get(1));
            this.selectedMonthIndex = findItemIndex(this.months, this.selectedDate.get(2) + 1);
            this.selectedDayIndex = findItemIndex(this.days, this.selectedDate.get(5));
            this.selectedHourIndex = findItemIndex(this.hours, this.selectedDate.get(11));
            this.selectedMinuteIndex = findItemIndex(this.minutes, this.selectedDate.get(12));
            this.lastDatePickerValue = getDatePickerValue(this.selectedDate.get(1), this.selectedDate.get(2) + 1, this.selectedDate.get(5), this.selectedDate.get(11), this.selectedDate.get(12));
            this.isAuto = false;
        }
    }

    private int findItemIndex(ArrayList<String> items, int item) {
        int index = Collections.binarySearch(items, Integer.valueOf(item), new h(this));
        if (index < 0) {
            return 0;
        }
        return index;
    }

    /* access modifiers changed from: private */
    @NonNull
    public String checkIntStringVaild(String lhsStr) {
        if (TextUtils.isEmpty(lhsStr)) {
            return "0";
        }
        return lhsStr;
    }

    public void setOnDatePickListener(OnDatePickListener listener) {
        this.onDatePickListener = listener;
    }

    /* access modifiers changed from: protected */
    public View makeCenterView(View view, int mode) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        for (int i = this.minYear; i <= this.maxYear; i++) {
            this.years.add(String.valueOf(i));
        }
        initMonths();
        for (int i2 = 1; i2 <= 31; i2++) {
            this.days.add(DateUtils.fillZero(i2));
        }
        for (int i3 = 0; i3 <= 23; i3++) {
            this.hours.add(DateUtils.fillZero(i3));
        }
        for (int i4 = 0; i4 <= 59; i4++) {
            this.minutes.add(DateUtils.fillZero(i4));
        }
        setDefaultDate();
        this.yearView = initWheelView(R.id.years, this.years, this.selectedYearIndex, (Integer.parseInt(H5AppPrepareData.PREPARE_FAIL, 2) & mode) > 0);
        int i5 = R.id.month;
        if ((Integer.parseInt("01000", 2) & mode) > 0) {
            z = true;
        } else {
            z = false;
        }
        this.monthView = initWheelView(i5, null, 0, z);
        int i6 = R.id.days;
        if ((Integer.parseInt("00100", 2) & mode) > 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.dayView = initWheelView(i6, null, 0, z2);
        int i7 = R.id.hours;
        if ((Integer.parseInt("00010", 2) & mode) > 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.hourView = initWheelView(i7, null, 0, z3);
        int i8 = R.id.minutes;
        if ((Integer.parseInt("00001", 2) & mode) <= 0) {
            z4 = false;
        }
        this.minuteView = initWheelView(i8, null, 0, z4);
        this.yearView.setOnWheelViewListener(new i(this));
        this.monthView.setOnWheelViewListener(new j(this));
        this.dayView.setOnWheelViewListener(new k(this));
        this.hourView.setOnWheelViewListener(new l(this));
        this.minuteView.setOnWheelViewListener(new m(this));
        return view;
    }

    /* access modifiers changed from: private */
    public void handleChangeByMinMaxDate(boolean needMonth, boolean needDay, boolean needHour, boolean needMinute) {
        int year = getSelectedYear();
        int month = getSelectedMonth();
        if (this.minYear == year && this.minYear == this.maxYear) {
            changeMonthsByMinMaxDate(month, this.minMonth, this.maxMonth, needMonth, needDay, needHour, needMinute, COMPARE_MODE_ALL);
        } else if (this.minYear == year) {
            changeMonthsByMinMaxDate(month, this.minMonth, 12, needMonth, needDay, needHour, needMinute, COMPARE_MODE_MIN);
        } else if (this.maxYear == year) {
            changeMonthsByMinMaxDate(month, 1, this.maxMonth, needMonth, needDay, needHour, needMinute, COMPARE_MODE_MAX);
        } else if ((this.months.size() < 12 || !TextUtils.equals(this.months.get(this.selectedMonthIndex), this.monthView.getSelectedItem())) && needMonth) {
            initMonths();
            this.selectedMonthIndex = findItemIndex(this.months, month);
            this.monthView.setItems((List<String>) this.months, this.selectedMonthIndex);
            changeDaysViewByYearMonth();
        } else if (needMonth) {
            changeDaysViewByYearMonth();
        }
    }

    private int changeMinutesByMinMaxDate(int minute, int minMinute2, int maxMinute2, boolean needMinute) {
        if (!needMinute) {
            return minute;
        }
        this.selectedMinuteIndex = changeByMinMaxDate(this.minutes, minute, minMinute2, maxMinute2, this.minuteView);
        return getSelectedMinute();
    }

    private int changeHoursByMinMaxDate(int hour, int minHour2, int maxHour2, boolean needHour, boolean needMinute, int compareMode) {
        if (needHour) {
            this.selectedHourIndex = changeByMinMaxDate(this.hours, hour, minHour2, maxHour2, this.hourView);
            hour = getSelectedHour();
        }
        int minute = getSelectedMinute();
        if ((COMPARE_MODE_EQUAL & compareMode) > 0 && minHour2 == hour && minHour2 == maxHour2) {
            changeMinutesByMinMaxDate(minute, this.minMinute, this.maxMinute, needMinute);
        } else if ((COMPARE_MODE_MIN & compareMode) > 0 && minHour2 == hour) {
            changeMinutesByMinMaxDate(minute, this.minMinute, 59, needMinute);
        } else if ((COMPARE_MODE_MAX & compareMode) > 0 && maxHour2 == hour) {
            changeMinutesByMinMaxDate(minute, 0, this.maxMinute, needMinute);
        } else if (this.minutes.size() < 60 || !TextUtils.equals(this.minutes.get(this.selectedMinuteIndex), this.minuteView.getSelectedItem())) {
            changeMinutesByMinMaxDate(minute, 0, 59, true);
        }
        return minute;
    }

    private int changeMonthsByMinMaxDate(int month, int minMonth2, int maxMonth2, boolean needMonth, boolean needDay, boolean needHour, boolean needMinute, int compareMode) {
        if (needMonth) {
            this.selectedMonthIndex = changeByMinMaxDate(this.months, month, minMonth2, maxMonth2, this.monthView);
            month = getSelectedMonth();
        }
        int year = getSelectedYear();
        int day = getSelectedDay();
        int defaultMaxDay = DateUtils.calculateDaysInMonth(year, month);
        if ((COMPARE_MODE_EQUAL & compareMode) > 0 && minMonth2 == month && minMonth2 == maxMonth2) {
            changeDaysByMinMaxDate(day, this.minDay, this.maxDay, needDay, needHour, needMinute, COMPARE_MODE_ALL);
        } else if ((COMPARE_MODE_MIN & compareMode) > 0 && minMonth2 == month) {
            changeDaysByMinMaxDate(day, this.minDay, defaultMaxDay, needDay, needHour, needMinute, COMPARE_MODE_MIN);
        } else if ((COMPARE_MODE_MAX & compareMode) > 0 && maxMonth2 == month) {
            changeDaysByMinMaxDate(day, 1, this.maxDay, needDay, needHour, needMinute, COMPARE_MODE_MAX);
        } else if (this.days.size() != defaultMaxDay || !DateUtils.fillZero(defaultMaxDay).equals(this.days.get(defaultMaxDay - 1)) || !TextUtils.equals(this.days.get(this.selectedDayIndex), this.dayView.getSelectedItem())) {
            changeDaysByMinMaxDate(day, 1, defaultMaxDay, true, false, false, 0);
        }
        return month;
    }

    private int changeDaysByMinMaxDate(int day, int minDay2, int maxDay2, boolean needDay, boolean needHour, boolean needMinute, int compareMode) {
        if (needDay) {
            this.selectedDayIndex = changeByMinMaxDate(this.days, day, minDay2, maxDay2, this.dayView);
            day = getSelectedDay();
        }
        int hour = getSelectedHour();
        if ((COMPARE_MODE_EQUAL & compareMode) > 0 && minDay2 == day && minDay2 == maxDay2) {
            changeHoursByMinMaxDate(hour, this.minHour, this.maxHour, needHour, needMinute, COMPARE_MODE_ALL);
        } else if ((COMPARE_MODE_MIN & compareMode) > 0 && minDay2 == day) {
            changeHoursByMinMaxDate(hour, this.minHour, 23, needHour, needMinute, COMPARE_MODE_MIN);
        } else if ((COMPARE_MODE_MAX & compareMode) > 0 && maxDay2 == day) {
            changeHoursByMinMaxDate(hour, 0, this.maxHour, needHour, needMinute, COMPARE_MODE_MAX);
        } else if (this.hours.size() < 24 || !TextUtils.equals(this.hours.get(this.selectedHourIndex), this.hourView.getSelectedItem())) {
            changeHoursByMinMaxDate(hour, 0, 23, true, false, 0);
        }
        return day;
    }

    private int changeByMinMaxDate(ArrayList<String> list, int currValue, int minValue, int maxValue, AUWheelView wheelView) {
        list.clear();
        for (int i = minValue; i <= maxValue; i++) {
            list.add(DateUtils.fillZero(i));
        }
        int index = findItemIndex(list, currValue);
        wheelView.setItems((List<String>) list, index);
        return index;
    }

    private void initMonths() {
        this.months.clear();
        for (int i = 1; i <= 12; i++) {
            this.months.add(DateUtils.fillZero(i));
        }
    }

    /* access modifiers changed from: private */
    public void onDatePickChange() {
        if (this.onDatePickListener != null) {
            int year = getSelectedYear();
            int month = getSelectedMonth();
            int day = getSelectedDay();
            int hour = getSelectedHour();
            int minute = getSelectedMinute();
            String datePickerValue = getDatePickerValue(year, month, day, hour, minute);
            if (!getDatePickerValue(year, month, day, hour, minute).equals(this.lastDatePickerValue)) {
                this.lastDatePickerValue = datePickerValue;
                this.onDatePickListener.onPickerChange(year, month, day, hour, minute);
            }
        }
    }

    public int getSelectedMinute() {
        return stringToYearMonthDay(this.minutes.get(this.selectedMinuteIndex));
    }

    public int getSelectedHour() {
        return stringToYearMonthDay(this.hours.get(this.selectedHourIndex));
    }

    public int getSelectedDay() {
        return stringToYearMonthDay(this.days.get(this.selectedDayIndex));
    }

    public int getSelectedMonth() {
        return stringToYearMonthDay(this.months.get(this.selectedMonthIndex));
    }

    public int getSelectedYear() {
        return stringToYearMonthDay(this.years.get(this.selectedYearIndex));
    }

    @NonNull
    private String getDatePickerValue(int year, int month, int day, int hour, int minute) {
        return year + "-" + month + "-" + day + Token.SEPARATOR + hour + ":" + minute;
    }

    private void changeDaysViewByYearMonth() {
        changeDaysByMinMaxDate(getSelectedDay(), 1, DateUtils.calculateDaysInMonth(getSelectedYear(), getSelectedMonth()), true, false, false, 0);
    }

    private int stringToYearMonthDay(String text) {
        if (text.startsWith("0")) {
            text = text.substring(1);
        }
        return Integer.parseInt(text);
    }

    private AUWheelView initWheelView(int viewId, ArrayList<String> items, int index, boolean show) {
        AUWheelView wheelView = (AUWheelView) findViewById(viewId);
        wheelView.setTextSize(this.textSize);
        wheelView.setLineVisible(this.lineVisible);
        wheelView.setOffset(this.offset);
        if (items != null) {
            if (index == 0) {
                wheelView.setItems(items);
            } else {
                wheelView.setItems((List<String>) items, index);
            }
        }
        if (show) {
            wheelView.setVisibility(0);
        } else {
            wheelView.setVisibility(8);
        }
        return wheelView;
    }

    public void setTextSize(int textSize2) {
        this.textSize = textSize2;
    }

    public void setTextColor(@ColorInt int textColorFocus2, @ColorInt int textColorNormal2) {
        this.textColorFocus = textColorFocus2;
        this.textColorNormal = textColorNormal2;
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColorFocus = textColor;
    }

    public void setLineVisible(boolean lineVisible2) {
        this.lineVisible = lineVisible2;
    }

    public void setLineColor(@ColorInt int lineColor2) {
        this.lineColor = lineColor2;
    }

    public void setOffset(@IntRange(from = 1, to = 4) int offset2) {
        this.offset = offset2;
    }
}
