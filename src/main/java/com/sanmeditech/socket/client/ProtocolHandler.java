package com.sanmeditech.socket.client;

import com.sanmeditech.socket.utils.ByteUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.FilterEvent;
import org.springframework.stereotype.Component;

/**
 * Created by lwq on 2019/12/4.
 */
@Component
public class ProtocolHandler implements IoHandler {

    private byte[] receive;

    private IoSession session;

    public byte[] getReceive() {
        return this.receive;
    }

    public void send(byte[] data){
        this.session.write(IoBuffer.wrap(data));
    }

    public void sessionCreated(IoSession ioSession) throws Exception {
        System.out.println("session created");
        this.session = ioSession;
    }

    public void sessionOpened(IoSession ioSession) throws Exception {
        System.out.println("session opened");
    }

    public void sessionClosed(IoSession ioSession) throws Exception {
        System.out.println("session closed");
    }

    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
        System.out.println("session idle : " + idleStatus.toString());
    }

    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        System.out.println("exception caught : " + throwable.toString());
    }

    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        IoBuffer buffer = (IoBuffer) o;
        System.out.println("message received length : " + buffer.remaining());
        this.receive = new byte[buffer.remaining()];
        buffer.get(this.receive);
//        ioSession.write(buffer);
    }

    public void messageSent(IoSession ioSession, Object o) throws Exception {
        System.out.println("message sent");
    }

    public void inputClosed(IoSession ioSession) throws Exception {
        System.out.println("input closed");
        ioSession.closeNow();
    }

    public void event(IoSession ioSession, FilterEvent filterEvent) throws Exception {
        System.out.println("event : " + filterEvent.toString());
    }
}
