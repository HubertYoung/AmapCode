package com.ali.user.mobile.ui.widget.inputfomatter;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import java.util.ArrayList;

public class APSplitTextFormatter implements APFormatter {
    private ArrayList<Integer> a = new ArrayList<>();
    private InputFilter[] b;

    public APSplitTextFormatter(String str) {
        if (str != null && str.length() > 0) {
            for (String parseInt : str.split(",")) {
                this.a.add(Integer.valueOf(Integer.parseInt(parseInt)));
            }
        }
    }

    public final void a(Editable editable) {
        if (this.a.size() != 0) {
            int selectionStart = Selection.getSelectionStart(editable);
            String obj = editable.toString();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < obj.length(); i++) {
                arrayList.add(Character.valueOf(obj.charAt(i)));
            }
            int i2 = selectionStart;
            int i3 = 0;
            while (i3 < arrayList.size()) {
                if (!this.a.contains(Integer.valueOf(i3))) {
                    while (arrayList.size() > i3 && ((Character) arrayList.get(i3)).charValue() == ' ') {
                        arrayList.remove(i3);
                        if (i3 < i2) {
                            i2--;
                        }
                    }
                } else if (((Character) arrayList.get(i3)).charValue() != ' ') {
                    arrayList.add(i3, Character.valueOf(' '));
                    if (i3 < i2) {
                        i2++;
                    }
                }
                i3++;
            }
            StringBuilder sb = new StringBuilder(arrayList.size());
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                sb.append(arrayList.get(i4));
            }
            String sb2 = sb.toString();
            if (!sb2.equals(obj)) {
                if (this.b != null) {
                    editable.setFilters(this.b);
                }
                editable.replace(0, editable.toString().length(), sb2, 0, sb2.length());
            }
            while (true) {
                if (i2 > editable.length() || (i2 > 0 && sb2.charAt(i2 - 1) == ' ')) {
                    i2--;
                }
            }
            Selection.setSelection(editable, i2);
        }
    }
}
