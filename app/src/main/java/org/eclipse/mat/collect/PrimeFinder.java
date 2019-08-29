package org.eclipse.mat.collect;

class PrimeFinder {
    PrimeFinder() {
    }

    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:29)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    public static int findNextPrime(int r5) {
        /*
            r0 = 0
            r1 = r5
            r5 = 0
        L_0x0003:
            if (r5 != 0) goto L_0x001d
            int r1 = r1 + 1
            double r2 = (double) r1
            double r2 = java.lang.Math.sqrt(r2)
            int r5 = (int) r2
            r2 = 2
            r3 = 1
        L_0x000f:
            if (r2 > r5) goto L_0x001b
            int r4 = r1 / r2
            int r4 = r4 * r2
            if (r4 != r1) goto L_0x0018
            r3 = 0
        L_0x0018:
            int r2 = r2 + 1
            goto L_0x000f
        L_0x001b:
            r5 = r3
            goto L_0x0003
        L_0x001d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.collect.PrimeFinder.findNextPrime(int):int");
    }

    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:29)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    public static int findPrevPrime(int r5) {
        /*
            r0 = 0
            r1 = r5
            r5 = 0
        L_0x0003:
            if (r5 != 0) goto L_0x001d
            int r1 = r1 + -1
            double r2 = (double) r1
            double r2 = java.lang.Math.sqrt(r2)
            int r5 = (int) r2
            r2 = 2
            r3 = 1
        L_0x000f:
            if (r2 > r5) goto L_0x001b
            int r4 = r1 / r2
            int r4 = r4 * r2
            if (r4 != r1) goto L_0x0018
            r3 = 0
        L_0x0018:
            int r2 = r2 + 1
            goto L_0x000f
        L_0x001b:
            r5 = r3
            goto L_0x0003
        L_0x001d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.collect.PrimeFinder.findPrevPrime(int):int");
    }
}
