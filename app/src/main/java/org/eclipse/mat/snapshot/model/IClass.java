package org.eclipse.mat.snapshot.model;

import java.util.List;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.util.IProgressListener;

public interface IClass extends IObject {
    public static final String JAVA_LANG_CLASS = "java.lang.Class";
    public static final String JAVA_LANG_CLASSLOADER = "java.lang.ClassLoader";

    boolean doesExtend(String str) throws SnapshotException;

    List<IClass> getAllSubclasses();

    long getClassLoaderAddress();

    int getClassLoaderId();

    List<FieldDescriptor> getFieldDescriptors();

    int getHeapSizePerInstance();

    String getName();

    int getNumberOfObjects();

    int[] getObjectIds() throws SnapshotException;

    long getRetainedHeapSizeOfObjects(boolean z, boolean z2, IProgressListener iProgressListener) throws SnapshotException;

    List<Field> getStaticFields();

    List<IClass> getSubclasses();

    IClass getSuperClass();

    int getSuperClassId();

    boolean hasSuperClass();

    boolean isArrayType();
}
