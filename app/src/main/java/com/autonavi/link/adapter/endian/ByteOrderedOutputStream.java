package com.autonavi.link.adapter.endian;

import java.io.DataOutput;
import java.io.FilterOutputStream;
import java.io.OutputStream;

public abstract class ByteOrderedOutputStream extends FilterOutputStream implements DataOutput {
    public ByteOrderedOutputStream(OutputStream outputStream) {
        super(outputStream);
    }
}
