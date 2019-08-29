package com.autonavi.minimap.ajx3.modules.platform;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.exception.InternalJsException;
import com.autonavi.minimap.ajx3.exception.InvalidParamJsException;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleArchive;
import com.autonavi.minimap.ajx3.util.PathUtil;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class AjxModuleArchive extends AbstractModuleArchive {
    private static final String FILE_SCHEME = "file:/";
    private static final int FILE_SCHEME_LEN = 6;
    private static Executor singleExecutor = new ahn(1);
    private Object lock = new Object();

    public AjxModuleArchive(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void compress(String str, String[] strArr, String str2, final JsFunctionCallback jsFunctionCallback) {
        final ArrayList arrayList = new ArrayList();
        if (!"zip".equals(str)) {
            callbackToJs(jsFunctionCallback, new InvalidParamJsException("type is not zip"));
        } else if (strArr == null) {
            callbackToJs(jsFunctionCallback, new InvalidParamJsException("sourceFilePath is null"));
        } else {
            if (strArr.length == 0) {
                callbackToJs(jsFunctionCallback, new InvalidParamJsException("sourceFilePath length is 0"));
            }
            for (String str3 : strArr) {
                if (str3 == null || !str3.startsWith(FILE_SCHEME)) {
                    callbackToJs(jsFunctionCallback, new InvalidParamJsException("file in sourceFilePath is ".concat(String.valueOf(str3))));
                    return;
                }
                arrayList.add(new File(PathUtils.getNoSchemeUrl(PathUtils.rectifyFileScheme(PathUtil.processPath(getContext(), str3)))));
            }
            if (str2 == null || !str2.startsWith(FILE_SCHEME)) {
                callbackToJs(jsFunctionCallback, new InvalidParamJsException("zipFilePath is ".concat(String.valueOf(str2))));
                return;
            }
            final File file = new File(PathUtils.getNoSchemeUrl(PathUtils.rectifyFileScheme(PathUtil.processPath(getContext(), str2))));
            singleExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        File parentFile = file.getParentFile();
                        if (!parentFile.exists()) {
                            if (!parentFile.mkdirs()) {
                                AjxModuleArchive.this.callbackToJs(jsFunctionCallback, new InternalJsException("mkdirs failed"));
                                return;
                            }
                        }
                        ahf.a(arrayList, file, (a) null);
                        AjxModuleArchive.this.callbackToJs(jsFunctionCallback, null);
                    } catch (Exception e) {
                        AjxModuleArchive ajxModuleArchive = AjxModuleArchive.this;
                        JsFunctionCallback jsFunctionCallback = jsFunctionCallback;
                        StringBuilder sb = new StringBuilder("compress failed ");
                        sb.append(e.getMessage());
                        ajxModuleArchive.callbackToJs(jsFunctionCallback, new InternalJsException(sb.toString()));
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void callbackToJs(JsFunctionCallback jsFunctionCallback, Object obj) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(obj);
        }
    }

    public void decompress(String str, String str2, String str3, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || !str2.startsWith(FILE_SCHEME) || !str3.startsWith(FILE_SCHEME)) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(Boolean.FALSE);
            }
            return;
        }
        final String substring = str2.substring(FILE_SCHEME_LEN);
        final String substring2 = str3.substring(FILE_SCHEME_LEN);
        ahn b = ahn.b();
        final JsFunctionCallback jsFunctionCallback2 = jsFunctionCallback;
        final String str4 = str3;
        AnonymousClass2 r0 = new Runnable() {
            /* JADX INFO: finally extract failed */
            public void run() {
                JsFunctionCallback jsFunctionCallback;
                Object[] objArr;
                try {
                    ahf.a(substring, substring2);
                    if (jsFunctionCallback2 != null) {
                        jsFunctionCallback = jsFunctionCallback2;
                        objArr = new Object[]{Boolean.TRUE, str4};
                        jsFunctionCallback.callback(objArr);
                    }
                } catch (Exception unused) {
                    if (jsFunctionCallback2 != null) {
                        jsFunctionCallback = jsFunctionCallback2;
                        objArr = new Object[]{Boolean.FALSE, str4};
                    }
                } catch (Throwable th) {
                    if (jsFunctionCallback2 != null) {
                        jsFunctionCallback2.callback(Boolean.FALSE, str4);
                    }
                    throw th;
                }
            }
        };
        b.execute(r0);
    }
}
