package com.sanmeditech.socket.handler;

/**
 * Created by lwq on 2019/12/4.
 */
public class GatewayHandler implements Handler {

    @Override
    public boolean isOver(byte[] bytes) {
        return 0x5B == bytes[0] && 0x5D == bytes[bytes.length - 1];
    }

    @Override
    public void add(byte b) {

    }

    @Override
    public boolean available() {
        return false;
    }

    @Override
    public void execute() {

    }
}
