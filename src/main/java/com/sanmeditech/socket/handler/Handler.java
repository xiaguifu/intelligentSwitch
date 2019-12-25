package com.sanmeditech.socket.handler;

/**
 * Created by lwq on 2019/11/21.
 */
public interface Handler {

    boolean isOver(byte[] bytes);

    public void add(byte b);

    public boolean available();

    public void execute();

}
