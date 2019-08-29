package com.squareup.leakcanary;

import java.io.File;

public interface HeapDumper {
    public static final File NO_DUMP = null;

    File dumpHeap();
}
