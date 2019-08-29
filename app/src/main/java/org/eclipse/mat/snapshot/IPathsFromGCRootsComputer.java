package org.eclipse.mat.snapshot;

import java.util.Collection;
import org.eclipse.mat.SnapshotException;

public interface IPathsFromGCRootsComputer {
    int[] getNextShortestPath() throws SnapshotException;

    PathsFromGCRootsTree getTree(Collection<int[]> collection);
}
