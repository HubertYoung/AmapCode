package org.eclipse.mat.snapshot.model;

import java.util.List;

public interface IInstance extends IObject {
    Field getField(String str);

    List<Field> getFields();
}
