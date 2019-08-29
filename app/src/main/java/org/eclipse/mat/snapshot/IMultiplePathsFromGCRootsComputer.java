package org.eclipse.mat.snapshot;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.util.IProgressListener;

public interface IMultiplePathsFromGCRootsComputer {
    Object[] getAllPaths(IProgressListener iProgressListener) throws SnapshotException;

    MultiplePathsFromGCRootsRecord[] getPathsByGCRoot(IProgressListener iProgressListener) throws SnapshotException;
}
