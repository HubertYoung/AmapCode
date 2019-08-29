package com.j256.ormlite.field.encrypt;

public class OrmLiteEncryptionProcessor {
    private static OrmLiteEncryptionAgent ormLiteEncryptionAgent;

    public static OrmLiteEncryptionAgent getOrmLiteEncryptionAgent() {
        return ormLiteEncryptionAgent;
    }

    public static void setOrmLiteEncryptionAgent(OrmLiteEncryptionAgent ea) {
        ormLiteEncryptionAgent = ea;
    }

    public static byte[] encrypt(byte[] input) {
        if (ormLiteEncryptionAgent != null) {
            return ormLiteEncryptionAgent.encrypt(input);
        }
        return input;
    }

    public static byte[] decrypt(byte[] input) {
        if (ormLiteEncryptionAgent != null) {
            return ormLiteEncryptionAgent.decrypt(input);
        }
        return input;
    }

    public static String encrypt(String input) {
        if (ormLiteEncryptionAgent != null) {
            return ormLiteEncryptionAgent.encrypt(input);
        }
        return input;
    }

    public static String decrypt(String input) {
        if (ormLiteEncryptionAgent != null) {
            return ormLiteEncryptionAgent.decrypt(input);
        }
        return input;
    }
}
