package com.autonavi.minimap.ajx3.widget.view.timepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.TimePickerProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.json.JSONObject;

public class TimePickerView extends FrameLayout implements ViewExtension {
    private static final boolean DEBUG = false;
    public static final int MAX_DAY_OF_MONTH = 31;
    public static final int MAX_HOUR = 23;
    public static final int MAX_MINUTE = 59;
    public static final int MAX_MONTH = 12;
    public static final int MIN_DAY_OF_MONTH = 1;
    public static final int MIN_HOUR = 0;
    public static final int MIN_MINUTE = 0;
    public static final int MIN_MONTH = 1;
    private static final int MSG_MONTH_CHANGED = 1004;
    private static final int MSG_SAVE_DATE = 1003;
    private static final String TAG = "TimePicker";
    private IAjxContext mAjxContext;
    /* access modifiers changed from: private */
    public Calendar mCal = Calendar.getInstance();
    /* access modifiers changed from: private */
    public int mDayIndex;
    private TimePickerWidgetView mDayOfMonthWidgetView;
    private DateSelectorAdapter mDayOfMonthWidgetViewAdapter;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 1003) {
                TimePickerView.this.saveDate();
                return;
            }
            if (message.what == 1004) {
                TimePickerView.this.onMonthItemChange(message.arg1);
            }
        }
    };
    private boolean mHasYearRange = false;
    /* access modifiers changed from: private */
    public int mHourIndex;
    private TimePickerWidgetView mHourOfDayWidgetView;
    private DateSelectorAdapter mHourOfDayWidgetViewAdapter;
    private int mMaxYear;
    private int mMinYear;
    /* access modifiers changed from: private */
    public int mMinuteIndex;
    private TimePickerWidgetView mMinuteWidgetView;
    private DateSelectorAdapter mMinuteWidgetViewAdapter;
    /* access modifiers changed from: private */
    public int mMonthIndex;
    private TimePickerWidgetView mMonthWidgetView;
    /* access modifiers changed from: private */
    public DateSelectorAdapter mMonthWidgetViewAdapter;
    private final BaseProperty mProperty;
    private boolean mShowDay = true;
    private boolean mShowHour = false;
    private boolean mShowMinute = false;
    private boolean mShowMonth = true;
    private boolean mShowYear = true;
    /* access modifiers changed from: private */
    public int mYearIndex;
    private TimePickerWidgetView mYearWidgetView;
    /* access modifiers changed from: private */
    public DateSelectorAdapter mYearWidgetViewAdapter;
    private int preMonthIndex;

    public static class DateSelectorAdapter implements TimePickerAdapter {
        public static final int DEFAULT_MAX_VALUE = 14;
        public static final int DEFAULT_MIN_VALUE = 0;
        private int current;
        private int dateField;
        private DateItemChanged mDateItemChanged;
        private String mFormatStr;
        private int maxValue;
        private int minValue;

        public interface DateItemChanged {
            void onItemChanged(int i);
        }

        public DateSelectorAdapter() {
            this(0, 14);
        }

        public DateSelectorAdapter(int i, int i2) {
            this.mFormatStr = "MM月dd日 E";
            this.minValue = i;
            this.maxValue = i2;
        }

        public DateSelectorAdapter(int i, int i2, String str) {
            this.mFormatStr = "MM月dd日 E";
            this.minValue = i;
            this.maxValue = i2;
            this.mFormatStr = str;
        }

        public void setDateItemChanged(DateItemChanged dateItemChanged) {
            this.mDateItemChanged = dateItemChanged;
        }

        public DateSelectorAdapter(int i, int i2, int i3, String str) {
            this.mFormatStr = "MM月dd日 E";
            this.minValue = i;
            this.maxValue = i2;
            this.dateField = i3;
            this.mFormatStr = str;
        }

        /* access modifiers changed from: 0000 */
        public int getCurrentItem(int i) {
            Calendar instance = Calendar.getInstance();
            this.current = i;
            if (this.dateField == 6) {
                instance.set(6, this.minValue + i);
            } else if (this.dateField == 2) {
                instance.set(5, 1);
                instance.set(2, (this.minValue + i) - 1);
            } else if (this.dateField == 1) {
                instance.set(1, this.minValue + i);
            } else if (this.dateField == 5) {
                instance.set(2, 0);
                instance.set(5, this.minValue + i);
            } else if (this.dateField == 11) {
                instance.set(11, this.minValue + i);
            } else if (this.dateField == 12) {
                instance.set(12, this.minValue + i);
            }
            return instance.get(this.dateField);
        }

        public String getItem(int i) {
            Calendar instance = Calendar.getInstance();
            this.current = i;
            if (this.dateField == 6) {
                instance.set(6, this.minValue + i);
            } else if (this.dateField == 2) {
                instance.set(5, 1);
                instance.set(2, (this.minValue + i) - 1);
                if (this.mDateItemChanged != null) {
                    this.mDateItemChanged.onItemChanged(i);
                }
            } else if (this.dateField == 1) {
                instance.set(1, this.minValue + i);
                if (this.mDateItemChanged != null) {
                    this.mDateItemChanged.onItemChanged(i);
                }
            } else if (this.dateField == 5) {
                instance.set(2, 0);
                instance.set(5, this.minValue + i);
                if (this.mDateItemChanged != null) {
                    this.mDateItemChanged.onItemChanged(i);
                }
            } else if (this.dateField == 11) {
                instance.set(11, this.minValue + i);
                if (this.mDateItemChanged != null) {
                    this.mDateItemChanged.onItemChanged(i);
                }
            } else if (this.dateField == 12) {
                instance.set(12, this.minValue + i);
                if (this.mDateItemChanged != null) {
                    this.mDateItemChanged.onItemChanged(i);
                }
            }
            return new SimpleDateFormat(this.mFormatStr, Locale.CHINA).format(instance.getTime());
        }

        public int getItemsCount() {
            return (this.maxValue - this.minValue) + 1;
        }

        public int getMaximumLength() {
            int length = Integer.toString(Math.max(Math.abs(this.maxValue), Math.abs(this.minValue))).length();
            return this.minValue < 0 ? length + 1 : length;
        }

        public int getCurrentIndex() {
            return this.current;
        }
    }

    public TimePickerView(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mProperty = new TimePickerProperty(this, iAjxContext);
        initView(iAjxContext.getNativeContext());
    }

    private void initView(Context context) {
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.date_time, this);
        this.mYearWidgetView = (TimePickerWidgetView) inflate.findViewById(R.id.date_year);
        this.mMonthWidgetView = (TimePickerWidgetView) inflate.findViewById(R.id.date_month);
        this.mDayOfMonthWidgetView = (TimePickerWidgetView) inflate.findViewById(R.id.date_day);
        this.mHourOfDayWidgetView = (TimePickerWidgetView) inflate.findViewById(R.id.date_hour);
        this.mMinuteWidgetView = (TimePickerWidgetView) inflate.findViewById(R.id.date_minute);
        initDate(System.currentTimeMillis());
        updateMode();
    }

    public void updateMode() {
        int i = 8;
        this.mYearWidgetView.setVisibility(this.mShowYear ? 0 : 8);
        this.mMonthWidgetView.setVisibility(this.mShowMonth ? 0 : 8);
        this.mDayOfMonthWidgetView.setVisibility(this.mShowDay ? 0 : 8);
        this.mHourOfDayWidgetView.setVisibility(this.mShowHour ? 0 : 8);
        TimePickerWidgetView timePickerWidgetView = this.mMinuteWidgetView;
        if (this.mShowMinute) {
            i = 0;
        }
        timePickerWidgetView.setVisibility(i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0090 A[Catch:{ Exception -> 0x00a8 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateDate(java.lang.String r12) {
        /*
            r11 = this;
            java.util.Calendar r0 = java.util.Calendar.getInstance()
            long r1 = java.lang.System.currentTimeMillis()
            r0.setTimeInMillis(r1)
            r1 = 1
            int r2 = r0.get(r1)
            r3 = 2
            int r3 = r0.get(r3)
            r4 = 5
            int r0 = r0.get(r4)
            r4 = 0
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x00a7 }
            r5.<init>(r12)     // Catch:{ Exception -> 0x00a7 }
            java.lang.String r12 = "year"
            boolean r12 = r5.has(r12)     // Catch:{ Exception -> 0x00a7 }
            if (r12 == 0) goto L_0x003b
            java.lang.String r12 = "year"
            java.lang.String r12 = r5.getString(r12)     // Catch:{ Exception -> 0x00a7 }
            boolean r6 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x00a7 }
            if (r6 != 0) goto L_0x003b
            int r12 = java.lang.Integer.parseInt(r12)     // Catch:{ Exception -> 0x00a7 }
            r2 = r12
        L_0x003b:
            java.lang.String r12 = "month"
            boolean r12 = r5.has(r12)     // Catch:{ Exception -> 0x00a7 }
            if (r12 == 0) goto L_0x0055
            java.lang.String r12 = "month"
            java.lang.String r12 = r5.getString(r12)     // Catch:{ Exception -> 0x00a7 }
            boolean r6 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x00a7 }
            if (r6 != 0) goto L_0x0055
            int r12 = java.lang.Integer.parseInt(r12)     // Catch:{ Exception -> 0x00a7 }
            int r3 = r12 + -1
        L_0x0055:
            java.lang.String r12 = "date"
            boolean r12 = r5.has(r12)     // Catch:{ Exception -> 0x00a7 }
            if (r12 == 0) goto L_0x006e
            java.lang.String r12 = "date"
            java.lang.String r12 = r5.getString(r12)     // Catch:{ Exception -> 0x00a7 }
            boolean r1 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x00a7 }
            if (r1 != 0) goto L_0x006e
            int r12 = java.lang.Integer.parseInt(r12)     // Catch:{ Exception -> 0x00a7 }
            r0 = r12
        L_0x006e:
            java.lang.String r12 = "hours"
            boolean r12 = r5.has(r12)     // Catch:{ Exception -> 0x00a7 }
            if (r12 == 0) goto L_0x0087
            java.lang.String r12 = "hours"
            java.lang.String r12 = r5.getString(r12)     // Catch:{ Exception -> 0x00a7 }
            boolean r1 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x00a7 }
            if (r1 != 0) goto L_0x0087
            int r12 = java.lang.Integer.parseInt(r12)     // Catch:{ Exception -> 0x00a7 }
            goto L_0x0088
        L_0x0087:
            r12 = 0
        L_0x0088:
            java.lang.String r1 = "minutes"
            boolean r1 = r5.has(r1)     // Catch:{ Exception -> 0x00a8 }
            if (r1 == 0) goto L_0x00a1
            java.lang.String r1 = "minutes"
            java.lang.String r1 = r5.getString(r1)     // Catch:{ Exception -> 0x00a8 }
            boolean r5 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x00a8 }
            if (r5 != 0) goto L_0x00a1
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x00a8 }
            r4 = r1
        L_0x00a1:
            r9 = r12
            r8 = r0
            r6 = r2
            r7 = r3
            r10 = r4
            goto L_0x00ad
        L_0x00a7:
            r12 = 0
        L_0x00a8:
            r9 = r12
            r8 = r0
            r6 = r2
            r7 = r3
            r10 = 0
        L_0x00ad:
            java.util.Calendar r5 = r11.mCal
            r5.set(r6, r7, r8, r9, r10)
            java.util.Calendar r12 = r11.mCal
            long r0 = r12.getTimeInMillis()
            r11.initDate(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView.updateDate(java.lang.String):void");
    }

    public void updateYearRange(String str) {
        if (!TextUtils.isEmpty(str) && str.contains("-") && str.indexOf("-") > 0 && str.indexOf("-") < str.length() - 1) {
            String[] split = str.split("-");
            if (split != null && split.length == 2) {
                int parseInt = Integer.parseInt(split[0]);
                this.mMaxYear = Integer.parseInt(split[1]);
                this.mMinYear = parseInt;
                this.mHasYearRange = true;
                int i = this.mCal.get(1);
                if (i < this.mMinYear || i > this.mMaxYear) {
                    this.mYearIndex = 0;
                } else {
                    this.mYearIndex = i - this.mMinYear;
                }
                updateYearView(this.mMinYear, this.mMaxYear);
            }
        }
    }

    public void updateType(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (!TextUtils.isEmpty(str) && str.length() == 5) {
                boolean z = false;
                this.mShowYear = str.charAt(0) == '1';
                this.mShowMonth = str.charAt(1) == '1';
                this.mShowDay = str.charAt(2) == '1';
                this.mShowHour = str.charAt(3) == '1';
                if (str.charAt(4) == '1') {
                    z = true;
                }
                this.mShowMinute = z;
            }
            updateMode();
        }
    }

    public void updateCyclic(String str) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str) && str.length() == 5) {
            boolean z = false;
            this.mYearWidgetView.setCyclic(str.charAt(0) == '1');
            this.mMonthWidgetView.setCyclic(str.charAt(1) == '1');
            this.mDayOfMonthWidgetView.setCyclic(str.charAt(2) == '1');
            this.mHourOfDayWidgetView.setCyclic(str.charAt(3) == '1');
            TimePickerWidgetView timePickerWidgetView = this.mMinuteWidgetView;
            if (str.charAt(4) == '1') {
                z = true;
            }
            timePickerWidgetView.setCyclic(z);
        }
    }

    private void initDate(long j) {
        if (j <= 0) {
            j = System.currentTimeMillis();
        }
        if (j > 0) {
            Calendar calendar = this.mCal;
            calendar.setTimeInMillis(j);
            if (!this.mHasYearRange) {
                this.mMaxYear = calendar.get(1) + 10;
                this.mMinYear = this.mMaxYear - 20;
            }
            int i = this.mCal.get(1);
            if (i < this.mMinYear || i > this.mMaxYear) {
                this.mYearIndex = 0;
            } else {
                this.mYearIndex = i - this.mMinYear;
            }
            this.mMonthIndex = calendar.get(2);
            this.mDayIndex = calendar.get(5) - 1;
            this.mHourIndex = calendar.get(11) - 0;
            this.mMinuteIndex = calendar.get(12) - 0;
        }
        updateYearView(this.mMinYear, this.mMaxYear);
        updateMonthView(1, 12);
        updateDayOfMonthView(1, 31);
        updateHourView(0, 23);
        updateMinuteView(0, 59);
    }

    /* access modifiers changed from: private */
    public void saveDate() {
        int currentItem = this.mYearWidgetView.getCurrentItem() + this.mMinYear;
        int currentItem2 = this.mMonthWidgetView.getCurrentItem() + 1;
        int currentItem3 = this.mDayOfMonthWidgetView.getCurrentItem() + 1;
        int currentItem4 = this.mHourOfDayWidgetView.getCurrentItem() + 0;
        int currentItem5 = this.mMinuteWidgetView.getCurrentItem() + 0;
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.mShowYear) {
                jSONObject.put("year", String.valueOf(currentItem));
            }
            if (this.mShowMonth) {
                jSONObject.put("month", formatDate(currentItem2));
            }
            if (this.mShowDay) {
                jSONObject.put("date", formatDate(currentItem3));
            }
            if (this.mShowHour) {
                jSONObject.put("hours", formatDate(currentItem4));
            }
            if (this.mShowMinute) {
                jSONObject.put("minutes", formatDate(currentItem5));
            }
        } catch (Exception unused) {
        }
        this.mProperty.updateAttribute("value", jSONObject.toString(), false, false, true, false);
    }

    private String formatDate(int i) {
        String valueOf = String.valueOf(i);
        return valueOf.length() == 1 ? "0".concat(String.valueOf(valueOf)) : valueOf;
    }

    /* access modifiers changed from: private */
    public void sendResult() {
        if (this.mHandler.hasMessages(1003)) {
            this.mHandler.removeMessages(1003);
        }
        this.mHandler.sendEmptyMessageDelayed(1003, 300);
    }

    private void updateYearView(int i, int i2) {
        this.mYearWidgetViewAdapter = new DateSelectorAdapter(i, i2, 1, "yyyy年");
        this.mYearWidgetViewAdapter.setDateItemChanged(new DateItemChanged() {
            public void onItemChanged(int i) {
                if (i != TimePickerView.this.mYearIndex) {
                    TimePickerView.this.mYearIndex = i;
                    TimePickerView.this.sendResult();
                    TimePickerView.this.mCal.set(1, TimePickerView.this.mYearWidgetViewAdapter.getCurrentItem(TimePickerView.this.mYearIndex));
                    if (TimePickerView.this.mMonthWidgetViewAdapter != null && 1 == TimePickerView.this.mMonthWidgetViewAdapter.getCurrentItem(TimePickerView.this.mMonthIndex)) {
                        TimePickerView.this.updateDayOfMonthView(1, TimePickerView.this.getEndDay(TimePickerView.this.mMonthIndex + 1, TimePickerView.this.mCal.get(1)));
                    }
                }
            }
        });
        this.mYearWidgetView.setAdapter(this.mYearWidgetViewAdapter);
        this.mYearWidgetView.setCyclic(false);
        this.mYearWidgetView.setCurrentItem(this.mYearIndex);
    }

    private void updateMonthView(int i, int i2) {
        this.mMonthWidgetViewAdapter = new DateSelectorAdapter(i, i2, 2, "MM月");
        this.mMonthWidgetViewAdapter.setDateItemChanged(new DateItemChanged() {
            public void onItemChanged(int i) {
                TimePickerView.this.sendMonthItemChange(i);
            }
        });
        this.mMonthWidgetView.setAdapter(this.mMonthWidgetViewAdapter);
        this.mMonthWidgetView.setCyclic(false);
        this.mMonthWidgetView.setCurrentItem(this.mMonthIndex);
    }

    /* access modifiers changed from: private */
    public void sendMonthItemChange(int i) {
        if (this.mHandler.hasMessages(1004)) {
            this.mHandler.removeMessages(1004);
        }
        Message message = new Message();
        message.what = 1004;
        message.arg1 = i;
        this.mHandler.sendMessageDelayed(message, 100);
    }

    /* access modifiers changed from: private */
    public void onMonthItemChange(int i) {
        this.mMonthIndex = i;
        sendResult();
        updateDayOfMonthView(1, getEndDay(i + 1, this.mCal.get(1)));
    }

    /* access modifiers changed from: private */
    public void updateDayOfMonthView(int i, int i2) {
        if (this.mDayIndex < 0) {
            this.mDayIndex = 0;
        } else {
            int i3 = i2 - i;
            if (this.mDayIndex > i3) {
                this.mDayIndex = i3;
            }
        }
        this.mDayOfMonthWidgetViewAdapter = new DateSelectorAdapter(i, i2, 5, "dd日");
        this.mDayOfMonthWidgetViewAdapter.setDateItemChanged(new DateItemChanged() {
            public void onItemChanged(int i) {
                if (i != TimePickerView.this.mDayIndex) {
                    TimePickerView.this.mDayIndex = i;
                    TimePickerView.this.sendResult();
                }
            }
        });
        this.mDayOfMonthWidgetView.setAdapter(this.mDayOfMonthWidgetViewAdapter);
        this.mDayOfMonthWidgetView.setCyclic(false);
        this.mDayOfMonthWidgetView.setCurrentItem(this.mDayIndex);
    }

    private void updateHourView(int i, int i2) {
        this.mHourOfDayWidgetViewAdapter = new DateSelectorAdapter(i, i2, 11, "HH时");
        this.mHourOfDayWidgetViewAdapter.setDateItemChanged(new DateItemChanged() {
            public void onItemChanged(int i) {
                if (i != TimePickerView.this.mHourIndex) {
                    TimePickerView.this.mHourIndex = i;
                    TimePickerView.this.sendResult();
                }
            }
        });
        if (this.mHourOfDayWidgetView != null) {
            if (this.mHourOfDayWidgetView.getCurrentItem() > i2) {
                this.mHourIndex = i2;
            }
            this.mHourOfDayWidgetView.setAdapter(this.mHourOfDayWidgetViewAdapter);
            this.mHourOfDayWidgetView.setCyclic(false);
            this.mHourOfDayWidgetView.setCurrentItem(this.mHourIndex);
        }
    }

    private void updateMinuteView(int i, int i2) {
        this.mMinuteWidgetViewAdapter = new DateSelectorAdapter(i, i2, 12, "mm分");
        this.mMinuteWidgetViewAdapter.setDateItemChanged(new DateItemChanged() {
            public void onItemChanged(int i) {
                if (TimePickerView.this.mMinuteIndex != i) {
                    TimePickerView.this.mMinuteIndex = i;
                    TimePickerView.this.sendResult();
                }
            }
        });
        this.mMinuteWidgetView.setAdapter(this.mMinuteWidgetViewAdapter);
        this.mMinuteWidgetView.setCyclic(false);
        this.mMinuteWidgetView.setCurrentItem(this.mMinuteIndex);
    }

    private boolean isLeapYear(int i) {
        return i % 400 == 0 || (i % 4 == 0 && i % 100 != 0);
    }

    /* access modifiers changed from: private */
    public int getEndDay(int i, int i2) {
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return isLeapYear(i2) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }
}
