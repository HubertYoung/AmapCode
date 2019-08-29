package org.nanohttpd.protocols.http.tempfiles;

import com.alipay.multimedia.common.logging.MLog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DefaultTempFileManager implements ITempFileManager {
    private final File a = new File(System.getProperty("java.io.tmpdir"));
    private final List<ITempFile> b;

    public DefaultTempFileManager() {
        if (!this.a.exists()) {
            this.a.mkdirs();
        }
        this.b = new ArrayList();
    }

    public void clear() {
        for (ITempFile file : this.b) {
            try {
                file.delete();
            } catch (Exception ignored) {
                MLog.e("DefaultTempFileManager", "could not delete file.exp=" + ignored);
            }
        }
        this.b.clear();
    }

    public ITempFile createTempFile(String filename_hint) {
        DefaultTempFile tempFile = new DefaultTempFile(this.a);
        this.b.add(tempFile);
        return tempFile;
    }
}
