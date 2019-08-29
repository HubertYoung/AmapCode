package org.eclipse.mat.hprof;

import java.io.IOException;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayLong;
import org.eclipse.mat.collect.HashMapLongObject;
import org.eclipse.mat.parser.IPreliminaryIndex;
import org.eclipse.mat.parser.index.IIndexReader.IOne2LongIndex;
import org.eclipse.mat.parser.model.ClassImpl;
import org.eclipse.mat.parser.model.XSnapshotInfo;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.util.IProgressListener;

public interface IHprofParserHandler {
    public static final String CREATION_DATE = "CREATION_DATE";
    public static final String IDENTIFIER_SIZE = "ID_SIZE";
    public static final String VERSION = "VERSION";

    public static class HeapObject {
        public ClassImpl clazz;
        public boolean isArray = false;
        public long objectAddress;
        public int objectId;
        public ArrayLong references = new ArrayLong();
        public int usedHeapSize;

        public HeapObject(int i, long j, ClassImpl classImpl, int i2) {
            this.objectId = i;
            this.objectAddress = j;
            this.clazz = classImpl;
            this.usedHeapSize = i2;
            this.isArray = false;
        }
    }

    void addClass(ClassImpl classImpl, long j) throws IOException;

    void addGCRoot(long j, long j2, int i) throws IOException;

    void addObject(HeapObject heapObject, long j) throws IOException;

    void addProperty(String str, String str2) throws IOException;

    void beforePass1(XSnapshotInfo xSnapshotInfo) throws IOException;

    void beforePass2(IProgressListener iProgressListener) throws IOException, SnapshotException;

    void cancel();

    IOne2LongIndex fillIn(IPreliminaryIndex iPreliminaryIndex) throws IOException;

    HashMapLongObject<String> getConstantPool();

    int getIdentifierSize();

    XSnapshotInfo getSnapshotInfo();

    IClass lookupClass(long j);

    IClass lookupClassByIndex(int i);

    IClass lookupClassByName(String str, boolean z);

    int mapAddressToId(long j);

    void reportInstance(long j, long j2);

    void reportRequiredObjectArray(long j);

    void reportRequiredPrimitiveArray(int i);

    List<IClass> resolveClassHierarchy(long j);
}
