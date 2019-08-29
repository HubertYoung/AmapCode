package com.squareup.leakcanary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LeakTrace implements Serializable {
    public final List<LeakTraceElement> elements;

    LeakTrace(List<LeakTraceElement> list) {
        this.elements = Collections.unmodifiableList(new ArrayList(list));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.elements.size(); i++) {
            LeakTraceElement leakTraceElement = this.elements.get(i);
            sb.append("* ");
            if (i == 0) {
                sb.append("GC ROOT ");
            } else if (i == this.elements.size() - 1) {
                sb.append("leaks ");
            } else {
                sb.append("references ");
            }
            sb.append(leakTraceElement);
            sb.append("\n");
        }
        return sb.toString();
    }

    public final String toDetailedString() {
        String str = "";
        for (LeakTraceElement detailedString : this.elements) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(detailedString.toDetailedString());
            str = sb.toString();
        }
        return str;
    }
}
