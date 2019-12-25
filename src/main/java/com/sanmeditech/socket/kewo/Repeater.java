package com.sanmeditech.socket.kewo;

import com.sanmeditech.socket.utils.ByteUtils;
import com.sanmeditech.socket.utils.Protocol;

/**
 * 中继器
 */
public class Repeater implements Protocol {

    public static final byte type_response = 0x0A;
    public static final byte type_request = 0x09;
    public static byte[] start = new byte[]{0x5A, (byte) 0xA5};
    public static final int address_length = 3;

    private byte[] address;
    private boolean isRequest;
    private byte[] body;
    private int totalLength = 0;
    private Request request;
    private Response response;

    public static byte[] encode(byte[] address, boolean isRequest, byte[] body) {
        byte[] bytes = new byte[body.length + 16];
        int i = 0;
        ByteUtils.appendTo(bytes, start, i);
        i += start.length;
        ByteUtils.appendTo(bytes, address, i);
        i += address.length;
        bytes[i] = isRequest ? type_request : type_response;//09-PC下发数据,0a-读写器上传数据
        i++;
        bytes[i] = (byte) (0xFF & body.length >> 8);
        i++;
        bytes[i] = (byte) (0x00FF & body.length);
        i++;
        i++;
        i++;
        ByteUtils.appendTo(bytes, body, i);
        i += body.length;
        i += 4;
        byte[] crc = CRCChecker.checkCRC16(bytes, bytes.length - 2);
        ByteUtils.appendTo(bytes, crc, i);
        return bytes;
    }


    public byte[] encode() {
        this.totalLength = body.length + 16;
        return encode(this.address, this.isRequest, this.body);
    }

    public static boolean check(byte[] bytes) {
        if (bytes != null && bytes.length >= 16 && bytes[0] == start[0] && bytes[1] == start[1]) {
            int length = bytes[6] << 8 | bytes[7];
            return bytes.length == 16 + length;
        }
        return false;
    }


    public void decode(byte[] bytes) {
        if(check(bytes)) {
            int i = 2;
            this.address = ByteUtils.read(bytes, i, address_length);
            i += address_length;
            this.isRequest = bytes[i] == type_request;
            i++;
            int length = bytes[i] << 8 | bytes[i + 1];
            i += 2;
            this.body = ByteUtils.read(bytes, i, length);
            if (this.isRequest) {
                this.request = new Request();
                this.request.decode(this.body);
            } else {
                this.response = new Response();
                this.response.decode(this.body);
            }
            this.totalLength = this.body.length + 16;
        }
    }


    public int totalLength() {
        return this.totalLength;
    }
}
