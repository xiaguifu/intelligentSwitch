package com.sanmeditech.socket.handler;

import com.sanmeditech.socket.utils.ByteUtils;

/**
 * Created by lwq on 2019/11/21.
 */
public class ConsoleHandler implements Handler {

    byte b;

    @Override
    public boolean isOver(byte[] bytes) {
        System.out.print(ByteUtils.hexString(bytes));
        return false;
    }

    public void add(byte b) {
        this.b = b;
    }

    public boolean available() {
        return false;
    }

    public void execute() {
        System.out.print(String.format("%02x",b));
    }
}
