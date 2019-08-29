package org.nanohttpd.protocols.http.tempfiles;

import java.io.OutputStream;

public interface ITempFile {
    void delete();

    String getName();

    OutputStream open();
}
