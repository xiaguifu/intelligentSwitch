package com.sanmeditech.socket.kewo;

import com.sanmeditech.socket.bluetooth.EmitterEvent;
import com.sanmeditech.socket.utils.ByteUtils;
import com.sanmeditech.socket.utils.Protocol;

/**
 * Created by lwq on 2019/11/21.
 */
public class Response implements Protocol {

    public static final int lora_length = 4;
    public static final int cnt_length = 4;
    private byte[] lora;
    private byte[] optionData;
    private byte[] eventData;
    private byte[] cnt;
    private int totalLength = 0;

    private Option option;
    private EmitterEvent event;

    public static byte[] encode(byte[] lora, byte[] data, byte[] cmd, byte[] cnt) {
        byte[] bytes = new byte[4 + data.length + cmd.length + 4];
        int i = 0;
        ByteUtils.appendTo(bytes, lora, i);
        i += lora.length;
        ByteUtils.appendTo(bytes, data, i);
        i += data.length;
        ByteUtils.appendTo(bytes, cmd, i);
        i += cmd.length;
        ByteUtils.appendTo(bytes, cnt, i);
        return bytes;
    }

    @Override
    public byte[] encode() {
        this.totalLength = this.optionData.length + this.eventData.length + 8;
        return encode(this.lora, this.optionData, this.eventData, this.cnt);
    }

    @Override
    public void decode(byte[] bytes) {
        int i = 0;
        this.lora = ByteUtils.read(bytes, i, lora_length);
        i += this.lora.length;
        this.option = new Option();
        this.option.decode(ByteUtils.sub(bytes, i,bytes.length-1-cnt_length));
        this.optionData = ByteUtils.read(bytes, i, this.option.totalLength());
        i += this.optionData.length;
        this.event = new EmitterEvent();
        this.event.decode(ByteUtils.sub(bytes, i,bytes.length-1-cnt_length));
        this.eventData = ByteUtils.read(bytes, i, this.event.totalLength());
        i += this.eventData.length;
        this.cnt = ByteUtils.read(bytes, i, cnt_length);
        this.totalLength = this.optionData.length + this.eventData.length + 8;
    }

    @Override
    public int totalLength() {
        return this.totalLength;
    }
}