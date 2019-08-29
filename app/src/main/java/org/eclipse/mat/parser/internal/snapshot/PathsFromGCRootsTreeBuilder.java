package org.eclipse.mat.parser.internal.snapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.eclipse.mat.snapshot.PathsFromGCRootsTree;

public class PathsFromGCRootsTreeBuilder {
    private ArrayList<Integer> objectIds = new ArrayList<>();
    private HashMap<Integer, PathsFromGCRootsTreeBuilder> objectInboundReferers;
    private int ownId;

    public PathsFromGCRootsTreeBuilder(int i) {
        this.ownId = i;
        this.objectInboundReferers = new HashMap<>();
    }

    public HashMap<Integer, PathsFromGCRootsTreeBuilder> getObjectReferers() {
        return this.objectInboundReferers;
    }

    public PathsFromGCRootsTree toPathsFromGCRootsTree() {
        HashMap hashMap = new HashMap(this.objectInboundReferers.size());
        for (Entry next : this.objectInboundReferers.entrySet()) {
            hashMap.put(next.getKey(), ((PathsFromGCRootsTreeBuilder) next.getValue()).toPathsFromGCRootsTree());
        }
        int[] iArr = new int[this.objectIds.size()];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = this.objectIds.get(i).intValue();
        }
        return new PathsFromGCRootsTree(this.ownId, hashMap, iArr);
    }

    public int getOwnId() {
        return this.ownId;
    }

    public void addObjectReferer(PathsFromGCRootsTreeBuilder pathsFromGCRootsTreeBuilder) {
        if (this.objectInboundReferers.put(Integer.valueOf(pathsFromGCRootsTreeBuilder.getOwnId()), pathsFromGCRootsTreeBuilder) == null) {
            this.objectIds.add(Integer.valueOf(pathsFromGCRootsTreeBuilder.getOwnId()));
        }
    }
}
