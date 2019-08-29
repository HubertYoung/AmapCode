package org.eclipse.mat.parser.index;

import java.io.IOException;
import java.io.Serializable;
import org.eclipse.mat.SnapshotException;

public interface IIndexReader {

    public interface IOne2LongIndex extends IIndexReader {
        long get(int i);

        long[] getNext(int i, int i2);

        int reverse(long j);
    }

    public interface IOne2ManyIndex extends IIndexReader {
        int[] get(int i);
    }

    public interface IOne2ManyObjectsIndex extends IOne2ManyIndex {
        int[] getObjectsOf(Serializable serializable) throws SnapshotException, IOException;
    }

    public interface IOne2OneIndex extends IIndexReader {
        int get(int i);

        int[] getAll(int[] iArr);

        int[] getNext(int i, int i2);
    }

    void close() throws IOException;

    void delete();

    int size();

    void unload() throws IOException;
}
