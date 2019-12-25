package com.sanmeditech.socket.client;

/**
 * Created by lwq on 2019/12/18.
 */
public class ClientConfig {

    private int readBufferSize = 1024*1024;
    private boolean tcpNoDelay = true;
    private String remote = "127.0.0.1";
    private int port = 30008;
    public void setReadBufferSize(int readBufferSize){
        this.readBufferSize = readBufferSize;
    }

    public void setTcpNoDelay(boolean tcpNoDelay){
        this.tcpNoDelay = tcpNoDelay;
    }

    public void setRemote(String remote){
        this.remote = remote;
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

    public String getRemote(){
        return this.remote;
    }

    public int getPort(){
        return this.port;
    }

}
