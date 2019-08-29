package com.autonavi.map.search.comment.common.adapter;

import java.util.ArrayList;

public class GroupList<G, C> extends ArrayList<C> {
    private static final long serialVersionUID = -7563743992043116362L;
    protected G mGroupObj;

    public GroupList(G g) {
        this.mGroupObj = g;
    }

    public G getGroupObj() {
        return this.mGroupObj;
    }
}
