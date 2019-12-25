package com.sanmeditech.socket.bluetooth;

import com.sanmeditech.socket.utils.Protocol;

/**
 * EVENT
 */
public class EmitterEvent implements Protocol {

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    @Override
    public void decode(byte[] bytes) {

    }

    @Override
    public int totalLength() {
        return 0;
    }
}
