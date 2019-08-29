package org.eclipse.mat.snapshot.model;

import org.eclipse.mat.snapshot.ISnapshot;

public class NamedReference extends ObjectReference {
    private static final long serialVersionUID = 1;
    private String name;

    public NamedReference(ISnapshot iSnapshot, long j, String str) {
        super(iSnapshot, j);
        this.name = str;
    }

    public String getName() {
        return this.name;
    }
}
