package com.sanmeditech.socket.server;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by lwq on 2019/12/17.
 */
@Component
public class RepeaterServer {

    private IoAcceptor acceptor;

    private IoHandler handler;

    public void setHandler(IoHandler handler){
        this.handler = handler;
    }

    public void start(ServerConfig config) throws IOException {
        this.acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors());
        ((NioSocketAcceptor)this.acceptor).getSessionConfig().setReadBufferSize(config.getReadBufferSize());
        ((NioSocketAcceptor)this.acceptor).getSessionConfig().setTcpNoDelay(config.getTcpNoDelay());
        this.acceptor.setHandler(this.handler);
        this.acceptor.bind(new InetSocketAddress(config.getPort()));
        System.out.println("服务端开始监听端口");
    }

    public void stop(){
        if(null != this.acceptor) {
            this.acceptor.dispose(true);
            System.out.println("服务端停止监听端口");
        }
    }

}
