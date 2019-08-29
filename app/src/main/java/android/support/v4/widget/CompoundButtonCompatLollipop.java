package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.widget.CompoundButton;

class CompoundButtonCompatLollipop {
    CompoundButtonCompatLollipop() {
    }

    static void a(CompoundButton compoundButton, ColorStateList colorStateList) {
        compoundButton.setButtonTintList(colorStateList);
    }

    static ColorStateList a(CompoundButton compoundButton) {
        return compoundButton.getButtonTintList();
    }

    static void a(CompoundButton compoundButton, Mode mode) {
        compoundButton.setButtonTintMode(mode);
    }

    static Mode b(CompoundButton compoundButton) {
        return compoundButton.getButtonTintMode();
    }
}
