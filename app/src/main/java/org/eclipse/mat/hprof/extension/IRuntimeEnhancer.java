package org.eclipse.mat.hprof.extension;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;

public interface IRuntimeEnhancer {
    <A> A getAddon(ISnapshot iSnapshot, Class<A> cls) throws SnapshotException;
}
