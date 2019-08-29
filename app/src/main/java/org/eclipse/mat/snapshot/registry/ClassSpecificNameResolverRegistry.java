package org.eclipse.mat.snapshot.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.inspections.CommonNameResolver.AccessibleObjectResolver;
import org.eclipse.mat.inspections.CommonNameResolver.ByteArrayResolver;
import org.eclipse.mat.inspections.CommonNameResolver.CharArrayResolver;
import org.eclipse.mat.inspections.CommonNameResolver.ConstructorResolver;
import org.eclipse.mat.inspections.CommonNameResolver.FieldResolver;
import org.eclipse.mat.inspections.CommonNameResolver.MethodResolver;
import org.eclipse.mat.inspections.CommonNameResolver.StringBufferResolver;
import org.eclipse.mat.inspections.CommonNameResolver.StringResolver;
import org.eclipse.mat.inspections.CommonNameResolver.ThreadGroupResolver;
import org.eclipse.mat.inspections.CommonNameResolver.ThreadResolver;
import org.eclipse.mat.inspections.CommonNameResolver.URLResolver;
import org.eclipse.mat.inspections.CommonNameResolver.ValueResolver;
import org.eclipse.mat.snapshot.extension.IClassSpecificNameResolver;
import org.eclipse.mat.snapshot.extension.Subject;
import org.eclipse.mat.snapshot.extension.Subjects;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.util.MessageUtil;

public final class ClassSpecificNameResolverRegistry {
    private static ClassSpecificNameResolverRegistry instance;
    /* access modifiers changed from: private */
    public RegistryImpl registry = new RegistryImpl();

    static class RegistryImpl {
        private Map<String, IClassSpecificNameResolver> resolvers = new HashMap();

        /* access modifiers changed from: private */
        public String doResolve(IObject iObject) {
            try {
                for (IClass clazz = iObject.getClazz(); clazz != null; clazz = clazz.getSuperClass()) {
                    IClassSpecificNameResolver iClassSpecificNameResolver = this.resolvers.get(clazz.getName());
                    if (iClassSpecificNameResolver != null) {
                        return iClassSpecificNameResolver.resolve(iObject);
                    }
                }
                return null;
            } catch (RuntimeException e) {
                Logger.getLogger(ClassSpecificNameResolverRegistry.class.getName()).log(Level.SEVERE, MessageUtil.format(Messages.ClassSpecificNameResolverRegistry_ErrorMsg_DuringResolving, iObject.getTechnicalName()), e);
                return null;
            } catch (SnapshotException e2) {
                Logger.getLogger(ClassSpecificNameResolverRegistry.class.getName()).log(Level.SEVERE, MessageUtil.format(Messages.ClassSpecificNameResolverRegistry_ErrorMsg_DuringResolving, iObject.getTechnicalName()), e2);
                return null;
            }
        }

        public void registerResolver(IClassSpecificNameResolver iClassSpecificNameResolver) {
            String[] extractSubjects = extractSubjects(iClassSpecificNameResolver);
            if (extractSubjects != null && extractSubjects.length > 0) {
                for (String put : extractSubjects) {
                    ClassSpecificNameResolverRegistry.instance().registry.resolvers.put(put, iClassSpecificNameResolver);
                }
            }
        }

        private String[] extractSubjects(IClassSpecificNameResolver iClassSpecificNameResolver) {
            Subjects subjects = (Subjects) iClassSpecificNameResolver.getClass().getAnnotation(Subjects.class);
            if (subjects != null) {
                return subjects.value();
            }
            Subject subject = (Subject) iClassSpecificNameResolver.getClass().getAnnotation(Subject.class);
            if (subject == null) {
                return null;
            }
            return new String[]{subject.value()};
        }
    }

    static {
        ClassSpecificNameResolverRegistry classSpecificNameResolverRegistry = new ClassSpecificNameResolverRegistry();
        instance = classSpecificNameResolverRegistry;
        classSpecificNameResolverRegistry.registry.registerResolver(new StringResolver());
        instance.registry.registerResolver(new StringBufferResolver());
        instance.registry.registerResolver(new ThreadResolver());
        instance.registry.registerResolver(new ThreadGroupResolver());
        instance.registry.registerResolver(new ValueResolver());
        instance.registry.registerResolver(new CharArrayResolver());
        instance.registry.registerResolver(new ByteArrayResolver());
        instance.registry.registerResolver(new URLResolver());
        instance.registry.registerResolver(new AccessibleObjectResolver());
        instance.registry.registerResolver(new FieldResolver());
        instance.registry.registerResolver(new MethodResolver());
        instance.registry.registerResolver(new ConstructorResolver());
    }

    public static ClassSpecificNameResolverRegistry instance() {
        return instance;
    }

    private ClassSpecificNameResolverRegistry() {
    }

    public static String resolve(IObject iObject) {
        if (iObject != null) {
            return instance().registry.doResolve(iObject);
        }
        throw new NullPointerException(Messages.ClassSpecificNameResolverRegistry_Error_MissingObject.pattern);
    }
}
