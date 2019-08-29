package org.aspectj.lang.reflect;

import java.lang.reflect.Constructor;

public interface InitializerSignature extends CodeSignature {
    Constructor getInitializer();
}
