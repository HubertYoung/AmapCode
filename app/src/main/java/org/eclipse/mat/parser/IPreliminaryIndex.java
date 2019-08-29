package org.eclipse.mat.parser;

import java.util.List;
import org.eclipse.mat.collect.HashMapIntObject;
import org.eclipse.mat.parser.index.IIndexReader.IOne2LongIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2ManyIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2OneIndex;
import org.eclipse.mat.parser.model.ClassImpl;
import org.eclipse.mat.parser.model.XGCRootInfo;
import org.eclipse.mat.parser.model.XSnapshotInfo;

public interface IPreliminaryIndex {
    XSnapshotInfo getSnapshotInfo();

    void setArray2size(IOne2OneIndex iOne2OneIndex);

    void setClassesById(HashMapIntObject<ClassImpl> hashMapIntObject);

    void setGcRoots(HashMapIntObject<List<XGCRootInfo>> hashMapIntObject);

    void setIdentifiers(IOne2LongIndex iOne2LongIndex);

    void setObject2classId(IOne2OneIndex iOne2OneIndex);

    void setOutbound(IOne2ManyIndex iOne2ManyIndex);

    void setThread2objects2roots(HashMapIntObject<HashMapIntObject<List<XGCRootInfo>>> hashMapIntObject);
}
