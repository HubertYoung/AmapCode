package org.nanohttpd.protocols.http.tempfiles;

import com.alipay.mobile.aspect.AliAspectCenter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.internal.AroundClosure;
import org.aspectj.runtime.internal.Conversions;
import org.aspectj.runtime.reflect.Factory;
import org.nanohttpd.protocols.http.NanoHTTPD;

public class DefaultTempFile implements ITempFile {
    private static final /* synthetic */ StaticPart c = null;
    private final File a;
    private final OutputStream b = new FileOutputStream(this.a);

    public class AjcClosure1 extends AroundClosure {
        public AjcClosure1(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            return Conversions.booleanObject(((File) this.state[1]).delete());
        }
    }

    static {
        a();
    }

    private static /* synthetic */ void a() {
        Factory factory = new Factory("DefaultTempFile.java", DefaultTempFile.class);
        c = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "delete", (String) "java.io.File", (String) "", (String) "", (String) "", (String) "boolean"), 65);
    }

    public DefaultTempFile(File tempdir) {
        this.a = File.createTempFile("NanoHTTPD-", "", tempdir);
    }

    public void delete() {
        NanoHTTPD.safeClose(this.b);
        File file = this.a;
        JoinPoint makeJP = Factory.makeJP(c, this, file);
        if (!Conversions.booleanValue(AliAspectCenter.aspectOf().doAspect(new AjcClosure1(new Object[]{this, file, makeJP}).linkClosureAndJoinPoint(4112)))) {
            throw new Exception("could not delete temporary file: " + this.a.getAbsolutePath());
        }
    }

    public String getName() {
        return this.a.getAbsolutePath();
    }

    public OutputStream open() {
        return this.b;
    }
}
