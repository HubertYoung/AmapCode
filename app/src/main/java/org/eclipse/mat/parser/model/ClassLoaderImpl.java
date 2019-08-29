package org.eclipse.mat.parser.model;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.ArrayInt;
import org.eclipse.mat.parser.internal.SnapshotImpl;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.Field;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IClassLoader;
import org.eclipse.mat.snapshot.registry.ClassSpecificNameResolverRegistry;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.VoidProgressListener;

public class ClassLoaderImpl extends InstanceImpl implements IClassLoader {
    public static final String NO_LABEL = "__none__";
    private static final long serialVersionUID = 1;
    private volatile transient List<IClass> definedClasses = null;

    public ClassLoaderImpl(int i, long j, ClassImpl classImpl, List<Field> list) {
        super(i, j, classImpl, list);
    }

    /* access modifiers changed from: protected */
    public synchronized void readFully() {
        if (getObjectAddress() == 0) {
            setFields(new ArrayList());
        } else {
            super.readFully();
        }
    }

    public String getClassSpecificName() {
        String classLoaderLabel = this.source.getClassLoaderLabel(getObjectId());
        if (NO_LABEL.equals(classLoaderLabel)) {
            classLoaderLabel = ClassSpecificNameResolverRegistry.resolve(this);
            if (classLoaderLabel != null) {
                this.source.setClassLoaderLabel(getObjectId(), classLoaderLabel);
            }
        }
        return classLoaderLabel;
    }

    public List<IClass> getDefinedClasses() throws SnapshotException {
        List<IClass> list = this.definedClasses;
        if (list == null) {
            synchronized (this) {
                if (list == null) {
                    try {
                        list = doGetDefinedClasses(this.source, getObjectId());
                        this.definedClasses = list;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
        return list;
    }

    public long getRetainedHeapSizeOfObjects(boolean z, boolean z2, IProgressListener iProgressListener) throws SnapshotException {
        return doGetRetainedHeapSizeOfObjects(this.source, getObjectId(), z, z2, iProgressListener);
    }

    public static final List<IClass> doGetDefinedClasses(ISnapshot iSnapshot, int i) throws SnapshotException {
        ArrayList arrayList = new ArrayList();
        for (IClass next : iSnapshot.getClasses()) {
            if (next.getClassLoaderId() == i) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public static final long doGetRetainedHeapSizeOfObjects(ISnapshot iSnapshot, int i, boolean z, boolean z2, IProgressListener iProgressListener) throws SnapshotException {
        long j;
        SnapshotImpl snapshotImpl = (SnapshotImpl) iSnapshot;
        long j2 = snapshotImpl.getRetainedSizeCache().get(i);
        int i2 = (j2 > 0 ? 1 : (j2 == 0 ? 0 : -1));
        if (i2 > 0 || !z) {
            return j2;
        }
        if (i2 < 0 && z2) {
            return j2;
        }
        if (iProgressListener == null) {
            iProgressListener = new VoidProgressListener();
        }
        ArrayInt arrayInt = new ArrayInt();
        arrayInt.add(i);
        for (IClass next : doGetDefinedClasses(iSnapshot, i)) {
            arrayInt.add(next.getObjectId());
            arrayInt.addAll(next.getObjectIds());
        }
        if (!z2) {
            int[] retainedSet = iSnapshot.getRetainedSet(arrayInt.toArray(), iProgressListener);
            if (iProgressListener.isCanceled()) {
                return 0;
            }
            j = iSnapshot.getHeapSize(retainedSet);
        } else {
            j = iSnapshot.getMinRetainedSize(arrayInt.toArray(), iProgressListener);
            if (iProgressListener.isCanceled()) {
                return 0;
            }
        }
        if (z2) {
            j = -j;
        }
        snapshotImpl.getRetainedSizeCache().put(i, j);
        return j;
    }
}
