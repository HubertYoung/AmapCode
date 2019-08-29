package org.eclipse.mat.snapshot.model;

import org.eclipse.mat.snapshot.ISnapshot;

public class PseudoReference extends NamedReference {
    private static final long serialVersionUID = 1;

    public PseudoReference(ISnapshot iSnapshot, long j, String str) {
        super(iSnapshot, j, str);
    }
}
