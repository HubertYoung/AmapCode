package com.alipay.mobile.antui.picker;

import com.alipay.mobile.antui.picker.AUWheelView.OnWheelViewListener;

/* compiled from: AUDatePickerView */
final class m implements OnWheelViewListener {
    final /* synthetic */ AUDatePickerView a;

    m(AUDatePickerView this$0) {
        this.a = this$0;
    }

    public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
        this.a.selectedMinuteIndex = selectedIndex;
        this.a.onDatePickChange();
    }
}
