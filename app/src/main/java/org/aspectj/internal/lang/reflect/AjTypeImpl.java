package org.aspectj.internal.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.aspectj.internal.lang.annotation.ajcDeclareEoW;
import org.aspectj.internal.lang.annotation.ajcDeclareParents;
import org.aspectj.internal.lang.annotation.ajcDeclarePrecedence;
import org.aspectj.internal.lang.annotation.ajcDeclareSoft;
import org.aspectj.internal.lang.annotation.ajcITD;
import org.aspectj.internal.lang.annotation.ajcPrivileged;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareError;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.DeclareWarning;
import org.aspectj.lang.reflect.Advice;
import org.aspectj.lang.reflect.AdviceKind;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.AjTypeSystem;
import org.aspectj.lang.reflect.DeclareAnnotation;
import org.aspectj.lang.reflect.DeclareErrorOrWarning;
import org.aspectj.lang.reflect.DeclarePrecedence;
import org.aspectj.lang.reflect.DeclareSoft;
import org.aspectj.lang.reflect.InterTypeConstructorDeclaration;
import org.aspectj.lang.reflect.InterTypeFieldDeclaration;
import org.aspectj.lang.reflect.InterTypeMethodDeclaration;
import org.aspectj.lang.reflect.NoSuchAdviceException;
import org.aspectj.lang.reflect.NoSuchPointcutException;
import org.aspectj.lang.reflect.PerClause;
import org.aspectj.lang.reflect.PerClauseKind;
import org.aspectj.lang.reflect.Pointcut;

public class AjTypeImpl<T> implements AjType<T> {
    private Class<T> a;
    private Pointcut[] b = null;
    private Pointcut[] c = null;
    private Advice[] d = null;
    private Advice[] e = null;
    private InterTypeMethodDeclaration[] f = null;
    private InterTypeMethodDeclaration[] g = null;
    private InterTypeFieldDeclaration[] h = null;
    private InterTypeFieldDeclaration[] i = null;
    private InterTypeConstructorDeclaration[] j = null;
    private InterTypeConstructorDeclaration[] k = null;

    public AjTypeImpl(Class<T> fromClass) {
        this.a = fromClass;
    }

    public String getName() {
        return this.a.getName();
    }

    public Package getPackage() {
        return this.a.getPackage();
    }

    public AjType<?>[] getInterfaces() {
        return a((Class<?>[]) this.a.getInterfaces());
    }

    public int getModifiers() {
        return this.a.getModifiers();
    }

    public Class<T> getJavaClass() {
        return this.a;
    }

    public AjType<? super T> getSupertype() {
        Class superclass = this.a.getSuperclass();
        if (superclass == null) {
            return null;
        }
        return new AjTypeImpl(superclass);
    }

    public Type getGenericSupertype() {
        return this.a.getGenericSuperclass();
    }

    public Method getEnclosingMethod() {
        return this.a.getEnclosingMethod();
    }

    public Constructor getEnclosingConstructor() {
        return this.a.getEnclosingConstructor();
    }

    public AjType<?> getEnclosingType() {
        Class enc = this.a.getEnclosingClass();
        if (enc != null) {
            return new AjTypeImpl(enc);
        }
        return null;
    }

    public AjType<?> getDeclaringType() {
        Class dec = this.a.getDeclaringClass();
        if (dec != null) {
            return new AjTypeImpl(dec);
        }
        return null;
    }

    public PerClause getPerClause() {
        if (!isAspect()) {
            return null;
        }
        String perClause = ((Aspect) this.a.getAnnotation(Aspect.class)).value();
        if (perClause.equals("")) {
            if (getSupertype().isAspect()) {
                return getSupertype().getPerClause();
            }
            return new PerClauseImpl(PerClauseKind.SINGLETON);
        } else if (perClause.startsWith("perthis(")) {
            return new PointcutBasedPerClauseImpl(PerClauseKind.PERTHIS, perClause.substring(8, perClause.length() - 1));
        } else {
            if (perClause.startsWith("pertarget(")) {
                return new PointcutBasedPerClauseImpl(PerClauseKind.PERTARGET, perClause.substring(10, perClause.length() - 1));
            }
            if (perClause.startsWith("percflow(")) {
                return new PointcutBasedPerClauseImpl(PerClauseKind.PERCFLOW, perClause.substring(9, perClause.length() - 1));
            }
            if (perClause.startsWith("percflowbelow(")) {
                return new PointcutBasedPerClauseImpl(PerClauseKind.PERCFLOWBELOW, perClause.substring(14, perClause.length() - 1));
            }
            if (perClause.startsWith("pertypewithin")) {
                return new TypePatternBasedPerClauseImpl(PerClauseKind.PERTYPEWITHIN, perClause.substring(14, perClause.length() - 1));
            }
            throw new IllegalStateException("Per-clause not recognized: " + perClause);
        }
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return this.a.isAnnotationPresent(annotationType);
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return this.a.getAnnotation(annotationType);
    }

