package org.eclipse.mat.parser.internal;

import org.eclipse.mat.snapshot.model.IStackFrame;

class StackFrameImpl implements IStackFrame {
    private int[] localObjectIds;
    private String text;

    public StackFrameImpl(String str, int[] iArr) {
        this.text = str;
        this.localObjectIds = iArr;
    }

    public int[] getLocalObjectsIds() {
        return this.localObjectIds == null ? new int[0] : this.localObjectIds;
    }

    public String getText() {
        return this.text;
    }
}
