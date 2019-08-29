package com.google.zxing.oned.rss.expanded;

import java.util.ArrayList;
import java.util.List;

final class ExpandedRow {
    private final List<ExpandedPair> pairs;
    private final int rowNumber;
    private final boolean wasReversed;

    ExpandedRow(List<ExpandedPair> list, int i, boolean z) {
        this.pairs = new ArrayList(list);
        this.rowNumber = i;
        this.wasReversed = z;
    }

    /* access modifiers changed from: 0000 */
    public final List<ExpandedPair> getPairs() {
        return this.pairs;
    }

    /* access modifiers changed from: 0000 */
    public final int getRowNumber() {
        return this.rowNumber;
    }

    /* access modifiers changed from: 0000 */
    public final boolean isReversed() {
        return this.wasReversed;
    }

    /* access modifiers changed from: 0000 */
    public final boolean isEquivalent(List<ExpandedPair> list) {
        return this.pairs.equals(list);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        sb.append(this.pairs);
        sb.append(" }");
        return sb.toString();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ExpandedRow)) {
            return false;
        }
        ExpandedRow expandedRow = (ExpandedRow) obj;
        if (!this.pairs.equals(expandedRow.getPairs()) || this.wasReversed != expandedRow.wasReversed) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.pairs.hashCode() ^ Boolean.valueOf(this.wasReversed).hashCode();
    }
}
