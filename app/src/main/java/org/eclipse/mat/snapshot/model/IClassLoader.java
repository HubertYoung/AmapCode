package org.eclipse.mat.snapshot.model;

import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.util.IProgressListener;

public interface IClassLoader extends IInstance {
    List<IClass> getDefinedClasses() throws SnapshotException;

    long getRetainedHeapSizeOfObjects(boolean z, boolean z2, IProgressListener iProgressListener) throws SnapshotException;
}
