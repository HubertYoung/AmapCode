package org.aspectj.lang.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public interface AjType<T> extends AnnotatedElement, Type {
    Advice getAdvice(String str);

    Advice[] getAdvice(AdviceKind... adviceKindArr);

    AjType<?>[] getAjTypes();

    Constructor getConstructor(AjType<?>... ajTypeArr);

    Constructor[] getConstructors();

    DeclareAnnotation[] getDeclareAnnotations();

    DeclareErrorOrWarning[] getDeclareErrorOrWarnings();

    DeclareParents[] getDeclareParents();

    DeclarePrecedence[] getDeclarePrecedence();

    DeclareSoft[] getDeclareSofts();

    Advice getDeclaredAdvice(String str);

    Advice[] getDeclaredAdvice(AdviceKind... adviceKindArr);

    AjType<?>[] getDeclaredAjTypes();

    Constructor getDeclaredConstructor(AjType<?>... ajTypeArr);

    Constructor[] getDeclaredConstructors();

    Field getDeclaredField(String str);

    Field[] getDeclaredFields();

    InterTypeConstructorDeclaration getDeclaredITDConstructor(AjType<?> ajType, AjType<?>... ajTypeArr);

    InterTypeConstructorDeclaration[] getDeclaredITDConstructors();

    InterTypeFieldDeclaration getDeclaredITDField(String str, AjType<?> ajType);

    InterTypeFieldDeclaration[] getDeclaredITDFields();

    InterTypeMethodDeclaration getDeclaredITDMethod(String str, AjType<?> ajType, AjType<?>... ajTypeArr);

    InterTypeMethodDeclaration[] getDeclaredITDMethods();

    Method getDeclaredMethod(String str, AjType<?>... ajTypeArr);

    Method[] getDeclaredMethods();

    Pointcut getDeclaredPointcut(String str);

    Pointcut[] getDeclaredPointcuts();

    AjType<?> getDeclaringType();

    Constructor getEnclosingConstructor();

    Method getEnclosingMethod();

    AjType<?> getEnclosingType();

    T[] getEnumConstants();

    Field getField(String str);

    Field[] getFields();

    Type getGenericSupertype();

    InterTypeConstructorDeclaration getITDConstructor(AjType<?> ajType, AjType<?>... ajTypeArr);

    InterTypeConstructorDeclaration[] getITDConstructors();

    InterTypeFieldDeclaration getITDField(String str, AjType<?> ajType);

    InterTypeFieldDeclaration[] getITDFields();

    InterTypeMethodDeclaration getITDMethod(String str, AjType<?> ajType, AjType<?>... ajTypeArr);

    InterTypeMethodDeclaration[] getITDMethods();

    AjType<?>[] getInterfaces();

    Class<T> getJavaClass();

    Method getMethod(String str, AjType<?>... ajTypeArr);

    Method[] getMethods();

    int getModifiers();

    String getName();

    Package getPackage();

    PerClause getPerClause();

    Pointcut getPointcut(String str);

    Pointcut[] getPointcuts();

    AjType<?> getSupertype();

    TypeVariable<Class<T>>[] getTypeParameters();

    boolean isArray();

    boolean isAspect();

    boolean isEnum();

    boolean isInstance(Object obj);

    boolean isInterface();

    boolean isLocalClass();

    boolean isMemberAspect();

    boolean isMemberClass();

    boolean isPrimitive();

    boolean isPrivileged();
}
