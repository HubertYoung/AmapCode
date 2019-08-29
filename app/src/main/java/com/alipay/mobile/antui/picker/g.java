package com.alipay.mobile.antui.picker;

import com.alipay.mobile.antui.picker.AUWheelView.OnWheelViewListener;

/* compiled from: AUDatePicker */
final class g implements OnWheelViewListener {
    final /* synthetic */ AUDatePicker a;

    g(AUDatePicker this$0) {
        this.a = this$0;
    }

    public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
        this.a.selectedDayIndex = selectedIndex;
    }
}
