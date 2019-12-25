package com.sanmeditech.socket.kewo;

import com.sanmeditech.socket.bluetooth.EmitterCommand;
import com.sanmeditech.socket.utils.ByteUtils;
import com.sanmeditech.socket.utils.Protocol;

/**
 * Created by lwq on 2019/11/21.
 */
public class Request implements Protocol {

    public static final int lora_length = 4;
    public static final int cnt_length = 4;
    private byte[] lora;
    private byte[] optionData;
    private byte[] cmdData;
    private byte[] cnt;
    private int totalLength = 0;
    private Option option;
    private EmitterCommand command;

    public static byte[] encode(byte[] lora, byte[] opdata, byte[] cmd, byte[] cnt) {
        byte[] bytes = new byte[4 + opdata.length + cmd.length + 4];
        int i = 0;
        ByteUtils.appendTo(bytes, lora, i);
        i += lora.length;
        ByteUtils.appendTo(bytes, opdata, i);
        i += opdata.length;
        ByteUtils.appendTo(bytes, cmd, i);
        i += cmd.length;
        ByteUtils.appendTo(bytes, cnt, i);
        return bytes;
    }

    public byte[] encode() {
        this.totalLength = this.optionData.length + this.cmdData.length + 8;
        return encode(this.lora, this.optionData, this.cmdData, this.cnt);
    }

    public void decode(byte[] bytes) {
        int i = 0;
        this.lora = ByteUtils.read(bytes, i, lora_length);
        i += this.lora.length;
        this.option = new Option();
        this.option.decode(ByteUtils.sub(bytes, i,bytes.length-1-cnt_length));
        this.optionData = ByteUtils.read(bytes, i, option.totalLength());
        i += this.optionData.length;
        this.command = new EmitterCommand();
        this.command.decode(ByteUtils.sub(bytes, i,bytes.length-1-cnt_length));
        this.cmdData = ByteUtils.read(bytes, i, this.command.totalLength());
        i += this.cmdData.length;
        this.cnt = ByteUtils.read(bytes, i, cnt_length);
        this.totalLength = this.optionData.length + this.cmdData.length + 8;
    }

    public int totalLength() {
        return this.totalLength;
    }
}