package com.j256.ormlite.field.encrypt;

public interface OrmLiteEncryptionAgent {
    String decrypt(String str);

    byte[] decrypt(byte[] bArr);

    String encrypt(String str);

    byte[] encrypt(byte[] bArr);
}