    public Annotation[] getAnnotations() {
        return this.a.getAnnotations();
    }

    public Annotation[] getDeclaredAnnotations() {
        return this.a.getDeclaredAnnotations();
    }

    public AjType<?>[] getAjTypes() {
        return a((Class<?>[]) this.a.getClasses());
    }

    public AjType<?>[] getDeclaredAjTypes() {
        return a((Class<?>[]) this.a.getDeclaredClasses());
    }

    public Constructor getConstructor(AjType<?>... parameterTypes) {
        return this.a.getConstructor(a(parameterTypes));
    }

    public Constructor[] getConstructors() {
        return this.a.getConstructors();
    }

    public Constructor getDeclaredConstructor(AjType<?>... parameterTypes) {
        return this.a.getDeclaredConstructor(a(parameterTypes));
    }

    public Constructor[] getDeclaredConstructors() {
        return this.a.getDeclaredConstructors();
    }

    public Field getDeclaredField(String name) {
        Field f2 = this.a.getDeclaredField(name);
        if (!f2.getName().startsWith("ajc$")) {
            return f2;
        }
        throw new NoSuchFieldException(name);
    }

    public Field[] getDeclaredFields() {
        Field[] fields = this.a.getDeclaredFields();
        List filteredFields = new ArrayList();
        for (Field field : fields) {
            if (!field.getName().startsWith("ajc$") && !field.isAnnotationPresent(DeclareWarning.class) && !field.isAnnotationPresent(DeclareError.class)) {
                filteredFields.add(field);
            }
        }
        Field[] ret = new Field[filteredFields.size()];
        filteredFields.toArray(ret);
        return ret;
    }

    public Field getField(String name) {
        Field f2 = this.a.getField(name);
        if (!f2.getName().startsWith("ajc$")) {
            return f2;
        }
        throw new NoSuchFieldException(name);
    }

    public Field[] getFields() {
        Field[] fields = this.a.getFields();
        List filteredFields = new ArrayList();
        for (Field field : fields) {
            if (!field.getName().startsWith("ajc$") && !field.isAnnotationPresent(DeclareWarning.class) && !field.isAnnotationPresent(DeclareError.class)) {
                filteredFields.add(field);
            }
        }
        Field[] ret = new Field[filteredFields.size()];
        filteredFields.toArray(ret);
        return ret;
    }

    public Method getDeclaredMethod(String name, AjType<?>... parameterTypes) {
        Method m = this.a.getDeclaredMethod(name, a(parameterTypes));
        if (a(m)) {
            return m;
        }
        throw new NoSuchMethodException(name);
    }

    public Method getMethod(String name, AjType<?>... parameterTypes) {
        Method m = this.a.getMethod(name, a(parameterTypes));
        if (a(m)) {
            return m;
        }
        throw new NoSuchMethodException(name);
    }

    public Method[] getDeclaredMethods() {
        Method[] methods = this.a.getDeclaredMethods();
        List filteredMethods = new ArrayList();
        for (Method method : methods) {
            if (a(method)) {
                filteredMethods.add(method);
            }
        }
        Method[] ret = new Method[filteredMethods.size()];
        filteredMethods.toArray(ret);
        return ret;
    }

    public Method[] getMethods() {
        Method[] methods = this.a.getMethods();
        List filteredMethods = new ArrayList();
        for (Method method : methods) {
            if (a(method)) {
                filteredMethods.add(method);
            }
        }
        Method[] ret = new Method[filteredMethods.size()];
        filteredMethods.toArray(ret);
        return ret;
    }

