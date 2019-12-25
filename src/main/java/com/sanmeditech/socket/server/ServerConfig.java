package com.sanmeditech.socket.server;

/**
 * Created by lwq on 2019/12/18.
 */
public class ServerConfig {

    private int readBufferSize = 1024*1024;
    private boolean tcpNoDelay = true;
    private int port = 30008;
    public void setReadBufferSize(int readBufferSize){
        this.readBufferSize = readBufferSize;
    }

    public void setTcpNoDelay(boolean tcpNoDelay){
        this.tcpNoDelay = tcpNoDelay;
    }

    public void setPort(int port){
        this.port = port;
    }

    public int getReadBufferSize(){
        return this.readBufferSize;
    }

    public boolean getTcpNoDelay(){
        return this.tcpNoDelay;
    }

    public int getPort(){
        return this.port;
    }

}
