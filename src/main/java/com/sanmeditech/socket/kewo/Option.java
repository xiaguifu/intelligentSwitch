package com.sanmeditech.socket.kewo;

import com.sanmeditech.socket.utils.Protocol;

/**
 * OPDATA
 */
public class Option implements Protocol {

    private byte type;
    private byte[] data;
    private int totalLength;

    public static byte[] encode(byte type, byte[] data) {
        if (type > 0 && data != null && data.length > 0) {
            byte length = (byte) data.length;
            byte[] bytes = new byte[data.length + 2];
            bytes[0] = type;
            bytes[1] = length;
            System.arraycopy(data, 0, bytes, 2, data.length);
        }
        return new byte[]{0, 0};
    }

    public byte[] encode() {
        this.totalLength = this.data.length + 2;
        return encode(this.type, this.data);
    }

    public void decode(byte[] bytes) {
        assert null != bytes && bytes.length >= 2;
        int i = 0;
        this.type = bytes[i];
        int length = bytes[i + 1];
        if (length > 0) {
            this.data = new byte[length];
            System.arraycopy(bytes, i + 2, this.data, 0, length);
        }
        this.totalLength = length + 2;
    }

    public int totalLength() {
        return this.totalLength;
    }
}