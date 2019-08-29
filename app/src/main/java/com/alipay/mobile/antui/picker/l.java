package com.alipay.mobile.antui.picker;

import com.alipay.mobile.antui.picker.AUWheelView.OnWheelViewListener;

/* compiled from: AUDatePickerView */
final class l implements OnWheelViewListener {
    final /* synthetic */ AUDatePickerView a;

    l(AUDatePickerView this$0) {
        this.a = this$0;
    }

    public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
        this.a.selectedHourIndex = selectedIndex;
        this.a.handleChangeByMinMaxDate(false, false, false, true);
        this.a.onDatePickChange();
    }
}
