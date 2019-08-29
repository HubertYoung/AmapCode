package org.eclipse.mat.parser;

import java.io.IOException;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.parser.model.ObjectArrayImpl;
import org.eclipse.mat.parser.model.PrimitiveArrayImpl;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.IObject;

public interface IObjectReader {
    void close() throws IOException;

    <A> A getAddon(Class<A> cls) throws SnapshotException;

    void open(ISnapshot iSnapshot) throws SnapshotException, IOException;

    IObject read(int i, ISnapshot iSnapshot) throws SnapshotException, IOException;

    long[] readObjectArrayContent(ObjectArrayImpl objectArrayImpl, int i, int i2) throws IOException, SnapshotException;

    Object readPrimitiveArrayContent(PrimitiveArrayImpl primitiveArrayImpl, int i, int i2) throws IOException, SnapshotException;
}
