package org.eclipse.mat.parser;

import java.io.File;
import java.io.IOException;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.util.IProgressListener;

public interface IIndexBuilder {
    void cancel();

    void clean(int[] iArr, IProgressListener iProgressListener) throws IOException;

    void fill(IPreliminaryIndex iPreliminaryIndex, IProgressListener iProgressListener) throws SnapshotException, IOException;

    void init(File file, String str) throws SnapshotException, IOException;
}
