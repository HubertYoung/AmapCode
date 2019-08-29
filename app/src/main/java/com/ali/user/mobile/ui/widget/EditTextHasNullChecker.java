package com.ali.user.mobile.ui.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TabHost;
import com.ali.user.mobile.ui.widget.keyboard.APSafeEditText;
import com.ali.user.mobile.ui.widget.keyboard.APSafeTextWatcher;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class EditTextHasNullChecker extends APSafeTextWatcher implements TextWatcher, OnCheckedChangeListener {
    private final List<EditText> a = new CopyOnWriteArrayList();
    private final List<EditText> b = new CopyOnWriteArrayList();
    private final Set<Button> c = new HashSet();
    private final Set<CheckBox> d = new HashSet();

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void a(EditText editText) {
        editText.addTextChangedListener(this);
        this.b.add(editText);
        a();
    }

    public final void a(Button button) {
        if (button != null) {
            button.setEnabled(false);
            this.c.add(button);
            a();
        }
    }

    public void afterTextChanged(Editable editable) {
        a();
    }

    private void a() {
        boolean z;
        boolean z2;
        boolean z3;
        String str;
        String str2;
        if (this.c == null || !this.c.isEmpty()) {
            Iterator<EditText> it = this.b.iterator();
            while (true) {
                z = false;
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                }
                EditText next = it.next();
                if (a((View) next)) {
                    if (next instanceof APSafeEditText) {
                        str2 = ((APSafeEditText) next).getSafeText().toString().trim();
                    } else {
                        str2 = next.getText().toString().trim();
                    }
                    if ("".equals(str2)) {
                        z2 = true;
                        break;
                    }
                }
            }
            if (!z2) {
                Iterator<EditText> it2 = this.a.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z3 = false;
                        break;
                    }
                    EditText next2 = it2.next();
                    if (a((View) next2)) {
                        if (next2 instanceof APSafeEditText) {
                            str = ((APSafeEditText) next2).getSafeText().toString().trim();
                        } else {
                            str = next2.getText().toString().trim();
                        }
                        if (str.length() < 6) {
                            z3 = true;
                            break;
                        }
                    }
                }
                if (!z3) {
                    Iterator<CheckBox> it3 = this.d.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            if (!it3.next().isChecked()) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            z = true;
            for (Button enabled : this.c) {
                enabled.setEnabled(!z);
            }
        }
    }

    private static boolean a(View view) {
        while (view.getId() != 16908290) {
            if (view.getVisibility() == 8 || view.getVisibility() == 4) {
                return false;
            }
            if (!(view.getParent() instanceof View) || (view.getParent() instanceof TabHost)) {
                return true;
            }
            view = (View) view.getParent();
        }
        return true;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        a();
    }
}
