package com.sanmeditech.socket.client;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Created by lwq on 2019/12/17.
 */
@Component
public class SocketClient {

    private IoConnector connector;

    private IoHandler handler;

    public void setHandler(IoHandler handler){
        this.handler = handler;
    }

    public void start(ClientConfig config){
        this.connector = new NioSocketConnector();
        ((SocketConnector)this.connector).getSessionConfig().setSendBufferSize(config.getReadBufferSize());
        ((SocketConnector)this.connector).getSessionConfig().setTcpNoDelay(config.getTcpNoDelay());
        this.connector.setHandler(this.handler);
        this.connector.connect(new InetSocketAddress(config.getRemote(),config.getPort()));
        System.out.println("客户端开启连接");
    }

    public void stop(){
        if(null != this.connector) {
            this.connector.dispose(false);
            System.out.println("客户端停止连接");
        }
    }

}
