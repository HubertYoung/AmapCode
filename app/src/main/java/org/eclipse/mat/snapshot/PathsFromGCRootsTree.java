package org.eclipse.mat.snapshot;

import java.util.HashMap;

public final class PathsFromGCRootsTree {
    private int[] objectIds;
    private HashMap<Integer, PathsFromGCRootsTree> objectInboundReferers;
    private int ownId;

    public PathsFromGCRootsTree(int i, HashMap<Integer, PathsFromGCRootsTree> hashMap, int[] iArr) {
        this.ownId = i;
        this.objectInboundReferers = hashMap;
        this.objectIds = iArr;
    }

    public final int getOwnId() {
        return this.ownId;
    }

    public final int[] getObjectIds() {
        return this.objectIds;
    }

    public final PathsFromGCRootsTree getBranch(int i) {
        return this.objectInboundReferers.get(Integer.valueOf(i));
    }
}
