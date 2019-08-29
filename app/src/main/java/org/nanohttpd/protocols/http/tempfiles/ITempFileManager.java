package org.nanohttpd.protocols.http.tempfiles;

public interface ITempFileManager {
    void clear();

    ITempFile createTempFile(String str);
}