    private static boolean a(Method method) {
        if (method.getName().startsWith("ajc$")) {
            return false;
        }
        if (method.getAnnotations().length == 0) {
            return true;
        }
        if (method.isAnnotationPresent(org.aspectj.lang.annotation.Pointcut.class) || method.isAnnotationPresent(Before.class) || method.isAnnotationPresent(After.class) || method.isAnnotationPresent(AfterReturning.class) || method.isAnnotationPresent(AfterThrowing.class) || method.isAnnotationPresent(Around.class)) {
            return false;
        }
        return true;
    }

    public Pointcut getDeclaredPointcut(String name) {
        Pointcut[] declaredPointcuts;
        for (Pointcut pc : getDeclaredPointcuts()) {
            if (pc.getName().equals(name)) {
                return pc;
            }
        }
        throw new NoSuchPointcutException(name);
    }

    public Pointcut getPointcut(String name) {
        Pointcut[] pointcuts;
        for (Pointcut pc : getPointcuts()) {
            if (pc.getName().equals(name)) {
                return pc;
            }
        }
        throw new NoSuchPointcutException(name);
    }

    public Pointcut[] getDeclaredPointcuts() {
        if (this.b != null) {
            return this.b;
        }
        List pointcuts = new ArrayList();
        for (Method b2 : this.a.getDeclaredMethods()) {
            Pointcut pc = b(b2);
            if (pc != null) {
                pointcuts.add(pc);
            }
        }
        Pointcut[] ret = new Pointcut[pointcuts.size()];
        pointcuts.toArray(ret);
        this.b = ret;
        return ret;
    }

    public Pointcut[] getPointcuts() {
        if (this.c != null) {
            return this.c;
        }
        List pcuts = new ArrayList();
        for (Method b2 : this.a.getMethods()) {
            Pointcut pc = b(b2);
            if (pc != null) {
                pcuts.add(pc);
            }
        }
        Pointcut[] ret = new Pointcut[pcuts.size()];
        pcuts.toArray(ret);
        this.c = ret;
        return ret;
    }

    private static Pointcut b(Method method) {
        org.aspectj.lang.annotation.Pointcut pcAnn = (org.aspectj.lang.annotation.Pointcut) method.getAnnotation(org.aspectj.lang.annotation.Pointcut.class);
        if (pcAnn == null) {
            return null;
        }
        String name = method.getName();
        if (name.startsWith("ajc$")) {
            name = name.substring(name.indexOf("$$") + 2, name.length());
            int nextDollar = name.indexOf("$");
            if (nextDollar != -1) {
                name = name.substring(0, nextDollar);
            }
        }
        return new PointcutImpl(name, pcAnn.value(), method, AjTypeSystem.getAjType(method.getDeclaringClass()), pcAnn.argNames());
    }

    public Advice[] getDeclaredAdvice(AdviceKind... ofType) {
        Set types;
        if (ofType.length == 0) {
            types = EnumSet.allOf(AdviceKind.class);
        } else {
            types = EnumSet.noneOf(AdviceKind.class);
            types.addAll(Arrays.asList(ofType));
        }
        return a(types);
    }

    public Advice[] getAdvice(AdviceKind... ofType) {
        Set types;
        if (ofType.length == 0) {
            types = EnumSet.allOf(AdviceKind.class);
        } else {
            types = EnumSet.noneOf(AdviceKind.class);
            types.addAll(Arrays.asList(ofType));
        }
        return b(types);
    }

    private Advice[] a(Set ofAdviceTypes) {
        Advice[] adviceArr;
        if (this.d == null) {
            a();
        }
        List adviceList = new ArrayList();
        for (Advice a2 : this.d) {
            if (ofAdviceTypes.contains(a2.getKind())) {
                adviceList.add(a2);
            }
        }
        Advice[] ret = new Advice[adviceList.size()];
        adviceList.toArray(ret);
        return ret;
    }

    private void a() {
        Method[] methods = this.a.getDeclaredMethods();
        List adviceList = new ArrayList();
        for (Method c2 : methods) {
            Advice advice = c(c2);
            if (advice != null) {
                adviceList.add(advice);
            }
        }
        this.d = new Advice[adviceList.size()];
        adviceList.toArray(this.d);
    }

