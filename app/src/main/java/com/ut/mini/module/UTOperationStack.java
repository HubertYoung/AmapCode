package com.ut.mini.module;

import android.text.TextUtils;
import java.util.List;
import java.util.Vector;

@Deprecated
public class UTOperationStack {
    private static final String DEFAULT_SEPARATOR = ",";
    private static final int MAX_HISTORY_AMOUNT = 1000;
    private static UTOperationStack s_instance = new UTOperationStack();
    private List<String> mActionHistory = new Vector();
    private int mHistoryAmount = 1000;

    private UTOperationStack() {
    }

    public static UTOperationStack getInstance() {
        return s_instance;
    }

    public String getOperationHistory(int i, String str) {
        StringBuffer stringBuffer = new StringBuffer(500);
        if (TextUtils.isEmpty(str)) {
            str = ",";
        }
        if (i <= 0) {
            return null;
        }
        boolean z = true;
        if (i >= this.mActionHistory.size()) {
            for (String next : this.mActionHistory) {
                if (!z) {
                    stringBuffer.append(str);
                }
                stringBuffer.append(next);
                z = false;
            }
        } else {
            int size = (this.mActionHistory.size() - i) - 1;
            int i2 = size;
            while (size < this.mActionHistory.size()) {
                String str2 = this.mActionHistory.get(i2);
                if (!TextUtils.isEmpty(str2)) {
                    if (!z) {
                        stringBuffer.append(str);
                    }
                    stringBuffer.append(str2);
                    z = false;
                }
                i2++;
            }
        }
        return stringBuffer.toString();
    }

    public void addAction(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (this.mActionHistory.size() >= this.mHistoryAmount) {
                this.mActionHistory.remove(0);
            }
            this.mActionHistory.add(str);
        }
    }

    public void clear() {
        if (this.mActionHistory != null) {
            this.mActionHistory.clear();
        }
    }
}
