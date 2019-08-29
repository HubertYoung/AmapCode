package com.alipay.android.phone.mobilesdk.socketcraft.handshake;

public class HandshakeImpl1Client extends HandshakedataImpl1 implements ClientHandshakeBuilder {
    private String resourceDescriptor = "*";

    public void setResourceDescriptor(String resourceDescriptor2) {
        if (resourceDescriptor2 == null) {
            throw new IllegalArgumentException("http resource descriptor must not be null");
        }
        this.resourceDescriptor = resourceDescriptor2;
    }

    public String getResourceDescriptor() {
        return this.resourceDescriptor;
    }
}
