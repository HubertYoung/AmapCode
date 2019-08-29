package com.ali.auth.third.core.registry;

import java.util.Map;

public interface ServiceRegistration {
    void setProperties(Map<String, String> map);

    void unregister();
}
