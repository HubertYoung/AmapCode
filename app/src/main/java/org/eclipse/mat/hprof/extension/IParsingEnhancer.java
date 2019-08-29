package org.eclipse.mat.hprof.extension;

import java.io.IOException;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.SnapshotInfo;

public interface IParsingEnhancer {
    void onParsingCompleted(SnapshotInfo snapshotInfo) throws SnapshotException, IOException;
}
