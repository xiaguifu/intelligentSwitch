package com.sanmeditech.socket.bluetooth;

import com.sanmeditech.socket.utils.Protocol;

/**
 * CMD
 */
public class EmitterCommand implements Protocol {

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
