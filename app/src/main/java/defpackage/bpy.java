package defpackage;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: bpy reason: default package */
/* compiled from: ParallelThreadPoolExecutor */
final class bpy implements b {
    private static String a = "ANet-ThreadPool";
    private ReentrantLock b = new ReentrantLock();
    private bqb c = new bqb();
    private a d;
    private bpz e;

    /* renamed from: bpy$a */
    /* compiled from: ParallelThreadPoolExecutor */
    class a extends ThreadPoolExecutor {
        final /* synthetic */ bpy a;

        /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
            jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 12, insn: 0x0007: MOVE  (r5 I:?[OBJECT, ARRAY]) = (r12 I:?[OBJECT, ARRAY]), block:B:0:0x0000
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:160)
            	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:132)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:55)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
            	at jadx.core.ProcessClass.process(ProcessClass.java:30)
            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
            */
        public a(
/*
Method generation error in method: bpy.a.<init>(bpy, int, int, java.util.concurrent.TimeUnit, java.util.concurrent.BlockingQueue, java.util.concurrent.ThreadFactory):void, dex: classes3.dex
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r8v0 ?
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:168)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:157)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:129)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:310)
        	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:261)
        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:224)
        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:109)
        	at jadx.core.codegen.ClassGen.addInnerClasses(ClassGen.java:236)
        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:223)
        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:109)
        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:75)
        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:32)
        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:20)
        	at jadx.core.ProcessClass.process(ProcessClass.java:36)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        
*/

        /* access modifiers changed from: protected */
        public final void afterExecute(Runnable runnable, Throwable th) {
            super.afterExecute(runnable, th);
            bpy.a(this.a, runnable);
        }
    }

    public bpy(String str, int[] iArr) {
        this.e = new bpz(iArr);
        int a2 = this.e.a();
        if (a2 <= 0) {
            throw new IllegalArgumentException("parallelNumber error!");
        }
        int i = a2;
        a aVar = new a(this, i, a2, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(a2, new Comparator<Runnable>() {
            public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
                Runnable runnable = (Runnable) obj;
                Runnable runnable2 = (Runnable) obj2;
                if (!(runnable instanceof c) || !(runnable2 instanceof c)) {
                    return 0;
                }
                return ((c) runnable2).b - ((c) runnable).b;
            }
        }), new defpackage.bqa.a(str));
        this.d = aVar;
        if (bpv.a(4)) {
            String str2 = a;
            StringBuilder sb = new StringBuilder("new ParallelThreadPoolExecutor, max poll size = ");
            sb.append(this.d.getMaximumPoolSize());
            bpv.c(str2, sb.toString());
        }
    }

    public final void a(Runnable runnable) {
        if (this.e == null) {
            throw new IllegalStateException("thread pool not init or has shutdown!");
        }
        this.b.lock();
        try {
            this.c.a.put((c) runnable);
            c b2 = this.c.b();
            if (b2 != null && !bpz.a(b2, this.e.b, this.e.a, "execute-")) {
                a(this.c.a());
            }
        } finally {
            this.b.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    public final void a() {
        bpv.c(a, "ParallelThreadPoolExecutor shutdown!");
        this.b.lock();
        try {
            this.c.a.clear();
            bpz bpz = this.e;
            bpz.a = null;
            bpz.b = null;
            this.b.unlock();
            this.d.shutdown();
        } catch (Throwable th) {
            this.b.unlock();
            throw th;
        }
    }

    private void a(c cVar) {
        if (cVar != null) {
            this.d.execute(cVar);
            this.e.a(cVar.b);
        }
    }

    static /* synthetic */ void a(bpy bpy, Runnable runnable) {
        bpy.b.lock();
        if (runnable != null) {
            try {
                if (runnable instanceof c) {
                    int i = ((c) runnable).b / 100;
                    int[] iArr = bpy.e.a;
                    iArr[i] = iArr[i] - 1;
                }
            } catch (Throwable th) {
                bpy.b.unlock();
                throw th;
            }
        }
        while (true) {
            c b2 = bpy.c.b();
            if (b2 == null || bpz.a(b2, bpy.e.b, bpy.e.a, "afterExecute-")) {
                bpy.b.unlock();
            } else {
                bpy.a(bpy.c.a());
            }
        }
        bpy.b.unlock();
    }
}
