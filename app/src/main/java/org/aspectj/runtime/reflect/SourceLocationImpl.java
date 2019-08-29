package org.aspectj.runtime.reflect;

import org.aspectj.lang.reflect.SourceLocation;

class SourceLocationImpl implements SourceLocation {
    String fileName;
    int line;
    Class withinType;

    SourceLocationImpl(Class withinType2, String fileName2, int line2) {
        this.withinType = withinType2;
        this.fileName = fileName2;
        this.line = line2;
    }

    public Class getWithinType() {
        return this.withinType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return -1;
    }

    public String toString() {
        return new StringBuffer().append(getFileName()).append(":").append(getLine()).toString();
    }
}