    private Advice[] b(Set ofAdviceTypes) {
        Advice[] adviceArr;
        if (this.e == null) {
            b();
        }
        List adviceList = new ArrayList();
        for (Advice a2 : this.e) {
            if (ofAdviceTypes.contains(a2.getKind())) {
                adviceList.add(a2);
            }
        }
        Advice[] ret = new Advice[adviceList.size()];
        adviceList.toArray(ret);
        return ret;
    }

    private void b() {
        Method[] methods = this.a.getMethods();
        List adviceList = new ArrayList();
        for (Method c2 : methods) {
            Advice advice = c(c2);
            if (advice != null) {
                adviceList.add(advice);
            }
        }
        this.e = new Advice[adviceList.size()];
        adviceList.toArray(this.e);
    }

    public Advice getAdvice(String name) {
        Advice[] adviceArr;
        if (name.equals("")) {
            throw new IllegalArgumentException("use getAdvice(AdviceType...) instead for un-named advice");
        }
        if (this.e == null) {
            b();
        }
        for (Advice a2 : this.e) {
            if (a2.getName().equals(name)) {
                return a2;
            }
        }
        throw new NoSuchAdviceException(name);
    }

    public Advice getDeclaredAdvice(String name) {
        Advice[] adviceArr;
        if (name.equals("")) {
            throw new IllegalArgumentException("use getAdvice(AdviceType...) instead for un-named advice");
        }
        if (this.d == null) {
            a();
        }
        for (Advice a2 : this.d) {
            if (a2.getName().equals(name)) {
                return a2;
            }
        }
        throw new NoSuchAdviceException(name);
    }

    private static Advice c(Method method) {
        if (method.getAnnotations().length == 0) {
            return null;
        }
        Before beforeAnn = (Before) method.getAnnotation(Before.class);
        if (beforeAnn != null) {
            return new AdviceImpl(method, beforeAnn.value(), AdviceKind.BEFORE);
        }
        After afterAnn = (After) method.getAnnotation(After.class);
        if (afterAnn != null) {
            return new AdviceImpl(method, afterAnn.value(), AdviceKind.AFTER);
        }
        AfterReturning afterReturningAnn = (AfterReturning) method.getAnnotation(AfterReturning.class);
        if (afterReturningAnn != null) {
            String pcExpr = afterReturningAnn.pointcut();
            if (pcExpr.equals("")) {
                pcExpr = afterReturningAnn.value();
            }
            return new AdviceImpl(method, pcExpr, AdviceKind.AFTER_RETURNING, afterReturningAnn.returning());
        }
        AfterThrowing afterThrowingAnn = (AfterThrowing) method.getAnnotation(AfterThrowing.class);
        if (afterThrowingAnn != null) {
            String pcExpr2 = afterThrowingAnn.pointcut();
            if (pcExpr2 == null) {
                pcExpr2 = afterThrowingAnn.value();
            }
            return new AdviceImpl(method, pcExpr2, AdviceKind.AFTER_THROWING, afterThrowingAnn.throwing());
        }
        Around aroundAnn = (Around) method.getAnnotation(Around.class);
        if (aroundAnn != null) {
            return new AdviceImpl(method, aroundAnn.value(), AdviceKind.AROUND);
        }
        return null;
    }

    public InterTypeMethodDeclaration getDeclaredITDMethod(String name, AjType<?> target, AjType<?>... parameterTypes) {
        InterTypeMethodDeclaration[] declaredITDMethods;
        for (InterTypeMethodDeclaration itdm : getDeclaredITDMethods()) {
            try {
                if (itdm.getName().equals(name) && itdm.getTargetType().equals(target)) {
                    AjType[] ptypes = itdm.getParameterTypes();
                    if (ptypes.length == parameterTypes.length) {
                        int i2 = 0;
                        while (i2 < ptypes.length) {
                            if (ptypes[i2].equals(parameterTypes[i2])) {
                                i2++;
                            }
                        }
                        return itdm;
                    }
                    continue;
                }
            } catch (ClassNotFoundException e2) {
            }
        }
        throw new NoSuchMethodException(name);
    }

