package com.alipay.mobile.antui.picker;

import com.alipay.mobile.antui.picker.AUWheelView.OnWheelViewListener;

/* compiled from: AUDatePickerView */
final class i implements OnWheelViewListener {
    final /* synthetic */ AUDatePickerView a;

    i(AUDatePickerView this$0) {
        this.a = this$0;
    }

    public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
        this.a.selectedYearIndex = selectedIndex;
        this.a.handleChangeByMinMaxDate(true, false, false, false);
        this.a.onDatePickChange();
    }
}
