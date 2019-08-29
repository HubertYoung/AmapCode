package org.eclipse.mat.snapshot.model;

import java.io.Serializable;

public class FieldDescriptor implements Serializable {
    private static final long serialVersionUID = 2;
    protected String name;
    protected int type;

    public FieldDescriptor(String str, int i) {
        this.name = str;
        this.type = i;
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getVerboseSignature() {
        if (this.type == 2) {
            return "ref";
        }
        String str = IPrimitiveArray.TYPE[this.type];
        return str.substring(0, str.length() - 2);
    }
}
