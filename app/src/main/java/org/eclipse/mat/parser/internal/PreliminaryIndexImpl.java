package org.eclipse.mat.parser.internal;

import java.util.List;
import org.eclipse.mat.collect.HashMapIntObject;
import org.eclipse.mat.parser.IPreliminaryIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2LongIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2ManyIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2OneIndex;
import org.eclipse.mat.parser.model.ClassImpl;
import org.eclipse.mat.parser.model.XGCRootInfo;
import org.eclipse.mat.parser.model.XSnapshotInfo;

class PreliminaryIndexImpl implements IPreliminaryIndex {
    IOne2OneIndex array2size = null;
    HashMapIntObject<ClassImpl> classesById;
    HashMapIntObject<List<XGCRootInfo>> gcRoots;
    IOne2LongIndex identifiers = null;
    IOne2OneIndex object2classId = null;
    IOne2ManyIndex outbound = null;
    XSnapshotInfo snapshotInfo;
    HashMapIntObject<HashMapIntObject<List<XGCRootInfo>>> thread2objects2roots;

    public void delete() {
    }

    public PreliminaryIndexImpl(XSnapshotInfo xSnapshotInfo) {
        this.snapshotInfo = xSnapshotInfo;
    }

    public XSnapshotInfo getSnapshotInfo() {
        return this.snapshotInfo;
    }

    public void setClassesById(HashMapIntObject<ClassImpl> hashMapIntObject) {
        this.classesById = hashMapIntObject;
    }

    public void setGcRoots(HashMapIntObject<List<XGCRootInfo>> hashMapIntObject) {
        this.gcRoots = hashMapIntObject;
    }

    public void setThread2objects2roots(HashMapIntObject<HashMapIntObject<List<XGCRootInfo>>> hashMapIntObject) {
        this.thread2objects2roots = hashMapIntObject;
    }

    public void setOutbound(IOne2ManyIndex iOne2ManyIndex) {
        this.outbound = iOne2ManyIndex;
    }

    public void setIdentifiers(IOne2LongIndex iOne2LongIndex) {
        this.identifiers = iOne2LongIndex;
    }

    public void setObject2classId(IOne2OneIndex iOne2OneIndex) {
        this.object2classId = iOne2OneIndex;
    }

    public void setArray2size(IOne2OneIndex iOne2OneIndex) {
        this.array2size = iOne2OneIndex;
    }
}
