package org.eclipse.mat.snapshot.model;

import org.eclipse.mat.snapshot.ISnapshot;

public class ThreadToLocalReference extends PseudoReference {
    private static final long serialVersionUID = 1;
    private GCRootInfo[] gcRootInfo;
    private int localObjectId;

    public ThreadToLocalReference(ISnapshot iSnapshot, long j, String str, int i, GCRootInfo[] gCRootInfoArr) {
        super(iSnapshot, j, str);
        this.localObjectId = i;
        this.gcRootInfo = gCRootInfoArr;
    }

    public int getObjectId() {
        return this.localObjectId;
    }

    public GCRootInfo[] getGcRootInfo() {
        return this.gcRootInfo;
    }
}
