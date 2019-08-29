package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

final class ExpandedPair {
    private final FinderPattern finderPattern;
    private final DataCharacter leftChar;
    private final boolean mayBeLast;
    private final DataCharacter rightChar;

    ExpandedPair(DataCharacter dataCharacter, DataCharacter dataCharacter2, FinderPattern finderPattern2, boolean z) {
        this.leftChar = dataCharacter;
        this.rightChar = dataCharacter2;
        this.finderPattern = finderPattern2;
        this.mayBeLast = z;
    }

    /* access modifiers changed from: 0000 */
    public final boolean mayBeLast() {
        return this.mayBeLast;
    }

    /* access modifiers changed from: 0000 */
    public final DataCharacter getLeftChar() {
        return this.leftChar;
    }

    /* access modifiers changed from: 0000 */
    public final DataCharacter getRightChar() {
        return this.rightChar;
    }

    /* access modifiers changed from: 0000 */
    public final FinderPattern getFinderPattern() {
        return this.finderPattern;
    }

    public final boolean mustBeLast() {
        return this.rightChar == null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        sb.append(this.leftChar);
        sb.append(" , ");
        sb.append(this.rightChar);
        sb.append(" : ");
        sb.append(this.finderPattern == null ? "null" : Integer.valueOf(this.finderPattern.getValue()));
        sb.append(" ]");
        return sb.toString();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ExpandedPair)) {
            return false;
        }
        ExpandedPair expandedPair = (ExpandedPair) obj;
        if (!equalsOrNull(this.leftChar, expandedPair.leftChar) || !equalsOrNull(this.rightChar, expandedPair.rightChar) || !equalsOrNull(this.finderPattern, expandedPair.finderPattern)) {
            return false;
        }
        return true;
    }

    private static boolean equalsOrNull(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public final int hashCode() {
        return (hashNotNull(this.leftChar) ^ hashNotNull(this.rightChar)) ^ hashNotNull(this.finderPattern);
    }

    private static int hashNotNull(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
