package com.sanmeditech.socket.bluetooth;

import com.sanmeditech.socket.utils.Protocol;

/**
 * CMD
 */
public class EmitterCommand implements Protocol {

    public byte[] encode() {
        return new byte[0];
    }

    public void decode(byte[] bytes) {

    }

    public int totalLength() {
        return 0;
    }
}
