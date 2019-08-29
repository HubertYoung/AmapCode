package org.nanohttpd.protocols.http.tempfiles;

import org.nanohttpd.util.IFactory;

public class DefaultTempFileManagerFactory implements IFactory<ITempFileManager> {
    public ITempFileManager create() {
        return new DefaultTempFileManager();
    }
}
