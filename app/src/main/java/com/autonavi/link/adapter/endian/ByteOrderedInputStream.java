package com.autonavi.link.adapter.endian;

import java.io.DataInput;
import java.io.FilterInputStream;
import java.io.InputStream;

public abstract class ByteOrderedInputStream extends FilterInputStream implements DataInput {
    protected ByteOrderedInputStream(InputStream inputStream) {
        super(inputStream);
    }
}
