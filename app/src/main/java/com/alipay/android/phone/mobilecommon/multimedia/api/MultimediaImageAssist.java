package com.alipay.android.phone.mobilecommon.multimedia.api;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public class MultimediaImageAssist {
    private static final Set<String> sBusinessSet = new HashSet(3);
    private static WeakReference<MultimediaImageService> sImageReference;

    public static void registerCommonMemBusiness(String business) {
        MultimediaImageService service = sImageReference != null ? (MultimediaImageService) sImageReference.get() : null;
        if (service != null) {
            service.registerCommonMemBusiness(business);
        } else if (business != null) {
            synchronized (sBusinessSet) {
                sBusinessSet.add(business);
            }
        }
    }

    public static void setupImageService(MultimediaImageService service) {
        if (service != null) {
            sImageReference = new WeakReference<>(service);
            synchronized (sBusinessSet) {
                for (String business : sBusinessSet) {
                    service.registerCommonMemBusiness(business);
                }
            }
        }
    }
}
