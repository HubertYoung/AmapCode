package com.alipay.android.phone.mobilesdk.storage.file;

import android.text.TextUtils;
import java.io.File;

public class BaseFile extends File {
    protected static final String nullString = "";
    private static final long serialVersionUID = 658006601973659810L;

    public BaseFile(String path) {
        super(path);
    }

    protected static String formatPath(String input) {
        if (TextUtils.isEmpty(input)) {
            return File.separator;
        }
        return (File.separator + input + File.separator).replaceAll("\\\\{1,}", File.separator).replaceAll("\\/{2,}", File.separator);
    }
}
