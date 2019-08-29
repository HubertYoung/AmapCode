package com.alibaba.sqlcrypto;

import android.database.Cursor;
import java.util.Iterator;

public final class CursorJoiner implements Iterable<Result>, Iterator<Result> {
    static final /* synthetic */ boolean $assertionsDisabled = (!CursorJoiner.class.desiredAssertionStatus());
    private int[] mColumnsLeft;
    private int[] mColumnsRight;
    private Result mCompareResult;
    private boolean mCompareResultIsValid;
    private Cursor mCursorLeft;
    private Cursor mCursorRight;
    private String[] mValues;

    public enum Result {
        RIGHT,
        LEFT,
        BOTH
    }

    public CursorJoiner(Cursor cursorLeft, String[] columnNamesLeft, Cursor cursorRight, String[] columnNamesRight) {
        if (columnNamesLeft.length != columnNamesRight.length) {
            throw new IllegalArgumentException("you must have the same number of columns on the left and right, " + columnNamesLeft.length + " != " + columnNamesRight.length);
        }
        this.mCursorLeft = cursorLeft;
        this.mCursorRight = cursorRight;
        this.mCursorLeft.moveToFirst();
        this.mCursorRight.moveToFirst();
        this.mCompareResultIsValid = false;
        this.mColumnsLeft = buildColumnIndiciesArray(cursorLeft, columnNamesLeft);
        this.mColumnsRight = buildColumnIndiciesArray(cursorRight, columnNamesRight);
        this.mValues = new String[(this.mColumnsLeft.length * 2)];
    }

    public final Iterator<Result> iterator() {
        return this;
    }

    private int[] buildColumnIndiciesArray(Cursor cursor, String[] columnNames) {
        int[] columns = new int[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columns[i] = cursor.getColumnIndexOrThrow(columnNames[i]);
        }
        return columns;
    }

    public final boolean hasNext() {
        if (this.mCompareResultIsValid) {
            switch (this.mCompareResult) {
                case BOTH:
                    if (!this.mCursorLeft.isLast() || !this.mCursorRight.isLast()) {
                        return true;
                    }
                    return false;
                case LEFT:
                    if (!this.mCursorLeft.isLast() || !this.mCursorRight.isAfterLast()) {
                        return true;
                    }
                    return false;
                case RIGHT:
                    if (!this.mCursorLeft.isAfterLast() || !this.mCursorRight.isLast()) {
                        return true;
                    }
                    return false;
                default:
                    throw new IllegalStateException("bad value for mCompareResult, " + this.mCompareResult);
            }
        } else if (!this.mCursorLeft.isAfterLast() || !this.mCursorRight.isAfterLast()) {
            return true;
        } else {
            return false;
        }
    }

    public final Result next() {
        boolean hasLeft;
        boolean hasRight;
        if (!hasNext()) {
            throw new IllegalStateException("you must only call next() when hasNext() is true");
        }
        incrementCursors();
        if ($assertionsDisabled || hasNext()) {
            if (!this.mCursorLeft.isAfterLast()) {
                hasLeft = true;
            } else {
                hasLeft = false;
            }
            if (!this.mCursorRight.isAfterLast()) {
                hasRight = true;
            } else {
                hasRight = false;
            }
            if (hasLeft && hasRight) {
                populateValues(this.mValues, this.mCursorLeft, this.mColumnsLeft, 0);
                populateValues(this.mValues, this.mCursorRight, this.mColumnsRight, 1);
                switch (compareStrings(this.mValues)) {
                    case -1:
                        this.mCompareResult = Result.LEFT;
                        break;
                    case 0:
                        this.mCompareResult = Result.BOTH;
                        break;
                    case 1:
                        this.mCompareResult = Result.RIGHT;
                        break;
                }
            } else if (hasLeft) {
                this.mCompareResult = Result.LEFT;
            } else if ($assertionsDisabled || hasRight) {
                this.mCompareResult = Result.RIGHT;
            } else {
                throw new AssertionError();
            }
            this.mCompareResultIsValid = true;
            return this.mCompareResult;
        }
        throw new AssertionError();
    }

    public final void remove() {
        throw new UnsupportedOperationException("not implemented");
    }

    private static void populateValues(String[] values, Cursor cursor, int[] columnIndicies, int startingIndex) {
        if ($assertionsDisabled || startingIndex == 0 || startingIndex == 1) {
            for (int i = 0; i < columnIndicies.length; i++) {
                values[(i * 2) + startingIndex] = cursor.getString(columnIndicies[i]);
            }
            return;
        }
        throw new AssertionError();
    }

    private void incrementCursors() {
        if (this.mCompareResultIsValid) {
            switch (this.mCompareResult) {
                case BOTH:
                    this.mCursorLeft.moveToNext();
                    this.mCursorRight.moveToNext();
                    break;
                case LEFT:
                    this.mCursorLeft.moveToNext();
                    break;
                case RIGHT:
                    this.mCursorRight.moveToNext();
                    break;
            }
            this.mCompareResultIsValid = false;
        }
    }

    private static int compareStrings(String... values) {
        if (values.length % 2 != 0) {
            throw new IllegalArgumentException("you must specify an even number of values");
        }
        for (int index = 0; index < values.length; index += 2) {
            if (values[index] == null) {
                if (values[index + 1] != null) {
                    return -1;
                }
            } else if (values[index + 1] == null) {
                return 1;
            } else {
                int comp = values[index].compareTo(values[index + 1]);
                if (comp != 0) {
                    if (comp >= 0) {
                        return 1;
                    }
                    return -1;
                }
            }
        }
        return 0;
    }
}
