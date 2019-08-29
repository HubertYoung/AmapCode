package com.autonavi.bundle.msgbox.ajx;

import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IMessageBoxService {
    void a(awm awm, String str);

    void a(String str);

    void a(String... strArr);

    void b(String... strArr);
}
