package com.alipay.mobile.antui.picker;

import com.alipay.mobile.antui.picker.AUWheelView.OnWheelViewListener;

/* compiled from: AUDatePickerView */
final class k implements OnWheelViewListener {
    final /* synthetic */ AUDatePickerView a;

    k(AUDatePickerView this$0) {
        this.a = this$0;
    }

    public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
        this.a.selectedDayIndex = selectedIndex;
        this.a.handleChangeByMinMaxDate(false, false, true, false);
        this.a.onDatePickChange();
    }
}
