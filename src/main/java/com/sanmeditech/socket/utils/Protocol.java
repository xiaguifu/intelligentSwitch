package com.sanmeditech.socket.utils;

/**
 * Created by lwq on 2019/11/21.
 */
public interface Protocol {

    byte[] encode();

    void decode(byte[] bytes);

    int totalLength();

}
