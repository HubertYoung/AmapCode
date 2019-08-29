package com.alipay.mobile.security.bio.service;

public abstract class BioStoreService extends BioService {
    public abstract String encrypt(String str, String str2);

    public abstract BioStoreResult encryptWithRandom(BioStoreParameter bioStoreParameter);

    public abstract byte[] encryptWithRandom(byte[] bArr, String str, byte[] bArr2);

    public abstract byte[] getRandom();
}
