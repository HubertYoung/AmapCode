package com.autonavi.map.widget;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.map.widget.wheel.TimePickerWidgetView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.CustomTimePickerAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerDialog extends CompatDialog {
    /* access modifiers changed from: private */
    public CustomDataPickerAdapter mHoursAdapter;
    private long mLDefaultTime;
    private long mLEndTime;
    private long mLStartTime;
    /* access modifiers changed from: private */
    public CustomTimePickerAdapter mMinuteAdapter;
    private int mMinuteInterval;
    private OnClickListener mNegClickListener = null;
    private OnClickListener mPosClickListener = null;
    private String mStrTitle = null;
    /* access modifiers changed from: private */
    public int[] mValidHour;
    /* access modifiers changed from: private */
    public int[] mValidHourForDay;
    /* access modifiers changed from: private */
    public int minMinute;
    private boolean tomorrowFirstState;
    private TimePickerWidgetView wv_day;
    /* access modifiers changed from: private */
    public TimePickerWidgetView wv_hours;
    /* access modifiers changed from: private */
    public TimePickerWidgetView wv_mins;

    public DatePickerDialog(Activity activity, String str, long j, long j2, long j3, int i, int[] iArr) {
        super(activity, R.style.custom_dlg);
        LayoutParams attributes = getWindow().getAttributes();
        attributes.windowAnimations = R.style.custom_dlg_animation;
        getWindow().setAttributes(attributes);
        getWindow().setGravity(87);
        setCanceledOnTouchOutside(true);
        this.mStrTitle = str;
        this.mLDefaultTime = j;
        this.mLStartTime = j2;
        this.mLEndTime = j3;
        this.mMinuteInterval = i;
        this.mValidHour = iArr;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x015e, code lost:
        if (r0 < 0) goto L_0x0165;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r13) {
        /*
            r12 = this;
            super.onCreate(r13)
            int r13 = com.autonavi.minimap.R.layout.timerpicker_dialog_layout
            r12.setContentView(r13)
            java.lang.String r13 = r12.mStrTitle
            boolean r13 = android.text.TextUtils.isEmpty(r13)
            if (r13 != 0) goto L_0x001d
            int r13 = com.autonavi.minimap.R.id.title
            android.view.View r13 = r12.findViewById(r13)
            android.widget.TextView r13 = (android.widget.TextView) r13
            java.lang.String r0 = r12.mStrTitle
            r13.setText(r0)
        L_0x001d:
            com.autonavi.map.widget.CustomDataPickerAdapter r13 = new com.autonavi.map.widget.CustomDataPickerAdapter
            r13.<init>()
            r12.mHoursAdapter = r13
            com.autonavi.minimap.widget.CustomTimePickerAdapter r13 = new com.autonavi.minimap.widget.CustomTimePickerAdapter
            int r0 = r12.mMinuteInterval
            java.lang.String r1 = "%02d"
            r2 = 59
            r3 = 0
            r13.<init>(r3, r2, r0, r1)
            r12.mMinuteAdapter = r13
            long r0 = r12.mLDefaultTime
            long r4 = r12.mLStartTime
            int r13 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            r0 = 12
            r1 = 11
            r4 = 6
            r5 = -1
            if (r13 <= 0) goto L_0x005b
            java.util.Calendar r13 = java.util.Calendar.getInstance()
            java.util.Date r6 = new java.util.Date
            long r7 = r12.mLDefaultTime
            r6.<init>(r7)
            r13.setTime(r6)
            int r6 = r13.get(r4)
            int r7 = r13.get(r1)
            int r13 = r13.get(r0)
            goto L_0x005e
        L_0x005b:
            r13 = -1
            r6 = -1
            r7 = -1
        L_0x005e:
            java.util.Calendar r8 = java.util.Calendar.getInstance()
            java.util.Date r9 = new java.util.Date
            long r10 = r12.mLStartTime
            r9.<init>(r10)
            r8.setTime(r9)
            int r4 = r8.get(r4)
            int r1 = r8.get(r1)
            int r0 = r8.get(r0)
            int r8 = r12.mMinuteInterval
            int r2 = r2 - r8
            r8 = 1
            if (r0 <= r2) goto L_0x0085
            com.autonavi.minimap.widget.CustomTimePickerAdapter r0 = r12.mMinuteAdapter
            r0.setMinValue(r3)
            r0 = 1
            goto L_0x0097
        L_0x0085:
            int r2 = r12.mMinuteInterval
            int r0 = r0 / r2
            int r0 = r0 + r8
            int r2 = r12.mMinuteInterval
            int r0 = r0 * r2
            r12.minMinute = r0
            com.autonavi.minimap.widget.CustomTimePickerAdapter r0 = r12.mMinuteAdapter
            int r2 = r12.minMinute
            r0.setMinValue(r2)
            r0 = 0
        L_0x0097:
            r12.tomorrowFirstState = r3
            int[] r2 = r12.mValidHour
            int[] r9 = r12.mValidHour
            int r9 = r9.length
            int r9 = r9 - r8
            r2 = r2[r9]
            if (r1 <= r2) goto L_0x00b2
            r12.tomorrowFirstState = r8
            com.autonavi.map.widget.CustomDataPickerAdapter r0 = r12.mHoursAdapter
            int[] r1 = r12.mValidHour
            r0.setData(r1)
            com.autonavi.minimap.widget.CustomTimePickerAdapter r0 = r12.mMinuteAdapter
            r0.setMinValue(r3)
            goto L_0x00ef
        L_0x00b2:
            int[] r2 = r12.mValidHour
            r2 = r2[r3]
            if (r1 >= r2) goto L_0x00c5
            com.autonavi.map.widget.CustomDataPickerAdapter r0 = r12.mHoursAdapter
            int[] r1 = r12.mValidHour
            r0.setData(r1)
            com.autonavi.minimap.widget.CustomTimePickerAdapter r0 = r12.mMinuteAdapter
            r0.setMinValue(r3)
            goto L_0x00ef
        L_0x00c5:
            r2 = 0
        L_0x00c6:
            int[] r8 = r12.mValidHour
            int r8 = r8.length
            if (r2 >= r8) goto L_0x00d4
            int[] r8 = r12.mValidHour
            r8 = r8[r2]
            if (r8 >= r1) goto L_0x00d4
            int r2 = r2 + 1
            goto L_0x00c6
        L_0x00d4:
            int[] r1 = r12.mValidHour     // Catch:{ Exception -> 0x00eb }
            if (r0 == 0) goto L_0x00da
            int r2 = r2 + 1
        L_0x00da:
            int[] r0 = r12.mValidHour     // Catch:{ Exception -> 0x00eb }
            int r0 = r0.length     // Catch:{ Exception -> 0x00eb }
            int[] r0 = r12.copyOfRange(r1, r2, r0)     // Catch:{ Exception -> 0x00eb }
            r12.mValidHourForDay = r0     // Catch:{ Exception -> 0x00eb }
            com.autonavi.map.widget.CustomDataPickerAdapter r0 = r12.mHoursAdapter     // Catch:{ Exception -> 0x00eb }
            int[] r1 = r12.mValidHourForDay     // Catch:{ Exception -> 0x00eb }
            r0.setData(r1)     // Catch:{ Exception -> 0x00eb }
            goto L_0x00ef
        L_0x00eb:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00ef:
            long r0 = r12.mLEndTime
            long r8 = r12.mLStartTime
            long r0 = r0 - r8
            r8 = 86400000(0x5265c00, double:4.2687272E-316)
            long r0 = r0 / r8
            int r0 = (int) r0
            if (r0 > 0) goto L_0x00fd
            r0 = 29
        L_0x00fd:
            boolean r1 = r12.tomorrowFirstState
            if (r1 == 0) goto L_0x0103
            int r0 = r0 + -1
        L_0x0103:
            int r6 = r6 - r4
            boolean r1 = r12.tomorrowFirstState
            if (r1 == 0) goto L_0x0109
            goto L_0x010a
        L_0x0109:
            r5 = 0
        L_0x010a:
            int r1 = r6 + r5
            if (r1 >= 0) goto L_0x010f
            r1 = 0
        L_0x010f:
            com.autonavi.map.widget.DatePickerAdapter r2 = new com.autonavi.map.widget.DatePickerAdapter
            android.content.Context r4 = r12.getContext()
            boolean r5 = r12.tomorrowFirstState
            r2.<init>(r4, r3, r0, r5)
            int r0 = com.autonavi.minimap.R.id.day
            android.view.View r0 = r12.findViewById(r0)
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = (com.autonavi.map.widget.wheel.TimePickerWidgetView) r0
            r12.wv_day = r0
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = r12.wv_day
            r0.setCyclic(r3)
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = r12.wv_day
            r0.setAdapter(r2)
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = r12.wv_day
            r0.setCurrentItem(r1)
            if (r1 == 0) goto L_0x013c
            com.autonavi.map.widget.CustomDataPickerAdapter r0 = r12.mHoursAdapter
            int[] r2 = r12.mValidHour
            r0.setData(r2)
        L_0x013c:
            int r0 = com.autonavi.minimap.R.id.hour
            android.view.View r0 = r12.findViewById(r0)
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = (com.autonavi.map.widget.wheel.TimePickerWidgetView) r0
            r12.wv_hours = r0
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = r12.wv_hours
            com.autonavi.map.widget.CustomDataPickerAdapter r2 = r12.mHoursAdapter
            r0.setAdapter(r2)
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = r12.wv_hours
            r0.setCyclic(r3)
            if (r7 < 0) goto L_0x0165
            com.autonavi.map.widget.CustomDataPickerAdapter r0 = r12.mHoursAdapter     // Catch:{ Exception -> 0x0161 }
            int[] r0 = r0.getData()     // Catch:{ Exception -> 0x0161 }
            int r0 = java.util.Arrays.binarySearch(r0, r7)     // Catch:{ Exception -> 0x0161 }
            if (r0 >= 0) goto L_0x0166
            goto L_0x0165
        L_0x0161:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0165:
            r0 = 0
        L_0x0166:
            com.autonavi.map.widget.wheel.TimePickerWidgetView r2 = r12.wv_hours
            r2.setCurrentItem(r0)
            if (r0 != 0) goto L_0x016f
            if (r1 == 0) goto L_0x0174
        L_0x016f:
            com.autonavi.minimap.widget.CustomTimePickerAdapter r0 = r12.mMinuteAdapter
            r0.setMinValue(r3)
        L_0x0174:
            int r0 = com.autonavi.minimap.R.id.mins
            android.view.View r0 = r12.findViewById(r0)
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = (com.autonavi.map.widget.wheel.TimePickerWidgetView) r0
            r12.wv_mins = r0
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = r12.wv_mins
            com.autonavi.minimap.widget.CustomTimePickerAdapter r1 = r12.mMinuteAdapter
            r0.setAdapter(r1)
            com.autonavi.map.widget.wheel.TimePickerWidgetView r0 = r12.wv_mins
            r0.setCyclic(r3)
            r0 = 0
        L_0x018b:
            com.autonavi.minimap.widget.CustomTimePickerAdapter r1 = r12.mMinuteAdapter
            int r1 = r1.getItemsCount()
            if (r0 >= r1) goto L_0x01a6
            java.lang.String r1 = java.lang.String.valueOf(r13)
            com.autonavi.minimap.widget.CustomTimePickerAdapter r2 = r12.mMinuteAdapter
            java.lang.String r2 = r2.getItem(r0)
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x01a6
            int r0 = r0 + 1
            goto L_0x018b
        L_0x01a6:
            com.autonavi.minimap.widget.CustomTimePickerAdapter r13 = r12.mMinuteAdapter
            int r13 = r13.getItemsCount()
            if (r0 >= r13) goto L_0x01b4
            com.autonavi.map.widget.wheel.TimePickerWidgetView r13 = r12.wv_mins
            r13.setCurrentItem(r0)
            goto L_0x01b9
        L_0x01b4:
            com.autonavi.map.widget.wheel.TimePickerWidgetView r13 = r12.wv_mins
            r13.setCurrentItem(r3)
        L_0x01b9:
            com.autonavi.map.widget.DatePickerDialog$1 r13 = new com.autonavi.map.widget.DatePickerDialog$1
            r13.<init>()
            com.autonavi.map.widget.DatePickerDialog$2 r0 = new com.autonavi.map.widget.DatePickerDialog$2
            r0.<init>()
            com.autonavi.map.widget.wheel.TimePickerWidgetView r1 = r12.wv_day
            r1.addChangingListener(r13)
            com.autonavi.map.widget.wheel.TimePickerWidgetView r13 = r12.wv_hours
            r13.addChangingListener(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.widget.DatePickerDialog.onCreate(android.os.Bundle):void");
    }

    public void setPosOnClickListener(OnClickListener onClickListener) {
        this.mPosClickListener = onClickListener;
        NoDBClickUtil.a(findViewById(R.id.btn_datetime_sure), this.mPosClickListener);
    }

    public void setNegOnClickListener(OnClickListener onClickListener) {
        this.mNegClickListener = onClickListener;
        NoDBClickUtil.a(findViewById(R.id.btn_datetime_cancel), this.mNegClickListener);
    }

    public long getSelectedTime() {
        StringBuilder sb = new StringBuilder();
        Calendar instance = Calendar.getInstance();
        instance.set(6, instance.get(6) + this.wv_day.getCurrentItem() + (this.tomorrowFirstState ? 1 : 0));
        sb.append(new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime()));
        sb.append(Token.SEPARATOR);
        sb.append(this.mHoursAdapter.getItem(this.wv_hours.getCurrentItem()));
        sb.append(":");
        sb.append(this.mMinuteAdapter.getItem(this.wv_mins.getCurrentItem()));
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sb.toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void cancel() {
        super.cancel();
    }

    public int[] copyOfRange(int[] iArr, int i, int i2) {
        if (i > i2) {
            throw new IllegalArgumentException();
        }
        int length = iArr.length;
        if (i < 0 || i > length || i2 > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = i2 - i;
        int min = Math.min(i3, length - i);
        int[] iArr2 = new int[i3];
        System.arraycopy(iArr, i, iArr2, 0, min);
        return iArr2;
    }
}
