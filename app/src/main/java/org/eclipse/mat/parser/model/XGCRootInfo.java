package org.eclipse.mat.parser.model;

import org.eclipse.mat.snapshot.model.GCRootInfo;

public final class XGCRootInfo extends GCRootInfo {
    private static final long serialVersionUID = 1;

    public XGCRootInfo(long j, long j2, int i) {
        super(j, j2, i);
    }

    public final void setObjectId(int i) {
        this.objectId = i;
    }

    public final void setContextId(int i) {
        this.contextId = i;
    }
}