    public InterTypeMethodDeclaration[] getDeclaredITDMethods() {
        Method[] declaredMethods;
        if (this.f == null) {
            List itdms = new ArrayList();
            for (Method m : this.a.getDeclaredMethods()) {
                if (m.getName().contains("ajc$interMethodDispatch1$") && m.isAnnotationPresent(ajcITD.class)) {
                    ajcITD ann = (ajcITD) m.getAnnotation(ajcITD.class);
                    itdms.add(new InterTypeMethodDeclarationImpl(this, ann.targetType(), ann.modifiers(), ann.name(), m));
                }
            }
            a(itdms, false);
            this.f = new InterTypeMethodDeclaration[itdms.size()];
            itdms.toArray(this.f);
        }
        return this.f;
    }

    public InterTypeMethodDeclaration getITDMethod(String name, AjType<?> target, AjType<?>... parameterTypes) {
        InterTypeMethodDeclaration[] iTDMethods;
        for (InterTypeMethodDeclaration itdm : getITDMethods()) {
            try {
                if (itdm.getName().equals(name) && itdm.getTargetType().equals(target)) {
                    AjType[] ptypes = itdm.getParameterTypes();
                    if (ptypes.length == parameterTypes.length) {
                        int i2 = 0;
                        while (i2 < ptypes.length) {
                            if (ptypes[i2].equals(parameterTypes[i2])) {
                                i2++;
                            }
                        }
                        return itdm;
                    }
                    continue;
                }
            } catch (ClassNotFoundException e2) {
            }
        }
        throw new NoSuchMethodException(name);
    }

    public InterTypeMethodDeclaration[] getITDMethods() {
        Method[] declaredMethods;
        if (this.g == null) {
            List itdms = new ArrayList();
            for (Method m : this.a.getDeclaredMethods()) {
                if (m.getName().contains("ajc$interMethod$") && m.isAnnotationPresent(ajcITD.class)) {
                    ajcITD ann = (ajcITD) m.getAnnotation(ajcITD.class);
                    if (Modifier.isPublic(ann.modifiers())) {
                        itdms.add(new InterTypeMethodDeclarationImpl(this, ann.targetType(), ann.modifiers(), ann.name(), m));
                    }
                }
            }
            a(itdms, true);
            this.g = new InterTypeMethodDeclaration[itdms.size()];
            itdms.toArray(this.g);
        }
        return this.g;
    }

    private void a(List<InterTypeMethodDeclaration> toList, boolean publicOnly) {
        Field[] declaredFields;
        Method[] declaredMethods;
        if (isAspect()) {
            for (Field f2 : this.a.getDeclaredFields()) {
                if (f2.getType().isInterface() && f2.isAnnotationPresent(DeclareParents.class)) {
                    Class decPAnnClass = DeclareParents.class;
                    if (((DeclareParents) f2.getAnnotation(decPAnnClass)).defaultImpl() != decPAnnClass) {
                        for (Method itdM : f2.getType().getDeclaredMethods()) {
                            if (Modifier.isPublic(itdM.getModifiers()) || !publicOnly) {
                                toList.add(new InterTypeMethodDeclarationImpl(this, AjTypeSystem.getAjType(f2.getType()), itdM, 1));
                            }
                        }
                    }
                }
            }
        }
    }

    public InterTypeConstructorDeclaration getDeclaredITDConstructor(AjType<?> target, AjType<?>... parameterTypes) {
        InterTypeConstructorDeclaration[] declaredITDConstructors;
        for (InterTypeConstructorDeclaration itdc : getDeclaredITDConstructors()) {
            try {
                if (itdc.getTargetType().equals(target)) {
                    AjType[] ptypes = itdc.getParameterTypes();
                    if (ptypes.length == parameterTypes.length) {
                        int i2 = 0;
                        while (i2 < ptypes.length) {
                            if (ptypes[i2].equals(parameterTypes[i2])) {
                                i2++;
                            }
                        }
                        return itdc;
                    }
                    continue;
                } else {
                    continue;
                }
            } catch (ClassNotFoundException e2) {
            }
        }
        throw new NoSuchMethodException();
    }

