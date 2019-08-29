package org.eclipse.mat.snapshot.extension;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.model.IObject;

public interface IClassSpecificNameResolver {
    String resolve(IObject iObject) throws SnapshotException;
}
