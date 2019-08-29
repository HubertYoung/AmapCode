package com.alipay.mobile.common.transport.http.multipart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FilePartSource implements PartSource {
    private File a;
    private String b;

    public FilePartSource(File file) {
        this.a = null;
        this.b = null;
        this.a = file;
        if (file == null) {
            return;
        }
        if (!file.isFile()) {
            throw new FileNotFoundException("File is not a normal file.");
        } else if (!file.canRead()) {
            throw new FileNotFoundException("File is not readable.");
        } else {
            this.b = file.getName();
        }
    }

    public FilePartSource(String fileName, File file) {
        this(file);
        if (fileName != null) {
            this.b = fileName;
        }
    }

    public long getLength() {
        if (this.a != null) {
            return this.a.length();
        }
        return 0;
    }

    public String getFileName() {
        return this.b == null ? "noname" : this.b;
    }

    public InputStream createInputStream() {
        if (this.a != null) {
            return new FileInputStream(this.a);
        }
        return new ByteArrayInputStream(new byte[0]);
    }
}