    public InterTypeConstructorDeclaration[] getDeclaredITDConstructors() {
        Method[] declaredMethods;
        if (this.k == null) {
            List itdcs = new ArrayList();
            for (Method m : this.a.getDeclaredMethods()) {
                if (m.getName().contains("ajc$postInterConstructor") && m.isAnnotationPresent(ajcITD.class)) {
                    ajcITD ann = (ajcITD) m.getAnnotation(ajcITD.class);
                    itdcs.add(new InterTypeConstructorDeclarationImpl(this, ann.targetType(), ann.modifiers(), m));
                }
            }
            this.k = new InterTypeConstructorDeclaration[itdcs.size()];
            itdcs.toArray(this.k);
        }
        return this.k;
    }

    public InterTypeConstructorDeclaration getITDConstructor(AjType<?> target, AjType<?>... parameterTypes) {
        InterTypeConstructorDeclaration[] iTDConstructors;
        for (InterTypeConstructorDeclaration itdc : getITDConstructors()) {
            try {
                if (itdc.getTargetType().equals(target)) {
                    AjType[] ptypes = itdc.getParameterTypes();
                    if (ptypes.length == parameterTypes.length) {
                        int i2 = 0;
                        while (i2 < ptypes.length) {
                            if (ptypes[i2].equals(parameterTypes[i2])) {
                                i2++;
                            }
                        }
                        return itdc;
                    }
                    continue;
                } else {
                    continue;
                }
            } catch (ClassNotFoundException e2) {
            }
        }
        throw new NoSuchMethodException();
    }

    public InterTypeConstructorDeclaration[] getITDConstructors() {
        Method[] methods;
        if (this.j == null) {
            List itdcs = new ArrayList();
            for (Method m : this.a.getMethods()) {
                if (m.getName().contains("ajc$postInterConstructor") && m.isAnnotationPresent(ajcITD.class)) {
                    ajcITD ann = (ajcITD) m.getAnnotation(ajcITD.class);
                    if (Modifier.isPublic(ann.modifiers())) {
                        itdcs.add(new InterTypeConstructorDeclarationImpl(this, ann.targetType(), ann.modifiers(), m));
                    }
                }
            }
            this.j = new InterTypeConstructorDeclaration[itdcs.size()];
            itdcs.toArray(this.j);
        }
        return this.j;
    }

    public InterTypeFieldDeclaration getDeclaredITDField(String name, AjType<?> target) {
        InterTypeFieldDeclaration[] declaredITDFields;
        for (InterTypeFieldDeclaration itdf : getDeclaredITDFields()) {
            if (itdf.getName().equals(name)) {
                try {
                    if (itdf.getTargetType().equals(target)) {
                        return itdf;
                    }
                } catch (ClassNotFoundException e2) {
                }
            }
        }
        throw new NoSuchFieldException(name);
    }

    public InterTypeFieldDeclaration[] getDeclaredITDFields() {
        Method[] declaredMethods;
        List itdfs = new ArrayList();
        if (this.h == null) {
            for (Method m : this.a.getDeclaredMethods()) {
                if (m.isAnnotationPresent(ajcITD.class) && m.getName().contains("ajc$interFieldInit")) {
                    ajcITD ann = (ajcITD) m.getAnnotation(ajcITD.class);
                    try {
                        Method dispatch = this.a.getDeclaredMethod(m.getName().replace("FieldInit", "FieldGetDispatch"), m.getParameterTypes());
                        itdfs.add(new InterTypeFieldDeclarationImpl(this, ann.targetType(), ann.modifiers(), ann.name(), AjTypeSystem.getAjType(dispatch.getReturnType()), dispatch.getGenericReturnType()));
                    } catch (NoSuchMethodException e2) {
                        throw new IllegalStateException("Can't find field get dispatch method for " + m.getName());
                    }
                }
            }
            this.h = new InterTypeFieldDeclaration[itdfs.size()];
            itdfs.toArray(this.h);
        }
        return this.h;
    }

    public InterTypeFieldDeclaration getITDField(String name, AjType<?> target) {
        InterTypeFieldDeclaration[] iTDFields;
        for (InterTypeFieldDeclaration itdf : getITDFields()) {
            if (itdf.getName().equals(name)) {
                try {
                    if (itdf.getTargetType().equals(target)) {
                        return itdf;
                    }
                } catch (ClassNotFoundException e2) {
                }
            }
        }
        throw new NoSuchFieldException(name);
    }

