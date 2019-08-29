package com.alipay.mobile.security.bio.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BioMetaInfo {
    public static final int SAMPLE_MODE_ENROLL = 300;
    public static final int SAMPLE_MODE_FACE_LOGIN = 303;
    public static final int SAMPLE_MODE_FACE_SAMPLE = 302;
    public static final int SAMPLE_MODE_VERIFY = 301;
    public static final int TYPE_DOC = 110;
    public static final int TYPE_EYE = 101;
    public static final int TYPE_FACE = 100;
    public static final int TYPE_FACE_EYE = 201;
    public static final int TYPE_FACE_VOICE = 202;
    public static final int TYPE_HANDWRITING = 103;
    public static final int TYPE_IDCARD = 108;
    public static final int TYPE_IDFACE = 200;
    public static final int TYPE_IDPAPERS = 107;
    public static final int TYPE_PAPERS = 109;
    public static final int TYPE_VOICE = 102;
    List<BioAppDescription> appDescriptions = new CopyOnWriteArrayList();
    List<Long> productIDs = new CopyOnWriteArrayList();
    List<BioServiceDescription> serviceDescriptions = new CopyOnWriteArrayList();

    public void addExtService(BioServiceDescription bioServiceDescription) {
        this.serviceDescriptions.add(bioServiceDescription);
    }

    public List<BioServiceDescription> getExtServices() {
        return this.serviceDescriptions;
    }

    public List<BioAppDescription> getApplications() {
        return this.appDescriptions;
    }

    public void addApplication(BioAppDescription bioAppDescription) {
        this.appDescriptions.add(bioAppDescription);
    }

    public List<Long> getProductIDs() {
        return this.productIDs;
    }

    public void addProductID(Long... lArr) {
        if (lArr != null && lArr.length > 0) {
            this.productIDs.addAll(Arrays.asList(lArr));
        }
    }
}
