package org.eclipse.mat.parser.model;

import java.util.Date;
import org.eclipse.mat.snapshot.SnapshotInfo;

public final class XSnapshotInfo extends SnapshotInfo {
    private static final long serialVersionUID = 3;

    public XSnapshotInfo() {
        super(null, null, null, 0, null, 0, 0, 0, 0, 0);
    }

    public final void setPrefix(String str) {
        this.prefix = str;
    }

    public final void setPath(String str) {
        this.path = str;
    }

    public final void setCreationDate(Date date) {
        this.creationDate = new Date(date.getTime());
    }

    public final void setIdentifierSize(int i) {
        this.identifierSize = i;
    }

    public final void setJvmInfo(String str) {
        this.jvmInfo = str;
    }

    public final void setNumberOfClasses(int i) {
        this.numberOfClasses = i;
    }

    public final void setNumberOfClassLoaders(int i) {
        this.numberOfClassLoaders = i;
    }

    public final void setNumberOfGCRoots(int i) {
        this.numberOfGCRoots = i;
    }

    public final void setNumberOfObjects(int i) {
        this.numberOfObjects = i;
    }

    public final void setUsedHeapSize(long j) {
        this.usedHeapSize = j;
    }
}