    public InterTypeFieldDeclaration[] getITDFields() {
        Method[] methods;
        List itdfs = new ArrayList();
        if (this.i == null) {
            for (Method m : this.a.getMethods()) {
                if (m.isAnnotationPresent(ajcITD.class)) {
                    ajcITD ann = (ajcITD) m.getAnnotation(ajcITD.class);
                    if (m.getName().contains("ajc$interFieldInit") && Modifier.isPublic(ann.modifiers())) {
                        try {
                            Method dispatch = m.getDeclaringClass().getDeclaredMethod(m.getName().replace("FieldInit", "FieldGetDispatch"), m.getParameterTypes());
                            itdfs.add(new InterTypeFieldDeclarationImpl(this, ann.targetType(), ann.modifiers(), ann.name(), AjTypeSystem.getAjType(dispatch.getReturnType()), dispatch.getGenericReturnType()));
                        } catch (NoSuchMethodException e2) {
                            throw new IllegalStateException("Can't find field get dispatch method for " + m.getName());
                        }
                    }
                }
            }
            this.i = new InterTypeFieldDeclaration[itdfs.size()];
            itdfs.toArray(this.i);
        }
        return this.i;
    }

    public DeclareErrorOrWarning[] getDeclareErrorOrWarnings() {
        Field[] declaredFields;
        Method[] declaredMethods;
        List deows = new ArrayList();
        for (Field field : this.a.getDeclaredFields()) {
            try {
                if (field.isAnnotationPresent(DeclareWarning.class)) {
                    DeclareWarning dw = (DeclareWarning) field.getAnnotation(DeclareWarning.class);
                    if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                        deows.add(new DeclareErrorOrWarningImpl(dw.value(), (String) field.get(null), false, this));
                    }
                } else if (field.isAnnotationPresent(DeclareError.class)) {
                    DeclareError de2 = (DeclareError) field.getAnnotation(DeclareError.class);
                    if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                        deows.add(new DeclareErrorOrWarningImpl(de2.value(), (String) field.get(null), true, this));
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException e2) {
            }
        }
        for (Method method : this.a.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ajcDeclareEoW.class)) {
                ajcDeclareEoW deowAnn = (ajcDeclareEoW) method.getAnnotation(ajcDeclareEoW.class);
                deows.add(new DeclareErrorOrWarningImpl(deowAnn.pointcut(), deowAnn.message(), deowAnn.isError(), this));
            }
        }
        DeclareErrorOrWarning[] ret = new DeclareErrorOrWarning[deows.size()];
        deows.toArray(ret);
        return ret;
    }

    public org.aspectj.lang.reflect.DeclareParents[] getDeclareParents() {
        Method[] declaredMethods;
        List decps = new ArrayList();
        for (Method method : this.a.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ajcDeclareParents.class)) {
                ajcDeclareParents decPAnn = (ajcDeclareParents) method.getAnnotation(ajcDeclareParents.class);
                decps.add(new DeclareParentsImpl(decPAnn.targetTypePattern(), decPAnn.parentTypes(), decPAnn.isExtends(), this));
            }
        }
        a(decps);
        if (getSupertype().isAspect()) {
            decps.addAll(Arrays.asList(getSupertype().getDeclareParents()));
        }
        org.aspectj.lang.reflect.DeclareParents[] ret = new org.aspectj.lang.reflect.DeclareParents[decps.size()];
        decps.toArray(ret);
        return ret;
    }

    private void a(List<org.aspectj.lang.reflect.DeclareParents> toList) {
        Field[] declaredFields;
        for (Field f2 : this.a.getDeclaredFields()) {
            if (f2.isAnnotationPresent(DeclareParents.class) && f2.getType().isInterface()) {
                toList.add(new DeclareParentsImpl(((DeclareParents) f2.getAnnotation(DeclareParents.class)).value(), f2.getType().getName(), false, this));
            }
        }
    }

    public DeclareSoft[] getDeclareSofts() {
        Method[] declaredMethods;
        List decs = new ArrayList();
        for (Method method : this.a.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ajcDeclareSoft.class)) {
                ajcDeclareSoft decSAnn = (ajcDeclareSoft) method.getAnnotation(ajcDeclareSoft.class);
                decs.add(new DeclareSoftImpl(this, decSAnn.pointcut(), decSAnn.exceptionType()));
            }
        }
        if (getSupertype().isAspect()) {
            decs.addAll(Arrays.asList(getSupertype().getDeclareSofts()));
        }
        DeclareSoft[] ret = new DeclareSoft[decs.size()];
        decs.toArray(ret);
        return ret;
    }

    public DeclareAnnotation[] getDeclareAnnotations() {
        Method[] declaredMethods;
        List decAs = new ArrayList();
        for (Method method : this.a.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ajcDeclareAnnotation.class)) {
                ajcDeclareAnnotation decAnn = (ajcDeclareAnnotation) method.getAnnotation(ajcDeclareAnnotation.class);
                Annotation targetAnnotation = null;
                Annotation[] annotations = method.getAnnotations();
                int length = annotations.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    Annotation ann = annotations[i2];
                    if (ann.annotationType() != ajcDeclareAnnotation.class) {
                        targetAnnotation = ann;
                        break;
                    }
                    i2++;
                }
                decAs.add(new DeclareAnnotationImpl(this, decAnn.kind(), decAnn.pattern(), targetAnnotation, decAnn.annotation()));
            }
        }
        if (getSupertype().isAspect()) {
            decAs.addAll(Arrays.asList(getSupertype().getDeclareAnnotations()));
        }
        DeclareAnnotation[] ret = new DeclareAnnotation[decAs.size()];
        decAs.toArray(ret);
        return ret;
    }

    public DeclarePrecedence[] getDeclarePrecedence() {
        Method[] declaredMethods;
        List decps = new ArrayList();
        if (this.a.isAnnotationPresent(org.aspectj.lang.annotation.DeclarePrecedence.class)) {
            decps.add(new DeclarePrecedenceImpl(((org.aspectj.lang.annotation.DeclarePrecedence) this.a.getAnnotation(org.aspectj.lang.annotation.DeclarePrecedence.class)).value(), this));
        }
        for (Method method : this.a.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ajcDeclarePrecedence.class)) {
                decps.add(new DeclarePrecedenceImpl(((ajcDeclarePrecedence) method.getAnnotation(ajcDeclarePrecedence.class)).value(), this));
            }
        }
        if (getSupertype().isAspect()) {
            decps.addAll(Arrays.asList(getSupertype().getDeclarePrecedence()));
        }
        DeclarePrecedence[] ret = new DeclarePrecedence[decps.size()];
        decps.toArray(ret);
        return ret;
    }

    public T[] getEnumConstants() {
        return this.a.getEnumConstants();
    }

    public TypeVariable<Class<T>>[] getTypeParameters() {
        return this.a.getTypeParameters();
    }

    public boolean isEnum() {
        return this.a.isEnum();
    }

    public boolean isInstance(Object o) {
        return this.a.isInstance(o);
    }

    public boolean isInterface() {
        return this.a.isInterface();
    }

    public boolean isLocalClass() {
        return this.a.isLocalClass() && !isAspect();
    }

    public boolean isMemberClass() {
        return this.a.isMemberClass() && !isAspect();
    }

    public boolean isArray() {
        return this.a.isArray();
    }

    public boolean isPrimitive() {
        return this.a.isPrimitive();
    }

    public boolean isAspect() {
        return this.a.getAnnotation(Aspect.class) != null;
    }

    public boolean isMemberAspect() {
        return this.a.isMemberClass() && isAspect();
    }

    public boolean isPrivileged() {
        return isAspect() && this.a.isAnnotationPresent(ajcPrivileged.class);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AjTypeImpl)) {
            return false;
        }
        return ((AjTypeImpl) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    private static AjType<?>[] a(Class<?>[] classes) {
        AjType[] ajtypes = new AjType[classes.length];
        for (int i2 = 0; i2 < ajtypes.length; i2++) {
            ajtypes[i2] = AjTypeSystem.getAjType(classes[i2]);
        }
        return ajtypes;
    }

    private static Class<?>[] a(AjType<?>[] ajTypes) {
        Class[] classes = new Class[ajTypes.length];
        for (int i2 = 0; i2 < classes.length; i2++) {
            classes[i2] = ajTypes[i2].getJavaClass();
        }
        return classes;
    }

    public String toString() {
        return getName();
    }
}
