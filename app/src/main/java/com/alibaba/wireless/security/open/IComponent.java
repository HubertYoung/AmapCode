package com.alibaba.wireless.security.open;

import com.alibaba.wireless.security.open.initialize.ISecurityGuardPlugin;

public interface IComponent {
    int init(ISecurityGuardPlugin iSecurityGuardPlugin, Object... objArr);
}
