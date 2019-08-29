package org.eclipse.mat.snapshot.model;

import java.io.Serializable;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;

public interface IObject extends Serializable {

    public interface Type {
        public static final int BOOLEAN = 4;
        public static final int BYTE = 8;
        public static final int CHAR = 5;
        public static final int DOUBLE = 7;
        public static final int FLOAT = 6;
        public static final int INT = 10;
        public static final int LONG = 11;
        public static final int OBJECT = 2;
        public static final int SHORT = 9;
    }

    String getClassSpecificName();

    IClass getClazz();

    String getDisplayName();

    GCRootInfo[] getGCRootInfo() throws SnapshotException;

    long getObjectAddress();

    int getObjectId();

    List<NamedReference> getOutboundReferences();

    long getRetainedHeapSize();

    ISnapshot getSnapshot();

    String getTechnicalName();

    int getUsedHeapSize();

    Object resolveValue(String str) throws SnapshotException;
}
