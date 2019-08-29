package com.alipay.mobile.antui.input.formatter;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import com.alipay.mobile.antui.input.AUFormatter;
import java.util.ArrayList;

public class AUSplitTextFormatter implements AUFormatter {
    private InputFilter[] filters;
    private ArrayList<Integer> separateIntList = new ArrayList<>();

    public AUSplitTextFormatter(String separateList) {
        if (separateList != null && separateList.length() > 0) {
            for (String curIndex : separateList.split(",")) {
                this.separateIntList.add(Integer.valueOf(Integer.parseInt(curIndex)));
            }
        }
    }

    public void format(Editable inputedStr) {
        if (this.separateIntList.size() != 0) {
            int selectionStart = Selection.getSelectionStart(inputedStr);
            String input = inputedStr.toString();
            ArrayList chars = new ArrayList();
            for (int i = 0; i < input.length(); i++) {
                chars.add(Character.valueOf(input.charAt(i)));
            }
            int i2 = 0;
            while (i2 < chars.size()) {
                if (!this.separateIntList.contains(Integer.valueOf(i2))) {
                    while (chars.size() > i2 && ((Character) chars.get(i2)).charValue() == ' ') {
                        chars.remove(i2);
                        if (i2 < selectionStart) {
                            selectionStart--;
                        }
                    }
                } else if (((Character) chars.get(i2)).charValue() != ' ') {
                    chars.add(i2, Character.valueOf(' '));
                    if (i2 < selectionStart) {
                        selectionStart++;
                    }
                }
                i2++;
            }
            StringBuilder sb = new StringBuilder(chars.size());
            for (int i3 = 0; i3 < chars.size(); i3++) {
                sb.append(chars.get(i3));
            }
            String result = sb.toString();
            if (!result.equals(input)) {
                if (this.filters != null) {
                    inputedStr.setFilters(this.filters);
                }
                inputedStr.replace(0, inputedStr.toString().length(), result, 0, result.length());
            }
            while (true) {
                if (selectionStart > inputedStr.length() || (selectionStart > 0 && result.charAt(selectionStart - 1) == ' ')) {
                    selectionStart--;
                }
            }
            Selection.setSelection(inputedStr, selectionStart);
        }
    }
}
